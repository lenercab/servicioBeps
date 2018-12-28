package co.gov.colpensiones.beps.vinculacion.businesslogic.actualizar;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import co.gov.colpensiones.beps.buc.businesslogic.BLHiloInvocacionBUC;
import co.gov.colpensiones.beps.comunes.enumeraciones.TipoConexionBaseDatosEnum;
import co.gov.colpensiones.beps.comunes.utilidades.Constantes;
import co.gov.colpensiones.beps.comunes.utilidades.ConstantesLoggerServicios;
import co.gov.colpensiones.beps.comunes.utilidades.DatabaseManager;
import co.gov.colpensiones.beps.comunes.utilidades.Util;
import co.gov.colpensiones.beps.comunes.utilidades.Validador;
import co.gov.colpensiones.beps.excepciones.DataAccessException;
import co.gov.colpensiones.beps.log.ConstantesLogger;
import co.gov.colpensiones.beps.log.LoggerBeps;
import co.gov.colpensiones.beps.log.TimeTracer;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstadoEjecucion;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContexto;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoCorreoElectronico;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoDatoTelefono;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionDatosGeneralesVinculado;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionDireccionPersonaNatural;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionNovedadesBuc;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionUbicacionPersona;
import co.gov.colpensiones.beps.vinculacion.businesslogic.BLVinculado;
import co.gov.colpensiones.beps.vinculacion.businesslogic.DAVinculado;

/**
 * <b>Descripcion:</b> Clase encargada de la logica de negocio para la modificacion de informacion general del vinculados <br/>
 * <b>Caso de Uso:</b> GVI-VIN-1-FAB-13-ModificarInformacionNoSensibleVinculado <br/>
 * 
 * @author Yenny Nustez Arevalo <ynustez@heinsohn.com.co>
 */
public class BLActualizarInformacionNoSensible extends BLVinculado {

    /** Clase de accedo a datos */
    DAVinculado daVinculado = null;

    /** Objeto de conexion a la base de datos */
    Connection connection = null;

    /** Atributo para tomar trazas de tiempo durante la ejecución del preproceso. */
    private TimeTracer tracer;

