package com.sdw.soft.demo.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by shangyd on 2017/5/13.
 */
public class HelloJob implements Job{


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("hello job...");
    }
}
