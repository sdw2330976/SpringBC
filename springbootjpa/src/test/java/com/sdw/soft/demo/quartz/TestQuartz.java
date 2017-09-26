package com.sdw.soft.demo.quartz;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by shangyd on 2017/5/13.
 */
public class TestQuartz {

    @Test
    public void test01() throws Exception{
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobDetail jobDetail = JobBuilder.newJob().ofType(HelloJob.class).build();
        Trigger trigger = TriggerBuilder.newTrigger().startNow().build();
        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();
        Thread.sleep(100000);
    }
}
