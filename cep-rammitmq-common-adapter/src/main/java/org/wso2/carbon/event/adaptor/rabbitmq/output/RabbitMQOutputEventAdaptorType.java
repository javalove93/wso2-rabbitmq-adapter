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
package org.wso2.carbon.event.adaptor.rabbitmq.output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.event.adaptor.rabbitmq.internal.AdaptorRuntimeException;
import org.wso2.carbon.event.adaptor.rabbitmq.internal.EventAdapterHelper;
import org.wso2.carbon.event.adaptor.rabbitmq.internal.RabbitMQEventAdaptorConstants;
import org.wso2.carbon.event.output.adaptor.core.AbstractOutputEventAdaptor;
import org.wso2.carbon.event.output.adaptor.core.MessageType;
import org.wso2.carbon.event.output.adaptor.core.Property;
import org.wso2.carbon.event.output.adaptor.core.config.OutputEventAdaptorConfiguration;
import org.wso2.carbon.event.output.adaptor.core.message.config.OutputEventAdaptorMessageConfiguration;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * <pre>
 * RabbitMQOutputEventAdaptorType.java
 * Core implementation of RabbitMQ Output Adaptor for WSO2 CEP
 * </pre>
 * 
 * @author jerryj
 * @date   2015. 3. 31.
 */
public final class RabbitMQOutputEventAdaptorType extends AbstractOutputEventAdaptor {

	private static final Log LOGGER = LogFactory.getLog(RabbitMQOutputEventAdaptorType.class);
	private ResourceBundle resourceBundle;
	private HashMap<Integer, Channel> cache = new HashMap<Integer, Channel>();
	private Connection connection;
	
	/**
	 * <pre>
	 * getName
	 * Return unique adaptor name
	 * <pre>
	 * 
	 * @return
	 * @see org.wso2.carbon.event.output.adaptor.core.AbstractOutputEventAdaptor#getName()
	 */
	@Override
	protected String getName() {
		LOGGER.debug("*** DEBUG RabbitMQOutputEventAdaptorType.getName()");
		return RabbitMQEventAdaptorConstants.EVENT_ADAPTOR_TYPE_RABBITMQ;
	}
	//end of getName()

	/**
	 * <pre>
	 * getSupportedOutputMessageTypes
	 * Return list of supported types by this adaptor
	 * <pre>
	 * 
	 * @return
	 * @see org.wso2.carbon.event.output.adaptor.core.AbstractOutputEventAdaptor#getSupportedOutputMessageTypes()
	 */
	@Override
	protected List<String> getSupportedOutputMessageTypes() {
		LOGGER.debug("*** DEBUG RabbitMQOutputEventAdaptorType.getSupportedOutputMessageTypes()");
		List<String> supportOutputMessageTypes = new ArrayList<String>();
		supportOutputMessageTypes.add(MessageType.XML);
		supportOutputMessageTypes.add(MessageType.JSON);
		supportOutputMessageTypes.add(MessageType.MAP);
		supportOutputMessageTypes.add(MessageType.TEXT);
		return supportOutputMessageTypes;
	}
	//end of getSupportedOutputMessageTypes()

	/**
	 * <pre>
	 * init
	 * Loading i18n resource bundle for this adaptor class
	 * <pre>
	 *
	 * @see org.wso2.carbon.event.output.adaptor.core.AbstractOutputEventAdaptor#init()
	 */
	@Override
	protected void init() {
		LOGGER.debug("*** DEBUG RabbitMQOutputEventAdaptorType.init()");
		this.resourceBundle = ResourceBundle.getBundle("org.wso2.carbon.event.adaptor.rabbitmq.i18n.Resources", Locale.getDefault());
	}
	//end of init()

