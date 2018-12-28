package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.comunes.anotaciones.StringLengthValidator;

/**
 * <b>Descripción:</b> Estructura que almacena el resumen de información de la cuenta de un vinculado en el Sistema de Cuentas Individuales.<br/>
 * @author Yesika Ramirez <yeramirez@heinsohn.com.co>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoInformacionCuentaVinculado", propOrder = {
    "saldoTotalCuenta",
    "valorSubsidioBEPS",
    "equivalenciaSemanasConSubsidio",
    "equivalenciaSemanasSinSubsidio"
})
public class TipoInformacionCuentaVinculado {
	
	/**
	 * Saldo Total Cuenta Individual
	 * */
	@XmlElement
	protected String saldoTotalCuenta;
	
	/**
	 * Valor del subsidio contingente generado en la Cuenta Individual del vinculado.
	 * */
	@XmlElement
	protected String valorSubsidioBEPS;
	
	/**
	 * Equivalencia en semanas para el vinculado contando el subsidio.
	 * */
	@XmlElement
	protected String equivalenciaSemanasConSubsidio;
	
	/**
	 * Equivalencia en semanas para el vinculado sin contar el subsidio.
	 * */
	@XmlElement
	protected String equivalenciaSemanasSinSubsidio;
	
	 /**
     * Devuelve el valor del atributo saldoTotalCuenta.
     *
     * @return El valor del atributo saldoTotalCuenta
     */
	@StringLengthValidator(min=0, max = 18, message = "{0} debe tener una longitud de min 0 y max  18 caracteres")
	public String getSaldoTotalCuenta() {
		return saldoTotalCuenta;
	}

	public void setSaldoTotalCuenta(String saldoTotalCuenta) {
		this.saldoTotalCuenta = saldoTotalCuenta;
	}
	
	 /**
     * Devuelve el valor del atributo valorSubsidioBEPS.
     *
     * @return El valor del atributo valorSubsidioBEPS
     */
	@StringLengthValidator(min=0, max = 18, message = "{0} debe tener una longitud de min 0 y max  18 caracteres")
	public String getValorSubsidioBEPS() {
		return valorSubsidioBEPS;
	}
	
	 /**
     * Establece el valor del atributo valorSubsidioBEPS.
     *
     * @param valorSubsidioBEPS El valor por establecer para el atributo valorSubsidioBEPS
     */
	public void setValorSubsidioBEPS(String valorSubsidioBEPS) {
		this.valorSubsidioBEPS = valorSubsidioBEPS;
	}
	
	 /**
     * Devuelve el valor del atributo equivalenciaSemanasConSubsidio.
     *
     * @return El valor del atributo equivalenciaSemanasConSubsidio
     */
	@StringLengthValidator(min=0, max = 7, message = "{0} debe tener una longitud de min 0 y max 7 caracteres")
	public String getEquivalenciaSemanasConSubsidio() {
		return equivalenciaSemanasConSubsidio;
	}
	
	 /**
     * Establece el valor del atributo equivalenciaSemanasConSubsidio.
     *
     * @param equivalenciaSemanasConSubsidio El valor por establecer para el atributo equivalenciaSemanasConSubsidio
     */
	public void setEquivalenciaSemanasConSubsidio(
			String equivalenciaSemanasConSubsidio) {
		this.equivalenciaSemanasConSubsidio = equivalenciaSemanasConSubsidio;
	}
	
	/**
     * Devuelve el valor del atributo equivalenciaSemanasSinSubsidio.
     *
     * @return El valor del atributo equivalenciaSemanasSinSubsidio
     */
	@StringLengthValidator(min=0, max = 7, message = "{0} debe tener una longitud de min 0 y max  7 caracteres")
	public String getEquivalenciaSemanasSinSubsidio() {
		return equivalenciaSemanasSinSubsidio;
	}
	
	 /**
     * Establece el valor del atributo equivalenciaSemanasSinSubsidio.
     *
     * @param equivalenciaSemanasSinSubsidio El valor por establecer para el atributo equivalenciaSemanasSinSubsidio
     */
	public void setEquivalenciaSemanasSinSubsidio(
			String equivalenciaSemanasSinSubsidio) {
		this.equivalenciaSemanasSinSubsidio = equivalenciaSemanasSinSubsidio;
	}
}
