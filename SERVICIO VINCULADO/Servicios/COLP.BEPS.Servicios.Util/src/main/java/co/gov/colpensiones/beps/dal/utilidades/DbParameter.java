package co.gov.colpensiones.beps.dal.utilidades;

/**
 * Estructura de un parametro, de un comando, que va a ser ejecutado en la base de datos
 * @author jgomez
 *
 */

public class DbParameter {

	private String parameterName;
	private Object parameterValue;
	private int parameterScale;
	private int parameterType;
	private ParameterDirection paramDirection;

	public DbParameter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DbParameter(String parameterName, 
			int parameterScale, int parameterType,
			ParameterDirection paramDirection, Object parameterValue) {
		super();
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;
		this.parameterScale = parameterScale;
		this.parameterType = parameterType;
		this.paramDirection = paramDirection;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public Object getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(Object parameterValue) {
		this.parameterValue = parameterValue;
	}

	public int getParameterScale() {
		return parameterScale;
	}

	public void setParameterScale(int parameterScale) {
		this.parameterScale = parameterScale;
	}

	public int getParameterType() {
		return parameterType;
	}

	public void setParameterType(int parameterType) {
		this.parameterType = parameterType;
	}

	public ParameterDirection getParamDirection() {
		return paramDirection;
	}

	public void setParamDirection(ParameterDirection paramDirection) {
		this.paramDirection = paramDirection;
	}

}
