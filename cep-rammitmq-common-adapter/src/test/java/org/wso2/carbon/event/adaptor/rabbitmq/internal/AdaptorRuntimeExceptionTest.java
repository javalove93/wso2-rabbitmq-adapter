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