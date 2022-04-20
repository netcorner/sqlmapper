package com.netcorner.sqlmapper;

import com.netcorner.sqlmapper.entity.User;

import java.util.*;

/**
 * Created by shijiufeng on 2022/4/20.
 */
public class SelectDemo extends HelloDemo {
    public static void main(String[] args) {
        initDatasource();

        testSelectBase();
        testSelectVar();
        testSelectFun();
        testSelectMutileResult();
        testSelectBaseEntity();
        testSelectIdEntity();
    }

    /**
     * 基本的 insert 语句示例
     */
    private static void testSelectBase(){
        SQLMap map=SQLMap.getMap("datasource.user");
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("UserName","test");
        System.out.println(map.executeForList("select_base", params));//获取多条使用executeForList， map.executeForMap 获取单条
    }

    /**
     * insert标签 中使用 table 变量替代表格名
     */
    private static void testSelectVar(){
        SQLMap map=SQLMap.getMap("datasource.user");
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("UserName","test");
        System.out.println(map.executeForMap("select_table", params));//获取多条使用executeForList， map.executeForMap 获取单条

    }

    /**
     * insert标签 中使用宏函数语句，传递参数名必须和数据库表格字段名字一致才有效。
     */
    private static void testSelectFun(){
        SQLMap map=SQLMap.getMap("datasource.user");
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("UserName","test");
        System.out.println(map.executeForList("select_fun", params));//#Where 条件对于字符串使用的是模糊匹配，可能返回多条

    }


    /**
     * 返回多结果集合
     */
    private static void testSelectMutileResult(){
        SQLMap map=SQLMap.getMap("datasource.user");
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("ID",1);
        System.out.println(map.executeForList("select_mutile", params));//#Where 条件对于字符串使用的是模糊匹配，可能返回多条

    }


    /**
     * 通过实体方式插入数据,默认statement声明体
     */
    private static void testSelectBaseEntity(){
        //实体 insert 的 statement是id=_insert，配置文件可合并datasource 下的base.xml，
        User user=new User();
        user.setID(1);
        user.get();//不填为默认的 id=_select中，也可以指定 id，传入的参数是实体，也可以指定其他参数

        //user.find(); //可以返回 List

        System.out.println(user);
    }

    /**
     * 通过实体方式插入数据,指定声明体
     */
    private static void testSelectIdEntity(){
        User user=new User();
        user.setID(2);
        user.get("select_mutile");//不填为默认的 id=_select中，也可以指定 id，传入的参数是实体，也可以指定其他参数
        //user.find(); //可以返回 List


        System.out.println(user);
    }
}
