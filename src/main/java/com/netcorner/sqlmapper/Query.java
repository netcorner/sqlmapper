package com.netcorner.sqlmapper;

import java.io.Serializable;
import java.util.List;


public class Query implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String primary;
	private String foreign;
    private String parent;
    
    private List<String> children;
    /**
     * 主键名称
     * @return
     */
    public String getPrimary() {
		return primary;
	}
    /**
     * 主键名称
     * @param primary
     */
	public void setPrimary(String primary) {
		this.primary = primary;
	}
	/**
	 * 外键名称
	 * @return
	 */
	public String getForeign() {
		return foreign;
	}
	/**
	 * 外键名称
	 * @param foreign
	 */
	public void setForeign(String foreign) {
		this.foreign = foreign;
	}
	/**
	 * 上级的ID名称
	 * @return
	 */
	public String getParent() {
		return parent;
	}
	/**
	 * 上级的ID名称
	 * @param parent
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	/**
	 * 得到子节点名
	 * @return
	 */
	public List<String> getChildren() {
		return children;
	}
	/**
	 * 设置得到子节点名
	 * @param children
	 */
	public void setChildren(List<String> children) {
		this.children = children;
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
}
