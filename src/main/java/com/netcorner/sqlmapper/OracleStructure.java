package com.netcorner.sqlmapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.jdbc.core.JdbcTemplate;


public class OracleStructure extends DBStructure {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造
	 * @param jdbcTemplate
	 */
	@SuppressWarnings("unchecked")
	public OracleStructure(JdbcTemplate jdbcTemplate){
		String sql=readFile("OracleStructure");
		List<Map<String,Object>> slist=jdbcTemplate.queryForList(sql);
        List<Field> list=null;
        List<String> tables = this.getTables();
        Map<String,List<Field>> fields = this.getFields();
        Map<String,List<Field>> primarys=this.getPrimarys();
        List<Field> plist=null;
        
        String tablename;
        int i=1;
        for(Map<String,Object> o:slist)
        {
            tablename=(o.get("TABLE_NAME")+"").toLowerCase();
            if (!fields.containsKey(tablename))
            {
                list = new ArrayList<Field>();
                plist = new ArrayList<Field>();
                fields.put(tablename, list);
                tables.add(tablename);
                primarys.put(tablename, plist);
            }
            Field field = new Field();
            field.setId(i++);
            field.setName(o.get("COLUMN_NAME")+"");
            field.setType((o.get("DATA_TYPE")+"").toLowerCase());
            field.setLen( Integer.parseInt(o.get("DATA_LENGTH")+""));
            field.setIsPrimary(Integer.parseInt(o.get("ISPRIMARY")+"") == 1);
            if (field.getIsPrimary()){ 
            	plist.add(field);
            	field.setIsIdentity(true);
            	field.setIsClusterKey(true);
            }
            field.setIsNullable ((o.get("NULLABLE")+"").equals("Y"));
            //field.setIsForiegn( Integer.parseInt(o.get("isForeign")+"") == 1);
            list.add(field);
        }
        setScript();
	}
}
