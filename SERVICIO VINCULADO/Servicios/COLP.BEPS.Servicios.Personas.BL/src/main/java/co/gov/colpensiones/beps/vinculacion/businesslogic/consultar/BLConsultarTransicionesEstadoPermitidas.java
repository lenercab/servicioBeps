package co.gov.colpensiones.beps.vinculacion.businesslogic.consultar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.gov.colpensiones.beps.comunes.dto.TransicionesEstadoPermitidasDTO;
import co.gov.colpensiones.beps.comunes.dto.VinculadoEstadoDetalleDTO;
import co.gov.colpensiones.beps.comunes.utilidades.Constantes;
import co.gov.colpensiones.beps.comunes.utilidades.Util;
import co.gov.colpensiones.beps.comunes.utilidades.Validador;
import co.gov.colpensiones.beps.log.ConstantesLogger;
import co.gov.colpensiones.beps.log.LoggerBeps;
import co.gov.colpensiones.beps.log.TimeTracer;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstadoEjecucion;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContextoExterno;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoDocumentoPersonaNatural;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoEstadoVinculadoBeps;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionTransicionEstado;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoRespuestaTransicionEstado;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoRespuestaTransicionEstadoPermitidos;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoRespuestaTransicionEstadoVinculado;
import co.gov.colpensiones.beps.vinculacion.businesslogic.DAConsultarTransicionesEstadoPermitidas;

/**
 * <b>Descripción:</b> Clase encargada de la lógica de negocio para consultar transiciones de estados permitidas. <br/>
 * <b>Caso de Uso:</b> GVI-VIN-3-FAB-12-ConsultarTransicionesDeEstadoPermitidas <br/>
 * 
 * @author Arnold Rios Delgado <arrios@heinsohn.com.co>
 */
public class BLConsultarTransicionesEstadoPermitidas {

    /** Clase de acceso a datos. */
    private DAConsultarTransicionesEstadoPermitidas daTransicionesEstado = null;
    /** Atributo para tomar trazas de tiempo durante la ejecución del preproceso. */
    private TimeTracer tracer;

    /**
     * Constructor de la clase.
     * 
     * @param log
     *            Log con el que se va a escribir de la BD.
     */
    public BLConsultarTransicionesEstadoPermitidas(LoggerBeps log) {
        daTransicionesEstado = new DAConsultarTransicionesEstadoPermitidas();
        this.tracer = new TimeTracer(log, new HashMap<String, String>());
    }

    /**
     * Método encargado de consultar transiciones de estados permitidas
     * 
     * @param informacionContexto
     *            Contiene los datos de contexto de la invocación del servicio web.
     * @param tipoInformacionTransicionEstado
     *            Información de entrada vinculados y/o estado detalle vinculado
     * @return respuesta del servicio
     */
    public TipoRespuestaTransicionEstado consultarTransicionEstado(TipoInformacionContextoExterno informacionContexto,
            TipoInformacionTransicionEstado tipoInformacionTransicionEstado) {

        HashMap<String, Object> payLoadTrace = new HashMap<String, Object>();
        tracer.inicio();

        payLoadTrace.put(ConstantesLogger.OBJETO_CONTEXTO_ENTRADA, informacionContexto);
        payLoadTrace.put(ConstantesLogger.OBJETO_NEGOCIO_ENTRADA, tipoInformacionTransicionEstado);
        tracer.getLogger().trace(payLoadTrace, tracer.getMetadata());

        TipoRespuestaTransicionEstado respuesta = new TipoRespuestaTransicionEstado();

        try {

            boolean esNuloInformacionVinculado = true;

            List<TipoDocumentoPersonaNatural> listaVinculados = tipoInformacionTransicionEstado.getVinculado();
            if (listaVinculados != null && !listaVinculados.isEmpty()) {
                for (TipoDocumentoPersonaNatural iter_vinculado : listaVinculados) {

                    if ((iter_vinculado.getNumeroDocumento() != null && !iter_vinculado.getNumeroDocumento().isEmpty())
                            || (iter_vinculado.getTipoDocumento() != null && !iter_vinculado.getTipoDocumento().isEmpty())) {
                        esNuloInformacionVinculado = false;
                    }
                }
            }
            /* logica del servicio */
            respuesta = consultarTransicionEstadosPermitidas(informacionContexto, tipoInformacionTransicionEstado,esNuloInformacionVinculado);

            if (Constantes.COD_FORMATO_INVALIDO_OBLIGATORIO_NO_RECIBIDO.equals(respuesta.getEstadoEjecucion().getCodigo())) {
                HashMap<String, Object> payLoad = new HashMap<String, Object>();
                payLoad.put(Constantes.MSJ_ERROR_LOG, respuesta.getEstadoEjecucion().getDescripcion());
                tracer.getLogger().error(payLoad, tracer.getMetadata());
            }
        } catch (Exception e) {
            respuesta.setEstadoEjecucion(respuestaNegocioServicio(Constantes.COD_ERROR_INTERNO, Constantes.DESC_ERROR_INTERNO));
            tracer.getLogger().error(e);
        } finally {
            tracer.fin("Fin consultar Transicion Estado");
        }
        return respuesta;
    }

