package co.gov.colpensiones.beps.comunes.utilidades;

import javax.xml.ws.WebServiceContext;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import co.gov.colpensiones.beps.comunes.ws.ColpensionesWS;

/**
 * <b>Descripcion:</b> Clase que permite escribir mensajes en el log de la aplicación.<br/>
 * 
 * @author csalazar <csalazar@heinsohn.com.co>
 */
public class Log extends ColpensionesWS {

    /** Atributo que contiene el logger para el servidor. */
    private Logger logger = Logger.getLogger(Log.class);
    /** Atriguto que mantiene el id de la transacción que genera el log. */
    private String id;
    /** Atributo que mantiene el Módulo que está generado el log. */
    private String modulo;
    /** Atributo que mantiene la tarea en ejecución que generá el log. */
    private String tarea;
    /** Atributo que mantiene la ip desde donde se genera el log. */
    private String ip;

    /**
     * Constructor de la clase para el manejo de log que no tiene contexto
     * de Web Service.
     * 
     * @param modulo Módulo que está generado el log.
     * @param tarea Tarea en ejecución que generá el log.
     * @param ip ip desde donde se genera el log.
     */
    public Log(String modulo, String tarea, String ip) {
        this.id = GeneradorUUID.crearUUID();
        this.modulo = modulo;
        this.tarea = tarea;
        this.ip = ip;
    }
    
    /**
     * Constructor de la clase para manejo de log en la ejecución de WS.
     * 
     * @param modulo Módulo que está generado el log.
     * @param tarea Tarea en ejecución que generá el log.
     * @param ip ip desde donde se genera el log.
     */
    public Log(String modulo, String tarea, WebServiceContext contextWs) {
        this.id = GeneradorUUID.crearUUID();
        this.modulo = modulo;
        this.tarea = tarea;
        this.ip = this.getIpClient(contextWs.getMessageContext());
    }

    /**
     * Método que escribe un mensaje de tipo info en el log del servidor.
     * 
     * @param mensaje
     */
    public void info(String mensaje) {
        escribirLog(mensaje, Level.INFO, null);
    }

    /**
     * Método que escribe un mensaje de tipo error en el log del servidor.
     * 
     * @param mensaje
     */
    public void error(String mensaje) {
        escribirLog(mensaje, Level.ERROR, null);
    }

    /**
     * Método que escribe un mensaje de tipo error en el log del servidor.
     * 
     * @param mensaje
     *            Mensaje a escribir en el log
     * @param t
     *            Excepción que genera el mensaje de error.
     */
    public void error(String mensaje, Throwable t) {
        escribirLog(mensaje, Level.ERROR, t);

    }

    /**
     * Método que escribe un mensaje de tipo debug en el log del servidor.
     * 
     * @param mensaje
     */
    public void debug(String mensaje) {
        escribirLog(mensaje, Level.DEBUG, null);
    }

    /**
     * Método que escribe un mensaje de error en el log del servidor.
     * 
     * @param mensaje
     *            Mensaje de información o error.
     * @param nivelError
     *            Nivel de error para escribir.
     */
    private void escribirLog(String mensaje, Level nivelError, Throwable t) {

        try {

            switch (nivelError.toInt()) {
            case Level.INFO_INT:
                logger.info(toString() + mensaje);
                break;
            case Level.ERROR_INT:
                if(t != null)
                    logger.error(toString() + mensaje,t);
                else
                    logger.error(toString() + mensaje);
                break;
            case Level.DEBUG_INT:
                logger.debug(toString() + mensaje);
                break;
            }
        } catch (Exception e) {
            logger.error(mensaje,e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Log [");
        if (id != null) {
            builder.append("id=");
            builder.append(id);
            builder.append(", ");
        }
        if (modulo != null) {
            builder.append("modulo=");
            builder.append(modulo);
            builder.append(", ");
        }
        if (tarea != null) {
            builder.append("tarea=");
            builder.append(tarea);
            builder.append(", ");
        }
        if (ip != null) {
            builder.append("ip=");
            builder.append(ip);
            builder.append(", ");
        }
        builder.append("] ");
        return builder.toString();
    }

    /**
     * Devuelve el valor de id
     * 
     * @return El valor de id
     */
    public String getId() {
        return id;
    }

}