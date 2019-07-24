/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author lwx
 * @create 2018/7/19
 * @since 1.0.0
 */
package com.yks.urc.mapper;

import com.yks.urc.entity.RoleOwnerDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleOwnerMapper {
    /**
     * 通过roleId 查找owner表
     * @param:
     * @return
     * @Author lwx
     * @Date 2018/7/19 10:14
     */
    List<RoleOwnerDO> selectOwnerByRoleId(@Param("roleId") long roleId);
    /**
     * 通过owner 查询
     * @param:
     * @return
     * @Author lwx
     * @Date 2018/7/19 15:23
     */
    List<RoleOwnerDO> selectOwnerByOwner(@Param("owner") String owner);
    /**
     * 插入
     * @param:
     * @return
     * @Author lwx
     * @Date 2018/7/19 20:43
     */
    int insertOwner(RoleOwnerDO ownerDO);
     /**
      *  通过roleId 删除owner
      * @param:
      * @return
      * @Author lwx
      * @Date 2018/7/19 20:50
      */
     int deleteOwnerByRoleId(Long roleId);
     /**
      * 通过roleId 更新owner
      * @param:
      * @return 
      * @Author lwx
      * @Date 2018/7/19 20:53
      */
     int updateOwnerByRoleId(RoleOwnerDO ownerDO);

    /**
     * 判断当前操作人是否是当前角色owner
     * @param:
     * @return
     * @Author lwx
     * @Date 2018/7/19 15:23
     */
   int judgeOwnerByOwnerAndId(@Param("owner") String owner,@Param("roleId") long roleId);
}
