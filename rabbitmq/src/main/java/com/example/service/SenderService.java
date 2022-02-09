package com.example.service;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SenderService {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String msg) {
        String sendMsg = "hello1 " + new Date();
        System.out.println("Sender1 : " + sendMsg);
        this.rabbitTemplate.convertAndSend("hello", sendMsg);
    }

    public void send() {
        String sendMsg = "hello " + new Date();
        System.out.println("Sender : " + sendMsg);
        this.rabbitTemplate.convertAndSend("hello", sendMsg);
    }

}
