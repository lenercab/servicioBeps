package co.gov.colpensiones.beps.vinculacion.businesslogic;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import co.gov.colpensiones.beps.comunes.dto.InformacionBanderaDatosNoSensiblesDTO;
import co.gov.colpensiones.beps.comunes.enumeraciones.TipoConexionBaseDatosEnum;
import co.gov.colpensiones.beps.comunes.utilidades.Constantes;
import co.gov.colpensiones.beps.comunes.utilidades.ConstantesLoggerServicios;
import co.gov.colpensiones.beps.comunes.utilidades.DatabaseManager;
import co.gov.colpensiones.beps.comunes.utilidades.Util;
import co.gov.colpensiones.beps.dal.utilidades.CommandType;
import co.gov.colpensiones.beps.dal.utilidades.DataRow;
import co.gov.colpensiones.beps.dal.utilidades.DataStoredProcedure;
import co.gov.colpensiones.beps.dal.utilidades.DataTable;
import co.gov.colpensiones.beps.dal.utilidades.DbCommand;
import co.gov.colpensiones.beps.dal.utilidades.DbParameter;
import co.gov.colpensiones.beps.dal.utilidades.DbType;
import co.gov.colpensiones.beps.excepciones.DataAccessException;
import co.gov.colpensiones.beps.log.Serializer;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContexto;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoCiudad;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoCorreoElectronico;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoDatoTelefono;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoDepartamento;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoDirecciones;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoDireccionesCorreoElectronico;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoDocumentoPersonaNatural;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoFiltrosConsulta;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionAdicional;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionBasicaPersonaNatural;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionBasicaSolicitante;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionContacto;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionDatosGeneralesVinculado;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionDireccionPersonaNatural;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionGeneralVinculado;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionNovedadesBuc;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionUbicacionPersona;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionVinculado;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoTelefonos;
import co.gov.colpensiones.beps.vinculacion.businesslogic.crear.InformacionVinculado;

/**
 * <b>Descripcion:</b> Clase encargada de la lógica de negocio para actualizar información del Vinculado <br/>
 * <b>Caso de Uso:</b> GOP-VIN-1-FAB-11-ModificarInformacionSensibleVinculado <br/>
 * <b>Caso de Uso:</b> GVI-VIN-1-FAB-09-ConsultarViabilidadVinculacion <br/>
 * 
 * @author Yenny Nustez <ynustez@heinsohn.com.co>
 */
public class DAVinculado {

    /**
     * Método contructor
     * 
     */
    public DAVinculado() {
    }

    /**
     * Método para consulta de existencia de un vinculado <br>
     * <b>Caso de Uso:</b> GOP-VIN-1-FAB-11-ModificarInformacionSensibleVinculado <br/>
     * 
     * @param database
     *            Conexion a la Base de datos.
     * @param informacionVinculado
     *            Objeto que contiene la información del vinculado
     * @return Identificador del registro asociado al vinculado en Base de Datos
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public String consultarExistenciaVinculado(DatabaseManager database, TipoDocumentoPersonaNatural informacionVinculado)
            throws DataAccessException {
        String pkVinculado = null;
        DbCommand command = null;

        try {
            command = database.GetXmlCommand("PR_ConsultarVinculado");
            command.setCommandType(CommandType.Text);
            database.AddInXmlParameter(command, "@vpe_tipo_documento", informacionVinculado.getTipoDocumento());
            database.AddInXmlParameter(command, "@vpe_numero_documento", informacionVinculado.getNumeroDocumento());

            DataTable data = database.ExecuteDataTableCommandText(command);
            if (data.getRows().size() > 0) {
                pkVinculado = data.getRows().get(0).getValue("vpe_pk_id").toString();
            }

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, informacionVinculado.getTipoDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, informacionVinculado.getNumeroDocumento());
            throw new DataAccessException(null, metaData, e);
        }
        return pkVinculado;
    }

    /**
     * Método para consulta de existencia de un vinculado <br>
     * <b>Caso de Uso:</b> GOP-VIN-1-FAB-11-ModificarInformacionSensibleVinculado <br/>
     * 
     * @param database
     *            Conexion a la Base de datos.
     * @param informacionVinculado
     *            Objeto que contiene la información del vinculado
     * @return Identificador del registro asociado al vinculado en Base de Datos
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public String[] consultarExistenciaVinculadoDatosModificados(DatabaseManager database, TipoDocumentoPersonaNatural informacionVinculado)
            throws DataAccessException {
        String[] infoVinculado = null;
        DbCommand command = null;

        try {
            command = database.GetXmlCommand("PR_ConsultarVinculadoDatosModificados");
            command.setCommandType(CommandType.Text);
            database.AddInXmlParameter(command, "@vpe_tipo_documento", informacionVinculado.getTipoDocumento());
            database.AddInXmlParameter(command, "@vpe_numero_documento", informacionVinculado.getNumeroDocumento());

            DataTable data = database.ExecuteDataTableCommandText(command);
            if (data.getRows().size() > 0) {
                infoVinculado = new String[2];
                infoVinculado[0] = data.getRows().get(0).getValue("vpe_pk_id").toString();
                infoVinculado[1] = data.getRows().get(0).getValue(1).toString();
            }

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, informacionVinculado.getTipoDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, informacionVinculado.getNumeroDocumento());
            throw new DataAccessException(null, metaData, e);
        }
        return infoVinculado;
    }

    /**
     * Método para actualizacion de información de un vinculado, sobre el sitema BEPS <b>Caso de Uso:</b>
     * GOP-VIN-1-FAB-11-ModificarInformacionSensibleVinculado <br/>
     * 
     * @param database
     *            Conexión a la Base de datos.
     * @param informacionContexto
     *            Información de encabezado del servicio
     * @param informacionVinculado
     *            Información del vinculado
     * @param pkVinculado
     *            Llave primaria del registro asociado al vinculado en la tabla vinc_vinculado
     * @param generoHomologado
     *            Género del vinculado homologado al Sistema de Cuentas Individuales
     * @return true Si la actualización de información sensible del vinculado se ejecutó exitosamente sobre el sistema BEPS, false en caso
     *         contrario.
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public boolean actualizarInformacionVinculadoBeps(DatabaseManager database, TipoInformacionContexto informacionContexto,
            TipoInformacionBasicaSolicitante informacionVinculado, String pkVinculado, String generoHomologado) throws DataAccessException {
        DbCommand command = null;

        String primerApellido = null;
        String segundoApellido = null;
        String primerNombre = null;
        String segundoNombre = null;
        String fechaNacimiento = null;
        String municipioExpedicion = null;

        /*
         * La lógica de negocio para los campos nulos ó vacíos se maneja dentro del procedimiento almacenado
         * [vinculacion].[pr_vinc_actualizar_info_sensible] ya que los campos se actualizan o no, dependiendo de uno u otro valor, tal es el
         * caso de los campos segundoNombre y segundoApellido que se borran de la base de datos si llegan vacíos y no sufren ninguna
         * modificación si llegan nulos.
         */

        if (informacionVinculado.getNombresApellidos() != null && informacionVinculado.getNombresApellidos().getValue() != null) {
            primerApellido = informacionVinculado.getNombresApellidos().getValue().getPrimerApellido();
            segundoApellido = informacionVinculado.getNombresApellidos().getValue().getSegundoApellido();
            primerNombre = informacionVinculado.getNombresApellidos().getValue().getPrimerNombre();
            segundoNombre = informacionVinculado.getNombresApellidos().getValue().getSegundoNombre();
        }

        if (informacionVinculado.getInformacionAdicional() != null
                && informacionVinculado.getInformacionAdicional().getInformacionLugarNacimiento() != null) {
            fechaNacimiento = informacionVinculado.getInformacionAdicional().getInformacionLugarNacimiento().getFechaNacimiento();
        }

        if (informacionVinculado.getInformacionAdicional() != null) {
            municipioExpedicion = informacionVinculado.getInformacionAdicional().getMunicipioExpedicionDocumentoIdenticacion();
        }

