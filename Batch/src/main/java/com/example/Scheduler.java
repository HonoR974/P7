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

    //tout les min a 18 h le 07/04
    @Scheduled(cron = " 0 */1 18 09 04 ? ")
    //toutes les 8 sec
   // @Scheduled(fixedDelay = 8000)
    public void perform() throws Exception {
        batchLauncher.run();
    }


}
