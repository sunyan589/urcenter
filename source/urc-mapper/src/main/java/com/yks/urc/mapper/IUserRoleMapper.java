package com.yks.urc.mapper;

import com.yks.urc.entity.UserRoleDO;
import com.yks.urc.vo.NameVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 用户-角色关系Mapper操作类
 *
 * @author lvcr
 * @version 1.0
 * @date 2018/6/8 14:47
 * @see IUserRoleMapper
 * @since JDK1.8
 */
public interface IUserRoleMapper {

    /**
     * Description:批量删除用户-角色关系
     *
     * @param : ids
     * @return:
     * @auther: lvcr
     * @date: 2018/6/8 15:06
     * @see
     */
    Integer deleteBatch(List<Long> ids);

    /**
     * 根据用户名获取sysKey
     *
     * @param userName
     * @return List<UserRoleDO>
     * @Author linwanxian@youkeshu.com
     * @Date 2018/6/12 15:59
     */
    List<String> getSysKeyByUser(@Param("userName") String userName);

    /**
     * Description: 批量新增或更新用户-角色关系表
     *
     * @param :userRoleDOS
     * @return:Integer
     * @auther: lvcr
     * @date: 2018/6/13 20:17
     * @see
     */
    Integer insertBatch(List<UserRoleDO> userRoleDOS);

    /**
     * Description: 根据roleId删除
     *
     * @param :roleId
     * @return: Integer
     * @auther: lvcr
     * @date: 2018/6/13 20:51
     * @see
     */
    Integer deleteByRoleId(Long roleId);

	List<String> getUserNameByRoleId(UserRoleDO userRoleDO);
	/**
	 *   获取 用户名和域账号 getNameVOByRoleId
	 * @param
	 * @return 
	 * @Author lwx
	 * @Date 2019/1/10 11:15
	 */
	List<NameVO> getNameVOByRoleId(UserRoleDO userRoleDO);

	/**
	 * 根据userName删除用与角色的关联表
	 * @param userNmae
	 * @return
	 */
	Integer deleteByUserName(String userNmae);



	/**
	 * 删除userRole关联表
	 * @param userRoleDO
	 * @return
	 */
	Integer deleteUserRole(UserRoleDO userRoleDO);

	/**
	 * 删除userRole中userName关联表
	 * @param userRole
	 * @param userNameList
	 * @return
	 */
	Integer deleteUserRoleInUserName(@Param("userRole") UserRoleDO userRole,@Param("userNameList")  List<String> userNameList);

    /**
     * Description: 根据roleId列表和创建人查找用户-角色对应的用户名
     *
     * @param : queryMap
     * @return: List<String>
     * @auther: lvcr
     * @date: 2018/6/20 11:53
     * @see
     */
    List<String> listUserNamesByRoleIds(Map queryMap);

	/**
	 * Description: 获取userName所拥有的系统
	 * @param :
	 * @return:
	 * @auther: lvcr
	 * @date: 2018/7/4 12:45
	 * @see
	 */
	List<String> getUserOwnSysByUserName(@Param("userName") String userName);


}