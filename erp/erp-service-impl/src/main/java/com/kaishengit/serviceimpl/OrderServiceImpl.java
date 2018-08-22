package com.kaishengit.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.dto.OrderStateDto;
import com.kaishengit.entity.*;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.mapper.*;
import com.kaishengit.sendMQ.FixOrderSendQueue;
import com.kaishengit.service.OrderService;
import com.kaishengit.util.Constant;
import com.kaishengit.vo.OrderVo;
import com.kaishengit.vo.PartsVo;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: chuzhaohui
 * @Date: Created in 14:58 2018/8/2
 */
@Service
public class OrderServiceImpl implements OrderService {


    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);


    @Autowired
    private ServiceTypeMapper serviceTypeMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderEmployeeMapper orderEmployeeMapper;
    @Autowired
    private OrderPartsMapper orderPartsMapper;
    @Autowired
    private FixOrderSendQueue fixOrderSendQueue;
    @Autowired
    private PartsMapper partsMapper;
    @Autowired
    private DailyStatisticalMapper dailyStatisticalMapper;



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

        System.out.println("==========================");
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
            Parts parts = partsMapper.selectByPrimaryKey(partsVo.getPartsId());
            if(partsVo.getNum() > parts.getInventory()){
                throw new NotAllowException("库存不足");
            }
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
        Order order = orderMapper.selectWihtCarInfo(id);
        if(order == null){
            throw new NotAllowException("无此订单");
        }

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
    @Transactional(rollbackFor = RuntimeException.class)
    public void transOrder(int id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        if(order == null){
            throw new NotAllowException("无此订单，请查证");
        }
        if(!order.getState().equals(Order.ORDER_STATE_NEW)){
            throw new NotAllowException("订单已下发，请勿重复操作");
        }
        order.setState(Order.ORDER_STATE_WAITFIX);
        orderMapper.updateByPrimaryKeySelective(order);

        //加入队列
        fixOrderSendQueue.frontTofixQueue(id);
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

    /**
     * 改变订单状态为正在维修，并添加订单与员工关联关系
     *
     * @param orderStateDto
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void changeStateToFixing(OrderStateDto orderStateDto) {
        //改变订单状态
        Order order = orderMapper.selectByPrimaryKey(orderStateDto.getOrderId());
        if(order == null){
            logger.info("orderId={} 的订单不存在,无法进行领取维修操作",orderStateDto.getOrderId());
        }else if(!Order.ORDER_STATE_WAITFIX.equals(order.getState())){
            logger.info("orderId={} 的订单是待维修,无法领取维修任务",orderStateDto.getOrderId());
        }else{
            order.setState(orderStateDto.getState());
            orderMapper.updateByPrimaryKeySelective(order);

            //新增员工订单关联关系表
            OrderEmployee orderEmployee = new OrderEmployee();
            orderEmployee.setOrderId(orderStateDto.getOrderId());
            orderEmployee.setEmployeeId(orderStateDto.getEmployeeId());
            orderEmployeeMapper.insertSelective(orderEmployee);
        }
    }

    /**
     * 更改订单状态为待质检
     * @param orderStateDto
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void changeStateToWaitCheck(OrderStateDto orderStateDto) {
        //改变订单状态
        Order order = orderMapper.selectByPrimaryKey(orderStateDto.getOrderId());
        if(order == null){
            logger.info("orderId={} 的订单不存在,无法进行领取维修操作",orderStateDto.getOrderId());
        }else if(!Order.ORDER_STATE_FIXING.equals(order.getState())){
            logger.info("orderId={} 的订单状态不是正在维修,无法提交检测",orderStateDto.getOrderId());
        }else{
            order.setState(orderStateDto.getState());
            orderMapper.updateByPrimaryKeySelective(order);
        }
    }

    /**
     * 更改订单状态为正在质检，并添加订单与员工关联关系
     *
     * @param orderStateDto
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void changeStateToChecking(OrderStateDto orderStateDto) {
        //改变订单状态
        Order order = orderMapper.selectByPrimaryKey(orderStateDto.getOrderId());
        if(order == null){
            logger.info("orderId={} 的订单不存在,无法进行领取维修操作",orderStateDto.getOrderId());
        }else if(!Order.ORDER_STATE_WAITCHECK.equals(order.getState())){
            logger.info("orderId={} 的订单是待质检,无法领取维修任务",orderStateDto.getOrderId());
        }else{
            order.setState(orderStateDto.getState());
            orderMapper.updateByPrimaryKeySelective(order);

            //新增质检员工订单关联关系表
            OrderEmployee orderEmployee = new OrderEmployee();
            orderEmployee.setOrderId(orderStateDto.getOrderId());
            orderEmployee.setEmployeeId(orderStateDto.getEmployeeId());
            orderEmployeeMapper.insertSelective(orderEmployee);
        }
    }

    /**
     * 更改订单状态为待结账
     *
     * @param orderStateDto
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void changeStateToWaitAccount(OrderStateDto orderStateDto) {
        //改变订单状态
        Order order = orderMapper.selectByPrimaryKey(orderStateDto.getOrderId());
        if(order == null){
            logger.info("orderId={} 的订单不存在,无法进行领取维修操作",orderStateDto.getOrderId());
        }else if(!Order.ORDER_STATE_CHECKING.equals(order.getState())){
            logger.info("orderId={} 的订单未处于检测流程,无法提交结账",orderStateDto.getOrderId());
        }else{
            order.setState(orderStateDto.getState());
            orderMapper.updateByPrimaryKeySelective(order);
        }
    }

    /**
     * 统计每日订单
     */
    @Override
    public void statisticalDailyOrder() {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime dt = new DateTime();
        dt = dt.minusDays(1);
        String yesterday = fmt.print(dt);

        List<Order> orderList = orderMapper.selectDailyOrder(Order.ORDER_STATE_DONE,yesterday);

        DailyStatistical dailyStatistical = new DailyStatistical();
        dailyStatistical.setDataTime(yesterday);
        if(orderList != null && orderList.size() > 0){
            dailyStatistical.setSumNum(orderList.size());

            BigDecimal totalMomey = BigDecimal.ZERO;
            for(Order order : orderList){
                totalMomey = totalMomey.add(order.getOrderMoney());
            }
            dailyStatistical.setSumMomey(totalMomey);

        }
        dailyStatisticalMapper.insertSelective(dailyStatistical);

    }

}
