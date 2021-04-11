package com.netcorner.test.model.entity;

import com.netcorner.sqlmapper.entity.Table;

import java.util.List;

/**
 * Created by shijiufeng on 2021/4/11.
 */
@Table(value = "Jobmate.b",debugger = true)
public class BExt extends B {
    private List<A> children;

    public List<A> getChildren() {
        return children;
    }

    public void setChildren(List<A> children) {
        this.children = children;
    }


}
