package co.gov.colpensiones.beps.schemas._1_0.personas;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.comunes.anotaciones.ObjectValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.RegexValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.StringLengthValidator;

/**
 * <p>
 * Java class for tipoInformacionDireccionPersonaNatural complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionDireccionPersonaNatural">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificador" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="departamento" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoDepartamento"/>
 *         &lt;element name="municipio" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoCiudad"/>
 *         &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="esPrincipal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionDireccionPersonaNatural", propOrder = { "identificador", "departamento", "municipio", "direccion",
        "esPrincipal" })
public class TipoInformacionDireccionPersonaNatural {

    @XmlElement(required = true, type = BigDecimal.class, defaultValue = "0")
    protected BigDecimal identificador = BigDecimal.ZERO;
    @XmlElement(required = true)
    protected TipoDepartamento departamento;
    @XmlElement(required = true)
    protected TipoCiudad municipio;
    @XmlElement(required = true)
    protected String direccion;
    @XmlElement(required = true)
    protected String esPrincipal;

    /**
     * Gets the value of the identificador property.
     * 
     * @return possible object is {@link BigDecimal }
     * 
     */
    public BigDecimal getIdentificador() {
        return identificador;
    }

    /**
     * Sets the value of the identificador property.
     * 
     * @param value
     *            allowed object is {@link BigDecimal }
     * 
     */
    public void setIdentificador(BigDecimal value) {
        this.identificador = value;
    }

    /**
     * Gets the value of the departamento property.
     * 
     * @return possible object is {@link TipoDepartamento }
     * 
     */
    @ObjectValidator
    public TipoDepartamento getDepartamento() {
        return departamento;
    }

    /**
     * Sets the value of the departamento property.
     * 
     * @param value
     *            allowed object is {@link TipoDepartamento }
     * 
     */
    public void setDepartamento(TipoDepartamento value) {
        this.departamento = value;
    }

    /**
     * Gets the value of the municipio property.
     * 
     * @return possible object is {@link TipoCiudad }
     * 
     */
    @ObjectValidator
    public TipoCiudad getMunicipio() {
        return municipio;
    }

    /**
     * Sets the value of the municipio property.
     * 
     * @param value
     *            allowed object is {@link TipoCiudad }
     * 
     */
    public void setMunicipio(TipoCiudad value) {
        this.municipio = value;
    }

    /**
     * Gets the value of the direccion property.
     * 
     * @return possible object is {@link String }
     * 
     */
    @StringLengthValidator(min = 1, max = 80, message = "{0} debe tener una longitud de 1 a 80 caracteres")        
    public String getDireccion() {
        return direccion;
    }

    /**
     * Sets the value of the direccion property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDireccion(String value) {
        this.direccion = value;
    }

    /**
     * Gets the value of the esPrincipal property.
     * 
     * @return possible object is {@link String }
     * 
     */
    @StringLengthValidator(min = 1, max = 1, message = "Indicador DireccionPrincipal debe tener una longitud de 1 caracter")        
    @RegexValidator(pattern="^(S|N)+$", message="Indicador DireccionPrincipal no es válido, debe ingresar alguna de las siguientes opciones: S, N")
    public String getEsPrincipal() {
        return esPrincipal;
    }

    /**
     * Sets the value of the esPrincipal property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setEsPrincipal(String value) {
        this.esPrincipal = value;
    }

}
