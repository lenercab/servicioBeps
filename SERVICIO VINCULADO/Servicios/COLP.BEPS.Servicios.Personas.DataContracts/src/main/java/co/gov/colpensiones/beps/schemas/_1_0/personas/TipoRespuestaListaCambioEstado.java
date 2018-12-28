package co.gov.colpensiones.beps.schemas._1_0.personas;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstadoEjecucion;

/**
 * <b>Descripción:</b> Clase que contiene la lista de respuesta del servicio <br/>
 * 
 * <b>Caso de Uso:</b> GVI-VIN-3-FAB-13-RealizarCambioEstadoVinculado <br/>
 * @author Arnold Rios Delgado <arrios@heinsohn.com.co>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoRespuestaListaCambioEstado", propOrder = { "respuestaCambioEstado", "estadoEjecucion" })
public class TipoRespuestaListaCambioEstado {
    
    /**Lista de resultado del cambio de estado */
    @XmlElement
    protected List<TipoRespuestaCambioEstado> respuestaCambioEstado;
    
    /**Código, identifiacor del log y mensaje de error de respuesta al servicio*/
    @XmlElement(required = true, nillable = true)
    protected TipoEstadoEjecucion estadoEjecucion;

    /**
     * Devuelve el valor de respuestaCambioEstado
     * @return El valor de respuestaCambioEstado
     */
    public List<TipoRespuestaCambioEstado> getRespuestaCambioEstado() {
        return respuestaCambioEstado;
    }

    /**
     * Establece el valor de respuestaCambioEstado
     * @param respuestaCambioEstado El valor por establecer para respuestaCambioEstado
     */
    public void setRespuestaCambioEstado(List<TipoRespuestaCambioEstado> respuestaCambioEstado) {
        this.respuestaCambioEstado = respuestaCambioEstado;
    }

    /**
     * Devuelve el valor de estadoEjecucion
     * @return El valor de estadoEjecucion
     */
    public TipoEstadoEjecucion getEstadoEjecucion() {
        return estadoEjecucion;
    }

    /**
     * Establece el valor de estadoEjecucion
     * @param estadoEjecucion El valor por establecer para estadoEjecucion
     */
    public void setEstadoEjecucion(TipoEstadoEjecucion estadoEjecucion) {
        this.estadoEjecucion = estadoEjecucion;
    }


}