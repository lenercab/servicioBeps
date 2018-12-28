
package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.comunes.anotaciones.DateValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.IgnoreEmptyValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.IgnoreNullsValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.StringLengthValidator;

/**
 * <b>Descripcion:</b> Contiene la información de Nacimiento del Solicitante <br/>
 * <b>Caso de Uso:</b> GOP-VIN-1-FAB-11-ModificarInformacionSensibleVinculado <br/>
 * 
 * @author Yenny Nustez <ynustez@heinsohn.com.co>
 */
/**
 * <p>Java class for tipoInformacionNacimientoPersonaNatural complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionNacimientoPersonaNatural">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="deptoNacimiento" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoDepartamento"/>
 *         &lt;element name="municipioNacimiento" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoCiudad"/>
 *         &lt;element name="fechaNacimiento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionNacimientoPersonaNatural", propOrder = {
    "deptoNacimiento",
    "municipioNacimiento",
    "fechaNacimiento"
})
public class TipoInformacionNacimientoPersonaNatural {

    /** Contiene el código Dane del departamento     */
    @XmlElement(nillable = true)
    protected TipoDepartamento deptoNacimiento;
    
    /** Contiene el código Dane del municipio      */
    @XmlElement(nillable = true)
    protected TipoCiudad municipioNacimiento;
    
    /** Fecha de nacimiento del solicitante a BEPS      */
    @XmlElement(nillable = true)
    protected String fechaNacimiento;

    /**
     * Gets the value of the deptoNacimiento property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDepartamento }
     *     
     */
    public TipoDepartamento getDeptoNacimiento() {
        return deptoNacimiento;
    }

    /**
     * Sets the value of the deptoNacimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDepartamento }
     *     
     */
    public void setDeptoNacimiento(TipoDepartamento value) {
        this.deptoNacimiento = value;
    }

    /**
     * Gets the value of the municipioNacimiento property.
     * 
     * @return
     *     possible object is
     *     {@link TipoCiudad }
     *     
     */
    public TipoCiudad getMunicipioNacimiento() {
        return municipioNacimiento;
    }

    /**
     * Sets the value of the municipioNacimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoCiudad }
     *     
     */
    public void setMunicipioNacimiento(TipoCiudad value) {
        this.municipioNacimiento = value;
    }

    /**
     * Gets the value of the fechaNacimiento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @IgnoreNullsValidator
    @StringLengthValidator(min = 8, max = 8, message = "{0} debe tener una longitud de 8 caracteres ")
    @DateValidator(validarFechaActual = true)
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Sets the value of the fechaNacimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaNacimiento(String value) {
        this.fechaNacimiento = value;
    }

}
