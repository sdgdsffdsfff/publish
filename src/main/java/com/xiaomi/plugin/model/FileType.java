package com.xiaomi.plugin.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "file_type")
public class FileType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 文件名称
     */
    private String name;

    private String sid;

    /**
     * 1.应用软件更新包管理 2.应用软件资源包管理  3.增值业务插件包管理 4.增值业务资源包管理
     */
    private String style;

    /**
     * 大分类下面的小分类 (应用软件下面分为qq 360等)
     */
//    private String sid;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取文件名称
     *
     * @return name - 文件名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置文件名称
     *
     * @param name 文件名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取1=应用软件升级包 2=应用软件资源包  3=增值业务资源包 4=增值业务插件包
     *
     * @return style - 1=应用软件升级包 2=应用软件资源包  3=增值业务资源包 4=增值业务插件包
     */
    public String getStyle() {
        return style;
    }

    /**
     * 设置1=应用软件升级包 2=应用软件资源包  3=增值业务资源包 4=增值业务插件包
     *
     * @param style 1=应用软件升级包 2=应用软件资源包  3=增值业务资源包 4=增值业务插件包
     */
    public void setStyle(String style) {
        this.style = style;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fileType",cascade = CascadeType.REMOVE)
    @OrderBy(value = "time DESC ")
    @JsonBackReference
    private Set<FileList> fileListSet;

    public Set<FileList> getFileListSet() {
        return fileListSet;
    }

    public void setFileListSet(Set<FileList> fileListSet) {
        this.fileListSet = fileListSet;
    }
}