package co.gov.colpensiones.beps.vinculacion.businesslogic.reActivar;

import java.math.BigDecimal;
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
import co.gov.colpensiones.beps.dal.utilidades.DataStoredProcedure;
import co.gov.colpensiones.beps.excepciones.DataAccessException;
import co.gov.colpensiones.beps.excepciones.LogicalException;
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
import co.gov.colpensiones.beps.vinculacion.businesslogic.crear.InformacionVinculado;

public class BLReactivar extends BLVinculado{

	DAVinculado daVinculado = new DAVinculado();
	
	/** Atributo para tomar trazas de tiempo durante la ejecución del preproceso.*/
    private TimeTracer tracer;
	
  public BLReactivar(LoggerBeps log) {
		super(log);
		// TODO Auto-generated constructor stub
	}

//////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Método encargado de realizar las validaciones y reactivación de vinculados
     * 
     * @param informacionContexto
     *            , datos de contexto del servicio
     * @param informacionVinculado
     *            , inforamación del vinculado
     * @return el estado de la ejecución
     * @throws Exception 
     */
    public TipoEstadoEjecucion logicaReActivarVinculado(TipoInformacionContexto informacionContexto,
            TipoInformacionGeneralVinculado informacionVinculado) throws Exception {
        TipoEstadoEjecucion response = new TipoEstadoEjecucion();
        DAReactivar daReactivar = new DAReactivar();
        DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);
        String numDocumento=null;
        String numRadicado = null;
        boolean creaBitacora = false;

        try {
        	
        	DataStoredProcedure result = null;
            // se inicializa la transacción
            database.beginTransaction();
                        
            numDocumento = informacionVinculado.getIdentificacion().getNumeroDocumento();
            numRadicado = informacionContexto.getIdCorrelacion();
            int validacion = daReactivar.validarReactivacion(database,numDocumento, numRadicado);
            
            if( validacion == 1){
            	 // Re activar al vinculado sin actualización de fecha de vinculación
            		result = daReactivar.reActivarVinculado(database, informacionContexto, informacionVinculado, 1);
            		//crea registro en la bitacora
                    creaBitacora = daReactivar.insertarBitacoraActCta(database, numDocumento, numRadicado);
            }else if (validacion ==2){
            	 // Re activar al vinculado con actualización de fecha de vinculación
            		result = daReactivar.reActivarVinculado(database, informacionContexto, informacionVinculado, 2);
            		//crea registro en la bitacora
                    creaBitacora = daReactivar.insertarBitacoraActCta(database, numDocumento, numRadicado);
            }else if (validacion == 3){
            	result = null;
            	response = respuestaErrorInvocacionPlenitud();
            }else if (validacion == 4){
            	result = null;
            	response = respuestaExitosaServicio();
            } else {
            	result = null;
            	response = respuestaErrorTecnicoServicio();
            }

            
            // confirma parametros de salida
            if (result != null && result.getParametrosSalida() != null && result.getParametrosSalida().get(6) != null
                    && result.getParametrosSalida().get(6).getParameterValue() != null) {
                
            	try {
            		actualizarInformacionDireccion(database, informacionContexto, informacionVinculado, result.getParametrosSalida().get(0).getParameterValue().toString());
                	actualizarInformacionEmail(database, informacionContexto, informacionVinculado,  result.getParametrosSalida().get(0).getParameterValue().toString());
                	actualizarInformacionTelefono(database, informacionContexto, informacionVinculado,  result.getParametrosSalida().get(0).getParameterValue().toString());
				} catch (Exception e) {
					log.error(e);
				}
                	
        	boolean seActualizoCuenta = false;
        	String rtaPlenitud = null; 
            
            Calendar fechaInicio = Calendar.getInstance();
            HashMap<String, String> metaData = new HashMap<String, String>();
            
            metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, informacionVinculado.getIdentificacion().getTipoDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, informacionVinculado.getIdentificacion().getNumeroDocumento());
            tracer = new TimeTracer(log, metaData);
            
            Calendar fechaFin = Calendar.getInstance();
            Float tiempoEjecucion = (fechaFin.getTimeInMillis() - fechaInicio.getTimeInMillis()) / 1000F;
           
            HashMap<String, Object> payLoad = new HashMap<String, Object>();
            
            tracer.inicio("Se inicia el llamado al procedimiento actualizar cuenta individual");
          
            try {
                // actualiza la cuenta individual
            	rtaPlenitud = daReactivar.cambiarEstadoCuentaIndividual(numDocumento, "A", "");
            	
            } catch (DataAccessException e) {
                log.error(e);
            }
            
            metaData.put(ConstantesLoggerServicios.METADATA_TIEMPO_LLAMADO_PLENITUD, tiempoEjecucion.toString());
            tracer.fin("fin del procedimiento actualizar cuenta individual.");
            
            // valida respuesta de plenitud y actualiza la bitacora
           
            if (rtaPlenitud != null && Constantes.COD_INVOCACION_EXITOSA_AS400.equals((String) rtaPlenitud)) {
                seActualizoCuenta = true;
                System.out.println("Actualiza cuenta individual: " + seActualizoCuenta);
                try {
                	if (creaBitacora == true){
                	daReactivar.actualizarBitacoraEstadoCta(database, "A", "Exitoso", numDocumento, numRadicado);
                	}
				} catch (Exception e) {
					 log.error(e);
				}
            }else{
            	try {
            		if (creaBitacora == true){
            		seActualizoCuenta = false;
                	daReactivar.actualizarBitacoraEstadoCta(database, "C", "Fallido", numDocumento, numRadicado);
            		}
				} catch (Exception e) {
            	 log.error(e);
				}
            }
            
            String respuestaPlenitud = rtaPlenitud != null ? (String) rtaPlenitud: Constantes.RESPUESTA_FALLIDA_INVOCACION_PLENITUD;
            
            payLoad.put("Respuesta de plentud: Código =",respuestaPlenitud);
            System.out.println("Respuesta de plentud: Código =" + respuestaPlenitud);
            
            payLoad.put(ConstantesLoggerServicios.METADATA_TIEMPO_LLAMADO_PLENITUD, tiempoEjecucion.toString());
            log.info(payLoad, metaData);
            
        	BLHiloInvocacionBUC blHiloInvocacionBUC= new BLHiloInvocacionBUC(result.getParametrosSalida().get(1).getParameterValue().toString()
            		, Constantes.TIPO_NOVEDAD_ACTUALIZACION_BUC,new Long (result.getParametrosSalida().get(0).getParameterValue().toString()),tracer,log);
            blHiloInvocacionBUC.start();
                	
            if (seActualizoCuenta == true) {// si se actualizo la cuenta individual se confirma exito
				response = respuestaExitosaServicio();
				daReactivar.cambiarFechaNotificacionBizagiReactiva(database, Integer.parseInt(result.getParametrosSalida().get(0).getParameterValue().toString()));
				database.commit(); 
			} else {
				if (rtaPlenitud == null) {// si no se pudo realizar conexion con plenitud
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
                    "Al Actualizar el vinculado no se retornaron datos del SP(pr_vinc_ReActivarVinculado) o el vinculado ya se re activo");
            log.error(payLoad, metaData);
            
            if(response == null){
            	response = respuestaErrorTecnicoServicio();
            }
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
    		TipoInformacionGeneralVinculado informacionVinculado, String idVinculado) throws DataAccessException {

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
    		TipoInformacionGeneralVinculado informacionVinculado, String idVinculado) throws DataAccessException {

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
    		TipoInformacionGeneralVinculado informacionVinculado, String idVinculado) throws DataAccessException {

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

	
}
