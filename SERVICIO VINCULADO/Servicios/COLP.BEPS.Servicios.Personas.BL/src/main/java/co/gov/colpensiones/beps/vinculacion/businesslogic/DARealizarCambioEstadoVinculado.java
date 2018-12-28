package co.gov.colpensiones.beps.vinculacion.businesslogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.gov.colpensiones.beps.comunes.dto.TransicionesEstadoPermitidasDTO;
import co.gov.colpensiones.beps.comunes.dto.VinculadoEstadoDetalleDTO;
import co.gov.colpensiones.beps.comunes.enumeraciones.TipoConexionBaseDatosEnum;
import co.gov.colpensiones.beps.comunes.utilidades.Constantes;
import co.gov.colpensiones.beps.comunes.utilidades.DatabaseManager;
import co.gov.colpensiones.beps.dal.utilidades.CommandType;
import co.gov.colpensiones.beps.dal.utilidades.DataRow;
import co.gov.colpensiones.beps.dal.utilidades.DataStoredProcedure;
import co.gov.colpensiones.beps.dal.utilidades.DataTable;
import co.gov.colpensiones.beps.dal.utilidades.DbCommand;
import co.gov.colpensiones.beps.dal.utilidades.DbParameter;
import co.gov.colpensiones.beps.dal.utilidades.DbType;
import co.gov.colpensiones.beps.excepciones.DataAccessException;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContextoRadicacion;

/**
 * <b>Descripción:</b> Clase encargada de la interacción con la base de datos. <br/>
 * 
 * <b>Caso de Uso:</b> GVI-VIN-3-FAB-13-RealizarCambioEstadoVinculado <br/>
 * @author Arnold Rios Delgado <arrios@heinsohn.com.co>
 */
public class DARealizarCambioEstadoVinculado {

    /**
     * constructor por defecto.
     */
    public DARealizarCambioEstadoVinculado() {
    }

