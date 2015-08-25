package com.xiaomi.plugin.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bar")
public class Bar {
    @Id
    @GeneratedValue(generator = "_assigned")
    @GenericGenerator(name = "_assigned", strategy = "assigned")
    private Integer id;

    private String barname;

    private String bararea;

    private Integer companyid;

    private String companyname;

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
     * @return barname
     */
    public String getBarname() {
        return barname;
    }

    /**
     * @param barname
     */
    public void setBarname(String barname) {
        this.barname = barname;
    }

    /**
     * @return bararea
     */
    public String getBararea() {
        return bararea;
    }

    /**
     * @param bararea
     */
    public void setBararea(String bararea) {
        this.bararea = bararea;
    }

    /**
     * @return companyid
     */
    public Integer getCompanyid() {
        return companyid;
    }

    /**
     * @param companyid
     */
    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    /**
     * @return companyname
     */
    public String getCompanyname() {
        return companyname;
    }

    /**
     * @param companyname
     */
    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }
}