        try {
            command = database.GetXmlCommand("StoredProcedure_ActualizarVinculado");
            command.setCommandType(CommandType.StoredProcedure);
            database.AddInXmlParameter(command, "@usuario", informacionContexto.getUsuarioSistemaExterno());
            database.AddInXmlParameter(command, "@primerApellido", primerApellido);
            database.AddInXmlParameter(command, "@segundoApellido", segundoApellido);
            database.AddInXmlParameter(command, "@primerNombre", primerNombre);
            database.AddInXmlParameter(command, "@segundoNombre", segundoNombre);
            database.AddInXmlParameter(command, "@genero", generoHomologado);
            database.AddInXmlParameter(command, "@fechaNacimiento", fechaNacimiento);
            database.AddInXmlParameter(command, "@fechaExpedicion", informacionVinculado.getInformacionAdicional()
                    .getFechaExpedicionDocumentoIdenticacion());
            database.AddInXmlParameter(command, "@municipioExpedicion", municipioExpedicion);
            database.AddInXmlParameter(command, "@numeroRadicado", informacionContexto.getIdCorrelacion());
            database.AddInXmlParameter(command, "@canalSistema", informacionContexto.getSistemaOrigen());
            database.AddInXmlParameter(command, "@idRegistroPersona", pkVinculado);

            /* Parametros de salida */
            database.AddOutParameter(command, "@codigoEjecucion", DbType.VARCHAR, 5);
            database.AddOutParameter(command, "@mensaje", DbType.VARCHAR, 100);

            /* Se obtienen los datos de la ejecucion del procedimiento */
            DataStoredProcedure data = database.executeGenericStoredProcedure(command);

            /* Se obtienen los parametros de salida */
            List<DbParameter> parametrosSalida = data.getParametrosSalida();

            if (parametrosSalida != null && parametrosSalida.size() > 0) {
                DbParameter parametroSalida = parametrosSalida.get(0);
                if (Constantes.COD_INVOCACION_EXITOSA.equals(parametroSalida.getParameterValue())) {
                    return true;
                } else {
                    parametroSalida = parametrosSalida.get(1);
                    throw new Exception("Error en la ejecución del SP vinculacion.pr_vinc_actualizar_info_sensible. "
                            + parametroSalida.getParameterValue());
                }
            }
        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, informacionVinculado.getDocumento().getTipoDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, informacionVinculado.getDocumento().getNumeroDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_RADICADO, informacionContexto.getIdCorrelacion());
            throw new DataAccessException(null, metaData, e);
        }
        return false;
    }

    /**
     * Método para la actualización de un vinculado sobre el Sistema de Cuentas Individuales<br>
     * <b>Caso de Uso:</b> GOP-VIN-1-FAB-11-ModificarInformacionSensibleVinculado <br/>
     * 
     * @param informacionVinculado
     *            Objeto que contiene la información del vinculado
     * @param fechaNacimiento
     *            fecha de nacimiento del vinculado homologada al Sistema de Cuentas Individuales
     * @return Lista con los parámetros de salida retornados al ejecutar el procedimiento almacenado de actualización de información
     *         sensible de Plenitud
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public List<DbParameter> actualizarInformacionVinculadoPlenitud(TipoInformacionBasicaSolicitante informacionVinculado,
            String fechaNacimiento) throws DataAccessException {

        List<DbParameter> parametrosSalida = new ArrayList<DbParameter>();
        try {
            DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.AS400);
            DbCommand command = database.GetXmlCommand("AS400StoredProcedure_Actualiza_Vinculado");
            command.setCommandType(CommandType.StoredProcedure);
            database.AddInXmlParameter(command, "ZIdentificacion", informacionVinculado.getDocumento().getNumeroDocumento());
            database.AddInXmlParameter(command, "ZTipoIdentifica", informacionVinculado.getDocumento().getTipoDocumento());
            database.AddInXmlParameter(command, "ZPrimerNombre", informacionVinculado.getNombresApellidos().getValue().getPrimerNombre());
            database.AddInXmlParameter(command, "ZPrimerApellido", informacionVinculado.getNombresApellidos().getValue()
                    .getPrimerApellido());
            database.AddInXmlParameter(command, "ZSegundoNombre", informacionVinculado.getNombresApellidos().getValue().getSegundoNombre());
            database.AddInXmlParameter(command, "ZSegundoApellido", informacionVinculado.getNombresApellidos().getValue()
                    .getSegundoApellido());
            database.AddInXmlParameter(command, "ZFechaNacimiento", fechaNacimiento);
            database.AddInXmlParameter(command, "ZSexo", informacionVinculado.getInformacionAdicional().getGenero());

            /* Parametros de salida */
            database.AddOutParameter(command, "ZCodigoError", DbType.CHAR, 7);
            database.AddOutParameter(command, "ZMensajeError", DbType.CHAR, 132);

            /* Se obtienen los datos de la ejecucion del procedimiento */
            DataStoredProcedure data = database.executeGenericStoredProcedure(command);

            /* Se obtienen los parametros de salida */
            parametrosSalida = data.getParametrosSalida();

        } catch (Exception e) {
            throw new DataAccessException(null, new HashMap<String, String>(), e);
        }
        return parametrosSalida;
    }

    /**
     * Método para consulta de viabilidad de un vinculado sobre el sistema BEPS <b>Caso de
     * Uso:</b>GOP-VIN-1-FAB-11-ModificarInformacionSensibleVinculado <br/>
     * 
     * @param tipoDocumento
     *            Objeto con la información de identificación del vinculado
     * @return DataStoredProcedure, objeto con los parametros de salida y una lista de objetos de tipo DataTable con los múltiples objetos
     *         de tipo ResultSet retornados por el procedimiento almacenado.
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public DataStoredProcedure consultarViabilidadVinculacion(TipoDocumentoPersonaNatural tipoDocumento, String sistemaOrigen)
            throws DataAccessException {
        DataStoredProcedure data = new DataStoredProcedure();
        try {
            DatabaseManager database = new DatabaseManager(
                    Constantes.SISTEMA_ORIGEN_BIZAGI.equals(sistemaOrigen.toUpperCase()) ? TipoConexionBaseDatosEnum.SQL_SERVER
                            : TipoConexionBaseDatosEnum.SQL_SERVER_ALT);
            DbCommand command = database.GetXmlCommand("StoredProcedure_Consulta_Viabilidad");
            command.setCommandType(CommandType.StoredProcedure);
            database.AddInXmlParameter(command, "@tipoDocumento", tipoDocumento.getTipoDocumento());
            database.AddInXmlParameter(command, "@numeroDocumento", tipoDocumento.getNumeroDocumento());

            // Parametros de salida
            database.AddOutParameter(command, "@tipoDatos", DbType.CHAR, 2);

            // Se obtienen los datos de la ejecucion del procedimiento
            data = database.executeListResultSet(command);

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, tipoDocumento.getTipoDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, tipoDocumento.getNumeroDocumento());
            throw new DataAccessException(null, metaData, e);
        }
        return data;
    }

    /**
     * Método encargado de acceder a la BD para consultar un prospecto, asignar la información de un prospecto a vinculado y eliminación del
     * prospecto
     * 
     * CU: GVI-VIN-1-FAB-02-RegistrarVinculado
     * 
     * @param database
     *            Conexión a la base de datos
     * @param informacionContexto
     *            , datos del contexto
     * @param informacionVinculado
     *            , datos del vinculado
     * @return la información que retorna el crear vinculado
     * @DataAccessException si se genera alguna excepción
     */
    public DataStoredProcedure crearVinculado(DatabaseManager database, TipoInformacionContexto informacionContexto,
            TipoInformacionGeneralVinculado informacionVinculado) throws DataAccessException {

        DbCommand command = null;
        DataStoredProcedure data = null;
        SimpleDateFormat formatoFechaVinculacion = new SimpleDateFormat(Constantes.FORMATO_FECHA_AAAAMMDD);

        try {
            java.sql.Date sqlFecha = new java.sql.Date(formatoFechaVinculacion.parse(informacionVinculado.getFechaVinculacion()).getTime());

            command = database.GetXmlCommand("StoredProcedure_CrearVinculado");
            command.setCommandType(CommandType.StoredProcedure);

            /* parametros que ingresan al SP */
            database.AddInXmlParameter(command, "@tipoDocumento", informacionVinculado.getIdentificacion().getTipoDocumento());
            database.AddInXmlParameter(command, "@numDocumento", informacionVinculado.getIdentificacion().getNumeroDocumento());
            database.AddInXmlParameter(command, "@actividadEconomicaPrin", informacionVinculado.getInformacionEconomica()
                    .getCodigoActividadEconomicaPrincipal());
            database.AddInXmlParameter(command, "@actividadEconomicaSec", informacionVinculado.getInformacionEconomica()
                    .getCodigoActividadEconomicaSecundaria());
            database.AddInXmlParameter(command, "@autorizaEnvioComunicacion",
                    Constantes.SI.equals(informacionVinculado.getTipoAutorizacion().getAutorizacionEnvioComunicacion()) ? 1 : 0);
            database.AddInXmlParameter(command, "@autorizaManejoInformacion",
                    Constantes.SI.equals(informacionVinculado.getTipoAutorizacion().getAutorizacionManejoInformacion()) ? 1 : 0);
            database.AddInXmlParameter(command, "@usuarioVinculacion", informacionVinculado.getUsuarioVinculacion());
            database.AddInXmlParameter(command, "@usuarioRegistro", informacionContexto.getUsuarioSistemaExterno());
            database.AddInXmlParameter(command, "@canalSistema", informacionContexto.getSistemaOrigen());
            database.AddInXmlParameter(command, "@canal", informacionVinculado.getCanal());
            database.AddInXmlParameter(command, "@numeroRadicado", informacionContexto.getIdCorrelacion());
            database.AddInXmlParameter(command, "@fechaVinculacion", sqlFecha);

            /* parámetros que salen del SP */
            database.AddOutParameter(command, "@identificadorVinculado", DbType.BIGINT, 8);
            database.AddOutParameter(command, "@idnovedad", DbType.BIGINT, 8);
            database.AddOutParameter(command, "@primerApellido", DbType.VARCHAR, 30);
            database.AddOutParameter(command, "@segundoApellido", DbType.VARCHAR, 30);
            database.AddOutParameter(command, "@primerNombre", DbType.VARCHAR, 30);
            database.AddOutParameter(command, "@segundoNombre", DbType.VARCHAR, 30);
            database.AddOutParameter(command, "@genero", DbType.CHAR, 1);
            database.AddOutParameter(command, "@fechaNacimiento", DbType.DATE, 8);

            data = database.executeListResultSet(command);
        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, informacionVinculado.getIdentificacion().getTipoDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, informacionVinculado.getIdentificacion().getNumeroDocumento());
            throw new DataAccessException(null, metaData, e);
        }
        return data;
    }

    /**
     * Método encargado de asociar la lista de direcciones al vincualdo
     * 
     * CU: GVI-VIN-1-FAB-02-RegistrarVinculado
     * 
     * @param database
     *            , la conexión con la BD
     * @param direcciones
     *            , lista de direcciones
     * @param idVinculado
     *            , identificador del vinculado en la BD
     * @param contexto
     *            , información de contexto
     * @return, arreglo de int que representan la ejecución en bach
     * @throws DataAccessException
     */
    public int[] agregarContactoDireccionVinculado(DatabaseManager database, List<TipoInformacionDireccionPersonaNatural> direcciones,
            Long idVinculado, TipoInformacionContexto contexto) throws DataAccessException {
        DbCommand command = null;
        int[] data = null;

        try {
            command = database.GetXmlCommand("PR_adicionarContactoDireccionVinculado");
            command.setCommandType(CommandType.Text);

            /* iterar lista de direccioens */
            for (TipoInformacionDireccionPersonaNatural direccion : direcciones) {
                database.AddInXmlParameter(command, "@idPersona", idVinculado);
                database.AddInXmlParameter(command, "@codMunicipio", direccion.getMunicipio().getCodigo());
                database.AddInXmlParameter(command, "@direccion", direccion.getDireccion());
                database.AddInXmlParameter(command, "@principal", Constantes.SI.equals(direccion.getEsPrincipal()) ? 1 : 0);
                database.AddInXmlParameter(command, "@usuario", contexto.getUsuarioSistemaExterno());
                database.AddInXmlParameter(command, "@radicado", contexto.getIdCorrelacion());
                database.AddInXmlParameter(command, "@canal", contexto.getSistemaOrigen());
            }

            data = database.executeBach(command, 7);

        } catch (Exception e) {
            HashMap<String, Object> payLoad = new HashMap<String, Object>();
            HashMap<String, String> metaData = new HashMap<String, String>();

            payLoad.put(Constantes.ERROR_BD, e.getMessage());
            payLoad.put(Constantes.TIPO_ERROR, Constantes.SERVICIO_WEB);
            payLoad.put(Constantes.URL, "/COLP.BEPS.Servicios.Vinculado/ContratoSvcVinculado");
            payLoad.put(ConstantesLoggerServicios.MENSAJE_ERROR, Constantes.ERROR + e.getMessage());

            metaData.put(ConstantesLoggerServicios.METADATA_CANTIDAD_DIRECCIONES, direcciones.size() + "");

            throw new DataAccessException(payLoad, metaData, e);
        }
        return data;
    }

    /**
     * Método encargado de asociar la lista de teléfonos al vincualdo
     * 
     * CU: GVI-VIN-1-FAB-02-RegistrarVinculado
     * 
     * @param database
     *            , la conexión con la BD
     * @param telefonos
     *            , lista de telefonos
     * @param idVinculado
     *            , identificador del vinculado
     * @param contexto
     *            , información del contexto
     * @return arreglo de int que representan la ejecución en bach
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public int[] agregarContactoTelefonosVinculado(DatabaseManager database, List<TipoDatoTelefono> telefonos, Long idVinculado,
            TipoInformacionContexto contexto) throws DataAccessException {
        DbCommand command = null;
        int[] data = null;

        try {
            command = database.GetXmlCommand("PR_adicionarContactoTelefonoVinculado");
            command.setCommandType(CommandType.Text);

            /* se itera sobre los telefonos para ingresarlos */
            for (TipoDatoTelefono telefono : telefonos) {
                database.AddInXmlParameter(command, "@idPersona", idVinculado);
                database.AddInXmlParameter(command, "@telefono", telefono.getTelefono());
                database.AddInXmlParameter(command, "@indPais",
                        (telefono.getIndicativoPais() != null) ? new Integer(telefono.getIndicativoPais()) : null);
                database.AddInXmlParameter(command, "@indCiudad",
                        (telefono.getIndicativoCiudad() != null) ? new Integer(telefono.getIndicativoCiudad()) : null);
                database.AddInXmlParameter(command, "@extension", telefono.getExtension());
                database.AddInXmlParameter(command, "@principal", Constantes.SI.equals(telefono.getEsPrincipal()) ? 1 : 0);
                database.AddInXmlParameter(command, "@tipo", telefono.getTipoTelefono());
                database.AddInXmlParameter(command, "@usuario", contexto.getUsuarioSistemaExterno());
                database.AddInXmlParameter(command, "@radicado", contexto.getIdCorrelacion());
                database.AddInXmlParameter(command, "@canal", contexto.getSistemaOrigen());
            }

            data = database.executeBach(command, 10);

        } catch (Exception e) {
            HashMap<String, Object> payLoad = new HashMap<String, Object>();
            HashMap<String, String> metaData = new HashMap<String, String>();
            payLoad.put(Constantes.ERROR_BD, e.getMessage());
            payLoad.put(Constantes.TIPO_ERROR, Constantes.SERVICIO_WEB);
            payLoad.put(Constantes.URL, "/COLP.BEPS.Servicios.Vinculado/ContratoSvcVinculado");
            payLoad.put(ConstantesLoggerServicios.MENSAJE_ERROR, Constantes.ERROR + e.getMessage());
            metaData.put(ConstantesLoggerServicios.METADATA_CANTIDAD_TELEFONOS, telefonos.size() + "");
            throw new DataAccessException(payLoad, metaData, e);
        }
        return data;
    }

    /**
     * Método encargado de asociar la lista de emails al vinculado
     * 
     * U: GVI-VIN-1-FAB-02-RegistrarVinculado
     * 
     * @param database
     *            , la conexión con la BD
     * @param emails
     *            , lista de email
     * @param identificador
     *            del vinculado
     * @param contexto
     *            , información del contexto
     * @return arreglo de int que representan la ejecución en bach
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public int[] agregarContactoEmailVinculado(DatabaseManager database, List<TipoCorreoElectronico> emails, Long idVinculado,
            TipoInformacionContexto contexto) throws DataAccessException {
        DbCommand command = null;
        int[] data = null;

        try {
            command = database.GetXmlCommand("PR_adicionarContactoEmailVinculado");
            command.setCommandType(CommandType.Text);

            /* itera sobre los email para ingresarlo */
            for (TipoCorreoElectronico email : emails) {
                database.AddInXmlParameter(command, "@idPersona", idVinculado);
                database.AddInXmlParameter(command, "@email", email.getDireccion());
                database.AddInXmlParameter(command, "@principal", Constantes.SI.equals(email.getEsPrincipal()) ? 1 : 0);
                database.AddInXmlParameter(command, "@usuario", contexto.getUsuarioSistemaExterno());
                database.AddInXmlParameter(command, "@radicado", contexto.getIdCorrelacion());
                database.AddInXmlParameter(command, "@canal", contexto.getSistemaOrigen());
            }

            data = database.executeBach(command, 6);

        } catch (Exception e) {
            HashMap<String, Object> payLoad = new HashMap<String, Object>();
            HashMap<String, String> metaData = new HashMap<String, String>();

            payLoad.put(Constantes.ERROR_BD, e.getMessage());
            payLoad.put(Constantes.TIPO_ERROR, Constantes.SERVICIO_WEB);
            payLoad.put(Constantes.URL, "/COLP.BEPS.Servicios.Vinculado/ContratoSvcVinculado");
            payLoad.put(ConstantesLoggerServicios.MENSAJE_ERROR, Constantes.ERROR + e.getMessage());

            metaData.put(ConstantesLoggerServicios.METADATA_CANTIDAD_CORREOS, emails.size() + "");

            throw new DataAccessException(payLoad, metaData, e);
        }

        return data;
    }

    /**
     * Método encargado de crear la cuenta individual
     * 
     * CU: GVI-VIN-1-FAB-02-RegistrarVinculado
     * 
     * @param informacionVinculado
     *            , información del vinculado
     * @return lista de parámetros retornados por plenitud
     * @throws DataAccessException
     *             , si se genera algún error en plenitud
     */
    public List<DbParameter> crearCuentaIndvidual(InformacionVinculado informacionVinculado) throws DataAccessException {
        List<DbParameter> parametrosSalida = null;
        try {
            DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.AS400);
            DbCommand command = database.GetXmlCommand("AS400StoredProcedure_CrearCuentaInividual");
            command.setCommandType(CommandType.StoredProcedure);
            database.AddInXmlParameter(command, "ZIdentificacion", informacionVinculado.getNumeroDocumento());
            database.AddInXmlParameter(command, "ZTipoIdentifica", informacionVinculado.getTipoDocumento());
            database.AddInXmlParameter(command, "ZPrimerNombre", informacionVinculado.getPrimerNombre());
            database.AddInXmlParameter(command, "ZPrimerApellido", informacionVinculado.getPrimerApellido());
            database.AddInXmlParameter(command, "ZSegundoNombre", informacionVinculado.getSegundoNombre());
            database.AddInXmlParameter(command, "ZSegundoApellido", informacionVinculado.getSegundoApellido());
            database.AddInXmlParameter(command, "ZFechaNacimiento", informacionVinculado.getFechaNacimiento());
            database.AddInXmlParameter(command, "ZSexo", informacionVinculado.getGenero());
            database.AddInXmlParameter(command, "ZFechaCreaCuenta", informacionVinculado.getFechaVinculacion());

            /* Parametros de salida */
            database.AddOutParameter(command, "ZEstadoSolicitud", DbType.CHAR, 3);
            database.AddOutParameter(command, "ZCodigoError", DbType.CHAR, 7);
            database.AddOutParameter(command, "ZMensajeError", DbType.CHAR, 132);

            /* Se obtienen los datos de la ejecucion del procedimiento */
            DataStoredProcedure data = database.executeGenericStoredProcedure(command);

            /* Se obtienen los parametros de salida */
            parametrosSalida = data.getParametrosSalida();

        } catch (Exception e) {
            parametrosSalida = null;
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, informacionVinculado.getTipoDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, informacionVinculado.getNumeroDocumento());

            HashMap<String, Object> payLoad = new HashMap<String, Object>();
            payLoad.put(ConstantesLoggerServicios.MENSAJE_ERROR, e.getMessage());
            throw new DataAccessException(payLoad, metaData, e);
        }
        return parametrosSalida;
    }

    /**
     * Método encargado en cambiar el estado de la creación de la cuenta de vinculado
     * 
     * CU: GVI-VIN-1-FAB-02-RegistrarVinculado
     * 
     * @param database
     *            , conexión a la BD
     * @param idPersona
     *            , identificador persona
     * @param seCreoCuenta
     *            , true = vinculado con cuenta individual, false = caso contrario
     * @throws DataAccessException
     *             , si se genera algún error
     */
    public void cambiarEstadoCuentaVinculado(DatabaseManager database, Long idPersona, boolean seCreoCuenta) throws DataAccessException {

        DbCommand command = null;

        try {
            command = database.GetXmlCommand("PR_CambiarEstadoCtaInd");
            command.setCommandType(CommandType.Text);

            /* parametros que ingresan al SP */
            database.AddInXmlParameter(command, "@estadoCuenta", seCreoCuenta ? 1 : 0);
            database.AddInXmlParameter(command, "@notificacionCuentaIndividual", seCreoCuenta ? 1 : 0);
            database.AddInXmlParameter(command, "@fechaNotificacion", seCreoCuenta ? new java.sql.Timestamp(new Date().getTime()) : null);
            database.AddInXmlParameter(command, "@idPersona", idPersona);

            database.executeNonQuery(command);

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_IDENTIFICADOR_PERSONA, idPersona + "");
            throw new DataAccessException(null, metaData, e);
        }
    }

    /**
     * Método para registro de información del un previnculado<br>
     * <b>Caso de Uso:</b> GVI-VIN-1-FAB-05-CrearPrevinculado <br/>
     * 
     * @param database
     *            Conexión a la base de datos
     * @param informacionContexto
     *            Información del encabezado del servicio
     * @param informacionPrevinculado
     *            Objeto con la información del vinculado
     * @param idPersona
     *            Identificador del registro asociado al vinculado en Base de datos
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public void actualizarInformacionNoSensibleVinculado(DatabaseManager database, TipoInformacionContexto informacionContexto,
            TipoInformacionDatosGeneralesVinculado informacionVinculado, String idPersona) throws DataAccessException {
        DbCommand command = null;
        try {
            command = database.GetXmlCommand("PR_ActualizarInformacionNoSensibleVinculado");
            command.setCommandType(CommandType.Text);
            database.AddInXmlParameter(command, "@autorizaManejoInformacion",
                    Constantes.SI.equals(informacionVinculado.getTipoAutorizacion().getAutorizacionManejoInformacion()) ? 1 : 0);
            database.AddInXmlParameter(command, "@autorizaEnvioComunicacion",
                    Constantes.SI.equals(informacionVinculado.getTipoAutorizacion().getAutorizacionEnvioComunicacion()) ? 1 : 0);
            database.AddInXmlParameter(command, "@actividadEconomicaPrincipal", informacionVinculado.getInformacionEconomica()
                    .getCodigoActividadEconomicaPrincipal());
            database.AddInXmlParameter(command, "@actividadEconomicaSecundaria", informacionVinculado.getInformacionEconomica()
                    .getCodigoActividadEconomicaSecundaria());
            database.AddInXmlParameter(command, "@usuarioUltimoCambio", informacionContexto.getUsuarioSistemaExterno());
            database.AddInXmlParameter(command, "@numeroRadicado", informacionContexto.getIdCorrelacion());
            database.AddInXmlParameter(command, "@canalVinculacion", informacionVinculado.getCanalModificacion());
            database.AddInXmlParameter(command, "@canalSistema", informacionContexto.getSistemaOrigen());
            database.AddInXmlParameter(command, "@idPersona", idPersona);

            /* Se ejecuta el query de inserción */
            database.executeInsertQuery(command, 1);

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_IDENTIFICADOR_PERSONA, idPersona.toString());
            metaData.put(ConstantesLoggerServicios.METADATA_USUARIO_ULTIMO_CAMBIO, informacionContexto.getUsuarioSistemaExterno());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_RADICADO, informacionContexto.getIdCorrelacion());
            metaData.put(ConstantesLoggerServicios.METADATA_CANAL_VINCULACION, informacionVinculado.getCanalModificacion());
            metaData.put(ConstantesLoggerServicios.METADATA_CANAL_SISTEMA, informacionContexto.getSistemaOrigen());
            throw new DataAccessException(null, metaData, e);
        }
    }

    /**
     * Método para la eliminación de direcciones existentes de un vinculado, exceptuando las ingresadas por parámetro.
     * 
     * @param database
     *            Conexión a la base de datos
     * @param Identificador
     *            del registro asociado a la persona en Base de datos
     * @param listaIdDirecciones
     *            Listado con los identificadores de registros de dirección que no deben ser eliminados
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public void eliminarDireccionesNoAsociadas(DatabaseManager database, String idPersona, String listaIdDirecciones)
            throws DataAccessException {
        DbCommand command = null;
        try {
            command = database.GetXmlCommand("PR_EliminarDireccionVinculado");
            command.setCommandType(CommandType.Text);
            database.AddInXmlParameter(command, "@idPersona", Integer.parseInt(idPersona.toString()));
            database.AddInXmlParameter(command, "@listaIdDireccion", listaIdDirecciones);

            /* Se ejecuta el query de eliminacion */
            database.executeInsertQuery(command, 1);

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_IDENTIFICADOR_PERSONA, idPersona);
            metaData.put(ConstantesLoggerServicios.METADATA_IDENTIFICADOR_DIRECCIONES, listaIdDirecciones);
            throw new DataAccessException(null, metaData, e);
        }
    }

    /**
     * Método para la actualización de direcciones de un vinculado
     * 
     * @param database
     *            Conexión a la base de datos
     * @param direcciones
     *            Listado de direcciones a actualizar
     * @param contexto
     *            Objeto con la información del encabezado del servicio
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public void actualizarContactoDireccion(DatabaseManager database, List<TipoInformacionDireccionPersonaNatural> direcciones,
            TipoInformacionContexto contexto) throws DataAccessException {
        DbCommand command = null;
        BigDecimal idRegistro = null;

        try {
            command = database.GetXmlCommand("PR_ActualizarContactoDireccionVinculado");
            command.setCommandType(CommandType.Text);

            /* iterar lista de direccioens */
            for (TipoInformacionDireccionPersonaNatural direccion : direcciones) {
                idRegistro = direccion.getIdentificador();
                database.AddInXmlParameter(command, "@codMunicipio", direccion.getMunicipio().getCodigo());
                database.AddInXmlParameter(command, "@direccion", direccion.getDireccion());
                database.AddInXmlParameter(command, "@principal", Constantes.SI.equals(direccion.getEsPrincipal()) ? 1 : 0);
                database.AddInXmlParameter(command, "@usuario", contexto.getUsuarioSistemaExterno());
                database.AddInXmlParameter(command, "@radicado", contexto.getIdCorrelacion());
                database.AddInXmlParameter(command, "@canal", contexto.getSistemaOrigen());
                database.AddInXmlParameter(command, "@idRegistro", idRegistro);
            }

            database.executeBach(command, 7);

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_CANTIDAD_DIRECCIONES, String.valueOf(direcciones.size()));
            metaData.put(ConstantesLoggerServicios.METADATA_IDENTIFICADOR_REGISTRO, idRegistro.toString());
            metaData.put(ConstantesLoggerServicios.METADATA_CANAL_MODIFICACION, contexto.getSistemaOrigen());
            throw new DataAccessException(null, metaData, e);
        }
    }

    /**
     * Método para la eliminación de telefonos existentes de un vinculado, exceptuando los ingresados por parámetro.
     * 
     * @param database
     *            Conexión a la base de datos
     * @param idPersona
     *            Identificador del registro asociado a la persona en Base de datos
     * @param listaIdTelefonos
     *            Listado con los identificadores de registros de teléfono que no deben ser eliminados
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public void eliminarTelefonosNoAsociados(DatabaseManager database, String idPersona, String listaIdTelefonos)
            throws DataAccessException {
        DbCommand command = null;
        try {
            command = database.GetXmlCommand("PR_EliminarTelefonoVinculado");
            command.setCommandType(CommandType.Text);
            database.AddInXmlParameter(command, "@idPersona", idPersona);
            database.AddInXmlParameter(command, "@listaIdTelefono", listaIdTelefonos);

            /* Se ejecuta el query de eliminacion */
            database.executeInsertQuery(command, 1);

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_IDENTIFICADOR_PERSONA, idPersona);
            metaData.put(ConstantesLoggerServicios.METADATA_IDENTIFICADOR_TELEFONOS, listaIdTelefonos);
            throw new DataAccessException(null, metaData, e);
        }
    }

    /**
     * Método para la actualización de teléfonos de un vinculado
     * 
     * @param database
     *            Conexión a la base de datos
     * @param telefonos
     *            Listado de telefonos a actualizar
     * @param contexto
     *            Objeto con la información del encabezado del servicio
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public void actualizarContactoTelefono(DatabaseManager database, List<TipoDatoTelefono> telefonos, TipoInformacionContexto contexto)
            throws DataAccessException {
        DbCommand command = null;
        BigDecimal idRegistro = null;
        try {
            command = database.GetXmlCommand("PR_ActualizarContactoTelefonoVinculado");
            command.setCommandType(CommandType.Text);

            /* se itera sobre los telefonos para actualizarlos */
            for (TipoDatoTelefono telefono : telefonos) {
                idRegistro = telefono.getIdentificador();
                database.AddInXmlParameter(command, "@telefono", telefono.getTelefono());
                database.AddInXmlParameter(command, "@tipo", telefono.getTipoTelefono());
                database.AddInXmlParameter(command, "@extension", telefono.getExtension());
                database.AddInXmlParameter(command, "@principal", Constantes.SI.equals(telefono.getEsPrincipal()) ? 1 : 0);
                database.AddInXmlParameter(command, "@usuario", contexto.getUsuarioSistemaExterno());
                database.AddInXmlParameter(command, "@radicado", contexto.getIdCorrelacion());
                database.AddInXmlParameter(command, "@canal", contexto.getSistemaOrigen());
                database.AddInXmlParameter(command, "@idRegistro", idRegistro);
            }

            database.executeBach(command, 8);

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_CANTIDAD_TELEFONOS, String.valueOf(telefonos.size()));
            metaData.put(ConstantesLoggerServicios.METADATA_IDENTIFICADOR_REGISTRO, idRegistro.toString());
            metaData.put(ConstantesLoggerServicios.METADATA_CANAL_MODIFICACION, contexto.getSistemaOrigen());
            throw new DataAccessException(null, metaData, e);
        }
    }

    /**
     * Método para la eliminación de correos electrónicos existentes de un vinculado, exceptuando los ingresados por parámetro.
     * 
     * @param database
     *            Conexión a la base de datos
     * @param idPersona
     *            Identificador del registro asociado a la persona en Base de datos
     * @param listaIdEmails
     *            Listado con los identificadores de registros de correos electrónicos que no deben ser eliminados
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public void eliminarEmailsNoAsociados(DatabaseManager database, String idPersona, String listaIdEmails) throws DataAccessException {
        DbCommand command = null;
        try {
            command = database.GetXmlCommand("PR_EliminarEmailVinculado");
            command.setCommandType(CommandType.Text);
            database.AddInXmlParameter(command, "@idPersona", idPersona);
            database.AddInXmlParameter(command, "@listaIdEmail", listaIdEmails);

            /* Se ejecuta el query de eliminacion */
            database.executeInsertQuery(command, 1);

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_IDENTIFICADOR_PERSONA, idPersona);
            metaData.put(ConstantesLoggerServicios.METADATA_IDENTIFICADOR_CORREOS, listaIdEmails);
            throw new DataAccessException(null, metaData, e);
        }
    }

    /**
     * Método para la actualización de correos electrónicos de un vinculado
     * 
     * @param database
     *            Conexión a la base de datos
     * @param correos
     *            Listado de correos a actualizar
     * @param contexto
     *            Objeto con la información del encabezado del servicio
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public void actualizarContactoEmail(DatabaseManager database, List<TipoCorreoElectronico> correos, TipoInformacionContexto contexto)
            throws DataAccessException {
        DbCommand command = null;
        BigDecimal idRegistro = null;
        try {
            command = database.GetXmlCommand("PR_ActualizarContactoEmailVinculado");
            command.setCommandType(CommandType.Text);

            /* itera sobre los email para ingresarlo */
            for (TipoCorreoElectronico email : correos) {
                idRegistro = email.getIdentificador();
                database.AddInXmlParameter(command, "@email", email.getDireccion());
                database.AddInXmlParameter(command, "@principal", Constantes.SI.equals(email.getEsPrincipal()) ? 1 : 0);
                database.AddInXmlParameter(command, "@usuario", contexto.getUsuarioSistemaExterno());
                database.AddInXmlParameter(command, "@radicado", contexto.getIdCorrelacion());
                database.AddInXmlParameter(command, "@canal", contexto.getSistemaOrigen());
                database.AddInXmlParameter(command, "@idRegistro", idRegistro);
            }

            database.executeBach(command, 6);

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_CANTIDAD_CORREOS, String.valueOf(correos.size()));
            metaData.put(ConstantesLoggerServicios.METADATA_IDENTIFICADOR_REGISTRO, idRegistro.toString());
            metaData.put(ConstantesLoggerServicios.METADATA_CANAL_MODIFICACION, contexto.getSistemaOrigen());
            throw new DataAccessException(null, metaData, e);
        }
    }

    /**
     * Método donde se consulta el género
     * 
     * @param generoBeps
     *            , genero en el sistema beps
     * @return la información asociada al genero seleccionado
     * @throws DataAccessException
     *             , si se genera una excepción
     */
    public DataTable consultarGenero(String generoBeps) throws DataAccessException {

        DbCommand command = null;
        DataTable dataTable = null;
        DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);

        try {
            command = database.GetXmlCommand("PR_ConsultarGenero");
            command.setCommandType(CommandType.Text);

            /* parametros que ingresan */
            database.AddInXmlParameter(command, "@generoBeps", generoBeps);
            database.AddInXmlParameter(command, "@generoBepsNum", generoBeps);

            dataTable = database.ExecuteDataTable(command);

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_GENERO_BEPS, generoBeps);

            throw new DataAccessException(null, metaData, e);
        }
        return dataTable;
    }

    /**
     * Método para la consulta de parámetros de vinculación
     * 
     * @param nombreParametro
     *            Nombre del parámetro
     * @return Valor asociado al parámetro consultado
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public String consultarParametroVinculacion(String nombreParametro) throws DataAccessException {

        DbCommand command = null;
        DataTable dataTable = null;

        String valorParametro = null;

        DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);

        try {
            command = database.GetXmlCommand("PR_ConsultarParametroVinculacion");
            command.setCommandType(CommandType.Text);

            /* parametros que ingresan */
            database.AddInXmlParameter(command, "@nombreParametro", nombreParametro);

            dataTable = database.ExecuteDataTable(command);

            if (dataTable.getRows().size() > 0) {
                valorParametro = dataTable.getRows().get(0).getValue("pg_valor").toString();
            }

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_PARAMETRO, nombreParametro);

            throw new DataAccessException(null, metaData, e);
        }
        return valorParametro;
    }

    /**
     * Método mediante el cual se consulta la información de un vinculado por tipo y número de documento.
     * 
     * @param database
     *            Conexión a la base de datos
     * @param informacionVinculado
     *            Objeto que contiene los datos de identificación del vinculado
     * @return Objeto con la información del vinculado
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public TipoInformacionVinculado consultarInformacionVinculado(DatabaseManager database, TipoDocumentoPersonaNatural informacionVinculado)
            throws DataAccessException {

        TipoInformacionVinculado infoVinculado = null;
        DbCommand command = null;

        try {

            command = database.GetXmlCommand("StoredProcedure_ConsultarInformacionVinculado");
            command.setCommandType(CommandType.StoredProcedure);

            /* parametros que ingresan al SP */
            database.AddInXmlParameter(command, "@tipoDocumento", informacionVinculado.getTipoDocumento());
            database.AddInXmlParameter(command, "@numDocumento", informacionVinculado.getNumeroDocumento());

            /* parámetros que salen del SP */
            database.AddOutParameter(command, "@primerApellido", DbType.VARCHAR, 30);
            database.AddOutParameter(command, "@segundoApellido", DbType.VARCHAR, 30);
            database.AddOutParameter(command, "@primerNombre", DbType.VARCHAR, 30);
            database.AddOutParameter(command, "@segundoNombre", DbType.VARCHAR, 30);
            database.AddOutParameter(command, "@genero", DbType.CHAR, 1);
            database.AddOutParameter(command, "@fechaNacimiento", DbType.DATE, 8);
            database.AddOutParameter(command, "@fechaVinculacion", DbType.DATE, 8);
            database.AddOutParameter(command, "@telefono", DbType.CHAR, 20);
            database.AddOutParameter(command, "@codDaneDepto", DbType.CHAR, 2);
            database.AddOutParameter(command, "@codDaneMunc", DbType.CHAR, 5);
            database.AddOutParameter(command, "@direccion", DbType.CHAR, 80);
            database.AddOutParameter(command, "@email", DbType.CHAR, 255);
            database.AddOutParameter(command, "@celular", DbType.CHAR, 20);

            DataStoredProcedure data = database.executeListResultSet(command);

            List<DbParameter> lista = data.getParametrosSalida();

            if (lista.size() > 0) {

                infoVinculado = new TipoInformacionVinculado();

                TipoDocumentoPersonaNatural identificacion = new TipoDocumentoPersonaNatural();
                TipoInformacionBasicaPersonaNatural nombresApellidos = new TipoInformacionBasicaPersonaNatural();
                TipoInformacionAdicional infoAdicional = new TipoInformacionAdicional();
                TipoInformacionContacto infoContacto = new TipoInformacionContacto();
                TipoDatoTelefono telefono = new TipoDatoTelefono();
                TipoDatoTelefono celular = new TipoDatoTelefono();
                TipoDepartamento deptoResidencia = new TipoDepartamento();
                TipoCiudad municipioResidencia = new TipoCiudad();
                TipoCorreoElectronico correo = new TipoCorreoElectronico();

                identificacion.setTipoDocumento(informacionVinculado.getTipoDocumento());
                identificacion.setNumeroDocumento(informacionVinculado.getNumeroDocumento());
                nombresApellidos.setPrimerApellido(lista.get(0).getParameterValue() != null ? ((String) lista.get(0).getParameterValue())
                        .toUpperCase() : "");
                nombresApellidos.setSegundoApellido(lista.get(1).getParameterValue() != null ? ((String) lista.get(1).getParameterValue())
                        .toUpperCase() : "");
                nombresApellidos.setPrimerNombre(lista.get(2).getParameterValue() != null ? ((String) lista.get(2).getParameterValue())
                        .toUpperCase() : "");
                nombresApellidos.setSegundoNombre(lista.get(3).getParameterValue() != null ? ((String) lista.get(3).getParameterValue())
                        .toUpperCase() : "");

                /* Homologacion genero */
                ArrayList<String> tiposGenero = Util.getResourcePropertyArray(Constantes._HOMOLOGACION_COMUN_NAME,
                        Constantes.PREFIJO_LLAVES_GENERO);

                String generoHomologado = "", generoEncontrado = lista.get(4).getParameterValue() != null ? ((String) lista.get(4)
                        .getParameterValue()) : "";

                for (String genero : tiposGenero) {
                    String generoProp = Util.getResourceProperty(Constantes._HOMOLOGACION_COMUN_NAME, Constantes.PREFIJO_LLAVES_GENERO
                            + genero);
                    if (generoProp.equals(generoEncontrado)) {
                        generoHomologado = genero;
                        break;
                    }
                }

                infoAdicional.setGenero(generoHomologado);

                DateFormat fechaDF = new SimpleDateFormat(Constantes.FORMATO_FECHA_AAAAMMDD);
                if (lista.get(5).getParameterValue() != null) {
                    Date fechaBD = (Date) lista.get(5).getParameterValue();
                    infoAdicional.setFechaNacimiento(fechaDF.format(fechaBD));
                }

                if (lista.get(6).getParameterValue() != null) {
                    Date fechaBD = (Date) lista.get(6).getParameterValue();
                    infoAdicional.setFechaVinculacion(fechaDF.format(fechaBD));
                }

                telefono.setTelefono(lista.get(7).getParameterValue() != null ? ((String) lista.get(7).getParameterValue()).toUpperCase()
                        : "");
                deptoResidencia.setCodigo(lista.get(8).getParameterValue() != null ? ((String) lista.get(8).getParameterValue())
                        .toUpperCase() : "");
                municipioResidencia.setCodigo(lista.get(9).getParameterValue() != null ? ((String) lista.get(9).getParameterValue())
                        .toUpperCase() : "");
                infoContacto.setDireccion(lista.get(10).getParameterValue() != null ? ((String) lista.get(10).getParameterValue())
                        .toUpperCase() : "");
                correo.setDireccion(lista.get(11).getParameterValue() != null ? ((String) lista.get(11).getParameterValue()).toUpperCase()
                        : "");
                celular.setTelefono(lista.get(12).getParameterValue() != null ? ((String) lista.get(12).getParameterValue()).toUpperCase()
                        : "");

                infoContacto.setCelular(celular);
                infoContacto.setCorreoElectronico(correo);
                infoContacto.setDepartamentoResidencia(deptoResidencia);
                infoContacto.setMunicipioResidencia(municipioResidencia);
                infoContacto.setTelefono(telefono);

                infoVinculado.setIdentificacion(identificacion);
                infoVinculado.setNombresApellidos(nombresApellidos);
                infoVinculado.setInformacionAdicional(infoAdicional);
                infoVinculado.setInformacionContacto(infoContacto);
            }

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, informacionVinculado.getTipoDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, informacionVinculado.getNumeroDocumento());
            throw new DataAccessException(null, metaData, e);
        }
        return infoVinculado;
    }

    /**
     * Método mediante el cual se consulta el identificador del registro asociado a un vinculado, de acuerdo a su número de identificación
     * 
     * @param database
     *            Conexión a la base de datos
     * @param informacionVinculado
     *            Objeto con la información de identificación del vinculado
     * @return Identificador del registro asociado al vinculado
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public String consultarVinculadoNumIdentificacion(DatabaseManager database, TipoDocumentoPersonaNatural informacionVinculado)
            throws DataAccessException {
        String pkVinculado = null;
        DbCommand command = null;

        try {
            command = database.GetXmlCommand("PR_ConsultarVinculadoNumIdentificacion");
            command.setCommandType(CommandType.Text);
            database.AddInXmlParameter(command, "@vpe_numero_documento", informacionVinculado.getNumeroDocumento());

            DataTable data = database.ExecuteDataTableCommandText(command);
            if (data.getRows().size() > 0) {
                pkVinculado = data.getRows().get(0).getValue("vpe_pk_id").toString();
            }

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, informacionVinculado.getTipoDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, informacionVinculado.getNumeroDocumento());
            throw new DataAccessException(null, metaData, e);
        }
        return pkVinculado;
    }

    /**
     * Método para la consulta de información de la cuenta individual de un vinculado
     * 
     * @param filtrosConsulta
     *            Fitros de consulta
     * @return Objeto con el resultado de la consulta
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public DataStoredProcedure consultarInformacionCuentaIndividual(TipoFiltrosConsulta filtrosConsulta) throws DataAccessException {

        DataStoredProcedure data = null;

        try {

            /* Homologacion tipo de documento */
            String tipoDocumento = Util.getResourceProperty(Constantes._HOMOLOGACION_NAME, Constantes.PREFIJO_LLAVES_TIPO_DOCUMENTO
                    + filtrosConsulta.getIdentificacion().getTipoDocumento());

            DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.AS400);

            DbCommand command = database.GetXmlCommand("AS400StoredProcedure_ConsultarSaldosMovimientos");
            command.setCommandType(CommandType.StoredProcedure);

            database.AddInXmlParameter(command, "ZETIPODOCUMENTO", tipoDocumento);
            database.AddInXmlParameter(command, "ZEIDENTIFICA", filtrosConsulta.getIdentificacion().getNumeroDocumento());
            database.AddInXmlParameter(command, "ZEFECHAINICIAL", filtrosConsulta.getFechaInicial());
            database.AddInXmlParameter(command, "ZEFECHAFINAL", filtrosConsulta.getFechaFinal());

            /* Parametros de salida */
            database.AddOutParameter(command, "ZNUMCUENTA", DbType.DECIMAL, 11);
            database.AddOutParameter(command, "ZSESTADOCUENTA", DbType.CHAR, 1);
            database.AddOutParameter(command, "ZSFECHAAPERTURA", DbType.CHAR, 8);
            database.AddOutParameter(command, "ZSFECHACANCELA", DbType.CHAR, 8);
            database.AddOutParameter(command, "ZSMOTIVOCANCELA", DbType.CHAR, 30);
            database.AddOutParameter(command, "ZSVALTASAEANUAL", DbType.DECIMAL, 5);
            database.AddOutParameter(command, "ZSAPORTESANTES", DbType.DECIMAL, 15);
            database.AddOutParameter(command, "ZSAPORTESDURANT", DbType.DECIMAL, 15);
            database.AddOutParameter(command, "ZSTOTALAPORTES", DbType.DECIMAL, 15);
            database.AddOutParameter(command, "ZSALACUMINTANT", DbType.DECIMAL, 15);
            database.AddOutParameter(command, "ZSALACUMINTDUR", DbType.DECIMAL, 15);
            database.AddOutParameter(command, "ZSALACUMINTTOT", DbType.DECIMAL, 15);
            database.AddOutParameter(command, "ZSADMINISTRACIO", DbType.DECIMAL, 15);
            database.AddOutParameter(command, "ZSGMF", DbType.DECIMAL, 15);
            database.AddOutParameter(command, "ZRETIROSDURANTE", DbType.DECIMAL, 15);
            database.AddOutParameter(command, "ZSALDOFINTOT", DbType.DECIMAL, 15);
            database.AddOutParameter(command, "ZSINCENTIVOPER", DbType.DECIMAL, 15);
            database.AddOutParameter(command, "ZSALACUMUNIANT", DbType.DECIMAL, 21);
            database.AddOutParameter(command, "ZSVALORUNIINI", DbType.DECIMAL, 21);
            database.AddOutParameter(command, "ZSTOTAUNIAPORT", DbType.DECIMAL, 21);
            database.AddOutParameter(command, "ZSVALORUNIFINAL", DbType.DECIMAL, 21);
            database.AddOutParameter(command, "ZSCODERROR", DbType.CHAR, 7);
            database.AddOutParameter(command, "ZSMENSAJEERROR", DbType.CHAR, 132);

            /* Se obtienen los datos de la ejecucion del procedimiento */
            data = database.executeListResultSet(command);

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, filtrosConsulta.getIdentificacion().getTipoDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, filtrosConsulta.getIdentificacion().getNumeroDocumento());

            HashMap<String, Object> payLoad = new HashMap<String, Object>();
            payLoad.put(ConstantesLoggerServicios.MENSAJE_ERROR, e.getMessage());
            throw new DataAccessException(payLoad, metaData, e);
        }
        return data;
    }

    /**
     * Método para la consulta de tipos de identificación del sistema BEPS
     * 
     * @return Listado con los tipos de documento parametrizados en el sistema BEPS
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public ArrayList<String> consultarTiposIdentificacion() throws DataAccessException {

        DbCommand command = null;
        ArrayList<String> tiposDocumento = new ArrayList<String>();

        DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);

        try {
            command = database.GetXmlCommand("PR_ConsultarTipoIdentificacion");
            command.setCommandType(CommandType.Text);

            DataTable data = database.ExecuteDataTableCommandText(command);

            for (DataRow fila : data.getRows()) {
                if (fila.getValue("vti_cod_beps") != null) {
                    tiposDocumento.add(fila.getValue("vti_cod_beps").toString());
                }
            }

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            throw new DataAccessException(null, metaData, e);
        }
        return tiposDocumento;
    }

    /**
     * Método mediante el cual se consulta un parametro BUC a partir del nombre.
     * 
     * @param database
     *            Conexión a la base de datos
     * @param String
     *            nombre del parametro
     * @return String Valor del parametro
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public String consultarParametroBucPorNombre(DatabaseManager database, String nombreParametro) throws DataAccessException {
        String pkVinculado = null;
        DbCommand command = null;

        try {
            command = database.GetXmlCommand("PR_ConsultarParametrosBucPorNombre");
            command.setCommandType(CommandType.Text);
            database.AddInXmlParameter(command, "@vinc_param_nombre", nombreParametro);

            DataTable data = database.ExecuteDataTableCommandText(command);
            if (data.getRows().size() > 0) {
                pkVinculado = data.getRows().get(0).getValue("vpb_valor").toString();
            }

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_PARAMETRO, nombreParametro);
            throw new DataAccessException(null, metaData, e);
        }
        return pkVinculado;
    }

    /**
     * Método mediante el cual se inserta la novedad en BUC 
     * 
     * @param database
     *            Conexión a la base de datos
     * @param usuario
     *            usuario que realizó la modificación
     * @return String Valor del parametro
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public Long insertarBitacoraNovedadesBUC(DatabaseManager database, String usuario, TipoInformacionNovedadesBuc datosNovedadActualizada,
            Long idVinculado, Boolean idBUC) throws DataAccessException {
        BigDecimal idNovedad = null;
        DbCommand command = null;

        try {
            command = database.GetXmlCommand("PR_InsertarNovedadBuc");
            command.setCommandType(CommandType.Text);
            database.AddInXmlParameter(command, "@id_vinculado_sgb", idVinculado);
            database.AddInXmlParameter(command, "@tipo_novedad", Constantes.TIPO_NOVEDAD_ACTUALIZACION_BUC);
            database.AddInXmlParameter(command, "@estado_novedad", Constantes.ESTADO_INICIAL_NOVEDAD_BUC);
            database.AddInXmlParameter(command, "@usuario_negocio", usuario);
            if (idBUC) {
                database.AddInXmlParameter(command, "@datos_novedad", Serializer.serializar(datosNovedadActualizada));
            } else {
                database.AddInXmlParameter(command, "@datos_novedad", null);
            }

            Collection<Object> llavesGeneradas = database.executeInsertQuery(command, 1);
            if (llavesGeneradas != null && !llavesGeneradas.isEmpty()) {
                Iterator itr = llavesGeneradas.iterator();
                if (itr.hasNext()) {
                    Object[] obj = (Object[]) itr.next();
                    idNovedad = (BigDecimal) obj[0];
                }
            }

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_PARAMETRO, idVinculado.toString());
            throw new DataAccessException(null, metaData, e);
        }
        return idNovedad.longValue();
    }

    /**
     * Método consulta las novedades pendientes que puede tener un vinculado en la bitacora de novedades BUC (vinc_bitacora_novedades_buc)
     * 
     * @param database
     *            Conexión a la base de datos
     * @param String
     *            nombre del parametro
     * @return String Valor del parametro
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public Boolean consultarNovedadesPendientesBUC(DatabaseManager database, Long idVinculado) throws DataAccessException {

        Boolean novedadPendiente = Boolean.FALSE;
        Long idNovedad = null;
        Long numRegistros = (long) 0;
        DbCommand command = null;

        try {
            command = database.GetXmlCommand("PR_consultarNovedadesPendientesBUC");
            command.setCommandType(CommandType.Text);
            database.AddInXmlParameter(command, "@id_vinculado_sgb", idVinculado);
            database.AddInXmlParameter(command, "@estado_novedad1", Constantes.ESTADO_INICIAL_NOVEDAD_BUC);
            database.AddInXmlParameter(command, "@estado_novedad2", Constantes.ESTADO_FALLIDO_INDISPONIBILIDAD_BUC);

            DataTable data = database.ExecuteDataTableCommandText(command);
            if (data.getRows().size() > 1) {
                novedadPendiente = Boolean.TRUE;
            }

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_PARAMETRO, idVinculado.toString());
            throw new DataAccessException(null, metaData, e);
        }
        return novedadPendiente;
    }

    /**
     * Método que consulta las direcciones o telefonos auxiliares del vinculado segun unas banderas.
     * 
     * @param database
     *            DataBaseManager
     * @param informacionVinculado
     *            informacion del vinculado
     * @param idVinculado
     *            id del vinculado
     * @return Objeto con el resultado de la consulta
     * @throws DataAccessException
     *             Excepción con el error de acceso a los datos en bd.
     */
    public TipoInformacionUbicacionPersona consultarInformacionDatosNoSensibles(DatabaseManager database,
            TipoInformacionDatosGeneralesVinculado informacionVinculado, Long idVinculado) throws DataAccessException {

        DataStoredProcedure data = null;
        TipoInformacionUbicacionPersona tipoInfoUbicacion = null;

        try {

            tipoInfoUbicacion = new TipoInformacionUbicacionPersona();
            TipoDirecciones tipoDirecciones = new TipoDirecciones();
            tipoInfoUbicacion.setDirecciones(tipoDirecciones);
            TipoDireccionesCorreoElectronico direccionesCorreoElectronico = new TipoDireccionesCorreoElectronico();
            tipoInfoUbicacion.setCorreosElectronicos(direccionesCorreoElectronico);
            TipoTelefonos tipoTelefonos = new TipoTelefonos();
            tipoInfoUbicacion.setTelefonos(tipoTelefonos);

            TipoInformacionDireccionPersonaNatural tipoDireccionPersonaNatural = null;
            TipoCorreoElectronico tipoCorreoElectronico = null;
            TipoDatoTelefono tipoDatoTelefono = null;

            InformacionBanderaDatosNoSensiblesDTO infoDatoNoSensible = new InformacionBanderaDatosNoSensiblesDTO();
            infoDatoNoSensible.setConsultarDireccion(informacionVinculado.getInformacionUbicacionResidencia().getDirecciones()
                    .getDireccion() != null ? Boolean.TRUE : Boolean.FALSE);
            infoDatoNoSensible.setConsultarCorreo(informacionVinculado.getInformacionUbicacionResidencia().getCorreosElectronicos()
                    .getCorreoElectronico() != null ? Boolean.TRUE : Boolean.FALSE);
            infoDatoNoSensible
                    .setConsultarTelefono(informacionVinculado.getInformacionUbicacionResidencia().getTelefonos().getTelefono() != null ? Boolean.TRUE
                            : Boolean.FALSE);

            /* Consulta Datos Info Sensible */

            if (infoDatoNoSensible.getConsultarCorreo() || infoDatoNoSensible.getConsultarDireccion()
                    || infoDatoNoSensible.getConsultarTelefono()) {

                DbCommand command = database.GetXmlCommand("StoredProcedure_ConsultarInfoDatosNoSensibles");
                command.setCommandType(CommandType.StoredProcedure);

                database.AddInXmlParameter(command, "@id_vinculado", idVinculado);
                database.AddInXmlParameter(command, "@consultar_direcciones", infoDatoNoSensible.getConsultarDireccion());
                database.AddInXmlParameter(command, "@consultar_correos", infoDatoNoSensible.getConsultarCorreo());
                database.AddInXmlParameter(command, "@consultar_telefonos", infoDatoNoSensible.getConsultarTelefono());

                /* Se obtienen los datos de la ejecucion del procedimiento */
                data = database.executeListResultSet(command);

                DataTable dirs = null;
                DataTable correos = null;
                DataTable telefo = null;
                List<DataTable> results = data.getResultSetList();

                if (results.size() > 0) {

                    if (results.get(0) != null) {
                        dirs = results.get(0);
                    }

                    if (results.get(1) != null) {
                        correos = results.get(1);
                    }

                    if (results.get(2) != null) {
                        telefo = results.get(2);
                    }

                }

                if (infoDatoNoSensible.getConsultarDireccion()) {
                    for (DataRow row : dirs.getRows()) {
                        tipoDireccionPersonaNatural = new TipoInformacionDireccionPersonaNatural();
                        tipoDireccionPersonaNatural.setIdentificador((Long) row.getValue(0) != null ? BigDecimal.valueOf((long) row
                                .getValue(0)) : new BigDecimal(0));
                        tipoDireccionPersonaNatural.setDireccion((String) (row.getValue(1) != null ? (String) row.getValue(1) : null));

                        if (row.getValue(2) != null) {
                            TipoDepartamento tipoDepartamento = new TipoDepartamento();
                            tipoDepartamento.setCodigo((String) row.getValue(2));
                            tipoDireccionPersonaNatural.setDepartamento(tipoDepartamento);
                        }

                        if (row.getValue(3) != null) {
                            TipoCiudad tipoCiudad = new TipoCiudad();
                            tipoCiudad.setCodigo((String) row.getValue(3));
                            tipoDireccionPersonaNatural.setMunicipio(tipoCiudad);
                        }

                        if (row.getValue(4) != null) {
                            if (row.getValue(4).equals(Boolean.TRUE)) {
                                tipoDireccionPersonaNatural.setEsPrincipal("S");
                            } else {
                                tipoDireccionPersonaNatural.setEsPrincipal("N");
                            }
                        }
                        tipoInfoUbicacion.getDirecciones().getDireccion().add(tipoDireccionPersonaNatural);
                    }

                }

                if (infoDatoNoSensible.getConsultarCorreo()) {
                    for (DataRow row : correos.getRows()) {
                        tipoCorreoElectronico = new TipoCorreoElectronico();
                        tipoCorreoElectronico.setIdentificador((Long) row.getValue(0) != null ? BigDecimal.valueOf((long) row.getValue(0))
                                : new BigDecimal(0));
                        tipoCorreoElectronico.setDireccion((String) (row.getValue(1) != null ? row.getValue(1) : null));
                        if (row.getValue(2) != null) {
                            if (row.getValue(2).equals(Boolean.TRUE)) {
                                tipoCorreoElectronico.setEsPrincipal("S");
                            } else {
                                tipoCorreoElectronico.setEsPrincipal("N");
                            }
                        }
                        tipoInfoUbicacion.getCorreosElectronicos().getCorreoElectronico().add(tipoCorreoElectronico);
                    }

                }

                if (infoDatoNoSensible.getConsultarTelefono()) {

                    for (DataRow row : telefo.getRows()) {

                        tipoDatoTelefono = new TipoDatoTelefono();
                        tipoDatoTelefono.setIdentificador((Long) row.getValue(0) != null ? BigDecimal.valueOf((long) row.getValue(0))
                                : new BigDecimal(0));
                        tipoDatoTelefono.setTipoTelefono((String) (row.getValue(1) != null ? row.getValue(1) : null));
                        tipoDatoTelefono.setTelefono((String) (row.getValue(2) != null ? row.getValue(2) : null));
                        if (row.getValue(3) != null) {
                            if (row.getValue(3).equals(Boolean.TRUE)) {
                                tipoDatoTelefono.setEsPrincipal("S");
                            } else {
                                tipoDatoTelefono.setEsPrincipal("N");
                            }
                        }

                        tipoDatoTelefono.setExtension((String) (row.getValue(4) != null ? row.getValue(4).toString() : null));
                        tipoDatoTelefono.setIndicativoCiudad(row.getValue(5) != null ? String.valueOf(row.getValue(5)) : null);
                        tipoInfoUbicacion.getTelefonos().getTelefono().add(tipoDatoTelefono);
                    }

                }
            }

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_IDENTIFICADOR_VINCULADO, idVinculado.toString());

            HashMap<String, Object> payLoad = new HashMap<String, Object>();
            payLoad.put(ConstantesLoggerServicios.MENSAJE_ERROR, e.getMessage());
            throw new DataAccessException(payLoad, metaData, e);
        }
        return tipoInfoUbicacion;
    }
  
}
