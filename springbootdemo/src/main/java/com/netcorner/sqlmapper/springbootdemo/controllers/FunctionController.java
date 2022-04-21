package com.netcorner.sqlmapper.springbootdemo.controllers;

import com.netcorner.sqlmapper.DBTools;
import com.netcorner.sqlmapper.springbootdemo.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shijiufeng on 2022/4/21.
 */
@RestController
public class FunctionController {
    @ResponseBody
    @RequestMapping(value="/testFunBase1",method = RequestMethod.GET)
    public Object testFunBase1() {

        //调用继承配置文件的函数
        Map<String,Object> params=new HashMap<String,Object>();
        return (DBTools.getData("datasource.user.fun_base1",params));
    }
    @ResponseBody
    @RequestMapping(value="/testFunBase2",method = RequestMethod.GET)
    public Object testFunBase2() {

        Map<String,Object> params=new HashMap<String,Object>();
        params.put("userid",1);
        return (DBTools.getData("datasource.user.fun_base2",params));
    }
}
