package com.yks.urc.dingding.client.vo;

import java.util.Date;
import java.util.Map;

public class DingUserVO {
	public String unionid;
	public String userid;
	public String email;
	public String mobile;
	public String name;
	public long[] department;

	public String position;
	public String jobnumber;
	// 入职日期
	public long hiredDate;
	public Date joinDate;

	public String gender;
	public Date birthday;

	public Map<String, String> extattr;
}
