package com.netcorner.sqlmapper.springbootdemo.controllers;


import com.netcorner.sqlmapper.DBTools;
import com.netcorner.sqlmapper.springbootdemo.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by shijiufeng on 2021/5/18.
 */
@RestController
public class InsertDemoController {


    /**
     * 基本的 insert 语句示例
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testInsertBase",method = RequestMethod.GET)
    public Object testInsertBase() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("UserName","test");
        params.put("RealName"," 测试员");
        params.put("Pwd","123456");
        params.put("LastLoginIP","127.0.0.1");
        return DBTools.insertData("datasource.user.insert_base", params);
    }

    /**
     * insert标签 中使用 table 变量替代表格名
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testInsertVar",method = RequestMethod.GET)
    public Object testInsertVar() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("UserName","test");
        params.put("RealName"," 测试员");
        params.put("Pwd","123456");
        params.put("LastLoginIP","127.0.0.1");
        return DBTools.insertData("datasource.user.insert_table", params);
    }

    /**
     *  insert标签 中使用宏函数语句，传递参数名必须和数据库表格字段名字一致才有效。
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testInsertFun",method = RequestMethod.GET)
    public Object testInsertFun() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("UserName","test");
        params.put("RealName"," 测试员");
        params.put("Pwd","123456");
        params.put("LastLoginIP","127.0.0.1");
        return DBTools.insertData("datasource.user.insert_fun", params);
    }

    /**
     * insert标签 中包含多个 insert 语句
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testInsertMutile",method = RequestMethod.GET)
    public Object testInsertMutile() {
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
        return DBTools.insertData("datasource.user.insert_mutile", params);
    }

    /**
     * 通过实体方式插入数据,默认statement声明体
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testInsertBaseEntity",method = RequestMethod.GET)
    public Object testInsertBaseEntity() {
        //实体 insert 的 statement是id=_insert，配置文件可合并datasource 下的base.xml，
        User user=new User();
        user.setUserName("abc");
        user.setRealName("测试员");
        user.setPwd("234567");
        user.setLastLoginIP("255.0.0.1");
        user.setLastLoginTime(new Date());
        return user.insert();//不填为默认的 id=_insert中，也可以指定 id，传入的参数是实体，也可以指定其他参数
    }

    /**
     * 通过实体方式插入数据,指定声明体
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testInsertIdEntity",method = RequestMethod.GET)
    public Object testInsertIdEntity() {
        User user=new User();
        user.setUserName("abc");
        user.setRealName("测试员");
        user.setPwd("234567");
        user.setLastLoginIP("255.0.0.1");
        user.setLastLoginTime(new Date());
        return user.insert("insert_entity");//不填为默认的 id=_insert中，也可以指定 id，传入的参数是实体，也可以指定其他参数
    }
}
