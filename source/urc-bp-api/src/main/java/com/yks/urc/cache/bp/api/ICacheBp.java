package com.yks.urc.cache.bp.api;

import java.util.List;

import com.yks.urc.entity.PermissionDO;
import com.yks.urc.vo.GetAllFuncPermitRespVO;
import com.yks.urc.vo.UserVO;

public interface ICacheBp {
	long getNextSeq(String strKey);
	UserVO getUser(String userName);

	String getWhiteApi(String str);

	/**
	 * 获取用户所有系统的功能权限
	 * @param userName
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月21日 下午3:30:56
	 */
	GetAllFuncPermitRespVO getUserFunc(String userName);

	void insertUser(UserVO u);
     //存入白名单到redis
	void insertWhiteApi(String apiStr);
	void removeUser(String userName);

	/**
	 * 缓存用户所有系统功能权限
	 * @param userName
	 * @param permitCache
	 * @author panyun@youkeshu.com
	 * @date 2018年6月21日 下午8:01:38
	 */
	void insertUserFunc(String userName, GetAllFuncPermitRespVO permitCache);

	/**
	 * 获取某个user的功能权限版本
	 *
	 * @param userName
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月14日 下午2:23:29
	 */
	String getFuncVersion(String userName);

	/**
	 * 获取sys功能权限json,null表示缓存中没有，需要查db;Empty则不需要查db
	 *
	 * @param sysKey
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月14日 下午2:46:02
	 */
	String getSysContext(String sysKey);

	/**
	 * 获取用户某个sys功能权限json
	 *
	 * @param operator
	 * @param sysKey
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月14日 下午2:46:21
	 */
	String getFuncJson(String userName, String sysKey);

	void insertSysContext(String sysKey, String sysContext);

	List<PermissionDO> getSysApiUrlPrefix();
	void setSysApiUrlPrefix(List<PermissionDO> lst);

	String getDingAccessToken(String accessTokeTime);

	void setDingAccessToken(String accessTokeTime,String accessTokeValue);

	/**
	 * 获取用户姓名
	 * @param userName
	 * @return
	 */
    String getPersonNameByUserName(String userName);

	/**
	 * 设置用户姓名
	 * @param userName
	 * @param personName
	 */
	void setPersonNameByUserName(String userName, String personName);
	/**
	 *  获取 所有有用的平台和账号
	 * @param
	 * @return
	 * @Author lwx
	 * @Date 2018/9/4 16:41
	 */
	String getAllPlatformShop(String platformShopKey,String entityCode);
	/**
	 *  设置 所有有用的平台和账号
	 * @param
	 * @return
	 * @Author lwx
	 * @Date 2018/9/4 16:43
	 */
	void  setAllPlatformShop(String allPlatformShopJson,String entityCode);

    void refreshUserExpiredTime(String userName);
}
