package co.gov.colpensiones.beps.comunes.dto;

/**
 * <b>Descripcion:</b> Clase que representa la tabla [parametricas].[vinc_transicion_estado]<br/>
 * <b>Caso de Uso:</b> BEPS - GVI-VIN-3-FAB-12-ConsultarTransicionesDeEstadoPermitidas <br/>
 * 
 * @author Arnold Rios Delgado <arrios@heinsohn.com.co>
 */
public class TransicionesEstadoPermitidasDTO {

    /** Estado incial */
    private String estadoInicial;

    /** detalle estado inicial */
    private String detalleEstadoInicial;
    /** estado final */
    private String estadoFinal;
    /** detalle estado final */
    private String detalleEstadoFinal;
    /** indicador de reactivar cuenta */
    private Boolean reactivaCuenta;
    /** indicadro de cancelar cuenta */
    private Boolean cancelaCuenta;

    /**
     * contructor de la clase
     * 
     * @param estadoInicial
     *            estado incial
     * @param detalleEstadoInicial
     *            detalle estado inicial
     * @param estadoFinal
     *            estado final
     * @param detalleEstadoFinal
     *            detalle estado final
     * @param reactivaCuenta
     *            indicador de reactivar cuenta
     * @param cancelaCuenta
     *            indicador de cancelar cuenta
     */
    public TransicionesEstadoPermitidasDTO(String estadoInicial, String detalleEstadoInicial, String estadoFinal,
            String detalleEstadoFinal, Boolean reactivaCuenta, Boolean cancelaCuenta) {
        this.estadoInicial = estadoInicial;
        this.detalleEstadoInicial = detalleEstadoInicial;
        this.estadoFinal = estadoFinal;
        this.detalleEstadoFinal = detalleEstadoFinal;
        this.reactivaCuenta = reactivaCuenta;
        this.cancelaCuenta = cancelaCuenta;
    }

    /**
     * Devuelve el valor de estadoInicial
     * 
     * @return El valor de estadoInicial
     */
    public String getEstadoInicial() {
        return estadoInicial;
    }

    /**
     * Establece el valor de estadoInicial
     * 
     * @param estadoInicial
     *            El valor por establecer para estadoInicial
     */
    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    /**
     * Devuelve el valor de detalleEstadoInicial
     * 
     * @return El valor de detalleEstadoInicial
     */
    public String getDetalleEstadoInicial() {
        return detalleEstadoInicial;
    }

    /**
     * Establece el valor de detalleEstadoInicial
     * 
     * @param detalleEstadoInicial
     *            El valor por establecer para detalleEstadoInicial
     */
    public void setDetalleEstadoInicial(String detalleEstadoInicial) {
        this.detalleEstadoInicial = detalleEstadoInicial;
    }

    /**
     * Devuelve el valor de estadoFinal
     * 
     * @return El valor de estadoFinal
     */
    public String getEstadoFinal() {
        return estadoFinal;
    }

    /**
     * Establece el valor de estadoFinal
     * 
     * @param estadoFinal
     *            El valor por establecer para estadoFinal
     */
    public void setEstadoFinal(String estadoFinal) {
        this.estadoFinal = estadoFinal;
    }

    /**
     * Devuelve el valor de detalleEstadoFinal
     * 
     * @return El valor de detalleEstadoFinal
     */
    public String getDetalleEstadoFinal() {
        return detalleEstadoFinal;
    }

    /**
     * Establece el valor de detalleEstadoFinal
     * 
     * @param detalleEstadoFinal
     *            El valor por establecer para detalleEstadoFinal
     */
    public void setDetalleEstadoFinal(String detalleEstadoFinal) {
        this.detalleEstadoFinal = detalleEstadoFinal;
    }

    /**
     * Devuelve el valor de reactivaCuenta
     * 
     * @return El valor de reactivaCuenta
     */
    public Boolean getReactivaCuenta() {
        return reactivaCuenta;
    }

    /**
     * Establece el valor de reactivaCuenta
     * 
     * @param reactivaCuenta
     *            El valor por establecer para reactivaCuenta
     */
    public void setReactivaCuenta(Boolean reactivaCuenta) {
        this.reactivaCuenta = reactivaCuenta;
    }

    /**
     * Devuelve el valor de cancelaCuenta
     * 
     * @return El valor de cancelaCuenta
     */
    public Boolean getCancelaCuenta() {
        return cancelaCuenta;
    }

    /**
     * Establece el valor de cancelaCuenta
     * 
     * @param cancelaCuenta
     *            El valor por establecer para cancelaCuenta
     */
    public void setCancelaCuenta(Boolean cancelaCuenta) {
        this.cancelaCuenta = cancelaCuenta;
    }

}