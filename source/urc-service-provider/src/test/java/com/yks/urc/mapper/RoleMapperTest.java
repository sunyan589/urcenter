package com.yks.urc.mapper;

import com.alibaba.fastjson.JSONObject;
import com.yks.urc.entity.RoleDO;
import com.yks.urc.entity.RoleOwnerDO;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.vo.UserVO;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 〈一句话功能简述〉
 * 角色管理mapper单元测试类
 *
 * @author lvcr
 * @version 1.0
 * @date 2018/6/7 16:12
 * @see RoleMapperTest
 * @since JDK1.8
 */
public class RoleMapperTest extends BaseMapperTest {
	
	
	@Autowired
	IRolePermissionMapper rolePermissionMapper;

    @Autowired
    private IRoleMapper roleMapper;
    
    
    @Autowired
    private IDataRuleTemplMapper dataRuleTemplMapper;
    
    @Autowired
    private IExpressionMapper expressionMapper;
    
    @Autowired
    private IDataRuleColMapper dataRuleColMapper;
    
    
    @Autowired
    private IUserMapper userMapper;
    
    
    @Autowired
    private IUserRoleMapper userRoleMapper;

    @Test
    public void testInsert() {
        RoleDO roleDO = new RoleDO();
        roleDO.setActive(Boolean.TRUE);
        roleDO.setAuthorizable(Boolean.TRUE);
        roleDO.setCreateBy("admin");
        roleDO.setEffectiveTime(new Date());
        roleDO.setCreateTime(new Date());
        roleDO.setForever(Boolean.FALSE);
        roleDO.setRoleName("admin"+System.currentTimeMillis());
        int rtn = roleMapper.insert(roleDO);
        Assert.assertEquals(1, rtn);
    }


    @Test
    public void deleteBatch() {
        List<Long> ids = new ArrayList();
        ids.add(9L);
        ids.add(15L);
        int rtn = roleMapper.deleteBatch(ids);
        System.out.println(rtn);

    }

    @Test
    public void getRoleByRoleId() {
    	String params="{\"user\":{\"userId\":\"123\"} }";
    	JSONObject jsonObject = StringUtility.parseString(params);
    	String sss=jsonObject.getString("user");
        UserVO userVO = StringUtility.parseObject(sss, UserVO.class);
        
    	if(userVO==null){
    		System.out.println("sdssd");
    	}else{
    		System.out.println("sdfsdfdsfd");
    	}
/*		UserVO userVO=new UserVO();
		userVO.userName="test";
		
		Query query=new Query(userVO, 0, 10);
		List<UserVO> userList=userMapper.fuzzySearchUsersByUserName(query);
		int userCount=userMapper.fuzzySearchUsersByUserNameCount(query);
		System.out.println(userList);
		System.out.println(userCount);*/
		//PageResultVO pageResultVO=new PageResultVO(userList, userCount, 10);
    	
    	
/*        DataRuleTemplDO templDO = new DataRuleTemplDO();
       templDO.setCreateBy("admin");
        
        Query query=new Query(templDO, 0, 10);
        
        
        int dataRuleTempCount = dataRuleTemplMapper.getMyDataRuleTemplCount(query);

        
        List<DataRuleTemplDO> dataRuleTempList = dataRuleTemplMapper.getMyDataRuleTempl(query);
    	System.out.println(dataRuleTempList);
    	System.out.println(dataRuleTempCount);*/
/*		RoleDO roleDO=roleMapper.getRoleByRoleId(Long.parseLong("1528856724627000041"));
		System.out.println(roleDO.getRoleName());*/
    	
/*		List<RoleDO> roleList=roleMapper.listAllRoles();
    	System.out.println(roleList.size());*/
		//List<RolePermissionDO> rolePermissionList=rolePermissionMapper.getUserAuthorizablePermission("panyun");
		//List<ExpressionDO> expressionList= expressionMapper.listExpressionDOsBySysKey("001","admin");
		//List<DataRuleColDO> dataRuleColList =dataRuleColMapper.listRuleColBySysKey("001","admin");

    	//System.out.println(dataRuleColList.toString());
    	
/*    	List<String> lstUserName=new ArrayList<String>();
    	lstUserName.add("panyun");
    	List<DataRuleVO> dataRuel=new ArrayList<DataRuleVO>();
		for (int i = 0; i < lstUserName.size(); i++) {
			//通过用户名得到sys_key
			List<String> syskeyList=userRoleMapper.getSysKeyByUser(lstUserName.get(i));
			List<DataRuleSysVO> lstDataRuleSys =new ArrayList<DataRuleSysVO>();
			for (int j = 0; j < syskeyList.size(); j++) {
				//通过sysKey得到 行权限
				List<ExpressionDO> expressionList= expressionMapper.listExpressionDOsBySysKey(syskeyList.get(i));
				//通过sysKey得到列权限
				List<DataRuleColDO> dataRuleColList =dataRuleColMapper.listRuleColBySysKey(syskeyList.get(i));
				List<DataRuleColVO> dataRuleColVOList =new ArrayList<DataRuleColVO>();
				 for (DataRuleColDO colDO : dataRuleColList) {
					DataRuleColVO dataRuleColVO=new DataRuleColVO();
					 BeanUtils.copyProperties(colDO, dataRuleColVO);
					 dataRuleColVOList.add(dataRuleColVO);
				}
				List<ExpressionVO> expressionVOList =new ArrayList<ExpressionVO>();
				 for (ExpressionDO  expressionDO : expressionList) {
					 ExpressionVO expressionVO=new ExpressionVO();
					 BeanUtils.copyProperties(expressionDO, expressionVO);
					 expressionVOList.add(expressionVO);
				}
				DataRuleSysVO dataRuleSysVO=new DataRuleSysVO();	
				ExpressionVO expressionVO=new ExpressionVO();
				expressionVO.setSubWhereClause(expressionVOList);
				dataRuleSysVO.sysKey=syskeyList.get(i);
				dataRuleSysVO.col=dataRuleColVOList;
				dataRuleSysVO.row=expressionVO;
				lstDataRuleSys.add(dataRuleSysVO);
			}
			DataRuleVO dataRuleVO=new DataRuleVO();
			dataRuleVO.userName=lstUserName.get(i);
			dataRuleVO.lstDataRuleSys=lstDataRuleSys;
			dataRuel.add(dataRuleVO);
		}
		
		System.out.println(StringUtility.toJSONString_NoException(dataRuel));*/
		
/*        RoleDO roleDO = roleMapper.getRoleByRoleId(1L);
        Assert.assertNull(roleDO);*/
		//List<DataRuleTemplDO> dataRuleTempList=dataRuleTemplMapper.getMyDataRuleTempl("admin");
/*    	List<String> syskeyList=new ArrayList<String>();
    	syskeyList.add("1");
    	syskeyList.add("2");
		List<ExpressionDO> expressionList= expressionMapper.listExpressionDOsBySysKey(syskeyList);*/
		
    	
/*    	List<String> syskeyList=new ArrayList<String>();
    	syskeyList.add("1");
    	syskeyList.add("2");
		List<DataRuleColDO> dataRuleColList =dataRuleColMapper.listRuleColBySysKey(syskeyList);*/
		
    	//List<DataRuleColDO> dataRuleColList =dataRuleColMapper.listRuleColBySysKey("001");

    	//List<UserVO> userList=userMapper.getUserByRoleId("1528856724627000011");

       // List<String> lstUserName= userRoleMapper.getUserNameByRoleId("1528856724627000011");

    }

