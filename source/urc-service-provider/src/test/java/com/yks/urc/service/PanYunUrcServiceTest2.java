/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/6/9
 * @since 1.0.0
 */
package com.yks.urc.service;

import com.alibaba.fastjson.JSONObject;
import com.yks.distributed.cache.core.Cache;
import com.yks.distributed.cache.core.DistributedCacheBuilder;
import com.yks.mq.client.MQConsumerClient;
import com.yks.mq.client.MQConsumerClient.MessageCallBack;
import com.yks.urc.cache.bp.api.ICacheBp;
import com.yks.urc.dingding.client.DingApiProxy;
import com.yks.urc.entity.PermissionDO;
import com.yks.urc.entity.RoleDO;
import com.yks.urc.fw.EncryptHelper;
import com.yks.urc.fw.HttpUtility;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.fw.constant.StringConstant;
import com.yks.urc.ldap.bp.api.ILdapBp;
import com.yks.urc.mapper.IRoleMapper;
import com.yks.urc.motan.MotanSession;
import com.yks.urc.motan.service.api.IUrcService;
import com.yks.urc.mq.bp.api.IMqBp;
import com.yks.urc.permitStat.bp.api.IPermitStatBp;
import com.yks.urc.seq.bp.api.ISeqBp;
import com.yks.urc.service.api.IOrganizationService;
import com.yks.urc.service.api.IPermissionService;
import com.yks.urc.service.api.IUserService;
import com.yks.urc.user.bp.api.IUserBp;
import com.yks.urc.userValidate.bp.api.IUserValidateBp;
import com.yks.urc.vo.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.*;

//@Component
public class PanYunUrcServiceTest2 extends BaseServiceTest {
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

    @Test
    public void mergeFuncJson2Obj_Test() throws IOException {
        String strJson1 = StringUtility.inputStream2String(ClassLoader.getSystemResourceAsStream("func1.json"));
        String strJson2 = StringUtility.inputStream2String(ClassLoader.getSystemResourceAsStream("func2.json"));
        List<String> lstJson = new ArrayList<>();
        lstJson.add(strJson1);
        lstJson.add(strJson2);
        System.out.println(userValidateBp.mergeFuncJson(lstJson));
    }

    public void testIPermissionService() throws Exception {
        String strJson1 = StringUtility.inputStream2String(ClassLoader.getSystemResourceAsStream("oms.json"));
        String strEncrypt = EncryptHelper.encryptAes_Base64(strJson1, aesPwd);
        System.out.println(permissionService.importSysPermit(strEncrypt));
    }

    @Test
    public void utcTime_Test() {
        System.out.println(StringUtility.dt2Str(new Date(), "yyyy-MM-dd'T'HH:mm:ssZ"));
    }

    @Test
    public void mqConsumer_Test() {
        String topic = String.format("URC_USER_DATARULE_%s", "006");
        MessageCallBack callBack = new MessageCallBack() {

            @Override
            public void call(String arg0, String arg1) {
                System.out.println(String.format("-----------------接收到消息:%s %s", arg0, arg1));
            }
        };
        new MQConsumerClient().subscribe(topic, callBack);
    }

    @Test
    public void mq_Test() {
        DataRuleVO dr = new DataRuleVO();
        dr.userName = "py";
        dr.lstDataRuleSys = new ArrayList<>();
        DataRuleSysVO e = new DataRuleSysVO();
        e.sysKey = "006";
        e.userName = "panyun";

        ExpressionVO exp = new ExpressionVO();
        exp.setIsAnd(1);
        List<ExpressionVO> subWhereClause = new ArrayList<>();
        ExpressionVO subExp = new ExpressionVO();
        subExp.setFieldCode("F_Platform_Shop_Site");
        subExp.setEntityCode("E_PlatformShopSite");
        subExp.setOper("in");
        List<String> operValuesArr = new ArrayList<>();
        subExp.setOperValuesArr(operValuesArr);
        subWhereClause.add(subExp);
        exp.setSubWhereClause(subWhereClause);
        e.row = exp;

        dr.lstDataRuleSys.add(e);
        mqBp.send2Mq(dr);
    }

    public void testUpdateExpiredRole() {
        List<RoleDO> lstRole = roleMapper.updateAllExpiredRole();
        System.out.println(StringUtility.toJSONString_NoException(lstRole));
    }

    @Test
    public void getAllFuncPermit_Test() {
        System.out.println(StringUtility.toJSONString_NoException(userBp.getAllFuncPermit("panyun2")));
    }

    @Test
    public void funcPermitValidate_Test() {
        Map<String, String> map = new HashMap<>();
        map.put("apiUrl", "/urc/motan/service/api/IUrcService/getMavenPackageTime");
        map.put(StringConstant.operator, "songguanye");
        map.put(StringConstant.ticket, "75f2b8637088e9abed918ec74640ecba");
        map.put(StringConstant.ip, "192.168.93.176");
        map.put(StringConstant.funcVersion, "d8f7cf702a69da5f5c2a878adc8e9cf2");
        map.put(StringConstant.deviceName, "Chrome浏览器");
        // map.put(StringConstant.sysKey, "001");
        System.out.println(StringUtility.toJSONString(map));
        System.out.println("----------------------" + StringUtility.toJSONString_NoException(userService.funcPermitValidate(map)));
    }

