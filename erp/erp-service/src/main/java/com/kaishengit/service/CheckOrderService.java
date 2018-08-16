package com.kaishengit.service;

import com.kaishengit.entity.FixOrder;

import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 8:47 2018/8/10
 */
public interface CheckOrderService {

    /**
     * 查询所有质检订单列表
     * @return
     */
    List<FixOrder> selectCheckList();

    /**\
     * 根据订单id查询订单详情
     * @param orderId
     * @return
     */
    FixOrder selectChekcOrderDetail(int orderId);

    /**
     * 领取质检任务
     * @param orderId
     * @param employeeId
     */
    void pullTast(int orderId, Integer employeeId);

    /**
     * 完成并提交质检任务
     * @param orderId
     * @param employeeId
     */
    void done(int orderId, Integer employeeId);
}
