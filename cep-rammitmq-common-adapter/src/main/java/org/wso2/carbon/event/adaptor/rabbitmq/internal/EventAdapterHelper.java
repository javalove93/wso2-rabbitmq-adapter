/*
Copyright [2016] [Jerry Jeong]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package org.wso2.carbon.event.adaptor.rabbitmq.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.rabbitmq.client.ConnectionFactory;

/**
 * <pre>
 * EventAdapterHelper class to create AdaptorProperties and ConnectionFactory
 * </pre>
 * @author Sang-cheon Park
 * @version 1.0
 */
public final class EventAdapterHelper {
	
	/**
	 * <pre>
	 * Create a adaptor properties for rabbitmq input adaptor
	 * </pre>
	 * @param resourceBundle
	 * @return
	 */
	public static List<org.wso2.carbon.event.input.adaptor.core.Property> getInputAdaptorProperties(ResourceBundle resourceBundle) {
		List<org.wso2.carbon.event.input.adaptor.core.Property> propertyList = new ArrayList<org.wso2.carbon.event.input.adaptor.core.Property>();

		// RabbitMQ Connection Information
		org.wso2.carbon.event.input.adaptor.core.Property property = new org.wso2.carbon.event.input.adaptor.core.Property(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_USERNAME);
		property.setDisplayName(resourceBundle.getString(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_USERNAME));
		propertyList.add(property);

		property = new org.wso2.carbon.event.input.adaptor.core.Property(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_PASSWORD);
		property.setSecured(true);
		property.setDisplayName(resourceBundle.getString(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_PASSWORD));
		propertyList.add(property);

		property = new org.wso2.carbon.event.input.adaptor.core.Property(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_HOSTNAME);
		property.setRequired(true);
		property.setDisplayName(resourceBundle.getString(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_HOSTNAME));
		propertyList.add(property);

		property = new org.wso2.carbon.event.input.adaptor.core.Property(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_PORT);
		property.setRequired(false);
		property.setDefaultValue("5672");
		property.setDisplayName(resourceBundle.getString(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_PORT));
		propertyList.add(property);

		property = new org.wso2.carbon.event.input.adaptor.core.Property(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_VIRTUALHOST);
		property.setRequired(false);
		property.setDefaultValue("/");
		property.setDisplayName(resourceBundle.getString(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_VIRTUALHOST));
		propertyList.add(property);

		return propertyList;
	}
	//end of getInputAdaptorProperties()
	
	/**
	 * <pre>
	 * Create a adaptor properties for rabbitmq output adaptor
	 * </pre>
	 * @param resourceBundle
	 * @return
	 */
	public static List<org.wso2.carbon.event.output.adaptor.core.Property> getOutputAdaptorProperties(ResourceBundle resourceBundle) {
		List<org.wso2.carbon.event.output.adaptor.core.Property> propertyList = new ArrayList<org.wso2.carbon.event.output.adaptor.core.Property>();

		// RabbitMQ Connection Information
		org.wso2.carbon.event.output.adaptor.core.Property property = new org.wso2.carbon.event.output.adaptor.core.Property(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_USERNAME);
		property.setDisplayName(resourceBundle.getString(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_USERNAME));
		propertyList.add(property);

		property = new org.wso2.carbon.event.output.adaptor.core.Property(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_PASSWORD);
		property.setSecured(true);
		property.setDisplayName(resourceBundle.getString(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_PASSWORD));
		propertyList.add(property);

		property = new org.wso2.carbon.event.output.adaptor.core.Property(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_HOSTNAME);
		property.setRequired(true);
		property.setDisplayName(resourceBundle.getString(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_HOSTNAME));
		propertyList.add(property);

		property = new org.wso2.carbon.event.output.adaptor.core.Property(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_PORT);
		property.setRequired(false);
		property.setDefaultValue("5672");
		property.setDisplayName(resourceBundle.getString(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_PORT));
		propertyList.add(property);

		property = new org.wso2.carbon.event.output.adaptor.core.Property(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_VIRTUALHOST);
		property.setRequired(false);
		property.setDefaultValue("/");
		property.setDisplayName(resourceBundle.getString(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_VIRTUALHOST));
		propertyList.add(property);

		return propertyList;
	}
	//end of getOutputAdaptorProperties()
	
	/**
	 * <pre>
	 * Create a rabbitmq ConnectionFactory instance
	 * </pre>
	 * @param hostName
	 * @param port
	 * @param userName
	 * @param password
	 * @param virtualHost
	 * @return
	 */
	public synchronized static ConnectionFactory getConnectionFactory(String hostName, int port, String userName, String password, String virtualHost) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(hostName);
		factory.setPort(port);
		factory.setUsername(userName);
		factory.setPassword(password);
		factory.setVirtualHost(virtualHost);

		/**
		 * Add connection recovery logic
		 * @author Sang-Cheon Park
		 * @date 2015.07.16
		 */
		/**
		 * connection that will recover automatically
		 */
		factory.setAutomaticRecoveryEnabled(true);
		/**
		 * attempt recovery every 5 seconds
		 */
		factory.setNetworkRecoveryInterval(5*1000);
		/**
		 * wait maximum 10 seconds to connect(if connection failed, will be retry to connect after 5 seconds)
		 */
		factory.setConnectionTimeout(10*1000);
		
		return factory;
	}
	//end of getConnectionFactory()
}
//end of EventAdapterHelper.java