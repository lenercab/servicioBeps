package co.gov.colpensiones.beps.comunes.dto;

/**
 * <b>Descripcion:</b> Clase que Contiene el estado y detalle estado por vinculado en base de datos<br/>
 * <b>Caso de Uso:</b> BEPS - GVI-VIN-3-FAB-12-ConsultarTransicionesDeEstadoPermitidas <br/>
 * 
 * @author Arnold Rios Delgado <arrios@heinsohn.com.co>
 */
public class VinculadoEstadoDetalleDTO {

    /** estado actual del vinculado en bd */
    private String estadoActual;

    /** detalle estado actual del vincualdo en bd */
    private String detalleEstadoActual;
    /** numero de documento del vinculado */
    private String numeroDocuemnto;
    /** tipo de documento del vinculado */
    private String tipoDocumento;

    /**
     * Constructor de la clase
     * 
     * @param tipoDocumento
     *            tipo de documento del vinculado
     * @param numeroDocumento
     *            número de documento del vinculado
     * @param estadoActual
     *            estado actual del vinculado en bd
     * @param detalleEstadoActual
     *            detalle estado actual del vinculado en bd
     * 
     */
    public VinculadoEstadoDetalleDTO(String tipoDocumento, String numeroDocumento, String estadoActual, String detalleEstadoActual) {
        this.estadoActual = estadoActual;
        this.detalleEstadoActual = detalleEstadoActual;
        this.numeroDocuemnto = numeroDocumento;
        this.tipoDocumento = tipoDocumento;

    }

    /**
     * Devuelve el valor de estadoActual
     * 
     * @return El valor de estadoActual
     */
    public String getEstadoActual() {
        return estadoActual;
    }

    /**
     * Establece el valor de estadoActual
     * 
     * @param estadoActual
     *            El valor por establecer para estadoActual
     */
    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }

    /**
     * Devuelve el valor de detalleEstadoActual
     * 
     * @return El valor de detalleEstadoActual
     */
    public String getDetalleEstadoActual() {
        return detalleEstadoActual;
    }

    /**
     * Establece el valor de detalleEstadoActual
     * 
     * @param detalleEstadoActual
     *            El valor por establecer para detalleEstadoActual
     */
    public void setDetalleEstadoActual(String detalleEstadoActual) {
        this.detalleEstadoActual = detalleEstadoActual;
    }

    /**
     * Devuelve el valor de numeroDocuemnto
     * 
     * @return El valor de numeroDocuemnto
     */
    public String getNumeroDocuemnto() {
        return numeroDocuemnto;
    }

    /**
     * Establece el valor de numeroDocuemnto
     * 
     * @param numeroDocuemnto
     *            El valor por establecer para numeroDocuemnto
     */
    public void setNumeroDocuemnto(String numeroDocuemnto) {
        this.numeroDocuemnto = numeroDocuemnto;
    }

    /**
     * Devuelve el valor de tipoDocumento
     * 
     * @return El valor de tipoDocumento
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Establece el valor de tipoDocumento
     * 
     * @param tipoDocumento
     *            El valor por establecer para tipoDocumento
     */
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

}