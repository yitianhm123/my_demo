package com.example.sem.contoller;

import com.example.common.Response.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class myfirstPageController {


    @PostMapping("getDataFromDb")
    private ResponseResult getDataFromDb(){


        return null;

    }


}
