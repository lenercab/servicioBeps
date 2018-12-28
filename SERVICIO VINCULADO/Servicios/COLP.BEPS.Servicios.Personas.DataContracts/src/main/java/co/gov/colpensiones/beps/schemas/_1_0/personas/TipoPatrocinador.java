
package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoPatrocinador complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoPatrocinador">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="patrocinador" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nombrePatrocinador" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoPatrocinador", propOrder = {
    "patrocinador",
    "nombrePatrocinador"
})
public class TipoPatrocinador {

    @XmlElement(nillable = true)
    protected String patrocinador;
    @XmlElement(nillable = true)
    protected String nombrePatrocinador;

    /**
     * Gets the value of the patrocinador property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatrocinador() {
        return patrocinador;
    }

    /**
     * Sets the value of the patrocinador property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatrocinador(String value) {
        this.patrocinador = value;
    }

    /**
     * Gets the value of the nombrePatrocinador property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombrePatrocinador() {
        return nombrePatrocinador;
    }

    /**
     * Sets the value of the nombrePatrocinador property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombrePatrocinador(String value) {
        this.nombrePatrocinador = value;
    }

}
