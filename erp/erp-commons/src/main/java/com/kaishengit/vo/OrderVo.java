package com.kaishengit.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 8:41 2018/8/4
 */
public class OrderVo {

    private Integer carId;
    private Integer serviceTypeId;
    private BigDecimal fee;
    private List<PartsVo> partsList;

    @Override
    public String toString() {
        return "OrderVo{" +
                "carId=" + carId +
                ", serviceTypeId=" + serviceTypeId +
                ", fee=" + fee +
                ", partsList=" + partsList +
                '}';
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

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public List<PartsVo> getPartsList() {
        return partsList;
    }

    public void setPartsList(List<PartsVo> partsList) {
        this.partsList = partsList;
    }
}
