package co.gov.colpensiones.beps.vinculacion.businesslogic;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import co.gov.colpensiones.beps.comunes.enumeraciones.TipoConexionBaseDatosEnum;
import co.gov.colpensiones.beps.comunes.utilidades.Constantes;
import co.gov.colpensiones.beps.comunes.utilidades.ConstantesLoggerServicios;
import co.gov.colpensiones.beps.comunes.utilidades.DatabaseManager;
import co.gov.colpensiones.beps.dal.utilidades.CommandType;
import co.gov.colpensiones.beps.dal.utilidades.DataStoredProcedure;
import co.gov.colpensiones.beps.dal.utilidades.DataTable;
import co.gov.colpensiones.beps.dal.utilidades.DbCommand;
import co.gov.colpensiones.beps.dal.utilidades.DbParameter;
import co.gov.colpensiones.beps.dal.utilidades.DbType;
import co.gov.colpensiones.beps.excepciones.DataAccessException;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContexto;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionGeneralVinculado;

public class DAReactivar {
	
	/**
     * Método mediante el cual se consulta el identificador del registro asociado a un prospecto, de acuerdo a su número de identificación
     * 
     * @param database
     *            Conexión a la base de datos
     * @param informacionVinculado
     *            Objeto con la información de identificación del prospecto
     * @return Identificador del registro asociado al vinculado
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public String consultarProspecto(DatabaseManager database, String nroDocumento)
            throws DataAccessException {
        String pkProspecto = null;
        DbCommand command = null;

        try {
            command = database.GetXmlCommand("PR_ConsultarProspecto");
            command.setCommandType(CommandType.Text);
            database.AddInXmlParameter(command, "@vpp_numero_documento", nroDocumento);

            DataTable data = database.ExecuteDataTableCommandText(command);
            if (data.getRows().size() > 0) {
            	pkProspecto = data.getRows().get(0).getValue("vpp_pk_id").toString();
            }

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, nroDocumento);
            throw new DataAccessException(null, metaData, e);
        }
        return pkProspecto;
    }
	
	
	
	 /**
     * Cambia el estado de la cuenta individual en plenitud.
     * @param idVinculado numero de documento del vinculado
     * @param operacion Operación a realizar (C=Cancelar; A=Activar) 
     * @param informacionContexto información de contexto 
     * @return retorna el estado de la operación 
     * @throws DataAccessException error al interactuar con la base de datos.
     */
    public String cambiarEstadoCuentaIndividual(String idVinculado, String operacion, String causal)
            throws DataAccessException {

        DataStoredProcedure data = null;
        DatabaseManager database = null;
        String resultado = null;
        try {

            database = new DatabaseManager(TipoConexionBaseDatosEnum.AS400_CAMBIO_ESTADO);

            database.beginTransaction();
            DbCommand command = database.GetXmlCommand("AS400StoredProcedure_ActualizaEstadoCuenta");
            command.setCommandType(CommandType.StoredProcedure);

            database.AddInXmlParameter(command, "ZIDVINCULADO", idVinculado);
            database.AddInXmlParameter(command, "ZOPERACION", operacion);
            database.AddInXmlParameter(command, "ZCAUSACANC", causal);

            /* Parametros de salida */
            database.AddOutParameter(command, "ZCODERROR", DbType.CHAR, 7);
            database.AddOutParameter(command, "ZMENSAERROR", DbType.CHAR, 132);

            /* Se obtienen los datos de la ejecucion del procedimiento */
            //data = database.executeGenericStoredProcedure(command);
            data = database.executeListResultSet(command);

            /* Verificación de resultado de invocación a plenitud */
            if (data.getParametrosSalida() != null && data.getParametrosSalida().size() > 0) {

                DbParameter parametroSalidaCodigo = data.getParametrosSalida().get(0);
                DbParameter parametroSalidaMensaje = data.getParametrosSalida().get(1);

                if (Constantes.COD_ERROR_CAMBIAR_ESTADO_CUENTA.contains(parametroSalidaCodigo.getParameterValue().toString())) {
                    resultado = parametroSalidaMensaje.getParameterValue().toString();
                } else {
                	resultado = "0000000";

//                	crearHistoricoEstado(database, idVinculado, informacionContexto);
//                	CREAR METODOS PARA LLENAR TABLA BITACORA
                     //database.commit();
                }
            }
            database.commit();

        } catch (DataAccessException e) {
            try {
                if (database != null) {
                    database.rollBack();
                }
            } catch (Exception e1) {
                HashMap<String, Object> payLoad = new HashMap<String, Object>();
                payLoad.put(Constantes.MSJ_ERROR_LOG, e1.getMessage());
                throw new DataAccessException(payLoad, new HashMap<String, String>(), e);
            }
            throw e;
	        }catch (Exception e) {
	            HashMap<String, Object> payLoad = new HashMap<String, Object>();
	            try {
	                if (database != null) {
	                    database.rollBack();
	                }
	            } catch (Exception e1) {
	                payLoad.put(Constantes.MSJ_ERROR_LOG, e1.getMessage());
	                throw new DataAccessException(payLoad, new HashMap<String, String>(), e);
	            }
	            payLoad.put(Constantes.MSJ_ERROR_LOG, "error al cambiar el Estado de la Cuenta Individual.");
	            throw new DataAccessException(payLoad, new HashMap<String, String>(), e);
	        }
        return resultado;
    }
	
    
    /**
     * Método encargado de acceder a la BD para realizar actualización de datos para re activar a un vinculado
     * 
     * Requerimiento: Re Activación de Vinculados
     * 
     * @param database
     *            Conexión a la base de datos
     * @param informacionContexto
     *            , datos del contexto
     * @param informacionVinculado
     *            , datos del vinculado
     * @return la información que retorna el re activar al vinculado
     * @DataAccessException si se genera alguna excepción
     */
    public DataStoredProcedure reActivarVinculado(DatabaseManager database, TipoInformacionContexto informacionContexto,
            TipoInformacionGeneralVinculado informacionVinculado, int validacion) throws DataAccessException {

        DbCommand command = null;
        DataStoredProcedure data = null;
        SimpleDateFormat formatoFechaVinculacion = new SimpleDateFormat(Constantes.FORMATO_FECHA_AAAAMMDD);

        try {
            java.sql.Date sqlFecha = new java.sql.Date(formatoFechaVinculacion.parse(informacionVinculado.getFechaVinculacion()).getTime());

            command = database.GetXmlCommand("StoredProcedure_ReActivarVinculado");
            command.setCommandType(CommandType.StoredProcedure);

            /* parametros que ingresan al SP */
            database.AddInXmlParameter(command, "@tipoDocumento", informacionVinculado.getIdentificacion().getTipoDocumento());
            database.AddInXmlParameter(command, "@numDocumento", informacionVinculado.getIdentificacion().getNumeroDocumento());
            database.AddInXmlParameter(command, "@actividadEconomicaPrin", informacionVinculado.getInformacionEconomica()
                    .getCodigoActividadEconomicaPrincipal());
            database.AddInXmlParameter(command, "@actividadEconomicaSec", informacionVinculado.getInformacionEconomica()
                    .getCodigoActividadEconomicaSecundaria());
            database.AddInXmlParameter(command, "@autorizaEnvioComunicacion",
                    Constantes.SI.equals(informacionVinculado.getTipoAutorizacion().getAutorizacionEnvioComunicacion()) ? 1 : 0);
            database.AddInXmlParameter(command, "@autorizaManejoInformacion",
                    Constantes.SI.equals(informacionVinculado.getTipoAutorizacion().getAutorizacionManejoInformacion()) ? 1 : 0);
            database.AddInXmlParameter(command, "@usuarioVinculacion", informacionVinculado.getUsuarioVinculacion());
            database.AddInXmlParameter(command, "@usuarioRegistro", informacionContexto.getUsuarioSistemaExterno());
            database.AddInXmlParameter(command, "@canalSistema", informacionContexto.getSistemaOrigen());
            database.AddInXmlParameter(command, "@canal", informacionVinculado.getCanal());
            database.AddInXmlParameter(command, "@numeroRadicado", informacionContexto.getIdCorrelacion());
            database.AddInXmlParameter(command, "@fechaVinculacion", sqlFecha);
            database.AddInXmlParameter(command, "@validacion", validacion);
            
            /* parámetros que salen del SP */
            database.AddOutParameter(command, "@identificadorVinculado", DbType.BIGINT, 8);
            database.AddOutParameter(command, "@idnovedad", DbType.BIGINT, 8);
            database.AddOutParameter(command, "@primerApellido", DbType.VARCHAR, 30);
            database.AddOutParameter(command, "@segundoApellido", DbType.VARCHAR, 30);
            database.AddOutParameter(command, "@primerNombre", DbType.VARCHAR, 30);
            database.AddOutParameter(command, "@segundoNombre", DbType.VARCHAR, 30);
            database.AddOutParameter(command, "@genero", DbType.CHAR, 1);
            database.AddOutParameter(command, "@fechaNacimiento", DbType.DATE, 8);

            data = database.executeListResultSet(command);
            
        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, informacionVinculado.getIdentificacion().getTipoDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, informacionVinculado.getIdentificacion().getNumeroDocumento());
            throw new DataAccessException(null, metaData, e);
        }
        return data;
    }
    
    /**
     * Método mediante el cual se consulta el identificador del registro asociado a un vinculado, de acuerdo a su número de identificación
     * 
     * @param database
     *            Conexión a la base de datos
     * @param informacionVinculado
     *            Objeto con la información de identificación del vinculado
     * @return Identificador del registro asociado al vinculado
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public int validarReactivacion(DatabaseManager database, String numDocumento, String nroRadicado)
            throws DataAccessException {
        int respuesta = 0;
        DbCommand command = null;
        DataStoredProcedure data = null;

        try {
        	
            command = database.GetXmlCommand("StoredProcedure_Valida_Reactivacion");
            command.setCommandType(CommandType.StoredProcedure);
            database.AddInXmlParameter(command, "@numDocumento", numDocumento);
            database.AddInXmlParameter(command, "@nroRadicado", nroRadicado);
            
            /* parámetros que salen del SP */
            database.AddOutParameter(command, "@respuesta", DbType.BIGINT, 2);
            
            data = database.executeListResultSet(command);
            
            respuesta = Integer.parseInt(data.getParametrosSalida().get(0).getParameterValue().toString());
            
        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, numDocumento);
            throw new DataAccessException(null, metaData, e);
        }
        return respuesta;
    }
    
    public boolean insertarBitacoraActCta(DatabaseManager database, String nroDocumento, String nroRadicado) throws DataAccessException {
        boolean respuesta = false;
        DbCommand command = null;

        try {
            command = database.GetXmlCommand("PR_InsertaBitacoraAct");
            command.setCommandType(CommandType.Text);
            database.AddInXmlParameter(command, "@numDocumento", nroDocumento);
            database.AddInXmlParameter(command, "@nroRadicado", nroRadicado);

            database.executeInsertQuery(command, 1);
            respuesta = true;

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put("Error al insertar en la bitacora", nroDocumento);
            throw new DataAccessException(null, metaData, e);
        }
        return respuesta;
    }
    
   
    public void actualizarBitacoraEstadoCta(DatabaseManager database, String estadoCta, String estadoReintento, String nroDocumento, String nroRadicado) throws DataAccessException {
        DbCommand command = null;

        try {
            command = database.GetXmlCommand("PR_ActualizarBitacora");
            command.setCommandType(CommandType.Text);

                database.AddInXmlParameter(command, "@estadoCta", estadoCta);
                database.AddInXmlParameter(command, "@estadoReintento", estadoReintento);
                database.AddInXmlParameter(command, "@nroDocumento",nroDocumento);
                database.AddInXmlParameter(command, "@nroRadicado", nroRadicado);

                database.executeNonQuery(command);

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, nroDocumento);
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_RADICADO, nroRadicado);
            throw new DataAccessException(null, metaData, e);
        }
    }
    
   
    public void cambiarFechaNotificacionBizagi(DatabaseManager database, int idVinculado) throws DataAccessException {

        DbCommand command = null;
        
        try {
            command = database.GetXmlCommand("PR_ActualizarNotificacionBizagi");
            command.setCommandType(CommandType.Text);
            
            database.AddInXmlParameter(command, "@id_vinculado", idVinculado);
            
            database.executeNonQuery(command);

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, idVinculado + "");
            throw new DataAccessException(null, metaData, e);
        }
    }
    
    public void cambiarFechaNotificacionBizagiReactiva(DatabaseManager database, int idVinculado) throws DataAccessException {

        DbCommand command = null;
        
        try {
            command = database.GetXmlCommand("PR_ActualizarNotificacionBizagiReactiva");
            command.setCommandType(CommandType.Text);
            
            database.AddInXmlParameter(command, "@id_vinculado", idVinculado);
            
            database.executeNonQuery(command);

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, idVinculado + "");
            throw new DataAccessException(null, metaData, e);
        }
    }
	
}
