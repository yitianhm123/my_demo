package com.example.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
public class topicMessageReceiver {

    @RabbitHandler
    @RabbitListener(queues = "topic.message")
    public void process(String msg)  {

        System.out.println("topicMessageReceiver  : " +msg);
        throw new RuntimeException("PENDING");
    }
}
