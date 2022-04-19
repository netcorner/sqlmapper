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
    
 ### 4.select标签中可支持多集合查询数据实现一次性返回，且返回结果已经生成好树型结构
 
    <select>标签中的 innerText中可写sql语句返回一个集合的记录集，同时可使用<query>标签方式支持多个结果集的返回。
    以下方式可以获取多集录集
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
    如：{userid:1,username:'sjf',nickname:'netcorner',message:[{messageid:1,title:'完善信息',userid:1},{messageid:2,title:'更新信息',userid:1} ...]}


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


 ### 9.为了更简单些，默认是去掉了数据表的映射自建实体，以HashMap为加载对象。
    小项目直接去了实体层可能开发会快速简单一点。
    

 ### 10.支持自建或自动生成表格映射实体。
    用以下语句自动生成实体
    SQLMap.enEntities("datasource","com.netcorner.ssx.model.entity",System.getProperty("user.dir")+"/api");
    自建实体请继承：com.netcorner.sqlmapper.entity.Entity
    @Table注解表示映射哪个库的哪个表如：@Table("datasource.user") 会映射于datasource中的user表






    


<p>
    <strong><span style="white-space:pre"></span>Step 1. Add the JitPack repository to your build file</strong>
</p>
<p>
    &lt;repositories&gt;
</p>
<p>
    <span style="white-space:pre"></span>&lt;repository&gt;
</p>
<p>
    <span style="white-space:pre"></span>&nbsp; &nbsp; &lt;id&gt;jitpack.io&lt;/id&gt;
</p>
<p>
    <span style="white-space:pre"></span>&nbsp; &nbsp; &lt;url&gt;https://jitpack.io&lt;/url&gt;
</p>
<p>
    <span style="white-space:pre"></span>&lt;/repository&gt;
</p>
<p>
    <span style="white-space:pre"></span>&lt;/repositories&gt;
</p>
<p>
    <strong>Step 2. Add the dependency</strong>
</p>
<p>
    &nbsp; &lt;dependency&gt;
</p>
<p>
    <span style="white-space:pre"></span>&nbsp; &nbsp; &lt;groupId&gt;com.github.netcorner&lt;/groupId&gt;
</p>
<p>
    <span style="white-space:pre"></span>&nbsp; &nbsp; &lt;artifactId&gt;sqlmapper&lt;/artifactId&gt;
</p>
<p>
    <span style="white-space:pre"></span>&nbsp; &nbsp; &lt;version&gt;2.3.0&lt;/version&gt;
</p>
<p>
    <span style="white-space:pre"></span>&lt;/dependency&gt;
</p>
<p>
    <br/>
</p>
