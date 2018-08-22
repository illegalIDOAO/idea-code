package com.kaishengit.serviceimpl;

import com.kaishengit.dto.OrderInfoDto;
import com.kaishengit.dto.OrderStateDto;
import com.kaishengit.entity.*;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.mapper.*;
import com.kaishengit.quartz.FixTimeOutJob;
import com.kaishengit.sendMQ.FixOrderSendQueue;
import com.kaishengit.service.FixOrderService;
import com.kaishengit.util.Constant;
import org.joda.time.DateTime;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 9:48 2018/8/9
 */
@Service
public class FixOrderServiceImpl implements FixOrderService {

    @Autowired
    private FixOrderMapper fixOrderMapper;
    @Autowired
    private FixOrderPartsMapper fixOrderPartsMapper;
    @Autowired
    private PartsMapper partsMapper;
    @Autowired
    private PartsStreamMapper partsStreamMapper;
    @Autowired
    private OrderPartsMapper orderPartsMapper;
    @Autowired
    private FixOrderSendQueue fixOrderSendQueue;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private FixTimeoutMapper fixTimeoutMapper;


    /**
     * 维修订单入库
     * @param orderInfoDto
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insertFixOrder(OrderInfoDto orderInfoDto) {

        //1.订单详情入库
        //封装FixOrder
        FixOrder fixOrder = new FixOrder();
        fixOrder.setOrderId(orderInfoDto.getOrder().getId());
        fixOrder.setOrderMoney(orderInfoDto.getOrder().getOrderMoney());
        fixOrder.setOrderTime(orderInfoDto.getOrder().getCreateTime());
        fixOrder.setState(orderInfoDto.getOrder().getState());
        fixOrder.setOrderType(orderInfoDto.getServiceType().getServiceName());
        fixOrder.setOrderServiceHour(orderInfoDto.getServiceType().getServiceHour());
        // 计算工时费
        fixOrder.setOrderServiceHourFee(new BigDecimal(Integer.parseInt(orderInfoDto.getServiceType().getServiceHour()) * Constant.DEFAULT_HOUR_FEE));
        // 计算配件费用
        fixOrder.setOrderPartsFee(fixOrder.getOrderMoney().subtract(fixOrder.getOrderServiceHourFee()));
        // 封装车辆信息
        fixOrder.setCarColor(orderInfoDto.getOrder().getCar().getColor());
        fixOrder.setCarLicence(orderInfoDto.getOrder().getCar().getLicenceNo());
        fixOrder.setCarType(orderInfoDto.getOrder().getCar().getCarType());
        // 封装客户信息
        fixOrder.setCustomerName(orderInfoDto.getOrder().getCustomer().getUserName());
        fixOrder.setCustomerTel(orderInfoDto.getOrder().getCustomer().getTel());

        fixOrderMapper.insert(fixOrder);

        //2.配件入库
        for(Parts parts : orderInfoDto.getPartsList()){
            FixOrderParts fixOrderParts = new FixOrderParts();
            fixOrderParts.setOrderId(orderInfoDto.getOrder().getId());
            fixOrderParts.setPartsId(parts.getId());
            fixOrderParts.setPartsName(parts.getPartsName());
            fixOrderParts.setPartsNo(parts.getPartsNo());
            fixOrderParts.setPartsNum(parts.getNum());

            fixOrderPartsMapper.insertSelective(fixOrderParts);
        }
    }

    /**
     * 查询订单列表信息
     *
     * @return
     */
    @Override
    public List<FixOrder> selectFixOrderList() {
        return fixOrderMapper.selectFixOrderInfoList();
    }

