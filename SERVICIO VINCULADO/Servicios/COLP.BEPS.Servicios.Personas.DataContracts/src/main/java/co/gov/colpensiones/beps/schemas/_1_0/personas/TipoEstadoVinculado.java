package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.comunes.anotaciones.NotNullValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.StringLengthValidator;

/**
 * <p>Java class for tipoAutorizacion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoEstadoVinculado">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="estadoVinculado" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descripcionEstadoVinculado" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codigoDetalleEstado" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descripcionDetalleEstado" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoEstadoVinculado", propOrder = {
    "estadoVinculado",
    "descripcionEstadoVinculado",
    "codigoDetalleEstado",
    "descripcionDetalleEstado"    
})
public class TipoEstadoVinculado {

	@XmlElement
	protected String estadoVinculado;
	@XmlElement
	protected String descripcionEstadoVinculado;
	@XmlElement
	protected String codigoDetalleEstado;
	@XmlElement
	protected String descripcionDetalleEstado; 

	/**
     * Gets the value of the estadoVinculado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */ 
    @NotNullValidator
    @StringLengthValidator(min = 1, max = 1, message = "{0} debe tener una longitud de 1 caracter")
	public String getEstadoVinculado() {
		return estadoVinculado;
	}
    
    /**
     * Sets the value of the estadoVinculado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setEstadoVinculado(String estadoVinculado) {
		this.estadoVinculado = estadoVinculado;
	}

	/**
     * Gets the value of the estadoVinculado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */	
	@StringLengthValidator(min = 0, max = 50, message = "{0} debe tener una longitud de 50 caracteres")
	public String getDescripcionEstadoVinculado() {
		return descripcionEstadoVinculado;
	}

    /**
     * Sets the value of the descripcionEstadoVinculado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setDescripcionEstadoVinculado(String descripcionEstadoVinculado) {
		this.descripcionEstadoVinculado = descripcionEstadoVinculado;
	}

	/**
     * Gets the value of the codigoDetalleEstado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
	@StringLengthValidator(min = 0, max = 2, message = "{0} debe tener una longitud de 2 caracteres")
	public String getCodigoDetalleEstado() {
		return codigoDetalleEstado;
	}

    /**
     * Sets the value of the codigoDetalleEstado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setCodigoDetalleEstado(String codigoDetalleEstado) {
		this.codigoDetalleEstado = codigoDetalleEstado;
	}

	/**
     * Gets the value of the descripcionDetalleEstado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
	@StringLengthValidator(min = 0, max = 50, message = "{0} debe tener una longitud de 50 caracteres")
	public String getDescripcionDetalleEstado() {
		return descripcionDetalleEstado;
	}

    /**
     * Sets the value of the descripcionDetalleEstado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setDescripcionDetalleEstado(String descripcionDetalleEstado) {
		this.descripcionDetalleEstado = descripcionDetalleEstado;
	}	

    
}
