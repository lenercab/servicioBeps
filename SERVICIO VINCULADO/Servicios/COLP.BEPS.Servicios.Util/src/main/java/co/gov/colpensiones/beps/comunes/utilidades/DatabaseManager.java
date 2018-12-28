package co.gov.colpensiones.beps.comunes.utilidades;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import co.gov.colpensiones.beps.comunes.databaseconfiguration.DatabaseConfiguration;
import co.gov.colpensiones.beps.comunes.databaseconfiguration.MetadataParameter;
import co.gov.colpensiones.beps.comunes.databaseconfiguration.MetadataQuery;
import co.gov.colpensiones.beps.comunes.enumeraciones.TipoConexionBaseDatosEnum;
import co.gov.colpensiones.beps.dal.utilidades.CommandType;
import co.gov.colpensiones.beps.dal.utilidades.DataColumn;
import co.gov.colpensiones.beps.dal.utilidades.DataRow;
import co.gov.colpensiones.beps.dal.utilidades.DataStoredProcedure;
import co.gov.colpensiones.beps.dal.utilidades.DataTable;
import co.gov.colpensiones.beps.dal.utilidades.DbCommand;
import co.gov.colpensiones.beps.dal.utilidades.DbParameter;
import co.gov.colpensiones.beps.dal.utilidades.DbType;
import co.gov.colpensiones.beps.dal.utilidades.ParameterDirection;

/**
 * Clase encargada de crear una conexión a Base de Datos en base al jndi que corresponda. Esta clase contiene métodos
 * utilitarios para operaciones CRUD sobre la base de datos.
 * 
 * Caso de Uso: Transversal
 * 
 * @author Yenny Ñustez Arévalo <ynustez@heinsohn.com.co>
 * 
 */
public class DatabaseManager {

	/** Driver de conexión a la BD */
	private String driverClass;

	/** URL de conexión a la BD */
	private String connectionURL;

	/** Usuario de conexión a la BD */
	private String user;

	/** Password de conexión a la BD */
	private String password;

	/** Objeto para el mapeo de Queries */
	private DatabaseConfiguration databaseConfiguration = null;

	/** Objeto para el mapeo de la metadata de los Queries */
	private MetadataQuery selectedMetadataQuery = null;

	/** Flags para manejo de conexiones */
	private boolean createConnectionFromDataSourceData = false;
	private boolean sharedConnection = false;

	/** Objeto para la conexión a Base de Datos */
	private Connection conn = null;

	/** jndi de conexión a la BD */
	private String jndiName = null;
	
	/** Atributo para mantener el id de transacción generado.*/
	private String uuid = "";

	/** Mapa que contiene las sentencias SQL que se deben de ejecutar en batch. */
	private HashMap<String, PreparedStatement> ejecucionesBatch;

	/** Definición de constantes utilizadas */
	private static final String NOMBRE_ARCHIVO_DATABASE_CONFIGURATION = "DatabaseConfiguration.xml";
	
	/** Constante para JNDI de SQL SERVER*/
	private static final String JNDI_SQLSERVER_NAME = "jndi-sqlserver-name";
	
	/** Constante para JNDI de SQL SERVER*/
	private static final String JNDI_SQLSERVER_ALT_NAME = "jndi-sqlserver-alt-name";
	
	/** Constante para JNDI de AS400*/
	private static final String JNDI_AS400_NAME = "jndi-as400-name";
	
	/** Constante para JNDI de AS400*/
    private static final String JNDI_AS400_CAMBIO_ESTADO_NAME = "jndi-as400-cambioEstado-name";
	
	/** Constante para indicar error al adicionar parámetros a la consulta.*/
	private static final String NO_SE_PUEDE_ADICIONAR_PARAMETRO = "Cannot add a xml parameter because it is not an Xml Command Type";
	
	/** Mensaje para indicar que un parámetro no existe en la consulta a ejecutar.*/
	private static final String PARAMETRO_NO_EXISTE = "Parameter {0} does not exists in the xml configuration for queryName {1}";
	
	/** Mensaje para traza de ejecución de consulta.*/
	private static final String QUERY_NAME = "QueryName '";
	
	/** Constante para indicar que un objeto no existe.*/
	private static final String DOES_NOT_EXISTS = "' does not exists";
	
	/** Mensaje para traza de ejecución de consulta.*/
	private static final String TEXTO_UUID = " UUID: ";
	
	/** Mensaje para traza de ejecución de consulta.*/
	private static final String USE_DATASOURCE = "useDataSource";
	
	/** Mensaje para traza de ejecución de consulta.*/
	private static final String PRINT_LOG_DB = "printLogDB";
	
	/** Constante para el Inicio de una ejecución de una consulta.*/
	private static final String INICIO_EJECUCION_QUERY = "Inicio  Ejecución Query : ";
	
	/** Mensaje para traza de ejecución de consulta.*/
	private static final String PRINT_COMMAND = "printCommand";
	
	/** Constante de Error para la ejecución de una instrucción SQL.*/
	private static final String ERROR_EJECUTANDO_PROCEDIMIENTO = "Error ExecuteDataTableStoredProcedure: ";
	
