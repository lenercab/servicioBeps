package co.gov.colpensiones.beps.comunes.anotaciones;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RegexValidator:
 * Valida una expresión regular definida por el usuario.
 * @author jgomez
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RegexValidator {
	String message() default "{0} does not match {1}";
	String pattern();
	
}
