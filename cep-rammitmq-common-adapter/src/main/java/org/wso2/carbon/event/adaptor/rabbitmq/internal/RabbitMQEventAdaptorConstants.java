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