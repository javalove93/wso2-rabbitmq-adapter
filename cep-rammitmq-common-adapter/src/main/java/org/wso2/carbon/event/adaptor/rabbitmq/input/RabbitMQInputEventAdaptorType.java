/* Copyright (C) 2015~ Hyundai Heavy Industries. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Hyundai Heavy Industries
 * You shall not disclose such Confidential Information and shall use it only 
 * in accordance with the terms of the license agreement
 * you entered into with Hyundai Heavy Industries.
 *
 * Revision History
 * Author             Date              Description
 * ---------------	----------------	------------
 * Jerry Jeong	       2015. 3. 31.		    First Draft.
 */

package org.wso2.carbon.event.adaptor.rabbitmq.input;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.axis2.engine.AxisConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.event.adaptor.rabbitmq.internal.AdaptorRuntimeException;
import org.wso2.carbon.event.adaptor.rabbitmq.internal.EventAdapterHelper;
import org.wso2.carbon.event.adaptor.rabbitmq.internal.RabbitMQEventAdaptorConstants;
import org.wso2.carbon.event.input.adaptor.core.AbstractInputEventAdaptor;
import org.wso2.carbon.event.input.adaptor.core.InputEventAdaptorListener;
import org.wso2.carbon.event.input.adaptor.core.MessageType;
import org.wso2.carbon.event.input.adaptor.core.Property;
import org.wso2.carbon.event.input.adaptor.core.config.InputEventAdaptorConfiguration;
import org.wso2.carbon.event.input.adaptor.core.exception.InputEventAdaptorEventProcessingException;
import org.wso2.carbon.event.input.adaptor.core.message.config.InputEventAdaptorMessageConfiguration;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * <pre>
 * RabbitMQInputEventAdaptorType.java
 * Core implementation of RabbitMQ Input Adaptor for WSO2 CEP
 * </pre>
 * 
 * @author jerryj
 * @date   2015. 3. 31.
 *
 */
public final class RabbitMQInputEventAdaptorType extends AbstractInputEventAdaptor {

	private static final Log LOGGER = LogFactory.getLog(RabbitMQInputEventAdaptorType.class);
	
	private ResourceBundle resourceBundle;
	private ConcurrentHashMap<String, MQListener> subscriptionsMap = new ConcurrentHashMap<String, MQListener>();
	
	/**
	 * <pre>
	 * getName
	 * Return unique adaptor name
	 * </pre>
	 * 
	 * @return
	 * @see org.wso2.carbon.event.input.adaptor.core.AbstractInputEventAdaptor#getName()
	 */
	@Override
	protected String getName() {
		LOGGER.debug("*** DEBUG RabbitMQInputEventAdaptorType.getName()");
		return RabbitMQEventAdaptorConstants.EVENT_ADAPTOR_TYPE_RABBITMQ;
	}
	//end of getName()

	/**
	 * <pre>
	 * getSupportedInputMessageTypes
	 * Return list of supported types by this adaptor
	 * </pre>
	 * 
	 * @return
	 * @see org.wso2.carbon.event.input.adaptor.core.AbstractInputEventAdaptor#getSupportedInputMessageTypes()
	 */
	@Override
	protected List<String> getSupportedInputMessageTypes() {
		LOGGER.debug("*** DEBUG RabbitMQInputEventAdaptorType.getSupportedInputMessageTypes()");
		List<String> supportInputMessageTypes = new ArrayList<String>();
		supportInputMessageTypes.add(MessageType.XML);
		supportInputMessageTypes.add(MessageType.JSON);
		supportInputMessageTypes.add(MessageType.MAP);
		supportInputMessageTypes.add(MessageType.TEXT);
		return supportInputMessageTypes;
	}
	//end of getSupportedInputMessageTypes()

	/**
	 * <pre>
	 * init
	 * Loading i18n resource bundle for this adaptor class
	 * </pre>
	 *
	 * @see org.wso2.carbon.event.input.adaptor.core.AbstractInputEventAdaptor#init()
	 */
	@Override
	protected void init() {
		LOGGER.debug("*** DEBUG RabbitMQInputEventAdaptorType.init()");
		this.resourceBundle = ResourceBundle.getBundle("org.wso2.carbon.event.adaptor.rabbitmq.i18n.Resources", Locale.getDefault());
	}
	//end of init()

	/**
	 * <pre>
	 * getInputAdaptorProperties
	 * Return input form fields for input event adaptor creation in WSO2 CEP
	 * </pre>
	 * 
	 * @return
	 * @see org.wso2.carbon.event.input.adaptor.core.AbstractInputEventAdaptor#getInputAdaptorProperties()
	 */
	@Override
	protected List<Property> getInputAdaptorProperties() {
		LOGGER.debug("*** DEBUG RabbitMQInputEventAdaptorType.getInputAdaptorProperties()");
		return EventAdapterHelper.getInputAdaptorProperties(resourceBundle);
	}
	//end of getInputAdaptorProperties()

