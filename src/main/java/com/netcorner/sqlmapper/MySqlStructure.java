package com.netcorner.sqlmapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

public class MySqlStructure extends DBStructure  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造
	 * @param jdbcTemplate
	 */
	@SuppressWarnings("unchecked")
	public MySqlStructure(JdbcTemplate jdbcTemplate){
		String sql=readFile("MySqlStructure");
		List<Field> list=null;
		List<String> tables = this.getTables();
		Map<String,List<Field>> fields = this.getFields();
		Map<String,List<Field>> primarys=this.getPrimarys();
		List<Field> plist=null;
		String tablename;
		List<Map<String,Object>> slist=jdbcTemplate.queryForList(sql);
		int i=1;
		for(Map<String,Object> m : slist){
			tablename=(m.get("TableName")+"").toLowerCase();
			if (!fields.containsKey(tablename))
			{
				list = new ArrayList<Field>();
				plist = new ArrayList<Field>();
				fields.put(tablename, list);
				tables.add(tablename);
				tableComments.put(tablename,m.get("TableComment")+"");
				primarys.put(tablename, plist);
			}

			Field field = new Field();
			field.setId(i++);
			field.setName(m.get("Field") + "");
			String type = (m.get("Type") + "").toLowerCase();
			field.setType(type.replaceAll("[(]\\d+[)]", ""));
			String str = type.replace(field.getType(), "").replace("(", "").replace(")", "");
			if (str.equals("")) {
				field.setLen(0);
			} else {
				field.setLen(Integer.parseInt(str));
			}
			field.setIsPrimary((m.get("Key") + "").equals("PRI"));
			if (field.getIsPrimary()) {
				plist.add(field);
				field.setIsIdentity(true);
				field.setIsClusterKey(true);
			}
			field.setComment(m.get("Comment")+"");
			//field.setIsForiegn(false);
			field.setIsNullable((m.get("Null") + "").equals("YES"));
			list.add(field);
		}
		setScript();

	}




//	public MySqlStructure(JdbcTemplate jdbcTemplate){
//        String sql = "SHOW TABLE STATUS";
//        List<Field> list=null;
//        List<String> tables = this.getTables();
//        Map<String,List<Field>> fields = this.getFields();
//        Map<String,List<Field>> primarys=this.getPrimarys();
//        List<Field> plist=null;
//        String tablename;
//        List<Map<String,Object>> slist=jdbcTemplate.queryForList(sql);
//        int i=1;
//        for(Map<String,Object> m : slist){
//            tablename=(m.get("Name")+"").toLowerCase();
//            if (!fields.containsKey(tablename))
//            {
//                list = new ArrayList<Field>();
//                plist = new ArrayList<Field>();
//                fields.put(tablename, list);
//                tables.add(tablename);
//                primarys.put(tablename, plist);
//            }
//        	sql="SHOW COLUMNS FROM "+tablename;
//
//
//        	List<Map<String,Object>> list1=null;
//        	try {
//				list1 = jdbcTemplate.queryForList(sql);
//			}catch (Exception e){}
//			if(list1!=null) {
//				for (Map<String, Object> o : list1) {
//					Field field = new Field();
//					field.setId(i++);
//					field.setName(o.get("Field") + "");
//					String type = (o.get("Type") + "").toLowerCase();
//					field.setType(type.replaceAll("[(]\\d+[)]", ""));
//					String str = type.replace(field.getType(), "").replace("(", "").replace(")", "");
//					if (str.equals("")) {
//						field.setLen(0);
//					} else {
//						field.setLen(Integer.parseInt(str));
//					}
//					field.setIsPrimary((o.get("Key") + "").equals("PRI"));
//					if (field.getIsPrimary()) {
//						plist.add(field);
//						field.setIsIdentity(true);
//						field.setIsClusterKey(true);
//					}
//					//field.setIsForiegn(false);
//					field.setIsNullable((o.get("Null") + "").equals("YES"));
//					list.add(field);
//				}
//			}
//        }
//        setScript();
//
//	}
}
