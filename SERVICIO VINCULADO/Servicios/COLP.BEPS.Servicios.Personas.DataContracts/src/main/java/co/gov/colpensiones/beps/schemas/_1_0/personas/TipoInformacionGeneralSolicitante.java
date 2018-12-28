
package co.gov.colpensiones.beps.schemas._1_0.personas;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoInformacionGeneralSolicitante complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionGeneralSolicitante">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoPersona" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="informacionBasicaSolicitante" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionBasicaSolicitante"/>
 *         &lt;element name="informacionUbicacion" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionUbicacionPersona"/>
 *         &lt;element name="estadoSisben" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="informacionBasicaSisben" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionBasicaSisben"/>
 *         &lt;element name="informacionEconomica" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionEconomica"/>
 *         &lt;element name="informacionAutorizacion" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoAutorizacion"/>
 *         &lt;element name="informacionViablidad" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionViabilidad"/>
 *         &lt;element name="informacionExtendidaSolicitante" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionExtendidaSolicitante"/>
 *         &lt;element name="informacionDivulgacion" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionDivulgacion"/>
 *         &lt;element name="listaPatrocinadores" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoPatrocinador" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="informacionCuentaBeps" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionCuentaBeps"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionGeneralSolicitante", propOrder = {
    "tipoPersona",
    "informacionBasicaSolicitante",
    "informacionUbicacion",
    "estadoSisben",
    "informacionBasicaSisben",
    "informacionEconomica",
    "informacionAutorizacion",
    "informacionViablidad",
    "informacionExtendidaSolicitante",
    "informacionDivulgacion",
    "listaPatrocinadores",
    "informacionCuentaBeps"
})
public class TipoInformacionGeneralSolicitante {

    @XmlElement(required = true)
    protected String tipoPersona;
    @XmlElement(required = true)
    protected TipoInformacionBasicaSolicitante informacionBasicaSolicitante;
    @XmlElement(required = true)
    protected TipoInformacionUbicacionPersona informacionUbicacion;
    @XmlElement(required = true)
    protected String estadoSisben;
    @XmlElement(required = true)
    protected TipoInformacionBasicaSisben informacionBasicaSisben;
    @XmlElement(required = true)
    protected TipoInformacionEconomica informacionEconomica;
    @XmlElement(required = true)
    protected TipoAutorizacion informacionAutorizacion;
    @XmlElement(required = true)
    protected TipoInformacionViabilidad informacionViablidad;
    @XmlElement(required = true)
    protected TipoInformacionExtendidaSolicitante informacionExtendidaSolicitante;
    @XmlElement(required = true)
    protected TipoInformacionDivulgacion informacionDivulgacion;
    @XmlElement(nillable = true)
    protected List<TipoPatrocinador> listaPatrocinadores;
    @XmlElement(required = true)
    protected TipoInformacionCuentaBeps informacionCuentaBeps;

    /**
     * Gets the value of the tipoPersona property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoPersona() {
        return tipoPersona;
    }

    /**
     * Sets the value of the tipoPersona property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoPersona(String value) {
        this.tipoPersona = value;
    }

    /**
     * Gets the value of the informacionBasicaSolicitante property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionBasicaSolicitante }
     *     
     */
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
     * Gets the value of the estadoSisben property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstadoSisben() {
        return estadoSisben;
    }

    /**
     * Sets the value of the estadoSisben property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstadoSisben(String value) {
        this.estadoSisben = value;
    }

    /**
     * Gets the value of the informacionBasicaSisben property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionBasicaSisben }
     *     
     */
    public TipoInformacionBasicaSisben getInformacionBasicaSisben() {
        return informacionBasicaSisben;
    }

    /**
     * Sets the value of the informacionBasicaSisben property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionBasicaSisben }
     *     
     */
    public void setInformacionBasicaSisben(TipoInformacionBasicaSisben value) {
        this.informacionBasicaSisben = value;
    }

    /**
     * Gets the value of the informacionEconomica property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionEconomica }
     *     
     */
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
     * Gets the value of the informacionAutorizacion property.
     * 
     * @return
     *     possible object is
     *     {@link TipoAutorizacion }
     *     
     */
    public TipoAutorizacion getInformacionAutorizacion() {
        return informacionAutorizacion;
    }

    /**
     * Sets the value of the informacionAutorizacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoAutorizacion }
     *     
     */
    public void setInformacionAutorizacion(TipoAutorizacion value) {
        this.informacionAutorizacion = value;
    }

    /**
     * Gets the value of the informacionViablidad property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionViabilidad }
     *     
     */
    public TipoInformacionViabilidad getInformacionViablidad() {
        return informacionViablidad;
    }

    /**
     * Sets the value of the informacionViablidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionViabilidad }
     *     
     */
    public void setInformacionViablidad(TipoInformacionViabilidad value) {
        this.informacionViablidad = value;
    }

    /**
     * Gets the value of the informacionExtendidaSolicitante property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionExtendidaSolicitante }
     *     
     */
    public TipoInformacionExtendidaSolicitante getInformacionExtendidaSolicitante() {
        return informacionExtendidaSolicitante;
    }

    /**
     * Sets the value of the informacionExtendidaSolicitante property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionExtendidaSolicitante }
     *     
     */
    public void setInformacionExtendidaSolicitante(TipoInformacionExtendidaSolicitante value) {
        this.informacionExtendidaSolicitante = value;
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

    /**
     * Gets the value of the listaPatrocinadores property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listaPatrocinadores property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaPatrocinadores().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipoPatrocinador }
     * 
     * 
     */
    public List<TipoPatrocinador> getListaPatrocinadores() {
        if (listaPatrocinadores == null) {
            listaPatrocinadores = new ArrayList<TipoPatrocinador>();
        }
        return this.listaPatrocinadores;
    }

    /**
     * Gets the value of the informacionCuentaBeps property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionCuentaBeps }
     *     
     */
    public TipoInformacionCuentaBeps getInformacionCuentaBeps() {
        return informacionCuentaBeps;
    }

    /**
     * Sets the value of the informacionCuentaBeps property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionCuentaBeps }
     *     
     */
    public void setInformacionCuentaBeps(TipoInformacionCuentaBeps value) {
        this.informacionCuentaBeps = value;
    }

}
