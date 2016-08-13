# wso2-rabbitmq-adapter

WSO2 CEP 3.1.0 AMQP(RabbitMQ) Transport

This transport has been developed based on the following the guide(Sucks !!):

* http://wso2.com/library/articles/2013/08/writing-custom-event-adaptors-for-cep-3.0.0/

How to build
  1. git clone from the repository and import into Eclipse (Tested on Luna, Mars)
  2. Run As ==> Maven Install
  3. Artifact: target\org.wso2.carbon.event.input.adaptor.rabbitmq-0.2.0.jar (OSGi bundle)

How to install(deploy) to WSO2 CEP 3.1
  1. Copy org.wso2.carbon.event.input.adaptor.rabbitmq-0.2.0.jar and amqp-client-3.4.3.jar(Maven repository) into wso2cep-3.1.0/repository/components/dropins directory
  2. Restart WSO2 CEP
  3. Go to WSO2 CEP console (https://ip:9443/carbon)
  4. Home > Configure > Event Processor Configs > Input Event Adaptors
  5. Add Input Event Adapter
  6. See rabbitmq at Event Adaptor Type listbox


