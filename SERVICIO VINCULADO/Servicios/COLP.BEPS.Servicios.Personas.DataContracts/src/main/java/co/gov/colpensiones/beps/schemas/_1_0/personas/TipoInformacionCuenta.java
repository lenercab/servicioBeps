
package co.gov.colpensiones.beps.schemas._1_0.personas;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoInformacionCuenta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionCuenta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaApertura" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaCierre" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="motivoCierreCancelacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valorTasaEfectivaAnual" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="fechaInicial" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaFinal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionCuenta", propOrder = {
    "numero",
    "estado",
    "fechaApertura",
    "fechaCierre",
    "motivoCierreCancelacion",
    "valorTasaEfectivaAnual",
    "fechaInicial",
    "fechaFinal"
})
public class TipoInformacionCuenta {

    @XmlElement(required = true)
    protected String numero;
    @XmlElement(required = true)
    protected String estado;
    @XmlElement(required = true)
    protected String fechaApertura;
    @XmlElement(required = true, nillable = true)
    protected String fechaCierre;
    @XmlElement(required = true, nillable = true)
    protected String motivoCierreCancelacion;
    @XmlElement(required = true)
    protected BigDecimal valorTasaEfectivaAnual;
    @XmlElement(required = true, nillable = true)
    protected String fechaInicial;
    @XmlElement(required = true, nillable = true)
    protected String fechaFinal;

    /**
     * Gets the value of the numero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Sets the value of the numero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumero(String value) {
        this.numero = value;
    }

    /**
     * Gets the value of the estado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the value of the estado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstado(String value) {
        this.estado = value;
    }

    /**
     * Gets the value of the fechaApertura property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaApertura() {
        return fechaApertura;
    }

    /**
     * Sets the value of the fechaApertura property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaApertura(String value) {
        this.fechaApertura = value;
    }

    /**
     * Gets the value of the fechaCierre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaCierre() {
        return fechaCierre;
    }

    /**
     * Sets the value of the fechaCierre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaCierre(String value) {
        this.fechaCierre = value;
    }

    /**
     * Gets the value of the motivoCierreCancelacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivoCierreCancelacion() {
        return motivoCierreCancelacion;
    }

    /**
     * Sets the value of the motivoCierreCancelacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivoCierreCancelacion(String value) {
        this.motivoCierreCancelacion = value;
    }

    /**
     * Gets the value of the valorTasaEfectivaAnual property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorTasaEfectivaAnual() {
        return valorTasaEfectivaAnual;
    }

    /**
     * Sets the value of the valorTasaEfectivaAnual property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorTasaEfectivaAnual(BigDecimal value) {
        this.valorTasaEfectivaAnual = value;
    }

    /**
     * Gets the value of the fechaInicial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaInicial() {
        return fechaInicial;
    }

    /**
     * Sets the value of the fechaInicial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaInicial(String value) {
        this.fechaInicial = value;
    }

    /**
     * Gets the value of the fechaFinal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaFinal() {
        return fechaFinal;
    }

    /**
     * Sets the value of the fechaFinal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaFinal(String value) {
        this.fechaFinal = value;
    }

}
