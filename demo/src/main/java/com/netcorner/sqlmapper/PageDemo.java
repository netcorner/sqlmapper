package com.netcorner.sqlmapper;

import com.netcorner.sqlmapper.entity.User;

import java.util.*;

/**
 * Created by shijiufeng on 2022/4/20.
 */
public class PageDemo extends Demo{
    public static void main(String[] args) {
        initDatasource();

        testPageBase();
//        testPageVar();
//        testPageFun();
//        testPageMutile();
//        testPageBaseEntity();
//        testPageIdEntity();
    }

    /**
     * 基本的 Page 语句示例
     */
    private static void testPageBase(){
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("UserName","test");

        QueryPage queryPage=new QueryPage();
        queryPage.setForm(params);

        List list=DBTools.pageData("datasource.user.page_base",params);

        System.out.println(list);
    }

    /**
     * Page标签 中使用 table 变量替代表格名
     */
    private static void testPageVar(){
        SQLMap map=SQLMap.getMap("datasource.user");
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("UserName","test");
        params.put("RealName"," 测试员");
        params.put("Pwd","123456");
        params.put("LastLoginIP","127.0.0.1");
        System.out.println(map.execute("Page_table", params));
    }

    /**
     * Page标签 中使用宏函数语句，传递参数名必须和数据库表格字段名字一致才有效。
     */
    private static void testPageFun(){
        SQLMap map=SQLMap.getMap("datasource.user");
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("UserName","test");
        params.put("RealName"," 测试员");
        params.put("Pwd","123456");
        params.put("LastLoginIP","127.0.0.1");
        System.out.println(map.execute("Page_fun", params));
    }

    /**
     * Page标签 中包含多个 Page 语句
     */
    private static void testPageMutile(){
        SQLMap map=SQLMap.getMap("datasource.user");


        List<Map> list=new ArrayList<Map>();
        Map<String,Object> obj=new HashMap<String,Object>();
        list.add(obj);
        obj.put("UserName","test1");
        obj.put("RealName"," 测试员");
        obj.put("Pwd","123456");
        obj.put("LastLoginIP","127.0.0.1");

        obj=new HashMap<String,Object>();
        list.add(obj);
        obj.put("UserName","test2");
        obj.put("RealName"," 测试员");
        obj.put("Pwd","123456");
        obj.put("LastLoginIP","127.0.0.1");

        Map<String,Object> params=new HashMap<String,Object>();
        params.put("list",list);
        System.out.println(map.execute("Page_mutile", params));
    }

//    /**
//     * 通过实体方式插入数据,默认statement声明体
//     */
//    private static void testPageBaseEntity(){
//        //实体 Page 的 statement是id=_Page，配置文件可合并datasource 下的base.xml，
//        User user=new User();
//        user.setUserName("abc");
//        user.setRealName("测试员");
//        user.setPwd("234567");
//        user.setLastLoginIP("255.0.0.1");
//        user.setLastLoginTime(new Date());
//        user.Page();//不填为默认的 id=_Page中，也可以指定 id，传入的参数是实体，也可以指定其他参数
//    }
//
//    /**
//     * 通过实体方式插入数据,指定声明体
//     */
//    private static void testPageIdEntity(){
//        User user=new User();
//        user.setUserName("abc");
//        user.setRealName("测试员");
//        user.setPwd("234567");
//        user.setLastLoginIP("255.0.0.1");
//        user.setLastLoginTime(new Date());
//        user.Page("Page_entity");//不填为默认的 id=_Page中，也可以指定 id，传入的参数是实体，也可以指定其他参数
//    }
}
