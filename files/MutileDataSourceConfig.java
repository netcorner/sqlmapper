package com.netcorner.hotelanalysis.config;

/**
 * Created by shijiufeng on 2019/3/4.
 */

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.netcorner.sqlmapper.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * 配置多数据源
 */
@Configuration
public class MutileDataSourceConfig {
    @Autowired
    DefaultListableBeanFactory defaultListableBeanFactory;


    @Bean(name = "testDataSource")
    @Qualifier("testDataSource")
    @ConfigurationProperties(prefix="spring.datasource.test.druid")
    public DataSource primaryDataSource() {
        DataSource dataSource= DruidDataSourceBuilder.create().build();
        setJdbcTemplateBean("test",dataSource);
        return dataSource;
    }



    @Bean(name = "test1DataSource")
    @Qualifier("test1DataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.test1.druid")
    public DataSource secondaryDataSource() {
        DataSource dataSource= DruidDataSourceBuilder.create().build();
        setJdbcTemplateBean("test1",dataSource);
        return dataSource;
    }


    private void setJdbcTemplateBean(String name,DataSource dataSource){
        JdbcTemplate jdbcTemplate=new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        defaultListableBeanFactory.registerSingleton(name,jdbcTemplate);
    }


    /**
     * 事务配置，使用注解如下：
     * @Transactional("testTransactionManager")
     * @param myqlDataSource
     * @return
     */
    @Bean(name = "testTransactionManager")
    public PlatformTransactionManager testTransactionManager(@Qualifier("testDataSource") DataSource myqlDataSource)
    {
        PlatformTransactionManager platformTransactionManager= new DataSourceTransactionManager(myqlDataSource);
        SqlSessionFactory.setSessions("test",platformTransactionManager);
        return platformTransactionManager;
    }


    /**
     * 事务配置，使用注解如下：
     * @Transactional("testTransactionManager")
     * @param myqlDataSource
     * @return
     */
    @Bean(name = "test1TransactionManager")
    public PlatformTransactionManager test1TransactionManager(@Qualifier("test1DataSource") DataSource myqlDataSource)
    {
        PlatformTransactionManager platformTransactionManager= new DataSourceTransactionManager(myqlDataSource);
        SqlSessionFactory.setSessions("test",platformTransactionManager);
        return platformTransactionManager;
    }
}
