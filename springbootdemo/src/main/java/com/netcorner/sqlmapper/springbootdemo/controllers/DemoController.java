package com.netcorner.sqlmapper.springbootdemo.controllers;


import com.netcorner.sqlmapper.DBTools;
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
public class DemoController{

    @ResponseBody
    @RequestMapping(value="/hello",method = RequestMethod.GET)
    public Map<String,Object> hello() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("userid",1);
        return DBTools.getData("datasource.user.hello", params);
    }




}
