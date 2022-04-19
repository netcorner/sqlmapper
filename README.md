# sqlmapper
**可能是比mybatis更好用的数据映射关系框架。**
## 产生背景：mybatis很强大，但是感觉没那么好用，有很多我想要的功能很难实现，适合自己的才是最好的！ **
 
 ### 1.传递的参数调用不好使 
 
    mysqbatis的xml就是一个模板，通过传递的参数动态生成组合的sql语句，但语法用起来没那么方便得去查询使用手册，
    我想用一种我自己习惯的模板语句（我在视图端用的velocity，所以我想着用这个语言直接编写模版）。
    
 ### 2.为了更简单些，默认是去掉了数据表的映射自建实体，以HashMap为加载对象。
    小项目直接去了实体层可能开发会快速简单一点。
 ### 3.支持自建或自动生成表格映射实体。
    用以下语句自动生成实体
    SQLMap.genEntities("datasource","com.netcorner.ssx.model.entity",System.getProperty("user.dir")+"/api");
    自建实体请继承：com.netcorner.sqlmapper.entity.Entity
    @Table注解表示映射哪个库的哪个表如：@Table("datasource.user") 会映射于datasource中的user表
    
 ### 4.对于分页数据使用了特定标签page实现，免去插件或写复杂的分页的sql语句
 ### 5.配置xml支持继承上级xml，免去写一些重复的语句
 ### 6.对于一些通用可共享的sql,通过 function 标签加上velocity  macro（宏）模块实现页面级共享调用
 ### 7.在一个statement中可实现事务级crud操作
 ### 8.集成一些通用的函数，如#Insert() #Update() #Delete() #Where() 的共享函数调用及$table表格名称变量，减少了代码量
   

    


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
