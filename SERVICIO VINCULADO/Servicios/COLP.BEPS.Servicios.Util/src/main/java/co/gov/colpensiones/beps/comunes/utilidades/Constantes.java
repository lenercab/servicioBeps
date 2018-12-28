package co.gov.colpensiones.beps.comunes.utilidades;


/**
 * <b>Descripcion:</b> Clase que contiene las constantes de los servicios<br/>
 * <b>Caso de Uso:</b> FAB- <br/>
 * 
 * @author ddelaroche <ddelaroche@heinsohn.com.co>
 * @author Arnold Rios Delgado <arrios@heinsohn.com.co>
 */
public class Constantes {

    /*
     * CONSTANTES COMUNES A TODOS LOS SERVICIOS ************************************************************************
     */

    /** Código para la invocacion exitosa */
    public static final String COD_INVOCACION_EXITOSA = "0";

    /** Código para la invocacion exitosa de un procedimiento AS/400 */
    public static final String COD_INVOCACION_EXITOSA_AS400 = "0000000";

    /** Descripción que se retorna cuando se ejecutó una operación del servicio correctamente */
    public static final String DESC_INVOCACION_EXITOSA = "";

    /** Código que se retorna cuando ocurre un error inesperado en la ejecución del servicio */
    public static final String COD_ERROR_INTERNO = "03";

    /** Código que se retorna cuando ocurre un error de lógica de negocio en la ejecución del servicio */
    public static final String COD_ERROR_LOGICA_NEGOCIO = "04";
    
    /** Código que se retorna cuando ocurre un error de conexion a Plenitud para el servicio de vinculacion */
    public static final String COD_ERROR_VINCULADO_INVOCAR_PLENITUD = "04";
    
    /** Código que se retorna cuando ocurre un error de conexion a Plenitud para el servicio de previnculacion */
    public static final String COD_ERROR_INVOCAR_PLENITUD = "05";

    /** Descripción que se retorna cuando se tiene un error de código 05-Error invocando plenitud */
    public static final String DESC_ERROR_INVOCAR_PLENITUD = "SGBEPS001";
    
    /** Descripción que se retorna cuando se tiene un error de código 03 error inesperado en la ejecución del servicio */
    public static final String DESC_ERROR_INTERNO = "Error interno durante la ejecución del servicio";

    /** Código para el formato inválido */
    public static final String COD_FORMATO_INVALIDO_OBLIGATORIO_NO_RECIBIDO = "01";
    
    /** Constante parametro 0 */
    public static final String PARAMETRO0 = "parametro0";

    /** Constante parametro 1 */
    public static final String PARAMETRO1 = "parametro1";

    /** mensaje para datos de entrada */
    public static final String MSJ_DATO_OBLIGATORIO = PARAMETRO0 + " es obligatorio\n";

    /** mensaje para validar longitud de un dato de entrada */
    public static final String MSJ_lONGITUD_DATO = PARAMETRO0 + " no es válido, debe tener una longitud de " + PARAMETRO1 + " caracteres\n";

    /** mensaje cuando la fecha validada es mayor a la fecha actual del sistema */
    public static final String MSJ_FECHA_MAYOR_FECHA_ACTUAL = PARAMETRO0 + " no puede ser mayor a la fecha actual\n";

    /** mensaje cuando se valida los decimales de un dato de entrada */
    public static final String MSJ_DATO_CON_DECIMALES = PARAMETRO0
            + " debe tener una longitud de 1 a 3 carácter enteros con dos decimales\n";

    /** nombre del servicio de previnculado */
    public static final String NOMBRE_WS_PREVINCULADO = "SvcPrevinculado";

    /** mensaje para tipo error */
    public static final String TIPO_ERROR = "TipoError: ";

    /** Mensaje para servicio web */
    public static final String SERVICIO_WEB = "ServicioWeb";

    /** Mensaje para los procesos asíncronos */
    public static final String PROCESOS_ASINCRONOS_BEPS = "ProcesosAsincronosBeps";

    /** Mensaje para url */
    public static final String URL = "URL: ";

    /** mensaje para request */
    public static final String REQUEST = "Request: ";

    /** mensaje para error */
    public static final String ERROR = "Error generado durante la ejecución del servicio web de Colpensiones. ";

    /** mensaje de error cuando se genera en el acceso a datos */
    public static final String ERROR_BD = "Error acceso a datos: ";

    /** mensaje para enviar al log cuando existe error */
    public static final String MSJ_ERROR_LOG = "MensajesError";

    /** Mesaje no puede serializar */
    public static final String MSJ_NO_SERIALIZAR = "No se puede serializar el objeto. ";

    /** Mesaje no puede serializar */
    public static final String FECHA_MINIMA_NACIMIENTO = "fecha-minima-nacimiento";

    /** mensaje genérico para datos no válidos */
    public static final String MSJ_ERROR_DATO_NO_VALIDO = PARAMETRO0 + " no es válido, debe ingresar alguna de las siguientes opciones: ";

    /** mensaje genérico para datos numéricos no válidos */
    public static final String MSJ_ERROR_DATO_NUMERICO = PARAMETRO0 + " no es válido, solo se permiten caracteres numéricos.\n";

    /** Formato para fecha */
    public static final String FORMATO_FECHA_AAAAMMDD = "yyyyMMdd";

    /** Formato para fecha */
    public static final String FORMATO_FECHA_AAAA_MM_DD = "yyyy-MM-dd";

    /** variable para identificar el Si como S */
    public static final String SI = "S";

    /** variable para identificar el No como N */
    public static final String NO = "N";

    /** variable para identificiar el genero Femenino en beps */
    public static final String GENERO_MASCULINO = "1";

    /** variable para identificiar el genero Femenino en beps */
    public static final String GENERO_FEMENINO = "2";

    /** Estado vinculacion - Previnculado */
    public static final String ESTADO_PREVINCULADO = "P";

    /*
     * CONSTANTES PARA EXPRESIONES REGULARES **********************************************************************
     */

    /** Expresion regular para validar datos numéricos */
    public static final String ER_DATOS_NUMERICOS = "[0-9]*";

    /** Expresion regular para validar el dato idCorrelacion para la creación y actualizacion de datos del vinculado */
    public static final String ER_VALIDAR_ID_CORRELACION = "^([0-9_]){1,50}$";

    /** Expresion regular para validar el dato usuarioSistemaExterno para actualizacion de datos del vinculado */
    public static final String ER_VALIDAR_USUARIO_SISTEMA = "[^\n\r]{5,60}";

    /** Expresion regular para validar el dato usuarioSistemaExterno para actualizacion de datos del vinculado */
    public static final String ER_VALIDAR_COD_DEPARTAMENTO = "^[0-9]{2,2}$";

    /** Expresion regular para validar el dato usuarioSistemaExterno para actualizacion de datos del vinculado */
    public static final String ER_VALIDAR_COD_MUNICIPIO = "^[0-9]{5,5}$";