	/** Constante de Error para la ejecución de una instrucción SQL.*/
	private static final String ERROR_EJECUTANDO_SP_GENERICO = "Error ExecuteStoredProcedure: ";
	
	/** Constante para el Fin de una ejecución de una consulta.*/
	private static final String FIN_EJECUCION_QUERY = "Fin  Ejecución Query : ";
	
	/** Constante de Error para la ejecución de una instrucción SQL.*/
	private static final String ERROR_EXECUTE_DATA_TABLE = "Error ExecuteDataTableCommandText: ";
	
	/** Constante de Error para la ejecución de una instrucción SQL.*/
	private static final String ERROR_EJECUTANDO_INSERT = "Error executeInsertCommandText: ";
	
	/**Constante de Error para la ejecución de una instrucción SQL.*/
	private static final String ERROR_EJECUTANDO_PROCEDIMIENTO2 = "Error executeStoredProcedure: ";

	/**
	 * Este constructor busca el jndi en el archivo resources.
	 */
	public DatabaseManager() {
		try {
			/* Obtiene el jdni */
			jndiName = ResourceUtil.getResourceValue(Constantes._RESOURCE_NAME, JNDI_SQLSERVER_NAME);
			ejecucionesBatch = new HashMap<String, PreparedStatement>();
		} catch (Exception e) {
			SvcLogger.error(e.getLocalizedMessage());
		}
	}

	/**
	 * Este constructor recibe la conexion compartida
	 * 
	 * @param connection
	 *            Conexión a base de datos
	 */
	public DatabaseManager(Connection connection) {
		this.conn = connection;
		this.sharedConnection = true;
		ejecucionesBatch = new HashMap<String, PreparedStatement>();
	}

	/**
	 * Este constructor recibe el nombre jndi de conexion
	 * 
	 * @param jndiName
	 *            jndi de conexión
	 */
	public DatabaseManager(String jndiName) {
		this.jndiName = jndiName;
		ejecucionesBatch = new HashMap<String, PreparedStatement>();
	}

	/**
	 * Este constructor recibe el tipo de conexión a realizar y de esta manera obtiene el jndi correspondiente
	 * 
	 * @param tipoConexion Enumeración que define el tipo de conexión
	 */
	public DatabaseManager(TipoConexionBaseDatosEnum tipoConexion) {
		try {
			/* Se obtiene el jndi correspondiente al tipo de conexión */
			if (TipoConexionBaseDatosEnum.SQL_SERVER.equals(tipoConexion)) {
				jndiName = ResourceUtil.getResourceValue(Constantes._RESOURCE_NAME, JNDI_SQLSERVER_NAME);
			} else if (TipoConexionBaseDatosEnum.SQL_SERVER_ALT.equals(tipoConexion)) {
				jndiName = ResourceUtil.getResourceValue(Constantes._RESOURCE_NAME, JNDI_SQLSERVER_ALT_NAME);
			} else if (TipoConexionBaseDatosEnum.AS400.equals(tipoConexion)) {
				jndiName = ResourceUtil.getResourceValue(Constantes._RESOURCE_NAME, JNDI_AS400_NAME);
			}else if (TipoConexionBaseDatosEnum.AS400_CAMBIO_ESTADO.equals(tipoConexion)) {
                jndiName = ResourceUtil.getResourceValue(Constantes._RESOURCE_NAME, JNDI_AS400_CAMBIO_ESTADO_NAME);
            }
			ejecucionesBatch = new HashMap<String, PreparedStatement>();
		} catch (Exception e) {
			SvcLogger.error(e.getLocalizedMessage());
		}
	}

	/**
	 * Constructor que obtiene el jndi para crear la conexión y el indicador de si debe ser creada desde un datasource.
	 * 
	 * @param jndiName
	 * @param createConnectionFromDataSourceData
	 */
	public DatabaseManager(String jndiName, boolean createConnectionFromDataSourceData) {
		this.jndiName = jndiName;
		this.createConnectionFromDataSourceData = createConnectionFromDataSourceData;
		ejecucionesBatch = new HashMap<String, PreparedStatement>();
	}

	/**
	 * Constructor que recibe todos los parámetros para creación de la conexión
	 * 
	 * @param driverClass
	 *            Driver de conexión
	 * @param connectionURL
	 *            URL de conexión
	 * @param user
	 *            Usuario de base de datos
	 * @param password
	 *            Contraseña de usuario
	 */
	public DatabaseManager(String driverClass, String connectionURL, String user, String password) {
		/* Asigna los valores de la conexion */
		super();
		this.driverClass = driverClass;
		this.connectionURL = connectionURL;
		this.user = user;
		this.password = password;
		ejecucionesBatch = new HashMap<String, PreparedStatement>();
	}

	/**
	 * Permite configurar la conexión con base en la información que se encuentra parametrizada en el archivo
	 * DatabaseConfiguration.xml
	 */
	private void configConnection() {
		try {
			String xml = Serializer.leerArchivo(NOMBRE_ARCHIVO_DATABASE_CONFIGURATION);

			/* Obtiene la configuracion de la base de datos */
			setDatabaseConfiguration((DatabaseConfiguration) Serializer.deserializar(xml, DatabaseConfiguration.class));

			/* Configura los valores de la conexion */
			setDriverClass(getDatabaseConfiguration().getDriverClass());
			setConnectionURL(getDatabaseConfiguration().getConnectionURL());
			setUser(getDatabaseConfiguration().getUser());
			setPassword(getDatabaseConfiguration().getPassword());
		} catch (Exception exc) {
			SvcLogger.error(exc.getMessage());
		}
	}

