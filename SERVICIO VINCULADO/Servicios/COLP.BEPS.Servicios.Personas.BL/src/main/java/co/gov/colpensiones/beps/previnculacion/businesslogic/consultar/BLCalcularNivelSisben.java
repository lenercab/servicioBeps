package co.gov.colpensiones.beps.previnculacion.businesslogic.consultar;

import java.util.HashMap;
import java.util.regex.Pattern;

import co.gov.colpensiones.beps.comunes.utilidades.Constantes;
import co.gov.colpensiones.beps.comunes.utilidades.ConstantesLoggerServicios;
import co.gov.colpensiones.beps.comunes.utilidades.Validador;
import co.gov.colpensiones.beps.dal.utilidades.DataTable;
import co.gov.colpensiones.beps.excepciones.DataAccessException;
import co.gov.colpensiones.beps.log.ConstantesLogger;
import co.gov.colpensiones.beps.log.LoggerBeps;
import co.gov.colpensiones.beps.previnculacion.businesslogic.BLPrevinculado;
import co.gov.colpensiones.beps.previnculacion.businesslogic.DAPrevinculado;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoEstadoEjecucion;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContexto;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionBasicaSisben;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoRespuestaCalculoNivelSisbenDTO;

/**
 * <b>Descripcion:</b> Clase encargada de la logica de negocio para realizar el cálculo de nivel sisben<br/>
 * <b>Caso de Uso:</b> GOP-AMB-1-FAB-08-CalcularNivelSisben <br/>
 * 
 * @author Helen Acero <hacero@heinsohn.com.co>
 * 
 * <b>Modificacion:</b> Se realiza ajuste de los datos de salida del proceso, para retornar el valor del nivel sisben calculado,
 * como una cadena<br/>
 * <b>Fecha:</b> 25/11/2014 <br/>
 * @author Yenny Ñustez <ynustez@heinsohn.com.co>
 */
public class BLCalcularNivelSisben extends BLPrevinculado {

    /** Objeto de acceso a datos */
    DAPrevinculado daPrevinculado = null;

    /**
     * Método contructor
     * 
     * @param log
     *            , objeto con el que se va a escribir el log en la BD
     */
    public BLCalcularNivelSisben(LoggerBeps log) {
        super(log);
        daPrevinculado = new DAPrevinculado();
    }

    /**
     * Método encargado de realizar el cálculo del nivel SISBEN de acuerdo con el área y puntaje asignados en la normatividad vigente
     * 
     * CU: GOP-AMB-1-FAB-08-CalcularNivelSisben
     * @param informacionContexto 
     * 			 datos encabezado
     * @param datosCalculo
     *            , datos de entrada requeridos para el cálculo de nivel sisben
     * @return respuesta del calculo y estado de la ejecución
     */
    public TipoRespuestaCalculoNivelSisbenDTO calcularNivelSisben(TipoInformacionContexto informacionContexto, TipoInformacionBasicaSisben datosCalculo) {

        TipoRespuestaCalculoNivelSisbenDTO response = new TipoRespuestaCalculoNivelSisbenDTO();

        /* registro en el log */
        HashMap<String, Object> payLoadTrace = new HashMap<String, Object>();
        HashMap<String, String> metaData = new HashMap<String, String>();
        HashMap<String, Object> payLoad = new HashMap<String, Object>();

        metaData.put(ConstantesLoggerServicios.METADATA_AREA, datosCalculo.getArea().toString());
        metaData.put(ConstantesLoggerServicios.METADATA_PUNTAJE, datosCalculo.getPuntaje().toString());

        payLoadTrace.put(ConstantesLogger.OBJETO_CONTEXTO_ENTRADA, informacionContexto);
        payLoadTrace.put(ConstantesLogger.OBJETO_NEGOCIO_ENTRADA, datosCalculo);
        if (informacionContexto != null) {
            metaData.put(ConstantesLoggerServicios.USUARIO_SISTEMA_EXTERNO, informacionContexto.getUsuarioSistemaExterno());
        }
        payLoad.putAll(metaData);

        try {

            log.trace(payLoadTrace, metaData);

            /* validar datos de entrada */
            StringBuffer lstErrores = new StringBuffer();
            lstErrores.append(new Validador<TipoInformacionBasicaSisben>().ValidarDataContract(datosCalculo));
            lstErrores.append(new Validador<TipoInformacionContexto>().ValidarDataContract(informacionContexto));
            lstErrores.append(this.validarDatosEntradaCalcularNivelSisben(informacionContexto,datosCalculo));

            /* validar si existen errores */
            if (lstErrores.toString().length() > 0) {
                payLoad.put(Constantes.MSJ_ERROR_LOG, lstErrores.toString());
                log.info(payLoad, metaData);
                response.setNivelSisben(null);
                response.setEstadoEjecucion(new TipoEstadoEjecucion());
                response.getEstadoEjecucion().setId(log.getIdTransaccion());
                response.getEstadoEjecucion().setCodigo(Constantes.COD_FORMATO_INVALIDO_OBLIGATORIO_NO_RECIBIDO);
                response.getEstadoEjecucion().setDescripcion(lstErrores.toString());
            } else {
                /* Se ejecuta el proceso de cálculo del nivel sisben */
                response = logicaCalcularNivel(datosCalculo);
            }
        } catch (DataAccessException e) {
            response.setNivelSisben(null);
            response.setEstadoEjecucion(respuestaErrorTecnicoServicio());
            generarLogError(e.getMetaData(), true, e);
        } catch (Exception e1) {
            response.setNivelSisben(null);
            response.setEstadoEjecucion(respuestaErrorTecnicoServicio());
            metaData.clear();
            metaData.put(ConstantesLoggerServicios.METADATA_AREA, datosCalculo.getArea().toString());
            metaData.put(ConstantesLoggerServicios.METADATA_PUNTAJE, datosCalculo.getPuntaje().toString());
            generarLogError(metaData, false, e1);
        } finally {
            payLoadTrace.put(ConstantesLogger.OBJETO_NEGOCIO_SALIDA, response);
            log.trace(payLoadTrace, metaData);
        }

        return response;
    }

