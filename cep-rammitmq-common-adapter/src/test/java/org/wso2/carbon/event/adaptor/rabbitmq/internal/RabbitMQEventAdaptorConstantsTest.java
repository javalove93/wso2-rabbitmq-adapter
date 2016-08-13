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