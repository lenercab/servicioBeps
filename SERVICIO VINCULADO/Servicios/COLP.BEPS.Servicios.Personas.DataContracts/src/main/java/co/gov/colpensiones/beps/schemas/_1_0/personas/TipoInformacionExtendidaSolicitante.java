
package co.gov.colpensiones.beps.schemas._1_0.personas;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoInformacionExtendidaSolicitante complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionExtendidaSolicitante">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoCiudadano" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="colombiaMayor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="afp" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valorIndemnizacionDevolucionSaldos" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="valorBeps" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="semanasCotizadas" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="fechaCorteSemanas" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaVinculacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaPrevinculacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroRadicado" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "tipoInformacionExtendidaSolicitante", propOrder = {
    "tipoCiudadano",
    "colombiaMayor",
    "afp",
    "valorIndemnizacionDevolucionSaldos",
    "valorBeps",
    "semanasCotizadas",
    "fechaCorteSemanas",
    "fechaVinculacion",
    "fechaPrevinculacion",
    "numeroRadicado",
    "canalDivulgacion",
    "fechaDivulgacion"
})
public class TipoInformacionExtendidaSolicitante {

    @XmlElement(nillable = true)
    protected String tipoCiudadano;
    @XmlElement(nillable = true)
    protected String colombiaMayor;
    @XmlElement(nillable = true)
    protected String afp;
    @XmlElement(nillable = true)
    protected BigDecimal valorIndemnizacionDevolucionSaldos;
    @XmlElement(nillable = true)
    protected BigDecimal valorBeps;
    @XmlElement(nillable = true)
    protected BigDecimal semanasCotizadas;
    @XmlElement(nillable = true)
    protected String fechaCorteSemanas;
    @XmlElement(nillable = true)
    protected String fechaVinculacion;
    @XmlElement(nillable = true)
    protected String fechaPrevinculacion;
    @XmlElement(nillable = true)
    protected String numeroRadicado;
    @XmlElement(nillable = true)
    protected String canalDivulgacion;
    @XmlElement(nillable = true)
    protected String fechaDivulgacion;

    /**
     * Gets the value of the tipoCiudadano property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoCiudadano() {
        return tipoCiudadano;
    }

    /**
     * Sets the value of the tipoCiudadano property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoCiudadano(String value) {
        this.tipoCiudadano = value;
    }

    /**
     * Gets the value of the colombiaMayor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColombiaMayor() {
        return colombiaMayor;
    }

    /**
     * Sets the value of the colombiaMayor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColombiaMayor(String value) {
        this.colombiaMayor = value;
    }

    /**
     * Gets the value of the afp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAfp() {
        return afp;
    }

    /**
     * Sets the value of the afp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAfp(String value) {
        this.afp = value;
    }

    /**
     * Gets the value of the valorIndemnizacionDevolucionSaldos property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorIndemnizacionDevolucionSaldos() {
        return valorIndemnizacionDevolucionSaldos;
    }

    /**
     * Sets the value of the valorIndemnizacionDevolucionSaldos property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorIndemnizacionDevolucionSaldos(BigDecimal value) {
        this.valorIndemnizacionDevolucionSaldos = value;
    }

    /**
     * Gets the value of the valorBeps property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorBeps() {
        return valorBeps;
    }

    /**
     * Sets the value of the valorBeps property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorBeps(BigDecimal value) {
        this.valorBeps = value;
    }

    /**
     * Gets the value of the semanasCotizadas property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSemanasCotizadas() {
        return semanasCotizadas;
    }

    /**
     * Sets the value of the semanasCotizadas property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSemanasCotizadas(BigDecimal value) {
        this.semanasCotizadas = value;
    }

    /**
     * Gets the value of the fechaCorteSemanas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaCorteSemanas() {
        return fechaCorteSemanas;
    }

    /**
     * Sets the value of the fechaCorteSemanas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaCorteSemanas(String value) {
        this.fechaCorteSemanas = value;
    }

    /**
     * Gets the value of the fechaVinculacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaVinculacion() {
        return fechaVinculacion;
    }

    /**
     * Sets the value of the fechaVinculacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaVinculacion(String value) {
        this.fechaVinculacion = value;
    }

    /**
     * Gets the value of the fechaPrevinculacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaPrevinculacion() {
        return fechaPrevinculacion;
    }

    /**
     * Sets the value of the fechaPrevinculacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaPrevinculacion(String value) {
        this.fechaPrevinculacion = value;
    }

    /**
     * Gets the value of the numeroRadicado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroRadicado() {
        return numeroRadicado;
    }

    /**
     * Sets the value of the numeroRadicado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroRadicado(String value) {
        this.numeroRadicado = value;
    }
    
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
