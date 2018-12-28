package co.gov.colpensiones.beps.vinculacion.businesslogic;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import co.gov.colpensiones.beps.comunes.utilidades.Constantes;
import co.gov.colpensiones.beps.comunes.utilidades.ConstantesLoggerServicios;
import co.gov.colpensiones.beps.comunes.utilidades.DatabaseManager;
import co.gov.colpensiones.beps.comunes.utilidades.ResourceUtil;
import co.gov.colpensiones.beps.comunes.utilidades.Util;
import co.gov.colpensiones.beps.dal.utilidades.DataRow;
import co.gov.colpensiones.beps.dal.utilidades.DataStoredProcedure;
import co.gov.colpensiones.beps.dal.utilidades.DataTable;
import co.gov.colpensiones.beps.dal.utilidades.DbParameter;
import co.gov.colpensiones.beps.excepciones.DataAccessException;
import co.gov.colpensiones.beps.excepciones.LogicalException;
import co.gov.colpensiones.beps.log.LoggerBeps;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstadoEjecucion;
import co.gov.colpensiones.beps.schemas._1_0.personas.ObjectFactory;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionBasicaPersonaNatural;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionBasicaSolicitante;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionGeneralVinculado;
import co.gov.colpensiones.beps.vinculacion.businesslogic.crear.InformacionVinculado;

/**
 * <b>Descripcion:</b> Clase encargada de la logica de negocio para la modificacion de informacion de vinculados <br/>
 * <b>Caso de Uso:</b> GOP-VIN-1-FAB-11-ModificarInformacionSensibleVinculado <br/>
 * 
 * @author Yenny Nustez <ynustez@heinsohn.com.co>
 */
public class BLVinculado {

    /** Variable para el manejo de log de la funcionalidad */
    protected LoggerBeps log = null;

    /**
     * Método constructor
     * 
     * @param log
     *            log asociado a la funcionalidad
     */
    public BLVinculado(LoggerBeps log) {
        this.log = log;
    }

    /**
     * Método que genera la traza de log de error del servicio
     * 
     * @param metaData
     *            datos del servicio
     * @param dataError
     *            true si corresponde a un error de acceso a datos. False en caso contrario.
     * @param e
     *            Excepción generada
     */
    protected void generarLogError(HashMap<String, String> metaData, boolean dataError, Exception e) {

        /* Registro en el log */
        HashMap<String, Object> payLoad = new HashMap<String, Object>();

        if (dataError) {
            /* Error de Base de datos */
            String mensajeError = ((DataAccessException) e).getErrorInterno().getMessage();
            payLoad.put(Constantes.ERROR_BD, mensajeError);
            payLoad.put(Constantes.TIPO_ERROR, Constantes.SERVICIO_WEB);
            payLoad.put(Constantes.URL, "/COLP.BEPS.Servicios.Vinculado/ContratoSvcVinculado");
            payLoad.put(ConstantesLoggerServicios.MENSAJE_ERROR, Constantes.ERROR + mensajeError);
            DataAccessException error = new DataAccessException(payLoad, metaData, e);
            log.error(error);
        } else {
            /* Error lógica de negocio */
            payLoad.put(Constantes.TIPO_ERROR, Constantes.SERVICIO_WEB);
            payLoad.put(Constantes.URL, "/COLP.BEPS.Servicios.Vinculado/ContratoSvcVinculado");
            payLoad.put(ConstantesLoggerServicios.MENSAJE_ERROR, Constantes.ERROR + e.getMessage());
            LogicalException error = new LogicalException(payLoad, metaData, e);
            log.error(error);
        }
    }

    /**
     * Método que retorna un objeto mapeando las constantes de código de error y respuesta fallida de un servicio.
     * 
     * @return tipoEstadoEjecucion, objeto que contiene la respuesta fallida del servicio
     */
    protected TipoEstadoEjecucion respuestaErrorTecnicoServicio() {
        return respuestaNegocioServicio(Constantes.COD_ERROR_INTERNO, Constantes.DESC_ERROR_INTERNO);
    }

