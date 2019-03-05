package com.netcorner.sqlmapper;

public class Ext extends CRUDBase {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;
	 /**
	  * 取传递参数hashmap的key对应的object
	  * @return
	  */
	public String getKey() {
		return key;
	}
	/**
	 * 设置传递参数hashmap的key对应的object
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}
}
