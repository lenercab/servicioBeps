package co.gov.colpensiones.beps.vinculacion.businesslogic.actualizar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import co.gov.colpensiones.beps.buc.businesslogic.BLHiloInvocacionBUC;
import co.gov.colpensiones.beps.comunes.enumeraciones.TipoConexionBaseDatosEnum;
import co.gov.colpensiones.beps.comunes.utilidades.Constantes;
import co.gov.colpensiones.beps.comunes.utilidades.ConstantesLoggerServicios;
import co.gov.colpensiones.beps.comunes.utilidades.DatabaseManager;
import co.gov.colpensiones.beps.comunes.utilidades.ResourceUtil;
import co.gov.colpensiones.beps.comunes.utilidades.Util;
import co.gov.colpensiones.beps.comunes.utilidades.Validador;
import co.gov.colpensiones.beps.dal.utilidades.DbParameter;
import co.gov.colpensiones.beps.excepciones.DataAccessException;
import co.gov.colpensiones.beps.log.ConstantesLogger;
import co.gov.colpensiones.beps.log.LoggerBeps;
import co.gov.colpensiones.beps.log.TimeTracer;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstadoEjecucion;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContexto;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionBasicaSolicitante;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionComplementariaPersonaNatural;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionNovedadesBuc;
import co.gov.colpensiones.beps.vinculacion.businesslogic.BLVinculado;
import co.gov.colpensiones.beps.vinculacion.businesslogic.DAVinculado;

/**
 * <b>Descripcion:</b> Clase encargada de la lógica de negocio para la modificación de información de vinculados <br/>
 * <b>Caso de Uso:</b> GOP-VIN-1-FAB-11-ModificarInformacionSensibleVinculado <br/>
 * 
 * @author Yenny Nustez Arevalo <ynustez@heinsohn.com.co>
 */
public class BLActualizarInformacionSensible extends BLVinculado {

    /** Clase de acceso a datos */
    DAVinculado daVinculado = null;

    /** Atributo para tomar trazas de tiempo durante la ejecución del preproceso. */
    private TimeTracer tracer;

    /**
     * Método contructor
     * 
     * @param log
     *            log asociado a la funcionalidad
     */
    public BLActualizarInformacionSensible(LoggerBeps log) {
        super(log);
    }

    /* *************************************************************************************************** */
    /* GOP-VIN-1-FAB-11-ModificarInformacionSensibleVinculado */
    /* **************************************************************************************************** */

