package com.example.esservice.controller;


import com.example.esservice.es.HouseIndexTemplate;
import com.example.esservice.es.reporsitory.HouseRepository;
import com.example.esservice.service.BaseSearchService;
import com.example.esservice.vo.HouseInfo;
import com.example.esservice.vo.ReqeustESBo;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "hello")
public class HelloController {


    @Resource
    private BaseSearchService baseSearchService;



    @PostMapping("getHouseInfosByname")
    public List<HouseInfo> getHouseInfosByname(String name){
        List<HouseInfo> houseIndexTemplates = baseSearchService.getHouseInfosByConditions(name);
        return houseIndexTemplates;
    }

    @PostMapping("getALl")
    public List<HouseInfo> getAll(String name){
        ReqeustESBo reqeustESBo =new ReqeustESBo();
        reqeustESBo.setName(name);
        List<HouseInfo> houseIndexTemplates = baseSearchService.findAll(reqeustESBo);
        return houseIndexTemplates;
    }

    @GetMapping(value = "sayHello")
    public String sayHello(){
        return "java hello world \n";
    }
}
