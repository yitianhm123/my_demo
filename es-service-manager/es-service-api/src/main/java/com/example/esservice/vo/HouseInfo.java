package com.example.esservice.vo;


import lombok.Data;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "houseindex",type = "house")
@ToString
public class HouseInfo {

    /**
     * hosueid
     */
    private long houseId;

    /**
     * title
     */
    private String title;

    /**
     * name
     */
    private String name;

    /**
     *价格
     */
    private double price;


}
