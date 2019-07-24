package com.yks.urc.entity;

import java.util.Date;
import java.util.List;

public class UserDO {
    private Long id;
    /**
     * 上网账号
     */
    private String userName;
    /**
     * 表示是否启用， 66050表示禁用，其他的表示启用。
     */
    private int isActive;
    /**
     * 启用时间
     */
    private Date activeTime;
    /**
     * 钉钉用户id
     */
    private String dingUserId;
    /**
     * 创建人
     */
    private String createBy;

    private Date createTime;
    /**
     * 修改人
     */
    private String modifiedBy;

    private Date modifiedTime;
    /**
     * Person 实体
     */
    private Person person;
    /**
     * 用户权限关系集合  一个用户 对应多个角色的权限
     */
    private List<UserRoleDO> userRoleDOList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public String getDingUserId() {
        return dingUserId;
    }

    public void setDingUserId(String dingUserId) {
        this.dingUserId = dingUserId == null ? null : dingUserId.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy == null ? null : modifiedBy.trim();
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<UserRoleDO> getUserRoleDOList() {
        return userRoleDOList;
    }

    public void setUserRoleDOList(List<UserRoleDO> userRoleDOList) {
        this.userRoleDOList = userRoleDOList;
    }
}