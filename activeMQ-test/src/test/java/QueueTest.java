import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;

/**
 * @Author: chuzhaohui
 * @Date: Created in 14:54 2018/8/7
 */
public class QueueTest {

    @Test
    public void testProducer() throws JMSException {
        //1. 创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        //2. 创建连接并开启
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //3. 创建 Session
        //param1:是否开启手动提交事务；param2:接收者签收的模式：AUTO_ACKNOWLEDGE（自动签收）,CLIENT_ACKNOWLEDGE(手动签收)
        Session session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
        //4. 创建消息目的地
        Destination destination = session.createQueue("helloWord-Queue");
        //5. 创建生产者
        MessageProducer producer = session.createProducer(destination);
        //开启持久模式
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        //6. 发送消息
        TextMessage textMessage = session.createTextMessage("Hello,MQ");
        producer.send(textMessage);
        session.commit();
        /*for(int i = 0;i<100;i++){
            TextMessage textMessage = session.createTextMessage("Hello,MQ"+i);
            producer.send(textMessage);
            session.commit();
        }*/
        //7. 释放资源
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testConsumer() throws JMSException {
        //1. 创建连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        //自定义重试
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        //设置重试次数
        redeliveryPolicy.setMaximumRedeliveries(3);
        //设置初次重试延迟时间，单位毫秒
        redeliveryPolicy.setInitialRedeliveryDelay(100);
        //设置每次重试延迟时间，单位毫秒
        redeliveryPolicy.setRedeliveryDelay(100);

        connectionFactory.setRedeliveryPolicy(redeliveryPolicy);

        //2. 创建连接并开启
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //3. 创建 Session
        Session session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
        //4. 创建消息目的地
        Destination destination = session.createQueue("helloWord-Queue");
        //5. 创建消费者
        MessageConsumer consumer = session.createConsumer(destination);
        //6. 获取消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                /*TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("testConsumer1 : "+ textMessage.getText());
                    textMessage.acknowledge();
                } catch (JMSException e) {
                    e.printStackTrace();
                }*/
                try{
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println(textMessage);
                    if("Hello,MQ".equals(textMessage.getText())){
                        throw new JMSException("error");
                    }
                    textMessage.acknowledge();
                }catch (JMSException e){
                    e.printStackTrace();
                    try {
                        session.recover();
                    } catch (JMSException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //7. 释放资源
        consumer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testConsumer2() throws JMSException {
        //1. 创建连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        //2. 创建并启动连接
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //3. 创建 Session
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //4. 创建消息目的地
        Destination destination = session.createQueue("helloWord-Queue");
        //5. 创建消费者
        MessageConsumer consumer = session.createConsumer(destination);
        //6. 获取消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("testConsumer2: " + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //7. 释放资源
        consumer.close();
        session.close();
        connection.close();
    }
}
