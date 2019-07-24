/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/6/9
 * @since 1.0.0
 */
package com.yks.urc.service;

import com.yks.mq.client.MQConsumerClient;
import com.yks.mq.client.MQConsumerClient.MessageCallBack;
import com.yks.urc.cache.bp.api.ICacheBp;
import com.yks.urc.entity.RoleDO;
import com.yks.urc.fw.EncryptHelper;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.fw.constant.StringConstant;
import com.yks.urc.mapper.IRoleMapper;
import com.yks.urc.motan.MotanSession;
import com.yks.urc.motan.service.api.IUrcService;
import com.yks.urc.motan.service.impl.UrcServiceImpl;
import com.yks.urc.mq.bp.api.IMqBp;
import com.yks.urc.permitStat.bp.api.IPermitStatBp;
import com.yks.urc.seq.bp.api.ISeqBp;
import com.yks.urc.service.api.IPermissionService;
import com.yks.urc.service.api.IUserService;
import com.yks.urc.service.api.MonitorMemoryService;
import com.yks.urc.user.bp.api.IUserBp;
import com.yks.urc.userValidate.bp.api.IUserValidateBp;
import com.yks.urc.vo.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//@Component
public class UrcServiceTest extends BaseServiceTest {
	private static Logger logger =Logger.getLogger(UrcServiceTest.class);
	@Autowired
	private IUrcService service;
	@Autowired
	private IUserValidateBp userValidateBp;
	@Autowired
	private IUserBp userBp;
	@Autowired
	private ISeqBp seqBp;

	@Autowired
	IRoleMapper roleMapper;

	@Autowired
	private ICacheBp cacheBp;

	@Autowired
	private IPermitStatBp permitStatBp;

	@Autowired
	private IUserService userService;
	@Autowired
	IPermissionService permissionService;

	@Autowired
	IMqBp mqBp;

	@Value("${importSysPermit.aesPwd}")
	private String aesPwd;
	public void testIPermissionService() throws Exception {
		String strJson1 = StringUtility.inputStream2String(ClassLoader.getSystemResourceAsStream("oms.json"));
		String strEncrypt = EncryptHelper.encryptAes_Base64(strJson1, aesPwd);
		System.out.println(permissionService.importSysPermit(strEncrypt));
	}

	public static void main(String[] args) {
		String topic = String.format("URC_USER_DATARULE_%s", "001");
		MessageCallBack callBack = new MessageCallBack() {

			@Override
			public void call(String arg0, String arg1) {
				System.out.println(String.format("-----------------MessageCallBack:%s %s", arg0, arg1));
			}
		};
		new MQConsumerClient().subscribe(topic, callBack);
	}

//	@Test
	public void mq_Test() {
		DataRuleVO dr = new DataRuleVO();
		dr.userName = "py";
		dr.lstDataRuleSys = new ArrayList<>();
		DataRuleSysVO e = new DataRuleSysVO();
		e.sysKey = "001";
		dr.lstDataRuleSys.add(e);
		mqBp.send2Mq(dr);
	}

	public void testUpdateExpiredRole() {
		List<RoleDO> lstRole = roleMapper.updateAllExpiredRole();
		System.out.println(StringUtility.toJSONString_NoException(lstRole));
	}
	@Test
	public void testGetAllFuncPermit() {
		System.out.println(StringUtility.toJSONString_NoException(userBp.getAllFuncPermit("linwanxian")));
	}

	public void test_funcPermitValidate() {
		Map<String, String> map = new HashMap<>();
		map.put("apiUrl", "/api/grab/smt/batchMarking");
		map.put("moduleUrl", "/");
		map.put(StringConstant.operator, "dcadmin");
		map.put(StringConstant.ticket, "bfba159f79a0f4b77ee82fabd41507f2");
		map.put(StringConstant.ip, "pyIP");
		map.put(StringConstant.funcVersion, "eb1043692883ef9010cd6cdc8b624e90");
		map.put(StringConstant.sysKey, "001");
		System.out.println("----------------------" + userService.funcPermitValidate(map));
	}

	public void testLogin() {
		UserVO authUser = new UserVO();
		authUser.userName = "dcadmin";
		authUser.pwd = "Ldap_test";
		authUser.ip = "pyIP";
//		System.out.println("------LOGIN-----------------" + StringUtility.toJSONString_NoException(userService.login(authUser)));
	}

	@Test
	public void testPermitCache() {
		List<String> lstUserName = new ArrayList<>();
		lstUserName.add("dcadmin");
		permitStatBp.updateUserPermitCache(lstUserName);
	}

