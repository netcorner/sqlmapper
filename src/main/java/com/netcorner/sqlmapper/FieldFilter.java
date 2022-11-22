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
    private String name;

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private boolean isAnsy=false;

    /**
     * 是否异步执行,默认是同步执行
     * @return
     */
    public boolean isAnsy() {
        return isAnsy;
    }

    public void setAnsy(boolean ansy) {
        isAnsy = ansy;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Map<String,FieldFilter> filterMap=new HashMap<String, FieldFilter>();
    public FieldFilter(String name,String description){
        init(name,description,false);
    }
    public FieldFilter(String name,String description,boolean ansy){
        init(name,description,ansy);
    }
    private void init(String name,String description,boolean ansy){
        if(!filterMap.containsKey(name)) {
            filterMap.put(name, this);
            setDescription(description);
            setName(name);
            setAnsy(ansy);
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
