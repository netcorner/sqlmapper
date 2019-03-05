package com.netcorner.sqlmapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

public class PostgreSqlStructure extends DBStructure {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造
	 * @param jdbcTemplate
	 */
	@SuppressWarnings("unchecked")
	public PostgreSqlStructure(JdbcTemplate jdbcTemplate){
		String sql=readFile("PostgreSqlStructure");
		List<Map<String,Object>> slist=jdbcTemplate.queryForList(sql);
        List<Field> list=null;
        List<String> tables = this.getTables();
        Map<String,List<Field>> fields = this.getFields();
        Map<String,List<Field>> primarys=this.getPrimarys();
        List<Field> plist=null;
        
        String tablename;
        for(Map<String,Object> o:slist)
        {
            tablename=(o.get("tablename")+"").toLowerCase();
            if (!fields.containsKey(tablename))
            {
                list = new ArrayList<Field>();
                plist = new ArrayList<Field>();
                fields.put(tablename, list);
                tables.add(tablename);
                primarys.put(tablename, plist);
            }
            Field field = new Field();
            field.setId(Integer.parseInt(o.get("fieldorder")+""));
            field.setName(o.get("fieldname")+"");
            field.setType((o.get("fieldtype")+"").toLowerCase());
            field.setLen( Integer.parseInt(o.get("fieldlen")+""));
            field.setIsPrimary(!(o.get("conname")+"").equals(""));
            if (field.getIsPrimary()){ 
            	plist.add(field);
            	field.setIsIdentity(true);
            	field.setIsClusterKey(true);
            }
            field.setIsNullable ( (o.get("isnullable")+"").equals("f"));
            //field.setIsIdentity( Integer.parseInt(o.get("isIdentity")+"") == 1);
            //field.setIsForiegn( Integer.parseInt(o.get("isForeign")+"") == 1);
            //field.setIsClusterKey (Integer.parseInt(o.get("isClusterKey")+"") == 1);
            list.add(field);
        }
        setScript();
	}
}
