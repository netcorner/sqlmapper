package com.netcorner.sqlmapper.demo;

import com.netcorner.sqlmapper.entity.Entity;
import com.netcorner.sqlmapper.entity.Table;

/**
 * Created by shijiufeng on 2021/4/5.
 */
@Table("Jobmate.b")
public class TestEntity extends Entity<TestEntity,Integer> {
    private Integer id;
    private String a;
    private Integer c;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }
}
