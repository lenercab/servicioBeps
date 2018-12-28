
package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoInformacionMarcacionDivulgacion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionMarcacionDivulgacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificacion" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoDocumentoPersonaNatural"/>
 *         &lt;element name="informacionDivulgacion" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionDivulgacion"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionMarcacionDivulgacion", propOrder = {
    "identificacion",
    "informacionDivulgacion"
})
public class TipoInformacionMarcacionDivulgacion {

    @XmlElement(required = true)
    protected TipoDocumentoPersonaNatural identificacion;
    @XmlElement(required = true)
    protected TipoInformacionDivulgacion informacionDivulgacion;

    /**
     * Gets the value of the identificacion property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDocumentoPersonaNatural }
     *     
     */
    public TipoDocumentoPersonaNatural getIdentificacion() {
        return identificacion;
    }

    /**
     * Sets the value of the identificacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDocumentoPersonaNatural }
     *     
     */
    public void setIdentificacion(TipoDocumentoPersonaNatural value) {
        this.identificacion = value;
    }

    /**
     * Gets the value of the informacionDivulgacion property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionDivulgacion }
     *     
     */
    public TipoInformacionDivulgacion getInformacionDivulgacion() {
        return informacionDivulgacion;
    }

    /**
     * Sets the value of the informacionDivulgacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionDivulgacion }
     *     
     */
    public void setInformacionDivulgacion(TipoInformacionDivulgacion value) {
        this.informacionDivulgacion = value;
    }

}
