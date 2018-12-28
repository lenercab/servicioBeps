/**
 * 
 */
package co.gov.colpensiones.beps.dal.utilidades;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import co.gov.colpensiones.beps.comunes.databaseconfiguration.DatabaseConfiguration;
import co.gov.colpensiones.beps.comunes.databaseconfiguration.MetadataParameter;
import co.gov.colpensiones.beps.comunes.databaseconfiguration.MetadataQuery;
import co.gov.colpensiones.beps.comunes.utilidades.Constantes;
import co.gov.colpensiones.beps.comunes.utilidades.ResourceUtil;
import co.gov.colpensiones.beps.comunes.utilidades.Serializer;
import co.gov.colpensiones.beps.comunes.utilidades.StringUtil;
import co.gov.colpensiones.beps.comunes.utilidades.SvcLogger;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Caso de Uso:</b> FAB- <br/>
 *
 * @author ddelaroche <ddelaroche@heinsohn.com.co>
 */
public class SqlServerDatabase {
	
    private String driverClass;
    private String connectionURL;
    private String user;
    private String password;
    private DatabaseConfiguration databaseConfiguration = null;
    private MetadataQuery selectedMetadataQuery = null;

    private boolean createConnectionFromDataSourceData = false;
    private boolean sharedConnection = false;

    private Connection conn = null;

    private String jndiName = null;

    private String uuid = "";
    
    private static final String NOMBRE_ARCHIVO_DATABASE_CONFIGURATION = "DatabaseConfiguration.xml";
    private static final String JNDI_NAME = "jndi-name";
    private static final String NO_SE_PUEDE_ADICIONAR_PARAMETRO = "Cannot add a xml parameter because it is not an Xml Command Type";
    private static final String PARAMETRO_NO_EXISTE = "Parameter {0} does not exists in the xml configuration for queryName {1}";
    private static final String QUERY_NAME = "QueryName '";
    private static final String DOES_NOT_EXISTS = "' does not exists";
    private static final String TEXTO_UUID = " UUID: ";
    private static final String USE_DATASOURCE = "useDataSource";
    private static final String PRINT_LOG_DB = "printLogDB";
    private static final String INICIO_EJECUCION_QUERY = "Inicio  EjecuciÃ³n Query : ";
    private static final String PRINT_COMMAND = "printCommand";
    private static final String ERROR_EJECUTANDO_PROCEDIMIENTO = "Error ExecuteDataTableStoredProcedure: ";
    private static final String FIN_EJECUCION_QUERY = "Fin  EjecuciÃ³n Query : ";
    private static final String ERROR_EXECUTE_DATA_TABLE = "Error ExecuteDataTableCommandText: ";
    private static final String ERROR_EJECUTANDO_INSERT = "Error executeInsertCommandText: ";
    private static final String ERROR_EJECUTANDO_PROCEDIMIENTO2 = "Error executeStoredProcedure: ";

    /*
     * Este constructor busca el jdni en el archivo resources.
     */
    public SqlServerDatabase() {
        try {
            // Obtiene el jdni
            jndiName = ResourceUtil.getResourceValue(Constantes._RESOURCE_NAME, JNDI_NAME);
        } catch (Exception e) {
            SvcLogger.error(e.getLocalizedMessage());
        }
    }

    /**
     * Este constructor recibe la conexion compartida
     * 
     * @param conexion
     */

    public SqlServerDatabase(Connection connection) {
        this.conn = connection;
        this.sharedConnection = true;
    }

    /**
     * Este constructor recibe el nombre jndi y crea la conexion
     * 
     * @param jndiName
     */

    public SqlServerDatabase(String jndiName) {
        this.jndiName = jndiName;
    }

    /**
     * Constructor que obtiene el jndi para crear la conexiÃ³n y el indicador de si debe ser creada desde un datasource.
     * 
     * @param jndiName
     * @param createConnectionFromDataSourceData
     */
    public SqlServerDatabase(String jndiName, boolean createConnectionFromDataSourceData) {
        this.jndiName = jndiName;
        this.createConnectionFromDataSourceData = createConnectionFromDataSourceData;
    }

    /**
     * Se envian los parametro para la base de datos
     * 
     * @param driverClass
     * @param connectionURL
     * @param user
     * @param password
     */

