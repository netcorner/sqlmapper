package com.netcorner.sqlmapper;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by shijiufeng on 2019/3/20.
 */
@ConfigurationProperties(prefix = "spring.sqlmapper")
public class SQLMapProperties {
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