	// @Test
	public void testCache() {
		List<String> lstFromDb = new ArrayList<>();
		lstFromDb.add("110");
		// cacheBp.insertUserSysKey("py", lstFromDb);
//		List<String> lst = cacheBp.getUserSysKey("py");
//		System.out.println("----------------SysKey:" + StringUtility.toJSONString_NoException(lst));
	}

	// @Test
	public void testSeq() {
		RoleDO roleDO = new RoleDO();
		roleDO.setRoleId(seqBp.getNextRoleId());
		roleDO.setRoleName("py");
		roleMapper.insert(roleDO);
		System.out.println(StringUtility.toJSONString_NoException(roleDO));
		// System.out.println(seqBp.getNextSeq("roleId"));
	}

	public void test2() {

		String userName = "panyun";
		String sysKey = "001";
		List<String> lstRslt = userValidateBp.getFuncJsonLstByUserAndSysKey(userName, sysKey);
		System.out.print(userValidateBp.getFuncJsonByUserAndSysKey(userName, sysKey));
		// testSync();
	}
	@Test
	public void getUserInfo(){
		testSync();
	}
	@Transactional
	public void testSync() {
		String json="{\n" +
				"\t\"operator\":\"linwanxian\"\n" +
				"}";
		MotanSession.initialSession(json);
		ResultVO resultVO=service.syncUserInfo(json);
		System.out.println("=====================");
		System.out.println(resultVO.msg);
	}
	@Test
	public void getPlatformTest(){
		String json="{\n" +
				"\t\"operator\":\"test3\"\n" +
				"}";
		MotanSession.initialSession(json);
		ResultVO resultVO=service.getPlatformList(json);
		System.out.println("=====================");
		System.out.println(resultVO.msg);
    }
	@Test
	public void searchUser(){
		Map map =new HashMap();
		map.put("operator","test");
		map.put("pageNumber",0);
		map.put("pageData",50);
		String mapStr =StringUtility.toJSONString(map);
		MotanSession.initialSession(mapStr);
		ResultVO<PageResultVO> resultVO=service.getUsersByUserInfo(mapStr);
		System.out.println("=================");
		System.out.println(resultVO.msg);
		List<UserVO>  userVOS= (List<UserVO>) resultVO.data.lst;
		System.out.println("=================");
		System.out.println(userVOS.size());
	}
	@Test
	public void getShopList(){
		Map map =new HashMap();
		map.put("operator","linwanxian");
		String json =StringUtility.toJSONString_NoException(map);
		MotanSession.initialSession(json);
		ResultVO resultVO =new ResultVO();
		 resultVO.data=service.getShopList(json);
		System.out.println("==================");
		System.out.println(resultVO);
	}

