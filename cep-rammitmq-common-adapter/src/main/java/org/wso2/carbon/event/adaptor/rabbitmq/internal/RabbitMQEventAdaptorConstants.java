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
package org.wso2.carbon.event.adaptor.rabbitmq.internal;


/**
 * <pre>
 * RabbitMQEventAdaptorConstants.java
 * Constants definitions
 * </pre>
 * 
 * @author jerryj
 * @date   2015. 3. 31.
 */
public final class RabbitMQEventAdaptorConstants {

    /**
     * <pre>
     * Default Constructor
     * to prevent create a instance
     * </pre>
     */
    private RabbitMQEventAdaptorConstants() {
    }
    //end of default contructor()

    /** rabbitmq adaptor type */
    public static final String EVENT_ADAPTOR_TYPE_RABBITMQ = "rabbitmq";
    /** rabbitmq hostname */
    public static final String ADAPTOR_RABBITMQ_HOSTNAME = "transport.rabbitmq.HostName";
    /** rabbitmq port */
    public static final String ADAPTOR_RABBITMQ_PORT = "transport.rabbitmq.Port";
    /** rabbitmq virtual host */
    public static final String ADAPTOR_RABBITMQ_VIRTUALHOST = "transport.rabbitmq.VirtualHost";
    /** rabbitmq username */
    public static final String ADAPTOR_RABBITMQ_USERNAME = "transport.rabbitmq.UserName";
    /** rabbitmq password */
    public static final String ADAPTOR_RABBITMQ_PASSWORD = "transport.rabbitmq.Password";
    /** rabbitmq queue name */
    public static final String ADAPTOR_RABBITMQ_QUEUE = "transport.rabbitmq.Queue";
    
}
//end of RabbitMQEventAdaptorConstants.java