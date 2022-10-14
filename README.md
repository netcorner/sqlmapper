# sqlmapper
**可能是比mybatis更好用的数据映射关系框架。**
## 产生背景：mybatis很强大，但是对我而言没那么好用，有很多我想要的功能很难实现，适合自己的才是最好的！所以码一个吧...
 
 ### 1.使用前端+后端一致的模板，降低学习成本
 
    mybatis的xml就是一个模板，通过传递的参数动态生成组合的sql语句。但语法用起来没那么方便，还得去查询使用手册再学习。
    假如你视图velocity引擎，那么后面 sql 处理引擎为啥不用这个呢？
    虽说现在都前后端分离了，但通过服务器端渲染的页面也不是一无事处，可能有地方需要用到的。如生成页面，生成代码总还需要这个模板引擎，所以我统一使用一种模板引擎，这样可降低学习成本。
    
 ### 2.使用xml方式配置，定义了少量的标签
    dtd可以查看https://raw.githubusercontent.com/netcorner/sqlmapper/master/files/sqlMap.dtd
    <sqlMap>标签， root标签,一个 xml配置文件开始节点
    <statement>标签，每个声明体开始的标签，需定义id属性，该属性必填且不能在一个页面中重复出现；
    statement里面可以多个 crud 操作(事务级)，自顶向下执行statement中的语句集，每个crud的语句集id用来返回执行结果，
    其中insert，update，delete会返回执行影响的条数
    select返回结果集，默认 select 是一条记录集，如要返回多条记录集在 select标签中加入 tolist="true"
    <function>共享函数标签，页面中所有标签都可以调用，避免重复代码，innerText 中可以使用 velocity 模板的macro定义函数
    以下是crud操作标签
    <insert>标签的innerText代表insert sql语句
    <update>标签的innerText代表update sql语句
    <delete>标签的innerText代表delete sql语句
    <select>标签的innerText代表select sql语句
    <page>标签里面有<select>筛选字段，<from>来自哪个表格，<where>条件筛选，<order>字段排序，处理需要有分页的读取操作
    
 ### 3.对于分页数据使用了特定标签page实现，免去插件或写复杂的分页的sql语句
    直接只有一个<page>标签，会自动产生分页
    也可以在 page 标签下面有以下标签，但这些标签也不是必须的，不写会自动生成
    <select>筛选字段
    <from>来自哪个表格
    <where>条件筛选
    <order>字段排序
    具体分页的一些参数可通过com.netcorner.sqlmapper.QueryPage或com.netcorner.sqlmapper.WebQueryPage 传入
    
 ### 4.select标签可支持返回多集合查询数据，且返回结果已经生成好树型结构
 
    <select>标签中的 innerText中可写sql语句返回一个集合的记录集，若可使用<query>标签可支持多个结果集的返回。
    以下方式可以获取多集录集，设定好主键（primary）及外键(foreign),可自动归整成一个树状集合
    <statement id="select">
        <select id="res">
            <query id="table" primary="userid">
                select userid,username,nickname from user where id=1
            </query>
            <query id="message" parent="table" foreign="userid">
                select messageid,title,userid from user where userid=1
            </query>
        </select>
    </statement>
    返回结果集是一个树结构的结果级
    如：{userid:1,username:'sjf',nickname:'netcorner',message:[{messageid:1,title:'完善信息',userid:1},
    {messageid:2,title:'更新信息',userid:1} ...]}


 ### 5.function标签，实现页面级代码共享调用
    函数形式是velocity  macro（宏）实现页面级共享调用
    <function>
        ##得到最大 id
        #macro(getMaxID)
            select max(id) id from $table
        #end
    </function>
    <statement id="fundemo1">
        <select>
            #getMaxID()
        </select>
    </statement>
     <statement id="fundemo2">
        <select>
            #getMaxID()
        </select>
       </statement>
    以上实现了函数调用功能，可省去重复代码
    
    
 ### 6.集成一些通用的函数，减少了代码量（数据库字段变更后可以不用改任何代码实现）
     #Insert() 是表的插入insert语句，空值不会插入
     #Update() 是表的更新update语句，空值不会更新
     #Delete()是表的删除delete语句
     #Where() 是表的条件 where 后面的语句，有值就会产生条件
     $table 是表格名
     
     
     
 ### 7.在一个statement中可有多个 insert,update,delete,select 标签，并可实现事务级crud操作（出错时会回滚）
    <statement id="mutile1">
        <insert>
            insert into a(a,b)
            values('777','1')
        </insert>
        <select>
            select 1 status
        </select>
        <insert>
            insert into a(a,b)
            values('1233333333333333333333333333333333','1')
        </insert>
        <insert>
            insert into a(a,b)
            values('777','1')
        </insert>
    </statement>
    以上操作有有一个出错就会回滚
    
 ### 8.支持继承上级配置xml，免去跨页面级的重复的语句重写的烦恼（单继承，多重继承==）
    例：
    <sqlMap  merge="datasource.base">
    merge属性用于继承datasource 下面的 base.xml 所有 statement,function
    通过以上方式实现跨页面级的代码利用
    