    /**
     * Método que contiene la lógica de consultar transiciones de estados permitidas
     * 
     * @param informacionContexto
     *            Contiene los datos de contexto de la invocación del servicio web.
     * @param tipoInformacionTransicionEstado
     *            Información de entrada vinculados y/o estado detalle vinculado
     * @param esNuloInformacionVinculado indica si la lista de vinculados esta nula
     * @return respuesta del servicio
     * @throws Exception
     *             propaga la excepción
     */
    private TipoRespuestaTransicionEstado consultarTransicionEstadosPermitidas(TipoInformacionContextoExterno informacionContexto,
            TipoInformacionTransicionEstado tipoInformacionTransicionEstado, boolean esNuloInformacionVinculado) throws Exception {

        TipoRespuestaTransicionEstado respuesta = new TipoRespuestaTransicionEstado();
        StringBuffer lstErrores = new StringBuffer();
        HashMap<String, Object> payLoad = new HashMap<String, Object>();
        lstErrores.append(this.validarDatosEntradaDetalleEstado(informacionContexto, tipoInformacionTransicionEstado, esNuloInformacionVinculado));
        if (lstErrores.toString().length() > 0) {
            respuesta.setEstadoEjecucion(respuestaNegocioServicio(Constantes.COD_FORMATO_INVALIDO_OBLIGATORIO_NO_RECIBIDO,
                    lstErrores.toString()));
        } else {

            /* valida que la lista tenga menos de 50 registros */
            if (tipoInformacionTransicionEstado.getVinculado() != null
                    && tipoInformacionTransicionEstado.getVinculado().size() > Constantes.NUMERO_MAXIMO_PERMITIDO_REGISTROS) {
                respuesta.setEstadoEjecucion(respuestaNegocioServicio(Constantes.COD_ERROR_CANTIDAD_REGISTROS_SUPERA_MAXIMO_PERMITIDO,
                        Constantes.MSJ_ERROR_CANTIDAD_REGISTROS_SUPERA_MAXIMO_PERMITIDO));
                payLoad.put(Constantes.MSJ_ERROR_LOG, Constantes.MSJ_ERROR_CANTIDAD_REGISTROS_SUPERA_MAXIMO_PERMITIDO);
                tracer.getLogger().error(payLoad, tracer.getMetadata());
                return respuesta;
            }

            /* Todas las transiciones permitidas parametrizadas en la base de datos */
            List<TransicionesEstadoPermitidasDTO> listaTransicionesParametricas = daTransicionesEstado.transicionesEstadoPermitidas();

            if (tipoInformacionTransicionEstado.getTipoEstadoVinculado() != null
                    && tipoInformacionTransicionEstado.getTipoEstadoVinculado().getEstadoActualVinculado() != null
                    && !tipoInformacionTransicionEstado.getTipoEstadoVinculado().getEstadoActualVinculado().isEmpty()) {
                // paso 5
                /* Lista de transiciones permitidas por estado y detalle estado recibidas */
                List<TipoRespuestaTransicionEstadoPermitidos> respuestaTransicionesPermitidas = buscarTransiconesPermitidas(
                        tipoInformacionTransicionEstado.getTipoEstadoVinculado().getEstadoActualVinculado(),
                        tipoInformacionTransicionEstado.getTipoEstadoVinculado().getDetalleEstadoActualVinculado(),
                        listaTransicionesParametricas);

                payLoad.put(Constantes.CAMPO_ESTADO, tipoInformacionTransicionEstado.getTipoEstadoVinculado().getEstadoActualVinculado());
                payLoad.put(Constantes.CAMPO_DETALLE_ESTADO, tipoInformacionTransicionEstado.getTipoEstadoVinculado().getDetalleEstadoActualVinculado());
                payLoad.put(Constantes.MSJ_LOG, "Validación estado");
                tracer.getLogger().info(payLoad,tracer.getMetadata());
                payLoad.remove(Constantes.MSJ_LOG);
                if (respuestaTransicionesPermitidas.isEmpty()) {
                    respuesta.setEstadoEjecucion(respuestaNegocioServicio(
                            Constantes.COD_ERROR_NO_TRANSICIONES_PERMITIDAS_ESTADO_DETALLE_ACTUAL,
                            Constantes.MSJ_ERROR_NO_TRANSICIONES_PERMITIDAS_ESTADO_DETALLE_ACTUAL));
                    payLoad.put(Constantes.MSJ_ERROR_LOG, Constantes.MSJ_ERROR_NO_TRANSICIONES_PERMITIDAS_ESTADO_DETALLE_ACTUAL);
                    tracer.getLogger().error(payLoad, tracer.getMetadata());
                    return respuesta;
                } else {// paso 14 Si todas las consultas se realizaron exitosamente, se envía:
                    respuesta.setEstadosPermitidos(respuestaTransicionesPermitidas);
                    respuesta.setEstadoEjecucion(respuestaNegocioServicio(Constantes.COD_INVOCACION_EXITOSA, Constantes.MSJ_FIN_EXITO));
                }
            }
            // paso 7

            
            
            lstErrores.append(this.validarDatosEntradaVinculado(informacionContexto, tipoInformacionTransicionEstado,esNuloInformacionVinculado));
            if (lstErrores.toString().length() > 0) {
                if (tipoInformacionTransicionEstado.getTipoEstadoVinculado() == null
                        || tipoInformacionTransicionEstado.getTipoEstadoVinculado().getEstadoActualVinculado() == null || tipoInformacionTransicionEstado
                                .getTipoEstadoVinculado().getEstadoActualVinculado().isEmpty()) {
                respuesta.setEstadoEjecucion(respuestaNegocioServicio(Constantes.COD_FORMATO_INVALIDO_OBLIGATORIO_NO_RECIBIDO,
                        lstErrores.toString()));
                }
            } else {

                if (!esNuloInformacionVinculado) {
                    /* lista de vincualdos recibidas como parametros */
                    List<TipoDocumentoPersonaNatural> listaVinculados = tipoInformacionTransicionEstado.getVinculado();
                    /* lista de los vinculados existentes en la bd con estado y detalle */
                    List<VinculadoEstadoDetalleDTO> listaVinculadoEstadoDetalle = daTransicionesEstado
                            .consultarEstadoDetalleVinculado(listaVinculados);
                    /* lista de los vinculados estados y el resultado de consulta */
                    List<TipoRespuestaTransicionEstadoVinculado> respuestaListaVinculado = new ArrayList<TipoRespuestaTransicionEstadoVinculado>();
                    /* transiciones permitdas por estado y detalle estado */
                    List<TipoRespuestaTransicionEstadoPermitidos> respuestaTransicionesPermitidas = null;
                    /* cada uno de los vinculados con los estadaos permitidos y el resultado de la consulta */
                    TipoRespuestaTransicionEstadoVinculado respuestaVinculado = null;

                    TipoDocumentoPersonaNatural vinculado;
                    int contadorExitoConsulta = 0;

                    for (TipoDocumentoPersonaNatural iter_personaNatural : listaVinculados) {
                        payLoad.clear();
                        /* Registro en el log*/
                        payLoad.put(Constantes.MSJ_LOG, "Validación vinculado");
                        payLoad.put(Constantes.CAMPO_TIPO_DOCUMENTO, iter_personaNatural.getTipoDocumento());
                        payLoad.put(Constantes.CAMPO_IDENTIFICACION, iter_personaNatural.getNumeroDocumento());
                        tracer.getLogger().info(payLoad,tracer.getMetadata());
                        payLoad.remove(Constantes.MSJ_LOG);
                        
                        respuestaVinculado = new TipoRespuestaTransicionEstadoVinculado();
                        vinculado = new TipoDocumentoPersonaNatural();
                        vinculado.setNumeroDocumento(iter_personaNatural.getNumeroDocumento());
                        vinculado.setTipoDocumento(iter_personaNatural.getTipoDocumento());
                        respuestaVinculado.setVinculado(vinculado);
                        respuestaVinculado.setResultadoConsulta(Constantes.MSJ_ERROR_NO_EXISTE_VINCULADO);
                        for (VinculadoEstadoDetalleDTO iter_vinculadoEstado : listaVinculadoEstadoDetalle) {

                            if (iter_personaNatural.getNumeroDocumento().equals(iter_vinculadoEstado.getNumeroDocuemnto()) 
                                    && iter_personaNatural.getTipoDocumento().equals(iter_vinculadoEstado.getTipoDocumento())) {
                                respuestaTransicionesPermitidas = buscarTransiconesPermitidas(iter_vinculadoEstado.getEstadoActual(),
                                        iter_vinculadoEstado.getDetalleEstadoActual(), listaTransicionesParametricas);
                                if (respuestaTransicionesPermitidas.isEmpty()) {
                                    respuestaVinculado
                                            .setResultadoConsulta(Constantes.MSJ_ERROR_NO_EXISTEN_TRANSICIONES_PERMITIDAS_ESTADO_DETALLE);
                                    payLoad.put(Constantes.MSJ_ERROR_LOG, Constantes.MSJ_ERROR_NO_EXISTEN_TRANSICIONES_PERMITIDAS_ESTADO_DETALLE);
                                    payLoad.put(Constantes.CAMPO_ESTADO, iter_vinculadoEstado.getEstadoActual());
                                    payLoad.put(Constantes.CAMPO_DETALLE_ESTADO, iter_vinculadoEstado.getDetalleEstadoActual());
                                    /* Registro en el log*/
                                    tracer.getLogger().error(payLoad, tracer.getMetadata());
                                    
                                } else {
                                    respuestaVinculado.setResultadoConsulta("Consulta exitosa");
                                    respuestaVinculado.setTipoEstados(respuestaTransicionesPermitidas);
                                    contadorExitoConsulta++;
                                }
                            }
                        }
                        if(Constantes.MSJ_ERROR_NO_EXISTE_VINCULADO.equals(respuestaVinculado.getResultadoConsulta())){
                            payLoad.put(Constantes.MSJ_ERROR_LOG, Constantes.MSJ_ERROR_NO_EXISTE_VINCULADO);
                            /* Registro en el log*/
                            tracer.getLogger().error(payLoad, tracer.getMetadata());
                            }
                       
                        respuestaListaVinculado.add(respuestaVinculado);
                    }

                    respuesta.setVinculados(respuestaListaVinculado);

                    if (contadorExitoConsulta == 0) {
                        /* Si no se realizó exitosamente ninguna consultar, se envía: */
                        respuesta.setEstadoEjecucion(respuestaNegocioServicio(Constantes.COD_FIN_FALLIDO, Constantes.MSJ_FIN_FALLIDO));
                    } else if (contadorExitoConsulta == respuestaListaVinculado.size()) {
                        /* Si todas las consultas se realizaron exitosamente */
                        respuesta.setEstadoEjecucion(respuestaNegocioServicio(Constantes.COD_INVOCACION_EXITOSA, Constantes.MSJ_FIN_EXITO));
                    } else {
                        /* Si no fueron realizadas exitosamente todas las consultas, se envía */
                        respuesta.setEstadoEjecucion(respuestaNegocioServicio(Constantes.COD_FIN_EXITO_ERRORES,
                                Constantes.MSJ_FIN_EXITO_ERRORES));
                    }
                }

            }
        }

        return respuesta;

    }

