/*
 * 
 */
package com.chandra.jms;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;


/**
 * The listener interface for receiving fxContext events. The class that is
 * interested in processing a fxContext event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addFxContextListener<code> method. When
 * the fxContext event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see FxContextEvent
 */
public class JMSContextListener implements ServletContextListener {

	/** The Constant logger. */
	private static final Logger LOGGER = Logger.getLogger(JMSContextListener.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		JMSMQListener mqListener = new JMSMQListener();

		try {
			mqListener.init();
		}catch (Exception e) {
			LOGGER.error("JMSMQListener init() Exception  " + e.getMessage());
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("JMSContextListener destroyed");
	}

}


