package com.yks.urc.service.api;

import com.yks.urc.vo.ResultVO;
import com.yks.urc.vo.UserVO;

public interface IOrganizationService {


	/**
	 * 得到所有部门的json树
	 * @return
	 */
	ResultVO getAllOrgTree();

	/**
	 * 获取全部用户及组织结构
	 * @param
	 * @return
	 */
	ResultVO getAllOrgTreeAndUser();

	/**
	 *  精确搜索用户
	 * @return
	 */
	ResultVO getUserByUserName(String operator, UserVO userVo);



}
