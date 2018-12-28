package co.gov.colpensiones.beps.vinculacion.businesslogic.consultar;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import co.gov.colpensiones.beps.comunes.enumeraciones.TipoConexionBaseDatosEnum;
import co.gov.colpensiones.beps.comunes.utilidades.Constantes;
import co.gov.colpensiones.beps.comunes.utilidades.ConstantesLoggerServicios;
import co.gov.colpensiones.beps.comunes.utilidades.DatabaseManager;
import co.gov.colpensiones.beps.comunes.utilidades.Util;
import co.gov.colpensiones.beps.comunes.utilidades.Validador;
import co.gov.colpensiones.beps.dal.utilidades.DataRow;
import co.gov.colpensiones.beps.dal.utilidades.DataStoredProcedure;
import co.gov.colpensiones.beps.dal.utilidades.DataTable;
import co.gov.colpensiones.beps.dal.utilidades.DbParameter;
import co.gov.colpensiones.beps.excepciones.DataAccessException;
import co.gov.colpensiones.beps.excepciones.LogicalException;
import co.gov.colpensiones.beps.log.ConstantesLogger;
import co.gov.colpensiones.beps.log.LoggerBeps;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstadoEjecucion;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContexto;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoConsultaMovimientosSaldo;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoFiltrosConsulta;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionAportesPesos;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionAportesUnidades;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionCuenta;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionMovimiento;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionTransaccionMovimientos;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionTransacciones;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionVinculado;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoRespuestaConsultaMovimientosSaldoDTO;
import co.gov.colpensiones.beps.vinculacion.businesslogic.BLVinculado;
import co.gov.colpensiones.beps.vinculacion.businesslogic.DAVinculado;

/**
 * <b>Descripcion:</b> Clase encargada de la logica de negocio para la consulta de Movimientos y saldos de la cuenta individual.<br/>
 * <b>Caso de Uso:</b> GSC-PLA-2-FAB-26-ConsultarMovimientosSaldoCtaIndividual <br/>
 * 
 * @author csalazar <csalazar@heinsohn.com.co>
 */
public class BLConsultarSaldosMovimientos extends BLVinculado {
   
    /** Clase de accedo a datos. */
    DAVinculado daVinculado = null;

    /** Fechas utilizadas en el filtro de consulta. */
    private Date fechaInicial = null, fechaFinal = null;

    /**
     * Constructor de la clase.
     * 
     * @param log Log con el que se va a escribir de la BD.
     */
    public BLConsultarSaldosMovimientos(LoggerBeps log) {
        super(log);
        daVinculado = new DAVinculado();
    }

