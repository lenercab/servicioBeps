package co.gov.colpensiones.beps.schemas._1_0.personas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import co.gov.colpensiones.beps.comunes.anotaciones.IgnoreNullsValidator;
import co.gov.colpensiones.beps.comunes.anotaciones.StringLengthValidator;

/**
 * <b>Descripcion:</b> Clase que Contiene la información de estado y detalle estado vinculado<br/>
 * <b>Caso de Uso:</b> BEPS - GVI-VIN-3-FAB-12-ConsultarTransicionesDeEstadoPermitidas <br/>
 *
 * @author Arnold Rios Delgado <arrios@heinsohn.com.co>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoEstadoVinculadoBeps", propOrder = {
    "estadoActualVinculado",
    "detalleEstadoActualVinculado"
})
@XmlRootElement
public class TipoEstadoVinculadoBeps {//TipoEstadoDetalleVinculado

    /** Atributo para indicar el estado del vinculado.*/
    @XmlElement
    protected String estadoActualVinculado;
    /** Atributo para indicar el código del detalle del estado del vinculado.*/
    @XmlElement
    protected String detalleEstadoActualVinculado;

    /**
     * Devuelve el valor de estadoActualVinculado
     * @return El valor de estadoActualVinculado
     */
    @IgnoreNullsValidator
    @StringLengthValidator(min = 1, max = 1, message = "{0} debe tener una longitud de 1 carácter")
    public String getEstadoActualVinculado() {
        return estadoActualVinculado;
    }

    /**
     * Establece el valor de estadoActualVinculado
     * @param estadoActualVinculado El valor por establecer para estadoActualVinculado
     */
    public void setEstadoActualVinculado(String estadoActualVinculado) {
        this.estadoActualVinculado = estadoActualVinculado;
    }

    /**
     * Devuelve el valor de detalleEstadoActualVinculado
     * @return El valor de detalleEstadoActualVinculado
     */
    @IgnoreNullsValidator
    @StringLengthValidator(min = 3, max = 3, message = "{0} debe tener una longitud de 3 caracteres")
    public String getDetalleEstadoActualVinculado() {
        return detalleEstadoActualVinculado;
    }

    /**
     * Establece el valor de detalleEstadoActualVinculado
     * @param detalleEstadoActualVinculado El valor por establecer para detalleEstadoActualVinculado
     */
    public void setDetalleEstadoActualVinculado(String detalleEstadoActualVinculado) {
        this.detalleEstadoActualVinculado = detalleEstadoActualVinculado;
    }
}