    public SqlServerDatabase(String driverClass, String connectionURL, String user, String password) {

        // Asigna los valores de la conexion
        super();
        this.driverClass = driverClass;
        this.connectionURL = connectionURL;
        this.user = user;
        this.password = password;
    }

    // GETTERS AND SETTERS
    
    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getConnectionURL() {
        return connectionURL;
    }

    public void setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MetadataQuery getSelectedMetadataQuery() {
        return selectedMetadataQuery;
    }

    public void setSelectedMetadataQuery(MetadataQuery selectedMetadataQuery) {
        this.selectedMetadataQuery = selectedMetadataQuery;
    }

    /**
     * Permite obtener la configuraciÃ³n de base de datos
     * 
     * @return configuraciÃ³n que tiene asignado el SqlServerDatabase
     */
    public DatabaseConfiguration getDatabaseConfiguration() {
        return databaseConfiguration;
    }

    /**
     * Permite establecer la configuraciÃ³n de la base de datos.
     * 
     * @param databaseConfiguration
     *            objeto a establecedr
     */
    public void setDatabaseConfiguration(DatabaseConfiguration databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
    }

    /**
     * Esta funcion obtiene un procedimiento almacenado leido desde el DataBaseConfiguration
     * 
     * @param storedProcedure
     * @return
     */
    public DbCommand GetStoredProcCommand(String storedProcedure) {
        return new DbCommand(CommandType.StoredProcedure, storedProcedure);
    }

    /**
     * Permite configurar la conexiÃ³n con base en la informaciÃ³n que se encuentra parametrizada en el archivo DatabaseConfiguration.xml
     */
    private void configConnection() {
        try {
            String xml = Serializer.leerArchivo(NOMBRE_ARCHIVO_DATABASE_CONFIGURATION);

            // Obtiene la configuracion de la base de datos
            setDatabaseConfiguration((DatabaseConfiguration) Serializer.deserializar(xml, DatabaseConfiguration.class));

            // Configura los valores de la conexion
            setDriverClass(getDatabaseConfiguration().getDriverClass());
            setConnectionURL(getDatabaseConfiguration().getConnectionURL());
            setUser(getDatabaseConfiguration().getUser());
            setPassword(getDatabaseConfiguration().getPassword());
        } catch (Exception exc) {
            SvcLogger.error(exc.getMessage());
        }
    }

    /**
     * Lee un comando XML desde el DatabaseConfiguration
     * 
     * @param queryName
     * @return
     * @throws Exception
     */

    public DbCommand GetXmlCommand(String queryName) throws Exception {
        configConnection();

        // Busca el query en el XML
        List<MetadataQuery> lst = getDatabaseConfiguration().getMetadataQueries().getMetadataQuery();
        String commandText = "";
        CommandType commandType = CommandType.Xml;
        for (MetadataQuery metadataQuery : lst) {
            if (metadataQuery.getQueryName().equals(queryName)) {
                setSelectedMetadataQuery(metadataQuery);
                commandText = metadataQuery.getCommandText();
                if (metadataQuery.getCommandType() != null && metadataQuery.getCommandType().equals("StoredProcedure"))
                    commandType = CommandType.StoredProcedure;
                break;
            }
        }
        // Si no encontro nada excepcion
        if (commandText.trim().length() == 0)
            throw new Exception(QUERY_NAME + queryName + DOES_NOT_EXISTS);

        return new DbCommand(commandType, commandText, queryName);
    }

    /**
     * Lee el comando tipo texto
     * 
     * @param textCommand
     * @return
     */

    public DbCommand GetTextCommand(String textCommand) {
        return new DbCommand(CommandType.Text, textCommand);
    }

    /**
     * Adiciona un parametro al comando seleccionado
     * 
     * @param command
     * @param parameterName
     * @param parameterType
     * @param parameterScale
     * @param parameterValue
     */

    public void AddInParameter(DbCommand command, String parameterName, DbType parameterType, int parameterScale, Object parameterValue) {
        command.addInParameter(parameterName, GetSqlType(parameterType), parameterScale, parameterValue);

    }

    /**
     * Adiciona un parametro XML al comando escojido
     * 
     * @param command
     * @param parameterName
     * @param parameterValue
     * @throws Exception
     */

