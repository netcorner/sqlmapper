package com.netcorner.sqlmapper.utils;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Spring IOC上下文工具类
 * 
 * @author Stephen
 * 
 */
public class SpringTools implements ApplicationContextAware {

    private static JdbcTemplate jdbcTemplate;

    private static JdbcTemplate getJdbcTemplateBean(String name,DataSource dataSource){
        if(jdbcTemplate==null) {
            jdbcTemplate = new JdbcTemplate();
            jdbcTemplate.setDataSource(dataSource);
        }
        return jdbcTemplate;
    }

    /**
     * 当前IOC
     */
    private static ApplicationContext applicationContext;

    /**
     * 设置当前上下文环境，此方法由spring自动装配
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 从当前IOC获取datasource
     * 
     * @param id
     *            bean的id
     * @return
     */
    public static Object getObject(String id) {
        Object object;
        if(id.equals("datasource")){
            object =applicationContext.getBean(DataSource.class);
            object=getJdbcTemplateBean(id,(DataSource) object);
        }else{
            object  = applicationContext.getBean(id);
        }
        return object;
    }

    /**
     * 从当前IOC获取bean
     * @param id
     * @return
     */
    public static Object getBean(String id) {
        Object object = null;
        object = applicationContext.getBean(id);
        return object;
    }

    /**
     * 从当前IOC获取多个bean
     * @param cls
     * @return
     */
    public static Object getBeans(Class<?> cls) {
        Object object = null;
        object = applicationContext.getBeansOfType(cls);
        return object;
    }

}