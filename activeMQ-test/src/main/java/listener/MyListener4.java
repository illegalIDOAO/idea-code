package listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * @Author: chuzhaohui
 * @Date: Created in 20:40 2018/8/7
 */
//@Component
public class MyListener4 implements SessionAwareMessageListener {

    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        TextMessage textMessage = (TextMessage) message;

        try {
            System.out.println("================" + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
