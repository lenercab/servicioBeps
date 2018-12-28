
package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.comunes.anotaciones.DateValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.NotNullValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.ObjectValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.StringLengthValidator;


/**
 * <p>Java class for tipoInformacionGeneralVinculado complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionGeneralVinculado">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificacion" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoDocumentoPersonaNatural"/>
 *         &lt;element name="informacionUbicacionResidencia" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionUbicacionPersona"/>
 *         &lt;element name="fechaVinculacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="informacionEconomica" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionEconomica"/>
 *         &lt;element name="tipoAutorizacion" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoAutorizacion"/>
 *         &lt;element name="usuarioVinculacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="canal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionGeneralVinculado", propOrder = {
    "identificacion",
    "informacionUbicacionResidencia",
    "fechaVinculacion",
    "informacionEconomica",
    "tipoAutorizacion",
    "usuarioVinculacion",
    "canal"
})
@XmlRootElement
public class TipoInformacionGeneralVinculado {

    @XmlElement
    protected TipoDocumentoPersonaNatural identificacion;
    @XmlElement
    protected TipoInformacionUbicacionPersona informacionUbicacionResidencia;
    @XmlElement
    protected String fechaVinculacion;
    @XmlElement
    protected TipoInformacionEconomica informacionEconomica;
    @XmlElement
    protected TipoAutorizacion tipoAutorizacion;
    @XmlElement
    protected String usuarioVinculacion;
    @XmlElement
    protected String canal;

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
     * Gets the value of the fechaVinculacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @StringLengthValidator(min = 8, max = 8, message = "{0} debe tener una longitud de 8 caracteres")
    @DateValidator
    public String getFechaVinculacion() {
        return fechaVinculacion;
    }

    /**
     * Sets the value of the fechaVinculacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaVinculacion(String value) {
        this.fechaVinculacion = value;
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
     * Gets the value of the usuarioVinculacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @NotNullValidator
    @StringLengthValidator(min = 5, max = 60, message = "{0} debe tener una longitud de 5 a 60 caracteres")
    public String getUsuarioVinculacion() {
        return usuarioVinculacion;
    }

    /**
     * Sets the value of the usuarioVinculacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsuarioVinculacion(String value) {
        this.usuarioVinculacion = value;
    }

    /**
     * Devuelve el valor de canal
     * @return El valor de canal
     */
    @NotNullValidator
    @StringLengthValidator(min = 2, max = 2, message = "{0} debe tener una longitud de 2 caracteres")        
    public String getCanal() {
        return canal;
    }

    /**
     * Establece el valor de canal
     * @param canal El valor por establecer para canal
     */
    public void setCanal(String canal) {
        this.canal = canal;
    }

    
}
