package com.example.sem.service.impl;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.sem.service.ISyncMassegeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class SyncMassegeServiceImpl implements ISyncMassegeService {


    @Async
    public void sayHello(){
        System.out.println("hello this async");
    }


}
