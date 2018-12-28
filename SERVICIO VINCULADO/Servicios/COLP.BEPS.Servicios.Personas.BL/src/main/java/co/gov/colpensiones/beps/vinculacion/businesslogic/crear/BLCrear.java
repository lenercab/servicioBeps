package co.gov.colpensiones.beps.vinculacion.businesslogic.crear;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import co.gov.colpensiones.beps.buc.businesslogic.BLHiloInvocacionBUC;
import co.gov.colpensiones.beps.comunes.enumeraciones.TipoConexionBaseDatosEnum;
import co.gov.colpensiones.beps.comunes.utilidades.Constantes;
import co.gov.colpensiones.beps.comunes.utilidades.ConstantesLoggerServicios;
import co.gov.colpensiones.beps.comunes.utilidades.DatabaseManager;
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
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstadoEjecucion;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContexto;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoCorreoElectronico;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoDatoTelefono;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionDireccionPersonaNatural;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionGeneralVinculado;
import co.gov.colpensiones.beps.vinculacion.businesslogic.BLVinculado;
import co.gov.colpensiones.beps.vinculacion.businesslogic.DAReactivar;
import co.gov.colpensiones.beps.vinculacion.businesslogic.DAVinculado;
import co.gov.colpensiones.beps.vinculacion.businesslogic.reActivar.BLReactivar;

/**
 * <b>Descripcion:</b> Clase encargada de la logica de negocio para la creación de un vinculado <br/>
 * <b>Caso de Uso:</b> GVI-VIN-1-FAB-02-RegistrarVinculado. <br/>
 * 
 * @author Helen Acero <hacero@heinsohn.com.co>
 * 
 * <b>Control de cambio:</b> ControlCambios_MaquinadeEstados<br/>
 * <b>Fecha:</b> 19 de Septiembre 2014 <br/>
 * 
 * @author Yenny Ñustez <ynustez@heinsohn.com.co>
 * 
 */
public class BLCrear extends BLVinculado {

    /** Clase de accedo a datos */
    DAVinculado daVinculado = null;

    /** Atributo para tomar trazas de tiempo durante la ejecución del preproceso.*/
    private TimeTracer tracer;
    
    /**
     * Método contructor
     * 
     * @param log
     *            log con el que se va a escribir de la BD
     */
    public BLCrear(LoggerBeps log) {
        super(log);
    }

    /**
     * Método que contiene la lógica de negocio para crear un vinculado
     * 
     * Caso de uso:GVI-VIN-1-FAB-02-RegistrarVinculado
     * 
     * @param informacionVinculado
     *            , información realaciona a un Vinculado
     * @return estado de la ejecución
     */
    public TipoEstadoEjecucion crearvinculado(TipoInformacionContexto informacionContexto,
            TipoInformacionGeneralVinculado informacionVinculado) {
        TipoEstadoEjecucion response = null;
        StringBuffer lstErrores = new StringBuffer();
        DAVinculado daVinculado = new DAVinculado();
        DAReactivar daReactivar = new DAReactivar();
        BLReactivar blReactivar = new BLReactivar(log);
        DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);
        HashMap<String, Object> payLoadTrace = new HashMap<String, Object>();
        HashMap<String, String> metaData = new HashMap<String, String>();
        HashMap<String, Object> payLoad = new HashMap<String, Object>();

        // obtener datos básicos
        String tipoDocumento = "";
        String numDocumento = "";

        if (informacionVinculado != null && informacionVinculado.getIdentificacion() != null) {
            tipoDocumento = informacionVinculado.getIdentificacion().getTipoDocumento();
            numDocumento = informacionVinculado.getIdentificacion().getNumeroDocumento();
        }

