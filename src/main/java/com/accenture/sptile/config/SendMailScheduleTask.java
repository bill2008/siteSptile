package com.accenture.sptile.config;

import com.accenture.sptile.service.SptileService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

@Configuration
@EnableScheduling
public class SendMailScheduleTask {
    @Resource()
    private SptileService nanjingSptileServiceImpl;

    @Scheduled(cron = "0 0/10 * * * ?")
    private void configureTasks() throws Exception {
        nanjingSptileServiceImpl.process();
    }

}
