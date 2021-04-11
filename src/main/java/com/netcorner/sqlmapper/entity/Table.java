package com.netcorner.sqlmapper.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by netcorner on 15/10/20.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Table {
    /**
     * 表格名称
     * @return
     */
    String value() default "";

    /**
     * 是否调试模式
     * @return
     */
    boolean debugger() default false;

}