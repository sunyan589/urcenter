package com.yks.urc.mapper;

import com.yks.urc.entity.RoleDO;
import com.yks.urc.entity.UserDO;
import com.yks.urc.entity.UserRoleDO;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.vo.UserVO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 〈一句话功能简述〉
 * 用户角色管理mapper单元测试类
 *
 * @author lvcr
 * @version 1.0
 * @date 2018/6/7 16:12
 * @see UserRoleMapperTest
 * @since JDK1.8
 */
public class UserRoleMapperTest extends BaseMapperTest {

    @Autowired
    private IRoleMapper roleMapper;

    @Autowired
    IUserRoleMapper userRoleMapper;

    @Test
    public void testInsert() {
        RoleDO roleDO = new RoleDO();
        roleDO.setActive(Boolean.TRUE);
        roleDO.setAuthorizable(Boolean.TRUE);
        roleDO.setCreateBy("admin");
        roleDO.setEffectiveTime(new Date());
        roleDO.setCreateTime(new Date());
        roleDO.setForever(Boolean.FALSE);
        roleDO.setRoleName("admin");
        int rtn = roleMapper.insert(roleDO);
        Assert.assertEquals(1, rtn);
    }
    @Test
    public void updateByRoleId(){
        RoleDO roleDO = new RoleDO();
        roleDO.setRoleId(1529742315053000002L);
        roleDO.setRoleName("admin角色");
        roleDO.setModifiedBy("lvchagntong");
        roleDO.setModifiedTime(new Date());
        int rtn = roleMapper.updateByRoleId(roleDO);
    }

    @Test
    public void deleteBatch() {
        List<Long> ids = new ArrayList<>();
        ids.add(2L);
        ids.add(3L);
        ids.add(4L);
        int rtn = userRoleMapper.deleteBatch(ids);
        System.out.println(rtn);

    }


    @Test
    public void getSysKeyByUserName() {
        String userName = "linwanxian";
        List<String> userRoleDOS = userRoleMapper.getSysKeyByUser(userName);
        System.out.println(StringUtility.toJSONString_NoException(userRoleDOS));
    }


    @Test
    public void insertBatch(){
        List<UserRoleDO> userRoleDOS = new ArrayList<>();
        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setRoleId(1l);
        userRoleDO.setUserName("admin");
        userRoleDO.setCreateTime(new Date());
        userRoleDO.setCreateBy("admin");
        userRoleDOS.add(userRoleDO);


        UserRoleDO userRoleDO1 = new UserRoleDO();
        userRoleDO1.setRoleId(1l);
        userRoleDO1.setUserName("admin1");
        userRoleDO1.setCreateTime(new Date());
        userRoleDO1.setCreateBy("admin1");
        userRoleDOS.add(userRoleDO1);


        UserRoleDO userRoleDO2 = new UserRoleDO();
        userRoleDO2.setRoleId(2l);
        userRoleDO2.setUserName("admin2");
        userRoleDO2.setCreateTime(new Date());
        userRoleDO2.setCreateBy("admin2");
        userRoleDOS.add(userRoleDO2);

        int rtn = userRoleMapper.insertBatch(userRoleDOS);
        System.out.println(rtn);
    }


    @Test
    public void deleteByRoleId(){
        int rtn =  userRoleMapper.deleteByRoleId(1L);
        System.out.println(rtn);

    }
    @Test
    public void listUserNamesByRoleIdsTest(){
        Map map =new HashMap();
        map.put("role_id","1529550145551000001");
        List<String> strings=userRoleMapper.listUserNamesByRoleIds(map);
        for (String str:strings){
            System.out.println(str);
        }
    }

}
