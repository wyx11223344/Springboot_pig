package com.mrwan.pigcount.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户信息表
 * 表名为:control_user
 */
@Entity
@Table(name = "pig_users")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class adminUsers implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "create_time")
    private long create_time;

    @Column(name = "limit")
    private String limit;

    @Column(name = "ip")
    private String ip;

    @Column(name = "last_time")
    private long last_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getLast_time() {
        return last_time;
    }

    public void setLast_time(long last_time) {
        this.last_time = last_time;
    }

    public adminUsers(String name, String username, String password, long create_time, String limit, String ip, long last_time) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.create_time = create_time;
        this.limit = limit;
        this.ip = ip;
        this.last_time = last_time;
    }

    public adminUsers(){

    }
}
