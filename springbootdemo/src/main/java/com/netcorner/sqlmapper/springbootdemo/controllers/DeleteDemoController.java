package com.netcorner.sqlmapper.springbootdemo.controllers;


import com.netcorner.sqlmapper.DBTools;
import com.netcorner.sqlmapper.springbootdemo.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shijiufeng on 2021/5/18.
 */
@RestController
public class DeleteDemoController {


    /**
     * 基本的 Delete 语句示例
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testDeleteBase",method = RequestMethod.GET)
    public Object testDeleteBase() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("ID","2084");
        return DBTools.insertData("datasource.user.delete_base", params);
    }

    /**
     * Delete标签 中使用 table 变量替代表格名
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testDeleteVar",method = RequestMethod.GET)
    public Object testDeleteVar() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("ID","2084");
        return DBTools.insertData("datasource.user.delete_base", params);
    }

    /**
     *  Delete标签 中使用宏函数语句，传递参数名必须和数据库表格字段名字一致才有效。
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testDeleteFun",method = RequestMethod.GET)
    public Object testDeleteFun() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("ID","2084");
        return DBTools.insertData("datasource.user.delete_fun", params);
    }

    /**
     * Delete标签 中包含多个 Delete 语句
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testDeleteMutile",method = RequestMethod.GET)
    public Object testDeleteMutile() {
        List<Map> list=new ArrayList<Map>();
        Map<String,Object> obj=new HashMap<String,Object>();
        list.add(obj);
        obj.put("ID","2084");

        obj=new HashMap<String,Object>();
        list.add(obj);
        obj.put("ID",2085);

        Map<String,Object> params=new HashMap<String,Object>();
        params.put("list",list);
        return DBTools.insertData("datasource.user.delete_mutile", params);
    }

    /**
     * 通过实体方式插入数据,默认statement声明体
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testDeleteBaseEntity",method = RequestMethod.GET)
    public Object testDeleteBaseEntity() {
        //实体 Delete 的 statement是id=_delete，配置文件可合并datasource 下的base.xml，
        User user=new User();
        user.setID(2084);
        return user.delete();//不填为默认的 id=_delete中，也可以指定 id，传入的参数是实体，也可以指定其他参数
    }

    /**
     * 通过实体方式插入数据,指定声明体
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/testDeleteIdEntity",method = RequestMethod.GET)
    public Object testDeleteIdEntity() {
        User user=new User();
        user.setID(2084);
        return user.delete("delete_entity");//不填为默认的 id=_update中，也可以指定 id，传入的参数是实体，也可以指定其他参数
    }
}
