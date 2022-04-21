package com.netcorner.demo;

import com.alibaba.druid.pool.DruidDataSource;
import com.netcorner.sqlmapper.DBTools;
import com.netcorner.sqlmapper.SQLMap;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;

/**
 * Created by shijiufeng on 2022/4/19.
 */
public class HelloDemo {
    private static DruidDataSource db;
    public static void initDatasource(){
        if(db==null) {
            db = new DruidDataSource();
            db.setDriverClassName("com.mysql.jdbc.Driver");
            db.setUsername("root");
            db.setPassword("sjf2008");
            db.setUrl("jdbc:mysql://localhost:3306/sqlmapper?autoReconnect=true&failOverReadOnly=false&maxReconnects=10&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull");
            JdbcTemplate jdbcTemplate = new JdbcTemplate(db);
            SQLMap.setJdbcTemplates("datasource", jdbcTemplate);
        }
    }
    public static void main(String[] args) {
        initDatasource();
        HashMap<String,Object> params=new HashMap<String,Object>();
        params.put("userid",1);
        System.out.println(DBTools.getData("datasource.user.hello", params));
    }
}
