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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RabbitMQOutputEventAdaptorFactoryTest {

	@Test
	public void testGetEventAdaptor() {
		RabbitMQOutputEventAdaptorFactory fac = new RabbitMQOutputEventAdaptorFactory();
		Object adaptor = fac.getEventAdaptor();
		assertTrue(adaptor instanceof RabbitMQOutputEventAdaptorType);
	}

}
//end of RabbitMQOutputEventAdaptorFactoryTest.java