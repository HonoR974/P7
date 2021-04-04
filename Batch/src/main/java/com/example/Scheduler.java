package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Autowired
    private BatchLauncher batchLauncher;

    @Scheduled(fixedDelay = 8000)
    public void perform() throws Exception {
        batchLauncher.run();
    }
}