	/**
	 * <pre>
	 * getInputMessageProperties
	 * Return input form fields for event format builder in WSO2 CEP
	 * </pre>
	 * 
	 * @return
	 * @see org.wso2.carbon.event.input.adaptor.core.AbstractInputEventAdaptor#getInputMessageProperties()
	 */
	@Override
	protected List<Property> getInputMessageProperties() {
		LOGGER.debug("*** DEBUG RabbitMQInputEventAdaptorType.getInputMessageProperties()");

        List<Property> propertyList = new ArrayList<Property>();
        
		Property queueProperty = new Property(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_QUEUE);
		queueProperty.setRequired(true);
		queueProperty.setDisplayName(resourceBundle.getString(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_QUEUE));
		propertyList.add(queueProperty);
		
		return propertyList;
	}
	//end of getInputMessageProperties()

	/**
	 * <pre>
	 * subscribe
	 * Start input adaptor that is queue listener to RabbitMQ and run as another thread
	 * </pre>
	 * 
	 * @param inputEventAdaptorMessageConfiguration
	 * @param inputEventAdaptorListener
	 * @param inputEventAdaptorConfiguration
	 * @param axisConfiguration
	 * @return
	 * @see org.wso2.carbon.event.input.adaptor.core.AbstractInputEventAdaptor#subscribe(org.wso2.carbon.event.input.adaptor.core.message.config.InputEventAdaptorMessageConfiguration, org.wso2.carbon.event.input.adaptor.core.InputEventAdaptorListener, org.wso2.carbon.event.input.adaptor.core.config.InputEventAdaptorConfiguration, org.apache.axis2.engine.AxisConfiguration)
	 */
	@Override
	public String subscribe(InputEventAdaptorMessageConfiguration inputEventAdaptorMessageConfiguration,
			InputEventAdaptorListener inputEventAdaptorListener,
			InputEventAdaptorConfiguration inputEventAdaptorConfiguration, AxisConfiguration axisConfiguration) {

		LOGGER.debug("*** DEBUG RabbitMQInputEventAdaptorType.subscribe()");
		
		Map<String, String> brokerProperties = inputEventAdaptorConfiguration.getInputProperties();
		LOGGER.debug("*** DEBUG RabbitMQInputEventAdaptorType.subscribe() " + brokerProperties);
        
		String hostName = brokerProperties.get(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_HOSTNAME);
		String port = brokerProperties.get(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_PORT);
		String password = brokerProperties.get(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_PASSWORD);
		String userName = brokerProperties.get(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_USERNAME);
		String virtualHost = brokerProperties.get(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_VIRTUALHOST);
		String queue = inputEventAdaptorMessageConfiguration.getInputMessageProperties().get(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_QUEUE);
		//String name = inputEventAdaptorConfiguration.getName();

		String subscriptionId = UUID.randomUUID().toString();

		MQListener listener = new MQListener();
		listener.setHostName(hostName);
		listener.setPort(Integer.parseInt(port));
		listener.setUserName(userName);
		listener.setPassword(password);
		listener.setVirtualHost(virtualHost);
		listener.setQueue(queue);
		listener.setInputEventAdaptorListener(inputEventAdaptorListener);
		try {
			listener.init();
		} catch (IOException e) {
			throw new AdaptorRuntimeException(e);
		}
		listener.start();
		
		subscriptionsMap.put(subscriptionId, listener);
		
		return subscriptionId;
	}
	//end of subscribe()

	/**
	 * <pre>
	 * unsubscribe
	 * Stop input adaptor and stop its thread by invoking interrupt()
	 * </pre>
	 * 
	 * @param inputEventAdaptorMessageConfiguration
	 * @param inputEventAdaptorConfiguration
	 * @param axisConfiguration
	 * @param subscriptionId
	 * @see org.wso2.carbon.event.input.adaptor.core.AbstractInputEventAdaptor#unsubscribe(org.wso2.carbon.event.input.adaptor.core.message.config.InputEventAdaptorMessageConfiguration, org.wso2.carbon.event.input.adaptor.core.config.InputEventAdaptorConfiguration, org.apache.axis2.engine.AxisConfiguration, java.lang.String)
	 */
	@Override
	public void unsubscribe(InputEventAdaptorMessageConfiguration inputEventAdaptorMessageConfiguration,
			InputEventAdaptorConfiguration inputEventAdaptorConfiguration, AxisConfiguration axisConfiguration, String subscriptionId) {
		
		LOGGER.debug("*** DEBUG RabbitMQInputEventAdaptorType.unsubscribe()");
		
		MQListener listener = subscriptionsMap.get(subscriptionId);
		if(listener == null) {
            throw new InputEventAdaptorEventProcessingException("There is no subscription for " + subscriptionId + " for event adaptor " + inputEventAdaptorConfiguration.getName());
		}
		
		listener.interrupt();
		try {
			listener.join();
		} catch (InterruptedException e) {
			LOGGER.debug("Listener will be stopped", e);
		}
	}
	//end of unsubscribe()

