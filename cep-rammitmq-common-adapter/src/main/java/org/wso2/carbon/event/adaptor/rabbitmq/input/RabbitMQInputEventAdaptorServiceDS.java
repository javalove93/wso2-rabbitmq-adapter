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
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.event.adaptor.rabbitmq.internal.AdaptorRuntimeException;
import org.wso2.carbon.event.input.adaptor.core.InputEventAdaptorFactory;

/**
 * <pre>
 * RabbitMQEventAdaptorServiceDS.java
 * OSGi service initiator
 * </pre>
 * 
 * @author jerryj
 * @date   2015. 3. 31.
 *
 */
@Component(name="input.RabbitMQEventAdaptorService.component", immediate=true)
public class RabbitMQInputEventAdaptorServiceDS {

    private static final Log LOGGER = LogFactory.getLog(RabbitMQInputEventAdaptorServiceDS.class);

    /**
     * <pre>
     * initialize the agent service here service here.
     * </pre>
     * 
     * @param context
     */
    @Activate
    protected void activate(ComponentContext context) {
        try {
            InputEventAdaptorFactory RabbitMQInputEventAdaptorFactory = new RabbitMQInputEventAdaptorFactory();
            context.getBundleContext().registerService(InputEventAdaptorFactory.class.getName(), RabbitMQInputEventAdaptorFactory, null);
            LOGGER.info("Successfully deployed the RabbitMQ input event adaptor service");
        } catch (RuntimeException e) {
            LOGGER.error("Can not create the RabbitMQ input event adaptor service ", e);
            throw new AdaptorRuntimeException(e);
        }
    }
    //end of activate()
}
//end of RabbitMQInputEventAdaptorServiceDS.java
