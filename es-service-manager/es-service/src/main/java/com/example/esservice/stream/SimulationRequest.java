package com.example.esservice.stream;

import com.example.esservice.metrics.DemoMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SimulationRequest {

    private Integer count1 = 0;

    @Autowired
    private DemoMetrics demoMetrics;

//    @Async
//    @Scheduled(fixedDelay = 1000)
    public void increment1(){
        count1++;
        demoMetrics.counter.increment();
        demoMetrics.map.put("x",Double.valueOf(count1));
        System.out.println("increment 1 count:"+ count1);

    }
}

