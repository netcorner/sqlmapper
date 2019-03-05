package com.netcorner.sqlmapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Statement implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
    private String exetend;
    private Statement extendStatement;
    private List<CRUDBase> sqlList;
    private String expand;
//    private boolean isSqlFilter;
//    /**
//     * sql字符是否过滤
//     * @return
//     */
//    public boolean isSqlFilter() {
//		return isSqlFilter;
//	}
//    /**
//     * sql字符是否过滤
//     * @param isSqlFilter
//     */
//	public void setSqlFilter(boolean isSqlFilter) {
//		this.isSqlFilter = isSqlFilter;
//	}
	/**
     * 得到扩展方式front(扩展到继承内容的),behind(扩展到继承内容的后面),空值将用父级的取代继承的
     * @return
     */
    public String getExpand() {
		return expand;
	}
    /**
     * 设置扩展方式
     * @param expand
     */
	public void setExpand(String expand) {
		this.expand = expand;
	}
    /**
     * 得到sql列表，key值为select(是个list),get(单条),update(更新),delete(删除),insert(插入),page(分页),ext(外部调用),config(执行前的配置设置)
     * @return
     */
    public List<CRUDBase> getSqlList() {
		if(sqlList==null){
			sqlList=new ArrayList<CRUDBase>();
		}
    	return sqlList;
	}
    /**
     * 设置sql列表
     * @param sqlList
     */
	public void setSqlList(List<CRUDBase> sqlList) {
		this.sqlList = sqlList;
	}
    /**
     * 继承声明
     * @return
     */
    public Statement getExtendStatement() {
		return extendStatement;
	}
    /**
     * 继承声明
     * @param extendStatement
     */
	public void setExtendStatement(Statement extendStatement) {
		this.extendStatement = extendStatement;
	}

	/**
     * id
     * @return
     */
    public String getId() {
		return id;
	}
    /**
     * id
     * @param id
     */
	public void setId(String id) {
		this.id = id;
	}
    /**
     * 继承id名
     * @return
     */
	public String getExetend() {
		return exetend;
	}
	/**
	 * 继承id名
	 * @param exetend
	 */
	public void setExetend(String exetend) {
		this.exetend = exetend;
	}
}
