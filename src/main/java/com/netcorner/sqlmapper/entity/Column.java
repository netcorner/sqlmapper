package com.netcorner.sqlmapper.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by netcorner on 15/10/20.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Column {
    /**
     * 字段名称
     * @return
     */
    String name() default "";

    /**
     * 字段数据类型名
     * @return
     */
    String type() default "";

    /**
     * 是否主键
     * @return
     */
    boolean isPrimary() default  false;

    /**
     * 是否为自增长
     * @return
     */
    boolean auto() default  false;
}