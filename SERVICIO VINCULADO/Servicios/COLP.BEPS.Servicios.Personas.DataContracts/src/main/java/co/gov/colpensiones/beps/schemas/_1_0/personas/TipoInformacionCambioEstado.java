package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <b>Descripción:</b> Clase que Contiene la información de entrada del vinculados y estado detalle vinculado<br/>
 * 
 * <b>Caso de Uso:</b> GVI-VIN-3-FAB-13-RealizarCambioEstadoVinculado <br/>
 * @author Arnold Rios Delgado <arrios@heinsohn.com.co>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionCambioEstado", propOrder = {
    "vinculado",
    "tipoEstadoDestinoVinculado"
})
public class TipoInformacionCambioEstado {
    
    /** Atributo para la informacion del vinculado.*/
    @XmlElement
    protected TipoDocumentoPersonaNatural vinculado;
    
    /** Tipo de estadoVinculado. */
    @XmlElement
    protected TipoEstadoVinculadoBeps tipoEstadoDestinoVinculado;

    /**
     * Devuelve el valor de vinculado
     * @return El valor de vinculado
     */
    public TipoDocumentoPersonaNatural getVinculado() {
        return vinculado;
    }

    /**
     * Establece el valor de vinculado
     * @param vinculado El valor por establecer para vinculado
     */
    public void setVinculado(TipoDocumentoPersonaNatural vinculado) {
        this.vinculado = vinculado;
    }

    /**
     * Devuelve el valor de tipoEstadoDestinoVinculado
     * @return El valor de tipoEstadoDestinoVinculado
     */
    public TipoEstadoVinculadoBeps getTipoEstadoDestinoVinculado() {
        return tipoEstadoDestinoVinculado;
    }

    /**
     * Establece el valor de tipoEstadoDestinoVinculado
     * @param tipoEstadoDestinoVinculado El valor por establecer para tipoEstadoDestinoVinculado
     */
    public void setTipoEstadoDestinoVinculado(TipoEstadoVinculadoBeps tipoEstadoDestinoVinculado) {
        this.tipoEstadoDestinoVinculado = tipoEstadoDestinoVinculado;
    }

}