	/**
	 * <pre>
	 * getOutputAdaptorProperties
	 * Return output form fields for output event adaptor creation in WSO2 CEP
	 * <pre>
	 * 
	 * @return
	 * @see org.wso2.carbon.event.output.adaptor.core.AbstractOutputEventAdaptor#getOutputAdaptorProperties()
	 */
	@Override
	protected List<Property> getOutputAdaptorProperties() {
		LOGGER.debug("*** DEBUG RabbitMQOutputEventAdaptorType.getOutputAdaptorProperties()");
		return EventAdapterHelper.getOutputAdaptorProperties(resourceBundle);
	}
	//end of getOutputAdaptorProperties()

	/**
	 * <pre>
	 * getOutputMessageProperties
	 * Return output form fields for event format builder in WSO2 CEP
	 * <pre>
	 * 
	 * @return
	 * @see org.wso2.carbon.event.output.adaptor.core.AbstractOutputEventAdaptor#getOutputMessageProperties()
	 */
	@Override
	protected List<Property> getOutputMessageProperties() {
		LOGGER.debug("*** DEBUG RabbitMQOutputEventAdaptorType.getOutputMessageProperties()");

        List<Property> propertyList = new ArrayList<Property>();
        
		Property queueProperty = new Property(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_QUEUE);
		queueProperty.setRequired(true);
		queueProperty.setDisplayName(resourceBundle.getString(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_QUEUE));
		propertyList.add(queueProperty);
		
		return propertyList;
	}
	//end of getOutputMessageProperties()

	/**
	 * <pre>
	 * publish
	 * <pre>
	 * 
	 * @param outputEventAdaptorMessageConfiguration
	 * @param message
	 * @param outputEventAdaptorConfiguration
	 * @param tenantId
	 * @see org.wso2.carbon.event.output.adaptor.core.AbstractOutputEventAdaptor#publish(org.wso2.carbon.event.output.adaptor.core.message.config.OutputEventAdaptorMessageConfiguration, java.lang.Object, org.wso2.carbon.event.output.adaptor.core.config.OutputEventAdaptorConfiguration, int)
	 */
	@Override
	protected void publish(OutputEventAdaptorMessageConfiguration outputEventAdaptorMessageConfiguration,
			Object message, OutputEventAdaptorConfiguration outputEventAdaptorConfiguration, int tenantId) {
		
		LOGGER.debug("*** DEBUG RabbitMQOutputEventAdaptorType.publish()");
		
		try {
			Channel channel = getChannel(outputEventAdaptorConfiguration, tenantId);
			
			String queue = outputEventAdaptorMessageConfiguration.getOutputMessageProperties().get(
					RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_QUEUE);
			
			boolean isExist = false;
			try {
				channel.queueDeclarePassive(queue);
				isExist = true;
			} catch (IOException e) {
				LOGGER.info("*** INFO : [" + queue + "] does not exist.");
			}
			
			if (!isExist) {
				String dlmExchangeName = "exchange_dlm";
				String routingKey = queue;

				if (!channel.isOpen()) {
					channel = getChannel(outputEventAdaptorConfiguration, tenantId);
				}

				/**
				 *  Add configuration for DLM
				 */
				Map<String, Object> arg = new HashMap<String, Object>();
				arg.put("x-dead-letter-exchange", dlmExchangeName);
				arg.put("x-dead-letter-routing-key", routingKey);
				
				/**
				 *  Create a queue and binding with DLM
				 */
				channel.queueDeclare(queue, true, false, false, arg);
				channel.queueBind(queue, dlmExchangeName, routingKey);
			}
			
			if (message instanceof String) {
				channel.basicPublish("", queue, MessageProperties.PERSISTENT_TEXT_PLAIN, ((String)message).getBytes());
			} else {
				channel.basicPublish("", queue, MessageProperties.PERSISTENT_TEXT_PLAIN, message.toString().getBytes());
			}
			LOGGER.debug("*** DEBUG: [x] Sent " + message.getClass() + " type, '" + message + "'");
		} catch (IOException e) {
			throw new AdaptorRuntimeException(e);
		}
	}
	//end of publish()

