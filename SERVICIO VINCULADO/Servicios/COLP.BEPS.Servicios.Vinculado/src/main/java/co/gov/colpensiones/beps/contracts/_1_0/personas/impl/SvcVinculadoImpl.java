package co.gov.colpensiones.beps.contracts._1_0.personas.impl;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import co.gov.colpensiones.beps.comunes.utilidades.ConstantesLoggerServicios;
import co.gov.colpensiones.beps.contracts._1_0.personas.SvcVinculado;
import co.gov.colpensiones.beps.log.LoggerBeps;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstadoEjecucion;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContexto;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContextoExterno;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContextoRadicacion;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoDocumentoPersonaNatural;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionBasicaSolicitante;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionDatosGeneralesVinculado;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionGeneralVinculado;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionListaCambioEstado;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionTransicionEstado;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoRespuestaInformacionSolicitanteDTO;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoRespuestaListaCambioEstado;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoRespuestaTransicionEstado;
import co.gov.colpensiones.beps.vinculacion.businesslogic.actualizar.BLActualizarInformacionNoSensible;
import co.gov.colpensiones.beps.vinculacion.businesslogic.actualizar.BLActualizarInformacionSensible;
import co.gov.colpensiones.beps.vinculacion.businesslogic.consultar.BLConsultar;
import co.gov.colpensiones.beps.vinculacion.businesslogic.consultar.BLConsultarSaldosMovimientos;
import co.gov.colpensiones.beps.vinculacion.businesslogic.consultar.BLConsultarTransicionesEstadoPermitidas;
import co.gov.colpensiones.beps.vinculacion.businesslogic.consultar.BLRealizarCambioEstadoRetiro;
import co.gov.colpensiones.beps.vinculacion.businesslogic.consultar.BLRealizarCambioEstadoVinculado;
import co.gov.colpensiones.beps.vinculacion.businesslogic.crear.BLCrear;

