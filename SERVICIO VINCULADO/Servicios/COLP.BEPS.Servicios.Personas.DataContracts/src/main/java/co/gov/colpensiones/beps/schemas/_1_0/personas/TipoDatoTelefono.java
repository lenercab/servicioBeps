
package co.gov.colpensiones.beps.schemas._1_0.personas;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.comunes.anotaciones.IgnoreNullsValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.NotNullValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.RegexValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.StringLengthValidator;



/**
 * <b>Descripcion:</b> Contiene la información del teléfono de un solicitante <br/>
 * <b>Caso de Uso:</b> GOP-VIN-1-FAB-11-ModificarInformacionSensibleVinculado <br/>
 * 
 * @author Yenny Nustez <ynustez@heinsohn.com.co>
 */
/**
 * <p>Java class for tipoDatoTelefono complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoDatoTelefono">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificador" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="tipoTelefono" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indicativoPais" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indicativoCiudad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="extension" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="esPrincipal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoDatoTelefono", propOrder = {
    "identificador",
    "tipoTelefono",
    "telefono",
    "indicativoPais",
    "indicativoCiudad",
    "extension",
    "esPrincipal"
})
public class TipoDatoTelefono {

    /** Identificador del telefono en el sistema beps    */
    @XmlElement(required = true, type = BigDecimal.class, defaultValue="0")
    protected BigDecimal identificador = BigDecimal.ZERO;
    
    /** Código correspondiente al tipo de teléfono de acuerdo con el estándar GEL XML    */
    @XmlElement(required = true)
    protected String tipoTelefono;
    
    /** Número de teléfono     */
    @XmlElement(required = true)
    protected String telefono;
    
    /** Número de indicativo de teléfono del País    */
    @XmlElement(nillable = true)
    protected String indicativoPais;
    
    /** Número de indicativo de teléfono de la Ciudad del afiliado    */
    @XmlElement(nillable = true)
    protected String indicativoCiudad;
    
    /** Número de extensión del teléfono    */
    @XmlElement(nillable = true)
    protected String extension;
    
    /** Indicador de Teléfono Principal    */
    @XmlElement(required = true)
    protected String esPrincipal;

    /**
     * Gets the value of the identificador property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIdentificador() {
        return identificador;
    }

    /**
     * Sets the value of the identificador property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIdentificador(BigDecimal value) {
        this.identificador = value;
    }

    /**
     * Gets the value of the tipoTelefono property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @NotNullValidator
    @StringLengthValidator(min=2, max=2, message="{0} debe tener una longitud de 2 caracteres")
    @RegexValidator(pattern="^(01|02|03|04)$", message="{0} no es válido, debe ingresar alguna de las siguientes opciones: 01, 02, 03, 04") 
    public String getTipoTelefono() {
        return tipoTelefono;
    }

    /**
     * Sets the value of the tipoTelefono property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoTelefono(String value) {
        this.tipoTelefono = value;
    }

    /**
     * Gets the value of the telefono property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @NotNullValidator
    @StringLengthValidator(min = 5, max = 20 , message = "{0} debe tener una longitud de 5 a 20")    
    public String getTelefono() {
        return telefono;
    }

    /**
     * Sets the value of the telefono property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefono(String value) {
        this.telefono = value;
    }

    /**
     * Gets the value of the indicativoPais property.
     * 
     */
    @IgnoreNullsValidator
    @StringLengthValidator(min = 1, max = 3, message = "{0} debe tener una longitud de mínimo 1 y máximo 3 caracteres")
    @RegexValidator(pattern = "[0-9]+", message = "{0} no es válido, solo se permiten números")   
    public String getIndicativoPais() {
        return indicativoPais;
    }

    /**
     * Sets the value of the indicativoPais property.
     * 
     */
    public void setIndicativoPais(String value) {
        this.indicativoPais = value;
    }

    /**
     * Gets the value of the indicativoCiudad property.
     * 
     */
    @IgnoreNullsValidator
    @StringLengthValidator(min = 1, max = 3, message = "{0} debe tener una longitud de mínimo 1 y máximo 3 caracteres")
    @RegexValidator(pattern = "[0-9]+", message = "{0} no es válido, solo se permiten números")       
    public String getIndicativoCiudad() {
        return indicativoCiudad;
    }

    /**
     * Sets the value of the indicativoCiudad property.
     * 
     */
    public void setIndicativoCiudad(String value) {
        this.indicativoCiudad = value;
    }

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    @IgnoreNullsValidator
    @StringLengthValidator(min = 1, max = 5, message = "{0} debe tener una longitud de 1 a 5")
    @RegexValidator(pattern = "[0-9]+", message = "{0} no es válido, solo se permiten números")       
    public String getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setExtension(String value) {
        this.extension = value;
    }

    /**
     * Gets the value of the esPrincipal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @NotNullValidator
    @StringLengthValidator(min = 1, max = 1, message = "Indicador TelefonoPrincipal  debe tener una longitud de 1 caracter") 
    @RegexValidator(pattern ="^(S|N)+$", message = "Indicador TelefonoPrincipal no es válido, debe ingresar alguna de las siguientes opciones: S, N")
    public String getEsPrincipal() {
        return esPrincipal;
    }

    /**
     * Sets the value of the esPrincipal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsPrincipal(String value) {
        this.esPrincipal = value;
    }

}
