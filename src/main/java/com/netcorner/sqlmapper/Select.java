package com.netcorner.sqlmapper;

import java.util.Map;

public class Select  extends CRUDBase {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String,Query> selectDirctionary;
	/**
	 * 得到相关查询语句字典
	 * @return
	 */
	public Map<String, Query> getSelectDirctionary() {
		return selectDirctionary;
	}
	/**
	 * 相关查询语句字典
	 * @param selectDirctionary
	 */
	public void setSelectDirctionary(Map<String, Query> selectDirctionary) {
		this.selectDirctionary = selectDirctionary;
	}
	private String rootID;
	public String getRootID() {
		return rootID;
	}
	public void setRootID(String rootID) {
		this.rootID = rootID;
	}
	
	private boolean isToList=false;
	/**
	 * 设置select返回值只有一个记录也为list类型
	 * @return
	 */
	public boolean isToList() {
		return isToList;
	}
	public void setToList(boolean isToList) {
		this.isToList = isToList;
	}


}
