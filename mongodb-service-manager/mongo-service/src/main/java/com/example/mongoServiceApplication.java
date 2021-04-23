package com.example;


import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.stream.Collectors;

/**
 * @Classname FeignConfig
 * @Created by huangman
 * @Description TODO
 * @Date 4/22/21 8:28 PM
 */


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {
        "com.example.esservice.*"
})
public class mongoServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(mongoServiceApplication.class, args);
    }




}
