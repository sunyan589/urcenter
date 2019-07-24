package com.yks.urc.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yks.urc.entity.RoleDO;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.service.api.IDataRuleService;
import com.yks.urc.service.api.IRoleService;
import com.yks.urc.vo.*;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataRuleServiceTest extends BaseServiceTest {

    @Autowired
    private IDataRuleService dataRuleService;


    @Test
    public void getDataRuleTemplByTemplId() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("operator","houyunfeng");
        jsonObject.put("templId",1530675623060000018L);
        jsonObject.put("valFlag",1);
        ResultVO<DataRuleTemplVO> resultVO = dataRuleService.getDataRuleTemplByTemplId(jsonObject.toString());
        System.out.println(resultVO);
    }

    @Test
    public void assignDataRuleTempl2User(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("operator","lvchangrong");
        jsonObject.put("templId",1529404283335L);
        List<String>  lstUserName = new ArrayList<>();
        lstUserName.add("admin2122");
        lstUserName.add("admin2222");
       /*   lstUserName.add("admin23");
        lstUserName.add("admin24");*/
        jsonObject.put("lstUserName",lstUserName);
        ResultVO resultVO = dataRuleService.assignDataRuleTempl2User(jsonObject.toString());
        System.out.println(resultVO);
    }


    @Test
    public void getDataRuleTempl(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("operator","admin");
        jsonObject.put("pageNumber",1);
        jsonObject.put("pageData",4);
        DataRuleTemplVO dataRuleTemplVO = new DataRuleTemplVO();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("数据权限模板1").append(System.getProperty("line.separator"))
                .append("数据权限模板1").append(System.getProperty("line.separator"))
                .append("数据权限模板2").append(System.getProperty("line.separator"))
                .append("数据权限模板3").append(System.getProperty("line.separator"))
                .append("数据权限模板4").append(System.getProperty("line.separator"))
                .append("数据权限模板5").append(System.getProperty("line.separator"))
                .append("数据权限模板6").append(System.getProperty("line.separator"))
                .append("数据权限模板7").append(System.getProperty("line.separator"))
                .append("数据权限模板8").append(System.getProperty("line.separator"))
                .append("数据权限模板9").append(System.getProperty("line.separator"))
                .append("数据权限模板10");
        dataRuleTemplVO.templName=stringBuilder.toString();
        jsonObject.put("templ",dataRuleTemplVO);
        ResultVO<PageResultVO> resultVO = dataRuleService.getDataRuleTempl(jsonObject.toString());
        System.out.println(resultVO);
    }


    @Test
    public void addOrUpdateDataRuleTempl(){
        /*列权限1*/
        List<DataRuleColVO> dataRuleColVOS = new ArrayList<>();
        DataRuleColVO calJsonObj1 = new DataRuleColVO();
        calJsonObj1.setEntityCode("entityProduct");
        JSONArray colArray1 = new JSONArray();
           JSONObject colJson1 = new JSONObject();
           colJson1.put("fieldCode","name");
           colJson1.put("showType",0);
        colArray1.add(colJson1);
            JSONObject colJson2 = new JSONObject();
            colJson2.put("fieldCode","price");
            colJson2.put("showType",0);
        colArray1.add(colJson2);
        calJsonObj1.setColJson(colArray1.toString());
        dataRuleColVOS.add(calJsonObj1);

        /*列权限2*/
        DataRuleColVO calJsonObj2 = new DataRuleColVO();
        calJsonObj2.setEntityCode("entitySupplier");
        JSONArray colArray2 = new JSONArray();
        JSONObject colJson21 = new JSONObject();
            colJson21.put("fieldCode","addr");
            colJson21.put("showType",0);
        colArray2.add(colJson21);
        JSONObject colJson22 = new JSONObject();
            colJson22.put("fieldCode","city");
            colJson22.put("showType",0);
        colArray2.add(colJson22);
        calJsonObj2.setColJson(colArray2.toJSONString());
        dataRuleColVOS.add(calJsonObj2);

        /*行权限*/
        ExpressionVO expressionVO = new ExpressionVO();
        expressionVO.setIsAnd(1);
        List<ExpressionVO> subWhereClause = new ArrayList<>();
        ExpressionVO  subWhereClause1= new ExpressionVO();
        subWhereClause1.setFieldCode("fieldPlatform");
        subWhereClause1.setEntityCode("platform");
        subWhereClause1.setOper("in");
        subWhereClause1.setOperValues("[\"eBay\",\"WISH\"]");
        subWhereClause.add(subWhereClause1);

        ExpressionVO  subWhereClause2= new ExpressionVO();
        subWhereClause2.setFieldCode("fieldSite");
        subWhereClause2.setOper("in");
        subWhereClause2.setOperValues("[\"美国\",\"英国\"]");
        subWhereClause.add(subWhereClause2);

        ExpressionVO  subWhereClause3= new ExpressionVO();
        subWhereClause3.setFieldCode("fieldProductSku");
        subWhereClause3.setOper("in");
        subWhereClause3.setOperValues("[\"SKU001\"]");
        subWhereClause.add(subWhereClause3);
        expressionVO.setSubWhereClause(subWhereClause);

        DataRuleSysVO dataRuleSysVO = new DataRuleSysVO();
        dataRuleSysVO.setSysKey("001");
        dataRuleSysVO.setRow(expressionVO);
        dataRuleSysVO.setCol(dataRuleColVOS);
        List<DataRuleSysVO> dataRuleSysVOS1 = new ArrayList<>();
        dataRuleSysVOS1.add(dataRuleSysVO);


        DataRuleTemplVO  dataRuleTemplVO = new DataRuleTemplVO();
        dataRuleTemplVO.setTemplName("采购一部3C产品组方案22");
        dataRuleTemplVO.setRemark("给xxxx的方案");
        dataRuleTemplVO.setLstDataRuleSys(dataRuleSysVOS1);
        //dataRuleTemplVO.setTemplId("3");

        JSONObject json = new JSONObject();

        json.put("operator","wujianghui");
        json.put("templ",dataRuleTemplVO);

        ResultVO resultVO = dataRuleService.addOrUpdateDataRuleTempl(json.toString());
        System.out.println(resultVO);
    }





    @Test
    public void  addOrUpdateDataRule(){
        /*列权限1*/
        List<DataRuleColVO> dataRuleColVOS = new ArrayList<>();
        DataRuleColVO calJsonObj1 = new DataRuleColVO();
        calJsonObj1.setEntityCode("entityProduct");
        JSONArray colArray1 = new JSONArray();
        JSONObject colJson1 = new JSONObject();
        colJson1.put("fieldCode","name");
        colJson1.put("showType",0);
        colArray1.add(colJson1);
        JSONObject colJson2 = new JSONObject();
        colJson2.put("fieldCode","price");
        colJson2.put("showType",0);
        colArray1.add(colJson2);
        calJsonObj1.setColJson(colArray1.toString());
        dataRuleColVOS.add(calJsonObj1);

        /*列权限2*/
        DataRuleColVO calJsonObj2 = new DataRuleColVO();
        calJsonObj2.setEntityCode("entitySupplier");
        JSONArray colArray2 = new JSONArray();
        JSONObject colJson21 = new JSONObject();
        colJson21.put("fieldCode","addr");
        colJson21.put("showType",0);
        colArray2.add(colJson21);
        JSONObject colJson22 = new JSONObject();
        colJson22.put("fieldCode","city");
        colJson22.put("showType",0);
        colArray2.add(colJson22);
        calJsonObj2.setColJson(colArray2.toJSONString());
        dataRuleColVOS.add(calJsonObj2);

        /*行权限*/
        ExpressionVO expressionVO = new ExpressionVO();
        expressionVO.setIsAnd(1);
        List<ExpressionVO> subWhereClause = new ArrayList<>();
        ExpressionVO  subWhereClause1= new ExpressionVO();
        subWhereClause1.setFieldCode("fieldPlatform");
        subWhereClause1.setEntityCode("platform");
        subWhereClause1.setOper("in");
        subWhereClause1.setOperValues("[\"eBay\",\"WISH\"]");
        subWhereClause.add(subWhereClause1);

        ExpressionVO  subWhereClause2= new ExpressionVO();
        subWhereClause2.setFieldCode("fieldSite");
        subWhereClause2.setOper("in");
        subWhereClause2.setOperValues("[\"美国\",\"英国\"]");
        subWhereClause.add(subWhereClause2);

        ExpressionVO  subWhereClause3= new ExpressionVO();
        subWhereClause3.setFieldCode("fieldProductSku");
        subWhereClause3.setOper("in");
        subWhereClause3.setOperValues("[\"SKU001\"]");
        subWhereClause.add(subWhereClause3);
        expressionVO.setSubWhereClause(subWhereClause);

        DataRuleSysVO dataRuleSysVO = new DataRuleSysVO();
        dataRuleSysVO.setSysKey("002");
        dataRuleSysVO.setRow(expressionVO);
        dataRuleSysVO.setCol(dataRuleColVOS);
        List<DataRuleSysVO> dataRuleSysVOS1 = new ArrayList<>();
        dataRuleSysVOS1.add(dataRuleSysVO);


        List<DataRuleVO> lstDataRule = new ArrayList<>();


        DataRuleVO dataRuleVO = new DataRuleVO();
        dataRuleVO.setUserName("songguanye");
        dataRuleVO.setLstDataRuleSys(dataRuleSysVOS1);
        lstDataRule.add(dataRuleVO);


        DataRuleVO dataRuleVO1 = new DataRuleVO();
        dataRuleVO1.setUserName("tangjianbo");
        dataRuleVO1.setLstDataRuleSys(dataRuleSysVOS1);
        lstDataRule.add(dataRuleVO1);


        JSONObject json = new JSONObject();

        json.put("operator","panyun");
        json.put("lstDataRule",lstDataRule);
        ResultVO resultVO = dataRuleService.addOrUpdateDataRule(json.toString());
    }

    /*@Test
    public void assignDataRuleTempl2User(){
        JSONObject jsonObject = new JSONObject();
        dataRuleService.assignDataRuleTempl2User(jsonObject.toString());
    }*/

    @Test
    public void getDataRuleGtDt(){
        System.out.println(StringUtility.toJSONString(dataRuleService.getDataRuleGtDt("001",null,3)));
    }


    @Test
    public void deleteDataRuleTempl(){
        List<Long> ids = new ArrayList<>();
        ids.add(1529375579688L);
        ids.add(1529375579689L);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("operator","admin");
        jsonObject.put("lstTemplId",ids);

        ResultVO resultVO = dataRuleService.deleteDataRuleTempl(jsonObject.toString());
        System.out.println(resultVO);
    }
    @Test
    public void checkDuplicateTemplName(){
        System.out.println(StringUtility.toJSONString(
                dataRuleService.checkDuplicateTemplName(
                        "ddd","数据权限模板6","ddd")));
        System.out.println(StringUtility.toJSONString(
                dataRuleService.checkDuplicateTemplName(
                        "sss","数据权限模11板6","sss")
        ));
    }
}
