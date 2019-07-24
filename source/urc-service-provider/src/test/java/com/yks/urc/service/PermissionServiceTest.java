package com.yks.urc.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yks.urc.service.api.IDataRuleService;
import com.yks.urc.service.api.IPermissionService;
import com.yks.urc.vo.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class PermissionServiceTest extends BaseServiceTest {

    @Autowired
    private IPermissionService permissionService;


    @Test
    public void getUserPermissionStatList() {
        JSONObject jsonObject = new JSONObject();
        Integer pageNumber = 1;
        Integer pageData = 10;
        jsonObject.put("operator","dcadmin");
        jsonObject.put("pageNumber",pageNumber);
        jsonObject.put("pageData",pageData);
        ResultVO resultVO = permissionService.getUserPermissionList(jsonObject.toString());
        System.out.println(resultVO);
    }


    @Test
    public void getUserAuthorizablePermission(){
        ResultVO resultVO = permissionService.getUserAuthorizablePermission("huanghongfei");
        System.out.println(resultVO);

    }


}
