package co.gov.colpensiones.beps.vinculacion.businesslogic.consultar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.gov.colpensiones.beps.comunes.dto.TransicionesEstadoPermitidasDTO;
import co.gov.colpensiones.beps.comunes.dto.VinculadoEstadoDetalleDTO;
import co.gov.colpensiones.beps.comunes.utilidades.Constantes;
import co.gov.colpensiones.beps.comunes.utilidades.ConstantesLoggerServicios;
import co.gov.colpensiones.beps.comunes.utilidades.Util;
import co.gov.colpensiones.beps.comunes.utilidades.Validador;
import co.gov.colpensiones.beps.log.ConstantesLogger;
import co.gov.colpensiones.beps.log.LoggerBeps;
import co.gov.colpensiones.beps.log.TimeTracer;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstadoEjecucion;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContextoRadicacion;
import co.gov.colpensiones.beps.schemas._1_0.personas.ExtendidoTipoRespuestaCambioEstado;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoDocumentoPersonaNatural;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoEstadoVinculadoBeps;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionCambioEstado;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionListaCambioEstado;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoRespuestaCambioEstado;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoRespuestaListaCambioEstado;
import co.gov.colpensiones.beps.vinculacion.businesslogic.DARealizarCambioEstadoVinculado;

/**
 * <b>Descripción:</b> Clase encargada de la lógica de negocio para realizar cambios de estado vinculado. <br/>
 * 
 * <b>Caso de Uso:</b> GVI-VIN-3-FAB-13-RealizarCambioEstadoVinculado <br/>
 * @author Arnold Rios Delgado <arrios@heinsohn.com.co>
 */
public class BLRealizarCambioEstadoVinculado {

    /** Clase de acceso a datos. */
    private DARealizarCambioEstadoVinculado daCambioEstado = null;
    /** Atributo para tomar trazas de tiempo durante la ejecución del preproceso. */
    private TimeTracer tracer;

    /**
     * Constructor de la clase.
     * 
     * @param log
     *            log asociado a la funcionalidad.
     */
    public BLRealizarCambioEstadoVinculado(LoggerBeps log) {
        daCambioEstado = new DARealizarCambioEstadoVinculado();
        this.tracer = new TimeTracer(log, new HashMap<String, String>());
    }

    /**
     * Método encargado de cambiar el estado del vinculado según las transiciones permitidas.
     * 
     * @param informacionContexto
     *            Contiene los datos de contexto de la invocación del servicio web.
     * @param tipoInformacionTransicionEstado
     *            Información de entrada vinculados y/o estado detalle vinculado
     * @return respuesta del servicio
     */
    public TipoRespuestaListaCambioEstado cambiarEstado(TipoInformacionContextoRadicacion informacionContexto,
            TipoInformacionListaCambioEstado tipoInformacionListaCambioEstado) {

        HashMap<String, Object> payLoadTrace = new HashMap<String, Object>();
        tracer.inicio();

        payLoadTrace.put(ConstantesLogger.OBJETO_CONTEXTO_ENTRADA, informacionContexto);
        payLoadTrace.put(ConstantesLogger.OBJETO_NEGOCIO_ENTRADA, tipoInformacionListaCambioEstado);
        HashMap<String, String> metaData= tracer.getMetadata();
       
        if (informacionContexto != null) {
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_RADICADO, informacionContexto.getNumeroRadicacion());
        }
        tracer.getLogger().trace(payLoadTrace, metaData);
        
        TipoRespuestaListaCambioEstado respuesta = new TipoRespuestaListaCambioEstado();

