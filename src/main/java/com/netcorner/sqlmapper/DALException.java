package com.netcorner.sqlmapper;

import com.netcorner.sqlmapper.utils.ClientTools;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Properties;


public  class DALException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(DALException.class); 
	
	
	public DALException(String message){
		super(message);
		String ip="";
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			if (request != null) {
				ip = "[" + ClientTools.getRemoteIpAddr(request) + "][" + request.getHeader("User-Agent") + "] - [" + request.getHeader("Referer") + "]";
			} else {
				Properties props = System.getProperties();
				ip = "[" + ClientTools.getHostIpAddr() + "] [os:" + props.getProperty("os.name") + "," + props.getProperty("os.version") + " jdk:" + props.getProperty("java.version") + "]";
			}

			logger.error(ip + " - " + message);
		}catch (Exception e){
			logger.error(message);
		}
	}


}