@Stateless
@WebService(name = "ContratoSvcVinculado", endpointInterface = "co.gov.colpensiones.beps.contracts._1_0.personas.SvcVinculado", targetNamespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas")
public class SvcVinculadoImpl implements SvcVinculado {

    /**
     * Método encargado de realizar la modificación de la información básica sensible del vinculado
     * 
     * Caso de Uso: GOP-VIN-1-FAB-11-ModificarInformacionSensibleVinculado
     * 
     * @param informacionContexto
     *            estructura para el envio de los datos de contexto de la invocacion del servicio web
     * @param informacionVinculado
     *            datos basicos del vinculado
     * @return estado de la ejecución
     */
    public TipoEstadoEjecucion actualizarDatosBasicos(TipoInformacionContexto informacionContexto,
            TipoInformacionBasicaSolicitante informacionVinculado) {
        BLActualizarInformacionSensible blVinculado = new BLActualizarInformacionSensible(new LoggerBeps(
                ConstantesLoggerServicios.SERVICIO_VINCULADO_ACTUALIZAR_INFO_SENSIBLE, informacionContexto.getSistemaOrigen()));
        return blVinculado.actualizarDatosBasicos(informacionContexto, informacionVinculado);
    }

    /**
     * Método encargado de realizar la consulta de viabilidad de vinculación
     * 
     * Caso de Uso: GVI-VIN-1-FAB-09-ConsultarViabilidadVinculacion
     * 
     * @param informacionContexto
     *            estructura para el envio de los datos de contexto de la invocacion del servicio web
     * @param identificacion
     *            tipo y número de documento del solicitante
     * @return información solicitante
     */
    public TipoRespuestaInformacionSolicitanteDTO consultar(TipoInformacionContexto informacionContexto,
            TipoDocumentoPersonaNatural identificacion) {
        BLConsultar blVinculado = new BLConsultar(new LoggerBeps(ConstantesLoggerServicios.SERVICIO_VINCULADO_CONSULTAR,
                informacionContexto.getSistemaOrigen()));
        return blVinculado.consultar(informacionContexto,identificacion);
    }

    /**
     * Método encargado de crear un vinculado
     * 
     * Caso de uso:GVI-VIN-1-FAB-02-RegistrarVinculado
     * 
     * @param informacionContexto
     *            estructura para el envio de los datos de contexto de la invocacion del servicio web
     * @param informacionVinculado
     *            , información realaciona a un Vinculado
     * @return estado de la ejecución
     */
    public TipoEstadoEjecucion crear(TipoInformacionContexto informacionContexto, TipoInformacionGeneralVinculado informacionVinculado) {
        BLCrear blCrear = new BLCrear(new LoggerBeps(ConstantesLoggerServicios.SERVICIO_VINCULADO_CREAR,
                informacionContexto.getSistemaOrigen()));
        return blCrear.crearvinculado(informacionContexto, informacionVinculado);
    }

    /**
     * Método encargado de realizar la modificación de la información no sensible del vinculado * Caso de Uso:
     * GVI-VIN-1-FAB-13-ModificarInformacionNoSensibleVinculado
     * 
     * @param informacionContexto
     *            datos del contexto
     * @param informacionGeneralVinculado
     *            datos generales del vinculado
     * @return estado de la ejecución
     */
    public TipoEstadoEjecucion actualizarDatosGenerales(TipoInformacionContexto informacionContexto,
            TipoInformacionDatosGeneralesVinculado informacionGeneralVinculado) {
        BLActualizarInformacionNoSensible blVinculado = new BLActualizarInformacionNoSensible(new LoggerBeps(
                ConstantesLoggerServicios.SERVICIO_VINCULADO_ACTUALIZAR_INFO_NO_SENSIBLE, informacionContexto.getSistemaOrigen()), null);
        return blVinculado.actualizarDatosBasicos(informacionContexto, informacionGeneralVinculado);
    }

    /**
     * Método encargado de realizar la consulta de trasacciones/movimientos y saldos de la cuenta individual del vinculado. Caso de Uso:
     * GSC-PLA-2-FAB-26-ConsultarMovimientosSaldoCtaIndividual
     * 
     * @param informacionContexto
     *            Datos del Contexto.
     * @param filtrosConsulta
     *            Datos Generales del vinculado.
     * @return <TipoRespuestaConsultaMovimientosSaldoDTO> Movimientos y saldos para la consulta realizada.
     */
    public co.gov.colpensiones.beps.schemas._1_0.personas.TipoRespuestaConsultaMovimientosSaldoDTO consultarSaldosMovimientos(
            co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContexto informacionContexto,
            co.gov.colpensiones.beps.schemas._1_0.personas.TipoFiltrosConsulta filtrosConsulta) {
        BLConsultarSaldosMovimientos blConsultaSaldos = new BLConsultarSaldosMovimientos(new LoggerBeps(
                ConstantesLoggerServicios.SERVICIO_VINCULADO_CONSULTAR_SALDOS_MOVIMIENTOS, informacionContexto.getSistemaOrigen()));
        return blConsultaSaldos.consultaSaldosMovimientos(informacionContexto, filtrosConsulta);

    }

    /**
     * Método que permite consultar los estados a los que podría cambiar un vinculado dependiendo de su estado actual y de las transiciones de estado permitidas que se encuentren parametrizadas en el Sistema de Gestión BEPS.
     * Caso de Uso: GVI-VIN-3-FAB-12-ConsultarTransicionesDeEstadoPermitidas
     * @param informacionContexto Datos del Contexto.
     * @param tipoInformacionTransicionEstado  Datos de Entrada Consultar Transiciones de Estado Permitidas
     * @return <TipoRespuestaTransicionEstado> Respuesta Servicio Web 
     */
    public TipoRespuestaTransicionEstado consultarTransicionEstado(TipoInformacionContextoExterno informacionContexto,
            TipoInformacionTransicionEstado tipoInformacionTransicionEstado) {
        BLConsultarTransicionesEstadoPermitidas blConsultarTransicionesEstadoPermitidas=new BLConsultarTransicionesEstadoPermitidas(new LoggerBeps(
                ConstantesLoggerServicios.SERVICIO_VINCULADO_CONSULTAR_TRANSICION_ESTADO, informacionContexto.getSistemaOrigen()));
        return blConsultarTransicionesEstadoPermitidas.consultarTransicionEstado(informacionContexto, tipoInformacionTransicionEstado);
    }

    /**
     * Método que realizar el cambio de estado en el Sistema de Gestión BEPS para uno o más vinculados, de acuerdo con las transiciones permitidas que se encuentren parametrizadas.
     * <b>Caso de Uso:</b> GVI-VIN-3-FAB-13-RealizarCambioEstadoVinculado <br/>
     * @param informacionContexto Datos del Contexto
     * @param tipoInformacionListaCambioEstado Datos de Entrada realizxar cambio de estados vinculado
     * @return <TipoRespuestaListaCambioEstado> Respuesta Servicio Web
     */
    public TipoRespuestaListaCambioEstado cambiarEstado(TipoInformacionContextoRadicacion informacionContexto,
            TipoInformacionListaCambioEstado tipoInformacionListaCambioEstado) {
        BLRealizarCambioEstadoVinculado blRealizarCambioEstadoVinculado=new BLRealizarCambioEstadoVinculado(new LoggerBeps(
                ConstantesLoggerServicios.SERVICIO_VINCULADO_REALIZAR_CAMBIO_ESTADO_VINCULADO, informacionContexto.getSistemaOrigen()));
        return blRealizarCambioEstadoVinculado.cambiarEstado(informacionContexto, tipoInformacionListaCambioEstado);
    }

    /**
     * Método que realizar el cambio de estado en el Sistema de Gestión BEPS y en plenitud para uno o más vinculados, de acuerdo con las transiciones permitidas que se 
     * encuentren parametrizadas en las nuevas parametricas.
     * @param informacionContexto Datos del Contexto
     * @param tipoInformacionListaCambioEstado Datos de Entrada realizxar cambio de estados vinculado
     * @return <TipoRespuestaListaCambioEstado> Respuesta Servicio Web
     */
    public TipoRespuestaListaCambioEstado cambiarEstadoRetiro(TipoInformacionContextoRadicacion informacionContexto,
            TipoInformacionListaCambioEstado tipoInformacionListaCambioEstado) {
        BLRealizarCambioEstadoRetiro blRealizarCambioEstadoRetiro=new BLRealizarCambioEstadoRetiro(new LoggerBeps(
                ConstantesLoggerServicios.SERVICIO_VINCULADO_REALIZAR_CAMBIO_ESTADO_RETIRO, informacionContexto.getSistemaOrigen()));
        return blRealizarCambioEstadoRetiro.cambiarEstado(informacionContexto, tipoInformacionListaCambioEstado);
    }

}