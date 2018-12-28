package co.gov.colpensiones.beps.dal.utilidades;

/**
 * La clase DataColumn
 * @author jgomez
 *
 * Estructura de una columna de la base de datos 
 */

public class DataColumn {
	
	public DataColumn() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DataColumn(String columnName) {
		super();
		this.columnName = columnName;
	}
	public DataColumn(String columnName, Object value) {
		super();
		this.columnName = columnName;
		this.value = value;
	}


	private String columnName;
	private Object value;
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	
}
