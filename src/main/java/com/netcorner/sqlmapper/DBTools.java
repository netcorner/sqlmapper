package com.netcorner.sqlmapper;

import com.netcorner.sqlmapper.utils.StringTools;

import java.util.ArrayList;
import java.util.HashMap;
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

	/**
	 * 分页显示数据，只限于 WEB
	 * @param key
	 * @param properties
	 * @return
	 */
	public static  List<Map<String, Object>> pageData(String key,Map<String,Object> properties){
		return pageData(key,properties,null);
	}

	/**
	 * 分页显示数据, 只限于 WEB
	 * @param key
	 * @param properties
	 * @param childrenKey
	 * @return
	 */
	public static  List<Map<String, Object>> pageData(String key,Map<String,Object> properties,String childrenKey){
		WebQueryPage webQueryPage=new WebQueryPage();
		webQueryPage.setShowPage(new int[]{ 15,30,50 });
		return pageData(key,webQueryPage,properties,childrenKey);
	}

	/**
	 * 分页显示数据, 只限于 WEB
	 * @param key
	 * @param webQueryPage
	 * @return
	 */
	public static  List<Map<String, Object>> pageData(String key,WebQueryPage webQueryPage){
		return pageData(key,webQueryPage,null,null);
	}

	/**
	 * 分页显示数据, 只限于 WEB
	 * @param key
	 * @param webQueryPage
	 * @param childrenKey
	 * @return
	 */
	public static  List<Map<String, Object>> pageData(String key,WebQueryPage webQueryPage,String childrenKey){
		return pageData(key,webQueryPage,null,childrenKey);
	}

	/**
	 * 分页显示数据
	 * @param key
	 * @param queryPage
	 * @param childrenKey
	 * @return
	 */
	public static  List<Map<String, Object>> pageData(String key,QueryPage queryPage,String childrenKey){
		return pageData(key,queryPage,null,childrenKey);
	}

	/**
	 * 分页显示数据
	 * @param key
	 * @param queryPage
	 * @return
	 */
	public static  List<Map<String, Object>> pageData(String key,QueryPage queryPage){
		return pageData(key,queryPage,null,null);
	}

	/**
	 * 分页显示数据
	 * @param key
	 * @param queryPage
	 * @param params
	 * @param childrenKey
	 * @return
	 */
	public static  List<Map<String, Object>> pageData(String key,QueryPage queryPage,Map<String,Object> params,String childrenKey){
		String[] arr=getMapKeyArray(key);
		String mapkey=getMapKey(arr);
		SQLMap map=SQLMap.getMap(mapkey);
		String sid=getMapStatementID(arr);
		if(params!=null)queryPage.setForm(params);
		List<Map<String, Object>> list= map.executeForList(sid, queryPage);
		if(!StringTools.isNullOrEmpty(childrenKey)) {
			if (list.size() > 0) {
				if(params==null) params=new HashMap<String,Object>();
				params.put("list", list);
				List<Map<String, Object>> list2 = DBTools.selectData(mapkey + "." + sid + "_children", params);
				if (list2.size() > 0) {
					for (Map<String, Object> obj : list) {
						for (Map<String, Object> hash : list2) {
							String fk = hash.get("FK") + "";
							if (fk.equals(obj.get(childrenKey) + "")) {
								List<Map<String, Object>> children;
								if (!obj.containsKey("children")) {
									children = new ArrayList<Map<String, Object>>();
									obj.put("children", children);
								} else {
									children = (List<Map<String, Object>>) obj.get("children");
								}
								hash.remove("FK");
								children.add(hash);
							}
						}

					}
				}
			}
		}
		return list;
	}
}
