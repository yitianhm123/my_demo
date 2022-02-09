package com.example.esservice.config;

/*
 * Copyright (c) 2020-2030 Sishun.Co.Ltd. All Rights Reserved.
 */

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：weiwei
 * @version ：V1.0
 * @program ：scp-inventory
 * @date ：Created in 2020/1/30 1:28 下午
 * @description ：druid 数据源配置
 */
@SpringBootConfiguration
@Slf4j
public class DruidDataSourceConfig {
    @Autowired
    private WallFilter wallFilter;

    @Autowired
    private StatFilter statFilter;

    @Autowired
    private Slf4jLogFilter slf4jLogFilter;

    @Value("${spring.datasource.druid.use-global-data-source-stat}")
    private boolean useGlobalDataSourceStat;

    @Value("${spring.datasource.druid.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.druid.pool-prepared-statements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.druid.max-pool-prepared-statement-per-connection-size}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.druid.time-between-eviction-runs-millis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.druid.min-evictable-idle-time-millis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.druid.validation-query}")
    private String validationQuery;

    @Value("${spring.datasource.druid.test-while-idle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.druid.test-on-borrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.druid.test-on-return}")
    private boolean testOnReturn;

    /**
     * 初始化DruidDataSource对象
     * Spring Boot 2.X 版本不再支持配置继承，多数据源的话每个数据源的所有配置都需要单独配置，否则配置不会生效
     * 为了配置方便，这里直接读取bootstrap.yml配置文件中的内容，并手动加载到DruidDataSource对象中
     *
     * @return DruidDataSource对象，并且已经按照配置文件加载好了各项配置
     */
    private DataSource getDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        // filter配置
        List filterList = new ArrayList<>();
        filterList.add(statFilter);
        filterList.add(wallFilter);
        filterList.add(slf4jLogFilter);
        druidDataSource.setProxyFilters(filterList);
        druidDataSource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        druidDataSource.setValidationQuery(validationQuery);
        druidDataSource.setTestWhileIdle(testWhileIdle);
        druidDataSource.setTestOnBorrow(testOnBorrow);
        druidDataSource.setTestOnReturn(testOnReturn);
        return druidDataSource;
    }

    @Bean(name = "bmpDataSource")
    @Qualifier("bmpDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.bmp")
    public DataSource bmpDataSource() {
        log.info("init bmp data source");
        return getDataSource();
    }

//    @Bean(name = "ebayDataSource")
//    @Qualifier("ebayDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.druid.ebay")
//    public DataSource ebayDataSource() {
//        log.info("init ebay data source");
//        return getDataSource();
//    }

//    @Bean(name = "shopifyDataSource")
//    @Qualifier("shopifyDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.druid.shopify")
//    public DataSource shopifyDataSource() {
//        log.info("init shopify data source");
//        return getDataSource();
//    }
//
//    @Bean(name = "erpDataSource")
//    @Qualifier("erpDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.druid.erp")
//    public DataSource erpDataSource() {
//        log.info("init erp data source");
//        return getDataSource();
//    }
//
//    @Bean(name = "amazonDataSource")
//    @Qualifier("amazonDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.druid.amazon")
//    public DataSource amazonDataSource() {
//        log.info("init amazon data source");
//        return getDataSource();
//    }
//
//    @Bean(name = "xxlDataSource")
//    @Qualifier("xxlDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.druid.xxl")
//    public DataSource xxlDataSource() {
//        log.info("init xxl data source");
//        return getDataSource();
//    }

}
