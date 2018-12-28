
package co.gov.colpensiones.beps.schemas._1_0.personas;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoInformacionViabilidad complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionViabilidad">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="viabilidad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="listaCausalNoViabilidad" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoCausalNoViabilidad" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionViabilidad", propOrder = {
    "viabilidad",
    "listaCausalNoViabilidad"
})
public class TipoInformacionViabilidad {

    @XmlElement(required = true)
    protected String viabilidad;
    @XmlElement(nillable = true)
    protected List<TipoCausalNoViabilidad> listaCausalNoViabilidad;

    /**
     * Gets the value of the viabilidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViabilidad() {
        return viabilidad;
    }

    /**
     * Sets the value of the viabilidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViabilidad(String value) {
        this.viabilidad = value;
    }

    /**
     * Gets the value of the listaCausalNoViabilidad property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listaCausalNoViabilidad property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaCausalNoViabilidad().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipoCausalNoViabilidad }
     * 
     * 
     */
    public List<TipoCausalNoViabilidad> getListaCausalNoViabilidad() {
        if (listaCausalNoViabilidad == null) {
            listaCausalNoViabilidad = new ArrayList<TipoCausalNoViabilidad>();
        }
        return this.listaCausalNoViabilidad;
    }

}
