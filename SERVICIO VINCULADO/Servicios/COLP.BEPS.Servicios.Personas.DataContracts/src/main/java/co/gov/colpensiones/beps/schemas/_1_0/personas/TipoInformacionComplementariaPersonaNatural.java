
package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.comunes.anotaciones.DateValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.IgnoreEmptyValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.IgnoreNullsValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.NotNullValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.ObjectValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.RegexValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.StringLengthValidator;

/**
 * <b>Descripcion:</b> Contiene los Datos de información complementaria para el solicitante <br/>
 * <b>Caso de Uso:</b> GOP-VIN-1-FAB-11-ModificarInformacionSensibleVinculado <br/>
 * 
 * @author Yenny Nustez <ynustez@heinsohn.com.co>
 */
/**
 * <p>Java class for tipoInformacionComplementariaPersonaNatural complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionComplementariaPersonaNatural">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="genero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="informacionLugarNacimiento" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionNacimientoPersonaNatural"/>
 *         &lt;element name="fechaExpedicionDocumentoIdenticacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="municipioExpedicionDocumentoIdenticacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionComplementariaPersonaNatural", propOrder = {
    "genero",
    "informacionLugarNacimiento",
    "fechaExpedicionDocumentoIdenticacion",
    "municipioExpedicionDocumentoIdenticacion"
})
public class TipoInformacionComplementariaPersonaNatural {

    /** Sexo del solicitante     */
    @XmlElement(nillable = true)
    protected String genero;
    
    /** Contiene la información de Nacimiento del Solicitante     */
    @XmlElement(nillable = true)
    protected TipoInformacionNacimientoPersonaNatural informacionLugarNacimiento;
    
    /** Fecha de expedición del documento de identidad     */
    @XmlElement(nillable = false)
    protected String fechaExpedicionDocumentoIdenticacion;
    
    /** Lugar de expedición del documento de identificación     */
    @XmlElement(nillable = true)
    protected String municipioExpedicionDocumentoIdenticacion;

    /**
     * Gets the value of the genero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @IgnoreNullsValidator
    @StringLengthValidator(min = 1, max = 1, message = "{0} debe tener una longitud de 1 caracter")
    public String getGenero() {
        return genero;
    }

    /**
     * Sets the value of the genero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGenero(String value) {
        this.genero = value;
    }

    /**
     * Gets the value of the informacionLugarNacimiento property.
     * 
     * @return
     *     possible object is
     *     {@link TipoInformacionNacimientoPersonaNatural }
     *     
     */
    @IgnoreNullsValidator
    @ObjectValidator
    public TipoInformacionNacimientoPersonaNatural getInformacionLugarNacimiento() {
        return informacionLugarNacimiento;
    }

    /**
     * Sets the value of the informacionLugarNacimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoInformacionNacimientoPersonaNatural }
     *     
     */
    public void setInformacionLugarNacimiento(TipoInformacionNacimientoPersonaNatural value) {
        this.informacionLugarNacimiento = value;
    }

    /**
     * Gets the value of the fechaExpedicionDocumentoIdenticacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @NotNullValidator
    @StringLengthValidator(min = 8, max = 8, message = "{0} debe tener una longitud de 8 caracteres")
    @DateValidator(validarFechaActual = true)
    public String getFechaExpedicionDocumentoIdenticacion() {
        return fechaExpedicionDocumentoIdenticacion;
    }

    /**
     * Sets the value of the fechaExpedicionDocumentoIdenticacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaExpedicionDocumentoIdenticacion(String value) {
        this.fechaExpedicionDocumentoIdenticacion = value;
    }

    /**
     * Gets the value of the municipioExpedicionDocumentoIdenticacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @IgnoreNullsValidator
    @StringLengthValidator(min = 5, max = 5, message = "Código ciudad debe tener una longitud de 5 caracteres")
    @RegexValidator(pattern = "[0-9]+", message = "Código ciudad no es válido, solo se permiten números")   
    public String getMunicipioExpedicionDocumentoIdenticacion() {
        return municipioExpedicionDocumentoIdenticacion;
    }

    /**
     * Sets the value of the municipioExpedicionDocumentoIdenticacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMunicipioExpedicionDocumentoIdenticacion(String value) {
        this.municipioExpedicionDocumentoIdenticacion = value;
    }

}
