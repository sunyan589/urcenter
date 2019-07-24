package com.yks.urc.dingding.client;

import java.util.Date;

import com.yks.urc.dingding.client.vo.DingApiRespVO;
import com.yks.urc.dingding.client.vo.DingUserVO;
import com.yks.urc.fw.HttpUtility;
import com.yks.urc.fw.StringUtility;

public class App {
	public static void main(String[] args) {
		App app = new App();
		String strAccessToken = app.getAccessToken();
		app.getAllDept(strAccessToken);
		app.getUserByDeptId(strAccessToken, 1L);
	}

	// 获取所有部门
	private String URL_GET_ALL_DEPT = "https://oapi.dingtalk.com/department/list?access_token=%s&fetch_child=true";

	// 根据部门ID获取直接子部门ID
	private String URL_GET_DEPT_SUB_ID = "https://oapi.dingtalk.com/department/list_ids?access_token=%s&id=%s";

	// 根据部门ID获取直接员工
	private String URL_GET_USER_BY_DEPT_ID = "https://oapi.dingtalk.com/user/list?access_token=%s&department_id=%s&size=%s&offset=%s";

	private String KEY_BIRTHDAY = "生日";
	private String KEY_GENDER = "性别";

	private void getUserByDeptId(String strAccessToken, long deptId) {
		System.out.println(String.format("根据部门ID获取直接员工:%s %s", deptId, strAccessToken));
		// 分页获取
		int idx = 0;
		int iPageSize = 1;
		int iCurOffset = 0;
		boolean hasMore = false;
		String strUrl = "";
		do {
			iCurOffset = idx * iPageSize;
			strUrl = String.format(URL_GET_USER_BY_DEPT_ID, strAccessToken, deptId, iPageSize, iCurOffset);
			String strResp = HttpUtility.httpGet(strUrl);
			System.out.println(strResp);
			DingApiRespVO resp = StringUtility.parseObject(strResp, DingApiRespVO.class);
			if (resp != null && resp.errcode == 0 && resp.userlist != null && resp.userlist.size() > 0) {
				for (DingUserVO u : resp.userlist) {
					u.joinDate = new Date(u.hiredDate);
					if (u.extattr != null) {
						// 生日、性别在扩展字段中
						if (u.extattr.containsKey(KEY_BIRTHDAY)) {
							u.birthday = StringUtility.convertToDate(u.extattr.get(KEY_BIRTHDAY), null);
						}
						if (u.extattr.containsKey(KEY_GENDER)) {
							u.gender = u.extattr.get(KEY_GENDER);
						}
					}
				}
				System.out.println(String.format("当前页:%s hasMore=%s\r\n%s", idx + 1, resp.hasMore, StringUtility.toJSONString(resp.userlist)));
				hasMore = resp.hasMore;
			} else {
				hasMore = false;
			}
			idx++;
		} while (hasMore);
	}

	// 获取所有部门
	private void getAllDept(String strAccessToken) {
		String strUrl = String.format(URL_GET_ALL_DEPT, strAccessToken);
		String strResp = HttpUtility.httpGet(strUrl);
		System.out.println(String.format("获取所有部门:\r\n%s\r\n", strResp));

		DingApiRespVO resp = StringUtility.parseObject(strResp, DingApiRespVO.class);
		if (resp != null && resp.errcode == 0) {

		}
	}

	// 获取accessToken
	private String getAccessToken() {
		String strUrl = "https://oapi.dingtalk.com/gettoken?corpid=dinge8d7141acdb006a135c2f4657eb6378f&corpsecret=1Tf9YqLFKPNF0xJumHQWmZYGt9HdpPjlWT68P1NJu3yWYM1r9hJAajlFbXaZeuis";
		String strResp = HttpUtility.httpGet(strUrl);
		System.out.println(String.format("GET TOKEN:\r\n%s\r\n", strResp));

		DingApiRespVO resp = StringUtility.parseObject(strResp, DingApiRespVO.class);
		if (resp != null) {
			if (resp.errcode == 0) {
				return resp.access_token;
			}
		}
		return "";
	}

}
