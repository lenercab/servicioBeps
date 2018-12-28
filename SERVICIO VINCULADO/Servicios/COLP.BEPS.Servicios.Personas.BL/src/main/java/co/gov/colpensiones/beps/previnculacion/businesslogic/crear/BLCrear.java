package co.gov.colpensiones.beps.previnculacion.businesslogic.crear;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import co.gov.colpensiones.beps.comunes.enumeraciones.TipoConexionBaseDatosEnum;
import co.gov.colpensiones.beps.comunes.utilidades.Constantes;
import co.gov.colpensiones.beps.comunes.utilidades.ConstantesLoggerServicios;
import co.gov.colpensiones.beps.comunes.utilidades.DatabaseManager;
import co.gov.colpensiones.beps.comunes.utilidades.Util;
import co.gov.colpensiones.beps.comunes.utilidades.Validador;
import co.gov.colpensiones.beps.dal.utilidades.DataRow;
import co.gov.colpensiones.beps.dal.utilidades.DataTable;
import co.gov.colpensiones.beps.excepciones.DataAccessException;
import co.gov.colpensiones.beps.log.ConstantesLogger;
import co.gov.colpensiones.beps.log.LoggerBeps;
import co.gov.colpensiones.beps.previnculacion.businesslogic.BLPrevinculado;
import co.gov.colpensiones.beps.previnculacion.businesslogic.DAPrevinculado;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstado;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstadoEjecucion;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContexto;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoDatoTelefono;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionDireccionPersonaNatural;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionPrevinculado;

/**
 * <b>Descripcion:</b> Clase encargada de la logica de negocio para el proceso de registrar la 
 * pre-vinculacion de un solicitante al programa BEPS <br/>
 * 
 * <b>Caso de Uso:</b> GVI-VIN-1-FAB-05-CrearPrevinculado <br/>
 * 
 * @author Yenny Nustez Arevalo <ynustez@heinsohn.com.co>
 */
public class BLCrear extends BLPrevinculado{

	 /* Clase de acceso a datos */
    DAPrevinculado daPrevinculado = null;

    /**
     * Método contructor
     * 
     * @param log
     *            log con el que se va a escribir de la BD
     */
    public BLCrear(LoggerBeps log) {
    	super(log);
        daPrevinculado = new DAPrevinculado();
    }


