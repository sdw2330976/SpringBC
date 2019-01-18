package com.sdw.soft.schedule;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Log4j2
@Component
public class JobScheduler {

    @Async
    @Scheduled(cron = "0/1 * * * * *")
    public void schedule() throws InterruptedException {
        Thread.sleep(3000);
        log.info("schedule 每1秒执行一次:{}", LocalDateTime.now());
    }

}
