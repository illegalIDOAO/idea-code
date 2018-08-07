package com.kaishengit.controller;

import com.kaishengit.dto.ResponseBean;
import com.kaishengit.entity.Car;
import com.kaishengit.entity.Customer;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: chuzhaohui
 * @Date: Created in 14:33 2018/8/2
 */
@Controller
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/check")
    @ResponseBody
    public ResponseBean check(String licenceNo){
        Car car = carService.selectLicenceNo(licenceNo);
        if(car != null){
            return ResponseBean.success(car);
        }else{
            return ResponseBean.error("未录入本车辆信息");
        }
    }

    @PostMapping("/new")
    @ResponseBody
    public ResponseBean newCarInfo(Car car, Customer customer){
        try{
            carService.newCarInfo(car,customer);
        }catch(NotAllowException e){
            return ResponseBean.error(e.getMessage());
        }
        car.setCustomer(customer);
        return ResponseBean.success(car);
    }





}
