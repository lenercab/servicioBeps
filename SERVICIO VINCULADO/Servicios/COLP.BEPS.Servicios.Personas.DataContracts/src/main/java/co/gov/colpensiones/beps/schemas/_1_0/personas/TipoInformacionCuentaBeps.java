
package co.gov.colpensiones.beps.schemas._1_0.personas;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoInformacionCuentaBeps complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionCuentaBeps">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="estadoVinculacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valorAhorroBeps" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="valorRendimientosBeps" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="valorSubsidiBeps" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="fechaCorteCuentaIndividual" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valorTraslados" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionCuentaBeps", propOrder = {
    "estadoVinculacion",
    "valorAhorroBeps",
    "valorRendimientosBeps",
    "valorSubsidiBeps",
    "fechaCorteCuentaIndividual",
    "valorTraslados"
})
public class TipoInformacionCuentaBeps {

    @XmlElement(nillable = true)
    protected String estadoVinculacion;
    @XmlElement(nillable = true)
    protected BigDecimal valorAhorroBeps;
    @XmlElement(nillable = true)
    protected BigDecimal valorRendimientosBeps;
    @XmlElement(nillable = true)
    protected BigDecimal valorSubsidiBeps;
    @XmlElement(nillable = true)
    protected String fechaCorteCuentaIndividual;
    @XmlElement(nillable = true)
    protected BigDecimal valorTraslados;

    /**
     * Gets the value of the estadoVinculacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstadoVinculacion() {
        return estadoVinculacion;
    }

    /**
     * Sets the value of the estadoVinculacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstadoVinculacion(String value) {
        this.estadoVinculacion = value;
    }

    /**
     * Gets the value of the valorAhorroBeps property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorAhorroBeps() {
        return valorAhorroBeps;
    }

    /**
     * Sets the value of the valorAhorroBeps property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorAhorroBeps(BigDecimal value) {
        this.valorAhorroBeps = value;
    }

    /**
     * Gets the value of the valorRendimientosBeps property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorRendimientosBeps() {
        return valorRendimientosBeps;
    }

    /**
     * Sets the value of the valorRendimientosBeps property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorRendimientosBeps(BigDecimal value) {
        this.valorRendimientosBeps = value;
    }

    /**
     * Gets the value of the valorSubsidiBeps property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorSubsidiBeps() {
        return valorSubsidiBeps;
    }

    /**
     * Sets the value of the valorSubsidiBeps property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorSubsidiBeps(BigDecimal value) {
        this.valorSubsidiBeps = value;
    }

    /**
     * Gets the value of the fechaCorteCuentaIndividual property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaCorteCuentaIndividual() {
        return fechaCorteCuentaIndividual;
    }

    /**
     * Sets the value of the fechaCorteCuentaIndividual property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaCorteCuentaIndividual(String value) {
        this.fechaCorteCuentaIndividual = value;
    }
    
    /**
     * Gets the value of the valorTraslados property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorTraslados() {
        return valorTraslados;
    }

    /**
     * Sets the value of the valorTraslados property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorTraslados(BigDecimal value) {
        this.valorTraslados = value;
    }

}
