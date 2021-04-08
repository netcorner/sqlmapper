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
    public Integer getId() {
        return id;
    }

    /**
     * 设置 ID
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public Integer getC() {
        return c;
    }

    public void setC(Integer c) {
        this.c = c;
    }
}
