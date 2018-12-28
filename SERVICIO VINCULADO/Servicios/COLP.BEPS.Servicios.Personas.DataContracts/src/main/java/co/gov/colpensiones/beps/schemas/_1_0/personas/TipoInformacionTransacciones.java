
package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoInformacionTransacciones complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionTransacciones">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="movimientosAhorrosPropios" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionTransaccionMovimientos"/>
 *         &lt;element name="movimientosPatrocinadores" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionTransaccionMovimientos"/>
 *         &lt;element name="movimientosTrasladosSGP" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionTransaccionMovimientos"/>
 *         &lt;element name="movimientosRetiros" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionTransaccionMovimientos"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionTransacciones", propOrder = {
    "movimientosAhorrosPropios",
    "movimientosPatrocinadores",
    "movimientosTrasladosSGP",
    "movimientosRetiros"
})
public class TipoInformacionTransacciones {

    @XmlElement(required = true, nillable = true)
    protected TipoInformacionTransaccionMovimientos movimientosAhorrosPropios;
    @XmlElement(required = true, nillable = true)
    protected TipoInformacionTransaccionMovimientos movimientosPatrocinadores;
    @XmlElement(required = true, nillable = true)
    protected TipoInformacionTransaccionMovimientos movimientosTrasladosSGP;
    @XmlElement(required = true, nillable = true)
    protected TipoInformacionTransaccionMovimientos movimientosRetiros;

    /**
     * Gets the value of the movimientosAhorrosPropios property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionTransaccionMovimientos }
     *     
     */
    public TipoInformacionTransaccionMovimientos getMovimientosAhorrosPropios() {
        return movimientosAhorrosPropios;
    }

    /**
     * Sets the value of the movimientosAhorrosPropios property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionTransaccionMovimientos }
     *     
     */
    public void setMovimientosAhorrosPropios(TipoInformacionTransaccionMovimientos value) {
        this.movimientosAhorrosPropios = value;
    }

    /**
     * Gets the value of the movimientosPatrocinadores property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionTransaccionMovimientos }
     *     
     */
    public TipoInformacionTransaccionMovimientos getMovimientosPatrocinadores() {
        return movimientosPatrocinadores;
    }

    /**
     * Sets the value of the movimientosPatrocinadores property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionTransaccionMovimientos }
     *     
     */
    public void setMovimientosPatrocinadores(TipoInformacionTransaccionMovimientos value) {
        this.movimientosPatrocinadores = value;
    }

    /**
     * Gets the value of the movimientosTrasladosSGP property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionTransaccionMovimientos }
     *     
     */
    public TipoInformacionTransaccionMovimientos getMovimientosTrasladosSGP() {
        return movimientosTrasladosSGP;
    }

    /**
     * Sets the value of the movimientosTrasladosSGP property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionTransaccionMovimientos }
     *     
     */
    public void setMovimientosTrasladosSGP(TipoInformacionTransaccionMovimientos value) {
        this.movimientosTrasladosSGP = value;
    }

    /**
     * Gets the value of the movimientosRetiros property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionTransaccionMovimientos }
     *     
     */
    public TipoInformacionTransaccionMovimientos getMovimientosRetiros() {
        return movimientosRetiros;
    }

    /**
     * Sets the value of the movimientosRetiros property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionTransaccionMovimientos }
     *     
     */
    public void setMovimientosRetiros(TipoInformacionTransaccionMovimientos value) {
        this.movimientosRetiros = value;
    }

}
