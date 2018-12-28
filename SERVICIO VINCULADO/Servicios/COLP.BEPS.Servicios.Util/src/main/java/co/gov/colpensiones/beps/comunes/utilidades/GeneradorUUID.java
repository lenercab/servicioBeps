package co.gov.colpensiones.beps.comunes.utilidades;

/**
 * 
 * Clase que obtiene un identificador único para las acciones que requerimos registrar en el log
 * 
 * @author lduque
 * 
 */
public class GeneradorUUID {

	/**
	 * Método que obtiene un identificador único para las acciones que son necesarias
	 * registrar en el log
	 * @return
	 */
	public static String crearUUID() {

		return java.util.UUID.randomUUID().toString();
	}

}