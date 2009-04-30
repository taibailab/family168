package com.family168.lingo;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.logicblaze.lingo.jms.JmsProducerConfig;
import org.logicblaze.lingo.jms.Requestor;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;


public class CallbackRequestorFactoryBean implements FactoryBean,
    InitializingBean {
    private CallbackRequestor requestor;
    private ConnectionFactory connectionFactory;
    private JmsProducerConfig producerConfig;
    private Destination destination;
    private Destination responseDestination;

    public void afterPropertiesSet() throws JMSException {
        Connection connection = producerConfig.createConnection(connectionFactory);
        Session session = producerConfig.createSession(connection);
        MessageProducer producer = producerConfig.createMessageProducer(session);
        requestor = new CallbackRequestor(connection, session, producer,
                destination, responseDestination, true);
    }

    public Object getObject() {
        return this.requestor;
    }

    public Class getObjectType() {
        return Requestor.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void setProducerConfig(JmsProducerConfig producerConfig) {
        this.producerConfig = producerConfig;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public void setResponseDestination(Destination responseDestination) {
        this.responseDestination = responseDestination;
    }
}
