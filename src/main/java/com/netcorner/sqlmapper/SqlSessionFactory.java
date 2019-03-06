package com.netcorner.sqlmapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by shijiufeng on 2019/3/6.
 */
public class SqlSessionFactory {
    private static Map<String,PlatformTransactionManager> sessions=new HashMap<String,PlatformTransactionManager>();

    public static Map<String, PlatformTransactionManager> getSessions() {
        return sessions;
    }

    public static void setSessions(String dbName, DataSource dataSource){
        if(!sessions.containsKey(dbName)){
            DataSourceTransactionManager dstm=new DataSourceTransactionManager();
            dstm.setDataSource(dataSource);
            sessions.put(dbName,dstm);
        }
    }
    public static void setSessions(String dbName, PlatformTransactionManager transactionManager){
        if(!sessions.containsKey(dbName)){
            sessions.put(dbName,transactionManager);
        }
    }

    private Stack<PlatformTransactionManager> dataSourceTransactionManagerStack;
    private Stack<TransactionStatus> transactionStatuStack;

    public  SqlSessionFactory(){
        dataSourceTransactionManagerStack = new Stack<PlatformTransactionManager>();
        transactionStatuStack = new Stack<TransactionStatus>();
        for (Map.Entry<String, PlatformTransactionManager> session : sessions.entrySet()) {
            dataSourceTransactionManagerStack.push(session.getValue());
            DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
            TransactionStatus transactionStatus = session.getValue().getTransaction(defaultTransactionDefinition);
            transactionStatuStack.push(transactionStatus);
        }
    }
    public void commit(){
        while (!dataSourceTransactionManagerStack.isEmpty()) {
            dataSourceTransactionManagerStack.pop().commit(
                    transactionStatuStack.pop());
        }
    }

    public void rollback(){
        while (!transactionStatuStack.isEmpty()) {
            dataSourceTransactionManagerStack.pop().rollback(
                    transactionStatuStack.pop());
        }
    }
}
