package com.netcorner.sqlmapper;

import java.io.Serializable;

public class CRUDBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
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
}