    /**
     * Método contructor
     * 
     * @param log
     *            log con el que se va a escribir de la BD
     * @param connection
     *            Objeto de conexion a la base de datos
     */
    public BLActualizarInformacionNoSensible(LoggerBeps log, Connection connection) {
        super(log);
        this.connection = connection;
        this.daVinculado = new DAVinculado();
    }

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
            TipoInformacionDatosGeneralesVinculado informacionVinculado) {

        TipoEstadoEjecucion response = new TipoEstadoEjecucion();

        // Manejo de Log
        HashMap<String, Object> payLoadTrace = new HashMap<String, Object>();
        HashMap<String, String> metaData = new HashMap<String, String>();
        HashMap<String, Object> payLoad = new HashMap<String, Object>();

        metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, informacionVinculado.getIdentificacion().getTipoDocumento());
        metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, informacionVinculado.getIdentificacion().getNumeroDocumento());
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

            // validar datos de entrada
            StringBuffer lstErrores = new StringBuffer();
            lstErrores.append(new Validador<TipoInformacionContexto>().ValidarDataContract(informacionContexto));
            lstErrores.append(new Validador<TipoInformacionDatosGeneralesVinculado>().ValidarDataContract(informacionVinculado));
            lstErrores.append(this.validarDatosEntrada(informacionContexto, informacionVinculado));

            // validar existencia errores
            if (lstErrores.toString().length() > 0) {
                payLoad.put(Constantes.MSJ_ERROR_LOG, lstErrores.toString());
                log.info(payLoad, metaData);
                response = respuestaNegocioServicio(Constantes.COD_FORMATO_INVALIDO_OBLIGATORIO_NO_RECIBIDO, lstErrores.toString());
            } else {
                // Se ejecuta la lógica de negocio
                response = actualizarDatosGeneralesVinculado(informacionContexto, informacionVinculado, Boolean.TRUE);
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
    private String validarDatosEntrada(TipoInformacionContexto informacionContexto,
            TipoInformacionDatosGeneralesVinculado informacionVinculado) throws Exception {
        StringBuffer errores = new StringBuffer();

        // Validar datos encabezado
        if (informacionContexto.getIdCorrelacion() == null) {
            errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replaceAll(Constantes.PARAMETRO0, "IdCorrelacion"));
        } else {
            if (!Pattern.compile(Constantes.ER_VALIDAR_ID_CORRELACION).matcher(informacionContexto.getIdCorrelacion()).matches()) {
                errores.append(Constantes.MSJ_ESTRUCTURA_IDCORRELACION_INVALIDA.replaceAll(Constantes.PARAMETRO0, "IdCorrelacion"));
            }
        }

        if (informacionContexto.getUsuarioSistemaExterno() == null) {
            errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replaceAll(Constantes.PARAMETRO0, "UsuarioSistemaExterno"));
        } else {
            if (!Pattern.compile(Constantes.ER_VALIDAR_USUARIO_SISTEMA).matcher(informacionContexto.getUsuarioSistemaExterno()).matches()) {
                errores.append(Constantes.MSJ_ESTRUCTURA_USUARIO_INVALIDA.replaceAll(Constantes.PARAMETRO0, "UsuarioSistemaExterno"));
            }
        }

        // Validacion tipo de documento
        ArrayList<String> tiposDocumento = Util.getResourcePropertyArray(Constantes._HOMOLOGACION_COMUN_NAME,
                Constantes.PREFIJO_LLAVES_TIPO_DOCUMENTO);
        if (!tiposDocumento.contains(informacionVinculado.getIdentificacion().getTipoDocumento())) {
            errores.append(Constantes.MSJ_ERROR_DATO_NO_VALIDO.replaceAll(Constantes.PARAMETRO0, "TipoDocumento")
                    + tiposDocumento.toString() + "\n");
        }

        /* validaciones para la ubicación */
        if (informacionVinculado != null && informacionVinculado.getInformacionUbicacionResidencia() != null) {
            /* valida obligatoriedad de la lista de direcciones de residencia */
            if (informacionVinculado.getInformacionUbicacionResidencia() != null
                    || informacionVinculado.getInformacionUbicacionResidencia().getDirecciones().getDireccion() != null
                    || !informacionVinculado.getInformacionUbicacionResidencia().getDirecciones().getDireccion().isEmpty()) {

                boolean indicadorEsPrincipal = false;

                for (TipoInformacionDireccionPersonaNatural infoDir : informacionVinculado.getInformacionUbicacionResidencia()
                        .getDirecciones().getDireccion()) {

                    /* validar obligatoriedad de identificador */
                    if (infoDir.getIdentificador() == null) {
                        errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "Identificador"));
                    }

                    /* validar obligatoriedad de dirección */
                    if (infoDir.getDireccion() == null) {
                        errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "Direccion"));
                    }

                    /* validar obligatoriedad de departamento */
                    if (infoDir.getDepartamento() == null || infoDir.getDepartamento().getCodigo() == null) {
                        errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "CodigoDepartamento"));
                    }

                    /* validar obligatoriedad de ciudad */
                    if (infoDir.getMunicipio() == null || infoDir.getMunicipio().getCodigo() == null) {
                        errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "CodigoMunicipio"));
                    }

                    /* validar obligatoriedad de indicador */
                    if (infoDir.getEsPrincipal() == null) {
                        errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "IndicadorDireccionPrincipal"));
                    } else if (infoDir.getEsPrincipal().equals(Constantes.SI)) {
                        indicadorEsPrincipal = true;
                    }
                }

                if (!indicadorEsPrincipal)
                    errores.append(Constantes.MSJ_LISTA_DIRECCIONES_SIN_ID_PRINCIPAL.replaceAll(Constantes.PARAMETRO0,
                            "IndicadorDireccionPrincipal"));
            }

            /* valida obligatoriedad de la lista de teléfonos */
            if (informacionVinculado.getInformacionUbicacionResidencia() != null
                    || informacionVinculado.getInformacionUbicacionResidencia().getTelefonos().getTelefono() != null
                    || !informacionVinculado.getInformacionUbicacionResidencia().getTelefonos().getTelefono().isEmpty()) {
                boolean indicadorEsPrincipal = false;
                for (TipoDatoTelefono tipoDatoTel : informacionVinculado.getInformacionUbicacionResidencia().getTelefonos().getTelefono()) {

                    /* validar estructura interna del teléfono */
                    if (tipoDatoTel.getTelefono() == null) {
                        errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "Telefono"));
                    }

                    /* validar obligatoriedad de indicador */
                    if (tipoDatoTel.getEsPrincipal() == null) {
                        errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "IndicadorTelefonoPrincipal"));
                    } else if (tipoDatoTel.getEsPrincipal().equals(Constantes.SI)) {
                        indicadorEsPrincipal = true;
                    }
                }

                if (!indicadorEsPrincipal)
                    errores.append(Constantes.MSJ_LISTA_TELEFONOS_SIN_ID_PRINCIPAL.replaceAll(Constantes.PARAMETRO0,
                            "IndicadorTelefonoPrincipal"));
            }
        } else {
            errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "InformacionUbicacion"));
        }

        /* Validacion Canal */
        ArrayList<String> canales = Util.getResourcePropertyArray(Constantes._HOMOLOGACION_COMUN_NAME, Constantes.PREFIJO_LLAVES_CANALES);
        if (!canales.contains(informacionVinculado.getCanalModificacion())) {
            errores.append(Constantes.MSJ_ERROR_DATO_NO_VALIDO.replaceAll(Constantes.PARAMETRO0, "CanalModificacion") + canales.toString()
                    + "\n");
        }

        return errores.toString();

    }

    /**
     * Método en el cual se ejecuta la lógica de negocio para la actualización de información general del vinculado sobre el sistema BEPS
     * 
     * @param informacionContexto
     *            datos encabezado
     * @param informacionVinculado
     *            datos generales del vinculado
     * @param actualizarInfoGeneral
     *            bandera que indica si se debe actualizar la información general del vinculado
     * @return estado de la ejecución
     */
    private TipoEstadoEjecucion actualizarDatosGeneralesVinculado(TipoInformacionContexto informacionContexto,
            TipoInformacionDatosGeneralesVinculado informacionVinculado, Boolean actualizarInfoGeneral) throws Exception {

        TipoEstadoEjecucion response = new TipoEstadoEjecucion();
        DatabaseManager database = null;

        try {
            /* Se valida la conexion a bd */
            if (connection != null) {
                database = new DatabaseManager(connection);
            } else {
                database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);
            }

            /* Se valida si el vinculado existe */
            // String idVinculado = daVinculado.consultarExistenciaVinculado(database, informacionVinculado.getIdentificacion());

            String[] infoVinculado = daVinculado.consultarExistenciaVinculadoDatosModificados(database,
                    informacionVinculado.getIdentificacion());
            if (infoVinculado != null) {

                database.beginTransaction();
                /* consultar estado banderas* */

                TipoInformacionNovedadesBuc novedadBUC = new TipoInformacionNovedadesBuc();

                // se consulta para el vinculado como estaban las listas de antes de direcciones, telefonos o correos de acuerdo a lo
                // solicitado por BUC
                TipoInformacionUbicacionPersona infoUbicacionAntes = daVinculado.consultarInformacionDatosNoSensibles(database,
                        informacionVinculado, new Long(infoVinculado[0]));
                TipoInformacionDatosGeneralesVinculado infoDatosGenerales = new TipoInformacionDatosGeneralesVinculado();
                infoDatosGenerales.setInformacionUbicacionResidencia(infoUbicacionAntes);

                novedadBUC.setInformacionNoSensibleAntes(infoUbicacionAntes);
                if (actualizarInfoGeneral) {
                    /* Se actualiza la informacion general del vinculado */

                    daVinculado.actualizarInformacionNoSensibleVinculado(database, informacionContexto, informacionVinculado,
                            infoVinculado[0]);
                }

                /* Se actualiza la informacion de ubicacion */
                actualizarInformacionDireccion(database, informacionContexto, informacionVinculado, infoVinculado[0]);

                /* Se actualiza la informacion de telefonos */
                actualizarInformacionTelefono(database, informacionContexto, informacionVinculado, infoVinculado[0]);

                /* Se actualiza la informacion de correo electronico */
                actualizarInformacionEmail(database, informacionContexto, informacionVinculado, infoVinculado[0]);

                Boolean idBuc = new Boolean((infoVinculado[1] != null || infoVinculado[1].trim() != "") ? Boolean.TRUE : Boolean.FALSE);

                // se consulta para el vinculado como quedaron (despues) las listas de direcciones, telefonos o correos de acuerdo a lo
                // solicitado por BUC
                TipoInformacionUbicacionPersona infoUbicacionDespues = daVinculado.consultarInformacionDatosNoSensibles(database,
                        informacionVinculado, new Long(infoVinculado[0]));

                novedadBUC.setInformacionNoSensibleDespues(infoUbicacionDespues);

                //Se crea la bitácora de novedades para BUC con la informacion en la columna [bnb_datos_novedad]
                Long idNovedad = daVinculado.insertarBitacoraNovedadesBUC(database, informacionContexto.getUsuarioSistemaExterno(),
                        novedadBUC, new Long(infoVinculado[0]), idBuc);

                database.commit();

                BLHiloInvocacionBUC blHiloInvocacionBUC = new BLHiloInvocacionBUC(idNovedad.toString(),
                        Constantes.TIPO_NOVEDAD_ACTUALIZACION_BUC, new Long(infoVinculado[0]), tracer, log);
                blHiloInvocacionBUC.start();

                /* Respuesta exitosa del servicio */
                response = respuestaExitosaServicio();

            } else {

                /* Si el vinculado no existe */
                response = respuestaNegocioServicio(Constantes.COD_ERROR_LOGICA_NEGOCIO, Constantes.MSJ_VINCULADO_INEXISTENTE);
            }

        } catch (DataAccessException e) {
            generarLogError(e.getMetaData(), true, e);
            response = respuestaErrorTecnicoServicio();
            ejecutarRollbackTransaccion(database);
        } catch (Exception e1) {
            String mensajeError = e1.getMessage();
            // Se realiza Rollback
            mensajeError += ejecutarRollbackTransaccion(database);
            throw new Exception(mensajeError, e1);
        }

        return response;
    }

    /**
     * Método para la actualización de direcciones de un vinculado
     * 
     * @param database
     *            Conexion a la bd
     * @param informacionContexto
     *            datos del contexto
     * @param informacionVinculado
     *            información del vinculado
     * @param idVinculado
     *            Cadena con el id del vinculado
     * @throws DataAccessException
     *             Excepción por error de datos
     */
    private void actualizarInformacionDireccion(DatabaseManager database, TipoInformacionContexto informacionContexto,
            TipoInformacionDatosGeneralesVinculado informacionVinculado, String idVinculado) throws DataAccessException {

        if (informacionVinculado != null && informacionVinculado.getInformacionUbicacionResidencia() != null) {
            if (informacionVinculado.getInformacionUbicacionResidencia().getDirecciones().getDireccion() != null
                    || !informacionVinculado.getInformacionUbicacionResidencia().getDirecciones().getDireccion().isEmpty()) {

                List<TipoInformacionDireccionPersonaNatural> direccionesNuevas = new ArrayList<TipoInformacionDireccionPersonaNatural>();
                List<TipoInformacionDireccionPersonaNatural> direccionesActualizar = new ArrayList<TipoInformacionDireccionPersonaNatural>();
                String idDirecciones = "";

                for (TipoInformacionDireccionPersonaNatural infoDir : informacionVinculado.getInformacionUbicacionResidencia()
                        .getDirecciones().getDireccion()) {

                    /* Si el identificador es 0 se crea un nuevo registro */
                    if (BigDecimal.ZERO.compareTo(infoDir.getIdentificador()) == 0) {
                        direccionesNuevas.add(infoDir);
                    } else if (BigDecimal.ZERO.compareTo(infoDir.getIdentificador()) == -1) {
                        idDirecciones += infoDir.getIdentificador().toString() + ",";
                        direccionesActualizar.add(infoDir);
                    }
                }

                if (idDirecciones.isEmpty()) {
                    /* Se eliminan todas las direcciones asociadas al vinculado */
                    daVinculado.eliminarDireccionesNoAsociadas(database, idVinculado, idDirecciones);
                } else {
                    /* Se eliminan las direcciones no incluidas en el request */
                    daVinculado.eliminarDireccionesNoAsociadas(database, idVinculado,
                            idDirecciones.substring(0, idDirecciones.length() - 1));

                    /* Se actualizan las direcciones incluidas en el request */
                    daVinculado.actualizarContactoDireccion(database, direccionesActualizar, informacionContexto);
                }

                /* Se insertan los registros nuevos */
                if (direccionesNuevas.size() > 0) {
                    daVinculado.agregarContactoDireccionVinculado(database, direccionesNuevas, Long.valueOf(idVinculado),
                            informacionContexto);
                }
            }
        }
    }

    /**
     * Método para la actualización de teléfono de un vinculado
     * 
     * @param database
     *            Conexion a la bd
     * @param informacionContexto
     *            datos del contexto
     * @param informacionVinculado
     *            información del vinculado
     * @param idVinculado
     *            Cadena con el id del vinculado
     * @throws DataAccessException
     *             Excepción por error de datos
     */
    private void actualizarInformacionTelefono(DatabaseManager database, TipoInformacionContexto informacionContexto,
            TipoInformacionDatosGeneralesVinculado informacionVinculado, String idVinculado) throws DataAccessException {

        if (informacionVinculado.getInformacionUbicacionResidencia() != null
                || informacionVinculado.getInformacionUbicacionResidencia().getTelefonos().getTelefono() != null
                || !informacionVinculado.getInformacionUbicacionResidencia().getTelefonos().getTelefono().isEmpty()) {

            List<TipoDatoTelefono> telefonosNuevos = new ArrayList<TipoDatoTelefono>();
            List<TipoDatoTelefono> telefonosActualizar = new ArrayList<TipoDatoTelefono>();
            String idTelefonos = "";

            for (TipoDatoTelefono infoTelefono : informacionVinculado.getInformacionUbicacionResidencia().getTelefonos().getTelefono()) {

                /* Si el identificador es 0 se crea un nuevo registro */
                if (BigDecimal.ZERO.compareTo(infoTelefono.getIdentificador()) == 0) {
                    telefonosNuevos.add(infoTelefono);
                } else if (BigDecimal.ZERO.compareTo(infoTelefono.getIdentificador()) == -1) {
                    idTelefonos += infoTelefono.getIdentificador().toString() + ",";
                    telefonosActualizar.add(infoTelefono);
                }
            }

            if (idTelefonos.isEmpty()) {
                /* Se eliminan todos los telefonos asociados al vinculado */
                daVinculado.eliminarTelefonosNoAsociados(database, idVinculado, idTelefonos);
            } else {
                /* Se eliminan los teléfonos no incluidos en el request */
                daVinculado.eliminarTelefonosNoAsociados(database, idVinculado, idTelefonos.substring(0, idTelefonos.length() - 1));

                /* Se actualizan los teléfonos incluidos en el request */
                daVinculado.actualizarContactoTelefono(database, telefonosActualizar, informacionContexto);
            }

            /* Se insertan los registros nuevos */
            if (telefonosNuevos.size() > 0) {
                daVinculado.agregarContactoTelefonosVinculado(database, telefonosNuevos, Long.valueOf(idVinculado), informacionContexto);
            }
        }
    }

    /**
     * Método para la actualización de correo electrónico de un vinculado
     * 
     * @param database
     *            Conexion a la bd
     * @param informacionContexto
     *            datos del contexto
     * @param informacionVinculado
     *            información del vinculado
     * @param idVinculado
     *            Cadena con el id del vinculado
     * @throws DataAccessException
     *             Excepción por error de datos
     */
    private void actualizarInformacionEmail(DatabaseManager database, TipoInformacionContexto informacionContexto,
            TipoInformacionDatosGeneralesVinculado informacionVinculado, String idVinculado) throws DataAccessException {

        if (informacionVinculado.getInformacionUbicacionResidencia() != null
                && informacionVinculado.getInformacionUbicacionResidencia().getCorreosElectronicos() != null
                && informacionVinculado.getInformacionUbicacionResidencia().getCorreosElectronicos().getCorreoElectronico() != null
                && !informacionVinculado.getInformacionUbicacionResidencia().getCorreosElectronicos().getCorreoElectronico().isEmpty()) {

            List<TipoCorreoElectronico> correosNuevos = new ArrayList<TipoCorreoElectronico>();
            List<TipoCorreoElectronico> correosActualizar = new ArrayList<TipoCorreoElectronico>();
            String idCorreos = "";

            for (TipoCorreoElectronico infoCorreo : informacionVinculado.getInformacionUbicacionResidencia().getCorreosElectronicos()
                    .getCorreoElectronico()) {

                /* Si el identificador es 0 se crea un nuevo registro */
                if (BigDecimal.ZERO.compareTo(infoCorreo.getIdentificador()) == 0) {
                    correosNuevos.add(infoCorreo);
                } else if (BigDecimal.ZERO.compareTo(infoCorreo.getIdentificador()) == -1) {
                    idCorreos += infoCorreo.getIdentificador().toString() + ",";
                    correosActualizar.add(infoCorreo);
                }
            }

            if (idCorreos.isEmpty()) {
                /* Se eliminan todos los correos electronicos asociados al vinculado */
                daVinculado.eliminarEmailsNoAsociados(database, idVinculado, idCorreos);
            } else {
                /* Se eliminan los correos electronicos no incluidos en el request */
                daVinculado.eliminarEmailsNoAsociados(database, idVinculado, idCorreos.substring(0, idCorreos.length() - 1));

                /* Se actualizan los correos electronicos incluidos en el request */
                daVinculado.actualizarContactoEmail(database, correosActualizar, informacionContexto);
            }

            /* Se insertan los registros nuevos */
            if (correosNuevos.size() > 0) {
                daVinculado.agregarContactoEmailVinculado(database, correosNuevos, Long.valueOf(idVinculado), informacionContexto);
            }
        }
    }

    /**
     * Método en el cual se ejecuta la lógica de negocio para la actualización de información general del vinculado sobre el sistema BEPS
     * permitiendo elegir si actualizar la información general.
     * 
     * @param informacionContexto
     *            Información de Contexto para el servicio.
     * @param informacionVinculado
     *            Información del vinculado a actualizar.
     * @param actualizarInfoGeneral
     *            indica si actualiza la información general.
     * @return TipoEstadoEjecución Respuesta del CU.
     * @throws Exception
     *             Excepción generada
     */
    public TipoEstadoEjecucion actualizarDatosGeneralesVinculadoUbicacion(TipoInformacionContexto informacionContexto,
            TipoInformacionDatosGeneralesVinculado informacionVinculado, Boolean actualizarInfoGeneral) throws Exception {

        return actualizarDatosGeneralesVinculado(informacionContexto, informacionVinculado, actualizarInfoGeneral);
    }

}
