package com.netcorner.sqlmapper.entity;

import com.google.gson.*;
import com.netcorner.sqlmapper.Field;
import com.netcorner.sqlmapper.QueryPage;
import com.netcorner.sqlmapper.SQLMap;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by netcorner on 15/10/20.
 */
public abstract class Entity<T>  implements Serializable {
    private String _mapKey;
    private SQLMap sqlMap;
    public Entity(){
        Annotation[] annotations=this.getClass().getAnnotations();
        for(Annotation annotation:annotations){
            if(annotation instanceof Table){
                Table table=(Table) annotation;
                _mapKey=table.value();
                sqlMap=SQLMap.getMap(_mapKey);
                break;
            }
        }
        if(_mapKey==null){
            try {
                throw  new Exception("映射实体未配置 Table 注解");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 插入映射实体到数据库表中
     * @return
     */
    public <B> B insert(){
        return insert("_insert", entity2Map(this),null);
    }

    /**
     * 插入映射实体到数据库表中
     * @param statementid
     * @param params
     * @return
     */
    public <B> B  insert(String statementid,Map<String,Object> params){
        return insert(statementid,params,null);
    }

    /**
     * 插入映射实体到数据库表中
     * @param statementid
     * @param params
     * @param clazz
     * @param <B>
     * @return
     */
    public <B> B  insert(String statementid,Map<String,Object> params,Class<?> clazz){
        Object obj= sqlMap.execute(statementid, params);
        return convertResult(obj,clazz);
    }




    /**
     * 更新映射实体到数据库表中
     * @return
     */
    public <B> B update(){
        return update("_update", entity2Map(this),null);
    }

    /**
     * 更新映射实体到数据库表中
     * @param statementid
     * @param params
     * @param <B>
     * @return
     */
    public <B> B  update(String statementid,Map<String,Object> params){
        return update(statementid,params,null);
    }
    /**
     * 更新映射实体到数据库表中
     * @param statementid
     * @param params
     * @return
     */
    public <B> B  update(String statementid,Map<String,Object> params,Class<?> clazz){
        Object obj= sqlMap.execute(statementid, params);
        return convertResult(obj,clazz);
    }

    /**
     * 删除映射实体到数据库表中
     * @return
     */
    public <B> B  delete(){
        return delete("_delete", entity2Map(this),null);
    }

    /**
     * 删除映射实体到数据库表中
     * @param statementid
     * @param params
     * @param <B>
     * @return
     */
    public <B> B  delete(String statementid,Map<String,Object> params){
        return delete(statementid, params,null);
    }

    /**
     * 删除映射实体到数据库表中
     * @param statementid
     * @param params
     * @return
     */
    public <B> B  delete(String statementid,Map<String,Object> params,Class<?> clazz){
        Object obj= sqlMap.execute(statementid, params);
        return convertResult(obj,clazz);
    }

    /**
     * 根据多个主键 ID 获取对象
     * @param id
     */
    public void get(Object id){
        get(new Object[]{id});
    }

    /**
     * 根据主键 ID 获取对象
     * @param ids
     */
    public void get(Object[] ids) {
        List<Field> list = sqlMap.getDbStructure().getPrimarys().get(sqlMap.getTable());
        Map<String,Object> params=new HashMap<String,Object>();
        int i = 0;
        if (list.size() > 0) {
            for (Field f : list) {
                params.put(f.getName(),ids[i]);
                i++;
            }
        }
        get("_list", params);

    }

    /**
     * 根据对象参数 获取对象
     */
    public void get() {
        get("_list",entity2Map(this));
    }

    /**
     * 根据hash参数 获取对象
     * @param statementid
     * @param params
     */
    public void get(String statementid,Map<String,Object> params) {
        Map<String, Object> obj= sqlMap.executeForMap(statementid, params);
        T t= (T)map2Entity(obj,this.getClass());
        BeanUtils.copyProperties(t, this);
    }

    /**
     * 根据对象参数 获取对象列表
     * @return
     */
    public List<T> find(){
        return find("_list",entity2Map(this));
    }

    /**
     * 根据hash参数 获取对象列表
     * @param statementid
     * @param params
     * @return
     */
    public List<T> find(String statementid,Map<String,Object> params){
        List<Map<String, Object>> list= sqlMap.executeForList(statementid, params);

        List<T> list1=new ArrayList<T>();
        for(Map<String,Object> obj:list){
            list1.add((T)map2Entity(obj,this.getClass()));
        }
        return list1;
    }

    /**
     * 获取分页信息
     * @param qp
     * @return
     */
    public List<T> page(QueryPage qp){
        return page("_page",qp,entity2Map(this));
    }

    /**获取分页信息
     * @param statementid
     * @param qp
     * @param params
     * @return
     */
    public List<T> page(String statementid,QueryPage qp,Map<String,Object> params){


        List<Map<String, Object>> list= sqlMap.executeForList(statementid,qp );
        List<T> list1=new ArrayList<T>();
        for(Map<String,Object> obj:list){
            list1.add((T)map2Entity(obj,this.getClass()));
        }
        return list1;
    }




    private static Object map2Entity(Map<String,Object> obj,Class<?> clazz) {
        return new Gson().fromJson(toJson(obj), clazz);
    }

    private static Map<String,Object> entity2Map(Entity obj) {
        return fromJson(toJson(obj),Map.class);
    }



    private static Gson getGson(){
        //此为要过滤掉的属性数组
        final String[] gl = {"_mapKey","sqlMap"};
        //创建临时实例,并编写过滤规则
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeAdapter(Map.class, new JsonDeserializer<Map>() {
                    public Map deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        JsonObject jsonObject = json.getAsJsonObject();
                        Map p = new HashMap();
                        for (Map.Entry<String, JsonElement> e : jsonObject.entrySet()) {
                            if (e.getValue().isJsonPrimitive()) {
                                p.put(e.getKey(), e.getValue());
                            }
                            p.put(e.getKey(), e.getValue().getAsString());
                        }
                        return p;
                    }
                })
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
            //此为转json的字段,当字段名与数组中的某个值一致时,不进行转json
            public boolean shouldSkipField(FieldAttributes fa) {
                for (String s : gl) {
                    if(s.equals(fa.getName())){
                        return true;
                    }
                }
                return false;
            }
            public boolean shouldSkipClass(Class<?> arg0) {
                // TODO Auto-generated method stub
                return false;
            }
        }).create();
        return gson;
    }

