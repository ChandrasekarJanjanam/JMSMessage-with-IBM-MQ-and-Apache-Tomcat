/*
 * 
 */
package com.chandra.jms;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 * The Class JMSMQSender.
 */
public class JMSMQSender {

	/** The Constant logger. */
	private static final Logger LOGGER = Logger.getLogger(JMSMQSender.class);

	/** The q conn factory. */
	private QueueConnectionFactory qConnFactory;

	/** The queue connection. */
	private QueueConnection queueConnection;

	/** The queue session. */
	private QueueSession queueSession;

	/** The queue session. */
	private QueueSender queueSender;

	/** The queue. */
	private Queue queue;

	/** The ctx. */
	private Context ctx = null;

	/** The text message. */
	private TextMessage textMessage;

	/**
	 * Send message.
	 * 
	 * @param text
	 *            the text
	 * @param jndiName
	 *            the jndi name
	 * @throws FxTechnicalException
	 *             the FxTechnicalException
	 * @throws NamingException 
	 * @throws JMSException 
	 */
	public void sendMessage(String text, String jndiName) throws NamingException, JMSException {

		try {

			// Create Context
			ctx = new InitialContext();
			
			// Enter QueueConnectionFactory name as per JNDI settings in server
			qConnFactory = (QueueConnectionFactory) ctx.lookup("JNDI/CONN_FACT_NAME");

			LOGGER.debug("qConnFactory: " + qConnFactory);

			// Create queue connection
			queueConnection = qConnFactory.createQueueConnection();
			
			LOGGER.debug("queueConnection: " + queueConnection);

			// Create queue session
			queueSession = queueConnection.createQueueSession(false, -1);

			// Enter the queue name as per JNDI settings in Server
			queue = (Queue) ctx.lookup("JMS/SEND_QUEUE");
			LOGGER.debug("queue: " + queue.getQueueName());

			// create a queue sender
			queueSender = queueSession.createSender(queue);
			queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			// Start queue
			queueConnection.start();
			
			//Create text message
			textMessage = queueSession.createTextMessage();
			textMessage.setText(text);
			
			LOGGER.debug("Sending the following message: " + textMessage.getText());
			
			//Send Message
			queueSender.send(textMessage);
			LOGGER.debug("After the following message: " + textMessage.getText());

		} catch (NamingException ne) {
			LOGGER.error("FxMQSender sendMessage: NamingException  " + ne.getMessage());
			throw ne;
		} catch (Exception e) {
			LOGGER.error("FxMQSender sendMessage: Exception  " + e.getMessage());
			throw e;
		} finally {
			
			// Close the queue connection and queue session after sending message
			if (null != queueSession) {
				queueSession.close();
			}
			if (null != queueConnection) {
				queueConnection.close();
			}
		}
	}
}
