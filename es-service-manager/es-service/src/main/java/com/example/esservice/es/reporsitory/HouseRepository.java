package com.example.esservice.es.reporsitory;


import com.example.esservice.es.HouseIndexTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends ElasticsearchRepository<HouseIndexTemplate, Long> {


    List<HouseIndexTemplate> findByName(@Param("name") String name, @Param("pageinfo") PageRequest page);


    HouseIndexTemplate findByHouseId(@Param("id") long id);





}


