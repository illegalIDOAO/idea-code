package com.kaishengit.controller;

import com.kaishengit.job.MyQuartzJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:21 2018/8/24
 */
@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @GetMapping("/add/quartz")
    public String quetzAddJob(){

        JobDetail jobDetail = JobBuilder.newJob(MyQuartzJob.class).withIdentity("one","quartzJob").build();

        ScheduleBuilder builder = CronScheduleBuilder.cronSchedule("0/2 * * * * ?");
        Trigger trigger = TriggerBuilder.newTrigger().withSchedule(builder).build();

        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        try{
            scheduler.scheduleJob(jobDetail,trigger);
            scheduler.start();
        }catch (SchedulerException e) {
            e.printStackTrace();
        }

        return "success";
    }

    @GetMapping("/del/quartz")
    public String quetzDelJob(){

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.deleteJob(new JobKey("one","quartzJob"));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }


        return "success";
    }

}