    /**
     * Método que retorna un objeto mapeando las constantes de código y respuesta exitosa de un servicio.
     * 
     * @return tipoEstadoEjecucion, objeto que contiene la respuesta exitosa del servicio
     */
    protected TipoEstadoEjecucion respuestaExitosaServicio() {
        return respuestaNegocioServicio(Constantes.COD_INVOCACION_EXITOSA, Constantes.DESC_INVOCACION_EXITOSA);
    }

    /**
     * Método que retorna un objeto mapeando las constantes de código y respuesta exitosa de un servicio.
     * 
     * @return tipoEstadoEjecucion, objeto que contiene la respuesta exitosa del servicio
     */
    protected TipoEstadoEjecucion respuestaErrorInvocacionPlenitud() {
        return respuestaNegocioServicio(Constantes.COD_ERROR_VINCULADO_INVOCAR_PLENITUD, Constantes.DESC_ERROR_INVOCAR_PLENITUD);
    }
    /**
     * Método que retorna un objeto mapeando las constantes de código y respuesta exitosa de un servicio.
     * @param descripcion, descripcion del error notificado por plenitud
     * @return tipoEstadoEjecucion, objeto que contiene la respuesta exitosa del servicio
     */
    protected TipoEstadoEjecucion respuestaCreacionVinculadoSinCuentaIndvidual(String descripcion) {
        return respuestaNegocioServicio(Constantes.COD_ERROR_VINCULADO_INVOCAR_PLENITUD, descripcion);
    }
    
    /**
     * Método que genera un objeto TipoEstadoEjecucion de acuerdo al código y mensaje ingresados por parámetro.
     * 
     * @param codigo
     *            Código de resultado de ejecución del proceso
     * @param mensaje
     *            Mensaje de resultado de ejecución del proceso
     * @return tipoEstadoEjecucion, objeto que contiene la respuesta del servicio
     */
    protected TipoEstadoEjecucion respuestaNegocioServicio(String codigo, String mensaje) {
        TipoEstadoEjecucion tipoEstadoEjecucion = new TipoEstadoEjecucion();
        tipoEstadoEjecucion.setId(log.getIdTransaccion());
        tipoEstadoEjecucion.setCodigo(codigo);
        tipoEstadoEjecucion.setDescripcion(mensaje);
        return tipoEstadoEjecucion;
    }

    /**
     * Método utilitario para parsear los valores nulos de una columna tipo String, definida para una fila de un objeto DataTable
     * 
     * @param fila
     *            Fila de la tabla que contiene los datos de la columna a validar
     * @param nombreColumna
     *            Nombre de la columna a validar
     * @return Cadena con el valor de la columna ingresada por parámetro. En caso que el valor sea nulo, se retorna la cadena vacía.
     */
    protected String parseDataRowValue(DataRow fila, String nombreColumna) {
        String value = "";
        if (fila != null && fila.getValue(nombreColumna) != null) {
            value = fila.getValue(nombreColumna).toString();
        }
        return value;
    }

    /**
     * Método utilitario para parsear los valores de una columna tipo BigDecimal, definida para una fila de un objeto DataTable
     * 
     * @param fila
     *            Fila de la tabla que contiene los datos de la columna a validar
     * @param nombreColumna
     *            Nombre de la columna a validar
     * @return BigDecimal con el valor de la columna ingresada por parámetro. En caso que el valor sea nulo, se retorna 0.
     */
    protected BigDecimal parseDataRowBigDecimalValue(DataRow fila, String nombreColumna) {
        BigDecimal value = BigDecimal.ZERO;
        if (fila != null && fila.getValue(nombreColumna) != null && fila.getValue(nombreColumna).toString().trim().length() > 0) {
            value = new BigDecimal(fila.getValue(nombreColumna).toString());
        }
        return value;
    }

    /**
     * Método utilitario para parsear los valores nulos de una columna tipo Integer, definida para una fila de un objeto DataTable
     * 
     * @param fila
     *            Fila de la tabla que contiene los datos de la columna a validar
     * @param nombreColumna
     *            Nombre de la columna a validar
     * @return Integer con el valor de la columna ingresada por parámetro. En caso que el valor sea nulo, se retorna 0.
     */
    protected Integer parseDataRowIntegerValue(DataRow fila, String nombreColumna) {
        Integer value = 0;
        if (fila != null && fila.getValue(nombreColumna) != null && fila.getValue(nombreColumna).toString().trim().length() > 0)
            value = new Integer(fila.getValue(nombreColumna).toString());
        return value;
    }