    /**
     * Válida tando la obligatoriedad, como la estructura de los vinculados recibidos como parámetros
     * 
     * @param informacionContexto
     *            Contiene los datos de contexto de la invocación del servicio web.
     * @param tipoInformacionTransicionEstado
     *            Información de entrada vinculados y/o estado detalle vinculado
     * @param esNuloInformacionVinculado Indica si la lista de vinculados esta nula
     * @return String errores presentados, separados por salto de línea.
     * @throws Exception
     *             propaga la excepción
     */
    private String validarDatosEntradaVinculado(TipoInformacionContextoExterno informacionContexto,
            TipoInformacionTransicionEstado tipoInformacionTransicionEstado, boolean esNuloInformacionVinculado) throws Exception {

        StringBuilder errores = new StringBuilder();

        /* valida Tipo estado */
        if (tipoInformacionTransicionEstado.getTipoEstadoVinculado() == null
                || tipoInformacionTransicionEstado.getTipoEstadoVinculado().getEstadoActualVinculado() == null || tipoInformacionTransicionEstado
                        .getTipoEstadoVinculado().getEstadoActualVinculado().isEmpty()) {
            if (tipoInformacionTransicionEstado.getVinculado() == null || tipoInformacionTransicionEstado.getVinculado().isEmpty()) {
                errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "EstadoActualVinculado"));

            } else {
                for (TipoDocumentoPersonaNatural iter_vinculado : tipoInformacionTransicionEstado.getVinculado()) {
                    if (iter_vinculado.getNumeroDocumento() == null || iter_vinculado.getNumeroDocumento().isEmpty()) {
                        errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "NumeroDocumento"));
                    }
                    if (iter_vinculado.getTipoDocumento() == null || iter_vinculado.getTipoDocumento().isEmpty()) {
                        errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "TipoDocumento"));
                    }
                }
            }

        }

        if(!esNuloInformacionVinculado)
        {
            ArrayList<String> tiposDocumento = Util.getResourcePropertyArray(Constantes._HOMOLOGACION_COMUN_NAME,
                    Constantes.PREFIJO_LLAVES_TIPO_DOCUMENTO);

            if (tipoInformacionTransicionEstado.getVinculado() != null && !tipoInformacionTransicionEstado.getVinculado().isEmpty()) {
                for (TipoDocumentoPersonaNatural iter_vinculado : tipoInformacionTransicionEstado.getVinculado()) {
                    /* Validacion tipo de documento */
                    if (!tiposDocumento.contains(iter_vinculado.getTipoDocumento())) {
                        errores.append(Constantes.MSJ_ERROR_DATO_NO_VALIDO.replaceAll(Constantes.PARAMETRO0, "TipoDocumento")
                                + tiposDocumento.toString() + "\n");
                    }

                    /* TipoDocumentoPersonaNatural */
                    errores.append(new Validador<TipoDocumentoPersonaNatural>().ValidarDataContract(iter_vinculado));
                }

            }
        }
       

        return errores.toString();

    }

    /**
     * Válida tando la obligatoriedad, como la estructura del estado detalle vinculado
     * 
     * @param informacionContexto
     *            Contiene los datos de contexto de la invocación del servicio web.
     * @param tipoInformacionTransicionEstado
     *            Información de entrada vinculados y/o estado detalle vinculado
     * @param esNuloInformacionVinculado Indica si la lista de vinculados esta nula
     * @return String errores presentados separados por salto de línea.
     * @throws Exception
     *             propaga la excepción
     */
    private String validarDatosEntradaDetalleEstado(TipoInformacionContextoExterno informacionContexto,
            TipoInformacionTransicionEstado tipoInformacionTransicionEstado, boolean esNuloInformacionVinculado) throws Exception {

        StringBuilder errores = new StringBuilder();

        if (tipoInformacionTransicionEstado != null) {

            /* valida Tipo estado */
            TipoEstadoVinculadoBeps tipoEstado = tipoInformacionTransicionEstado.getTipoEstadoVinculado();
            if (tipoEstado != null && tipoEstado.getEstadoActualVinculado() != null) {
                if (!tipoEstado.getEstadoActualVinculado().isEmpty()) {
                    String listaEstados = daTransicionesEstado.consultarEstadoPermitidos();

                    if (!listaEstados.contains(tipoInformacionTransicionEstado.getTipoEstadoVinculado().getEstadoActualVinculado())) {
                        errores.append("EstadoActualVinculado no pertenece al dominio parametrizados en el Sistema de Gestión Beps.\n");
                    }

                } else {
                    if (esNuloInformacionVinculado) {
                        errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "EstadoActualVinculado"));
                    }
                }
                if (tipoEstado != null && tipoEstado.getDetalleEstadoActualVinculado() != null
                        && !tipoEstado.getDetalleEstadoActualVinculado().isEmpty()) {
                    String listaDetalleEstados = daTransicionesEstado.consultarDetalleEstadoPermitidos();
                    if (!listaDetalleEstados.contains(tipoInformacionTransicionEstado.getTipoEstadoVinculado()
                            .getDetalleEstadoActualVinculado())) {
                        errores.append("DetalleEstadoActualVinculado no pertenece al dominio parametrizados en el Sistema de Gestión Beps.\n");
                    }
                }

            } else {
                if (esNuloInformacionVinculado) {
                    errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "EstadoActualVinculado"));
                }
            }

            if (informacionContexto != null) {

                /* valida el sitema de origen */
                if (informacionContexto.getSistemaOrigen() == null || informacionContexto.getSistemaOrigen().isEmpty()) {
                    errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "SistemaOrigen"));
                } else {

                    if (informacionContexto.getSistemaOrigen().length() > 50) {
                        errores.append("SistemaOrigen debe tener una longitud máximo 50\n");
                    }

                    Pattern p = Pattern.compile("[A-Z0-9]+");
                    Matcher m = p.matcher(informacionContexto.getSistemaOrigen());
                    if (!m.matches()) {
                        errores.append("SistemaOrigen Permite solo letras mayúsculas y números.No permite espacios\n");
                    }

                    if (informacionContexto.getUsuarioSistemaExterno() != null) {
                        if (informacionContexto.getUsuarioSistemaExterno().length() > 60
                                || informacionContexto.getUsuarioSistemaExterno().length() < 5) {
                            errores.append("UsuarioSistema debe tener una longitud de 5 a 60 caracteres\n");
                        }
                    }
                }

            } else {
                errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "informacionContexto"));
            }

        } else {
            errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "DatosEntrada"));
        }

        return errores.toString();

    }

    /**
     * Busca en la lista de transiciones recibidas como parametro, las transaciones permitidas por estado y detalle estado
     * 
     * @param estadoActualVinculado
     *            Estado actual
     * @param detalleEstadoActualVinculado
     *            Detalle estado actual
     * @param listaTransicionesParametricas
     *            Lista de todas las transiciones permitidas parametrizada en la base de datos
     * @return List<TipoRespuestaTransicionEstadoPermitidos> lista de las transiciones permitidas por estado y detalle estado
     */
    private List<TipoRespuestaTransicionEstadoPermitidos> buscarTransiconesPermitidas(String estadoActualVinculado,
            String detalleEstadoActualVinculado, List<TransicionesEstadoPermitidasDTO> listaTransicionesParametricas) {
        List<TipoRespuestaTransicionEstadoPermitidos> listaTransicionesPermitidas = new ArrayList<TipoRespuestaTransicionEstadoPermitidos>();
        /* Cada una de las transiciones permitidas */
        TipoRespuestaTransicionEstadoPermitidos transicionesPermitidas = null;
        TipoEstadoVinculadoBeps tipoEstado = null;
        for (TransicionesEstadoPermitidasDTO var_estadoPermitido : listaTransicionesParametricas) {
            if (var_estadoPermitido.getEstadoInicial().equals(estadoActualVinculado)) {
                if ((detalleEstadoActualVinculado == null && var_estadoPermitido.getDetalleEstadoInicial() == null)
                        || (detalleEstadoActualVinculado != null && var_estadoPermitido.getDetalleEstadoInicial() != null && detalleEstadoActualVinculado
                                .equals(var_estadoPermitido.getDetalleEstadoInicial()))) {

                    String tipoOperacion = null;
                    if (var_estadoPermitido.getReactivaCuenta() || var_estadoPermitido.getCancelaCuenta()) {
                        tipoOperacion = var_estadoPermitido.getReactivaCuenta() ? Constantes.TIPO_OPERACION_REACTIVACION
                                : Constantes.TIPO_OPERACION_CANCELACION;
                    }
                    transicionesPermitidas = new TipoRespuestaTransicionEstadoPermitidos();
                    tipoEstado = new TipoEstadoVinculadoBeps();
                    tipoEstado.setEstadoActualVinculado(var_estadoPermitido.getEstadoFinal());
                    tipoEstado.setDetalleEstadoActualVinculado(var_estadoPermitido.getDetalleEstadoFinal());
                    transicionesPermitidas.setTipoEstado(tipoEstado);
                    transicionesPermitidas.setTipoOperacion(tipoOperacion);
                    listaTransicionesPermitidas.add(transicionesPermitidas);
                }
            }

        }
        return listaTransicionesPermitidas;
    }

    /**
     * Método que genera un objeto TipoEstadoEjecucion de acuerdo al código y mensaje ingresados por parámetro.
     * 
     * @param codigo
     *            , Código de resultado de ejecución del proceso
     * @param mensaje
     *            , Mensaje de resultado de ejecución del proceso
     * @return tipoEstadoEjecucion, objeto que contiene la respuesta del servicio
     */
    protected TipoEstadoEjecucion respuestaNegocioServicio(String codigo, String mensaje) {
        TipoEstadoEjecucion tipoEstadoEjecucion = new TipoEstadoEjecucion();
        tipoEstadoEjecucion.setId(tracer.getLogger().getIdTransaccion());
        tipoEstadoEjecucion.setCodigo(codigo);
        tipoEstadoEjecucion.setDescripcion(mensaje);
        return tipoEstadoEjecucion;
    }

}
