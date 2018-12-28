package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <b>Descripcion:</b> Contiene la estructura que almacena los datos de la novedad que se deben registrar en la bitacora de novedades BUC para cuando se realiza la actualización de información sensible o No sensible  <br/>
 * <b>Caso de Uso:</b> PT-INGE-014-GVI-VIN-1-FAB-13-ModificarInformacionNoSensibleVinculado_AnexoTecnico_SW <br/>
 *                     PT-INGE-014-GOP-VIN-1-FAB-11-ModificarInformacionSensibleVinculado_AnexoTecnico_SW <br/>
 * 
 * @author Juan Miguel Alvear N <malvear@heinsohn.com.co>
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoInformacionNovedadesBuc", propOrder = { "informacionNoSensibleAntes", "informacionNoSensibleDespues", "informacionSensible" })
public class TipoInformacionNovedadesBuc {
    
    /** Contiene la información no sensible antes de ser actualizada en el SGB */
    @XmlElement
    protected TipoInformacionUbicacionPersona informacionNoSensibleAntes;
    
    /** Contiene la información no sensible luego de ser actualizada en el SGB */
    @XmlElement
    protected TipoInformacionUbicacionPersona informacionNoSensibleDespues;
    
    /** Contiene la información sensible actualizada en el SGB*/
    @XmlElement
    protected TipoInformacionBasicaSolicitante informacionSensible;
    
    

    public TipoInformacionNovedadesBuc() {
        
    }

    /**
     * @return the informacionNoSensibleAntes
     */
    public TipoInformacionUbicacionPersona getInformacionNoSensibleAntes() {
        return informacionNoSensibleAntes;
    }

    /**
     * @param informacionNoSensibleAntes the informacionNoSensibleAntes to set
     */
    public void setInformacionNoSensibleAntes(TipoInformacionUbicacionPersona informacionNoSensibleAntes) {
        this.informacionNoSensibleAntes = informacionNoSensibleAntes;
    }

    /**
     * @return the informacionNoSensibleDespues
     */
    public TipoInformacionUbicacionPersona getInformacionNoSensibleDespues() {
        return informacionNoSensibleDespues;
    }

    /**
     * @param informacionNoSensibleDespues the informacionNoSensibleDespues to set
     */
    public void setInformacionNoSensibleDespues(TipoInformacionUbicacionPersona informacionNoSensibleDespues) {
        this.informacionNoSensibleDespues = informacionNoSensibleDespues;
    }

    /**
     * @return the informacionSensible
     */
    public TipoInformacionBasicaSolicitante getInformacionSensible() {
        return informacionSensible;
    }

    /**
     * @param informacionSensible the informacionSensible to set
     */
    public void setInformacionSensible(TipoInformacionBasicaSolicitante informacionSensible) {
        this.informacionSensible = informacionSensible;
    }
    
    

}
