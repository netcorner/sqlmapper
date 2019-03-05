package com.netcorner.sqlmapper.demo; /**
 * Created by shijiufeng on 2019/3/4.
 */

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * 配置多数据源,在spring boot 时配置多数据源
 */
@Configuration
public class DynamicDataSourceConfig {
    @Autowired
    DefaultListableBeanFactory defaultListableBeanFactory;


    @Bean(name = "hotelDataSource")
    @Qualifier("hotelDataSource")
    @ConfigurationProperties(prefix="spring.datasource.hotel.druid")
    public DataSource primaryDataSource() {
        DataSource dataSource= DruidDataSourceBuilder.create().build();
        setJdbcTemplateBean("hotel",dataSource);
        return dataSource;
    }



    @Bean(name = "testDataSource")
    @Qualifier("testDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.test.druid")
    public DataSource secondaryDataSource() {
        DataSource dataSource= DruidDataSourceBuilder.create().build();
        setJdbcTemplateBean("test",dataSource);
        return dataSource;
    }


    private void setJdbcTemplateBean(String name,DataSource dataSource){
        JdbcTemplate jdbcTemplate=new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        defaultListableBeanFactory.registerSingleton(name,jdbcTemplate);
    }

}
