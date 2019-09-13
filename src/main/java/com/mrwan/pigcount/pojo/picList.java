package com.mrwan.pigcount.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pic_list")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class picList implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "pic_url")
    private String pic_url;

    @Column(name = "type")
    private String type;

    @Column(name = "create_time")
    private Long create_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public picList(Integer id, String pic_url, String type, Long create_time) {
        this.id = id;
        this.pic_url = pic_url;
        this.type = type;
        this.create_time = create_time;
    }

    public picList(){

    }
}
