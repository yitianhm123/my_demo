package com.example.esservice.es.vo;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestVo {

    private String houseName;

    private int houseId;

    private String title;

    private double price;



}
