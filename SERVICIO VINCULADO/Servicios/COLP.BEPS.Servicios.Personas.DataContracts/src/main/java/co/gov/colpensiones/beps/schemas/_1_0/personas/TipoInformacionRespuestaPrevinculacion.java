
package co.gov.colpensiones.beps.schemas._1_0.personas;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.comunes.anotaciones.NotNullValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.ObjectValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.RegexValidator;


/**
 * <p>Java class for tipoInformacionRespuestaPrevinculacion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoInformacionRespuestaPrevinculacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificacion" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoDocumentoPersonaNatural"/>
 *         &lt;element name="indicadorAprobacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="listaCodigosRechazo" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionRespuestaPrevinculacion", propOrder = {
    "identificacion",
    "indicadorAprobacion",
    "codigoRechazo"
})
@XmlRootElement
public class TipoInformacionRespuestaPrevinculacion {

    @XmlElement(required = true)
    protected TipoDocumentoPersonaNatural identificacion;
    @XmlElement(required = true)
    protected String indicadorAprobacion;
    @XmlElement(nillable = true)
    protected List<String> codigoRechazo;

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
     * Gets the value of the indicadorAprobacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @NotNullValidator
    @RegexValidator(pattern ="^(S|N)$", message = "{0} no es válido, debe ingresar alguna de las siguientes opciones: S, N")
    public String getIndicadorAprobacion() {
        return indicadorAprobacion;
    }

    /**
     * Sets the value of the indicadorAprobacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndicadorAprobacion(String value) {
        this.indicadorAprobacion = value;
    }
    
    
    /**
     * Gets the value of the codigoRechazo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the codigoRechazo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaCodigosRechazo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCodigoRechazo() {
        if (codigoRechazo == null) {
            codigoRechazo = new ArrayList<String>();
        }
        return this.codigoRechazo;
    }

}