    /**
     * Método utilitario para parsear los valores nulos de una columna tipo Date, definida para una fila de un objeto DataTable.
     * 
     * @param fila
     *            Fila de la tabla que contiene los datos de la columna a validar
     * @param nombreColumna
     *            Nombre de la columna a validar
     * @return Cadena con la fecha en formato yyyyMMdd. En caso que el valor sea nulo, se retorna vacío.
     */
    protected String parseDataRowDateValue(DataRow fila, String nombreColumna) {
        String value = "";
        if (fila != null && fila.getValue(nombreColumna) != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                Date fecha = sdf.parse(fila.getValue(nombreColumna).toString());
                sdf.applyPattern("yyyyMMdd");
                value = sdf.format(fecha);
            } catch (Exception e) {
                value = "";
            }
        }
        return value;
    }

    /**
     * Método utilitario para parsear los valores nulos de una columna tipo Bit, definida para una fila de un objeto DataTable.
     * 
     * @param fila
     *            Fila de la tabla que contiene los datos de la columna a validar
     * @param nombreColumna
     *            Nombre de la columna a validar
     * @param numericValue
     *            Variable booleana que indica el tipo de dato al cual debe ser homologado el valor obtenido de la columna. True
     *            homologación a números, false homologación a cadena
     * @return Cadena con el valor de la columna ingresada por parámetro, homologado según la variable numericValue. En caso que el valor
     *         sea nulo, se retorna vacío.
     */
    protected String parseDataRowBitValue(DataRow fila, String nombreColumna, boolean numericValue) {
        String value = "";
        if (fila != null && fila.getValue(nombreColumna) != null && fila.getValue(nombreColumna).toString().trim().length() > 0) {

            if (numericValue) {
                value = (Boolean.parseBoolean(fila.getValue(nombreColumna).toString())) ? "1" : "0";
            } else {
                value = (Boolean.parseBoolean(fila.getValue(nombreColumna).toString())) ? "S" : "N";
            }
        }
        return value;
    }

    /**
     * Método que realiza la homologación y ajuste de información de acuerdo a los parámetros establecidos por el Sistema de Cuentas
     * Individuales para la actualización de información sensible.
     * 
     * @param informacionVinculado
     *            Objeto que contiene la información del vinculado
     * @return Objeto con la información del vinculado, homologada al Sistema de Cuentas Individuales
     * @throws LogicalException
     */
    protected TipoInformacionBasicaSolicitante homologarDatosPlenitud(TipoInformacionBasicaSolicitante informacionVinculado)
            throws LogicalException {

        /* Homologacion tipo de documento, de acuerdo a definición de archivo de propiedades homologacionPlenitud.properties */
        String tipoDocumento = Util.getResourceProperty(Constantes._HOMOLOGACION_NAME, Constantes.PREFIJO_LLAVES_TIPO_DOCUMENTO
                + informacionVinculado.getDocumento().getTipoDocumento());
        informacionVinculado.getDocumento().setTipoDocumento(tipoDocumento);

        /* Homologación nombres y apellidos */
        try {

            /*
             * Se trunca la informacion de acuerdo a la longitud definida para cada campo por Plenitud. Estos valores se encuentran
             * configurados en el arhivo resources.properties
             */
            if (informacionVinculado.getNombresApellidos() != null) {

                int maxLongPrimerNombre = Integer.parseInt(ResourceUtil.getResourceValue(Constantes._RESOURCE_NAME,
                        "long-max-primer-nombre"));
                int maxLongSegundoNombre = Integer.parseInt(ResourceUtil.getResourceValue(Constantes._RESOURCE_NAME,
                        "long-max-segundo-nombre"));
                int maxLongPrimerApellido = Integer.parseInt(ResourceUtil.getResourceValue(Constantes._RESOURCE_NAME,
                        "long-max-primer-apellido"));
                int maxLongSegundoApellido = Integer.parseInt(ResourceUtil.getResourceValue(Constantes._RESOURCE_NAME,
                        "long-max-segundo-apellido"));

                TipoInformacionBasicaPersonaNatural nombres = informacionVinculado.getNombresApellidos().getValue();
                String primerNombre = nombres.getPrimerNombre() != null ? nombres.getPrimerNombre().toUpperCase() : "";
                String segundoNombre = nombres.getSegundoNombre() != null ? nombres.getSegundoNombre().toUpperCase() : "";
                String primerApellido = nombres.getPrimerApellido() != null ? nombres.getPrimerApellido().toUpperCase() : "";
                String segundoApellido = nombres.getSegundoApellido() != null ? nombres.getSegundoApellido().toUpperCase() : "";

                if (primerNombre.length() > maxLongPrimerNombre) {
                    primerNombre = primerNombre.substring(0, maxLongPrimerNombre);
                }
                if (segundoNombre.length() > maxLongSegundoNombre) {
                    segundoNombre = segundoNombre.substring(0, maxLongSegundoNombre);
                }
                if (primerApellido.length() > maxLongPrimerApellido) {
                    primerApellido = primerApellido.substring(0, maxLongPrimerApellido);
                }
                if (segundoApellido.length() > maxLongSegundoApellido) {
                    segundoApellido = segundoApellido.substring(0, maxLongSegundoApellido);
                }

                nombres.setPrimerNombre(primerNombre);
                nombres.setSegundoNombre(segundoNombre);
                nombres.setPrimerApellido(primerApellido);
                nombres.setSegundoApellido(segundoApellido);
                informacionVinculado.getNombresApellidos().setValue(nombres);
            } else {
                ObjectFactory factoryPersonas = new ObjectFactory();
                TipoInformacionBasicaPersonaNatural nombres = factoryPersonas.createTipoInformacionBasicaPersonaNatural();
                nombres.setPrimerNombre("");
                nombres.setSegundoNombre("");
                nombres.setPrimerApellido("");
                nombres.setSegundoApellido("");
                informacionVinculado.setNombresApellidos(factoryPersonas.createTipoInformacionBasicaSolicitanteNombresApellidos(nombres));
            }

            /* Homologación género */
            if (informacionVinculado.getInformacionAdicional() == null
                    || informacionVinculado.getInformacionAdicional().getGenero() == null) {
                informacionVinculado.getInformacionAdicional().setGenero("");
            }

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            HashMap<String, Object> payLoad = new HashMap<String, Object>();

            metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, informacionVinculado.getDocumento().getTipoDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, informacionVinculado.getDocumento().getNumeroDocumento());
            payLoad.put(Constantes.MSJ_ERROR_LOG, e.toString());
            throw new LogicalException(payLoad, metaData, e);
        }
        return informacionVinculado;
    }

    /**
     * Método que realiza la homologación y ajuste de información de acuerdo a los parámetros establecidos por el Sistema de Cuentas
     * Individuales para crear cuenta individual
     * 
     * @param result
     *            , contiene los datos que se van a homologar
     * @param informacionVinculado
     *            , información del vincualdo
     * @param respuestaGenero
     *            , objeto con la información para homologación de género
     * @return información del vinculado homologada
     * @throws LogicalException
     *             , si se genera algún error
     */
    protected InformacionVinculado homologarDatosPlenitudCrearCuenta(DataStoredProcedure result,
            TipoInformacionGeneralVinculado informacionVinculado, DataTable respuestaGenero) throws LogicalException {
        InformacionVinculado informacionHomologada = new InformacionVinculado();

        List<DbParameter> lista = result.getParametrosSalida();

        try {
            informacionHomologada.setIdentificadorVinculado(new Long(lista.get(0).getParameterValue().toString()));
            informacionHomologada.setNumeroDocumento(informacionVinculado.getIdentificacion().getNumeroDocumento());
            informacionHomologada.setTipoDocumento(informacionVinculado.getIdentificacion().getTipoDocumento());

            /* Homologacion tipo de documento */
            String tipoDocumento = Util.getResourceProperty(Constantes._HOMOLOGACION_NAME, Constantes.PREFIJO_LLAVES_TIPO_DOCUMENTO
                    + informacionVinculado.getIdentificacion().getTipoDocumento());
            informacionHomologada.setTipoDocumento(tipoDocumento);

            /* Se trunca la informacion de acuerdo a la definicion de plenitud */
            int maxLongPrimerNombre = Integer.parseInt(ResourceUtil.getResourceValue(Constantes._RESOURCE_NAME, "long-max-primer-nombre"));
            int maxLongSegundoNombre = Integer
                    .parseInt(ResourceUtil.getResourceValue(Constantes._RESOURCE_NAME, "long-max-segundo-nombre"));
            int maxLongPrimerApellido = Integer.parseInt(ResourceUtil.getResourceValue(Constantes._RESOURCE_NAME,
                    "long-max-primer-apellido"));
            int maxLongSegundoApellido = Integer.parseInt(ResourceUtil.getResourceValue(Constantes._RESOURCE_NAME,
                    "long-max-segundo-apellido"));

            String primerNombre = ((String) lista.get(4).getParameterValue()).toUpperCase();
            String segundoNombre = lista.get(5).getParameterValue() != null ? ((String) lista.get(5).getParameterValue()).toUpperCase()
                    : "";
            String primerApellido = ((String) lista.get(2).getParameterValue()).toUpperCase();
            String segundoApellido = lista.get(3).getParameterValue() != null ? ((String) lista.get(3).getParameterValue()).toUpperCase()
                    : "";

            if (primerNombre.length() > maxLongPrimerNombre) {
                primerNombre = primerNombre.substring(0, maxLongPrimerNombre);
            }
            if (segundoNombre.length() > maxLongSegundoNombre) {
                segundoNombre = segundoNombre.substring(0, maxLongSegundoNombre);
            }
            if (primerApellido.length() > maxLongPrimerApellido) {
                primerApellido = primerApellido.substring(0, maxLongPrimerApellido);
            }
            if (segundoApellido.length() > maxLongSegundoApellido) {
                segundoApellido = segundoApellido.substring(0, maxLongSegundoApellido);
            }

            informacionHomologada.setPrimerNombre(primerNombre);
            informacionHomologada.setSegundoNombre(segundoNombre);
            informacionHomologada.setPrimerApellido(primerApellido);
            informacionHomologada.setSegundoApellido(segundoApellido);

            /* Homologacion género */
            if (respuestaGenero != null && respuestaGenero.getRows() != null && !respuestaGenero.getRows().isEmpty()
                    && respuestaGenero.getRows().get(0).getValue("vho_valor_beps") != null) {
                informacionHomologada.setGenero((String) respuestaGenero.getRows().get(0).getValue("vho_valor_plenitud"));
            } else {
                informacionHomologada.setGenero("");
            }

            /* Homologacion fecha de nacimiento */
            if (lista.get(7).getParameterValue() != null) {
                Date fechaBD = (Date) lista.get(7).getParameterValue();
                DateFormat fechaDF = new SimpleDateFormat(Constantes.FORMATO_FECHA_AAAAMMDD);
                informacionHomologada.setFechaNacimiento(fechaDF.format(fechaBD));
            }
			if (informacionVinculado.getFechaVinculacion() != null) {
				informacionHomologada.setFechaVinculacion(informacionVinculado
						.getFechaVinculacion());
			}
        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            HashMap<String, Object> payLoad = new HashMap<String, Object>();
            metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, informacionVinculado.getIdentificacion().getTipoDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, informacionVinculado.getIdentificacion().getNumeroDocumento());
            payLoad.put(Constantes.MSJ_ERROR_LOG, e.toString());
            throw new LogicalException(payLoad, metaData, e);
        }
        return informacionHomologada;
    }

    /**
     * Método para ejecutar manualmente el rollback de una transaccion
     * 
     * @param database
     *            - Base de datos con la conexión que se encuentra abierta
     * @return cadena vacia si ocurrio una ejecución correcta
     */
    public String ejecutarRollbackTransaccion(DatabaseManager database) {
        try {
            /* Se realiza rollback */
            database.rollBack();
        } catch (Exception e) {
            return "Error al ejecutar la operación de Rollback : " + e.getMessage();
        }
        return "";
    }
}
