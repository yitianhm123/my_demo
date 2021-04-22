package com.example.esservice.es.reporsitory;

import com.example.esservice.es.HouseOwnerDo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends ElasticsearchRepository<HouseOwnerDo,Long> {
}
