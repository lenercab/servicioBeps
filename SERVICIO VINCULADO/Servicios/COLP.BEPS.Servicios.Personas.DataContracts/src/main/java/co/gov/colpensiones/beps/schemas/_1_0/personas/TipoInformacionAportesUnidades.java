
package co.gov.colpensiones.beps.schemas._1_0.personas;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoInformacionAportesUnidades complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionAportesUnidades">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numeroUnidadesFechaInicioPeriodo" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="valorInicialUnidadPeriodo" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="totalUnidadesAportesRealizados" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="valorFinalUnidad" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionAportesUnidades", propOrder = {
    "numeroUnidadesFechaInicioPeriodo",
    "valorInicialUnidadPeriodo",
    "totalUnidadesAportesRealizados",
    "valorFinalUnidad"
})
public class TipoInformacionAportesUnidades {

    @XmlElement(required = true)
    protected BigDecimal numeroUnidadesFechaInicioPeriodo;
    @XmlElement(required = true)
    protected BigDecimal valorInicialUnidadPeriodo;
    @XmlElement(required = true)
    protected BigDecimal totalUnidadesAportesRealizados;
    @XmlElement(required = true)
    protected BigDecimal valorFinalUnidad;

    /**
     * Gets the value of the numeroUnidadesFechaInicioPeriodo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNumeroUnidadesFechaInicioPeriodo() {
        return numeroUnidadesFechaInicioPeriodo;
    }

    /**
     * Sets the value of the numeroUnidadesFechaInicioPeriodo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNumeroUnidadesFechaInicioPeriodo(BigDecimal value) {
        this.numeroUnidadesFechaInicioPeriodo = value;
    }

    /**
     * Gets the value of the valorInicialUnidadPeriodo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorInicialUnidadPeriodo() {
        return valorInicialUnidadPeriodo;
    }

    /**
     * Sets the value of the valorInicialUnidadPeriodo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorInicialUnidadPeriodo(BigDecimal value) {
        this.valorInicialUnidadPeriodo = value;
    }

    /**
     * Gets the value of the totalUnidadesAportesRealizados property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalUnidadesAportesRealizados() {
        return totalUnidadesAportesRealizados;
    }

    /**
     * Sets the value of the totalUnidadesAportesRealizados property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalUnidadesAportesRealizados(BigDecimal value) {
        this.totalUnidadesAportesRealizados = value;
    }

    /**
     * Gets the value of the valorFinalUnidad property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorFinalUnidad() {
        return valorFinalUnidad;
    }

    /**
     * Sets the value of the valorFinalUnidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorFinalUnidad(BigDecimal value) {
        this.valorFinalUnidad = value;
    }

}
