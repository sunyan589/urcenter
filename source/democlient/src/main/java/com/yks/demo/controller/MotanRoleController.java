/*
 * 文件名：MotanRoleController.java
 * 版权：Copyright by www.youkeshu.com
 * 描述：
 * 创建人：OuJie
 * 创建时间：2018年06月13日
 * 修改理由：
 * 修改内容：
 */
package com.yks.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.motan.service.api.IUrcService;
import com.yks.urc.vo.ResultVO;
import com.yks.urc.vo.RoleVO;
import com.yks.urc.vo.UserVO;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author OuJie
 * @version 1.2
 * @date 2018年06月13日
 * @see MotanRoleController
 * @since JDK1.8
 */
@RestController
@RequestMapping("role")
public class MotanRoleController {

//    @MotanReferer
    private IUrcService urcService;

    @RequestMapping("checkDupName/{newRoleName}")
    @ResponseBody
    public ResultVO checkDuplicateRoleName(@PathVariable("newRoleName") String roleName, @RequestParam(value = "roleId", required = false) String roleId){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("operator", mockOperator().userName);
        jsonObject.put("newRoleName",roleName);
        jsonObject.put("roleId",roleId);
        return urcService.checkDuplicateRoleName(jsonObject.toJSONString());
    }
    private UserVO mockOperator(){
        UserVO curUser = new UserVO();
        curUser.userName = "py";
        curUser.userName = "py_" + UUID.randomUUID().toString();
        return curUser;
    }

    private ResultVO parseResultVO(String result){
        return StringUtility.parseObject(result, ResultVO.class);
    }
}
