
package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoConsultaMovimientosSaldo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoConsultaMovimientosSaldo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="informacionVinculado" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionVinculado"/>
 *         &lt;element name="informacionCuenta" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionCuenta"/>
 *         &lt;element name="informacionMovimientos" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionTransacciones"/>
 *         &lt;element name="InformacionAportesPesos" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionAportesPesos"/>
 *         &lt;element name="informacionAportesUnidades" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionAportesUnidades"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoConsultaMovimientosSaldo", propOrder = {
    "informacionVinculado",
    "informacionCuenta",
    "informacionMovimientos",
    "informacionAportesPesos",
    "informacionAportesUnidades"
})
public class TipoConsultaMovimientosSaldo {

    @XmlElement(required = true)
    protected TipoInformacionVinculado informacionVinculado;
    @XmlElement(required = true)
    protected TipoInformacionCuenta informacionCuenta;
    @XmlElement(required = true)
    protected TipoInformacionTransacciones informacionMovimientos;
    @XmlElement(name = "InformacionAportesPesos", required = true)
    protected TipoInformacionAportesPesos informacionAportesPesos;
    @XmlElement(required = true)
    protected TipoInformacionAportesUnidades informacionAportesUnidades;

    /**
     * Gets the value of the informacionVinculado property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionVinculado }
     *     
     */
    public TipoInformacionVinculado getInformacionVinculado() {
        return informacionVinculado;
    }

    /**
     * Sets the value of the informacionVinculado property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionVinculado }
     *     
     */
    public void setInformacionVinculado(TipoInformacionVinculado value) {
        this.informacionVinculado = value;
    }

    /**
     * Gets the value of the informacionCuenta property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionCuenta }
     *     
     */
    public TipoInformacionCuenta getInformacionCuenta() {
        return informacionCuenta;
    }

    /**
     * Sets the value of the informacionCuenta property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionCuenta }
     *     
     */
    public void setInformacionCuenta(TipoInformacionCuenta value) {
        this.informacionCuenta = value;
    }

    /**
     * Gets the value of the informacionMovimientos property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionTransacciones }
     *     
     */
    public TipoInformacionTransacciones getInformacionMovimientos() {
        return informacionMovimientos;
    }

    /**
     * Sets the value of the informacionMovimientos property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionTransacciones }
     *     
     */
    public void setInformacionMovimientos(TipoInformacionTransacciones value) {
        this.informacionMovimientos = value;
    }

    /**
     * Gets the value of the informacionAportesPesos property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionAportesPesos }
     *     
     */
    public TipoInformacionAportesPesos getInformacionAportesPesos() {
        return informacionAportesPesos;
    }

    /**
     * Sets the value of the informacionAportesPesos property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionAportesPesos }
     *     
     */
    public void setInformacionAportesPesos(TipoInformacionAportesPesos value) {
        this.informacionAportesPesos = value;
    }

    /**
     * Gets the value of the informacionAportesUnidades property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionAportesUnidades }
     *     
     */
    public TipoInformacionAportesUnidades getInformacionAportesUnidades() {
        return informacionAportesUnidades;
    }

    /**
     * Sets the value of the informacionAportesUnidades property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionAportesUnidades }
     *     
     */
    public void setInformacionAportesUnidades(TipoInformacionAportesUnidades value) {
        this.informacionAportesUnidades = value;
    }

}
