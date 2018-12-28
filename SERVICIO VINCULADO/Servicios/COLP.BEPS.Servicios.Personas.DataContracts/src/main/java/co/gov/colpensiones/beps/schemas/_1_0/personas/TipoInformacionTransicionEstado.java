package co.gov.colpensiones.beps.schemas._1_0.personas;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <b>Descripcion:</b> Clase que Contiene la información de entrada vinculados y/o estado detalle vinculado<br/>
 * <b>Caso de Uso:</b> BEPS - GVI-VIN-3-FAB-12-ConsultarTransicionesDeEstadoPermitidas <br/>
 *
 * @author Arnold Rios Delgado <arrios@heinsohn.com.co>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionTransicionEstado", propOrder = {
    "vinculado",
    "tipoEstadoVinculado"
})
public class TipoInformacionTransicionEstado {
    
    /** Atributo para la lista de vinculados.*/
    @XmlElement(nillable = true)
    protected List<TipoDocumentoPersonaNatural> vinculado;
    
    /** Tipo de estadoVinculado. */
    @XmlElement
    protected TipoEstadoVinculadoBeps tipoEstadoVinculado;

    /**
     * Devuelve el valor de vinculado
     * @return El valor de vinculado
     */
    public List<TipoDocumentoPersonaNatural> getVinculado() {
        return vinculado;
    }

    /**
     * Establece el valor de vinculado
     * @param vinculado El valor por establecer para vinculado
     */
    public void setVinculado(List<TipoDocumentoPersonaNatural> vinculado) {
        this.vinculado = vinculado;
    }

    /**
     * Devuelve el valor de tipoEstadoVinculado
     * @return El valor de tipoEstadoVinculado
     */
    public TipoEstadoVinculadoBeps getTipoEstadoVinculado() {
        return tipoEstadoVinculado;
    }

    /**
     * Establece el valor de tipoEstadoVinculado
     * @param tipoEstadoVinculado El valor por establecer para tipoEstadoVinculado
     */
    public void setTipoEstadoVinculado(TipoEstadoVinculadoBeps tipoEstadoVinculado) {
        this.tipoEstadoVinculado = tipoEstadoVinculado;
    }

}
