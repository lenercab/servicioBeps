package co.gov.colpensiones.beps.comunes.utilidades;

/**
 * <b>Descripcion:</b> Clase que almacena las constantes para el modelo de estados para vinculación <br/>
 *  
 * @author csalazar <csalazar@heinsohn.com.co>
 */
public class ConstantesEstados {
    
    /** Constante que corresponde al estado de nivel 2 Activo*/
    public static final String ESTADO_ACTIVO = "A";
    
    /** Constante que corresponde al estado de nivel 2 Retirado*/
    public static final String ESTADO_RETIRADO = "R";
    
    /** Constante que corresponde al estado de nivel 2 Suspendido*/
    public static final String ESTADO_SUSPENDIDO = "S";

    /** Constante que corresponde al origen del cambio del estado del previnculado a vinculado.*/
    public static final String ORIGEN_CAMBIO_ESTADO_RESULTADO_PREVINCULACION = "Aprobación Pre-Vinculación";
    
    /** Constante que corresponde al origen del cambio del estado del vinculado.*/
    public static final String ORIGEN_CAMBIO_ESTADO_REGISTRAR_SOLICITUD_OTORGAMIENTO = "Registrar Solicitud de Otorgamiento";
}
