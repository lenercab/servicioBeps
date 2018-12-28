package co.gov.colpensiones.beps.dal.utilidades;

import java.util.ArrayList;

/**
 * Estructura de una fila en la base de datos
 * @author jgomez
 *
 */

public class DataRow {

	private ArrayList<DataColumn> columns;

	public DataRow() {
		columns = new ArrayList<DataColumn>();
	}

	private ArrayList<DataColumn> getColumns() {
		if (columns == null)
			columns = new ArrayList<DataColumn>();
		return columns;
	}

	
	public void addColumn(DataColumn column){
		getColumns().add(column);
	}

	public Object getValue(int index){
		return getColumns().get(index).getValue();
	}
	
	public Object getValue(String columnName){
		Object value=null;
		for (DataColumn column : getColumns()) {
			if(column.getColumnName().equals(columnName))
				return column.getValue();
		}
		return value;
	}
	
	public boolean isNull(String columnName){
		return getValue(columnName)==null;
	}
	
	public boolean isNull(int index){
		return getValue(index)==null;
	}
	

	
}
