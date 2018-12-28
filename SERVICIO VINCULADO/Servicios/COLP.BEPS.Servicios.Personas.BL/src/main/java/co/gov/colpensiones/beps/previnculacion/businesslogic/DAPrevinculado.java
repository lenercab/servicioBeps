package co.gov.colpensiones.beps.previnculacion.businesslogic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import co.gov.colpensiones.beps.comunes.enumeraciones.TipoConexionBaseDatosEnum;
import co.gov.colpensiones.beps.comunes.utilidades.Constantes;
import co.gov.colpensiones.beps.comunes.utilidades.ConstantesEstados;
import co.gov.colpensiones.beps.comunes.utilidades.ConstantesLoggerServicios;
import co.gov.colpensiones.beps.comunes.utilidades.DatabaseManager;
import co.gov.colpensiones.beps.dal.utilidades.CommandType;
import co.gov.colpensiones.beps.dal.utilidades.DataStoredProcedure;
import co.gov.colpensiones.beps.dal.utilidades.DataTable;
import co.gov.colpensiones.beps.dal.utilidades.DbCommand;
import co.gov.colpensiones.beps.dal.utilidades.DbParameter;
import co.gov.colpensiones.beps.dal.utilidades.DbType;
import co.gov.colpensiones.beps.excepciones.DataAccessException;
import co.gov.colpensiones.beps.schemas._1_0.comun.TipoInformacionContexto;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoCorreoElectronico;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoDatoTelefono;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoDocumentoPersonaNatural;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionBasicaSisben;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionBasicaSolicitante;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionDireccionPersonaNatural;
import co.gov.colpensiones.beps.schemas._1_0.personas.TipoInformacionPrevinculado;
import co.gov.colpensiones.beps.vinculacion.businesslogic.crear.InformacionVinculado;

/**
 * <b>Descripcion:</b> Clase encargada de la lógica con la Base de datos relacionada con el previnculado<br/>
 * <b>Caso de Uso:</b> GOP-AMB-1-FAB-08-CalcularNivelSisben <br/>
 * 
 * @author Helen Acero <hacero@heinsohn.com.co>
 */
public class DAPrevinculado {