    @Autowired
    private ILdapBp ldapBp;

    @Test
    public void ldap_Test() {
        String userName = "panyun";
        String pwd = "ASDFhjkl12345";
        System.out.println("validateUser:" + ldapBp.validateUser(userName, pwd));
    }

    @Test
    public void login_Test() {
        Map map = new HashMap();
        map.put(StringConstant.userName, "songguanye");
        map.put(StringConstant.pwd, "670317483sgy???");
        map.put(StringConstant.userName, "songguanye");
        map.put(StringConstant.ip, "192.168.93.176");
        map.put(StringConstant.deviceName, "Chrome浏览器");
        System.out.println("------LOGIN-----------------" + StringUtility.toJSONString_NoException(userService.login(map)));
    }

    @Test
    public void logout_Test() {
        Map<String, String> map = new HashMap<>();
        map.put("apiUrl", "/urc/motan/service/api/IUrcService/logout");
        map.put(StringConstant.operator, "songguanye");
        map.put(StringConstant.ticket, "d8d4009eb438cf644fcb088602d0db04");
        map.put(StringConstant.ip, "192.168.121.140");
        map.put(StringConstant.funcVersion, "999f7e17655abe2f917a7b667f741d3c");
        String jsonStr = StringUtility.toJSONString_NoException(map);
        System.out.println(StringUtility.toJSONString_NoException(service.logout(jsonStr)));
    }

    @Autowired
    private DingApiProxy myDingApiProxy;

    public void syncDingOrgAndUser_Test() throws Exception {
        myDingApiProxy.getDingAccessToken();
    }

    @Test
    public void getUserByUserInfo_Test() {
        Map<String, Object> map = new HashMap<>();
        map.put("operator", "panyun");
        map.put("pageNumber", 1);
        map.put("pageData", 22);
        Map<String, String> mapUser = new HashMap<>();
        mapUser.put("jobNumber", "");
        mapUser.put("phoneNum", "");
//        mapUser.put("personName", "潘韵");
        map.put("user", mapUser);
        String jsonStr = StringUtility.toJSONString_NoException(map);
        System.out.println("----------------------" +
                StringUtility.toJSONString_NoException(service.getUserByUserInfo(jsonStr)));
    }

    public void getUserInfo_Test() throws Exception {
        // userBp.SynUserFromUserInfo("panyun");
        String httpOrgCreateTest = "https://userinfo.youkeshu.com/api/get_token";
        JSONObject object = new JSONObject();
        object.put("username", "panyun");
        object.put("password", "ASDFhjkl1234");
        String httpOrgCreateTestRtn = HttpUtility.sendPost(httpOrgCreateTest, object.toJSONString());
        System.out.println("result:" + httpOrgCreateTestRtn);
    }

    @Test
    public void assignAllPermit2Role_Test() {
        // 将所有系统的所有功能权限授予指定角色
        Long roleId = 1529746874242000015L;
        Map<String, Object> map = new HashMap<>();
        map.put(StringConstant.operator, "panyun");
        map.put("roleId", roleId);
        String jsonStr = StringUtility.toJSONString_NoException(map);
        MotanSession.initialSession(map);
        service.assignAllPermit2Role(jsonStr);
    }

    @Test
    public void updateUserPermitCache_Test() {
        List<String> lstUserName = new ArrayList<>();
        lstUserName.add("panyun");
        lstUserName.add("renmaohua");
        lstUserName.add("tangfeng");
        lstUserName.add("tangyong");
        lstUserName.add("weijie");
        lstUserName.add("yangbo");
        lstUserName.add("chenglifu");
        lstUserName.add("chensi");
        lstUserName.add("mengyuhua");
        lstUserName.add("chensi2");
        lstUserName.add("xieyi1");
        lstUserName.add("xieyi2");
        lstUserName.add("liujun");
        lstUserName.add("huangpeiqin");
        lstUserName.add("houyunfeng");
        lstUserName.add("panxi");

        lstUserName.clear();
        lstUserName.add("chenglifu1");
        permitStatBp.updateUserPermitCache(lstUserName);
        // permitStatBp.updateUserPermitCache(lstUserName);
    }

    @Test
    public void importSysPermit_Test() throws IOException {
        String strJson1 = StringUtility.inputStream2String(ClassLoader.getSystemResourceAsStream("oms.json"));
        MotanSession.initialSession(strJson1);
        permissionService.importSysPermit(strJson1);
    }

