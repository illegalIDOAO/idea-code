package com.kaishengit.service;

import com.kaishengit.dto.OrderInfoDto;
import com.kaishengit.entity.FixOrder;

import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 10:51 2018/8/9
 */
public interface FixOrderService {

    /**
     * 维修订单入库
     * @param orderInfoDto
     */
    void insertFixOrder(OrderInfoDto orderInfoDto);

    /**
     * 查询订单列表信息
     * @return
     */
    List<FixOrder> selectFixOrderList();

    /**
     * 领取维修任务
     * @param orderId
     * @param employeeId
     */
    FixOrder pullTast(int orderId, Integer employeeId);

    /**
     * 查询任务详情
     * @param id
     * @return
     */
    FixOrder selectFixOrderDetail(int id);

    /**
     *  完成订单
     * @param orderId
     * @param employeeId
     */
    void done(int orderId, Integer employeeId);

}
