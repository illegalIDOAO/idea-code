package com.kaishengit.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @Author: chuzhaohui
 * @Date: Created in 19:34 2018/7/16
 */
@Component
@Aspect
public class AopAspect {

    @Pointcut("execution(* com.kaishengit..*.*(..))")
    public void  pointcut(){}

    @Before("pointcut()")
    public void beforeInform(){
        System.out.println("前置通知");
    }

    @AfterReturning(value = "pointcut()",returning="result")
    public Object afterInform(Object result){
        System.out.println("后置通知");
        return result;
    }

    @AfterThrowing(value = "pointcut()",throwing = "ex")
    public void exceptionInform(Exception ex){
        System.out.println("异常通知");
        System.out.println(ex.getMessage());
    }

    @After("pointcut()")
    public void finallyInform(){
        System.out.println("最终通知");
    }


    /*   @Around(value = "pointcut()")
       public Object arroud(ProceedingJoinPoint proceedingJoinPoint){
           Object result = null;
           try{
               System.out.println("前置通知");
               result = proceedingJoinPoint.proceed();
               System.out.println("后置通知");
           }catch(Throwable e){
               System.out.println("异常通知：" + e.getMessage());
           }finally{
               System.out.println("最终通知");
           }
           return result;
       }
   */

}