    /** Expresion regular para validar el dato primerApellido para actualizacion de datos del vinculado */
    public static final String ER_VALIDAR_PRIMER_NOMBRE_APELLIDO = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäÄëËïÏöÖüÜ]+[a-zA-ZñÑáéíóúÁÉÍÓÚäÄëËïÏöÖüÜ\\s]*$";

    /** Expresion regular para validar el dato primerApellido para actualizacion de datos del vinculado */
    public static final String ER_VALIDAR_SEGUNDO_NOMBRE_APELLIDO = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäÄëËïÏöÖüÜ\\s]*$";

    /*
     * CONSTANTES CATEGORIAS HOMOLOGACION (vinc_homologacion)******************************************************
     */

    /** Categoria correspondiente a los tipos de documento de identidad */
    public static final String CATEGORIA_TIPO_IDENTIFICACION = "TIPO_IDENTIFICACION";

    /** Categoria correspondiente al género */
    public static final String CATEGORIA_GENERO = "GENERO";

    /*
     * CONSTANTES ARCHIVOS DE PROPIEDADES ******************************************************
     */

    /** nombre del archivo de resources */
    public static final String _RESOURCE_NAME = "resources";

    /** nombre del archivo de homologacion para plenitud */
    public static final String _HOMOLOGACION_NAME = "homologacionPlenitud";

    /** nombre del archivo de homologaciones comunes */
    public static final String _HOMOLOGACION_COMUN_NAME = "homologacionComun";

    /** Prefijo que agrupa las llaves de los valores correspondientes a tipos de documento */
    public static final String PREFIJO_LLAVES_TIPO_DOCUMENTO = "tipo_documento_";

    /** Prefijo que agrupa las llaves de los valores correspondientes a genero */
    public static final String PREFIJO_LLAVES_GENERO = "tipo_genero_";

    /** Prefijo que agrupa las llaves de los valores correspondientes a canales de vinculación */
    public static final String PREFIJO_LLAVES_CANALES = "canal_vinculacion_";

    /*
     * CONSTANTES PROPIOS PARA CADA SERVICIO **********************************************************************
     */

    /** Descripción cuando no se encuentra nivel siben en la BD */
    public static final String DESC_SIN_NIVEL_SISBEN = "Sin definir Nivel SISBEN";

    /** mensaje cuando la diferencia entre fecha de nacimiento y la fecha actual es menor a 18 años */
    public static final String MSJ_FECHA_NACIMIENTO_INVALIDA = PARAMETRO0
            + " no válida. La diferencia entre fecha de nacimiento y la fecha actual debe ser mayor o igual a 18 años\n";

    /** Rango de diferencia en años para determinar si una fecha de nacimiento es válida, comparándola con la fecha actual */
    public static final int RANGO_DIFERENCIA_FECHA_NACIMIENTO = 18;

    /** mensaje cuando ninguna de las direcciones de una lista tiene indicador de direccion principal en Si */
    public static final String MSJ_LISTA_DIRECCIONES_SIN_ID_PRINCIPAL = "Al menos una de las direcciones debe tener el " + PARAMETRO0
            + " con valor: " + SI + "\n";

    /** mensaje cuando ninguno de los teléfonos de una lista tiene indicador de teléfono principal en Si */
    public static final String MSJ_LISTA_TELEFONOS_SIN_ID_PRINCIPAL = "Al menos uno de los telefonos debe tener el " + PARAMETRO0
            + " con valor: " + SI + "\n";

    /** mensaje cuando el campo idCorrelacion no tiene la estructura correcta */
    public static final String MSJ_FORMATO_IDCORRELACION_INVALIDA = PARAMETRO0
            + " solo permite caracteres numéricos incluido el carácter especial guión bajo (_)\n";

    /** mensaje cuando no hay parametrizacion de Nivel Sisben */
    public static final String MSJ_PARAMETRIZACION_NIVEL_SISBEN = "No hay datos parametrizados para validar el Nivel Sisben";

    /*
     * CONSTANTES SERVICIO SvcVinculado ----------------------------
     */

    /** nombre del servicio vinculado */
    public static final String NOMBRE_WS_VINCULADO = "SvcVinculado_Service";

    /** mensaje cuando ninguno de los email de una lista tiene indicador de email principal en Si */
    public static final String MSJ_LISTA_EMAIL_SIN_ID_PRINCIPAL = "Al menos uno de los correos electrónicos debe tener el " + PARAMETRO0
            + " con valor :" + SI + "\n";

    /** mensaje cuando el campo idCorrelacion no tiene la estructura correcta */
    public static final String MSJ_ESTRUCTURA_IDCORRELACION_INVALIDA = PARAMETRO0
            + " no es válido, debe tener mínimo 1 y máximo 50 caracteres numéricos incluido el carácter especial guión bajo (_)\n";

    /** mensaje cuando el campo usuarioSistemaExterno no tiene la estructura correcta */
    public static final String MSJ_ESTRUCTURA_USUARIO_INVALIDA = PARAMETRO0 + " debe tener mínimo 5 y máximo 60 caracteres\n";

    /** mensaje cuando la fecha de expedición es mayor a la fecha de modificación */
    public static final String MSJ_FECHA_INVALIDA_MODIFICACION = PARAMETRO0 + " no puede ser mayor a la fecha de modificación\n";

    /** mensaje cuando la fecha ingresada es mayor a la fecha de expedición */
    public static final String MSJ_FECHA_INVALIDA_EXPEDICION = PARAMETRO0 + " no puede ser mayor a la fecha de expedición\n";

    /** mensaje cuando la fecha de expedición es menor a la fecha de nacimiento */
    public static final String MSJ_FECHA_INVALIDA_MINIMA = PARAMETRO0 + " no puede ser menor al parámetro mínimo establecido\n";

    /** mensaje cuando el vinculado no existe en sistema beps */
    public static final String MSJ_VINCULADO_INEXISTENTE = "Solicitante no existe como vinculado";

    /** mensaje cuando ocurre un error al modificar datos sobre plenitud */
    public static final String MSJ_ERROR_ACTUALIZACION_SCI = "Error al modificar datos en el sistema de Cuentas Individuales. ";

    /** mensaje cuando no existe el solicitante sobre ninguna de las tablas de vinculado, previnculado ó prospecto */
    public static final String MSJ_ERROR_CONSULTA_VIABILIDAD = "Solicitante no existe como prospecto";

    /** mensaje cuando no hay respuesta de plenitud */
    public static final String RESPUESTA_FALLIDA_INVOCACION_PLENITUD = "Error de Conexión con Plenitud";

    /** mensaje para validación de código departamento - ciudad */
    public static final String MSJ_VALIDAR_DEPTO_CIUDAD = PARAMETRO0 + " debe tener " + PARAMETRO1
            + " caracteres. Solo permite caractes numéricos\n";

    /** Constante que contiene el nombre del parámetro que hace referencia a la fecha de inicio del programa Beps. */
    public static final String FECHA_INICIO_PROGRAMA_BEPS = "fecha_inicio_programa_beps";

    /** Constante que define el número de días de un año */
    public static final float CONSTANTE_DIAS = 365.0F;

    /** Constante para la definición de milisegundos */
    public static final float CONSTANTE_MILISEGUNDOS = 1000F;

    /** Constante que define minutos - segundos */
    public static final float CONSTANTE_MINUTOS_SEGUNDOS = 60F;

    /** Constante que define las horas asociadas a un día */
    public static final float CONSTANTE_HORA = 24F;

    /** Códigos retornados por Plenitud en la consulta de saldos y movimientos */
    public static final String COD_ERROR_CONSULTA_SALDOS_ = "COSA001,COSA002,COSA012,COSA003,COSA010,COSA013,COSA005,COSA009,COSA011,COSA014,COSA015";

    /** Código cuando se genera error en la actualización de información sensible sobre el Sistema de Cuentas Individuales - Plenitud */
    public static final String COD_ERROR_ACTUALIZACION_DATOS_PLENITUD = "01";
    
    /** Codigo para identficar el valor de bizagi */
    public static final String SISTEMA_ORIGEN_BIZAGI = "BIZAGI";

    /*
     * CONSTANTES SERVICIO SvcPrevinculado ----------------------------
     */

    /** mensaje de validación de un parámetro */
    public static final String VALIDACION_CODIGO_RECHAZO = PARAMETRO0 + " es Obligatorio si el " + PARAMETRO1 + " tiene el valor N\n";

    /** mensaje cuando el el solicitante del estudio de la prevalidación no existe */
    public static final String MSJ_SOLICITANTE_INNEXISTENTE = "Solicitante No Existente";

    /** mensaje cuando el el solicitante del estudio de la prevalidación ya existe */
    public static final String MSJ_SOLICITANTE_EXISTENTE = "Solicitante Existente";

    /*
     * CONSTANTES SERVICIO SvcBeneficios ----------------------------
     */
    
    /** Constante para nombrar operacion de actualizacion del vinculado persona*/
    public static final String PR_ACTUALIZAR_VINCULADO_PERSONA = "PR_ActualizarVinculadoPersona";
    
    /** Constante con el llamado del SP que actualiza los datos del viculado por primera vez y 
     * el estado de detalle dependiendo del estado =Activo y EstadoDetalle="VACIO"*/
   public static final String SP_OTORGAMIENTO_ACTUALIZA_DATOS_VINCULADO_INICIAL = "StoredProcedure_pr_otor_act_dat_vinc_inicio";

    /** Constantes para la estructura información de contexto. */
    public static final String CAMPO_CONTEXTO = "InformacionContexto";

    /** Constantes para la estructura información Solicitud Destinación Recursos. */
    public static final String CAMPO_INFO_SOLICITUD_DESTINACION = "InformacionSolicitudDestinacionRecursos";
    
    /** Constante para almacenar el nombre del campo TipoDocumento */
    public static final String CAMPO_TIPO_DOCUMENTO = "TipoDocumento";

    /** Constante para almacenar el nombre de la estructura identificación. */
    public static final String CAMPO_IDENTIFICACION = "Identificacion";

    /** Constante para almacenar el nombre del campo PrimerNombre. */
    public static final String CAMPO_PRIMER_NOMBRE = "PrimerNombre";

    /** Constante para almacenar el nombre del campo SegundoNombre. */
    public static final String CAMPO_SEGUNDO_NOMBRE = "SegundoNombre";

    /** Constante para almacenar el nombre del campo PrimerApellido. */
    public static final String CAMPO_PRIMER_APELLIDO = "PrimerApellido";

    /** Constante para almacenar el nombre del campo SegundoApellido. */
    public static final String CAMPO_SEGUNDO_APELLIDO = "SegundoApellido";

    /** Código de error para la información. */
    public static final String COD_02 = "02";
    /** Mensaje de error para la información de vinculado inexistente */
    public static final String MSJ_ERROR_INFO_VINCULADO_OBLIGATORIA = "La información del vinculado es obligatoria.\n";

    /** Código de error para la no existencia del vinculado. */
    public static final String COD_ERROR_VINCULADO_NO_EXISTE = "05";

    /** Mensaje de error para no existencia del vinculado. */
    public static final String MSJ_ERROR_VINCULADO_NO_EXISTE = "Vinculado no existe en el sistema BEPS.\n";

    /** Código de error para la no existencia del vinculado. */
    public static final String COD_ERROR_NUM_RADICACION_PADRE = "06";

    /** Mensaje de error para no existencia del vinculado. */
    public static final String MSJ_ERROR_NUM_RADICACION_PADRE_NO_EXISTE = "Número de Radicación Padre No existe en el sistema BEPS.\n";

    /** Código de error para la falta de información de destinación de recursos. */
    public static final String COD_ERROR_DESTINACION_RECURSOS = "07";

    /** Mensaje de error para la información de vinculado inexistente */
    public static final String MSJ_ERROR_DESTINACION_RECURSOS = "No se ha seleccionado ningún tipo de destinación, ni de traslado.\n";

    /** Mensaje de error para la obligatoriedad del código AFP. */
    public static final String MSJ_ERROR_CODIGO_AFP = "El Código de la AFP es obligatorio.\n";

    /** Mensaje de error para la obligatoriedad del campo número de tarjeta profesional. */
    public static final String MSJ_ERROR_NUMERO_TARJETA_P = "Apoderado no presenta tarjeta profesional.\n";

    /** Código de error para el porcentaje de distribución. */
    public static final String COD_ERROR_PORCENTAJE_DISTRIBUCION = "09";

    /** Mensaje de error para error en porcentaje de distribución. */
    public static final String MSJ_ERROR_PORCENTAJE_DISTRIBUCION_100 = "El valor del porcentaje para la distribución de recursos debe ser igual a 100.\n";

    /** Mensaje de error para la obligatoriedad de la fecha ejecutoria. */
    public static final String MSJ_ERROR_FECHA_EJECUTORIA = "Fecha constancia Ejecutoria obligatoria.\n";

    /** Mensaje de error para la obligatoriedad del número de radicación padre. */
    public static final String MSJ_ERROR_NUM_RADICACION_PADRE = "Es obligatorio que llegue el número de radicación padre.\n";

    /** Código de error para el porcentaje de distribución. */
    public static final String COD_ERROR_ACTUALIZACION_DATOS_NO_SENSIBLES = "10";

    /** Mensaje de error para la información de vinculado inexistente. */
    public static final String MSJ_ERROR_ACTUALIZACION_DATOS_NO_SENSIBLES = "Error Actualización Datos No Sensibles\n";

    /** Mensaje de error para la información de dominio incorrecto para una tabla parametrica. */
    public static final String MSJ_ERROR_VALOR_INCORRECTO_DOMINIO = "El valor del campo " + PARAMETRO0 + " no corresponde al dominio.\n";

    /** Mensaje de error para la información inconsistente del primer nombre y/o apellido. */
    public static final String MSJ_ERROR_PRIMER_NOMBRE_APELLIDO = PARAMETRO0
            + " no es válido, debe ingresar un valor alfabético sin caracteres especiales. No puede contener solo espacios en blanco\n";

    /** Mensaje de error para la información inconsistente del segundo nombre y/o apellido. */
    public static final String MSJ_ERROR_SEGUNDO_NOMBRE_APELLIDO = PARAMETRO0
            + " no es válido, debe ingresar un valor alfabético sin caracteres especiales\n";

    /** Mensaje de error para indicar la existencia del número de radicado. */
    public static final String MSJ_ERROR_NUMERO_RADICADO_DUPLICADO = "El número de radicado ya existe en el sistema BEPS.\n";
    
    

    /*
     * CONSTANTES SERVICIO SvcBeneficios ----------------------------
     */

    /* REGISTRAR SOLICITUD --------------------------- */

    /** Constante correspondiente a la AFP COLPENSIONES */
    public static final String AFP_COLPENSIONES = "COLPENSIONES";
    
    /* REGISTRAR SOLICITUD DEVOLUCION DE AHORROS A HEREDEROS --------------------------- */
    
    /** Nombre del campo autoriza envío comunicación herederos. */
    public static final String CAMPO_AUTORIZA_ENVIO_COMUNICACION_HEREDERO = "informacionHerederos.informacionAutorizacion.autorizacionEnvioComunicacion";
    
    /** Nombre del campo autoriza manejo información herederos. */
    public static final String CAMPO_AUTORIZA_MANEJO_INFORMACIONN_HEREDERO = "informacionHerederos.informacionAutorizacion.autorizacionManejoInformacion";
    
    /** Nombre del campo canal radicación. */
    public static final String CAMPO_CANAL_RADICACION = "canalRadicacion";
    
    /** Nombre del campo código tipo documental. */
    public static final String CAMPO_CODIGO_TIPO_DOCUMENTAL = "documento.codigoTipoDocumental";
    
    /** Nombre del campo destinacion recursos. */
    public static final String CAMPO_DESTINACION_RECURSOS = "destinacionRecursos";
    
    /** Nombre del campo departamento del heredero. */
    public static final String CAMPO_DEPARTAMENTO_HEREDERO = "informacionHerederos.departamento";
    
    /** Nombre del campo documento. */
    public static final String CAMPO_DOCUMENTO = "documento";
    
    /** Nombre del campo es representante herederos. */
    public static final String CAMPO_ES_REPRESENTANTE_HEREDEROS = "informacionHerederos.esRepresentanteHerederos";
    
    /** Nombre del campo entidad financiera. */
    public static final String CAMPO_ENTIDAD_FINANCIERA = "informacionCuentaBancaria.entidadFinanciera";
    
    /** Nombre del campo fecha de defunción del vinculado fallecido. */
    public static final String CAMPO_FECHA_DEFUNCION = "informacionVinculado.fechaDefuncion";
    
    /** Nombre del campo documento. */
    public static final String CAMPO_FECHA_REGISTRO = "fechaRegistro";
    
    /** Nombre del campo id documento. */
    public static final String CAMPO_ID_DOCUMENTO = "documento.id";
    
    /** Constantes para la estructura información Solicitud Destinación Recursos. */
    public static final String CAMPO_INFO_SOLICITUD_DEVOLUCION_AHORROS_HEREDEROS = "InformacionSolicitudDevolucionAhorrosHerederos";
    
    /** Nombre del campo municipio del heredero. */
    public static final String CAMPO_MUNICIPIO_HEREDERO = "informacionHerederos.municipio";
    
    /** Nombre del campo número cuenta. */
    public static final String CAMPO_NUMERO_CUENTA = "informacionCuentaBancaria.numeroCuenta";
    
    /** Nombre del campo número de documento del vinculado fallecido. */
    public static final String CAMPO_NUMERO_DOCUMENTO_HEREDERO = "informacionHerederos.identificacion.numeroDocumento";
    
    /** Nombre del campo número de documento del vinculado fallecido. */
    public static final String CAMPO_NUMERO_DOCUMENTO_VINCULADO_FALLECIDO = "informacionVinculado.numeroDocumento";
    
    /** Nombre del campo número radicación. */
    public static final String CAMPO_NUMERO_RADICACION = "numeroRadicacion";
    
    /** Nombre del campo parentesco del heredero. */
    public static final String CAMPO_PARENTESCO_HEREDERO = "informacionHerederos.parentesco";
    
    /** Nombre del campo primer apellido del heredero. */
    public static final String CAMPO_PRIMER_APELLIDO_HEREDERO = "informacionHerederos.nombres.primerApellido";
    
    /** Nombre del campo primer nombre del heredero. */
    public static final String CAMPO_PRIMER_NOMBRE_HEREDERO = "informacionHerederos.nombres.primerNombre";
    
    /** Nombre del campo registro civil de defunción del vinculado fallecido. */
    public static final String CAMPO_REGISTRO_CIVIL_DEFUNCION = "informacionVinculado.registroCivilDefuncion";
    
    /** Nombre del campo tipo cuenta. */
    public static final String CAMPO_TIPO_CUENTA = "informacionCuentaBancaria.tipoCuenta";
    
    /** Nombre del campo tipo de documento del heredero. */
    public static final String CAMPO_TIPO_DOCUMENTO_HEREDERO = "informacionHerederos.identificacion.tipoDocumento";
    
    /** Nombre del campo tipo de documento de titular de la cuenta bancaria. */
    public static final String CAMPO_TIPO_DOCUMENTO_TITULAR_CUENTA = "informacionCuentaBancaria.identificacionTitular.tipoDocumento";
    
    /** Nombre del campo tipo de documento del vinculado fallecido. */
    public static final String CAMPO_TIPO_DOCUMENTO_VINCULADO_FALLECIDO = "informacionVinculado.identificacion.tipoDocumento";
    
    /** Nombre del campo tipo solicitante del heredero. */
    public static final String CAMPO_TIPO_SOLICITANTE_HEREDERO = "informacionHerederos.tipoSolicitante";
    
    /** Nombre del campo tipo solicitud. */
    public static final String CAMPO_TIPO_SOLICITUD = "tipoSolicitud";
    
    /** Nombre del campo titular de la cuenta es tercero. */
    public static final String CAMPO_TITULA_CUENTA_ES_TERCERO = "informacionCuentaBancaria.titularCuentaEsTercero";
    
    /** Nombre del campo usuario registro sistema. */
    public static final String CAMPO_USUARIO_REGISTRO = "usuarioRegistroSistema";
    
    /** Constante que representa el número cero. */
    public static final int CERO = 0;
    
    /** Constante que representa el número uno. */
    public static final int UNO = 1;
    
    /** Código de error que se retorna cuando el apoderado del heredero no tiene tarjeta profesional. */
    public static final String COD_APODERADO_SIN_TARJETA_PROFESIONAL = "02";
    
    /** Código de error que se retorna cuando se recibe algún campo con el formato invalido. */
    public static final String COD_CAMPOS_FORMATO_INVALIDO = "01";
    
    /** Código de error que se retorna cuando hay algún campo obligatorio que no fue recibido. */
    public static final String COD_CAMPOS_OBLIGATORIOS_NO_RECIBIDOS = "02";
    
    /** Código del detalle de estado de vinculado retirado. */
    public static final String COD_DETALLE_ESTADO_VINCULADO_RETIRADO = "R01";
    
    /** Código del detalle de estado de vinculado suspendido. */
    public static final String COD_DETALLE_ESTADO_VINCULADO_SUSPENDIDO = "S01";
    
    /** Código del estado de vinculado retirado. */
    public static final String COD_ESTADO_VINCULADO_RETIRADO = "R";
    
    /** Código del estado de vinculado suspendido. */
    public static final String COD_ESTADO_VINCULADO_SUSPENDIDO = "S";
    
    /** Código de error que se retorna cuando en la lista de herederos existe más de un tercero autorizado. */
    public static final String COD_EXISTE_MAS_DE_UN_TERCERO_AUTORIZADO = "06";
    
    /** Código de error que se retorna cuando ya existe una Solicitud de Devolución de Ahorros a Herederos. */
    public static final String COD_EXISTE_SOLICITUD_DEVOLUCION_HEREDEROS = "08";
    
    /** Código de error que se retorna cuando se la lista de herederos excede el tamaño máximo permitido. */
    public static final String COD_LISTA_HEREDEROS_EXCEDE_TAMANO_MAXIMO = "04";
    
    /** Código de error que se retorna cuando en la lista de herederos, no hay registros con tipo tolicitante Heredero. */
    public static final String COD_NO_SE_REGISTRARON_HEREDEROS = "05";
    
    /** Código del tipo solicitante apoderado. */
    public static final String COD_TIPO_SOLICITANTE_APODERADO = "05";
    
    /** Código del tipo solicitante curador. */
    public static final String COD_TIPO_SOLICITANTE_CURADOR = "04";
    
    /** Código del tipo solicitante heredero. */
    public static final String COD_TIPO_SOLICITANTE_HEREDERO = "06";
    
    /** Código del tipo solicitante tercero autorizado. */
    public static final String COD_TIPO_SOLICITANTE_TERCERO_AUTORIZADO = "03";
    
    /** Código de error que se retorna cuando el vinculado no existe como fallecido en el sistema BEPS. */
    public static final String COD_VINCULADO_NO_FALLECIDO = "07";

    /** Expresión regular para validar campos alfabéticos en mayúscula. */
    public static final String ER_ALFABETICO_MAYUSCULA = "^[A-ZÁÉÍÓÚÄËÏÖÜ]+$";
    
    /** Expresión regular para validar campos alfabéticos sin caracteres especiales. */
    public static final String ER_ALFABETICO_SIN_CARACTERES_ESPECIALES = "^[a-zA-ZñÑáéíóúÁÉÍÓÚäÄëËïÏöÖüÜ]+[a-zA-ZñÑáéíóúÁÉÍÓÚäÄëËïÏöÖüÜ\\s]*$";
    
    /** Expresión regular para validar campos numéricos. */
    public static final String ER_NUMERICO = "^[0-9]+$";
    
    /** Expresión regular para validar campos numéricos sin ceros a la izquierda. */
    public static final String ER_NUMERICO_SIN_CEROS_IZQUIERDA = "^[^0*][0-9]*$";
    
    /** Expresión regular para validar que los documentos cumplan con la estructura GUID. */
    public static final String ER_IDENTIFICADOR_DOCUMENTOS_GUID = "^\\{?[a-fA-F0-9]{8}-([a-fA-F0-9]{4}-){3}[a-fA-F0-9]{12}\\}?$";
    
    /** Estado en el cual queda una solicitud de Devolución de Ahorros a Herederos cuando se registra */
    public static final String ESTADO_CREADO = "CREADO";
    
    /** Etapa en la cual queda una solicitud de Devolución de Ahorros a Herederos cuando se registra */
    public static final String ETAPA_REVISION = "Revisión";
    
    /** Mensaje que indica que el apoderado del heredero no tiene tarjeta profesional. */
    public static final String MSJ_APODERADO_SIN_TARJETA_PROFESIONAL = "Apoderado no presenta tarjeta profesional.";

    /** Mensaje que indica que el dominio del campo Autoriza Envío Comunicación es inválido. */
    public static final String MSJ_AUTORIZACION_ENVIO_INFO_DOMINIO_INVALIDO = "informacionHerederos.informacionAutorizacion.autorizacionEnvioComunicacion no es válido, debe ingresar alguna de las siguientes opciones: S, N.";
    
    /** Mensaje que indica que la longitud de campo Autoriza Envío Comunicación es inválida. */
    public static final String MSJ_AUTORIZACION_ENVIO_INFO_LONGITUD_INVALIDA = "informacionHerederos.informacionAutorizacion.autorizacionEnvioComunicacion debe tener una longitud de 1 caracter.";
    
    /** Mensaje que indica que la longitud del campo barrio es inválida. */
    public static final String MSJ_BARRIO_LONGITUD_INVALIDA = "informacionHerederos.barrio debe tener una longitud de máximo 30 caracteres.";
        
    /** Mensaje que indica que en la lista de herederos, sólo debe existir un registro con el valor "S". */
    public static final String MSJ_CANTIDAD_REPRESENTANTE_HEREDEROS = "En la lista informacionHerederos siempre debe existir un registro con valor S en el campo esRepresentanteHerederos.";
    
    /** Mensaje que indica que la longitud del campo correo electrónico es inválida. */
    public static final String MSJ_CORREO_ELECTRONICO_LONGITUD_INVALIDA = "informacionHerederos.correoElectronico debe tener una longitud de 5 a 255 caracteres.";
    
    /** Mensaje que indica que el formato de campo parentesco es inválido. */
    public static final String MSJ_CORREO_ELECTRONICO_FORMATO_INVALIDO = "informacionHerederos.correoElectronico no válido.";
    
    /** Mensaje que indica que el formato de campo departamento es inválido. */
    public static final String MSJ_DEPARTAMENTO_FORMATO_INVALIDO = "informacionHerederos.departamento no es válido, debe ingresar un valor numérico.";
    
    /** Mensaje que indica que la longitud del campo departamento es inválida. */
    public static final String MSJ_DEPARTAMENTO_LONGITUD_INVALIDA = "informacionHerederos.departamento debe tener una longitud de 2 caracteres.";
    
    /** Mensaje que indica que el departamento de residencia del heredero es obligatorio. */
    public static final String MSJ_DEPARTAMENTO_RESIDENCIA_HEREDERO_OBLIGATORIO = "departamento es obligatorio si tipoSolicitante es 06: Heredero.";
    
    /** Mensaje que indica que la longitud del campo dirección es inválida. */
    public static final String MSJ_DIRECCION_LONGITUD_INVALIDA = "informacionHerederos.direccion debe tener una longitud de 1 a 60 caracteres.";
    
    /** Mensaje que indica que la dirección de residencia del heredero es obligatoria. */
    public static final String MSJ_DIRECCION_RESIDENCIA_HEREDERO_OBLIGATORIO = "direccion es obligatorio si tipoSolicitante es 06: Heredero.";
    
    /** Mensaje que indica que identificador del documento no cumple con la estructura GUID. */
    public static final String MSJ_DOCUMENTO_ID_NO_GUID = "documento.id no cumple con la estructura GUID.";
        
    /** Mensaje que indica que en la lista de herederos existe más de un tercero autorizado. */
    public static final String MSJ_EXISTE_MAS_DE_UN_TERCERO_AUTORIZADO = "Existe más de un Tercero Autorizado en la Solicitud de Devolución de Ahorros a Herederos.";
    
    /** Mensaje que indica que ya existe una Solicitud de Devolución de Ahorros a Herederos. */
    public static final String MSJ_EXISTE_SOLICITUD_DEVOLUCION_HEREDEROS = "Existe Solicitud de Devolución de Ahorros a Herederos en gestión del Gerente o aprobada.";
    
    /** Mensaje que indica que en la lista de herederos, sólo debe existir un registro con el valor "S". */
    public static final String MSJ_LISTA_HEREDEROS_EXCEDE_TAMANO_MAXIMO = "La lista datos heredero o tercero supera el número máximo permitido a procesar (99).";
    
    /** Mensaje que indica que el formato de campo municipio es inválido. */
    public static final String MSJ_MUNICIPIO_FORMATO_INVALIDO = "informacionHerederos.municipio no es válido, debe ingresar un valor numérico.";
    
    /** Mensaje que indica que la longitud del campo municipio es inválida. */
    public static final String MSJ_MUNICIPIO_LONGITUD_INVALIDA = "informacionHerederos.municipio debe tener una longitud de 5 caracteres.";
    
    /** Mensaje que indica que el municipio de residencia del heredero es obligatorio. */
    public static final String MSJ_MUNICIPIO_RESIDENCIA_HEREDERO_OBLIGATORIO = "municipio es obligatorio si tipoSolicitante es 06: Heredero.";
    
    /** Mensaje que indica que en la lista de herederos no hay registros con tipo solicitante Heredero. */
    public static final String MSJ_NO_SE_REGISTRARON_HEREDEROS = "No se registraron herederos para la solicitud de Devolución de Ahorros a Herederos.";
    
    /** Mensaje que indica que el nombre del titular de la cuenta bancaria es obligatorio. */
    public static final String MSJ_NOMBRE_TITULAR_CUENTA_OBLIGATORIO = "nombreTitular es obligatorio si titularCuentaEsTercero es N.";
    
    /** Mensaje que indica que el formato del nombre del titular de la cuenta es inválido. */
    public static final String MSJ_NOMBRE_TITULAR_CUENTA_FORMATO_INVALIDO = "informacionCuentaBancaria.nombreTitular no es válido, debe ingresar un valor alfabético sin caracteres especiales. No puede contener solo espacios en blanco.";
    
    /** Mensaje que indica que la longitud del campo parentesco es inválida. */
    public static final String MSJ_NOMBRE_TITULAR_CUENTA_LONGITUD_INVALIDA = "informacionCuentaBancaria.nombreTitular debe tener una longitud de 1 a 50 caracteres.";
    
    /** Mensaje que indica que el número de radicación ingresado ya existe en la BD. */
    public static final String MSJ_NUMERO_RADICACION_NO_UNICO = "numeroRadicacion no es único.";
    
    /** Mensaje que indica que la longitud del campo tipo de número del titular de la cuenta bancaria es inválida. */
    public static final String MSJ_NUMERO_DOCUMENTO_TITULAR_CUENTA_LONGITUD_INVALIDA = "informacionCuentaBancaria.identificacionTitular.numeroDocumento debe tener una longitud de mínimo 2 y máximo 11 caracteres.";
    
    /** Mensaje que indica que el formato de campo número de documento del titular de la cuenta bancaria es inválido. */
    public static final String MSJ_TIPO_NUMERO_TITULAR_CUENTA_FORMATO_INVALIDO = "informacionCuentaBancaria.identificacionTitular.numeroDocumento no es válido, debe ingresar un valor numérico sin ceros a la izquierda.";

    /** Mensaje que indica que el numero de documento del titular de la cuenta bancaria es obligatorio. */
    public static final String MSJ_NUMERO_DOCUMENTO_TITULAR_CUENTA_OBLIGATORIO = "numeroDocumento es obligatorio si titularCuentaEsTercero es N.";
    
    /** Mensaje que indica que el tipo de documento del titular no coincide con el tipo de documento del representante de los herederos. */
    public static final String MSJ_NUMERO_DOCUMENTO_TITULAR_CUENTA_NO_COINCIDE = "El número de documento del titular de la cuenta bancaria no coincide con el número de documento del representante de los herederos";

    /** Mensaje que indica que el formato de campo parentesco es inválido. */
    public static final String MSJ_PARENTESCO_FORMATO_INVALIDO = "informacionHerederos.parentesco no es válido, debe ingresar un valor numérico.";
    
    /** Mensaje que indica que el municipio de residencia del heredero es obligatorio. */
    public static final String MSJ_PARENTESCO_HEREDERO_OBLIGATORIO = "parentesco es obligatorio si tipoSolicitante es 06: Heredero.";
    
    /** Mensaje que indica que la longitud del campo parentesco es inválida. */
    public static final String MSJ_PARENTESCO_LONGITUD_INVALIDA = "informacionHerederos.parentesco debe tener una longitud de 2 caracteres.";
    
    /** Mensaje que indica que eL telefono1 del heredero es obligatorio. */
    public static final String MSJ_TELEFONO1_HEREDERO_OBLIGATORIO = "telefono1 es obligatorio si tipoSolicitante es 06: Heredero.";
    
    /** Mensaje que indica que la longitud del campo telefono1 es inválida. */
    public static final String MSJ_TELEFONO1_LONGITUD_INVALIDA = "informacionHerederos.telefono1 debe tener una longitud de 5 a 20 caracteres.";
    
    /** Mensaje que indica que la longitud del campo telefono2 es inválida. */
    public static final String MSJ_TELEFONO2_LONGITUD_INVALIDA = "informacionHerederos.telefono2 debe tener una longitud de 5 a 20 caracteres.";
    
    /** Mensaje que indica que el formato de campo tipo de documento del titular de la cuenta bancaria es inválido. */
    public static final String MSJ_TIPO_DOCUMENTO_TITULAR_CUENTA_FORMATO_INVALIDO = "informacionCuentaBancaria.identificacionTitular.tipoDocumento no es válido, debe ingresar los caracteres alfabéticos en mayúscula.";
    
    /** Mensaje que indica que la longitud del campo tipo de documento del titular de la cuenta bancaria es inválida. */
    public static final String MSJ_TIPO_DOCUMENTO_TITULAR_CUENTA_LONGITUD_INVALIDA = "informacionCuentaBancaria.identificacionTitular.tipoDocumento debe tener una longitud de 2 caracteres.";
    
    /** Mensaje que indica que el tipo de documento del titular de la cuenta bancaria es obligatorio. */
    public static final String MSJ_TIPO_DOCUMENTO_TITULAR_CUENTA_OBLIGATORIO = "tipoDocumento es obligatorio si titularCuentaEsTercero es N.";
    
    /** Mensaje que indica que el tipo de documento del titular no coincide con el tipo de documento del representante de los herederos. */
    public static final String MSJ_TIPO_DOCUMENTO_TITULAR_CUENTA_NO_COINCIDE = "El tipo de documento del titular de la cuenta bancaria no coincide con el tipo de documento del representante de los herederos.";
            
    /** Mensaje que indica que el vinculado no existe como fallecido en el sistema BEPS. */
    public static final String MSJ_VINCULADO_NO_FALLECIDO = "Vinculado no existe en el sistema BEPS como fallecido.";
        
    /** Porcentaje de destinación de recursos para las solicitudes de Devolución de Ahorros a Herederos. */
    public static final int PORCENTAJE_DESTINACION_RECURSOS_100 = 100;
    
    /** Tamaño máximo permitido para la lista de herederos de una solicitud de Devolución de Ahorros a Herederos. */
    public static final int TAMANO_MAXIMO_LISTA_HEREDEROS = 99; 

    /* VALIDACIONES AUTOMATICAS --------------------------- */

    /** Valor asignado para la validacion de edad por genero cuando se cumple correctamente */
    public static final String VALIDACION_EDAD_CORRECTA = "0";

    /** Valor asignado para la validacion de edad por genero cuando NO se cumple */
    public static final String VALIDACION_EDAD_INCORRECTA = "1";

    /** Valor asignado para la validacion de edad por genero cuando los datos no son validos */
    public static final String VALIDACION_EDAD_INCOMPLETA = "2";

    /** Codigo asociado al tipo de destinacion Anualidad Vitalicia */
    public static final String CODIGO_DESTINACION_ANUALIDAD_VITALICIA = "02";

    /** Fuente definida como parametro de entrada para el consumo del servicio web SvcNominaBeneficios */
    public static final String FUENTE_CONSUMO_WS_NOMINA_BENEFICIOS = "BEPS";

    /** Usuario definido como parametro de entrada para el consumo del servicio web SvcNominaBeneficios */
    public static final String USUARIO_CONSUMO_WS_NOMINA_BENEFICIOS = "ValidacionesAutomaticasMDB";

    /** Código de error retornado por el servicio web SvcNominaBeneficios cuando el Pensionado o Beneficiario No Existe */
    public static final String COD_ERROR_WS_NB_DATOS_INCOMPLETOS = "04";

    /** Código de error retornado por el servicio web SvcNominaBeneficios cuando el Ciudadano tiene registro inconsistente en Nómina */
    public static final String COD_ERROR_WS_NB_REGISTRO_INCONSISTENTE = "05";

    /** Valor asignado para la validacion de pensión cuando la respuesta del servicio SvcNominaBeneficios es 04 */
    public static final String VALIDACION_PENSION_RPM_SIN_PENSION = "Sin Pensión";

    /** Valor asignado para la validacion de pensión cuando la respuesta del servicio SvcNominaBeneficios es 05 */
    public static final String VALIDACION_PENSION_RPM_INCONSISTENTE = "Inconsistente";

    /** Etapa con la cual se actualiza la solicitud una vez realizado el proceso de validaciones automáticas */
    public static final String ETAPA_SOLICITUD_VALIDACIONES = "Revisión";

    /** Llave del parámetro que contiene la edad de pensión para las mujeres */
    public static final String PARAMETRO_EDAD_PENSION_MUJERES = "edad_pension_mujeres";

    /** Llave del parámetro que contiene la edad de pensión para los hombres */
    public static final String PARAMETRO_EDAD_PENSION_HOMBRES = "edad_pension_hombres";

    /** Mensaje de error cuando los parámetros requeridos para el proceso no se encuentran configurados */
    public static final String MSJ_ERROR_PARAMETROS_INEXISTENTES = "Los par\u00e1metros requeridos para el proceso no se encuentran configurados.";
    
    /** Valor asignado para la actualizacion de una novedad */
    public static final String TIPO_NOVEDAD_ACTUALIZACION_BUC = "ACT";
    
    /** Valor asignado para la creacion de una novedad */
    public static final String TIPO_NOVEDAD_CREACION_BUC = "CRE";
    
    /** Valor asignado para la creacion de una novedad */
    public static final String ESTADO_INICIAL_NOVEDAD_BUC = "01";
    
    /** Valor asignado para el estado fallido o indisponibilidad */
    public static final String ESTADO_FALLIDO_INDISPONIBILIDAD_BUC = "06";

    /** Valor para consultar en la base de datos al URL del servicio de Indicio Prestaciones */
    public static final String NOMBRE_URL_SERVICIO_INDICIO_PRESTACION = "url_servicio_indicio_prestacion";
    
    /** Valor para consultar en la base de datos al URL del servicio de Afiliado */
    public static final String NOMBRE_URL_SERVICIO_AFILIADO = "url_servicio_afiliado";
    
    /** Valor para indicar si la invocación al servicio Indicio Prestaciones fue exitosa */
    public static final String COD_INVOCACION_EXITOSA_INDICIO_PRESTACION = "00";
    
    /** Valor para indicar si la invocación al servicio AFiliado fue exitosa */
    public static final String COD_INVOCACION_EXITOSA_AFILIADO = "00";
    
    /** Valor para indicar si la invocación al servicio Indicio Prestaciones fue No existe información en el sistema */
    public static final String COD_INVOCACION_NO_EXISTE_INFORMACION_INDICIO_PRESTACION = "06";
    
    /** Valor para el tipo prestacion Devolución de saldos*/
    public static final String COD_TIPO_PRESTACION_DEVOLUCION_SALDOS = "16";
    /** Valor para el tipo prestacion Indemnización sustitutiva*/
    public static final String COD_TIPO_PRESTACION_INDEMNIZACION_SUSTITUTIVA = "17";
    /** Valor para el tipo prestacion Indemnización vejez*/
    public static final String COD_TIPO_PRESTACION_INDEMNIZACION_VEJEZ = "33";
    /** Valor para el tipo prestacion Indemnización invalidez*/
    public static final String COD_TIPO_PRESTACION_INDEMNIZACION_INVALIDEZ = "34";
    
    /*
     * CONSTANTES SERVICIO SvcCertificados ----------------------------
     */

    /** nombre del servicio vinculado */
    public static final String NOMBRE_WS_CERTIFICADOS = "SvcCertificado_Service";

    /** mensaje cuando el vinculado no existe en sistema beps */
    public static final String MSJ_DATOS_FIRMA_NO_EXISTEN = "No se encuentra parametrizado el cargo o nombre o firma del responsable.";
    /** mensaje cuando el prospecto o previnculado no existe en sistema beps */
    public static final String MSJ_DOCUMENTO_ECONTRADO_VINCULADO="Documento se encuentra como vinculado";
    
    /** Código para Campos Obligatorios No Recibidos */
    public static final String COD_CAMPOS_OBLIGATORIO_NO_RECIBIDO = "02";

    /** Codigo para identficar el valor de bizagi */
    public static final String NOMBRE_BIZAGI = "BIZAGI";
    
    /** mensaje genérico para datos numéricos no válidos */
    public static final String MSJ_ERROR_DATO_ESPACIO = PARAMETRO0 + " no es válido, no puede ser solo espacios.\n";

    /** codigo de error para cuando no se encutra el documento*/
	public static final String COD_ERROR_DOCUMENTO_ENCONTRADO_VINCULADO = "05";

	/** Codigo de error para cuando vinculado está fallecido o no tiene solicitud de destinación*/
	public static final String COD_ERROR_NO_CUMPLE_CONDICIONES ="05";
	
	/** Mensaje de error para cuando vinculado está fallecido o no tiene solicitud de destinación*/
	public static final String MSJ_ERROR_NO_CUMPLE_CONDICIONES="El vinculado no presenta solicitud de otorgamiento o su estado es fallecido.";
	
	/** Codigo de error para cuando no se encuentra parametrizada una observación para el tipo de destinación*/
	public static final String COD_ERROR_NO_PARAMETRIZADO_OBSERVACION="06";
	
	/** Mensaje de error para cuando no se encuentra parametrizada una observación para el tipo de destinación*/
	public static final String MSJ_ERROR_NO_PARAMETRIZADO_OBSERVACION="No se encuentra parametrizado la Observación por Tipo de Destinación de Recursos.";

	 /*
     * CONSTANTES SERVICIO vinculacion GVI-VIN-3-FAB-12-ConsultarTransicionesDeEstadoPermitidas ----------------------------
     */
	/** Código que se retorna cuando ocurre que la Cantidad de registros supera el número máximo permitido */
    public static final int NUMERO_MAXIMO_PERMITIDO_REGISTROS = 50;
	/** Código que se retorna cuando ocurre que la Cantidad de registros supera el número máximo permitido */
    public static final String COD_ERROR_CANTIDAD_REGISTROS_SUPERA_MAXIMO_PERMITIDO = "04";
    /** Mensaje que se retorna cuando ocurre que la Cantidad de registros supera el número máximo permitido */
    public static final String MSJ_ERROR_CANTIDAD_REGISTROS_SUPERA_MAXIMO_PERMITIDO = "La lista de vinculados supera el número máximo permitido a procesar ("+NUMERO_MAXIMO_PERMITIDO_REGISTROS+")";
    /** Código que se retorna cuando ocurre que No hay transiciones permitidas para el estado y detalle de estado actual */
    public static final String COD_ERROR_NO_TRANSICIONES_PERMITIDAS_ESTADO_DETALLE_ACTUAL = "05";
    /** Mensaje que se retorna cuando ocurre que No hay transiciones permitidas para el estado y detalle de estado actual */
    public static final String MSJ_ERROR_NO_TRANSICIONES_PERMITIDAS_ESTADO_DETALLE_ACTUAL = "No existen transiciones permitidas para el estado y detalle de estado enviados.";
    /** Constante para R: Reactivación. Se asigna este valor si en la parametrización de transiciones de estado permitidas está indicado que la transición implica Reactivación de la cuenta individual.*/
    public static final String TIPO_OPERACION_REACTIVACION = "R";
    /** Constante para C: Cancelación.  Se asigna este valor si en la parametrización de transiciones de estado permitidas está indicado que la transición implica Cancelación de la cuenta individual*/ 
    public static final String TIPO_OPERACION_CANCELACION = "C";
    /** Mensaje Si todas las consultas se realizaron exitosamente */
    public static final String MSJ_FIN_EXITO = "Exitoso";
    /** Mensaje Si no fueron realizadas exitosamente todas las consultas */
    public static final String MSJ_FIN_EXITO_ERRORES = "Finalizado con Errores";
    /** Codigo Si no fueron realizadas exitosamente todas las consultas */
    public static final String COD_FIN_EXITO_ERRORES = "05";
    /** Mensaje Si no se realizó exitosamente ninguna consultar */
    public static final String MSJ_FIN_FALLIDO = "Fallido";
    /** Mensaje Si no se realizó exitosamente ninguna consultar */
    public static final String COD_FIN_FALLIDO = "06";
    /** mensaje para enviar al log cuando */
    public static final String MSJ_LOG = "Mensaje";
    /** mensaje para enviar al log cuando No existe el vinculado*/
    public static final String MSJ_ERROR_NO_EXISTE_VINCULADO = "No existe el vinculado.";
    /** mensaje para enviar al log cuando No existen transiciones permitidas para el estado y detalle de estado enviados.*/
    public static final String MSJ_ERROR_NO_EXISTEN_TRANSICIONES_PERMITIDAS_ESTADO_DETALLE = "No existen transiciones permitidas para el estado y detalle de estado enviados.";
    /** Constante para almacenar el nombre del campo estado */
    public static final String CAMPO_ESTADO = "Estado";
    /** Constante para almacenar el nombre del campo detalle estado. */
    public static final String CAMPO_DETALLE_ESTADO = "DetalleEstado";
    
    /*
     * CONSTANTES SERVICIO vinculacion GVI-VIN-3-FAB-13-RealizarCambioEstadoVinculado ----------------------------
     */
    /** Códigos retornados por Plenitud en la cambiar estado cuenta */
    public static final String COD_ERROR_CAMBIAR_ESTADO_CUENTA = "0000001,0000002,0000003,0000004,0000005";
    /** mensaje para enviar al log cuando hay Cambio de estado exitoso*/
    public static final String MSJ_CAMBIO_ESTADO_EXITOSO = "Cambio de estado exitoso.";
    /** mensaje para enviar al log cuando hay Error interno durante la ejecución del cambio de estado"*/
    public static final String MSJ_ERROR_INTERNO_DURANTE_EJECUCION_CAMBIO_ESTADO = "Error interno durante la ejecución del cambio de estado.";
    /** mensaje para enviar al log cuando hay Error Esta solicitud se encuentra duplicada para el vinculado."*/
    public static final String MSJ_ERROR_SOLICITUD_DUPLICADA_VINCULADO = "Esta solicitud se encuentra duplicada para el vinculado.";
    /** mensaje para enviar al log cuando hay Error El vinculado contiene más de una solicitud de cambio de estado."*/
    public static final String MSJ_ERROR_VINCULADO_MAS_UNA_SOLICITUD = "El vinculado contiene más de una solicitud de cambio de estado.";

	/*
     * TABLAS Y CAMPOS DE BASES DE DATOS ----------------------------
     */
    
    /** Tabla BEPS_TRANSACCIONAL.vinculacion.vinc_vinculado*/
    public static final String VINCULACION_VINC_VINCULADO = "BEPS_TRANSACCIONAL.vinculacion.vinc_vinculado";
    
    /**Campo vvi_pk_id de la Tabla BEPS_TRANSACCIONAL.vinculacion.vinc_vinculado*/
    public static final String VINC_VINCULADO_VVI_PK_ID = "@vvi_pk_id";
    /**Campo vvi_numero_radicado de la Tabla BEPS_TRANSACCIONAL.vinculacion.vinc_vinculado*/
    public static final String VINC_VINCULADO_VVI_NUMERO_RADICADO = "@vvi_numero_radicado";
    /**Campo vvi_autorizacion_manejo_info de la Tabla BEPS_TRANSACCIONAL.vinculacion.vinc_vinculado*/
    public static final String VINC_VINCULADO_VVI_AUTORIZACION_MANEJO_INFO = "@vvi_autorizacion_manejo_info";
    /**Campo vvi_autorizacion_envio_info de la Tabla BEPS_TRANSACCIONAL.vinculacion.vinc_vinculado*/
    public static final String VINC_VINCULADO_VVI_AUTORIZACION_ENVIO_INFO = "@vvi_autorizacion_envio_info";
    /**Campo vvi_autoriza_colombia_mayor de la Tabla BEPS_TRANSACCIONAL.vinculacion.vinc_vinculado*/
    public static final String VINC_VINCULADO_VVI_AUTORIZA_COLOMBIA_MAYOR = "@vvi_autoriza_colombia_mayor";
    /**Campo vvi_afp de la Tabla BEPS_TRANSACCIONAL.vinculacion.vinc_vinculado*/
    public static final String VINC_VINCULADO_VVI_AFP = "@vvi_afp";
    /**Campo vvi_pk_id de la Tabla BEPS_TRANSACCIONAL.vinculacion.vinc_vinculado*/
    public static final String VINC_VINCULADO_VVI_USUARIO_ULTIMO_CAMBIO = "@vvi_usuario_ultimo_cambio";
    /**Campo vvi_canal_sistema de la Tabla BEPS_TRANSACCIONAL.vinculacion.vinc_vinculado*/
    public static final String VINC_VINCULADO_VVI_CANAL_SISTEMA = "@vvi_canal_sistema";
    /**Campo estadoVinculado de la Tabla BEPS_TRANSACCIONAL.vinculacion.vinc_vinculado*/
    public static final String VINC_VINCULADO_VVI_ESTADO_VINCULADO = "@estadoVinculado";
    /**Campo detalleEstadoVinculado de la Tabla BEPS_TRANSACCIONAL.vinculacion.vinc_vinculado*/
    public static final String VINC_VINCULADO_VVI_DETALLE_ESTADO_VINCULADO = "@detalleEstadoVinculado";
    
    
    /** Tabla BEPS_TRANSACCIONAL.vinculacion.vinc_persona*/
    public static final String VINCULACION_VINC_PERSONA = "BEPS_TRANSACCIONAL.vinculacion.vinc_persona";
    
    /**Campo vpe_departamento_nacimiento de la Tabla BEPS_TRANSACCIONAL.vinculacion.vinc_persona*/
    public static final String VINC_PERSONA_VPE_DEPARTAMENTO_NACIMIENTO = "@vpe_departamento_nacimiento";
    /**Campo vpe_municipio_nacimiento de la Tabla BEPS_TRANSACCIONAL.vinculacion.vinc_persona*/
    public static final String VINC_PERSONA_VPE_MUNICIPIO_NACIMIENTO = "@vpe_municipio_nacimiento";
    /**Campo vpe_usuario_ultimo_cambio de la Tabla BEPS_TRANSACCIONAL.vinculacion.vinc_persona*/
    public static final String VINC_PERSONA_VPE_USUARIO_ULTIMO_CAMBIO = "@vpe_usuario_ultimo_cambio";
    /**Campo vpe_numero_radicado de la Tabla BEPS_TRANSACCIONAL.vinculacion.vinc_persona*/
    public static final String VINC_PERSONA_VPE_NUMERO_RADICADO = "@vpe_numero_radicado";
    /**Campo vpe_canal_sistema de la Tabla BEPS_TRANSACCIONAL.vinculacion.vinc_persona*/
    public static final String VINC_PERSONA_VPE_CANAL_SISTEMA = "@vpe_canal_sistema";
    /**Campo vpe_tipo_documento de la Tabla BEPS_TRANSACCIONAL.vinculacion.vinc_persona*/
    public static final String VINC_PERSONA_VPE_TIPO_DOCUMENTO = "@vpe_tipo_documento";
    /**Campo vpe_numero_documento de la Tabla BEPS_TRANSACCIONAL.vinculacion.vinc_persona*/
    public static final String VINC_PERSONA_VPE_NUMERO_DOCUMENTO = "@vpe_numero_documento";
    
    /*
     * VARIABLES DE RETORNO PARA PROCEDIMIENTOS ALMACENADOS ----------------------------
     */
    public static final String INDICADOR_RETORNO = "@indicadorRetorno";
    
}
