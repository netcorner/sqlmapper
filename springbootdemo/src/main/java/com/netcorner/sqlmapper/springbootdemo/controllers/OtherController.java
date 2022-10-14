package com.netcorner.sqlmapper.springbootdemo.controllers;


import com.alibaba.druid.pool.DruidDataSource;
import com.netcorner.sqlmapper.DBTools;
import com.netcorner.sqlmapper.SQLMap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shijiufeng on 2021/5/18.
 */
@RestController
public class OtherController {


    /**
     * 声明体中有多个执行
     */
    @ResponseBody
    @RequestMapping(value="/testOtherMutile1",method = RequestMethod.GET)
    public Object testOtherMutile1(){
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("ID",1);
        return (DBTools.execute("datasource.user.other1",params));
    }

    /**
     * 声明体中有多个执行
     */
    @ResponseBody
    @RequestMapping(value="/testOtherMutile2",method = RequestMethod.GET)
    public Object testOtherMutile2(){
        Map<String,Object> params=new HashMap<String,Object>();
        return (DBTools.execute("datasource.user.other2",params));
    }




    /**
     * 多数据源
     */
    @ResponseBody
    @RequestMapping(value="/testOtherMutileDataSource",method = RequestMethod.GET)
    public Object testOtherMutileDataSource(){
        DruidDataSource db2=new DruidDataSource();
        db2.setDriverClassName("com.mysql.jdbc.Driver");
        db2.setUsername("root");
        db2.setPassword("sjf2008");
        db2.setUrl("jdbc:mysql://localhost:3306/sqlmapper2?autoReconnect=true&failOverReadOnly=false&maxReconnects=10&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull");
        JdbcTemplate jdbcTemplate=new JdbcTemplate(db2);
        SQLMap.setJdbcTemplates("datasource2",jdbcTemplate);


        HashMap<String,Object> params=new HashMap<String,Object>();
        return (DBTools.getData("datasource2.info.hello",params));
    }

    /**
     * 自动创建实体
     */
    @ResponseBody
    @RequestMapping(value="/testOtherGenEntity",method = RequestMethod.GET)
    private static void testOtherGenEntity() {
        SQLMap.genEntities("datasource","com.netcorner.ssx.model.entity",System.getProperty("user.dir")+"/demo");
    }


}
