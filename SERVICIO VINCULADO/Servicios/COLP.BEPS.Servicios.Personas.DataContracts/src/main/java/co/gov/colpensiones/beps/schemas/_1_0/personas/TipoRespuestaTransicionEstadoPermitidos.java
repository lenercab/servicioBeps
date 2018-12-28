package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <b>Descripcion:</b> Clase que Contiene la respuesta del servicio, cuando se recibe como entrada al servicio, el Estado actual del vinculado<br/>
 * <b>Caso de Uso:</b> BEPS - GVI-VIN-3-FAB-12-ConsultarTransicionesDeEstadoPermitidas <br/>
 *
 * @author Arnold Rios Delgado <arrios@heinsohn.com.co>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoRespuestaTransicionEstadoPermitidos", propOrder = { "tipoEstado", "tipoOperacion" })
public class TipoRespuestaTransicionEstadoPermitidos {

    /** Atributo con estado y detalle estado vinculado.*/
    @XmlElement
    protected TipoEstadoVinculadoBeps tipoEstado;
    
    /**Tipo de Operación*/
    @XmlElement
    protected String tipoOperacion;

    /**
     * Devuelve el valor de tipoEstado
     * @return El valor de tipoEstado
     */
    public TipoEstadoVinculadoBeps getTipoEstado() {
        return tipoEstado;
    }

    /**
     * Establece el valor de tipoEstado
     * @param tipoEstado El valor por establecer para tipoEstado
     */
    public void setTipoEstado(TipoEstadoVinculadoBeps tipoEstado) {
        this.tipoEstado = tipoEstado;
    }

    /**
     * Devuelve el valor de tipoOperacion
     * @return El valor de tipoOperacion
     */
    public String getTipoOperacion() {
        return tipoOperacion;
    }

    /**
     * Establece el valor de tipoOperacion
     * @param tipoOperacion El valor por establecer para tipoOperacion
     */
    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }
    
}