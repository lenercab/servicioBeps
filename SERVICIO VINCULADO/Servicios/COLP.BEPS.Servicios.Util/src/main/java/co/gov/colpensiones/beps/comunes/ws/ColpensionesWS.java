package co.gov.colpensiones.beps.comunes.ws;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.handler.MessageContext;

/**
 * Clase creada para ubicar todas las utilidades propias de los Web Services, un web service que quiera adoptar esta funcionalidad deberá
 * heredar de esta clase
 * 
 * @author fvargas
 * 
 */
public class ColpensionesWS {

    /**
     * Obtiene del contexto la IP del cliente desde la cual se invoca el servicio.
     * 
     * @param context
     *            Contexto del Web Service.
     * @return Dirección IP del cliente.
     */
    protected static String getIpClient(MessageContext context) {
        HttpServletRequest req = (HttpServletRequest) context.get(MessageContext.SERVLET_REQUEST);
        String clientIP = req.getRemoteAddr();
        return clientIP;
    }

}
