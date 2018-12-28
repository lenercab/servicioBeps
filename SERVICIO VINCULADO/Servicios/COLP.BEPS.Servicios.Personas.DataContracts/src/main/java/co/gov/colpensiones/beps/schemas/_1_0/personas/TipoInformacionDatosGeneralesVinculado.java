
package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.comunes.anotaciones.NotNullValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.ObjectValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.StringLengthValidator;

/**
 * <b>Descripcion:</b> Contiene la estructura que almacena la información general del vinculado que se desea modificar <br/>
 * <b>Caso de Uso:</b> GOP-VIN-1-FAB-11-ModificarInformacionSensibleVinculado <br/>
 * 
 * @author Yenny Nustez <ynustez@heinsohn.com.co>
 */
/**
 * <p>Java class for tipoInformacionDatosGeneralesVinculado complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionDatosGeneralesVinculado">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificacion" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoDocumentoPersonaNatural"/>
 *         &lt;element name="informacionUbicacionResidencia" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionUbicacionPersona"/>
 *         &lt;element name="informacionEconomica" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionEconomica"/>
 *         &lt;element name="tipoAutorizacion" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoAutorizacion"/>
 *         &lt;element name="canalModificacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "tipoInformacionDatosGeneralesVinculado", propOrder = {
    "identificacion",
    "informacionUbicacionResidencia",
    "informacionEconomica",
    "tipoAutorizacion",
    "canalModificacion"
})
public class TipoInformacionDatosGeneralesVinculado {

    /** Contiene la identificacion del vinculado      */
    @XmlElement(required = true)
    protected TipoDocumentoPersonaNatural identificacion;
    
    /** Contiene la ubicación de la residencia     */
    @XmlElement(nillable = true)
    protected TipoInformacionUbicacionPersona informacionUbicacionResidencia;
    
    /** Contiene la información económica     */
    @XmlElement(required = true)
    protected TipoInformacionEconomica informacionEconomica;
    
    /** Contiene las autorizaciones que da el Vinculado     */
    @XmlElement(required = true)
    protected TipoAutorizacion tipoAutorizacion;
    
    /** Canal por el cual se realizó la modificación     */
    @XmlElement(required = true)
    protected String canalModificacion;

    /**
     * Gets the value of the identificacion property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDocumentoPersonaNatural }
     *     
     */
    @ObjectValidator
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
     * Gets the value of the informacionUbicacionResidencia property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionUbicacionPersona }
     *     
     */
    @ObjectValidator
    public TipoInformacionUbicacionPersona getInformacionUbicacionResidencia() {
        return informacionUbicacionResidencia;
    }

    /**
     * Sets the value of the informacionUbicacionResidencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionUbicacionPersona }
     *     
     */
    public void setInformacionUbicacionResidencia(TipoInformacionUbicacionPersona value) {
        this.informacionUbicacionResidencia = value;
    }

    /**
     * Gets the value of the informacionEconomica property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionEconomica }
     *     
     */
    @ObjectValidator
    public TipoInformacionEconomica getInformacionEconomica() {
        return informacionEconomica;
    }

    /**
     * Sets the value of the informacionEconomica property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionEconomica }
     *     
     */
    public void setInformacionEconomica(TipoInformacionEconomica value) {
        this.informacionEconomica = value;
    }

    /**
     * Gets the value of the tipoAutorizacion property.
     * 
     * @return
     *     possible object is
     *     {@link TipoAutorizacion }
     *     
     */
    @ObjectValidator
    public TipoAutorizacion getTipoAutorizacion() {
        return tipoAutorizacion;
    }

    /**
     * Sets the value of the tipoAutorizacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoAutorizacion }
     *     
     */
    public void setTipoAutorizacion(TipoAutorizacion value) {
        this.tipoAutorizacion = value;
    }

    /**
     * Gets the value of the canalModificacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @NotNullValidator
    @StringLengthValidator(min = 2, max = 2, message = "{0} debe tener una longitud de 2 caracteres") 
    public String getCanalModificacion() {
        return canalModificacion;
    }

    /**
     * Sets the value of the canalModificacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanalModificacion(String value) {
        this.canalModificacion = value;
    }

}
