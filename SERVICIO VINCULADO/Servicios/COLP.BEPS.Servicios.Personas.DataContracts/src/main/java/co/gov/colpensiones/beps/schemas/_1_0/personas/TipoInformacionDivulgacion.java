
package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoInformacionDivulgacion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionDivulgacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="canalDivulgacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaDivulgacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionDivulgacion", propOrder = {
    "canalDivulgacion",
    "fechaDivulgacion"
})
public class TipoInformacionDivulgacion {

    @XmlElement(nillable = false)
    protected String canalDivulgacion;
    @XmlElement(nillable = false)
    protected String fechaDivulgacion;

    /**
     * Gets the value of the canalDivulgacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanalDivulgacion() {
        return canalDivulgacion;
    }

    /**
     * Sets the value of the canalDivulgacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanalDivulgacion(String value) {
        this.canalDivulgacion = value;
    }

    /**
     * Gets the value of the fechaDivulgacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaDivulgacion() {
        return fechaDivulgacion;
    }

    /**
     * Sets the value of the fechaDivulgacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaDivulgacion(String value) {
        this.fechaDivulgacion = value;
    }

}
