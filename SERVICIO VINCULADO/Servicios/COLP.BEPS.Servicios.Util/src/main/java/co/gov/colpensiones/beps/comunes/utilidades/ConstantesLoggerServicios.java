package co.gov.colpensiones.beps.comunes.utilidades;

/**
 * <b>Descripcion:</b> Clase que Almacena las constantes para nombrar los logger del servidor.<br/>
 * 
 * @author csalazar <csalazar@heinsohn.com.co>
 */
public class ConstantesLoggerServicios {

    // ************************ SvcPrevinculado **********************************

    /** Constante para mantener el nombre de la operacion correspondiente al logger. */
    public static String SERVICIO_PREVINCULADO_CALCULO_NIVEL = "SvcPrevinculado.calcularNivelSisben";

    /** Constante para mantener el nombre de la operacion correspondiente al logger. */
    public static String SERVICIO_PREVINCULADO_CREAR = "SvcPrevinculado.crear";

    /** Constante para mantener el nombre de la operacion correspondiente al logger. */
    public static String SERVICIO_PREVINCULADO_REG_RESULTADO_ESTUDIO = "SvcPrevinculado.registrarResultadoEstudio";

    // ************************ SvcVinculado **********************************

    /** Constante para mantener el nombre de la operacion correspondiente al logger. */
    public static String SERVICIO_VINCULADO_ACTUALIZAR_INFO_SENSIBLE = "SvcVinculado.actualizarDatosBasicos";

    /** Constante para mantener el nombre de la operacion correspondiente al logger. */
    public static String SERVICIO_VINCULADO_CONSULTAR = "SvcVinculado.consultar";

    /** Constante para mantener el nombre de la operacion correspondiente al logger. */
    public static String SERVICIO_VINCULADO_CREAR = "SvcVinculado.crear";

    /** Constante para mantener el nombre de la operacion correspondiente al logger. */
    public static String SERVICIO_VINCULADO_ACTUALIZAR_INFO_NO_SENSIBLE = "SvcVinculado.actualizarDatosGenerales";

    /** Constante para mantener el nombre de la operacion correspondiente al logger. */
    public static String SERVICIO_VINCULADO_CONSULTAR_SALDOS_MOVIMIENTOS = "SvcVinculado.consultarSaldosMovimientos";
    
    /** Constante para mantener el nombre de la operacion correspondiente al logger. */
    public static String SERVICIO_VINCULADO_CONSULTAR_TRANSICION_ESTADO = "SvcVinculado.consultarTransicionEstado";
    
    /** Constante para mantener el nombre de la operacion correspondiente al logger. */
    public static String SERVICIO_VINCULADO_REALIZAR_CAMBIO_ESTADO_VINCULADO = "SvcVinculado.realizarCambioEstadoVinculado";
    
    /** Constante para mantener el nombre de la operacion correspondiente al logger. */
    public static String SERVICIO_VINCULADO_REALIZAR_CAMBIO_ESTADO_RETIRO = "SvcVinculado.realizarCambioEstadoRetiro";
    
    /** Constante para mantener el nombre de la operacion correspondiente al logger. */
    public static String SERVICIO_VINCULADO_CONSULTAR_TRANSICION_ESTADO_RETIRO = "SvcVinculado.consultarTransicionEstadoRetiro";

    // ************************ SvcBeneficios **********************************

    /** Constante para mantener el nombre de la operación correspondiente al logger. */
    public static String SERVICIO_BENEFICIOS_REGISTRAR_SOLICITUD = "SvcBeneficios.registrarSolicitud";

    /** Constante para mantener el nombre de la operación correspondiente al logger. */
    public static String SERVICIO_BENEFICIOS_VALIDACIONES_AUTOMATICAS = "ValidacionesAutomaticasMDB.validarSolicitudDestinacionRecursos";
    
    /** Constante para mantener el nombre de la operación correspondiente al logger. */
    public static String SERVICIO_BENEFICIOS_REGISTRAR_SOLICITUD_HEREDEROS = "SvcBeneficios.registrarSolicitudHerederos";

    // ************************ SvcCertificados **********************************

    /** Constante para mantener el nombre de la operación correspondiente al logger. */
    public static String SERVICIO_CERTIFICADOS_VINCULACION = "SvcCertificados.certificadoVinculacion";
    
