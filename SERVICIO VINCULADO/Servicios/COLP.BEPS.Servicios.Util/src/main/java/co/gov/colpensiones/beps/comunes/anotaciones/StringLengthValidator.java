package co.gov.colpensiones.beps.comunes.anotaciones;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @StringLengthValidator
 * Valida el tamaño de una cadena de texto entre los límites máximos y mínimos.
 * @author jgomez
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface StringLengthValidator {

	String message() default "{0} must contain at least {1} character(s) and max {2} character(s)"; 
	int min();
	int max();
}
