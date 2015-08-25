package com.xiaomi.plugin.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "pub")
public class Pub {
    /**
     * 通用-发布
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 发布时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 有效时间开始
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 有效时间结束
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 任务状态
     */
    private String state;

    /**
     * 延迟天数
     */
    private Integer delay;

    /**
     * 限制次数
     */
    @Column(name = "[limit]")
    private Integer limit;

    /**
     * 当前发布已经使用次数
     */
    private Integer number;

    /**
     * 执行方式 0=重启后执行 1=下载后执行
     */
    private String start;

    /**
     * 发布分类
     */
//    private String sid;

    /**
     * 1.应用软件更新发布管理 2.应用软件资源发布管理  其他的为增值业务插件资源发布
     */
    private String style;

    /**
     * 任务说明
     */
    @Column(name = "[desc]")
    private String desc;

    /**
     * 任务范围
     */
    @Column(name = "range_type")
    private String rangeType;

    /**
     * 范围值
     */
    @Column(name = "range_value")
    private String rangeValue;

    private String sid;

    /**
     * 获取通用-发布
     *
     * @return id - 通用-发布
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置通用-发布
     *
     * @param id 通用-发布
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取发布时间
     *
     * @return create_time - 发布时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置发布时间
     *
     * @param createTime 发布时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取有效时间开始
     *
     * @return start_time - 有效时间开始
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置有效时间开始
     *
     * @param startTime 有效时间开始
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取有效时间结束
     *
     * @return end_time - 有效时间结束
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置有效时间结束
     *
     * @param endTime 有效时间结束
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取任务状态
     *
     * @return state - 任务状态
     */
    public String getState() {
        return state;
    }

    /**
     * 设置任务状态
     *
     * @param state 任务状态
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 获取延迟天数
     *
     * @return delay - 延迟天数
     */
    public Integer getDelay() {
        return delay;
    }

    /**
     * 设置延迟天数
     *
     * @param delay 延迟天数
     */
    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    /**
     * 获取限制次数
     *
     * @return limit - 限制次数
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * 设置限制次数
     *
     * @param limit 限制次数
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * 获取当前发布已经使用次数
     *
     * @return number - 当前发布已经使用次数
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * 设置当前发布已经使用次数
     *
     * @param number 当前发布已经使用次数
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * 获取执行方式 0=重启后执行 1=下载后执行
     *
     * @return start - 执行方式 0=重启后执行 1=下载后执行
     */
    public String getStart() {
        return start;
    }

    /**
     * 设置执行方式 0=重启后执行 1=下载后执行
     *
     * @param start 执行方式 0=重启后执行 1=下载后执行
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * 获取发布分类
     *
     * @return sid - 发布分类
     */
//    public String getSid() {
//        return sid;
//    }


    /**
     * 设置发布分类
     *
     * @param sid 发布分类
     */
//    public void setSid(String sid) {
//        this.sid = sid;
//    }

    /**
     * 获取1=应用软件升级发布 2= 应用软件资源发布 3=增值业务资源发布 4=增值业务插件发布
     *
     * @return style - 1=应用软件升级发布 2= 应用软件资源发布 3=增值业务资源发布 4=增值业务插件发布
     */
    public String getStyle() {
        return style;
    }

    /**
     * 设置1=应用软件升级发布 2= 应用软件资源发布 3=增值业务资源发布 4=增值业务插件发布
     *
     * @param style 1=应用软件升级发布 2= 应用软件资源发布 3=增值业务资源发布 4=增值业务插件发布
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * 获取任务说明
     *
     * @return desc - 任务说明
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 设置任务说明
     *
     * @param desc 任务说明
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 获取任务范围
     *
     * @return range_type - 任务范围
     */
    public String getRangeType() {
        return rangeType;
    }

    /**
     * 设置任务范围
     *
     * @param rangeType 任务范围
     */
    public void setRangeType(String rangeType) {
        this.rangeType = rangeType;
    }

    /**
     * 获取范围值
     *
     * @return range_value - 范围值
     */
    public String getRangeValue() {
        return rangeValue;
    }

    /**
     * 设置范围值
     *
     * @param rangeValue 范围值
     */
    public void setRangeValue(String rangeValue) {
        this.rangeValue = rangeValue;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "pub_file", joinColumns = @JoinColumn(name = "pub_id"), inverseJoinColumns = @JoinColumn(name = "file_id"))
    private Set<FileList> fileListSet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usr")
    private User user;

    public Set<FileList> getFileListSet() {
        return fileListSet;
    }

    public void setFileListSet(Set<FileList> fileListSet) {
        this.fileListSet = fileListSet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 辅助判断当前发布被使用过
     */
    @Transient
    private boolean isUsed;

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }


    @Transient
    private String filelistnames;
    @Transient
    private String filelistids;
    @Transient
    private String rangeValueView;

    public String getFilelistnames() {
        return filelistnames;
    }

    public void setFilelistnames(String filelistnames) {
        this.filelistnames = filelistnames;
    }

    public String getFilelistids() {
        return filelistids;
    }

    public void setFilelistids(String filelistids) {
        this.filelistids = filelistids;
    }

    public String getRangeValueView() {
        return rangeValueView;
    }

    public void setRangeValueView(String rangeValueView) {
        this.rangeValueView = rangeValueView;
    }
}