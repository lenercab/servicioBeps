/**
 * 
 */
package co.gov.colpensiones.beps.previnculacion.businesslogic.crear;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import co.gov.colpensiones.beps.buc.businesslogic.BLHiloInvocacionBUC;
import co.gov.colpensiones.beps.comunes.utilidades.Constantes;
import co.gov.colpensiones.beps.comunes.utilidades.ConstantesLoggerServicios;
import co.gov.colpensiones.beps.comunes.utilidades.RegexUtil;
import co.gov.colpensiones.beps.comunes.utilidades.Util;
import co.gov.colpensiones.beps.comunes.utilidades.Validador;
import co.gov.colpensiones.beps.dal.utilidades.DataStoredProcedure;
import co.gov.colpensiones.beps.dal.utilidades.DataTable;
import co.gov.colpensiones.beps.dal.utilidades.DbParameter;
import co.gov.colpensiones.beps.excepciones.DataAccessException;
import co.gov.colpensiones.beps.excepciones.LogicalException;
import co.gov.colpensiones.beps.log.ConstantesLogger;
import co.gov.colpensiones.beps.log.LoggerBeps;
import co.gov.colpensiones.beps.log.TimeTracer;
import co.gov.colpensiones.beps.previnculacion.businesslogic.BLPrevinculado;
import co.gov.colpensiones.beps.previnculacion.businesslogic.DAPrevinculado;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstadoEjecucion;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContexto;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionRespuestaPrevinculacion;
import co.gov.colpensiones.beps.vinculacion.businesslogic.crear.InformacionVinculado;

/**
 * <b>Descripcion:</b> Clase que contiene la lógica para registrar el resultado del estudio<br/>
 * <b>Caso de Uso:</b> GVI-VIN-1-FAB-07-ResultadoPrevinculacion <br/>
 * 
 * @author Helen Acero <hacero@heinsohn.com.co>
 */
public class BLRegistrarResultadoEstudio extends BLPrevinculado {

    /* Clase de acceso a datos */
    DAPrevinculado daPrevinculado = null;
    
    /** Atributo para tomar trazas de tiempo durante la ejecución del preproceso.*/
    private TimeTracer tracer;

    /**
     * Método contructor
     * 
     * @param log
     *            , instancia del log
     */
    public BLRegistrarResultadoEstudio(LoggerBeps log) {
        super(log);
        daPrevinculado = new DAPrevinculado();
    }

