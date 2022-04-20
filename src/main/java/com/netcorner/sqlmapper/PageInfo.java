package com.netcorner.sqlmapper;

import com.netcorner.sqlmapper.QueryPage;

import java.util.List;
import java.util.Map;

/**
 * Created by shijiufeng on 2022/4/20.
 */
public class PageInfo {
    private List<Map<String, Object>> list;
    private QueryPage queryPage;

    public List<Map<String, Object>> getList() {
        return list;
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

    public QueryPage getQueryPage() {
        return queryPage;
    }

    public void setQueryPage(QueryPage queryPage) {
        this.queryPage = queryPage;
    }
}