    /**
     * Método encargado de calcular el nivel sisben dado el área y el puntaje
     * 
     * @param area
     *            , Código de áreas para Sisben
     * @param puntaje
     *            , Puntaje oficial establecido por el Ministerio del Trabajo
     * @return el valor del nivel SISBEN
     * @throws DataAccessException
     *             , si se genera alguna excepcion
     */
    public DataTable calcularNivelSisben(String area, String puntaje) throws DataAccessException {

        DbCommand command = null;
        DataTable data = null;

        try {
            // invocar el SP para calcular
            DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);
            command = database.GetXmlCommand("StoredProcedure_CalcularNivelSisben");
            command.setCommandType(CommandType.Text);
            database.AddInXmlParameter(command, "@area", Integer.parseInt(area));
            database.AddInXmlParameter(command, "@puntaje", new BigDecimal(puntaje));

            data = database.ExecuteDataTableCommandText(command);

        } catch (Exception e) {
            HashMap<String, Object> payLoad = new HashMap<String, Object>();
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_AREA, area.toString());
            metaData.put(ConstantesLoggerServicios.METADATA_PUNTAJE, puntaje.toString());
            throw new DataAccessException(payLoad, metaData, e);
        }
        return data;
    }

    /**
     * Método para consultar la lista de valores aceptados para el campo NivelSisben
     * 
     * @return lista con los valores de NivelSisben válidos
     * @throws DataAccessException
     *             , si se genera alguna excepcion
     */
    public DataTable consultarNivelesSisben() throws DataAccessException {
        DbCommand command = null;
        DataTable data = null;
        try {
            DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);
            command = database.GetXmlCommand("PR_ConsultarNivelesSisben");
            command.setCommandType(CommandType.Text);
            data = database.ExecuteDataTableCommandText(command);
        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_CONSULTA, "Consulta para validar datos de entrada NivelSisben");
            throw new DataAccessException(null, metaData, e);
        }
        return data;
    }

    /**
     * Método para consulta de existencia de un previnculado
     * 
     * @param informacionPrevinculado
     * @return idRegistro del previnculado existente
     */
    public String consultarExistenciaPrevinculado(TipoDocumentoPersonaNatural informacionPrevinculado) throws DataAccessException {
        String pkVinculado = null;
        DbCommand command = null;

        try {
            DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);
            command = database.GetXmlCommand("PR_ConsultarExistenciaPrevinculado");
            command.setCommandType(CommandType.Text);
            database.AddInXmlParameter(command, "@vpe_tipo_documento", informacionPrevinculado.getTipoDocumento());
            database.AddInXmlParameter(command, "@vpe_numero_documento", informacionPrevinculado.getNumeroDocumento());

            DataTable data = database.ExecuteDataTableCommandText(command);
            if (data.getRows().size() > 0) {
                pkVinculado = data.getRows().get(0).getValue("vpe_pk_id").toString();
            }

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, informacionPrevinculado.getTipoDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, informacionPrevinculado.getNumeroDocumento());
            throw new DataAccessException(null, metaData, e);
        }
        return pkVinculado;
    }

    /**
     * Método para registro de información del detalle de persona para un previnculado <b>Caso de Uso:</b>
     * GVI-VIN-1-FAB-05-CrearPrevinculado <br/>
     * 
     * @param database
     * @param informacionVinculado
     * @param pkVinculado
     * @return
     * @throws DataAccessException
     */
    @SuppressWarnings("rawtypes")
    public BigDecimal crearPersonaPrevinculado(DatabaseManager database, TipoInformacionContexto informacionContexto,
            TipoInformacionBasicaSolicitante informacionPrevinculado, String generoHomologado) throws DataAccessException {

        DbCommand command = null;
        BigDecimal idRegistro = null;
        try {
            command = database.GetXmlCommand("PR_CrearPersonaPrevinculado");
            command.setCommandType(CommandType.Text);
            database.AddInXmlParameter(command, "@vpe_tipo_documento", informacionPrevinculado.getDocumento().getTipoDocumento());
            database.AddInXmlParameter(command, "@vpe_numero_documento", informacionPrevinculado.getDocumento().getNumeroDocumento());
            database.AddInXmlParameter(command, "@vpe_primer_apellido", informacionPrevinculado.getNombresApellidos().getValue()
                    .getPrimerApellido());
            database.AddInXmlParameter(command, "@vpe_segundo_apellido", informacionPrevinculado.getNombresApellidos().getValue()
                    .getSegundoApellido());
            database.AddInXmlParameter(command, "@vpe_primer_nombre", informacionPrevinculado.getNombresApellidos().getValue()
                    .getPrimerNombre());
            database.AddInXmlParameter(command, "@vpe_segundo_nombre", informacionPrevinculado.getNombresApellidos().getValue()
                    .getSegundoNombre());
            database.AddInXmlParameter(command, "@vpe_sexo", generoHomologado);
            database.AddInXmlParameter(command, "@vpe_fecha_nacimiento", informacionPrevinculado.getInformacionAdicional()
                    .getInformacionLugarNacimiento().getFechaNacimiento());
            database.AddInXmlParameter(command, "@vpe_fecha_expedicion", informacionPrevinculado.getInformacionAdicional()
                    .getFechaExpedicionDocumentoIdenticacion());
            database.AddInXmlParameter(command, "@vpe_municipio_expedicion", informacionPrevinculado.getInformacionAdicional()
                    .getMunicipioExpedicionDocumentoIdenticacion());
            database.AddInXmlParameter(command, "@vpe_usuario_ultimo_cambio", informacionContexto.getUsuarioSistemaExterno());
            database.AddInXmlParameter(command, "@vpe_numero_radicado", informacionContexto.getIdCorrelacion());
            database.AddInXmlParameter(command, "@vpe_canal_sistema", informacionContexto.getSistemaOrigen());

            Collection<Object> llavesGeneradas = database.executeInsertQuery(command, 1);
            if (llavesGeneradas != null && !llavesGeneradas.isEmpty()) {
                Iterator itr = llavesGeneradas.iterator();
                if (itr.hasNext()) {
                    Object[] obj = (Object[]) itr.next();
                    idRegistro = (BigDecimal) obj[0];
                }
            }

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, informacionPrevinculado.getDocumento().getTipoDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, informacionPrevinculado.getDocumento().getNumeroDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_RADICADO, informacionContexto.getIdCorrelacion());
            throw new DataAccessException(null, metaData, e);
        }
        return idRegistro;
    }

    /**
     * Método para registro de información del un previnculado <b>Caso de Uso:</b> GVI-VIN-1-FAB-05-CrearPrevinculado <br/>
     * 
     * @param database
     * @param informacionContexto
     * @param informacionPrevinculado
     * @return
     * @throws DataAccessException
     */
    public void crearPrevinculado(DatabaseManager database, TipoInformacionContexto informacionContexto,
            TipoInformacionPrevinculado informacionPrevinculado, BigDecimal idPersona) throws DataAccessException {
        DbCommand command = null;
        try {
            command = database.GetXmlCommand("PR_CrearPrevinculado");
            command.setCommandType(CommandType.Text);
            database.AddInXmlParameter(command, "@vvi_pk_id", idPersona);
            database.AddInXmlParameter(command, "@vvi_fecha_vinculacion", new java.sql.Date(new Date().getTime()));
            database.AddInXmlParameter(command, "@vvi_tipo_vinculado", Constantes.ESTADO_PREVINCULADO);
            database.AddInXmlParameter(command, "@vvi_usuario_vinculacion", informacionPrevinculado.getUsuarioPrevinculacion());
            database.AddInXmlParameter(command, "@vvi_autorizacion_manejo_info",
                    Constantes.SI.equals(informacionPrevinculado.getTipoAutorizacion().getAutorizacionManejoInformacion()) ? 1 : 0);
            database.AddInXmlParameter(command, "@vvi_autorizacion_envio_info",
                    Constantes.SI.equals(informacionPrevinculado.getTipoAutorizacion().getAutorizacionEnvioComunicacion()) ? 1 : 0);
            database.AddInXmlParameter(command, "@vvi_actividad", informacionPrevinculado.getInformacionEconomica()
                    .getCodigoActividadEconomicaPrincipal());
            database.AddInXmlParameter(command, "@vvi_actividad_secundaria", informacionPrevinculado.getInformacionEconomica()
                    .getCodigoActividadEconomicaSecundaria());
            database.AddInXmlParameter(command, "@vvi_canal_vinculacion", informacionPrevinculado.getCanal());
            database.AddInXmlParameter(command, "@vvi_usuario_ultimo_cambio", informacionContexto.getUsuarioSistemaExterno());
            database.AddInXmlParameter(command, "@vvi_numero_radicado", informacionContexto.getIdCorrelacion());
            database.AddInXmlParameter(command, "@vvi_canal_sistema", informacionContexto.getSistemaOrigen());
            /* Se ejecuta el query de inserción */
            database.executeInsertQuery(command, 1);

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_IDENTIFICADOR_PERSONA, idPersona.toString());
            metaData.put(ConstantesLoggerServicios.METADATA_USUARIO_VINCULACION, informacionPrevinculado.getUsuarioPrevinculacion());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_RADICADO, informacionContexto.getIdCorrelacion());
            throw new DataAccessException(null, metaData, e);
        }
    }

    /***
     * Método encargado de asociar la lista de direcciones al previncualdo
     * 
     * @param direcciones
     *            , lista de direcciones
     * @param idPersona
     *            , identificador de la persona a la cual se le crea la previnculacion
     * @throws DataAccessException
     *             , error generado
     */
    public void agregarContactoDireccionPrevinculado(DatabaseManager database, List<TipoInformacionDireccionPersonaNatural> direcciones,
            BigDecimal idPersona, TipoInformacionContexto contexto) throws DataAccessException {

        DbCommand command = null;
        try {
            command = database.GetXmlCommand("PR_adicionarContactoDireccionPrevinculado");
            command.setCommandType(CommandType.Text);

            for (TipoInformacionDireccionPersonaNatural direccion : direcciones) {
                database.AddInXmlParameter(command, "@idPersona", idPersona);
                database.AddInXmlParameter(command, "@codMunicipio", direccion.getMunicipio().getCodigo());
                database.AddInXmlParameter(command, "@direccion", direccion.getDireccion());
                database.AddInXmlParameter(command, "@principal", Constantes.SI.equals(direccion.getEsPrincipal()) ? 1 : 0);
                database.AddInXmlParameter(command, "@usuario", contexto.getUsuarioSistemaExterno());
                database.AddInXmlParameter(command, "@radicado", contexto.getIdCorrelacion());
                database.AddInXmlParameter(command, "@canal", contexto.getSistemaOrigen());
            }

            database.executeBach(command, 7);

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_CANTIDAD_DIRECCIONES, direcciones.size() + "");
            metaData.put(ConstantesLoggerServicios.METADATA_IDENTIFICADOR_PERSONA, idPersona + "");
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_RADICADO, contexto.getIdCorrelacion());
            throw new DataAccessException(null, metaData, e);
        }
    }

    /***
     * Método encargado de asociar la lista de teléfonos al Previnculado
     * 
     * @param direcciones
     *            , lista de direcciones
     * @throws DataAccessException
     *             , error generado
     */
    public void agregarContactoTelefonosPrevinculado(DatabaseManager database, List<TipoDatoTelefono> telefonos, BigDecimal idPersona,
            TipoInformacionContexto contexto) throws DataAccessException {
        DbCommand command = null;

        try {
            command = database.GetXmlCommand("PR_adicionarContactoTelefonoPrevinculado");
            command.setCommandType(CommandType.Text);

            for (TipoDatoTelefono telefono : telefonos) {
                database.AddInXmlParameter(command, "@idPersona", idPersona);
                database.AddInXmlParameter(command, "@telefono", telefono.getTelefono());
                database.AddInXmlParameter(command, "@indPais", new Integer(telefono.getIndicativoPais()));
                database.AddInXmlParameter(command, "@indCiudad", new Integer(telefono.getIndicativoCiudad()));
                database.AddInXmlParameter(command, "@extension", telefono.getExtension());
                database.AddInXmlParameter(command, "@principal", Constantes.SI.equals(telefono.getEsPrincipal()) ? 1 : 0);
                database.AddInXmlParameter(command, "@tipo", telefono.getTipoTelefono());
                database.AddInXmlParameter(command, "@usuario", contexto.getUsuarioSistemaExterno());
                database.AddInXmlParameter(command, "@radicado", contexto.getIdCorrelacion());
                database.AddInXmlParameter(command, "@canal", contexto.getSistemaOrigen());
            }

            database.executeBach(command, 10);

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_CANTIDAD_TELEFONOS, telefonos.size() + "");
            metaData.put(ConstantesLoggerServicios.METADATA_IDENTIFICADOR_PERSONA, idPersona + "");
            throw new DataAccessException(null, metaData, e);
        }
    }

    /***
     * Método encargado de asociar la lista de emails al Previnculado
     * 
     * @param direcciones
     *            , lista de direcciones
     * @throws DataAccessException
     *             , error generado
     */
    public void agregarContactoEmailPrevinculado(DatabaseManager database, List<TipoCorreoElectronico> emails, BigDecimal idPersona,
            TipoInformacionContexto contexto) throws DataAccessException {
        DbCommand command = null;

        try {
            command = database.GetXmlCommand("PR_adicionarContactoEmailPrevinculado");
            command.setCommandType(CommandType.Text);

            for (TipoCorreoElectronico email : emails) {
                database.AddInXmlParameter(command, "@idPersona", idPersona);
                database.AddInXmlParameter(command, "@email", email.getDireccion());
                database.AddInXmlParameter(command, "@principal", Constantes.SI.equals(email.getEsPrincipal()) ? 1 : 0);
                database.AddInXmlParameter(command, "@usuario", contexto.getUsuarioSistemaExterno());
                database.AddInXmlParameter(command, "@radicado", contexto.getIdCorrelacion());
                database.AddInXmlParameter(command, "@canal", contexto.getSistemaOrigen());
            }

            database.executeBach(command, 6);

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_CANTIDAD_CORREOS, emails.size() + "");
            metaData.put(ConstantesLoggerServicios.METADATA_IDENTIFICADOR_PERSONA, idPersona + "");
            throw new DataAccessException(null, metaData, e);
        }
    }

    /***
     * Método encargado de registrar la informacion de sisben del Previnculado
     * 
     * @param direcciones
     *            , lista de direcciones
     * @throws DataAccessException
     *             , error generado
     */
    public void registrarInformacionSisben(DatabaseManager database, TipoInformacionContexto contexto, TipoInformacionBasicaSisben sisben,
            BigDecimal idPersona) throws DataAccessException {
        DbCommand command = null;

        try {
            command = database.GetXmlCommand("PR_RegistrarInformacionSisbenPrevinculado");
            command.setCommandType(CommandType.Text);
            database.AddInXmlParameter(command, "@idPersona", idPersona);
            database.AddInXmlParameter(command, "@puntajeSisben", sisben.getPuntaje());
            database.AddInXmlParameter(command, "@areaSisben", sisben.getArea());
            String nivelSisben = null;
            if (sisben.getNivel() != null && sisben.getNivel().getValue() != null)
                nivelSisben = sisben.getNivel().getValue().getCodigo();
            database.AddInXmlParameter(command, "@nivelSisben", nivelSisben);
            database.AddInXmlParameter(command, "@usuario", contexto.getUsuarioSistemaExterno());
            database.AddInXmlParameter(command, "@radicado", contexto.getIdCorrelacion());
            database.AddInXmlParameter(command, "@canal", contexto.getSistemaOrigen());

            /* Se ejecuta el query de inserción */
            database.executeInsertQuery(command, 1);

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_IDENTIFICADOR_PERSONA, idPersona + "");
            metaData.put(ConstantesLoggerServicios.METADATA_PUNTAJE, sisben.getPuntaje() + "");
            throw new DataAccessException(null, metaData, e);
        }
    }

    /**
     * Método que consulta la información del previnculado dada la identificación CU: GVI-VIN-1-FAB-07-ResultadoPrevinculacion
     * 
     * @param identificacion
     *            , identificación a consultar
     * @return datos del previnculado
     */
    public DataTable consultarInformacionPrevinculado(TipoDocumentoPersonaNatural identificacion) throws DataAccessException {
        DataTable data = null;
        DbCommand command = null;

        try {
            DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);

            command = database.GetXmlCommand("PR_ConsultarInformacionPrevinculado");
            command.setCommandType(CommandType.Text);
            database.AddInXmlParameter(command, "@tipoDocumento", identificacion.getTipoDocumento());
            database.AddInXmlParameter(command, "@numeroDocumento", identificacion.getNumeroDocumento());

            /* Se ejecuta el query de inserción */
            data = database.ExecuteDataTable(command);

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, identificacion.getTipoDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, identificacion.getNumeroDocumento());
            throw new DataAccessException(null, metaData, e);
        }
        return data;

    }

    /**
     * Método encargado de cambiar el estado de previnculado a vinculado CU: GVI-VIN-1-FAB-07-ResultadoPrevinculacion
     * 
     * @param contexto
     *            ,información de contexto
     * @param idPrevinculado
     *            , identificador del previnculado
     * @param identificacion
     *            , información de la identificación
     * @throws DataAccessException
     *             , si se genera una excepción
     */
    public DataStoredProcedure cambiarEstadoPrevinculadoAVinculado(TipoInformacionContexto contexto, 
            TipoDocumentoPersonaNatural identificacion, BigDecimal idVinculado)
            throws DataAccessException {

        DbCommand command = null;
               
        DataStoredProcedure data = null;
                       
        DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);
        
        try {
            database.beginTransaction();
            
            command = database.GetXmlCommand("pr_cambiar_estado_previnculado");
            command.setCommandType(CommandType.StoredProcedure);
            
            
            /* parametros que ingresan al SP */
            database.AddInXmlParameter(command, "@estado_activo_vinculado", ConstantesEstados.ESTADO_ACTIVO);
            database.AddInXmlParameter(command, "@usuario", contexto.getUsuarioSistemaExterno());
            database.AddInXmlParameter(command, "@id_correlacion", contexto.getIdCorrelacion());
            database.AddInXmlParameter(command, "@tipo_documento", identificacion.getTipoDocumento());
            database.AddInXmlParameter(command, "@numero_documento", identificacion.getNumeroDocumento());
            database.AddInXmlParameter(command, "@id_vinculado", idVinculado);
            database.AddInXmlParameter(command, "@origen_cambio", ConstantesEstados.ORIGEN_CAMBIO_ESTADO_RESULTADO_PREVINCULACION);
            
            /* parámetros que salen del SP */
            database.AddOutParameter(command, "@idnovedad", DbType.BIGINT, 8); 
            data = database.executeListResultSet(command);
            database.commit();
         } catch (Exception e) {
        	 HashMap<String, String> metaData = new HashMap<String, String>();
        	 try {
        		 metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, identificacion.getTipoDocumento());
                 metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, identificacion.getNumeroDocumento());
                 database.rollBack();
             } catch (Exception e1) {
                 throw new DataAccessException(null, metaData, e1);
             }

             throw new DataAccessException(null, metaData, e);
         } finally {

             try {
                 database.closeConnection();
             } catch (Exception e2) {
                 HashMap<String, String> metaData = new HashMap<String, String>();
                 metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, identificacion.getTipoDocumento());
                 metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, identificacion.getNumeroDocumento());
                 metaData.put(ConstantesLoggerServicios.METADATA_NUM_RADICADO, contexto.getIdCorrelacion());
                 throw new DataAccessException(null, metaData, e2);
             }

         }
        return data;
    }

    /**
     * Método encargado de crear la cuenta individual
     * 
     * CU: GVI-VIN-1-FAB-07-ResultadoPrevinculacion
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
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, informacionVinculado.getTipoDocumento());
            metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, informacionVinculado.getNumeroDocumento());
            metaData.put(ConstantesLoggerServicios.MENSAJE_ERROR, e.getMessage());
            throw new DataAccessException(null, metaData, e);
        }
        return parametrosSalida;
    }

    /**
     * Método encargado en cambiar el estado de la creación de la cuenta de vinculado
     * 
     * CU: GVI-VIN-1-FAB-07-ResultadoPrevinculacion
     * 
     * @param idPersona
     *            , identificador persona
     * @param seCreoCuenta
     *            , true = vinculado con cuenta individual, false = caso contrario
     * @throws DataAccessException
     *             , si se genera algún error
     */
    public void cambiarEstadoCuentaVinculado(Long idPersona, boolean seCreoCuenta) throws DataAccessException {

        DbCommand command = null;

        try {
            DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);

            command = database.GetXmlCommand("PR_CambiarEstadoCtaInd");
            command.setCommandType(CommandType.Text);

            /* parametros que ingresan al SP */
            database.AddInXmlParameter(command, "@estadoCuenta", seCreoCuenta ? 1 : 0);
            database.AddInXmlParameter(command, "@notificacionCuentaIndividual", seCreoCuenta ? 1 : 0);
            database.AddInXmlParameter(command, "@fechaNotificacion", seCreoCuenta ? new java.sql.Timestamp (new Date().getTime()) : null);
            database.AddInXmlParameter(command, "@idPersona", idPersona);

            database.executeNonQuery(command);

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios.METADATA_IDENTIFICADOR_PERSONA, idPersona + "");
            throw new DataAccessException(null, metaData, e);
        }
    }

    /**
     * Método encargado de consultar los códigos de rechazo CU: GVI-VIN-1-FAB-07-ResultadoPrevinculacion
     * 
     * @return lista de códigos de rechazo
     * @throws DataAccessException
     */
    public ArrayList<String> consultarCodigosRechazos() throws DataAccessException {

        ArrayList<String> arreglo = null;
        DbCommand command = null;

        try {
            DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);
            command = database.GetXmlCommand("PR_ConsultarCodigosRechazos");
            command.setCommandType(CommandType.Text);

            DataTable dataTable = database.ExecuteDataTable(command);

            // si existen datos se devuelven en un arreglo
            if (dataTable != null && dataTable.getRows() != null && dataTable.getRows().size() > 0) {
                arreglo = new ArrayList<String>();
                for (int i = 0; i < dataTable.getRows().size(); i++) {
                    arreglo.add((dataTable.getRows().get(i).getValue("vvr_codigo")) + "");
                }
            }

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            metaData.put(ConstantesLoggerServicios. METADATA_CANTIDAD_RECHAZOS, arreglo != null ? arreglo.size() + "" : 0 + "");
            throw new DataAccessException(null, metaData, e);
        }

        return arreglo;
    }

    /**
     * Método encargado de registrar el rechazo de un previnculado
     * 
     * @param contexto
     *            , información del contexto
     * @param identificacion
     *            , identificación del previnculado
     * @param codigosRechazo
     *            , lista de códigos de rechazo
     * @param usuarioAprobacion
     *            ,usuario que aprobó
     * @param radVinculacion
     *            , radicado de la vinculación
     * @throws DataAccessException
     *             , si se genera un error
     */
    public void previnculadoRechazado(TipoInformacionContexto contexto, TipoDocumentoPersonaNatural identificacion, List<String> codigosRechazo)
            throws DataAccessException {

        DbCommand command = null;
        DatabaseManager database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);

        try {

            database.beginTransaction();
            command = database.GetXmlCommand("StoredProcedure_PrevinculadoRechazado");
            command.setCommandType(CommandType.StoredProcedure);
            
            String cadenaCodigosRechazo = codigosRechazo.toString().replace("[", "").replace("]", "");            

            /* parametros que ingresan al SP */
            database.AddInXmlParameter(command, "@tipoDocumento", identificacion.getTipoDocumento());
            database.AddInXmlParameter(command, "@numeroDocumento", identificacion.getNumeroDocumento());
            database.AddInXmlParameter(command, "@codigosRechazo", cadenaCodigosRechazo);
            database.AddInXmlParameter(command, "@numeroRadicado", contexto.getIdCorrelacion());
            database.AddInXmlParameter(command, "@usuarioUltimoCambio", contexto.getUsuarioSistemaExterno());
            database.AddInXmlParameter(command, "@canalSistema", contexto.getSistemaOrigen());

            database.execute(command);
            database.commit();

        } catch (Exception e) {
            HashMap<String, String> metaData = new HashMap<String, String>();
            try {
                metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, identificacion.getTipoDocumento());
                metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, identificacion.getNumeroDocumento());
                metaData.put(ConstantesLoggerServicios.METADATA_NUM_RADICADO, contexto.getIdCorrelacion());

                database.rollBack();
            } catch (Exception e1) {
                throw new DataAccessException(null, metaData, e1);
            }

            throw new DataAccessException(null, metaData, e);
        } finally {

            try {
                database.closeConnection();
            } catch (Exception e2) {
                HashMap<String, String> metaData = new HashMap<String, String>();
                metaData.put(ConstantesLoggerServicios.METADATA_TIPO_DOCUMENTO, identificacion.getTipoDocumento());
                metaData.put(ConstantesLoggerServicios.METADATA_NUM_DOCUMENTO, identificacion.getNumeroDocumento());
                metaData.put(ConstantesLoggerServicios.METADATA_NUM_RADICADO, contexto.getIdCorrelacion());
                throw new DataAccessException(null, metaData, e2);
            }

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

}