    /** Constante para mantener el nombre de la operación correspondiente al logger. */
    public static String SERVICIO_CERTIFICADOS_NO_VINCULACION = "SvcCertificados.certificadoNoVinculacion";
    
    /** Constante para mantener el nombre de la operación correspondiente al logger. */
    public static String SERVICIO_CERTIFICADOS_VINCULACION_SALDOS = "SvcCertificados.certificadoVinculacionSaldos";
    
    /** Constante para mantener el nombre de la operación correspondiente al logger. */
    public static String SERVICIO_CERTIFICADOS_VINCULACION_MOVIMIENTOS = "SvcCertificados.certificadoVinculacionMovimientos";
    
    /** Constante para mantener el nombre de la operación correspondiente al logger. */
    public static String SERVICIO_CERTIFICADOS_VINCULACION_OTORGADO = "SvcCertificados.certificadoVinculacionOtorgado";

 // ************************ SvcVinculadoAsofondos **********************************

    /** Constante para mantener el nombre de la operación correspondiente al logger. */
    public static String SERVICIO_VINCULADO_ASOFONDOS = "SvcVinculadoBepsAsofondos.consultar";
    
    /* ************** Constantes para almacenar los nombres de campos utilizados en la información de metadata para logs. ************** */

    /** Constante para mantener el nombre del metadata tipo documento. */
    public static String METADATA_TIPO_DOCUMENTO = "tipo_documento";
    
    /** Constante para mantener el nombre del metadata tipo documento para la 
     * operación de registro de solicitud de devolución de ahorros a herederos. */
    public static String METADATA_TIPO_DOCUMENTO_HEREDEROS = "tipoDocumento";

    /** Constante para mantener el nombre del metadata tipo documento. */
    public static String METADATA_NUM_DOCUMENTO = "numero_documento";

    /** Constante para mantener el nombre del metadata número documento para la 
     * operación de registro de solicitud de devolución de ahorros a herederos. */
    public static String METADATA_NUM_DOCUMENTO_HEREDEROS = "numeroDocumento";
    
    /** Constante para mantener el nombre del metadata número de radicado solicitud para la 
     * operación de registro de solicitud de devolución de ahorros a herederos. */
    public static String METADATA_NUM_RADICACION_HEREDEROS = "numeroRadicacion";
    
    /** Constante para mantener el nombre del metadata número de radicado solicitud. */
    public static String METADATA_NUM_RADICADO_SOLICITUD = "numero_radicado_solicitud";

    /** Constante para mantener el nombre del metadata número de radicado padre. */
    public static String METADATA_NUM_RADICADO_PADRE = "numero_radicado_padre";

    /** Constante para mantener el nombre del metadata número de radicado. */
    public static String METADATA_NUM_RADICADO = "numero_radicado";
    
    /** Constante para mantener el nombre del metadata número de radicado. */
    public static String METADATA_ID_CORRELACION = "id_correlacion";
    
    /** Constante para mantener el nombre del metadata id correlacion para la 
     * operación de registro de solicitud de devolución de ahorros a herederos. */
    public static String METADATA_ID_CORRELACION_HEREDEROS = "idCorrelacion";
    
    /** Constante para mantener el nombre del metadata  usuario de sistema externo. */
    public static String  USUARIO_SISTEMA_EXTERNO = "usuario_sistema_externo";
    