	/**
	 * <pre>
	 * removeConnectionInfo
	 * <pre>
	 * 
	 * @param outputEventAdaptorMessageConfiguration
	 * @param outputEventAdaptorConfiguration
	 * @param tenantId
	 * @see org.wso2.carbon.event.output.adaptor.core.AbstractOutputEventAdaptor#removeConnectionInfo(org.wso2.carbon.event.output.adaptor.core.message.config.OutputEventAdaptorMessageConfiguration, org.wso2.carbon.event.output.adaptor.core.config.OutputEventAdaptorConfiguration, int)
	 */
	@Override
	public void removeConnectionInfo(
			OutputEventAdaptorMessageConfiguration outputEventAdaptorMessageConfiguration,
			OutputEventAdaptorConfiguration outputEventAdaptorConfiguration, int tenantId) {
		
		Channel channel = cache.remove(tenantId);
		if (channel != null) {
			try {
				channel.close();
				//channel.getConnection().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LOGGER.warn("RabbitMQ connection was closed already", e);
			}
		}
	}
	//end of removeConnectionInfo()

	/**
	 * <pre>
	 * testConnection
	 * <pre>
	 * 
	 * @param outputEventAdaptorConfiguration
	 * @param tenantId
	 * @see org.wso2.carbon.event.output.adaptor.core.AbstractOutputEventAdaptor#testConnection(org.wso2.carbon.event.output.adaptor.core.config.OutputEventAdaptorConfiguration, int)
	 */
	@Override
	public void testConnection(OutputEventAdaptorConfiguration outputEventAdaptorConfiguration, int tenantId) {
		Channel channel = getChannel(outputEventAdaptorConfiguration, tenantId);
		try {
			channel.basicQos(1);
		} catch (IOException e) {
			throw new AdaptorRuntimeException(e);
		}
	}
	//end of testConnection()
	
	/**
	 * <pre>
	 * get channel
	 * </pre>
	 * 
	 * @param outputEventAdaptorConfiguration
	 * @param tenantId
	 * @return
	 */
	Channel getChannel(OutputEventAdaptorConfiguration outputEventAdaptorConfiguration, int tenantId) {
		Map<String, String> brokerProperties = outputEventAdaptorConfiguration.getOutputProperties();
		LOGGER.debug("*** DEBUG RabbitMQOutputEventAdaptorType.getChannel() " + brokerProperties);

		Channel channel = cache.get(tenantId);
		if (channel == null || !channel.isOpen()) {
			String hostName = brokerProperties.get(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_HOSTNAME);
			String port = brokerProperties.get(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_PORT);
			String password = brokerProperties.get(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_PASSWORD);
			String userName = brokerProperties.get(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_USERNAME);
			String virtualHost = brokerProperties.get(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_VIRTUALHOST);
			
			ConnectionFactory factory = EventAdapterHelper.getConnectionFactory(hostName, Integer.parseInt(port), userName, password, virtualHost);
			
			LOGGER.debug("*** DEBUG: RabbitMQOutputEventAdaptorType.getChannel() hostName=" + hostName);
			LOGGER.debug("*** DEBUG: RabbitMQOutputEventAdaptorType.getChannel() port=" + port);
			LOGGER.debug("*** DEBUG: RabbitMQOutputEventAdaptorType.getChannel() userName=" + userName);
			LOGGER.debug("*** DEBUG: RabbitMQOutputEventAdaptorType.getChannel() password=" + password);
			LOGGER.debug("*** DEBUG: RabbitMQOutputEventAdaptorType.getChannel() virtualHost=" + virtualHost);
			LOGGER.debug("*** DEBUG: RabbitMQOutputEventAdaptorType.getChannel() tenantId=" + tenantId);

			try {
				if (connection == null || !connection.isOpen()) {
					connection = factory.newConnection();
				}
				
				/**
				 * Multiple operations into Channel instance are serialized and thread-safe
				 */
				channel = connection.createChannel();
				channel.basicQos(1);
				cache.put(tenantId, channel);
			} catch (IOException e) {
				LOGGER.warn("Failed to create communication channel", e);
				throw new AdaptorRuntimeException(e);
			}
		}
		
		return channel;
	}
	//end of getChannel()
}
//end of RabbitMQOutputEventAdaptorType.java