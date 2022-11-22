package com.netcorner.sqlmapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shijiufeng on 2022/11/11.
 * 字段过滤器抽象类
 */
public abstract class FieldFilter<T> {
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Map<String,FieldFilter> filterMap=new HashMap<String, FieldFilter>();
    public FieldFilter(String name,String description){
        if(!filterMap.containsKey(name)) {
            filterMap.put(name, this);
            setDescription(description);
        }else{
            new DALException(name+"字段过滤名已经存在！");
        }
    }
    public static FieldFilter getFieldFilter(String name){
        if(filterMap.containsKey(name)) {
            return filterMap.get(name);
        }else{
            return null;
        }
    }

    /**
     * 过滤结果集
     * @param result
     * @param params
     */
    public void filterResult(Object result, Map<String, Object> params){
        if(result instanceof Map){
            filterResult((Map)result,params);
        }else if(result instanceof List){
            for(Map map:(List<Map<String,Object>>)result) {
                filterResult((Map) map, params);
            }
        }else{

        }
    }
    public  abstract   void filterResult(Map result, Map<String, Object> params);
}
