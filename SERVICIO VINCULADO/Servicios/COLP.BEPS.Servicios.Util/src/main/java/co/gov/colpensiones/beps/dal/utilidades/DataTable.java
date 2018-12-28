package co.gov.colpensiones.beps.dal.utilidades;

import java.util.ArrayList;

/**
 * Estructura de una tabla retornada de la base de datos.
 * @author jgomez
 *
 */

public class DataTable {

	private String tableName;
	private ArrayList<DataRow> rows;
	private ArrayList<DataColumn> columns;

	public DataTable() {
		rows = new ArrayList<DataRow>();
		columns = new ArrayList<DataColumn>();
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public ArrayList<DataRow> getRows() {
		if (rows == null)
			rows = new ArrayList<DataRow>();
		return rows;
	}

	public ArrayList<DataColumn> getColumns() {
		if (columns == null)
			columns = new ArrayList<DataColumn>();
		return columns;
	}

	public void addRow(DataRow row) {
		getRows().add(row);
	}

	public void addColumn(DataColumn column) {
		getColumns().add(column);
	}

	public void printTable() {

		System.out.println("Table Name: [" + getTableName()+"]");
		for (int i = 0; i < getColumns().size(); i++) {
			if (i == 0)
				System.out.print("["
						+ getColumns().get(i).getColumnName()
						+ "]");
			else
				System.out.print("\t, ["
						+ getColumns().get(i).getColumnName()
						+ "]");
		}
		System.out.println();

		for (int i = 0; i < getRows().size(); i++) {
			for (int j = 0; j < getColumns().size(); j++) {
				if (j == 0)
					System.out.print(getRows().get(i).getValue(j));
				else
					System.out.print("\t," + getRows().get(i).getValue(j));
			}
			System.out.println();
		}

	}
}
