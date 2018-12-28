package co.gov.colpensiones.beps.comunes.ws;

import java.util.Collections;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 * Handler encargado de registrar en un log administrado por Log4j los mensajes SOAP generados por parte de cada uno de los Web Services
 * publicados en la aplicaci�n.
 * 
 * @author <a href="mailto:fvargas@heinsohn.com.co">Fidel Andr�s Vargas </a>
 * 
 */
public class SOAPLogHandler implements SOAPHandler<SOAPMessageContext> {

    /*
     * (non-Javadoc)
     * 
     * @see javax.xml.ws.handler.soap.SOAPHandler#getHeaders()
     */
    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.xml.ws.handler.Handler#close(javax.xml.ws.handler.MessageContext)
     */
    @Override
    public void close(MessageContext context) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.xml.ws.handler.Handler#handleFault(javax.xml.ws.handler.MessageContext )
     */
    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @seejavax.xml.ws.handler.Handler#handleMessage(javax.xml.ws.handler. MessageContext)
     */
    public boolean handleMessage(SOAPMessageContext context) {
        this.logSOAPMessage(context);
        return true;
    }

    /**
     * Registra los mensajes SOAP en un log administrado por Log4j
     * 
     * @param inSOAPMsgContext
     *            Contexto del mensaje SOAP
     */
    private void logSOAPMessage(final SOAPMessageContext inSOAPMsgContext) {
        SOAPLogGenerator.generateLog(inSOAPMsgContext);
    }

}
