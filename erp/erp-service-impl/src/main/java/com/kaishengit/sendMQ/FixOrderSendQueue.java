package com.kaishengit.sendMQ;

import com.google.gson.Gson;
import com.kaishengit.dto.OrderInfoDto;
import com.kaishengit.dto.OrderStateDto;
import com.kaishengit.entity.*;
import com.kaishengit.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 9:53 2018/8/9
 */
@Component
public class FixOrderSendQueue {

    @Autowired
    private ServiceTypeMapper serviceTypeMapper;
    @Autowired
    private OrderPartsMapper orderPartsMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private OrderMapper orderMapper;


    /**
     * front-web向fix-web发送队列
     */
    public void frontTofixQueue(int id) {
        OrderInfoDto orderInfoDto = new OrderInfoDto();

        Order order = orderMapper.selectWihtCarInfo(id);
        orderInfoDto.setOrder(order);

        ServiceType serviceType = serviceTypeMapper.selectByPrimaryKey(order.getServiceTypeId());
        orderInfoDto.setServiceType(serviceType);

        List<Parts> partsList = orderPartsMapper.selectPartsListByOrderId(order.getId());
        orderInfoDto.setPartsList(partsList);

        String data = (new Gson()).toJson(orderInfoDto);

        jmsTemplate.send("front-fix", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(data);
            }
        });

    }

    /**
     * fix-web向front-web发送队列
     */
    public void fixToFront(OrderStateDto orderStateDto) {
        String data = (new Gson()).toJson(orderStateDto);
        jmsTemplate.send("fix-front", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(data);
            }
        });

    }

}
