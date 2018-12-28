
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
 * <p>Java class for tipoInformacionPrevinculado complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionPrevinculado">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="informacionBasicaSolicitante" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionBasicaSolicitante"/>
 *         &lt;element name="informacionUbicacion" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionUbicacionPersona"/>
 *         &lt;element name="informacionSisben" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionBasicaSisben"/>
 *         &lt;element name="informacionEconomica" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionEconomica"/>
 *         &lt;element name="tipoAutorizacion" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoAutorizacion"/>
 *         &lt;element name="canal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="usuarioPrevinculacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "tipoInformacionPrevinculado", propOrder = {
    "informacionBasicaSolicitante",
    "informacionUbicacion",
    "informacionSisben",
    "informacionEconomica",
    "tipoAutorizacion",
    "canal",
    "usuarioPrevinculacion"
})
public class TipoInformacionPrevinculado {

    @XmlElement(required = true)
    protected TipoInformacionBasicaSolicitante informacionBasicaSolicitante;
    @XmlElement(required = true)
    protected TipoInformacionUbicacionPersona informacionUbicacion;
    @XmlElement(required = true)
    protected TipoInformacionBasicaSisben informacionSisben;
    @XmlElement(required = true)
    protected TipoInformacionEconomica informacionEconomica;
    @XmlElement(required = true)
    protected TipoAutorizacion tipoAutorizacion;
    @XmlElement(required = true)
    protected String canal;
    @XmlElement(required = true)
    protected String usuarioPrevinculacion;

    /**
     * Gets the value of the informacionBasicaSolicitante property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionBasicaSolicitante }
     *     
     */
    @NotNullValidator
    @ObjectValidator
    public TipoInformacionBasicaSolicitante getInformacionBasicaSolicitante() {
        return informacionBasicaSolicitante;
    }

    /**
     * Sets the value of the informacionBasicaSolicitante property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionBasicaSolicitante }
     *     
     */
    public void setInformacionBasicaSolicitante(TipoInformacionBasicaSolicitante value) {
        this.informacionBasicaSolicitante = value;
    }

    /**
     * Gets the value of the informacionUbicacion property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionUbicacionPersona }
     *     
     */
    @NotNullValidator
    @ObjectValidator
    public TipoInformacionUbicacionPersona getInformacionUbicacion() {
        return informacionUbicacion;
    }

    /**
     * Sets the value of the informacionUbicacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionUbicacionPersona }
     *     
     */
    public void setInformacionUbicacion(TipoInformacionUbicacionPersona value) {
        this.informacionUbicacion = value;
    }

    /**
     * Gets the value of the informacionSisben property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionBasicaSisben }
     *     
     */
    @NotNullValidator
    @ObjectValidator
    public TipoInformacionBasicaSisben getInformacionSisben() {
        return informacionSisben;
    }

    /**
     * Sets the value of the informacionSisben property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionBasicaSisben }
     *     
     */
    public void setInformacionSisben(TipoInformacionBasicaSisben value) {
        this.informacionSisben = value;
    }

    /**
     * Gets the value of the informacionEconomica property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionEconomica }
     *     
     */
    @NotNullValidator
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
    @NotNullValidator
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
     * Gets the value of the canal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @NotNullValidator
    @StringLengthValidator(min = 2, max = 2, message = "{0} debe tener una longitud de 2 caracteres")
    public String getCanal() {
        return canal;
    }

    /**
     * Sets the value of the canal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanal(String value) {
        this.canal = value;
    }

    /**
     * Gets the value of the usuarioPrevinculacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @NotNullValidator
    @StringLengthValidator(min = 5, max = 60, message = "{0} debe tener una longitud de 5 a 60 caracteres")
    public String getUsuarioPrevinculacion() {
        return usuarioPrevinculacion;
    }

    /**
     * Sets the value of the usuarioPrevinculacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsuarioPrevinculacion(String value) {
        this.usuarioPrevinculacion = value;
    }

}
