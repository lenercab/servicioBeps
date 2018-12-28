
package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.comunes.anotaciones.NotNullValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.RegexValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.StringLengthValidator;



/**
 * <b>Descripcion:</b> Contiene los Datos de  tipo y número de documento <br/>
 * <b>Caso de Uso:</b> GOP-VIN-1-FAB-11-ModificarInformacionSensibleVinculado <br/>
 * 
 * @author Yenny Nustez <ynustez@heinsohn.com.co>
 */
/**
 * <p>Java class for tipoDocumentoPersonaNatural complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoDocumentoPersonaNatural">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroDocumento" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "tipoDocumentoPersonaNatural", propOrder = {
    "tipoDocumento",
    "numeroDocumento"
})
public class TipoDocumentoPersonaNatural {

    /** Tipo de identificación de solicitante    */
    @XmlElement
    protected String tipoDocumento;
    
    /** Número de identificación del solicitante    */
    @XmlElement
    protected String numeroDocumento;

    /**
     * Gets the value of the tipoDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @NotNullValidator
    @RegexValidator(pattern ="^[A-Z]*$", message = "{0} no es válido, debe ingresar un valor alfabético")
    @StringLengthValidator(min = 2, max = 2, message = "{0} debe tener una longitud de 2 caracteres")
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Sets the value of the tipoDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    /**
     * Gets the value of the numeroDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @NotNullValidator
    @RegexValidator(pattern ="^[^0*][0-9]*$", message = "{0} no es válido, debe ingresar un valor numérico sin ceros a la izquierda")
    @StringLengthValidator(min = 2, max = 15, message = "{0} debe tener una longitud de mínimo 2 y máximo 15 caracteres")
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * Sets the value of the numeroDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroDocumento(String value) {
        this.numeroDocumento = value;
    }

}
