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