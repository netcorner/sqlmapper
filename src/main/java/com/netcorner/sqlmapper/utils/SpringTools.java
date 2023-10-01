package com.netcorner.sqlmapper.utils;


import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;

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
     * 得到配置文件对应key值
     * @param key
     * @return
     */
    public static String getEnvironmentValue(String key) {
        if(applicationContext!=null) {
            String object = applicationContext.getEnvironment().getProperty(key);
            return object;
        }else{
            return null;
        }
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
     * 从当前IOC获取bean
     * @param cls
     * @return
     */
    public static Object getBean(Class<?> cls) {
        Object object = null;
        object = applicationContext.getBean(cls);
        return object;
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @param name
     * @return boolean
     */
    public static boolean containsBean(String name)
    {
        return applicationContext.containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *
     * @param name
     * @return boolean
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     *
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException
    {
        return applicationContext.isSingleton(name);
    }
    /**
     * @param name
     * @return Class 注册对象的类型
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     *
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException
    {
        return applicationContext.getType(name);
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     *
     * @param name
     * @return
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     *
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException
    {
        return applicationContext.getAliases(name);
    }

    /**
     * 获取当前的环境配置，无配置返回null
     *
     * @return 当前的环境配置
     */
    public static String[] getActiveProfiles()
    {
        return applicationContext.getEnvironment().getActiveProfiles();
    }

    /**
     * 获取当前的环境配置，当有多个环境配置时，只获取第一个
     *
     * @return 当前的环境配置
     */
    public static String getActiveProfile()
    {
        final String[] activeProfiles = getActiveProfiles();
        return activeProfiles!=null&&activeProfiles.length>0 ? activeProfiles[0] : null;
    }



    /**
     * 获取aop代理对象
     *
     * @param invoker
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAopProxy(T invoker)
    {
        return (T) AopContext.currentProxy();
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