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

package org.wso2.carbon.event.adaptor.rabbitmq.input;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.event.input.adaptor.core.AbstractInputEventAdaptor;
import org.wso2.carbon.event.input.adaptor.core.InputEventAdaptorFactory;

/**
 * <pre>
 * RabbitMQInputEventAdaptorFactory.java
 * Factory class to return RabbitMQEventAdaptor instance
 * </pre>
 * 
 * @author jerryj
 * @date   2015. 3. 31.
 */
public class RabbitMQInputEventAdaptorFactory implements InputEventAdaptorFactory {

    private static final Log LOGGER = LogFactory.getLog(RabbitMQInputEventAdaptorFactory.class);
    
    /**
     * <pre>
     * getEventAdaptor
     * Return RabbitMQEventAdaptor instance
     * </pre>
     * 
     * @return
     * @see org.wso2.carbon.event.input.adaptor.core.InputEventAdaptorFactory#getEventAdaptor()
     */
    public AbstractInputEventAdaptor getEventAdaptor() {
    	LOGGER.debug("*** DEBUG Input RabbitMQInputEventAdaptorFactory.getEventAdaptor()");
        return new RabbitMQInputEventAdaptorType();
    }
    //end of getEventAdaptor()
}
//end of RabbitMQInputEventAdaptorFactory.java