    @Test
    public void cacheBp_Test() {
        UserVO u = new UserVO();
        u.ip = "ip";
        u.userName = "py";
        u.ticket = "ticket";
        cacheBp.insertUser(u);
        UserVO uFromCache = cacheBp.getUser(u.userName);
        System.out.println(String.format("---------%s", StringUtility.toJSONString_NoException(uFromCache)));

        cacheBp.insertSysContext("110", "sysContext");
        System.out.println(String.format("---------%s", cacheBp.getSysContext("110")));

        GetAllFuncPermitRespVO permitCache = new GetAllFuncPermitRespVO();
        permitCache.funcVersion = "funcVersion";
        permitCache.lstUserSysVO = new ArrayList<>();
        UserSysVO e = new UserSysVO();
        e.sysKey = "110";
        e.context = "110 context";
//        permitCache.lstUserSysVO.add(e);
        cacheBp.insertUserFunc(u.userName, permitCache);
        GetAllFuncPermitRespVO pRslt = cacheBp.getUserFunc(u.userName);
        System.out.println(String.format("---------%s", StringUtility.toJSONString_NoException(pRslt)));

        cacheBp.removeUser(u.userName);
        cacheBp.setDingAccessToken("dingAccessToken", "test");
        List<PermissionDO> lst = new ArrayList<>();
        PermissionDO pDO = new PermissionDO();
        pDO.setSysKey("111");
        pDO.setApiUrlPrefixJson("apiUrlPrefixJson");
        lst.add(pDO);
        cacheBp.setSysApiUrlPrefix(lst);

        System.out.println("------------ testRoleId: " + cacheBp.getNextSeq("testRoleId"));
    }

    @Test
    public void cache_Test() {
        DistributedCacheBuilder b = DistributedCacheBuilder.newBuilder().config("/cache.properties");

//        Cache cacheTest = b.cacheName("cache").expire(50).build();
//        cacheTest.put("kobe", "bryant");

        Cache cacheForever = b.cacheName("user_sys_func_panyun2").build();
//        Map<String, String> allCacheItem = cacheForever.getAll();

//        Map<String, String> map = new HashMap<>();
//        map.put("001", "URC");
//        map.put("002", "OMS");
        String strKey = "sys_api_url_prefix";
//        cacheForever.put("a", "panyun");
//        cacheForever.put("a1", "b1");
//        cacheForever.remove("a1");
//        cacheForever.batchPut();
//        cacheForever.clear();
        Map<String, String> map = cacheForever.getAll();
        System.out.println(String.format("----> %s",
                StringUtility.toJSONString_NoException(map)));
//        while (true) {
//            try {
//                System.out.println(String.format("----> %s %s", StringUtility.getDateTime_yyyyMMddHHmmssSSS(new Date())
//                        , cacheForever.get("a")));
//
//                System.out.println(String.format("roleId=%s", cacheForever.incrSequence("roleId")));
//
//                Thread.sleep(2000L);
//                break;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    private void put(Cache cacheForever, String strKey, Map<String, String> map) {
        if (map == null) return;
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            cacheForever.put(String.format("%s:%s", strKey, key), map.get(key));
        }
    }

    @Test
    public void seq_Test() {
        RoleDO roleDO = new RoleDO();
        roleDO.setRoleId(seqBp.getNextRoleId());
        roleDO.setRoleName("py");
//        roleMapper.insert(roleDO);
        System.out.println(StringUtility.toJSONString_NoException(roleDO));
        // System.out.println(seqBp.getNextSeq("roleId"));
    }

    @Test
    public void getAllFuc_Test() {
        Map<String, Object> map = new HashMap<>();
        map.put(StringConstant.operator, "hanhanzhou");
        String jsonStr = StringUtility.toJSONString_NoException(map);
        System.out.println(StringUtility.toJSONString_NoException(service.getAllFuncPermit(jsonStr)));
    }

    public void createRole_Test() {
        Map<String, Object> map = new HashMap<>();
        map.put(StringConstant.operator, "panyun");
        RoleVO role = new RoleVO();
        role.roleName = "panyunTest2";
        map.put("role", role);
        String jsonStr = StringUtility.toJSONString_NoException(map);
        service.addOrUpdateRoleInfo(jsonStr);
    }

    @Test
    public void mybatis_log_Test() {
        Long roleId = 1529649147479000001L;
        RoleDO role = roleMapper.getRoleByRoleId(String.valueOf(roleId));
        System.out.println(StringUtility.toJSONString_NoException(role));
    }


    @Autowired
    private IOrganizationService organizationService;

    @Test
    public void org_Test(){

        ResultVO rslt= organizationService.getAllOrgTreeAndUser();
        System.out.println(StringUtility.toJSONString_NoException(rslt));
    }
    @Test
    public void testResetPwdGetVerificationCode(){
        String json = "{\"userName\":\"songguanye\",\"mobile\":\"18376740674\",\"ticket\":\"\",\"operator\":\"\",\"personName\":\"\",\"funcVersion\":\"\",\"moduleUrl\":\"/login/forget/\",\"deviceName\":\"Chrome浏览器\"}";
        MotanSession.initialSession(json);
        service.resetPwdGetVerificationCode(json);
    }
}