	@Test
	public void getMyWay(){
        String json="{\n" +
                "\t\"operator\":\"panyun\"\n" +
                "}";
		ResultVO resultVO=service.getMyAuthWay(json);
        System.out.println("==================");
        System.out.println(resultVO.msg);
    }
    @Test
    public void  updateManyFunc(){
	    Map map =new HashMap();
	    map.put("operator","linwanxian");
        RoleVO roleVO =new RoleVO();
        List<RoleVO> lstRole =new ArrayList<>();
        roleVO.roleId= "1529550145551000001";
        lstRole.add(roleVO);

        map.put("lstRole",lstRole);
        String json=StringUtility.toJSONString(map);
        String strJson ="{\"lstRole\":[{\"active\":false,\"authorizable\":false,\"forever\":false,\"isActive\":false,\"isAuthorizable\":false,\"isForever\":false,\"roleId\":1529550145551000001}],\"operator\":\"linwanxian\"}";
        System.out.println(json);
        ResultVO resultVO =service.updateRolePermission(json);
        System.out.println(resultVO.msg);
    }
    @Test
	public void searchUserByUserName(){
		String json ="{\n" +
				"\t\"operator\":\"test3\",\n" +
				"\t\"pageNumber\":0,\n" +
				"\t\"pageData\":30,\n" +
				"\t\"user\":{\n" +
				"\t\t\"userName\":\"xxxx\"\n" +
				"\t}\n" +
				"}";
		UserVO userVO =new UserVO();
		userVO.userName="xxxxx";
		Map map =new HashMap();
		map.put("operator","linwanxian");
		map.put("user",userVO);
		String jsonStr =StringUtility.toJSONString(map);
		MotanSession.initialSession(jsonStr);
		System.out.println("==================");
		System.out.println(jsonStr);
		ResultVO resultVO=service.getUserByUserName(jsonStr);
		System.out.println("==================");
		System.out.println(resultVO.msg);
	}
	@Test
	public void updateRolePermission(){
		Map map =new HashMap();
		map.put("operator","panyun");
		List<RoleVO> lstRole =new ArrayList<>();
		RoleVO roleVO =new RoleVO();
		roleVO.roleId = "1529746076695000006";
		List<PermissionVO> selectedContext =new ArrayList<>();
		PermissionVO permissionVO =new PermissionVO();
		permissionVO.setSysKey("004");
		String sysCOntext="{\n" +
				"\t\"menu\": [{\n" +
				"\t\t\"key\": \"004-000001\",\n" +
				"\t\t\"module\": [{\n" +
				"\t\t\t\"function\": [],\n" +
				"\t\t\t\"key\": \"004-000001-000001\",\n" +
				"\t\t\t\"module\": [],\n" +
				"\t\t\t\"name\": \"组织架构\",\n" +
				"\t\t\t\"pageFullPathName\": \"\",\n" +
				"\t\t\t\"show\": 1,\n" +
				"\t\t\t\"url\": \"/user/organization/\"\n" +
				"\t\t},\n" +
				"\t\t{\n" +
				"\t\t\t\"function\": [],\n" +
				"\t\t\t\"key\": \"004-000001-000002\",\n" +
				"\t\t\t\"module\": [{\n" +
				"\t\t\t\t\"function\": [],\n" +
				"\t\t\t\t\"key\": \"004-000001-000002-000001\",\n" +
				"\t\t\t\t\"module\": [{\n" +
				"\t\t\t\t\t\"function\": [],\n" +
				"\t\t\t\t\t\"key\": \"004-000001-000002-000001-000001\",\n" +
				"\t\t\t\t\t\"name\": \"查看方案\",\n" +
				"\t\t\t\t\t\"pageFullPathName\": \"\",\n" +
				"\t\t\t\t\t\"show\": 0,\n" +
				"\t\t\t\t\t\"url\": \"/user/usermanagementlist/datapermissiontempl/viewplan/\"\n" +
				"\t\t\t\t},\n" +
				"\t\t\t\t{\n" +
				"\t\t\t\t\t\"function\": [],\n" +
				"\t\t\t\t\t\"key\": \"004-000001-000002-000001-000002\",\n" +
				"\t\t\t\t\t\"name\": \"新增/编辑方案\",\n" +
				"\t\t\t\t\t\"pageFullPathName\": \"\",\n" +
				"\t\t\t\t\t\"show\": 0,\n" +
				"\t\t\t\t\t\"url\": \"/user/usermanagementlist/datapermissiontempl/compileplan/\"\n" +
				"\t\t\t\t}],\n" +
				"\t\t\t\t\"name\": \"数据权限模板\",\n" +
				"\t\t\t\t\"pageFullPathName\": \"\",\n" +
				"\t\t\t\t\"show\": 1,\n" +
				"\t\t\t\t\"url\": \"/user/usermanagementlist/datapermissiontempl/\"\n" +
				"\t\t\t},\n" +
				"\t\t\t{\n" +
				"\t\t\t\t\"function\": [],\n" +
				"\t\t\t\t\"key\": \"004-000001-000002-000002\",\n" +
				"\t\t\t\t\"module\": [],\n" +
				"\t\t\t\t\"name\": \"数据授权\",\n" +
				"\t\t\t\t\"pageFullPathName\": \"\",\n" +
				"\t\t\t\t\"show\": 1,\n" +
				"\t\t\t\t\"url\": \"/user/usermanagementlist/datapauthorization/\"\n" +
				"\t\t\t},\n" +
				"\t\t\t{\n" +
				"\t\t\t\t\"function\": [],\n" +
				"\t\t\t\t\"key\": \"004-000001-000002-000003\",\n" +
				"\t\t\t\t\"module\": [],\n" +
				"\t\t\t\t\"name\": \"操作权限列表\",\n" +
				"\t\t\t\t\"pageFullPathName\": \"\",\n" +
				"\t\t\t\t\"show\": 1,\n" +
				"\t\t\t\t\"url\": \"/user/usermanagementlist/functionalpermissionlist/\"\n" +
				"\t\t\t}],\n" +
				"\t\t\t\"name\": \"用户管理\",\n" +
				"\t\t\t\"pageFullPathName\": \"\",\n" +
				"\t\t\t\"show\": 1,\n" +
				"\t\t\t\"url\": \"/user/usermanagementlist/\"\n" +
				"\t\t},\n" +
				"\t\t{\n" +
				"\t\t\t\"function\": [],\n" +
				"\t\t\t\"key\": \"004-000001-000003\",\n" +
				"\t\t\t\"module\": [{\n" +
				"\t\t\t\t\"function\": [],\n" +
				"\t\t\t\t\"key\": \"004-000001-000003-000001\",\n" +
				"\t\t\t\t\"name\": \"新增修改界面\",\n" +
				"\t\t\t\t\"pageFullPathName\": \"\",\n" +
				"\t\t\t\t\"show\": 0,\n" +
				"\t\t\t\t\"url\": \"/user/rolemanagement/addupdaterole/\"\n" +
				"\t\t\t},\n" +
				"\t\t\t{\n" +
				"\t\t\t\t\"function\": [],\n" +
				"\t\t\t\t\"key\": \"004-000001-000003-000002\",\n" +
				"\t\t\t\t\"name\": \"角色授权界面\",\n" +
				"\t\t\t\t\"pageFullPathName\": \"\",\n" +
				"\t\t\t\t\"show\": 0,\n" +
				"\t\t\t\t\"url\": \"/user/rolemanagement/operatingAuthorization/\"\n" +
				"\t\t\t},\n" +
				"\t\t\t{\n" +
				"\t\t\t\t\"function\": [],\n" +
				"\t\t\t\t\"key\": \"004-000001-000003-000003\",\n" +
				"\t\t\t\t\"name\": \"分配用户界面\",\n" +
				"\t\t\t\t\"pageFullPathName\": \"\",\n" +
				"\t\t\t\t\"show\": 0,\n" +
				"\t\t\t\t\"url\": \"/user/rolemanagement/allocUser/\"\n" +
				"\t\t\t}],\n" +
				"\t\t\t\"name\": \"角色管理\",\n" +
				"\t\t\t\"pageFullPathName\": \"\",\n" +
				"\t\t\t\"show\": 1,\n" +
				"\t\t\t\"url\": \"/user/rolemanagement/\"\n" +
				"\t\t}],\n" +
				"\t\t\"name\": \"应用中心\",\n" +
				"\t\t\"url\": \"/user/\"\n" +
				"\t}],\n" +
				"\t\"system\": {\n" +
				"\t\t\"key\": \"004\",\n" +
				"\t\t\"name\": \"应用中心\",\n" +
				"\t\t\"url\": \"/user/\"\n" +
				"\t}\n" +
				"}";
		//若为空

		permissionVO.setSysContext(sysCOntext);
		selectedContext.add(permissionVO);
		roleVO.selectedContext=selectedContext;
		//roleVO.selectedContext = null;
		lstRole.add(roleVO);
		map.put("lstRole",lstRole);
		String json =StringUtility.toJSONString(map);
		System.out.println("==================");
		System.out.println(json);
		MotanSession.initialSession(json);
		ResultVO resultVO =service.updateRolePermission(json);
		System.out.println(resultVO.msg);

	}
	@Test
	public void testsyncUserInfo(){
		System.out.println("****************************");
		long startTime = System.currentTimeMillis();    //获取开始时间

		Map map =new HashMap();
		map.put("operator","linwanxian");
		String json =StringUtility.toJSONString(map);
		ResultVO resultVO =service.syncUserInfo(json);
		System.out.println("====================");
		System.out.println(resultVO.msg);
		long endTime = System.currentTimeMillis();    //获取结束时间
		System.out.println("**************************");
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时

	}

