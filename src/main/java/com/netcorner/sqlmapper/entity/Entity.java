package com.netcorner.sqlmapper.entity;

import com.google.gson.*;
import com.netcorner.sqlmapper.*;
import com.netcorner.sqlmapper.utils.StringTools;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
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
                sqlMap.setDebugger(table.debugger());
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
     * 插入映射实体到数据库表中
     * @param statementid
     * @param <B>
     * @return
     */
    public <B> B  insert(String statementid){
        return insert(statementid,entity2Map(this),null);
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

    public <B> B  update(String statementid){
        return update(statementid,entity2Map(this),null);
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
     * 删除映射实体到数据库表中
     * @param statementid
     * @param <B>
     * @return
     */
    public <B> B  delete(String statementid){
        return delete(statementid, entity2Map(this),null);
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
        if(obj!=null) {
            T t = (T) map2Entity(obj, this.getClass());
            BeanUtils.copyProperties(t, this);
        }
    }

    /**
     * 根据对象参数 获取对象
     * @param statementid
     */
    public void get(String statementid) {
        get(statementid,entity2Map(this));
    }

    /**
     * 根据对象参数 获取对象
     * @param clazz
     * @param <B>
     */
    public <B> void get(Class<?> clazz) {
        get("_list",entity2Map(this),clazz);
    }

    /**
     * 根据对象参数 获取对象
     * @param statementid
     * @param params
     * @param clazz
     * @param <B>
     */
    public <B> void get(String statementid,Map<String,Object> params,Class<?> clazz) {
        Map<String, Object> obj= sqlMap.executeForMap(statementid, params);
        if(obj!=null) {
            B t = (B) map2Entity(obj, clazz);
            BeanUtils.copyProperties(t, this);
        }
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
     * 根据hash参数 获取对象列表
     * @param clazz
     * @param <B>
     * @return
     */
    public <B> List<B> find(Class<?> clazz){
        return find("_list",entity2Map(this),clazz);
    }

    /**
     * 根据对象参数 获取对象列表
     * @param statementid
     * @return
     */
    public List<T> find(String statementid){
        return find(statementid,entity2Map(this));
    }
    /**
     * 根据hash参数 获取对象列表
     * @param statementid
     * @param params
     * @param clazz
     * @param <B>
     * @return
     */
    public <B> List<B> find(String statementid,Map<String,Object> params,Class<?> clazz){
        List<Map<String, Object>> list= sqlMap.executeForList(statementid, params);
        List<B> list1=new ArrayList<B>();
        for(Map<String,Object> obj:list){
            list1.add((B)map2Entity(obj,clazz));
        }
        return list1;
    }

    /**
     * 获取分页信息
     * @param qp
     * @return
     */
    public List<T> page(QueryPage qp){
        return page("_page",qp,null,entity2Map(this),this.getClass());
    }

    /**
     * 获取分页信息
     * @param qp
     * @param childrenKey
     * @return
     */
    public List<T> page(QueryPage qp,String childrenKey){
        return page("_page",qp,childrenKey,entity2Map(this),this.getClass());
    }

    /**
     * 获取分页信息
     * @param qp
     * @param clazz
     * @return
     */
    public <B> List<B> page(QueryPage qp,Class<?> clazz){
        return page("_page",qp,null,entity2Map(this),clazz);
    }

    /**
     * 获取分页信息
     * @param qp
     * @param childrenKey
     * @param clazz
     * @return
     */
    public <B> List<B> page(QueryPage qp,String childrenKey,Class<?> clazz){
        return page("_page",qp,childrenKey,entity2Map(this),clazz);
    }


    /**
     * 获取分页信息
     * @param statementid
     * @param qp
     * @param childrenKey
     * @param params
     * @param clazz
     * @param <B>
     * @return
     */
    public <B> List<B> page(String statementid,QueryPage qp,String childrenKey,Map<String,Object> params,Class<?> clazz){

        if(params!=null) qp.setForm(params);
        List<Map<String, Object>> list= sqlMap.executeForList(statementid,qp );

        if(!StringTools.isNullOrEmpty(childrenKey)){
            if(list.size()>0){
                if(params==null) params=new HashMap<String,Object>();
                params.put("list",list);
                List<Map<String, Object>> list2 =DBTools.selectData(_mapKey+"._page_children",params);
                if(list2.size()>0){
                    for(Map<String, Object> obj:list) {
                        for (Map<String, Object> hash : list2) {
                            String fk = hash.get("FK") + "";

                            if (fk.equals(obj.get(childrenKey) + "")){
                                List<Map<String, Object>> children;
                                if (!obj.containsKey("children")) {
                                    children = new ArrayList<Map<String, Object>>();
                                    obj.put("children", children);
                                } else {
                                    children = (List<Map<String, Object>>) obj.get("children");
                                }
                                children.add(hash);

                            }
                        }

                    }
                }
            }

        }

        List<B> list1=new ArrayList<B>();
        for(Map<String,Object> obj:list){
            list1.add((B)map2Entity(obj,clazz));
        }
        return list1;
    }



    public static Object map2Entity(Map<String,Object> obj,Class<?> clazz) {
        return getGson().fromJson(toJson(obj), clazz);
    }

    public static Map<String,Object> entity2Map(Entity obj) {
        return fromJson(toJson(obj).replaceAll("\\\\u0027","''"),Map.class);
    }


    private static Gson gson=null;
    public static Gson getGson(){
        if(gson==null) {
            //此为要过滤掉的属性数组
            final String[] gl = {"_mapKey", "sqlMap", "_debugger"};
            //创建临时实例,并编写过滤规则
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
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
                    }).registerTypeAdapter(LocalDateTime.class,new LocalDateAdapter())
                    .addSerializationExclusionStrategy(new ExclusionStrategy() {
                        //此为转json的字段,当字段名与数组中的某个值一致时,不进行转json
                        public boolean shouldSkipField(FieldAttributes fa) {
                            for (String s : gl) {
                                if (s.equals(fa.getName())) {
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
        }
        return gson;
    }

    /**
     * 对象转换成json字符串
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        Gson gson = getGson();
        return gson.toJson(obj);
    }

    /**
     * json字符串转成对象
     * @param str
     * @param type
     * @return
     */
    public static <B> B fromJson(String str, Type type) {
        Gson gson = getGson();
        return gson.fromJson(str, type);
    }



    /**
     * 记录转换结果
     * @param obj
     * @return
     */
    public static  <B> B  convertResult(Object obj,Class<?> clazz){
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
