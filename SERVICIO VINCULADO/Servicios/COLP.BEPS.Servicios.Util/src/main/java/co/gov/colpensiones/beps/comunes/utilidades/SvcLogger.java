package co.gov.colpensiones.beps.comunes.utilidades;

import org.apache.log4j.Logger;

/**
 * Esta clase sirve para la insercion de registros en el logger
 * 
 * @author jgomez
 * 
 */

public class SvcLogger {

    // Esta es la variable que se utiliza como logger
    static Logger logger = Logger.getLogger(SvcLogger.class);

    /**
     * Registrar errores usando solo un mensaje
     * 
     * @param message
     */

    public static void error(String message) {
        logger.error(message);
    }

    /**
     * Esta funcion recibe un mensaje y reciba una excepcion que ha sido lanzada.
     * 
     * @param message
     * @param t
     */

    public static void error(String message, Throwable t) {
        logger.error(message, t);

    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void info(String message, Boolean imprimir) {

        if (imprimir) {
            logger.info(message);
        }
    }

    public static void info(String message, Throwable t) {
        logger.info(message, t);
    }

    public static void fatal(String message) {
        logger.fatal(message);
    }

    public static void fatal(String message, Throwable t) {
        logger.fatal(message, t);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void warn(String message, Throwable t) {
        logger.warn(message, t);
    }

}
