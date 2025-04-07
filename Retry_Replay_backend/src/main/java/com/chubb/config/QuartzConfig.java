package com.chubb.config;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail retryJobDetail() {
        return JobBuilder.newJob(RetryJob.class)
                .withIdentity("retryJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger retryJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(30) // Run every 30 seconds
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(retryJobDetail())
                .withIdentity("retryTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}