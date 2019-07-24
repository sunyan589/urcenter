package com.yks.urc.dingding.client.vo;

import java.util.List;

public class DingApiRespVO {
	public int errcode;
	public String access_token;
	public String errmsg;
	public int expires_in;
	public List<DingDeptVO> department;
	public long[] sub_dept_id_list;

	public boolean hasMore;
	public List<DingUserVO> userlist;
}
