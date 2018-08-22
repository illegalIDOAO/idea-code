package springActiveMQTest;

import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:55 2018/8/7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springTopic2.xml")
public class TopicTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void readMessage() throws Exception{
        System.out.println("starting");
        System.in.read();
    }

    @Test
    public void testSendMessage() throws InterruptedException {
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("springQuese" + System.currentTimeMillis());
            }
        });
    }
    @Test
    public void testSendMessage2() throws InterruptedException {
        Destination destination = new ActiveMQQueue("queue");
        jmsTemplate.send(destination,new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("springQuese" + System.currentTimeMillis());
            }
        });
    }
    @Test
    public void testSendMessage3() throws InterruptedException {
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


}
