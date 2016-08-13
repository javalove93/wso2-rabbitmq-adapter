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
 * Sang-cheon Park	2015. 9. 7.		First Draft.
 */
package org.wso2.carbon.event.adaptor.rabbitmq.internal;

import org.junit.Test;

/**
 * <pre>
 * 
 * </pre>
 * @author Sang-cheon Park
 * @version 1.0
 */
public class RabbitMQEventAdaptorConstantsTest {

	@Test
	public void test() {
		System.out.println(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_HOSTNAME);
		System.out.println(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_PASSWORD);
		System.out.println(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_PORT);
		System.out.println(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_QUEUE);
		System.out.println(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_USERNAME);
		System.out.println(RabbitMQEventAdaptorConstants.ADAPTOR_RABBITMQ_VIRTUALHOST);
	}
}
//end of RabbitMQEventAdaptorConstantsTest.java