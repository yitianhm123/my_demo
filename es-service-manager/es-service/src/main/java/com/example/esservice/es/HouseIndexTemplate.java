package com.example.esservice.es;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


// indexName索引名称，type类型
@Document(indexName = "houseindex", type = "house")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HouseIndexTemplate {

    @Id
    private Long houseId;

    // 使用分词器
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
//    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Keyword)
    private String name;

    @Field(type = FieldType.Double)
    private double price;


}