    /**
     * Método encargado de realizar la lógica para calcular el nivel sisben
     * 
     * @param datosCalculo
     *            , datos de entrada requeridos para el cálculo de nivel sisben
     * @return respuesta del calculo y estado de la ejecución
     * @exception DataAccessException
     */
    private TipoRespuestaCalculoNivelSisbenDTO logicaCalcularNivel(TipoInformacionBasicaSisben datosCalculo) throws DataAccessException {
        TipoRespuestaCalculoNivelSisbenDTO response = new TipoRespuestaCalculoNivelSisbenDTO();

        /* realizar la consulta para calcular el nivel sisben */
        DataTable dtResult = daPrevinculado.calcularNivelSisben(datosCalculo.getArea(), datosCalculo.getPuntaje());
        Object resultadoCalculo = dtResult.getRows().get(0).getValue("Nivel_SISBEN");
        int nivel = (dtResult != null && resultadoCalculo != null) ? new Integer(resultadoCalculo.toString()) : 0;

        /* resultado exitoso, datos de resultado del proceso */
        response.setNivelSisben(Integer.toString(nivel));

        /* resultado exitoso, datos de asignacion al estado ejecucion */
        response.setEstadoEjecucion(new TipoEstadoEjecucion());
        response.getEstadoEjecucion().setCodigo(Constantes.COD_INVOCACION_EXITOSA);
        response.getEstadoEjecucion().setDescripcion(nivel == 0 ? Constantes.DESC_SIN_NIVEL_SISBEN : Constantes.DESC_INVOCACION_EXITOSA);
        response.getEstadoEjecucion().setId(log.getIdTransaccion());

        return response;
    }

    /**
     * Método que realiza las validaciones de obligatoriedad de los datos de entrada de la operación calcularNivelSisben, y tambien se
     * realizan las validaciones de negocio que se requieran
     * @param informacionContexto 
     * 
     * @param datosCalculo
     *            , datos de entrada
     * @return validaciones que no cumple, en caso contrario retorna vacio
     */
    private String validarDatosEntradaCalcularNivelSisben(TipoInformacionContexto informacionContexto, TipoInformacionBasicaSisben datosCalculo) {
        StringBuffer lst = new StringBuffer();

		/* Validar datos encabezado */
        if (informacionContexto != null) {
	        if (informacionContexto.getUsuarioSistemaExterno() == null) {
	        	lst.append(Constantes.MSJ_DATO_OBLIGATORIO.replaceAll(Constantes.PARAMETRO0, "usuarioSistemaExterno"));
	        } else {
	            if (!Pattern.compile(Constantes.ER_VALIDAR_USUARIO_SISTEMA).matcher(informacionContexto.getUsuarioSistemaExterno()).matches()) {
	            	lst.append(Constantes.MSJ_ESTRUCTURA_USUARIO_INVALIDA.replaceAll(Constantes.PARAMETRO0, "UsuarioSistemaExterno"));
	            }
	        }
        }
        /* validar obligatoriedades de los campos */
        if (datosCalculo == null) {
            lst.append(Constantes.MSJ_DATO_OBLIGATORIO.replaceAll(Constantes.PARAMETRO0, "Datos de Calculo"));
        } else {
            if (datosCalculo.getArea() == null) {
                lst.append(Constantes.MSJ_DATO_OBLIGATORIO.replaceAll(Constantes.PARAMETRO0, "Área"));
            }
            if (datosCalculo.getPuntaje() == null) {
                lst.append(Constantes.MSJ_DATO_OBLIGATORIO.replaceAll(Constantes.PARAMETRO0, "Puntaje"));
            }
        }

        return lst.toString();
    }
}
