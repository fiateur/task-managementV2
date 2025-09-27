package com.adacorp.task_managementV2.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyTask {

    @Scheduled(fixedRate = 5000)
    public void execute() {
        System.out.println("Tâche exécutée toutes les 5 secondes...");
    }
}
