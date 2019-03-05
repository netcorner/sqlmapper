package com.netcorner.sqlmapper;

import com.netcorner.sqlmapper.utils.StringTools;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;




 
public class DataSourceConfig {

	private static ResourceBundle bundle=PropertyResourceBundle.getBundle("config");
	
	private static String driverClassName;
	private static String url;
	private static String username;
	private static String password;
	private static int initialSize=-1;
	private static int maxActive=-1;
	private static int maxIdle=-1;
	private static int minIdle=-1;
	private static int maxWait=-1;
	private static boolean logAbandoned;
	private static boolean removeAbandoned;
	private static int removeAbandonedTimeout=-1;
	
	public static String getDriverClassName() {
		if(StringTools.isNullOrEmpty(driverClassName)){
			driverClassName=bundle.getString("driverClassName");
		}
		return driverClassName;
	}
	public static void setDriverClassName(String driverClassName) {
		DataSourceConfig.driverClassName = driverClassName;
	}
	public static String getUrl() {
		if(StringTools.isNullOrEmpty(url)){
			url=bundle.getString("url");
		}
		return url;
	}
	public static void setUrl(String url) {
		DataSourceConfig.url = url;
	}
	public static String getUsername() {
		if(StringTools.isNullOrEmpty(username)){
			username=bundle.getString("username");
		}
		return username;
	}
	public static void setUsername(String username) {
		DataSourceConfig.username = username;
	}
	public static String getPassword() {
		if(StringTools.isNullOrEmpty(password)){
			password=bundle.getString("password");
		}
		return password;
	}
	public static void setPassword(String password) {
		DataSourceConfig.password = password;
	}
	public static int getInitialSize() {
		if(initialSize==-1){
			//password=Integer.parseInt(bundle.getString("initialSize"));
		}
		return initialSize;
	}
	public static void setInitialSize(int initialSize) {
		DataSourceConfig.initialSize = initialSize;
	}
	public static int getMaxActive() {
		return maxActive;
	}
	public static void setMaxActive(int maxActive) {
		DataSourceConfig.maxActive = maxActive;
	}
	public static int getMaxIdle() {
		return maxIdle;
	}
	public static void setMaxIdle(int maxIdle) {
		DataSourceConfig.maxIdle = maxIdle;
	}
	public static int getMinIdle() {
		return minIdle;
	}
	public static void setMinIdle(int minIdle) {
		DataSourceConfig.minIdle = minIdle;
	}
	public static int getMaxWait() {
		return maxWait;
	}
	public static void setMaxWait(int maxWait) {
		DataSourceConfig.maxWait = maxWait;
	}
	public static boolean isLogAbandoned() {
		return logAbandoned;
	}
	public static void setLogAbandoned(boolean logAbandoned) {
		DataSourceConfig.logAbandoned = logAbandoned;
	}
	public static boolean isRemoveAbandoned() {
		return removeAbandoned;
	}
	public static void setRemoveAbandoned(boolean removeAbandoned) {
		DataSourceConfig.removeAbandoned = removeAbandoned;
	}
	public static int getRemoveAbandonedTimeout() {
		return removeAbandonedTimeout;
	}
	public static void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
		DataSourceConfig.removeAbandonedTimeout = removeAbandonedTimeout;
	}
}
