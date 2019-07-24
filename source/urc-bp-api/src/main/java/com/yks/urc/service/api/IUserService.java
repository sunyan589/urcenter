package com.yks.urc.service.api;

import com.yks.urc.vo.*;

import java.util.List;
import java.util.Map;

public interface IUserService {
	ResultVO syncUserInfo(String curUser);

	ResultVO login(Map<String, String> map);

	ResultVO logout(String jsonStr);

/*	*//**
	 * 根据数据dataRuleId获取用户名
	 * @param ruleDO
	 * @return
	 *//*
	ResultVO queryUserDataByRuleId(DataRuleDO ruleDO);
	
	*//**
	 * 根据数据没有dataRuleId获取用户名
	 * @param ruleDO
	 * @return
	 *//*
	ResultVO queryUserNoDataByRuleId(DataRuleDO ruleDO);
	*/
	
	/**
	 * 用户管理搜索用户
	 * @param userVO
	 * @param pageNumber
	 * @param pageData
	 * @return
	 */
	ResultVO<PageResultVO> getUsersByUserInfo(String operator,UserVO userVO, String pageNumber, String pageData);
	/**
	 *  获取所有平台
	 * @param operator
	 * @return
	 */
	ResultVO<List<OmsPlatformVO>> getPlatformList(String operator);
	/**
	 *  获取指定平台下的账号,站点
	 * @param operator
	 * @param platform
	 * @return
	 */
	ResultVO<List<OmsShopVO>> getShopList(String operator, String platform);


	/**
	 *  获取应用系统及其授权方式
	 * @param
	 * @return
	 * @Author linwanxian@youkeshu.com
	 * @Date 2018/6/14 17:42
	 */
	ResultVO<List<SysAuthWayVO>> getMyAuthWay(String operator);


	ResultVO<GetAllFuncPermitRespVO> getAllFuncPermit(String jsonStr);

	ResultVO funcPermitValidate(Map<String, String> map);

	
	/**
	 * 模糊搜索用户域账号
	 * @param userNmae
	 * @param roleId
	 * @return
	 */
	ResultVO fuzzySearchUsersByUserName(String pageNumber,String pageData,String userName,String operator);
	/**
	 * 精确搜索用户
	 * @param   operator
	 * @param userVO
	 * @return
	 * @Author linwanxian@youkeshu.com
	 * @Date 2018/6/25 14:46
	 */
	ResultVO<List<UserVO>> getUserByUserName(String operator, UserVO userVO);
	/**
	 *   获取所有平台账号站点数据
	 * @param
	 * @return
	 * @Author linwanxian@youkeshu.com
	 * @Date 2018/7/7 14:45
	 */
	ResultVO<List<OmsPlatformVO>> getPlatformShopSite(String operator);

	ResultVO syncPlatform(String operator);

	ResultVO syncShopSite(String operator);

	/**
	 *
	 * @param userName
	 * @param mobile
	 * @return
	 */
	ResultVO resetPwdGetVerificationCode(String userName,String mobile);

	/**
	 *  重置密码-提交重置请求
	 * @param mobile
	 * @param new_password
	 * @param username
	 * @param get_code
	 * @return
	 */
	ResultVO resetPwdSubmit(String mobile,String new_password,String username,String get_code);

	ResultVO getBasicDataList(String jsonStr);

	ResultVO  getWarehouse(String josnStr) ;

	ResultVO searchUserPerson(String jsonStr);
}