    public void AddInXmlParameter(DbCommand command, String parameterName, Object parameterValue) throws Exception {

        if (getSelectedMetadataQuery() == null)
            throw new Exception(NO_SE_PUEDE_ADICIONAR_PARAMETRO);

        List<MetadataParameter> list = getSelectedMetadataQuery().getParameters().getParameter();

        String parameterType = "";
        int parameterScale = -1;
        for (MetadataParameter metadataParameter : list) {
            if (metadataParameter.getParameterName().equals(parameterName)) {
                parameterType = metadataParameter.getParameterType();
                parameterScale = metadataParameter.getParameterScale();
                break;
            }
        }
        if (parameterType.trim().length() == 0 || parameterScale == -1)
            throw new Exception(StringUtil.Format(PARAMETRO_NO_EXISTE,
                    parameterName, getSelectedMetadataQuery().getQueryName()));

        AddInParameter(command, parameterName, DbType.valueOf(parameterType), parameterScale, parameterValue);

    }

    /**
     * Adiciona un parametro de salida al parametro escojido
     * 
     * @param command
     * @param parameterName
     * @param parameterType
     * @param parameterScale
     */

    public void AddOutParameter(DbCommand command, String parameterName, DbType parameterType, int parameterScale) {
        command.addOutParameter(parameterName, GetSqlType(parameterType), parameterScale);
    }

    /**
     * Obitene el valor de un parametro
     * 
     * @param command
     * @param parameterName
     * @return
     */

    public Object getParameterValue(DbCommand command, String parameterName) {
        return command.getParameterValue(parameterName);
    }

    /**
     * Ejecuta un Query para obtener una estructura DataTable desde una consulta
     * 
     * @param command
     * @return
     * @throws Exception
     */

    public DataTable ExecuteDataTable(DbCommand command) throws Exception {
        DataTable table = new DataTable();
        if (command.getCommandType() == CommandType.StoredProcedure)
            table = ExecuteDataTableStoredProcedure(command);
        else if (command.getCommandType() == CommandType.Text || command.getCommandType() == CommandType.Xml)
            table = ExecuteDataTableCommandText(command);
        return table;
    }

    /**
     * Imprime en el log de acuerdo con lo que se haya establecido en el archivo de recursos para la propiedad printLogDB Si se ha
     * parametrizado true en la propiedad, se imprimen el tÃ­tulo y mensaje que ingresan como parÃ¡metro, en caso contrario no se imprimen.
     * 
     * @param title
     *            tÃ­tulo del log a imprimir
     * @param message
     *            mensaje a imprimir
     */
    private void printLogBD(String title, String message) {

        boolean printLogDB = false;
        try {
            printLogDB = Boolean.parseBoolean(ResourceUtil.getResourceValue(Constantes._RESOURCE_NAME, PRINT_LOG_DB));

        } catch (Exception e1) {
            printLogDB = true;
        }

        if (printLogDB == true) {
            if (title != null) {
                SvcLogger.info(title);
            }

            SvcLogger.info(message);
        }

    }

    /**
     * Obtener el SQL de un comando
     * 
     * @param command
     * @return sql obtenido
     */

    private String getCommandAsString(DbCommand command) {
        int iParametersCount = command.getParameters().size();
        String parametersCall = "(";

        for (int i = 0; i < iParametersCount; i++) {
            if (i == 0)
                parametersCall = command.getParameters().get(i).getParameterName() + " = "
                        + command.getParameters().get(i).getParameterValue();
            else
                parametersCall += "," + command.getParameters().get(i).getParameterName() + " = "
                        + command.getParameters().get(i).getParameterValue();
        }

        parametersCall = parametersCall + ")";

        String callStoredProcedure = command.getCommandText() + parametersCall;

        return callStoredProcedure;

    }

    /**
     * Ejecuta un procedimiento rpara obtener un DatatTable
     * 
     * @param command
     * @return
     * @throws Exception
     */

