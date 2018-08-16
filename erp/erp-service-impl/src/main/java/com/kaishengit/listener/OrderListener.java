package com.kaishengit.listener;

import com.google.gson.Gson;
import com.kaishengit.dto.OrderInfoDto;
import com.kaishengit.dto.OrderStateDto;
import com.kaishengit.entity.Order;
import com.kaishengit.service.FixOrderService;
import com.kaishengit.service.OrderService;
import com.kaishengit.serviceimpl.FixOrderServiceImpl;

import com.kaishengit.serviceimpl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * @Author: chuzhaohui
 * @Date: Created in 22:23 2018/8/7
 */
@Component
public class OrderListener{

    @Autowired
    private FixOrderService fixOrderService;
    @Autowired
    private OrderService orderService;

    @JmsListener(destination = "front-fix")
    public void getMessageFrontToFix(Message message,Session session) {
        try {
            OrderInfoDto orderInfoDto = (new Gson()).fromJson(((TextMessage) message).getText(),OrderInfoDto.class);
            fixOrderService.insertFixOrder(orderInfoDto);

            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
            try {
                session.recover();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        }
    }

    @JmsListener(destination = "fix-front")
    public void getMessageFixToFront(Message message,Session session) {
        try {
            OrderStateDto orderStateDto  = (new Gson()).fromJson(((TextMessage) message).getText(),OrderStateDto.class);
            if(Order.ORDER_STATE_FIXING.equals(orderStateDto.getState())){
                orderService.changeStateToFixing(orderStateDto);
            }else if(Order.ORDER_STATE_WAITCHECK.equals(orderStateDto.getState())){
                orderService.changeStateToWaitCheck(orderStateDto);
            }else if(Order.ORDER_STATE_CHECKING.equals(orderStateDto.getState())){
                orderService.changeStateToChecking(orderStateDto);
            }else if(Order.ORDER_STATE_WAITACCOUNT.equals(orderStateDto.getState())){
                orderService.changeStateToWaitAccount(orderStateDto);
            }
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
            try {
                session.recover();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        }
    }


}
