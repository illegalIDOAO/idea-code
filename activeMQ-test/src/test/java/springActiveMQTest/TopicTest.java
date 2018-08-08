package springActiveMQTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:55 2018/8/7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springTopic.xml")
public class TopicTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void testSendMessage() throws InterruptedException {
        /*Destination destination = new ActiveMQQueue("queue");
        jmsTemplate.send(destination,new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("springQuese" + System.currentTimeMillis());
            }
        });*/
        /*jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("springQuese" + System.currentTimeMillis());
            }
        });*/
        while(true){
            jmsTemplate.send(new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage("springQuese" + System.currentTimeMillis());
                }
            });
            Thread.sleep(1000);
        }
    }

    @Test
    public void readMessage() throws Exception{
        System.out.println("starting");
        System.in.read();
    }

}
