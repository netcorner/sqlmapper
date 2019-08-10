package com.netcorner.sqlmapper;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DBTools {
	/**
	 * 得到key数组
	 * @param key
	 * @return
	 */
	public static  String[] getMapKeyArray(String key){
		String[] arr=key.split("\\.");
		if(arr.length!=3){
			throw new DALException("映射key:"+key+"参数有误！");
		}
		return arr;
	}
	/**
	 * 得到映射key
	 * @param arr
	 * @return
	 */
	public static  String getMapKey(String[] arr){
		return arr[0]+"."+arr[1];
	}
	/**
	 * 得到声明id
	 * @param arr
	 * @return
	 */
	public static   String getMapStatementID(String[] arr){
		return arr[2];
	}
	/**
	 * 执行结果
	 * @param key
	 * @param properties
	 * @return
	 */
	public static Object execute(String key,Map<String,Object> properties){
		String[] arr=getMapKeyArray(key);
		SQLMap map=SQLMap.getMap(getMapKey(arr));
		String sid=getMapStatementID(arr);
		Object obj= map.execute(sid, properties);
		properties.put(sid, obj);
		return obj;
	}
	public static Map<String, Object> executeByMap(String key,Map<String,Object> properties){
		String[] arr=getMapKeyArray(key);
		SQLMap map=SQLMap.getMap(getMapKey(arr));
		String sid=getMapStatementID(arr);
		Map<String, Object> obj= map.executeForMap(sid, properties);
		properties.put(sid, obj);
		return obj;
	}

	/**
	 * 得到单条数据
	 * @param key
	 * @param properties
	 * @return
	 */
	public static Map<String, Object> getData(String key,Map<String,Object> properties){
		String[] arr=getMapKeyArray(key);
		SQLMap map=SQLMap.getMap(getMapKey(arr));
		String sid=getMapStatementID(arr);
		Map<String, Object> obj= map.executeForMap(sid, properties);
		properties.put(sid, obj);
		return obj;
	}
	/**
	 * 列出数据记录集
	 * @param key
	 * @param properties
	 * @return
	 */
	public static List<Map<String, Object>> selectData(String key,Map<String,Object> properties){
		String[] arr=getMapKeyArray(key);
		SQLMap map=SQLMap.getMap(getMapKey(arr));
		String sid=getMapStatementID(arr);
		List<Map<String, Object>> obj= map.executeForList(sid, properties);
		properties.put(sid, obj);
		return obj;
	}
	
	/**
	 * 插入数据操作
	 * @param key
	 * @param properties
	 * @return
	 */
	public static Object insertData(String key,Map<String,Object> properties){
		String[] arr=getMapKeyArray(key);
		SQLMap map=SQLMap.getMap(getMapKey(arr));
		String sid=getMapStatementID(arr);
		Object obj= map.execute(sid, properties);
		if(obj instanceof Map){
			for(Entry<String, Object> entry : ((Map<String,Object>)obj).entrySet()){
				return entry.getValue();
			}
		}
		return obj;
	}
	/**
	 * 更新数据
	 * @param key
	 * @param properties
	 * @return
	 */
	public static Object updateData(String key,Map<String,Object> properties){
		String[] arr=getMapKeyArray(key);
		SQLMap map=SQLMap.getMap(getMapKey(arr));
		String sid=getMapStatementID(arr);
		Object obj= map.execute(sid, properties);
		if(obj instanceof Map){
			for(Entry<String, Object> entry : ((Map<String,Object>)obj).entrySet()){
				return entry.getValue();
			}
		}
		return obj;
	}
	/**
	 * 删除数据
	 * @param key
	 * @param properties
	 * @return
	 */
	public static Object deleteData(String key,Map<String,Object> properties){
		String[] arr=getMapKeyArray(key);
		SQLMap map=SQLMap.getMap(getMapKey(arr));
		String sid=getMapStatementID(arr);
		Object obj= map.execute(sid, properties);
		if(obj instanceof Map){
			for(Entry<String, Object> entry : ((Map<String,Object>)obj).entrySet()){
				return entry.getValue();
			}
		}
		return obj;
	}
}
