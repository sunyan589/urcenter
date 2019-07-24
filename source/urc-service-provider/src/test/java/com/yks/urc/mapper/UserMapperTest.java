package com.yks.urc.mapper;

import com.yks.urc.entity.RoleDO;
import com.yks.urc.entity.UserDO;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.user.bp.impl.UserBpImpl;
import com.yks.urc.vo.RoleVO;
import com.yks.urc.vo.UserVO;
import com.yks.urc.vo.helper.Query;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * userMapper单元测试类
 *
 * @author lvcr
 * @version 1.0
 * @date 2018/6/7 16:12
 * @see UserMapperTest
 * @since JDK1.8
 */
public class UserMapperTest extends BaseMapperTest {

    @Autowired
    private IUserMapper userMapper;
    @Autowired
    private  IRolePermissionMapper rolePermissionMapper;

    @Autowired
    private  IRoleMapper roleMapper;

    /*@Autowired
    UserBp userBp;*/

    @Test
    public void insert() {
        insert2();
    }

    @Transactional
    public void insert2() {
        UserDO userDo = new UserDO();
        userDo.setUserName("test");
        userDo.setIsActive(1);
        userDo.setDingUserId("1232");
        userDo.setActiveTime(StringUtility.getDateTimeNow());
        userDo.setModifiedBy("lwx");
        userDo.setCreateBy("lwx");

        UserDO userDo1 = new UserDO();
        userDo1.setUserName("123");
        userDo1.setIsActive(1);
        userDo1.setDingUserId("1232");
        userDo1.setActiveTime(StringUtility.getDateTimeNow());
        userDo1.setModifiedBy("lll");
        userDo1.setCreateBy("lll");
        List<UserDO> list = new ArrayList<>();
        list.add(userDo);
        list.add(userDo1);

        int delete = userMapper.deleteUrcUser();
        System.out.println("-------------"+delete);
        int result = userMapper.insertBatchUser(list);
        System.out.println(result);
//        userBp.insert();
    }

    @Test
    public void testInsert() {
/*        List<UserDO> users = userMapper.listUsersByRoleId("1");
        Assert.assertNotNull(users);*/
        RoleVO roleVO=new RoleVO();
        roleVO.roleId="1529743116993000004";
        List<String> roleSysKey=new ArrayList<>();
        roleSysKey.add("001");
        roleSysKey.add("003");
        rolePermissionMapper.deleteByRoleIdInSysKey(roleVO.roleId,roleSysKey);
    }

    @Test
    public void testgetUserInfo() {
		//List<String> lstUserName= userMapper.listAllUsersUserName();
		
/*		List<String> lstUserName =userMapper.listUsersUserNameByRoleId(2L);

        for (int i = 0; i < lstUserName.size(); i++) {
			System.out.println(lstUserName.get(i));
		}*/
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("currIndex", 0);
        queryMap.put("pageSize", 200);
        String[] roleNames = {"admin2","复制"};
        queryMap.put("roleNames", roleNames);
        //List<RoleVO> roleVOS = roleMapper.listRolesByPage(queryMap);
        //System.out.println(roleVOS.size());
    	
/*        UserVO userVO = new UserVO();
        int pagaNum = 0;
        int pageData = 10;
        userVO.userName = "苏林";
        List<UserDO> list = new ArrayList<>();
        Query query = new Query(userVO, pagaNum, pageData);
        list = userMapper.getUsersByUserInfo(query);
        int count = userMapper.getUsersByUserInfoCount(query);
        System.out.println(count);
        for (UserDO user : list) {
            System.out.println("==============");
            System.out.println(user.getUserName());
        }*/
    }
}
