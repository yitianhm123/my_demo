package com.example.demo;

import com.alibaba.druid.support.json.JSONUtils;
import com.example.esservice.EsServiceApplication;
import com.example.esservice.es.HouseIndexTemplate;
import com.example.esservice.es.reporsitory.HouseRepository;
import com.example.esservice.es.reporsitory.OwnerRepository;
import com.example.esservice.util.Jsonutil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsServiceApplication.class)
public class EsServiceApplicationTests {
//
    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private HouseRepository houseRepository;

    @Resource(name = "elasticsearchTemplate")
    private ElasticsearchTemplate elasticsearchTemplate;

    @Resource
    private ElasticsearchClient elasticsearchClient;


    @Autowired
    private OwnerRepository ownerRepository;

/*    @Autowired
    private HouseIndexTemplateService houseIndexTemplateServicel;*/


    @Test
    public void addUser() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (int i = 0; i < 5; i++) {
            atomicInteger.addAndGet(1);
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    HouseIndexTemplate houseIndexTemplate =
                            HouseIndexTemplate.builder().houseId(atomicInteger.longValue()).
                                    title("hm "+atomicInteger.toString())
                                    .name("Tom_" + atomicInteger.intValue()).build();
                    houseRepository.save(houseIndexTemplate);
                }
            });
        }

    }


    @Test
    public void findUser() {
//        List<HouseIndexTemplate> houses = houseRepository.findByName("tom3");
//        if(CollectionUtils.isEmpty(houses)){
//            return;
//        }
//        for (HouseIndexTemplate house:houses) {
//            System.out.println(house.toString());
//        }
//        houses.forEach(house->{
//            System.out.println(house.toString());
//        });

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        // 拼接查询条件
        queryBuilder.filter(QueryBuilders.termQuery("name","Tom_4")); //假设查询状态为1的
//        queryBuilder.filter(QueryBuilders.rangeQuery("price").gte(-1).lte(100));
        FilterAggregationBuilder filterAggregationBuilder
                = AggregationBuilders.filter("filter_buckets",queryBuilder)
                .subAggregation(AggregationBuilders.sum("price").field("price"));
        // 创建查询对象
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("houseindex")//索引名
                .withTypes("house")//类型名
                .withQuery(queryBuilder)// 查询条件对象
                .addAggregation(filterAggregationBuilder)
                .withPageable(PageRequest.of(0, 1000))//从0页开始查，每页1000个结果
                .build();



//        log.info(JSONUtils.toJSONString(searchQuery));
        // 滚动查询

        List<HouseIndexTemplate> houseIndexTemplates =
                elasticsearchTemplate.query(searchQuery,new ResultsExtractor<List<HouseIndexTemplate>>() {
                    @Override
                    public List<HouseIndexTemplate> extract(SearchResponse searchResponse) {
                        log.info(Jsonutil.toJsonString(searchResponse));
                        return null;
                    }
                });






        System.out.print(JSONUtils.toJSONString(houseIndexTemplates));

//       //  判断是否有内容
//        while (scroll.hasContent()) {
//            List<HouseIndexTemplate> content = scroll.getContent();
//            // 业务逻辑省略
//            //取下一页，scrollId在es服务器上可能会发生变化，需要用最新的。发起continueScroll请求会重新刷新快照保留时间
//            scroll = (ScrolledPage<HouseIndexTemplate>) elasticsearchTemplate.continueScroll(scroll.getScrollId(), 3000, HouseIndexTemplate.class);
//            System.out.println("content"+JSONArray.toJSONString(content));
//
//        }
//
//         最后释放查询
//        elasticsearchTemplate.clearScroll(scroll.getScrollId());
    }

}


