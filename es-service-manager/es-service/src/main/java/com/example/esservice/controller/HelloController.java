package com.example.esservice.controller;


import com.example.esservice.config.CommonConfig;
import com.example.esservice.config.CommonValueConfig;
import com.example.esservice.service.BaseSearchService;
import com.example.esservice.service.FileServiceImpl;
import com.example.esservice.vo.HouseInfo;
import com.example.esservice.vo.ReqeustESBo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.DateUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import reactor.netty.http.server.HttpServerRequest;



import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "hello")
public class HelloController {


    @Resource
    private BaseSearchService baseSearchService;

    @Resource
    private FileServiceImpl fileService;


    @Resource
    private CommonConfig config;

    @Resource
    private CommonValueConfig valueConfig;


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


    @PostMapping(value = "getFile")
    public String getFILe(){
        String datetime = DateUtils.formatDate(new Date(),"yyyyMMdd");
        System.out.println("datetime:  "+datetime);
        String prefix = config.getDirName();
        log.info("dirname ,{}",prefix);
        String filename = valueConfig.getInputFilePath()+datetime+".csv";
        String outfileName = "orderfile"
;        String dir = prefix+datetime+"/";
        File file =new File(filename);
        if(file==null){
            System.out.println("file is not exist");
        }
        fileService.parseFile(file,dir,outfileName);
        return "success";
    }
}
