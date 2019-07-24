package com.yks.urc.mapper;

import com.yks.urc.entity.RolePermissionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 〈一句话功能简述〉
 * 角色-功能权限Mapper操作类
 *
 * @author lvcr
 * @version 1.0
 * @date 2018/6/8 15:06
 * @see IRolePermissionMapper
 * @since JDK1.8
 */
public interface IRolePermissionMapper {

    /**
     * Description: 批量删除角色-操作权限关系
     *
     * @param : ids
     * @return:
     * @auther: lvcr
     * @date: 2018/6/8 15:08
     * @see
     */
    Integer deleteBatch(List<Long> ids);

    Integer deleteByRoleId(@Param("roleId") Long roleId);


    /**
     * Description: 批量操作角色-操作权限关系 以roleId+sysKey作为唯一约束
     * 1、记录存在则更新
     * 2、记录不存在则新增
     *
     * @param : rolePermissionDOS
     * @return: Integer
     * @auther: lvcr
     * @date: 2018/6/9 12:14
     * @see
     */
    Integer insertAndUpdateBatch(List<RolePermissionDO> rolePermissionDOS);

    /**
     * Description: 批量新增角色-操作权限关系
     *
     * @param : rolePermissionDOS
     * @return: Integer
     * @auther: lvcr
     * @date: 2018/6/9 16:09
     * @see
     */
    Integer insertBatch(List<RolePermissionDO> rolePermissionDOS);



	/**
	 * 获取多个角色已有的功能权限超管
	 * @param permissionDO
	 * @return
	 */
	List<RolePermissionDO> getRoleSuperAdminPermission(RolePermissionDO permissionDO);

    /**
     * 获取多个角色已有的功能权限业务员
     * @param permissionDO
     * @return
     */
    List<RolePermissionDO> getRoleSalePermission(RolePermissionDO permissionDO);

    /**
     *  通过用户名获取角色对应的sys_key
     * @param  userName
     * @return
     * @Author linwanxian@youkeshu.com
     * @Date 2018/6/14 15:20
     */
    List<String> getSysKetByRoleAndUserName(@Param("userName") String userName);
    /**
     * 根据roleId 和 sysKey 更新角色的功能权限
     * @param  rolePermissionDO
     * @return
     * @Author linwanxian@youkeshu.com
     * @Date 2018/6/20 9:45
     */
    int updateUserRoleByRoleId(@Param("rolePermissionDO") RolePermissionDO rolePermissionDO);

    /**
     * 删除指定角色下的sys_key权限
     * @param roleId
     * @param roleSysKey
     * @return
     */
    Integer deleteByRoleIdInSysKey( @Param("roleId") String roleId, @Param("roleSysKey") List<String> roleSysKey);


    /**
     *  获取 sys有关的角色 和权限
     * @param
     * @return
     * @Author lwx
     * @Date 2018/11/2 10:41
     */
    List<RolePermissionDO> getROlePermissionBySysKey(String sysKey);
    
    /**
     * Description: 根据sysKey删除系统对应的功能权限
     * @param :
     * @return: 
     * @auther: lvcr
     * @date: 2018/11/8 14:48
     * @see
     */
    Integer deleteBySysKey(@Param("sysKey") String sysKey);


}