        try {

            String errores=validarInformacionEntradaContexto(informacionContexto);
            if (errores.length() > 0) {
                respuesta.setEstadoEjecucion(respuestaNegocioServicio(Constantes.COD_FORMATO_INVALIDO_OBLIGATORIO_NO_RECIBIDO,
                		errores));
            } else {
                /* logica del servicio */
                respuesta = realizarCambioEstadoVinculado(informacionContexto, tipoInformacionListaCambioEstado);
            }

            if (Constantes.COD_FORMATO_INVALIDO_OBLIGATORIO_NO_RECIBIDO.equals(respuesta.getEstadoEjecucion().getCodigo())) {
                HashMap<String, Object> payLoad = new HashMap<String, Object>();
                payLoad.put(Constantes.MSJ_ERROR_LOG, respuesta.getEstadoEjecucion().getDescripcion());
                tracer.getLogger().error(payLoad, tracer.getMetadata());
            }
        } catch (Exception e) {
            respuesta.setEstadoEjecucion(respuestaNegocioServicio(Constantes.COD_ERROR_INTERNO, Constantes.DESC_ERROR_INTERNO));
            tracer.getLogger().error(e);
        } finally {
            payLoadTrace.put(ConstantesLogger.OBJETO_NEGOCIO_SALIDA, respuesta);
            tracer.getLogger().trace(payLoadTrace, metaData);
        }
        return respuesta;
    }

    /**
     * Valida la información de contexto
     * @param informacionContexto información de contexto
     * @return lista de errores 
     * @throws Exception error en la validación
     */
    private String validarInformacionEntradaContexto(TipoInformacionContextoRadicacion informacionContexto) throws Exception
    {
    	   StringBuffer lstErrores = new StringBuffer();
           /* obligatoriedad y definicion de datos de los campos Sistema Origen, Número de Radicación y Usuario del sistema origen */
           lstErrores.append(new Validador<TipoInformacionContextoRadicacion>().ValidarDataContract(informacionContexto));

           if (informacionContexto.getUsuarioSistema()!=null) {
               if(informacionContexto.getUsuarioSistema().length()>0 && informacionContexto.getUsuarioSistema().trim().length()==0){
        		   lstErrores.append("UsuarioOrigen no permite solo espacios\n");
        	   }
           }
    	return lstErrores.toString();
    	
    }
    /**
     * Método que contiene la lógica de consultar transiciones de estados permitidas
     * 
     * @param informacionContexto
     *            Contiene los datos de contexto de la invocación del servicio web.
     * @param tipoInformacionTransicionEstado
     *            Información de entrada vinculados y/o estado detalle vinculado
     * @return respuesta del servicio
     * @throws Exception
     *             propaga la excepción
     */
    private TipoRespuestaListaCambioEstado realizarCambioEstadoVinculado(TipoInformacionContextoRadicacion informacionContexto,
            TipoInformacionListaCambioEstado tipoInformacionListaCambioEstado) throws Exception {

        TipoRespuestaListaCambioEstado respuesta = new TipoRespuestaListaCambioEstado();

        HashMap<String, Object> payLoad = new HashMap<String, Object>();
        List<TipoInformacionCambioEstado> informacionEntradaCambioEstado = tipoInformacionListaCambioEstado.getDatosRecibidosCanal();
        if (informacionEntradaCambioEstado != null) {
            
            /* valida que la lista tenga menos de 50 registros */
            if (informacionEntradaCambioEstado.size() > Constantes.NUMERO_MAXIMO_PERMITIDO_REGISTROS) {
                respuesta.setEstadoEjecucion(respuestaNegocioServicio(Constantes.COD_ERROR_CANTIDAD_REGISTROS_SUPERA_MAXIMO_PERMITIDO,
                        Constantes.MSJ_ERROR_CANTIDAD_REGISTROS_SUPERA_MAXIMO_PERMITIDO));
                payLoad.put(Constantes.MSJ_ERROR_LOG, Constantes.MSJ_ERROR_CANTIDAD_REGISTROS_SUPERA_MAXIMO_PERMITIDO);
                tracer.getLogger().error(payLoad, new HashMap<String,String>());
            } else {
                List<ExtendidoTipoRespuestaCambioEstado> lista = new ArrayList<>();

                /* lista con la informacion en la base de datos para estado y detalle estado */
                String listaEstados = daCambioEstado.consultarEstadoPermitidos();
                String listaDetalleEstados = daCambioEstado.consultarDetalleEstadoPermitidos();

                StringBuffer listaNumeroDocumento = new StringBuffer();
                String errores = "";

                int actulizacionesExitosas = 0;
                for (TipoInformacionCambioEstado iter_entradaCambioEstado : informacionEntradaCambioEstado) {
                    
                    TipoEstadoVinculadoBeps estadoDetalleDestinoVinculado = iter_entradaCambioEstado.getTipoEstadoDestinoVinculado();
                    /* Reemplaza el valor null por vacío para realizar la comparación de detalle estado estados*/
                    if(estadoDetalleDestinoVinculado.getDetalleEstadoActualVinculado()==null){
                        estadoDetalleDestinoVinculado.setDetalleEstadoActualVinculado("");
                    }
                    ExtendidoTipoRespuestaCambioEstado extendidoTipoRespuestaCambioEstado = new ExtendidoTipoRespuestaCambioEstado(
                            iter_entradaCambioEstado.getVinculado(), estadoDetalleDestinoVinculado);
                    /*Registro en el log*/
                    payLoad.clear();
                    payLoad.put(Constantes.MSJ_LOG, "Validación vinculado");
                    payLoad.put(Constantes.CAMPO_TIPO_DOCUMENTO, iter_entradaCambioEstado.getVinculado().getTipoDocumento());
                    payLoad.put(Constantes.CAMPO_IDENTIFICACION, iter_entradaCambioEstado.getVinculado().getNumeroDocumento());
                    tracer.getLogger().info(payLoad,new HashMap<String,String>());
                    payLoad.remove(Constantes.MSJ_LOG);

                    errores = validarDatosEntradaListaVinculados(iter_entradaCambioEstado, listaEstados, listaDetalleEstados);
                    if (errores.length() > 0) {
                        extendidoTipoRespuestaCambioEstado.setResultadoCambioEstado(errores);
                    } else {
                        listaNumeroDocumento.append("'").append(iter_entradaCambioEstado.getVinculado().getNumeroDocumento()).append("',");
                        /* Vinculados con más de un cambio de estado */
                        if (lista.contains(extendidoTipoRespuestaCambioEstado)) {

                            int index = lista.indexOf(extendidoTipoRespuestaCambioEstado);
                            ExtendidoTipoRespuestaCambioEstado valorLista = lista.get(index);

                            if (valorLista.getTipoEstadoDestinoVinculado().getEstadoActualVinculado()
                                    .equals(extendidoTipoRespuestaCambioEstado.getTipoEstadoDestinoVinculado().getEstadoActualVinculado())
                                    && valorLista
                                            .getTipoEstadoDestinoVinculado()
                                            .getDetalleEstadoActualVinculado()
                                            .equals(extendidoTipoRespuestaCambioEstado.getTipoEstadoDestinoVinculado()
                                                    .getDetalleEstadoActualVinculado())) { /*Esta solicitud se encuentra duplicada para el
                                                                                           vinculado.*/
                                extendidoTipoRespuestaCambioEstado
                                        .setResultadoCambioEstado(Constantes.MSJ_ERROR_SOLICITUD_DUPLICADA_VINCULADO);
                            } else {
                                // El vinculado contiene más de una solicitud de cambio de estado.
                                // Y no realiza el cambio de estado.
                                lista.remove(index);
                                lista.add(valorLista);
                                valorLista.setResultadoCambioEstado(Constantes.MSJ_ERROR_VINCULADO_MAS_UNA_SOLICITUD);
                                extendidoTipoRespuestaCambioEstado.setResultadoCambioEstado(Constantes.MSJ_ERROR_VINCULADO_MAS_UNA_SOLICITUD);
                            }
                        }

                    }
                    /*registra en el log los diferentes errores*/
                    if(!"".equals(extendidoTipoRespuestaCambioEstado.getResultadoCambioEstado()))
                    {
                        payLoad.put(Constantes.MSJ_ERROR_LOG, extendidoTipoRespuestaCambioEstado.getResultadoCambioEstado());
                        tracer.getLogger().error(payLoad, new HashMap<String,String>());
                    }
                    
                    lista.add(extendidoTipoRespuestaCambioEstado);

                }
                if (listaNumeroDocumento.length() > 0) {
                    listaNumeroDocumento.deleteCharAt(listaNumeroDocumento.length() - 1);

                    /* Transiciones permitidas por base de datos */
                    List<TransicionesEstadoPermitidasDTO> transicionesPermitidas = daCambioEstado.transicionesEstadoPermitidas();

                    /* Vinculado con estado y detalle estado en la base de datos */
                    List<VinculadoEstadoDetalleDTO> vinculadoEstado = daCambioEstado.consultarEstadoDetalleVinculado(listaNumeroDocumento
                            .toString());

                    List<ExtendidoTipoRespuestaCambioEstado> listaFinal = new ArrayList<>();
                    for (ExtendidoTipoRespuestaCambioEstado iter_respuesta : lista) {
                        /*Registro en el log*/
                        payLoad.clear();
                        payLoad.put(Constantes.CAMPO_TIPO_DOCUMENTO, iter_respuesta.getVinculado().getTipoDocumento());
                        payLoad.put(Constantes.CAMPO_IDENTIFICACION, iter_respuesta.getVinculado().getNumeroDocumento());
                        
                        if ("".equals(iter_respuesta.getResultadoCambioEstado())) {
                            int contadorResultadoCambioEsado = 0;
                            TransicionesEstadoPermitidasDTO transicionPermitaVinculado = null;
                            
                            for (VinculadoEstadoDetalleDTO iter_vinculadoEstado : vinculadoEstado) {
                                /*recorre los vinculaods existentes*/
                                if (iter_respuesta.getVinculado().getNumeroDocumento().equals(iter_vinculadoEstado.getNumeroDocuemnto())
                                        && iter_respuesta.getVinculado().getTipoDocumento().equals(iter_vinculadoEstado.getTipoDocumento())) {

                                    contadorResultadoCambioEsado++;
                                    /*recorre las transiciones permitidas*/
                                    for (TransicionesEstadoPermitidasDTO iter_transicionesPermitidas : transicionesPermitidas) {
                                        if (iter_transicionesPermitidas.getEstadoInicial().equals(iter_vinculadoEstado.getEstadoActual())
                                                && iter_transicionesPermitidas.getDetalleEstadoInicial().equals(
                                                        iter_vinculadoEstado.getDetalleEstadoActual())
                                                && iter_transicionesPermitidas.getEstadoFinal().equals(
                                                        iter_respuesta.getTipoEstadoDestinoVinculado().getEstadoActualVinculado())
                                                && iter_transicionesPermitidas.getDetalleEstadoFinal().equals(
                                                        iter_respuesta.getTipoEstadoDestinoVinculado().getDetalleEstadoActualVinculado())) {
                                            contadorResultadoCambioEsado++;
                                            transicionPermitaVinculado = iter_transicionesPermitidas;
                                            break;
                                        }
                                    }
                                    break;
                                }

                            }
                            if (contadorResultadoCambioEsado == 0) {
                                iter_respuesta.setResultadoCambioEstado(Constantes.MSJ_ERROR_NO_EXISTE_VINCULADO);
                            } else if (contadorResultadoCambioEsado == 1) {
                                iter_respuesta
                                        .setResultadoCambioEstado(Constantes.MSJ_ERROR_NO_EXISTEN_TRANSICIONES_PERMITIDAS_ESTADO_DETALLE);
                            } else {
                                iter_respuesta.setResultadoCambioEstado( actualizarVinculadoSistemaBepsPlenitud(iter_respuesta.getVinculado().getNumeroDocumento(),
                                        transicionPermitaVinculado, informacionContexto));
                            }

                        }
                        /*registra en el log los diferentes errores*/
                        if(!Constantes.MSJ_CAMBIO_ESTADO_EXITOSO.equals(iter_respuesta.getResultadoCambioEstado()))
                        {
                            payLoad.put(Constantes.MSJ_ERROR_LOG, iter_respuesta.getResultadoCambioEstado());
                            tracer.getLogger().error(payLoad, new HashMap<String,String>());
                        }else{
                        	actulizacionesExitosas++;
                        }
                        listaFinal.add(iter_respuesta);

                    }

                    List<TipoRespuestaCambioEstado> response = (List<TipoRespuestaCambioEstado>) (List<?>) listaFinal;
                    respuesta.setRespuestaCambioEstado(response);

                }else
                {
                    List<TipoRespuestaCambioEstado> response = (List<TipoRespuestaCambioEstado>) (List<?>) lista;
                    respuesta.setRespuestaCambioEstado(response);
                }
                if (actulizacionesExitosas == 0) {
                    respuesta.setEstadoEjecucion(respuestaNegocioServicio(Constantes.COD_FIN_FALLIDO, Constantes.MSJ_FIN_FALLIDO));
                } else if (actulizacionesExitosas == respuesta.getRespuestaCambioEstado().size()) {
                    respuesta.setEstadoEjecucion(respuestaNegocioServicio(Constantes.COD_INVOCACION_EXITOSA, Constantes.MSJ_FIN_EXITO));
                } else {
                    /* Si no fueron realizadas exitosamente todas las consultas, se envía */
                    respuesta.setEstadoEjecucion(respuestaNegocioServicio(Constantes.COD_FIN_EXITO_ERRORES,
                            Constantes.MSJ_FIN_EXITO_ERRORES));
                }
            }

        }
        return respuesta;
    }

    /**
     * Método que invoca la lógica de base de datos para Plenitud y sistema gestión.
     * @param idVinculado numero de identificación del vinculado
     * @param transicionPermitaVinculado transición permitida
     * @param informacionContexto información de contexto
     * @return retorna el estado del cambio.
     */
    private String actualizarVinculadoSistemaBepsPlenitud(String idVinculado,
            TransicionesEstadoPermitidasDTO transicionPermitaVinculado, TipoInformacionContextoRadicacion informacionContexto) {
        String resultado = "";

        try {
            if (transicionPermitaVinculado.getCancelaCuenta() || transicionPermitaVinculado.getReactivaCuenta()) {

                if (transicionPermitaVinculado.getCancelaCuenta()) {
                    resultado = daCambioEstado.cambiarEstadoCuentaIndividual(idVinculado, "C", "P",
                            transicionPermitaVinculado, informacionContexto);
                } else {
                    resultado = daCambioEstado.cambiarEstadoCuentaIndividual(idVinculado, "A", "",
                            transicionPermitaVinculado, informacionContexto);
                }

                
            } else {

                resultado = daCambioEstado.realizarCambiosVinculadoSistemaBeps(idVinculado, transicionPermitaVinculado,
                        informacionContexto);

            }
            tracer.getLogger().info("numero documento: " + idVinculado + " resultado: " + resultado);

        } catch (Exception e) {
            resultado = Constantes.MSJ_ERROR_INTERNO_DURANTE_EJECUCION_CAMBIO_ESTADO;
            tracer.getLogger().error(e);
        } 

        return resultado;

    }

    /**
     * Valida la lista de información de vinculado y estado, recibidos como parámetros.
     * @param iter_entradaCambioEstado valor en la lista de entrada
     * @param listaEstados lista de estados permitidos
     * @param listaDetalleEstados lista de detalle estado permitidos
     * @return valida lista de errores
     * @throws Exception errores validado la información.
     */
    private String validarDatosEntradaListaVinculados(TipoInformacionCambioEstado iter_entradaCambioEstado, String listaEstados,
            String listaDetalleEstados) throws Exception {

        StringBuilder errores = new StringBuilder();

        TipoDocumentoPersonaNatural vinculado = iter_entradaCambioEstado.getVinculado();
        if (vinculado != null) {
            ArrayList<String> tiposDocumento = Util.getResourcePropertyArray(Constantes._HOMOLOGACION_COMUN_NAME,
                    Constantes.PREFIJO_LLAVES_TIPO_DOCUMENTO);
            if (vinculado.getTipoDocumento() == null || vinculado.getTipoDocumento().isEmpty()) {
                errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "tipoDocumento"));
            } else {
                if (!tiposDocumento.contains(vinculado.getTipoDocumento())) {
                    errores.append(Constantes.MSJ_ERROR_DATO_NO_VALIDO.replaceAll(Constantes.PARAMETRO0, "TipoDocumento")
                            + tiposDocumento.toString() + "\n");
                }
            }
            if (vinculado.getNumeroDocumento() == null || vinculado.getNumeroDocumento().isEmpty()) {
                errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "numeroDocumento"));
            } else {
                /* TipoDocumentoPersonaNatural */
                errores.append(new Validador<TipoDocumentoPersonaNatural>().ValidarDataContract(vinculado));
            }
        } else {
            errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "Vinculado"));
        }

        TipoEstadoVinculadoBeps estado = iter_entradaCambioEstado.getTipoEstadoDestinoVinculado();

        if (estado != null) {
            if (estado.getEstadoActualVinculado() == null || estado.getEstadoActualVinculado().isEmpty()) {
                errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "estadoDestino"));
            } else {
                if (!listaDetalleEstados.contains(estado.getEstadoActualVinculado())) {
                    errores.append("EstadoDestino no pertenece al dominio parametrizados en el Sistema de Gestión Beps.\n");
                }
            }

            if (estado.getDetalleEstadoActualVinculado() != null && !estado.getDetalleEstadoActualVinculado().isEmpty()) {
                if (!listaDetalleEstados.contains(estado.getDetalleEstadoActualVinculado())) {
                    errores.append("DetalleEstadoDestino no pertenece al dominio parametrizados en el Sistema de Gestión Beps.\n");
                }
            }
        } else {
            errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "TipoEstadoDestinoVinculado"));
        }

        return errores.toString();
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