    /**
     * Método que realiza las validaciones de los datos de entrada CU: GVI-VIN-1-FAB-07-ResultadoPrevinculacion
     * 
     * @param informacionContexto
     *            , información del contexto
     * @param informacionResultadoPrevinculacion
     *            , información del resultado
     * @return el estado de la ejecución
     */
    public TipoEstadoEjecucion registrarResultadoEstudio(TipoInformacionContexto informacionContexto,
            TipoInformacionRespuestaPrevinculacion informacionResultadoPrevinculacion) {

        TipoEstadoEjecucion response = new TipoEstadoEjecucion();
        HashMap<String, Object> payLoadTrace = new HashMap<String, Object>();
        HashMap<String, String> metaData = new HashMap<String, String>();
        HashMap<String, Object> payLoad = new HashMap<String, Object>();
        StringBuffer lstErrores = new StringBuffer();

        /* obtener datos básicos */
        String tipoDocumento = "";
        String numDocumento = "";

        if (informacionResultadoPrevinculacion != null && informacionResultadoPrevinculacion.getIdentificacion() != null) {
            tipoDocumento = informacionResultadoPrevinculacion.getIdentificacion().getTipoDocumento();
            numDocumento = informacionResultadoPrevinculacion.getIdentificacion().getNumeroDocumento();
        }

        /* Manejo de Log */
        metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, tipoDocumento);
        metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, numDocumento);
        
        
        if(informacionContexto != null){
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_RADICADO, informacionContexto.getIdCorrelacion());
            metaData.put(ConstantesLoggerServicios.USUARIO_SISTEMA_EXTERNO, informacionContexto.getUsuarioSistemaExterno());
        }
        
        payLoad.putAll(metaData);

        try {
            /* registro en el log */
            payLoadTrace.put(ConstantesLogger.OBJETO_CONTEXTO_ENTRADA, informacionContexto);
            payLoadTrace.put(ConstantesLogger.OBJETO_NEGOCIO_ENTRADA, informacionResultadoPrevinculacion);
            log.trace(payLoadTrace, metaData);

            /* realizar las validaciones de datos de entrada */
            if (informacionContexto == null) {
                lstErrores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "Contexto"));
            } else {
                lstErrores.append(new Validador<TipoInformacionContexto>().ValidarDataContract(informacionContexto));
            }

            if (informacionResultadoPrevinculacion == null) {
                lstErrores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "InformacionResultadoPrevinculacion"));
            } else {
                lstErrores.append(new Validador<TipoInformacionRespuestaPrevinculacion>()
                        .ValidarDataContract(informacionResultadoPrevinculacion));
            }

            if (informacionContexto != null || informacionResultadoPrevinculacion != null) {
                lstErrores.append(this.validarDatosEntrada(informacionContexto, informacionResultadoPrevinculacion));
            }

            /* se genera errorres */
            if (lstErrores.toString().length() != 0) {
                payLoad.put(Constantes.MSJ_ERROR_LOG, lstErrores.toString());
                log.info(payLoad, metaData);
                response = respuestaNegocioServicio(Constantes.COD_FORMATO_INVALIDO_OBLIGATORIO_NO_RECIBIDO, lstErrores.toString());
            } else {
                response = this.logicaRegistrarResultadoEstudio(informacionContexto, informacionResultadoPrevinculacion);
            }

        } catch (DataAccessException e1) {
            generarLogError(metaData, true, e1);
            response = respuestaErrorTecnicoServicio();

        } catch (LogicalException e1) {
            generarLogError(metaData, false, e1);
            response = respuestaErrorTecnicoServicio();

        } catch (Exception e) {
            generarLogError(metaData, false, e);
            response = respuestaErrorTecnicoServicio();
        } finally {
            payLoadTrace.put(ConstantesLogger.OBJETO_NEGOCIO_SALIDA, response);
            log.trace(payLoadTrace, metaData);
        }

        return response;
    }

    /**
     * Método encargado de validar los datos de entrada y de salida
     * 
     * @param informacionContexto
     *            , información del contexto
     * @param informacionResultadoPrevinculacion
     *            , información del resultado de la previnculación
     * @return retorna vacío si cumple con las validaciones, false en caso contrario
     * @throws DataAccessException
     *             , si se genera un error
     */
    private String validarDatosEntrada(TipoInformacionContexto informacionContexto,
            TipoInformacionRespuestaPrevinculacion informacionResultadoPrevinculacion) throws DataAccessException {
        StringBuffer lstErrores = new StringBuffer();

        /* validar contexto */
        if (informacionContexto != null) {
            if (informacionContexto.getIdCorrelacion() == null) {
                lstErrores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "IdCorrelacion"));
            if (informacionContexto.getUsuarioSistemaExterno() == null) {
            	lstErrores.append(Constantes.MSJ_DATO_OBLIGATORIO.replaceAll(Constantes.PARAMETRO0, "usuarioSistemaExterno"));
            } else {
                if (!Pattern.compile(Constantes.ER_VALIDAR_USUARIO_SISTEMA).matcher(informacionContexto.getUsuarioSistemaExterno()).matches()) {
                	lstErrores.append(Constantes.MSJ_ESTRUCTURA_USUARIO_INVALIDA.replaceAll(Constantes.PARAMETRO0, "UsuarioSistemaExterno"));
                }
            }
         } else {
                if (informacionContexto.getIdCorrelacion().length() < 1 || informacionContexto.getIdCorrelacion().length() > 50) {
                    lstErrores.append(Constantes.MSJ_lONGITUD_DATO.replace(Constantes.PARAMETRO0, "IdCorrelacion").replaceAll(
                            Constantes.PARAMETRO1, "1 a 50"));
                }

                if (!RegexUtil.isValid(Constantes.ER_VALIDAR_ID_CORRELACION, informacionContexto.getIdCorrelacion())) {
                    lstErrores.append(Constantes.MSJ_ESTRUCTURA_IDCORRELACION_INVALIDA.replaceAll(Constantes.PARAMETRO0, "IdCorrelacion"));
                }
            }
        }

        /* validar la respuesta de la previncualción */
        if (informacionResultadoPrevinculacion == null) {
            lstErrores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "InformacionResultadoPrevinculacion"));
        } else {

            /* Validacion tipo de documento */
            ArrayList<String> tiposDocumento = Util.getResourcePropertyArray(Constantes._HOMOLOGACION_COMUN_NAME,
                    Constantes.PREFIJO_LLAVES_TIPO_DOCUMENTO);
            if (!tiposDocumento.contains(informacionResultadoPrevinculacion.getIdentificacion().getTipoDocumento())) {
                lstErrores.append(Constantes.MSJ_ERROR_DATO_NO_VALIDO.replaceAll(Constantes.PARAMETRO0, "TipoDocumento")
                        + tiposDocumento.toString() + "\n");
            }

            /* validar el código de rechazo */
            if (Constantes.NO.equals(informacionResultadoPrevinculacion.getIndicadorAprobacion())) {
                if (informacionResultadoPrevinculacion.getCodigoRechazo() == null
                        || informacionResultadoPrevinculacion.getCodigoRechazo().isEmpty()) {

                    lstErrores.append(Constantes.VALIDACION_CODIGO_RECHAZO.replace(Constantes.PARAMETRO0, "CodigoRechazo").replaceAll(
                            Constantes.PARAMETRO1, "IndicadorAprobacion"));
                } else {
                    ArrayList<String> codigosRechazo = daPrevinculado.consultarCodigosRechazos();
                    ArrayList<String> codigosRechazoInvalidos = new ArrayList<String>();
                    
                    for(String codigoRechazo : informacionResultadoPrevinculacion.getCodigoRechazo()){
                        if(codigoRechazo.length() == 3){
                            if (Pattern.matches(Constantes.ER_DATOS_NUMERICOS, codigoRechazo)){
                                if (!codigosRechazo.contains(codigoRechazo)) {
                                    codigosRechazoInvalidos.add(codigoRechazo);
                                }                           
                            } else{
                                lstErrores.append(Constantes.MSJ_ERROR_DATO_NUMERICO.replace(Constantes.PARAMETRO0, "CodigoRechazo " + codigoRechazo));
                            }
                        } else {
                            lstErrores.append(Constantes.MSJ_lONGITUD_DATO.replace(Constantes.PARAMETRO0, "CodigoRechazo "+codigoRechazo)
                                    .replaceAll(Constantes.PARAMETRO1, "3"));
                        }
                    } 
                    
                    if(!codigosRechazoInvalidos.isEmpty()){
                        String mensaje = "Los siguientes códigos de rechazo " + codigosRechazoInvalidos.toString() +  " tienen un valor que ";
                        lstErrores.append(Constantes.MSJ_ERROR_DATO_NO_VALIDO.replaceAll(Constantes.PARAMETRO0, mensaje)
                                + codigosRechazo.toString() + "\n");
                    }
                }
            }
        }

        return lstErrores.toString();
    }

    /**
     * Método que contiene la lógica para registrar el resultado
     * 
     * @param informacionContexto
     *            , información del contexto
     * @param informacionResultadoPrevinculacion
     *            , información del resultado
     * @return el estado de la ejecución
     * @throws LogicalException
     *             ,DataAccessException, si se genera un error
     */
    private TipoEstadoEjecucion logicaRegistrarResultadoEstudio(TipoInformacionContexto informacionContexto,
            TipoInformacionRespuestaPrevinculacion informacionResultadoPrevinculacion) throws DataAccessException, LogicalException {
        TipoEstadoEjecucion response = null;
        /* validar que el solicitante exista */
        DataTable infoSolicitante = daPrevinculado.consultarInformacionPrevinculado(informacionResultadoPrevinculacion.getIdentificacion());

        /* el solicitante existe */
        if (infoSolicitante != null && infoSolicitante.getRows() != null && infoSolicitante.getRows().size() > 0) {

            /* valida la respuesta del estudio */
            if (Constantes.SI.equals(informacionResultadoPrevinculacion.getIndicadorAprobacion())) {
            	String respuestaPlenitud=this.estudioAprobado(informacionContexto, informacionResultadoPrevinculacion, infoSolicitante);
                if (Constantes.COD_INVOCACION_EXITOSA.equals(respuestaPlenitud)) {
    				response = respuestaExitosaServicio();
    			} else {
    				if (Constantes.COD_ERROR_INVOCAR_PLENITUD.equals(respuestaPlenitud)) {
    					response = respuestaErrorInvocacionPlenitud();
    				} else {
    					response = respuestaCreacionPreVinculadoSinCuentaIndvidual(respuestaPlenitud);
    				}
    			}
            } else {
                daPrevinculado.previnculadoRechazado(informacionContexto, informacionResultadoPrevinculacion.getIdentificacion(),
                        informacionResultadoPrevinculacion.getCodigoRechazo());
                response = respuestaExitosaServicio();
            }
        } else {
            /* el solicitante no existe */
            response = respuestaNegocioServicio(Constantes.COD_ERROR_LOGICA_NEGOCIO, Constantes.MSJ_SOLICITANTE_INNEXISTENTE);
        }

        return response;
    }

    /**
     * Método que contiene la lógica cuando se aprueba el estudio
     * 
     * @param informacionContexto
     *            , información del contexto
     * @param informacionResultadoPrevinculacion
     *            , información del resultado aprobado
     * @param infoSolicitante
     *            , información del solicitante
     * @throws DataAccessExceptionm
     *             si se genera un error en el acceso a datos
     * @throws LogicalException
     *             , si se genera un error en la lógica
     */
    private String estudioAprobado(TipoInformacionContexto informacionContexto,
            TipoInformacionRespuestaPrevinculacion informacionResultadoPrevinculacion, DataTable infoSolicitante)
            throws DataAccessException, LogicalException {

        /*
         * Se obtiene el id del vinculado necesario para registar el histórico de estado
         */
        BigDecimal idVinculado = new BigDecimal(infoSolicitante.getRows().get(0).getValue("vvi_pk_id").toString());

     // crear el vinculado
        DataStoredProcedure result= daPrevinculado.cambiarEstadoPrevinculadoAVinculado(informacionContexto, informacionResultadoPrevinculacion.getIdentificacion(),
                idVinculado);
        
        if (result != null && result.getParametrosSalida() != null && result.getParametrosSalida().get(0) != null
                && result.getParametrosSalida().get(0).getParameterValue() != null) {
        	
        	BLHiloInvocacionBUC blHiloInvocacionBUC= new BLHiloInvocacionBUC(result.getParametrosSalida().get(0).getParameterValue().toString(),Constantes.TIPO_NOVEDAD_CREACION_BUC,idVinculado.longValue(), tracer,log);
        	blHiloInvocacionBUC.start();
                	
        }
        

        /* obtener la lista de genero */
        DataTable respuestaGenero = daPrevinculado.consultarGenero((String) infoSolicitante.getRows().get(0).getValue("vpe_sexo"));

        /* homologar los datos para plenitud */
        InformacionVinculado informacionVinculado = this.homologarDatosPlenitudCrearCuenta(infoSolicitante,
                informacionResultadoPrevinculacion, respuestaGenero);
        
        boolean seCreoCuenta = false;
        /*variable que guarda la respuesta informada por plenitud*/
        String respuestaPlenitud="";
        
        try{
            /* crear la cuenta individual */
            List<DbParameter> rtaCta = daPrevinculado.crearCuentaIndvidual(informacionVinculado);
            
            if (rtaCta != null && Constantes.COD_INVOCACION_EXITOSA_AS400.equals((String) rtaCta.get(1).getParameterValue())) {
                seCreoCuenta = true;
                respuestaPlenitud=Constantes.COD_INVOCACION_EXITOSA;
                
            } else {
                /* guardar en el log la respuesta de plentitud */
                HashMap<String, Object> payLoad = new HashMap<String, Object>();
                HashMap<String, String> metaData = new HashMap<String, String>();
                metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, informacionResultadoPrevinculacion.getIdentificacion().getTipoDocumento());
                metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, informacionResultadoPrevinculacion.getIdentificacion().getNumeroDocumento());               
                
                //se almacena la respuesta de plenitud
                respuestaPlenitud=rtaCta != null && rtaCta.size() > 2 ? (String) rtaCta.get(1).getParameterValue()
                         : Constantes.RESPUESTA_FALLIDA_INVOCACION_PLENITUD;
                payLoad.put("Respuesta de plentud: Código =", respuestaPlenitud);
               
                log.info(payLoad, metaData);
            }
        } catch (DataAccessException e){
        	respuestaPlenitud=Constantes.COD_ERROR_INVOCAR_PLENITUD;
            generarLogError(e.getMetaData(), true, e);   
        }        

        /* actualizar el estado del vinculado */
        daPrevinculado.cambiarEstadoCuentaVinculado(informacionVinculado.getIdentificadorVinculado(), seCreoCuenta);
        return respuestaPlenitud;
    }
    
}
