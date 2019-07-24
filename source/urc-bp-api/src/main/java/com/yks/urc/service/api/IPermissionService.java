package com.yks.urc.service.api;


import com.yks.urc.vo.FuncTreeVO;
import com.yks.urc.vo.ResultVO;

import java.util.List;

public interface IPermissionService {

	ResultVO importSysPermit(String jsonStr);
	
	
	/**
	 * 获取指定用户可授权给其它角色的功能权限
	 * @param operator
	 * @return
	 */
	ResultVO getUserAuthorizablePermission(String operator);

	ResultVO getUserPermissionList(String jsonStr);
	/**
	 *  更新缓存API前缀
	 * @param
	 * @return
	 * @Author lwx
	 * @Date 2018/8/15 10:18
	 */
	ResultVO updateApiPrefixCache();

	/**
	 *  删除功能权限树节点
	 * @param
	 * @return
	 * @Author lwx
	 * @Date 2018/11/2 10:26
	 */
	ResultVO deleteSysPermitNode(FuncTreeVO funcTreeVO);
	/**
	 *  修改功能权限树节点
	 * @param
	 * @return
	 * @Author lwx
	 * @Date 2018/11/2 15:43
	 */
	ResultVO updateSysPermitNode(FuncTreeVO funcTreeVO);

}
