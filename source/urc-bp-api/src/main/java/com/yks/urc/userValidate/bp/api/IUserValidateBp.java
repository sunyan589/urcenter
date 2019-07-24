package com.yks.urc.userValidate.bp.api;

import java.util.List;
import java.util.Map;

import com.yks.urc.entity.UserPermitStatDO;
import com.yks.urc.vo.ResultVO;
import com.yks.urc.vo.SystemRootVO;

public interface IUserValidateBp {
	/**
	 * 根据username/syskey获取角色功能权限json list
	 * 
	 * @param userName
	 * @param sysKey
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月12日 下午3:44:53
	 */
	List<String> getFuncJsonLstByUserAndSysKey(String userName, String sysKey);

	/**
	 * 根据username/syskey获取角色功能权限json(合并后的json)
	 * 
	 * @param userName
	 * @param sysKey
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月12日 下午3:44:55
	 */
	String getFuncJsonByUserAndSysKey(String userName, String sysKey);

	/**
	 * 生成ticket
	 * 
	 * @param strUserName
	 * @param ip
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月13日 上午9:57:37
	 */
	String createTicket(String strUserName);

	/**
	 * 合并为一个sys树
	 * 
	 * @param lstJson
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月13日 下午3:46:16
	 */
	String mergeFuncJson(List<String> lstJson);

	SystemRootVO mergeFuncJson2Obj(List<String> lstJson);


	/**
	 * 计算功能权限版本号
	 * 
	 * @param strFuncJson
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月13日 下午3:49:04
	 */
	String calcFuncVersion(String strFuncJson);

	/**
	 * 打平所有module
	 * 
	 * @param sys1
	 * @param userName
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月13日 下午3:59:42
	 */
	List<UserPermitStatDO> plainSys(SystemRootVO sys1, String userName);

	/**
	 * 校验ticket、功能权限版本、是否有权限
	 * @param map
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月14日 下午2:07:35
	 */
	ResultVO funcPermitValidate(Map<String, String> map);

	 /**
	 * 功能权限json清理：将old与newest对比，删除old中存在但newest不存在的node删除
	 * @param strFuncJsonOld
	 * @param strFuncJsonNewest
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月15日 上午10:21:38
	 */
	String cleanDeletedNode(String strFuncJsonOld, String strFuncJsonNewest);

	ResultVO addUrcWhiteApi(String json);
	ResultVO deleteWhiteApi(String json);
}
