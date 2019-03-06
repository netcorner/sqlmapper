package com.netcorner.aop;

import java.lang.annotation.*;

/**
 * Created by shijiufeng on 2019/3/6.
 * 处理多数据源的注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MultiTransactional {
    String[] values() default "";
}