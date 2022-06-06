package com.example.esservice.config;


import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Data
@Component
public class CommonValueConfig {

    @Value("${es.common.inputFilePath:/home/yitianhm/temp/split_order}")
    private String  inputFilePath;
    private Integer age;
    private Boolean boss;


}
