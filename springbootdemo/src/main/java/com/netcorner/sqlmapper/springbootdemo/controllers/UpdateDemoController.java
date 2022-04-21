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
public class UpdateDemoController {


    /**
     * 基本的 insert 语句示例
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testUpdateBase",method = RequestMethod.GET)
    public Object testUpdateBase() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("ID","2073");
        params.put("RealName"," 测试员XXX");
        return DBTools.insertData("datasource.user.update_base", params);
    }

    /**
     * Update标签 中使用 table 变量替代表格名
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testUpdateVar",method = RequestMethod.GET)
    public Object testUpdateVar() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("ID","2073");
        params.put("RealName"," 测试员YYY");
        return DBTools.insertData("datasource.user.update_base", params);
    }

    /**
     *  Update标签 中使用宏函数语句，传递参数名必须和数据库表格字段名字一致才有效。
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testUpdateFun",method = RequestMethod.GET)
    public Object testUpdateFun() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("ID","2073");
        params.put("RealName"," 测试员ZZZ");
        return DBTools.insertData("datasource.user.update_fun", params);
    }

    /**
     * Update标签 中包含多个 Update 语句
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testUpdateMutile",method = RequestMethod.GET)
    public Object testUpdateMutile() {
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
        return DBTools.insertData("datasource.user.update_mutile", params);
    }

    /**
     * 通过实体方式插入数据,默认statement声明体
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testUpdateBaseEntity",method = RequestMethod.GET)
    public Object testUpdateBaseEntity() {
        //实体 Update 的 statement是id=_update，配置文件可合并datasource 下的base.xml，
        User user=new User();
        user.setID(2073);
        user.setUserName("abc");
        user.setPwd("234567x");
        return user.update();//不填为默认的 id=_update中，也可以指定 id，传入的参数是实体，也可以指定其他参数
    }

    /**
     * 通过实体方式插入数据,指定声明体
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testUpdateIdEntity",method = RequestMethod.GET)
    public Object testUpdateIdEntity() {
        User user=new User();
        user.setID(2073);
        user.setUserName("abcx");
        return user.update("update_entity");//不填为默认的 id=_update中，也可以指定 id，传入的参数是实体，也可以指定其他参数
    }
}
