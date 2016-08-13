/* Copyright (C) 2015~ Hyundai Heavy Industries. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Hyundai Heavy Industries
 * You shall not disclose such Confidential Information and shall use it only 
 * in accordance with the terms of the license agreement
 * you entered into with Hyundai Heavy Industries.
 *
 * Revision History
 * Author			Date				Description
 * ---------------	----------------	------------
 * Sang-cheon Park	2015. 7. 17.		First Draft.
 */
package org.wso2.carbon.event.adaptor.rabbitmq.output;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Test;
import org.wso2.carbon.event.adaptor.rabbitmq.internal.EventAdapterHelper;
import org.wso2.carbon.event.adaptor.rabbitmq.internal.RabbitMQEventAdaptorConstants;
import org.wso2.carbon.event.output.adaptor.core.Property;
import org.wso2.carbon.event.output.adaptor.core.config.OutputEventAdaptorConfiguration;
import org.wso2.carbon.event.output.adaptor.core.message.config.OutputEventAdaptorMessageConfiguration;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQOutputEventAdaptorTypeTest {
	
	@AfterClass
	public static void afterClass() {
		try {
			ConnectionFactory factory = EventAdapterHelper.getConnectionFactory("192.168.0.62", 5672, "admin", "admin", "/");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.queueDelete("wso2cep_test_output_queue");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link org.wso2.carbon.event.output.adaptor.rabbitmq.RabbitMQOutputEventAdaptorType#getSupportedOutputMessageTypes()}.
	 */
	@Test
	public void testGetSupportedOutputMessageTypes() {
		RabbitMQOutputEventAdaptorType adaptor = new RabbitMQOutputEventAdaptorType();
		List<String> types = adaptor.getSupportedOutputMessageTypes();
		assertTrue(types != null && types.size() > 0);
	}

	/**
	 * Test method for {@link org.wso2.carbon.event.output.adaptor.rabbitmq.RabbitMQOutputEventAdaptorType#getOutputAdaptorProperties()}.
	 */
	@Test
	public void testGetOutputAdaptorProperties() {
		RabbitMQOutputEventAdaptorType adaptor = new RabbitMQOutputEventAdaptorType();
		adaptor.init();
		List<Property> propertyList = adaptor.getOutputAdaptorProperties();
		assertTrue(propertyList != null && propertyList.size() > 0);
	}

	/**
	 * Test method for {@link org.wso2.carbon.event.output.adaptor.rabbitmq.RabbitMQOutputEventAdaptorType#getOutputMessageProperties()}.
	 */
	@Test
	public void testGetOutputMessageProperties() {
		RabbitMQOutputEventAdaptorType adaptor = new RabbitMQOutputEventAdaptorType();
		adaptor.init();
		List<Property> propertyList = adaptor.getOutputMessageProperties();
		assertTrue(propertyList != null && propertyList.size() > 0);
	}

	@Test
	public void testPublish() {
		RabbitMQOutputEventAdaptorType adaptor = new RabbitMQOutputEventAdaptorType();
		adaptor.init();
		adaptor.publish(new MockOutputEventAdaptorMessageConfiguration(), "Hello", new MockOutputEventAdaptorConfiguration(), 1);
	}

	@Test
	public void testRemoveConnection() {
		RabbitMQOutputEventAdaptorType adaptor = new RabbitMQOutputEventAdaptorType();
		adaptor.init();
		adaptor.removeConnectionInfo(new MockOutputEventAdaptorMessageConfiguration(), new MockOutputEventAdaptorConfiguration(), 1);
	}

	@Test
	public void testTestConnection() {
		RabbitMQOutputEventAdaptorType adaptor = new RabbitMQOutputEventAdaptorType();
		adaptor.init();
		adaptor.testConnection(new MockOutputEventAdaptorConfiguration(), 1);
	}
	
	class MockOutputEventAdaptorMessageConfiguration extends OutputEventAdaptorMessageConfiguration {
		@Override
		public Map<String, String> getOutputMessageProperties() {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_QUEUE, "wso2cep_test_output_queue");
			return properties;
		}
	}
	
	class MockOutputEventAdaptorConfiguration extends OutputEventAdaptorConfiguration {
		@Override
		public Map<String, String> getOutputProperties() {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_HOSTNAME, "192.168.0.62");
			properties.put(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_PORT, "5672");
			properties.put(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_PASSWORD, "admin");
			properties.put(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_USERNAME, "admin");
			properties.put(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_VIRTUALHOST, "/");
			return properties;
		}
	}
}
//end of RabbitMQOutputEventAdaptorTypeTest.java