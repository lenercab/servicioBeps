package co.gov.colpensiones.beps.comunes.dto;

/**
 * <b>Descripcion:</b> Clase que almacena los datos de estado de un vinculado.<br/>
 *
 * @author jduran <jduran@heinsohn.com.co>
 */

public class InformacionBanderaDatosNoSensiblesDTO {
	
	/** Atributo para identificar la bandera de direcciones.*/
	Boolean consultarDireccion;
	
	/** Atributo para identificar la bandera de telefonos.*/
	Boolean consultarTelefono;
	
	/** Atributo para identificar la bandera de correo.*/
	Boolean consultarCorreo;

	/**
	 * @return the consultarDireccion
	 */
	public Boolean getConsultarDireccion() {
		return consultarDireccion;
	}

	/**
	 * @param consultarDireccion the consultarDireccion to set
	 */
	public void setConsultarDireccion(Boolean consultarDireccion) {
		this.consultarDireccion = consultarDireccion;
	}

	/**
	 * @return the consultarTelefono
	 */
	public Boolean getConsultarTelefono() {
		return consultarTelefono;
	}

	/**
	 * @param consultarTelefono the consultarTelefono to set
	 */
	public void setConsultarTelefono(Boolean consultarTelefono) {
		this.consultarTelefono = consultarTelefono;
	}

	/**
	 * @return the consultarCorreo
	 */
	public Boolean getConsultarCorreo() {
		return consultarCorreo;
	}

	/**
	 * @param consultarCorreo the consultarCorreo to set
	 */
	public void setConsultarCorreo(Boolean consultarCorreo) {
		this.consultarCorreo = consultarCorreo;
	}
	
	
	

}
