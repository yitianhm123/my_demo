package com.example.esservice.api;


import com.example.esservice.vo.HouseInfo;
import com.example.esservice.vo.ReqeustESBo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "my-es-service", contextId = "house-es", path = "house")
public interface IHouseEsService {

    @GetMapping("/getHouseInfoById")
    HouseInfo getHouseInfoById(@RequestParam("houseId") Integer houseId);


    @GetMapping("/getHouseInfoById")
    List<HouseInfo> findAll(@RequestBody ReqeustESBo reqeustESBo);


    @PostMapping("/getHouseInfosByConditions")
    List<HouseInfo> getHouseInfosByConditions(@RequestParam("houseId") String name);
}
