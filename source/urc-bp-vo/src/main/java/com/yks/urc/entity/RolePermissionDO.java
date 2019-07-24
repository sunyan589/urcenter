package com.yks.urc.entity;

import java.util.Date;

public class RolePermissionDO {
    private Integer id;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 业务系统key
     */
    private String sysKey;
    /**
     * 功能权限json
     */
    private String selectedContext;

    private Date createTime;

    private String createBy;

    private Date modifiedTime;

    private String modifiedBy;




    private String sysName;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId( Long roleId) {
        this.roleId = roleId;
    }

    public String getSysKey() {
        return sysKey;
    }

    public void setSysKey(String sysKey) {
        this.sysKey = sysKey;
    }

    public String getSelectedContext() {
        return selectedContext;
    }

    public void setSelectedContext(String selectedContext) {
        this.selectedContext = selectedContext;
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
        this.createBy = createBy;
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
        this.modifiedBy = modifiedBy;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }
}