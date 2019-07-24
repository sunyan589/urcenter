package com.yks.urc.mapper;

import java.util.List;
import java.util.Map;

import com.yks.urc.vo.ResultVO;
import org.apache.ibatis.annotations.Param;

import com.yks.urc.entity.UserPermissionCacheDO;
import com.yks.urc.entity.UserPermitStatDO;

public interface IUserPermissionCacheMapper {

	/**
	 * delete urc_user_permission_cache table
	 * 
	 * @param userName
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月13日 下午3:00:40
	 */
	int deletePermitCacheByUser(@Param("userName") String userName);

	/**
	 * delete urc_user_permit_stat table
	 * 
	 * @param userName
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月13日 下午3:00:35
	 */
	int deletePermitStatByUser(@Param("userName") String userName);

	/**
	 * insert urc_user_permission_cache table
	 * 
	 * @param lstCacheToAdd
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月13日 下午4:05:50
	 */
	int insertPermitCache(@Param("lst") List<UserPermissionCacheDO> lstCacheToAdd);

	/**
	 * insert urc_user_permit_stat table
	 * 
	 * @param lstStatToAdd
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月13日 下午4:06:00
	 */
	int insertPermitStat(@Param("lst") List<UserPermitStatDO> lstStatToAdd);


	Long getCounts(@Param("creatBy") String creatBy);

}