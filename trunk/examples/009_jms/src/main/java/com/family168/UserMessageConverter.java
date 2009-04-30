package com.family168;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQObjectMessage;

import org.springframework.jms.support.converter.MessageConverter;


public class UserMessageConverter implements MessageConverter {
    public Message toMessage(Object obj, Session session)
        throws JMSException {
        //check Type
        if (obj instanceof User) {
            ActiveMQObjectMessage objMsg = (ActiveMQObjectMessage) session
                .createObjectMessage();
            HashMap<String, byte[]> map = new HashMap<String, byte[]>();

            try {
                // must implements Seralizable
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(obj);
                bos.close();
                map.put("User", bos.toByteArray());
                objMsg.setObjectProperty("Map", map);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return objMsg;
        } else {
            throw new JMSException("Object:[" + obj + "] is not User");
        }
    }

    public Object fromMessage(Message msg) throws JMSException {
        if (msg instanceof ObjectMessage) {
            HashMap<String, byte[]> map = (HashMap<String, byte[]>) ((ObjectMessage) msg)
                .getObjectProperty("Map");

            try {
                //  must implements Seralizable
                ByteArrayInputStream bis = new ByteArrayInputStream(map.get(
                            "User"));
                ObjectInputStream ois = new ObjectInputStream(bis);

                return ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        } else {
            throw new JMSException("Msg:[" + msg + "] is not Map");
        }
    }
}
