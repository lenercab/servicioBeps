/**
 * 
 */
package co.gov.colpensiones.beps.vinculacion.businesslogic.crear;

/**
 * <b>Descripcion:</b> Clase que contiene la información necesaria para crear la cuenta individual<br/>
 * <b>Caso de Uso:</b> GVI-VIN-1-FAB-02-RegistrarVinculado <br/>
 * 
 * @author Helen Patricia Acero <hacero@heinsohn.com.co>
 */
public class InformacionVinculado {
    private String fechaNacimiento;
    private String genero;
    private String numeroDocumento;
    private String primerApellido;
    private String primerNombre;
    private String segundoApellido;
    private String segundoNombre;
    private String tipoDocumento;
    private Long identificadorVinculado;
    /**
     * fecha real de vinculación del ciudadano 
     */
    private String fechaVinculacion;

    /**
     * Devuelve el valor de fechaNacimiento
     * 
     * @return El valor de fechaNacimiento
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Establece el valor de fechaNacimiento
     * 
     * @param fechaNacimiento
     *            El valor por establecer para fechaNacimiento
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Devuelve el valor de genero
     * 
     * @return El valor de genero
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Establece el valor de genero
     * 
     * @param genero
     *            El valor por establecer para genero
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * Devuelve el valor de numeroDocumento
     * 
     * @return El valor de numeroDocumento
     */
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * Establece el valor de numeroDocumento
     * 
     * @param numeroDocumento
     *            El valor por establecer para numeroDocumento
     */
    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    /**
     * Devuelve el valor de primerApellido
     * 
     * @return El valor de primerApellido
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * Establece el valor de primerApellido
     * 
     * @param primerApellido
     *            El valor por establecer para primerApellido
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * Devuelve el valor de primerNombre
     * 
     * @return El valor de primerNombre
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * Establece el valor de primerNombre
     * 
     * @param primerNombre
     *            El valor por establecer para primerNombre
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     * Devuelve el valor de segundoApellido
     * 
     * @return El valor de segundoApellido
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * Establece el valor de segundoApellido
     * 
     * @param segundoApellido
     *            El valor por establecer para segundoApellido
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     * Devuelve el valor de segundoNombre
     * 
     * @return El valor de segundoNombre
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     * Establece el valor de segundoNombre
     * 
     * @param segundoNombre
     *            El valor por establecer para segundoNombre
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
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

    /**
     * Devuelve el valor de identificadorVinculado
     * 
     * @return El valor de identificadorVinculado
     */
    public Long getIdentificadorVinculado() {
        return identificadorVinculado;
    }

    /**
     * Establece el valor de identificadorVinculado
     * 
     * @param identificadorVinculado
     *            El valor por establecer para identificadorVinculado
     */
    public void setIdentificadorVinculado(Long identificadorVinculado) {
        this.identificadorVinculado = identificadorVinculado;
    }

	/**
	 * Devuelve el valor de fechaVinculacion
	 * @return El valor de fechaVinculacion
	 */
	public String getFechaVinculacion() {
		return fechaVinculacion;
	}

	/**
	 * Establece el valor de fechaVinculacion
	 * @param fechaVinculacion El valor por establecer para fechaVinculacion
	 */
	public void setFechaVinculacion(String fechaVinculacion) {
		this.fechaVinculacion = fechaVinculacion;
	}

}
