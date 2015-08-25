package com.xiaomi.plugin.model;

import javax.persistence.*;
import java.util.Set;

/**
 * 软件分类 发布分类
 * Created by lijie on 2015-06-25.
 */
@Entity
@Table(name = "type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "[name]")
    private String name;

    @Column(name = "[desc]")
    private String desc;

    private Integer orderNO;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getOrderNO() {
        return orderNO;
    }

    public void setOrderNO(Integer orderNO) {
        this.orderNO = orderNO;
    }

}
