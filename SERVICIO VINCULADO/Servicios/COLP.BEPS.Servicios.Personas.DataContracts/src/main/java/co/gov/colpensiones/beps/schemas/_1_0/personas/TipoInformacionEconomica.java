
package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.comunes.anotaciones.IgnoreNullsValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.NotNullValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.RegexValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.StringLengthValidator;


/**
 * <p>Java class for tipoInformacionEconomica complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionEconomica">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoActividadEconomicaPrincipal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codigoActividadEconomicaSecundaria" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionEconomica", propOrder = {
    "codigoActividadEconomicaPrincipal",
    "codigoActividadEconomicaSecundaria"
})
public class TipoInformacionEconomica {

    @XmlElement
    protected String codigoActividadEconomicaPrincipal;
    @XmlElement
    protected String codigoActividadEconomicaSecundaria;

    /**
     * Gets the value of the codigoActividadEconomicaPrincipal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @NotNullValidator
    @StringLengthValidator(min = 4, max = 4, message = "{0} debe tener una longitud de 4 caracteres")
    @RegexValidator(pattern ="[0-9]*", message = "{0} no es válido, permite caracteres numéricos. Se permiten números con ceros a la izquierda")
    public String getCodigoActividadEconomicaPrincipal() {
        return codigoActividadEconomicaPrincipal;
    }

    /**
     * Sets the value of the codigoActividadEconomicaPrincipal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoActividadEconomicaPrincipal(String value) {
        this.codigoActividadEconomicaPrincipal = value;
    }

    /**
     * Gets the value of the codigoActividadEconomicaSecundaria property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @IgnoreNullsValidator
    @StringLengthValidator(min = 4, max = 4, message = "{0} debe tener una longitud de 4 caracteres")
    @RegexValidator(pattern ="[0-9]*", message = "{0} no es válido, permite caracteres numéricos. Se permiten números con ceros a la izquierda")    
    public String getCodigoActividadEconomicaSecundaria() {
        return codigoActividadEconomicaSecundaria;
    }

    /**
     * Sets the value of the codigoActividadEconomicaSecundaria property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoActividadEconomicaSecundaria(String value) {
        this.codigoActividadEconomicaSecundaria = value;
    }

}
