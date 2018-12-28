package co.gov.colpensiones.beps.schemas._1_0.personas;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.comunes.anotaciones.ObjectValidator;

/**
 * <p>
 * Java class for tipoDirecciones complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoDirecciones">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="direccion" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/personas}tipoInformacionDireccionPersonaNatural" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoDirecciones", propOrder = { "direccion" })
public class TipoDirecciones {

    @XmlElement(nillable = true)
    protected List<TipoInformacionDireccionPersonaNatural> direccion;

    /**
     * Gets the value of the direccion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list
     * will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the direccion property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getDireccion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link TipoInformacionDireccionPersonaNatural }
     * 
     * 
     */
    @ObjectValidator
    public List<TipoInformacionDireccionPersonaNatural> getDireccion() {
        if (direccion == null) {
            direccion = new ArrayList<TipoInformacionDireccionPersonaNatural>();
        }
        return this.direccion;
    }

}
