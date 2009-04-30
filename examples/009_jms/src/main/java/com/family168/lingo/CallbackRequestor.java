package com.family168.lingo;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.logicblaze.lingo.jms.impl.AsyncReplyHandler;
import org.logicblaze.lingo.jms.impl.MultiplexingRequestor;


public class CallbackRequestor extends MultiplexingRequestor {
    public CallbackRequestor(Connection connection, Session session,
        MessageProducer producer, Destination destination,
        Destination responseDestination, boolean ownsConnection)
        throws JMSException {
        super(connection, session, producer, destination,
            responseDestination, ownsConnection);
    }

    protected void doSend(Destination destination, Message message,
        long timeToLive) throws JMSException {
        this.doSend(destination, message, getDeliveryMode(),
            getPriority(), timeToLive);
    }

    protected void doSend(Destination destination, Message message,
        int deliveryMode, int priority, long timeToLive)
        throws JMSException {
        destination = validateDestination(destination);
        // setup callback destination
        populateHeaders(message);

        getMessageProducer()
            .send(destination, message, deliveryMode, priority, timeToLive);
    }
}
