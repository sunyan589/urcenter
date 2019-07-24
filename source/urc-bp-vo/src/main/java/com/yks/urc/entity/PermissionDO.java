package com.yks.urc.entity;

import java.util.Date;

public class PermissionDO {
    private Long id;
    /**
     * 系统名称
     */
    private String sysName;
    /**
     * 系统key
     */
    private String sysKey;

    private String createBy;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private String modifiedBy;
    /**
     *
     */
    private Date modifiedTime;
    /**
     *
     */
    private String sysContext;

    private String apiUrlPrefixJson;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName == null ? null : sysName.trim();
    }

    public String getSysKey() {
        return sysKey;
    }

    public void setSysKey(String sysKey) {
        this.sysKey = sysKey == null ? null : sysKey.trim();
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

    public String getSysContext() {
        return sysContext;
    }

    public void setSysContext(String sysContext) {
        this.sysContext = sysContext == null ? null : sysContext.trim();
    }

	public String getApiUrlPrefixJson() {
		return apiUrlPrefixJson;
	}

	public void setApiUrlPrefixJson(String apiUrlPrefixJson) {
		this.apiUrlPrefixJson = apiUrlPrefixJson;
	}
}