    /**
     * 领取维修任务
     *
     * @param orderId
     * @param employeeId
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public FixOrder pullTast(int orderId,Integer employeeId){
        //验证该账户下是否有未完成的维修订单
        FixOrderExample fixOrderExample = new FixOrderExample();
        fixOrderExample.createCriteria().andFixEmployeeIdEqualTo(employeeId).andStateEqualTo(FixOrder.ORDER_STATE_FIXING);
        List<FixOrder> fixOrderList = fixOrderMapper.selectByExample(fixOrderExample);
        if(fixOrderList != null && fixOrderList.size() != 0){
            throw new ServiceException("该账户下有维修订单未完成，请完成后再领取新订单");
        }

        //更新FixOrder
        FixOrder fixOrder = fixOrderMapper.selectOrderByOrderId(orderId);
        if(fixOrder == null){
            throw new ServiceException("订单不存在");
        }else if(!FixOrder.ORDER_STATE_WAITFIX.equals(fixOrder.getState())){
            throw new ServiceException("订单不是待维修状态，无法领取维修任务");
        }

        fixOrder.setState(FixOrder.ORDER_STATE_FIXING);
        fixOrder.setFixEmployeeId(employeeId);
        fixOrder.setFixEmployeeName(employeeMapper.selectByPrimaryKey(employeeId).getEmployeeName());
        fixOrderMapper.updateByPrimaryKeySelective(fixOrder);

        //添加消息队列，通知修改订单状态
        OrderStateDto orderStateDto = new OrderStateDto();
        orderStateDto.setOrderId(orderId);
        orderStateDto.setEmployeeId(employeeId);
        orderStateDto.setState(FixOrder.ORDER_STATE_FIXING);
        fixOrderSendQueue.fixToFront(orderStateDto);

        //TODO 转移至库存管理系统
        List<Parts> partsList = orderPartsMapper.selectPartsListByOrderId(orderId);
        for(Parts parts : partsList){
            //记录配件流水
            PartsStream partsStream  = new PartsStream();
            partsStream.setPartsId(parts.getId());
            partsStream.setPreInventory(parts.getInventory());
            partsStream.setNum(parts.getNum());
            partsStream.setAfterInventory(parts.getInventory() - parts.getNum());
            partsStream.setEmployeeId(employeeId);
            partsStream.setType(PartsStream.PARTSSTREAM_TYPE_OUT);
            partsStream.setOrderId(orderId);
            partsStream.setCreateTime(new Date());
            partsStreamMapper.insertSelective(partsStream);

            //更新配件库存
            parts.setInventory(parts.getInventory() - parts.getNum());
            partsMapper.updateByPrimaryKeySelective(parts);
        }

        addTask(orderId,employeeId,Integer.parseInt(fixOrder.getOrderServiceHour()));

        return fixOrder;
    }

    /**
     * 查询任务详情
     * @param id
     * @return
     */
    @Override
    public FixOrder selectFixOrderDetail(int id) {
        return fixOrderMapper.selectOrderByOrderId(id);
    }

    /**
     * 完成订单
     *
     * @param orderId
     * @param employeeId
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void done(int orderId, Integer employeeId) {
        FixOrder fixOrder = fixOrderMapper.selectByPrimaryKey(orderId);
        if(fixOrder == null){
            throw new NotAllowException("订单不存在");
        }else if(!FixOrder.ORDER_STATE_FIXING.equals(fixOrder.getState())){
            throw new NotAllowException("订单不是正在维修状态，无法提交至待检测");
        } else if(!fixOrder.getFixEmployeeId().equals(employeeId)){
            throw new NotAllowException("请维修任务领取人本人进行提交操作");
        }

        //更新FixOrder
        fixOrder.setState(FixOrder.ORDER_STATE_WAITCHECK);
        fixOrderMapper.updateByPrimaryKeySelective(fixOrder);

        //添加消息队列，通知修改订单状态
        OrderStateDto orderStateDto = new OrderStateDto();
        orderStateDto.setOrderId(orderId);
        orderStateDto.setState(FixOrder.ORDER_STATE_WAITCHECK);
        fixOrderSendQueue.fixToFront(orderStateDto);

        delteTask(orderId,employeeId);
    }

    /**
     * 新增维修超时记录
     *
     * @param orderId
     * @param employeeId
     */
    @Override
    public void addFixTimeout(Integer orderId, Integer employeeId) {
        FixTimeout fixTimeout = new FixTimeout();
        fixTimeout.setEmployeeId(employeeId);
        fixTimeout.setOrderId(orderId);
        fixTimeoutMapper.insertSelective(fixTimeout);
    }


    public void addTask(Integer orderId, Integer employeeId, Integer serviceHour){

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("orderId",orderId);
        jobDataMap.put("employeeId",employeeId);

        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        JobDetail jobDetail = JobBuilder.newJob(FixTimeOutJob.class)
                .withIdentity(orderId + "-" + employeeId,"fix")
                .setJobData(jobDataMap).build();

        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusHours(serviceHour);
        //dateTime = dateTime.plusMinutes(serviceHour);//
        String cronExpression = dateTime.getSecondOfMinute() + " "
                + dateTime.getMinuteOfHour() + " "
                + dateTime.getHourOfDay() + " "
                + dateTime.getDayOfMonth() + " "
                + dateTime.getMonthOfYear() + " ? "
                + dateTime.getYear();

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        Trigger trigger = TriggerBuilder.newTrigger().withSchedule(cronScheduleBuilder).build();

        try {
            scheduler.scheduleJob(jobDetail,trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void delteTask(int orderId,int employeeId){
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.deleteJob(new JobKey(orderId + "-" + employeeId,"fix"));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }


}
