package com.netcorner.sqlmapper.utils;

import java.util.Map;

/**
 * Created by shijiufeng on 2022/11/15.
 */
public class TmplUtils {
    public String eval(String template,Map<String,Object> context){
        return StringTools.evaluate(template,context);
    }
}
