package com.family168.lingo;

import javax.jms.JMSException;
import javax.jms.Message;

import org.logicblaze.lingo.jms.JmsProxyFactoryBean;
import org.logicblaze.lingo.jms.JmsServiceExporter;


public class OneWayServer extends JmsServiceExporter {
    protected Object createRemoteProxy(Message message,
        Class parameterType, Object argument) throws JMSException {
        JmsProxyFactoryBean factory = new JmsProxyFactoryBean();
        factory.setDestination(message.getJMSReplyTo());

        String correlationID = (String) argument;

        factory.setCorrelationID(correlationID);
        factory.setMarshaller(getMarshaller());
        factory.setRemoteInvocationFactory(getInvocationFactory());
        factory.setMetadataStrategy(getMetadataStrategy());
        factory.setServiceInterface(parameterType);
        factory.setRequestor(getResponseRequestor());
        factory.afterPropertiesSet();

        return factory.getObject();
    }
}
