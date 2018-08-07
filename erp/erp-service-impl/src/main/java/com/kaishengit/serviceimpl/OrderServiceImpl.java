package com.kaishengit.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.*;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.mapper.*;
import com.kaishengit.service.OrderService;
import com.kaishengit.util.Constant;
import com.kaishengit.vo.OrderVo;
import com.kaishengit.vo.PartsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: chuzhaohui
 * @Date: Created in 14:58 2018/8/2
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ServiceTypeMapper serviceTypeMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderEmployeeMapper orderEmployeeMapper;
    @Autowired
    private OrderPartsMapper orderPartsMapper;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 分页查询订单信息
     *
     * @param pageNo
     * @param map
     * @return
     */
    @Override
    public PageInfo<Order> selectPage(Integer pageNo, Map<String, String> map) {
        PageHelper.startPage(pageNo,Constant.DEFAULT_PAGE_SIZE);

        List<Order> orders = orderMapper.selectByMap(map);

        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        return pageInfo;
    }

    /**
     * 查找服務類型列表
     *
     * @return
     */
    @Override
    public List<ServiceType> selectServiceTypeList() {
        ServiceTypeExample serviceTypeExample = new ServiceTypeExample();
        return serviceTypeMapper.selectByExample(serviceTypeExample);
    }

    /**
     * 新增订单
     *
     * @param orderVo
     * @param employeeId
     * @return 订单id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Integer newOrder(OrderVo orderVo, Integer employeeId) {

        //录入订单信息
        Order order = new Order();
        order.setCarId(orderVo.getCarId());
        order.setServiceTypeId(orderVo.getServiceTypeId());
        order.setOrderMoney(orderVo.getFee());
        order.setState(Order.ORDER_STATE_NEW);

        orderMapper.insertSelective(order);

        //录入订单和员工关联关系
        OrderEmployee orderEmployee = new OrderEmployee();
        orderEmployee.setEmployeeId(employeeId);
        orderEmployee.setOrderId(order.getId());
        orderEmployeeMapper.insert(orderEmployee);

        //录入订单和配件关联关系
        OrderParts orderParts = new OrderParts();
        orderParts.setOrderId(order.getId());
        List<PartsVo> partsVoList = orderVo.getPartsList();
        for(PartsVo partsVo : partsVoList){
            orderParts.setPartsId(partsVo.getPartsId());
            orderParts.setNum(partsVo.getNum());
            orderPartsMapper.insert(orderParts);
        }
        return order.getId();
    }

    /**
     * 查找订单信息
     *
     * @param id
     * @return
     */
    @Override
    public Order selectOrderWithCarAndCustomerById(int id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        if(order == null){
            throw new NotAllowException("无此订单");
        }
        Car car = carMapper.selectByPrimaryKey(order.getCarId());
        Customer customer = customerMapper.selectByPrimaryKey(car.getCustomerId());
        order.setCar(car);
        order.setCustomer(customer);

        return order;
    }

    /**
     * 查找服务类型信息
     *
     * @param id
     * @return
     */
    @Override
    public ServiceType selectServiceById(int id) {
        return serviceTypeMapper.selectByPrimaryKey(id);
    }

    /**
     * 查找订单中配件列表
     *
     * @param id
     * @return
     */
    @Override
    public List<Parts> selectPartsListByOrderId(int id) {
        return orderPartsMapper.selectPartsListByOrderId(id);
    }

    /**
     * 删除订单
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delOrder(int id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        if(order == null){
            throw new NotAllowException("无此订单，请查证");
        }
        if(!order.getState().equals(Order.ORDER_STATE_NEW)){
            throw new NotAllowException("该订单正在处理，不能删除");
        }

        //删除订单和员工关联关系
        OrderEmployeeExample orderEmployeeExample = new OrderEmployeeExample();
        orderEmployeeExample.createCriteria().andOrderIdEqualTo(id);
        orderEmployeeMapper.deleteByExample(orderEmployeeExample);

        //删除订单和配件关联关系
        OrderPartsExample orderPartsExample = new OrderPartsExample();
        orderPartsExample.createCriteria().andOrderIdEqualTo(id);
        orderPartsMapper.deleteByExample(orderPartsExample);

        //删除订单
        orderMapper.deleteByPrimaryKey(id);
    }

    /**
     * 订单下发
     *
     * @param id
     */
    @Override
    public void transOrder(int id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        if(order == null){
            throw new NotAllowException("无此订单，请查证");
        }
        if(!order.getState().equals(Order.ORDER_STATE_NEW)){
            throw new NotAllowException("订单已下发，请勿重复操作");
        }
        order.setState(Order.ORDER_STATE_WAITPULL);
        orderMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * 修改订单
     *
     * @param orderId
     * @param orderVo
     * @param employeeId
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void editOrder(int orderId, OrderVo orderVo, Integer employeeId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if(order == null){
            throw new NotAllowException("无此订单，请查证");
        }
        if(!order.getState().equals(Order.ORDER_STATE_NEW)){
            throw new NotAllowException("订单已下发，不能修改");
        }

        //更新订单信息
        order.setCarId(orderVo.getCarId());
        order.setServiceTypeId(orderVo.getServiceTypeId());
        order.setOrderMoney(orderVo.getFee());
        order.setState(Order.ORDER_STATE_NEW);

        orderMapper.updateByPrimaryKeySelective(order);

        //更新订单和员工关联关系
        OrderEmployeeExample orderEmployeeExample = new OrderEmployeeExample();
        orderEmployeeExample.createCriteria().andOrderIdEqualTo(orderId);
        orderEmployeeMapper.deleteByExample(orderEmployeeExample);

        OrderEmployee orderEmployee = new OrderEmployee();
        orderEmployee.setEmployeeId(employeeId);
        orderEmployee.setOrderId(order.getId());
        orderEmployeeMapper.insert(orderEmployee);

        //更新订单和配件关联关系
        OrderPartsExample orderPartsEmployee = new OrderPartsExample();
        orderPartsEmployee.createCriteria().andOrderIdEqualTo(orderId);
        orderPartsMapper.deleteByExample(orderPartsEmployee);

        OrderParts orderParts = new OrderParts();
        orderParts.setOrderId(order.getId());
        List<PartsVo> partsVoList = orderVo.getPartsList();
        for(PartsVo partsVo : partsVoList){
            orderParts.setPartsId(partsVo.getPartsId());
            orderParts.setNum(partsVo.getNum());
            orderPartsMapper.insert(orderParts);
        }

    }
}
