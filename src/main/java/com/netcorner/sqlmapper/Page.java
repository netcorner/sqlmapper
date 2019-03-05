package com.netcorner.sqlmapper;

import java.util.Map;

public class Page  extends CRUDBase {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String table;
	private String where;
    private String columns;
    private String order;
    private String count;
	private Map<String,Query>  children;
	private String primary="ID";
	/**
	 * 得到主键
	 * @return
	 */
    public String getPrimary() {
		return primary;
	}
    /**
     * 设置主键
     * @param primary
     */
	public void setPrimary(String primary) {
		this.primary = primary;
	}
	/**
     * 得到表格语句
     * @return
     */
    public String getTable() {
		return table;
	}
	/**
	 * 设置表格语句
	 * @param table
	 */
	public void setTable(String table) {
		this.table = table;
	}
    /**
     * 得到条件语句
     * @return
     */
	public String getWhere() {
		return where;
	}
	/**
	 * 设置条件语句
	 * @param where
	 */
	public void setWhere(String where) {
		this.where = where;
	}
    /**
     * 得到列语句
     * @return
     */
	public String getColumns() {
		return columns;
	}
	/**
	 * 设置列语句
	 * @param columns
	 */
	public void setColumns(String columns) {
		this.columns = columns;
	}
    /**
     * 得到排序语句
     * @return
     */
	public String getOrder() {
		return order;
	}
	/**
	 * 设置排序语句
	 * @param order
	 */
	public void setOrder(String order) {
		this.order = order;
	}
    /**
     * 得到统计语句
     * @return
     */
	public String getCount() {
		return count;
	}
	/**
	 * 设置统计语句
	 * @param count
	 */
	public void setCount(String count) {
		this.count = count;
	}
    /**
     * 得到子节点集合
     * @return
     */
	public Map<String, Query> getChildren() {
		return children;
	}
	/**
	 * 设置子节点集合
	 * @param children
	 */
	public void setChildren(Map<String, Query> children) {
		this.children = children;
	}
}
