package com.kaishengit.quartz;

import com.kaishengit.service.FixOrderService;
import com.kaishengit.service.OrderService;
import org.quartz.*;
import org.springframework.context.ApplicationContext;

/**
 * @Author: chuzhaohui
 * @Date: Created in 19:38 2018/8/16
 */
public class FixTimeOutJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            ApplicationContext context = (ApplicationContext) jobExecutionContext.getScheduler().getContext().get("applicationContext");
            FixOrderService fixOrderService = context.getBean(FixOrderService.class);

            JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
            Integer orderId = (Integer) jobDataMap.get("orderId");
            Integer employeeId = (Integer) jobDataMap.get("employeeId");
            fixOrderService.addFixTimeout(orderId,employeeId);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
