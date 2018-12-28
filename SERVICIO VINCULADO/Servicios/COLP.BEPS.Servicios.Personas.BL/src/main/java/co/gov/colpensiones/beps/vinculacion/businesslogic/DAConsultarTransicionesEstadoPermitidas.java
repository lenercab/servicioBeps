package co.gov.colpensiones.beps.vinculacion.businesslogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.gov.colpensiones.beps.comunes.dto.TransicionesEstadoPermitidasDTO;
import co.gov.colpensiones.beps.comunes.dto.VinculadoEstadoDetalleDTO;
import co.gov.colpensiones.beps.comunes.enumeraciones.TipoConexionBaseDatosEnum;
import co.gov.colpensiones.beps.comunes.utilidades.DatabaseManager;
import co.gov.colpensiones.beps.dal.utilidades.CommandType;
import co.gov.colpensiones.beps.dal.utilidades.DataRow;
import co.gov.colpensiones.beps.dal.utilidades.DataTable;
import co.gov.colpensiones.beps.dal.utilidades.DbCommand;
import co.gov.colpensiones.beps.excepciones.DataAccessException;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoDocumentoPersonaNatural;

/**
 * <b>Descripción:</b> Clase encargada de la interacción con la base de datos. <br/>
 * <b>Caso de Uso:</b> GVI-VIN-3-FAB-12-ConsultarTransicionesDeEstadoPermitidas <br/>
 * 
 * @author Arnold Rios Delgado <arrios@heinsohn.com.co>
 */
public class DAConsultarTransicionesEstadoPermitidas {

    /**
     * constructor por defecto.
     */
    public DAConsultarTransicionesEstadoPermitidas() {
    }

    /**
     * Consulta todas la transiciones disponibles en la base de datos
     * @return List<TransicionesEstadoPermitidasDTO>  lista de todas la transiciones posibles
     * @throws  DataAccessException error al interactuar con la base de datos.
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
                            : estadoInicial.toString(), detalleInicial == null ? "" : detalleInicial.toString(),
                            estadoFinal == null ? "" : estadoFinal.toString(), detalleFinal == null ? "" : detalleFinal.toString(),
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
     * @param vinculados lista de vinculados recibidos como parámetros del servicio.
     * @return List<VinculadoEstadoDetalleDTO> lista de tipo, detalle, estado, detalle estado de los vinculados que esten de la base de datos.
     * @throws DataAccessException error al interactuar con la base de datos.
     */
    public List<VinculadoEstadoDetalleDTO> consultarEstadoDetalleVinculado(List<TipoDocumentoPersonaNatural> vinculados)
            throws DataAccessException {

        DbCommand command = null;
        DataTable dataTable = null;
        DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);
        List<VinculadoEstadoDetalleDTO> lista = new ArrayList<VinculadoEstadoDetalleDTO>();
        try {
            command = database.GetXmlCommand("PR_ConsultarEstadoDetalleVinculado");

            command.setCommandType(CommandType.Text);

            StringBuffer cedulasVinculado = new StringBuffer();
            for (TipoDocumentoPersonaNatural iter_vinculado : vinculados) {
                cedulasVinculado.append("'").append(iter_vinculado.getNumeroDocumento()).append("'").append(",");
            }
            cedulasVinculado.deleteCharAt(cedulasVinculado.length() - 1);
            command.setCommandText(command.getCommandText().replace("%vinculados%", cedulasVinculado));
            /* parametros que ingresan */

            dataTable = database.ExecuteDataTable(command);

            if (dataTable != null && dataTable.getRows() != null && !dataTable.getRows().isEmpty()) {
                VinculadoEstadoDetalleDTO dto = null;
                for (DataRow iter_ : dataTable.getRows()) {
                    Object detalle=iter_.getValue("detalle");
                    dto = new VinculadoEstadoDetalleDTO(iter_.getValue("documento").toString(), iter_.getValue("numero").toString(), iter_
                            .getValue("estado").toString(), detalle==null? "" :detalle.toString());
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
     * @return lista separado por comas de todos los estados permitidos.
     * @throws DataAccessException error al interactuar con la base de datos.
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
     * @return lita de detalle estados permitidos separados por coma.
     * @throws DataAccessException error al interactuar con la base de datos.
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
        
}
