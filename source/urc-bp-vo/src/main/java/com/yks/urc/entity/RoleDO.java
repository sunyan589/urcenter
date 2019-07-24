package com.yks.urc.entity;

import java.util.Date;
import java.util.List;

public class RoleDO {

    private Long id;
    /**
     * 角色Id
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 是否启用
     */
    private boolean isActive;
    /**
     * 是否管理员角色
     */
    private boolean isAuthorizable;
    /**
     * 是否永久有效
     */
    private boolean isForever;
    /**
     * 有效开始时间
     */
    private Date effectiveTime;
    /**
     * 有效结束时间
     */
    private Date expireTime;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;
    /**
     * 更新时间
     */
    private Date modifiedTime;
    /**
     * 更新人
     */
    private String modifiedBy;
    /**
     * 备注
     */
    private String remark;

    /**
     *   角色-功能权限关系表
     */
    private RolePermissionDO permissionDO;

    private List<RolePermissionDO> permissionDOList;

    private List<UserRoleDO> userRoleDOS;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isAuthorizable() {
        return isAuthorizable;
    }

    public void setAuthorizable(boolean authorizable) {
        isAuthorizable = authorizable;
    }

    public boolean isForever() {
        return isForever;
    }

    public void setForever(boolean forever) {
        isForever = forever;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public RolePermissionDO getPermissionDO() {
        return permissionDO;
    }

    public void setPermissionDO(RolePermissionDO permissionDO) {
        this.permissionDO = permissionDO;
    }

    public List<RolePermissionDO> getPermissionDOList() {
        return permissionDOList;
    }

    public void setPermissionDOList(List<RolePermissionDO> permissionDOList) {
        this.permissionDOList = permissionDOList;
    }

    public List<UserRoleDO> getUserRoleDOS() {
        return userRoleDOS;
    }

    public void setUserRoleDOS(List<UserRoleDO> userRoleDOS) {
        this.userRoleDOS = userRoleDOS;
    }
}
