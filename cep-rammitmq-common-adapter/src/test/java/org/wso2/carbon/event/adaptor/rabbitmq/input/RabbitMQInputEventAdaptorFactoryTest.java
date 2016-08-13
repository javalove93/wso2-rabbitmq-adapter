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

import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * <pre>
 * 
 * </pre>
 * @author Sang-cheon Park
 * @version 1.0
 */
public class RabbitMQInputEventAdaptorFactoryTest {

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	@Test
	public void testGetEventAdaptor() {
		RabbitMQInputEventAdaptorFactory fac = new RabbitMQInputEventAdaptorFactory();
		Object adaptor = fac.getEventAdaptor();
		assertTrue(adaptor instanceof RabbitMQInputEventAdaptorType);
	}

}
//end of RabbitMQInputEventAdaptorFactoryTest.java