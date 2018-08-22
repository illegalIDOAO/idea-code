package listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * @Author: chuzhaohui
 * @Date: Created in 20:40 2018/8/7
 */
@Component
public class MyListener5 {

    @JmsListener(destination = "spring-topic")
    public void getDefultMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;

        try {
            System.out.println("================" + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @JmsListener(destination = "spring-queue",containerFactory = "jmsQueueListenerContainerFactory")
    public void getOtherMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;

        try {
            System.out.println("================" + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


}
