package co.gov.colpensiones.beps.schemas._1_0.personas;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstadoEjecucion;

/**
 * <b>Descripcion:</b> Clase que Contiene la respuesta del servicio <br/>
 * <b>Caso de Uso:</b> BEPS - GVI-VIN-3-FAB-12-ConsultarTransicionesDeEstadoPermitidas <br/>
 *
 * @author Arnold Rios Delgado <arrios@heinsohn.com.co>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoRespuestaTransicionEstado", propOrder = { "estadosPermitidos", "vinculados","estadoEjecucion" })
public class TipoRespuestaTransicionEstado {

    /**Respuesta al servicio cuando se recibe como entrada al servicio, el Estado actual del vinculado*/
    @XmlElement(nillable = true)
    protected List<TipoRespuestaTransicionEstadoPermitidos> estadosPermitidos;
    
    /**Respuesta al servicio cuando se recibe como entrada una lista de vinculados*/
    @XmlElement(nillable = true)
    protected List<TipoRespuestaTransicionEstadoVinculado> vinculados;
    
    /**Código, identifiacor del log y mensaje de error de respuesta al servicio*/
    @XmlElement(required = true, nillable = true)
    protected TipoEstadoEjecucion estadoEjecucion;

    /**
     * Devuelve el valor de estadosPermitidos
     * @return El valor de estadosPermitidos
     */
    public List<TipoRespuestaTransicionEstadoPermitidos> getEstadosPermitidos() {
        return estadosPermitidos;
    }

    /**
     * Establece el valor de estadosPermitidos
     * @param estadosPermitidos El valor por establecer para estadosPermitidos
     */
    public void setEstadosPermitidos(List<TipoRespuestaTransicionEstadoPermitidos> estadosPermitidos) {
        this.estadosPermitidos = estadosPermitidos;
    }

    /**
     * Devuelve el valor de vinculados
     * @return El valor de vinculados
     */
    public List<TipoRespuestaTransicionEstadoVinculado> getVinculados() {
        return vinculados;
    }

    /**
     * Establece el valor de vinculados
     * @param vinculados El valor por establecer para vinculados
     */
    public void setVinculados(List<TipoRespuestaTransicionEstadoVinculado> vinculados) {
        this.vinculados = vinculados;
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