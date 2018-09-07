package com.kaishengit.job;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:20 2018/8/24
 */
@Component
public class MyScheduler {

    @Async("taskExecutor")
    @Scheduled(cron = "0/5 * * * * ?")
    public void doJob(){
        System.out.println("MyScheduler do job ..." + System.currentTimeMillis());
    }

}
