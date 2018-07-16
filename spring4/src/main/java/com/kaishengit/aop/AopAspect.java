package com.kaishengit.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @Author: chuzhaohui
 * @Date: Created in 11:19 2018/7/16
 */
public class AopAspect {

    public void beforeInform(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        Object target = joinPoint.getTarget();

/*        System.out.println(methodName);
        System.out.println(args.length);
        System.out.println(target.toString());
        System.out.println(target.getClass());
        System.out.println(target.getClass().getName());*/
        System.out.println("前置通知");
    }
    public int afterInform(Integer result){
        System.out.println("后置通知");
        return result + 100 ;
    }
    public void exceptionInform(Exception e){
        System.out.println("异常通知 : " + e.getMessage());
    }
    public void finallyInform(){
        System.out.println("最终通知");
    }

    public Object around(ProceedingJoinPoint proceedingJoinPoint){

        Object result = null;
        try{
            System.out.println("around，前置通知");
            result = proceedingJoinPoint.proceed();
            System.out.println("around，后置通知");
        }catch (Throwable throwable){
            System.out.println("around，异常通知 ：" + throwable.getMessage());
        }finally {
            System.out.println("around，最终通知");
        }



        return result;
    }

}
