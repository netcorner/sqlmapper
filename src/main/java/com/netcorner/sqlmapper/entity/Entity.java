package com.netcorner.sqlmapper.entity;

import com.google.gson.*;
import com.netcorner.sqlmapper.Field;
import com.netcorner.sqlmapper.QueryPage;
import com.netcorner.sqlmapper.SQLMap;
import com.netcorner.sqlmapper.WebQueryPage;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by netcorner on 15/10/20.
 */
public abstract class Entity<T,R>  implements Serializable {
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
    public R insert(){
        Object obj= sqlMap.execute("_insert", entity2Map(this));
        if(obj instanceof Map){
            for(Map.Entry<String, Object> entry : ((Map<String,Object>)obj).entrySet()){
                return (R)entry.getValue();
            }
        }
        return (R)obj;
    }

    /**
     * 更新映射实体到数据库表中
     * @return
     */
    public R update(){
        Object obj= sqlMap.execute("_update", entity2Map(this));
        if(obj instanceof Map){
            for(Map.Entry<String, Object> entry : ((Map<String,Object>)obj).entrySet()){
                return (R)entry.getValue();
            }
        }
        return (R)obj;
    }

    /**
     * 删除映射实体到数据库表中
     * @return
     */
    public R delete(){
        Object obj= sqlMap.execute("_delete", entity2Map(this));
        if(obj instanceof Map){
            for(Map.Entry<String, Object> entry : ((Map<String,Object>)obj).entrySet()){
                return (R)entry.getValue();
            }
        }
        return (R)obj;
    }

    /**
     * 根据多个主键 ID 获取对象
     * @param id
     */
    public void find(Object id){
        find(new Object[]{id});
    }

    /**
     * 根据主键 ID 获取对象
     * @param ids
     */
    public void find(Object[] ids) {
        List<Field> list = sqlMap.getDbStructure().getPrimarys().get(sqlMap.getTable());
        Map<String,Object> params=new HashMap<String,Object>();
        int i = 0;
        if (list.size() > 0) {
            for (Field f : list) {
                params.put(f.getName(),ids[i]);
                i++;
            }
        }
        Map<String, Object> obj= sqlMap.executeForMap("_list", params);

        T t=(T)map2Entity(obj,this.getClass());
        BeanUtils.copyProperties(t, this);


    }

    /**
     * 根据对象参数 获取对象
     */
    public void find() {
        find(entity2Map(this));
    }

    /**
     * 根据hash参数 获取对象
     * @param params
     */
    public void find(Map<String,Object> params) {
        Map<String, Object> obj= sqlMap.executeForMap("_list", params);
        T t= (T)map2Entity(obj,this.getClass());
        BeanUtils.copyProperties(t, this);
    }

    /**
     * 根据对象参数 获取对象列表
     * @return
     */
    public List<T> getList(){
        return getList(entity2Map(this));
    }

    /**
     * 根据hash参数 获取对象列表
     * @param params
     * @return
     */
    public List<T> getList(Map<String,Object> params){
        List<Map<String, Object>> list= sqlMap.executeForList("_list", params);

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
    public List<T> getPageList(QueryPage qp){
        return getPageList(qp,entity2Map(this));
    }

    /**
     * 获取分页信息
     * @param qp
     * @param params
     * @return
     */
    public List<T> getPageList(QueryPage qp,Map<String,Object> params){


        List<Map<String, Object>> list= sqlMap.executeForList("_page",qp );
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
    private static <T> T fromJson(String str, Type type) {
        Gson gson = getGson();
        return gson.fromJson(str, type);
    }
}