    /**
     * Método encargado de realizar la modificación de la información básica sensible del vinculado
     * 
     * @param informacionContexto
     *            datos de contexto
     * @param informacionVinculado
     *            datos basicos del vinculado
     * @return estado de la ejecución
     */
    public TipoEstadoEjecucion actualizarDatosBasicos(TipoInformacionContexto informacionContexto,
            TipoInformacionBasicaSolicitante informacionVinculado) {

        TipoEstadoEjecucion response = new TipoEstadoEjecucion();

        /* Manejo de Log */
        HashMap<String, Object> payLoadTrace = new HashMap<String, Object>();
        HashMap<String, String> metaData = new HashMap<String, String>();
        HashMap<String, Object> payLoad = new HashMap<String, Object>();

        metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, informacionVinculado.getDocumento().getTipoDocumento());
        metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, informacionVinculado.getDocumento().getNumeroDocumento());
        if (informacionContexto != null) {
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_RADICADO, informacionContexto.getIdCorrelacion());
            metaData.put(ConstantesLoggerServicios.USUARIO_SISTEMA_EXTERNO, informacionContexto.getUsuarioSistemaExterno());
        }
        payLoad.putAll(metaData);

        try {
            /* registro en el log */
            payLoadTrace.put(ConstantesLogger.OBJETO_CONTEXTO_ENTRADA, informacionContexto);
            payLoadTrace.put(ConstantesLogger.OBJETO_NEGOCIO_ENTRADA, informacionVinculado);
            log.trace(payLoadTrace, metaData);

            /* validar datos de entrada */
            StringBuffer lstErrores = new StringBuffer();
            lstErrores.append(new Validador<TipoInformacionContexto>().ValidarDataContract(informacionContexto));
            lstErrores.append(new Validador<TipoInformacionBasicaSolicitante>().ValidarDataContract(informacionVinculado));
            lstErrores.append(this.validarDatosEntrada(informacionContexto, informacionVinculado));

            /* validar existencia errores */
            if (lstErrores.toString().length() > 0) {
                payLoad.put(Constantes.MSJ_ERROR_LOG, lstErrores.toString());
                log.info(payLoad, metaData);
                response = respuestaNegocioServicio(Constantes.COD_FORMATO_INVALIDO_OBLIGATORIO_NO_RECIBIDO, lstErrores.toString());
            } else {
                /* Se ejecuta la lógica de negocio */
                response = actualizarDatosVinculado(informacionContexto, informacionVinculado);
            }
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
     * Método que realiza las validaciones de negocio definidas para los parámetros de entrada
     * 
     * @param informacionContexto
     *            datos encabezado
     * @param informacionVinculado
     *            datos basicos del vinculado
     * @return cadena con los errores generados luego de las validaciones
     */
    private String validarDatosEntrada(TipoInformacionContexto informacionContexto, TipoInformacionBasicaSolicitante informacionVinculado)
            throws Exception {
        StringBuffer errores = new StringBuffer();

        /* Validar datos encabezado */
        if (informacionContexto.getIdCorrelacion() == null) {
            errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replaceAll(Constantes.PARAMETRO0, "idCorrelacion"));
        } else {
            if (!Pattern.compile(Constantes.ER_VALIDAR_ID_CORRELACION).matcher(informacionContexto.getIdCorrelacion()).matches()) {
                errores.append(Constantes.MSJ_ESTRUCTURA_IDCORRELACION_INVALIDA.replaceAll(Constantes.PARAMETRO0, "IdCorrelacion"));
            }
        }

        if (informacionContexto.getUsuarioSistemaExterno() == null) {
            errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replaceAll(Constantes.PARAMETRO0, "usuarioSistemaExterno"));
        } else {
            if (!Pattern.compile(Constantes.ER_VALIDAR_USUARIO_SISTEMA).matcher(informacionContexto.getUsuarioSistemaExterno()).matches()) {
                errores.append(Constantes.MSJ_ESTRUCTURA_USUARIO_INVALIDA.replaceAll(Constantes.PARAMETRO0, "UsuarioSistemaExterno"));
            }
        }

        /* Validacion tipo de documento */
        ArrayList<String> tiposDocumento = Util.getResourcePropertyArray(Constantes._HOMOLOGACION_NAME,
                Constantes.PREFIJO_LLAVES_TIPO_DOCUMENTO);
        if (!tiposDocumento.contains(informacionVinculado.getDocumento().getTipoDocumento())) {
            errores.append(Constantes.MSJ_ERROR_DATO_NO_VALIDO.replaceAll(Constantes.PARAMETRO0, "TipoDocumento")
                    + tiposDocumento.toString() + "\n");
        }

        /* Validación de datos adicionales */
        if (informacionVinculado != null && informacionVinculado.getInformacionAdicional() != null) {

            TipoInformacionComplementariaPersonaNatural infoComplementaria = informacionVinculado.getInformacionAdicional();

            /*
             * Validacion género - Este dato es opcional, pero en caso que que venga como dato de entrada, se valida que el valor
             * corresponda a los parámetros definidos
             */
            String genero = infoComplementaria.getGenero();
            if (genero != null && !genero.trim().isEmpty()) {
                ArrayList<String> tiposGenero = Util.getResourcePropertyArray(Constantes._HOMOLOGACION_NAME,
                        Constantes.PREFIJO_LLAVES_GENERO);
                if (!tiposGenero.contains(genero)) {
                    errores.append(Constantes.MSJ_ERROR_DATO_NO_VALIDO.replaceAll(Constantes.PARAMETRO0, "Genero") + tiposGenero.toString()
                            + "\n");
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat(Constantes.FORMATO_FECHA_AAAAMMDD);

            /* Fecha Expedición */

            String fExpedicion = infoComplementaria.getFechaExpedicionDocumentoIdenticacion();
            Date fechaExpedicion = null;

            if (fExpedicion != null && !fExpedicion.trim().isEmpty()) {
                fechaExpedicion = sdf.parse(fExpedicion);

                /* La fecha de expedición no puede ser mayor a la fecha de modificación */
                if (fechaExpedicion.compareTo(new Date()) > 0) {
                    errores.append(Constantes.MSJ_FECHA_INVALIDA_MODIFICACION.replaceAll(Constantes.PARAMETRO0,
                            "fechaExpedicionDocumentoIdenticacion"));
                }
            }

            /* Fecha Nacimiento */

            if (infoComplementaria.getInformacionLugarNacimiento() != null) {

                String fNacimiento = infoComplementaria.getInformacionLugarNacimiento().getFechaNacimiento();

                if (fNacimiento != null && !fNacimiento.trim().isEmpty()) {
                    Date fechaNacimiento = sdf.parse(fNacimiento);
                    String fechaMinima = ResourceUtil.getResourceValue(Constantes._RESOURCE_NAME, Constantes.FECHA_MINIMA_NACIMIENTO);
                    Date fechaNacimientoMinima = sdf.parse(fechaMinima);

                     /* 
                     * La fecha de nacimiento no puede ser mayor a la fecha de expedición
                     */ 
                      if (fechaExpedicion != null && fechaNacimiento.compareTo(fechaExpedicion) > 0) {
                      errores.append(Constantes.MSJ_FECHA_INVALIDA_EXPEDICION.replaceAll(Constantes.PARAMETRO0, "FechaNacimiento")); }
                     

                    /*
                     * La fecha de nacimiento no puede ser mayor a la fecha modificación
                     */
                    if (fechaNacimiento.compareTo(new Date()) > 0) {
                        errores.append(Constantes.MSJ_FECHA_INVALIDA_MODIFICACION.replaceAll(Constantes.PARAMETRO0, "FechaNacimiento"));
                    }

                    /*
                     * La fecha de nacimiento no puede ser menor al parámetro mínimo establecido
                     */
                    if (fechaNacimiento.compareTo(fechaNacimientoMinima) < 0) {
                        errores.append(Constantes.MSJ_FECHA_INVALIDA_MINIMA.replaceAll(Constantes.PARAMETRO0, "FechaNacimiento"));
                    }
                }
            }
        }
        return errores.toString();
    }

    /**
     * Método en el cual se ejecuta la lógica de negocio para la actualización de información del vinculado sobre los sistemas BEPS y
     * Cuentas Individuales
     * 
     * @param informacionContexto
     *            datos encabezado
     * @param informacionVinculado
     *            datos basicos del vinculado
     * @return estado de la ejecución
     * 
     * @throws Exception
     */
    private TipoEstadoEjecucion actualizarDatosVinculado(TipoInformacionContexto informacionContexto,
            TipoInformacionBasicaSolicitante informacionVinculado) throws Exception {

        TipoEstadoEjecucion response = new TipoEstadoEjecucion();
        List<DbParameter> parametrosSalida = new ArrayList<DbParameter>();
        String codigoEjecucionPlenitud = "";
        String mensajeErrorPlenitud = "";
        DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);

        try {
            daVinculado = new DAVinculado();

            /* Actualizar informacion vinculado sobre sistema BEPS */
            database.beginTransaction();

            /* Validar existencia del vinculado */
            // String pkVinculado = daVinculado.consultarExistenciaVinculado(database, informacionVinculado.getDocumento());
            String[] infoVinculado = daVinculado
                    .consultarExistenciaVinculadoDatosModificados(database, informacionVinculado.getDocumento());

            if (infoVinculado != null) {

                /* Homologacion de informacion */
                String generoHomologado = Util.getResourceProperty(Constantes._HOMOLOGACION_NAME, Constantes.PREFIJO_LLAVES_GENERO
                        + informacionVinculado.getInformacionAdicional().getGenero());

                if (daVinculado.actualizarInformacionVinculadoBeps(database, informacionContexto, informacionVinculado, infoVinculado[0],
                        generoHomologado)) {

                    /* Cambio mantis mejor 221008 no se registra el depto ni municipio de nacimiento*/
                    if( informacionVinculado.getInformacionAdicional()!=null && informacionVinculado.getInformacionAdicional().getInformacionLugarNacimiento()!=null){
                        informacionVinculado.getInformacionAdicional().getInformacionLugarNacimiento().setDeptoNacimiento(null);
                        informacionVinculado.getInformacionAdicional().getInformacionLugarNacimiento().setMunicipioNacimiento(null);
                    }
                   
                    // Se construye la información sensible que se debe almacenar en la bitácora de novedades en la columna [bnb_datos_novedad]
                    TipoInformacionNovedadesBuc novedadBUC = new TipoInformacionNovedadesBuc();
                    novedadBUC.setInformacionSensible(informacionVinculado);

                    Boolean idBuc = new Boolean((infoVinculado[1] != null || infoVinculado[1].trim() != "") ? Boolean.TRUE : Boolean.FALSE);
                    
                    // creamos el registro de la bitácora de novedades
                    Long idNovedad = daVinculado.insertarBitacoraNovedadesBUC(database, informacionContexto.getUsuarioSistemaExterno(),
                            novedadBUC, new Long(infoVinculado[0]), idBuc);

                    /*
                     * Homologacion de informacion Sistema de Cuentas Individuales
                     */
                    informacionVinculado = homologarDatosPlenitud(informacionVinculado);

                    try {
                        HashMap<String, String> metaData = new HashMap<String, String>();
                        metaData.put(ConstantesLoggerServicios.METADATA_ZIDENTIFICACION, informacionVinculado.getDocumento()
                                .getNumeroDocumento());
                        metaData.put(ConstantesLoggerServicios.METADATA_ZTIPOIDENTIFICA, informacionVinculado.getDocumento()
                                .getTipoDocumento());
                        metaData.put(ConstantesLoggerServicios.METADATA_ZPRIMERNOMBRE, informacionVinculado.getNombresApellidos()
                                .getValue().getPrimerNombre());
                        metaData.put(ConstantesLoggerServicios.METADATA_ZPRIMERAPELLIDO, informacionVinculado.getNombresApellidos()
                                .getValue().getPrimerApellido());
                        metaData.put(ConstantesLoggerServicios.METADATA_ZSEGUNDONOMBRE, informacionVinculado.getNombresApellidos()
                                .getValue().getSegundoNombre());
                        metaData.put(ConstantesLoggerServicios.METADATA_ZSEGUNDOAPELLIDO, informacionVinculado.getNombresApellidos()
                                .getValue().getSegundoApellido());
                        String fechaNacimiento = "";
                        if (informacionVinculado.getInformacionAdicional() != null
                                && informacionVinculado.getInformacionAdicional().getInformacionLugarNacimiento() != null
                                && informacionVinculado.getInformacionAdicional().getInformacionLugarNacimiento().getFechaNacimiento() != null) {
                            fechaNacimiento = informacionVinculado.getInformacionAdicional().getInformacionLugarNacimiento()
                                    .getFechaNacimiento();
                        }
                        metaData.put(ConstantesLoggerServicios.METADATA_ZSEXO, informacionVinculado.getInformacionAdicional().getGenero());

                        HashMap<String, Object> payLoad = new HashMap<String, Object>();
                        payLoad.put("Mensaje",
                                "Inicio ejecucion StoredProcedure Plenitud : SP_Actualiza_Vinculado (? , ? , ?,  ? , ? , ? , ? , ? , ? , ?)");
                        log.info(payLoad, metaData);

                        /* Actualizar informacion vinculado sobre Sistema de Cuentas Individuales */
                        parametrosSalida = daVinculado.actualizarInformacionVinculadoPlenitud(informacionVinculado, fechaNacimiento);

                    } catch (DataAccessException e) {
                        generarLogError(e.getMetaData(), true, e);
                        parametrosSalida = new ArrayList<DbParameter>();
                    }
                    log.info("Fin ejecucion StoredProcedure Plenitud SP_Actualiza_Vinculado ");

                    if (parametrosSalida.size() > 0) {

                        if (parametrosSalida.get(0) != null && parametrosSalida.get(0).getParameterValue() != null) {
                            codigoEjecucionPlenitud = parametrosSalida.get(0).getParameterValue().toString();
                        }

                        if (parametrosSalida.size() > 1 && parametrosSalida.get(1) != null
                                && parametrosSalida.get(1).getParameterValue() != null) {
                            mensajeErrorPlenitud = parametrosSalida.get(1).getParameterValue().toString();
                        }
                    }

                    log.info("Parametros de salida Plenitud : " + codigoEjecucionPlenitud + " - " + mensajeErrorPlenitud);

                    if (codigoEjecucionPlenitud.equals(Constantes.COD_INVOCACION_EXITOSA_AS400)) {

                        /* se hace commit de la transaccion */
                        database.commit();

                        BLHiloInvocacionBUC blHiloInvocacionBUC = new BLHiloInvocacionBUC(idNovedad.toString(),
                                Constantes.TIPO_NOVEDAD_ACTUALIZACION_BUC, new Long(infoVinculado[0]), tracer,log);
                        blHiloInvocacionBUC.start();

                    } else {
                        parametrosSalida = new ArrayList<DbParameter>();
                        DbParameter dbParameter = new DbParameter();
                        dbParameter.setParameterValue(Constantes.COD_ERROR_ACTUALIZACION_DATOS_PLENITUD);
                        parametrosSalida.add(dbParameter);
                        dbParameter = new DbParameter();
                        dbParameter.setParameterValue(Constantes.MSJ_ERROR_ACTUALIZACION_SCI);
                        parametrosSalida.add(dbParameter);

                        /*
                         * Se realiza rollback de los datos actualizados en Beps ejecutarRollbackTransaccion(database)
                         */

                        database.rollBack();
                    }
                }

            } else {
                /*
                 * Si el vinculado no existe se retorna error de logica de negocio
                 */
                DbParameter dbParameter = new DbParameter();
                dbParameter.setParameterValue(Constantes.COD_ERROR_LOGICA_NEGOCIO);
                parametrosSalida.add(dbParameter);
                dbParameter = new DbParameter();
                dbParameter.setParameterValue(Constantes.MSJ_VINCULADO_INEXISTENTE);
                parametrosSalida.add(dbParameter);

            }

            /* Se genera la respuesta del servicio */

            if (codigoEjecucionPlenitud.equals(Constantes.COD_INVOCACION_EXITOSA_AS400)) {
                response = respuestaExitosaServicio();
            } else {
                response = respuestaNegocioServicio(parametrosSalida.get(0).getParameterValue().toString(), parametrosSalida.get(1)
                        .getParameterValue().toString());
            }

        } catch (DataAccessException e) {
            generarLogError(e.getMetaData(), true, e);
            response = respuestaErrorTecnicoServicio();
            ejecutarRollbackTransaccion(database);
        } catch (Exception e1) {
            String mensajeError = e1.getMessage();
            /* Se realiza Rollback */
            mensajeError += ejecutarRollbackTransaccion(database);
            throw new Exception(mensajeError, e1);
        } finally {
            database.closeConnection();
        }
        return response;
    }

}
