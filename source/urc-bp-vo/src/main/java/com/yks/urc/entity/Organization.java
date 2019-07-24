package com.yks.urc.entity;

import java.util.Date;

public class Organization {
    private Long id;

    private String dingOrgId;

    private String orgName;

    private String fullIdPath;

    private String fullNamePath;

    private int orgLevel;

    private String parentDingOrgId;

    private Date createTime;

    private String createBy;

    private Date modifiedTime;

    private String modifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDingOrgId() {
        return dingOrgId;
    }

    public void setDingOrgId(String dingOrgId) {
        this.dingOrgId = dingOrgId == null ? null : dingOrgId.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getFullIdPath() {
        return fullIdPath;
    }

    public void setFullIdPath(String fullIdPath) {
        this.fullIdPath = fullIdPath == null ? null : fullIdPath.trim();
    }

    public String getFullNamePath() {
        return fullNamePath;
    }

    public void setFullNamePath(String fullNamePath) {
        this.fullNamePath = fullNamePath == null ? null : fullNamePath.trim();
    }

    public int getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(int orgLevel) {
        this.orgLevel = orgLevel;
    }

    public String getParentDingOrgId() {
        return parentDingOrgId;
    }

    public void setParentDingOrgId(String parentDingOrgId) {
        this.parentDingOrgId = parentDingOrgId == null ? null : parentDingOrgId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy == null ? null : modifiedBy.trim();
    }
}