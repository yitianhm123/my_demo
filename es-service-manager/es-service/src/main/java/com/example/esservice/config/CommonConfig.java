package com.example.esservice.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;


@Data
@Component
@ConfigurationProperties(prefix = "es.common")
public class CommonConfig {
    private String  dirName;
    private Integer age;
    private Boolean boss;


}
