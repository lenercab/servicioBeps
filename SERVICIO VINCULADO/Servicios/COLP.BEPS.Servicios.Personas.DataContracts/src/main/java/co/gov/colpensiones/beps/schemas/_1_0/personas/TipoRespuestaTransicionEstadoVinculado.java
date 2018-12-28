package co.gov.colpensiones.beps.schemas._1_0.personas;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <b>Descripcion:</b> Clase que Contiene la respuesta del servicio, cuando se recibe como entrada una lista de vinculados<br/>
 * <b>Caso de Uso:</b> BEPS - GVI-VIN-3-FAB-12-ConsultarTransicionesDeEstadoPermitidas <br/>
 *
 * @author Arnold Rios Delgado <arrios@heinsohn.com.co>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoRespuestaTransicionEstadoVinculado", propOrder = { "vinculado", "tipoEstados", "resultadoConsulta" })
public class TipoRespuestaTransicionEstadoVinculado {

    
    /** Atributo para vinculados.*/
    @XmlElement
    protected TipoDocumentoPersonaNatural vinculado;

    /** Lista de estados permitidos por vinculado*/
    @XmlElement(nillable = true)
    protected List<TipoRespuestaTransicionEstadoPermitidos> tipoEstados;
    
    /**Resultado de la consulta*/
    @XmlElement
    protected String resultadoConsulta;

    
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
     * Devuelve el valor de tipoEstados
     * @return El valor de tipoEstados
     */
    public List<TipoRespuestaTransicionEstadoPermitidos> getTipoEstados() {
        return tipoEstados;
    }

    /**
     * Establece el valor de tipoEstados
     * @param tipoEstados El valor por establecer para tipoEstados
     */
    public void setTipoEstados(List<TipoRespuestaTransicionEstadoPermitidos> tipoEstados) {
        this.tipoEstados = tipoEstados;
    }

    /**
     * Devuelve el valor de resultadoConsulta
     * @return El valor de resultadoConsulta
     */
    public String getResultadoConsulta() {
        return resultadoConsulta;
    }

    /**
     * Establece el valor de resultadoConsulta
     * @param resultadoConsulta El valor por establecer para resultadoConsulta
     */
    public void setResultadoConsulta(String resultadoConsulta) {
        this.resultadoConsulta = resultadoConsulta;
    }
    
}