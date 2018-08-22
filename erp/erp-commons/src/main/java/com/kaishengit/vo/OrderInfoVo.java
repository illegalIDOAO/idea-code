package com.kaishengit.vo;

import com.kaishengit.entity.Order;
import com.kaishengit.entity.Parts;
import com.kaishengit.entity.ServiceType;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 11:17 2018/8/6
 */
public class OrderInfoVo implements Serializable {

    private Order order;

    private ServiceType serviceType;

    private List<Parts> partsList;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public List<Parts> getPartsList() {
        return partsList;
    }

    public void setPartsList(List<Parts> partsList) {
        this.partsList = partsList;
    }
}
