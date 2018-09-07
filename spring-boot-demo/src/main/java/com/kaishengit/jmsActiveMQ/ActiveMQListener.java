package com.kaishengit.jmsActiveMQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:56 2018/8/23
 */
@Component
public class ActiveMQListener {

    private Logger logger = LoggerFactory.getLogger(ActiveMQListener.class);

    @JmsListener(destination = "springboot-queue")
    public void queueListener(String message){
        logger.debug("收到queue消息{}",message);
    }

    @JmsListener(destination = "springboot-topic",containerFactory = "jmsTopicListenerContainerFactory",concurrency = "1")
    public void topicListener(String message){
        logger.debug("收到topic消息{}",message);
    }
}
