package com.netcorner.sqlmapper.springbootdemo.controllers;


import com.netcorner.sqlmapper.DBTools;
import com.netcorner.sqlmapper.SQLMap;
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
public class SelectDemoController {


    /**
     * 基本的 select 语句示例
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testSelectBase",method = RequestMethod.GET)
    public Object testSelectBase() {
        SQLMap map=SQLMap.getMap("datasource.user");
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("UserName","test");
        return (map.executeForList("select_base", params));//获取多条使用executeForList， map.executeForMap 获取单条
    }

    /**
     * select标签 中使用 table 变量替代表格名
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testSelectVar",method = RequestMethod.GET)
    public Object testselectVar() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("UserName","test");
        return DBTools.selectData("datasource.user.select_table", params);
    }

    /**
     *  select标签 中使用宏函数语句，传递参数名必须和数据库表格字段名字一致才有效。
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testSelectFun",method = RequestMethod.GET)
    public Object testselectFun() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("UserName","test");
        return DBTools.selectData("datasource.user.select_fun", params);
    }

    /**
     * select标签 中包含多个 select 语句
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testSelectMutileResult",method = RequestMethod.GET)
    public Object testSelectMutileResult() {
        SQLMap map=SQLMap.getMap("datasource.user");
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("ID",1);
        return (map.executeForList("select_mutile", params));//#Where 条件对于字符串使用的是模糊匹配，可能返回多条

    }

    /**
     * 通过实体方式插入数据,默认statement声明体
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testSelectBaseEntity",method = RequestMethod.GET)
    public Object testSelectBaseEntity() {
        //实体 select 的 statement是id=_select，配置文件可合并datasource 下的base.xml，
        User user=new User();
        user.setID(1);
        user.get();//不填为默认的 id=_select中，也可以指定 id，传入的参数是实体，也可以指定其他参数

        //user.find(); //可以返回 List

        return (user);
    }

    /**
     * 通过实体方式插入数据,指定声明体
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testSelectIdEntity",method = RequestMethod.GET)
    public Object testSelectIdEntity() {
        User user=new User();
        user.setID(2);
        user.get("select_mutile");//不填为默认的 id=_select中，也可以指定 id，传入的参数是实体，也可以指定其他参数
        return user; //可以返回 List
    }
}