    @Test
    public void testIsAdminAccount(){
        //非管理员用户
        Assert.assertEquals(roleMapper.isSuperAdminAccount("panyun"), false);
        //管理员用户
        Assert.assertEquals(roleMapper.isSuperAdminAccount("oujie"), true);
    }
    @Test
    public void testGetByRoleName(){
       RoleDO roleDO = roleMapper.getByRoleName("admin");
       Assert.assertNotNull(roleDO);
    }
    @Autowired
    private RoleOwnerMapper ownerMapper;

    @Test
    public void testListRolesByPage(){
        int currPage = 1;
        int pageSize = 3;
        RoleDO roleDO = new RoleDO();
//        roleDO.setRoleName("admin");
//        roleDO.setRemark("hehe");
        List<RoleOwnerDO>  ownerDOS =ownerMapper.selectOwnerByOwner("wujianghui");
        List<Long> roleIds =new ArrayList<>();
        for (RoleOwnerDO ownerDO :ownerDOS){
            roleIds.add(ownerDO.getRoleId());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("createBy", "wujianghui");
//        map.put("roleDO", roleDO);
        map.put("currIndex", (currPage - 1) * pageSize);
        map.put("pageSize", pageSize);
        map.put("roleIds",roleIds);
       List<RoleDO> roleDOS = roleMapper.listRolesByPage(map);
       // Assert.assertNotNull(roleDOS);
    }
    @Test
    public void getRoleName(){
    /*    String username ="panyun";
       List<String> roleName=  roleMapper.selectRoleNameByUserName(username);
        System.out.println(roleName);*/
    }


    @Test
    public void getRoleDatasByRoleId(){
        RoleDO role =  roleMapper.getRoleDatasByRoleId(1529550145551000001L);
        System.out.println(role);
    }

    @Test
    public void deleteBatchRoleDatas(){
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("createBy", "admin");
        List<Long> lstRoleId = new ArrayList<>();
        lstRoleId.add(1L);
        lstRoleId.add(2L);
        dataMap.put("roleIds", lstRoleId);
        int rtn = roleMapper.deleteBatchRoleDatas(dataMap);
        System.out.println(rtn);
    }
    @Test
    public void testIsAdmin(){
        String str ="zhangqun";
       boolean flag= roleMapper.isAdminOrSuperAdmin(str);
        System.out.println(flag);
    }

    @Test
    public void isSysAdminAccount(){
        Boolean flag = roleMapper.isSysAdminAccount("houyunfeng","004");
        System.out.println(flag);
    }
}
