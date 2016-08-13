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

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * <pre>
 * 
 * </pre>
 * @author Sang-cheon Park
 * @version 1.0
 */
public class AdaptorRuntimeExceptionTest {

	@Test
	public void testAdaptorRuntimeException() {
		AdaptorRuntimeException exception = new AdaptorRuntimeException(new Exception("message"));
		assertNotNull("exception must not be null", exception);
	}
}
//end of AdaptorRuntimeExceptionTest.java