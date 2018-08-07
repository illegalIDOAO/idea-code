package com.kaishengit.service;

import com.kaishengit.entity.Car;
import com.kaishengit.entity.Customer;

/**
 * @Author: chuzhaohui
 * @Date: Created in 14:57 2018/8/2
 */
public interface CarService {

    /**
     * 根据licenseNo查找车辆
     * @param licenseNo
     * @return car信息
     */
    Car selectLicenceNo(String licenseNo);

    /**
     * 新增车辆信息
     * @param car
     * @param customer
     */
    void newCarInfo(Car car, Customer customer);
}
