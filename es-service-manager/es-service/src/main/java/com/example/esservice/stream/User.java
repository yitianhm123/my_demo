package com.example.esservice.stream;


import lombok.Builder;
import lombok.Data;

import java.util.List;


@Builder
@Data
public class User {

    private String userName;

    private Integer age;

    private String idCard;

    private List<Apple> apples;



    @Data
    class Apple{

        private String prdLocation;
    }
}
