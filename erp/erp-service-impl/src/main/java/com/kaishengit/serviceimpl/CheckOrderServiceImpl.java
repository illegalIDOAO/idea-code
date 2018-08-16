package com.kaishengit.serviceimpl;

import com.kaishengit.dto.OrderStateDto;
import com.kaishengit.entity.FixOrder;
import com.kaishengit.entity.FixOrderExample;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.mapper.EmployeeMapper;
import com.kaishengit.mapper.FixOrderMapper;
import com.kaishengit.sendMQ.FixOrderSendQueue;
import com.kaishengit.service.CheckOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 8:48 2018/8/10
 */
@Service
public class CheckOrderServiceImpl implements CheckOrderService {

    @Autowired
    private FixOrderMapper fixOrderMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private FixOrderSendQueue fixOrderSendQueue;

    /**
     * 查询所有质检订单列表
     *
     * @return
     */
    @Override
    public List<FixOrder> selectCheckList() {
        return fixOrderMapper.selectCheckOrderInfoList();
    }

    /**
     * \
     * 根据订单id查询订单详情
     *
     * @param orderId
     * @return
     */
    @Override
    public FixOrder selectChekcOrderDetail(int orderId) {
        return fixOrderMapper.selectOrderByOrderId(orderId);
    }

    /**
     * 领取质检任务
     *
     * @param orderId
     * @param employeeId
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void pullTast(int orderId, Integer employeeId) {
        //验证该账户下是否有未完成的质检订单
        FixOrderExample fixOrderExample = new FixOrderExample();
        fixOrderExample.createCriteria().andCheckEmployeeIdEqualTo(employeeId).andStateEqualTo(FixOrder.ORDER_STATE_CHECKING);
        List<FixOrder> fixOrderList = fixOrderMapper.selectByExample(fixOrderExample);
        if(fixOrderList != null && fixOrderList.size() != 0){
            throw new NotAllowException("该账户下有质检订单未完成，请完成后再领取新订单");
        }

        //更新FixOrder
        FixOrder fixOrder = fixOrderMapper.selectOrderByOrderId(orderId);
        if(fixOrder == null){
            throw new NotAllowException("订单不存在");
        }else if(!FixOrder.ORDER_STATE_WAITCHECK.equals(fixOrder.getState())){
            throw new NotAllowException("订单不是待质检状态，无法领取质检任务");
        }

        fixOrder.setState(FixOrder.ORDER_STATE_CHECKING);
        fixOrder.setCheckEmployeeId(employeeId);
        fixOrder.setCheckEmployeeName(employeeMapper.selectByPrimaryKey(employeeId).getEmployeeName());
        fixOrderMapper.updateByPrimaryKeySelective(fixOrder);

        //添加消息队列，通知修改订单状态
        OrderStateDto orderStateDto = new OrderStateDto();
        orderStateDto.setOrderId(orderId);
        orderStateDto.setEmployeeId(employeeId);
        orderStateDto.setState(FixOrder.ORDER_STATE_CHECKING);
        fixOrderSendQueue.fixToFront(orderStateDto);

    }

    /**
     * 完成并提交质检任务
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
        }else if(!FixOrder.ORDER_STATE_CHECKING.equals(fixOrder.getState())){
            throw new NotAllowException("订单未处于质检流程，无法提交至待结账");
        } else if(!fixOrder.getCheckEmployeeId().equals(employeeId)){
            throw new NotAllowException("请维修任务领取人本人进行提交操作");
        }

        //更新FixOrder
        fixOrder.setState(FixOrder.ORDER_STATE_WAITACCOUNT);
        fixOrderMapper.updateByPrimaryKeySelective(fixOrder);

        //添加消息队列，通知修改订单状态
        OrderStateDto orderStateDto = new OrderStateDto();
        orderStateDto.setOrderId(orderId);
        orderStateDto.setState(FixOrder.ORDER_STATE_WAITACCOUNT);
        fixOrderSendQueue.fixToFront(orderStateDto);
    }


}
