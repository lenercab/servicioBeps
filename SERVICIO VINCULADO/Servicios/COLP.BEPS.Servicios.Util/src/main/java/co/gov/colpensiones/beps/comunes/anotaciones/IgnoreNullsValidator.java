package co.gov.colpensiones.beps.comunes.anotaciones;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @IgnoreNullValidator: 
 * Esta anotación ignora los campos nulos, si el campo es diferente de nulo 
 * (Ejm : Vacio o '' ) se valida.
 * @author jgomez
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IgnoreNullsValidator {
	boolean ignoreNulls() default true; 
}
