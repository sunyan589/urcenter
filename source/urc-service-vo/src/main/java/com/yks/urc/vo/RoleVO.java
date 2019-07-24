/**
 * 〈一句话功能简述〉<br>
 * 〈角色基础信息〉
 *
 * @author 31076
 * @create 2018/6/5
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RoleVO implements Serializable {
    private static final long serialVersionUID = -706052625111152853L;
    /**
     * roleId
     */
    public String roleId;
    /**
     *
     */
    public Integer id;
    /**
     * 角色名
     */
    public String roleName;
    /**
     * 是否启用
     */
    public boolean isActive;
    /**
     * 是否管理员角色
     */
    public boolean isAuthorizable;
    /**
     * 是否永久有效
     */
    public boolean isForever;
    /**
     * 有效开始时间
     */
    public Date effectiveTime;
    /**
     * 有效结束时间
     */
    public Date expireTime;

    private String effectiveTimeStr;
    private String expireTimeStr;
    private String createTimeStr;
    private String modifiedTimeStr;
    /**
     * 创建时间
     */
    public Date createTime;
    /**
     * 创建人
     */
    public String createBy;

    /**
     * 更新时间
     */
    public Date modifiedTime;

    /**
     * 更新人
     */
    private String modifiedBy;

    /**
     * 备注
     */
    private String remark;

    /**
     * 功能权限json
     */
    public List<PermissionVO> selectedContext;
    /**
     * 拥有此角色的用户域账号
     */
    public List<String> lstUserName;
    /**
     *  用户名 和域账号
     */
    public List<NameVO> lstUser;
    /**
     *  此角色的拥有者
     */
    public List<String> lstOwner;
    /**
     *   此角色拥有者的中文名和域名
     */
    public List<NameVO> lstOwnerInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<PermissionVO> getSelectedContext() {
        return selectedContext;
    }

    public void setSelectedContext(List<PermissionVO> selectedContext) {
        this.selectedContext = selectedContext;
    }

    public List<String> getLstUserName() {
        return lstUserName;
    }

    public void setLstUserName(List<String> lstUserName) {
        this.lstUserName = lstUserName;
    }

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEffectiveTimeStr() {
        return effectiveTimeStr;
    }

    public void setEffectiveTimeStr(String effectiveTimeStr) {
        this.effectiveTimeStr = effectiveTimeStr;
    }

    public String getExpireTimeStr() {
        return expireTimeStr;
    }

    public void setExpireTimeStr(String expireTimeStr) {
        this.expireTimeStr = expireTimeStr;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getModifiedTimeStr() {
        return modifiedTimeStr;
    }

    public void setModifiedTimeStr(String modifiedTimeStr) {
        this.modifiedTimeStr = modifiedTimeStr;
    }

    public List<NameVO> getLstUser() {
        return lstUser;
    }

    public void setLstUser(List<NameVO> lstUser) {
        this.lstUser = lstUser;
    }

    public List<String> getLstOwner() {
        return lstOwner;
    }

    public void setLstOwner(List<String> lstOwner) {
        this.lstOwner = lstOwner;
    }

    public List<NameVO> getLstOwnerInfo() {
        return lstOwnerInfo;
    }

    public void setLstOwnerInfo(List<NameVO> lstOwnerInfo) {
        this.lstOwnerInfo = lstOwnerInfo;
    }
}