### 9.一个insert,update,delete 进行数据更改操作中可以包含多条操作语句。
    通过{}形式进行拆分，以下执行了两次 insert 语句
    
     <statement id="mutile1">
            <insert>
                {insert into a(a,b)
                values('777','1')}
                {insert into a(a,b)
                                values('777','1')}
            </insert>
        </statement>

### 10.封装了DBTools工具用于操作CRUD
    包含的key是一个字符串，如 datasource.user.add 表示 datasource 数据源的 user 表的 add 声明块
   DBTools.getData 获取单条记录，多个声明体中包含insert、update、delete 不是事务级的、不会回滚
   DBTools.selectData 获取多条记录，多个声明体中包含insert、update、delete 不是事务级的、不会回滚
   DBTools.pageData 获取多条记录，多个声明体中包含insert、update、delete 不是事务级的、不会回滚
   DBTools.insertData 插入记录，多个声明体中包含insert、update、delete 是事务级的、会回滚
   DBTools.updateData 插入记录，多个声明体中包含insert、update、delete 是事务级的、会回滚
   DBTools.deleteData 插入记录，多个声明体中包含insert、update、delete 是事务级的、会回滚
   DBTools.execute 执行数据操作，返回声明体的最后一个执行体的状态、记录集或记录，多个声明体中包含insert、update、delete 是事务级的、会回滚
   DBTools.executeByMap 执行数据操作，返回声明体的最后一个执行体记录，多个声明体中包含insert、update、delete 是事务级的、会回滚

### 11.传入的参入在模板中以$map开头输出
    如：$map.username map是参数对象 username 对象属性

### 12.为了更简单些，默认是去掉了数据表的映射自建实体，以HashMap为加载对象。
    小项目直接去了实体层可能开发会快速简单一点。
    

 ### 13.支持自建或自动生成表格映射实体。
    用以下语句自动生成实体
    SQLMap.genEntities("datasource","com.netcorner.ssx.model.entity",System.getProperty("user.dir")+"/api");
    自建实体请继承：com.netcorner.sqlmapper.entity.Entity
    @Table注解表示映射哪个库的哪个表如：@Table("datasource.user") 会映射于datasource中的user表






 ## Step 1. Add the JitPack repository to your build file
    <repositories>
        <repository>
          <id>jitpack.io</id>
          <url>https://jitpack.io</url>
        </repository>
    </repositories>
    

## Step 2. Add the dependency
      <dependency>
          <groupId>com.github.netcorner</groupId>
          <artifactId>sqlmapper</artifactId>
          <version>2.4.1</version>
      </dependency>

## Step 3. Hello World!
       initDatasource();
       HashMap<String,Object> params=new HashMap<String,Object>();
       params.put("userid",1);
       System.out.println(DBTools.getData("datasource.user.hello", params));