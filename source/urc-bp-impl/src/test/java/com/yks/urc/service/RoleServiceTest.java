/*
 * 文件名：RoleServiceTest.java
 * 版权：Copyright by www.youkeshu.com
 * 描述：测试RoleService相关功能
 * 创建人：OuJie
 * 创建时间：2018年06月13日
 * 修改理由：
 * 修改内容：
 */
package com.yks.urc.service;

import com.yks.urc.AbstractSpringTest;
import com.yks.urc.mapper.IRoleMapper;
import com.yks.urc.service.api.IRoleService;
import com.yks.urc.vo.ResultVO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * 测试RoleService相关功能
 * @author OuJie
 * @version 1.2
 * @date 2018年06月13日
 * @see RoleServiceTest
 * @since JDK1.8
 */

public class RoleServiceTest extends AbstractSpringTest {

    @Autowired
    private IRoleMapper roleMapper;
    @Autowired
    private IRoleService roleService;

    String operator = "user";

    @Test
    public void testCheckDuplicateRoleName(){
        String newRoleName = "admin1";
        String roleId = null;
        ResultVO<Integer> resultVO = roleService.checkDuplicateRoleName(operator, newRoleName, roleId);
        System.out.println("..............");
        //当前角色名重复---新增情况
        Assert.assertEquals(Optional.of(resultVO.data), Optional.of(1));
//        当前角色名重复---修改情况
        roleId = "12";
        resultVO = roleService.checkDuplicateRoleName(operator, newRoleName, roleId);
        Assert.assertEquals(Optional.of(resultVO.data), Optional.of(1));
//        当前角色名不重复---修改情况
        roleId = "7";
        resultVO = roleService.checkDuplicateRoleName(operator, newRoleName, roleId);
        Assert.assertEquals(Optional.of(resultVO.data), Optional.of(0));
    }



    @Test
    public void testIsAdminAccount(){
        //非管理员用户
        Assert.assertEquals(roleMapper.isSuperAdminAccount("panyun"), false);
        //管理员用户
        Assert.assertEquals(roleMapper.isSuperAdminAccount("oujie"), true);
    }
}
