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
public class DeleteDemo extends HelloDemo {
    public static void main(String[] args) {
        initDatasource();

        testDeleteBase();
        testDeleteVar();
        testDeleteFun();
        testDeleteMutile();
        testDeleteBaseEntity();
        testDeleteIdEntity();
    }

    /**
     * 基本的 Delete 语句示例
     */
    private static void testDeleteBase(){
        SQLMap map= SQLMap.getMap("datasource.user");
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("ID","2084");
        System.out.println(map.execute("delete_base", params));
    }

    /**
     * Delete标签 中使用 table 变量替代表格名
     */
    private static void testDeleteVar(){
        SQLMap map= SQLMap.getMap("datasource.user");
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("ID","2084");
        System.out.println(map.execute("delete_base", params));
    }

    /**
     * Delete标签 中使用宏函数语句，传递参数名必须和数据库表格字段名字一致才有效。
     */
    private static void testDeleteFun(){
        SQLMap map= SQLMap.getMap("datasource.user");
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("ID","2084");
        System.out.println(map.execute("delete_fun", params));
    }

    /**
     * Delete标签 中包含多个 Delete 语句
     */
    private static void testDeleteMutile(){
        SQLMap map= SQLMap.getMap("datasource.user");


        List<Map> list=new ArrayList<Map>();
        Map<String,Object> obj=new HashMap<String,Object>();
        list.add(obj);
        obj.put("ID","2084");

        obj=new HashMap<String,Object>();
        list.add(obj);
        obj.put("ID",2085);

        Map<String,Object> params=new HashMap<String,Object>();
        params.put("list",list);
        System.out.println(map.execute("delete_mutile", params));
    }

    /**
     * 通过实体方式插入数据,默认statement声明体
     */
    private static void testDeleteBaseEntity(){
        //实体 Delete 的 statement是id=_delete，配置文件可合并datasource 下的base.xml，
        User user=new User();
        user.setID(2084);
        user.delete();//不填为默认的 id=_delete中，也可以指定 id，传入的参数是实体，也可以指定其他参数
    }

    /**
     * 通过实体方式插入数据,指定声明体
     */
    private static void testDeleteIdEntity(){
        User user=new User();
        user.setID(2084);
        user.delete("delete_entity");//不填为默认的 id=_delete中，也可以指定 id，传入的参数是实体，也可以指定其他参数
    }
}
