package com.netcorner.sqlmapper.demo;

import com.netcorner.sqlmapper.entity.Entity;
import com.netcorner.sqlmapper.entity.Table;

/**
 * Created by shijiufeng on 2021/4/5.
 */
@Table("Jobmate.b")
public class TestEntity extends Entity<TestEntity,Integer> {

    private String a;
    private Integer c;

    //@ApiModelProperty(value = "ID", name = "id")
    private Integer id;
    /**
     * 得到 ID
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * 设置 ID
     * @param id
     */
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
