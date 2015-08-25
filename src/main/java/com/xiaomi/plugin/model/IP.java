package com.xiaomi.plugin.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 记录ip文件的上传信息
 * Created by lijie on 2015-06-30.
 */
@Entity
@Table(name = "ip")
public class IP {
    private Integer id;
    @Column(name = "[time]")
    private Date time;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
