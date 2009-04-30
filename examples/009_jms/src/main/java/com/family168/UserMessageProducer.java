package com.family168;

import javax.jms.Queue;

import org.springframework.jms.core.JmsTemplate;


public class UserMessageProducer {
    private JmsTemplate template;
    private Queue destination;

    public void setTemplate(JmsTemplate template) {
        this.template = template;
    }

    public void setDestination(Queue destination) {
        this.destination = destination;
    }

    public void send(User user) {
        template.convertAndSend(this.destination, user);
    }
}