	@Test
	public void testMonitor(){
		Map map =new HashMap();
		map.put("operator","linwanxian");
		String json =StringUtility.toJSONString(map);
		MotanSession.initialSession(json);
		ResultVO resultVO =service.startMonitorMemory(json);
		System.out.println("====================");
		System.out.println(resultVO.msg);
	}
	@Test
	public void  testUpdateUserPermitCache(){
		List<String> lstUser =new ArrayList<>();
		lstUser.add("panyun");
		lstUser.add("linwanxian");
		Map map =new HashMap();
		map.put("oper","linwanxian");
		map.put("lstUser",lstUser);
		String json =StringUtility.toJSONString_NoException(map);
		MotanSession.initialSession(json);
		System.out.println("====================");
		System.out.println(json);
		ResultVO resultVO=service.updateUserPermitCache(json);
		System.out.println(resultVO.msg);
	}
	@Test
	public void getPlatformShopSite(){
		Map map =new HashMap();
		System.out.println("START====================");
		long startTime = System.currentTimeMillis();
        map.put("operator","linwanxian");
		String json =StringUtility.toJSONString(map);
		MotanSession.initialSession(json);
		for (int i=0;i<10;i++) {
			ResultVO resultVO = service.getPlatformShopSite(json);
			System.out.println("========================");
			System.out.println(StringUtility.toJSONString_NoException(resultVO));
			System.out.println("END====================");
			long endTime = System.currentTimeMillis();
			System.out.println(String.format("耗时:[%d]", endTime - startTime));
			try {
				TimeUnit.SECONDS.sleep((long) 0.1);
			} catch (InterruptedException e) {
				logger.error("未知异常",e);
			}
		}
	}
	@Test
    public void syncPlatform(){
        Map map =new HashMap();
        System.out.println(StringUtility.getDateTime_yyyyMMddHHmmssSSS(new Date()));
        System.out.println("START====================");
        map.put("operator","linwanxian");
        String json =StringUtility.toJSONString(map);
        MotanSession.initialSession(json);
        ResultVO resultVO =service.syncPlatform(json);
    }
    @Test
    public void syncShopSite(){
        Map map =new HashMap();
        System.out.println(StringUtility.getDateTime_yyyyMMddHHmmssSSS(new Date()));
        System.out.println("START====================");
        map.put("operator","linwanxian");
        String json =StringUtility.toJSONString(map);
        MotanSession.initialSession(json);
        ResultVO resultVO =service.syncShopSite(json);
		System.out.println(StringUtility.toJSONString(resultVO));
	}
    @Test
	public void fuzzSearchPersonByName(){
		Map map =new HashMap();
		map.put("operator","linwanxian");
		map.put("name","pan");
		String json =StringUtility.toJSONString(map);
		MotanSession.initialSession(json);

		System.out.println(json);
		System.out.println("START====================" +StringUtility.dt2Str(new Date(),"yyyy-MM-dd HH:mm:sss"));
		ResultVO resultVO =service.fuzzSearchPersonByName(json);
		System.out.println(StringUtility.toJSONString(resultVO));
		System.out.println("END====================" +StringUtility.dt2Str(new Date(),"yyyy-MM-dd HH:mm:sss"));
	}
	@Test
	public void test_updateApiPrefixCache(){
		Map map =new HashMap();
		map.put("operator","linwanxian");
		String json =StringUtility.toJSONString(map);
		MotanSession.initialSession(json);

		System.out.println(json);
		System.out.println("START====================" +StringUtility.dt2Str(new Date(),"yyyy-MM-dd HH:mm:sss"));
		ResultVO resultVO =service.updateApiPrefixCache(json);
		System.out.println(StringUtility.toJSONString(resultVO));
		System.out.println("END====================" +StringUtility.dt2Str(new Date(),"yyyy-MM-dd HH:mm:sss"));
	}

