package com.netcorner.demo;

import com.netcorner.sqlmapper.FieldFilter;

import java.util.List;
import java.util.Map;

/**
 * Created by shijiufeng on 2022/11/11.
 */
public class TestFieldFilter extends FieldFilter {
    public TestFieldFilter(){
        super("testFieldFilter","测试过滤器");
    }



    @Override
    public void filterResult(Map result, Map params) {
//        System.out.println("过滤前结果集====》"+result);
//        System.out.println("过滤前参数====》"+params);

        result.remove("id");//过滤字段 id
        result.remove("a");//过滤字段 a
        //System.out.println("过滤后结果集====》"+result);
        //System.out.println("过滤后参数====》"+params);
    }
}
