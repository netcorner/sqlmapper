package com.netcorner.demo;

import com.netcorner.sqlmapper.DBTools;
import com.netcorner.sqlmapper.PageInfo;
import com.netcorner.sqlmapper.QueryPage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shijiufeng on 2022/4/20.
 */
public class PageDemo extends HelloDemo {
    public static void main(String[] args) {
        initDatasource();

       testPageBase1();
        testPageBase2();
       testPageVar();
        testPageFun();
       testPageMutile1();
        testPageMutile2();
       testPageIdEntity();
    }

    /**
     * 基本的 Page 语句示例1
     */
    private static void testPageBase1(){
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("size",10);
        params.put("UserName","abc");
         PageInfo pageInfo= DBTools.pageData("datasource.user.page_base",params);
        System.out.println("记录："+pageInfo.getList());
        System.out.println("总记录数："+pageInfo.getQueryPage().getTotal());
    }

    /**
     * 基本的 Page 语句示例2
     */
    private static void testPageBase2(){
        QueryPage queryPage=new QueryPage();
        queryPage.setSize(10);
        queryPage.setCurrent(2);//跳到第二页
        PageInfo pageInfo= DBTools.pageData("datasource.user.page_base",queryPage);
        System.out.println(pageInfo.getList());
        System.out.println(pageInfo.getQueryPage().getPageTotal());
    }

    /**
     * Page标签 中使用 table 变量替代表格名
     */
    private static void testPageVar(){
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("size",10);
        PageInfo pageInfo= DBTools.pageData("datasource.user.page_var",params);
        System.out.println("记录："+pageInfo.getList());
        System.out.println("总记录数："+pageInfo.getQueryPage().getTotal());
    }

    /**
     * Page标签 中使用宏函数语句，传递参数名必须和数据库表格字段名字一致才有效。
     */
    private static void testPageFun(){
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("UserName","test");
        params.put("size",10);
        PageInfo pageInfo= DBTools.pageData("datasource.user.page_fun",params);
        System.out.println("记录："+pageInfo.getList());
        System.out.println("总记录数："+pageInfo.getQueryPage().getTotal());
    }

    /**
     * Page标签 有子集合，仅支持二级子集合
     */
    private static void testPageMutile1(){
        QueryPage queryPage=new QueryPage();
        queryPage.setSize(10);
        queryPage.setCurrent(1);
        PageInfo pageInfo= DBTools.pageData("datasource.user.page_mutile1",queryPage);
        System.out.println(pageInfo.getList());
        System.out.println(pageInfo.getQueryPage().getTotal());
    }
    /**
     * Page标签 有子集合，支持无限级子集合，调用配置页的另外一个声明体
     */
    private static void testPageMutile2(){
        QueryPage queryPage=new QueryPage();
        queryPage.setSize(50);
        queryPage.setCurrent(1);
        //会调用id=page_mutile2_children 声明体中去
        PageInfo pageInfo= DBTools.pageData("datasource.user.page_mutile2",queryPage,"ID");




        System.out.println(pageInfo.getList());
        System.out.println(pageInfo.getQueryPage().getTotal());
    }

    /**
     * 通过实体方式插入数据,指定声明体
     */
    private static void testPageIdEntity(){

        QueryPage queryPage=new QueryPage();
        queryPage.setSize(10);
        queryPage.setCurrent(1);//跳到第二页


        PageInfo pageInfo= DBTools.pageData("datasource.user.page_entity",queryPage);
        System.out.println(pageInfo.getList());
        System.out.println(pageInfo.getQueryPage().getTotal());



    }
}