    /**
     * 对象转换成json字符串
     * @param obj
     * @return
     */
    private static String toJson(Object obj) {
        Gson gson = getGson();
        return gson.toJson(obj);
    }

    /**
     * json字符串转成对象
     * @param str
     * @param type
     * @return
     */
    private static <B> B fromJson(String str, Type type) {
        Gson gson = getGson();
        return gson.fromJson(str, type);
    }


    /**
     * 记录转换结果
     * @param obj
     * @return
     */
    private <B> B  convertResult(Object obj,Class<?> clazz){
        if(obj instanceof List){
            List<Map<String,Object>> list=(List<Map<String,Object>>)obj;
            if(list.size()==1) {
                Map<String,Object> o=list.get(0);
                if (o.entrySet().size() == 1 && list.size() == 1) {
                    return (B) o;
                } else {
                    if (clazz != null) {
                        return (B)map2Entity(o,clazz);
                    } else {
                        return (B) o;
                    }

                }
            }else{
                if(clazz!=null){
                    List<B> l=new ArrayList<B>();
                    for(Map<String,Object> o:list){
                        l.add((B)map2Entity(o,clazz));
                    }
                    return (B)l;
                }
            }
        }else if(obj instanceof Map){
            Map<String,Object> o=(Map<String,Object>)obj;
            if (o.entrySet().size() == 1 ) {
                return (B) o;
            } else {
                if (clazz != null) {
                    return (B)map2Entity(o,clazz);
                } else {
                    return (B) o;
                }
            }
        }
        return (B)obj;
    }

}
