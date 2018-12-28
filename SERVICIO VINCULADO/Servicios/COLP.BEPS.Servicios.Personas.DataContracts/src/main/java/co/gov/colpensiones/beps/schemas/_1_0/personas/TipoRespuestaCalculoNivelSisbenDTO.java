package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstadoEjecucion;

/**
 * <p>
 * Java class for tipoRespuestaCalculoNivelSisbenDTO complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoRespuestaCalculoNivelSisbenDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nivelSisben" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="estadoEjecucion" type="{http://www.colpensiones.gov.co/beps/schemas/1.0/comun}tipoEstadoEjecucion"/>
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
@XmlType(name = "tipoRespuestaCalculoNivelSisbenDTO", propOrder = { "nivelSisben", "estadoEjecucion" })
public class TipoRespuestaCalculoNivelSisbenDTO {

    @XmlElement(nillable = true)
    protected String nivelSisben;
    @XmlElement(required = true, nillable = true)
    protected TipoEstadoEjecucion estadoEjecucion;

    /**
     * Gets the value of the nivelSisben property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNivelSisben() {
        return nivelSisben;
    }

    /**
     * Sets the value of the nivelSisben property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setNivelSisben(String nivelSisben) {
        this.nivelSisben = nivelSisben;
    }

    /**
     * Gets the value of the estadoEjecucion property.
     * 
     * @return possible object is {@link TipoEstadoEjecucion }
     * 
     */
    public TipoEstadoEjecucion getEstadoEjecucion() {
        return estadoEjecucion;
    }

    /**
     * Sets the value of the estadoEjecucion property.
     * 
     * @param value
     *            allowed object is {@link TipoEstadoEjecucion }
     * 
     */
    public void setEstadoEjecucion(TipoEstadoEjecucion value) {
        this.estadoEjecucion = value;
    }

}
