package co.gov.colpensiones.beps.comunes.ws;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.log4j.Logger;

/**
 * Clase encargada de registrar en un log administrado por Log4j los mensajes SOAP generados por parte de cada uno de los Web Services
 * publicados en la aplicaci�n. Se utiliza como criterio de identificaci�n el nombre de la operaci�n que se est� invocando.
 * 
 * @author <a href="mailto:fvargas@heinsohn.com.co">Fidel Vargas</a>
 * 
 */
public class SOAPLogGenerator {

    /**
     * Logger general.
     */
    private transient static final Logger rootLooger = Logger.getLogger(SOAPLogGenerator.class);

    /**
     * Registra los mensajes SOAP en un log administrado por Log4j
     * 
     * @param inSOAPMsgContext
     *            Contexto del mensaje SOAP
     */
    public static void generateLog(final SOAPMessageContext inSOAPMsgContext) {

        @SuppressWarnings("unused")
        String operationName = "";
        Logger logger = null;
        /*
         * Se recupera el nombre de la operaci�n
         */
        QName operationNameQName = (QName) inSOAPMsgContext.get(MessageContext.WSDL_OPERATION);

        /*
         * Si el nombre de la operaci�n es diferente de nulo se procede a registrar el log
         */
        if (operationNameQName != null) {
            operationName = operationNameQName.toString();

            /*
             * Escritura de log
             */
            if (logger != null) {
                OutputStream os = new ByteArrayOutputStream();
                try {
                    inSOAPMsgContext.getMessage().writeTo(os);
                    logger.info(os);
                } catch (SOAPException e) {
                    rootLooger.error("No fue posible recuperar el mensaje SOAP", e);
                } catch (IOException e) {
                    rootLooger.error("No fue posible escribir el log", e);
                }

            }

        }
    }
}
