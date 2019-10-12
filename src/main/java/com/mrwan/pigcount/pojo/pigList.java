package com.mrwan.pigcount.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 记账数据表格
 * 表名为:type_list
 */
@Entity
@Table(name = "pig_list")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class pigList implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "money")
    private int money;

    @Column(name = "time")
    private long time;

    @Column(name = "create_time")
    private long create_time;

    @Column(name = "status")
    private int status;

    @Column(name = "detaile")
    private String detaile;

    @Column(name = "type")
    private int type;

    @Column(name = "pic_ids")
    private String pic_ids;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetaile() {
        return detaile;
    }

    public void setDetaile(String detaile) {
        this.detaile = detaile;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPic_ids() {
        return pic_ids;
    }

    public void setPic_ids(String pic_ids) {
        this.pic_ids = pic_ids;
    }

    public pigList(String username, int money, long time, long create_time, int status, String detaile, int type, String pic_ids) {
        this.username = username;
        this.money = money;
        this.create_time = create_time;
        this.time = time;
        this.status = status;
        this.detaile = detaile;
        this.type = type;
        this.pic_ids = pic_ids;
    }

    public pigList() {
    }
}
