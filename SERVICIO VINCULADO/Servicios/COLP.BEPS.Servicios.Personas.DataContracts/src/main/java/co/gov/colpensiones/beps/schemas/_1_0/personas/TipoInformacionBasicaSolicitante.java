
package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.comunes.anotaciones.IgnoreNullsValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.ObjectValidator;

/**
 * <b>Descripcion:</b> Contiene la información básica del solicitante a previncular <br/>
 * <b>Caso de Uso:</b> GOP-VIN-1-FAB-11-ModificarInformacionSensibleVinculado <br/>
 * 
 * @author Yenny Nustez <ynustez@heinsohn.com.co>
 */
/**
 * <p>Java class for tipoInformacionBasicaSolicitante complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionBasicaSolicitante">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documento" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoDocumentoPersonaNatural"/>
 *         &lt;element name="nombresApellidos" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionBasicaPersonaNatural" minOccurs="0"/>
 *         &lt;element name="informacionAdicional" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionComplementariaPersonaNatural"/>
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
@XmlType(name = "tipoInformacionBasicaSolicitante", propOrder = {
    "documento",
    "nombresApellidos",
    "informacionAdicional"
})
public class TipoInformacionBasicaSolicitante {

    /** Contiene los Datos de  tipo y número de documento del solicitante a previncular    */
    @XmlElement(required = true)
    protected TipoDocumentoPersonaNatural documento;
    
    /** Contiene los datos de nombres y apellidos del solicitante a previncular     */
    @XmlElementRef(name = "nombresApellidos", namespace = "http://www.colpensiones.gov.co/beps/schemas/1.0/personas", type = JAXBElement.class, required = false)
    protected JAXBElement<TipoInformacionBasicaPersonaNatural> nombresApellidos;
    
    /** Contiene los datos complementarios del solicitante a previncular    */
    @XmlElement(nillable = false)
    protected TipoInformacionComplementariaPersonaNatural informacionAdicional;

    /**
     * Gets the value of the documento property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDocumentoPersonaNatural }
     *     
     */
    @ObjectValidator
    public TipoDocumentoPersonaNatural getDocumento() {
        return documento;
    }

    /**
     * Sets the value of the documento property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDocumentoPersonaNatural }
     *     
     */
    public void setDocumento(TipoDocumentoPersonaNatural value) {
        this.documento = value;
    }

    /**
     * Gets the value of the nombresApellidos property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link TipoInformacionBasicaPersonaNatural }{@code >}
     *     
     */
    @IgnoreNullsValidator
    @ObjectValidator
    public JAXBElement<TipoInformacionBasicaPersonaNatural> getNombresApellidos() {
        return nombresApellidos;
    }

    /**
     * Sets the value of the nombresApellidos property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link TipoInformacionBasicaPersonaNatural }{@code >}
     *     
     */
    public void setNombresApellidos(JAXBElement<TipoInformacionBasicaPersonaNatural> value) {
        this.nombresApellidos = value;
    }

    /**
     * Gets the value of the informacionAdicional property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionComplementariaPersonaNatural }
     *     
     */
    @ObjectValidator
    public TipoInformacionComplementariaPersonaNatural getInformacionAdicional() {
        return informacionAdicional;
    }

    /**
     * Sets the value of the informacionAdicional property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionComplementariaPersonaNatural }
     *     
     */
    public void setInformacionAdicional(TipoInformacionComplementariaPersonaNatural value) {
        this.informacionAdicional = value;
    }
    

}
