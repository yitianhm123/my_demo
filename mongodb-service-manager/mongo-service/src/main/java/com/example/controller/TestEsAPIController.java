package com.example.controller;

import com.example.esservice.api.IHouseEsService;
import com.example.esservice.vo.HouseInfo;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class TestEsAPIController {

    @Resource
    private IHouseEsService houseEsService;

    @GetMapping("findHouseById/{houseId}")
    public String getHouseByid(@PathVariable("houseId") Integer houseId){
        HouseInfo houseInfo=  houseEsService.getHouseInfoById(houseId);
        return houseInfo.toString();
    }
}
