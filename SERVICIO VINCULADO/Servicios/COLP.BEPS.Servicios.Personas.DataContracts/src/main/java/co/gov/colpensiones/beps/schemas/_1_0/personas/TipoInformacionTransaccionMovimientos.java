
package co.gov.colpensiones.beps.schemas._1_0.personas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoInformacionTransaccionMovimientos complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionTransaccionMovimientos">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="movimiento" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionMovimiento" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="valorTotalMovimientos" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionTransaccionMovimientos", propOrder = {
    "movimiento",
    "valorTotalMovimientos"
})
public class TipoInformacionTransaccionMovimientos {

    @XmlElement(nillable = true)
    protected List<TipoInformacionMovimiento> movimiento;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal valorTotalMovimientos;

    /**
     * Gets the value of the movimiento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the movimiento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMovimiento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipoInformacionMovimiento }
     * 
     * 
     */
    public List<TipoInformacionMovimiento> getMovimiento() {
        if (movimiento == null) {
            movimiento = new ArrayList<TipoInformacionMovimiento>();
        }
        return this.movimiento;
    }
    
    /**
     * 
     * @param movimiento
     */
    public void setMovimiento(ArrayList<TipoInformacionMovimiento> movimiento){
        this.movimiento = movimiento;
    }

    /**
     * Gets the value of the valorTotalMovimientos property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorTotalMovimientos() {
        return valorTotalMovimientos;
    }

    /**
     * Sets the value of the valorTotalMovimientos property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorTotalMovimientos(BigDecimal value) {
        this.valorTotalMovimientos = value;
    }

}
