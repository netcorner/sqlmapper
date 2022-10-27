package com.netcorner.sqlmapper;

import java.io.Serializable;

public class CRUDBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String afterExecId;
	private String beforeExecId;
	private String execAppendSql;
	private boolean filter;
	/**
	 * 得到id
	 * @return
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置id
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	private String tagName;
	/**
	 * 得到标签
	 * @return
	 */
	public String getTagName() {
		return tagName;
	}
	/**
	 * 设置标签
	 * @param tagName
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	private String sql;
	/**
	 * sql
	 * @return
	 */
	public String getSql() {
		return sql;
	}
	/**
	 * sql
	 * @param sql
	 */
	 public void setSql(String sql,String template) {
        if (!template.equals(""))
        {
            sql = template + "\r\n" + sql;
        }
		this.sql = sql;
	}

	public boolean isFilter() {
		return filter;
	}

	public void setFilter(boolean filter) {
		this.filter = filter;
	}

	/**
	 * 得到语句体执行后跳到对应的 statmentid 执行体中去执行
	 * @return
	 */
	public String getAfterExecId() {
		return afterExecId;
	}

	public void setAfterExecId(String afterExecId) {
		this.afterExecId = afterExecId;
	}

	/**
	 * 得到语句体执行前跳到对应的 statmentid 执行体中去执行
	 * @return
	 */
	public String getBeforeExecId() {
		return beforeExecId;
	}

	public void setBeforeExecId(String beforeExecId) {
		this.beforeExecId = beforeExecId;
	}

	/**
	 * 得到语句体执行时拦截 对sql 进行拼接生成新的 sql
	 * @return
	 */
	public String getExecAppendSql() {
		return execAppendSql;
	}

	public void setExecAppendSql(String execAppendSql) {
		this.execAppendSql = execAppendSql;
	}
}
