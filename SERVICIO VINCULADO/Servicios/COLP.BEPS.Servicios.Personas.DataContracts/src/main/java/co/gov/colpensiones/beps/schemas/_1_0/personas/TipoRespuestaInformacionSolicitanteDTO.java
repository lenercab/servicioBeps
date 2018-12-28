
package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstadoEjecucion;


/**
 * <p>Java class for tipoRespuestaInformacionSolicitanteDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoRespuestaInformacionSolicitanteDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="detalle" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionGeneralSolicitanteDTO"/>
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
@XmlType(name = "tipoRespuestaInformacionSolicitanteDTO", propOrder = {
    "detalle",
    "estadoEjecucion"
})
public class TipoRespuestaInformacionSolicitanteDTO {

    @XmlElement(required = false)
    protected TipoInformacionGeneralSolicitanteDTO detalle;
    @XmlElement(required = true)
    protected TipoEstadoEjecucion estadoEjecucion;

    /**
     * Gets the value of the detalle property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionGeneralSolicitanteDTO }
     *     
     */
    public TipoInformacionGeneralSolicitanteDTO getDetalle() {
        return detalle;
    }

    /**
     * Sets the value of the detalle property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionGeneralSolicitanteDTO }
     *     
     */
    public void setDetalle(TipoInformacionGeneralSolicitanteDTO value) {
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
