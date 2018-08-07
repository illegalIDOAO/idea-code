package com.kaishengit.serviceimpl;

import com.kaishengit.entity.Car;
import com.kaishengit.entity.Customer;
import com.kaishengit.entity.CustomerExample;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.mapper.CarMapper;
import com.kaishengit.mapper.CustomerMapper;
import com.kaishengit.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 14:57 2018/8/2
 */
@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarMapper carMapper;
    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 根据licenseNo查找车辆
     *
     * @param licenseNo
     * @return car信息
     */
    @Override
    public Car selectLicenceNo(String licenseNo) {
        Car car = carMapper.selectBylicenceNo(licenseNo);
        return car;
    }

    /**
     * 新增车辆信息
     *
     * @param car
     * @param customer
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void newCarInfo(Car car, Customer customer) {
        Car checkCar = carMapper.selectBylicenceNo(car.getLicenceNo());
        if(checkCar != null){
            throw new NotAllowException("请勿重复录入车辆信息");
        }

        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andIdCardEqualTo(customer.getIdCard());
        List<Customer> customerList = customerMapper.selectByExample(customerExample);
        Integer custormerId = null;
        if(customerList != null && customerList.size() > 0){
            custormerId = customerList.get(0).getId();
        }else{
            customerMapper.insertSelective(customer);
            custormerId = customer.getId();
        }

        car.setCustomerId(custormerId);
        carMapper.insertSelective(car);
    }
}