    /**
     * Metodo que realiza la validacion de datos de entrada y ejecuta la logica del proceso,
     * para la creacion de la previnculacion de un solicitante sobre el sistema BEPS.
     * 
     * @param informacionContexto
     * @param informacionPrevinculado
     * @return estado de ejecución del proceso
     */
    public TipoEstadoEjecucion crear(TipoInformacionContexto informacionContexto, TipoInformacionPrevinculado informacionPrevinculado) {
		
    	TipoEstadoEjecucion response = null;

    	/* Manejo de Log */
        HashMap<String, Object> payLoadTrace = new HashMap<String, Object>();
        HashMap<String, Object> payLoad = new HashMap<String, Object>();
        HashMap<String, String> metaData = new HashMap<String, String>();                
		metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO,informacionPrevinculado.getInformacionBasicaSolicitante().getDocumento().getTipoDocumento());
		metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO,informacionPrevinculado.getInformacionBasicaSolicitante().getDocumento().getNumeroDocumento());
		metaData.put(ConstantesLoggerServicios.METADATA_NUM_RADICADO,informacionContexto.getIdCorrelacion());
		if (informacionContexto != null) {
            metaData.put(ConstantesLoggerServicios.USUARIO_SISTEMA_EXTERNO, informacionContexto.getUsuarioSistemaExterno());
        }
		payLoad.putAll(metaData);
                
		try {
			/* registro en el log */
			payLoadTrace.put(ConstantesLogger.OBJETO_CONTEXTO_ENTRADA, informacionContexto);
			payLoadTrace.put(ConstantesLogger.OBJETO_NEGOCIO_ENTRADA, informacionPrevinculado);			
			log.trace(payLoadTrace, metaData);

			/* validar datos de entrada */
			StringBuffer lstErrores = new StringBuffer();
			lstErrores.append(new Validador<TipoInformacionContexto>().ValidarDataContract(informacionContexto));
			lstErrores.append(new Validador<TipoInformacionPrevinculado>().ValidarDataContract(informacionPrevinculado));
			lstErrores.append(this.validarDatosEntrada(informacionContexto, informacionPrevinculado));
			
			/* validar existencia errores */
			if (lstErrores.toString().length() > 0) {
				payLoad.put(Constantes.MSJ_ERROR_LOG, lstErrores.toString());
                log.info(payLoad, metaData);
				response = respuestaNegocioServicio(Constantes.COD_FORMATO_INVALIDO_OBLIGATORIO_NO_RECIBIDO,lstErrores.toString());
			}else{		
				/* Se ejecuta la lógica de negocio */
				response = crearPrevinculado(informacionContexto, informacionPrevinculado); 				
			}
		}catch(DataAccessException e){    		
    		generarLogError(e.getMetaData(), true, e);
    		response = respuestaErrorTecnicoServicio();
    	}catch(Exception e1){
    		generarLogError(metaData, false, e1);
    		response = respuestaErrorTecnicoServicio();
    	} finally {
            payLoadTrace.put(ConstantesLogger.OBJETO_NEGOCIO_SALIDA, response);
            log.trace(payLoadTrace, metaData);
		}
		return response;
    }

    /**
     * Método encargado de realizar las validaciones de datos de entrada para la operación crear
     * 
     * @param informacionContexto
     *            , datos del contexto
     * @param informacionPrevinculado
     *            , datos del solicitante a ser previnculado
     * @return la cadena con las validaciones que no se cumplen, nulo o vacio si todas las validaciones se cumplen
     */
    private String validarDatosEntrada(TipoInformacionContexto informacionContexto, TipoInformacionPrevinculado informacionPrevinculado) throws DataAccessException {

        StringBuilder errores = new StringBuilder();

        /* validacion del contexto */
        if (informacionContexto == null) {
            errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "Contexto"));
        } else {
            /* validar usuario */
            if (informacionContexto.getUsuarioSistemaExterno() == null || informacionContexto.getUsuarioSistemaExterno().isEmpty()) {
                errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "UsuarioSistemaExterno"));
            }

            /* validar numero radicado */
            if (informacionContexto.getIdCorrelacion() == null || informacionContexto.getIdCorrelacion().isEmpty()) {
                errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "IdCorrelacion"));
            }else{
            	if (!Pattern.compile(Constantes.ER_VALIDAR_ID_CORRELACION).matcher(informacionContexto.getIdCorrelacion()).matches()){
        			errores.append(Constantes.MSJ_ESTRUCTURA_IDCORRELACION_INVALIDA.replaceAll(Constantes.PARAMETRO0, "IdCorrelacion"));
        		}
            }
        }

        /* Validacion tipo de documento */
        ArrayList<String> tiposDocumento = Util.getResourcePropertyArray(Constantes._HOMOLOGACION_COMUN_NAME, Constantes.PREFIJO_LLAVES_TIPO_DOCUMENTO);
        if (!tiposDocumento.contains(informacionPrevinculado.getInformacionBasicaSolicitante().getDocumento().getTipoDocumento())) {
            errores.append(Constantes.MSJ_ERROR_DATO_NO_VALIDO.replaceAll(Constantes.PARAMETRO0, "TipoDocumento")
                    + tiposDocumento.toString() + "\n");
        }
        
        /* Validacion Nombres y apellidos */
        if (informacionPrevinculado.getInformacionBasicaSolicitante().getNombresApellidos().getValue().getPrimerApellido() == null || 
        		informacionPrevinculado.getInformacionBasicaSolicitante().getNombresApellidos().getValue().getPrimerApellido().isEmpty()) {
            errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replaceAll(Constantes.PARAMETRO0, "PrimerApellido"));
        }
        
        if (informacionPrevinculado.getInformacionBasicaSolicitante().getNombresApellidos().getValue().getPrimerNombre() == null || 
        		informacionPrevinculado.getInformacionBasicaSolicitante().getNombresApellidos().getValue().getPrimerNombre().isEmpty()) {
            errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replaceAll(Constantes.PARAMETRO0, "PrimerNombre"));
        }
        
        /* Validacion género */
        ArrayList<String> tiposGenero = Util.getResourcePropertyArray(Constantes._HOMOLOGACION_COMUN_NAME, Constantes.PREFIJO_LLAVES_GENERO);
        if(!tiposGenero.contains(informacionPrevinculado.getInformacionBasicaSolicitante().getInformacionAdicional().getGenero())){
        	errores.append(Constantes.MSJ_ERROR_DATO_NO_VALIDO.replaceAll(Constantes.PARAMETRO0, "Genero") + tiposGenero.toString()+"\n");
        }
        
        /* Validacion Nivel SISBEN */
        JAXBElement<TipoEstado> infoSisben = null;
        if(informacionPrevinculado != null && informacionPrevinculado.getInformacionSisben() != null && informacionPrevinculado.getInformacionSisben().getNivel() != null){
        infoSisben= new JAXBElement<TipoEstado>(new QName("http://www.colpensiones.gov.co/beps/schemas/1.0/personas", "nivel"),
				TipoEstado.class, informacionPrevinculado.getInformacionSisben().getNivel().getValue());
        }
        if (infoSisben == null || infoSisben.getValue() == null || infoSisben.getValue().getCodigo() == null || infoSisben.getValue().getCodigo().isEmpty()) {
            errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replaceAll(Constantes.PARAMETRO0, "NivelSisben"));
		} else {
			DataTable data = daPrevinculado.consultarNivelesSisben();
			if (data != null && data.getRows().size() > 0) {
				boolean nivelParametrizado = false;
				String valoresNivelSisben = "";
				for (DataRow fila : data.getRows()) {
					if (infoSisben.getValue().getCodigo().equals(fila.getValue("vns_nivel").toString())) {
						nivelParametrizado = true;
					}
					valoresNivelSisben += fila.getValue("vns_nivel") + ",";
				}
				if (!nivelParametrizado) {
					errores.append(Constantes.MSJ_ERROR_DATO_NO_VALIDO.replaceAll(Constantes.PARAMETRO0, "NivelSisben")
							+ valoresNivelSisben.substring(0, valoresNivelSisben.length() - 1) + "\n");
				}
			} else {
				errores.append(Constantes.MSJ_PARAMETRIZACION_NIVEL_SISBEN);
			}
		}
        
        /* Validacion Canal */
        ArrayList<String> canales = Util.getResourcePropertyArray(Constantes._HOMOLOGACION_COMUN_NAME, Constantes.PREFIJO_LLAVES_CANALES);
        if(!canales.contains(informacionPrevinculado.getCanal())){
        	errores.append(Constantes.MSJ_ERROR_DATO_NO_VALIDO.replaceAll(Constantes.PARAMETRO0, "Canal") + canales.toString()+"\n");
        }
        
        /* Validación Fecha Nacimiento */
    	String fNacimiento = informacionPrevinculado.getInformacionBasicaSolicitante().getInformacionAdicional().getInformacionLugarNacimiento().getFechaNacimiento();
    	if (fNacimiento == null || fNacimiento.trim().isEmpty()) {
            errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "FechaNacimiento"));
        }else{
        	try {
        		SimpleDateFormat sdf = new SimpleDateFormat(Constantes.FORMATO_FECHA_AAAAMMDD);
        		Date dateNacimiento = sdf.parse(fNacimiento);
        		
	        		GregorianCalendar fechaNacimiento = new GregorianCalendar();
	        		fechaNacimiento.setTime(dateNacimiento); 
	        		GregorianCalendar fechaActual = new GregorianCalendar();
	        		fechaActual.setTime(new Date());
	        		
	        		/* Se calcula la edad */
	        		int edad = fechaActual.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);
	        	    if (fechaNacimiento.get(Calendar.MONTH) > fechaActual.get(Calendar.MONTH) || 
	        	    		(fechaActual.get(Calendar.MONTH) == fechaNacimiento.get(Calendar.MONTH) && 
	        	    		fechaNacimiento.get(Calendar.DATE) >fechaActual.get(Calendar.DATE))) {
	        	    	edad--;
	        	    }
	        		
	        		/* La diferencia entre fecha de nacimiento y la fecha actual debe ser mayor o igual a 18 años. */
	        		if(edad < Constantes.RANGO_DIFERENCIA_FECHA_NACIMIENTO){
	        			errores.append(Constantes.MSJ_FECHA_NACIMIENTO_INVALIDA.replaceAll(Constantes.PARAMETRO0, "FechaNacimiento"));	
	        		}  
	        		
	            	String fExpedicion = informacionPrevinculado.getInformacionBasicaSolicitante().getInformacionAdicional().getFechaExpedicionDocumentoIdenticacion();
	            	if (fExpedicion != null && !fExpedicion.trim().isEmpty()) {
	            		Date dateExpedicion = sdf.parse(fExpedicion);
		            	//La fecha de nacimiento no puede ser mayor a la fecha de expedición */
		    			if(dateNacimiento.compareTo(dateExpedicion) > 0){
		        			errores.append(Constantes.MSJ_FECHA_INVALIDA_EXPEDICION.replaceAll(Constantes.PARAMETRO0, "FechaNacimiento"));	
		        		}
	            	}
        	} catch (Exception ex) {
        		/* la excepcion generada por parseo de fechas ya es validada inicialmente, cuando se verifica */
        		/* longitud, tipo y máscara de los datos; por esta razón no se toma en cuenta. */
       		}
       }
    	
        /* validaciones para la ubicación */
        if (informacionPrevinculado != null && informacionPrevinculado.getInformacionUbicacion() != null) {
            /* valida obligatoriedad de la lista de direcciones de residencia */
            if (informacionPrevinculado.getInformacionUbicacion().getDirecciones() == null
                    || informacionPrevinculado.getInformacionUbicacion().getDirecciones().getDireccion() == null
                    || informacionPrevinculado.getInformacionUbicacion().getDirecciones().getDireccion().isEmpty()) {
                errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "ListaDireccion"));
            } else {
            	boolean indicadorEsPrincipal = false;
            	
                for (TipoInformacionDireccionPersonaNatural infoDir : informacionPrevinculado.getInformacionUbicacion()
                        .getDirecciones().getDireccion()) {
                	
                    /* validar obligatoriedad de dirección */
                    if (infoDir.getDireccion() == null || infoDir.getDireccion().trim().isEmpty()) {
                        errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "Direccion"));
                    }
                    
                    /* validar obligatoriedad de departamento */
                    if (infoDir.getDepartamento() == null || infoDir.getDepartamento().getCodigo() == null) {
                        errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "CodigoDepartamento"));
                    }

                    /* validar obligatoriedad de ciudad */
                    if (infoDir.getMunicipio() == null || infoDir.getMunicipio().getCodigo() == null) {
                        errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "CodigoMunicipio"));
                    }
                    
                    /* validar obligatoriedad de indicador */
                    if (infoDir.getEsPrincipal() == null || infoDir.getEsPrincipal().trim().isEmpty()) {
                        errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "IndicadorDireccionPrincipal"));
                    }else if(infoDir.getEsPrincipal().equals(Constantes.SI)){
                    	indicadorEsPrincipal = true;
                    }
                }
                
                if(!indicadorEsPrincipal)
                	errores.append(Constantes.MSJ_LISTA_DIRECCIONES_SIN_ID_PRINCIPAL.replaceAll(Constantes.PARAMETRO0, "IndicadorDireccionPrincipal"));
            }

            /* valida obligatoriedad de la lista de teléfonos */
            if (informacionPrevinculado.getInformacionUbicacion().getTelefonos() == null
                    || informacionPrevinculado.getInformacionUbicacion().getTelefonos().getTelefono() == null
                    || informacionPrevinculado.getInformacionUbicacion().getTelefonos().getTelefono().isEmpty()) {
                errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "ListaTelefonos"));
            } else {
            	boolean indicadorEsPrincipal = false;
                for (TipoDatoTelefono tipoDatoTel : informacionPrevinculado.getInformacionUbicacion().getTelefonos().getTelefono()) {
                    
                	/* validar estructura interna del teléfono */
                	if(tipoDatoTel.getTelefono() == null || tipoDatoTel.getTelefono().trim().isEmpty()){
                		errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "Telefono"));
                	}
                	
                	/* validar estructura interna del teléfono para el indicativo pais */
                    if (tipoDatoTel.getIndicativoPais() == null || tipoDatoTel.getIndicativoPais().trim().isEmpty()) {
                        errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "IndicativoPais"));
                    }
                    /* validar estructura interna del teléfono para el indicativo ciudad */
                    if (tipoDatoTel.getIndicativoCiudad() == null || tipoDatoTel.getIndicativoCiudad().trim().isEmpty()) {
                        errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "IndicativoCiudad"));
                    }
                    
                    /* validar obligatoriedad de indicador */
                    if (tipoDatoTel.getEsPrincipal() == null || tipoDatoTel.getEsPrincipal().trim().isEmpty()) {
                        errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "IndicadorTelefonoPrincipal"));
                    }else if(tipoDatoTel.getEsPrincipal().equals(Constantes.SI)){
                    	indicadorEsPrincipal = true;
                    }
                }
                
                if(!indicadorEsPrincipal)
                	errores.append(Constantes.MSJ_LISTA_TELEFONOS_SIN_ID_PRINCIPAL.replaceAll(Constantes.PARAMETRO0, "IndicadorTelefonoPrincipal"));
            }
        } else {
            errores.append(Constantes.MSJ_DATO_OBLIGATORIO.replace(Constantes.PARAMETRO0, "InformacionUbicacion"));
        }

        return errores.toString();
    }
    
	/**
	 * Método mediante el cual se realizan las inserciones de datos del previnculado sobre la base de datos del sistema.
	 * 
	 * @param informacionContexto
	 * @param informacionPrevinculado
	 * @return
	 */
	private TipoEstadoEjecucion crearPrevinculado(TipoInformacionContexto informacionContexto,
			TipoInformacionPrevinculado informacionPrevinculado) throws Exception {
		TipoEstadoEjecucion response = new TipoEstadoEjecucion();
		DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);

		try {
			daPrevinculado = new DAPrevinculado();
			database.beginTransaction();
			String generoHomologado = Util.getResourceProperty(Constantes._HOMOLOGACION_COMUN_NAME, Constantes.PREFIJO_LLAVES_GENERO
					+ informacionPrevinculado.getInformacionBasicaSolicitante().getInformacionAdicional().getGenero());
			
			/* Se valida si el previnculado existe */
			String idPrevinculado = daPrevinculado.consultarExistenciaPrevinculado(informacionPrevinculado.getInformacionBasicaSolicitante().getDocumento());
			if (idPrevinculado != null) {
				/* Si el vinculado ya existe como Vinculado ó Previnculado*/
				response = respuestaNegocioServicio(Constantes.COD_ERROR_LOGICA_NEGOCIO, Constantes.MSJ_SOLICITANTE_EXISTENTE);
			}else{

				/* Se inserta la información de persona */
				BigDecimal idPersona = daPrevinculado.crearPersonaPrevinculado(database, informacionContexto,
						informacionPrevinculado.getInformacionBasicaSolicitante(), generoHomologado);
	
				if (idPersona != null) {
	
					/* Se crea el Previnculado */
					daPrevinculado.crearPrevinculado(database, informacionContexto, informacionPrevinculado, idPersona);
					
					/* Se registra la información de Sisben del Previnculado */
					daPrevinculado.registrarInformacionSisben(database, informacionContexto, informacionPrevinculado.getInformacionSisben(), idPersona);
	
					/* Se asocian los datos de ubicación */
					if (informacionPrevinculado.getInformacionUbicacion().getDirecciones() != null
							&& informacionPrevinculado.getInformacionUbicacion().getDirecciones().getDireccion() != null
							&& !informacionPrevinculado.getInformacionUbicacion().getDirecciones().getDireccion().isEmpty()) {
	
						daPrevinculado.agregarContactoDireccionPrevinculado(database, informacionPrevinculado.getInformacionUbicacion()
								.getDirecciones().getDireccion(), idPersona, informacionContexto);
					}
					
		            if (informacionPrevinculado.getInformacionUbicacion().getCorreosElectronicos() != null
		                    && informacionPrevinculado.getInformacionUbicacion().getCorreosElectronicos().getCorreoElectronico() != null
		                    && !informacionPrevinculado.getInformacionUbicacion().getCorreosElectronicos().getCorreoElectronico().isEmpty()) {
	
		            	daPrevinculado.agregarContactoEmailPrevinculado(database, informacionPrevinculado.getInformacionUbicacion().getCorreosElectronicos().getCorreoElectronico(),
		            			idPersona, informacionContexto);
		            }
	
		            if (informacionPrevinculado.getInformacionUbicacion().getTelefonos() != null
		                    && informacionPrevinculado.getInformacionUbicacion().getTelefonos().getTelefono() != null
		                    && !informacionPrevinculado.getInformacionUbicacion().getTelefonos().getTelefono().isEmpty()) {
	
		            	daPrevinculado.agregarContactoTelefonosPrevinculado(database, informacionPrevinculado.getInformacionUbicacion().getTelefonos().getTelefono(), idPersona,
		                        informacionContexto);
		            }
		            
					/* se hace commit de la transaccion */
					database.commit();
					response = respuestaExitosaServicio();
				}
			}
    	}catch(DataAccessException e){  
    		generarLogError(e.getMetaData(), true, e);
    		response = respuestaErrorTecnicoServicio();
    		ejecutarRollbackTransaccion(database);
    		
    	}catch(Exception e1){
    		String mensajeError = e1.getMessage();
    		//Se realiza Rollback
    		mensajeError += ejecutarRollbackTransaccion(database);
    		throw new Exception(mensajeError, e1);
    	}

		return response;
	}
	
    /**
     * Método para ejecutar manualmente el rollback de una transaccion
     * @param database
     * @return
     */
    private String ejecutarRollbackTransaccion(DatabaseManager database){
    	try {
    		/* Se realiza rollback */
    		database.rollBack();
        } catch (Exception e2) {
        	return "Error al ejecutar la operación de Rollback : " + e2.getMessage();
        }    	
    	return "";
    }
}