	@Test
	public void test_getRolesByInfo(){

		int pageNumber =1;
		int pageData =20;
		RoleVO roleVO = new RoleVO();
		roleVO.roleName ="urc";
		List<Long> roleIdList =new ArrayList<>();
		roleIdList.add(Long.valueOf("1531973851898000027"));
		Map map =new HashMap();
		map.put("operator","linwanxian");
		map.put("roleIds",roleIdList);
		map.put("pageNumber",pageNumber);
		map.put("pageData",pageData);
		String json =StringUtility.toJSONString(map);
		MotanSession.initialSession(json);
		ResultVO resultVO =service.getRolesByInfo(json);
		System.out.println(StringUtility.toJSONString(resultVO));

	}
	@Test
	public void test_getRoleByRoleId(){
		Map map =new HashMap();
		map.put("operator","wujianghui");
		map.put("roleId","1531973851898000027");
		String json =StringUtility.toJSONString(map);
		MotanSession.initialSession(json);
		ResultVO resultVO =service.getRoleByRoleId(json);
		System.out.println(StringUtility.toJSONString(resultVO));
	}
	@Test
	public void testfuzzSearchPersonByName(){
		String json = "\t\t{\n" +
				"\t\t\t\"operator\":\"songguanye\",\n" +
				"\t\t\t\"name\":\"程立夫\"\n" +
				"\t\t}";
		MotanSession.initialSession(json);
		service.fuzzSearchPersonByName(json);

	}


}
