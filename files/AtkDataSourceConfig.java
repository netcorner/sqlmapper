package com.netcorner.hotelanalysis.config;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.alibaba.druid.util.MySqlUtils;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

/**
 * Created by shijiufeng on 2019/3/5.
 * mysql driver 需使用8.0.11，否则DruidXADataSource要出错的
 */
//@Configuration
public class AtkDataSourceConfig {
    @Autowired
    DefaultListableBeanFactory defaultListableBeanFactory;

    private void setJdbcTemplateBean(String name,DataSource dataSource){
        JdbcTemplate jdbcTemplate=new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        defaultListableBeanFactory.registerSingleton(name,jdbcTemplate);
    }
    @Bean(name = "test1DataSource")
    @Primary
    public DataSource test1DataSource(Environment env) throws SQLException {
        MysqlXADataSource druidXADataSource = build(env, "spring.datasource.test1.druid.");
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setXaDataSource(druidXADataSource);
        ds.setUniqueResourceName("test1DataSource");
        setJdbcTemplateBean("test1",druidXADataSource);
        return ds;

    }


    @Bean(name = "testDataSource")
    public DataSource testDataSource(Environment env) throws SQLException {
        MysqlXADataSource druidXADataSource = build(env, "spring.datasource.test.druid.");
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setXaDataSource(druidXADataSource);
        ds.setUniqueResourceName("testDataSource");
        setJdbcTemplateBean("test",druidXADataSource);
        return ds;

    }


    private MysqlXADataSource build(Environment env, String prefix) throws SQLException {
        MysqlXADataSource druidXADataSource=new MysqlXADataSource();

        druidXADataSource.setUrl(env.getProperty(prefix + "url"));
        druidXADataSource.setUser(env.getProperty(prefix + "username"));
        druidXADataSource.setPassword(env.getProperty(prefix + "password"));
        druidXADataSource.setPinGlobalTxToPhysicalConnection(true);


//        druidXADataSource.setDriverClassName(env.getProperty(prefix + "driver-class-name", ""));
//        //属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：
//        //监控统计用的filter:stat
//        //日志用的filter:log4j
//        //防御sql注入的filter:wall
//        druidXADataSource.setFilters(env.getProperty(prefix + "filters"));
//        //最大连接池数量
//        druidXADataSource.setMaxActive( env.getProperty(prefix + "max-active", Integer.class));
//        //初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
//        druidXADataSource.setInitialSize(env.getProperty(prefix + "initial-size", Integer.class));
//        //最小连接池数量
//        druidXADataSource.setMinIdle( env.getProperty(prefix + "min-idle", Integer.class));
//        //获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
//        druidXADataSource.setMaxWait(env.getProperty(prefix + "max-wait", Integer.class));
//        //有两个含义：
//        //1) Destroy线程会检测连接的间隔时间，如果连接空闲时间大于等于minEvictableIdleTimeMillis则关闭物理连接。
//        //2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明
//        druidXADataSource.setTimeBetweenEvictionRunsMillis(env.getProperty(prefix + "time-between-eviction-runs-millis", Integer.class));
//        //建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，
//        //(空闲时测试)执行validationQuery检测连接是否有效。
//        druidXADataSource.setTestWhileIdle(env.getProperty(prefix + "test-while-idle", Boolean.class));
//        //连接保持空闲而不被驱逐的最小时间
//        druidXADataSource.setMinEvictableIdleTimeMillis(env.getProperty(prefix + "min-evictable-idle-time-millis", Integer.class));
//        druidXADataSource.setValidationQuery( env.getProperty(prefix + "validation-query"));
//        //申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
//        druidXADataSource.setTestOnBorrow( env.getProperty(prefix + "test-on-borrow", Boolean.class));
//        //归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
//        druidXADataSource.setTestOnReturn( env.getProperty(prefix + "test-on-return", Boolean.class));
//        druidXADataSource.setPoolPreparedStatements(env.getProperty(prefix + "pool-prepared-statements", Boolean.class));

        return druidXADataSource;
    }



    //分布式事务配置，配置JTA事务

    @Bean(name = "userTransaction")
    public UserTransaction userTransaction() throws Throwable {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(10000);
        return userTransactionImp;
    }
    @Bean(name = "atomikosTransactionManager", initMethod = "init", destroyMethod = "close")
    public TransactionManager atomikosTransactionManager() throws Throwable {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        return userTransactionManager;
    }
    @Bean(name = "transactionManager")
    @DependsOn({ "userTransaction", "atomikosTransactionManager" })
    public PlatformTransactionManager transactionManager() throws Throwable {
        UserTransaction userTransaction = userTransaction();
        JtaTransactionManager manager = new JtaTransactionManager(userTransaction,atomikosTransactionManager());




        return manager;
    }



}
