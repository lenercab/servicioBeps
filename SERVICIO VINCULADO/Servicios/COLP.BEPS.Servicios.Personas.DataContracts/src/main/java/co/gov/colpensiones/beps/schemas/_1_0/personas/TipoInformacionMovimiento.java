
package co.gov.colpensiones.beps.schemas._1_0.personas;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoInformacionMovimiento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionMovimiento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fechaConsignacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descripcionMovimiento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valorAporte" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="terminalPago" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaAcreditacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valorAplicadoVigenciasfuturas" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionMovimiento", propOrder = {
    "fechaConsignacion",
    "descripcionMovimiento",
    "valorAporte",
    "terminalPago",
    "fechaAcreditacion",
    "valorAplicadoVigenciasfuturas"
})
public class TipoInformacionMovimiento {

    @XmlElement(required = true, nillable = true)
    protected String fechaConsignacion;
    @XmlElement(required = true)
    protected String descripcionMovimiento;
    @XmlElement(required = true)
    protected BigDecimal valorAporte;
    @XmlElement(required = true, nillable = true)
    protected String terminalPago;
    @XmlElement(required = true)
    protected String fechaAcreditacion;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal valorAplicadoVigenciasfuturas;

    /**
     * Gets the value of the fechaConsignacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaConsignacion() {
        return fechaConsignacion;
    }

    /**
     * Sets the value of the fechaConsignacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaConsignacion(String value) {
        this.fechaConsignacion = value;
    }

    /**
     * Gets the value of the descripcionMovimiento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionMovimiento() {
        return descripcionMovimiento;
    }

    /**
     * Sets the value of the descripcionMovimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionMovimiento(String value) {
        this.descripcionMovimiento = value;
    }

    /**
     * Gets the value of the valorAporte property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorAporte() {
        return valorAporte;
    }

    /**
     * Sets the value of the valorAporte property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorAporte(BigDecimal value) {
        this.valorAporte = value;
    }

    /**
     * Gets the value of the terminalPago property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTerminalPago() {
        return terminalPago;
    }

    /**
     * Sets the value of the terminalPago property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerminalPago(String value) {
        this.terminalPago = value;
    }

    /**
     * Gets the value of the fechaAcreditacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaAcreditacion() {
        return fechaAcreditacion;
    }

    /**
     * Sets the value of the fechaAcreditacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaAcreditacion(String value) {
        this.fechaAcreditacion = value;
    }

    /**
     * Gets the value of the valorAplicadoVigenciasfuturas property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorAplicadoVigenciasfuturas() {
        return valorAplicadoVigenciasfuturas;
    }

    /**
     * Sets the value of the valorAplicadoVigenciasfuturas property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorAplicadoVigenciasfuturas(BigDecimal value) {
        this.valorAplicadoVigenciasfuturas = value;
    }

}
