package co.gov.colpensiones.beps.schemas._1_0.personas;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <b>Descripción:</b> Clase que Contiene la lista de información de entrada<br/>
 * 
 * <b>Caso de Uso:</b> GVI-VIN-3-FAB-13-RealizarCambioEstadoVinculado <br/>
 * @author Arnold Rios Delgado <arrios@heinsohn.com.co>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionListaCambioEstado", propOrder = {
    "datosRecibidosCanal"
})
public class TipoInformacionListaCambioEstado {
    
    /** Lista de vinculados con el estado y detalle de estado al que va a cambiar cada uno.*/
    @XmlElement(nillable = false)
    protected List<TipoInformacionCambioEstado> datosRecibidosCanal;

    /**
     * Devuelve el valor de datosRecibidosCanal
     * @return El valor de datosRecibidosCanal
     */
    public List<TipoInformacionCambioEstado> getDatosRecibidosCanal() {
        return datosRecibidosCanal;
    }

    /**
     * Establece el valor de datosRecibidosCanal
     * @param datosRecibidosCanal El valor por establecer para datosRecibidosCanal
     */
    public void setDatosRecibidosCanal(List<TipoInformacionCambioEstado> datosRecibidosCanal) {
        this.datosRecibidosCanal = datosRecibidosCanal;
    }

}