	/**
	 * RabbitMQInputEventAdaptorType.java
	 * Actual RabbitMQ queue listener which runs as independent thread
	 * The thread is created by subscribe() and stopped(interrupted) by unsubscribe()
	 *
	 * @author jerryj
	 * @date   2015. 3. 31.
	 *
	 */
	class MQListener extends Thread {
		private String hostName;
		private int port;
		private String userName;
		private String password;
		private String virtualHost;
		private String queue;
		private InputEventAdaptorListener inputEventAdaptorListener;
		private Channel channel;
		private QueueingConsumer consumer;

		/**
		 * <pre>
		 * initialize
		 * </pre>
		 * @throws IOException
		 */
		public void init() throws IOException {
			ConnectionFactory factory = EventAdapterHelper.getConnectionFactory(hostName, port, userName, password, virtualHost);
			
			LOGGER.debug("*** DEBUG: MQListener.run() hostName=" + hostName);
			LOGGER.debug("*** DEBUG: MQListener.run() port=" + port);
			LOGGER.debug("*** DEBUG: MQListener.run() userName=" + userName);
			LOGGER.debug("*** DEBUG: MQListener.run() password=" + password);
			LOGGER.debug("*** DEBUG: MQListener.run() virtualHost=" + virtualHost);
			
			Connection connection = factory.newConnection();
			channel = connection.createChannel();
			channel.basicQos(10);
			channel.queueDeclare(queue, true, false, false, null);
			LOGGER.debug(" [*] Waiting for messages. To exit press CTRL+C");
			consumer = new QueueingConsumer(channel);
			channel.basicConsume(queue, false, consumer);
		}
		//end of init()
		
		/**
		 * <pre>
		 * run
		 * Thread main
		 * </pre>
		 *
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			QueueingConsumer.Delivery delivery = null;
			while (true) {
				try {
					delivery = consumer.nextDelivery();
					String message = new String(delivery.getBody());
					LOGGER.debug(" [x] Received '" + message + "'");
					inputEventAdaptorListener.onEventCall(message);
					channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				} catch (ShutdownSignalException e) {
					LOGGER.warn("RabbitMQ is being shutdown", e);
				} catch (ConsumerCancelledException e) {
					LOGGER.warn("RabbitMQ operation has been cancelled", e);
				} catch (InterruptedException e) {
					LOGGER.warn("MQListener stop request has been received", e);
					try {
						channel.getConnection().close();
					} catch (IOException e1) {}
					break;
				} catch (IOException e) {
					LOGGER.warn("Communication error with RabbitMQ", e);
				} catch (Exception e) {
					LOGGER.warn("Unhandled exception has occurred", e);
				}
			}
		}
		//end of run()

		/**
		 * <pre>
		 * read file with given path
		 * </pre>
		 * @param path
		 * @return
		 * @throws IOException
		 */
		public String readFile(String path) throws IOException {
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			return new String(encoded, Charset.defaultCharset());
		}
		//end of readFile()

		/**
		 * @return the hostName
		 */
		public String getHostName() {
			return hostName;
		}

		/**
		 * @param hostName the hostName to set
		 */
		public void setHostName(String hostName) {
			this.hostName = hostName;
		}
		
		/**
		 * @return the port
		 */
		public int getPort() {
			return port;
		}

		/**
		 * @param port the port to set
		 */
		public void setPort(int port) {
			this.port = port;
		}

		/**
		 * @return the userName
		 */
		public String getUserName() {
			return userName;
		}

		/**
		 * @param userName the userName to set
		 */
		public void setUserName(String userName) {
			this.userName = userName;
		}

		/**
		 * @return the password
		 */
		public String getPassword() {
			return password;
		}

		/**
		 * @param password the password to set
		 */
		public void setPassword(String password) {
			this.password = password;
		}

		/**
		 * @return the virtualHost
		 */
		public String getVirtualHost() {
			return virtualHost;
		}

		/**
		 * @param virtualHost the virtualHost to set
		 */
		public void setVirtualHost(String virtualHost) {
			this.virtualHost = virtualHost;
		}

		/**
		 * @return the queue
		 */
		public String getQueue() {
			return queue;
		}

		/**
		 * @param queue the queue to set
		 */
		public void setQueue(String queue) {
			this.queue = queue;
		}

		/**
		 * @return the inputEventAdaptorListener
		 */
		public InputEventAdaptorListener getInputEventAdaptorListener() {
			return inputEventAdaptorListener;
		}

		/**
		 * @param inputEventAdaptorListener the inputEventAdaptorListener to set
		 */
		public void setInputEventAdaptorListener(
				InputEventAdaptorListener inputEventAdaptorListener) {
			this.inputEventAdaptorListener = inputEventAdaptorListener;
		}

		/**
		 * @return the channel
		 */
		public Channel getChannel() {
			return channel;
		}

		/**
		 * @param channel the channel to set
		 */
		public void setChannel(Channel channel) {
			this.channel = channel;
		}

		/**
		 * @return the consumer
		 */
		public QueueingConsumer getConsumer() {
			return consumer;
		}

		/**
		 * @param consumer the consumer to set
		 */
		public void setConsumer(QueueingConsumer consumer) {
			this.consumer = consumer;
		}
	}
}