        // Manejo de Log
        metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, tipoDocumento);
        metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, numDocumento);
        if (informacionContexto != null) {
        	metaData.put(ConstantesLoggerServicios.METADATA_NUM_RADICADO, informacionContexto.getIdCorrelacion());
            metaData.put(ConstantesLoggerServicios.USUARIO_SISTEMA_EXTERNO, informacionContexto.getUsuarioSistemaExterno());
        }
        payLoad.putAll(metaData);

        try {
            // registro en el log
            payLoadTrace.put(ConstantesLogger.OBJETO_CONTEXTO_ENTRADA, informacionContexto);
            payLoadTrace.put(ConstantesLogger.OBJETO_NEGOCIO_ENTRADA, informacionVinculado);
            log.trace(payLoadTrace, metaData);

            // validación de datos de entrada
            if (informacionContexto == null) {
                lstErrores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "Contexto"));
            } else {
                lstErrores.append(new Validador<TipoInformacionContexto>().ValidarDataContract(informacionContexto));
            }
            if (informacionVinculado == null) {
                lstErrores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "InformacionVinculado"));
            } else {
                lstErrores.append(new Validador<TipoInformacionGeneralVinculado>().ValidarDataContract(informacionVinculado));
            }
            if (informacionContexto != null || informacionVinculado != null) {
                lstErrores.append(this.validarDatosEntradaCrear(informacionContexto, informacionVinculado));
            }

            // se genera errorres
            if (lstErrores.toString().length() != 0) {
                payLoad.put(Constantes.MSJ_ERROR_LOG, lstErrores.toString());
                log.info(payLoad, metaData);
                response = respuestaNegocioServicio(Constantes.COD_FORMATO_INVALIDO_OBLIGATORIO_NO_RECIBIDO, lstErrores.toString());
            } else {
            	if(daReactivar.consultarProspecto(database, numDocumento) != null){
            		response = this.logicaCrearVinculado(informacionContexto, informacionVinculado);
            	}else{
            		response = blReactivar.logicaReActivarVinculado(informacionContexto, informacionVinculado);
            	}
                
            }
        } catch (DataAccessException e2) {
            generarLogError(metaData, true, e2);
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
     * Método encargado de realizar las validaciones de datos de entrada para la operación crear
     * 
     * @param informacionContexto
     *            , datos del contexto
     * @param informacionVinculado
     *            , datos de un vinculado
     * @return la cadena con las validaciones que no se cumplen, nulo o vacio si todas las validaciones se cumplen
     */
    private String validarDatosEntradaCrear(TipoInformacionContexto informacionContexto,
            TipoInformacionGeneralVinculado informacionVinculado) {

        StringBuilder errores = new StringBuilder();

        // validacion del contexto
        if (informacionContexto != null) {
            // validar usuario
            if (informacionContexto.getUsuarioSistemaExterno() == null || informacionContexto.getUsuarioSistemaExterno().isEmpty()) {
                errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "UsuarioSistemaExterno"));
                errores.append(Constantes.MSJ_ESTRUCTURA_IDCORRELACION_INVALIDA.replaceAll(Constantes.PARAMETRO0, "IdCorrelacion"));
            }

            // validar numero radicado
            if (informacionContexto.getIdCorrelacion() == null || informacionContexto.getIdCorrelacion().isEmpty()) {
                errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "IdCorrelacion"));
            } else {
                if (!RegexUtil.isValid(Constantes.ER_VALIDAR_ID_CORRELACION, informacionContexto.getIdCorrelacion())) {
                    errores.append(Constantes.MSJ_ESTRUCTURA_IDCORRELACION_INVALIDA.replaceAll(Constantes.PARAMETRO0, "IdCorrelacion"));
                }
            }
        }

        if (informacionVinculado != null) {
            // identificador
            if (informacionVinculado.getIdentificacion() == null) {
                errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "Identificacion"));
            } else {
                if (informacionVinculado.getIdentificacion().getTipoDocumento() != null) {
                    // Validacion tipo de documento
                    ArrayList<String> tiposDocumento = Util.getResourcePropertyArray(Constantes._HOMOLOGACION_COMUN_NAME,
                            Constantes.PREFIJO_LLAVES_TIPO_DOCUMENTO);
                    if (!tiposDocumento.contains(informacionVinculado.getIdentificacion().getTipoDocumento())) {
                        errores.append(Constantes.MSJ_ERROR_DATO_NO_VALIDO.replaceAll(Constantes.PARAMETRO0, "TipoDocumento")
                                + tiposDocumento.toString() + "\n");
                    }
                }
            }

            // información económica
            if (informacionVinculado.getInformacionEconomica() == null) {
                errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "InformacionEconomica"));
            }

            // autorizaciones
            if (informacionVinculado.getTipoAutorizacion() == null) {
                errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "TipoAutorizacion"));
            }

            // validar canal de vinculación
            ArrayList<String> tiposDocumento = Util.getResourcePropertyArray(Constantes._HOMOLOGACION_COMUN_NAME,
                    Constantes.PREFIJO_LLAVES_CANALES);
            if (informacionVinculado.getCanal() == null) {
                errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "Canal"));
                errores.append(Constantes.MSJ_ERROR_DATO_NO_VALIDO.replaceAll(Constantes.PARAMETRO0, "Canal") + tiposDocumento.toString()
                        + "\n");
            } else {
                // validar formato
                if (!tiposDocumento.contains(informacionVinculado.getCanal())) {
                    errores.append(Constantes.MSJ_ERROR_DATO_NO_VALIDO.replaceAll(Constantes.PARAMETRO0, "Canal")
                            + tiposDocumento.toString() + "\n");
                }
            }

            // validaciones para la ubicación
            if (informacionVinculado != null && informacionVinculado.getInformacionUbicacionResidencia() != null) {

                // valida obligatoriedad de la lista de direcciones de
                // residencia
                if (informacionVinculado.getInformacionUbicacionResidencia().getDirecciones() == null
                        || informacionVinculado.getInformacionUbicacionResidencia().getDirecciones().getDireccion() == null
                        || informacionVinculado.getInformacionUbicacionResidencia().getDirecciones().getDireccion().isEmpty()) {
                    errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "ListaDireccion"));
                } else {
                    boolean esPrincipal = false;
                    for (TipoInformacionDireccionPersonaNatural infoDir : informacionVinculado.getInformacionUbicacionResidencia()
                            .getDirecciones().getDireccion()) {
                        // validar obligatoriedad de departamento
                        if (infoDir.getDepartamento() == null || infoDir.getDepartamento().getCodigo() == null) {
                            errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "CodigoDepartamento"));
                        } else {
                            if (!RegexUtil.isValid(Constantes.ER_VALIDAR_COD_DEPARTAMENTO, infoDir.getDepartamento().getCodigo())) {
                                errores.append(Constantes.MSJ_VALIDAR_DEPTO_CIUDAD.replaceAll(Constantes.PARAMETRO0, "CodigoDepartamento")
                                        .replaceAll(Constantes.PARAMETRO1, "2"));
                            }
                        }

                        // validar obligatoriedad de ciudad
                        if (infoDir.getMunicipio() == null || infoDir.getMunicipio().getCodigo() == null) {
                            errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "CodigoMunicipio"));
                        } else {
                            if (!RegexUtil.isValid(Constantes.ER_VALIDAR_COD_MUNICIPIO, infoDir.getMunicipio().getCodigo())) {
                                errores.append(Constantes.MSJ_VALIDAR_DEPTO_CIUDAD.replaceAll(Constantes.PARAMETRO0, "CodigoMunicipio")
                                        .replaceAll(Constantes.PARAMETRO1, "5"));
                            }
                        }

                        if (Constantes.SI.equals(infoDir.getEsPrincipal())) {
                            esPrincipal = true;
                        }
                    }
                    if (!esPrincipal) {
                        errores.append(Constantes.MSJ_LISTA_DIRECCIONES_SIN_ID_PRINCIPAL.replace(Constantes.PARAMETRO0, "EsPrincipal"));

                    }
                }

                // valida obligatoriedad de la lista de teléfonos
                if (informacionVinculado.getInformacionUbicacionResidencia().getTelefonos() == null
                        || informacionVinculado.getInformacionUbicacionResidencia().getTelefonos().getTelefono() == null
                        || informacionVinculado.getInformacionUbicacionResidencia().getTelefonos().getTelefono().isEmpty()) {
                    errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "ListaTelefonos"));
                } else {
                    boolean esPrincipal = false;
                    for (TipoDatoTelefono tipoDatoTel : informacionVinculado.getInformacionUbicacionResidencia().getTelefonos()
                            .getTelefono()) {
                        // validar estructura interna del teléfono para el
                        // indicativo pais
                        if (tipoDatoTel.getIndicativoPais() == null) {
                            errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "IndicativoPais"));
                        }
                        // validar estructura interna del teléfono para el
                        // indicativo ciudad
                        if (tipoDatoTel.getIndicativoCiudad() == null) {
                            errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "IndicativoCiudad"));
                        }
                        if (Constantes.SI.equals(tipoDatoTel.getEsPrincipal())) {
                            esPrincipal = true;
                        }
                    }
                    if (!esPrincipal) {
                        errores.append(Constantes.MSJ_LISTA_TELEFONOS_SIN_ID_PRINCIPAL.replace(Constantes.PARAMETRO0, "EsPrincipal"));

                    }
                }

                // validar obligatoriedad de lista de emails
                if (informacionVinculado.getInformacionUbicacionResidencia().getCorreosElectronicos() != null
                        && !informacionVinculado.getInformacionUbicacionResidencia().getCorreosElectronicos().getCorreoElectronico()
                                .isEmpty()) {
                    boolean esPrincipal = false;
                    for (TipoCorreoElectronico tipoCorreoE : informacionVinculado.getInformacionUbicacionResidencia()
                            .getCorreosElectronicos().getCorreoElectronico()) {
                        // validar correo
                        if (tipoCorreoE.getDireccion() == null) {
                            errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "DireccionCorreo"));
                        }
                        if (Constantes.SI.equals(tipoCorreoE.getEsPrincipal())) {
                            esPrincipal = true;
                        }
                    }
                    if (!esPrincipal) {
                        errores.append(Constantes.MSJ_LISTA_EMAIL_SIN_ID_PRINCIPAL.replace(Constantes.PARAMETRO0, "EsPrincipal"));

                    }
                }
            } else {
                errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "InformacionUbicacionResidencia"));
            }
        }
        return errores.toString();
    }

    /**
     * Método encargado convertir a un prospecto a un vinculado en la BD
     * 
     * @param informacionContexto
     *            , datos de contexto del servicio
     * @param informacionVinculado
     *            , inforamación del vinculado
     * @return el estado de la ejecución
     */
    private TipoEstadoEjecucion logicaCrearVinculado(TipoInformacionContexto informacionContexto,
            TipoInformacionGeneralVinculado informacionVinculado) throws LogicalException, DataAccessException {
        TipoEstadoEjecucion response = new TipoEstadoEjecucion();
        DAVinculado daVinculado = new DAVinculado();
        DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);
        int[] resultContacto = null;

        try {
            // se inicializa la transcción
            database.beginTransaction();

            // crear el vinculado
            DataStoredProcedure result = daVinculado.crearVinculado(database, informacionContexto, informacionVinculado);

            // obtener valores de genero
            if (result != null && result.getParametrosSalida() != null && result.getParametrosSalida().get(6) != null
                    && result.getParametrosSalida().get(6).getParameterValue() != null) {
                
                DataTable respuestaGenero = daVinculado.consultarGenero((String) result.getParametrosSalida().get(6).getParameterValue()
                        .toString());

                // datos a enviar a cuenta individual
                InformacionVinculado informacion = homologarDatosPlenitudCrearCuenta(result, informacionVinculado, respuestaGenero);

                // crear contactos
                if (informacionVinculado.getInformacionUbicacionResidencia().getDirecciones() != null
                        && informacionVinculado.getInformacionUbicacionResidencia().getDirecciones().getDireccion() != null
                        && !informacionVinculado.getInformacionUbicacionResidencia().getDirecciones().getDireccion().isEmpty()) {

                    resultContacto = daVinculado.agregarContactoDireccionVinculado(database, informacionVinculado
                            .getInformacionUbicacionResidencia().getDirecciones().getDireccion(), informacion.getIdentificadorVinculado(),
                            informacionContexto);
                }

                if (informacionVinculado.getInformacionUbicacionResidencia().getCorreosElectronicos() != null
                        && informacionVinculado.getInformacionUbicacionResidencia().getCorreosElectronicos().getCorreoElectronico() != null
                        && !informacionVinculado.getInformacionUbicacionResidencia().getCorreosElectronicos().getCorreoElectronico()
                                .isEmpty()) {

                    resultContacto = daVinculado.agregarContactoEmailVinculado(database, informacionVinculado
                            .getInformacionUbicacionResidencia().getCorreosElectronicos().getCorreoElectronico(),
                            informacion.getIdentificadorVinculado(), informacionContexto);
                }

                if (informacionVinculado.getInformacionUbicacionResidencia().getTelefonos() != null
                        && informacionVinculado.getInformacionUbicacionResidencia().getTelefonos().getTelefono() != null
                        && !informacionVinculado.getInformacionUbicacionResidencia().getTelefonos().getTelefono().isEmpty()) {

                    resultContacto = daVinculado.agregarContactoTelefonosVinculado(database, informacionVinculado
                            .getInformacionUbicacionResidencia().getTelefonos().getTelefono(), informacion.getIdentificadorVinculado(),
                            informacionContexto);
                }
                
                boolean seCreoCuenta = false;
                List<DbParameter> rtaPlenitud = null; 
                
                Calendar fechaInicio = Calendar.getInstance();
                HashMap<String, String> metaData = new HashMap<String, String>();
                
                metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, informacionVinculado.getIdentificacion().getTipoDocumento());
                metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, informacionVinculado.getIdentificacion().getNumeroDocumento());
                tracer = new TimeTracer(log, metaData);
                tracer.inicio("Se inicia el llamdo al procedimiento crear cuenta individual");
                
                try {
                    // crear la cuenta individual
                    rtaPlenitud = daVinculado.crearCuentaIndvidual(informacion);
                } catch (DataAccessException e) {
                    log.error(e);
                }
                tracer.fin("fin del procedimiento crearcuenta individual.");
                
                Calendar fechaFin = Calendar.getInstance();
                Float tiempoEjecucion = (fechaFin.getTimeInMillis() - fechaInicio.getTimeInMillis()) / 1000F;

               
                HashMap<String, Object> payLoad = new HashMap<String, Object>();

                // guardar en el log la respuseta de plentitud
                metaData.put(ConstantesLoggerServicios.METADATA_TIEMPO_LLAMADO_PLENITUD, tiempoEjecucion.toString());
                
                if (rtaPlenitud != null && Constantes.COD_INVOCACION_EXITOSA_AS400.equals((String) rtaPlenitud.get(1).getParameterValue())) {
                    seCreoCuenta = true;
                }
                String respuestaPlenitud= rtaPlenitud != null && rtaPlenitud.size() > 2 ? (String) rtaPlenitud.get(1)
                        .getParameterValue(): Constantes.RESPUESTA_FALLIDA_INVOCACION_PLENITUD;
                        
                payLoad.put("Respuesta de plentud: Código =",respuestaPlenitud);
                
                payLoad.put(ConstantesLoggerServicios.METADATA_TIEMPO_LLAMADO_PLENITUD, tiempoEjecucion.toString());
                log.info(payLoad, metaData);

                // actualizar el estado del vinculado, notificacion a bizzagui y fecha
                daVinculado.cambiarEstadoCuentaVinculado(database, informacion.getIdentificadorVinculado(), seCreoCuenta);

                database.commit();
                
                BLHiloInvocacionBUC blHiloInvocacionBUC= new BLHiloInvocacionBUC(result.getParametrosSalida().get(1).getParameterValue().toString()
                		, Constantes.TIPO_NOVEDAD_CREACION_BUC,null,tracer,log);
                blHiloInvocacionBUC.start();
                
				if (seCreoCuenta == true) {// si se creo la cuenta individual se confirma exito
					response = respuestaExitosaServicio();
				} else {
					if (rtaPlenitud == null) {// si no se pudo realizar conexion conplenitud
						response = respuestaErrorInvocacionPlenitud();
					} else {
						response = respuestaCreacionVinculadoSinCuentaIndvidual(respuestaPlenitud);
					}
				}
            } else {
                HashMap<String, String> metaData = new HashMap<String, String>();
                HashMap<String, Object> payLoad = new HashMap<String, Object>();

                metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, informacionVinculado.getIdentificacion().getTipoDocumento());
                metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, informacionVinculado.getIdentificacion().getNumeroDocumento());
                payLoad.put(Constantes.MSJ_ERROR_LOG,
                        "Al crear el vinculado no se retornaron datos - No retorno datos el SP([vinculacion].[pr_vinc_crearVinculado])");
                log.error(payLoad, metaData);
                response = respuestaErrorTecnicoServicio();
            }
        } catch (DataAccessException e) {
            // reversar la transacción
            try {
                database.rollBack();
            } catch (Exception e1) {
                e1.printStackTrace();
            }            
            throw e;
        }
        catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            HashMap<String, Object> payLoad = new HashMap<String, Object>();

            metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, informacionVinculado.getIdentificacion().getTipoDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, informacionVinculado.getIdentificacion().getNumeroDocumento());
            payLoad.put(Constantes.MSJ_ERROR_LOG, e.toString());

            // reversar la transacción
            try {
                payLoad.put(Constantes.MSJ_ERROR_LOG, e.toString());
                database.rollBack();
            } catch (Exception e1) {
                payLoad.put(Constantes.MSJ_ERROR_LOG, e.toString() + e1.toString());
                e1.printStackTrace();
            }

            throw new LogicalException(payLoad, metaData, e);
        } finally {
            try {
                database.closeConnection();
            } catch (SQLException e) {
                HashMap<String, String> metaData = new HashMap<String, String>();
                HashMap<String, Object> payLoad = new HashMap<String, Object>();
                metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, informacionVinculado.getIdentificacion().getTipoDocumento());
                metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, informacionVinculado.getIdentificacion().getNumeroDocumento());
                payLoad.put(Constantes.MSJ_ERROR_LOG, e.toString());
                throw new LogicalException(payLoad, metaData, e);
            }
        }

        return response;

    }
    
  
    
    
    
    
    
    
    
    
}
