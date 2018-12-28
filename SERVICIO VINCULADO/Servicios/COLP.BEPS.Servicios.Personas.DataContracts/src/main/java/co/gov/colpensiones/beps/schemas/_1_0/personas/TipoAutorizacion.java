
package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.comunes.anotaciones.NotNullValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.RegexValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.StringLengthValidator;


/**
 * <p>Java class for tipoAutorizacion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoAutorizacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="autorizacionEnvioComunicacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="autorizacionManejoInformacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoAutorizacion", propOrder = {
    "autorizacionEnvioComunicacion",
    "autorizacionManejoInformacion"
})
public class TipoAutorizacion {

    @XmlElement
    protected String autorizacionEnvioComunicacion;
    @XmlElement
    protected String autorizacionManejoInformacion;

    /**
     * Gets the value of the autorizacionEnvioComunicacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */ 
    @NotNullValidator
    @RegexValidator(pattern ="^(S|N)+$", message = "{0} no es válido, debe ingresar alguna de las siguientes opciones: S, N")
    @StringLengthValidator(min = 1, max = 1, message = "{0} debe tener una longitud de 1 caracter")    
    public String getAutorizacionEnvioComunicacion() {
        return autorizacionEnvioComunicacion;
    }

    /**
     * Sets the value of the autorizacionEnvioComunicacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutorizacionEnvioComunicacion(String value) {
        this.autorizacionEnvioComunicacion = value;
    }

    /**
     * Gets the value of the autorizacionManejoInformacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @NotNullValidator
    @RegexValidator(pattern ="^(S|N)+$", message = "{0} no es válido, debe ingresar alguna de las siguientes opciones: S, N")
    @StringLengthValidator(min = 1, max = 1, message = "{0} debe tener una longitud de 1 caracter")        
    public String getAutorizacionManejoInformacion() {
        return autorizacionManejoInformacion;
    }

    /**
     * Sets the value of the autorizacionManejoInformacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutorizacionManejoInformacion(String value) {
        this.autorizacionManejoInformacion = value;
    }

}
