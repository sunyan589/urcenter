package com.yks.urc.service.api;

import com.yks.urc.entity.RoleDO;
import com.yks.urc.entity.UserInfoDO;
import com.yks.urc.vo.PageResultVO;
import com.yks.urc.vo.ResultVO;
import com.yks.urc.vo.RoleVO;
import com.yks.urc.vo.SystemRootVO;
import com.yks.urc.vo.UserVO;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.List;
import java.util.Map;

public interface IRoleService {

    ResultVO<PageResultVO> getRolesByInfo(String jsonStr);

    ResultVO addOrUpdateRoleInfo(String jsonStr);

    ResultVO<RoleVO> getRoleByRoleId(String jsonStr);

    ResultVO getUserByRoleId(String operator,String roleId);

    ResultVO deleteRoles(String jsonStr);


    ResultVO getRolePermission(String operator,List<String> lstRoleId);
    /**
     * 更新角色的功能权限
     * @param  operator
     * @param lstRole
     * @return
     * @Author linwanxian@youkeshu.com
     * @Date 2018/6/20 10:39
     */
    ResultVO updateRolePermission(String operator,List<RoleVO> lstRole);


    ResultVO getRoleUser(String operator, List<String> lstRoleId);

    ResultVO updateUsersOfRole(List<RoleVO> lstRole,String operator);

    ResultVO copyRole(String operator,String newRoleName,String sourceRoleId);

    /**
     * 检查角色名是否重复
     * @param newRoleName
     * @param roleId
     * @return 返回值为0--表示不重复, 1--表示重复
     * @Author oujie
     * @Date 2018/6/12 16:57
     */
    ResultVO checkDuplicateRoleName(String operator, String newRoleName, String roleId);

	/**
	 * 角色过期处理，由taskScheduler调用
	 * @author panyun@youkeshu.com
	 * @date 2018年6月15日 上午11:32:09
	 */
	ResultVO handleExpiredRole();

    ResultVO assignAllPermit2Role();

    /**
     * 判断当前用户是否时超级管理员
     * @param operator
     * @return
     */
    ResultVO operIsSuperAdmin(String operator);
}
