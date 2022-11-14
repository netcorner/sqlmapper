package com.netcorner.demo;

import com.alibaba.druid.pool.DruidDataSource;
import com.netcorner.demo.entity.User;
import com.netcorner.sqlmapper.*;
import com.netcorner.sqlmapper.entity.Entity;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class Test {
    public static void main(String[] args)  {
		Test testMain=new Test();
		DruidDataSource db=new DruidDataSource();
		db.setDriverClassName("com.mysql.jdbc.Driver");
		db.setUsername("root");
		db.setPassword("sjf2008");
		db.setUrl("jdbc:mysql://localhost:3306/test?autoReconnect=true&failOverReadOnly=false&maxReconnects=10&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull");
		JdbcTemplate jdbcTemplate=new JdbcTemplate(db);
		SQLMap.setJdbcTemplates("jobmate",jdbcTemplate);


		//System.out.println(SQLMap.exportDBStructure("jobmate"));


        //System.out.println(SQLMap.exportDBData("jobmate"));

		//TestExecAppendSql();

//
//		db=new DruidDataSource();
//		db.setDriverClassName("com.mysql.jdbc.Driver");
//		db.setUsername("root");
//		db.setPassword("sjf2008");
//		db.setUrl("jdbc:mysql://localhost:3306/test1?autoReconnect=true&failOverReadOnly=false&maxReconnects=10&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull");
//		jdbcTemplate=new JdbcTemplate(db);
//		SQLMap.setJdbcTemplates("Jobmate1",jdbcTemplate);
//
//
//
//
//
//
//		TestEntity();

		//testMain.testAll();



//		Map<String,Object> hash=new HashMap<String,Object>();
//		hash.put("time",LocalDateTime.now());
//		System.out.print(Entity.toJson(hash));


		//TestAfterExecId();
		//TestBeforeExecId();
		//TestConfig();
		//TestAfterExecId5();
		//fieldFilterTest();
		utilsTest();

    }

	/**
	 * 模板中的模板应用
	 */
	public static void utilsTest(){
		Map<String,Object> properties=new HashMap<String,Object>();
		properties.put("str","$map.field1='$map.value'");
		properties.put("field1",1);
		properties.put("value",2);
		SQLMap map=SQLMap.getMap("jobmate.a");
		System.out.println(map.execute("utils", properties));
	}

    public static void fieldFilterTest(){
		TestFieldFilter testFieldFilter=new TestFieldFilter();
		Map<String,Object> properties=new HashMap<String,Object>();
		properties.put("id",1);
		SQLMap map=SQLMap.getMap("jobmate.a");
		System.out.println(map.execute("fieldFilter", properties));
	}


    public void testAll(){

//
//    	for(int i=0;i<100;i++) {
//			try {
//
//
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}






		try {
			TestMutilSql();
			Thread.sleep(11000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			TestMutilSql();
			Thread.sleep(11000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		TestMutilSql();
		TestInsert();
		TestMulti();
		TestFun();
		TestUseStatementID();
		TestSelect();
		TestPage1();
		TestPage2();
		TestPage3();
		TestPage4();
		TestPage5();
		TestMerge();
		TestExt();

		TestExtMore();
		TestEntity();
	}




	public static void TestConfig(){

		Map<String,Object> properties=new HashMap<String,Object>();
		SQLMap map=SQLMap.getMap("jobmate.a");
		System.out.println(map.execute("ConfigTest", properties));

	}

	public static void TestAfterExecId(){

		Map<String,Object> properties=new HashMap<String,Object>();
		SQLMap map=SQLMap.getMap("jobmate.a");
		System.out.println(map.execute("AfterExecId4", properties));

	}

	public static void TestAfterExecId5(){

		Map<String,Object> properties=new HashMap<String,Object>();
		SQLMap map=SQLMap.getMap("jobmate.a");
		System.out.println(map.execute("AfterExecId5", properties));

	}

	public static void TestAfterExecId4(){

		Map<String,Object> properties=new HashMap<String,Object>();
		SQLMap map=SQLMap.getMap("jobmate.a");
		System.out.println(map.execute("AfterExecId3", properties));

	}


	public static void TestExecAppendSql(){

		Map<String,Object> properties=new HashMap<String,Object>();
		SQLMap map=SQLMap.getMap("jobmate.a");
		System.out.println(map.execute("AfterExecId", properties));

	}

	public static void TestAfterExecId3(){

		Map<String,Object> properties=new HashMap<String,Object>();
		SQLMap map=SQLMap.getMap("jobmate.a");
		System.out.println(map.execute("AfterExecId2", properties));

	}

	public static void TestBeforeExecId(){

		Map<String,Object> properties=new HashMap<String,Object>();
		SQLMap map=SQLMap.getMap("jobmate.a");
		System.out.println(map.execute("BeforeExecId", properties));

	}

	public static void TestAfterExecId2(){

		Map<String,Object> properties=new HashMap<String,Object>();
		SQLMap map=SQLMap.getMap("jobmate.a");
		System.out.println(map.execute("AfterExecId", properties));

	}


	public static void TestFun2(){

		Map<String,Object> properties=new HashMap<String,Object>();
		SQLMap map=SQLMap.getMap("jobmate.a");
		System.out.println(map.execute("fun2", properties));

	}

	/**
	 * 生成实体
	 */
	public static void TestGen(){

		SQLMap.genEntities("jobmate","com.netcorner.test.model.entity");
	}





	public static void TestEntity(){



//		B tee=new B();
//		//tee.setid(1111);
//		tee.seta("'asdf'sdaf");
//		tee.insert();
		//tee.get(BExt.class);
		//System.out.println(tee.getChildren().size());



//		List list=DBTools.pageData("jobmate.b._page",new QueryPage(),"a");
//		System.out.println(list.size());



//		BExt tee=new BExt();
//
//		QueryPage qp=new QueryPage();
//		qp.setSize(10);
//		List<BExt> list=tee.page(qp,"a",BExt.class);
//
//		System.out.println(list.size());


//
//		TestEntity tee=new TestEntity();
//
//		QueryPage qp=new QueryPage();
//		int[] showPage = { 15 };
//		qp.setShowPage(showPage);
//
//
//		System.out.println(tee.page(qp));





//		List<B> b=tee.insert("111",new HashMap<String,Object>(),B.class);
//		System.out.println(b.size());






//		TestEntity tee=new TestEntity();
//		tee.setA("xxx1");
//		tee.setC(1);
//		tee.insert();

//		TestEntity tee=new TestEntity();
//		tee.setA("yxxx111");
//		tee.setC(1117);
//		tee.setId(1);
//		tee.update();


//		TestEntity tee=new TestEntity();
//		tee.setId(2800);
//		tee.delete();


//		TestEntity tee=new TestEntity();
//		tee.get(1);
//		System.out.println(tee.getC());
//		tee.insert();


//				TestEntity tee=new TestEntity();
//				tee.setC(1117);
//		tee.get();
//		System.out.println(tee.getC());


//		TestEntity tee=new TestEntity();
//		tee.setA("kkk");
//		System.out.println(tee.getList().size());

//			TestEntity tee=new TestEntity();
//		tee.setA("kkk");
//
//		QueryPage qp=new QueryPage();
//		int[] showPage = { 15 };
//		qp.setShowPage(showPage);
//		tee.getPageList(qp);
//
//		System.out.println(qp.getTotal());


	}

	public static void TestMutilSql(){
		SQLMap map=SQLMap.getMap("jobmate.b");
		HashMap<String,Object> obj=new HashMap<String,Object>();
		System.out.println(map.execute("mutile1", obj));
	}


	public static void TestVelocity(){
      VelocityContext vcontext = new VelocityContext();
      vcontext.put("map", "aaa");
      StringWriter w = new StringWriter(); 
      VelocityEngine v=new VelocityEngine();
      v.evaluate(vcontext, w, "sqlmap", "\\#set($tmp=\\$map)");  
      System.out.println(" string : " + w );
	}
    
    public static void TestFun(){
    	Map<String,Object> properties=new HashMap<String,Object>();
    	SQLMap map=SQLMap.getMap("jobmate.b");
    	System.out.println(map.execute("fun", properties));
    }
	/**
	 * 测试<ext>标签用途,支持多数据源事务级
	 */
	public static void TestExtMore(){
		Map<String,Object> properties=new HashMap<String,Object>();

		Map<String,Object> hash=new HashMap<String,Object>();
		hash.put("a", "1");
		properties.put("b", hash);

		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		hash=new HashMap<String,Object>();
		hash.put("a", "123");
		hash.put("b", "1212");
		list.add(hash);
		hash=new HashMap<String,Object>();
		hash.put("a", "lklk");
		hash.put("b", "11111111111111222222222221111111111");
		list.add(hash);
		properties.put("a", list);

		SQLMap map=SQLMap.getMap("jobmate.b");
		System.out.println(map.execute("extMore", properties));
	}

	/**
	 * 测试<ext>标签用途,单数据源事务级
	 */
	public static void TestExt(){
    	Map<String,Object> properties=new HashMap<String,Object>();
    	
    	Map<String,Object> hash=new HashMap<String,Object>();
    	hash.put("a", "1");
    	properties.put("b", hash);
    	
    	List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
    	hash=new HashMap<String,Object>();
    	hash.put("a", "aaa423a");
    	hash.put("b", "cba");
    	list.add(hash);
    	hash=new HashMap<String,Object>();
    	hash.put("a", "lklk");
    	hash.put("b", "8989");
    	list.add(hash);
    	properties.put("a", list);
    	
    	SQLMap map=SQLMap.getMap("jobmate.b");
    	System.out.println(map.execute("ext", properties));
	}
	/**
	 * 测试合并功能
	 */
	public static void TestMerge(){
		SQLMap map=SQLMap.getMap("jobmate.b");
    	Map<String,Object> properties=new HashMap<String,Object>();
    	properties.put("a", "abc");
		System.out.println(map.execute("add", properties));
	}
	/**
	 * 多条数据插入功能
	 */
	public static void TestInsert(){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
    	Map<String,Object> properties=new HashMap<String,Object>();
    	
    	Map<String,Object> hash=new HashMap<String,Object>();
    	hash.put("a", "abc");
    	list.add(hash);
    	hash=new HashMap<String,Object>();
    	hash.put("a", "abcx");
    	list.add(hash);
    	properties.put("list", list);
    	
    	SQLMap map=SQLMap.getMap("jobmate.b");
    	System.out.println(map.execute("insert", properties));
	}
	/**
	 * 多条语句同时执行（事务级的）
	 */
	public static  void TestMulti(){
		SQLMap map=SQLMap.getMap("jobmate.b");
    	Map<String,Object> properties=new HashMap<String,Object>();
		System.out.println(map.execute("multi", properties));
	}
	/**
	 * statement的子标签的id作用
	 */
	public static void TestUseStatementID(){
		SQLMap map=SQLMap.getMap("jobmate.b");
    	Map<String,Object> properties=new HashMap<String,Object>();




		System.out.println(map.execute("ids", properties));
	}
	/**
	 * select的使用，可得到子集合
	 */
	public static void TestSelect(){
		SQLMap map=SQLMap.getMap("jobmate.b");
    	Map<String,Object> properties=new HashMap<String,Object>();
		System.out.println(map.execute("select", properties));
	}

	public static  void TestPage1(){
    	QueryPage qpage=new QueryPage();
    	qpage.setCurrent(2);
    	qpage.setSize(2);
    	SQLMap map=SQLMap.getMap("jobmate.b");
    	System.out.println(map.executeForList("simplepage1", qpage));
    	System.out.println(qpage.getTotal());
	}
	public static void TestPage2(){
    	QueryPage qpage=new QueryPage();
    	qpage.setCurrent(2);
    	qpage.setSize(2);
    	SQLMap map=SQLMap.getMap("jobmate.b");
    	System.out.println(map.executeForList("simplepage2", qpage));
    	System.out.println(qpage.getTotal());
	}
	public static void TestPage3(){
    	QueryPage qpage=new QueryPage();
    	qpage.setCurrent(2);
    	qpage.setSize(2);
    	SQLMap map=SQLMap.getMap("jobmate.b");
    	System.out.println(map.executeForList("simplepage3", qpage));
    	System.out.println(qpage.getCountResult());
	}
	public static void TestPage4(){
    	QueryPage qpage=new QueryPage();
    	qpage.setCurrent(2);
    	qpage.setSize(2);
    	SQLMap map=SQLMap.getMap("jobmate.b");
    	System.out.println(map.executeForList("simplepage4", qpage));
    	System.out.println(qpage.getCountResult());
	}
	public static void TestPage5(){
    	QueryPage qpage=new QueryPage();
    	qpage.setCurrent(2);
    	qpage.setSize(2);
    	SQLMap map=SQLMap.getMap("jobmate.b");
    	System.out.println(map.executeForList("simplepage5", qpage));
    	System.out.println(qpage.getTotal());
	}
}
