# JMSMessage-with-IBM-MQ-and-Apache-Tomcat
This project help in sending and receive IBM MQ messages using JMS api and Apache Tomcat. 
I spent couple of weeks to figure out solution for this combination as we really don't get on web using MQListener procedure

# Prerequisite
1. WebSphere MQ requirements
 
   Copy the following jars from [WebSphere MQ] to [Tomcat/lib]

    com.ibm.mq.jar

    com.ibm.mq.jmqi.jar
    
    com.ibm.mqjms.jar
    
    com.ibm.mq.pcf.jar
    
    com.ibm.mq.soap.jar
    
    com.ibm.mq.jms.Nojndi.jar
    
    connector.jar
    
    dhbcore.jar
    
    fscontext.jar
    
    jms.jar
    
    jndi.jar
    
    geronimo-j2ee-management_1.0_spec-1.0.jar
    
    geronimo-jms_1.1_spec-1.1.1.jar

2. QueueConnectionFactory and Queue Setup: Refer context.xml
3. JMSContextListener - To initiate MessageListner on server startup
4. JMSMQListener - JMS Message Listner to receive messages once placed in receiver queue
5. JMSMQSender - To send JMS messages in sender queue
