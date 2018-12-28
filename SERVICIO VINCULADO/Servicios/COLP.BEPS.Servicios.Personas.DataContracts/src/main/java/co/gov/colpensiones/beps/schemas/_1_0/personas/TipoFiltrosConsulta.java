
package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.comunes.anotaciones.DateValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.IgnoreNullsValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.ObjectValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.StringLengthValidator;


/**
 * <p>Java class for tipoFiltrosConsulta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoFiltrosConsulta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificacion" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoDocumentoPersonaNatural"/>
 *         &lt;element name="fechaInicial" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaFinal" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "tipoFiltrosConsulta", propOrder = {
    "identificacion",
    "fechaInicial",
    "fechaFinal"
})
public class TipoFiltrosConsulta {

    @XmlElement(required = true)
    protected TipoDocumentoPersonaNatural identificacion;
    @XmlElement(nillable = true)
    protected String fechaInicial;
    @XmlElement(nillable = true)
    protected String fechaFinal;

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
     * Gets the value of the fechaInicial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @IgnoreNullsValidator
    @StringLengthValidator(min = 8, max = 8, message = "{0} debe tener una longitud de 8 caracteres ")
    @DateValidator(validarFechaActual= false)
    public String getFechaInicial() {
        return fechaInicial;
    }

    /**
     * Sets the value of the fechaInicial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
     public void setFechaInicial(String value) {
        this.fechaInicial = value;
    }

    /**
     * Gets the value of the fechaFinal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
     @IgnoreNullsValidator
     @StringLengthValidator(min = 8, max = 8, message = "{0} debe tener una longitud de 8 caracteres ")
     @DateValidator(validarFechaActual= true)
    public String getFechaFinal() {
        return fechaFinal;
    }

    /**
     * Sets the value of the fechaFinal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaFinal(String value) {
        this.fechaFinal = value;
    }

}
