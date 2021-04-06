package com.example;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Scheduler {

    @Autowired
    private BatchLauncher batchLauncher;

    //
    @Scheduled(cron = "*/10 32 13 06 04 ? ")
    public void perform() throws Exception {
        batchLauncher.run();
    }


}
