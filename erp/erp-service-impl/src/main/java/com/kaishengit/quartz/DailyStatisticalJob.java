package com.kaishengit.quartz;


import com.kaishengit.service.OrderService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

/**
 * @Author: chuzhaohui
 * @Date: Created in 10:12 2018/8/17
 */
public class DailyStatisticalJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            ApplicationContext context = (ApplicationContext) jobExecutionContext.getScheduler().getContext().get("applicationContext");
            OrderService orderService = context.getBean(OrderService.class);
            orderService.statisticalDailyOrder();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
