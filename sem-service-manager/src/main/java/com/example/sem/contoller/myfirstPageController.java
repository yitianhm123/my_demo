package com.example.sem.contoller;

import com.example.common.Response.ResponseResult;
import com.example.sem.service.ISyncMassegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class myfirstPageController {


    @Autowired
    private ISyncMassegeService syncMassegeService;


    @PostMapping("getDataFromDb")
    private ResponseResult getDataFromDb(){
        for (int i=0;i<10;i++){
            syncMassegeService.sayHello();
        }
        return null;

    }


}
