
package co.gov.colpensiones.beps.schemas._1_0.personas;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.comunes.anotaciones.NotNullValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.RegexValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.StringLengthValidator;

/**
 * <b>Descripcion:</b> Clase que define la información relacionada con el correo electrónico de un solicitante <br/>
 * <b>Caso de Uso:</b> GOP-VIN-1-FAB-11-ModificarInformacionSensibleVinculado <br/>
 * 
 * @author Yenny Nustez <ynustez@heinsohn.com.co>
 */

/**
 * <p>Java class for tipoCorreoElectronico complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoCorreoElectronico">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificador" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "tipoCorreoElectronico", propOrder = {
    "identificador",
    "direccion",
    "esPrincipal"
})
public class TipoCorreoElectronico {

    /** Identificador del correo en el sistema beps    */
    @XmlElement(required = true, type = BigDecimal.class, defaultValue="0")
    protected BigDecimal identificador=BigDecimal.ZERO;
    
    /** Dirección de correo electrónico    */
    @XmlElement(required = true)
    protected String direccion;
    
    /** Indicador de Dirección Correo electrónico Principal    */
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
     * Gets the value of the direccion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @NotNullValidator
    @RegexValidator(pattern ="[0-9a-zA-Z][0-9a-zA-Z]*(([_|.|$|\\-|+|&|*|?|^|{|}|])*[0-9a-zA-Z]*)*[@][0-9a-zA-Z]*[[_|.|$|\\-|+|&|*|?|^|{|}|]w]*[0-9a-zA-Z]+[.]([a-zA-Z]{2,3}|[a-zA-Z]{2,3}[.][a-zA-Z]{2,3}|[a-zA-Z]{2,3}[.][a-zA-Z]{2,3}[.][a-zA-Z]{2,3})$", message = "{0} correo electrónico no es válido")
    @StringLengthValidator(min = 5, max = 100, message = "{0} correo electrónico debe tener una longitud de 5 a 100 caracteres")
    public String getDireccion() {
        return direccion;
    }

    /**
     * Sets the value of the direccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDireccion(String value) {
        this.direccion = value;
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
    @StringLengthValidator(min = 1, max = 1, message = "Indicador CorreoElectronicoPrincipal debe tener una longitud de 1 caracter")
    @RegexValidator(pattern ="^(S|N)+$", message="Indicador CorreoElectronicoPrincipal no es válido, debe ingresar alguna de las siguientes opciones: S, N")
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
