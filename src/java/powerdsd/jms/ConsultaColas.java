/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package powerdsd.jms;

import java.util.Enumeration;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author s7263
 */
public class ConsultaColas {

    public int numeroMensajes() throws JMSException, NamingException {

        InitialContext ctx = new InitialContext();


        Queue queue = (Queue) ctx.lookup("java:comp/env/jms/myQueue");


        QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.
                lookup("java:comp/env/jms/myQueueFactory");

        QueueConnection queueConn = connFactory.createQueueConnection();

        QueueSession queueSession = queueConn.createQueueSession(false,
                Session.AUTO_ACKNOWLEDGE);

        QueueBrowser queueBrowser = queueSession.createBrowser(queue);

        queueConn.start();

        Enumeration e = queueBrowser.getEnumeration();




        int contador = 0;
        while (e.hasMoreElements()) {
            e.nextElement();
            contador++;

        }

        queueConn.close();

        return contador;

    }

    public String obtener() throws NamingException, JMSException {
        QueueConnection queueCon = null;
        String mensaje = null;
        try {
            // get the initial context, refer to your app server docs for this
            Context ctx = new InitialContext();

            // get the connection factory, and open a connection
            QueueConnectionFactory qcf = (QueueConnectionFactory) ctx.lookup("java:comp/env/jms/myQueueFactory");
            queueCon = qcf.createQueueConnection();

            // create queue session off the connection
            QueueSession queueSession = queueCon.
                    createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            // get handle on queue, create a sender and send the message
            Queue queue = (Queue) ctx.lookup("java:comp/env/jms/myQueue");
            QueueReceiver receiver = queueSession.createReceiver(queue);

            queueCon.start();
            Message m = receiver.receive();



            if (m != null && m instanceof TextMessage) {
                TextMessage message = (TextMessage) m;

                mensaje = message.getText();
                System.out.println("Reading message: " + mensaje);
            } else {
                System.out.println("no data");
            }

        } finally {
            if (queueCon != null) {
                queueCon.close();
            }
        }

        return mensaje;
    }
}
