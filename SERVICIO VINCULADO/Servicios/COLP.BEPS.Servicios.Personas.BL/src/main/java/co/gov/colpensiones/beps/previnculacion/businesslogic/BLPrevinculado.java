package co.gov.colpensiones.beps.previnculacion.businesslogic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import co.gov.colpensiones.beps.comunes.utilidades.Constantes;
import co.gov.colpensiones.beps.comunes.utilidades.ConstantesLoggerServicios;
import co.gov.colpensiones.beps.comunes.utilidades.ResourceUtil;
import co.gov.colpensiones.beps.comunes.utilidades.Util;
import co.gov.colpensiones.beps.dal.utilidades.DataRow;
import co.gov.colpensiones.beps.dal.utilidades.DataTable;
import co.gov.colpensiones.beps.excepciones.DataAccessException;
import co.gov.colpensiones.beps.excepciones.LogicalException;
import co.gov.colpensiones.beps.log.LoggerBeps;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstadoEjecucion;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionRespuestaPrevinculacion;
import co.gov.colpensiones.beps.vinculacion.businesslogic.crear.InformacionVinculado;

/**
 * <b>Descripcion:</b> Clase encargada de la logica de negocio de los previnculados <br/>
 * <b>Caso de Uso:</b> GOP-AMB-1-FAB-08-CalcularNivelSisben <br/>
 * 
 * @author Helen Acero <hacero@heinsohn.com.co>
 */
public class BLPrevinculado {

    /** Variable para el manejo de log de la funcionalidad */
    protected LoggerBeps log = null;

    /**
     * Método contructor
     * 
     * @param log
     *            log con el que se va a escribir de la BD
     */
    public BLPrevinculado(LoggerBeps log) {
        this.log = log;
    }

    /**
     * Método utilitario para parsear los valores nulos de una columna, definida para una fila de un objeto DataTable
     * 
     * @param columnName
     * @return
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

        // registro en el log
        HashMap<String, Object> payLoad = new HashMap<String, Object>();

        if (dataError) {
            String mensajeError = ((DataAccessException) e).getErrorInterno().getMessage();
            payLoad.put(Constantes.ERROR_BD, mensajeError);
            payLoad.put(Constantes.TIPO_ERROR, Constantes.SERVICIO_WEB);
            payLoad.put(Constantes.URL, "/COLP.BEPS.Servicios.Previnculado/ContratoSvcPrevinculado");
            payLoad.put(ConstantesLoggerServicios.MENSAJE_ERROR, Constantes.ERROR + mensajeError);
            DataAccessException error = new DataAccessException(payLoad, metaData, e);
            log.error(error);
        } else {
            payLoad.put(Constantes.TIPO_ERROR, Constantes.SERVICIO_WEB);
            payLoad.put(Constantes.URL, "/COLP.BEPS.Servicios.Previnculado/ContratoSvcPrevinculado");
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
        TipoEstadoEjecucion tipoEstadoEjecucion = new TipoEstadoEjecucion();
        tipoEstadoEjecucion.setId(log.getIdTransaccion());
        tipoEstadoEjecucion.setCodigo(Constantes.COD_ERROR_INTERNO);
        tipoEstadoEjecucion.setDescripcion(Constantes.DESC_ERROR_INTERNO);
        return tipoEstadoEjecucion;
    }

    /**
     * Método que retorna un objeto mapeando las constantes de código y respuesta exitosa de un servicio.
     * 
     * @return tipoEstadoEjecucion, objeto que contiene la respuesta exitosa del servicio
     */
    protected TipoEstadoEjecucion respuestaExitosaServicio() {
        TipoEstadoEjecucion tipoEstadoEjecucion = new TipoEstadoEjecucion();
        tipoEstadoEjecucion.setId(log.getIdTransaccion());
        tipoEstadoEjecucion.setCodigo(Constantes.COD_INVOCACION_EXITOSA);
        tipoEstadoEjecucion.setDescripcion(Constantes.DESC_INVOCACION_EXITOSA);
        return tipoEstadoEjecucion;
    }

