package co.gov.colpensiones.beps.comunes.anotaciones;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <b>Descripcion:</b> Anotación para validación de campos obligatorios <br/>
 * 
 * @author Yenny Nustez Arevalo <ynustez@heinsohn.com.co>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OptionalFieldValidator {

	/**
	 * Mensaje de validación para la anotación
	 */
	String message() default "{0} es opcional"; 
}
