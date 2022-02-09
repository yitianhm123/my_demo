package com.example.controller;

import com.example.service.ReceiveService;
import com.example.service.SenderService;
import com.example.service.TopicSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rabbit")
public class RabbitTest {

    @Autowired
    private SenderService senderService;

    @Autowired
    private TopicSender topicSender;

    @PostMapping("/hello")
    public void hello() {
        senderService.send();
    }


    /**
     * 单生产者-多消费者
     */
    @PostMapping("/oneToMany")
    public void oneToMany() {
        for(int i=0;i<10;i++){
            senderService.send("hello:"+i);
        }

    }


    /**
     * topic exchange类型rabbitmq测试
     */
    @PostMapping("/topicTest")
    public void topicTest() {
        topicSender.send();
    }
}
