package com.example.esservice.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DemoMetrics  implements MeterBinder {

    public Counter counter;
    public Map<String,Double> map;

    DemoMetrics(){
        map=new HashMap<>();
    }

    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        this.counter=Counter.builder("prometheus.demo.counter").tags(new String[]{"name","counter1"})
                .description("demo counter").register(meterRegistry);
    }
}
