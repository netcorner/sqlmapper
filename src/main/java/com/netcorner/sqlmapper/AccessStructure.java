package com.netcorner.sqlmapper;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

public class AccessStructure extends DBStructure {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造
	 * @param jdbcTemplate
	 */
	@SuppressWarnings("unchecked")
	public AccessStructure(JdbcTemplate jdbcTemplate){
        List<String> tables = this.getTables();
        Map<String,List<Field>> fields = this.getFields();
        Map<String,List<Field>> primarys=this.getPrimarys();
        String tablename;
        ResultSet tableRet=null;
        String sql;
		try {
			tableRet = jdbcTemplate.getDataSource().getConnection().getMetaData().getTables(jdbcTemplate.getDataSource().getConnection().getCatalog(), null, null, new String[]{"TABLE"});

	        while(tableRet.next()){
	            tablename=tableRet.getString("TABLE_NAME").toLowerCase();
	            if (!fields.containsKey(tablename))
	            {
	               
	                tables.add(tablename);
	                
		        	sql="select * from "+tablename+" where 1=2";
		        	List<List<Field>> objs=(List<List<Field>>)jdbcTemplate.execute(sql,new PreparedStatementCallback(){
		        		public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
						      	ps.execute();
						      	ResultSetMetaData rsd = ps.executeQuery().getMetaData(); 
						      	List<Field> list = new ArrayList<Field>();
						      	List<Field> plist = new ArrayList<Field>();
						      	for(int i = 0; i < rsd.getColumnCount(); i++) { 
						      		//System.out.println(rsd.getColumnName(i + 1)+":"+rsd.isReadOnly(i + 1)+":"+rsd.getColumnTypeName(i + 1));
					        		Field field = new Field();
					        		field.setId(i);
					        		field.setName(rsd.getColumnName(i + 1));
					        		String type=(rsd.getColumnTypeName(i + 1)).toLowerCase();
					        		field.setType(type);
					        		field.setLen(rsd.getColumnDisplaySize(i + 1));
					        		//access数据库只能约定有键名为id
					        		if(field.getName().toLowerCase().equals("id")){
					        			field.setIsPrimary(true);
					        		}
					                if (field.getIsPrimary()){ 
					                	plist.add(field);
					                	field.setIsIdentity(true);
					                	field.setIsClusterKey(true);
					                }
	//				                //field.setIsForiegn(false);
					                field.setIsNullable(rsd.isNullable(i+1)==1);
					                list.add(field);
						          }  
						      	List<List<Field>> objs=new ArrayList<List<Field>>();
						      	objs.add(list);
						      	objs.add(plist);
						      	return objs;
				         }
				      });
		        	fields.put(tablename,objs.get(0));
		        	primarys.put(tablename, objs.get(1));
	            }
	        }
		} catch (SQLException e) {
			throw new DALException(e.getMessage());
		}

        setScript();
	}
	@Override
	public String exportStructure(){
		return null;
	}
}