    /**
     * Método encargado de realizar la consulta de trasacciones/movimientos y saldos de la cuenta individual del vinculado.
     * 
     * @param informacionContexto Datos del Contexto.
     * @param filtrosConsulta Datos Generales del vinculado.
     * @return Movimientos y saldos para la consulta realizada.
     */
    public TipoRespuestaConsultaMovimientosSaldoDTO consultaSaldosMovimientos(TipoInformacionContexto informacionContexto,
            TipoFiltrosConsulta filtrosConsulta) {

        TipoRespuestaConsultaMovimientosSaldoDTO response = new TipoRespuestaConsultaMovimientosSaldoDTO();

        // Manejo de Log
        HashMap<String, Object> payLoadTrace = new HashMap<String, Object>();
        HashMap<String, String> metaData = new HashMap<String, String>();
        HashMap<String, Object> payLoad = new HashMap<String, Object>();

        metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, filtrosConsulta.getIdentificacion().getTipoDocumento());
        metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, filtrosConsulta.getIdentificacion().getNumeroDocumento());
        if (informacionContexto != null) {
	        metaData.put(ConstantesLoggerServicios.USUARIO_SISTEMA_EXTERNO, informacionContexto.getUsuarioSistemaExterno());
        }
        payLoad.putAll(metaData);

        try {
            // registro en el log
        	payLoadTrace.put(ConstantesLogger.OBJETO_CONTEXTO_ENTRADA, filtrosConsulta);
            payLoadTrace.put(ConstantesLogger.OBJETO_NEGOCIO_ENTRADA, filtrosConsulta);
            log.trace(payLoadTrace, metaData);

            // validar datos de entrada
            StringBuffer lstErrores = new StringBuffer();
            lstErrores.append(new Validador<TipoInformacionContexto>().ValidarDataContract(informacionContexto));
            lstErrores.append(new Validador<TipoFiltrosConsulta>().ValidarDataContract(filtrosConsulta));
            lstErrores.append(this.validarDatosEntrada(informacionContexto, filtrosConsulta));

            // validar existencia errores
            if (lstErrores.toString().length() > 0) {
                payLoad.put(Constantes.MSJ_ERROR_LOG, lstErrores.toString());
                log.info(payLoad, metaData);
                response.setEstadoEjecucion(respuestaNegocioServicio(Constantes.COD_FORMATO_INVALIDO_OBLIGATORIO_NO_RECIBIDO,
                        lstErrores.toString()));
            } else {
                // Se ejecuta la lógica de negocio
                response = consultarSaldos(informacionContexto, filtrosConsulta);
            }
        } catch (DataAccessException e2) {
            generarLogError(metaData, true, e2);
            response.setEstadoEjecucion(respuestaErrorTecnicoServicio());
        } catch (LogicalException e1) {
            generarLogError(metaData, false, e1);
            response.setEstadoEjecucion(respuestaErrorTecnicoServicio());
        } catch (Exception e) {
            generarLogError(metaData, false, e);
            response.setEstadoEjecucion(respuestaErrorTecnicoServicio());
        } finally {
            payLoadTrace.put(ConstantesLogger.OBJETO_NEGOCIO_SALIDA, response);
            log.trace(payLoadTrace, metaData);
        }
        return response;

    }

    /**
     * Método que realiza las validaciones de negocio definidas para los parámetros de entrada.
     * 
     * @param informacionContexto datos encabezado
     * @param filtrosConsulta datos basicos del vinculado
     * @return cadena con los errores generados luego de las validaciones
     * @throws Exception Excepción generada en la validación de los datos de entrada.
     */
    private String validarDatosEntrada(TipoInformacionContexto informacionContexto, TipoFiltrosConsulta filtrosConsulta) throws Exception {

        StringBuffer errores = new StringBuffer();
	    if (informacionContexto != null) {
	        /* Validar datos encabezado */
	        if (informacionContexto.getUsuarioSistemaExterno() == null) {
	            errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replaceAll(Constantes.PARAMETRO0, "usuarioSistemaExterno"));
	        } else {
	            if (!Pattern.compile(Constantes.ER_VALIDAR_USUARIO_SISTEMA).matcher(informacionContexto.getUsuarioSistemaExterno()).matches()) {
	                errores.append(Constantes.MSJ_ESTRUCTURA_USUARIO_INVALIDA.replaceAll(Constantes.PARAMETRO0, "UsuarioSistemaExterno"));
	            }
	        }
        }

        /* Validacion tipo de documento */
        ArrayList<String> tiposDocumento = daVinculado.consultarTiposIdentificacion();

        if (!tiposDocumento.contains(filtrosConsulta.getIdentificacion().getTipoDocumento())) {
            errores.append(Constantes.MSJ_ERROR_DATO_NO_VALIDO.replaceAll(Constantes.PARAMETRO0, "TipoDocumento")
                    + tiposDocumento.toString() + "\n");
        }

        String fechaInicioPrograma = daVinculado.consultarParametroVinculacion(Constantes.FECHA_INICIO_PROGRAMA_BEPS);

        Date fechaInicioP = null;

        if (fechaInicioPrograma != null) {
            fechaInicioP = Util.obtenerFechaString(fechaInicioPrograma);
        } else {
            errores.append("No se encuentra parametrizada la fecha de inicio del programa Beps.\n");
        }

        /* F.B. Paso 1 Validación de obligatoriedad para los campos de fecha inicial y fecha final. */
        if (filtrosConsulta.getFechaInicial() != null) {
            if (filtrosConsulta.getFechaFinal() == null) {
                errores.append("La Fecha Final es Obligatoria al existir la Fecha Inicial.\n");
            }
            fechaInicial = Util.obtenerFechaString(filtrosConsulta.getFechaInicial());
        }

        /* F.B. Paso 1 Validación de obligatoriedad para los campos de fecha inicial y fecha final. */
        if (filtrosConsulta.getFechaFinal() != null) {
            if (filtrosConsulta.getFechaInicial() == null) {
                errores.append("La Fecha inicial es Obligatoria al existir la Fecha Final.\n");
            }
            fechaFinal = Util.obtenerFechaString(filtrosConsulta.getFechaFinal());
        }

        /* Validación entre las fechas de consulta, deben cumplir con las restricciones del campo. */
        if (fechaInicial != null && fechaFinal != null) {

            if (fechaFinal.before(fechaInicial)) {
                errores.append("La fecha final no puede ser menor a la Fecha Inicial. \n");
            }
            if (fechaInicial.after(fechaFinal)) {
                errores.append("La fecha Inicial no puede ser superior a la Fecha Final.\n");
            }
        }

        /* Validación de la fecha inicial no debe ser anterior a la fecha de inicio del programa. */
        if (fechaInicial != null) {
            if (fechaInicial.before(fechaInicioP)) {
                errores.append("La fecha Inicial no puede ser inferior a la fecha de inicio del programa Beps: " + fechaInicioPrograma
                        + "\n.");
            }
        }
        return errores.toString();
    }

    /**
     * Método que realiza la consulta de saldos y movimientos para un vinculado.
     * @param informacionContexto información de contexto
     * @param filtrosConsulta Contiene los filtros para realizar la consulta
     * @return <TipoRespuestaConsultaMovimientosSaldoDTO> respuesta a la consulta del servicio.
     * @throws DataAccessException Excepción generada por la consulta de datos.
     * @throws LogicalException 
     */
    private TipoRespuestaConsultaMovimientosSaldoDTO consultarSaldos(TipoInformacionContexto informacionContexto,
            TipoFiltrosConsulta filtrosConsulta) throws DataAccessException, LogicalException {

        TipoRespuestaConsultaMovimientosSaldoDTO response = new TipoRespuestaConsultaMovimientosSaldoDTO();

        try {

            /* F.B. Paso 3 opción A. */
            if (fechaInicial == null && fechaFinal == null) {

                fechaFinal = new Date();

                Calendar fechaAnteriorAnio = Calendar.getInstance();
                fechaAnteriorAnio.add(Calendar.YEAR, -1);

                fechaInicial = fechaAnteriorAnio.getTime();
                
                DateFormat df = new SimpleDateFormat("yyyyMMdd");
                
                filtrosConsulta.setFechaInicial(df.format(fechaInicial));
                filtrosConsulta.setFechaFinal(df.format(fechaFinal));
                
            }
            /* F.B. Paso 3 opción B. */
            else if (fechaInicial != null && fechaFinal != null) {

                // calcular la diferencia en milisengundos
                Long diff = fechaFinal.getTime() - fechaInicial.getTime();
                
                //calcular la diferencia en dias
                Float diffDay = diff / (Constantes.CONSTANTE_HORA * Constantes.CONSTANTE_MINUTOS_SEGUNDOS * Constantes.CONSTANTE_MINUTOS_SEGUNDOS * Constantes.CONSTANTE_MILISEGUNDOS);
                //Calcular la diferencia en años. 
                Float diffYear = diffDay / Constantes.CONSTANTE_DIAS;

                /* F.A. 8.1 Rango de Fechas no valido para consulta */
                if (diffYear > 1l) {
                    TipoEstadoEjecucion estado = new TipoEstadoEjecucion();
                    estado.setCodigo("07");
                    estado.setDescripcion("El rango de fechas recibido no es válido para ejecutar la consulta.\n");
                    response.setEstadoEjecucion(estado);
                    return response;
                }
            }

            /* F.B. Paso 4 */
            DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);

            String idVinculado = daVinculado.consultarExistenciaVinculado(database, filtrosConsulta.getIdentificacion());

            /* F.A. 8.2 No existe vinculado para consulta */
            if (idVinculado == null) {
                idVinculado = daVinculado.consultarVinculadoNumIdentificacion(database, filtrosConsulta.getIdentificacion());
                if (idVinculado != null) {
                    TipoEstadoEjecucion estado = new TipoEstadoEjecucion();
                    estado.setCodigo("04");
                    estado.setDescripcion("Tipo documento no válido para número de documento.\n");
                    response.setEstadoEjecucion(estado);
                    return response;
                } else {
                    TipoEstadoEjecucion estado = new TipoEstadoEjecucion();
                    estado.setCodigo("04");
                    estado.setDescripcion("No existe vinculado.\n");
                    response.setEstadoEjecucion(estado);
                    return response;
                }
            }
            
            TipoInformacionVinculado vinculado = daVinculado.consultarInformacionVinculado(database, filtrosConsulta.getIdentificacion());
            
            Calendar fechaInicio = Calendar.getInstance();
            
            /* F.B. Paso 5 */
            DataStoredProcedure resultado = daVinculado.consultarInformacionCuentaIndividual(filtrosConsulta);
            
            Calendar fechaFin = Calendar.getInstance();
            Float tiempoEjecucion = (fechaFin.getTimeInMillis() - fechaInicio.getTimeInMillis()) / Constantes.CONSTANTE_MILISEGUNDOS;

            HashMap<String, String> metaData = new HashMap<String, String>();
            HashMap<String, Object> payLoad = new HashMap<String, Object>();

            // guardar en el log la respuesta de plentitud
            metaData.put(ConstantesLoggerServicios.METADATA_TIEMPO_LLAMADO_PLENITUD, tiempoEjecucion.toString());
            metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, filtrosConsulta.getIdentificacion().getTipoDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, filtrosConsulta.getIdentificacion().getNumeroDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_FECHA_INICIAL, filtrosConsulta.getFechaInicial());
            metaData.put(ConstantesLoggerServicios.METADATA_FECHA_FINAL, filtrosConsulta.getFechaFinal());
            
            payLoad.put(ConstantesLoggerServicios.METADATA_TIEMPO_LLAMADO_PLENITUD, tiempoEjecucion.toString());
            
            TipoConsultaMovimientosSaldo detalle = new TipoConsultaMovimientosSaldo();
            
            log.info(payLoad, metaData);
            
            /*Verificación de resultado de invocación a plenitud*/
            if (resultado.getParametrosSalida() != null && resultado.getParametrosSalida().size() > 0) {
                
                DbParameter parametroSalidaCodigo = resultado.getParametrosSalida().get(21);
                DbParameter parametroSalidaMensaje = resultado.getParametrosSalida().get(22);
                
                if (Constantes.COD_ERROR_CONSULTA_SALDOS_.contains(parametroSalidaCodigo.getParameterValue().toString())) {
                	String mensajeError = ("Error en la ejecución del SP SP_Consulta_Saldos_Tipo_Aporte o SP_Estado_Cuenta. Código"
                            + parametroSalidaCodigo.getParameterValue()  +" Mensaje:" + parametroSalidaMensaje.getParameterValue());
                    payLoad.put("Respuesta Plenitud", mensajeError);
                    log.error(payLoad, metaData);
                    TipoEstadoEjecucion estado = new TipoEstadoEjecucion();
                    estado.setCodigo("03");
                    estado.setDescripcion(parametroSalidaMensaje.getParameterValue()+"\n");
                    response.setEstadoEjecucion(estado);
                    return response;
                }
                else{
                    detalle = procesarInformacionCuenta(resultado, filtrosConsulta);
                }
            }
            else{
                payLoad.put(ConstantesLoggerServicios.MENSAJE_ERROR, Constantes.RESPUESTA_FALLIDA_INVOCACION_PLENITUD);
                log.error(payLoad, metaData);
                TipoEstadoEjecucion estado = new TipoEstadoEjecucion();
                estado.setCodigo("03");
                estado.setDescripcion(Constantes.RESPUESTA_FALLIDA_INVOCACION_PLENITUD+"\n");
                response.setEstadoEjecucion(estado);
                return response;
            }
            
            detalle.setInformacionVinculado(vinculado);

            response.setDetalle(detalle);
            response.setEstadoEjecucion(respuestaExitosaServicio());

        } catch (DataAccessException e) {
            throw e;
        } catch (LogicalException e) {
            throw e;
        }
        return response;
    }

    /**
     * Método que permite realizar el procesamiento de la información consultada en plenitud.
     * @param resultado Datos obtenidos como resultado de consultar en plenitud la información de cuenta.
     * @param filtrosConsulta Filtros utilizados en la consulta a plenitud.
     * @return <TipoConsultaMovimientosSaldo> Contiene la información de cuenta, saldos y movimientos.
     * @throws LogicalException Excepción generada al procesar la información.
     */
    public TipoConsultaMovimientosSaldo procesarInformacionCuenta(DataStoredProcedure resultado, TipoFiltrosConsulta filtrosConsulta) throws LogicalException {

        /* Se obtienen los parametros de salida */
        List<DbParameter> parametrosSalida = resultado.getParametrosSalida();

        TipoConsultaMovimientosSaldo detalle = new TipoConsultaMovimientosSaldo();
        TipoInformacionCuenta informacionCuenta = new TipoInformacionCuenta();
        TipoInformacionTransacciones informacionMovimientos = new TipoInformacionTransacciones();
        TipoInformacionAportesPesos informacionAportesPesos = new TipoInformacionAportesPesos();
        TipoInformacionAportesUnidades informacionAportesUnidades = new TipoInformacionAportesUnidades();

        try {
            
            if (parametrosSalida.size() > 0) {

                informacionCuenta.setNumero(parametrosSalida.get(0).getParameterValue().toString());
                informacionCuenta.setEstado(parametrosSalida.get(1).getParameterValue().toString());
                informacionCuenta.setFechaApertura(parametrosSalida.get(2).getParameterValue().toString());
                informacionCuenta.setFechaCierre(parametrosSalida.get(3).getParameterValue().toString());
                informacionCuenta.setMotivoCierreCancelacion(parametrosSalida.get(4).getParameterValue().toString());
                informacionCuenta.setValorTasaEfectivaAnual((BigDecimal) parametrosSalida.get(5).getParameterValue());
                informacionCuenta.setFechaInicial(filtrosConsulta.getFechaInicial());
                informacionCuenta.setFechaFinal(filtrosConsulta.getFechaFinal());
                detalle.setInformacionCuenta(informacionCuenta);

                informacionAportesPesos.setSaldoAcumuladoFechaInicioPeriodo((BigDecimal) parametrosSalida.get(6).getParameterValue());
                informacionAportesPesos.setAportesPeriodo((BigDecimal) parametrosSalida.get(7).getParameterValue());
                informacionAportesPesos.setTotalAportesRealizados((BigDecimal) parametrosSalida.get(8).getParameterValue());
                informacionAportesPesos.setSaldoAcumuladoInteresesFechaInicioPeriodo((BigDecimal) parametrosSalida.get(9).getParameterValue());
                informacionAportesPesos.setInteresesPeriodo((BigDecimal) parametrosSalida.get(10).getParameterValue());
                informacionAportesPesos.setTotalInteresesRecibidosCuenta((BigDecimal) parametrosSalida.get(11).getParameterValue());
                informacionAportesPesos.setAdministracionCuenta((BigDecimal) parametrosSalida.get(12).getParameterValue());
                informacionAportesPesos.setGravamenFinanciero((BigDecimal) parametrosSalida.get(13).getParameterValue());
                informacionAportesPesos.setTotalRetiros((BigDecimal) parametrosSalida.get(14).getParameterValue());
                informacionAportesPesos.setSaldoFinal((BigDecimal) parametrosSalida.get(15).getParameterValue());
                informacionAportesPesos.setTotalIncentivoPeriodicoAportes((BigDecimal) parametrosSalida.get(16).getParameterValue());
                detalle.setInformacionAportesPesos(informacionAportesPesos);

                informacionAportesUnidades.setNumeroUnidadesFechaInicioPeriodo((BigDecimal) parametrosSalida.get(17).getParameterValue());
                informacionAportesUnidades.setValorInicialUnidadPeriodo((BigDecimal) parametrosSalida.get(18).getParameterValue());
                informacionAportesUnidades.setTotalUnidadesAportesRealizados((BigDecimal) parametrosSalida.get(19).getParameterValue());
                informacionAportesUnidades.setValorFinalUnidad((BigDecimal) parametrosSalida.get(20).getParameterValue());
                detalle.setInformacionAportesUnidades(informacionAportesUnidades);
            }

            if (resultado.getResultSetList().size() > 0) {

                for (int i = 0; i < resultado.getResultSetList().size(); i++) {

                    DataTable resultSet = resultado.getResultSetList().get(i);

                    TipoInformacionTransaccionMovimientos informacionTransaccion = new TipoInformacionTransaccionMovimientos();

                    ArrayList<TipoInformacionMovimiento> movimientos = new ArrayList<TipoInformacionMovimiento>();
                    
                    BigDecimal totalAportes = BigDecimal.ZERO;
                    
                    for (DataRow fila : resultSet.getRows()) {

                        TipoInformacionMovimiento movimiento = new TipoInformacionMovimiento();

                        movimiento.setFechaConsignacion(fila.getValue(0).toString());
                        movimiento.setDescripcionMovimiento(fila.getValue(1).toString());
                        movimiento.setValorAporte((BigDecimal) fila.getValue(2));
                        movimiento.setTerminalPago(fila.getValue(3).toString());
                        movimiento.setFechaAcreditacion(fila.getValue(4).toString());
                        movimiento.setValorAplicadoVigenciasfuturas((BigDecimal) fila.getValue(5));
                        
                        totalAportes = totalAportes.add(movimiento.getValorAporte());
                        
                        movimientos.add(movimiento);
                    }
                    
                    informacionTransaccion.setMovimiento(movimientos);
                    informacionTransaccion.setValorTotalMovimientos(totalAportes);
                    
                    switch (i) {
                        case 0:
                            informacionMovimientos.setMovimientosAhorrosPropios(informacionTransaccion);
                            break;
                        case 1:
                            informacionMovimientos.setMovimientosPatrocinadores(informacionTransaccion);
                            break;
                        case 2:
                            informacionMovimientos.setMovimientosTrasladosSGP(informacionTransaccion);
                            break;
                        case 3:
                            informacionMovimientos.setMovimientosRetiros(informacionTransaccion);
                            break;
                    }
                }
            }

            detalle.setInformacionCuenta(informacionCuenta);
            detalle.setInformacionAportesPesos(informacionAportesPesos);
            detalle.setInformacionAportesUnidades(informacionAportesUnidades);
            detalle.setInformacionMovimientos(informacionMovimientos);

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            HashMap<String, Object> payLoad = new HashMap<String, Object>();

            metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, filtrosConsulta.getIdentificacion().getTipoDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, filtrosConsulta.getIdentificacion().getNumeroDocumento());
            payLoad.put(Constantes.MSJ_ERROR_LOG, e.toString());
            throw new LogicalException(payLoad, metaData, e);
        }

        return detalle;
    }

}
