package co.gov.colpensiones.beps.comunes.jms;

import java.io.DataInputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

/**
 * Clase utilitaria JMS para la conexión de colas de mensajes.
 * 
 * @author HEINSOHN BUSINESS TECHNOLOGY - HSH
 */
public class JMSUtil {

    /** Fabrica de conexion */
    private static ConnectionFactory connectionFactory;

    /** Cola de mensajes. */
    private static Queue queue;

    /**
     * Método encargado de establecer la conexión con la cola de mensaje y enviar el objeto.
     * 
     * @param obj
     *            , Contiene la información necesaria la creación del mensaje.
     * 
     * @param queueName
     *            , Contiene la información necesaria para buscar la cola para los mensajes.
     * @throws Exception
     */
    public static void sendMessage(Object obj, String queueName) throws Exception {
        Connection connection = null;
        Session session = null;
        MessageProducer messageProducer = null;

        ObjectMessage objectMessage = null;
        MapMessage mapMessage = null;
        TextMessage textMessage = null;
        BytesMessage bytesMessage = null;
        StreamMessage streamMessage = null;

        try {
            InitialContext jndiContext = new InitialContext();
            // Se busca la cola para los mensajes
            queue = (Queue) jndiContext.lookup(queueName);
            connectionFactory = (ConnectionFactory) jndiContext.lookup("ConnectionFactory");

            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            messageProducer = session.createProducer(queue);

            if (obj instanceof Map) {
                mapMessage = session.createMapMessage();
                mapMessage.setObject("map", obj);
                messageProducer.send(mapMessage);
            } else if (obj instanceof String) {
                textMessage = session.createTextMessage();
                textMessage.setText(obj.toString());
                messageProducer.send(textMessage);
            } else if (obj instanceof Byte) {
                bytesMessage = session.createBytesMessage();
                bytesMessage.setByteProperty("byte", (Byte) obj);
                messageProducer.send(bytesMessage);
            } else if ((obj instanceof DataInputStream) || (obj instanceof OutputStream)) {
                streamMessage = session.createStreamMessage();
                streamMessage.writeObject(obj);
                messageProducer.send(streamMessage);
            } else {
                objectMessage = session.createObjectMessage();
                objectMessage.setObject((Serializable) obj);
                messageProducer.send(objectMessage);
            }

            connection.close();
        } catch (Exception e) {
            throw new Exception("No fue posible escribir el mensaje [" + obj.toString() + "] en la cola [" + queueName + "]");
        }
    }
    
    /**
     * Método encargado de establecer la conexión con la cola de mensaje y enviar el objeto.
     * 
     * @param obj
     *            , Contiene la información necesaria la creación del mensaje.
     * 
     * @param queueName
     *            , Contiene la información necesaria para buscar la cola para los mensajes.
     * @throws Exception
     */
    public static void sendMessage(Object obj, String queueName, long delay) throws Exception {
        Connection connection = null;
        Session session = null;
        MessageProducer messageProducer = null;

        ObjectMessage objectMessage = null;
        MapMessage mapMessage = null;
        TextMessage textMessage = null;
        BytesMessage bytesMessage = null;
        StreamMessage streamMessage = null;

        try {
            InitialContext jndiContext = new InitialContext();
            // Se busca la cola para los mensajes
            queue = (Queue) jndiContext.lookup(queueName);
            connectionFactory = (ConnectionFactory) jndiContext.lookup("ConnectionFactory");

            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            messageProducer = session.createProducer(queue);

            if (obj instanceof Map) {
                mapMessage = session.createMapMessage();
                mapMessage.setObject("map", obj);
                mapMessage.setLongProperty("_HQ_SCHED_DELIVERY", System.currentTimeMillis() + delay);
                messageProducer.send(mapMessage);
                

            } else if (obj instanceof String) {
                textMessage = session.createTextMessage();
                textMessage.setText(obj.toString());
                textMessage.setLongProperty("_HQ_SCHED_DELIVERY", System.currentTimeMillis() + delay);
                messageProducer.send(textMessage);
                
            } else if (obj instanceof Byte) {
                bytesMessage = session.createBytesMessage();
                bytesMessage.setByteProperty("byte", (Byte) obj);
                bytesMessage.setLongProperty("_HQ_SCHED_DELIVERY", System.currentTimeMillis() + delay);
                messageProducer.send(bytesMessage);
                
            } else if ((obj instanceof DataInputStream) || (obj instanceof OutputStream)) {
                streamMessage = session.createStreamMessage();
                streamMessage.writeObject(obj);
                streamMessage.setLongProperty("_HQ_SCHED_DELIVERY", System.currentTimeMillis() + delay);
                messageProducer.send(streamMessage);
                
            } else {
                objectMessage = session.createObjectMessage();
                objectMessage.setObject((Serializable) obj);
                objectMessage.setLongProperty("_HQ_SCHED_DELIVERY", System.currentTimeMillis() + delay);
                messageProducer.send(objectMessage);
            }

            connection.close();
        } catch (Exception e) {
            throw new Exception("No fue posible escribir el mensaje [" + obj.toString() + "] en la cola [" + queueName + "]");
        }
    }
}