package com.netcorner.sqlmapper.entity;

import java.util.Date;

/**
 * Created by shijiufeng on 2022/4/20.
 */
@Table("datasource.user")
public class User extends Entity<User> {
    private Integer ID;
    private String UserName;
    private String RealName;
    private String Pwd;
    private Date LastLoginTime;
    private String LastLoginIP;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public String getPwd() {
        return Pwd;
    }

    public void setPwd(String pwd) {
        Pwd = pwd;
    }

    public Date getLastLoginTime() {
        return LastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        LastLoginTime = lastLoginTime;
    }

    public String getLastLoginIP() {
        return LastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        LastLoginIP = lastLoginIP;
    }
}
