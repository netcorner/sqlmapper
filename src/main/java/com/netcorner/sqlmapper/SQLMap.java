package com.netcorner.sqlmapper;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import com.netcorner.sqlmapper.utils.FileTools;
import com.netcorner.sqlmapper.utils.SpringTools;
import com.netcorner.sqlmapper.utils.StringTools;
import com.netcorner.sqlmapper.utils.TmplUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.*;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class SQLMap   implements Serializable {
	private static Logger logger =  LoggerFactory.getLogger(SQLMap.class);
	public static final String JDBC_ERROR_KEY="JdbcErrorKey";
	private static final long serialVersionUID = 1L;
	private static final String DBCache = "DBCache";
    private Map<String,Statement> statementList;
    private String key;
    private String dbName;
    private String table;
    private String commonTemplate;
    private long fileTime;
    private String resPath;

	/**
	 * 得到数据库名
	 * @return
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * 得到表格名称
	 * @return
	 */
	public String getTable(){
    	return table;
	}
	/**
     * 得到资源文件路径
     * @return
     */
	public String getResPath() {
		return resPath;
	}
	/**
	 * 设置资源文件路径
	 * @param resPath
	 */
	public void setResPath(String resPath) {
		this.resPath = resPath;
	}
//	private  ApplicationContext context;
//	/**
//	 * 得到spring窗器
//	 * @return
//	 */
//	public  ApplicationContext getContext() {
//		if(context==null){
//			context = new ClassPathXmlApplicationContext("spring.xml");
//		}
//		return context;
//	}
//	/**
//	 * 设置spring容器
//	 * @param ctx
//	 */
//	public   void setContext(ApplicationContext ctx) {
//		context = ctx;
//	}
	
//	private DataSource dataSource;
//	/**
//	 * 得到数据连接源
//	 * @return
//	 */
//	public DataSource getDataSource() {
//		if(dataSource==null){
//			if(isWebApp()){
//				this.dataSource=(DataSource)SpringTools.getObject(this.dbName);
//			}else{
//				this.dataSource = (DataSource)getContext().getBean(this.dbName);
//			}
//			
//			
//			//连接池
//			//DruidDataSource db=new DruidDataSource();
//	//		db.setDriverClassName("oracle.jdbc.driver.OracleDriver");
//	//		db.setUsername("pnxd");
//	//		db.setPassword("padmin");
//	//		db.setUrl("jdbc:oracle:thin:@localhost:1521:PNXD");		
//	
//	//		db.setDriverClassName("org.postgresql.Driver");
//	//		db.setUsername("postgres");
//	//		db.setPassword("sjf2008");
//	//		db.setUrl("jdbc:postgresql://localhost:5432/postgres");	
//			
//	//		db.setDriverClassName("com.mysql.jdbc.Driver");
//	//		db.setUsername("root");
//	//		db.setPassword("");
//	//		db.setUrl("jdbc:mysql://127.0.0.1:3306/ecshop");	
//			
//	//		db.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//	//		db.setUsername("sa");
//	//		db.setPassword("sjf@2008");
//	//		db.setUrl("jdbc:sqlserver://localhost:1433;DatabaseName=jobmate");
//	
//	//		db.setDriverClassName("sun.jdbc.odbc.JdbcOdbcDriver");
//	//		String fpath=getRootPath()+"config/test/a.mdb";
//	//		db.setUrl("jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ="+fpath);
//			
////			db.setDriverClassName("org.sqlite.JDBC");
////			String fpath=getRootPath()+"config/test/db.db";
////			db.setUrl("jdbc:sqlite:"+fpath);
//		}
//		return dataSource;
//	}

//	/**
//	 * 设置数据源
//	 * @param dataSource
//	 */
//	public void setDataSource(DataSource dataSource) {
//		this.dataSource = dataSource;
//	}
	
	public  JdbcTemplate getJdbcTemplate() {
		JdbcTemplate jdbcTemplate;
		if(jdbcTemplateMap==null){
			jdbcTemplate= (JdbcTemplate) SpringTools.getObject(this.dbName);
		}else {
			jdbcTemplate=  jdbcTemplateMap.get(dbName);
		}
		if(jdbcTemplate!=null)
			SqlSessionFactory.setSessions(dbName,jdbcTemplate.getDataSource());
		else{
			throw new DALException("请设置数据源！");
		}
		return jdbcTemplate;
	}
	private static Map<String,JdbcTemplate> jdbcTemplateMap;
	public static  void setJdbcTemplates(String dbName,JdbcTemplate jdbcTemplate){
		if(jdbcTemplateMap==null)
			jdbcTemplateMap=new HashMap<String, JdbcTemplate>();
		jdbcTemplateMap.put(dbName,jdbcTemplate);
		SqlSessionFactory.setSessions(dbName,jdbcTemplate.getDataSource());
	}

	
	private String dbVersion;
	/**
	 * 得到数据库版本
	 * @return
	 */
	public String getDbVersion(JdbcTemplate jdbc) {
		if(dbVersion==null){
			DatabaseMetaData md;
			String ver="";
			Connection conn=null;
			try {
				conn=jdbc.getDataSource().getConnection();
				md = conn.getMetaData();
				dbVersion= md.getDatabaseProductName();  
				ver=md.getDatabaseProductVersion();
			} catch (SQLException e) {
				throw new DALException(e.getMessage());
			}
			
			
			if(dbVersion.equals("Microsoft SQL Server")){
				int v=Integer.parseInt(ver.substring(0,1));
				if(v<9){
					dbVersion="SQLServer2000";
				}else{
					dbVersion="SQLServer2005";
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return dbVersion;
	}

	/**
	 * 得到sqlmap对象 的静态方法
	 * @param dbName
	 * @return
	 */
	public static SQLMap getMapByName(String dbName){
		CacheManager manager = CacheManager.create();
		Cache cache = manager.getCache(DBCache);

		if(cache==null){
			manager.addCache(DBCache);
			cache = manager.getCache(DBCache);
		}
		net.sf.ehcache.Element element = cache.get(dbName);
		SQLMap map= (SQLMap)(element == null ? null : element.getObjectValue());

		if(map==null){
			map=new SQLMap(dbName);
			element = new net.sf.ehcache.Element(dbName, map);
			cache.put(element);
		}
		return map;
	}


	/**
	 * 直接通过文件
	 * @param dbname
	 * @param path
	 * @return
	 */
	public static SQLMap getMapByFile(String dbname,String path)
	{
		File file = new File(path);
		if(file.exists()){
			long time =file.lastModified();
			SQLMap map=SQLMap.getMapByName(dbname);
			map.setFileTime(time);
			return map;
		}
		return null;
	}

	/**
	 * 得到sqlmap对象 的静态方法（文件存缓存）
	 * @param key 映射路径如：datasource.sys_user
	 * @return
	 */
	public static SQLMap getMap(String key)
    {
        String path = getPath(getConfigPath(),key);

        CacheManager manager = CacheManager.create(); 
        Cache cache = manager.getCache(DBCache);

        if(cache==null){
        	manager.addCache(DBCache);
        	cache = manager.getCache(DBCache);
        }
        net.sf.ehcache.Element element = cache.get(path);

		//logger.debug("get SQLMap========>"+element);

        SQLMap map= (SQLMap)(element == null ? null : element.getObjectValue());


		File file = null;//new File(path);
		try {
			file = ResourceUtils.getFile(path);
		} catch (FileNotFoundException e) {
			logger.error("找不到文件"+path);
		}

		if(file.exists()){
	    	long time =file.lastModified();
	        if(map==null){
	        	map=new SQLMap(key,path);
	        	map.setFileTime(time);
		        element = new net.sf.ehcache.Element(path, map); 
		        cache.put(element); 
	        }else{
	        	if(map.getFileTime()!=time){ 
	        		cache.remove(path); 
		    		map=new SQLMap(key,path);
		    		map.setFileTime(time);
			        element = new net.sf.ehcache.Element(path, map); 
			        cache.put(element); 
	        	}
	        }
    	}else{
        	if(map==null){
	        	map=new SQLMap(key,path);
		        element = new net.sf.ehcache.Element(path, map); 
		        cache.put(element); 
        	}
    	}
		//map.setTransactionStatus(status);
        return map;
    }

    private static String _appPath;

	private static String getAppPath(){
		if(_appPath==null){
			URL url=Thread.currentThread().getContextClassLoader().getResource("");
			if(url==null){
				//对于直接运行的jar 包
				_appPath=System.getProperty("user.dir");
			}else{
				_appPath=url.toString().replace("file:/", "");
			}
		}
		return _appPath;
	}


	private static String configPath;


    private static String rootPath;









    /**
     * 得到根目录路径
     * @return
     */
    public static String getRootPath() {
    	if(rootPath==null){
			rootPath=SpringTools.getEnvironmentValue("spring.sqlmapper.location");
			if(rootPath==null){
				rootPath="/resources/";//getAppPath();
			}
    		/*
            if(isWebApp()){
            	//去除classes/
            	rootPath = rootPath.substring(0,rootPath.length()-8);
        	}
        	*/
    	}
		return rootPath;
	}

	
    private static String fileExt="/{0}.xml";
    
    private static int isWebAppFlag=-1;
    /**
     * 判断是不是web项目
     * @return
     */
    private static boolean isWebApp(){
    	if(isWebAppFlag==-1){
	    	String[] arr=getAppPath().split("/");
	    	boolean f=arr[arr.length-2].toUpperCase().equals("WEB-INF");
	    	if(f){
	    		isWebAppFlag=1;
	    	}else{
	    		isWebAppFlag=0;
	    	}
    	}
    	return isWebAppFlag==1;
    }
    
    private static String configFolderPath;
    /**
     * 配置文件夹路径
     * @return
     */
    private static String getConfigFolderPath() {
    	if (StringTools.isNullOrEmpty(configFolderPath)){
	    	//可通过配置文件得到,暂时未支持
			if(StringTools.isNullOrEmpty(configFolderPath)) configFolderPath="/mapper";
	    	if(configFolderPath.substring(0,1).equals("/")){
				configFolderPath=configFolderPath.substring(1);
			}
    	}
		return configFolderPath;
	}
	/**
     * 得到配置文件路径
     * @return
     */
    private static String getConfigPath()
    {
        if (StringTools.isNullOrEmpty(configPath))
        {
            configPath = getRootPath()+getConfigFolderPath() +fileExt;
        }
    	return configPath;
    }
    /**
     * 得到资源文件的路径
     * @return
     */
    private static String getResourcePath(){
    	return getConfigFolderPath()+fileExt;
    }
    
    /**
     * 得到dal文件路径
     * @param path
     * @param key
     * @return
     */
    private static String getPath(String path,String key)
    {
        return path.replace("{0}", key.replace(".", "/"));
    }

	/**
	 * 构造时不要采用方式
	 * @param key
	 * @param path
	 */
	public SQLMap(String key,String path){
		String[] arr=key.split("\\.");
		this.key=key;
		dbName = arr[0];
		table = arr[1].toLowerCase();
		resPath= getPath(getResourcePath(),key);
		initDocument(path);
	}

	public SQLMap(String db){
		this.dbName = db;
	}
//    /**
//     * 插入操作
//     * @param statementid
//     * @param properties
//     * @return
//     */
//    public Object insert(String statementid, HashMap<String,Object> properties)
//    {
//        Statement statement = getStatement(statementid);
//        if (statement.getInsertQuery() == null){ 
//        	try {
//				throw new DALException("数据映射：" + this.key + "." + statementid + "的insert不存在！");
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
//        String sql = getTemplateValue(statement.getInsertQuery(), properties);
//        JdbcTemplate jdbc=getJdbcTemplate();
//        return jdbc.execute(sql,new PreparedStatementCallback(){
//			      public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//			    	  		List<List<Map<String,Object>>> list=new ArrayList<List<Map<String,Object>>>();
//					      	ps.execute();
//					      	while(ps.getMoreResults()){
//					      		ResultSet rs =ps.getResultSet();
//					      		while(rs.next()){
//					      			return rs.getObject(1);
//					      		}
//					      	}
//					      	return ps.getUpdateCount();
//			         }
//			      });
//    }

    private DBStructure dbStructure;
    /**
     * 得到数据库结构
     * @return
     */
    public DBStructure getDbStructure() {
    	if(dbStructure==null){
    		JdbcTemplate jdbc=this.getJdbcTemplate();
    		dbStructure=DBDictionary.getRelationDictionary(jdbc, this.dbName, this.getDbVersion(jdbc));
    	}
		return dbStructure;
	}
    /**
     * crud执行结果
     * @param statementid
     * @param properties
     * @return
     */
    public Object execute(String statementid, Map<String,Object> properties){
    	return execute(statementid,properties,false,null);
    }
    /**
     * 分页执行结果
     * @param statementid
     * @param qpage
     * @return
     */
    public List<Map<String,Object>> execute(String statementid,  QueryPage qpage){
    	return executeForList(statementid,qpage);
    }


	/**
	 * 执行sql脚本
	 * @param filePath
	 * @return
	 */
	public boolean executeBySqlFile(String filePath){
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			logger.error(e.toString());
			return false;
		}
		String line;
		StringBuilder sql = new StringBuilder();
		JdbcTemplate jdbc=this.getJdbcTemplate();
		try {
			while ((line = reader.readLine()) != null) {
				sql.append(line);
				// 如果一行以分号结尾，表示一个完整的SQL语句，执行它
				if (line.trim().endsWith(";")) {
					String sqlStatement = sql.toString();
					jdbc.execute(sqlStatement);
					sql.setLength(0); // 清空StringBuilder，准备下一个语句
				}
			}
		}catch (Exception e){
			logger.error(e.toString());
			return false;
		}
		return true;
	}


	/**
     * 带有分页的操作
     */
    @SuppressWarnings("unchecked")
	public List<Map<String,Object>> executeForList(String statementid, QueryPage qpage){
    	Object obj=execute(statementid,qpage.getForm(),true,qpage);
    	if(obj instanceof Map){
    		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
    		list.add((Map<String,Object>)obj);
    		return list;
    	}else{
    		return (List<Map<String,Object>>)obj;
    	}
    }
    /**
     * 执行操作,添加，删除，更新操作
     * @param statementid
     * @param properties
     * @return
     */
    public int executeForInt(String statementid, Map<String,Object> properties){
    	return (Integer)execute(statementid,properties,false,null);
    }
    /**
     * 执行操作返回List,多条数据
     * @param statementid
     * @param properties
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<Map<String,Object>> executeForList(String statementid, Map<String,Object> properties){
    	Object obj=execute(statementid,properties,true,null);
    	if(obj instanceof Map){
    		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
    		list.add((Map<String,Object>)obj);
    		return list;
    	}else{
    		return (List<Map<String,Object>>)obj;
    	}
    }


	/**
	 * select 返回多条记录
	 * @param sql
	 * @return
	 */
	public List<Map<String,Object>> executeForList(String sql){
		JdbcTemplate jdbc=getJdbcTemplate();
		List<Map<String,Object>> list=jdbc.queryForList(sql);
		return list;
	}

	/**
	 * select 返回一条记录
	 * @param sql
	 * @return
	 */
	public Map<String,Object> executeForMap(String sql){
		List<Map<String,Object>> list=executeForList(sql);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	/**
	 * insert、update、delete 执行
	 * @param sql
	 * @return
	 */
	public int update(String sql){
		JdbcTemplate jdbc=getJdbcTemplate();
		return jdbc.update(sql);
	}

	/**
	 * 执行字符串sql
	 * @param sql
	 */
	public void execute(String sql){
		JdbcTemplate jdbc=getJdbcTemplate();
		jdbc.execute(sql);

	}



	/**
	 * 执行操作返回Map,单条数据
	 * @param statementid
	 * @param properties
	 * @return
	 */
	public Map<String,Object> executeForMap(String statementid, Map<String,Object> properties){
		return executeForMap(statementid,properties,false);
	}
    /**
     * 执行操作返回Map,单条数据
     * @param statementid
     * @param properties
     * @return
     */
    @SuppressWarnings("unchecked")
	public Map<String,Object> executeForMap(String statementid, Map<String,Object> properties,boolean tran){
    	Object obj=execute(statementid,properties,!tran,null);
    	if(obj instanceof Map){
    		return (Map<String,Object>)obj;
    	}else{
    		List<Map<String,Object>> list=(List<Map<String,Object>>)obj;
    		if(list!=null){
	        	if(list.size()>0){
	        		return list.get(0);
	        	}
    		}
        	return null;
    	}
    }
    /**
     * 
     * @param statementid
     * @param properties
     * @return
     */
    @SuppressWarnings("unchecked")
	public Object executeForResultValue(String statementid, Map<String,Object> properties){
    	Object obj=execute(statementid,properties,false,null);
    	if(obj instanceof Map){
    		Map<String,Object> m=(Map<String,Object>)obj;
    		Set<Entry<String, Object>> set = m.entrySet(); 
            for (Entry<String, Object> entry : set) {
            	return entry.getValue();
            }
    	}
    	return null;
    }
    public int executeForResultInt(String statementid, Map<String,Object> properties){
    	return (Integer)executeForResultValue(statementid,properties);
    }
    public long executeForResultLong(String statementid, Map<String,Object> properties){
    	return (Long)executeForResultValue(statementid,properties);
    }
    
    public float executeForResultFloat(String statementid, Map<String,Object> properties){
    	return (Float)executeForResultValue(statementid,properties);
    }
    public double executeForResultDouble(String statementid, Map<String,Object> properties){
    	return (Double)executeForResultValue(statementid,properties);
    }
    public String executeForResultString(String statementid, Map<String,Object> properties){
    	return (String)executeForResultValue(statementid,properties);
    }
//    private transient TransactionStatus transactionStatus;//排除序列化
//    private void setTransactionStatus(TransactionStatus transactionStatus){
//		this.transactionStatus=transactionStatus;
//	}
    /**
     * 执行操作
     * @param statementid
     * @param properties
     * @return
     */
    private Object execute(String statementid, Map<String,Object> properties,boolean issearch,QueryPage qpage)
    {
        Object returnValue=null,tmp=null;
    	Statement statement = getStatement(statementid);
        JdbcTemplate jdbc=getJdbcTemplate();
        //jdbc.execute("set names utf8mb4");
//        if(statement.isSqlFilter()){
//        	if(qpage==null){
//        		filterSqlString(properties, issearch);
//        	}else{
//        		filterSqlString(qpage.getForm(), issearch);
//        	}
//        }


        if(issearch){
	        for(CRUDBase crud : statement.getSqlList()){
	        	tmp=execStatementSQL(jdbc,crud,properties,qpage,statementid);
	        	if(tmp!=null) returnValue=tmp;
	        	if(properties!=null){
		        	if(properties.containsKey(JDBC_ERROR_KEY)){
		        		throw new DALException("dal配置文件有误:"+this.key+"."+statementid+",错误为："+properties.get(JDBC_ERROR_KEY));
		        	}
	        	}
	        }
        }else{
//			DataSourceTransactionManager dstm = SqlSessionFactory.getSessions().get(dbName);
//			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//			TransactionStatus status = dstm.getTransaction(def);

			SqlSessionFactory sqlSessionFactory=new SqlSessionFactory();
			for (CRUDBase crud : statement.getSqlList()) {
				tmp = execStatementSQL(jdbc, crud, properties, qpage, statementid);
				if (tmp != null) returnValue = tmp;
				if (properties != null) {
					if (properties.containsKey(JDBC_ERROR_KEY)) {
						//dstm.rollback(status);
						sqlSessionFactory.rollback();
						throw new DALException("dal配置文件有误:" + this.key + "." + statementid + ",错误为：" + properties.get(JDBC_ERROR_KEY));
					}
				}
			}
			sqlSessionFactory.commit();
			//dstm.commit(status);
        }
        return returnValue;
    }




	private Object execStatementSQL(CRUDBase crud,Map<String,Object> properties,QueryPage qpage,String statementid){
		JdbcTemplate jdbc=getJdbcTemplate();
		return execStatementSQL(jdbc,crud,properties,qpage,statementid);
	}
    /**
     * 执行声明的sql语句
     * @param crud
     * @param properties
     * @param qpage
     * @return
     */
    private Object execStatementSQL(JdbcTemplate jdbc,CRUDBase crud,Map<String,Object> properties,QueryPage qpage,String statementid){
    	String tag=crud.getTagName();
    	if(tag.equals("page")){
    		return execPageSQL(jdbc,crud,qpage,statementid);
    	}else if(tag.equals("select")){
    		return execSelectSQL(jdbc,crud,properties,statementid);
    	}else if(tag.equals("insert")||tag.equals("delete")||tag.equals("update")){
    		return execCUDSQL(jdbc,crud,properties,statementid);
    	}else if(tag.equals("ext")){
    		return execExtSQL(jdbc,crud,properties,qpage,statementid);
    	}else if(tag.equals("config")){//可以做数据库的外部配置
    		String sql = getTemplateValue(crud.getSql(), properties);
    		jdbc.execute(sql);
    	}
    	
    	return null;
    }
    /**
     * 执行外部集成的sql
     * @param crud
     * @param properties
     */
    @SuppressWarnings("unchecked")
	private Object execExtSQL(JdbcTemplate jdbc,CRUDBase crud,Map<String,Object> properties,QueryPage qpage,String statementid){
		String sql = getTemplateValue(crud.getSql(), properties);
		Ext ext=(Ext)crud;
		List<Map<String,Object>> list=null;

		Map<String,Object> prop=properties;
		if(properties.containsKey(ext.getKey())){
			Object o=properties.get(ext.getKey());
			if(o instanceof  Map){
				prop=(Map<String,Object>)properties.get(ext.getKey());
			}else{
				if(o instanceof  List){
					list=(List<Map<String,Object>>)properties.get(ext.getKey());
					prop=new HashMap<String,Object>();
					prop.put("list",list);
				}else {
					throw new DALException("dal配置文件有误:" + this.key + "." + statementid + " <ext>标签key=" + ext.getKey() + "在传递的hashmap中类型必须是map或者list<map>类型!");
				}
			}
		}else{
			throw new DALException("dal配置文件有误:"+this.key+"."+statementid+" <ext>标签key="+ext.getKey()+"在传递的hashmap中不存在该key!");
		}
		Object value=null;
		sql=sql.replaceAll("[\r\n\t ]*", "");
		if(!sql.equals("")){
			String fun=sql.replace("{", "").replace("}", "");
			String[] arr=fun.split("\\.");
			if(arr.length==3){
				SQLMap map=SQLMap.getMap(arr[0]+"."+arr[1]);
				Statement statement=map.getStatement(arr[2]);
		        for(CRUDBase crudBase : statement.getSqlList()){
		        	if(list==null){
		        		value=map.execStatementSQL(crudBase,prop,qpage,statementid);
		        	}else{
		        		for(Map<String,Object> oMap:list){
		        			value=map.execStatementSQL(crudBase,oMap,qpage,statementid);
		        		}
		        	}
		        }
			}
		}
		properties.put(crud.getId(),value);
		return value;
    }
    
    /**
     * 执行分页sql
     * @param crud
     * @param qpage
     * @return
     */
    @SuppressWarnings("unchecked")
	private List<Map<String,Object>> execPageSQL(JdbcTemplate jdbc,CRUDBase crud,QueryPage qpage,String statementid){
    	//JdbcTemplate jdbc=getJdbcTemplate();
		Page page=(Page)crud;
		Map<String,Object> hash=getPageInfo(page,qpage,statementid);
		Map<String,Object> properties;
		String sql=getTemplateValue(Template.getSqlTemplate(getDbVersion(jdbc)), hash);
		
		List<String> arrsql=StringTools.split(sql, "[{][^}]+[}]");
		
		String csql;
		int i=1;
		List<Map<String,Object>> pageList=null;
		for(String s:arrsql){
			if(!s.replaceAll("[\r\n\t ]*", "").equals("")){
				csql=s.substring(1,s.length()-1);
				if(i==1){
					//主集合
					properties=new HashMap<String,Object>();
					pageList=jdbc.queryForList(csql);
					properties.put(page.getId(),pageList);
					if(qpage.getForm()==null) qpage.setForm(new HashMap<String,Object>());
					qpage.getForm().put(page.getId(), pageList);
				}else if(i==2){
					if(StringTools.isNullOrEmpty(page.getCount())){
						Boolean o=(Boolean)hash.get("return");
						if(o.booleanValue()){
    						//统计数据个数
    						qpage.setTotal(jdbc.queryForObject(csql,Integer.class));
						}
					}else{
						//其它统计信息
						qpage.setCountResult(jdbc.queryForMap(csql));
						if(qpage.getCountResult().containsKey("total")){
							int total=Integer.parseInt(qpage.getCountResult().get("total").toString());
							qpage.setTotal(total);
						}
					}
					if(qpage.getTotal()>0&&qpage.getSize()>0) qpage.setPageTotal( (qpage.getTotal()-1)/qpage.getSize()+1);
				}else if(i==3){
					if(StringTools.isNullOrEmpty(page.getCount())){
						//其它统计信息
						qpage.setCountResult(jdbc.queryForMap(csql));
					}
				}
				i++;
			}
		}
		//子集合遍历
		Map<String,Query> selectDictionary=page.getChildren();
		if(selectDictionary!=null){
			List<String> children=new ArrayList<String>();
			Map<String,List<Map<String,Object>>> result=new HashMap<String,List<Map<String,Object>>>();
			Set<Entry<String, Query>> set = selectDictionary.entrySet();




            for (Entry<String, Query> entry : set) {
            	csql = getTemplateValue(entry.getValue().getSql(), qpage.getForm());
            	result.put(entry.getKey(),jdbc.queryForList(csql));
            	children.add(entry.getKey());
            }
            generationTreeHash(pageList, children,page.getPrimary(), result, selectDictionary,statementid);
		}

		setFieldFilter(statementid,crud,pageList,qpage.getForm());

		return pageList;
    }

    private void setFieldFilter(String statementid,CRUDBase crud,final Object result,final Map<String,Object> params){
		if(!StringTools.isNullOrEmpty(crud.getFieldFilter())){

			String filter =crud.getFieldFilter();
			String[] arr=filter.split(",");
			for(String f:arr) {
				//执行字段过滤器
				final FieldFilter fieldFilter = FieldFilter.getFieldFilter(f);
				if (fieldFilter != null) {
					if(fieldFilter.isAnsy()) {

						Thread t = new Thread(){
							@Override
							public void run() {
								fieldFilter.filterResult(result, params);
							}
						};

						t.start();


					}else {
						fieldFilter.filterResult(result, params);
					}
				} else {
					throw new DALException(this.key + "." + statementid + "===>" + crud.getId() + "中找不到字段过滤器：" + crud.getFieldFilter());
				}
			}
		}
	}
    
    /**
     * 执行筛选sql语句
     * @param crud
     * @param properties
     * @return
     */
    @SuppressWarnings("unchecked")
	private Object execSelectSQL(JdbcTemplate jdbc,CRUDBase crud,Map<String,Object> properties,String statementid){
    	//JdbcTemplate jdbc=getJdbcTemplate();
    	Select select=(Select)crud;
		Map<String,Query> selectDictionary=select.getSelectDirctionary();
		String csql;
		if(selectDictionary!=null){
			Map<String,List<Map<String,Object>>> result=new HashMap<String,List<Map<String,Object>>>();
			Set<Entry<String, Query>> set = selectDictionary.entrySet(); 
            for (Entry<String, Query> entry : set) {
            	csql = getTemplateValue(entry.getValue().getSql(), properties);
            	if(csql.replaceAll("[\r\n\t ]*", StringTools.empty).length()>0)
    				try{
    					result.put(entry.getKey(),jdbc.queryForList(csql));
    				}catch(Exception e){
    					properties.put(JDBC_ERROR_KEY,e.getMessage());
    					return null;
    				}
            }
            //hash中存在多个集合，其中有一个集合是主集合
            properties.put(select.getId(),fillOneListResult(select.getRootID(),selectDictionary,result,statementid));
        }else{
        	csql = getTemplateValue(select.getSql(), properties);
        	if(csql.replaceAll("[\r\n\t ]*", StringTools.empty).length()>0){
        		List<Map<String,Object>> lst;
        		
				try{
					lst=jdbc.queryForList(csql);
				}catch(Exception e){
					properties.put(JDBC_ERROR_KEY,e.getMessage());
					return null;
				}
        		
				//logger.info("tolist:"+select.getId()+","+select.isToList());
        		if(lst.size()==1&&select.isToList()==false){
        			properties.put(select.getId(),lst.get(0));
        		}else{
        			properties.put(select.getId(),lst);
        		}
        	}
		}
		Object result = properties.get(select.getId());
		setFieldFilter(statementid,crud,result,properties);
		return result;
    }
    /**
     * 执行insert,update,delete的sql
     * @param crud
     * @param properties
     * @return
     */
    private Object execCUDSQL(JdbcTemplate jdbc,CRUDBase crud,Map<String,Object> properties,String statementid){
    	//JdbcTemplate jdbc=getJdbcTemplate();
    	Object returnValue=null;
		String sql = getTemplateValue(crud.getSql(), properties);
		if(!sql.replaceAll("[\r\n\t ]*", "").equals("")){
			

	    	List<String> arr;
			if(crud.isFilter()){
				arr=new ArrayList<String>();
				arr.add(sql);
			}else {
				arr = StringTools.split(sql, "[{][^}]+[}]");
			}
	    	if(arr.size()>0){
	    		for(String s:arr){
	    			if(!s.replaceAll("[\r\n\t ]*", "").equals("")){
	    				try{
	    					properties.put(crud.getId(),jdbc.update(s.substring(1,s.length()-1)));
	    				}catch(Exception e){
	    					properties.put(JDBC_ERROR_KEY,e.getMessage());
							return null;
							//throw new DALException("dal配置文件有误:"+this.key+"."+statementid+",错误为："+properties.get(JDBC_ERROR_KEY));
	    				}
	    				returnValue=properties.get(crud.getId());
	    			}
	    		}
	    	}
		}

		//对 cud 操作不需要过滤字段信息
		//setFieldFilter(statementid,crud,returnValue,properties);

		return returnValue;
    }
    
    /**
     * 得到分类信息
     * @param page
     * @param qpage
     * @return
     */
    private Map<String,Object> getPageInfo(Page page, QueryPage qpage,String statementid)
    {
    	Map<String,Object> context = new HashMap<String,Object>();
    	if(!StringTools.isNullOrEmpty(page.getPrimary())) context.put("primary", page.getPrimary());
    	else{
    		throw new DALException("dal配置文件有误:"+this.key+"."+statementid+" <page>标签下必须指定primary值");
    	}
    	//System.out.println( qpage.getForm());
        context.put("filter",getTemplateValue(page.getColumns(), qpage.getForm()));
        context.put("table",getTemplateValue(page.getTable(), qpage.getForm()));
        String order = "";


        if (!StringTools.isNullOrEmpty(qpage.getOrder()))
        {
            if (qpage.getWay() == 1)
            {
                order = qpage.getOrder() + " desc";
            }
            else
            {
                order = qpage.getOrder() + " asc";
            }
        }
        else
        {
            if (!StringTools.isNullOrEmpty(page.getOrder()))
            {
                order = getTemplateValue(page.getOrder(), qpage.getForm()).toLowerCase();
            }
            if (StringTools.isNullOrEmpty(order))
            {
                order = context.get("primary") + " desc";
            }

        }
        context.put("order", order);
        context.put("deorder" ,order.replaceAll(" asc", " TMP").replaceAll(" desc", " asc").replaceAll(" TMP", " desc"));
        
        context.put("where", " where " + getTemplateValue(page.getWhere(), qpage.getForm()));
        


        if (StringTools.isNullOrEmpty(page.getCount()))
        {
			if(qpage.getTotal()>0) {
				context.put("return", false);
			}else {
				context.put("return", qpage.getSize() > 0);
			}
        }
        else
        {
            context.put("return", false);
            String countsql = getTemplateValue(page.getCount(), qpage.getForm());
            if(countsql.replaceAll("[\\t\\r\\n ]*select[\\t\\r\\n \\w,(*)\\[\\]]+", "").equals(countsql)){
            	countsql="select "+ countsql+ " from "+ context.get("table")+ (page.getWhere() == null ? "" : context.get("where"));
            }
            context.put("count",countsql);
        }


        context.put("index", qpage.getCurrent());
        context.put("size", qpage.getSize());
        context.put("dispersion", qpage.getSize() * qpage.getCurrent());
        int pageno = qpage.getSize() * (qpage.getCurrent() - 1) + 1;
        context.put("pfrom", pageno);
        context.put("pto", pageno + qpage.getSize() - 1);
        return context;
    }
        
    
    /**
     * 填充多级筛选
     * @param select
     * @param result
     * @return
     */
    private List<Map<String,Object>> fillOneListResult(String rootid,Map<String, Query> select, Map<String,List<Map<String,Object>>> result,String statementid)
    {
    	generationTreeHash(result.get(rootid), select.get(rootid), result, select,statementid);
    	return result.get(rootid);
    }
    /**
     * 生成树结构
     * @param list
     * @param query
     * @param result
     * @param select
     */
    private void generationTreeHash(List<Map<String,Object>> list, Query query, Map<String,List<Map<String,Object>>> result, Map<String, Query> select,String statementid)
    {
    	generationTreeHash(list,query.getChildren(),query.getPrimary(),result,select,statementid);
    }
    /**
     * 生成树结构
     * @param list
     * @param children
     * @param primary
     * @param result
     * @param select
     */
    @SuppressWarnings("unchecked")
	private void generationTreeHash(List<Map<String,Object>> list, List<String> children,String primary, Map<String,List<Map<String,Object>>> result, Map<String, Query> select,String statementid)
    {
        if (children == null||list==null) return;
        if (primary == null && children != null){
				throw new DALException("dal配置文件有误:"+this.key+"."+statementid+"<query>标签配置有误：拥有子节点的<query>未配置primary属性");
        }else{
        	primary=primary.replaceAll("^[\\w+_]+[.]", "");
        }
        Map<String, Map<String,Object>> dir = new HashMap<String, Map<String,Object>>();
        String myid;
        for (Map<String,Object> hash : list)
        {
            myid=hash.get(primary)+"";
            dir.put(myid.toLowerCase(), hash);
        }


        for(String key : children)
        {
            if (!select.containsKey(key)) return; 
            if (select.get(key).getForeign() == null){
            	throw new DALException("dal配置文件有误:"+this.key+"."+statementid+"<query>标签未配置foreign属性");
            }
            generationTreeHash(result.get(key), select.get(key), result, select,statementid);
            List<Map<String,Object>> tmpList = null;
            if(result.containsKey(key)){
	            for (Map<String,Object> hash1 : result.get(key))
	            {
	                myid = hash1.get(select.get(key).getForeign()) + "";
	                myid=myid.toLowerCase();
	                if (dir.containsKey(myid))
	                {
	                    if (!dir.get(myid).containsKey(key))
	                    {
	                        tmpList = new ArrayList<Map<String,Object>>();
	                        dir.get(myid).put(key, tmpList);
	                    }
	                    else
	                    {
	                        tmpList = (List<Map<String,Object>>)dir.get(myid).get(key);
	                    }
	                    tmpList.add(hash1);
	                }
	            }
            }
        }


    }

    
//    /**
//     * 得到Map list
//     * @param rs
//     * @return
//     * @throws SQLException
//     */
//    private List<Map<String,Object>> getHashMap(ResultSet rs)throws SQLException{
//        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();  
//        try {
//        	ResultSetMetaData rsmd = rs.getMetaData();  
//            while(rs.next()){  
//                Map<String,Object> map = new HashMap<String,Object>();   
//                int columnCount = rsmd.getColumnCount();  
//                for(int i=0;i<columnCount;i++){  
//                    String columnName = rsmd.getColumnName(i+1);  
//                    map.put(columnName, rs.getObject(i+1));  
//                }  
//                list.add(map);  
//            }  
//        } catch (SQLException e) {  
//            e.printStackTrace();  
//        }  
//        return list;  
//    } 
    
    /**
     * 得到声明
     * @param statementid
     * @return
     */
    private Statement getStatement(String statementid)
    {
        if (!statementList.containsKey(statementid)) {
        	throw new DALException("dal配置文件有误:"+this.key+"."+statementid+"不存在");
        }
        return statementList.get(statementid);
    }
    private String extend;
    
    public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
	/**
     * 初始化文档
     * @param path
     */
    private void initDocument(String path)
    {
    	Document doc=getXMLDoc(path); 
    	if(doc==null){
			throw new DALException("数据配置文件不存在:"+path);
    	}
        Element root=doc.getDocumentElement();
        commonTemplate = getInnerText(root.getElementsByTagName("function"));
        if(commonTemplate==null) commonTemplate="";
        //根据表格中的字段自动产共享模板，insert及update的相关插入
        setCommonTemplateByField();
        mergeXML(doc,root);
        setStatement(root);
    }
    /**
     * 合并xml
     * @param root
     */
    private void mergeXML(Document doc,Element root){
        String merge=getAttributesValue(root,"merge");
        if(!StringTools.isNullOrEmpty(merge)){
        	Element root1=getXMLDoc(getPath(getConfigPath(),merge)).getDocumentElement();
    		NodeList children=root1.getChildNodes();


    		for (int j=0;j<children.getLength();j++){
    			Node tmp=children.item(j);

				logger.error("hhh====>"+tmp.getTextContent());

    			if(!tmp.getNodeName().equals("#text")&&!tmp.getNodeName().equals("#comment")){
    				Element child=(Element)tmp;
    				String tag=child.getTagName();
        			if(tag.equals("statement")){
        				String id1=this.getAttributesValue(child, "id");



        				NodeList children1=root.getElementsByTagName("statement");
        				if(children1!=null){
        					boolean flag=true;
	        				for (int i=0;i<children1.getLength();i++){
	        					Element child1=(Element)children1.item(i);
	        					String id2=this.getAttributesValue(child1, "id");
	        					if(id2.equals(id1)){
	        						flag=false;
	        						break;
	        					}
	        				}
	        				if(flag){
	        					//追加statement node
	        					root.appendChild(doc.importNode(child, true));
	        					
	        				}
        				}
        			}else if(tag.equals("function")){
        				commonTemplate+=this.getInnerText(child);
        			}    				
    			}
    		}
        }
    }


    
    /**
     * 得到xml文档
     * @param path
     * @return
     */
    private Document getXMLDoc(String path){
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        Document doc=null; 
        try{
	        DocumentBuilder builder=factory.newDocumentBuilder();
        	File file=ResourceUtils.getFile(path);//new File(path);
			if(file.exists()){
	        	//实际文件
	        	doc=builder.parse(file); 
        	}else{
	        	//资源文件
        		try{
        			doc=builder.parse(getClass().getClassLoader().getResourceAsStream(path.replace(getRootPath(),"")));
        		}catch(Exception e){
						throw new DALException("配置文件'"+file+"'或资源文件"+resPath+"不存在！");
        		}
        	}
        }catch(Exception e){
        	throw new DALException(e.getMessage());
        }
        return doc;
    }
    /**
     * 设置函数模板
     */
    private void setCommonTemplateByField()
    {
    	DBStructure structure=this.getDbStructure();
    	this.commonTemplate+=structure.getInsertScript().get(this.table);
    	this.commonTemplate+=structure.getUpdateScript().get(this.table);
    	this.commonTemplate+=structure.getDeleteScript().get(this.table);
    	this.commonTemplate+=structure.getWhereScript().get(this.table);
    	
    }
    private static TmplUtils  tmplUtils=new TmplUtils();
    /**
     * 得到模板值
     * @param template
     * @param context
     * @return
     */
    private String getTemplateValue(String template, Map<String,Object> context)
    {
        //if (context == null) return template;
        VelocityContext vcontext = new VelocityContext();
        vcontext.put("table", table);
		vcontext.put("comment",getDbStructure().getTableComments().get(table));

        if(getDbStructure().getFields().containsKey(table)){
	        vcontext.put("primarys", getDbStructure().getPrimarys().get(table));
	        vcontext.put("fields", getDbStructure().getFields().get(table));
        }

        vcontext.put("map", context);
		vcontext.put("util", tmplUtils);
        StringWriter w = new StringWriter(); 
        
        //System.out.println("default============="+template);
        
        VelocityEngine v=StringTools.getVelocityEngine();
        v.evaluate(vcontext, w, "sqlmap", template);
        
        //System.out.println("default:"+v.getProperty("output.encoding"));
        
        /**
        Velocity.setProperty("output.encoding", "UTF-8");
        Velocity.setProperty("input.encoding", "UTF-8");
        Velocity.setProperty("velocimacro.permissions.allow.inline.local.scope", true);
        Velocity.init();
        Velocity.evaluate(vcontext, w, "sqlmap", template ); //该方法定义的宏是全局的，所以放弃了该使用方法 
        System.out.println("write:"+Velocity.getProperty("output.encoding"));
        */
        
//        if(w.toString().replaceAll("[\\t\\r\\n ]+", "").length()>0){
//        	logger.info("Executing SQL Query[ " + w+" ]");
//        }
        
        if(debugger) logger.info("generation sql : " + w);
        //System.out.println( );
        return w.toString();
    }
    private boolean debugger=false;


	public void setDebugger(boolean debugger) {
		this.debugger = debugger;
	}

	/**
     * 得到共享模板
     * @return
     */
    public String getCommonTemplate(){
    	return commonTemplate;
    }
    
    /**
     * 设置声明.
     * @param root
     */
    private void setStatement(Element root)
    {
        statementList = new HashMap<String, Statement>();
        NodeList statementNodeList=root.getElementsByTagName("statement");
        if (statementNodeList != null)
        {
        	for (int i=0;i<statementNodeList.getLength();i++){
        		Element node=(Element)statementNodeList.item(i);
        		Statement statement=putStatementList(node);
        		NodeList children=node.getChildNodes();
        		for (int j=0;j<children.getLength();j++){
        			Node tmp=children.item(j);
        			if(!tmp.getNodeName().equals("#text")&&!tmp.getNodeName().equals("#comment")){
	        			Element child=(Element)tmp;
	        			String tag=child.getTagName();
	        			//config 可做数据库的外部配置
	        			if(tag.equals("insert")||tag.equals("update")||tag.equals("delete")||tag.equals("config")){
	        				setSqlTag(statement,child,children.getLength());
	        			}else if(tag.equals("select")){
	        				setSelectTag(statement,child,children.getLength());
	        			}else if(tag.equals("page")){
	        				setPageTag(statement,child,children.getLength());
	        			}else if(tag.equals("ext")){
	        				setExtTag(statement,child,children.getLength());
	        			}
	        			idIndex++;
        			}
        		}
        	}


			setExecIds(execIds);


        }
    }

    private void setExecIds(List<String> exes){
		for(String key:exes){
			Statement statement=getStatement(key);
			List<CRUDBase> list=new ArrayList<CRUDBase>();
			for (CRUDBase crudBase : statement.getSqlList()) {
				addCRUDBase(crudBase.getBeforeExecId(),list);
				list.add(crudBase);
				addCRUDBase(crudBase.getAfterExecId(),list);
			}
			statement.setSqlList(list);
		}
	}
	private void addCRUDBase(String key,List<CRUDBase> list){
		if(!StringTools.isNullOrEmpty(key)) {
			String[] slist=key.split(",");
			for(String s:slist) {
				Statement exeStatement=getExeStatement(s);
				if (exeStatement != null) {
					for (CRUDBase crudBaseAdd : exeStatement.getSqlList()) {
						list.add(crudBaseAdd);
					}
				}
			}
		}
	}

	private Statement getExeStatement(String s){
		Statement exeStatement;
		String[] arr = s.split("\\.");
		if (arr.length == 1) {
			exeStatement = getStatement(s);
		} else if (arr.length == 2) {
			SQLMap sqlMap = SQLMap.getMap(getDbName()+"."+arr[0]);
			exeStatement = sqlMap.getStatement(arr[1]);
		}else {
			SQLMap sqlMap = SQLMap.getMap(arr[0]+"."+arr[1]);
			exeStatement = sqlMap.getStatement(arr[2]);
		}
		return exeStatement;
	}



    /**
     * 设置扩展标签
     * @param statement
     * @param node
     * @param nodecount
     */
    private void setExtTag(Statement statement, Element node, int nodecount) {
        String id = getAttributesValue(node, "id");
        if(StringTools.isNullOrEmpty(id)){
        	if(nodecount==1){
        		id=statement.getId();
        	}else{
        		id=statement.getId()+idIndex;
        	}
        }

		String afterExecId = getAttributesValue(node, "afterExecId");//语句体执行后执行的语句体，可以多个，用逗号分开
		String beforeExecId = getAttributesValue(node, "beforeExecId");//语句体执行前先执行的语句体，可以多个，用逗号分开
		String execAppendSql = getAttributesValue(node, "execAppendSql");//追加 sql 语句，


    	String sql=getInnerText(node);
		if(!StringTools.isNullOrEmpty(execAppendSql)) sql+=execAppendSql;
		Ext ext=new Ext();
		ext.setTagName(node.getTagName());
		ext.setSql(sql,this.commonTemplate);
		ext.setId(id);



		ext.setAfterExecId(afterExecId);
		ext.setBeforeExecId(beforeExecId);
		ext.setExecAppendSql(execAppendSql);

		setExecIds(statement,ext);

		String key = getAttributesValue(node, "key");
		ext.setKey(key);

		statement.getSqlList().add(ext);
	}
	private void setExecIds(Statement statement,CRUDBase crudBase){
		if(!StringTools.isNullOrEmpty(crudBase.getAfterExecId())) {
			if(!execIds.contains(statement.getId()))execIds.add(statement.getId());
		}
		if(!StringTools.isNullOrEmpty(crudBase.getBeforeExecId())) {
			if(!execIds.contains(statement.getId())) execIds.add(statement.getId());
		}
	}
	private List<String> execIds=new ArrayList<String>();
	/**
     * 分页标签语句
     * @param statement
     * @param node
     */
    private void setPageTag(Statement statement,Element node,int nodecount) {
        Page page = new Page();
        page.setTagName("page");
        String id = getAttributesValue(node, "id");
        if(StringTools.isNullOrEmpty(id)){
        	if(nodecount==1){
        		id=statement.getId();
        	}else{
        		id=statement.getId()+idIndex;
        	}
        }
        page.setId(id);
		String afterExecId = getAttributesValue(node, "afterExecId");
		String beforeExecId = getAttributesValue(node, "beforeExecId");
		String execAppendSql = getAttributesValue(node, "execAppendSql");
		String fieldFilter=getAttributesValue(node, "fieldFilter");//字段过滤器
		page.setAfterExecId(afterExecId);
		page.setBeforeExecId(beforeExecId);
		page.setExecAppendSql(execAppendSql);
		page.setFieldFilter(fieldFilter);

		setExecIds(statement,page);

        String primary = getAttributesValue(node, "primary");
        if(StringTools.isNullOrEmpty(primary)){
        	if(!this.getDbStructure().getPrimarys().containsKey(this.table)){
        		throw new DALException("dal配置文件有误:表格“"+this.table+"”不存在！");
        	}
        	List<Field> fs=this.getDbStructure().getPrimarys().get(this.table);
        	if(fs.size()==1){
        		primary=fs.get(0).getName();
        	}else{
        		throw new DALException("dal配置文件有误:"+this.key+"."+statement.getId()+" <page>标签中未设定primary属性");
        	}
        }
        page.setPrimary(primary);
        
        page.setColumns(getInnerText(node.getElementsByTagName("select")));
        if (page.getColumns() == null)
        {
        	page.setColumns("*");
        }
        page.setTable(getInnerText(node.getElementsByTagName("from")));
        if (page.getTable() == null)
        {
        	page.setTable(table);
        }else{
        	page.setTable(this.commonTemplate+page.getTable());
        }
        page.setWhere(getInnerText(node.getElementsByTagName("where")));
        if(page.getWhere() == null){
        	page.setWhere(this.commonTemplate+"#Where()");
        }else{
        	page.setWhere(this.commonTemplate+page.getWhere());
        }
		if(!StringTools.isNullOrEmpty(execAppendSql)) {
			page.setWhere(page.getWhere()+execAppendSql);
		}






        page.setOrder(getInnerText(node.getElementsByTagName("order")));
        page.setCount(getInnerText(node.getElementsByTagName("count")));
        
        NodeList xmlNodeList = node.getElementsByTagName("children");
        if (xmlNodeList.getLength() > 0)
        {
            Map<String, Query> selectDictionary = new HashMap<String, Query>();
            Query select;
        	for (int i=0;i<xmlNodeList.getLength();i++)
            {
        		Element tmpNode=(Element)xmlNodeList.item(i);
                select = new Query();
                id = getAttributesValue(tmpNode, "id");
                if (StringTools.isNullOrEmpty(id)){
                	throw new DALException("dal配置文件有误:"+this.key+"."+statement.getId()+"<page>-><children>中未指定id");
                }
                if (selectDictionary.containsKey(id)) {
                	throw new DALException("dal配置文件有误:"+this.key+"."+statement.getId()+"<page>-><children>在同一个statement的配置中多次出现");
                }
                selectDictionary.put(id, select);
                select.setPrimary(getAttributesValue(tmpNode, "primary"));
                select.setForeign(getAttributesValue(tmpNode, "foreign"));
                select.setSql(getInnerText(tmpNode),this.commonTemplate);
                String parent=getAttributesValue(tmpNode, "parent");
                if(!StringTools.isNullOrEmpty(parent)){
                	select.setParent(parent);
                }else{
                	select.setParent(page.getId());
                }
            }
            page.setChildren(selectDictionary);
        }
		statement.getSqlList().add(page);



	}

    /**
     * sql标签语句设置
     * @param statement
     * @param node
     */
    private void setSqlTag(Statement statement,Element node,int nodecount){
        String id = getAttributesValue(node, "id");
        if(StringTools.isNullOrEmpty(id)){
        	if(nodecount==1){
        		id=statement.getId();
        	}else{
        		id=statement.getId()+idIndex;
        	}
        }

		String afterExecId = getAttributesValue(node, "afterExecId");
		String beforeExecId = getAttributesValue(node, "beforeExecId");
		String execAppendSql = getAttributesValue(node, "execAppendSql");
		String fieldFilter=getAttributesValue(node, "fieldFilter");//字段过滤器




    	String sql=getInnerText(node);
    	if(!StringTools.isNullOrEmpty(execAppendSql)) sql+=execAppendSql;
		CRUDBase crud=new CRUDBase();
		crud.setTagName(node.getTagName());
		crud.setSql(sql,this.commonTemplate);
		crud.setId(id);
		crud.setAfterExecId(afterExecId);
		crud.setBeforeExecId(beforeExecId);
		crud.setExecAppendSql(execAppendSql);
		crud.setFieldFilter(fieldFilter);
		setExecIds(statement,crud);
		String filter = getAttributesValue(node, "filter");
		if(filter.equals("true")){
			crud.setFilter(true);
		}

		statement.getSqlList().add(crud);
    }





    private int idIndex=1;
    /**
     * 存放声明列表
     * @param oXmlNode
     * @return
     */
    private Statement putStatementList(Element  oXmlNode){
    	String id,extend;
    	Statement extendStatement=null;
    	Statement statement = new Statement();
    	id=getAttributesValue(oXmlNode,"id");
    	if (StringTools.isNullOrEmpty(id)){ 
    		throw new DALException("dal配置文件有误:"+this.key+"."+id+"未在<statement>标记中指明id属性");
    	}
    	//id=id.toLowerCase();
    	statement.setId(id);
//    	//statement设置是否sql过滤属性,默认是过滤的
//    	String filter=getAttributesValue(oXmlNode,"filter");
//    	if(StringTools.isNullOrEmpty(filter)){
//    		statement.setSqlFilter(true);
//    	}else{
//    		if(filter.equals("false")){
//    			statement.setSqlFilter(false);
//    		}else{
//    			statement.setSqlFilter(true);
//    		}
//    	}
    	
    	extend=getAttributesValue(oXmlNode,"extend");
    	if(!StringTools.isNullOrEmpty(extend)){
    		statement.setExetend(extend);
            String[] arr = extend.split("\\.");
            if (arr.length != 3){ 
            	throw new DALException("dal配置文件有误:"+this.key+"继承KEY有误");
            }
            extendStatement = SQLMap.getMap(arr[0] + "." + arr[1]).getStatementList().get(arr[2]);
            String expand=getAttributesValue(oXmlNode, "expand");
            if (!StringTools.isNullOrEmpty(expand))
            {
            	statement.setExpand(expand);
            }
    	}else{
    		extendStatement = null;
    	}
    	statement.setExtendStatement(extendStatement);
    	statementList.put(id,statement);
    	return statement;
    }

    /**
     * 设置select标签内容
     * @param statement
     * @param xmlNode
     */
    private void setSelectTag(Statement statement,Element xmlNode,int nodecount){
        Select get = new Select();
        get.setTagName("select");
        String id = getAttributesValue(xmlNode, "id");
        if(StringTools.isNullOrEmpty(id)){
        	if(nodecount==1){
        		id=statement.getId();
        	}else{
        		id=statement.getId()+idIndex;
        	}
        }
        get.setId(id);
		String afterExecId = getAttributesValue(xmlNode, "afterExecId");
		String beforeExecId = getAttributesValue(xmlNode, "beforeExecId");
		String execAppendSql = getAttributesValue(xmlNode, "execAppendSql");
		String fieldFilter=getAttributesValue(xmlNode, "fieldFilter");//字段过滤器


		get.setAfterExecId(afterExecId);
		get.setBeforeExecId(beforeExecId);
		get.setExecAppendSql(execAppendSql);
		get.setFieldFilter(fieldFilter);
		setExecIds(statement,get);
        //单条记录也是列表形式出现
        String tolist = getAttributesValue(xmlNode, "tolist");
        get.setToList(tolist.equals("true"));
        
        
        
        NodeList xmlNodeList = xmlNode.getElementsByTagName("query");
        if (xmlNodeList.getLength() == 0)
        {
        	String sql=getInnerText(xmlNode);
			if(!StringTools.isNullOrEmpty(execAppendSql)) sql+=execAppendSql;
            get.setSql(sql,this.commonTemplate);
        }
        else
        {
            Map<String, Query> selectDictionary = new HashMap<String, Query>();
            Query select;
            for (int i=0;i<xmlNodeList.getLength();i++)
            {
           	 	Element node=(Element)xmlNodeList.item(i);
                select = new Query();
                id = getAttributesValue(node, "id");
                if (StringTools.isNullOrEmpty(id)) {
                	throw new DALException("dal配置文件有误:"+this.key+"."+statement.getId()+"<select>-><query>未指定id");
                }
                if (selectDictionary.containsKey(id)) continue;
                selectDictionary.put(id, select);
                select.setPrimary(getAttributesValue(node, "primary"));
                select.setForeign( getAttributesValue(node, "foreign"));

                select.setParent( getAttributesValue(node, "parent"));


                String sql=node.getTextContent();
                if(StringTools.isNullOrEmpty(select.getParent())){
					if(!StringTools.isNullOrEmpty(execAppendSql)) sql+=execAppendSql;
				}
				select.setSql(sql,this.commonTemplate);
            }

       	 	Set<Entry<String, Query>> set = selectDictionary.entrySet();  
       	 	int i=0;
            for (Entry<String, Query> entry : set) { 
                if (StringTools.isNullOrEmpty(entry.getValue().getParent())){ 
                	get.setSql(entry.getValue().getSql(),this.commonTemplate);
                	if(id.equals(statement.getId()+idIndex)){
                		get.setId(entry.getKey());
                	}
                	get.setRootID(entry.getKey());
                	i++;
                }else{
                	try {
						if (selectDictionary.get(entry.getValue().getParent()).getChildren() == null)
							selectDictionary.get(entry.getValue().getParent()).setChildren(new ArrayList<String>());
						selectDictionary.get(entry.getValue().getParent()).getChildren().add(entry.getKey());
					}catch (Exception e){
						throw new DALException("dal配置文件有误:"+this.key+"."+statement.getId()+"<select>-><query>父级节点不存在=="+entry.getValue().getParent());
					}
                }
            }
            if(i>1){
            	throw new DALException("dal配置文件有误:"+this.key+"."+statement.getId()+"<select>-><query>只能有一个根节点");
            }
            get.setSelectDirctionary(selectDictionary);
        }

		statement.getSqlList().add(get);

   }
    
    /**
     * 得到 标记中的内容.
     * @param xmlNodes
     * @return
     */
    private String getInnerText(NodeList xmlNodes)
    {
        if (xmlNodes == null) return null;
        if(xmlNodes.getLength()>0){
        	return xmlNodes.item(0).getTextContent();
        }else{
        	return null;
        }
        
    }
    /**
     * 得到 标记中的内容.
     * @param xmlNode
     * @return
     */
    private String getInnerText(Element xmlNode)
    {
        if (xmlNode == null) return null;
        return xmlNode.getTextContent();
    }
    /**
     * 得到节点属性值
     * @param el
     * @param attr
     * @return
     */
    private String getAttributesValue(Element  el,String attr){
        if (el == null) return null;
        String val=el.getAttribute(attr);
        if (val == null) return null;
        return val;
    }
    /**
     * 得到文件最后修改时间
     * @return
     */
    public long getFileTime() {
		return fileTime;
	}
    /**
     * 设置文件最后修改时间
     * @param fileTime
     */
	public void setFileTime(long fileTime) {
		this.fileTime = fileTime;
	}
    /**
     * 文档中的声明列表
     */
    public Map<String, Statement> getStatementList() {
		return statementList;
	}
    /**
     * 文档中的声明列表
     */
	public void setStatementList(Map<String, Statement> statementList) {
		this.statementList = statementList;
	}
//	/**
//	 * sql字符过滤
//	 * @param context
//	 * @param issearch
//	 */
//	private void filterSqlString(Map<String,Object> context,boolean issearch){
//		if(context==null) return;
//		Set<Map.Entry<String, Object>> set = context.entrySet(); 
//		for (Entry<String, Object> entry : set) { 
//			if(entry.getValue() instanceof String){
//				entry.setValue(entry.getValue().toString().replaceAll("'","''"));
//				if(issearch){
//					entry.setValue(entry.getValue().toString().replaceAll("[%]","&#37;").replaceAll("_","＿"));
//				}
//			}
//		} 
//	}

	/**
	 * 自动产生实体
	 * @param dbName
	 * @param packages
	 */
	public static void genEntities(String dbName,String packages) {
		genEntities(dbName,packages,System.getProperty("user.dir"));
	}

	/**
	 * 导出数据库结构
	 * @param dbName
	 */
	public static String exportDBStructure(String dbName) {
		SQLMap sqlMap=getMapByName(dbName);
		return sqlMap.getDbStructure().exportStructure();
	}

    /**
     * 导出数据库所有数据
     * @param dbName
     * @return
     */
    public static String exportDBData(String dbName) {
        SQLMap sqlMap=getMapByName(dbName);
        StringBuffer sqlList = new StringBuffer();
        for(String table:sqlMap.getDbStructure().getTables()){
            List<Map<String,Object>> list=sqlMap.getJdbcTemplate().queryForList("select * from "+table);
            if(list.size()>0) sqlList.append("##插入表"+table+"数据\r\n");
            for(Map<String,Object> obj:list){
                StringBuffer sql = new StringBuffer("insert into " + table + " (");
                boolean flag=false;
                for(Entry<String, Object> entry : ((Map<String,Object>)obj).entrySet()){
                    if(flag) sql.append(",");
                    sql.append(entry.getKey());
                    flag=true;
                }
                sql.append(") VALUES (");
                flag=false;
                for(Entry<String, Object> entry : ((Map<String,Object>)obj).entrySet()){
                    if(flag) sql.append(",");
                    sql.append("'");
                    sql.append(entry.getKey());
                    sql.append("'");
                    flag=true;
                }
                sql.append(");\r\n");
                sqlList.append(sql);
            }
        }
        return sqlList.toString();
    }


	public static void genEntities(String dbName,String packages,String userPath){
		genEntities(dbName,packages,userPath,null);
	}
	/**
	 * 自动产生实体
	 * @param dbName
	 * @param packages
	 * @param userPath
	 */
	public static void genEntities(String dbName,String packages,String userPath,String tmplPath){
//		String dbName="jobmate";
//		String packages="com.netcorner.test.model.entity";

		String path=userPath+"/src/main/java/"+packages.replace(".","/");

		String xmlpath=userPath+"/src/main/resources/mapper/"+dbName;

		SQLMap sqlMap=getMapByName(dbName);

		for(String table:sqlMap.getDbStructure().getTables()){
			List<Field> fields=sqlMap.getDbStructure().getFields().get(table);
			String tableComment=sqlMap.getDbStructure().getTableComments().get(table);
			Map<String,Object> hash=new HashMap<String,Object>();

			String[] ls=table.split("_");
			StringBuilder sb=new StringBuilder();
			for(String s:ls){
				sb.append(s.substring(0,1).toUpperCase());
				sb.append(s.substring(1));
			}
			String className=sb.toString();


			hash.put("package",packages);
			hash.put("DBName",sqlMap.getDbName());
			hash.put("ClassName",className);
			hash.put("Table",table);
			hash.put("TableComment",tableComment+"");
			hash.put("Fields",fields);
			String template;
			if(StringTools.isNullOrEmpty(tmplPath)) {
				template = FileTools.getResFile("/template/Entity.tpl");
			}else{
				template = FileTools.getPathFile(tmplPath);
			}




			VelocityContext vcontext = new VelocityContext();
			vcontext.put("map", hash);
			StringWriter w = new StringWriter();
			VelocityEngine v=new VelocityEngine();
			v.evaluate(vcontext, w, "sqlmap", template);
			//System.out.println(" string : " + w );



			createFile(path+"/"+className+".java",w.toString(),true);


			String tmp=FileTools.getResFile("/template/base.xml");
			createFile(xmlpath+"/base.xml",tmp,false);
			tmp=FileTools.getResFile("/template/table.xml");
			createFile(xmlpath+"/"+table+".xml",tmp,false);


		}
	}

	private static void createFile(String filepath,String template,boolean isReplace){

		File file = new File(filepath);
		if(!file.exists()){
			file.getParentFile().mkdirs();
		}else{
			if(isReplace) file.delete();
			else return;
		}
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(template);
			bw.flush();
			bw.close();
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
