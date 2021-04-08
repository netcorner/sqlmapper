package com.netcorner.sqlmapper;

import java.io.Serializable;

/**
 * 数据库字段字典
 * @author Stephen
 *
 */
public class Field implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String name;
    private String type;
    private String comment;
    private boolean isPrimary;
    private int len;
    private boolean isNullable;
    private boolean isIdentity;
    private boolean isForiegn;
    private boolean isClusterKey;
    /**
     * ID
     * @return
     */
	public int getId() {
		return id;
	}
	/**
	 * ID
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 字段名称
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * 字段名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 字段类型
	 * @return
	 */
	public String getType() {
		return type;
	}
	/**
	 * 字段类型
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 是否是主键
	 * @return
	 */
	public boolean getIsPrimary() {
		return isPrimary;
	}
	/**
	 * 是否是主键
	 * @param isPrimary
	 */
	public void setIsPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}
	/**
	 * 字段长度
	 * @return
	 */
	public int getLen() {
		return len;
	}
	/**
	 * 字段长度
	 * @param len
	 */
	public void setLen(int len) {
		this.len = len;
	}
	/**
	 * 是否充许为空
	 * @return
	 */
	public boolean getIsNullable() {
		return isNullable;
	}
	/**
	 * 是否充许为空
	 * @param isNullable
	 */
	public void setIsNullable(boolean isNullable) {
		this.isNullable = isNullable;
	}
	/**
	 * 是否是唯一
	 * @return
	 */
	public boolean getIsIdentity() {
		return isIdentity;
	}
	/**
	 * 是否是唯一
	 * @param isIdentity
	 */
	public void setIsIdentity(boolean isIdentity) {
		this.isIdentity = isIdentity;
	}
	/**
	 * 是否是外键
	 * @return
	 */
	public boolean getIsForiegn() {
		return isForiegn;
	}
	/**
	 * 是否是外键
	 * @param isForiegn
	 */
	public void setIsForiegn(boolean isForiegn) {
		this.isForiegn = isForiegn;
	}
	/**
	 * 是否充许聚集
	 * @return
	 */
	public boolean getIsClusterKey() {
		return isClusterKey;
	}
	/**
	 * 是否充许聚集
	 * @param isClusterKey
	 */
	public void setIsClusterKey(boolean isClusterKey) {
		this.isClusterKey = isClusterKey;
	}

	/**
	 * 得到字段注释
	 * @return
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * 设置字段注释
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
}
