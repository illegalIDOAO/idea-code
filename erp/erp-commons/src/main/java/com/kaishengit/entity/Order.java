package com.kaishengit.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 
 */
public class Order implements Serializable {

    public static final String ORDER_STATE_NEW= "0";
    public static final String ORDER_STATE_WAITFIX = "1";
    public static final String ORDER_STATE_FIXING = "2";
    public static final String ORDER_STATE_WAITCHECK = "3";
    public static final String ORDER_STATE_CHECKING = "4";
    public static final String ORDER_STATE_WAITACCOUNT = "5";
    public static final String ORDER_STATE_DONE = "6";

    public String getStateMean(){
        if(getState().equals(ORDER_STATE_NEW)){
            return "新订单";
        }else if(getState().equals(ORDER_STATE_WAITFIX)){
            return "已下发，等待领取";
        }else if(getState().equals(ORDER_STATE_FIXING)){
            return "正在处理";
        }else if(getState().equals(ORDER_STATE_WAITCHECK)){
            return "等待质检";
        }else if(getState().equals(ORDER_STATE_CHECKING)){
            return "正在质检";
        }else if(getState().equals(ORDER_STATE_WAITACCOUNT)){
            return "待结账";
        }else if(getState().equals(ORDER_STATE_DONE)){
            return "已完成";
        }else{
            return "UNKNOW";
        }
    }

    private Integer id;

    /**
     * 订单总价
     */
    private BigDecimal orderMoney;

    /**
     * 订单状态 
     */
    private String state;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 车辆id
     */
    private Integer carId;

    /**
     * 工时费
     */
    private Integer serviceTypeId;

    private Car car;
    private Customer customer;
    private List<Parts> partsList;

    public List<Parts> getPartsList() {
        return partsList;
    }

    public void setPartsList(List<Parts> partsList) {
        this.partsList = partsList;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Order other = (Order) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderMoney() == null ? other.getOrderMoney() == null : this.getOrderMoney().equals(other.getOrderMoney()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCarId() == null ? other.getCarId() == null : this.getCarId().equals(other.getCarId()))
            && (this.getServiceTypeId() == null ? other.getServiceTypeId() == null : this.getServiceTypeId().equals(other.getServiceTypeId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderMoney() == null) ? 0 : getOrderMoney().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCarId() == null) ? 0 : getCarId().hashCode());
        result = prime * result + ((getServiceTypeId() == null) ? 0 : getServiceTypeId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderMoney=").append(orderMoney);
        sb.append(", state=").append(state);
        sb.append(", createTime=").append(createTime);
        sb.append(", carId=").append(carId);
        sb.append(", serviceTypeId=").append(serviceTypeId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}