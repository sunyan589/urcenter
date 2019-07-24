package com.yks.urc.mapper;

import com.yks.urc.entity.RoleDO;
import com.yks.urc.entity.RolePermissionDO;
import com.yks.urc.fw.StringUtility;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉
 * 角色-功能权限mapper单元测试类
 *
 * @author lvcr
 * @version 1.0
 * @date 2018/6/7 16:12
 * @see RolePermissionMapperTest
 * @since JDK1.8
 */
public class RolePermissionMapperTest extends BaseMapperTest {

    @Autowired
    private IRolePermissionMapper rolePermissionMapper;

    @Test
    public void deleteBatch() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(3L);
        ids.add(4L);
        int rtn = rolePermissionMapper.deleteBatch(ids);
        System.out.println(rtn);

    }

    @Test
    public void insertAndUpdateBatch() {
        List<RolePermissionDO> rolePermissionDOS = new ArrayList<>();
        //更新
        RolePermissionDO rolePermissionDO = new RolePermissionDO();
        rolePermissionDO.setRoleId(1L );
        rolePermissionDO.setSysKey("001");
        rolePermissionDO.setSelectedContext("text select context");
        rolePermissionDO.setModifiedBy("test-admin");
        rolePermissionDO.setModifiedTime(new Date());
        RolePermissionDO rolePermissionDO2 = new RolePermissionDO();
        rolePermissionDO2.setRoleId(1L);
        rolePermissionDO2.setSysKey("002");
        rolePermissionDO2.setSelectedContext("text select context2");
        rolePermissionDO2.setModifiedBy("test-admin2");
        rolePermissionDO2.setModifiedTime(new Date());
        RolePermissionDO rolePermissionDO5 = new RolePermissionDO();
        rolePermissionDO5.setRoleId(2L);
        rolePermissionDO5.setSysKey("001");
        rolePermissionDO5.setSelectedContext("text select context2-------------");
        rolePermissionDO5.setModifiedBy("test-admin2");
        rolePermissionDO5.setModifiedTime(new Date());
        //新增
        RolePermissionDO rolePermissionDO3 = new RolePermissionDO();
        rolePermissionDO3.setRoleId(4L);
        rolePermissionDO3.setSysKey("001");
        rolePermissionDO3.setSelectedContext("text select context4");
        rolePermissionDO3.setCreateBy("test-admin4");
        rolePermissionDO3.setCreateTime(new Date());
        RolePermissionDO rolePermissionDO4 = new RolePermissionDO();
        rolePermissionDO4.setRoleId(3L);
        rolePermissionDO4.setSysKey("001");
        rolePermissionDO4.setSelectedContext("text select context....");
        rolePermissionDO4.setCreateBy("test-admin....");
        rolePermissionDO4.setCreateTime(new Date());

        rolePermissionDOS.add(rolePermissionDO);
        rolePermissionDOS.add(rolePermissionDO2);
        rolePermissionDOS.add(rolePermissionDO3);
        rolePermissionDOS.add(rolePermissionDO4);
        rolePermissionDOS.add(rolePermissionDO5);
        int rtn = rolePermissionMapper.insertAndUpdateBatch(rolePermissionDOS);
        System.out.println(rtn);
    }


    @Test
    public void insertBatch(){
        List<RolePermissionDO> rolePermissionDOS = new ArrayList<>();
        //更新
        RolePermissionDO rolePermissionDO = new RolePermissionDO();
        rolePermissionDO.setRoleId(1L);
        rolePermissionDO.setSysKey("003");
        rolePermissionDO.setSelectedContext("text select context");
        rolePermissionDO.setModifiedBy("test-admin");
        rolePermissionDO.setModifiedTime(new Date());
        RolePermissionDO rolePermissionDO2 = new RolePermissionDO();
        rolePermissionDO2.setRoleId(1L);
        rolePermissionDO2.setSysKey("004");
        rolePermissionDO2.setSelectedContext("text select context2");
        rolePermissionDO2.setModifiedBy("test-admin2");
        rolePermissionDO2.setModifiedTime(new Date());
        RolePermissionDO rolePermissionDO5 = new RolePermissionDO();
        rolePermissionDO5.setRoleId(2L);
        rolePermissionDO5.setSysKey("010");
        rolePermissionDO5.setSelectedContext("text select context2-------------");
        rolePermissionDO5.setModifiedBy("test-admin2");
        rolePermissionDO5.setModifiedTime(new Date());
        //新增
        RolePermissionDO rolePermissionDO3 = new RolePermissionDO();
        rolePermissionDO3.setRoleId(5L);
        rolePermissionDO3.setSysKey("111");
        rolePermissionDO3.setSelectedContext("text select context4");
        rolePermissionDO3.setCreateBy("test-admin4");
        rolePermissionDO3.setCreateTime(new Date());
        RolePermissionDO rolePermissionDO4 = new RolePermissionDO();
        rolePermissionDO4.setRoleId(6L);
        rolePermissionDO4.setSysKey("001");
        rolePermissionDO4.setSelectedContext("text select context....");
        rolePermissionDO4.setCreateBy("test-admin....");
        rolePermissionDO4.setCreateTime(new Date());

        rolePermissionDOS.add(rolePermissionDO);
        rolePermissionDOS.add(rolePermissionDO2);
        rolePermissionDOS.add(rolePermissionDO3);
        rolePermissionDOS.add(rolePermissionDO4);
        rolePermissionDOS.add(rolePermissionDO5);
        int rtn = rolePermissionMapper.insertBatch(rolePermissionDOS);
        System.out.println(rtn);
    }
    @Test
    public void testGetKey(){
        String operator ="panyun";
        List<String> list =rolePermissionMapper.getSysKetByRoleAndUserName(operator);
        for (String str:list){
            System.out.println(str);
        }
    }

    @Test
    public void deleteBySysKey(){
        String sysKey = "111";
        Integer rtn = rolePermissionMapper.deleteBySysKey(sysKey);
        System.out.println(rtn);
    }

    @Test
    public void test_update(){
        RolePermissionDO permissionDO =new RolePermissionDO();
        permissionDO.setRoleId(Long.valueOf("1541831483517000027"));
        permissionDO.setModifiedTime(StringUtility.getDateTimeNow());
        rolePermissionMapper.updateUserRoleByRoleId(permissionDO);
    }
}
