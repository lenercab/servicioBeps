
package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoInformacionContacto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionContacto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="departamentoResidencia" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoDepartamento"/>
 *         &lt;element name="municipioResidencia" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoCiudad"/>
 *         &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="telefono" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoDatoTelefono"/>
 *         &lt;element name="celular" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoDatoTelefono"/>
 *         &lt;element name="correoElectronico" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoCorreoElectronico"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionContacto", propOrder = {
    "departamentoResidencia",
    "municipioResidencia",
    "direccion",
    "telefono",
    "celular",
    "correoElectronico"
})
public class TipoInformacionContacto {

    @XmlElement(required = true)
    protected TipoDepartamento departamentoResidencia;
    @XmlElement(required = true)
    protected TipoCiudad municipioResidencia;
    @XmlElement(required = true)
    protected String direccion;
    @XmlElement(required = true)
    protected TipoDatoTelefono telefono;
    @XmlElement(required = true, nillable = true)
    protected TipoDatoTelefono celular;
    @XmlElement(required = true)
    protected TipoCorreoElectronico correoElectronico;

    /**
     * Gets the value of the departamentoResidencia property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDepartamento }
     *     
     */
    public TipoDepartamento getDepartamentoResidencia() {
        return departamentoResidencia;
    }

    /**
     * Sets the value of the departamentoResidencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDepartamento }
     *     
     */
    public void setDepartamentoResidencia(TipoDepartamento value) {
        this.departamentoResidencia = value;
    }

    /**
     * Gets the value of the municipioResidencia property.
     * 
     * @return
     *     possible object is
     *     {@link TipoCiudad }
     *     
     */
    public TipoCiudad getMunicipioResidencia() {
        return municipioResidencia;
    }

    /**
     * Sets the value of the municipioResidencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoCiudad }
     *     
     */
    public void setMunicipioResidencia(TipoCiudad value) {
        this.municipioResidencia = value;
    }

    /**
     * Gets the value of the direccion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Sets the value of the direccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDireccion(String value) {
        this.direccion = value;
    }

    /**
     * Gets the value of the telefono property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDatoTelefono }
     *     
     */
    public TipoDatoTelefono getTelefono() {
        return telefono;
    }

    /**
     * Sets the value of the telefono property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDatoTelefono }
     *     
     */
    public void setTelefono(TipoDatoTelefono value) {
        this.telefono = value;
    }

    /**
     * Gets the value of the celular property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDatoTelefono }
     *     
     */
    public TipoDatoTelefono getCelular() {
        return celular;
    }

    /**
     * Sets the value of the celular property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDatoTelefono }
     *     
     */
    public void setCelular(TipoDatoTelefono value) {
        this.celular = value;
    }

    /**
     * Gets the value of the correoElectronico property.
     * 
     * @return
     *     possible object is
     *     {@link TipoCorreoElectronico }
     *     
     */
    public TipoCorreoElectronico getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * Sets the value of the correoElectronico property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoCorreoElectronico }
     *     
     */
    public void setCorreoElectronico(TipoCorreoElectronico value) {
        this.correoElectronico = value;
    }

}
