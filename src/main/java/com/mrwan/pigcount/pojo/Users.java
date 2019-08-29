package com.mrwan.pigcount.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 用户信息表
 * 表名为:pig_users
 */
@Entity
@Table(name = "pig_users")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Users implements Serializable {

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
    private Long create_time;

    @Column(name = "state")
    private Integer state;

    @Column(name = "code")
    private String code;

    @Column(name = "ip")
    private String ip;

    @Column(name = "last_time")
    private Long last_time;

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

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getLast_time() {
        return last_time;
    }

    public void setLast_time(Long last_time) {
        this.last_time = last_time;
    }

    @Override
    public String toString() {
        return "Users [id=" + id + ", name=" + name + "]";
    }

    public Users toUsers(Integer id,String name,String username,String password,Long create_time,Integer state,String code,Long last_time,String ip)
    {
        Users users = new Users();
        users.setId(id);
        users.setName(name);
        users.setUsername(username);
        users.setPassword(password);
        users.setCreate_time(create_time);
        users.setState(state);
        users.setCode(code);
        users.setLast_time(last_time);
        users.setIp(ip);
        return users;
    }

    public Users(String name, String username, String password, Long create_time, Integer state, String code, String ip, Long last_time) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.create_time = create_time;
        this.state = state;
        this.code = code;
        this.ip = ip;
        this.last_time = last_time;
    }

    public Users(){

    }
}
