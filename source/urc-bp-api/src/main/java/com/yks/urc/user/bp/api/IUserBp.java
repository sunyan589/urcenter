package com.yks.urc.user.bp.api;

import java.util.List;
import java.util.Map;

import com.yks.urc.vo.GetAllFuncPermitRespVO;
import com.yks.urc.vo.PageResultVO;
import com.yks.urc.vo.ResultVO;
import com.yks.urc.vo.UserSysVO;
import com.yks.urc.vo.UserVO;

public interface IUserBp {

	ResultVO SynUserFromUserInfo(String userName);

	ResultVO login(Map<String, String> map);
	
	
	/**
	 * 用户管理搜索用户
	 * @param userVO
	 * @param pageNumber
	 * @param pageData
	 * @return
	 */
	ResultVO<PageResultVO> getUsersByUserInfo(String operator,UserVO userVO, String pageNumber, String pageData);
	/**
	 * 获取系统key
	 * @param
	 * @return
	 * @Author linwanxian@youkeshu.com
	 * @Date 2018/6/14 14:47
	 */
	ResultVO<UserSysVO> getSysKeyByUserName(String userName, String sysKey, String ticket);


	/**
	 * 获取用户所有系统功能权限及version
	 * @param operator
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月14日 下午12:58:45
	 */
	ResultVO<GetAllFuncPermitRespVO> getAllFuncPermit(String operator);

	ResultVO logout(String jsonStr);

}
