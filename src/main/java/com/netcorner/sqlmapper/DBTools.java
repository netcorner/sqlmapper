package com.netcorner.sqlmapper;

import com.netcorner.sqlmapper.utils.StringTools;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.sql.DataSource;
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

	/**
	 * 执行方法体并返回一条记录
	 * @param key
	 * @param properties
	 * @return
	 */
	public static Map<String, Object> executeByMap(String key,Map<String,Object> properties){
		String[] arr=getMapKeyArray(key);
		SQLMap map=SQLMap.getMap(getMapKey(arr));
		String sid=getMapStatementID(arr);
		Map<String, Object> obj= map.executeForMap(sid, properties,true);
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
		Map<String, Object> obj= map.executeForMap(sid, properties,false);
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
	 * 分页显示数据
	 * @param key
	 * @param properties
	 * @return
	 */
	public static  PageInfo pageData(String key,Map<String,Object> properties){
		return pageData(key,properties,null);
	}

	/**
	 * 分页显示数据
	 * @param key
	 * @param properties
	 * @param primaryKey
	 * @return
	 */
	public static  PageInfo pageData(String key,Map<String,Object> properties,String primaryKey){
		RequestAttributes requestAttributes=RequestContextHolder.getRequestAttributes();
		if(requestAttributes==null){
			QueryPage queryPage = new QueryPage();
			queryPage.setShowPage(new int[]{15, 30, 50});

			if(properties.containsKey("order")){
				queryPage.setOrder(properties.get("order")+"");
			}
			if(properties.containsKey("way")){
				queryPage.setWay(Integer.parseInt(properties.get("way")+""));
			}
			if(properties.containsKey("page")){
				queryPage.setCurrent(Integer.parseInt(properties.get("page")+""));
			}
			if(properties.containsKey("size")){
				queryPage.setSize(Integer.parseInt(properties.get("size")+""));
			}
			if(properties.containsKey("rigor")){
				queryPage.setRigor(Integer.parseInt(properties.get("rigor")+""));
			}

			return pageData(key, queryPage, properties, primaryKey);
		}else {
			WebQueryPage webQueryPage = new WebQueryPage();
			webQueryPage.setShowPage(new int[]{15, 30, 50});
			return pageData(key, webQueryPage, properties, primaryKey);
		}
	}

	/**
	 * 分页显示数据, 只限于 WEB
	 * @param key
	 * @param webQueryPage
	 * @return
	 */
	public static  PageInfo pageData(String key,WebQueryPage webQueryPage){
		return pageData(key,webQueryPage,null,null);
	}

	/**
	 * 分页显示数据, 只限于 WEB
	 * @param key
	 * @param webQueryPage
	 * @param primaryKey
	 * @return
	 */
	public static  PageInfo pageData(String key,WebQueryPage webQueryPage,String primaryKey){
		return pageData(key,webQueryPage,null,primaryKey);
	}

	/**
	 * 分页显示数据
	 * @param key
	 * @param queryPage
	 * @param primaryKey
	 * @return
	 */
	public static  PageInfo pageData(String key,QueryPage queryPage,String primaryKey){
		return pageData(key,queryPage,null,primaryKey);
	}

	/**
	 * 分页显示数据
	 * @param key
	 * @param queryPage
	 * @return
	 */
	public static PageInfo pageData(String key,QueryPage queryPage){
		return pageData(key,queryPage,null,null);
	}

	/**
	 * 分页显示数据
	 * @param key
	 * @param queryPage
	 * @param params
	 * @param primaryKey
	 * @return
	 */
	public static  PageInfo pageData(String key,QueryPage queryPage,Map<String,Object> params,String primaryKey){
		String[] arr=getMapKeyArray(key);
		String mapkey=getMapKey(arr);
		SQLMap map=SQLMap.getMap(mapkey);
		String sid=getMapStatementID(arr);
		if(params!=null)queryPage.setForm(params);
		List<Map<String, Object>> list= map.executeForList(sid, queryPage);
		if(!StringTools.isNullOrEmpty(primaryKey)) {
			if (list.size() > 0) {
				if(params==null) params=new HashMap<String,Object>();
				params.put("list", list);
				List<Map<String, Object>> list2 = DBTools.selectData(mapkey + "." + sid + "_children", params);
				if (list2.size() > 0) {
					for (Map<String, Object> obj : list) {
						for (Map<String, Object> hash : list2) {
							String fk = hash.get("FK") + "";
							if (fk.equals(obj.get(primaryKey) + "")) {
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

		PageInfo pageInfo=new PageInfo();
		pageInfo.setList(list);
		pageInfo.setQueryPage(queryPage);
		return pageInfo;
	}


	/**
	 * 初始化数据库
	 * @param dataSource
	 * @param dbName
	 */
	public static void initDB(DataSource dataSource,String dbName){
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		SQLMap.setJdbcTemplates(dbName,jdbcTemplate);
	}

	/**
	 * select 返回多条记录
	 * @param sql
	 * @return
	 */
	public static List<Map<String,Object>> executeForList(String sql){
		return executeForList("datasource",sql);
	}
	/**
	 * select 返回多条记录
	 * @param dbName
	 * @param sql
	 * @return
	 */
	public static List<Map<String,Object>> executeForList(String dbName,String sql){
		SQLMap sqlMap=SQLMap.getMapByName(dbName);
		return sqlMap.executeForList(sql);
	}

	/**
	 * select 返回一条记录
	 * @param sql
	 * @return
	 */
	public static Map<String,Object> executeForMap(String sql){
		return executeForMap("datasource",sql);
	}

	/**
	 * select 返回一条记录
	 * @param dbName
	 * @param sql
	 * @return
	 */
	public static Map<String,Object> executeForMap(String dbName,String sql){
		SQLMap sqlMap=SQLMap.getMapByName(dbName);
		return sqlMap.executeForMap(sql);
	}

	/**
	 * insert、update、delete 执行
	 * @param sql
	 * @return
	 */
	public static int update(String sql){
		return update("datasource",sql);
	}
	/**
	 * insert、update、delete 执行
	 * @param dbName
	 * @param sql
	 * @return
	 */
	public static int update(String dbName,String sql){
		SQLMap sqlMap=SQLMap.getMapByName(dbName);
		return sqlMap.update(sql);
	}

	/**
	 *  执行字符串sql
	 * @param sql
	 */
	public static void execute(String sql){
		execute("datasource",sql);
	}
	/**
	 * 执行字符串sql
	 * @param dbName
	 * @param sql
	 */
	public static void execute(String dbName,String sql){
		SQLMap sqlMap=SQLMap.getMapByName(dbName);
		sqlMap.execute(sql);
	}

}
