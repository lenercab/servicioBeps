package co.gov.colpensiones.beps.comunes.anotaciones;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @RangeValidator:
 *  Valida que un número no tenga un valor por fuera de los lúmites establecidos.
 * 
 * @author jgomez
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RangeValidator {
	String message() default "{0} must be in a range between {1} and {2}"; 
	double min();
	double max();
}
