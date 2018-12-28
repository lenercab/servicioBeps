
package co.gov.colpensiones.beps.schemas._1_0.personas;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoInformacionAportesPesos complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionAportesPesos">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="saldoAcumuladoFechaInicioPeriodo" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="aportesPeriodo" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="totalAportesRealizados" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="saldoAcumuladoInteresesFechaInicioPeriodo" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="interesesPeriodo" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="totalInteresesRecibidosCuenta" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="administracionCuenta" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="gravamenFinanciero" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="totalRetiros" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="saldoFinal" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="totalIncentivoPeriodicoAportes" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionAportesPesos", propOrder = {
    "saldoAcumuladoFechaInicioPeriodo",
    "aportesPeriodo",
    "totalAportesRealizados",
    "saldoAcumuladoInteresesFechaInicioPeriodo",
    "interesesPeriodo",
    "totalInteresesRecibidosCuenta",
    "administracionCuenta",
    "gravamenFinanciero",
    "totalRetiros",
    "saldoFinal",
    "totalIncentivoPeriodicoAportes"
})
public class TipoInformacionAportesPesos {

    @XmlElement(required = true)
    protected BigDecimal saldoAcumuladoFechaInicioPeriodo;
    @XmlElement(required = true)
    protected BigDecimal aportesPeriodo;
    @XmlElement(required = true)
    protected BigDecimal totalAportesRealizados;
    @XmlElement(required = true)
    protected BigDecimal saldoAcumuladoInteresesFechaInicioPeriodo;
    @XmlElement(required = true)
    protected BigDecimal interesesPeriodo;
    @XmlElement(required = true)
    protected BigDecimal totalInteresesRecibidosCuenta;
    @XmlElement(required = true)
    protected BigDecimal administracionCuenta;
    @XmlElement(required = true)
    protected BigDecimal gravamenFinanciero;
    @XmlElement(required = true)
    protected BigDecimal totalRetiros;
    @XmlElement(required = true)
    protected BigDecimal saldoFinal;
    @XmlElement(required = true)
    protected BigDecimal totalIncentivoPeriodicoAportes;

    /**
     * Gets the value of the saldoAcumuladoFechaInicioPeriodo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSaldoAcumuladoFechaInicioPeriodo() {
        return saldoAcumuladoFechaInicioPeriodo;
    }

    /**
     * Sets the value of the saldoAcumuladoFechaInicioPeriodo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSaldoAcumuladoFechaInicioPeriodo(BigDecimal value) {
        this.saldoAcumuladoFechaInicioPeriodo = value;
    }

    /**
     * Gets the value of the aportesPeriodo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAportesPeriodo() {
        return aportesPeriodo;
    }

    /**
     * Sets the value of the aportesPeriodo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAportesPeriodo(BigDecimal value) {
        this.aportesPeriodo = value;
    }

    /**
     * Gets the value of the totalAportesRealizados property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalAportesRealizados() {
        return totalAportesRealizados;
    }

    /**
     * Sets the value of the totalAportesRealizados property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalAportesRealizados(BigDecimal value) {
        this.totalAportesRealizados = value;
    }

    /**
     * Gets the value of the saldoAcumuladoInteresesFechaInicioPeriodo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSaldoAcumuladoInteresesFechaInicioPeriodo() {
        return saldoAcumuladoInteresesFechaInicioPeriodo;
    }

    /**
     * Sets the value of the saldoAcumuladoInteresesFechaInicioPeriodo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSaldoAcumuladoInteresesFechaInicioPeriodo(BigDecimal value) {
        this.saldoAcumuladoInteresesFechaInicioPeriodo = value;
    }

    /**
     * Gets the value of the interesesPeriodo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getInteresesPeriodo() {
        return interesesPeriodo;
    }

    /**
     * Sets the value of the interesesPeriodo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setInteresesPeriodo(BigDecimal value) {
        this.interesesPeriodo = value;
    }

    /**
     * Gets the value of the totalInteresesRecibidosCuenta property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalInteresesRecibidosCuenta() {
        return totalInteresesRecibidosCuenta;
    }

    /**
     * Sets the value of the totalInteresesRecibidosCuenta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalInteresesRecibidosCuenta(BigDecimal value) {
        this.totalInteresesRecibidosCuenta = value;
    }

    /**
     * Gets the value of the administracionCuenta property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAdministracionCuenta() {
        return administracionCuenta;
    }

    /**
     * Sets the value of the administracionCuenta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAdministracionCuenta(BigDecimal value) {
        this.administracionCuenta = value;
    }

    /**
     * Gets the value of the gravamenFinanciero property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getGravamenFinanciero() {
        return gravamenFinanciero;
    }

    /**
     * Sets the value of the gravamenFinanciero property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setGravamenFinanciero(BigDecimal value) {
        this.gravamenFinanciero = value;
    }

    /**
     * Gets the value of the totalRetiros property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalRetiros() {
        return totalRetiros;
    }

    /**
     * Sets the value of the totalRetiros property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalRetiros(BigDecimal value) {
        this.totalRetiros = value;
    }

    /**
     * Gets the value of the saldoFinal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSaldoFinal() {
        return saldoFinal;
    }

    /**
     * Sets the value of the saldoFinal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSaldoFinal(BigDecimal value) {
        this.saldoFinal = value;
    }

    /**
     * Gets the value of the totalIncentivoPeriodicoAportes property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalIncentivoPeriodicoAportes() {
        return totalIncentivoPeriodicoAportes;
    }

    /**
     * Sets the value of the totalIncentivoPeriodicoAportes property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalIncentivoPeriodicoAportes(BigDecimal value) {
        this.totalIncentivoPeriodicoAportes = value;
    }

}
