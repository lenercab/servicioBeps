package co.gov.colpensiones.beps.vinculacion.businesslogic.consultar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import co.gov.colpensiones.beps.comunes.utilidades.Constantes;
import co.gov.colpensiones.beps.comunes.utilidades.ConstantesLoggerServicios;
import co.gov.colpensiones.beps.comunes.utilidades.Util;
import co.gov.colpensiones.beps.comunes.utilidades.Validador;
import co.gov.colpensiones.beps.dal.utilidades.DataRow;
import co.gov.colpensiones.beps.dal.utilidades.DataStoredProcedure;
import co.gov.colpensiones.beps.dal.utilidades.DataTable;
import co.gov.colpensiones.beps.dal.utilidades.DbParameter;
import co.gov.colpensiones.beps.excepciones.DataAccessException;
import co.gov.colpensiones.beps.log.ConstantesLogger;
import co.gov.colpensiones.beps.log.LoggerBeps;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstado;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContexto;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoAutorizacion;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoCausalNoViabilidad;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoCiudad;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoCorreoElectronico;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoDatoTelefono;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoDepartamento;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoDirecciones;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoDireccionesCorreoElectronico;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoDocumentoPersonaNatural;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoEstadoVinculado;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionBasicaPersonaNatural;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionBasicaSisben;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionBasicaSolicitante;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionComplementariaPersonaNatural;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionCuentaBeps;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionDireccionPersonaNatural;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionDivulgacion;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionEconomica;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionExtendidaSolicitante;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionGeneralSolicitanteDTO;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionNacimientoPersonaNatural;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionUbicacionPersona;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionViabilidad;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoPatrocinador;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoRespuestaInformacionSolicitanteDTO;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoTelefonos;
import co.gov.colpensiones.beps.vinculacion.businesslogic.BLVinculado;
import co.gov.colpensiones.beps.vinculacion.businesslogic.DAVinculado;

/**
 * <b>Descripcion:</b> Clase encargada de la logica de negocio para la consulta
 * de información del vinculado <br/>
 * <b>Caso de Uso:</b> GVI-VIN-1-FAB-09-ConsultarViabilidadVinculacion <br/>
 * 
 * @author Yenny Nustez Arevalo <ynustez@heinsohn.com.co>
 */
public class BLConsultar extends BLVinculado {

	/** Clase de accedo a datos */
	DAVinculado daVinculado = null;

	/**
	 * Método contructor
	 * 
	 * @param log
	 *            log con el que se va a escribir de la BD
	 */
	public BLConsultar(LoggerBeps log) {
		super(log);
	}

