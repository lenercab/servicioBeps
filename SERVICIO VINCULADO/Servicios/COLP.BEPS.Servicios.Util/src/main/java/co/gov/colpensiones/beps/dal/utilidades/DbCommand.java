package co.gov.colpensiones.beps.dal.utilidades;

import java.util.ArrayList;

/**
 * Estructura de un commando que va a  ser ejecutado en la base de datos 
 * @author jgomez
 *
 */

public class DbCommand {
	private CommandType commandType;
	private String commandText;
	private String commandName;
	private ArrayList<DbParameter> parameters;

	public DbCommand() {
		super();
		parameters = new ArrayList<DbParameter>();
		// TODO Auto-generated constructor stub
	}

	public DbCommand(CommandType type, String commandText) {
		super();
		this.commandType = type;
		this.commandText = commandText;
		parameters = new ArrayList<DbParameter>();
		
		if (this.commandType ==  type.StoredProcedure )
			this.commandName = commandText;

			
	}

	public DbCommand(CommandType type, String commandText,
			ArrayList<DbParameter> parameters) {
		super();
		this.commandType = type;
		this.commandText = commandText;
		this.parameters = parameters;
		if (this.commandType ==  type.StoredProcedure )
			this.commandName = commandText;

	}

	public DbCommand(CommandType type, String commandText, String commandName) {
		super();
		this.commandType = type;
		this.commandText = commandText;
		parameters = new ArrayList<DbParameter>();
		this.commandName = commandName;

			
	}

	
	public String getCommandName() {
		return commandName;
	}

	
	public CommandType getCommandType() {
		return commandType;
	}

	public void setCommandType(CommandType type) {
		this.commandType = type;
	}

	public String getCommandText() {
		return commandText;
	}

	public void setCommandText(String commandText) {
		this.commandText = commandText;
	}

	public ArrayList<DbParameter> getParameters() {
		if (parameters == null)
			parameters = new ArrayList<DbParameter>();
		return parameters;
	}

	public void setParameters(ArrayList<DbParameter> parameters) {
		this.parameters = parameters;
	}

	public void addParameter(DbParameter parameter) {
		getParameters().add(parameter);
	}

	public void addParameter(String parameterName, int parameterScale, int parameterType,
			ParameterDirection paramDirection, Object parameterValue) {
		getParameters().add(
				new DbParameter(parameterName, parameterScale, parameterType, paramDirection, parameterValue));
	}

	public void addInParameter(String parameterName, 
			int parameterType, int parameterScale, Object parameterValue) {

		addParameter(parameterName, parameterScale, parameterType, ParameterDirection.IN, parameterValue);
	}

	public void addOutParameter(String parameterName, 
			int parameterType, int parameterScale) {

		addParameter(parameterName, parameterScale, parameterType, ParameterDirection.OUT, null);
	}
	
	public Object getParameterValue(String parameterName){
		
		for (DbParameter parameter : getParameters()) {
			if(parameter.getParameterName().equals(parameterName))
				return parameter.getParameterValue();
		}
		return null;
	}
	
	/**
	 * Método que permite limpiar los parámetros del DBCommand
	 */
	public void clearParameter(){
	    this.parameters.clear();	    
	}
}
