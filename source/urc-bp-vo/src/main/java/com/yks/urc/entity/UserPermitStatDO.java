package com.yks.urc.entity;

import java.util.Date;

public class UserPermitStatDO {
	private Long id;

	private String userName;

	private String sysKey;

	private String moduleName;

	private PermissionDO permissionDO;

	public PermissionDO getPermissionDO() {
		return permissionDO;
	}

	public void setPermissionDO(PermissionDO permissionDO) {
		this.permissionDO = permissionDO;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

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
		this.userName = userName;
	}

	public String getSysKey() {
		return sysKey;
	}

	public void setSysKey(String sysKey) {
		this.sysKey = sysKey;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getFuncJson() {
		return funcJson;
	}

	public void setFuncJson(String funcJson) {
		this.funcJson = funcJson;
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

	public String getUserContext() {
		return userContext;
	}

	public void setUserContext(String userContext) {
		this.userContext = userContext;
	}

	private String funcJson;

	private Date createTime;
	
	private String createBy;

	private Date modifiedTime;

	private String modifiedBy;

	private String userContext;

}