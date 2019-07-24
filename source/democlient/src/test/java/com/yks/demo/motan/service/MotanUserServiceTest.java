/*
package com.yks.demo.motan.service;

import com.alibaba.fastjson.JSONObject;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import com.yks.demo.DemoClientApplication;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.fw.constant.StringConstant;
import com.yks.urc.motan.service.api.IUrcService;
import com.yks.urc.vo.LoginRespVO;
import com.yks.urc.vo.ResultVO;
import com.yks.urc.vo.UserVO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoClientApplication.class)
public class MotanUserServiceTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @MotanReferer
    private IUrcService urcService;

 @MotanReferer
    private IOmsApiFace omsApiFace;

    @Test
    public void getStoreAccount_Test() {
        System.out.println(String.format("getStoreAccount_Test:\r\n%s", omsApiFace.getStoreAccount()));
    }


   @MotanReferer
   private IOrderManageConfigApi orderManageConfigApi;

   @Test
   public void IOrderManageConfigApi_Test(){
       System.out.println(StringUtility.toJSONString_NoException(orderManageConfigApi.queryConfigRuleWarehouse("{}")));
   }

    @Test
    public void getPlatformShopSite_Test() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("operator", "py");
        for (int i = 0; i < 100; i++) {
            ResultVO rslt = null;
            rslt = urcService.getPlatformShopSite(StringUtility.toJSONString_NoException(jsonObject));
//            rslt = urcService.operIsSuperAdmin(StringUtility.toJSONString_NoException(jsonObject));
            System.out.println("getPlatformShopSite_Test:\r\n" + StringUtility.toJSONString_NoException(rslt));

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // @Test
    public void testFilter() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("operator", "py");
        jsonObject.put("newRoleName", "admin2");
        jsonObject.put("roleId", "");

        ResultVO<Integer> rslt = urcService.checkDuplicateRoleName(jsonObject.toJSONString());
        System.out.println(">>>>>>>>>>>>>>>>>>" + StringUtility.toJSONString_NoException(rslt));
    }

    // @Test
    public void testSayHello() {
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("userName", "dcadmin");
            map.put("pwd", "Ldap_test");
            map.put("ip", "127.0.0.1");
            ResultVO<LoginRespVO> rslt = urcService.login(map);
            System.out.println(StringUtility.toJSONString_NoException(rslt));

            // for (int i = 0; i < 10; i++) {
            // authUser.userName = "panyun" + i;
            // urcService.login(curUser, authUser);
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // @Test
    public void logout_Test() {
        Map<String, String> map = new HashMap<>();
        map.put(StringConstant.operator, "test2");
        String jsonStr = StringUtility.toJSONString_NoException(map);
        System.out.println("----------------------" + StringUtility.toJSONString_NoException(urcService.logout(jsonStr)));
    }

    // @Test
    public void getAllOrgTree_Test() {
        System.out.println("----------------------" + StringUtility.toJSONString_NoException((urcService.getAllOrgTree())));
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
        System.out.println("----------------------START " +
                StringUtility.getDateTime_yyyyMMddHHmmssSSS(new Date())
                + StringUtility.toJSONString_NoException(urcService.getUserByUserInfo(jsonStr)));

        System.out.println("----------------------END " +
                StringUtility.getDateTime_yyyyMMddHHmmssSSS(new Date()));
    }

    @Test
    public void testGetAllFuncPermit() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("operator", "linwanxian");
        String jsonStr = StringUtility.toJSONString_NoException(map);
        System.out.println("----------------------" + StringUtility.toJSONString_NoException(urcService.getAllFuncPermit(jsonStr)));
    }

    @Test
    public void test_funcPermitValidate() {
        Map<String, String> map = new HashMap<>();
        map.put("apiUrl", "/urc/motan/service/api/IUrcService/getAllFuncPermit");
        map.put("moduleUrl", "/");
        map.put(StringConstant.operator, "dcadmin");
        map.put(StringConstant.ticket, "f57be85c55187292236b0d95ba719a43");
        map.put(StringConstant.ip, "192.168.201.62");
        map.put(StringConstant.funcVersion, "007d787e2b15e66fd9451f5adef0d2f5");
        map.put(StringConstant.sysKey, "004");
        System.out.println("----------------------" + StringUtility.toJSONString_NoException(urcService.funcPermitValidate(map)));
    }

    @Test
    public void importPermit_Test() throws IOException {
        String strJson1 = StringUtility.inputStream2String(ClassLoader.getSystemResourceAsStream("oms.json"));
        logger.info(strJson1);
        System.out.println(StringUtility.toJSONString_NoException(urcService.importSysPermit(strJson1)));
    }

    @Test
    public void updateRolePermission_Test() {
        Map<String, Object> mapArg = new HashMap<>();

        String jsonStr;
//		urcService.updateRolePermission(jsonStr);
    }

    @Test
    public void login_Test() {
        // 登陆+获取功能权限版本号+鉴权
        String ip = "192.168.201.62";
        Map<String, String> map = new HashMap<>();
        UserVO authUser = new UserVO();
        map.put("userName", "linwanxian");
        map.put("pwd", "linwx123");
        map.put("ip", ip);
        ResultVO<LoginRespVO> loginResp = urcService.login(map);

        System.out.println("------LOGIN-----------------" + StringUtility.toJSONString_NoException(loginResp));
        // ResultVO<LoginRespVO> loginResp = new ResultVO<LoginRespVO>();
        // JSONObject loginResp = StringUtility.parseString(strResp);
//        map.put("operator", "dcadmin");
//        String jsonStr = StringUtility.toJSONString_NoException(map);
//        ResultVO<GetAllFuncPermitRespVO> allFuncResp = urcService.getAllFuncPermit(jsonStr);
//        System.out.println("------getAllFuncPermit-----------------" + StringUtility.toJSONString_NoException(allFuncResp));
//
//        String strSysKey = "004";
//
//        map.put("apiUrl", "/api/grab/smt/batchMarking");
//        map.put("moduleUrl", "/");
//        map.put(StringConstant.operator, "dcadmin");
//        map.put(StringConstant.ticket, loginResp.data.ticket);
//        map.put(StringConstant.ip, ip);
//        map.put(StringConstant.funcVersion, allFuncResp.data.funcVersion);// "eb1043692883ef9010cd6cdc8b624e90");
//        map.put(StringConstant.sysKey, strSysKey);
//        System.out.println("------funcPermitValidate----------------" + urcService.funcPermitValidate(map));
    }

    @Test
    public void startMonitor_Test() {
        Map<String, String> map = new HashMap<>();
        map.put(StringConstant.operator, "panyun");
        urcService.startMonitorMemory(StringUtility.toJSONString_NoException(map));
    }

    @Test
    public void endMonitorMemory_Test() {
        Map<String, String> map = new HashMap<>();
        map.put(StringConstant.operator, "panyun");
        urcService.endMonitorMemory(StringUtility.toJSONString_NoException(map));
    }
}
*/
