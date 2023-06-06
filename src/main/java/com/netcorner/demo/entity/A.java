package com.netcorner.demo.entity;

import com.netcorner.sqlmapper.entity.Entity;
import com.netcorner.sqlmapper.entity.Table;

import java.util.Date;

/**
 * Created by shijiufeng on 2022/4/20.
 */
@Table("jobmate.a")
public class A extends Entity<A> {
    private Integer id;
    private String a;
    private String b;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }
}
