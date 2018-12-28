package co.gov.colpensiones.beps.contracts._1_0.personas;

import javax.ejb.Local;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;

import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstadoEjecucion;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContexto;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContextoExterno;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContextoRadicacion;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoDocumentoPersonaNatural;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionBasicaSolicitante;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionDatosGeneralesVinculado;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionListaCambioEstado;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionTransicionEstado;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoRespuestaInformacionSolicitanteDTO;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoRespuestaListaCambioEstado;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoRespuestaTransicionEstado;

/**
 * Descripción: Clase que representa los operaciones expuestas por el servicio de vinculado.
 */
@Local
@WebService(targetNamespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas", name = "ContratoSvcVinculado")
@XmlSeeAlso({co.gov.colpensiones.beps.schemas._1_0.comun.ObjectFactory.class, ObjectFactory.class, co.gov.colpensiones.beps.schemas._1_0.personas.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface SvcVinculado {

    /**
     * Método encargado de realizar la modificación de la información básica sensible del vinculado
     * 
     * Caso de Uso: GOP-VIN-1-FAB-11-ModificarInformacionSensibleVinculado
     * 
     * @param informacionVinculado
     *            datos basicos del vinculado
     * @return estado de la ejecución
     */
    @WebResult(name = "Resultado", targetNamespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas", partName = "Resultado")
    @Action(input = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/ActualizarDatosBasicos", output = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/ActualizarDatosBasicosResponse")
    @WebMethod(operationName="ActualizarDatosBasicos", action = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/ActualizarDatosBasicos")
    public TipoEstadoEjecucion actualizarDatosBasicos(
        @WebParam(partName = "InformacionContexto", name = "InformacionContexto", targetNamespace = "http://www.colpensiones.gov.co/beps/schemas/1.0/comun", header=true)
        TipoInformacionContexto informacionContexto,   
        @WebParam(partName = "InformacionVinculado", name = "InformacionVinculado", targetNamespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas")
        TipoInformacionBasicaSolicitante informacionVinculado
    );
    
    
    /**
     * Método encargado de realizar la consulta de viabilidad de vinculación
     * 
     * Caso de Uso: GVI-VIN-1-FAB-09-ConsultarViabilidadVinculacion
     * 
     * @param identificacion
     *            tipo y número de documento del solicitante
     * @return información solicitante
     */
    @WebResult(name = "tipoRespuestaSolicitanteDTO", targetNamespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas", partName = "InformacionSolicitante")
    @WebMethod(operationName="Consultar", action = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/Consultar")
    public TipoRespuestaInformacionSolicitanteDTO consultar(
        @WebParam(partName = "InformacionContexto", name = "InformacionContexto", targetNamespace = "http://www.colpensiones.gov.co/beps/schemas/1.0/comun", header=true)
        TipoInformacionContexto informacionContexto,	
        @WebParam(partName = "Identificacion", name = "identificacion", targetNamespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas")
        TipoDocumentoPersonaNatural identificacion
    );
    
    
    @WebResult(name = "EstadoEjecucion", targetNamespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/", partName = "EstadoEjecucion")
    @WebMethod(operationName = "Crear", action = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/Crear")
    public co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstadoEjecucion crear(
    	@WebParam(partName = "InformacionContexto", name = "InformacionContexto", targetNamespace = "http://www.colpensiones.gov.co/beps/schemas/1.0/comun", header=true)
        TipoInformacionContexto informacionContexto,
        @WebParam(partName = "InformacionVinculado", name = "InformacionVinculacion", targetNamespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/")
        co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionGeneralVinculado informacionVinculado
    );
    
    
    /**
     * Método encargado de realizar la modificación de la información no sensible del vinculado     * 
     * Caso de Uso: GVI-VIN-1-FAB-13-ModificarInformacionNoSensibleVinculado
     * 
     * @param informacionContexto
     *            datos del contexto
     * @param informacionGeneralVinculado
     *            datos generales del vinculado
     * @return estado de la ejecución
     */
    @WebResult(name = "EstadoEjecucion", targetNamespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/", partName = "EstadoEjecucion")
    @WebMethod(operationName = "ActualizarDatosGenerales", action = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/ActualizarDatosGenerales")
    public TipoEstadoEjecucion actualizarDatosGenerales(
    	@WebParam(partName = "InformacionContexto", name = "InformacionContexto", targetNamespace = "http://www.colpensiones.gov.co/beps/schemas/1.0/comun", header=true)
        TipoInformacionContexto informacionContexto,
        @WebParam(partName = "InformacionGeneralVinculado", name = "InformacionGeneralVinculado", targetNamespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/")
        TipoInformacionDatosGeneralesVinculado informacionGeneralVinculado
    );
    
    /**
     * Método encargado de realizar la consulta de trasacciones/movimientos y saldos de la cuenta individual del vinculado.
     * Caso de Uso: GSC-PLA-2-FAB-26-ConsultarMovimientosSaldoCtaIndividual 
     * 
     * @param informacionContexto Datos del Contexto.
     * @param filtrosConsulta Datos Generales del vinculado.
     * @return <TipoRespuestaConsultaMovimientosSaldoDTO> Movimientos y saldos para la consulta realizada. 
     */
    @WebResult(name = "tipoRespuestaConsultaMovimientosSaldoDTO", targetNamespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/", partName = "RespuestaConsulta")
    @WebMethod(operationName = "ConsultarSaldosMovimientos", action = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/ConsultarSaldosMovimientos")
    public co.gov.colpensiones.beps.schemas._1_0.personas.TipoRespuestaConsultaMovimientosSaldoDTO consultarSaldosMovimientos(
        @WebParam(partName = "InformacionContexto", name = "InformacionContexto", targetNamespace = "http://www.colpensiones.gov.co/beps/schemas/1.0/comun", header = true)
        co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContexto informacionContexto,
        @WebParam(partName = "FiltrosConsulta", name = "FiltrosConsulta", targetNamespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/")
        co.gov.colpensiones.beps.schemas._1_0.personas.TipoFiltrosConsulta filtrosConsulta
    );
    
    /**
     * Método que permite consultar los estados a los que podría cambiar un vinculado dependiendo de su estado actual y de las transiciones de estado permitidas que se encuentren parametrizadas en el Sistema de Gestión BEPS.
     * Caso de Uso: GVI-VIN-3-FAB-12-ConsultarTransicionesDeEstadoPermitidas
     * @param informacionContexto Datos del Contexto.
     * @param tipoInformacionTransicionEstado  Datos de Entrada Consultar Transiciones de Estado Permitidas
     * @return <TipoRespuestaTransicionEstado> Respuesta Servicio Web 
     */
    @WebResult(name = "tipoRespuestaTransicionEstado", targetNamespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/", partName = "TipoRespuestaTransicionEstado")
    @WebMethod(operationName = "ConsultarTransicionEstado", action = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/ConsultarTransicionEstado")
    public TipoRespuestaTransicionEstado consultarTransicionEstado(
        @WebParam(partName = "TipoInformacionContexto", name = "TipoInformacionContexto", targetNamespace = "http://www.colpensiones.gov.co/beps/schemas/1.0/comun", header = true)
        TipoInformacionContextoExterno informacionContexto,
        @WebParam(partName = "TipoInformacionTransicionEstado", name = "TipoInformacionTransicionEstado", targetNamespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/")
        TipoInformacionTransicionEstado tipoInformacionTransicionEstado
    );
    
    
    /**
     * Método que realizar el cambio de estado en el Sistema de Gestión BEPS para uno o más vinculados, de acuerdo con las transiciones permitidas que se encuentren parametrizadas.
     * <b>Caso de Uso:</b> GVI-VIN-3-FAB-13-RealizarCambioEstadoVinculado <br/>
     * @param informacionContexto Datos del Contexto
     * @param tipoInformacionListaCambioEstado Datos de Entrada realizxar cambio de estados vinculado
     * @return <TipoRespuestaListaCambioEstado> Respuesta Servicio Web
     */
    @WebResult(name = "tipoRespuestaListaCambioEstado", targetNamespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/", partName = "TipoRespuestaListaCambioEstado")
    @WebMethod(operationName = "CambiarEstado", action = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/ConsultarTransicionEstado")
    public TipoRespuestaListaCambioEstado cambiarEstado(
        @WebParam(partName = "InformacionContextoRadicacion", name = "InformacionContextoRadicacion", targetNamespace = "http://www.colpensiones.gov.co/beps/schemas/1.0/comun", header = true)
        TipoInformacionContextoRadicacion informacionContexto,
        @WebParam(partName = "TipoInformacionListaCambioEstado", name = "TipoInformacionListaCambioEstado", targetNamespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/")
        TipoInformacionListaCambioEstado tipoInformacionListaCambioEstado
    );
    
    /**
     * Método que realizar el cambio de estado en el Sistema de Gestión BEPS para uno o más vinculados, de acuerdo con las transiciones permitidas para realizar retiros
     * @param informacionContexto Datos del Contexto
     * @param tipoInformacionListaCambioEstado Datos de Entrada realizxar cambio de estados vinculado
     * @return <TipoRespuestaListaCambioEstado> Respuesta Servicio Web
     */
    @WebResult(name = "tipoRespuestaListaCambioEstadoRetiro", targetNamespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/", partName = "TipoRespuestaListaCambioEstadoRetiro")
    @WebMethod(operationName = "CambiarEstadoRetiro", action = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/CambiarEstadoRetiro")
    public TipoRespuestaListaCambioEstado cambiarEstadoRetiro(
        @WebParam(partName = "InformacionContextoRadicacion", name = "InformacionContextoRadicacion", targetNamespace = "http://www.colpensiones.gov.co/beps/schemas/1.0/comun", header = true)
        TipoInformacionContextoRadicacion informacionContexto,
        @WebParam(partName = "TipoInformacionListaCambioEstado", name = "TipoInformacionListaCambioEstado", targetNamespace = "http://www.colpensiones.gov.co/beps/contracts/1.0/personas/")
        TipoInformacionListaCambioEstado tipoInformacionListaCambioEstado
    );
}
