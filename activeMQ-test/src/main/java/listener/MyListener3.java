package listener;

import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.*;

/**
 * @Author: chuzhaohui
 * @Date: Created in 20:40 2018/8/7
 */
public class MyListener3 implements SessionAwareMessageListener {
    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("================" + textMessage.getText());
            if(textMessage.getText().startsWith("springQuese")){
                throw new JMSException("手抛异常");
            }
            textMessage.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
            session.recover();
        }
    }
}
