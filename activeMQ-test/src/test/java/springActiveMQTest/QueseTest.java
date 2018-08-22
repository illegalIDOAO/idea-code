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
@ContextConfiguration(locations = "classpath:springQueue2.xml")
public class QueseTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void testCustomer() throws Exception{
        System.out.println("starting");
        System.in.read();
    }

    @Test
    public void testProductor() throws InterruptedException {
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("springQuese" + System.currentTimeMillis());
            }
        });
    }
    @Test
    public void testProductor1() throws InterruptedException {
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
    public void testProductor2() throws InterruptedException {
        Destination destination = new ActiveMQQueue("queue");
        jmsTemplate.send(destination,new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("springQuese" + System.currentTimeMillis());
            }
        });
    }
    @Test
    public void testProductor3() throws InterruptedException {
        jmsTemplate.send("queue2",new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("springQuese" + System.currentTimeMillis());
            }
        });
    }

}
