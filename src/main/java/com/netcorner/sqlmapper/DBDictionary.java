package com.netcorner.sqlmapper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

public class DBDictionary {

    private static Map<String, DBStructure> dbNameList = new HashMap<String, DBStructure>();

    public static void clear()
    {
        dbNameList = new HashMap<String, DBStructure>();
    }
    public static DBStructure getRelationDictionary(JdbcTemplate jdbcTemplate,String dbname,String dbVersion)
    {
        if (dbNameList.containsKey(dbname))
        {
        	return dbNameList.get(dbname);
        }else{
        	DBStructure dbStructure=null;
			if(dbVersion.equals("SQLServer2000")||dbVersion.equals("SQLServer2005")){
				dbStructure=new SqlServerStructure(jdbcTemplate);
			}else if (dbVersion.equals("MySQL")){
				dbStructure=new MySqlStructure(jdbcTemplate);
			}else if (dbVersion.equals("Oracle")){
				dbStructure=new OracleStructure(jdbcTemplate);
			}else if (dbVersion.equals("SQLite")){
				dbStructure=new SqliteStructure(jdbcTemplate);
			}else if (dbVersion.equals("Access")){
				dbStructure=new AccessStructure(jdbcTemplate);
			}else if (dbVersion.equals("PostgreSql")){
				dbStructure=new PostgreSqlStructure(jdbcTemplate);
			}else{
					throw new DALException("未实现"+dbVersion+"数据库的结构生成！");
			}
			dbNameList.put(dbname,dbStructure);
			return dbStructure;
        }
    }

}
