package com.netcorner.demo;

import com.netcorner.sqlmapper.SQLMap;
import com.netcorner.demo.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shijiufeng on 2022/4/20.
 */
public class UpdateDemo extends HelloDemo {
    public static void main(String[] args) {
        initDatasource();

        testUpdateBase();
        testUpdateVar();
        testUpdateFun();
        testUpdateMutile();
        testUpdateBaseEntity();
        testUpdateIdEntity();
    }

    /**
     * 基本的 Update 语句示例
     */
    private static void testUpdateBase(){
        SQLMap map= SQLMap.getMap("datasource.user");
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("ID","2073");
        params.put("RealName"," 测试员XXX");
        System.out.println(map.execute("update_base", params));
    }

    /**
     * Update标签 中使用 table 变量替代表格名
     */
    private static void testUpdateVar(){
        SQLMap map= SQLMap.getMap("datasource.user");
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("ID","2073");
        params.put("RealName"," 测试员YYY");
        System.out.println(map.execute("update_base", params));
    }

    /**
     * Update标签 中使用宏函数语句，传递参数名必须和数据库表格字段名字一致才有效。
     */
    private static void testUpdateFun(){
        SQLMap map= SQLMap.getMap("datasource.user");
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("ID","2073");
        params.put("RealName"," 测试员ZZZ");
        System.out.println(map.execute("update_fun", params));
    }

    /**
     * Update标签 中包含多个 Update 语句
     */
    private static void testUpdateMutile(){
        SQLMap map= SQLMap.getMap("datasource.user");


        List<Map> list=new ArrayList<Map>();
        Map<String,Object> obj=new HashMap<String,Object>();
        list.add(obj);
        obj.put("ID","2074");
        obj.put("RealName"," 测试员2074");

        obj=new HashMap<String,Object>();
        list.add(obj);
        obj.put("ID",2075);
        obj.put("RealName"," 测试员2075");

        Map<String,Object> params=new HashMap<String,Object>();
        params.put("list",list);
        System.out.println(map.execute("update_mutile", params));
    }

    /**
     * 通过实体方式插入数据,默认statement声明体
     */
    private static void testUpdateBaseEntity(){
        //实体 Update 的 statement是id=_update，配置文件可合并datasource 下的base.xml，
        User user=new User();
        user.setID(2073);
        user.setUserName("abc");
        user.setPwd("234567x");
        user.update();//不填为默认的 id=_update中，也可以指定 id，传入的参数是实体，也可以指定其他参数
    }

    /**
     * 通过实体方式插入数据,指定声明体
     */
    private static void testUpdateIdEntity(){
        User user=new User();
        user.setID(2073);
        user.setUserName("abcx");
        user.update("update_entity");//不填为默认的 id=_update中，也可以指定 id，传入的参数是实体，也可以指定其他参数
    }
}
