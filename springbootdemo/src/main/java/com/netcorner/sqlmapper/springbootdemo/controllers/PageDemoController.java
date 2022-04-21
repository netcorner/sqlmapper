package com.netcorner.sqlmapper.springbootdemo.controllers;


import com.netcorner.sqlmapper.DBTools;
import com.netcorner.sqlmapper.PageInfo;
import com.netcorner.sqlmapper.QueryPage;
import com.netcorner.sqlmapper.SQLMap;
import com.netcorner.sqlmapper.springbootdemo.entity.User;
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
public class PageDemoController {


    /**
     * 基本的 Page 语句示例1
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testPageBase1",method = RequestMethod.GET)
    public Object testPageBase1() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("size",10);
        params.put("UserName","abc");
        PageInfo pageInfo= DBTools.pageData("datasource.user.page_base",params);
        System.out.println("记录："+pageInfo.getList());
        System.out.println("总记录数："+pageInfo.getQueryPage().getTotal());
        return pageInfo.getList();
    }

    /**
     * 基本的 Page 语句示例2
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testPageBase2",method = RequestMethod.GET)
    public Object testPageBase2() {
        QueryPage queryPage=new QueryPage();
        queryPage.setSize(10);
        queryPage.setCurrent(2);//跳到第二页
        PageInfo pageInfo= DBTools.pageData("datasource.user.page_base",queryPage);
        System.out.println(pageInfo.getList());
        System.out.println(pageInfo.getQueryPage().getPageTotal());
        return pageInfo;
    }

    /**
     * Page标签 中使用 table 变量替代表格名
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testPageVar",method = RequestMethod.GET)
    public Object testPageVar() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("size",10);
        PageInfo pageInfo= DBTools.pageData("datasource.user.page_var",params);
        System.out.println("记录："+pageInfo.getList());
        System.out.println("总记录数："+pageInfo.getQueryPage().getTotal());
        return pageInfo.getList();
    }

    /**
     * Page标签 中使用宏函数语句，传递参数名必须和数据库表格字段名字一致才有效。
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testPageFun",method = RequestMethod.GET)
    public Object testPageFun() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("UserName","test");
        params.put("size",10);
        PageInfo pageInfo= DBTools.pageData("datasource.user.page_fun",params);
        System.out.println("记录："+pageInfo.getList());
        System.out.println("总记录数："+pageInfo.getQueryPage().getTotal());
        return pageInfo.getList();

        //return 1/0;
    }

    /**
     * Page标签 有子集合，仅支持二级子集合
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testPageMutile1",method = RequestMethod.GET)
    public Object testPageMutile1() {
        QueryPage queryPage=new QueryPage();
        queryPage.setSize(10);
        queryPage.setCurrent(1);
        PageInfo pageInfo= DBTools.pageData("datasource.user.page_mutile1",queryPage);
        System.out.println(pageInfo.getList());
        System.out.println(pageInfo.getQueryPage().getTotal());
        return pageInfo;
    }

    /**
     * Page标签 有子集合，支持无限级子集合，调用配置页的另外一个声明体
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testPageMutile2",method = RequestMethod.GET)
    public Object testPageMutile2() {
        QueryPage queryPage=new QueryPage();
        queryPage.setSize(50);
        queryPage.setCurrent(1);
        //会调用id=page_mutile2_children 声明体中去
        PageInfo pageInfo= DBTools.pageData("datasource.user.page_mutile2",queryPage,"ID");




        System.out.println(pageInfo.getList());
        System.out.println(pageInfo.getQueryPage().getTotal());

        return pageInfo;
    }

    /**
     * 通过实体方式插入数据,指定声明体
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testPageIdEntity",method = RequestMethod.GET)
    public Object testPageIdEntity() {
        QueryPage queryPage=new QueryPage();
        queryPage.setSize(10);
        queryPage.setCurrent(1);//跳到第二页


        PageInfo pageInfo= DBTools.pageData("datasource.user.page_entity",queryPage);
        System.out.println(pageInfo.getList());
        System.out.println(pageInfo.getQueryPage().getTotal());

        return pageInfo;

    }
}
