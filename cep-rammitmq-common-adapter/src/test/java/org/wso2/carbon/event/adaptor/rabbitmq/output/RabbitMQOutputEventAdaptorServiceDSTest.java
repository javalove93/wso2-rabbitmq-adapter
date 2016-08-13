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
package org.wso2.carbon.event.adaptor.rabbitmq.output;

import org.junit.Test;

/**
 * <pre>
 * 
 * </pre>
 * @author Sang-cheon Park
 * @version 1.0
 */
public class RabbitMQOutputEventAdaptorServiceDSTest {

	/**
	 * Test method for {@link org.wso2.carbon.event.adaptor.rabbitmq.input.RabbitMQInputEventAdaptorServiceDS#activate(org.osgi.service.component.ComponentContext)}.
	 */
	@Test
	public void testActivate() {
		try {
			new RabbitMQOutputEventAdaptorServiceDS().activate(null);
		} catch (Exception e) {
		}
	}
}
//end of RabbitMQOutputEventAdaptorServiceDSTest.java