package com.example.esservice.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.example.esservice.api.IHouseEsService;
import com.example.esservice.es.HouseIndexTemplate;
import com.example.esservice.es.reporsitory.HouseRepository;
import com.example.esservice.vo.HouseInfo;
import com.example.esservice.vo.ReqeustESBo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


@Service
public class BaseSearchService implements IHouseEsService {

    @Resource
    HouseRepository houseRepository;


//    @Resource
//    ElasticsearchRestTemplate elasticsearchRestTemplate;


    @Resource
    private RedisTemplate redisTemplate;



    @Override
    @GetMapping("/getHouseInfoById")
    public HouseInfo getHouseInfoById(Integer houseId) {
        HouseIndexTemplate houseIndexTemplate =  houseRepository.findByHouseId(houseId.longValue());
        if(houseIndexTemplate==null){
            return null;
        }
        HouseInfo houseInfo =new HouseInfo();
        BeanUtils.copyProperties(houseIndexTemplate,houseInfo);
        return houseInfo;

    }

    @Override
    public List<HouseInfo> findAll(ReqeustESBo reqeustESBo) {
        PageRequest pageRequest = PageRequest.of(1,10);
        Object obj = redisTemplate.opsForValue().get("mykey");
        Random random = new Random();
        int i = random.nextInt(100);
        redisTemplate.opsForHash().put("com.hm.redis",i+"_hm", JSONUtils.toJSONString(Arrays.asList(i+"123","456",i)));
        redisTemplate.opsForList().leftPush(i+"","123");
        if(obj!=null){
            System.out.println(obj);
        }


        Page<HouseIndexTemplate> houseInfos  =  houseRepository.findAll(pageRequest);
        if(houseInfos!=null){
            return null;
        }
        List<HouseIndexTemplate> houseIndexTemplates =  houseInfos.toList();
        List<HouseInfo> list=new ArrayList<>();
        houseIndexTemplates.forEach(e->{  HouseInfo houseInfo =new HouseInfo();
            BeanUtils.copyProperties(e,houseInfo);
            list.add(houseInfo);
        });

        return list;
    }

    @Override
    public List<HouseInfo> getHouseInfosByConditions(String name) {
        System.out.print("asdfasdf");
        PageRequest pg =PageRequest.of(0,10);
        List<HouseIndexTemplate> houseIndexTemplates =  houseRepository.findByName("hm",pg);
        if(CollectionUtils.isEmpty(houseIndexTemplates)){
            return null;
        }
        List<HouseInfo> list=new ArrayList<>();
        houseIndexTemplates.forEach(e->{  HouseInfo houseInfo =new HouseInfo();
            BeanUtils.copyProperties(e,houseInfo);
            list.add(houseInfo);
        });

        return list;
    }
}
