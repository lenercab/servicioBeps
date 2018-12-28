package co.gov.colpensiones.beps.comunes.utilidades;

/**
 * 
 * @author jgomez
 * Esta clase sirve para formatear cadenas
 *
 */

public class StringUtil {

	
	/**
	 * Formatea una cadena
	 * @param text
	 * @param params
	 * @return
	 */
	public static String Format(String text, Object... params){
		
		for (int i = 0; i < params.length; i++) 
			text=text.replace("{"+i+"}", params[i]==null?"":params[i].toString());		
		return text;
	}
	/**
	 * Formatea una linea
	 * @param text
	 * @param params
	 * @return
	 */
	
	public static String FormatLine(String text, Object... params) {

		for (int i = 0; i < params.length; i++)
			text = text.replace("{" + i + "}", params[i] == null ? ""
					: params[i].toString());
		return text + "\n";
	}
}
