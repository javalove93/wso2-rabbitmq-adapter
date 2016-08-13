/* Copyright (C) 2015~ Hyundai Heavy Industries. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Hyundai Heavy Industries
 * You shall not disclose such Confidential Information and shall use it only 
 * in accordance with the terms of the license agreement
 * you entered into with Hyundai Heavy Industries.
 *
 * Revision History
 * Author             Date              Description
 * ---------------	----------------	------------
 * Jerry Jeong	       2015. 3. 31.		    First Draft.
 */
package org.wso2.carbon.event.adaptor.rabbitmq.output;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.event.adaptor.rabbitmq.internal.AdaptorRuntimeException;
import org.wso2.carbon.event.output.adaptor.core.OutputEventAdaptorFactory;

/**
 * <pre>
 * RabbitMQEventAdaptorServiceDS.java
 * OSGi service initiator
 * </pre>
 * 
 * @author jerryj
 * @date   2015. 3. 31.
 */
@Component(name="output.RabbitMQEventAdaptorService.component", immediate=true)
public class RabbitMQOutputEventAdaptorServiceDS {

    private static final Log LOGGER = LogFactory.getLog(RabbitMQOutputEventAdaptorServiceDS.class);

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
            OutputEventAdaptorFactory RabbitMQInputEventAdaptorFactory = new RabbitMQOutputEventAdaptorFactory();
            context.getBundleContext().registerService(OutputEventAdaptorFactory.class.getName(), RabbitMQInputEventAdaptorFactory, null);
            LOGGER.info("Successfully deployed the RabbitMQ output event adaptor service");
        } catch (RuntimeException e) {
            LOGGER.error("Can not create the RabbitMQ output event adaptor service ", e);
            throw new AdaptorRuntimeException(e);
        }
    }
    //end of activate()
}
//end of RabbitMQOutputEventAdaptorServiceDS.java