	/**
	 * Método que ejecuta la lógica de negocio para la consulta de viabilidad de
	 * vinculación.
	 * @param informacionContexto 
	 * 			datos de contexto
	 * @param identificacion
	 *            Información del solicitante
	 * @return objeto de tipo TipoRespuestaInformacionSolicitanteDTO que
	 *         contiene toda la información consultada para el solicitante
	 */
	public TipoRespuestaInformacionSolicitanteDTO consultar(TipoInformacionContexto informacionContexto, 
			TipoDocumentoPersonaNatural identificacion) {

		TipoRespuestaInformacionSolicitanteDTO response = new TipoRespuestaInformacionSolicitanteDTO();

		/* Manejo de Log */
		HashMap<String, Object> payLoadTrace = new HashMap<String, Object>();
		HashMap<String, Object> payLoad = new HashMap<String, Object>();
		HashMap<String, String> metaData = new HashMap<String, String>();
		metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, identificacion.getTipoDocumento());
		metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, identificacion.getNumeroDocumento());
	    if (informacionContexto != null) {
            metaData.put(ConstantesLoggerServicios.USUARIO_SISTEMA_EXTERNO, informacionContexto.getUsuarioSistemaExterno());
        }
		payLoad.putAll(metaData);

		try {
			/* registro en el log */
			payLoadTrace.put(ConstantesLogger.OBJETO_CONTEXTO_ENTRADA, informacionContexto);
			payLoadTrace.put(ConstantesLogger.OBJETO_NEGOCIO_ENTRADA,
					identificacion);
			log.trace(payLoadTrace, metaData);

			/* validar datos de entrada */
			StringBuffer lstErrores = new StringBuffer();
			lstErrores.append(new Validador<TipoInformacionContexto>().ValidarDataContract(informacionContexto));
			lstErrores.append(new Validador<TipoDocumentoPersonaNatural>()
					.ValidarDataContract(identificacion));
			lstErrores.append(this.validarDatosEntrada(informacionContexto,identificacion));

			/* validar existencia errores */
			if (lstErrores.toString().length() > 0) {
				payLoad.put(Constantes.MSJ_ERROR_LOG, lstErrores.toString());
				log.info(payLoad, metaData);
				response.setEstadoEjecucion(respuestaNegocioServicio(
						Constantes.COD_FORMATO_INVALIDO_OBLIGATORIO_NO_RECIBIDO,
						lstErrores.toString()));
			} else {
				/* Se ejecuta la lógica de negocio */
				response = consultarViabilidad(identificacion, informacionContexto);
			}
		} catch (DataAccessException e) {
			generarLogError(e.getMetaData(), true, e);
			response.setEstadoEjecucion(respuestaErrorTecnicoServicio());
		} catch (Exception e1) {
			generarLogError(metaData, false, e1);
			response.setEstadoEjecucion(respuestaErrorTecnicoServicio());
		} finally {
			payLoadTrace.put(ConstantesLogger.OBJETO_NEGOCIO_SALIDA, response);
			log.trace(payLoadTrace, metaData);
		}
		return response;
	}

	/**
	 * Metodo para validaciones de negocio de los datos de entrada
	 * @param informacionContexto
     *            datos encabezado
	 * @param identificacion
	 *            Información del solicitante
	 * @return listado de errores
	 * @throws Exception
	 */
	private String validarDatosEntrada(
			TipoInformacionContexto informacionContexto, TipoDocumentoPersonaNatural identificacion) throws Exception {
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
		ArrayList<String> tiposDocumento = Util.getResourcePropertyArray(
				Constantes._HOMOLOGACION_COMUN_NAME,
				Constantes.PREFIJO_LLAVES_TIPO_DOCUMENTO);
		if (!tiposDocumento.contains(identificacion.getTipoDocumento())) {
			errores.append(Constantes.MSJ_ERROR_DATO_NO_VALIDO.replaceAll(
					Constantes.PARAMETRO0, "TipoDocumento")
					+ tiposDocumento.toString() + "\n");
		}

		return errores.toString();
	}

	/**
	 * Método que se encarga de la ejecución de la lógica de negocio para la
	 * consulta de viabilidad de vinculación. Ejecuta un procedimiento
	 * almacenado que extrae la información del vinculado, previnculado ó
	 * prospecto en caso de que se encuentre información asociada al
	 * solicitante.
	 * 
	 * @param identificacion
	 *            Información del solicitante
	 * @param informacionContexto
	 * 			  Información del contexto de la invocación
	 * @return objeto de tipo TipoRespuestaInformacionSolicitanteDTO que
	 *         contiene toda la información consultada para el solicitante
	 */
	protected TipoRespuestaInformacionSolicitanteDTO consultarViabilidad(
			TipoDocumentoPersonaNatural identificacion, TipoInformacionContexto informacionContexto) throws Exception {

		TipoRespuestaInformacionSolicitanteDTO response = new TipoRespuestaInformacionSolicitanteDTO();
		DataStoredProcedure data = new DataStoredProcedure();
		HashMap<String, Object> payLoad = new HashMap<String, Object>();
		HashMap<String, String> metaData = new HashMap<String, String>();
		metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, identificacion.getTipoDocumento());
		metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, identificacion.getNumeroDocumento());

		daVinculado = new DAVinculado();

		/* Se ejecuta el procedimiento almacenado de consulta */
		data = daVinculado.consultarViabilidadVinculacion(identificacion, informacionContexto.getSistemaOrigen());
		List<DbParameter> parametrosSalida = data.getParametrosSalida();

		/*
		 * Se verifica el parámetro de salida que define el tipo de mapeo a
		 * realizar a los datos
		 */
		if (parametrosSalida.size() > 0) {
			String tipoSolicitante = parametrosSalida.get(0)
					.getParameterValue().toString();
			payLoad.put("Tipo solicitante", tipoSolicitante);
			log.info(payLoad, metaData);

			/* Si el solicitante no existe */
			if (tipoSolicitante.equals("NA")) {
				response.setEstadoEjecucion(respuestaNegocioServicio(
						Constantes.COD_ERROR_LOGICA_NEGOCIO,
						Constantes.MSJ_ERROR_CONSULTA_VIABILIDAD));
				response.setDetalle(null);

			} else if (tipoSolicitante.equals("VI")) {
				/* Si es vinculado */
				List<DataTable> informacionPrevinculado = data
						.getResultSetList();
				TipoInformacionGeneralSolicitanteDTO detalle = mapearDatosVinculado(
						informacionPrevinculado, identificacion);
				response.setDetalle(detalle);

				/* Ejecución exitosa del procedimiento */
				response.setEstadoEjecucion(respuestaExitosaServicio());

			} else if (tipoSolicitante.equals("PV")) {
				/* Si es previnculado */
				List<DataTable> informacionPrevinculado = data
						.getResultSetList();
				TipoInformacionGeneralSolicitanteDTO detalle = mapearDatosPrevinculado(
						informacionPrevinculado, identificacion);
				response.setDetalle(detalle);

				/* Ejecución exitosa del procedimiento */
				response.setEstadoEjecucion(respuestaExitosaServicio());

			} else if (tipoSolicitante.equals("PP")) {
				List<DataTable> informacionPrevinculado = data
						.getResultSetList();
				TipoInformacionGeneralSolicitanteDTO detalle = mapearDatosProspecto(
						informacionPrevinculado, identificacion);
				response.setDetalle(detalle);

				/* Ejecución exitosa del procedimiento */
				response.setEstadoEjecucion(respuestaExitosaServicio());
			}
		}

		return response;
	}

	/**
	 * Método que realiza el mapeo de los datos de consulta de un previnculado
	 * 
	 * @param informacionPrevinculado
	 *            Información encontrada en la base de datos
	 * @param identificacion
	 *            Información del solicitante
	 * @return objeto de tipo TipoRespuestaInformacionSolicitanteDTO que
	 *         contiene toda la información consultada para el solicitante
	 */
	private TipoInformacionGeneralSolicitanteDTO mapearDatosPrevinculado(
			List<DataTable> informacionPrevinculado,
			TipoDocumentoPersonaNatural identificacion) throws Exception {

		TipoInformacionGeneralSolicitanteDTO detallePrevinculado = new TipoInformacionGeneralSolicitanteDTO();

		try {
			if (informacionPrevinculado != null
					&& informacionPrevinculado.size() > 0) {
				DataRow dtResult = informacionPrevinculado.get(0).getRows()
						.get(0);

				/* Tipo Persona */
				detallePrevinculado.setTipoPersona(parseDataRowValue(dtResult,
						"tipo_persona"));

				TipoInformacionBasicaSolicitante infoBasica = new TipoInformacionBasicaSolicitante();
				/* Documento */
				infoBasica.setDocumento(identificacion);

				// TODO Codigo agregado para evitar error en validación
				TipoInformacionComplementariaPersonaNatural info = new TipoInformacionComplementariaPersonaNatural();
				info.setFechaExpedicionDocumentoIdenticacion(new String());
				infoBasica.setInformacionAdicional(info);

				/* Nombres y apellidos */
				TipoInformacionBasicaPersonaNatural nombres = new TipoInformacionBasicaPersonaNatural();
				nombres.setPrimerApellido(parseDataRowValue(dtResult,
						"primer_apellido"));
				nombres.setPrimerNombre(parseDataRowValue(dtResult,
						"primer_nombre"));
				nombres.setSegundoApellido(parseDataRowValue(dtResult,
						"segundo_apellido"));
				nombres.setSegundoNombre(parseDataRowValue(dtResult,
						"segundo_nombre"));
				JAXBElement<TipoInformacionBasicaPersonaNatural> infoPersona = new JAXBElement<TipoInformacionBasicaPersonaNatural>(
						new QName(
								"http://www.colpensiones.gov.co/beps/schemas/1.0/personas",
								"nombresApellidos"),
						TipoInformacionBasicaPersonaNatural.class, nombres);
				infoBasica.setNombresApellidos(infoPersona);

				/* Informacion Basica */
				detallePrevinculado.setInformacionBasicaSolicitante(infoBasica);

				/* Datos adicionales */
				TipoInformacionExtendidaSolicitante infoAdicional = new TipoInformacionExtendidaSolicitante();
				infoAdicional.setFechaPrevinculacion(parseDataRowValue(
						dtResult, "fecha_vinculacion"));
				infoAdicional.setNumeroRadicado(parseDataRowValue(dtResult,
						"num_radicado_vinculacion"));

				// TODO CODIGO AGREGADO PARA EVITAR ERROR EN VALIDACIÓN
				infoAdicional.setAfp(new String());
				infoAdicional.setCanalDivulgacion(new String());
				detallePrevinculado
						.setInformacionExtendidaSolicitante(infoAdicional);
				detallePrevinculado
						.setInformacionUbicacion(new TipoInformacionUbicacionPersona());

				if (detallePrevinculado.getEstadoSisben() == null)
					detallePrevinculado.setEstadoSisben(new String());

				if (detallePrevinculado.getInformacionBasicaSisben() == null)
					detallePrevinculado
							.setInformacionBasicaSisben(new TipoInformacionBasicaSisben());

				if (detallePrevinculado.getInformacionViablidad() == null) {
					TipoInformacionViabilidad viabilidad = new TipoInformacionViabilidad();
					viabilidad.setViabilidad(new String());
					detallePrevinculado.setInformacionViablidad(viabilidad);
				}

				if (detallePrevinculado.getInformacionDivulgacion() == null) {
					TipoInformacionDivulgacion divulgacion = new TipoInformacionDivulgacion();
					divulgacion.setCanalDivulgacion(new String());
					divulgacion.setFechaDivulgacion(new String());
					detallePrevinculado.setInformacionDivulgacion(divulgacion);
				}

				detallePrevinculado
						.setInformacionEconomica(new TipoInformacionEconomica());
				detallePrevinculado
						.setInformacionAutorizacion(new TipoAutorizacion());
				TipoInformacionCuentaBeps infoCuenta = new TipoInformacionCuentaBeps();
				infoCuenta.setEstadoVinculacion(new String());
				detallePrevinculado.setInformacionCuentaBeps(infoCuenta);
				detallePrevinculado
						.setEstadoVinculado(new TipoEstadoVinculado());
			}
		} catch (Exception e) {
			throw new Exception("Error mapeando datos del Previnculado. " + e);
		}

		return detallePrevinculado;
	}

	/**
	 * Método que realiza el mapeo de los datos de consulta de un Vinculado
	 * 
	 * @param informacionVinculado
	 *            Información encontrada en la base de datos
	 * @param identificacion
	 *            Información del solicitante
	 * @return objeto de tipo TipoRespuestaInformacionSolicitanteDTO que
	 *         contiene toda la información consultada para el solicitante
	 */
	private TipoInformacionGeneralSolicitanteDTO mapearDatosVinculado(
			List<DataTable> informacionVinculado,
			TipoDocumentoPersonaNatural identificacion) throws Exception {

		TipoInformacionGeneralSolicitanteDTO detalleVinculado = new TipoInformacionGeneralSolicitanteDTO();

		try {
			if (informacionVinculado != null && informacionVinculado.size() > 0) {

				/*
				 * -------------------- INFORMACION BASICA
				 * --------------------------
				 */

				if (informacionVinculado.get(0).getRows().size() > 0) {
					DataRow dtResult = informacionVinculado.get(0).getRows()
							.get(0);

					/* Tipo Persona */
					detalleVinculado.setTipoPersona(parseDataRowValue(dtResult,
							"tipo_persona"));

					TipoInformacionBasicaSolicitante infoBasica = new TipoInformacionBasicaSolicitante();
					/* Documento */
					infoBasica.setDocumento(identificacion);
					/* Nombres y apellidos */
					TipoInformacionBasicaPersonaNatural nombres = new TipoInformacionBasicaPersonaNatural();
					nombres.setPrimerApellido(parseDataRowValue(dtResult,
							"primer_apellido"));
					nombres.setPrimerNombre(parseDataRowValue(dtResult,
							"primer_nombre"));
					nombres.setSegundoApellido(parseDataRowValue(dtResult,
							"segundo_apellido"));
					nombres.setSegundoNombre(parseDataRowValue(dtResult,
							"segundo_nombre"));
					JAXBElement<TipoInformacionBasicaPersonaNatural> infoPersona = new JAXBElement<TipoInformacionBasicaPersonaNatural>(
							new QName(
									"http://www.colpensiones.gov.co/beps/schemas/1.0/personas",
									"nombresApellidos"),
							TipoInformacionBasicaPersonaNatural.class, nombres);
					infoBasica.setNombresApellidos(infoPersona);

					/* Datos nacimiento */
					TipoInformacionComplementariaPersonaNatural infoNacimiento = new TipoInformacionComplementariaPersonaNatural();

					/* Homologacion genero */
					ArrayList<String> tiposGenero = Util
							.getResourcePropertyArray(
									Constantes._HOMOLOGACION_COMUN_NAME,
									Constantes.PREFIJO_LLAVES_GENERO);
					String generoHomologado = "";
					for (String genero : tiposGenero) {
						if (parseDataRowValue(dtResult, "sexo").equals(
								Util.getResourceProperty(
										Constantes._HOMOLOGACION_COMUN_NAME,
										Constantes.PREFIJO_LLAVES_GENERO
												+ genero))) {
							generoHomologado = genero;
							break;
						}
					}
					infoNacimiento.setGenero(generoHomologado);
					infoNacimiento
							.setFechaExpedicionDocumentoIdenticacion(parseDataRowDateValue(
									dtResult, "fecha_expedicion"));
					TipoInformacionNacimientoPersonaNatural lugarNacimiento = new TipoInformacionNacimientoPersonaNatural();
					lugarNacimiento.setFechaNacimiento(parseDataRowDateValue(
							dtResult, "fecha_nacimiento"));
					infoNacimiento
							.setInformacionLugarNacimiento(lugarNacimiento);
					infoNacimiento
							.setMunicipioExpedicionDocumentoIdenticacion(parseDataRowValue(
									dtResult, "municipio_expedicion"));
					infoBasica.setInformacionAdicional(infoNacimiento);

					/* Informacion Basica */
					detalleVinculado
							.setInformacionBasicaSolicitante(infoBasica);

					/* Datos adicionales */
					TipoInformacionExtendidaSolicitante infoAdicional = new TipoInformacionExtendidaSolicitante();
					infoAdicional.setFechaVinculacion(parseDataRowDateValue(
							dtResult, "fecha_vinculacion"));
					infoAdicional.setNumeroRadicado(parseDataRowValue(dtResult,
							"num_radicado_vinculacion"));
					infoAdicional.setColombiaMayor(parseDataRowValue(dtResult,
							"candidto_colombia_mayor"));
					infoAdicional.setAfp(parseDataRowValue(dtResult, "afp"));

					// TODO AGREGADO PARA EVITAR ERROR DE VALIDACIÓN
					infoAdicional.setCanalDivulgacion(new String());

					detalleVinculado
							.setInformacionExtendidaSolicitante(infoAdicional);

					TipoInformacionEconomica infoEconomica = new TipoInformacionEconomica();
					infoEconomica
							.setCodigoActividadEconomicaPrincipal(parseDataRowValue(
									dtResult, "actividad"));
					infoEconomica
							.setCodigoActividadEconomicaSecundaria(parseDataRowValue(
									dtResult, "actividad_secundaria"));
					detalleVinculado.setInformacionEconomica(infoEconomica);

					TipoAutorizacion tipoAutorizacion = new TipoAutorizacion();
					tipoAutorizacion
							.setAutorizacionEnvioComunicacion(parseDataRowBitValue(
									dtResult, "autorizacion_envio_info", false));
					tipoAutorizacion
							.setAutorizacionManejoInformacion(parseDataRowBitValue(
									dtResult, "autorizacion_manejo_info", false));
					detalleVinculado
							.setInformacionAutorizacion(tipoAutorizacion);

					/*
					 * Adición de estados de acuerdo a definición de máquina de
					 * estados - 19/11/2014
					 */
					TipoEstadoVinculado estadoVinculado = new TipoEstadoVinculado();
					estadoVinculado.setEstadoVinculado(parseDataRowValue(
							dtResult, "estado_vinculado"));
					estadoVinculado.setDescripcionEstadoVinculado(new String());
					estadoVinculado.setCodigoDetalleEstado(parseDataRowValue(
							dtResult, "codigo_detalle_estado"));
					estadoVinculado
							.setDescripcionDetalleEstado(parseDataRowValue(
									dtResult, "descripcion_detalle_estado"));
					detalleVinculado.setEstadoVinculado(estadoVinculado);
					
					TipoInformacionBasicaSisben infoSisben = new TipoInformacionBasicaSisben();
					infoSisben.setPuntaje(parseDataRowValue(dtResult,
							"vsa_puntaje_sisben"));
					infoSisben.setArea(parseDataRowValue(dtResult, "vsa_area_sisben"));
					TipoEstado nivel = new TipoEstado();
					nivel.setCodigo(parseDataRowValue(dtResult,
							"vsa_nivel_sisben"));
					JAXBElement<TipoEstado> nivelSisben = new JAXBElement<TipoEstado>(
							new QName(
									"http://www.colpensiones.gov.co/beps/schemas/1.0/personas",
									"nivel"), TipoEstado.class, nivel);
					infoSisben.setNivel(nivelSisben);
					detalleVinculado.setInformacionBasicaSisben(infoSisben);
					detalleVinculado.setEstadoSisben(parseDataRowValue(
							dtResult, "vsa_estado_sisben"));
					
					
				}

				/* -------------------- RENDIMIENTOS -------------------------- */

				TipoInformacionCuentaBeps infoCuenta = new TipoInformacionCuentaBeps();
				if (informacionVinculado.get(1).getRows().size() > 0) {
					/* Rendimientos */
					DataRow dtRendimientos = informacionVinculado.get(1)
							.getRows().get(0);
					infoCuenta.setValorAhorroBeps(parseDataRowBigDecimalValue(
							dtRendimientos, "vrc_valor_ahorro_beps"));
					infoCuenta
							.setValorRendimientosBeps(parseDataRowBigDecimalValue(
									dtRendimientos,
									"vrc_valor_rendimiento_beps"));
					infoCuenta.setValorSubsidiBeps(parseDataRowBigDecimalValue(
							dtRendimientos, "vrc_valor_subsidio_beps"));
					infoCuenta.setFechaCorteCuentaIndividual(parseDataRowValue(
							dtRendimientos, "vrc_fecha_corte"));
					infoCuenta.setValorTraslados(parseDataRowBigDecimalValue(
							dtRendimientos, "vrc_valor_traslado_sgp"));
				}
				// TODO AGREGADO PARA EVITAR ERROR DE VALIDACIÓN
				infoCuenta.setEstadoVinculacion(new String());

				detalleVinculado.setInformacionCuentaBeps(infoCuenta);

				/*
				 * -------------------- LISTA DIRECCIONES
				 * --------------------------
				 */

				/* Lista Direcciones */
				List<TipoInformacionDireccionPersonaNatural> listaDirecciones = new ArrayList<TipoInformacionDireccionPersonaNatural>();
				DataRow dtDireccion = null;
				TipoInformacionDireccionPersonaNatural infoDireccion = null;
				TipoDepartamento departamento = null;
				TipoCiudad municipio = null;
				for (int i = 0; i < informacionVinculado.get(2).getRows()
						.size(); i++) {
					dtDireccion = informacionVinculado.get(2).getRows().get(i);
					infoDireccion = new TipoInformacionDireccionPersonaNatural();
					infoDireccion.setIdentificador(parseDataRowBigDecimalValue(
							dtDireccion, "vdi_pk_id"));
					infoDireccion.setDireccion(parseDataRowValue(dtDireccion,
							"vdi_direccion"));
					infoDireccion.setEsPrincipal(parseDataRowBitValue(
							dtDireccion, "vdi_direccion_principal", false));
					departamento = new TipoDepartamento();
					departamento.setCodigo(parseDataRowValue(dtDireccion,
							"vde_codigo_dane_depto"));
					// TODO Agregado para evitar error de validación
					departamento.setNombre(new String());

					infoDireccion.setDepartamento(departamento);
					municipio = new TipoCiudad();
					municipio.setCodigo(parseDataRowValue(dtDireccion,
							"vdi_codigo_municipio"));
					// TODO Agregado para evitar error de validación.
					municipio.setNombre(new String());

					infoDireccion.setMunicipio(municipio);
					listaDirecciones.add(infoDireccion);
				}
				TipoDirecciones direcciones = new TipoDirecciones();
				direcciones.getDireccion().addAll(listaDirecciones);

				/*
				 * -------------------- LISTA TELEFONOS
				 * --------------------------
				 */

				/* Lista Telefonos */
				List<TipoDatoTelefono> listaTelefonos = new ArrayList<TipoDatoTelefono>();
				DataRow dtTelefono = null;
				TipoDatoTelefono infoTelefono = null;
				for (int i = 0; i < informacionVinculado.get(3).getRows()
						.size(); i++) {
					dtTelefono = informacionVinculado.get(3).getRows().get(i);
					infoTelefono = new TipoDatoTelefono();
					infoTelefono.setIdentificador(parseDataRowBigDecimalValue(
							dtTelefono, "vte_pk_id"));
					infoTelefono.setTipoTelefono(parseDataRowValue(dtTelefono,
							"vte_tipo"));
					infoTelefono.setIndicativoCiudad(parseDataRowValue(
							dtTelefono, "vte_indicativo_ciudad"));
					infoTelefono.setTelefono(parseDataRowValue(dtTelefono,
							"vte_telefono"));
					infoTelefono.setExtension(parseDataRowValue(dtTelefono,
							"vte_extension"));
					infoTelefono.setEsPrincipal(parseDataRowBitValue(
							dtTelefono, "vte_telefono_principal", false));
					// TODO Agregado para evitar error de validación
					infoTelefono.setIndicativoPais(new String());
					listaTelefonos.add(infoTelefono);
				}
				TipoTelefonos telefonos = new TipoTelefonos();
				telefonos.getTelefono().addAll(listaTelefonos);

				/*
				 * -------------------- LISTA CORREOS ELECTRONICOS
				 * --------------------------
				 */

				/* Lista Correos electronicos */
				List<TipoCorreoElectronico> listaEmail = new ArrayList<TipoCorreoElectronico>();
				DataRow dtEmail = null;
				TipoCorreoElectronico infoEmail = null;
				for (int i = 0; i < informacionVinculado.get(4).getRows()
						.size(); i++) {
					dtEmail = informacionVinculado.get(4).getRows().get(i);
					infoEmail = new TipoCorreoElectronico();
					infoEmail.setIdentificador(parseDataRowBigDecimalValue(
							dtEmail, "vem_pk_id"));
					infoEmail.setDireccion(parseDataRowValue(dtEmail,
							"vem_email"));
					infoEmail.setEsPrincipal(parseDataRowBitValue(dtEmail,
							"vem_email_principal", false));
					listaEmail.add(infoEmail);
				}
				TipoDireccionesCorreoElectronico correos = new TipoDireccionesCorreoElectronico();
				correos.getCorreoElectronico().addAll(listaEmail);

				TipoInformacionUbicacionPersona infoUbicacion = new TipoInformacionUbicacionPersona();
				infoUbicacion.setDirecciones(direcciones);
				infoUbicacion.setTelefonos(telefonos);
				infoUbicacion.setCorreosElectronicos(correos);
				detalleVinculado.setInformacionUbicacion(infoUbicacion);


				// TODO AGREGADO PARA EVITAR ERRORES DE VALIDACIÓN
//				if (detalleVinculado.getEstadoSisben() == null)
//					detalleVinculado.setEstadoSisben(new String());
//				if (detalleVinculado.getInformacionBasicaSisben() == null)
//					detalleVinculado
//							.setInformacionBasicaSisben(new TipoInformacionBasicaSisben());
				if (detalleVinculado.getInformacionViablidad() == null) {
					TipoInformacionViabilidad viabilidad = new TipoInformacionViabilidad();
					viabilidad.setViabilidad(new String());
					detalleVinculado.setInformacionViablidad(viabilidad);
				}
				if (detalleVinculado.getInformacionDivulgacion() == null) {
					TipoInformacionDivulgacion divulgacion = new TipoInformacionDivulgacion();
					divulgacion.setCanalDivulgacion(new String());
					divulgacion.setFechaDivulgacion(new String());
					detalleVinculado.setInformacionDivulgacion(divulgacion);
				}

			}
		} catch (Exception e) {
			throw new Exception("Error mapeando datos del Vinculado. " + e);
		}

		return detalleVinculado;
	}

	/**
	 * Método que realiza el mapeo de los datos de consulta de un Prospecto
	 * 
	 * @param informacionProspecto
	 *            Información encontrada en la base de datos
	 * @param identificacion
	 *            Información del solicitante
	 * @return objeto de tipo TipoRespuestaInformacionSolicitanteDTO que
	 *         contiene toda la información consultada para el solicitante
	 */
	private TipoInformacionGeneralSolicitanteDTO mapearDatosProspecto(
			List<DataTable> informacionProspecto,
			TipoDocumentoPersonaNatural identificacion) throws Exception {

		TipoInformacionGeneralSolicitanteDTO detalleProspecto = new TipoInformacionGeneralSolicitanteDTO();

		try {
			if (informacionProspecto != null && informacionProspecto.size() > 0) {

				/*
				 * -TipoCiudadano.
				 */

				/*
				 * -------------------- INFORMACION BASICA
				 * --------------------------
				 */

				if (informacionProspecto.get(0).getRows().size() > 0) {

					DataRow dtResult = informacionProspecto.get(0).getRows()
							.get(0);

					/* Tipo Persona */
					detalleProspecto.setTipoPersona(parseDataRowValue(dtResult,
							"tipo_persona"));

					TipoInformacionBasicaSolicitante infoBasica = new TipoInformacionBasicaSolicitante();
					/* Documento */
					infoBasica.setDocumento(identificacion);
					/* Nombres y apellidos */
					TipoInformacionBasicaPersonaNatural nombres = new TipoInformacionBasicaPersonaNatural();
					nombres.setPrimerApellido(parseDataRowValue(dtResult,
							"vpp_primer_apellido"));
					nombres.setPrimerNombre(parseDataRowValue(dtResult,
							"vpp_primer_nombre"));
					nombres.setSegundoApellido(parseDataRowValue(dtResult,
							"vpp_segundo_apellido"));
					nombres.setSegundoNombre(parseDataRowValue(dtResult,
							"vpp_segundo_nombre"));
					JAXBElement<TipoInformacionBasicaPersonaNatural> infoPersona = new JAXBElement<TipoInformacionBasicaPersonaNatural>(
							new QName(
									"http://www.colpensiones.gov.co/beps/schemas/1.0/personas",
									"nombresApellidos"),
							TipoInformacionBasicaPersonaNatural.class, nombres);
					infoBasica.setNombresApellidos(infoPersona);

					/* Datos nacimiento */
					TipoInformacionComplementariaPersonaNatural infoNacimiento = new TipoInformacionComplementariaPersonaNatural();

					/* Homologacion genero */
					ArrayList<String> tiposGenero = Util
							.getResourcePropertyArray(
									Constantes._HOMOLOGACION_NAME,
									Constantes.PREFIJO_LLAVES_GENERO);
					String generoHomologado = "";
					for (String genero : tiposGenero) {
						if (parseDataRowValue(dtResult, "vpp_sexo").equals(
								Util.getResourceProperty(
										Constantes._HOMOLOGACION_NAME,
										Constantes.PREFIJO_LLAVES_GENERO
												+ genero))) {
							generoHomologado = genero;
							break;
						}
					}
					infoNacimiento.setGenero(generoHomologado);
					infoNacimiento
							.setFechaExpedicionDocumentoIdenticacion(parseDataRowDateValue(
									dtResult, "vpp_fecha_expedicion"));
					TipoInformacionNacimientoPersonaNatural lugarNacimiento = new TipoInformacionNacimientoPersonaNatural();
					lugarNacimiento.setFechaNacimiento(parseDataRowDateValue(
							dtResult, "vpp_fecha_nacimiento"));
					infoNacimiento
							.setInformacionLugarNacimiento(lugarNacimiento);
					infoNacimiento
							.setMunicipioExpedicionDocumentoIdenticacion(parseDataRowValue(
									dtResult, "vpp_municipio_expedicion"));
					infoBasica.setInformacionAdicional(infoNacimiento);

					/* Informacion Basica */
					detalleProspecto
							.setInformacionBasicaSolicitante(infoBasica);

					TipoInformacionBasicaSisben infoSisben = new TipoInformacionBasicaSisben();
					infoSisben.setPuntaje(parseDataRowValue(dtResult,
							"vpr_puntaje_sisben"));
					infoSisben.setArea(parseDataRowValue(dtResult, "vpr_area"));
					TipoEstado nivel = new TipoEstado();
					nivel.setCodigo(parseDataRowValue(dtResult,
							"vpr_nivel_sisben"));
					JAXBElement<TipoEstado> nivelSisben = new JAXBElement<TipoEstado>(
							new QName(
									"http://www.colpensiones.gov.co/beps/schemas/1.0/personas",
									"nivel"), TipoEstado.class, nivel);
					infoSisben.setNivel(nivelSisben);
					detalleProspecto.setInformacionBasicaSisben(infoSisben);
					detalleProspecto.setEstadoSisben(parseDataRowValue(
							dtResult, "vpr_estado_sisben"));

					/* Datos adicionales */
					TipoInformacionExtendidaSolicitante infoAdicional = new TipoInformacionExtendidaSolicitante();
					infoAdicional.setColombiaMayor(parseDataRowValue(dtResult,
							"vpr_colombia_mayor"));
					infoAdicional
							.setAfp(parseDataRowValue(dtResult, "vpr_afp"));
					infoAdicional
							.setSemanasCotizadas(parseDataRowBigDecimalValue(
									dtResult, "vpr_semanas_cotizadas"));
					infoAdicional.setFechaCorteSemanas(parseDataRowDateValue(
							dtResult, "vpr_fecha_corte_semanas"));
					infoAdicional
							.setValorIndemnizacionDevolucionSaldos(parseDataRowBigDecimalValue(
									dtResult, "vpr_valor_indemnizacion"));
					infoAdicional.setValorBeps(parseDataRowBigDecimalValue(
							dtResult, "vpr_valor_beps"));
					infoAdicional.setCanalDivulgacion(parseDataRowValue(
							dtResult, "vpr_canal_divulgacion"));
					infoAdicional.setFechaDivulgacion(parseDataRowValue(
							dtResult, "vpr_fecha_divulgacion"));

					// TODO Agregado para evitar error en validación de response
					infoAdicional.setNumeroRadicado(new String());

					/*
					 * Se ajusta el procedimiento almacenado
					 * [vinculacion].[pr_vinc_detalle_prospecto] de acuerdo a
					 * definición del cambio - 25/11/2014
					 */
					infoAdicional.setTipoCiudadano(parseDataRowValue(dtResult,
							"tipo_ciudadano"));

					detalleProspecto
							.setInformacionExtendidaSolicitante(infoAdicional);

					/* viabilidad */
					TipoInformacionViabilidad infoViabilidad = new TipoInformacionViabilidad();
					infoViabilidad.setViabilidad(parseDataRowValue(dtResult,
							"vpr_vinculable"));
					detalleProspecto.setInformacionViablidad(infoViabilidad);

					/* Patrocinadores */
					/** TODO */
					TipoPatrocinador tipoPatrocinador = new TipoPatrocinador();
					tipoPatrocinador.setPatrocinador("");
					tipoPatrocinador.setNombrePatrocinador("");
					detalleProspecto.getListaPatrocinadores().add(
							tipoPatrocinador);

					// TODO Agregado para evitar error en validación de response
					detalleProspecto
							.setEstadoVinculado(new TipoEstadoVinculado());

				}

				/*
				 * -------------------------- VIABILIDAD
				 * --------------------------
				 */

				TipoCausalNoViabilidad causalNoViabilidad = null;
				List<TipoCausalNoViabilidad> infoViabilidad = new ArrayList<TipoCausalNoViabilidad>();
				for (int i = 0; i < informacionProspecto.get(1).getRows()
						.size(); i++) {
					/* causal no viabiliadad */
					DataRow dtRendimientos = informacionProspecto.get(1)
							.getRows().get(i);
					causalNoViabilidad = new TipoCausalNoViabilidad();
					causalNoViabilidad.setCausal(parseDataRowValue(
							dtRendimientos, "vvv_id_viabilidad"));
					infoViabilidad.add(causalNoViabilidad);
				}
				detalleProspecto.getInformacionViablidad()
						.getListaCausalNoViabilidad().addAll(infoViabilidad);

				/*
				 * -------------------- LISTA DIRECCIONES
				 * --------------------------
				 */

				/* Lista Direcciones */
				List<TipoInformacionDireccionPersonaNatural> listaDirecciones = new ArrayList<TipoInformacionDireccionPersonaNatural>();
				DataRow dtDireccion = null;
				TipoInformacionDireccionPersonaNatural infoDireccion = null;
				TipoDepartamento departamento = null;
				TipoCiudad municipio = null;
				for (int i = 0; i < informacionProspecto.get(2).getRows()
						.size(); i++) {
					dtDireccion = informacionProspecto.get(2).getRows().get(i);
					infoDireccion = new TipoInformacionDireccionPersonaNatural();
					infoDireccion.setIdentificador(parseDataRowBigDecimalValue(
							dtDireccion, "vdi_pk_id"));
					infoDireccion.setDireccion(parseDataRowValue(dtDireccion,
							"vdi_direccion"));
					infoDireccion.setEsPrincipal(parseDataRowBitValue(
							dtDireccion, "vdi_direccion_principal", false));
					departamento = new TipoDepartamento();
					departamento.setCodigo(parseDataRowValue(dtDireccion,
							"vde_codigo_dane_depto"));
					// TODO Agregado para evitar error de validación
					departamento.setNombre(new String());

					infoDireccion.setDepartamento(departamento);
					municipio = new TipoCiudad();
					municipio.setCodigo(parseDataRowValue(dtDireccion,
							"vdi_codigo_municipio"));
					// TODO Agregado para evitar error de validación
					municipio.setNombre(new String());

					infoDireccion.setMunicipio(municipio);
					listaDirecciones.add(infoDireccion);
				}
				TipoDirecciones direcciones = new TipoDirecciones();
				direcciones.getDireccion().addAll(listaDirecciones);

				/*
				 * -------------------- LISTA TELEFONOS
				 * --------------------------
				 */

				/* Lista Telefonos */
				List<TipoDatoTelefono> listaTelefonos = new ArrayList<TipoDatoTelefono>();
				DataRow dtTelefono = null;
				TipoDatoTelefono infoTelefono = null;
				for (int i = 0; i < informacionProspecto.get(3).getRows()
						.size(); i++) {
					dtTelefono = informacionProspecto.get(3).getRows().get(i);
					infoTelefono = new TipoDatoTelefono();
					infoTelefono.setIdentificador(parseDataRowBigDecimalValue(
							dtTelefono, "vtp_pk_id"));
					infoTelefono.setTipoTelefono(parseDataRowValue(dtTelefono,
							"vtp_tipo"));
					infoTelefono.setIndicativoCiudad(parseDataRowValue(
							dtTelefono, "vtp_indicativo_ciudad"));
					// TODO Agregado para evitar error de validación
					infoTelefono.setIndicativoPais(new String());

					infoTelefono.setTelefono(parseDataRowValue(dtTelefono,
							"vtp_telefono"));
					infoTelefono.setExtension(parseDataRowValue(dtTelefono,
							"vtp_extension"));
					infoTelefono.setEsPrincipal(parseDataRowBitValue(
							dtTelefono, "vtp_telefono_principal", false));
					listaTelefonos.add(infoTelefono);
				}
				TipoTelefonos telefonos = new TipoTelefonos();
				telefonos.getTelefono().addAll(listaTelefonos);

				/*
				 * -------------------- LISTA CORREOS ELECTRONICOS
				 * --------------------------
				 */

				/* Lista Correos electronicos */
				List<TipoCorreoElectronico> listaEmail = new ArrayList<TipoCorreoElectronico>();
				DataRow dtEmail = null;
				TipoCorreoElectronico infoEmail = null;
				for (int i = 0; i < informacionProspecto.get(4).getRows()
						.size(); i++) {
					dtEmail = informacionProspecto.get(4).getRows().get(i);
					infoEmail = new TipoCorreoElectronico();
					infoEmail.setIdentificador(parseDataRowBigDecimalValue(
							dtEmail, "vep_pk_id"));
					infoEmail.setDireccion(parseDataRowValue(dtEmail,
							"vep_email"));
					infoEmail.setEsPrincipal(parseDataRowBitValue(dtEmail,
							"vep_email_principal", false));
					listaEmail.add(infoEmail);
				}
				TipoDireccionesCorreoElectronico correos = new TipoDireccionesCorreoElectronico();
				correos.getCorreoElectronico().addAll(listaEmail);

				TipoInformacionUbicacionPersona infoUbicacion = new TipoInformacionUbicacionPersona();
				infoUbicacion.setDirecciones(direcciones);
				infoUbicacion.setTelefonos(telefonos);
				infoUbicacion.setCorreosElectronicos(correos);
				detalleProspecto.setInformacionUbicacion(infoUbicacion);

				// TODO CODIGO AGREGADO PARA EVITAR ERROR EN VALIDACIÓN
				detalleProspecto
						.setInformacionEconomica(new TipoInformacionEconomica());
				detalleProspecto
						.setInformacionAutorizacion(new TipoAutorizacion());

				TipoInformacionDivulgacion divulgacion = new TipoInformacionDivulgacion();
				divulgacion.setCanalDivulgacion(new String());
				divulgacion.setFechaDivulgacion(new String());

				detalleProspecto.setInformacionDivulgacion(divulgacion);

				TipoInformacionCuentaBeps infoCuenta = new TipoInformacionCuentaBeps();
				infoCuenta.setEstadoVinculacion(new String());

				detalleProspecto.setInformacionCuentaBeps(infoCuenta);

			}
		} catch (Exception e) {
			throw new Exception("Error mapeando datos del Prospecto. " + e);
		}

		return detalleProspecto;
	}

}
