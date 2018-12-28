package co.gov.colpensiones.beps.comunes.anotaciones;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ObjectValidator: 
 * Esta anotacién se usa para evaluar las anotaciones que tenga un objeto, también se puede utilizar de manera recursiva.
 * @author jgomez
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ObjectValidator {
	String message() default "{0} no tiene un valor válido ";
}
