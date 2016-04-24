/*
 * 
 */
package com.chandra.jms;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;



/**
 * @author CHANDRA
 *
 */
public class JMSMQListener implements MessageListener {

	/** The Constant logger. */
	private static final Logger LOGGER = Logger.getLogger(JMSMQListener.class);

	/** The q conn factory. */
	private QueueConnectionFactory qConnFactory = null;

	/** The queue connection. */
	private QueueConnection queueConnection = null;

	/** The queue session. */
	private QueueSession queueSession = null;

	/** The queue. */
	private Queue queue = null;

	/** The ctx. */
	private Context ctx = null;

	/** The messageConsumer. */
	private MessageConsumer messageConsumer = null;

	/**
	 * Inits the.
	 * @throws Exception 
	 *
	 * @throws FxTechnicalException
	 */
	public void init() throws Exception {

		try {

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
			queue = (Queue) ctx.lookup("JMS/RECEIVE_QUEUE");

			// Create message consumer
			messageConsumer = queueSession.createConsumer(queue);
			
			// Open queue
			queueConnection.start();
			
			// Set messageListener to message queue
			messageConsumer.setMessageListener(this);

		} catch (NamingException ne) {
			LOGGER.error("JMS Message init() NamingException  " + ne.getMessage());
			throw ne;
		} catch (JMSException e) {
			LOGGER.error("JMS Message init() Exception  " + e.getMessage());
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message message) {
		String msgStr = null;

		try {
			LOGGER.info("onMessage start ");
			if (message instanceof TextMessage) {
				// Recieve message from Message queue
				msgStr = ((TextMessage) message).getText();

			} else if (message instanceof BytesMessage) {
				LOGGER.debug("BytesMessage... ");
				// Read message in bytes
				BytesMessage byteMessage = (BytesMessage) message;
				byte[] byteArr = new byte[(int) byteMessage.getBodyLength()];
				for (int i = 0; i < (int) byteMessage.getBodyLength(); i++) {
					byteArr[i] = byteMessage.readByte();
				}
				msgStr = new String(byteArr);
			}
			LOGGER.info("JMS Message received successfully" + msgStr);
		} catch (Exception e) {
			LOGGER.error("JMS Message Exception  " + e.getMessage());
			e.printStackTrace();
		}
	}

}
