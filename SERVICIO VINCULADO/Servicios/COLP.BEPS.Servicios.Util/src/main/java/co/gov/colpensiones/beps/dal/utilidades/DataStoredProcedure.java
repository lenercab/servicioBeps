/**
 * 
 */
package co.gov.colpensiones.beps.dal.utilidades;

import java.util.ArrayList;
import java.util.List;

/**
 * Objeto para el manejo de una estructura compleja retornada por la ejecución un procedimiento almacenado. Contiene un
 * objeto de tipo DataTable en el cual se incluyen los resultados de un objeto de tipo ResultSet. Adicionalmente
 * contiene una lista en la cual se incluyen los parámetros de tipo OUT configurados
 * 
 * @author Yenny Ñustez Arevalo <ynustez@heinsohn.com.co>
 * 
 */
public class DataStoredProcedure {

	/** Estructura de una tabla generada a partir de un objeto de tipo ResultSet */
	private DataTable tablaResultSet;

	/** Lista que contiene los parametros de salida obtenidos de la ejecucion de un Procedimiento Almacenado */
	private List<DbParameter> parametrosSalida;

	/** Lista que contiene los ResultSet de salida obtenidos de la ejecucion de un Procedimiento Almacenado */
	private List<DataTable> resultSetList;

	/**
	 * Constructor
	 */
	public DataStoredProcedure() {
		tablaResultSet = new DataTable();
		parametrosSalida = new ArrayList<DbParameter>();
		resultSetList = new ArrayList<DataTable>();
	}

	/**
	 * @return the tablaResultSet
	 */
	public DataTable getTablaResultSet() {
		return tablaResultSet;
	}

	/**
	 * @param tablaResultSet
	 *            the tablaResultSet to set
	 */
	public void setTablaResultSet(DataTable tablaResultSet) {
		this.tablaResultSet = tablaResultSet;
	}

	/**
	 * @return the parametrosSalida
	 */
	public List<DbParameter> getParametrosSalida() {
		return parametrosSalida;
	}

	/**
	 * @param parametrosSalida
	 *            the parametrosSalida to set
	 */
	public void setParametrosSalida(List<DbParameter> parametrosSalida) {
		this.parametrosSalida = parametrosSalida;
	}

	/**
	 * @return the multipleResultSet
	 */
	public List<DataTable> getResultSetList() {
		return resultSetList;
	}

	/**
	 * @param multipleResultSet
	 *            the multipleResultSet to set
	 */
	public void setResultSetList(List<DataTable> resultSetList) {
		this.resultSetList = resultSetList;
	}

}