    private DataTable ExecuteDataTableStoredProcedure(DbCommand command) throws Exception {

        DataTable table = new DataTable();

        try {
            // if (conn == null)
            if (conn == null || (conn != null && conn.isClosed()))
                conn = GetConnection();

            String callStoredProcedure = "{ call " + command.getCommandText() + " }";

            printLogBD(null, INICIO_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
            printLogBD(PRINT_COMMAND, getCommandAsString(command) + TEXTO_UUID + uuid);

            CallableStatement stmt = conn.prepareCall(callStoredProcedure);

            // Adiciona los parametros al Callable Statement

            for (DbParameter parameter : command.getParameters()) {
                if (parameter.getParamDirection() == ParameterDirection.OUT)
                    stmt.registerOutParameter(parameter.getParameterName(), parameter.getParameterType(), parameter.getParameterScale());
                else
                    stmt.setObject(parameter.getParameterName(), parameter.getParameterValue(), parameter.getParameterType(),
                            parameter.getParameterScale());

            }

            ResultSet rs = stmt.executeQuery();
            // Lee el ResultSet y lo convierte a DataTable
            ResultSetMetaData metaData = rs.getMetaData();

            // Obtiene el valor de los parametros de salida

            if (metaData.getColumnCount() > 0) {
                table.setTableName(metaData.getTableName(1));
                for (int i = 1; i <= metaData.getColumnCount(); i++)
                    table.addColumn(new DataColumn(metaData.getColumnLabel(i)));
            }
            while (rs.next()) {

                DataRow row = new DataRow();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    row.addColumn(new DataColumn(metaData.getColumnLabel(i), rs.getObject(i)));
                }

                table.addRow(row);
            }

            for (DbParameter parameter : command.getParameters()) {
                if (parameter.getParamDirection() == ParameterDirection.OUT)
                    parameter.setParameterValue(stmt.getObject(parameter.getParameterName()));
            }

            rs.close();
            stmt.close();

            closeConnectionAutoCommit();

        } catch (Exception e) {
            SvcLogger.error(ERROR_EJECUTANDO_PROCEDIMIENTO + e.getMessage());

            closeConnectionExecution();

            throw e;
        } finally {

            printLogBD(null, FIN_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
        }

        return table;
    }

    /**
     * Ejecuta un commando tipo texto para obtener un DatatTable
     * 
     * @param command
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */

    private DataTable ExecuteDataTableCommandText(DbCommand command) throws ClassNotFoundException, SQLException {

        DataTable table = new DataTable();

        try {
            if (conn == null || (conn != null && conn.isClosed()))
                conn = GetConnection();

            PreparedStatement stmt = conn.prepareStatement(command.getCommandText());

            // Adiciona los parametros al PreparedStatement Statement

            for (int i = 0; i < command.getParameters().size(); i++) {
                DbParameter parameter = command.getParameters().get(i);
                stmt.setObject(i + 1, parameter.getParameterValue());
            }

            printLogBD(null, INICIO_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
            printLogBD(PRINT_COMMAND, getCommandAsString(command) + TEXTO_UUID + uuid);

            ResultSet rs = stmt.executeQuery();

            // Lee el ResultSet y lo convierte a DataTable
            ResultSetMetaData metaData = rs.getMetaData();

            if (metaData.getColumnCount() > 0) {
                table.setTableName(metaData.getTableName(1));
                for (int i = 1; i <= metaData.getColumnCount(); i++)
                    table.addColumn(new DataColumn(metaData.getColumnLabel(i)));
            }
            while (rs.next()) {

                DataRow row = new DataRow();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    row.addColumn(new DataColumn(metaData.getColumnLabel(i), rs.getObject(i)));
                }

                table.addRow(row);
            }

            rs.close();
            stmt.close();
            closeConnectionAutoCommit();

        } catch (SQLException e) {
            SvcLogger.error(ERROR_EXECUTE_DATA_TABLE + e.getMessage());

            closeConnectionExecution();

            throw e;
        }

        finally {

            printLogBD(null, FIN_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);

        }

        return table;
    }

    /**
     * Ejecuta un comando para obtener un ResultSet
     * 
     * @param command
     * @return
     * @throws Exception
     */

    public ResultSet ExecuteResultSet(DbCommand command) throws Exception {

        if (command.getCommandType() == CommandType.StoredProcedure)
            return ExecuteResultSetStoredProcedure(command);
        else if (command.getCommandType() == CommandType.Text || command.getCommandType() == CommandType.Xml)
            return ExecuteResultSetCommandText(command);

        return null;
    }

    /**
     * Ejecuta un procedimiento almacenado para obtener un ResultSet
     * 
     * @param command
     * @return
     * @throws Exception
     */

    private ResultSet ExecuteResultSetStoredProcedure(DbCommand command) throws Exception {

        // Prepara para abrir la conexion
        if (conn == null || (conn != null && conn.isClosed()))
            conn = GetConnection();

        // Prepara el CallableStatement
        String callStoredProcedure = "{ call " + command.getCommandText() + " }";

        CallableStatement stmt = conn.prepareCall(callStoredProcedure);

        // Adiciona los parametros al Callable Statement

        for (DbParameter parameter : command.getParameters()) {
            if (parameter.getParamDirection() == ParameterDirection.OUT)
                stmt.registerOutParameter(parameter.getParameterName(), parameter.getParameterType(), parameter.getParameterScale());
            else
                stmt.setObject(parameter.getParameterName(), parameter.getParameterValue(), parameter.getParameterType(),
                        parameter.getParameterScale());

        }

        printLogBD(null, INICIO_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
        printLogBD(PRINT_COMMAND, getCommandAsString(command) + TEXTO_UUID + uuid);
        ResultSet result = stmt.executeQuery();
        printLogBD(null, FIN_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
        return result;
    }

    /**
     * Obtiene un resultSet de un comando tipo texto
     * 
     * @param command
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */

    private ResultSet ExecuteResultSetCommandText(DbCommand command) throws ClassNotFoundException, SQLException {

        if (conn == null || (conn != null && conn.isClosed()))

            conn = GetConnection();

        PreparedStatement stmt = conn.prepareStatement(command.getCommandText());

        // Adiciona los parametros al PreparedStatement Statement

        for (int i = 0; i < command.getParameters().size(); i++) {
            DbParameter parameter = command.getParameters().get(i);
            stmt.setObject(i + 1, parameter.getParameterValue(), parameter.getParameterType(), parameter.getParameterScale());

        }
        printLogBD(null, INICIO_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
        printLogBD(PRINT_COMMAND, getCommandAsString(command) + TEXTO_UUID + uuid);
        ResultSet result = stmt.executeQuery();
        printLogBD(null, FIN_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);

        return result;
    }

    /**
     * Cierra un resultset
     * 
     * @param rs
     * @throws SQLException
     */

    public void closeResultSet(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
            rs.getStatement().close();
        }
        closeConnectionExecution();

    }

    /**
     * Cierra el resultset que ingresa como parÃ¡metro
     * 
     * @param rs
     *            resultset a cerrar.
     * @throws SQLException
     */
    public void closeExternalResultSet(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }

        closeConnectionExecution();

    }

    /**
     * Inicia una transacciÃ³n creando la conexiÃ³n con el motor e indicando que no se debe realizar autocommit.
     * 
     * @throws Exception
     */
    public void beginTransaction() throws Exception {

        if (this.sharedConnection == false) {
            conn = GetConnection();
            conn.setAutoCommit(false);
        }

    }

    /**
     * Realiza commit a la conexiÃ³n que se encuentra abierta.
     * 
     * @throws Exception
     */
    public void commit() throws Exception {
        if (conn != null) {

            if (this.sharedConnection == false) {
                conn.commit();
                conn.close();
            }

        }
    }

    /**
     * Realiza rollback sobre la conexiÃ³n que se encuentra abierta.
     * 
     * @throws Exception
     *             cuando se presenta algÃºn error al realizar la operaciÃ³n.
     */
    public void rollBack() throws Exception {
        if (conn != null) {

            if (this.sharedConnection == false) {
                conn.rollback();
                conn.close();
            }

        }
    }

    /**
     * Ejecuta un comando que no se de consulta
     * 
     * @param command
     * @return
     * @throws Exception
     */

    public int executeNonQuery(DbCommand command) throws Exception {

        int returnValue = -1;
        if (command.getCommandType() == CommandType.StoredProcedure)
            returnValue = executeNonQueryStoredProcedure(command);
        else if (command.getCommandType() == CommandType.Text || command.getCommandType() == CommandType.Xml)
            returnValue = executeNonQueryCommandText(command);
        return returnValue;

    }

    /**
     * Ejecuta un insert en la base de datos, retornando como resultado una colecciÃ³n con los id de los objetos creados.
     * 
     * @param command
     *            contiene la sentencia a ejecutar
     * @param cantidadLlaves
     *            cantidad de llaves que serÃ­an generadas automÃ¡ticamente al ejecutar la sentencia.
     * @return colecciÃ³n de Object[], en la cual cada arreglo de objetos tiene tantas posciones como se haya indicado en el parÃ¡metro
     *         cantidad de llaves y en cada posiciÃ³n contiene el valor retornado por el motor para dicha llave. como resultado del insert.
     *         ColecciÃ³n vacÃ­a en caso que no se haya podido realizar la inserciÃ³n.
     * @throws Exception
     */
    public Collection<Object> executeInsertQuery(DbCommand command, int cantidadLlaves) throws Exception {
        Collection<Object> llavesGeneradas = new ArrayList<Object>();
        if (command.getCommandType() == CommandType.Text || command.getCommandType() == CommandType.Xml)
            llavesGeneradas = executeInsertCommandText(command, cantidadLlaves);
        return llavesGeneradas;
    }

    /**
     * Ejecuta un procedimiento almacenado que no sea de consulta
     * 
     * @param command
     * @return
     * @throws Exception
     */

    private int executeNonQueryStoredProcedure(DbCommand command) throws Exception {

        int result = 0;
        try {

            if (conn == null || (conn != null && conn.isClosed()))
                conn = GetConnection();

            CallableStatement stmt = conn.prepareCall("{call " + command.getCommandText() + "}");
            for (int i = 0; i < command.getParameters().size(); i++) {
                DbParameter parameter = command.getParameters().get(i);
                stmt.setObject(i + 1, parameter.getParameterValue(), parameter.getParameterType(), parameter.getParameterScale());

            }

            printLogBD(null, INICIO_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
            printLogBD(PRINT_COMMAND, getCommandAsString(command) + TEXTO_UUID + uuid);

            result = stmt.executeUpdate();

            closeConnectionAutoCommit();

        } catch (Exception e) {
            SvcLogger.error("Error executeNonQueryStoredProcedure: " + e.getMessage());
            closeConnectionExecution();

            throw e;
        }

        finally {

            printLogBD(null, FIN_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);

        }
        return result;

    }

    /**
     * Ejcuta un comando tipo texto que no sea de consulta
     * 
     * @param command
     * @return
     * @throws Exception
     */

    private int executeNonQueryCommandText(DbCommand command) throws Exception {

        int result;
        try {

            if (conn == null || (conn != null && conn.isClosed()))

                conn = GetConnection();

            PreparedStatement stmt = conn.prepareStatement(command.getCommandText());

            for (int i = 0; i < command.getParameters().size(); i++) {
                DbParameter parameter = command.getParameters().get(i);
                stmt.setObject(i + 1, parameter.getParameterValue());

            }

            printLogBD(null, INICIO_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
            printLogBD(PRINT_COMMAND, getCommandAsString(command) + TEXTO_UUID + uuid);
            result = stmt.executeUpdate();

            closeConnectionAutoCommit();

        } catch (Exception e) {
            SvcLogger.error("Error executeNonQueryCommandText: " + e.getMessage());

            closeConnectionExecution();

            throw e;
        } finally {
            printLogBD(null, FIN_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
        }
        return result;
    }

    /**
     * Ejcuta un insert retornando las llaves generadas por el motor.
     * 
     * @param command
     * @param cantidadLlaves
     *            indica la cantidad de llaves que se espera sean generadas por el motor de base de datos para ser consultadas y retornadas.
     * @return colecciÃ³n de Object[], en la cual cada arreglo de objetos tiene tantas posciones como se haya indicado en el parÃ¡metro
     *         cantidad de llaves y en cada posiciÃ³n contiene el valor retornado por el motor para dicha llave. como resultado del insert.
     *         ColecciÃ³n vacÃ­a en caso que no se haya podido realizar la inserciÃ³n.
     * @throws Exception
     *             cuando se presenta algÃºn error al realizar la operaciÃ³n
     */

    private Collection<Object> executeInsertCommandText(DbCommand command, int cantidadLlaves) throws Exception {
        Collection<Object> llavesGeneradas = new ArrayList<Object>();
        try {

            if (conn == null || (conn != null && conn.isClosed()))

                conn = GetConnection();

            PreparedStatement stmt = conn.prepareStatement(command.getCommandText(), PreparedStatement.RETURN_GENERATED_KEYS);

            for (int i = 0; i < command.getParameters().size(); i++) {
                DbParameter parameter = command.getParameters().get(i);
                stmt.setObject(i + 1, parameter.getParameterValue());

            }

            printLogBD(null, INICIO_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
            printLogBD(PRINT_COMMAND, getCommandAsString(command) + TEXTO_UUID + uuid);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            while (rs.next()) {
                Object[] llaves = new Object[cantidadLlaves];
                for (int llaveNo = 0; llaveNo < cantidadLlaves; llaveNo++) {
                    llaves[llaveNo] = rs.getObject(llaveNo + 1);
                }
                llavesGeneradas.add(llaves);
            }

            closeConnectionAutoCommit();

        } catch (Exception e) {
            SvcLogger.error(ERROR_EJECUTANDO_INSERT + e.getMessage());

            closeConnectionExecution();

            throw e;
        } finally {
            printLogBD(null, FIN_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
        }
        return llavesGeneradas;
    }

    /**
     * Ejecuta un comando que no retorna un resultado
     * 
     * @param command
     * @return
     * @throws Exception
     */

    public void execute(DbCommand command) throws Exception {

        if (command.getCommandType() == CommandType.StoredProcedure)
            executeStoredProcedure(command);

    }

    /**
     * Ejecuta un procedimiento almacenado que no devuelve respuesta
     * 
     * @param command
     * @return
     * @throws Exception
     */
    private void executeStoredProcedure(DbCommand command) throws Exception {

        try {
            if (conn == null || (conn != null && conn.isClosed()))
                conn = GetConnection();
            CallableStatement stmt = conn.prepareCall("{call " + command.getCommandText() + "}");
            for (int i = 0; i < command.getParameters().size(); i++) {
                DbParameter parameter = command.getParameters().get(i);
                stmt.setObject(i + 1, parameter.getParameterValue(), parameter.getParameterType(), parameter.getParameterScale());

            }
            printLogBD(null, INICIO_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
            printLogBD(PRINT_COMMAND, getCommandAsString(command) + TEXTO_UUID + uuid);

            stmt.execute();

            closeConnectionAutoCommit();

        } catch (Exception e) {
            SvcLogger.error(ERROR_EJECUTANDO_PROCEDIMIENTO2 + e.getMessage());

            closeConnectionExecution();

            throw e;
        }

        finally {
            printLogBD(null, FIN_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
        }

    }

    /**
     * Permite obtener la conexiÃ³n dependiendo de si se ha indicado que se maneja datasource o si se debe obtener la informaciÃ³n del archivo
     * DatabaseConfiguration.xml.
     * 
     * @return conexiÃ³n creada.
     */
    private Connection GetConnection() {

        if (this.sharedConnection == true)
            return conn;

        // Obtiene informacion de la conexion
        boolean useDataSource = false;
        try {
            useDataSource = Boolean.parseBoolean(ResourceUtil.getResourceValue(Constantes._RESOURCE_NAME, USE_DATASOURCE));
        } catch (Exception e1) {
            useDataSource = true;
        }

        Connection connection = null;
        // Busca la conexion en el datasource
        try {

            if (useDataSource) {
                InitialContext cxt = new InitialContext();

                DataSource ds = (DataSource) cxt.lookup(jndiName);
                if (ds == null)
                    throw new Exception("Data source not found!");

                if (!createConnectionFromDataSourceData)
                    connection = ds.getConnection();
            }

        } catch (SQLException e) {
            SvcLogger.error(e.getMessage());
        } catch (Exception e) {
            SvcLogger.error(e.getMessage());
        }
        if (connection == null) {
            try {

                if (getDriverClass() == null || getConnectionURL() == null || getUser() == null || getPassword() == null)
                    configConnection();
                Class.forName(getDriverClass());

                connection = DriverManager.getConnection(getConnectionURL(), getUser(), getPassword());
            } catch (ClassNotFoundException e) {

                SvcLogger.error(e.getMessage());
            } catch (SQLException e) {

                SvcLogger.error(e.getMessage());
            }
        }
        return connection;
    }

    /**
     * Obtiene los tipos de datos validos en SQL
     * 
     * @param parameterType
     * @return
     */
    private int GetSqlType(DbType parameterType) {

        switch (parameterType) {
        case BIT:
            return Types.BIT;
            // -7
        case TINYINT:
            return Types.TINYINT;
            // -6
        case SMALLINT:
            return Types.SMALLINT;
            // 5
        case INTEGER:
            return Types.INTEGER;
            // 4
        case BIGINT:
            return Types.BIGINT;
            // -5
        case FLOAT:
            return Types.FLOAT;
            // 6
        case REAL:
            return Types.REAL;
            // 7
        case DOUBLE:
            return Types.DOUBLE;
            // 8
        case NUMERIC:
            return Types.NUMERIC;
            // 2
        case DECIMAL:
            return Types.DECIMAL;
            // 3
        case CHAR:
            return Types.CHAR;
            // 1
        case VARCHAR:
            return Types.VARCHAR;
            // 12
        case LONGVARCHAR:
            return Types.LONGVARCHAR;
            // -1
        case DATE:
            return Types.DATE;
            // 91
        case TIME:
            return Types.TIME;
            // 92
        case TIMESTAMP:
            return Types.TIMESTAMP;
            // 93
        case BINARY:
            return Types.BINARY;
            // -2
        case VARBINARY:
            return Types.VARBINARY;
            // -3
        case LONGVARBINARY:
            return Types.LONGVARBINARY;
            // -4
        case NULL:
            return Types.NULL;
            // 0
        case OTHER:
            return Types.OTHER;
            // 1111
        case JAVA_OBJECT:
            return Types.JAVA_OBJECT;
            // 2000
        case DISTINCT:
            return Types.DISTINCT;
            // 2001
        case STRUCT:
            return Types.STRUCT;
            // 2002
        case ARRAY:
            return Types.ARRAY;
            // 2003
        case BLOB:
            return Types.BLOB;
            // 2004
        case CLOB:
            return Types.CLOB;
            // 2005
        case REF:
            return Types.REF;
            // 2006
        case DATALINK:
            return Types.DATALINK;
            // 70
        case BOOLEAN:
            return Types.BOOLEAN;
            // 16
        case ROWID:
            return Types.ROWID;
            // -8
        case NCHAR:
            return Types.NCHAR;
            // -15
        case NVARCHAR:
            return Types.NVARCHAR;
            // -9
        case LONGNVARCHAR:
            return Types.LONGNVARCHAR;
            // -16
        case NCLOB:
            return Types.NCLOB;
            // 2011
        case SQLXML:
            return Types.SQLXML;
            // 2009
        }
        return 0;
    }

    /**
     * Permite obtener la conexiÃ³n.
     * 
     * @return conexiÃ³n con la que se estÃ¡ trabajando.
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * Permite establecer la conexiÃ³n.
     * 
     * @param conn
     *            conexiÃ³n a establecer
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    /**
     * Permite obtener una conexiÃ³n nueva a la base de datos.
     * 
     * @return conexiÃ³n creada.
     * @throws SQLException
     *             cuando se presenta algÃºn error.
     */
    public Connection createNewConnection() throws SQLException {
        if (conn != null && conn.isClosed())
            conn = null;
        return GetConnection();
    }

    /**
     * Cierra la conexion si no es compartida
     * 
     * @param conn
     * @throws SQLException
     */
    private void closeConnectionAutoCommit() throws SQLException {
        if (this.sharedConnection == false) {
            if (conn.getAutoCommit())
                conn.close();
        }

    }

    /**
     * Cierra la conexion si no es compartida. Solo en caso de error
     * 
     * @param conn
     * @throws SQLException
     */
    private void closeConnectionExecution() throws SQLException {
        if (this.sharedConnection == false) {
            if (conn != null && conn.getAutoCommit() && !conn.isClosed())
                conn.close();
        }

    }

    /**
     * Permite obtener el identificador Ãºnico del proceso
     * 
     * @return identificador Ãºnico
     */
    public String getUUID() {
        return uuid;
    }

    /**
     * Permite establecer el identificador Ãºnico del proceso.
     * 
     * @param uUID
     *            identificador a establecer
     */
    public void setUUID(String uUID) {
        uuid = uUID;
    }

}
