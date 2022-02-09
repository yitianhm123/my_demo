package com.example.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
public class topicMessagesReceiver2 {


    @RabbitHandler
//    @RabbitListener(queues = "topic.messages")
    public void process(String msg) {
        System.out.println("topicMessageReceiver2  : " +msg);
    }
}
