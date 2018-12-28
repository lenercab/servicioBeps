package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.comunes.anotaciones.IgnoreNullsValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.RegexValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.StringLengthValidator;



/**
 * <b>Descripcion:</b> Contiene los Datos de  tipo y número de documento <br/>
 * <b>Caso de Uso:</b> GOP-VIN-1-FAB-11-ModificarInformacionSensibleVinculado <br/>
 * 
 * @author Yenny Nustez <ynustez@heinsohn.com.co>
 */
/**
 * <p>
 * Java class for tipoInformacionBasicaPersonaNatural complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionBasicaPersonaNatural">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="primerApellido" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="segundoApellido" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="primerNombre" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="segundoNombre" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionBasicaPersonaNatural", propOrder = {
		"primerApellido", "segundoApellido", "primerNombre", "segundoNombre" })
public class TipoInformacionBasicaPersonaNatural {

    /** Primer apellido del solicitante    */
    @XmlElement(nillable = true)
	protected String primerApellido;
	
    /** Segundo apellido del solicitante    */
    @XmlElement(nillable = true)
	protected String segundoApellido;
	
    /** Primer nombre del solicitante    */
    @XmlElement(nillable = true)
	protected String primerNombre;
	
    /** Segundo nombre del solicitante   */
    @XmlElement(nillable = true)
	protected String segundoNombre;

	/**
	 * Gets the value of the primerApellido property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@IgnoreNullsValidator
	@StringLengthValidator(min = 1, max = 25, message = "{0} debe tener una longitud de mínimo 1 y máximo 25 caracteres")
	@RegexValidator(pattern = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäÄëËïÏöÖüÜ]+[a-zA-ZñÑáéíóúÁÉÍÓÚäÄëËïÏöÖüÜ\\s]*$", message = "{0} no es válido, debe ingresar un valor alfabético sin caracteres especiales. No puede contener solo espacios en blanco")
	public String getPrimerApellido() {
		return primerApellido;
	}

	/**
	 * Sets the value of the primerApellido property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPrimerApellido(String value) {
		this.primerApellido = value;
	}

	/**
	 * Gets the value of the segundoApellido property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@IgnoreNullsValidator
	@StringLengthValidator(min = 0, max = 25, message = "{0} debe tener una longitud de máximo 25 caracteres")
	@RegexValidator(pattern = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäÄëËïÏöÖüÜ\\s]*$", message = "{0} no es válido, debe ingresar un valor alfabético sin caracteres especiales")
	public String getSegundoApellido() {
		return segundoApellido;
	}

	/**
	 * Sets the value of the segundoApellido property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSegundoApellido(String value) {
		this.segundoApellido = value;
	}

	/**
	 * Gets the value of the primerNombre property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@IgnoreNullsValidator
	@StringLengthValidator(min = 1, max = 25, message = "{0} debe tener una longitud de mínimo 1 y máximo 25 caracteres")
	@RegexValidator(pattern = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäÄëËïÏöÖüÜ]+[a-zA-ZñÑáéíóúÁÉÍÓÚäÄëËïÏöÖüÜ\\s]*$", message = "{0} no es válido, debe ingresar un valor alfabético sin caracteres especiales. No puede contener solo espacios en blanco")
	public String getPrimerNombre() {
		return primerNombre;
	}

	/**
	 * Sets the value of the primerNombre property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPrimerNombre(String value) {
		this.primerNombre = value;
	}

	/**
	 * Gets the value of the segundoNombre property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@IgnoreNullsValidator
	@StringLengthValidator(min = 0, max = 25, message = "{0} debe tener una longitud de máximo 25 caracteres")
	@RegexValidator(pattern = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäÄëËïÏöÖüÜ\\s]*$", message = "{0} no es válido, debe ingresar un valor alfabético sin caracteres especiales")
	public String getSegundoNombre() {
		return segundoNombre;
	}

	/**
	 * Sets the value of the segundoNombre property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSegundoNombre(String value) {
		this.segundoNombre = value;
	}

}
