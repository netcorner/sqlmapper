package com.netcorner.demo;

import com.alibaba.druid.pool.DruidDataSource;
import com.netcorner.sqlmapper.DBTools;
import com.netcorner.sqlmapper.SQLMap;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shijiufeng on 2022/4/20.
 */
public class OtherDemo  extends HelloDemo {
    public static void main(String[] args) {
        initDatasource();

        testOtherMutile1();
        testOtherMutile2();

        testOtherMutileDataSource();
        testOtherGenEntity();
    }

    /**
     * 声明体中有多个执行
     */
    private static void testOtherMutile1(){
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("ID",1);
        System.out.println(DBTools.execute("datasource.user.other1",params));
    }

    /**
     * 声明体中有多个执行
     */
    private static void testOtherMutile2(){
        Map<String,Object> params=new HashMap<String,Object>();
        System.out.println(DBTools.execute("datasource.user.other2",params));
    }




    /**
     * 多数据源
     */
    private static void testOtherMutileDataSource(){
        DruidDataSource db2=new DruidDataSource();
        db2.setDriverClassName("com.mysql.jdbc.Driver");
        db2.setUsername("root");
        db2.setPassword("sjf2008");
        db2.setUrl("jdbc:mysql://localhost:3306/sqlmapper2?autoReconnect=true&failOverReadOnly=false&maxReconnects=10&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull");
        JdbcTemplate jdbcTemplate=new JdbcTemplate(db2);
        SQLMap.setJdbcTemplates("datasource2",jdbcTemplate);


        HashMap<String,Object> params=new HashMap<String,Object>();
        System.out.println(DBTools.getData("datasource2.info.hello",params));
    }

    /**
     * 自动创建实体
     */
    private static void testOtherGenEntity() {
        SQLMap.genEntities("datasource","com.netcorner.ssx.model.entity",System.getProperty("user.dir")+"/demo");
    }

}
