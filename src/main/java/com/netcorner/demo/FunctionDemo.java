package com.netcorner.demo;

import com.netcorner.sqlmapper.DBTools;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shijiufeng on 2022/4/20.
 */
public class FunctionDemo  extends HelloDemo {
    public static void main(String[] args) {
        initDatasource();

        testFunBase1();
        testFunBase2();
    }
    private static void testFunBase1(){
        //调用继承配置文件的函数
        Map<String,Object> params=new HashMap<String,Object>();
        System.out.println(DBTools.getData("datasource.user.fun_base1",params));
    }
    private static void testFunBase2(){
        //调用继承配置文件的函数
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("userid",1);
        System.out.println(DBTools.getData("datasource.user.fun_base2",params));
    }
}
