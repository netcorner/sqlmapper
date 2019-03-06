package com.netcorner.aop;

import com.alibaba.druid.pool.DruidDataSource;
import com.netcorner.sqlmapper.SQLMap;
import com.netcorner.sqlmapper.SqlSessionFactory;
import com.netcorner.sqlmapper.utils.SpringTools;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Stack;

/**
 * Created by shijiufeng on 2019/3/6.
 */
@Aspect            //使用@Aspect注解将一个java类定义为切面类
@Component
public class MultiTransactionalAspect {


    @Pointcut("execution(public * com.netcorner.*.service.*.*(..))")
    public void MultiTransactional() {
    }


    private static Logger logger = Logger.getLogger(SQLMap.class);
    /**
     * 切入点
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("MultiTransactional()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Stack<PlatformTransactionManager> dataSourceTransactionManagerStack = new Stack<PlatformTransactionManager>();
        Stack<TransactionStatus> transactionStatuStack = new Stack<TransactionStatus>();

        try {
            Object target = point.getTarget();
            String method = point.getSignature().getName();
            Class classz = target.getClass();
            Class[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
            Method m = classz.getMethod(method, parameterTypes);
            if (m != null && m.isAnnotationPresent(MultiTransactional.class)) {
                MultiTransactional multiTransactional = m.getAnnotation(MultiTransactional.class);
                if (!openTransaction(dataSourceTransactionManagerStack, transactionStatuStack, multiTransactional)) {
                    return null;
                }
                Object ret = point.proceed();
                commit(dataSourceTransactionManagerStack, transactionStatuStack);
                return ret;
            }
            Object ret = point.proceed();
            return ret;
        } catch (Exception e) {
            rollback(dataSourceTransactionManagerStack, transactionStatuStack);
            logger.error(String.format(
                    "MultiTransactionalAspect, method:%s-%s occors error:", point
                            .getTarget().getClass().getSimpleName(), point
                            .getSignature().getName()), e);
            throw e;
        }
    }

    /**
     * 打开一个事物方法
     *
     * @param dataSourceTransactionManagerStack
     * @param transactionStatuStack
     * @param multiTransactional
     * @return
     */
    private boolean openTransaction(
            Stack<PlatformTransactionManager> dataSourceTransactionManagerStack,
            Stack<TransactionStatus> transactionStatuStack,
            MultiTransactional multiTransactional) {
        // 获取注解中要打开的事物类型
        String[] transactionMangerNames = multiTransactional.values();
        if (ArrayUtils.isEmpty(multiTransactional.values())) {
            return false;
        }

        if (transactionMangerNames[0].equals("")) {
            for (Map.Entry<String, PlatformTransactionManager> session : SqlSessionFactory.getSessions().entrySet()) {

                PlatformTransactionManager dataSourceTransactionManager = session.getValue();
                // 定义一个新的事物定义
                DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
                // 设置一个默认的事物传播机制,注意的是这里可以拓展注解中没有用到的属性
                // defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(defaultTransactionDefinition);
                // 将这个事物的定义放入Stack中
                transactionStatuStack.push(transactionStatus);
                dataSourceTransactionManagerStack
                        .push(dataSourceTransactionManager);

            }
        }else {
            for (String beanName : transactionMangerNames) {
                // 创建一个新的事物管理器,用来管理接下来要用到的事物
                DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
                // 根据注解中获取到的数据标识,从spring容器中去查找对应的数据源
                DruidDataSource dataSource = (DruidDataSource) SpringTools.getBean(beanName);
                //然后交给事物管理器去管理
                dataSourceTransactionManager.setDataSource(dataSource);
                // 定义一个新的事物定义
                DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
                // 设置一个默认的事物传播机制,注意的是这里可以拓展注解中没有用到的属性
                // defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(defaultTransactionDefinition);
                // 将这个事物的定义放入Stack中
                /**
                 * 其中为什么要用Stack来保存TransactionManager和TransactionStatus呢？
                 * 那是因为Spring的事务处理是按照LIFO/stack behavior的方式进行的。
                 * 如若顺序有误，则会报错：
                 */
                transactionStatuStack.push(transactionStatus);
                dataSourceTransactionManagerStack
                        .push(dataSourceTransactionManager);
            }
        }
        return true;
    }

    /**
     * 提交事物方法实现
     *
     * @param dataSourceTransactionManagerStack
     * @param transactionStatuStack
     */
    private void commit(
            Stack<PlatformTransactionManager> dataSourceTransactionManagerStack,
            Stack<TransactionStatus> transactionStatuStack) {
        while (!dataSourceTransactionManagerStack.isEmpty()) {
            dataSourceTransactionManagerStack.pop().commit(
                    transactionStatuStack.pop());
        }
    }

    /**
     * 回滚事物方法实现
     *
     * @param dataSourceTransactionManagerStack
     * @param transactionStatuStack
     */
    private void rollback(
            Stack<PlatformTransactionManager> dataSourceTransactionManagerStack,
            Stack<TransactionStatus> transactionStatuStack) {
        while (!dataSourceTransactionManagerStack.isEmpty()) {
            dataSourceTransactionManagerStack.pop().rollback(
                    transactionStatuStack.pop());
        }
    }
}