	/**
	 * Lee un comando XML desde el archivo DatabaseConfiguration y lo mapea dentro de un objeto DbCommand
	 * 
	 * @param queryName
	 *            Nombre del query a ejecutar
	 * @return DbCommand Objeto con la estructura del query a ejecutar sobre la BD
	 * @throws Exception
	 */
	public DbCommand GetXmlCommand(String queryName) throws Exception {
		configConnection();

		/* Busca el query en el XML */
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
		/* Si no encuentra la consulta se genera excepcion indicando el query no encontrado */
		if (commandText.trim().length() == 0)
			throw new Exception(QUERY_NAME + queryName + DOES_NOT_EXISTS);

		return new DbCommand(commandType, commandText, queryName);
	}

	/**
	 * Lee un comando SQL tipo texto y lo mapea dentro de un objeto DbCommand
	 * 
	 * @param nombre
	 *            de la consulta (textCommand)
	 * @return DbCommand Objeto con la estructura del query a ejecutar sobre la BD
	 */
	public DbCommand GetTextCommand(String textCommand) {
		return new DbCommand(CommandType.Text, textCommand);
	}

	/**
	 * Adiciona un parametro al comando seleccionado
	 * 
	 * @param command
	 *            Objeto que representa la sentencia SQL dentro del archivo DatabaseConfiguration.xml
	 * @param parameterName
	 *            Nombre del parámetro
	 * @param parameterType
	 *            Tipo parámetro
	 * @param parameterScale
	 *            Longitud (escala) del parámetro
	 * @param parameterValue
	 *            Valor a asignar al parámetro
	 */
	public void AddInParameter(DbCommand command, String parameterName, DbType parameterType, int parameterScale, Object parameterValue) {
		command.addInParameter(parameterName, GetSqlType(parameterType), parameterScale, parameterValue);

	}

	/**
	 * Adiciona un parametro XML al comando seleccionado
	 * 
	 * @param command
	 *            Objeto que representa la sentencia SQL dentro del archivo DatabaseConfiguration.xml
	 * @param parameterName
	 *            Nombre del parámetro
	 * @param parameterValue
	 *            Nombre del parámetro
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
			throw new Exception(StringUtil.Format(PARAMETRO_NO_EXISTE, parameterName, getSelectedMetadataQuery().getQueryName()));

		AddInParameter(command, parameterName, DbType.valueOf(parameterType), parameterScale, parameterValue);

	}

	/**
	 * Adiciona un parametro de salida al comando seleccionado
	 * 
	 * @param command
	 *            Objeto que representa la sentencia SQL dentro del archivo DatabaseConfiguration.xml
	 * @param parameterName
	 *            Nombre del parámetro
	 * @param parameterType
	 *            Tipo parámetro
	 * @param parameterScale
	 *            Longitud (escala) del parámetro
	 */

	public void AddOutParameter(DbCommand command, String parameterName, DbType parameterType, int parameterScale) {
		command.addOutParameter(parameterName, GetSqlType(parameterType), parameterScale);
	}

	/**
	 * Obtene el valor de un parametro
	 * 
	 * @param command
	 *            Objeto que representa la sentencia SQL dentro del archivo DatabaseConfiguration.xml
	 * @param parameterName
	 *            Nombre del parámetro
	 * @return Objeto con el valor asociado al parámetro ingresado
	 */

	public Object getParameterValue(DbCommand command, String parameterName) {
		return command.getParameterValue(parameterName);
	}

