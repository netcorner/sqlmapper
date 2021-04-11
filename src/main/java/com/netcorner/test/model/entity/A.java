package com.netcorner.test.model.entity;

import com.netcorner.sqlmapper.entity.Entity;
import com.netcorner.sqlmapper.entity.Table;

/**
 * Created by shijiufeng on 2021/4/11.
 */
@Table("Jobmate.b")
public class A extends Entity<A> {
    private  int id;
    private String a;
    private String b;

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

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }
}
