package co.gov.colpensiones.beps.comunes.utilidades;

import java.util.ArrayList;

import co.gov.colpensiones.beps.comunes.anotaciones.Validator;


/**
 * 
 * @author jgomez
 *
 * Esta clase es un validador genérico que se encarga de recibir un objeto y 
 * ejecutar las anotaciones que tiene en cada uno de sus métodos get.
 *
 * @param <T>
 */

public class Validador<T> {

	/**
	 * Método encargado de realizar las validaciones de los campos del objeto a analizar.
	 * @param oInstance
	 * @return
	 * @throws Exception
	 */
	public String ValidarDataContract(T oInstance) throws Exception{
		
		Validator validator=new Validator();
		ArrayList<String> lst= validator.errorList(oInstance);
		
		String sError="";
		for (String err : lst)
			sError+=err+"\n";
		
		
		
		
		return sError;
	}
	
}