	/**
	 * Ejecuta un Query para obtener una estructura DataTable desde una consulta
	 * 
	 * @param command
	 *            Objeto que representa la sentencia SQL dentro del archivo DatabaseConfiguration.xml
	 * @return Objeto de tipo DataTable con el resultado de la consulta ingresada por parámetro
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
	 * Imprime en el log de acuerdo con lo que se haya establecido en el archivo de recursos para la propiedad
	 * printLogDB Si se ha parametrizado true en la propiedad, se imprimen el título y mensaje que ingresan como
	 * parámetro, en caso contrario no se imprimen.
	 * 
	 * @param title
	 *            título del log a imprimir
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
	 *            Objeto que representa la sentencia SQL dentro del archivo DatabaseConfiguration.xml
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
	 * Ejecuta un procedimiento almacenado para obtener un DatatTable
	 * 
	 * @param command
	 *            Objeto que representa la sentencia SQL dentro del archivo DatabaseConfiguration.xml
	 * @return Objeto de tipo DataTable con el resultado de la ejecución del procedimiento almacenado ingresado por
	 *         parámetro
	 * @throws Exception
	 */
	private DataTable ExecuteDataTableStoredProcedure(DbCommand command) throws Exception {
		DataTable table = new DataTable();
		List<DbParameter> parametrosSalida = new ArrayList<DbParameter>();

		try {
			if (conn == null || (conn != null && conn.isClosed()))
				conn = GetConnection();

			String callStoredProcedure = "{ call " + command.getCommandText() + " }";
			printLogBD(null, INICIO_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
			printLogBD(PRINT_COMMAND, getCommandAsString(command) + TEXTO_UUID + uuid);

			CallableStatement stmt = conn.prepareCall(callStoredProcedure);

			/* Adiciona los parametros al Callable Statement */
			for (DbParameter parameter : command.getParameters()) {
                if (parameter.getParamDirection() == ParameterDirection.OUT){
                    stmt.registerOutParameter(parameter.getParameterName(), parameter.getParameterType());
                } else { 
                    stmt.setObject(parameter.getParameterName(), parameter.getParameterValue(), parameter.getParameterType());
                }  
			}

			ResultSet rs = stmt.executeQuery();
			/* Lee el ResultSet y lo convierte a DataTable */
			table = resultSetToDataTable(rs);
			rs.close();

			for (DbParameter parameter : command.getParameters()) {
				if (parameter.getParamDirection() == ParameterDirection.OUT) {
					parameter.setParameterValue(stmt.getObject(parameter.getParameterName()));
					parametrosSalida.add(parameter);
				}
			}
			stmt.close();
			closeConnectionAutoCommit();

		} catch (Exception e) {
			SvcLogger.error(ERROR_EJECUTANDO_PROCEDIMIENTO + e.getMessage());
			e.printStackTrace();
			closeConnectionExecution();
			throw e;
		} finally {
			printLogBD(null, FIN_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
		}

		return table;
	}

	/**
	 * Método que ejecuta un procedimiento almacenado, obteniendo los parámetros de salida (si los tiene definidos) y un
	 * objeto de tipo ResultSet (en caso de que el procedimiento retorne un objeto de este tipo).
	 * 
	 * @param command
	 *            Objeto que representa la sentencia SQL dentro del archivo DatabaseConfiguration.xml
	 * @return DataStoredProcedure : objeto que tiene dos atributos; un objeto de tipo DataTable en el cual se incluyen
	 *         los resultados de un objeto de tipo ResultSet. Adicionalmente contiene una lista en la cual se incluyen
	 *         los parámetros de tipo OUT configurados
	 * @throws Exception
	 */

	public DataStoredProcedure executeGenericStoredProcedure(DbCommand command) throws Exception {

		DataStoredProcedure data = new DataStoredProcedure();

		try {
			if (conn == null || (conn != null && conn.isClosed())) {
				conn = GetConnection();
			}

			String callStoredProcedure = "{ call " + command.getCommandText() + " }";

			printLogBD(null, INICIO_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
			printLogBD(PRINT_COMMAND, getCommandAsString(command) + TEXTO_UUID + uuid);

			CallableStatement stmt = conn.prepareCall(callStoredProcedure);

			/* Adiciona los parametros de entrada y salida al Callable Statement */
			for (DbParameter parameter : command.getParameters()) {
                if (parameter.getParamDirection() == ParameterDirection.OUT){
                    stmt.registerOutParameter(parameter.getParameterName(), parameter.getParameterType());
                } else { 
                    stmt.setObject(parameter.getParameterName(), parameter.getParameterValue(), parameter.getParameterType());
                }  
			}

			/* Se ejecuta el SP */
			stmt.execute();

			/* Se verifica si el SP retorna un objeto de tipo ResultSet */
			ResultSet rs = stmt.getResultSet();
			if (rs != null) {
				/* Lee el ResultSet y lo convierte a DataTable */
				DataTable table = resultSetToDataTable(rs);
				data.setTablaResultSet(table);
				rs.close();
			}

			/* Obtiene el valor de los parametros de salida */
			List<DbParameter> parametrosSalida = new ArrayList<DbParameter>();
			for (DbParameter parameter : command.getParameters()) {
				if (parameter.getParamDirection() == ParameterDirection.OUT) {
					parameter.setParameterValue(stmt.getObject(parameter.getParameterName()));
					parametrosSalida.add(parameter);
				}
			}
			data.setParametrosSalida(parametrosSalida);
			stmt.close();
			closeConnectionAutoCommit();

		} catch (Exception e) {
			SvcLogger.error(ERROR_EJECUTANDO_SP_GENERICO + e.getMessage());
			e.printStackTrace();
			closeConnectionExecution();
			throw e;
		} finally {
			printLogBD(null, FIN_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
		}

		return data;
	}

	/**
	 * Método que ejecuta un procedimiento almacenado, obteniendo los parámetros de salida (si los tiene definidos) y
	 * una lista de objetos de tipo ResultSet (en caso de que el procedimiento retorne uno o más objetos de este tipo).
	 * 
	 * @param command
	 *            Objeto que representa la sentencia SQL dentro del archivo DatabaseConfiguration.xml
	 * @return DataStoredProcedure : objeto que tiene dos atributos; un lista de objetos de tipo DataTable en la cual se
	 *         incluyen los resultados de uno o más objetos de tipo ResultSet. Adicionalmente contiene una lista en la
	 *         cual se incluyen los parámetros de tipo OUT configurados
	 * @throws Exception
	 */

	public DataStoredProcedure executeListResultSet(DbCommand command) throws Exception {

		DataStoredProcedure data = new DataStoredProcedure();

		try {
			if (conn == null || (conn != null && conn.isClosed())) {
				conn = GetConnection();
			}

			String callStoredProcedure = "{ call " + command.getCommandText() + " }";
			printLogBD(null, INICIO_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
			printLogBD(PRINT_COMMAND, getCommandAsString(command) + TEXTO_UUID + uuid);

			CallableStatement stmt = conn.prepareCall(callStoredProcedure);

			/* Adiciona los parametros de entrada y salida al Callable Statement */ 
			for (DbParameter parameter : command.getParameters()) {
				if (parameter.getParamDirection() == ParameterDirection.OUT){
					stmt.registerOutParameter(parameter.getParameterName(), parameter.getParameterType());
				} else { 
				    stmt.setObject(parameter.getParameterName(), parameter.getParameterValue(), parameter.getParameterType());
				}    
			}

			/* Se ejecuta el SP */
			stmt.execute();

			/* Se verifica si el SP retorna uno o más objetos de tipo ResultSet */
			List<DataTable> listaResultSet = new ArrayList<DataTable>();
			do {
				ResultSet rs = stmt.getResultSet();
				if (rs != null) {
					DataTable table = resultSetToDataTable(rs);
					listaResultSet.add(table);
					rs.close();
				}
			} while (stmt.getMoreResults());

			data.setResultSetList(listaResultSet);

			/* Obtiene el valor de los parametros de salida */
			List<DbParameter> parametrosSalida = new ArrayList<DbParameter>();
			for (DbParameter parameter : command.getParameters()) {
				if (parameter.getParamDirection() == ParameterDirection.OUT) {
					parameter.setParameterValue(stmt.getObject(parameter.getParameterName()));
					parametrosSalida.add(parameter);
				}
			}
			data.setParametrosSalida(parametrosSalida);
			stmt.close();
			closeConnectionAutoCommit();

		} catch (Exception e) {
			SvcLogger.error(ERROR_EJECUTANDO_SP_GENERICO + e.getMessage());
			e.printStackTrace();
			closeConnectionExecution();
			throw e;
		} finally {
			printLogBD(null, FIN_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
		}

		return data;
	}

	/**
	 * Ejecuta un commando tipo texto para obtener un DatatTable
	 * 
	 * @param command
	 *            Objeto que representa la sentencia SQL dentro del archivo DatabaseConfiguration.xml
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public DataTable ExecuteDataTableCommandText(DbCommand command) throws ClassNotFoundException, SQLException {

		DataTable table = new DataTable();

		try {
			if (conn == null || (conn != null && conn.isClosed()))
				conn = GetConnection();

			PreparedStatement stmt = conn.prepareStatement(command.getCommandText());

			/* Adiciona los parametros al PreparedStatement Statement */
			for (int i = 0; i < command.getParameters().size(); i++) {
				DbParameter parameter = command.getParameters().get(i);
				stmt.setObject(i + 1, parameter.getParameterValue());
			}

			printLogBD(null, INICIO_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
			printLogBD(PRINT_COMMAND, getCommandAsString(command) + TEXTO_UUID + uuid);

			ResultSet rs = stmt.executeQuery();

			/* Lee el ResultSet y lo convierte a DataTable */
			table = resultSetToDataTable(rs);

			rs.close();
			stmt.close();
			closeConnectionAutoCommit();

		} catch (SQLException e) {
			SvcLogger.error(ERROR_EXECUTE_DATA_TABLE + e.getMessage());
			closeConnectionExecution();
			throw e;
		} finally {
			printLogBD(null, FIN_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
		}

		return table;
	}

	/**
	 * Mapea los datos de un objeto de tipo ResultSet a otro de tipo DataTable
	 * 
	 * @param ResultSet
	 * @return DataTable con la información organizada en filas y columnas.
	 * @throws Exception
	 */
	private DataTable resultSetToDataTable(ResultSet resultSet) throws SQLException {
		DataTable table = new DataTable();
		try {
			if (resultSet != null) {
				/* Lee el ResultSet y lo convierte a DataTable*/
				ResultSetMetaData metaData = resultSet.getMetaData();

				if (metaData.getColumnCount() > 0) {
					table.setTableName(metaData.getTableName(1));
					for (int i = 1; i <= metaData.getColumnCount(); i++)
						table.addColumn(new DataColumn(metaData.getColumnLabel(i)));
				}

				while (resultSet.next()) {
					DataRow row = new DataRow();
					for (int i = 1; i <= metaData.getColumnCount(); i++) {
						if(resultSet.getObject(i) instanceof Clob){
							row.addColumn(new DataColumn(metaData.getColumnLabel(i), resultSet.getString(i)));
						}else{
							row.addColumn(new DataColumn(metaData.getColumnLabel(i), resultSet.getObject(i)));
						}
					}
					table.addRow(row);
				}
			}
		} catch (SQLException e) {
			SvcLogger.error("Error mapeando ResultSet a DataTable: " + e.getMessage());
			throw e;
		}
		return table;
	}

	/**
	 * Ejecuta un comando para obtener un ResultSet
	 * 
	 * @param command
	 *            Objeto que representa la sentencia SQL dentro del archivo DatabaseConfiguration.xml
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
	 *            Objeto que representa la sentencia SQL dentro del archivo DatabaseConfiguration.xml
	 * @return
	 * @throws Exception
	 */
	private ResultSet ExecuteResultSetStoredProcedure(DbCommand command) throws Exception {

		/* Prepara para abrir la conexion*/
		if (conn == null || (conn != null && conn.isClosed()))
			conn = GetConnection();

		/* Prepara el CallableStatement */
		String callStoredProcedure = "{ call " + command.getCommandText() + " }";
		CallableStatement stmt = conn.prepareCall(callStoredProcedure);

		/* Adiciona los parametros al Callable Statement */
		for (DbParameter parameter : command.getParameters()) {
            if (parameter.getParamDirection() == ParameterDirection.OUT){
                stmt.registerOutParameter(parameter.getParameterName(), parameter.getParameterType());
            } else { 
                stmt.setObject(parameter.getParameterName(), parameter.getParameterValue(), parameter.getParameterType());
            }  
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
	 *            Objeto que representa la sentencia SQL dentro del archivo DatabaseConfiguration.xml
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private ResultSet ExecuteResultSetCommandText(DbCommand command) throws ClassNotFoundException, SQLException {

		if (conn == null || (conn != null && conn.isClosed()))
			conn = GetConnection();

		PreparedStatement stmt = conn.prepareStatement(command.getCommandText());

		/* Adiciona los parametros al PreparedStatement Statement*/
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
	 * Cierra el resultset que ingresa como parámetro
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
	 * Inicia una transacción creando la conexión con el motor e indicando que no se debe realizar autocommit.
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
	 * Realiza commit a la conexión que se encuentra abierta.
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
	 * Realiza rollback sobre la conexión que se encuentra abierta.
	 * 
	 * @throws Exception
	 *             cuando se presenta algún error al realizar la operación.
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
	 *            Objeto que representa la sentencia SQL dentro del archivo DatabaseConfiguration.xml
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
	 * Ejecuta un insert en la base de datos, retornando como resultado una colección con los id de los objetos creados.
	 * 
	 * @param command
	 *            contiene la sentencia a ejecutar
	 * @param cantidadLlaves
	 *            cantidad de llaves que serían generadas automáticamente al ejecutar la sentencia.
	 * @return colección de Object[], en la cual cada arreglo de objetos tiene tantas posciones como se haya indicado en
	 *         el parámetro cantidad de llaves y en cada posición contiene el valor retornado por el motor para dicha
	 *         llave. como resultado del insert. Colección vacía en caso que no se haya podido realizar la inserción.
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
	 *            Objeto que representa la sentencia SQL dentro del archivo DatabaseConfiguration.xml
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
		} finally {
			printLogBD(null, FIN_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
		}
		return result;

	}

	/**
	 * Ejcuta un comando tipo texto que no sea de consulta
	 * 
	 * @param command
	 *            Objeto que representa la sentencia SQL dentro del archivo DatabaseConfiguration.xml
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
	 *            Objeto que representa la sentencia SQL dentro del archivo DatabaseConfiguration.xml
	 * @param cantidadLlaves
	 *            indica la cantidad de llaves que se espera sean generadas por el motor de base de datos para ser
	 *            consultadas y retornadas.
	 * @return colección de Object[], en la cual cada arreglo de objetos tiene tantas posciones como se haya indicado en
	 *         el parámetro cantidad de llaves y en cada posición contiene el valor retornado por el motor para dicha
	 *         llave. como resultado del insert. Colección vacía en caso que no se haya podido realizar la inserción.
	 * @throws Exception
	 *             cuando se presenta algún error al realizar la operación
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
	 *            Objeto que representa la sentencia SQL dentro del archivo DatabaseConfiguration.xml
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
	 *            Objeto que representa la sentencia SQL dentro del archivo DatabaseConfiguration.xml
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
				stmt.setObject(i + 1, parameter.getParameterValue(), parameter.getParameterType());

			}
			printLogBD(null, INICIO_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
			printLogBD(PRINT_COMMAND, getCommandAsString(command) + TEXTO_UUID + uuid);

			stmt.execute();
			closeConnectionAutoCommit();

		} catch (Exception e) {
			SvcLogger.error(ERROR_EJECUTANDO_PROCEDIMIENTO2 + e.getMessage());
			closeConnectionExecution();
			throw e;
		} finally {
			printLogBD(null, FIN_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
		}

	}

	/**
	 * Método que ejecuta en bach las sentencias
	 * 
	 * @param command
	 *            Objeto que representa la sentencia SQL dentro del archivo DatabaseConfiguration.xml
	 * @param cantValoresXreg
	 *            , cantidad de valores que tienes un values
	 * @throws Exception
	 *             , si se genera alguna excepción
	 */
	public int[] executeBach(DbCommand command, int cantValoresXreg) throws Exception {
		StringBuilder cadena = new StringBuilder();

		try {
			if (conn == null || (conn != null && conn.isClosed()))
				conn = GetConnection();

			PreparedStatement stmt = conn.prepareStatement(command.getCommandText());

			/* Adiciona los parametros al PreparedStatement Statement*/
			for (int i = 0, j = 0; i < command.getParameters().size(); i++) {
				if (j < cantValoresXreg) {
					DbParameter parameter = command.getParameters().get(i);
					stmt.setObject(j + 1, parameter.getParameterValue());
					j++;
				}

				if (j == cantValoresXreg) {
					stmt.addBatch();
					j = 0;
				}

			}

			printLogBD(null, INICIO_EJECUCION_QUERY + cadena + TEXTO_UUID + uuid);
			printLogBD(PRINT_COMMAND, cadena + TEXTO_UUID + uuid);

			int[] rs = stmt.executeBatch();

			stmt.clearBatch();
			stmt.close();
			closeConnectionAutoCommit();

			return rs;
		} catch (SQLException e) {
			SvcLogger.error(ERROR_EXECUTE_DATA_TABLE + e.getMessage());
			closeConnectionExecution();
			throw e;
		} finally {
			printLogBD(null, FIN_EJECUCION_QUERY + cadena + TEXTO_UUID + uuid);
		}

	}

	/**
	 * Método que permite la ejecución en batch de una sentencia SQL
	 * 
	 * @param command
	 *            Objeto que representa la sentencia SQL dentro del archivo DatabaseConfiguration.xml
	 * @throws SQLException
	 *             Excepción generada al ejecutar las sentencias SQL.
	 */
	public void ExecuteDataTableCommandTextBatch(DbCommand command) throws SQLException {

		try {

			if (conn == null || (conn != null && conn.isClosed()))
				conn = GetConnection();

			PreparedStatement stmt = conn.prepareStatement(command.getCommandText());

			/* Adiciona los parametros al PreparedStatement Statement */

			for (int i = 0; i < command.getParameters().size(); i++) {
				DbParameter parameter = command.getParameters().get(i);
				stmt.setObject(i + 1, parameter.getParameterValue());
			}

			printLogBD(null, INICIO_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
			printLogBD(PRINT_COMMAND, getCommandAsString(command) + TEXTO_UUID + uuid);

			stmt.addBatch();
			ejecucionesBatch.put(command.getCommandName(), stmt);
			command.clearParameter();

		} catch (SQLException e) {
			SvcLogger.error(ERROR_EXECUTE_DATA_TABLE + e.getMessage());
			closeConnectionExecution();
			throw e;
		} finally {
			printLogBD(null, FIN_EJECUCION_QUERY + command.getCommandName() + TEXTO_UUID + uuid);
		}
	}

	/**
	 * Método que permite realizar un flush a un conjunto de consultas que se crean en batch.
	 * 
	 * @throws SQLException
	 *             Exepción generada al ejecutar los sql en batch.
	 */
	public void flushBatch() throws SQLException {

		try {
			Iterator<String> it = ejecucionesBatch.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				PreparedStatement stmt = ejecucionesBatch.get(key);
				stmt.executeBatch();
				stmt.clearBatch();
				stmt.close();
			}
			closeConnectionAutoCommit();
		} catch (SQLException e) {
			SvcLogger.error(ERROR_EXECUTE_DATA_TABLE + e.getMessage());
			closeConnectionExecution();
			throw e;
		} finally {
			printLogBD(null, FIN_EJECUCION_QUERY + TEXTO_UUID + uuid);
		}
	}

	/**
	 * Permite obtener la conexión dependiendo de si se ha indicado que se maneja datasource o si se debe obtener la
	 * información del archivo DatabaseConfiguration.xml.
	 * 
	 * @return conexión creada.
	 */
	private Connection GetConnection() {
		if (this.sharedConnection == true && conn != null)
			return conn;

		/* Obtiene informacion de la conexion*/
		boolean useDataSource = false;
		try {
			useDataSource = Boolean.parseBoolean(ResourceUtil.getResourceValue(Constantes._RESOURCE_NAME, USE_DATASOURCE));
		} catch (Exception e1) {
			useDataSource = true;
		}

		Connection connection = null;
		/* Busca la conexion en el datasource*/
		try {
			if (useDataSource) {
				InitialContext cxt = new InitialContext();

				DataSource ds = (DataSource) cxt.lookup(jndiName);
				if (ds == null)
					throw new Exception("Datasource no encontrado " + jndiName);

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
	 * @return entero que representa el tipo de dato en SQL
	 */
	private int GetSqlType(DbType parameterType) {

		switch (parameterType) {
		case BIT:
			return Types.BIT;
			/* -7 */
		case TINYINT:
			return Types.TINYINT;
			/* -6 */
		case SMALLINT:
			return Types.SMALLINT;
			/* 5 */
		case INTEGER:
			return Types.INTEGER;
			/* 4 */
		case BIGINT:
			return Types.BIGINT;
			/* -5 */
		case FLOAT:
			return Types.FLOAT;
			/* 6 */
		case REAL:
			return Types.REAL;
			/*  7 */
		case DOUBLE:
			return Types.DOUBLE;
			/*  8 */
		case NUMERIC:
			return Types.NUMERIC;
			/*  2 */
		case DECIMAL:
			return Types.DECIMAL;
			/*  3 */
		case CHAR:
			return Types.CHAR;
			/*  1 */
		case VARCHAR:
			return Types.VARCHAR;
			/*  12 */
		case LONGVARCHAR:
			return Types.LONGVARCHAR;
			/*  -1 */
		case DATE:
			return Types.DATE;
			/*  91 */
		case TIME:
			return Types.TIME;
			/*  92 */
		case TIMESTAMP:
			return Types.TIMESTAMP;
			/*  93 */
		case BINARY:
			return Types.BINARY;
			/*  -2 */
		case VARBINARY:
			return Types.VARBINARY;
			/*  -3 */
		case LONGVARBINARY:
			return Types.LONGVARBINARY;
			/*  -4 */
		case NULL:
			return Types.NULL;
			/*  0 */
		case OTHER:
			return Types.OTHER;
			/*  1111 */
		case JAVA_OBJECT:
			return Types.JAVA_OBJECT;
			/*  2000 */
		case DISTINCT:
			return Types.DISTINCT;
			/*  2001 */
		case STRUCT:
			return Types.STRUCT;
			/*  2002 */
		case ARRAY:
			return Types.ARRAY;
			/*  2003 */
		case BLOB:
			return Types.BLOB;
			/*  2004 */
		case CLOB:
			return Types.CLOB;
			/*  2005 */
		case REF:
			return Types.REF;
			/*  2006 */
		case DATALINK:
			return Types.DATALINK;
			/*  70 */
		case BOOLEAN:
			return Types.BOOLEAN;
			/*  16 */
		case ROWID:
			return Types.ROWID;
			/*  -8 */
		case NCHAR:
			return Types.NCHAR;
			/*  -15 */
		case NVARCHAR:
			return Types.NVARCHAR;
			/*  -9 */
		case LONGNVARCHAR:
			return Types.LONGNVARCHAR;
			/*  -16 */
		case NCLOB:
			return Types.NCLOB;
			/*  2011 */
		case SQLXML:
			return Types.SQLXML;
			/*  2009 */
		}
		return 0;
	}

	/**
	 * Permite obtener una conexión nueva a la base de datos.
	 * 
	 * @return conexión creada.
	 * @throws SQLException
	 *             cuando se presenta algún error.
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
	 * Cierra la conexion si no es compartida.
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	public void closeConnection() throws SQLException {
		if (this.sharedConnection == false) {
			if (conn != null && !conn.isClosed())
				conn.close();
		}
	}

	/* GETTERS AND SETTERS */

	/**
	 * Devuelve el valor de driverClass.
	 * 
	 * @return El valor de driverClass
	 */
	public String getDriverClass() {
		return driverClass;
	}

	/**
	 * Establece el valor de driverClass.
	 * 
	 * @param driverClass
	 *            El valor por establecer para driverClass
	 */
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	/**
	 * Devuelve el valor de connectionURL.
	 * 
	 * @return El valor de connectionURL
	 */
	public String getConnectionURL() {
		return connectionURL;
	}

	/**
	 * Establece el valor de connectionURL.
	 * 
	 * @param connectionURL
	 *            El valor por establecer para connectionURL
	 */
	public void setConnectionURL(String connectionURL) {
		this.connectionURL = connectionURL;
	}

	/**
	 * Devuelve el valor de user.
	 * 
	 * @return El valor de user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Establece el valor de user.
	 * 
	 * @param user
	 *            El valor por establecer para user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Devuelve el valor de password.
	 * 
	 * @return El valor de password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Establece el valor de password.
	 * 
	 * @param password
	 *            El valor por establecer para password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Devuelve el valor de databaseConfiguration.
	 * 
	 * @return El valor de databaseConfiguration
	 */
	public DatabaseConfiguration getDatabaseConfiguration() {
		return databaseConfiguration;
	}

	/**
	 * Establece el valor de databaseConfiguration.
	 * 
	 * @param databaseConfiguration
	 *            El valor por establecer para databaseConfiguration
	 */
	public void setDatabaseConfiguration(DatabaseConfiguration databaseConfiguration) {
		this.databaseConfiguration = databaseConfiguration;
	}

	/**
	 * Devuelve el valor de selectedMetadataQuery.
	 * 
	 * @return El valor de selectedMetadataQuery
	 */
	public MetadataQuery getSelectedMetadataQuery() {
		return selectedMetadataQuery;
	}

	/**
	 * Establece el valor de selectedMetadataQuery.
	 * 
	 * @param selectedMetadataQuery
	 *            El valor por establecer para selectedMetadataQuery
	 */
	public void setSelectedMetadataQuery(MetadataQuery selectedMetadataQuery) {
		this.selectedMetadataQuery = selectedMetadataQuery;
	}

	/**
	 * Devuelve el valor de conn.
	 * 
	 * @return El valor de conn
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * Establece el valor de conn.
	 * 
	 * @param conn
	 *            El valor por establecer para conn
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Devuelve el valor de uuid.
	 * 
	 * @return El valor de uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * Establece el valor de uuid.
	 * 
	 * @param uuid
	 *            El valor por establecer para uuid
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * Esta funcion obtiene un procedimiento almacenado leido desde el DataBaseConfiguration.
	 * 
	 * @param storedProcedure
	 * @return
	 */
	public DbCommand GetStoredProcCommand(String storedProcedure) {
		return new DbCommand(CommandType.StoredProcedure, storedProcedure);
	}

}
