package com.example.esservice.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.example.esservice.es.vo.RequestVo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.micrometer.core.instrument.util.JsonUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.InternalOrder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


/**
 * elasticsearch 搜索引擎
 * @author zhoudong
 * @version 0.1
 * @date 2018/12/13 15:33
 */
@Service
public class HouseIndexTemplateService<T> {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;




    //    @Override
    public List<T> query(String keyword, Class<T> clazz) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(new QueryStringQueryBuilder(keyword))
                .withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                // .withSort(new FieldSortBuilder("createTime").order(SortOrder.DESC))
                .build();

        return elasticsearchTemplate.queryForList(searchQuery,clazz);
    }


    public List<T> getHouse(RequestVo requestVO){
        BoolQueryBuilder queryBuilder=new BoolQueryBuilder();
        Arrays.stream(RequestVo.class.getDeclaredFields()).forEach(field -> {
        JsonObject jsonObject =new JsonObject();
            queryBuilder.filter(QueryBuilders.termQuery(field.getName(),JsonParser.parseString(JSONUtils.toJSONString(requestVO)).getAsJsonObject().get(field.getName())));
        });
        TermsAggregationBuilder aggregationBuilder =  AggregationBuilders.terms("").field("").
                subAggregation(AggregationBuilders.sum("sum_").field("sales"));
        NativeSearchQuery nativeSearchQuery =new NativeSearchQueryBuilder().
                addAggregation(aggregationBuilder)
                .withIndices("")
                .withTypes("")
                .withPageable(PageRequest.of(1,10))
                .withQuery(queryBuilder).build();

        return null;
    }


    /**
     * 高亮显示
     * @auther: zhoudong
     * @date: 2018/12/13 21:22
     */

    public  List<Map<String,Object>> queryHit(String keyword, String indexName, String ... fieldNames) {
        // 构造查询条件,使用标准分词器.
        QueryBuilder matchQuery = createQueryBuilder(keyword,fieldNames);

        // 设置高亮,使用默认的highlighter高亮器
        HighlightBuilder highlightBuilder = createHighlightBuilder(fieldNames);

        // 设置查询字段
        SearchResponse response = elasticsearchTemplate.getClient().prepareSearch(indexName)
                .setQuery(matchQuery)
                .highlighter(highlightBuilder)
                .setSize(10000) // 设置一次返回的文档数量，最大值：10000
                .get();

        // 返回搜索结果
        SearchHits hits = response.getHits();

        return getHitList(hits);
    }
    /**
     * 高亮显示，返回分页
     * @auther: zhoudong
     * @date: 2018/12/18 10:29
     */

    public Page<Map<String, Object>> queryHitByPage(int pageNo, int pageSize, String keyword, String indexName, String... fieldNames) {
        // 构造查询条件,使用标准分词器.
        QueryBuilder matchQuery = createQueryBuilder(keyword,fieldNames);

        // 设置高亮,使用默认的highlighter高亮器
        HighlightBuilder highlightBuilder = createHighlightBuilder(fieldNames);

        // 设置查询字段
        SearchResponse response = elasticsearchTemplate.getClient().prepareSearch(indexName)
                .setQuery(matchQuery)
                .highlighter(highlightBuilder)
                .setFrom((pageNo-1) * pageSize)
                .setSize(pageNo * pageSize) // 设置一次返回的文档数量，最大值：10000
                .get();


        // 返回搜索结果
        SearchHits hits = response.getHits();

        Long totalCount = hits.getTotalHits();

//
//        Page<Map<String, Object>> page = new AggregatedPageImpl<>(pageNo,pageSize,totalCount.intValue());
//        page.setList(getHitList(hits));
        return null;
    }

    /**
     * 构造查询条件
     * @auther: zhoudong
     * @date: 2018/12/18 10:42
     */
    private QueryBuilder createQueryBuilder(String keyword, String... fieldNames){
        // 构造查询条件,使用标准分词器.
        return QueryBuilders.multiMatchQuery(keyword,fieldNames)   // matchQuery(),单字段搜索
                .analyzer("ik_max_word")
                .operator(Operator.OR);
    }
    /**
     * 构造高亮器
     * @auther: zhoudong
     * @date: 2018/12/18 10:44
     */
    private HighlightBuilder createHighlightBuilder(String... fieldNames){
        // 设置高亮,使用默认的highlighter高亮器
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                // .field("productName")
                .preTags("<span style='color:red'>")
                .postTags("</span>");

        // 设置高亮字段
        for (String fieldName: fieldNames) highlightBuilder.field(fieldName);

        return highlightBuilder;
    }

    /**
     * 处理高亮结果
     * @auther: zhoudong
     * @date: 2018/12/18 10:48
     */
    private List<Map<String,Object>> getHitList(SearchHits hits){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map;
        for(SearchHit searchHit : hits){
            map = new HashMap<>();
            // 处理源数据
            map.put("source",searchHit.getSourceAsMap());
            // 处理高亮数据
            Map<String,Object> hitMap = new HashMap<>();
            searchHit.getHighlightFields().forEach((k,v) -> {
                String hight = "";
                for(Text text : v.getFragments()) hight += text.string();
                hitMap.put(v.getName(),hight);
            });
            map.put("highlight",hitMap);
            list.add(map);
        }
        return list;
    }

//    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteIndex(String indexName) {
        elasticsearchTemplate.deleteIndex(indexName);
    }
}
