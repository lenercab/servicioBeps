/**
 * 
 */
package co.gov.colpensiones.beps.comunes.dto;

/**
 * <b>Descripcion:</b> Clase que almacena los datos de estado de un vinculado.<br/>
 *
 * @author csalazar <csalazar@heinsohn.com.co>
 */
public class InformacionVinculadoEstadosDTO {
    
    /** Atributo para el identificador del vinculado.*/
    private Long identificadorVinculado;
    /** Atributo para almacenar el estado actual del vinculado.*/
    private String estadoVinculado;
    /** Atributo para almacenar el detalle de estado actual del vinculado.*/
    private String detalleEstadoVinculado;
    /** Atributo para almacenar el número de radicado de la solicitud de destinación de recursos.*/
    private String numeroRadicado;
    
    /**
     * Devuelve el valor de identificadorVinculado
     * @return El valor de identificadorVinculado
     */
    public Long getIdentificadorVinculado() {
        return identificadorVinculado;
    }
    
    /**
     * Establece el valor de identificadorVinculado
     * @param identificadorVinculado El valor por establecer para identificadorVinculado
     */
    public void setIdentificadorVinculado(Long identificadorVinculado) {
        this.identificadorVinculado = identificadorVinculado;
    }
    
    /**
     * Devuelve el valor de estadoVinculado
     * @return El valor de estadoVinculado
     */
    public String getEstadoVinculado() {
        return estadoVinculado;
    }
    
    /**
     * Establece el valor de estadoVinculado
     * @param estadoVinculado El valor por establecer para estadoVinculado
     */
    public void setEstadoVinculado(String estadoVinculado) {
        this.estadoVinculado = estadoVinculado;
    }
    
    /**
     * Devuelve el valor de detalleEstadoVinculado
     * @return El valor de detalleEstadoVinculado
     */
    public String getDetalleEstadoVinculado() {
        return detalleEstadoVinculado;
    }
    
    /**
     * Establece el valor de detalleEstadoVinculado
     * @param detalleEstadoVinculado El valor por establecer para detalleEstadoVinculado
     */
    public void setDetalleEstadoVinculado(String detalleEstadoVinculado) {
        this.detalleEstadoVinculado = detalleEstadoVinculado;
    }

    /**
     * Devuelve el valor de numeroRadicado
     * @return El valor de numeroRadicado
     */
    public String getNumeroRadicado() {
        return numeroRadicado;
    }

    /**
     * Establece el valor de numeroRadicado
     * @param numeroRadicado El valor por establecer para numeroRadicado
     */
    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }
}