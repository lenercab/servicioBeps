package co.gov.colpensiones.beps.comunes.utilidades;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Esta clase es util para la lectura de un archivo properties
 * @author jgomez
 *
 */

public class ResourceUtil {

	/**
	 * Lee un valor de una propiedad de un archivo properties
	 * @param fileResourceName
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getResourceValue(String fileResourceName, String key)
			throws Exception {
		
		
		String keyValue = null;
		ResourceBundle resources = ResourceBundle.getBundle(fileResourceName,
				Locale.getDefault());

		keyValue = resources.getString(key);

		return keyValue;
	}
}
