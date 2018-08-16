package com.kaishengit.controller.controllerExceptionHandeler;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

/**
 * @Author: chuzhaohui
 * @Date: Created in 10:43 2018/7/24
 */
@ControllerAdvice
public class ControlerExceptionHandler {

    @ExceptionHandler(value = IOException.class)
    public String IOExceptionHandler(){
        return "error/500IO";
    }

    @ExceptionHandler(Exception.class)
    public String ExceptionHandler(){
        return "error/500";
    }

    @ExceptionHandler(AuthorizationException.class)
    public String authorizationException(){
        return "error/401";
    }

    @ExceptionHandler(NotFountException.class)
    public String notFountException(){
        return "error/404";
    }

}
