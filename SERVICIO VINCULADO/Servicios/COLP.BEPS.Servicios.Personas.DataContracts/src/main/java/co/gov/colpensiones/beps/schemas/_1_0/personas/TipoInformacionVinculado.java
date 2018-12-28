
package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoInformacionVinculado complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionVinculado">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificacion" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoDocumentoPersonaNatural"/>
 *         &lt;element name="nombresApellidos" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionBasicaPersonaNatural"/>
 *         &lt;element name="informacionAdicional" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionAdicional"/>
 *         &lt;element name="informacionContacto" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionContacto"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionVinculado", propOrder = {
    "identificacion",
    "nombresApellidos",
    "informacionAdicional",
    "informacionContacto"
})
public class TipoInformacionVinculado {

    @XmlElement(required = true)
    protected TipoDocumentoPersonaNatural identificacion;
    @XmlElement(required = true)
    protected TipoInformacionBasicaPersonaNatural nombresApellidos;
    @XmlElement(required = true)
    protected TipoInformacionAdicional informacionAdicional;
    @XmlElement(required = true)
    protected TipoInformacionContacto informacionContacto;

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
     * Gets the value of the nombresApellidos property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionBasicaPersonaNatural }
     *     
     */
    public TipoInformacionBasicaPersonaNatural getNombresApellidos() {
        return nombresApellidos;
    }

    /**
     * Sets the value of the nombresApellidos property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionBasicaPersonaNatural }
     *     
     */
    public void setNombresApellidos(TipoInformacionBasicaPersonaNatural value) {
        this.nombresApellidos = value;
    }

    /**
     * Gets the value of the informacionAdicional property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionAdicional }
     *     
     */
    public TipoInformacionAdicional getInformacionAdicional() {
        return informacionAdicional;
    }

    /**
     * Sets the value of the informacionAdicional property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionAdicional }
     *     
     */
    public void setInformacionAdicional(TipoInformacionAdicional value) {
        this.informacionAdicional = value;
    }

    /**
     * Gets the value of the informacionContacto property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionContacto }
     *     
     */
    public TipoInformacionContacto getInformacionContacto() {
        return informacionContacto;
    }

    /**
     * Sets the value of the informacionContacto property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionContacto }
     *     
     */
    public void setInformacionContacto(TipoInformacionContacto value) {
        this.informacionContacto = value;
    }

}
