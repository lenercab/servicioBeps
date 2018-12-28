package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.comunes.anotaciones.NotNullValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.StringLengthValidator;

/**
 * <b>Descripción:</b> Estructura que almacena la información del estado de un solicitante .<br/>
 * @author Yesika Ramirez <yeramirez@heinsohn.com.co>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoEstadoSolicitante", propOrder = {
    "tipoPersona",
    "estadoVinculacion",
    "detalleEstadoVinculacion",
    "fechaVinculacion",
    "colombiaMayor"
})
public class TipoEstadoSolicitante {
	
	/**
	 * Estado del solicitante según la consulta realizada en el Sistema BEPS sobre la información
	 * del prospecto,vinculado o pre vinculado.
	 * */
	@XmlElement
	protected String tipoPersona;
	
	/**
	 * Estado del vinculado en el SGBEPS
	 * */
	@XmlElement
	protected String estadoVinculacion;
	
	/**
	 *Corresponde a la descripción asignada al detalle del estado actual del vinculado. 
	 * */
	@XmlElement
	protected String detalleEstadoVinculacion;
	
	/**
	 * Corresponde a la fecha de vinculación al programa BEPS 
	 * (solamente aplica cuando el solicitante es un vinculado)
	 * */
	@XmlElement
	protected String fechaVinculacion;
	
	/**
	 * Marcación de Colombia Mayor
	 * */
	@XmlElement
	protected String colombiaMayor;

	/**
	 * Devuelve el valor del atributo tipoPersona.
	 *
	 * @return El valor del atributo tipoPersona
	 */
	@NotNullValidator
	public String getTipoPersona() {
		return tipoPersona;
	}
	
	 /**
     * Establece el valor del atributo tipoPersona.
     *
     * @param tipoPersona El valor por establecer para el atributo tipoPersona
     */
	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	
	/**
	 * Devuelve el valor del atributo estadoVinculacion.
	 *
	 * @return El valor del atributo estadoVinculacion
	 */
	@NotNullValidator
	@StringLengthValidator(min = 1, max = 50, message = "{0} debe tener una longitud de min 1 y max  50 caracteres")
	public String getEstadoVinculacion() {
		return estadoVinculacion;
	}
	
	 /**
     * Establece el valor del atributo estadoVinculacion.
     *
     * @param estadoVinculacion El valor por establecer para el atributo estadoVinculacion
     */
	public void setEstadoVinculacion(String estadoVinculacion) {
		this.estadoVinculacion = estadoVinculacion;
	}
	
	/**
	 * Devuelve el valor del atributo detalleEstadoVinculacion.
	 *
	 * @return El valor del atributo detalleEstadoVinculacion
	 */	
	@StringLengthValidator(min = 0, max = 50, message = "{0} debe tener una longitud de min 0 y max  50 caracteres")
	public String getDetalleEstadoVinculacion() {
		return detalleEstadoVinculacion;
	}

	 /**
     * Establece el valor del atributo detalleEstadoVinculacion.
     *
     * @param detalleEstadoVinculacion El valor por establecer para el atributo detalleEstadoVinculacion
     */
	public void setDetalleEstadoVinculacion(String detalleEstadoVinculacion) {
		this.detalleEstadoVinculacion = detalleEstadoVinculacion;
	}
	
	/**
	 * Devuelve el valor del atributo fechaVinculacion.
	 *
	 * @return El valor del atributo fechaVinculacion
	 */	
	@StringLengthValidator(min = 0, max = 8, message = "{0} debe tener una longitud de min 0 y max  8 caracteres")
	public String getFechaVinculacion() {
		return fechaVinculacion;
	}
	
	/**
     * Establece el valor del atributo fechaVinculacion.
     *
     * @param fechaVinculacion El valor por establecer para el atributo fechaVinculacion
     */
	public void setFechaVinculacion(String fechaVinculacion) {
		this.fechaVinculacion = fechaVinculacion;
	}
	
	/**
	 * Devuelve el valor del atributo colombiaMayor.
	 *
	 * @return El valor del atributo colombiaMayor
	 */	
	@StringLengthValidator(min = 0, max = 50, message = "{0} debe tener una longitud de min 0 y max  50 caracteres")
	public String getColombiaMayor() {
		return colombiaMayor;
	}
	
	/**
     * Establece el valor del atributo colombiaMayor.
     *
     * @param colombiaMayor El valor por establecer para el atributo colombiaMayor
     */
	public void setColombiaMayor(String colombiaMayor) {
		this.colombiaMayor = colombiaMayor;
	}
	
	
	
}
