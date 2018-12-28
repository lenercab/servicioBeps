package co.gov.colpensiones.beps.schemas._1_0.personas;

/**
 * <b>Descripción:</b> Clase que extiende de la respuesta del servicio <br/>
 * 
 * <b>Caso de Uso:</b> GVI-VIN-3-FAB-13-RealizarCambioEstadoVinculado <br/>
 * @author Arnold Rios Delgado <arrios@heinsohn.com.co>
 */
public class ExtendidoTipoRespuestaCambioEstado extends TipoRespuestaCambioEstado {


	/**
     * Constructor
     * @param numeroDocumento numero de documento del vinculado
     * @param tipoDocumento tipo de documento del vinculado
     * @param estado estado al que pasara el vinculado
     * @param detalleEstado detalle estado al que pasara el vinculado
     */
    public ExtendidoTipoRespuestaCambioEstado(TipoDocumentoPersonaNatural personaNatural, TipoEstadoVinculadoBeps tipoEstado) {
        this.vinculado = personaNatural;
        this.tipoEstadoDestinoVinculado = tipoEstado;
        this.resultadoCambioEstado="";
        this.tipoOperacion="";
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj instanceof ExtendidoTipoRespuestaCambioEstado) {
            ExtendidoTipoRespuestaCambioEstado respuestaCambioEstado = (ExtendidoTipoRespuestaCambioEstado) obj;
            if (respuestaCambioEstado.getVinculado().getNumeroDocumento().equals(this.vinculado.getNumeroDocumento())
                    && respuestaCambioEstado.getVinculado().getTipoDocumento().equals(this.vinculado.getTipoDocumento())) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }

    }

}