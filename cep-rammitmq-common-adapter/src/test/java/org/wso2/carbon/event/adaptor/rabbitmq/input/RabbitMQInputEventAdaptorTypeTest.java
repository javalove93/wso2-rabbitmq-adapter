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
package org.wso2.carbon.event.adaptor.rabbitmq.input;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Test;
import org.wso2.carbon.event.adaptor.rabbitmq.internal.EventAdapterHelper;
import org.wso2.carbon.event.adaptor.rabbitmq.internal.RabbitMQEventAdaptorConstants;
import org.wso2.carbon.event.input.adaptor.core.InputEventAdaptorListener;
import org.wso2.carbon.event.input.adaptor.core.Property;
import org.wso2.carbon.event.input.adaptor.core.config.InputEventAdaptorConfiguration;
import org.wso2.carbon.event.input.adaptor.core.message.config.InputEventAdaptorMessageConfiguration;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQInputEventAdaptorTypeTest {
	
	@AfterClass
	public static void afterClass() {
		try {
			ConnectionFactory factory = EventAdapterHelper.getConnectionFactory("192.168.0.62", 5672, "admin", "admin", "/");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.queueDelete("wso2cep_test_input_queue");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link org.wso2.carbon.event.input.adaptor.rabbitmq.RabbitMQInputEventAdaptorType#getSupportedInputMessageTypes()}.
	 */
	@Test
	public void testGetSupportedInputMessageTypes() {
		RabbitMQInputEventAdaptorType adaptor = new RabbitMQInputEventAdaptorType();
		List<String> types = adaptor.getSupportedInputMessageTypes();
		assertTrue(types != null && types.size() > 0);
	}

	/**
	 * Test method for {@link org.wso2.carbon.event.input.adaptor.rabbitmq.RabbitMQInputEventAdaptorType#getInputAdaptorProperties()}.
	 */
	@Test
	public void testGetInputAdaptorProperties() {
		RabbitMQInputEventAdaptorType adaptor = new RabbitMQInputEventAdaptorType();
		adaptor.init();
		List<Property> propertyList = adaptor.getInputAdaptorProperties();
		assertTrue(propertyList != null && propertyList.size() > 0);
	}

	/**
	 * Test method for {@link org.wso2.carbon.event.input.adaptor.rabbitmq.RabbitMQInputEventAdaptorType#getInputMessageProperties()}.
	 */
	@Test
	public void testGetInputMessageProperties() {
		RabbitMQInputEventAdaptorType adaptor = new RabbitMQInputEventAdaptorType();
		adaptor.init();
		List<Property> propertyList = adaptor.getInputMessageProperties();
		assertTrue(propertyList != null && propertyList.size() > 0);
	}

	@Test
	public void testSubscribe() {
		RabbitMQInputEventAdaptorType adaptor = new RabbitMQInputEventAdaptorType();
		adaptor.init();
		adaptor.subscribe(new MockInputEventAdaptorMessageConfiguration(), new MockInputEventAdaptorListener(), new MockInputEventAdaptorConfiguration(), null);
	}

	@Test
	public void testUnsubscribe() {
		RabbitMQInputEventAdaptorType adaptor = new RabbitMQInputEventAdaptorType();
		adaptor.init();
		String id = adaptor.subscribe(new MockInputEventAdaptorMessageConfiguration(), new MockInputEventAdaptorListener(), new MockInputEventAdaptorConfiguration(), null);
		adaptor.unsubscribe(new MockInputEventAdaptorMessageConfiguration(), new MockInputEventAdaptorConfiguration(), null, id);
	}
	
	class MockInputEventAdaptorMessageConfiguration extends InputEventAdaptorMessageConfiguration {
		@Override
		public Map<String, String> getInputMessageProperties() {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_QUEUE, "wso2cep_test_input_queue");
			return properties;
		}
	}
	
	class MockInputEventAdaptorListener extends InputEventAdaptorListener {

		@Override
		public void addEventDefinition(Object arg0) {
		}

		@Override
		public void onEvent(Object arg0) {
		}

		@Override
		public void removeEventDefinition(Object arg0) {
		}
		
	}
	
	class MockInputEventAdaptorConfiguration extends InputEventAdaptorConfiguration {
		@Override
		public Map<String, String> getInputProperties() {
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
//end of RabbitMQInputEventAdaptorTypeTest.java