
package co.gov.colpensiones.beps.comunes.anotaciones;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación para la validación de fechas en formato AAAAMMDD
 * 
 * @author Yenny Nustez <ynustez@heinsohn.com.co>
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DateValidator {
	String message() default "{0} no es una fecha válida con formato AAAAMMDD";
	String messageValidacionFechaActual() default "{0} no puede ser mayor a la fecha actual";
	boolean validarFechaActual() default false;
}
