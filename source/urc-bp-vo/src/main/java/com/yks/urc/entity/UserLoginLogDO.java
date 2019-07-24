package com.yks.urc.entity;

import java.util.Date;

public class UserLoginLogDO {
	public long id;
	public String userName;
	public String ip;
	public long ldapCost;
	public int loginSuccess;
	public String remark;
	public Date loginTime;
	public Date createTime;
	public Date modifiedTime;
}