    /** Constante para mantener el nombre del metadata usuario sistema externo para la 
     * operación de registro de solicitud de devolución de ahorros a herederos. */
    public static String USUARIO_SISTEMA_EXTERNO_HEREDEROS = "usuarioSistemaExterno";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_CODIGO_CONSULTA = "codigo_consulta";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_CONSULTA = "consulta";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_CANTIDAD_DIRECCIONES = "cantidad_direcciones";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_CANTIDAD_TELEFONOS = "cantidad_telefonos";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_CANTIDAD_CORREOS = "cantidad_correos";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_CANTIDAD_RECHAZOS = "cantidad_rechazos";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_IDENTIFICADOR_PERSONA = "id_persona";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_IDENTIFICADOR_REGISTRO = "id_registro";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_IDENTIFICADOR_SOLICITUD = "id_solicitud";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_IDENTIFICADOR_VINCULADO = "id_vinculado";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_USUARIO_ULTIMO_CAMBIO = "usuario_ultimo_cambio";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_USUARIO_VINCULACION = "usuario_vinculacion";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_CANAL_VINCULACION = "canal_vinculacion";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_CANAL_SISTEMA = "canal_sistema";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_CANAL_MODIFICACION = "canal_modificacion";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_IDENTIFICADOR_DIRECCIONES = "id_direcciones";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_IDENTIFICADOR_TELEFONOS = "id_telefonos";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_IDENTIFICADOR_CORREOS = "id_correos";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_GENERO_BEPS = "genero_beps";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_ESTADO_VINCULADO = "estado_vinculado";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_DETALLE_ESTADO_VINCULADO = "detalle_estado_vinculado";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_FECHA_CORTE = "fecha_corte";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_PARAMETRO = "parametro";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_TIEMPO_LLAMADO_PLENITUD = "TIEMPO_LLAMADO_PLENITUD";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_COLA_MENSAJERIA = "cola_mensajeria";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_PUNTAJE = "area";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_AREA = "puntaje";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_FECHA_NACIMIENTO = "fecha_nacimiento";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_CANTIDAD_REINTENTOS_CUENTA = "cantidad_reintentos_cuenta";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_DATOS_PERSONA = "datos_persona";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_NOMBRE_AFP = "nombre_afp";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_ETAPA_SOLICITUD = "etapa_solicitud";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_FECHA_INICIAL = "fecha_inicial";

    /** Constante para mantener el nombre del metadata asociado al log. */
    public static String METADATA_FECHA_FINAL = "fecha_final";

    /** Constante para mantener el tiempo ejecución del metadata asociado al log. */
    public static String METADATA_TIEMPO_EJECUCION="TIEMPO_EJECUCION";
    
    /** Constante para mantener la metadata de numero de cuenta asociado al log. */
    public static String METADATA_NUMERO_CUENTA="NUMERO_CUENTA";
    
    /** Constante para mantener la metadata del nombre del tipo de cuenta asociado al log. */
    public static String METADATA_NOMBRE_TIPO_CUENTA="NOMBRE_TIPO_CUENTA";
    
    /*
     * Constantes para almacenar los nombres de campos utilizados en la información de metadata para logs del proceso de actualizar
     * información sensible
     */

    /**
     * Constante para mantener el nombre del metadata del parametro ZIdentificacion utilizado en la ejecución del SP de actualización de
     * información sensible sobre Plenitud
     */
    public static String METADATA_ZIDENTIFICACION = "ZIdentificacion";
    /**
     * Constante para mantener el nombre del metadata del parametro ZTipoIdentifica utilizado en la ejecución del SP de actualización de
     * información sensible sobre Plenitud
     */
    public static String METADATA_ZTIPOIDENTIFICA = "ZTipoIdentifica";
    /**
     * Constante para mantener el nombre del metadata del parametro ZPrimerNombre utilizado en la ejecución del SP de actualización de
     * información sensible sobre Plenitud
     */
    public static String METADATA_ZPRIMERNOMBRE = "ZPrimerNombre";
    /**
     * Constante para mantener el nombre del metadata del parametro ZPrimerApellido utilizado en la ejecución del SP de actualización de
     * información sensible sobre Plenitud
     */
    public static String METADATA_ZPRIMERAPELLIDO = "ZPrimerApellido";
    /**
     * Constante para mantener el nombre del metadata del parametro ZSegundoNombre utilizado en la ejecución del SP de actualización de
     * información sensible sobre Plenitud
     */
    public static String METADATA_ZSEGUNDONOMBRE = "ZSegundoNombre";
    /**
     * Constante para mantener el nombre del metadata del parametro ZSegundoApellido utilizado en la ejecución del SP de actualización de
     * información sensible sobre Plenitud
     */
    public static String METADATA_ZSEGUNDOAPELLIDO = "ZSegundoApellido";
    /**
     * Constante para mantener el nombre del metadata del parametro ZFechaNacimiento utilizado en la ejecución del SP de actualización de
     * información sensible sobre Plenitud
     */
    public static String METADATA_ZFECHANACIMIENTO = "ZFechaNacimiento";
    /**
     * Constante para mantener el nombre del metadata del parametro ZSexo utilizado en la ejecución del SP de actualización de información
     * sensible sobre Plenitud
     */
    public static String METADATA_ZSEXO = "ZSexo";

    /* Constantes para campos en payload. */
    /** Constante para mostrar los mensajes de error en el payload del log. */
    public static final String MENSAJE_ERROR = "MensajesError";

}