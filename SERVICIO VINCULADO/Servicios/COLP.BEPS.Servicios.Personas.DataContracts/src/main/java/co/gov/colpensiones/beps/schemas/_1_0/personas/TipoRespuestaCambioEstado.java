package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <b>Descripción:</b> Clase que contiene la respuesta del servicio <br/>
 * 
 * <b>Caso de Uso:</b> GVI-VIN-3-FAB-13-RealizarCambioEstadoVinculado <br/>
 * @author Arnold Rios Delgado <arrios@heinsohn.com.co>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoRespuestaCambioEstado", propOrder = { "vinculado", "tipoEstadoDestinoVinculado","tipoOperacion","resultadoCambioEstado" })
public class TipoRespuestaCambioEstado {

    /** Atributo para la información vinculado.*/
    @XmlElement
    protected TipoDocumentoPersonaNatural vinculado;
    
    /** Tipo de estadoVinculado. */
    @XmlElement
    protected TipoEstadoVinculadoBeps tipoEstadoDestinoVinculado;
    
    /** Tipo de operación a realizar*/
    @XmlElement
    protected String tipoOperacion;
    
    /**Resultado del cambio de estado */
    @XmlElement
    protected String resultadoCambioEstado;

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

    /**
     * Devuelve el valor de resultadoCambioEstado
     * @return El valor de resultadoCambioEstado
     */
    public String getResultadoCambioEstado() {
        return resultadoCambioEstado;
    }

    /**
     * Establece el valor de resultadoCambioEstado
     * @param resultadoCambioEstado El valor por establecer para resultadoCambioEstado
     */
    public void setResultadoCambioEstado(String resultadoCambioEstado) {
        this.resultadoCambioEstado = resultadoCambioEstado;
    }


}