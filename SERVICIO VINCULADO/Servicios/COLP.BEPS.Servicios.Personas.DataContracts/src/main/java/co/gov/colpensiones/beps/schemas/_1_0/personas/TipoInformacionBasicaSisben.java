package co.gov.colpensiones.beps.schemas._1_0.personas;

import java.math.BigDecimal;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.comunes.anotaciones.IgnoreNullsValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.NotNullValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.ObjectValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.RegexValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.StringLengthValidator;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstado;

/**
 * <p>
 * Java class for tipoInformacionBasicaSisben complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionBasicaSisben">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="puntaje" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="area" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nivel" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/comun}tipoEstado" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionBasicaSisben", propOrder = { "puntaje", "area", "nivel" })
@XmlRootElement
public class TipoInformacionBasicaSisben {

    @XmlElement(nillable = true)
    protected String puntaje;
    @XmlElement(nillable = true)
    protected String area;
    @XmlElementRef(name = "nivel", namespace = "http://www.colpensiones.gov.co/beps/schemas/1.0/personas", type = JAXBElement.class, required = false)
    protected JAXBElement<TipoEstado> nivel;

    /**
     * Gets the value of the puntaje property.
     * 
     * @return possible object is {@link BigDecimal }
     * 
     */
    @NotNullValidator
    @RegexValidator(pattern = "^[0-9]{1,3}(\\.[0-9]{1,2}){0,1}$", message = "{0} debe estar conformado por mínimo 1 y máximo 3 dígitos enteros. Máximo 2 dígitos decimales, con caracter separador decimal (.)")
    public String getPuntaje() {
        return puntaje;
    }

    /**
     * Sets the value of the puntaje property.
     * 
     * @param value
     *            allowed object is {@link BigDecimal }
     * 
     */
    public void setPuntaje(String value) {
        this.puntaje = value;
    }

    /**
     * Gets the value of the area property.
     * 
     * @return possible object is {@link String }
     * 
     */
    @NotNullValidator
    @StringLengthValidator(min = 1, max = 1, message = "{0} debe tener una longitud de 1 caracter")
    @RegexValidator(pattern = "^(1|2|3)+$", message = "{0} no es válido, debe ingresar alguna de las siguientes opciones: [1|2|3]")
    public String getArea() {
        return area;
    }

    /**
     * Sets the value of the area property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setArea(String value) {
        this.area = value;
    }

    /**
     * Gets the value of the nivel property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link TipoEstado }{@code >}
     * 
     */
    @IgnoreNullsValidator
    public JAXBElement<TipoEstado> getNivel() {
        return nivel;
    }

    /**
     * Sets the value of the nivel property.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link TipoEstado }{@code >}
     * 
     */
    public void setNivel(JAXBElement<TipoEstado> value) {
        this.nivel = value;
    }

}
