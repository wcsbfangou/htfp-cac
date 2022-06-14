package com.htfp.service.cac.router.biz.job;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author sunjipeng
 * @Date 2022-06-06 14:45
 * @Description 定时任务
 */
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail gcsPingJobQuartz() {
        return JobBuilder.newJob(GcsPingJob.class).withIdentity("gcsPingJob").storeDurably().build();
    }

    @Bean
    public Trigger gcsPingJobQuartzTrigger() {
        return TriggerBuilder.newTrigger().forJob(gcsPingJobQuartz())
                .withIdentity("gcsPingJob")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/5 * * * ? "))
                .build();
    }

    @Bean
    public JobDetail rcsPingJobQuartz() {
        return JobBuilder.newJob(RcsPingJob.class).withIdentity("rcsPingJob").storeDurably().build();
    }

    @Bean
    public Trigger rcsPingJobQuartzTrigger() {
        return TriggerBuilder.newTrigger().forJob(rcsPingJobQuartz())
                .withIdentity("rcsPingJob")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/5 * * * ? "))
                .build();
    }
}
