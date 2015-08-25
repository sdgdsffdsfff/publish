package com.xiaomi.plugin.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "region")
public class Region {
    /**
     * 区域管理
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 区域名称
     */
    @Column(name = "[text]")
    private String text;

    /**
     * 父Id
     */
    private Integer pid;

    /**
     * 控制节点是否展开
     */
    private String state;

    private Integer orderNo;


    /**
     * 获取区域管理
     *
     * @return id - 区域管理
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置区域管理
     *
     * @param id 区域管理
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取区域名称
     *
     * @return text - 区域名称
     */
    public String getText() {
        return text;
    }

    /**
     * 设置区域名称
     *
     * @param text 区域名称
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 获取父Id
     *
     * @return pid - 父Id
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 设置父Id
     *
     * @param pid 父Id
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取节点展开状态
     *
     * @return state -state
     */
    public String getState() {
        return state;
    }

    /**
     * 设置节点展开状态
     *
     * @param state 状态state
     */
    public void setState(String state) {
        this.state = state;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "pid")
    @OrderBy(value = "orderNo")
    private List<Region> children;

    public List<Region> getChildren() {
        return children;
    }

    public void setChildren(List<Region> children) {
        this.children = children;
    }
}