
package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstadoEjecucion;


/**
 * <p>Java class for tipoRespuestaConsultaMovimientosSaldoDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoRespuestaConsultaMovimientosSaldoDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="detalle" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoConsultaMovimientosSaldo"/>
 *         &lt;element name="estadoEjecucion" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/comun}tipoEstadoEjecucion"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoRespuestaConsultaMovimientosSaldoDTO", propOrder = {
    "detalle",
    "estadoEjecucion"
})
public class TipoRespuestaConsultaMovimientosSaldoDTO {

    @XmlElement(required = true, nillable = true)
    protected TipoConsultaMovimientosSaldo detalle;
    @XmlElement(required = true)
    protected TipoEstadoEjecucion estadoEjecucion;

    /**
     * Gets the value of the detalle property.
     * 
     * @return
     *     possible object is
     *     {@link TipoConsultaMovimientosSaldo }
     *     
     */
    public TipoConsultaMovimientosSaldo getDetalle() {
        return detalle;
    }

    /**
     * Sets the value of the detalle property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoConsultaMovimientosSaldo }
     *     
     */
    public void setDetalle(TipoConsultaMovimientosSaldo value) {
        this.detalle = value;
    }

    /**
     * Gets the value of the estadoEjecucion property.
     * 
     * @return
     *     possible object is
     *     {@link TipoEstadoEjecucion }
     *     
     */
    public TipoEstadoEjecucion getEstadoEjecucion() {
        return estadoEjecucion;
    }

    /**
     * Sets the value of the estadoEjecucion property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoEstadoEjecucion }
     *     
     */
    public void setEstadoEjecucion(TipoEstadoEjecucion value) {
        this.estadoEjecucion = value;
    }

}
