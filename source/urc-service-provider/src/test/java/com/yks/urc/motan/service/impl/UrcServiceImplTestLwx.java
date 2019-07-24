package com.yks.urc.motan.service.impl;

import com.yks.urc.fw.StringUtility;
import com.yks.urc.motan.MotanSession;
import com.yks.urc.motan.service.api.IUrcService;
import com.yks.urc.service.BaseServiceTest;
import com.yks.urc.vo.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.*;

public class UrcServiceImplTestLwx extends BaseServiceTest {
    @Autowired
    private IUrcService service;
    private ResultVO resultVO;
    private Map map = new HashMap();
    private Map dataMap = new HashMap();
    private String operator = "linwanxian";
    private int pageData = 20;
    private int pageNumber = 1;
    private String roleId = "1529635932385000003";

    @Test
    public void syncUserInfo() throws Exception {

    }

    @Test
    public void login() throws Exception {
        map.put("userName", "songguanye");
        map.put("pwd", "670317483sgy???");
        map.put("ip", "192.168.93.176");
        map.put("deviceName", "Chrome浏览器");
        System.out.println(StringUtility.toJSONString(map));
        ResultVO<LoginRespVO>  resultVO = service.login(map);
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void syncDingOrgAndUser() throws Exception {
    }

    @Test
    public void getUserByDingOrgId() throws Exception {
    }

    @Test
    public void getUserByUserInfo() throws Exception {
    }

    @Test
    public void getAllOrgTree() throws Exception {
    }

    @Test
    public void getUsersByUserInfo() throws Exception {
    }

    @Test
    public void assignDataRuleTempl2User() throws Exception {
    }

    @Test
    public void getDataRuleTemplByTemplId() throws Exception {
    }

    @Test
    public void getDataRuleTempl() throws Exception {
    }

    @Test
    public void getPlatformList() throws Exception {
        map.put("operator", operator);
        String json = StringUtility.toJSONString(map);
        MotanSession.initialSession(json);
        System.out.println(json);
        resultVO = service.getPlatformList(json);
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void getShopList() throws Exception {
        map.put("operator", operator);
        map.put("platform", "亚马逊");
        String json = StringUtility.toJSONString(map);
        MotanSession.initialSession(json);
        System.out.println(json);
        resultVO = service.getShopList(json);
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void checkDuplicateRoleName() throws Exception {
        String json = "{\"newRoleName\":\"开发环境专用权限,勿动\",\"roleId\":\"1546589709303000002\",\"ticket\":\"0ebac79b03605693d7e3613274bd209a\",\"operator\":\"songguanye\",\"personName\":\"songguanye\",\"funcVersion\":\"34bf269604f019931cef4e39d0c54fab\",\"moduleUrl\":\"/user/rolemanagement/addupdaterole/\",\"deviceName\":\"Chrome浏览器\"}";
        MotanSession.initialSession(json);
        System.out.println(service.checkDuplicateRoleName(json));

    }

    @Test
    public void copyRole() throws Exception {
        map.put("newRoleName", "adminTest4");
        map.put("sourceRoleId", "1532146669816000034");
        map.put("operator", operator);
        String json = StringUtility.toJSONString(map);
        MotanSession.initialSession(json);
        System.out.println(json);
        resultVO = service.copyRole(json);
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void getAllFuncPermit() throws Exception {
        map.put("operator", "huangpeiqin");
        // map.put("ticket","d49b892e591dc3e098fd02f34410e5f5");
        String json = StringUtility.toJSONString(map);
        MotanSession.initialSession(json);
        System.out.println(json);
        resultVO = service.getAllFuncPermit(json);
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void funcPermitValidate() throws Exception {

    }

    @Test
    public void getUserByRoleId() throws Exception {
    }

    @Test
    public void getRoleUser() throws Exception {
        String strJson1 = StringUtility.inputStream2String(ClassLoader.getSystemResourceAsStream("getRoleUser.json"));
        MotanSession.initialSession(strJson1);
        resultVO =service.getRoleUser(strJson1);
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void getMyDataRuleTempl() throws Exception {
    }

    @Test
    public void getDataRuleByUser() throws Exception {
    }

    @Test
    public void importSysPermit() throws Exception {
        String strJson1 = StringUtility.inputStream2String(ClassLoader.getSystemResourceAsStream("oms.json"));
        MotanSession.initialSession(strJson1);
        resultVO =service.importSysPermit(strJson1);
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void getUserAuthorizablePermission() throws Exception {
        map.put("operator","huanghongfei");
        String json = StringUtility.toJSONString(map);
        MotanSession.initialSession(json);
        resultVO=service.getUserAuthorizablePermission(json);
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void getRolePermission() throws Exception {

        List<String> lstRoleId = new ArrayList<>();
        lstRoleId.add("1538624216130000003");
        map.put("operator", "songguanye");
        map.put("lstRoleId", lstRoleId);
        String json = StringUtility.toJSONString(map);
        MotanSession.initialSession(json);
        resultVO = service.getRolePermission(json);
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void getUserByUserName() throws Exception {
    }

    @Test
    public void getMyAuthWay() throws Exception {
        map.put("operator", operator);
        String json = StringUtility.toJSONString(map);
        MotanSession.initialSession(json);
        resultVO = service.getMyAuthWay(json);
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void fuzzySearchUsersByUserName() throws Exception {
        map.put("name", "pei");
        map.put("operator", operator);
        String json = StringUtility.toJSONString(map);
        MotanSession.initialSession(json);
        resultVO = service.fuzzSearchPersonByName(json);
        System.out.println(StringUtility.toJSONString(resultVO));

    }

    @Test
    public void updateUsersOfRole() throws Exception {
        List<RoleVO> roleVOS = new ArrayList<>();
        RoleVO roleVO = new RoleVO();
        roleVO.roleId = "1539595765302000077";
        roleVO.lstUserName = new ArrayList<>();
        roleVO.lstUserName.add("test333");
        roleVOS.add(roleVO);
        map.put("lstRole", roleVOS);
        map.put("operator", "test");
        String json = StringUtility.toJSONString(map);
        MotanSession.initialSession(json);
        resultVO=service.updateUsersOfRole(json);
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void updateRolePermission() throws Exception {

    }

    @Test
    public void logout() throws Exception {

    }

    @Test
    public void getMavenPackageTime() throws Exception {
    }

    @Test
    public void addOrUpdateDataRuleTempl() throws Exception {
    }

    @Test
    public void deleteDataRuleTempl() throws Exception {
    }

    @Test
    public void checkDuplicateTemplName() throws Exception {
    }

    @Test
    public void getUserPermissionList() throws Exception {
    }

    @Test
    public void addOrUpdateDataRule() throws Exception {
//        String json = StringUtility.inputStream2String(new FileInputStream(new File("F:\\feature\\urc-func_dev\\urcenter\\source\\urc-service-provider\\src\\test\\resources\\addDataRule1.json")));
        String json = "{\"data\":{\"lstUserName\":[\"chaozhaohui\"],\"lstDataRuleSys\":[{\"sysKey\":\"001\",\"sysName\":\"订单\",\"row\":{\"isAnd\":1,\"subWhereClause\":[{\"entityCode\":\"E_PlatformShopSite\",\"entityName\":\"【平台】【账号】【站点】\",\"expressionId\":1546563992270000600,\"fieldCode\":\"F_PlatformShopSite\",\"parentExpressionId\":1546563992269000700,\"operValuesArr\":[]}]}},{\"sysKey\":\"011\",\"sysName\":\"基础资料\",\"row\":{\"isAnd\":1,\"subWhereClause\":[{\"entityCode\":\"E_StatisticsSystem\",\"entityName\":\"[品类][中文名称][可用库存][成本价]\",\"expressionId\":1546563992273000700,\"fieldCode\":\"F_StatisticsSystem\",\"operValuesArr\":[\"{\\\"value\\\":\\\"firstCategory\\\",\\\"name\\\":\\\"品类\\\",\\\"data\\\":[{\\\"cateId\\\":\\\"3354\\\",\\\"cateNameCn\\\":\\\"健康美容个人护品\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"3480\\\",\\\"cateNameCn\\\":\\\"彩妆\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9038\\\",\\\"cateNameCn\\\":\\\"BB霜/CC霜\\\"},{\\\"cateId\\\":\\\"9039\\\",\\\"cateNameCn\\\":\\\"彩妆套装\\\"},{\\\"cateId\\\":\\\"9040\\\",\\\"cateNameCn\\\":\\\"彩妆用具套件\\\"},{\\\"cateId\\\":\\\"9041\\\",\\\"cateNameCn\\\":\\\"唇彩\\\"},{\\\"cateId\\\":\\\"9042\\\",\\\"cateNameCn\\\":\\\"唇线笔\\\"},{\\\"cateId\\\":\\\"9043\\\",\\\"cateNameCn\\\":\\\"底妆\\\"},{\\\"cateId\\\":\\\"9044\\\",\\\"cateNameCn\\\":\\\"底妆/遮瑕\\\"},{\\\"cateId\\\":\\\"9045\\\",\\\"cateNameCn\\\":\\\"粉饼/蜜粉/散粉\\\"},{\\\"cateId\\\":\\\"9046\\\",\\\"cateNameCn\\\":\\\"高光/阴影\\\"},{\\\"cateId\\\":\\\"9047\\\",\\\"cateNameCn\\\":\\\"隔离/妆前/打底\\\"},{\\\"cateId\\\":\\\"9048\\\",\\\"cateNameCn\\\":\\\"化妆剪\\\"},{\\\"cateId\\\":\\\"9049\\\",\\\"cateNameCn\\\":\\\"化妆扑\\\"},{\\\"cateId\\\":\\\"9050\\\",\\\"cateNameCn\\\":\\\"化妆刷\\\"},{\\\"cateId\\\":\\\"9051\\\",\\\"cateNameCn\\\":\\\"假睫毛\\\"},{\\\"cateId\\\":\\\"9052\\\",\\\"cateNameCn\\\":\\\"睫毛膏\\\"},{\\\"cateId\\\":\\\"9053\\\",\\\"cateNameCn\\\":\\\"睫毛夹\\\"},{\\\"cateId\\\":\\\"9054\\\",\\\"cateNameCn\\\":\\\"睫毛胶\\\"},{\\\"cateId\\\":\\\"9055\\\",\\\"cateNameCn\\\":\\\"睫毛增长液\\\"},{\\\"cateId\\\":\\\"9056\\\",\\\"cateNameCn\\\":\\\"口红\\\"},{\\\"cateId\\\":\\\"9057\\\",\\\"cateNameCn\\\":\\\"眉粉/眉笔/眉膏\\\"},{\\\"cateId\\\":\\\"9058\\\",\\\"cateNameCn\\\":\\\"眉模具\\\"},{\\\"cateId\\\":\\\"9059\\\",\\\"cateNameCn\\\":\\\"眉钳\\\"},{\\\"cateId\\\":\\\"9060\\\",\\\"cateNameCn\\\":\\\"润唇膏\\\"},{\\\"cateId\\\":\\\"9061\\\",\\\"cateNameCn\\\":\\\"闪粉\\\"},{\\\"cateId\\\":\\\"9062\\\",\\\"cateNameCn\\\":\\\"闪光粉\\\"},{\\\"cateId\\\":\\\"9063\\\",\\\"cateNameCn\\\":\\\"身体彩绘\\\"},{\\\"cateId\\\":\\\"9064\\\",\\\"cateNameCn\\\":\\\"身体粉底\\\"},{\\\"cateId\\\":\\\"9065\\\",\\\"cateNameCn\\\":\\\"双眼皮及工具\\\"},{\\\"cateId\\\":\\\"9066\\\",\\\"cateNameCn\\\":\\\"卸妆产品\\\"},{\\\"cateId\\\":\\\"9067\\\",\\\"cateNameCn\\\":\\\"修眉刀\\\"},{\\\"cateId\\\":\\\"9068\\\",\\\"cateNameCn\\\":\\\"胭脂\\\"},{\\\"cateId\\\":\\\"9069\\\",\\\"cateNameCn\\\":\\\"眼线\\\"},{\\\"cateId\\\":\\\"9070\\\",\\\"cateNameCn\\\":\\\"眼影\\\"},{\\\"cateId\\\":\\\"9071\\\",\\\"cateNameCn\\\":\\\"眼影棒\\\"},{\\\"cateId\\\":\\\"9072\\\",\\\"cateNameCn\\\":\\\"眼影眼线笔\\\"},{\\\"cateId\\\":\\\"9073\\\",\\\"cateNameCn\\\":\\\"遮瑕\\\"}]},{\\\"cateId\\\":\\\"3481\\\",\\\"cateNameCn\\\":\\\"个护健康\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9074\\\",\\\"cateNameCn\\\":\\\"电动剃须刀1\\\"},{\\\"cateId\\\":\\\"9075\\\",\\\"cateNameCn\\\":\\\"耳鼻毛器\\\"},{\\\"cateId\\\":\\\"9076\\\",\\\"cateNameCn\\\":\\\"耳部护理（婴儿的请发布到婴儿耳勺下）\\\"},{\\\"cateId\\\":\\\"9077\\\",\\\"cateNameCn\\\":\\\"丰胸膏\\\"},{\\\"cateId\\\":\\\"9079\\\",\\\"cateNameCn\\\":\\\"口罩\\\"},{\\\"cateId\\\":\\\"9080\\\",\\\"cateNameCn\\\":\\\"蜡纸\\\"},{\\\"cateId\\\":\\\"9081\\\",\\\"cateNameCn\\\":\\\"瘦身产品\\\"},{\\\"cateId\\\":\\\"9082\\\",\\\"cateNameCn\\\":\\\"睡眠/止鼾\\\"},{\\\"cateId\\\":\\\"9083\\\",\\\"cateNameCn\\\":\\\"个护体重秤（婴儿用的请发布到婴儿体重秤下）\\\"},{\\\"cateId\\\":\\\"9084\\\",\\\"cateNameCn\\\":\\\"剃须刀\\\"},{\\\"cateId\\\":\\\"9085\\\",\\\"cateNameCn\\\":\\\"剃须刀片\\\"},{\\\"cateId\\\":\\\"9086\\\",\\\"cateNameCn\\\":\\\"剃须膏\\\"},{\\\"cateId\\\":\\\"9087\\\",\\\"cateNameCn\\\":\\\"剃须泡\\\"},{\\\"cateId\\\":\\\"9088\\\",\\\"cateNameCn\\\":\\\"剃须刷\\\"},{\\\"cateId\\\":\\\"9089\\\",\\\"cateNameCn\\\":\\\"脱毛器1\\\"},{\\\"cateId\\\":\\\"9090\\\",\\\"cateNameCn\\\":\\\"温度计\\\"},{\\\"cateId\\\":\\\"9091\\\",\\\"cateNameCn\\\":\\\"须后膏\\\"},{\\\"cateId\\\":\\\"9093\\\",\\\"cateNameCn\\\":\\\"普通血压计\\\"},{\\\"cateId\\\":\\\"9094\\\",\\\"cateNameCn\\\":\\\"药盒/分药器\\\"},{\\\"cateId\\\":\\\"9095\\\",\\\"cateNameCn\\\":\\\"义乳\\\"},{\\\"cateId\\\":\\\"9096\\\",\\\"cateNameCn\\\":\\\"脂肪测量仪\\\"},{\\\"cateId\\\":\\\"9097\\\",\\\"cateNameCn\\\":\\\"美容仪器附件配件\\\"},{\\\"cateId\\\":\\\"9098\\\",\\\"cateNameCn\\\":\\\"微针滚轮仪\\\"},{\\\"cateId\\\":\\\"9099\\\",\\\"cateNameCn\\\":\\\"激光脱毛仪/Yag激光仪\\\"},{\\\"cateId\\\":\\\"9100\\\",\\\"cateNameCn\\\":\\\"微晶磨皮机\\\"},{\\\"cateId\\\":\\\"9101\\\",\\\"cateNameCn\\\":\\\"无针美塑仪\\\"},{\\\"cateId\\\":\\\"9102\\\",\\\"cateNameCn\\\":\\\"洁肤仪\\\"},{\\\"cateId\\\":\\\"9103\\\",\\\"cateNameCn\\\":\\\"导入导出仪\\\"},{\\\"cateId\\\":\\\"9104\\\",\\\"cateNameCn\\\":\\\"提拉紧肤仪\\\"},{\\\"cateId\\\":\\\"9105\\\",\\\"cateNameCn\\\":\\\"SPA按摩仪\\\"},{\\\"cateId\\\":\\\"9106\\\",\\\"cateNameCn\\\":\\\"身体按摩美容仪\\\"},{\\\"cateId\\\":\\\"9107\\\",\\\"cateNameCn\\\":\\\"去眼袋美容仪\\\"},{\\\"cateId\\\":\\\"9108\\\",\\\"cateNameCn\\\":\\\"瘦脸按摩器\\\"},{\\\"cateId\\\":\\\"9109\\\",\\\"cateNameCn\\\":\\\"家用美容仪\\\"},{\\\"cateId\\\":\\\"9110\\\",\\\"cateNameCn\\\":\\\"射频美容仪\\\"},{\\\"cateId\\\":\\\"9111\\\",\\\"cateNameCn\\\":\\\"皮肤诊断仪\\\"},{\\\"cateId\\\":\\\"9112\\\",\\\"cateNameCn\\\":\\\"减肥纤体仪\\\"},{\\\"cateId\\\":\\\"9113\\\",\\\"cateNameCn\\\":\\\"儿童视力保护\\\"},{\\\"cateId\\\":\\\"9114\\\",\\\"cateNameCn\\\":\\\"隐形眼镜护理产品\\\"},{\\\"cateId\\\":\\\"9115\\\",\\\"cateNameCn\\\":\\\"电子烟\\\"},{\\\"cateId\\\":\\\"9116\\\",\\\"cateNameCn\\\":\\\"美容仪\\\"},{\\\"cateId\\\":\\\"9117\\\",\\\"cateNameCn\\\":\\\"按摩相关\\\"},{\\\"cateId\\\":\\\"9118\\\",\\\"cateNameCn\\\":\\\"养生器械\\\"},{\\\"cateId\\\":\\\"9119\\\",\\\"cateNameCn\\\":\\\"中医保健\\\"},{\\\"cateId\\\":\\\"9120\\\",\\\"cateNameCn\\\":\\\"急救卫生\\\"},{\\\"cateId\\\":\\\"9121\\\",\\\"cateNameCn\\\":\\\"脂肪检测仪\\\"},{\\\"cateId\\\":\\\"9122\\\",\\\"cateNameCn\\\":\\\"血氧仪\\\"},{\\\"cateId\\\":\\\"9124\\\",\\\"cateNameCn\\\":\\\"成人尿裤/片\\\"},{\\\"cateId\\\":\\\"9125\\\",\\\"cateNameCn\\\":\\\"香薰疗法\\\"},{\\\"cateId\\\":\\\"9126\\\",\\\"cateNameCn\\\":\\\"耳部保养产品\\\"},{\\\"cateId\\\":\\\"9127\\\",\\\"cateNameCn\\\":\\\"女性卫生\\\"},{\\\"cateId\\\":\\\"9128\\\",\\\"cateNameCn\\\":\\\"紧急救护\\\"},{\\\"cateId\\\":\\\"9129\\\",\\\"cateNameCn\\\":\\\"足部护理产品\\\"},{\\\"cateId\\\":\\\"9130\\\",\\\"cateNameCn\\\":\\\"保健仪器\\\"},{\\\"cateId\\\":\\\"9131\\\",\\\"cateNameCn\\\":\\\"臀部护理用品\\\"},{\\\"cateId\\\":\\\"9132\\\",\\\"cateNameCn\\\":\\\"冷热贴垫\\\"},{\\\"cateId\\\":\\\"9133\\\",\\\"cateNameCn\\\":\\\"磁疗\\\"},{\\\"cateId\\\":\\\"9134\\\",\\\"cateNameCn\\\":\\\"自然疗法\\\"},{\\\"cateId\\\":\\\"9135\\\",\\\"cateNameCn\\\":\\\"止痛用品\\\"},{\\\"cateId\\\":\\\"9136\\\",\\\"cateNameCn\\\":\\\"止鼾器\\\"},{\\\"cateId\\\":\\\"9137\\\",\\\"cateNameCn\\\":\\\"背部按摩枕垫\\\"},{\\\"cateId\\\":\\\"9138\\\",\\\"cateNameCn\\\":\\\"眼部按摩器\\\"},{\\\"cateId\\\":\\\"9139\\\",\\\"cateNameCn\\\":\\\"脸部按摩\\\"},{\\\"cateId\\\":\\\"9140\\\",\\\"cateNameCn\\\":\\\"便携全身按摩器\\\"},{\\\"cateId\\\":\\\"9141\\\",\\\"cateNameCn\\\":\\\"头部按摩器\\\"},{\\\"cateId\\\":\\\"9142\\\",\\\"cateNameCn\\\":\\\"足部按摩\\\"},{\\\"cateId\\\":\\\"9143\\\",\\\"cateNameCn\\\":\\\"腰部按摩器\\\"},{\\\"cateId\\\":\\\"9144\\\",\\\"cateNameCn\\\":\\\"按摩椅/按摩桌\\\"},{\\\"cateId\\\":\\\"9145\\\",\\\"cateNameCn\\\":\\\"按摩油或按摩膏\\\"},{\\\"cateId\\\":\\\"9146\\\",\\\"cateNameCn\\\":\\\"按摩石\\\"},{\\\"cateId\\\":\\\"9147\\\",\\\"cateNameCn\\\":\\\"按摩枕/披肩\\\"},{\\\"cateId\\\":\\\"9148\\\",\\\"cateNameCn\\\":\\\"针灸/拔罐\\\"},{\\\"cateId\\\":\\\"9149\\\",\\\"cateNameCn\\\":\\\"酒精检测仪器\\\"},{\\\"cateId\\\":\\\"9150\\\",\\\"cateNameCn\\\":\\\"超声波\\\"},{\\\"cateId\\\":\\\"9152\\\",\\\"cateNameCn\\\":\\\"血脂检测仪\\\"},{\\\"cateId\\\":\\\"9153\\\",\\\"cateNameCn\\\":\\\"糖尿病护理用品\\\"},{\\\"cateId\\\":\\\"9154\\\",\\\"cateNameCn\\\":\\\"心电图机\\\"},{\\\"cateId\\\":\\\"9155\\\",\\\"cateNameCn\\\":\\\"脑电图机\\\"},{\\\"cateId\\\":\\\"9156\\\",\\\"cateNameCn\\\":\\\"多普勒胎心仪\\\"},{\\\"cateId\\\":\\\"9157\\\",\\\"cateNameCn\\\":\\\"助听器\\\"},{\\\"cateId\\\":\\\"9158\\\",\\\"cateNameCn\\\":\\\"心率监视器\\\"},{\\\"cateId\\\":\\\"9159\\\",\\\"cateNameCn\\\":\\\"家用理疗仪器\\\"},{\\\"cateId\\\":\\\"9160\\\",\\\"cateNameCn\\\":\\\"输液泵\\\"},{\\\"cateId\\\":\\\"9161\\\",\\\"cateNameCn\\\":\\\"医疗耗材\\\"},{\\\"cateId\\\":\\\"9162\\\",\\\"cateNameCn\\\":\\\"医疗诊断/检测用品\\\"},{\\\"cateId\\\":\\\"9163\\\",\\\"cateNameCn\\\":\\\"医学影像系统\\\"},{\\\"cateId\\\":\\\"9164\\\",\\\"cateNameCn\\\":\\\"行动辅助\\\"},{\\\"cateId\\\":\\\"9165\\\",\\\"cateNameCn\\\":\\\"制氧机\\\"},{\\\"cateId\\\":\\\"9166\\\",\\\"cateNameCn\\\":\\\"轮椅胎\\\"},{\\\"cateId\\\":\\\"9167\\\",\\\"cateNameCn\\\":\\\"心仪理疗仪\\\"},{\\\"cateId\\\":\\\"9169\\\",\\\"cateNameCn\\\":\\\"雾化器\\\"},{\\\"cateId\\\":\\\"9170\\\",\\\"cateNameCn\\\":\\\"呼吸机\\\"},{\\\"cateId\\\":\\\"9171\\\",\\\"cateNameCn\\\":\\\"拔罐器\\\"},{\\\"cateId\\\":\\\"9172\\\",\\\"cateNameCn\\\":\\\"创口贴\\\"},{\\\"cateId\\\":\\\"9173\\\",\\\"cateNameCn\\\":\\\"护具\\\"},{\\\"cateId\\\":\\\"9174\\\",\\\"cateNameCn\\\":\\\"导尿袋\\\"},{\\\"cateId\\\":\\\"9175\\\",\\\"cateNameCn\\\":\\\"血糖仪\\\"},{\\\"cateId\\\":\\\"9176\\\",\\\"cateNameCn\\\":\\\"针灸用品\\\"},{\\\"cateId\\\":\\\"9177\\\",\\\"cateNameCn\\\":\\\"护理床\\\"},{\\\"cateId\\\":\\\"9178\\\",\\\"cateNameCn\\\":\\\"褥疮垫\\\"},{\\\"cateId\\\":\\\"9179\\\",\\\"cateNameCn\\\":\\\"家用器械\\\"},{\\\"cateId\\\":\\\"9180\\\",\\\"cateNameCn\\\":\\\"按摩器材\\\"},{\\\"cateId\\\":\\\"9181\\\",\\\"cateNameCn\\\":\\\"吸痰器\\\"},{\\\"cateId\\\":\\\"9182\\\",\\\"cateNameCn\\\":\\\"座便椅\\\"},{\\\"cateId\\\":\\\"9184\\\",\\\"cateNameCn\\\":\\\"颈椎治疗仪\\\"},{\\\"cateId\\\":\\\"9185\\\",\\\"cateNameCn\\\":\\\"刮痧洗鼻器\\\"},{\\\"cateId\\\":\\\"9186\\\",\\\"cateNameCn\\\":\\\"病人监护器\\\"},{\\\"cateId\\\":\\\"9187\\\",\\\"cateNameCn\\\":\\\"便携药盒\\\"},{\\\"cateId\\\":\\\"9188\\\",\\\"cateNameCn\\\":\\\"听诊器\\\"},{\\\"cateId\\\":\\\"9189\\\",\\\"cateNameCn\\\":\\\"美白仪\\\"},{\\\"cateId\\\":\\\"9190\\\",\\\"cateNameCn\\\":\\\"美容护肤加湿器\\\"},{\\\"cateId\\\":\\\"9192\\\",\\\"cateNameCn\\\":\\\"灭蚊灯\\\"},{\\\"cateId\\\":\\\"9193\\\",\\\"cateNameCn\\\":\\\"减肥贴\\\"},{\\\"cateId\\\":\\\"9194\\\",\\\"cateNameCn\\\":\\\"减肥仪\\\"},{\\\"cateId\\\":\\\"9195\\\",\\\"cateNameCn\\\":\\\"减肥腰带\\\"},{\\\"cateId\\\":\\\"9196\\\",\\\"cateNameCn\\\":\\\"减肥精油\\\"},{\\\"cateId\\\":\\\"9197\\\",\\\"cateNameCn\\\":\\\"燃脂衣\\\"},{\\\"cateId\\\":\\\"9198\\\",\\\"cateNameCn\\\":\\\"燃脂腰带\\\"},{\\\"cateId\\\":\\\"9199\\\",\\\"cateNameCn\\\":\\\"燃脂袜\\\"}]},{\\\"cateId\\\":\\\"3482\\\",\\\"cateNameCn\\\":\\\"工具及配件\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9200\\\",\\\"cateNameCn\\\":\\\"分装瓶\\\"},{\\\"cateId\\\":\\\"9201\\\",\\\"cateNameCn\\\":\\\"附件\\\"},{\\\"cateId\\\":\\\"9202\\\",\\\"cateNameCn\\\":\\\"工具套装\\\"},{\\\"cateId\\\":\\\"9203\\\",\\\"cateNameCn\\\":\\\"化妆镜\\\"},{\\\"cateId\\\":\\\"9204\\\",\\\"cateNameCn\\\":\\\"化妆棉\\\"},{\\\"cateId\\\":\\\"9205\\\",\\\"cateNameCn\\\":\\\"棉签（婴儿的请发布到婴儿棉签下）\\\"},{\\\"cateId\\\":\\\"9206\\\",\\\"cateNameCn\\\":\\\"洗浴镜\\\"},{\\\"cateId\\\":\\\"9207\\\",\\\"cateNameCn\\\":\\\"足部护理工具\\\"},{\\\"cateId\\\":\\\"9208\\\",\\\"cateNameCn\\\":\\\"放大镜\\\"},{\\\"cateId\\\":\\\"9393\\\",\\\"cateNameCn\\\":\\\"隐形眼镜\\\"},{\\\"cateId\\\":\\\"9394\\\",\\\"cateNameCn\\\":\\\"老花镜\\\"}]},{\\\"cateId\\\":\\\"3485\\\",\\\"cateNameCn\\\":\\\"健康保健\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"3483\\\",\\\"cateNameCn\\\":\\\"光学仪器\\\"},{\\\"cateId\\\":\\\"3490\\\",\\\"cateNameCn\\\":\\\"女性护理\\\"},{\\\"cateId\\\":\\\"3497\\\",\\\"cateNameCn\\\":\\\"眼镜及配件2\\\"},{\\\"cateId\\\":\\\"9237\\\",\\\"cateNameCn\\\":\\\"护肤用品套装\\\"},{\\\"cateId\\\":\\\"9245\\\",\\\"cateNameCn\\\":\\\"按摩/按摩器\\\"},{\\\"cateId\\\":\\\"9247\\\",\\\"cateNameCn\\\":\\\"眼罩\\\"},{\\\"cateId\\\":\\\"11121\\\",\\\"cateNameCn\\\":\\\"健康美容待录入2\\\"},{\\\"cateId\\\":\\\"11447\\\",\\\"cateNameCn\\\":\\\"香水/除臭剂\\\"},{\\\"cateId\\\":\\\"11448\\\",\\\"cateNameCn\\\":\\\"沐浴用品1\\\"},{\\\"cateId\\\":\\\"12094\\\",\\\"cateNameCn\\\":\\\"性用品\\\"},{\\\"cateId\\\":\\\"12095\\\",\\\"cateNameCn\\\":\\\"纹身/身体艺术\\\"},{\\\"cateId\\\":\\\"12096\\\",\\\"cateNameCn\\\":\\\"美发护发\\\"},{\\\"cateId\\\":\\\"12097\\\",\\\"cateNameCn\\\":\\\"口腔护理1\\\"}]},{\\\"cateId\\\":\\\"3486\\\",\\\"cateNameCn\\\":\\\"口腔清洁1\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9248\\\",\\\"cateNameCn\\\":\\\"牙粉\\\"},{\\\"cateId\\\":\\\"9249\\\",\\\"cateNameCn\\\":\\\"牙线\\\"},{\\\"cateId\\\":\\\"9250\\\",\\\"cateNameCn\\\":\\\"冲牙器\\\"},{\\\"cateId\\\":\\\"9251\\\",\\\"cateNameCn\\\":\\\"电动牙刷\\\"},{\\\"cateId\\\":\\\"9252\\\",\\\"cateNameCn\\\":\\\"口腔清洁\\\"},{\\\"cateId\\\":\\\"9253\\\",\\\"cateNameCn\\\":\\\"牙齿矫形器\\\"},{\\\"cateId\\\":\\\"9254\\\",\\\"cateNameCn\\\":\\\"牙齿美白产品\\\"},{\\\"cateId\\\":\\\"9255\\\",\\\"cateNameCn\\\":\\\"牙膏\\\"},{\\\"cateId\\\":\\\"9256\\\",\\\"cateNameCn\\\":\\\"牙间刷\\\"},{\\\"cateId\\\":\\\"9257\\\",\\\"cateNameCn\\\":\\\"牙刷\\\"},{\\\"cateId\\\":\\\"9258\\\",\\\"cateNameCn\\\":\\\"牙刷杀菌器\\\"},{\\\"cateId\\\":\\\"9259\\\",\\\"cateNameCn\\\":\\\"牙刷头\\\"},{\\\"cateId\\\":\\\"9260\\\",\\\"cateNameCn\\\":\\\"漱口水\\\"},{\\\"cateId\\\":\\\"9261\\\",\\\"cateNameCn\\\":\\\"套装\\\"},{\\\"cateId\\\":\\\"9262\\\",\\\"cateNameCn\\\":\\\"洗牙及抛光\\\"},{\\\"cateId\\\":\\\"9263\\\",\\\"cateNameCn\\\":\\\"口腔耗材/检测产品\\\"},{\\\"cateId\\\":\\\"9264\\\",\\\"cateNameCn\\\":\\\"牙科光固化机\\\"},{\\\"cateId\\\":\\\"9265\\\",\\\"cateNameCn\\\":\\\"牙科工具\\\"},{\\\"cateId\\\":\\\"9266\\\",\\\"cateNameCn\\\":\\\"口腔内窥镜\\\"}]},{\\\"cateId\\\":\\\"3487\\\",\\\"cateNameCn\\\":\\\"美发理发工具\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9246\\\",\\\"cateNameCn\\\":\\\"假发及周边\\\"},{\\\"cateId\\\":\\\"9267\\\",\\\"cateNameCn\\\":\\\"编发工具\\\"},{\\\"cateId\\\":\\\"9268\\\",\\\"cateNameCn\\\":\\\"编发造型用品\\\"},{\\\"cateId\\\":\\\"9269\\\",\\\"cateNameCn\\\":\\\"箔纸\\\"},{\\\"cateId\\\":\\\"9270\\\",\\\"cateNameCn\\\":\\\"吹风机\\\"},{\\\"cateId\\\":\\\"9271\\\",\\\"cateNameCn\\\":\\\"弹力素\\\"},{\\\"cateId\\\":\\\"9272\\\",\\\"cateNameCn\\\":\\\"电卷发棒/卷发器1（带电造型工具）\\\"},{\\\"cateId\\\":\\\"9273\\\",\\\"cateNameCn\\\":\\\"发膏\\\"},{\\\"cateId\\\":\\\"9274\\\",\\\"cateNameCn\\\":\\\"发夹（造型辅助发夹/非装饰性发夹）\\\"},{\\\"cateId\\\":\\\"9275\\\",\\\"cateNameCn\\\":\\\"发蜡\\\"},{\\\"cateId\\\":\\\"9276\\\",\\\"cateNameCn\\\":\\\"发针（造型辅助/非装饰性）\\\"},{\\\"cateId\\\":\\\"9277\\\",\\\"cateNameCn\\\":\\\"防脱发\\\"},{\\\"cateId\\\":\\\"9278\\\",\\\"cateNameCn\\\":\\\"烘发筒1\\\"},{\\\"cateId\\\":\\\"9279\\\",\\\"cateNameCn\\\":\\\"护发素\\\"},{\\\"cateId\\\":\\\"9280\\\",\\\"cateNameCn\\\":\\\"护发套装\\\"},{\\\"cateId\\\":\\\"9281\\\",\\\"cateNameCn\\\":\\\"帽子1\\\"},{\\\"cateId\\\":\\\"9282\\\",\\\"cateNameCn\\\":\\\"美发剪刀\\\"},{\\\"cateId\\\":\\\"9283\\\",\\\"cateNameCn\\\":\\\"摩丝\\\"},{\\\"cateId\\\":\\\"9284\\\",\\\"cateNameCn\\\":\\\"染发剂\\\"},{\\\"cateId\\\":\\\"9285\\\",\\\"cateNameCn\\\":\\\"梳子（婴儿用的请发布到婴儿梳子下）\\\"},{\\\"cateId\\\":\\\"9286\\\",\\\"cateNameCn\\\":\\\"烫发剂\\\"},{\\\"cateId\\\":\\\"9287\\\",\\\"cateNameCn\\\":\\\"调色套装（工具类）\\\"},{\\\"cateId\\\":\\\"9288\\\",\\\"cateNameCn\\\":\\\"调色碗\\\"},{\\\"cateId\\\":\\\"9289\\\",\\\"cateNameCn\\\":\\\"头发头皮修复\\\"},{\\\"cateId\\\":\\\"9290\\\",\\\"cateNameCn\\\":\\\"头发修剪器1（婴儿用的请发布到婴儿理发器下）\\\"},{\\\"cateId\\\":\\\"9291\\\",\\\"cateNameCn\\\":\\\"围巾婴儿用的请发布到婴儿理发围布下）\\\"},{\\\"cateId\\\":\\\"9292\\\",\\\"cateNameCn\\\":\\\"洗发水\\\"},{\\\"cateId\\\":\\\"9293\\\",\\\"cateNameCn\\\":\\\"洗头瓶\\\"},{\\\"cateId\\\":\\\"9294\\\",\\\"cateNameCn\\\":\\\"造型发卷（不带电/普通型）\\\"},{\\\"cateId\\\":\\\"9295\\\",\\\"cateNameCn\\\":\\\"造型配件\\\"},{\\\"cateId\\\":\\\"9296\\\",\\\"cateNameCn\\\":\\\"造型喷雾\\\"},{\\\"cateId\\\":\\\"9297\\\",\\\"cateNameCn\\\":\\\"造型乳液\\\"},{\\\"cateId\\\":\\\"9298\\\",\\\"cateNameCn\\\":\\\"造型啫喱\\\"},{\\\"cateId\\\":\\\"9299\\\",\\\"cateNameCn\\\":\\\"直发膏\\\"},{\\\"cateId\\\":\\\"9300\\\",\\\"cateNameCn\\\":\\\"直发夹板1\\\"},{\\\"cateId\\\":\\\"11086\\\",\\\"cateNameCn\\\":\\\"胶棒\\\"}]},{\\\"cateId\\\":\\\"3488\\\",\\\"cateNameCn\\\":\\\"美甲用品及修甲工具\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9302\\\",\\\"cateNameCn\\\":\\\"彩色指甲油\\\"},{\\\"cateId\\\":\\\"9303\\\",\\\"cateNameCn\\\":\\\"打孔器\\\"},{\\\"cateId\\\":\\\"9304\\\",\\\"cateNameCn\\\":\\\"底油\\\"},{\\\"cateId\\\":\\\"9305\\\",\\\"cateNameCn\\\":\\\"点笔\\\"},{\\\"cateId\\\":\\\"9306\\\",\\\"cateNameCn\\\":\\\"雕花粉\\\"},{\\\"cateId\\\":\\\"9307\\\",\\\"cateNameCn\\\":\\\"多功能底油盖油\\\"},{\\\"cateId\\\":\\\"9308\\\",\\\"cateNameCn\\\":\\\"分趾器\\\"},{\\\"cateId\\\":\\\"9309\\\",\\\"cateNameCn\\\":\\\"浮石\\\"},{\\\"cateId\\\":\\\"9310\\\",\\\"cateNameCn\\\":\\\"盖油\\\"},{\\\"cateId\\\":\\\"9311\\\",\\\"cateNameCn\\\":\\\"假指甲\\\"},{\\\"cateId\\\":\\\"9312\\\",\\\"cateNameCn\\\":\\\"脚锉\\\"},{\\\"cateId\\\":\\\"9313\\\",\\\"cateNameCn\\\":\\\"美甲闪粉\\\"},{\\\"cateId\\\":\\\"9314\\\",\\\"cateNameCn\\\":\\\"美甲设备\\\"},{\\\"cateId\\\":\\\"9315\\\",\\\"cateNameCn\\\":\\\"美甲套装/修甲套装\\\"},{\\\"cateId\\\":\\\"9316\\\",\\\"cateNameCn\\\":\\\"美甲展示架\\\"},{\\\"cateId\\\":\\\"9317\\\",\\\"cateNameCn\\\":\\\"美甲指托\\\"},{\\\"cateId\\\":\\\"9318\\\",\\\"cateNameCn\\\":\\\"模板\\\"},{\\\"cateId\\\":\\\"9319\\\",\\\"cateNameCn\\\":\\\"抛光块\\\"},{\\\"cateId\\\":\\\"9320\\\",\\\"cateNameCn\\\":\\\"手枕\\\"},{\\\"cateId\\\":\\\"9321\\\",\\\"cateNameCn\\\":\\\"水晶粉\\\"},{\\\"cateId\\\":\\\"9322\\\",\\\"cateNameCn\\\":\\\"水钻/装饰物\\\"},{\\\"cateId\\\":\\\"9323\\\",\\\"cateNameCn\\\":\\\"死皮剪\\\"},{\\\"cateId\\\":\\\"9324\\\",\\\"cateNameCn\\\":\\\"死皮推\\\"},{\\\"cateId\\\":\\\"9325\\\",\\\"cateNameCn\\\":\\\"斜口死皮指甲刀\\\"},{\\\"cateId\\\":\\\"9326\\\",\\\"cateNameCn\\\":\\\"卸甲相关\\\"},{\\\"cateId\\\":\\\"9327\\\",\\\"cateNameCn\\\":\\\"指甲锉\\\"},{\\\"cateId\\\":\\\"9328\\\",\\\"cateNameCn\\\":\\\"指甲刀（婴儿的请发布到婴儿指甲剪下）\\\"},{\\\"cateId\\\":\\\"9329\\\",\\\"cateNameCn\\\":\\\"指甲烘干器\\\"},{\\\"cateId\\\":\\\"9330\\\",\\\"cateNameCn\\\":\\\"指甲胶\\\"},{\\\"cateId\\\":\\\"9331\\\",\\\"cateNameCn\\\":\\\"指甲胶水\\\"},{\\\"cateId\\\":\\\"9332\\\",\\\"cateNameCn\\\":\\\"指甲刷\\\"},{\\\"cateId\\\":\\\"9333\\\",\\\"cateNameCn\\\":\\\"指甲贴\\\"},{\\\"cateId\\\":\\\"9334\\\",\\\"cateNameCn\\\":\\\"指甲修复用品\\\"},{\\\"cateId\\\":\\\"9335\\\",\\\"cateNameCn\\\":\\\"美甲色卡/色本\\\"}]},{\\\"cateId\\\":\\\"3489\\\",\\\"cateNameCn\\\":\\\"沐浴用品\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9336\\\",\\\"cateNameCn\\\":\\\"沐浴工具\\\"},{\\\"cateId\\\":\\\"9337\\\",\\\"cateNameCn\\\":\\\"沐浴乳\\\"},{\\\"cateId\\\":\\\"9338\\\",\\\"cateNameCn\\\":\\\"沐浴油\\\"},{\\\"cateId\\\":\\\"9339\\\",\\\"cateNameCn\\\":\\\"泡澡用品（婴儿的请发布到婴儿洗浴用品下）\\\"},{\\\"cateId\\\":\\\"9340\\\",\\\"cateNameCn\\\":\\\"身体磨砂/修复\\\"},{\\\"cateId\\\":\\\"9341\\\",\\\"cateNameCn\\\":\\\"套装\\\"},{\\\"cateId\\\":\\\"9342\\\",\\\"cateNameCn\\\":\\\"香皂\\\"},{\\\"cateId\\\":\\\"9343\\\",\\\"cateNameCn\\\":\\\"浴盐\\\"}]},{\\\"cateId\\\":\\\"3492\\\",\\\"cateNameCn\\\":\\\"塑型与瘦身\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9349\\\",\\\"cateNameCn\\\":\\\"胳膊塑型\\\"},{\\\"cateId\\\":\\\"9350\\\",\\\"cateNameCn\\\":\\\"身体矫正\\\"},{\\\"cateId\\\":\\\"9351\\\",\\\"cateNameCn\\\":\\\"腿部塑型\\\"},{\\\"cateId\\\":\\\"9352\\\",\\\"cateNameCn\\\":\\\"其他塑型瘦身产品\\\"},{\\\"cateId\\\":\\\"9353\\\",\\\"cateNameCn\\\":\\\"便携塑身仪\\\"},{\\\"cateId\\\":\\\"9354\\\",\\\"cateNameCn\\\":\\\"瘦身消脂贴/膏\\\"},{\\\"cateId\\\":\\\"9355\\\",\\\"cateNameCn\\\":\\\"瘦身腰带\\\"},{\\\"cateId\\\":\\\"9356\\\",\\\"cateNameCn\\\":\\\"腰腹塑型衣（带）\\\"},{\\\"cateId\\\":\\\"9357\\\",\\\"cateNameCn\\\":\\\"瑜伽垫\\\"},{\\\"cateId\\\":\\\"9358\\\",\\\"cateNameCn\\\":\\\"瑜伽球\\\"},{\\\"cateId\\\":\\\"9359\\\",\\\"cateNameCn\\\":\\\"瑜伽圈/普拉提圈\\\"},{\\\"cateId\\\":\\\"9360\\\",\\\"cateNameCn\\\":\\\"瑜伽砖\\\"},{\\\"cateId\\\":\\\"9361\\\",\\\"cateNameCn\\\":\\\"瑜伽发带\\\"},{\\\"cateId\\\":\\\"9362\\\",\\\"cateNameCn\\\":\\\"瑜伽拉力带\\\"},{\\\"cateId\\\":\\\"9363\\\",\\\"cateNameCn\\\":\\\"拉力环\\\"},{\\\"cateId\\\":\\\"9364\\\",\\\"cateNameCn\\\":\\\"伸展带/瑜伽绳\\\"},{\\\"cateId\\\":\\\"9365\\\",\\\"cateNameCn\\\":\\\"甩脂机\\\"},{\\\"cateId\\\":\\\"9366\\\",\\\"cateNameCn\\\":\\\"燃烧脂肪\\\"}]},{\\\"cateId\\\":\\\"3493\\\",\\\"cateNameCn\\\":\\\"卫生用纸\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9367\\\",\\\"cateNameCn\\\":\\\"餐巾纸\\\"},{\\\"cateId\\\":\\\"9368\\\",\\\"cateNameCn\\\":\\\"成人尿布\\\"},{\\\"cateId\\\":\\\"9369\\\",\\\"cateNameCn\\\":\\\"面巾纸\\\"},{\\\"cateId\\\":\\\"9370\\\",\\\"cateNameCn\\\":\\\"湿巾（婴儿用的请发布到婴儿湿巾下）\\\"},{\\\"cateId\\\":\\\"9371\\\",\\\"cateNameCn\\\":\\\"卫生纸\\\"},{\\\"cateId\\\":\\\"12149\\\",\\\"cateNameCn\\\":\\\"卫生用纸1\\\"}]},{\\\"cateId\\\":\\\"3494\\\",\\\"cateNameCn\\\":\\\"纹身/人体艺术\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9372\\\",\\\"cateNameCn\\\":\\\"纹身电源\\\"},{\\\"cateId\\\":\\\"9373\\\",\\\"cateNameCn\\\":\\\"纹身机\\\"},{\\\"cateId\\\":\\\"9374\\\",\\\"cateNameCn\\\":\\\"纹身模版\\\"},{\\\"cateId\\\":\\\"9375\\\",\\\"cateNameCn\\\":\\\"纹身配件\\\"},{\\\"cateId\\\":\\\"9376\\\",\\\"cateNameCn\\\":\\\"纹身器具套件\\\"},{\\\"cateId\\\":\\\"9377\\\",\\\"cateNameCn\\\":\\\"纹身色料\\\"},{\\\"cateId\\\":\\\"9378\\\",\\\"cateNameCn\\\":\\\"纹身手柄\\\"},{\\\"cateId\\\":\\\"9379\\\",\\\"cateNameCn\\\":\\\"纹身针\\\"},{\\\"cateId\\\":\\\"9380\\\",\\\"cateNameCn\\\":\\\"一次性纹身\\\"},{\\\"cateId\\\":\\\"9381\\\",\\\"cateNameCn\\\":\\\"纹身清洗机\\\"},{\\\"cateId\\\":\\\"9382\\\",\\\"cateNameCn\\\":\\\"纹身贴纸\\\"},{\\\"cateId\\\":\\\"9383\\\",\\\"cateNameCn\\\":\\\"针嘴\\\"},{\\\"cateId\\\":\\\"9384\\\",\\\"cateNameCn\\\":\\\"纹身色料/墨水\\\"},{\\\"cateId\\\":\\\"9385\\\",\\\"cateNameCn\\\":\\\"纹绣练习皮\\\"}]},{\\\"cateId\\\":\\\"3495\\\",\\\"cateNameCn\\\":\\\"香氛/除臭芳香用品\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9386\\\",\\\"cateNameCn\\\":\\\"除臭\\\"},{\\\"cateId\\\":\\\"9387\\\",\\\"cateNameCn\\\":\\\"止汗\\\"}]},{\\\"cateId\\\":\\\"11155\\\",\\\"cateNameCn\\\":\\\"护肤品\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"3491\\\",\\\"cateNameCn\\\":\\\"足部皮肤护理\\\"},{\\\"cateId\\\":\\\"9210\\\",\\\"cateNameCn\\\":\\\"T区护理\\\"},{\\\"cateId\\\":\\\"9211\\\",\\\"cateNameCn\\\":\\\"按摩膏\\\"},{\\\"cateId\\\":\\\"9212\\\",\\\"cateNameCn\\\":\\\"唇部防晒\\\"},{\\\"cateId\\\":\\\"9214\\\",\\\"cateNameCn\\\":\\\"护手霜\\\"},{\\\"cateId\\\":\\\"9215\\\",\\\"cateNameCn\\\":\\\"洁面用品\\\"},{\\\"cateId\\\":\\\"9216\\\",\\\"cateNameCn\\\":\\\"精华\\\"},{\\\"cateId\\\":\\\"9217\\\",\\\"cateNameCn\\\":\\\"精油\\\"},{\\\"cateId\\\":\\\"9218\\\",\\\"cateNameCn\\\":\\\"颈部护理\\\"},{\\\"cateId\\\":\\\"9219\\\",\\\"cateNameCn\\\":\\\"蜡疗机1\\\"},{\\\"cateId\\\":\\\"9220\\\",\\\"cateNameCn\\\":\\\"脸部防晒\\\"},{\\\"cateId\\\":\\\"9221\\\",\\\"cateNameCn\\\":\\\"脸部美黑\\\"},{\\\"cateId\\\":\\\"9222\\\",\\\"cateNameCn\\\":\\\"面部护肤工具\\\"},{\\\"cateId\\\":\\\"9223\\\",\\\"cateNameCn\\\":\\\"面部磨砂\\\"},{\\\"cateId\\\":\\\"9224\\\",\\\"cateNameCn\\\":\\\"面膜\\\"},{\\\"cateId\\\":\\\"9225\\\",\\\"cateNameCn\\\":\\\"日霜\\\"},{\\\"cateId\\\":\\\"9226\\\",\\\"cateNameCn\\\":\\\"乳液\\\"},{\\\"cateId\\\":\\\"9227\\\",\\\"cateNameCn\\\":\\\"润肤手套\\\"},{\\\"cateId\\\":\\\"9228\\\",\\\"cateNameCn\\\":\\\"晒后修复\\\"},{\\\"cateId\\\":\\\"9229\\\",\\\"cateNameCn\\\":\\\"身体防晒\\\"},{\\\"cateId\\\":\\\"9230\\\",\\\"cateNameCn\\\":\\\"身体美黑\\\"},{\\\"cateId\\\":\\\"9231\\\",\\\"cateNameCn\\\":\\\"身体乳\\\"},{\\\"cateId\\\":\\\"9232\\\",\\\"cateNameCn\\\":\\\"手部磨砂\\\"},{\\\"cateId\\\":\\\"9233\\\",\\\"cateNameCn\\\":\\\"手蜡\\\"},{\\\"cateId\\\":\\\"9234\\\",\\\"cateNameCn\\\":\\\"手膜\\\"},{\\\"cateId\\\":\\\"9235\\\",\\\"cateNameCn\\\":\\\"爽肤水\\\"},{\\\"cateId\\\":\\\"9236\\\",\\\"cateNameCn\\\":\\\"爽身粉（婴儿的请发到婴儿护肤下面）\\\"},{\\\"cateId\\\":\\\"9238\\\",\\\"cateNameCn\\\":\\\"晚霜\\\"},{\\\"cateId\\\":\\\"9239\\\",\\\"cateNameCn\\\":\\\"吸油面纸\\\"},{\\\"cateId\\\":\\\"9240\\\",\\\"cateNameCn\\\":\\\"洗手液\\\"},{\\\"cateId\\\":\\\"9241\\\",\\\"cateNameCn\\\":\\\"眼部精华\\\"},{\\\"cateId\\\":\\\"9242\\\",\\\"cateNameCn\\\":\\\"眼膜\\\"},{\\\"cateId\\\":\\\"9243\\\",\\\"cateNameCn\\\":\\\"眼霜\\\"},{\\\"cateId\\\":\\\"9244\\\",\\\"cateNameCn\\\":\\\"指甲护理\\\"},{\\\"cateId\\\":\\\"9344\\\",\\\"cateNameCn\\\":\\\"女生卫生护理\\\"},{\\\"cateId\\\":\\\"9345\\\",\\\"cateNameCn\\\":\\\"私处护理\\\"},{\\\"cateId\\\":\\\"9347\\\",\\\"cateNameCn\\\":\\\"孕妇皮肤护理\\\"},{\\\"cateId\\\":\\\"11156\\\",\\\"cateNameCn\\\":\\\"身体护理\\\"},{\\\"cateId\\\":\\\"11157\\\",\\\"cateNameCn\\\":\\\"手部护理\\\"},{\\\"cateId\\\":\\\"11158\\\",\\\"cateNameCn\\\":\\\"眼部护理\\\"},{\\\"cateId\\\":\\\"11159\\\",\\\"cateNameCn\\\":\\\"脸部护理\\\"},{\\\"cateId\\\":\\\"11160\\\",\\\"cateNameCn\\\":\\\"防晒/助晒\\\"},{\\\"cateId\\\":\\\"11233\\\",\\\"cateNameCn\\\":\\\"颈部护理2\\\"},{\\\"cateId\\\":\\\"11235\\\",\\\"cateNameCn\\\":\\\"套装2\\\"},{\\\"cateId\\\":\\\"11236\\\",\\\"cateNameCn\\\":\\\"唇部护理2\\\"},{\\\"cateId\\\":\\\"11247\\\",\\\"cateNameCn\\\":\\\"护肤品套装\\\"}]},{\\\"cateId\\\":\\\"11449\\\",\\\"cateNameCn\\\":\\\"健康护理（剃须及脱毛产品）\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9346\\\",\\\"cateNameCn\\\":\\\"脱毛膏\\\"}]}]},{\\\"cateId\\\":\\\"3356\\\",\\\"cateNameCn\\\":\\\"汽摩及配件\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"3516\\\",\\\"cateNameCn\\\":\\\"巴士配件\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9577\\\",\\\"cateNameCn\\\":\\\"巴士刹车部件\\\"},{\\\"cateId\\\":\\\"9578\\\",\\\"cateNameCn\\\":\\\"巴士车轮/轮胎\\\"},{\\\"cateId\\\":\\\"9579\\\",\\\"cateNameCn\\\":\\\"巴士车身部件\\\"},{\\\"cateId\\\":\\\"9580\\\",\\\"cateNameCn\\\":\\\"巴士灯部件\\\"},{\\\"cateId\\\":\\\"9581\\\",\\\"cateNameCn\\\":\\\"巴士附件\\\"},{\\\"cateId\\\":\\\"9582\\\",\\\"cateNameCn\\\":\\\"巴士引擎部件\\\"}]},{\\\"cateId\\\":\\\"3517\\\",\\\"cateNameCn\\\":\\\"刹车系统\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9583\\\",\\\"cateNameCn\\\":\\\"ABS/EBS系统部件\\\"},{\\\"cateId\\\":\\\"9584\\\",\\\"cateNameCn\\\":\\\"刹车鼓及五金\\\"},{\\\"cateId\\\":\\\"9585\\\",\\\"cateNameCn\\\":\\\"刹车盘/转子/五金\\\"},{\\\"cateId\\\":\\\"9586\\\",\\\"cateNameCn\\\":\\\"刹车片/刹车蹄\\\"},{\\\"cateId\\\":\\\"9587\\\",\\\"cateNameCn\\\":\\\"刹车钳及配件\\\"},{\\\"cateId\\\":\\\"9588\\\",\\\"cateNameCn\\\":\\\"刹车线\\\"},{\\\"cateId\\\":\\\"9589\\\",\\\"cateNameCn\\\":\\\"刹车助力泵\\\"},{\\\"cateId\\\":\\\"9590\\\",\\\"cateNameCn\\\":\\\"刹车总泵\\\"},{\\\"cateId\\\":\\\"9591\\\",\\\"cateNameCn\\\":\\\"车轮制动分泵缸\\\"},{\\\"cateId\\\":\\\"9592\\\",\\\"cateNameCn\\\":\\\"传感器及开关\\\"},{\\\"cateId\\\":\\\"9593\\\",\\\"cateNameCn\\\":\\\"发动机舱\\\"},{\\\"cateId\\\":\\\"9594\\\",\\\"cateNameCn\\\":\\\"挂车刹车\\\"},{\\\"cateId\\\":\\\"9595\\\",\\\"cateNameCn\\\":\\\"前后轮制动缸\\\"},{\\\"cateId\\\":\\\"9596\\\",\\\"cateNameCn\\\":\\\"手刹\\\"},{\\\"cateId\\\":\\\"9597\\\",\\\"cateNameCn\\\":\\\"手刹头\\\"},{\\\"cateId\\\":\\\"9598\\\",\\\"cateNameCn\\\":\\\"制动软管及附件\\\"},{\\\"cateId\\\":\\\"9599\\\",\\\"cateNameCn\\\":\\\"驻车制动电缆\\\"},{\\\"cateId\\\":\\\"9600\\\",\\\"cateNameCn\\\":\\\"驻车制动器\\\"}]},{\\\"cateId\\\":\\\"3518\\\",\\\"cateNameCn\\\":\\\"车窗及玻璃\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9601\\\",\\\"cateNameCn\\\":\\\"汽车玻璃\\\"},{\\\"cateId\\\":\\\"9602\\\",\\\"cateNameCn\\\":\\\"升窗系统/曲柄\\\"},{\\\"cateId\\\":\\\"9603\\\",\\\"cateNameCn\\\":\\\"雨刮喷水壶滤网\\\"},{\\\"cateId\\\":\\\"9604\\\",\\\"cateNameCn\\\":\\\"雨刷\\\"}]},{\\\"cateId\\\":\\\"3519\\\",\\\"cateNameCn\\\":\\\"车灯\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9605\\\",\\\"cateNameCn\\\":\\\"车灯光源\\\"},{\\\"cateId\\\":\\\"9606\\\",\\\"cateNameCn\\\":\\\"车内灯具\\\"},{\\\"cateId\\\":\\\"9607\\\",\\\"cateNameCn\\\":\\\"车外灯\\\"},{\\\"cateId\\\":\\\"9608\\\",\\\"cateNameCn\\\":\\\"底座\\\"},{\\\"cateId\\\":\\\"9609\\\",\\\"cateNameCn\\\":\\\"外罩\\\"},{\\\"cateId\\\":\\\"9610\\\",\\\"cateNameCn\\\":\\\"线材\\\"}]},{\\\"cateId\\\":\\\"3520\\\",\\\"cateNameCn\\\":\\\"车内部件\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9611\\\",\\\"cateNameCn\\\":\\\"车内门拉手\\\"},{\\\"cateId\\\":\\\"9612\\\",\\\"cateNameCn\\\":\\\"车内门面板及配件\\\"},{\\\"cateId\\\":\\\"9613\\\",\\\"cateNameCn\\\":\\\"窗口曲柄及配件\\\"},{\\\"cateId\\\":\\\"9614\\\",\\\"cateNameCn\\\":\\\"点烟器\\\"},{\\\"cateId\\\":\\\"9615\\\",\\\"cateNameCn\\\":\\\"方向盘与喇叭\\\"},{\\\"cateId\\\":\\\"9616\\\",\\\"cateNameCn\\\":\\\"扶手\\\"},{\\\"cateId\\\":\\\"9617\\\",\\\"cateNameCn\\\":\\\"脚踏板\\\"},{\\\"cateId\\\":\\\"9618\\\",\\\"cateNameCn\\\":\\\"开关/继电器\\\"},{\\\"cateId\\\":\\\"9619\\\",\\\"cateNameCn\\\":\\\"门锁扣保护盖\\\"},{\\\"cateId\\\":\\\"9620\\\",\\\"cateNameCn\\\":\\\"排档头\\\"},{\\\"cateId\\\":\\\"9621\\\",\\\"cateNameCn\\\":\\\"汽车座椅\\\"},{\\\"cateId\\\":\\\"9622\\\",\\\"cateNameCn\\\":\\\"手套箱\\\"},{\\\"cateId\\\":\\\"9623\\\",\\\"cateNameCn\\\":\\\"音响面板\\\"},{\\\"cateId\\\":\\\"9624\\\",\\\"cateNameCn\\\":\\\"遮阳板\\\"}]},{\\\"cateId\\\":\\\"3521\\\",\\\"cateNameCn\\\":\\\"车外部件\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9625\\\",\\\"cateNameCn\\\":\\\"保险杠\\\"},{\\\"cateId\\\":\\\"9626\\\",\\\"cateNameCn\\\":\\\"车标\\\"},{\\\"cateId\\\":\\\"9627\\\",\\\"cateNameCn\\\":\\\"车灯保护\\\"},{\\\"cateId\\\":\\\"9628\\\",\\\"cateNameCn\\\":\\\"车顶行李架\\\"},{\\\"cateId\\\":\\\"9629\\\",\\\"cateNameCn\\\":\\\"车身套件\\\"},{\\\"cateId\\\":\\\"9630\\\",\\\"cateNameCn\\\":\\\"车外后视镜/外壳\\\"},{\\\"cateId\\\":\\\"9631\\\",\\\"cateNameCn\\\":\\\"车外门把手\\\"},{\\\"cateId\\\":\\\"9632\\\",\\\"cateNameCn\\\":\\\"挡泥板1\\\"},{\\\"cateId\\\":\\\"9633\\\",\\\"cateNameCn\\\":\\\"底盘零件\\\"},{\\\"cateId\\\":\\\"9634\\\",\\\"cateNameCn\\\":\\\"防撞胶/装饰条\\\"},{\\\"cateId\\\":\\\"9635\\\",\\\"cateNameCn\\\":\\\"高光镀铬装饰\\\"},{\\\"cateId\\\":\\\"9636\\\",\\\"cateNameCn\\\":\\\"行李箱盖及配件\\\"},{\\\"cateId\\\":\\\"9637\\\",\\\"cateNameCn\\\":\\\"后备箱装饰条\\\"},{\\\"cateId\\\":\\\"9638\\\",\\\"cateNameCn\\\":\\\"门铰链转换套件\\\"},{\\\"cateId\\\":\\\"9639\\\",\\\"cateNameCn\\\":\\\"平衡杆胶套\\\"},{\\\"cateId\\\":\\\"9640\\\",\\\"cateNameCn\\\":\\\"牵引\\\"},{\\\"cateId\\\":\\\"9641\\\",\\\"cateNameCn\\\":\\\"前包围/散热器护栏\\\"},{\\\"cateId\\\":\\\"9642\\\",\\\"cateNameCn\\\":\\\"前端罩\\\"},{\\\"cateId\\\":\\\"9643\\\",\\\"cateNameCn\\\":\\\"前裙板\\\"},{\\\"cateId\\\":\\\"9644\\\",\\\"cateNameCn\\\":\\\"锁具五金\\\"},{\\\"cateId\\\":\\\"9645\\\",\\\"cateNameCn\\\":\\\"天窗/敞篷/顶棚\\\"},{\\\"cateId\\\":\\\"9646\\\",\\\"cateNameCn\\\":\\\"天线\\\"},{\\\"cateId\\\":\\\"9647\\\",\\\"cateNameCn\\\":\\\"外门面板/框架\\\"},{\\\"cateId\\\":\\\"9648\\\",\\\"cateNameCn\\\":\\\"外油箱盖\\\"},{\\\"cateId\\\":\\\"9649\\\",\\\"cateNameCn\\\":\\\"尾翼\\\"},{\\\"cateId\\\":\\\"9650\\\",\\\"cateNameCn\\\":\\\"引擎盖\\\"},{\\\"cateId\\\":\\\"9651\\\",\\\"cateNameCn\\\":\\\"引擎盖饰条\\\"}]},{\\\"cateId\\\":\\\"3522\\\",\\\"cateNameCn\\\":\\\"汽车外饰配件\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9652\\\",\\\"cateNameCn\\\":\\\"边档遮阳挡\\\"},{\\\"cateId\\\":\\\"9653\\\",\\\"cateNameCn\\\":\\\"车贴\\\"},{\\\"cateId\\\":\\\"9654\\\",\\\"cateNameCn\\\":\\\"车衣/支架\\\"},{\\\"cateId\\\":\\\"9655\\\",\\\"cateNameCn\\\":\\\"反光条\\\"},{\\\"cateId\\\":\\\"9656\\\",\\\"cateNameCn\\\":\\\"防爆膜/太阳膜\\\"},{\\\"cateId\\\":\\\"9657\\\",\\\"cateNameCn\\\":\\\"汽车后视镜收纳器\\\"},{\\\"cateId\\\":\\\"9658\\\",\\\"cateNameCn\\\":\\\"汽车牌照架\\\"},{\\\"cateId\\\":\\\"9659\\\",\\\"cateNameCn\\\":\\\"前档遮阳挡\\\"},{\\\"cateId\\\":\\\"9660\\\",\\\"cateNameCn\\\":\\\"晴雨挡\\\"}]},{\\\"cateId\\\":\\\"3523\\\",\\\"cateNameCn\\\":\\\"车载支架\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9661\\\",\\\"cateNameCn\\\":\\\"GPS支架\\\"}]},{\\\"cateId\\\":\\\"3524\\\",\\\"cateNameCn\\\":\\\"传动与动力系统\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9662\\\",\\\"cateNameCn\\\":\\\"CV及部件\\\"},{\\\"cateId\\\":\\\"9663\\\",\\\"cateNameCn\\\":\\\"变速箱重装套件\\\"},{\\\"cateId\\\":\\\"9664\\\",\\\"cateNameCn\\\":\\\"差速器及配件\\\"},{\\\"cateId\\\":\\\"9665\\\",\\\"cateNameCn\\\":\\\"车轴部件\\\"},{\\\"cateId\\\":\\\"9666\\\",\\\"cateNameCn\\\":\\\"飞轮/挠性板/配件\\\"},{\\\"cateId\\\":\\\"9667\\\",\\\"cateNameCn\\\":\\\"离合器及配件\\\"},{\\\"cateId\\\":\\\"9668\\\",\\\"cateNameCn\\\":\\\"密封件\\\"},{\\\"cateId\\\":\\\"9669\\\",\\\"cateNameCn\\\":\\\"排档杆\\\"},{\\\"cateId\\\":\\\"9670\\\",\\\"cateNameCn\\\":\\\"手动变速箱及配件\\\"},{\\\"cateId\\\":\\\"9671\\\",\\\"cateNameCn\\\":\\\"速度计\\\"},{\\\"cateId\\\":\\\"9672\\\",\\\"cateNameCn\\\":\\\"万向节/传动轴\\\"},{\\\"cateId\\\":\\\"9673\\\",\\\"cateNameCn\\\":\\\"压盘\\\"},{\\\"cateId\\\":\\\"9674\\\",\\\"cateNameCn\\\":\\\"液力变矩器\\\"},{\\\"cateId\\\":\\\"9675\\\",\\\"cateNameCn\\\":\\\"转换器\\\"},{\\\"cateId\\\":\\\"9676\\\",\\\"cateNameCn\\\":\\\"自动变速器及配件\\\"}]},{\\\"cateId\\\":\\\"3525\\\",\\\"cateNameCn\\\":\\\"传感器\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9677\\\",\\\"cateNameCn\\\":\\\"ABS传感器\\\"},{\\\"cateId\\\":\\\"9678\\\",\\\"cateNameCn\\\":\\\"爆震传感器\\\"},{\\\"cateId\\\":\\\"9679\\\",\\\"cateNameCn\\\":\\\"侧倾角传感器\\\"},{\\\"cateId\\\":\\\"9680\\\",\\\"cateNameCn\\\":\\\"车身高度传感器\\\"},{\\\"cateId\\\":\\\"9681\\\",\\\"cateNameCn\\\":\\\"节气门位置传感器\\\"},{\\\"cateId\\\":\\\"9682\\\",\\\"cateNameCn\\\":\\\"开关控制信号传感器\\\"},{\\\"cateId\\\":\\\"9683\\\",\\\"cateNameCn\\\":\\\"空气流量传感器\\\"},{\\\"cateId\\\":\\\"9684\\\",\\\"cateNameCn\\\":\\\"里程表传感器\\\"},{\\\"cateId\\\":\\\"9685\\\",\\\"cateNameCn\\\":\\\"曲轴与凸轮轴位置传感器\\\"},{\\\"cateId\\\":\\\"9686\\\",\\\"cateNameCn\\\":\\\"速度传感器\\\"},{\\\"cateId\\\":\\\"9687\\\",\\\"cateNameCn\\\":\\\"温度传感器\\\"},{\\\"cateId\\\":\\\"9688\\\",\\\"cateNameCn\\\":\\\"压力传感器\\\"},{\\\"cateId\\\":\\\"9689\\\",\\\"cateNameCn\\\":\\\"氧传感器\\\"},{\\\"cateId\\\":\\\"9690\\\",\\\"cateNameCn\\\":\\\"转角传感器\\\"},{\\\"cateId\\\":\\\"9691\\\",\\\"cateNameCn\\\":\\\"转矩传感器\\\"}]},{\\\"cateId\\\":\\\"3526\\\",\\\"cateNameCn\\\":\\\"船舶零配件\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9692\\\",\\\"cateNameCn\\\":\\\"船锚\\\"},{\\\"cateId\\\":\\\"9693\\\",\\\"cateNameCn\\\":\\\"船引擎\\\"},{\\\"cateId\\\":\\\"9694\\\",\\\"cateNameCn\\\":\\\"船用螺旋桨\\\"},{\\\"cateId\\\":\\\"9695\\\",\\\"cateNameCn\\\":\\\"船用水泵\\\"},{\\\"cateId\\\":\\\"9696\\\",\\\"cateNameCn\\\":\\\"船用五金\\\"},{\\\"cateId\\\":\\\"9697\\\",\\\"cateNameCn\\\":\\\"船用音响\\\"},{\\\"cateId\\\":\\\"9698\\\",\\\"cateNameCn\\\":\\\"船罩\\\"}]},{\\\"cateId\\\":\\\"3527\\\",\\\"cateNameCn\\\":\\\"点火系统\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9699\\\",\\\"cateNameCn\\\":\\\"点火线圈\\\"},{\\\"cateId\\\":\\\"9700\\\",\\\"cateNameCn\\\":\\\"火花塞/电热塞\\\"},{\\\"cateId\\\":\\\"9701\\\",\\\"cateNameCn\\\":\\\"汽车钥匙\\\"}]},{\\\"cateId\\\":\\\"3528\\\",\\\"cateNameCn\\\":\\\"电动车配件\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9702\\\",\\\"cateNameCn\\\":\\\"充电器/服务设备\\\"},{\\\"cateId\\\":\\\"9703\\\",\\\"cateNameCn\\\":\\\"电动车电池\\\"},{\\\"cateId\\\":\\\"9704\\\",\\\"cateNameCn\\\":\\\"电动车电机\\\"},{\\\"cateId\\\":\\\"9705\\\",\\\"cateNameCn\\\":\\\"控制器\\\"},{\\\"cateId\\\":\\\"9706\\\",\\\"cateNameCn\\\":\\\"配件\\\"},{\\\"cateId\\\":\\\"9707\\\",\\\"cateNameCn\\\":\\\"转化套件\\\"},{\\\"cateId\\\":\\\"9708\\\",\\\"cateNameCn\\\":\\\"转换器/逆变器\\\"}]},{\\\"cateId\\\":\\\"3529\\\",\\\"cateNameCn\\\":\\\"电源及启动系统\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9709\\\",\\\"cateNameCn\\\":\\\"充电器\\\"},{\\\"cateId\\\":\\\"9710\\\",\\\"cateNameCn\\\":\\\"电池电缆及连接器\\\"},{\\\"cateId\\\":\\\"9711\\\",\\\"cateNameCn\\\":\\\"电池托盘\\\"},{\\\"cateId\\\":\\\"9712\\\",\\\"cateNameCn\\\":\\\"电瓶测量\\\"},{\\\"cateId\\\":\\\"9713\\\",\\\"cateNameCn\\\":\\\"电压调节器\\\"},{\\\"cateId\\\":\\\"9714\\\",\\\"cateNameCn\\\":\\\"交流发电机及发电机组\\\"},{\\\"cateId\\\":\\\"9715\\\",\\\"cateNameCn\\\":\\\"交流发电机及发电机组配件\\\"},{\\\"cateId\\\":\\\"9716\\\",\\\"cateNameCn\\\":\\\"启动器\\\"},{\\\"cateId\\\":\\\"9717\\\",\\\"cateNameCn\\\":\\\"启动器配件\\\"},{\\\"cateId\\\":\\\"9718\\\",\\\"cateNameCn\\\":\\\"汽车电池及附件\\\"}]},{\\\"cateId\\\":\\\"3530\\\",\\\"cateNameCn\\\":\\\"垫片\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9719\\\",\\\"cateNameCn\\\":\\\"进气垫片\\\"},{\\\"cateId\\\":\\\"9720\\\",\\\"cateNameCn\\\":\\\"排气垫片\\\"},{\\\"cateId\\\":\\\"9721\\\",\\\"cateNameCn\\\":\\\"汽缸头/阀盖垫片\\\"},{\\\"cateId\\\":\\\"9722\\\",\\\"cateNameCn\\\":\\\"全套垫片\\\"}]},{\\\"cateId\\\":\\\"3531\\\",\\\"cateNameCn\\\":\\\"发动机及零部件\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9723\\\",\\\"cateNameCn\\\":\\\"发动机大修包\\\"},{\\\"cateId\\\":\\\"9724\\\",\\\"cateNameCn\\\":\\\"发动机罩\\\"},{\\\"cateId\\\":\\\"9725\\\",\\\"cateNameCn\\\":\\\"发动机轴承\\\"},{\\\"cateId\\\":\\\"9726\\\",\\\"cateNameCn\\\":\\\"阀盖\\\"},{\\\"cateId\\\":\\\"9727\\\",\\\"cateNameCn\\\":\\\"阀门及配件\\\"},{\\\"cateId\\\":\\\"9728\\\",\\\"cateNameCn\\\":\\\"缸体及零件\\\"},{\\\"cateId\\\":\\\"9729\\\",\\\"cateNameCn\\\":\\\"活塞/环/杆件\\\"},{\\\"cateId\\\":\\\"9730\\\",\\\"cateNameCn\\\":\\\"空气旁通阀\\\"},{\\\"cateId\\\":\\\"9731\\\",\\\"cateNameCn\\\":\\\"皮带/皮带轮/支架\\\"},{\\\"cateId\\\":\\\"9732\\\",\\\"cateNameCn\\\":\\\"气缸体及配件\\\"},{\\\"cateId\\\":\\\"9733\\\",\\\"cateNameCn\\\":\\\"汽车钥匙改装壳\\\"},{\\\"cateId\\\":\\\"9734\\\",\\\"cateNameCn\\\":\\\"曲轴及零件\\\"},{\\\"cateId\\\":\\\"9735\\\",\\\"cateNameCn\\\":\\\"凸轮轴/挺杆及配件\\\"},{\\\"cateId\\\":\\\"9736\\\",\\\"cateNameCn\\\":\\\"涡轮增压器\\\"},{\\\"cateId\\\":\\\"9737\\\",\\\"cateNameCn\\\":\\\"摇臂及零件\\\"},{\\\"cateId\\\":\\\"9738\\\",\\\"cateNameCn\\\":\\\"引擎\\\"},{\\\"cateId\\\":\\\"9739\\\",\\\"cateNameCn\\\":\\\"真空泵\\\"},{\\\"cateId\\\":\\\"9740\\\",\\\"cateNameCn\\\":\\\"正时系统\\\"}]},{\\\"cateId\\\":\\\"3534\\\",\\\"cateNameCn\\\":\\\"卡车配件\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9758\\\",\\\"cateNameCn\\\":\\\"卡车变速箱部件\\\"},{\\\"cateId\\\":\\\"9759\\\",\\\"cateNameCn\\\":\\\"卡车刹车部件\\\"},{\\\"cateId\\\":\\\"9760\\\",\\\"cateNameCn\\\":\\\"卡车车轮部件\\\"},{\\\"cateId\\\":\\\"9761\\\",\\\"cateNameCn\\\":\\\"卡车车身部件\\\"},{\\\"cateId\\\":\\\"9762\\\",\\\"cateNameCn\\\":\\\"卡车车胎\\\"},{\\\"cateId\\\":\\\"9763\\\",\\\"cateNameCn\\\":\\\"卡车传动件/车桥件\\\"},{\\\"cateId\\\":\\\"9764\\\",\\\"cateNameCn\\\":\\\"卡车灯件\\\"},{\\\"cateId\\\":\\\"9765\\\",\\\"cateNameCn\\\":\\\"卡车附件\\\"},{\\\"cateId\\\":\\\"9766\\\",\\\"cateNameCn\\\":\\\"卡车交流发电机\\\"},{\\\"cateId\\\":\\\"9767\\\",\\\"cateNameCn\\\":\\\"卡车开关\\\"},{\\\"cateId\\\":\\\"9768\\\",\\\"cateNameCn\\\":\\\"卡车起动系统\\\"},{\\\"cateId\\\":\\\"9769\\\",\\\"cateNameCn\\\":\\\"卡车蓄电池\\\"},{\\\"cateId\\\":\\\"9770\\\",\\\"cateNameCn\\\":\\\"卡车悬挂部件\\\"},{\\\"cateId\\\":\\\"9771\\\",\\\"cateNameCn\\\":\\\"卡车引擎部件\\\"},{\\\"cateId\\\":\\\"9772\\\",\\\"cateNameCn\\\":\\\"卡车转向部件\\\"}]},{\\\"cateId\\\":\\\"3535\\\",\\\"cateNameCn\\\":\\\"冷却系统\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9773\\\",\\\"cateNameCn\\\":\\\"防冻液/冷却液\\\"},{\\\"cateId\\\":\\\"9774\\\",\\\"cateNameCn\\\":\\\"风扇总成\\\"},{\\\"cateId\\\":\\\"9775\\\",\\\"cateNameCn\\\":\\\"软管及夹具\\\"},{\\\"cateId\\\":\\\"9776\\\",\\\"cateNameCn\\\":\\\"水泵\\\"},{\\\"cateId\\\":\\\"9777\\\",\\\"cateNameCn\\\":\\\"温控器及配件\\\"}]},{\\\"cateId\\\":\\\"3536\\\",\\\"cateNameCn\\\":\\\"轮毂/轮胎及配件\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9778\\\",\\\"cateNameCn\\\":\\\"阀门杆及盖\\\"},{\\\"cateId\\\":\\\"9779\\\",\\\"cateNameCn\\\":\\\"轮毂\\\"},{\\\"cateId\\\":\\\"9780\\\",\\\"cateNameCn\\\":\\\"胎压监测系统\\\"}]},{\\\"cateId\\\":\\\"3537\\\",\\\"cateNameCn\\\":\\\"摩托车防护用具\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9781\\\",\\\"cateNameCn\\\":\\\"衬衫/T恤\\\"},{\\\"cateId\\\":\\\"9782\\\",\\\"cateNameCn\\\":\\\"防护护膝\\\"},{\\\"cateId\\\":\\\"9783\\\",\\\"cateNameCn\\\":\\\"防护套装\\\"},{\\\"cateId\\\":\\\"9784\\\",\\\"cateNameCn\\\":\\\"夹克1\\\"},{\\\"cateId\\\":\\\"9785\\\",\\\"cateNameCn\\\":\\\"摩托车包\\\"},{\\\"cateId\\\":\\\"9786\\\",\\\"cateNameCn\\\":\\\"摩托车保暖面罩\\\"},{\\\"cateId\\\":\\\"9787\\\",\\\"cateNameCn\\\":\\\"眼镜/风镜\\\"},{\\\"cateId\\\":\\\"9788\\\",\\\"cateNameCn\\\":\\\"长裤\\\"}]},{\\\"cateId\\\":\\\"3538\\\",\\\"cateNameCn\\\":\\\"摩托车配件\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9789\\\",\\\"cateNameCn\\\":\\\"安全与故障援助\\\"},{\\\"cateId\\\":\\\"9790\\\",\\\"cateNameCn\\\":\\\"边衬\\\"},{\\\"cateId\\\":\\\"9791\\\",\\\"cateNameCn\\\":\\\"侧镜/配件\\\"},{\\\"cateId\\\":\\\"9792\\\",\\\"cateNameCn\\\":\\\"车盖/保护膜\\\"},{\\\"cateId\\\":\\\"9793\\\",\\\"cateNameCn\\\":\\\"车牌架\\\"},{\\\"cateId\\\":\\\"9794\\\",\\\"cateNameCn\\\":\\\"大灯支架\\\"},{\\\"cateId\\\":\\\"9795\\\",\\\"cateNameCn\\\":\\\"挡风玻璃/导风板\\\"},{\\\"cateId\\\":\\\"9796\\\",\\\"cateNameCn\\\":\\\"短裤\\\"},{\\\"cateId\\\":\\\"9797\\\",\\\"cateNameCn\\\":\\\"防盗保护\\\"},{\\\"cateId\\\":\\\"9798\\\",\\\"cateNameCn\\\":\\\"风扇罩\\\"},{\\\"cateId\\\":\\\"9799\\\",\\\"cateNameCn\\\":\\\"护理用品\\\"},{\\\"cateId\\\":\\\"9800\\\",\\\"cateNameCn\\\":\\\"机壳/装饰框条\\\"},{\\\"cateId\\\":\\\"9801\\\",\\\"cateNameCn\\\":\\\"机油滤清器\\\"},{\\\"cateId\\\":\\\"9802\\\",\\\"cateNameCn\\\":\\\"脚踏板\\\"},{\\\"cateId\\\":\\\"9803\\\",\\\"cateNameCn\\\":\\\"空气滤清器\\\"},{\\\"cateId\\\":\\\"9804\\\",\\\"cateNameCn\\\":\\\"落地保护\\\"},{\\\"cateId\\\":\\\"9805\\\",\\\"cateNameCn\\\":\\\"摩托车安全带\\\"},{\\\"cateId\\\":\\\"9806\\\",\\\"cateNameCn\\\":\\\"摩托车保险杠/底盘\\\"},{\\\"cateId\\\":\\\"9807\\\",\\\"cateNameCn\\\":\\\"摩托车刹车\\\"},{\\\"cateId\\\":\\\"9808\\\",\\\"cateNameCn\\\":\\\"摩托车车轮/轮圈\\\"},{\\\"cateId\\\":\\\"9809\\\",\\\"cateNameCn\\\":\\\"摩托车传动系统/齿轮\\\"},{\\\"cateId\\\":\\\"9810\\\",\\\"cateNameCn\\\":\\\"摩托车点火装置\\\"},{\\\"cateId\\\":\\\"9811\\\",\\\"cateNameCn\\\":\\\"摩托车电机\\\"},{\\\"cateId\\\":\\\"9812\\\",\\\"cateNameCn\\\":\\\"摩托车发动机/部件\\\"},{\\\"cateId\\\":\\\"9813\\\",\\\"cateNameCn\\\":\\\"摩托车防护靴\\\"},{\\\"cateId\\\":\\\"9814\\\",\\\"cateNameCn\\\":\\\"摩托车行李网\\\"},{\\\"cateId\\\":\\\"9815\\\",\\\"cateNameCn\\\":\\\"摩托车排气系统\\\"},{\\\"cateId\\\":\\\"9816\\\",\\\"cateNameCn\\\":\\\"摩托车起动机\\\"},{\\\"cateId\\\":\\\"9817\\\",\\\"cateNameCn\\\":\\\"摩托车燃料系统\\\"},{\\\"cateId\\\":\\\"9818\\\",\\\"cateNameCn\\\":\\\"摩托车润滑油\\\"},{\\\"cateId\\\":\\\"9819\\\",\\\"cateNameCn\\\":\\\"摩托车蓄电池\\\"},{\\\"cateId\\\":\\\"9820\\\",\\\"cateNameCn\\\":\\\"摩托车衣\\\"},{\\\"cateId\\\":\\\"9821\\\",\\\"cateNameCn\\\":\\\"摩托车仪表\\\"},{\\\"cateId\\\":\\\"9822\\\",\\\"cateNameCn\\\":\\\"摩托车照明\\\"},{\\\"cateId\\\":\\\"9823\\\",\\\"cateNameCn\\\":\\\"摩托车转换器开关\\\"},{\\\"cateId\\\":\\\"9824\\\",\\\"cateNameCn\\\":\\\"摩托车座垫\\\"},{\\\"cateId\\\":\\\"9825\\\",\\\"cateNameCn\\\":\\\"摩托车用润滑剂\\\"},{\\\"cateId\\\":\\\"9826\\\",\\\"cateNameCn\\\":\\\"手把\\\"},{\\\"cateId\\\":\\\"9827\\\",\\\"cateNameCn\\\":\\\"手套1\\\"},{\\\"cateId\\\":\\\"9828\\\",\\\"cateNameCn\\\":\\\"锁/插销\\\"},{\\\"cateId\\\":\\\"9829\\\",\\\"cateNameCn\\\":\\\"贴纸\\\"},{\\\"cateId\\\":\\\"9830\\\",\\\"cateNameCn\\\":\\\"停车架\\\"},{\\\"cateId\\\":\\\"9831\\\",\\\"cateNameCn\\\":\\\"头盔\\\"},{\\\"cateId\\\":\\\"9832\\\",\\\"cateNameCn\\\":\\\"头盔对讲机\\\"},{\\\"cateId\\\":\\\"9833\\\",\\\"cateNameCn\\\":\\\"拖车/挂车接头\\\"},{\\\"cateId\\\":\\\"9834\\\",\\\"cateNameCn\\\":\\\"握把\\\"},{\\\"cateId\\\":\\\"9835\\\",\\\"cateNameCn\\\":\\\"引擎保险盖\\\"},{\\\"cateId\\\":\\\"9836\\\",\\\"cateNameCn\\\":\\\"油漆/喷雾\\\"},{\\\"cateId\\\":\\\"9837\\\",\\\"cateNameCn\\\":\\\"整流罩套件\\\"},{\\\"cateId\\\":\\\"9838\\\",\\\"cateNameCn\\\":\\\"座椅/长凳\\\"}]},{\\\"cateId\\\":\\\"3539\\\",\\\"cateNameCn\\\":\\\"汽车内饰件\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9839\\\",\\\"cateNameCn\\\":\\\"安全带/安全带套\\\"},{\\\"cateId\\\":\\\"9840\\\",\\\"cateNameCn\\\":\\\"笔记本支架\\\"},{\\\"cateId\\\":\\\"9841\\\",\\\"cateNameCn\\\":\\\"车内镜\\\"},{\\\"cateId\\\":\\\"9842\\\",\\\"cateNameCn\\\":\\\"车内装饰条\\\"},{\\\"cateId\\\":\\\"9843\\\",\\\"cateNameCn\\\":\\\"车衣/支架1\\\"},{\\\"cateId\\\":\\\"9844\\\",\\\"cateNameCn\\\":\\\"车用挂饰/摆件\\\"},{\\\"cateId\\\":\\\"9845\\\",\\\"cateNameCn\\\":\\\"车用夹子/扣件\\\"},{\\\"cateId\\\":\\\"9846\\\",\\\"cateNameCn\\\":\\\"车用靠垫\\\"},{\\\"cateId\\\":\\\"9847\\\",\\\"cateNameCn\\\":\\\"车载行李车\\\"},{\\\"cateId\\\":\\\"9848\\\",\\\"cateNameCn\\\":\\\"车载衣服架\\\"},{\\\"cateId\\\":\\\"9849\\\",\\\"cateNameCn\\\":\\\"方向盘套\\\"},{\\\"cateId\\\":\\\"9850\\\",\\\"cateNameCn\\\":\\\"防滑垫(固定手机等设备)\\\"},{\\\"cateId\\\":\\\"9851\\\",\\\"cateNameCn\\\":\\\"防瞌睡提醒器\\\"},{\\\"cateId\\\":\\\"9852\\\",\\\"cateNameCn\\\":\\\"行车记录仪支架\\\"},{\\\"cateId\\\":\\\"9853\\\",\\\"cateNameCn\\\":\\\"后备箱储物/支架\\\"},{\\\"cateId\\\":\\\"9854\\\",\\\"cateNameCn\\\":\\\"后备箱垫\\\"},{\\\"cateId\\\":\\\"9855\\\",\\\"cateNameCn\\\":\\\"脚垫\\\"},{\\\"cateId\\\":\\\"9856\\\",\\\"cateNameCn\\\":\\\"空气清新用品\\\"},{\\\"cateId\\\":\\\"9857\\\",\\\"cateNameCn\\\":\\\"门槽垫\\\"},{\\\"cateId\\\":\\\"9858\\\",\\\"cateNameCn\\\":\\\"排挡套\\\"},{\\\"cateId\\\":\\\"9859\\\",\\\"cateNameCn\\\":\\\"平板支架\\\"},{\\\"cateId\\\":\\\"9860\\\",\\\"cateNameCn\\\":\\\"汽车钥匙包\\\"},{\\\"cateId\\\":\\\"9861\\\",\\\"cateNameCn\\\":\\\"汽车座套/坐垫\\\"},{\\\"cateId\\\":\\\"9862\\\",\\\"cateNameCn\\\":\\\"收纳整理\\\"},{\\\"cateId\\\":\\\"9863\\\",\\\"cateNameCn\\\":\\\"手刹套\\\"},{\\\"cateId\\\":\\\"9864\\\",\\\"cateNameCn\\\":\\\"手机座\\\"},{\\\"cateId\\\":\\\"9865\\\",\\\"cateNameCn\\\":\\\"停车辅助(非电子类产品)\\\"},{\\\"cateId\\\":\\\"9866\\\",\\\"cateNameCn\\\":\\\"通用支架\\\"},{\\\"cateId\\\":\\\"9867\\\",\\\"cateNameCn\\\":\\\"眼镜架2\\\"},{\\\"cateId\\\":\\\"9868\\\",\\\"cateNameCn\\\":\\\"钥匙环\\\"},{\\\"cateId\\\":\\\"9869\\\",\\\"cateNameCn\\\":\\\"饮料架\\\"},{\\\"cateId\\\":\\\"9870\\\",\\\"cateNameCn\\\":\\\"杂物袋\\\"},{\\\"cateId\\\":\\\"9871\\\",\\\"cateNameCn\\\":\\\"杂物架\\\"},{\\\"cateId\\\":\\\"9872\\\",\\\"cateNameCn\\\":\\\"张力绳\\\"}]},{\\\"cateId\\\":\\\"3540\\\",\\\"cateNameCn\\\":\\\"排气系统\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9873\\\",\\\"cateNameCn\\\":\\\"车用单向阀\\\"},{\\\"cateId\\\":\\\"9874\\\",\\\"cateNameCn\\\":\\\"催化转化器\\\"},{\\\"cateId\\\":\\\"9875\\\",\\\"cateNameCn\\\":\\\"挡热板\\\"},{\\\"cateId\\\":\\\"9876\\\",\\\"cateNameCn\\\":\\\"电子点火系统\\\"},{\\\"cateId\\\":\\\"9878\\\",\\\"cateNameCn\\\":\\\"废气清洁及再循环\\\"},{\\\"cateId\\\":\\\"9879\\\",\\\"cateNameCn\\\":\\\"废弃循环阀\\\"},{\\\"cateId\\\":\\\"9880\\\",\\\"cateNameCn\\\":\\\"分电器及配件\\\"},{\\\"cateId\\\":\\\"9881\\\",\\\"cateNameCn\\\":\\\"盖子/转子/触点\\\"},{\\\"cateId\\\":\\\"9882\\\",\\\"cateNameCn\\\":\\\"排气歧管\\\"},{\\\"cateId\\\":\\\"9883\\\",\\\"cateNameCn\\\":\\\"排气头\\\"},{\\\"cateId\\\":\\\"9884\\\",\\\"cateNameCn\\\":\\\"排气总成\\\"},{\\\"cateId\\\":\\\"9885\\\",\\\"cateNameCn\\\":\\\"热管\\\"},{\\\"cateId\\\":\\\"9887\\\",\\\"cateNameCn\\\":\\\"消声器\\\"},{\\\"cateId\\\":\\\"9888\\\",\\\"cateNameCn\\\":\\\"悬挂/夹具/法兰\\\"},{\\\"cateId\\\":\\\"9889\\\",\\\"cateNameCn\\\":\\\"烟雾/空气泵\\\"},{\\\"cateId\\\":\\\"9890\\\",\\\"cateNameCn\\\":\\\"钥匙坯\\\"}]},{\\\"cateId\\\":\\\"3541\\\",\\\"cateNameCn\\\":\\\"其它机动车零配件\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9891\\\",\\\"cateNameCn\\\":\\\"敞篷车附件\\\"},{\\\"cateId\\\":\\\"9892\\\",\\\"cateNameCn\\\":\\\"电动车\\\"},{\\\"cateId\\\":\\\"9893\\\",\\\"cateNameCn\\\":\\\"房车配件/附件\\\"},{\\\"cateId\\\":\\\"9894\\\",\\\"cateNameCn\\\":\\\"高尔夫球车配件\\\"},{\\\"cateId\\\":\\\"9895\\\",\\\"cateNameCn\\\":\\\"古董机动车配件\\\"},{\\\"cateId\\\":\\\"9896\\\",\\\"cateNameCn\\\":\\\"航空器零配件\\\"},{\\\"cateId\\\":\\\"9897\\\",\\\"cateNameCn\\\":\\\"救助车零配件\\\"},{\\\"cateId\\\":\\\"9898\\\",\\\"cateNameCn\\\":\\\"卡丁车配件\\\"},{\\\"cateId\\\":\\\"9899\\\",\\\"cateNameCn\\\":\\\"沙滩车配件/附件\\\"},{\\\"cateId\\\":\\\"9900\\\",\\\"cateNameCn\\\":\\\"水上摩托零配件\\\"},{\\\"cateId\\\":\\\"9901\\\",\\\"cateNameCn\\\":\\\"雪地车零配件\\\"}]},{\\\"cateId\\\":\\\"3542\\\",\\\"cateNameCn\\\":\\\"车载电子\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9902\\\",\\\"cateNameCn\\\":\\\"GPS跟踪器\\\"},{\\\"cateId\\\":\\\"9903\\\",\\\"cateNameCn\\\":\\\"GPS配件\\\"},{\\\"cateId\\\":\\\"9904\\\",\\\"cateNameCn\\\":\\\"冰箱\\\"},{\\\"cateId\\\":\\\"9905\\\",\\\"cateNameCn\\\":\\\"测速雷达(仅有测速功能)\\\"},{\\\"cateId\\\":\\\"9906\\\",\\\"cateNameCn\\\":\\\"车内空气净化器\\\"},{\\\"cateId\\\":\\\"9907\\\",\\\"cateNameCn\\\":\\\"车用对讲机\\\"},{\\\"cateId\\\":\\\"9908\\\",\\\"cateNameCn\\\":\\\"车用吸尘器\\\"},{\\\"cateId\\\":\\\"9909\\\",\\\"cateNameCn\\\":\\\"车载GPS设备\\\"},{\\\"cateId\\\":\\\"9910\\\",\\\"cateNameCn\\\":\\\"车载MP4/MP5\\\"},{\\\"cateId\\\":\\\"9911\\\",\\\"cateNameCn\\\":\\\"车载充电器\\\"},{\\\"cateId\\\":\\\"9912\\\",\\\"cateNameCn\\\":\\\"车载电脑\\\"},{\\\"cateId\\\":\\\"9913\\\",\\\"cateNameCn\\\":\\\"车载电源\\\"},{\\\"cateId\\\":\\\"9914\\\",\\\"cateNameCn\\\":\\\"车载逆变器\\\"},{\\\"cateId\\\":\\\"9915\\\",\\\"cateNameCn\\\":\\\"车载数字电视盒\\\"},{\\\"cateId\\\":\\\"9916\\\",\\\"cateNameCn\\\":\\\"车载显示器\\\"},{\\\"cateId\\\":\\\"9917\\\",\\\"cateNameCn\\\":\\\"车载遥控器\\\"},{\\\"cateId\\\":\\\"9918\\\",\\\"cateNameCn\\\":\\\"船用GPS\\\"},{\\\"cateId\\\":\\\"9919\\\",\\\"cateNameCn\\\":\\\"倒车雷达\\\"},{\\\"cateId\\\":\\\"9920\\\",\\\"cateNameCn\\\":\\\"低音炮机箱/外壳\\\"},{\\\"cateId\\\":\\\"9921\\\",\\\"cateNameCn\\\":\\\"低音音箱\\\"},{\\\"cateId\\\":\\\"9922\\\",\\\"cateNameCn\\\":\\\"电缆/适配器/插座\\\"},{\\\"cateId\\\":\\\"9923\\\",\\\"cateNameCn\\\":\\\"电瓶搭线\\\"},{\\\"cateId\\\":\\\"9924\\\",\\\"cateNameCn\\\":\\\"电视调谐器\\\"},{\\\"cateId\\\":\\\"9925\\\",\\\"cateNameCn\\\":\\\"行车记录仪/车载录像机\\\"},{\\\"cateId\\\":\\\"9926\\\",\\\"cateNameCn\\\":\\\"机动车摄像头\\\"},{\\\"cateId\\\":\\\"9927\\\",\\\"cateNameCn\\\":\\\"接收器/天线\\\"},{\\\"cateId\\\":\\\"9928\\\",\\\"cateNameCn\\\":\\\"均衡器\\\"},{\\\"cateId\\\":\\\"9929\\\",\\\"cateNameCn\\\":\\\"蓝牙设备\\\"},{\\\"cateId\\\":\\\"9930\\\",\\\"cateNameCn\\\":\\\"摩托车GPS\\\"},{\\\"cateId\\\":\\\"9931\\\",\\\"cateNameCn\\\":\\\"汽车收音机\\\"},{\\\"cateId\\\":\\\"9932\\\",\\\"cateNameCn\\\":\\\"取暖/风扇\\\"},{\\\"cateId\\\":\\\"9933\\\",\\\"cateNameCn\\\":\\\"手持/户外GPS(非车上使用)\\\"},{\\\"cateId\\\":\\\"9934\\\",\\\"cateNameCn\\\":\\\"抬头显示器\\\"},{\\\"cateId\\\":\\\"9935\\\",\\\"cateNameCn\\\":\\\"调频发射机\\\"},{\\\"cateId\\\":\\\"9936\\\",\\\"cateNameCn\\\":\\\"扬声器\\\"},{\\\"cateId\\\":\\\"9937\\\",\\\"cateNameCn\\\":\\\"扬声器格板\\\"},{\\\"cateId\\\":\\\"9938\\\",\\\"cateNameCn\\\":\\\"扬声器机箱\\\"},{\\\"cateId\\\":\\\"9939\\\",\\\"cateNameCn\\\":\\\"音箱线\\\"},{\\\"cateId\\\":\\\"11097\\\",\\\"cateNameCn\\\":\\\"视频播放器\\\"},{\\\"cateId\\\":\\\"11116\\\",\\\"cateNameCn\\\":\\\"车载CD播放器\\\"},{\\\"cateId\\\":\\\"11146\\\",\\\"cateNameCn\\\":\\\"防盗报警\\\"},{\\\"cateId\\\":\\\"11288\\\",\\\"cateNameCn\\\":\\\"车载MP3播放器\\\"}]},{\\\"cateId\\\":\\\"3543\\\",\\\"cateNameCn\\\":\\\"汽车滤清器\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9940\\\",\\\"cateNameCn\\\":\\\"变频分离过滤器\\\"},{\\\"cateId\\\":\\\"9941\\\",\\\"cateNameCn\\\":\\\"传动滤清器\\\"},{\\\"cateId\\\":\\\"9942\\\",\\\"cateNameCn\\\":\\\"机油滤清器\\\"},{\\\"cateId\\\":\\\"9943\\\",\\\"cateNameCn\\\":\\\"空气滤清器\\\"},{\\\"cateId\\\":\\\"9944\\\",\\\"cateNameCn\\\":\\\"空调滤清器\\\"},{\\\"cateId\\\":\\\"9945\\\",\\\"cateNameCn\\\":\\\"燃油滤清器\\\"}]},{\\\"cateId\\\":\\\"3544\\\",\\\"cateNameCn\\\":\\\"燃油供应系统\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9946\\\",\\\"cateNameCn\\\":\\\"备用油箱\\\"},{\\\"cateId\\\":\\\"9947\\\",\\\"cateNameCn\\\":\\\"翻转阀\\\"},{\\\"cateId\\\":\\\"9948\\\",\\\"cateNameCn\\\":\\\"化油器\\\"},{\\\"cateId\\\":\\\"9949\\\",\\\"cateNameCn\\\":\\\"化油器部件\\\"},{\\\"cateId\\\":\\\"9950\\\",\\\"cateNameCn\\\":\\\"内油箱盖\\\"},{\\\"cateId\\\":\\\"9951\\\",\\\"cateNameCn\\\":\\\"喷油嘴控制及配件\\\"},{\\\"cateId\\\":\\\"9952\\\",\\\"cateNameCn\\\":\\\"燃油泵\\\"},{\\\"cateId\\\":\\\"9953\\\",\\\"cateNameCn\\\":\\\"油气分离器\\\"},{\\\"cateId\\\":\\\"9954\\\",\\\"cateNameCn\\\":\\\"油压调节器\\\"}]},{\\\"cateId\\\":\\\"3545\\\",\\\"cateNameCn\\\":\\\"润滑系统\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9955\\\",\\\"cateNameCn\\\":\\\"机油泵链条\\\"},{\\\"cateId\\\":\\\"9956\\\",\\\"cateNameCn\\\":\\\"机油泵吸油管\\\"},{\\\"cateId\\\":\\\"9957\\\",\\\"cateNameCn\\\":\\\"机油集滤器\\\"},{\\\"cateId\\\":\\\"9958\\\",\\\"cateNameCn\\\":\\\"机油加注盖\\\"},{\\\"cateId\\\":\\\"9959\\\",\\\"cateNameCn\\\":\\\"润滑油\\\"},{\\\"cateId\\\":\\\"9960\\\",\\\"cateNameCn\\\":\\\"润滑油添加剂\\\"},{\\\"cateId\\\":\\\"9961\\\",\\\"cateNameCn\\\":\\\"润滑脂\\\"},{\\\"cateId\\\":\\\"9962\\\",\\\"cateNameCn\\\":\\\"油泵\\\"},{\\\"cateId\\\":\\\"9963\\\",\\\"cateNameCn\\\":\\\"油盘\\\"}]},{\\\"cateId\\\":\\\"3546\\\",\\\"cateNameCn\\\":\\\"维修保养\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9964\\\",\\\"cateNameCn\\\":\\\"钣金尺\\\"},{\\\"cateId\\\":\\\"9965\\\",\\\"cateNameCn\\\":\\\"钣金锤\\\"},{\\\"cateId\\\":\\\"9966\\\",\\\"cateNameCn\\\":\\\"钣金工具套件\\\"},{\\\"cateId\\\":\\\"9967\\\",\\\"cateNameCn\\\":\\\"车窗清洁\\\"},{\\\"cateId\\\":\\\"9968\\\",\\\"cateNameCn\\\":\\\"车胎维修工具\\\"},{\\\"cateId\\\":\\\"9969\\\",\\\"cateNameCn\\\":\\\"冲头\\\"},{\\\"cateId\\\":\\\"9970\\\",\\\"cateNameCn\\\":\\\"充气泵\\\"},{\\\"cateId\\\":\\\"9971\\\",\\\"cateNameCn\\\":\\\"锉刀\\\"},{\\\"cateId\\\":\\\"9972\\\",\\\"cateNameCn\\\":\\\"打板\\\"},{\\\"cateId\\\":\\\"9973\\\",\\\"cateNameCn\\\":\\\"顶铁\\\"},{\\\"cateId\\\":\\\"9974\\\",\\\"cateNameCn\\\":\\\"读码器/扫描工具\\\"},{\\\"cateId\\\":\\\"9975\\\",\\\"cateNameCn\\\":\\\"发动机保养\\\"},{\\\"cateId\\\":\\\"9976\\\",\\\"cateNameCn\\\":\\\"防冻膜\\\"},{\\\"cateId\\\":\\\"9977\\\",\\\"cateNameCn\\\":\\\"防冻液\\\"},{\\\"cateId\\\":\\\"9978\\\",\\\"cateNameCn\\\":\\\"防锈/除锈\\\"},{\\\"cateId\\\":\\\"9979\\\",\\\"cateNameCn\\\":\\\"海绵/抹布/刷子\\\"},{\\\"cateId\\\":\\\"9980\\\",\\\"cateNameCn\\\":\\\"划针\\\"},{\\\"cateId\\\":\\\"9981\\\",\\\"cateNameCn\\\":\\\"驾驶舱保养\\\"},{\\\"cateId\\\":\\\"9982\\\",\\\"cateNameCn\\\":\\\"胶枪\\\"},{\\\"cateId\\\":\\\"9983\\\",\\\"cateNameCn\\\":\\\"角磨机\\\"},{\\\"cateId\\\":\\\"9984\\\",\\\"cateNameCn\\\":\\\"节油用品\\\"},{\\\"cateId\\\":\\\"9985\\\",\\\"cateNameCn\\\":\\\"救生锤\\\"},{\\\"cateId\\\":\\\"9986\\\",\\\"cateNameCn\\\":\\\"轮毂保养\\\"},{\\\"cateId\\\":\\\"9987\\\",\\\"cateNameCn\\\":\\\"轮毂盖\\\"},{\\\"cateId\\\":\\\"9988\\\",\\\"cateNameCn\\\":\\\"轮毂轮胎包\\\"},{\\\"cateId\\\":\\\"9989\\\",\\\"cateNameCn\\\":\\\"轮毂中心盖\\\"},{\\\"cateId\\\":\\\"9990\\\",\\\"cateNameCn\\\":\\\"轮毂轴承\\\"},{\\\"cateId\\\":\\\"9991\\\",\\\"cateNameCn\\\":\\\"轮胎\\\"},{\\\"cateId\\\":\\\"9992\\\",\\\"cateNameCn\\\":\\\"轮胎防滑钉\\\"},{\\\"cateId\\\":\\\"9993\\\",\\\"cateNameCn\\\":\\\"轮胎附件\\\"},{\\\"cateId\\\":\\\"9994\\\",\\\"cateNameCn\\\":\\\"轮胎链\\\"},{\\\"cateId\\\":\\\"9995\\\",\\\"cateNameCn\\\":\\\"轮胎上光\\\"},{\\\"cateId\\\":\\\"9996\\\",\\\"cateNameCn\\\":\\\"密封条/粘合剂\\\"},{\\\"cateId\\\":\\\"9997\\\",\\\"cateNameCn\\\":\\\"皮革/地毯清洁\\\"},{\\\"cateId\\\":\\\"9998\\\",\\\"cateNameCn\\\":\\\"漆面保养\\\"},{\\\"cateId\\\":\\\"9999\\\",\\\"cateNameCn\\\":\\\"汽车方向盘锁\\\"},{\\\"cateId\\\":\\\"10000\\\",\\\"cateNameCn\\\":\\\"汽车清洗器\\\"},{\\\"cateId\\\":\\\"10001\\\",\\\"cateNameCn\\\":\\\"千斤顶\\\"},{\\\"cateId\\\":\\\"10002\\\",\\\"cateNameCn\\\":\\\"撬镐\\\"},{\\\"cateId\\\":\\\"10003\\\",\\\"cateNameCn\\\":\\\"润滑剂\\\"},{\\\"cateId\\\":\\\"10004\\\",\\\"cateNameCn\\\":\\\"砂带机\\\"},{\\\"cateId\\\":\\\"10005\\\",\\\"cateNameCn\\\":\\\"手电钻\\\"},{\\\"cateId\\\":\\\"10006\\\",\\\"cateNameCn\\\":\\\"塑料/橡胶制品保养\\\"},{\\\"cateId\\\":\\\"10007\\\",\\\"cateNameCn\\\":\\\"添加剂\\\"},{\\\"cateId\\\":\\\"10008\\\",\\\"cateNameCn\\\":\\\"拖车杆\\\"},{\\\"cateId\\\":\\\"10009\\\",\\\"cateNameCn\\\":\\\"拖车绳\\\"},{\\\"cateId\\\":\\\"10010\\\",\\\"cateNameCn\\\":\\\"诊断工具\\\"},{\\\"cateId\\\":\\\"10011\\\",\\\"cateNameCn\\\":\\\"注油枪\\\"}]},{\\\"cateId\\\":\\\"3547\\\",\\\"cateNameCn\\\":\\\"悬挂/转向系统\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"10012\\\",\\\"cateNameCn\\\":\\\"避震\\\"},{\\\"cateId\\\":\\\"10013\\\",\\\"cateNameCn\\\":\\\"动力转向泵及配件\\\"},{\\\"cateId\\\":\\\"10014\\\",\\\"cateNameCn\\\":\\\"方向盘（零部件）\\\"},{\\\"cateId\\\":\\\"10015\\\",\\\"cateNameCn\\\":\\\"防倾杆\\\"},{\\\"cateId\\\":\\\"10016\\\",\\\"cateNameCn\\\":\\\"钢板弹簧\\\"},{\\\"cateId\\\":\\\"10017\\\",\\\"cateNameCn\\\":\\\"横拉杆系统\\\"},{\\\"cateId\\\":\\\"10018\\\",\\\"cateNameCn\\\":\\\"脚轮/曲面套件\\\"},{\\\"cateId\\\":\\\"10019\\\",\\\"cateNameCn\\\":\\\"控制臂及配件\\\"},{\\\"cateId\\\":\\\"10020\\\",\\\"cateNameCn\\\":\\\"连杆/臂\\\"},{\\\"cateId\\\":\\\"10021\\\",\\\"cateNameCn\\\":\\\"螺旋弹簧\\\"},{\\\"cateId\\\":\\\"10022\\\",\\\"cateNameCn\\\":\\\"球头\\\"},{\\\"cateId\\\":\\\"10023\\\",\\\"cateNameCn\\\":\\\"升降套件及配件\\\"},{\\\"cateId\\\":\\\"10024\\\",\\\"cateNameCn\\\":\\\"悬挂\\\"},{\\\"cateId\\\":\\\"10025\\\",\\\"cateNameCn\\\":\\\"支撑杆\\\"},{\\\"cateId\\\":\\\"10026\\\",\\\"cateNameCn\\\":\\\"转向齿条/齿轮箱\\\"}]},{\\\"cateId\\\":\\\"3548\\\",\\\"cateNameCn\\\":\\\"仪表\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"10027\\\",\\\"cateNameCn\\\":\\\"电压表\\\"},{\\\"cateId\\\":\\\"10028\\\",\\\"cateNameCn\\\":\\\"发光仪\\\"},{\\\"cateId\\\":\\\"10029\\\",\\\"cateNameCn\\\":\\\"进气垫片1\\\"},{\\\"cateId\\\":\\\"10030\\\",\\\"cateNameCn\\\":\\\"冷光仪表盘\\\"},{\\\"cateId\\\":\\\"10031\\\",\\\"cateNameCn\\\":\\\"排温表\\\"},{\\\"cateId\\\":\\\"10032\\\",\\\"cateNameCn\\\":\\\"汽车尾气表\\\"},{\\\"cateId\\\":\\\"10033\\\",\\\"cateNameCn\\\":\\\"汽缸头/阀盖垫片1\\\"},{\\\"cateId\\\":\\\"10034\\\",\\\"cateNameCn\\\":\\\"全套垫片1\\\"},{\\\"cateId\\\":\\\"10035\\\",\\\"cateNameCn\\\":\\\"燃油表\\\"},{\\\"cateId\\\":\\\"10036\\\",\\\"cateNameCn\\\":\\\"时钟\\\"},{\\\"cateId\\\":\\\"10037\\\",\\\"cateNameCn\\\":\\\"水温计\\\"},{\\\"cateId\\\":\\\"10038\\\",\\\"cateNameCn\\\":\\\"速度计1\\\"},{\\\"cateId\\\":\\\"10039\\\",\\\"cateNameCn\\\":\\\"仪表板/仪表盘\\\"},{\\\"cateId\\\":\\\"10040\\\",\\\"cateNameCn\\\":\\\"仪表装饰\\\"},{\\\"cateId\\\":\\\"10041\\\",\\\"cateNameCn\\\":\\\"油底壳垫片\\\"},{\\\"cateId\\\":\\\"10042\\\",\\\"cateNameCn\\\":\\\"油压仪表\\\"},{\\\"cateId\\\":\\\"10043\\\",\\\"cateNameCn\\\":\\\"真空计\\\"},{\\\"cateId\\\":\\\"10044\\\",\\\"cateNameCn\\\":\\\"转速表\\\"}]},{\\\"cateId\\\":\\\"3550\\\",\\\"cateNameCn\\\":\\\"增压器/氮/增压\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"10047\\\",\\\"cateNameCn\\\":\\\"氮及配件\\\"},{\\\"cateId\\\":\\\"10048\\\",\\\"cateNameCn\\\":\\\"涡轮增压器及配件\\\"},{\\\"cateId\\\":\\\"10049\\\",\\\"cateNameCn\\\":\\\"增压器及配件\\\"}]},{\\\"cateId\\\":\\\"3551\\\",\\\"cateNameCn\\\":\\\"专业零配件\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"10050\\\",\\\"cateNameCn\\\":\\\"变流器\\\"},{\\\"cateId\\\":\\\"10051\\\",\\\"cateNameCn\\\":\\\"刹车液\\\"},{\\\"cateId\\\":\\\"10052\\\",\\\"cateNameCn\\\":\\\"车窗马达与零件\\\"},{\\\"cateId\\\":\\\"10053\\\",\\\"cateNameCn\\\":\\\"车喇叭\\\"},{\\\"cateId\\\":\\\"10054\\\",\\\"cateNameCn\\\":\\\"挡风玻璃\\\"},{\\\"cateId\\\":\\\"10055\\\",\\\"cateNameCn\\\":\\\"电机支架\\\"},{\\\"cateId\\\":\\\"10056\\\",\\\"cateNameCn\\\":\\\"电路保险\\\"},{\\\"cateId\\\":\\\"10057\\\",\\\"cateNameCn\\\":\\\"防护架\\\"},{\\\"cateId\\\":\\\"10058\\\",\\\"cateNameCn\\\":\\\"空调设备\\\"},{\\\"cateId\\\":\\\"10059\\\",\\\"cateNameCn\\\":\\\"轮毂1\\\"},{\\\"cateId\\\":\\\"10060\\\",\\\"cateNameCn\\\":\\\"轮胎/配件\\\"},{\\\"cateId\\\":\\\"10061\\\",\\\"cateNameCn\\\":\\\"螺钉帽/螺栓\\\"},{\\\"cateId\\\":\\\"10062\\\",\\\"cateNameCn\\\":\\\"密封件1\\\"},{\\\"cateId\\\":\\\"10063\\\",\\\"cateNameCn\\\":\\\"喷油嘴\\\"},{\\\"cateId\\\":\\\"10064\\\",\\\"cateNameCn\\\":\\\"前裙板1\\\"},{\\\"cateId\\\":\\\"10065\\\",\\\"cateNameCn\\\":\\\"燃油加液口总成\\\"},{\\\"cateId\\\":\\\"10066\\\",\\\"cateNameCn\\\":\\\"升压计\\\"},{\\\"cateId\\\":\\\"10067\\\",\\\"cateNameCn\\\":\\\"下散件\\\"},{\\\"cateId\\\":\\\"10068\\\",\\\"cateNameCn\\\":\\\"迎宾踏板\\\"},{\\\"cateId\\\":\\\"10069\\\",\\\"cateNameCn\\\":\\\"油底壳垫片1\\\"},{\\\"cateId\\\":\\\"10070\\\",\\\"cateNameCn\\\":\\\"油冷\\\"},{\\\"cateId\\\":\\\"10071\\\",\\\"cateNameCn\\\":\\\"油箱\\\"},{\\\"cateId\\\":\\\"10072\\\",\\\"cateNameCn\\\":\\\"中网\\\"},{\\\"cateId\\\":\\\"10073\\\",\\\"cateNameCn\\\":\\\"主油道\\\"},{\\\"cateId\\\":\\\"10074\\\",\\\"cateNameCn\\\":\\\"总成部件\\\"}]},{\\\"cateId\\\":\\\"11139\\\",\\\"cateNameCn\\\":\\\"汽摩待录入2\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"11140\\\",\\\"cateNameCn\\\":\\\"汽摩待录入3\\\"}]},{\\\"cateId\\\":\\\"11408\\\",\\\"cateNameCn\\\":\\\"交通安全\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9741\\\",\\\"cateNameCn\\\":\\\"除雪铲\\\"},{\\\"cateId\\\":\\\"9744\\\",\\\"cateNameCn\\\":\\\"反光安全服\\\"},{\\\"cateId\\\":\\\"9746\\\",\\\"cateNameCn\\\":\\\"防滑链\\\"},{\\\"cateId\\\":\\\"9750\\\",\\\"cateNameCn\\\":\\\"三角警示牌\\\"},{\\\"cateId\\\":\\\"11113\\\",\\\"cateNameCn\\\":\\\"酒精测试仪\\\"}]},{\\\"cateId\\\":\\\"12108\\\",\\\"cateNameCn\\\":\\\"汽车摩配\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"3533\\\",\\\"cateNameCn\\\":\\\"进气系统2\\\"},{\\\"cateId\\\":\\\"10217\\\",\\\"cateNameCn\\\":\\\"传感器\\\"}]},{\\\"cateId\\\":\\\"12169\\\",\\\"cateNameCn\\\":\\\"运输/储藏\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"10045\\\",\\\"cateNameCn\\\":\\\"手推车\\\"},{\\\"cateId\\\":\\\"10046\\\",\\\"cateNameCn\\\":\\\"拖车链接/附件\\\"}]},{\\\"cateId\\\":\\\"12170\\\",\\\"cateNameCn\\\":\\\"进气系统\\\",\\\"children\\\":[{\\\"cateId\\\":\\\"9751\\\",\\\"cateNameCn\\\":\\\"怠速控制阀\\\"},{\\\"cateId\\\":\\\"9752\\\",\\\"cateNameCn\\\":\\\"扼流圈\\\"},{\\\"cateId\\\":\\\"9753\\\",\\\"cateNameCn\\\":\\\"节气门体\\\"},{\\\"cateId\\\":\\\"9754\\\",\\\"cateNameCn\\\":\\\"进气歧管\\\"},{\\\"cateId\\\":\\\"9755\\\",\\\"cateNameCn\\\":\\\"进气系统1\\\"},{\\\"cateId\\\":\\\"9756\\\",\\\"cateNameCn\\\":\\\"空气滤清器总成\\\"},{\\\"cateId\\\":\\\"9757\\\",\\\"cateNameCn\\\":\\\"汽车电子节气门控制器\\\"}]}]}],\\\"checkedKeys\\\":[\\\"3354\\\",\\\"3356\\\",\\\"3480\\\",\\\"3481\\\",\\\"3482\\\",\\\"3483\\\",\\\"3485\\\",\\\"3486\\\",\\\"3487\\\",\\\"3488\\\",\\\"3489\\\",\\\"3490\\\",\\\"3491\\\",\\\"3492\\\",\\\"3493\\\",\\\"3494\\\",\\\"3495\\\",\\\"3497\\\",\\\"3516\\\",\\\"3517\\\",\\\"3518\\\",\\\"3519\\\",\\\"3520\\\",\\\"3521\\\",\\\"3522\\\",\\\"3523\\\",\\\"3524\\\",\\\"3525\\\",\\\"3526\\\",\\\"3527\\\",\\\"3528\\\",\\\"3529\\\",\\\"3530\\\",\\\"3531\\\",\\\"3533\\\",\\\"3534\\\",\\\"3535\\\",\\\"3536\\\",\\\"3537\\\",\\\"3538\\\",\\\"3539\\\",\\\"3540\\\",\\\"3541\\\",\\\"3542\\\",\\\"3543\\\",\\\"3544\\\",\\\"3545\\\",\\\"3546\\\",\\\"3547\\\",\\\"3548\\\",\\\"3550\\\",\\\"3551\\\",\\\"9038\\\",\\\"9039\\\",\\\"9040\\\",\\\"9041\\\",\\\"9042\\\",\\\"9043\\\",\\\"9044\\\",\\\"9045\\\",\\\"9046\\\",\\\"9047\\\",\\\"9048\\\",\\\"9049\\\",\\\"9050\\\",\\\"9051\\\",\\\"9052\\\",\\\"9053\\\",\\\"9054\\\",\\\"9055\\\",\\\"9056\\\",\\\"9057\\\",\\\"9058\\\",\\\"9059\\\",\\\"9060\\\",\\\"9061\\\",\\\"9062\\\",\\\"9063\\\",\\\"9064\\\",\\\"9065\\\",\\\"9066\\\",\\\"9067\\\",\\\"9068\\\",\\\"9069\\\",\\\"9070\\\",\\\"9071\\\",\\\"9072\\\",\\\"9073\\\",\\\"9074\\\",\\\"9075\\\",\\\"9076\\\",\\\"9077\\\",\\\"9079\\\",\\\"9080\\\",\\\"9081\\\",\\\"9082\\\",\\\"9083\\\",\\\"9084\\\",\\\"9085\\\",\\\"9086\\\",\\\"9087\\\",\\\"9088\\\",\\\"9089\\\",\\\"9090\\\",\\\"9091\\\",\\\"9093\\\",\\\"9094\\\",\\\"9095\\\",\\\"9096\\\",\\\"9097\\\",\\\"9098\\\",\\\"9099\\\",\\\"9100\\\",\\\"9101\\\",\\\"9102\\\",\\\"9103\\\",\\\"9104\\\",\\\"9105\\\",\\\"9106\\\",\\\"9107\\\",\\\"9108\\\",\\\"9109\\\",\\\"9110\\\",\\\"9111\\\",\\\"9112\\\",\\\"9113\\\",\\\"9114\\\",\\\"9115\\\",\\\"9116\\\",\\\"9117\\\",\\\"9118\\\",\\\"9119\\\",\\\"9120\\\",\\\"9121\\\",\\\"9122\\\",\\\"9124\\\",\\\"9125\\\",\\\"9126\\\",\\\"9127\\\",\\\"9128\\\",\\\"9129\\\",\\\"9130\\\",\\\"9131\\\",\\\"9132\\\",\\\"9133\\\",\\\"9134\\\",\\\"9135\\\",\\\"9136\\\",\\\"9137\\\",\\\"9138\\\",\\\"9139\\\",\\\"9140\\\",\\\"9141\\\",\\\"9142\\\",\\\"9143\\\",\\\"9144\\\",\\\"9145\\\",\\\"9146\\\",\\\"9147\\\",\\\"9148\\\",\\\"9149\\\",\\\"9150\\\",\\\"9152\\\",\\\"9153\\\",\\\"9154\\\",\\\"9155\\\",\\\"9156\\\",\\\"9157\\\",\\\"9158\\\",\\\"9159\\\",\\\"9160\\\",\\\"9161\\\",\\\"9162\\\",\\\"9163\\\",\\\"9164\\\",\\\"9165\\\",\\\"9166\\\",\\\"9167\\\",\\\"9169\\\",\\\"9170\\\",\\\"9171\\\",\\\"9172\\\",\\\"9173\\\",\\\"9174\\\",\\\"9175\\\",\\\"9176\\\",\\\"9177\\\",\\\"9178\\\",\\\"9179\\\",\\\"9180\\\",\\\"9181\\\",\\\"9182\\\",\\\"9184\\\",\\\"9185\\\",\\\"9186\\\",\\\"9187\\\",\\\"9188\\\",\\\"9189\\\",\\\"9190\\\",\\\"9192\\\",\\\"9193\\\",\\\"9194\\\",\\\"9195\\\",\\\"9196\\\",\\\"9197\\\",\\\"9198\\\",\\\"9199\\\",\\\"9200\\\",\\\"9201\\\",\\\"9202\\\",\\\"9203\\\",\\\"9204\\\",\\\"9205\\\",\\\"9206\\\",\\\"9207\\\",\\\"9208\\\",\\\"9210\\\",\\\"9211\\\",\\\"9212\\\",\\\"9214\\\",\\\"9215\\\",\\\"9216\\\",\\\"9217\\\",\\\"9218\\\",\\\"9219\\\",\\\"9220\\\",\\\"9221\\\",\\\"9222\\\",\\\"9223\\\",\\\"9224\\\",\\\"9225\\\",\\\"9226\\\",\\\"9227\\\",\\\"9228\\\",\\\"9229\\\",\\\"9230\\\",\\\"9231\\\",\\\"9232\\\",\\\"9233\\\",\\\"9234\\\",\\\"9235\\\",\\\"9236\\\",\\\"9237\\\",\\\"9238\\\",\\\"9239\\\",\\\"9240\\\",\\\"9241\\\",\\\"9242\\\",\\\"9243\\\",\\\"9244\\\",\\\"9245\\\",\\\"9246\\\",\\\"9247\\\",\\\"9248\\\",\\\"9249\\\",\\\"9250\\\",\\\"9251\\\",\\\"9252\\\",\\\"9253\\\",\\\"9254\\\",\\\"9255\\\",\\\"9256\\\",\\\"9257\\\",\\\"9258\\\",\\\"9259\\\",\\\"9260\\\",\\\"9261\\\",\\\"9262\\\",\\\"9263\\\",\\\"9264\\\",\\\"9265\\\",\\\"9266\\\",\\\"9267\\\",\\\"9268\\\",\\\"9269\\\",\\\"9270\\\",\\\"9271\\\",\\\"9272\\\",\\\"9273\\\",\\\"9274\\\",\\\"9275\\\",\\\"9276\\\",\\\"9277\\\",\\\"9278\\\",\\\"9279\\\",\\\"9280\\\",\\\"9281\\\",\\\"9282\\\",\\\"9283\\\",\\\"9284\\\",\\\"9285\\\",\\\"9286\\\",\\\"9287\\\",\\\"9288\\\",\\\"9289\\\",\\\"9290\\\",\\\"9291\\\",\\\"9292\\\",\\\"9293\\\",\\\"9294\\\",\\\"9295\\\",\\\"9296\\\",\\\"9297\\\",\\\"9298\\\",\\\"9299\\\",\\\"9300\\\",\\\"9302\\\",\\\"9303\\\",\\\"9304\\\",\\\"9305\\\",\\\"9306\\\",\\\"9307\\\",\\\"9308\\\",\\\"9309\\\",\\\"9310\\\",\\\"9311\\\",\\\"9312\\\",\\\"9313\\\",\\\"9314\\\",\\\"9315\\\",\\\"9316\\\",\\\"9317\\\",\\\"9318\\\",\\\"9319\\\",\\\"9320\\\",\\\"9321\\\",\\\"9322\\\",\\\"9323\\\",\\\"9324\\\",\\\"9325\\\",\\\"9326\\\",\\\"9327\\\",\\\"9328\\\",\\\"9329\\\",\\\"9330\\\",\\\"9331\\\",\\\"9332\\\",\\\"9333\\\",\\\"9334\\\",\\\"9335\\\",\\\"9336\\\",\\\"9337\\\",\\\"9338\\\",\\\"9339\\\",\\\"9340\\\",\\\"9341\\\",\\\"9342\\\",\\\"9343\\\",\\\"9344\\\",\\\"9345\\\",\\\"9346\\\",\\\"9347\\\",\\\"9349\\\",\\\"9350\\\",\\\"9351\\\",\\\"9352\\\",\\\"9353\\\",\\\"9354\\\",\\\"9355\\\",\\\"9356\\\",\\\"9357\\\",\\\"9358\\\",\\\"9359\\\",\\\"9360\\\",\\\"9361\\\",\\\"9362\\\",\\\"9363\\\",\\\"9364\\\",\\\"9365\\\",\\\"9366\\\",\\\"9367\\\",\\\"9368\\\",\\\"9369\\\",\\\"9370\\\",\\\"9371\\\",\\\"9372\\\",\\\"9373\\\",\\\"9374\\\",\\\"9375\\\",\\\"9376\\\",\\\"9377\\\",\\\"9378\\\",\\\"9379\\\",\\\"9380\\\",\\\"9381\\\",\\\"9382\\\",\\\"9383\\\",\\\"9384\\\",\\\"9385\\\",\\\"9386\\\",\\\"9387\\\",\\\"9393\\\",\\\"9394\\\",\\\"9577\\\",\\\"9578\\\",\\\"9579\\\",\\\"9580\\\",\\\"9581\\\",\\\"9582\\\",\\\"9583\\\",\\\"9584\\\",\\\"9585\\\",\\\"9586\\\",\\\"9587\\\",\\\"9588\\\",\\\"9589\\\",\\\"9590\\\",\\\"9591\\\",\\\"9592\\\",\\\"9593\\\",\\\"9594\\\",\\\"9595\\\",\\\"9596\\\",\\\"9597\\\",\\\"9598\\\",\\\"9599\\\",\\\"9600\\\",\\\"9601\\\",\\\"9602\\\",\\\"9603\\\",\\\"9604\\\",\\\"9605\\\",\\\"9606\\\",\\\"9607\\\",\\\"9608\\\",\\\"9609\\\",\\\"9610\\\",\\\"9611\\\",\\\"9612\\\",\\\"9613\\\",\\\"9614\\\",\\\"9615\\\",\\\"9616\\\",\\\"9617\\\",\\\"9618\\\",\\\"9619\\\",\\\"9620\\\",\\\"9621\\\",\\\"9622\\\",\\\"9623\\\",\\\"9624\\\",\\\"9625\\\",\\\"9626\\\",\\\"9627\\\",\\\"9628\\\",\\\"9629\\\",\\\"9630\\\",\\\"9631\\\",\\\"9632\\\",\\\"9633\\\",\\\"9634\\\",\\\"9635\\\",\\\"9636\\\",\\\"9637\\\",\\\"9638\\\",\\\"9639\\\",\\\"9640\\\",\\\"9641\\\",\\\"9642\\\",\\\"9643\\\",\\\"9644\\\",\\\"9645\\\",\\\"9646\\\",\\\"9647\\\",\\\"9648\\\",\\\"9649\\\",\\\"9650\\\",\\\"9651\\\",\\\"9652\\\",\\\"9653\\\",\\\"9654\\\",\\\"9655\\\",\\\"9656\\\",\\\"9657\\\",\\\"9658\\\",\\\"9659\\\",\\\"9660\\\",\\\"9661\\\",\\\"9662\\\",\\\"9663\\\",\\\"9664\\\",\\\"9665\\\",\\\"9666\\\",\\\"9667\\\",\\\"9668\\\",\\\"9669\\\",\\\"9670\\\",\\\"9671\\\",\\\"9672\\\",\\\"9673\\\",\\\"9674\\\",\\\"9675\\\",\\\"9676\\\",\\\"9677\\\",\\\"9678\\\",\\\"9679\\\",\\\"9680\\\",\\\"9681\\\",\\\"9682\\\",\\\"9683\\\",\\\"9684\\\",\\\"9685\\\",\\\"9686\\\",\\\"9687\\\",\\\"9688\\\",\\\"9689\\\",\\\"9690\\\",\\\"9691\\\",\\\"9692\\\",\\\"9693\\\",\\\"9694\\\",\\\"9695\\\",\\\"9696\\\",\\\"9697\\\",\\\"9698\\\",\\\"9699\\\",\\\"9700\\\",\\\"9701\\\",\\\"9702\\\",\\\"9703\\\",\\\"9704\\\",\\\"9705\\\",\\\"9706\\\",\\\"9707\\\",\\\"9708\\\",\\\"9709\\\",\\\"9710\\\",\\\"9711\\\",\\\"9712\\\",\\\"9713\\\",\\\"9714\\\",\\\"9715\\\",\\\"9716\\\",\\\"9717\\\",\\\"9718\\\",\\\"9719\\\",\\\"9720\\\",\\\"9721\\\",\\\"9722\\\",\\\"9723\\\",\\\"9724\\\",\\\"9725\\\",\\\"9726\\\",\\\"9727\\\",\\\"9728\\\",\\\"9729\\\",\\\"9730\\\",\\\"9731\\\",\\\"9732\\\",\\\"9733\\\",\\\"9734\\\",\\\"9735\\\",\\\"9736\\\",\\\"9737\\\",\\\"9738\\\",\\\"9739\\\",\\\"9740\\\",\\\"9741\\\",\\\"9744\\\",\\\"9746\\\",\\\"9750\\\",\\\"9751\\\",\\\"9752\\\",\\\"9753\\\",\\\"9754\\\",\\\"9755\\\",\\\"9756\\\",\\\"9757\\\",\\\"9758\\\",\\\"9759\\\",\\\"9760\\\",\\\"9761\\\",\\\"9762\\\",\\\"9763\\\",\\\"9764\\\",\\\"9765\\\",\\\"9766\\\",\\\"9767\\\",\\\"9768\\\",\\\"9769\\\",\\\"9770\\\",\\\"9771\\\",\\\"9772\\\",\\\"9773\\\",\\\"9774\\\",\\\"9775\\\",\\\"9776\\\",\\\"9777\\\",\\\"9778\\\",\\\"9779\\\",\\\"9780\\\",\\\"9781\\\",\\\"9782\\\",\\\"9783\\\",\\\"9784\\\",\\\"9785\\\",\\\"9786\\\",\\\"9787\\\",\\\"9788\\\",\\\"9789\\\",\\\"9790\\\",\\\"9791\\\",\\\"9792\\\",\\\"9793\\\",\\\"9794\\\",\\\"9795\\\",\\\"9796\\\",\\\"9797\\\",\\\"9798\\\",\\\"9799\\\",\\\"9800\\\",\\\"9801\\\",\\\"9802\\\",\\\"9803\\\",\\\"9804\\\",\\\"9805\\\",\\\"9806\\\",\\\"9807\\\",\\\"9808\\\",\\\"9809\\\",\\\"9810\\\",\\\"9811\\\",\\\"9812\\\",\\\"9813\\\",\\\"9814\\\",\\\"9815\\\",\\\"9816\\\",\\\"9817\\\",\\\"9818\\\",\\\"9819\\\",\\\"9820\\\",\\\"9821\\\",\\\"9822\\\",\\\"9823\\\",\\\"9824\\\",\\\"9825\\\",\\\"9826\\\",\\\"9827\\\",\\\"9828\\\",\\\"9829\\\",\\\"9830\\\",\\\"9831\\\",\\\"9832\\\",\\\"9833\\\",\\\"9834\\\",\\\"9835\\\",\\\"9836\\\",\\\"9837\\\",\\\"9838\\\",\\\"9839\\\",\\\"9840\\\",\\\"9841\\\",\\\"9842\\\",\\\"9843\\\",\\\"9844\\\",\\\"9845\\\",\\\"9846\\\",\\\"9847\\\",\\\"9848\\\",\\\"9849\\\",\\\"9850\\\",\\\"9851\\\",\\\"9852\\\",\\\"9853\\\",\\\"9854\\\",\\\"9855\\\",\\\"9856\\\",\\\"9857\\\",\\\"9858\\\",\\\"9859\\\",\\\"9860\\\",\\\"9861\\\",\\\"9862\\\",\\\"9863\\\",\\\"9864\\\",\\\"9865\\\",\\\"9866\\\",\\\"9867\\\",\\\"9868\\\",\\\"9869\\\",\\\"9870\\\",\\\"9871\\\",\\\"9872\\\",\\\"9873\\\",\\\"9874\\\",\\\"9875\\\",\\\"9876\\\",\\\"9878\\\",\\\"9879\\\",\\\"9880\\\",\\\"9881\\\",\\\"9882\\\",\\\"9883\\\",\\\"9884\\\",\\\"9885\\\",\\\"9887\\\",\\\"9888\\\",\\\"9889\\\",\\\"9890\\\",\\\"9891\\\",\\\"9892\\\",\\\"9893\\\",\\\"9894\\\",\\\"9895\\\",\\\"9896\\\",\\\"9897\\\",\\\"9898\\\",\\\"9899\\\",\\\"9900\\\",\\\"9901\\\",\\\"9902\\\",\\\"9903\\\",\\\"9904\\\",\\\"9905\\\",\\\"9906\\\",\\\"9907\\\",\\\"9908\\\",\\\"9909\\\",\\\"9910\\\",\\\"9911\\\",\\\"9912\\\",\\\"9913\\\",\\\"9914\\\",\\\"9915\\\",\\\"9916\\\",\\\"9917\\\",\\\"9918\\\",\\\"9919\\\",\\\"9920\\\",\\\"9921\\\",\\\"9922\\\",\\\"9923\\\",\\\"9924\\\",\\\"9925\\\",\\\"9926\\\",\\\"9927\\\",\\\"9928\\\",\\\"9929\\\",\\\"9930\\\",\\\"9931\\\",\\\"9932\\\",\\\"9933\\\",\\\"9934\\\",\\\"9935\\\",\\\"9936\\\",\\\"9937\\\",\\\"9938\\\",\\\"9939\\\",\\\"9940\\\",\\\"9941\\\",\\\"9942\\\",\\\"9943\\\",\\\"9944\\\",\\\"9945\\\",\\\"9946\\\",\\\"9947\\\",\\\"9948\\\",\\\"9949\\\",\\\"9950\\\",\\\"9951\\\",\\\"9952\\\",\\\"9953\\\",\\\"9954\\\",\\\"9955\\\",\\\"9956\\\",\\\"9957\\\",\\\"9958\\\",\\\"9959\\\",\\\"9960\\\",\\\"9961\\\",\\\"9962\\\",\\\"9963\\\",\\\"9964\\\",\\\"9965\\\",\\\"9966\\\",\\\"9967\\\",\\\"9968\\\",\\\"9969\\\",\\\"9970\\\",\\\"9971\\\",\\\"9972\\\",\\\"9973\\\",\\\"9974\\\",\\\"9975\\\",\\\"9976\\\",\\\"9977\\\",\\\"9978\\\",\\\"9979\\\",\\\"9980\\\",\\\"9981\\\",\\\"9982\\\",\\\"9983\\\",\\\"9984\\\",\\\"9985\\\",\\\"9986\\\",\\\"9987\\\",\\\"9988\\\",\\\"9989\\\",\\\"9990\\\",\\\"9991\\\",\\\"9992\\\",\\\"9993\\\",\\\"9994\\\",\\\"9995\\\",\\\"9996\\\",\\\"9997\\\",\\\"9998\\\",\\\"9999\\\",\\\"10000\\\",\\\"10001\\\",\\\"10002\\\",\\\"10003\\\",\\\"10004\\\",\\\"10005\\\",\\\"10006\\\",\\\"10007\\\",\\\"10008\\\",\\\"10009\\\",\\\"10010\\\",\\\"10011\\\",\\\"10012\\\",\\\"10013\\\",\\\"10014\\\",\\\"10015\\\",\\\"10016\\\",\\\"10017\\\",\\\"10018\\\",\\\"10019\\\",\\\"10020\\\",\\\"10021\\\",\\\"10022\\\",\\\"10023\\\",\\\"10024\\\",\\\"10025\\\",\\\"10026\\\",\\\"10027\\\",\\\"10028\\\",\\\"10029\\\",\\\"10030\\\",\\\"10031\\\",\\\"10032\\\",\\\"10033\\\",\\\"10034\\\",\\\"10035\\\",\\\"10036\\\",\\\"10037\\\",\\\"10038\\\",\\\"10039\\\",\\\"10040\\\",\\\"10041\\\",\\\"10042\\\",\\\"10043\\\",\\\"10044\\\",\\\"10045\\\",\\\"10046\\\",\\\"10047\\\",\\\"10048\\\",\\\"10049\\\",\\\"10050\\\",\\\"10051\\\",\\\"10052\\\",\\\"10053\\\",\\\"10054\\\",\\\"10055\\\",\\\"10056\\\",\\\"10057\\\",\\\"10058\\\",\\\"10059\\\",\\\"10060\\\",\\\"10061\\\",\\\"10062\\\",\\\"10063\\\",\\\"10064\\\",\\\"10065\\\",\\\"10066\\\",\\\"10067\\\",\\\"10068\\\",\\\"10069\\\",\\\"10070\\\",\\\"10071\\\",\\\"10072\\\",\\\"10073\\\",\\\"10074\\\",\\\"10217\\\",\\\"11086\\\",\\\"11097\\\",\\\"11113\\\",\\\"11116\\\",\\\"11121\\\",\\\"11139\\\",\\\"11140\\\",\\\"11146\\\",\\\"11155\\\",\\\"11156\\\",\\\"11157\\\",\\\"11158\\\",\\\"11159\\\",\\\"11160\\\",\\\"11233\\\",\\\"11235\\\",\\\"11236\\\",\\\"11247\\\",\\\"11288\\\",\\\"11408\\\",\\\"11447\\\",\\\"11448\\\",\\\"11449\\\",\\\"12094\\\",\\\"12095\\\",\\\"12096\\\",\\\"12097\\\",\\\"12108\\\",\\\"12149\\\",\\\"12169\\\",\\\"12170\\\"]}\",\"{\\\"value\\\":\\\"dataColumn\\\",\\\"name\\\":\\\"数据列\\\",\\\"data\\\":[{\\\"key\\\":\\\"chineseName\\\",\\\"name\\\":\\\"中文名称\\\",\\\"visiable\\\":\\\"可见\\\"},{\\\"key\\\":\\\"costPrice\\\",\\\"name\\\":\\\"成本价\\\",\\\"visiable\\\":\\\"可见\\\"},{\\\"key\\\":\\\"availableStock\\\",\\\"name\\\":\\\"可用库存\\\",\\\"visiable\\\":\\\"可见\\\"}],\\\"checkedKeys\\\":[\\\"availableStock\\\",\\\"chineseName\\\",\\\"costPrice\\\"]}\"],\"parentExpressionId\":1546563992272000800}]}},{\"sysKey\":\"002\",\"sysName\":\"物流\",\"row\":{\"isAnd\":1,\"subWhereClause\":[{\"entityCode\":\"E_Logistics\",\"entityName\":\"【平台】\",\"expressionId\":1546563992275000600,\"fieldCode\":\"F_Logistics\",\"operValuesArr\":[\"{\\\"platformId\\\":\\\"zoodmall\\\",\\\"platformName\\\":\\\"zoodmall\\\"}\",\"{\\\"platformId\\\":\\\"速卖通\\\",\\\"platformName\\\":\\\"速卖通\\\"}\"],\"parentExpressionId\":1546563992274000600}]}}]},\"ticket\":\"ab4818c21b08a5b5ab794596dee9ccc8\",\"operator\":\"songguanye\",\"funcVersion\":\"89be783577322d73a0fb03f2dc769a63\",\"moduleUrl\":\"/user/usermanagementlist/datapauthorization/\",\"personName\":\"songguanye\",\"deviceName\":\"Chrome浏览器\"}";
        System.out.println(json);
        resultVO = service.addOrUpdateDataRule(json);
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void getRolesByInfo() throws Exception {
        map.put("pageNumber", pageNumber);
        map.put("pageData", pageData);
        map.put("operator", operator);
        map.put("isActive",1);
        map.put("isAdmin",0);
        map.put("searchType",2);
        map.put("searchContent","linwanxian");
        String json = StringUtility.toJSONString(map);
        MotanSession.initialSession(json);
        resultVO = service.getRolesByInfo(json);
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void addOrUpdateRoleInfo() throws Exception {
//        map.put("operator", "panyun");
//        PermissionVO permissionVO = new PermissionVO();
//        List<PermissionVO> permissionVOS = new ArrayList<>();
//        permissionVO.setSysKey("000");
//        permissionVO.setSysContext("{\"menu\":[{\"key\":\"000-000001\",\"module\":[{\"function\":[],\"key\":\"000-000001-000001\",\"module\":[{\"function\":[],\"key\":\"000-000001-000001-000001\",\"name\":\"我的操作权限\",\"pageFullPathName\":\"\",\"show\":0,\"url\":\"/permissionlist/\"}],\"name\":\"数据走势\",\"pageFullPathName\":\"\",\"show\":1,\"url\":\"/\"}],\"name\":\"首页\",\"url\":\"/\"}],\"system\":{\"key\":\"000\",\"name\":\"首页\",\"url\":\"/\"}}");
//        permissionVOS.add(permissionVO);
//        RoleVO roleVO = new RoleVO();
//        roleVO.roleName = "test_bug_3";
//        roleVO.setRemark("test");
//        roleVO.setSelectedContext(permissionVOS);
//        // roleVO.roleId="1539221185095000011";
//        roleVO.isForever = true;
//        roleVO.setActive(Boolean.TRUE);
//        roleVO.setAuthorizable(Boolean.FALSE);
//        roleVO.setEffectiveTime(new Date());
//        roleVO.setExpireTime(new Date());
//        roleVO.setCreateBy("linwanxian");
//        roleVO.setExpireTime(new Date());
//        roleVO.lstUserName = new ArrayList<>();
//        roleVO.lstUserName.add("houyunfeng");
//        roleVO.lstUserName.add("hexiaopeng");
//        roleVO.lstOwner = new ArrayList<>();
//        roleVO.lstOwner.add("linwanxian");
//        roleVO.lstOwner.add("huanghongfei");
//
//        map.put("role", roleVO);
//        String json = StringUtility.toJSONString(map);
//        MotanSession.initialSession(json);
//        System.out.println(json);
        File file = new File("D:\\testAddOrUpdateRoleInfo.txt");
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder stringBuilder = new StringBuilder();
        String b;
        while ((b = bufferedReader.readLine()) != null){
            stringBuilder.append(b);
        }
        resultVO = service.addOrUpdateRoleInfo(stringBuilder.toString());
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void getRoleByRoleId() throws Exception {
        map.put("operator", operator);
        map.put("roleId", "1546939778294000003");
        String json = StringUtility.toJSONString(map);
        MotanSession.initialSession(json);
        System.out.println(json);
        resultVO = service.getRoleByRoleId(json);
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void deleteRoles() throws Exception {
//        map.put("operator", "chenjiangxin");
//        List<String> lstRoleId = new ArrayList<>();
//        lstRoleId.add("1539597354603000085");
//        map.put("lstRoleId", lstRoleId);
//        String json = StringUtility.toJSONString(map);
//        MotanSession.initialSession(json);
//        System.out.println(json);
        String json = "{\"lstRoleId\":[\"1547177430854000004\"],\"ticket\":\"664281f3fe93e77a12316f72294eb9d7\",\"operator\":\"songguanye\",\"funcVersion\":\"3fe890a9611eab0f81226bc7215710b4\",\"moduleUrl\":\"/user/rolemanagement/\",\"personName\":\"songguanye\",\"deviceName\":\"Chrome浏览器\"}";
        resultVO = service.deleteRoles(json);
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void assignAllPermit2Role() throws Exception {
        map.put("roleId","1529635932385000001");
        String json = StringUtility.toJSONString(map);
        MotanSession.initialSession(json);
        System.out.println(json);
        resultVO = service.assignAllPermit2Role(json);
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void startMonitorMemory() throws Exception {
    }

    @Test
    public void endMonitorMemory() throws Exception {
    }

    @Test
    public void handleExpiredRole() throws Exception {
    }

    @Test
    public void updateUserPermitCache() throws Exception {
    }

    @Test
    public void operIsSuperAdmin() throws Exception {
    }

    @Test
    public void getPlatformShopSite() throws Exception {
    }

    @Test
    public void syncPlatform() throws Exception {
        map.put("operator", operator);
        String json = StringUtility.toJSONString(map);
        MotanSession.initialSession(json);
        resultVO = service.syncPlatform(json);
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void syncShopSite() throws Exception {
        map.put("operator", operator);
        String json = StringUtility.toJSONString(map);
        MotanSession.initialSession(json);
        resultVO = service.syncShopSite(json);
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void fuzzSearchPersonByName() throws Exception {
        map.put("name","pan");
        String json = StringUtility.toJSONString(map);
        MotanSession.initialSession(json);
        resultVO = service.fuzzSearchPersonByName(json);
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void getDataRuleGtDt() throws Exception {
    }

    @Test
    public void updateApiPrefixCache() throws Exception {
        map.put("operator", "linwanxian");
        String json = StringUtility.toJSONString(map);
        MotanSession.initialSession(json);

        System.out.println(json);
        System.out.println("START====================" + StringUtility.dt2Str(new Date(), "yyyy-MM-dd HH:mm:sss"));
        ResultVO resultVO = service.updateApiPrefixCache(json);
        System.out.println(StringUtility.toJSONString(resultVO));
        System.out.println("END====================" + StringUtility.dt2Str(new Date(), "yyyy-MM-dd HH:mm:sss"));
    }

    @Test
    public void getAmazonShop() throws Exception {
        map.put("operator", operator);
        map.put("platformId", "亚马逊");
        String json = StringUtility.toJSONString(map);
        MotanSession.initialSession(json);
        //resultVO = service.getAmazonShop(json);
        System.out.println();
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void appointPlatformShopSite() throws Exception {
        map.put("operator", operator);
        map.put("platformId", "ebay");
        String json = StringUtility.toJSONString(map);
        MotanSession.initialSession(json);
        resultVO = service.appointPlatformShopSite(json);
        System.out.println();
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void test_getPlatformShopByEntityCode() {
        Long startTime = StringUtility.getDateTimeNow().getTime();
        map.put("operator", operator);
        map.put("entityCode", "E_CsOrg");
        //map.put("entityCode", "E_CustomerService");

        String json = StringUtility.toJSONString(map);
        MotanSession.initialSession(json);
        resultVO = service.getPlatformShopByEntityCode(json);
        System.out.println("返回结果为");
        System.out.println(StringUtility.toJSONString(resultVO));
        Long endTime = StringUtility.getDateTimeNow().getTime();
        System.out.println(String.format("花费的时间为:[%d]", endTime - startTime));
       outToFile(StringUtility.toJSONString(resultVO), "platform.json");
    }

    @Test
    public void test() {
        String jsonStr = "{\"userName\":\"songguanye\",\n" +
                "\n" +
                "\"mobile\":\"18376740674\", \n" +
                "\n" +
                "\"verificationCode\": 7415,\n" +
                "\n" + "\"newPwd\": \"670317483sgy???\" \n" +
                "\t\n" +
                "}";
        service.resetPwdSubmit(jsonStr);
    }
    @Test
    public  void test2(){
        map.put("userName","songguanye");
        map.put("mobile","18376740674");
        map.put("get_code","true");
       String json = StringUtility.toJSONString(map);
        service.resetPwdGetVerificationCode(json);

    }
    /**
     *  写出到文件
     * @param
     * @return
     * @Author lwx
     * @Date 2018/10/16 15:46
     */
    public static void outToFile(String str, String filePath) {
        File file = new File("F:\\Gitrepository\\urcenter\\source\\urc-service-provider\\src\\test\\resources\\" + filePath);
        BufferedWriter writer = null;
        //文件不存在
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "utf-8"));
            writer.write(str);
            writer.flush();
            writer.close();
        } catch (Exception e) {

        }
    }




    @Test
    public void test_kafka(){
        List<DataRuleSysVO> dataRuleSysVOS =new ArrayList<>();
        DataRuleSysVO sysVO =new DataRuleSysVO();
        ExpressionVO expressionVO =new ExpressionVO();
       /* ProducerRecord<String, String> arg0 = new ProducerRecord<String, String>("URC_USER_DATARULE_009", value);
        Callback arg1 = new Callback() {
            @Override
            public void onCompletion(RecordMetadata arg0, Exception arg1) {

            }
        };
        KafkaProducerSingleton.getInstance(null).send(arg0, arg1);*/
    }
    @Test
    public void test_meageJson(){
       String  test ="   abc  def    ";
       String str =null;
        //System.out.println(str.trim());
       // System.out.println(StringUtils.trim(str));
        System.out.println(StringUtility.trimPattern_Private(test,"\\s"));
    }

    @Test
    public void test_deleteNode(){
        Set<String> delKeys =new HashSet<>();
        delKeys.add("015-000001");
        dataMap.put("sysKey","015");
        dataMap.put("delKeys",delKeys);
        map.put("data",dataMap);
        map.put("operator",operator);
        String json =StringUtility.toJSONString(map);
        MotanSession.initialSession(json);
        System.out.println(json);
        resultVO=service.deleteSysPermitNode(json);
        System.out.println(StringUtility.toJSONString(resultVO));
    }

    @Test
    public void test_updateNode(){
        dataMap.put("sysKey","015");
       List<NodeVO> updateNode =new ArrayList<>();
        NodeVO nodeVO =new NodeVO();
        nodeVO.key="015-000002-000003-002";
        nodeVO.name="新增/编辑TestTest";
        nodeVO.url="";
        updateNode.add(nodeVO);
        dataMap.put("updateNode",updateNode);
        map.put("data",dataMap);
        map.put("operator",operator);
        String json =StringUtility.toJSONString(map);
        MotanSession.initialSession(json);
        System.out.println(json);
        resultVO=service.updateSysPermitNode(json);
        System.out.println(StringUtility.toJSONString(resultVO));
    }
    @Test
    public void testGetPlatformShopByEntityCode(){
        String json = "{\n" +
                "\"operator\":\"songguanye\",\n" +
                "\"entityCode\":\"E_PlatformShopSite\"\n" +
                "}";
        MotanSession.initialSession(json);
        service.getPlatformShopByEntityCode(json);
    }
    @Test
    public void test_searchUserPerson() {
        String string = "{\n" +
                "\t\"data\":{\n" +
                "\t\t\"searchContext\":\"\",\n" +
                "\t\t\"pageData\":10,\n" +
                "\t\t\"pageNumber\":0\n" +
                "\t},\n" +
                "    \"ticket\":\"e883f46659e7a4d4e6e804778c9ecedf\",\n" +
                "    \"operator\":\"tangjianbo\",\n" +
                "    \"funcVersion\":\"17cf4017ca9212b53d96d172e94a2177\",\n" +
                "    \"moduleUrl\":\"/order/\",\n" +
                "    \"personName\":\"tangjianbo\",\n" +
                "    \"deviceName\":\"Chrome浏览器\"\n" +
                "}";
        System.out.println(StringUtility.toJSONString(service.searchUserPerson(string)));
    }

    }
