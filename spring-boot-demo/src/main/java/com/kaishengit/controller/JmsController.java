package com.kaishengit.controller;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:58 2018/8/23
 */
@RestController
public class JmsController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @GetMapping("/jms/queue")
    public String sendQueue(){
        ActiveMQQueue queue = new ActiveMQQueue("springboot-queue");
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage queueMessage = session.createTextMessage("queue message");
                return queueMessage;
            }
        });
        return "success";
    }
    @GetMapping("/jms/topic")
    public String sendTopic(){
        ActiveMQTopic topic = new ActiveMQTopic("springboot-topic");
        jmsTemplate.send(topic, session -> session.createTextMessage("topic message"));
        return "success";
    }
}
