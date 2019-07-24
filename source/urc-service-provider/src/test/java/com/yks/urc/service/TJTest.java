package com.yks.urc.service;

import com.yks.urc.fw.StringUtility;
import com.yks.urc.fw.constant.StringConstant;
import com.yks.urc.motan.MotanSession;
import com.yks.urc.motan.service.api.IUrcService;
import com.yks.urc.userValidate.bp.impl.UserValidateBp;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class TJTest extends BaseServiceTest {
    @Autowired
    IUrcService iUrcService;
    @Test
    public void getAllOrgTree_test(){
        System.out.println(StringUtility.toJSONString( iUrcService.getAllOrgTree()));
    }
    @Test
    public void getAllOrgTreeAndUser_test(){
        System.out.println(StringUtility.toJSONString(iUrcService.getAllOrgTreeAndUser()));

    }
    @Autowired
    private UserValidateBp userValidateBp;
    @Test
    public  void test(){
        Map<String, String> map = new HashMap<>();
        map.put("apiUrl", "/urc/motan/service/api/IUrcService/getAllFuncPermit");
        map.put("moduleUrl", "/");
        map.put(StringConstant.operator, "tangjianbo");
        map.put(StringConstant.ticket, "bfba159f79a0f4b77ee82fabd41507f2");
        map.put(StringConstant.ip, "pyIP");
        map.put(StringConstant.funcVersion, "eb1043692883ef9010cd6cdc8b624e90");
        map.put(StringConstant.sysKey, "001");
        userValidateBp.funcPermitValidate(map);
    }
    @Test
    public  void test_addUrcWhiteApi(){
        String json="{\n" +
                "    \"data\":{\n" +
                "        \"whiteApiUrl\":[\n" +
                "            \"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet\",\n" +
                "            \"fsjkladhflkas/fkasd\"\n" +
                "        ]\n" +
                "    },\n" +
                "    \"ticket\":\"aeb00772e1596dcaa8faa0c82c843562\",\n" +
                "    \"operator\":\"tangjianbo\",\n" +
                "    \"funcVersion\":\"d7a3cada5b3a770a89cd097b564f664a\",\n" +
                "    \"moduleUrl\":\"/order/basicdata/tracknumbermanage/\",\n" +
                "    \"personName\":\"tangjianbo\",\n" +
                "    \"deviceName\":\"Chrome浏览器\"\n" +
                "}";
        MotanSession.initialSession(json);
        System.out.println(StringUtility.toJSONString(iUrcService.addUrcWhiteApi(json)));
    }
    @Test
    public  void tets_deleteWhiteApi(){
        String json="{\n" +
                "    \"data\":\n" +
                "        {\n" +
                "            \"whiteApiUrl\":\"/urc/motan/service/api/IUrcService/TJBtest\"\n" +
                "           \n" +
                "        },\n" +
                "    \"ticket\":\"aeb00772e1596dcaa8faa0c82c843562\",\n" +
                "    \"operator\":\"tangjianbo\",\n" +
                "    \"funcVersion\":\"d7a3cada5b3a770a89cd097b564f664a\",\n" +
                "    \"moduleUrl\":\"/order/basicdata/tracknumbermanage/\",\n" +
                "    \"personName\":\"tangjianbo\",\n" +
                "    \"deviceName\":\"Chrome浏览器\"\n" +
                "}\n";
        MotanSession.initialSession(json);
        System.out.println(StringUtility.toJSONString(iUrcService.deleteWhiteApi(json)));
    }
    @Test
    public void test_(){
        return;
    }

}
