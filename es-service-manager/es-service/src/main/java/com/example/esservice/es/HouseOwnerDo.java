package com.example.esservice.es;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "houseindex",type = "house")
@Data
@Builder
public class HouseOwnerDo {

    @Id
    private Long ownerId;

    // 使用分词器
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String ownerName;

    @Field(type = FieldType.Keyword)
    private String houseName;

    @Field(type = FieldType.Integer)
    private int age;

}
