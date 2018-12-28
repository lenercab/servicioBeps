package co.gov.colpensiones.beps.dal.utilidades;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import co.gov.colpensiones.beps.comunes.utilidades.ResourceUtil;
import co.gov.colpensiones.beps.comunes.utilidades.SvcLogger;

/**
 * Esta clase Sirve para manejar la conexi�n con la base de datos.
 * Maneja los campos basico para controlar una conexion.
 * @author jgomez
 *
 */

public class DaoSybase {

	// Conexion de la base de datos
	private static DaoSybase dao;

	
	
	private Connection conn;
	private String driverClass;
	private String connectionURL;
	private String user;
	private String password;
	private Statement stm = null;
	@SuppressWarnings("unused")
	private ResultSet rs = null;

	public DaoSybase() {

	}
	/**
	 * Esta funci�n sirve para inicializar la conexi�n de la base de datos
	 * @param driverClass
	 * @param connectionURL
	 * @param user
	 * @param password
	 */
	public DaoSybase(String driverClass, String connectionURL, String user,
			String password) {
		super();
		this.driverClass = driverClass;
		this.connectionURL = connectionURL;
		this.user = user;
		this.password = password;
	}

	public static DaoSybase getInstance(String driverClass,
			String connectionURL, String user, String password) {

		dao = new DaoSybase(driverClass, connectionURL, user, password);

		return dao;

	}


		/**
		 * Esta funcino nos entrega la conexion de la base de datos
		 * @return
		 */
	
	public static DaoSybase getInstance() {

		if (dao == null) {
			dao = new DaoSybase();
		}
		return dao;

	}

	/**
	 * Busca  la  la conexion de la base de datos si no la hay
	 * carga una de acuerdo al jndiname y carga el datasource desplegado desde el jboss.
	 * Si no encontro nada en el jboss entonces crea una conexion de manera directa
	 * sin un datasource de por medio.
	 *  
	 *  
	 * @return Connection
	 */
	
	public Connection getConexion() {
		try {
			InitialContext cxt = new InitialContext();
			String jndiName = ResourceUtil.getResourceValue("resources",
					"jndi-name-histlab");
			DataSource ds = (DataSource) cxt.lookup(jndiName);
			if (ds == null)
				throw new Exception("Data source not found!");

			conn = ds.getConnection();

		} catch (SQLException e) {
			SvcLogger.error(e.getMessage(), e);
		} catch (Exception e) {
			SvcLogger.error(e.getMessage(), e);
		}
		// Condicion para realizar pruebas unitarias en la BL
		try {
			if (conn == null || conn.isClosed()) {
				try {
					if (getPassword() != null && getUser() != null) {
						Class.forName(getDriverClass());
						Properties props = new Properties();
						props.put("user", getPassword());
						props.put("password", getUser());

						conn = DriverManager.getConnection(getConnectionURL(),
								props);
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					SvcLogger.error(e.getMessage());
				} catch (SQLException e) {
					

					SvcLogger.error(e.getMessage(), e);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * Ejecuta una sentencia SQL en string
	 * @param sentencia
	 * @return
	 */
	
	public int ejecutarSentencia(String sentencia) {
		try {
			stm = conn.createStatement();
			int retorno = stm.executeUpdate(sentencia);
			return retorno;
		} catch (Exception e) {
			SvcLogger.error("Error al ejecutar la sentencia", e);
			return 0;
		}

	}

	
	/**
	 * //Esta funciones ejecutan procedimientos almacenados valida si se utiliza el arg[2]
	 * @param nombre
	 * @param arg
	 */
	
	public void ejecutarStoreProcedureHLT(String nombre, String... arg) {
		try {
			CallableStatement cs;
			String call = "{ call " + nombre;

			if (arg.length == 2) {
				call = call + "(?,?) }";
			} else if (arg.length == 3) {
				call = call + "(?,?,?) }";
			}
			cs = conn.prepareCall(call);

			int cont = 1;
			if (arg[0] != null) {
               cs.setBigDecimal(1, new BigDecimal(arg[0]));
			}
			if (arg[1] != null) {
               cs.setString(2, arg[1]);
			}
			if (arg[2] != null) {
				 cs.setString(3, arg[2]);
			}
			cs.execute();

		} catch (Exception e) {
			SvcLogger.error("Error al llamar store procedure" + nombre, e);
		}
	}
	
	/**
	 * //Esta funciones ejecutan procedimientos almacenados 
	 * @param nombre
	 * @param arg
	 */
	
	public void ejecutarStoreProcedureHLU(String nombre, String... arg) {
		try {
			CallableStatement cs;
			String call = "{ call " + nombre;

			if (arg.length == 2) {
				call = call + "(?,?) }";
			} else if (arg.length == 3) {
				call = call + "(?,?,?) }";
			}
			cs = conn.prepareCall(call);

			int cont = 1;
			if (arg[0] != null) {
               cs.setBigDecimal(1, new BigDecimal(arg[0]));
			}
			if (arg[1] != null) {
               cs.setString(2, arg[1]);
			}
			
			cs.execute();

		} catch (Exception e) {
			SvcLogger.error("Error al llamar store procedure" + nombre, e);
		}
	}

	/**
	 * Ejecuta una query SQL que retorne filas
	 * @param sentencia
	 * @return DataTable
	 */
	
	public ResultSet ejecutarConsulta(String sentencia) {
		try {
			stm = conn.createStatement();
			return stm.executeQuery(sentencia);

		} catch (Exception e) {
			SvcLogger.error("Error al ejecutar la sentencia", e);
			return null;
		}
	}

	public void closeConexion() throws Exception {
		if (conn != null) {
			conn.close();
		}
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
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

	public String getConnectionURL() {
		return connectionURL;
	}

	public void setConnectionURL(String connectionURL) {
		this.connectionURL = connectionURL;
	}

}
