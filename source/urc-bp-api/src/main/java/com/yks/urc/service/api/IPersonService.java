package com.yks.urc.service.api;

import com.yks.urc.vo.PersonVO;
import com.yks.urc.vo.ResultVO;

public interface IPersonService {
	
	
	/**
	 * 根据DingOrgId获取人员信息含有分页
	 * @return
	 */
	ResultVO getUserByDingOrgId(String dingOrgId,String pageNumber, String pageData);
	
	
	/**
	 * 搜索用户全局
	 * @return
	 */
	ResultVO getUserByUserInfo(PersonVO person,String pageNumber, String pageData);
	
	
	
	/**
	 * 同步钉钉数据
	 * @param userName
	 * @return
	 */
	ResultVO SynPersonOrgFromDing(String userName);
	/**
	 * 通过名字模糊搜索用户信息
	 * @param
	 * @return
	 * @Author linwanxian@youkeshu.com
	 * @Date 2018/7/9 19:25
	 */
	ResultVO fuzzSearchPersonByName(String operator,String userName);

}
