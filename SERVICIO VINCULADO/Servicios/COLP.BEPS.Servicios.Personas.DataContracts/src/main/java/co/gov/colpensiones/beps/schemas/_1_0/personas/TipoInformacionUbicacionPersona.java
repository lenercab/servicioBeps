package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.comunes.anotaciones.IgnoreNullsValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.ObjectValidator;

/**
 * <p>
 * Java class for tipoInformacionUbicacionPersona complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionUbicacionPersona">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="direcciones" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoDirecciones"/>
 *         &lt;element name="telefonos" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoTelefonos"/>
 *         &lt;element name="correosElectronicos" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoDireccionesCorreoElectronico"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionUbicacionPersona", propOrder = { "direcciones", "telefonos", "correosElectronicos" })
public class TipoInformacionUbicacionPersona {

    @XmlElement
    protected TipoDirecciones direcciones;
    @XmlElement
    protected TipoTelefonos telefonos;
    @XmlElement
    protected TipoDireccionesCorreoElectronico correosElectronicos;

    /**
     * Gets the value of the direcciones property.
     * 
     * @return possible object is {@link TipoDirecciones }
     * 
     */
    @ObjectValidator
    public TipoDirecciones getDirecciones() {
        return direcciones;
    }

    /**
     * Sets the value of the direcciones property.
     * 
     * @param value
     *            allowed object is {@link TipoDirecciones }
     * 
     */
    public void setDirecciones(TipoDirecciones value) {
        this.direcciones = value;
    }

    /**
     * Gets the value of the telefonos property.
     * 
     * @return possible object is {@link TipoTelefonos }
     * 
     */
    @IgnoreNullsValidator
    @ObjectValidator
    public TipoTelefonos getTelefonos() {
        return telefonos;
    }

    /**
     * Sets the value of the telefonos property.
     * 
     * @param value
     *            allowed object is {@link TipoTelefonos }
     * 
     */
    public void setTelefonos(TipoTelefonos value) {
        this.telefonos = value;
    }

    /**
     * Gets the value of the correosElectronicos property.
     * 
     * @return possible object is {@link TipoDireccionesCorreoElectronico }
     * 
     */
    @IgnoreNullsValidator
    @ObjectValidator
    public TipoDireccionesCorreoElectronico getCorreosElectronicos() {
        return correosElectronicos;
    }

    /**
     * Sets the value of the correosElectronicos property.
     * 
     * @param value
     *            allowed object is {@link TipoDireccionesCorreoElectronico }
     * 
     */
    public void setCorreosElectronicos(TipoDireccionesCorreoElectronico value) {
        this.correosElectronicos = value;
    }

}
