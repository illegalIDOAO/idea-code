package com.kaishengit.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Order;
import com.kaishengit.entity.Parts;
import com.kaishengit.entity.ServiceType;
import com.kaishengit.vo.OrderVo;

import java.util.List;
import java.util.Map;

/**
 * @Author: chuzhaohui
 * @Date: Created in 14:58 2018/8/2
 */
public interface OrderService {

    /**
     * 分页查询订单信息
     * @param pageNo
     * @param map
     * @return
     */
    PageInfo<Order> selectPage(Integer pageNo, Map<String,String> map);

    /**
     * 查找服務類型列表
     *
     * @return
     */
    List<ServiceType> selectServiceTypeList();

    /**
     * 新增订单
     * @param orderVo
     * @param employeeId
     * @return 订单id
     */
    Integer newOrder(OrderVo orderVo,Integer employeeId);

    /**
     * 查找订单信息
     * @param id
     * @return
     */
    Order selectOrderWithCarAndCustomerById(int id);

    /**
     * 查找服务类型信息
     * @param id
     * @return
     */
    ServiceType selectServiceById(int id);

    /**
     * 查找订单中配件列表
     * @param id
     * @return
     */
    List<Parts> selectPartsListByOrderId(int id);

    /**
     * 删除订单
     * @param id
     */
    void delOrder(int id);

    /**
     * 订单下发
     * @param id
     */
    void transOrder(int id);

    /**
     *
     * @param orderId
     * @param orderVo
     * @param employeeId
     */
    void editOrder(int orderId, OrderVo orderVo, Integer employeeId);
}
