
package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoCausalNoViabilidad complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoCausalNoViabilidad">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="causal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoCausalNoViabilidad", propOrder = {
    "causal"
})
public class TipoCausalNoViabilidad {

    @XmlElement(required = true)
    protected String causal;

    /**
     * Gets the value of the causal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCausal() {
        return causal;
    }

    /**
     * Sets the value of the causal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCausal(String value) {
        this.causal = value;
    }

}
