package co.gov.colpensiones.beps.comunes.utilidades;

import java.util.regex.Pattern;

/**
 * 
 * @author jgomez
 *
 * Esta clase se utiliza para validar las expresiones regulares
 */

public class RegexUtil {

	
	/**
	 * Evalua una extesion regular utilizando un patron.
	 * @param pattern
	 * @param value
	 * @return
	 */
	public static boolean isValid(String pattern, String value) {
		return Pattern.compile(pattern).matcher(value).matches();
	}

	/**
	 * Evalua si una fecha es valida o no
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static boolean isDateTimeValid(int year, int month, int day) {
		if (month == 2) {
			if (day == 29) {
				if (year % 4 != 0 || year % 100 == 0 && year % 400 != 0) {
					return false;
				}
			} else if (day > 28) {
				return false;
			}
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			if (day > 30) {
				return false;
			}
		} else {
			if (day > 31) {
				return false;
			}

		}
		return true;
	}
}
