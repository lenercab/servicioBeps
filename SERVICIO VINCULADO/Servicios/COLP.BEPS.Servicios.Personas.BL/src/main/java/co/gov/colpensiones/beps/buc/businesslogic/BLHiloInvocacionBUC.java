package co.gov.colpensiones.beps.buc.businesslogic;

import java.util.HashMap;

import co.gov.colpensiones.beps.comunes.enumeraciones.TipoConexionBaseDatosEnum;
import co.gov.colpensiones.beps.comunes.utilidades.Constantes;
import co.gov.colpensiones.beps.comunes.utilidades.DatabaseManager;
import co.gov.colpensiones.beps.log.LoggerBeps;
import co.gov.colpensiones.beps.log.TimeTracer;
import co.gov.colpensiones.beps.vinculacion.businesslogic.DAVinculado;
import co.gov.colpensiones.www.beps.contracts._1_0.novedadesbuc.SrvGenerarNovedadesBucProxy;

/**
 * <b>Descripcion:</b> Clase que se encarga del manejo asincrono de la etapa de notificacion.<br/>
 * <b>Caso de Uso:</b> PT-INGE-014-GVI-VIN-1-FAB-02-RegistrarVinculado_AnexoTecnico_SW <br/>
 * PT-INGE-014-GVI-VIN-1-FAB-07-ResultadoPrevinculacion_AnexoTecnico_SW <br/>
 * PT-INGE-014-GVI-VIN-1-FAB-13-ModificarInformacionNoSensibleVinculado_AnexoTecnico_SW <br/>
 * PT-INGE-014-GOP-VIN-1-FAB-11-ModificarInformacionSensibleVinculado_AnexoTecnico_SW <br/>
 * 
 * @author John A Duran G<jduran@heinsohn.com.co>
 */
public class BLHiloInvocacionBUC extends Thread {

    /**
     * Atributo que identifica el id de la novedad
     */
    private String idNovedad;

    /**
     * Atributo que identifica el tipo de novedad
     */

    private String tipoNovedad;

    /**
     * Atributo que identifica el id del vinculado
     */

    private Long idVinculado;

    /**
     * Tracer para el registro de tiempos en el log
     */
    private TimeTracer tracer;

    /**
     * log para llevar la traza
     */
    private LoggerBeps logg;

    /**
     * Data Base Manager
     */
    private DatabaseManager database;

    /**
     * Constante para identificar la URL End point de Generar Novedad en Base de datos
     */
    public static final String ENDPOINT_GENERARNOVEDAD = "ENDPOINT_GENERAR_NOVEDAD";

    public BLHiloInvocacionBUC(String idNovedad, String tipoNovedad, Long idVinculado, TimeTracer tracer, LoggerBeps log) {
        super();
        this.idNovedad = idNovedad;
        this.tipoNovedad = tipoNovedad;
        this.idVinculado = idVinculado;
        this.logg = log;
        this.tracer = tracer;
        this.database = new DatabaseManager(TipoConexionBaseDatosEnum.SQL_SERVER);

    }

    @Override
    public void run() {
        try {
            // Ejecucion asincrona de la notificacion de firma

            HashMap<String, String> metaData = new HashMap<String, String>();
            tracer = new TimeTracer(logg, metaData);

            // Se invoca el servicio SrvGenerarNovedadBucImpl para enviar la novedad de creación o actualizacion dependiendo del caso
            // PT-INGE-014-GVI-VIN-3-FAB-05-GenerarNovedadesCreaciónVinculadosBUC
            // PT-INGE-014-GVI-VIN-3-FAB-06-GenerarNovedadesActualizaciónVinculadosBUC

            DAVinculado daVinculado = new DAVinculado();
            if (tipoNovedad.equals(Constantes.TIPO_NOVEDAD_CREACION_BUC)) {
                tracer.inicio("Inicio del proceso de creacion a BUC");
                String endPoint = daVinculado.consultarParametroBucPorNombre(database, ENDPOINT_GENERARNOVEDAD);
                SrvGenerarNovedadesBucProxy srvGenerarNovedadesBucProxy = new SrvGenerarNovedadesBucProxy(endPoint);
                srvGenerarNovedadesBucProxy.generarNovedad(new Long(idNovedad));
                tracer.fin("Fin del proceso de creacion a BUC ");
            }// para las novedades de actualización siempre se debe validar si el vinculado tienen alguna novedad pendiente por reintento en
             // la bitácora de novedades BUC
            else if (tipoNovedad.equals(Constantes.TIPO_NOVEDAD_ACTUALIZACION_BUC)) {
                Boolean novedadesPendReintentos = daVinculado.consultarNovedadesPendientesBUC(database, idVinculado);
                if (!novedadesPendReintentos) {
                    tracer.inicio("Inicio del proceso Actualizacion Novedad a BUC ");
                    String endPoint = daVinculado.consultarParametroBucPorNombre(database, ENDPOINT_GENERARNOVEDAD);
                    SrvGenerarNovedadesBucProxy srvGenerarNovedadesBucProxy = new SrvGenerarNovedadesBucProxy(endPoint);
                    srvGenerarNovedadesBucProxy.generarNovedad(new Long(idNovedad));
                    tracer.fin("Fin del proceso Actualizacion Novedad a BUC ");
                }

            }
        } catch (Exception e) {
            tracer.getLogger().error(e);
        }
    }

}
