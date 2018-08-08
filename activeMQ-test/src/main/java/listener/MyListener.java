package listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @Author: chuzhaohui
 * @Date: Created in 20:40 2018/8/7
 */
public class MyListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("================" + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
