package com.netcorner.sqlmapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

public class SqliteStructure extends DBStructure {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造
	 * @param jdbcTemplate
	 */
	@SuppressWarnings("unchecked")
	public SqliteStructure(JdbcTemplate jdbcTemplate){
        String sql = "select * from sqlite_master where type='table'";
        List<Field> list=null;
        List<String> tables = this.getTables();
        Map<String,List<Field>> fields = this.getFields();
        Map<String,List<Field>> primarys=this.getPrimarys();
        List<Field> plist=null;
        String tablename;
        List<Map<String,Object>> slist=jdbcTemplate.queryForList(sql);
        int i=1;
        for(Map<String,Object> m : slist){
            tablename=(m.get("name")+"").toLowerCase();
            if (!fields.containsKey(tablename))
            {
                list = new ArrayList<Field>();
                plist = new ArrayList<Field>();
                fields.put(tablename, list);
                tables.add(tablename);
                primarys.put(tablename, plist);
            }
        	sql="PRAGMA table_info ("+tablename+")";
        	List<Map<String,Object>> list1=jdbcTemplate.queryForList(sql);
        	for(Map<String,Object> o : list1){
        		Field field = new Field();
        		field.setId(i++);
        		field.setName(o.get("name")+"");
        		String type=(o.get("type")+"").toLowerCase();
        		field.setType(type.replaceAll("[(]\\d+[)]", ""));
        		String str=type.replace(field.getType(), "").replace("(", "").replace(")", "");
        		if(str.equals("")){
        			field.setLen(0);
        		}else{
        			field.setLen(Integer.parseInt(str));
        		}
        		field.setIsPrimary(Integer.parseInt(o.get("pk")+"")==1);
                if (field.getIsPrimary()){ 
                	plist.add(field);
                	field.setIsIdentity(true);
                	field.setIsClusterKey(true);
                }
                //field.setIsForiegn(false);
                field.setIsNullable(Integer.parseInt((o.get("notnull")+""))==0);
                list.add(field);
        	}
        }
        setScript();
	}
}