    /**
     * Método que retorna un objeto mapeando las constantes de código y respuesta exitosa de un servicio.
     * 
     * @return tipoEstadoEjecucion, objeto que contiene la respuesta exitosa del servicio
     */
    protected TipoEstadoEjecucion respuestaErrorInvocacionPlenitud() {
        return respuestaNegocioServicio(Constantes.COD_ERROR_INVOCAR_PLENITUD, Constantes.DESC_ERROR_INVOCAR_PLENITUD);
    }
    /**
     * Método que retorna un objeto mapeando las constantes de código y respuesta exitosa de un servicio.
     * @param descripcion, descripcion del error notificado por plenitud
     * @return tipoEstadoEjecucion, objeto que contiene la respuesta exitosa del servicio
     */
    protected TipoEstadoEjecucion respuestaCreacionPreVinculadoSinCuentaIndvidual(String descripcion) {
        return respuestaNegocioServicio(Constantes.COD_ERROR_INVOCAR_PLENITUD, descripcion);
    }
    /**
     * Método que genera un objeto TipoEstadoEjecucion de acuerdo al código y mensaje ingresados por parámetro.
     * 
     * @param codigo
     * @param mensaje
     * @return
     */
    protected TipoEstadoEjecucion respuestaNegocioServicio(String codigo, String mensaje) {
        TipoEstadoEjecucion tipoEstadoEjecucion = new TipoEstadoEjecucion();
        tipoEstadoEjecucion.setId(log.getIdTransaccion());
        tipoEstadoEjecucion.setCodigo(codigo);
        tipoEstadoEjecucion.setDescripcion(mensaje);
        return tipoEstadoEjecucion;
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
     *            , registro en la BD que contiene la información del género en la BD
     * @return información del vinculado homologada
     * @throws LogicalException
     *             , si se genera algún error
     */
    protected InformacionVinculado homologarDatosPlenitudCrearCuenta(DataTable result,
            TipoInformacionRespuestaPrevinculacion informacionResultadoPrevinculacion, DataTable respuestaGenero) throws LogicalException {
        InformacionVinculado informacionHomologada = new InformacionVinculado();

        try {
            informacionHomologada.setIdentificadorVinculado(new Long(result.getRows().get(0).getValue("vvi_pk_id").toString()));
            informacionHomologada.setNumeroDocumento(informacionResultadoPrevinculacion.getIdentificacion().getNumeroDocumento());
            informacionHomologada.setTipoDocumento(informacionResultadoPrevinculacion.getIdentificacion().getTipoDocumento());

            // Homologacion tipo de documento
            String tipoDocumento = Util.getResourceProperty(Constantes._HOMOLOGACION_NAME, Constantes.PREFIJO_LLAVES_TIPO_DOCUMENTO
                    + informacionHomologada.getTipoDocumento());
            informacionHomologada.setTipoDocumento(tipoDocumento);

            // Se trunca la informacion de acuerdo a la definicion de plenitud
            int maxLongPrimerNombre = Integer.parseInt(ResourceUtil.getResourceValue(Constantes._RESOURCE_NAME, "long-max-primer-nombre"));
            int maxLongSegundoNombre = Integer
                    .parseInt(ResourceUtil.getResourceValue(Constantes._RESOURCE_NAME, "long-max-segundo-nombre"));
            int maxLongPrimerApellido = Integer.parseInt(ResourceUtil.getResourceValue(Constantes._RESOURCE_NAME,
                    "long-max-primer-apellido"));
            int maxLongSegundoApellido = Integer.parseInt(ResourceUtil.getResourceValue(Constantes._RESOURCE_NAME,
                    "long-max-segundo-apellido"));

            String primerNombre = ((String) result.getRows().get(0).getValue("vpe_primer_nombre").toString()).toUpperCase();
            String segundoNombre = result.getRows().get(0).getValue("vpe_segundo_nombre") != null ? ((String) result.getRows().get(0)
                    .getValue("vpe_segundo_nombre").toString()).toUpperCase() : "";
            String primerApellido = ((String) result.getRows().get(0).getValue("vpe_primer_apellido").toString()).toUpperCase();
            String segundoApellido = result.getRows().get(0).getValue("vpe_segundo_apellido") != null ? ((String) result.getRows().get(0)
                    .getValue("vpe_segundo_apellido").toString()).toUpperCase() : "";

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

            if (respuestaGenero != null && respuestaGenero.getRows() != null && !respuestaGenero.getRows().isEmpty()
                    && respuestaGenero.getRows().get(0).getValue("vho_valor_beps") != null) {
                informacionHomologada.setGenero((String) respuestaGenero.getRows().get(0).getValue("vho_valor_plenitud"));
            } else {
                informacionHomologada.setGenero("");
            }
            
            if (result.getRows().get(0).getValue("vpe_fecha_nacimiento") != null) {
                DateFormat fechaDF = new SimpleDateFormat(Constantes.FORMATO_FECHA_AAAAMMDD);
                String fechaS = this.parseDataRowDateValue(result.getRows().get(0), "vpe_fecha_nacimiento");
                Date fechaBD = new Date(fechaDF.parse(fechaS).getTime());
                informacionHomologada.setFechaNacimiento(fechaDF.format(fechaBD));
            }
            DateFormat fechaDF = new SimpleDateFormat(Constantes.FORMATO_FECHA_AAAAMMDD);
            informacionHomologada.setFechaVinculacion(fechaDF.format(new Date()));
        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            HashMap<String, Object> payLoad = new HashMap<String, Object>();

            metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, informacionResultadoPrevinculacion.getIdentificacion().getTipoDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, informacionResultadoPrevinculacion.getIdentificacion().getNumeroDocumento());
            payLoad.put(Constantes.MSJ_ERROR_LOG, e.toString());
            throw new LogicalException(payLoad, metaData, e);
        }
        return informacionHomologada;
    }

}
