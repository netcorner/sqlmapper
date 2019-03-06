# sqlmapper
<p>
   <strong> &nbsp;&nbsp;&nbsp;&nbsp;可能是比mybatis更好用的数据映射关系框架。</strong>
    
    产生背景：mybatis很强大，但是感觉没那么好用，有很多我想要的功能很难实现，适合自己的才是最好的！
    
    1.传递的参数调用不好使
    mysqbatis的xml就是一个模板，通过传递的参数动态生成组合的sql语句，但语法用起来没那么方便得去查询使用手册，我想用一种我自己习惯的模板语句（我在视图端用的velocity，所以我想着用这个语言直接编写模版）。
    
    2.未写完，待续...
    
    

    
</p>

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
    <span style="white-space:pre"></span>&nbsp; &nbsp; &lt;version&gt;1.1&lt;/version&gt;
</p>
<p>
    <span style="white-space:pre"></span>&lt;/dependency&gt;
</p>
<p>
    <br/>
</p>
