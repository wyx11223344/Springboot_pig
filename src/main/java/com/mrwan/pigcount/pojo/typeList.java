package com.mrwan.pigcount.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 类型表格
 * 表名为:type_list
 */
@Entity
@Table(name = "type_list")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class typeList implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private Integer type;

    @Column(name = "typename")
    private String typename;

    @Column(name = "is_get")
    private Integer is_get;

    @Column(name = "icon_name")
    private String icon_name;

    @Column(name = "bgc")
    private String bgc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public Integer getIs_get() {
        return is_get;
    }

    public void setIs_get(Integer is_get) {
        this.is_get = is_get;
    }

    public String getIcon_name() {
        return icon_name;
    }

    public void setIcon_name(String icon_name) {
        this.icon_name = icon_name;
    }

    public String getBgc() {
        return bgc;
    }

    public void setBgc(String bgc) {
        this.bgc = bgc;
    }

    public typeList(Integer id ,Integer type, String typename, Integer is_get, String icon_name, String bgc) {
        this.id = id;
        this.type = type;
        this.typename = typename;
        this.is_get = is_get;
        this.icon_name = icon_name;
        this.bgc = bgc;
    }

    public typeList() {
    }
}
