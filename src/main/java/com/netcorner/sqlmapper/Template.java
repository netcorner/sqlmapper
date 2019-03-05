package com.netcorner.sqlmapper;



import com.netcorner.sqlmapper.utils.FileTools;

import java.util.HashMap;
import java.util.Map;

public class Template {
	private static Map<String,String> sqlHash=new HashMap<String,String>();
	/**
	 * 得到数据模板
	 * @param dbtype
	 * @return
	 */
	public static String getSqlTemplate(String dbtype) {
		if(!sqlHash.containsKey(dbtype)){
	        sqlHash.put(dbtype, FileTools.getResFile("/template/" +dbtype+"Page.sql"));
		}
		return sqlHash.get(dbtype);
	}

}