    /**
     * Consulta todas las transiciones disponibles en la base de datos
     * 
     * @return List<TransicionesEstadoPermitidasDTO> lista de todas la transiciones posibles
     * @throws DataAccessException
     *             error al interactuar con la base de datos.
     */
    public List<TransicionesEstadoPermitidasDTO> transicionesEstadoPermitidas() throws DataAccessException {

        DbCommand command = null;
        DataTable dataTable = null;
        DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);
        List<TransicionesEstadoPermitidasDTO> lista = new ArrayList<TransicionesEstadoPermitidasDTO>();
        try {
            command = database.GetXmlCommand("PR_ConsultarTransicionesEstadoPermitida");
            command.setCommandType(CommandType.Text);

            /* parametros que ingresan */

            dataTable = database.ExecuteDataTable(command);

            if (dataTable != null && dataTable.getRows() != null && !dataTable.getRows().isEmpty()) {
                for (DataRow iter_ : dataTable.getRows()) {
                    Object estadoInicial = iter_.getValue("estadoInicial");
                    Object detalleInicial = iter_.getValue("detalleInicial");
                    Object estadoFinal = iter_.getValue("estadoFinal");
                    Object detalleFinal = iter_.getValue("detalleFinal");
                    TransicionesEstadoPermitidasDTO dto = new TransicionesEstadoPermitidasDTO(estadoInicial == null ? ""
                            : estadoInicial.toString(), detalleInicial == null ? "" : detalleInicial.toString(), estadoFinal == null ? ""
                            : estadoFinal.toString(), detalleFinal == null ? "" : detalleFinal.toString(),
                            (Boolean) iter_.getValue("reactiva"), (Boolean) iter_.getValue("cancela"));
                    lista.add(dto);
                }

            }

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            // metaData.put(ConstantesLoggerServicios.METADATA_GENERO_BEPS, generoBeps);
            throw new DataAccessException(null, metaData, e);
        }
        return lista;
    }

    /**
     * Consulta por número de documento de un vinculado, el estado y detalle estado actual.
     * 
     * @param cedulasVinculado
     *            lista de vinculados a consultar en la base de datos separados por comas.
     * @return List<VinculadoEstadoDetalleDTO> lista de tipo, detalle, estado, detalle estado de los vinculados que esten de la base de
     *         datos.
     * @throws DataAccessException
     *             error al interactuar con la base de datos.
     */
    public List<VinculadoEstadoDetalleDTO> consultarEstadoDetalleVinculado(String cedulasVinculado) throws DataAccessException {

        DbCommand command = null;
        DataTable dataTable = null;
        DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);
        List<VinculadoEstadoDetalleDTO> lista = new ArrayList<VinculadoEstadoDetalleDTO>();
        try {
            command = database.GetXmlCommand("PR_ConsultarEstadoDetalleVinculado");

            command.setCommandType(CommandType.Text);

            command.setCommandText(command.getCommandText().replace("%vinculados%", cedulasVinculado));
            /* parametros que ingresan */

            dataTable = database.ExecuteDataTable(command);

            if (dataTable != null && dataTable.getRows() != null && !dataTable.getRows().isEmpty()) {
                VinculadoEstadoDetalleDTO dto = null;
                for (DataRow iter_ : dataTable.getRows()) {
                    Object detalle = iter_.getValue("detalle");
                    dto = new VinculadoEstadoDetalleDTO(iter_.getValue("documento").toString(), iter_.getValue("numero").toString(), iter_
                            .getValue("estado").toString(), detalle == null ? "" : detalle.toString());
                    lista.add(dto);
                }

            }

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            // metaData.put(ConstantesLoggerServicios.METADATA_GENERO_BEPS, generoBeps);
            throw new DataAccessException(null, metaData, e);
        }
        return lista;
    }

    /**
     * Método que consulta en base de datos todos los estados permitidos.
     * 
     * @return lista separado por comas de todos los estados permitidos.
     * @throws DataAccessException
     *             error al interactuar con la base de datos.
     */
    public String consultarEstadoPermitidos() throws DataAccessException {

        DbCommand command = null;
        DataTable dataTable = null;
        DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);
        StringBuilder listaEstados = new StringBuilder();
        try {
            command = database.GetXmlCommand("PR_ConsultarEstadosParametricos");
            command.setCommandType(CommandType.Text);

            dataTable = database.ExecuteDataTable(command);

            for (DataRow fila : dataTable.getRows()) {
                Object estado = fila.getValue("estado");
                if (estado != null) {
                    listaEstados.append(estado.toString()).append(",");
                }
            }

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            // metaData.put(ConstantesLoggerServicios.METADATA_GENERO_BEPS, generoBeps);
            throw new DataAccessException(null, metaData, e);
        }
        return listaEstados.toString();
    }

    /**
     * Método que Consulta los detalles de estados permitidos.
     * 
     * @return lita de detalle estados permitidos separados por coma.
     * @throws DataAccessException
     *             error al interactuar con la base de datos.
     */
    public String consultarDetalleEstadoPermitidos() throws DataAccessException {

        DbCommand command = null;
        DataTable dataTable = null;
        DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);
        StringBuilder listaEstados = new StringBuilder();
        try {
            command = database.GetXmlCommand("PR_ConsultarDetalleEstadosParametricos");
            command.setCommandType(CommandType.Text);

            dataTable = database.ExecuteDataTable(command);

            for (DataRow fila : dataTable.getRows()) {
                Object detalleEstado = fila.getValue("detalleEsado");
                if (detalleEstado != null) {
                    listaEstados.append(detalleEstado.toString()).append(",");
                }
            }

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            // metaData.put(ConstantesLoggerServicios.METADATA_GENERO_BEPS, generoBeps);
            throw new DataAccessException(null, metaData, e);
        }
        return listaEstados.toString();
    }

    /**
     * Cambia el estado de la cuenta individual en plenitud.
     * @param idVinculado numero de documento del vinculado
     * @param operacion Operación a realizar (C=Cancelar; A=Activar) 
     * @param causal Causa cancelación (Debe estar parametrizada como código de causal en Plenitud)
     * @param transicionPermitaVinculado transición permitida de estados y detalle estado
     * @param informacionContexto información de contexto 
     * @return retorna el estado de la operación 
     * @throws DataAccessException error al interactuar con la base de datos.
     */
    public String cambiarEstadoCuentaIndividual(String idVinculado, String operacion, String causal,
            TransicionesEstadoPermitidasDTO transicionPermitaVinculado, TipoInformacionContextoRadicacion informacionContexto)
            throws DataAccessException {

        DataStoredProcedure data = null;
        DatabaseManager database = null;
        String resultado = "";
        try {

            /* Homologacion tipo de documento */
            // String tipoDocumento = Util.getResourceProperty(Constantes._HOMOLOGACION_NAME, Constantes.PREFIJO_LLAVES_TIPO_DOCUMENTO
            // + filtrosConsulta.getIdentificacion().getTipoDocumento());

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
            data = database.executeListResultSet(command);

            /* Verificación de resultado de invocación a plenitud */
            if (data.getParametrosSalida() != null && data.getParametrosSalida().size() > 0) {

                DbParameter parametroSalidaCodigo = data.getParametrosSalida().get(0);
                DbParameter parametroSalidaMensaje = data.getParametrosSalida().get(1);

                if (Constantes.COD_ERROR_CAMBIAR_ESTADO_CUENTA.contains(parametroSalidaCodigo.getParameterValue().toString())) {
                    resultado = parametroSalidaMensaje.getParameterValue().toString();
                } else {

                    resultado = realizarCambiosVinculadoSistemaBeps(idVinculado, transicionPermitaVinculado, informacionContexto);
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
     * Crea un registro en el histórico de cambio de estado.
     * @param databaseSqlServer conexión a la base de datos.
     * @param numeroDocumento numero de documento del vinculado
     * @param transicionPermitaVinculado transición permitida de estados y detalle estado.
     * @param informacionContexto información de contexto
     * @throws DataAccessException error al interactuar con la base de datos.
     */
    public void crearHistoricoEstado(DatabaseManager databaseSqlServer, String numeroDocumento,
            TransicionesEstadoPermitidasDTO transicionPermitaVinculado, TipoInformacionContextoRadicacion informacionContexto)
            throws DataAccessException {

        try {

            DbCommand command = databaseSqlServer.GetXmlCommand("PR_CrearHistoricoEstado");
            command.setCommandType(CommandType.Text);

            databaseSqlServer.AddInXmlParameter(command, "@idVinculado", numeroDocumento);
            databaseSqlServer.AddInXmlParameter(command, "@estadoAnterior", transicionPermitaVinculado.getEstadoInicial());
            databaseSqlServer.AddInXmlParameter(
                    command,
                    "@detalleEstadoAnterior",
                    transicionPermitaVinculado.getDetalleEstadoInicial() == "" ? null : transicionPermitaVinculado
                            .getDetalleEstadoInicial());
            databaseSqlServer.AddInXmlParameter(command, "@estadoNuevo", transicionPermitaVinculado.getEstadoFinal());
            databaseSqlServer.AddInXmlParameter(command, "@detalleEstadoNuevo",
                    transicionPermitaVinculado.getDetalleEstadoFinal() == "" ? null : transicionPermitaVinculado.getDetalleEstadoFinal());
            databaseSqlServer.AddInXmlParameter(command, "@origenCambio", informacionContexto.getSistemaOrigen());
            databaseSqlServer.AddInXmlParameter(command, "@referenciaCambio", informacionContexto.getNumeroRadicacion());

            databaseSqlServer.executeInsertQuery(command, 1);

        } catch (Exception e) {
            HashMap<String, Object> payLoad = new HashMap<String, Object>();
            payLoad.put(Constantes.MSJ_ERROR_LOG, "Error al crear el Histórico de Estado" );
            throw new DataAccessException(payLoad, new HashMap<String, String>(), e);
        }
    }

    /**
     * Actualiza el estado y detalle estado del vinculado.
     * @param databaseSqlServer conexión a la base de datos.
     * @param idVinculado numero de documento de vinculado.
     * @param transicionPermitaVinculado transición permitida de estados y detalle estado 
     * @throws DataAccessException error al interactuar con la base de datos.
     */
    public void actualizarEstadoDetalleVinculado(DatabaseManager databaseSqlServer, String idVinculado,
            TransicionesEstadoPermitidasDTO transicionPermitaVinculado) throws DataAccessException {

        try {

            DbCommand command = databaseSqlServer.GetXmlCommand("PR_CambiarEstadoDetalleVinculado");
            command.setCommandType(CommandType.Text);

            databaseSqlServer.AddInXmlParameter(command, "@estado", transicionPermitaVinculado.getEstadoFinal());
            databaseSqlServer.AddInXmlParameter(
                    command,
                    "@detalleEstado",
                    transicionPermitaVinculado.getDetalleEstadoFinal() == "" ? null : transicionPermitaVinculado
                            .getDetalleEstadoFinal());
            databaseSqlServer.AddInXmlParameter(command, "@idVinculado", idVinculado);

            databaseSqlServer.executeNonQuery(command);

        } catch (Exception e) {
            HashMap<String, Object> payLoad = new HashMap<String, Object>();
            payLoad.put(Constantes.MSJ_ERROR_LOG, "error al actualizar el estado y detalle estado del vinculado");
            throw new DataAccessException(payLoad, new HashMap<String, String>(), e);
        }
    }

    /**
     * Crea una conexión a la base de datos transaccional e invoca los metodos de actualizar estado y crear histórico.
     * @param idVinculado numero de documento de vinculado.
     * @param transicionPermitaVinculado transición permitida de estados y detalle estado.
     * @param informacionContexto información de contexto.
     * @return estado del cambio.
     * @throws Exception error al interactuar con la base de datos.
     */
    public String realizarCambiosVinculadoSistemaBeps(String idVinculado, TransicionesEstadoPermitidasDTO transicionPermitaVinculado,
            TipoInformacionContextoRadicacion informacionContexto) throws Exception {
        DatabaseManager databaseSqlServer = null;

        try {
            databaseSqlServer = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);
            databaseSqlServer.beginTransaction();

            actualizarEstadoDetalleVinculado(databaseSqlServer, idVinculado, transicionPermitaVinculado);
            crearHistoricoEstado(databaseSqlServer, idVinculado, transicionPermitaVinculado, informacionContexto);
            databaseSqlServer.commit();

        } catch (DataAccessException e) {
            try {
                if (databaseSqlServer != null) {
                    databaseSqlServer.rollBack();
                }
            } catch (Exception e1) {
                HashMap<String, Object> payLoad = new HashMap<String, Object>();
                payLoad.put(Constantes.MSJ_ERROR_LOG, e1.getMessage());
                throw new DataAccessException(payLoad, new HashMap<String, String>(), e);
            }
            throw e;
        }

        return Constantes.MSJ_CAMBIO_ESTADO_EXITOSO;

    }
    
    /**
     * Consulta todas las transiciones disponibles en la base de datos
     * 
     * @return List<TransicionesEstadoPermitidasDTO> lista de todas la transiciones posibles
     * @throws DataAccessException
     *             error al interactuar con la base de datos.
     */
    public List<TransicionesEstadoPermitidasDTO> transicionesEstadoPermitidasRetiro() throws DataAccessException {

        DbCommand command = null;
        DataTable dataTable = null;
        DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);
        List<TransicionesEstadoPermitidasDTO> lista = new ArrayList<TransicionesEstadoPermitidasDTO>();
        try {
            command = database.GetXmlCommand("PR_ConsultarTransicionesEstadoPermitidaRetiro");
            command.setCommandType(CommandType.Text);

            /* parametros que ingresan */

            dataTable = database.ExecuteDataTable(command);

            if (dataTable != null && dataTable.getRows() != null && !dataTable.getRows().isEmpty()) {
                for (DataRow iter_ : dataTable.getRows()) {
                    Object estadoInicial = iter_.getValue("estadoInicial");
                    Object detalleInicial = iter_.getValue("detalleInicial");
                    Object estadoFinal = iter_.getValue("estadoFinal");
                    Object detalleFinal = iter_.getValue("detalleFinal");
                    TransicionesEstadoPermitidasDTO dto = new TransicionesEstadoPermitidasDTO(estadoInicial == null ? ""
                            : estadoInicial.toString(), detalleInicial == null ? "" : detalleInicial.toString(), estadoFinal == null ? ""
                            : estadoFinal.toString(), detalleFinal == null ? "" : detalleFinal.toString(),
                            (Boolean) iter_.getValue("reactiva"), (Boolean) iter_.getValue("cancela"));
                    lista.add(dto);
                }

            }

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            // metaData.put(ConstantesLoggerServicios.METADATA_GENERO_BEPS, generoBeps);
            throw new DataAccessException(null, metaData, e);
        }
        return lista;
    }
    
    
}
