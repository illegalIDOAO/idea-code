package com.kaishengit.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:19 2018/8/24
 */
@Component
public class MyQuartzJob implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("MyQuartz do job ..." + System.currentTimeMillis());
    }
}
