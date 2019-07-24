/*
 * 文件名：TestServiceImpl.java
 * 版权：Copyright by www.youkeshu.com
 * 描述：
 * 创建人：OuJie
 * 创建时间：2018年06月23日
 * 修改理由：
 * 修改内容：
 */
package com.yks.demo.validator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yks.common.validator.constant.Message;
import com.yks.common.validator.core.ValidatorFacade;
import com.yks.urc.vo.UserVO;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author OuJie
 * @version 1.2
 * @date 2018年06月23日
 * @see TestServiceImpl
 * @since JDK1.8
 * @description 校验部分，可提取到aop中进行校验。统一处理，减少代码耦合度，
 * TODO 若考虑aop的方式，则需要解决获取方法入参的名字问题，实现根据入参名称转换对应map对象key。
 */
public class TestServiceImpl {
    //占内存，建议一个应用共享一个校验对象
    private final static ValidatorFacade VALIDATOR = new ValidatorFacade("/validator/validator-config.xml");

    public void addUser(UserVO userVO){
        Map<String, Object> param = Maps.newHashMap();
        param.put("name", userVO.userName);
        param.put("sex",userVO.gender);
        param.put("jobNumber",userVO.jobNumber);
        String message = VALIDATOR.validate("addUser", param);
        if (!Message.SUCCESS.equals(message)){
            System.out.println(message);
            return;
        }
        System.out.println("新增用户成功>>>>>>"+param);
    }

    public void addUser(String name, String sex, String jobNumber){
        Map<String, Object> param = Maps.newHashMap();
        param.put("name", name);
        param.put("sex",sex);
        param.put("jobNumber",jobNumber);
        String message = VALIDATOR.validate("addUser", param);
        if (!Message.SUCCESS.equals(message)){
            System.out.println(message);
            return;
        }
        System.out.println("新增用户成功>>>>>>"+param);
    }

    public void addUsers(List<UserVO> userVOs){
        List<Map<String, Object>> params = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(userVOs)){
            userVOs.stream().forEach(userVO -> {
                Map<String, Object> param = Maps.newHashMap();
                param.put("name", userVO.userName);
                param.put("sex",userVO.gender);
                param.put("jobNumber",userVO.jobNumber);
                params.add(param);
            });

            String message = VALIDATOR.validate("addUsers", params);
            if (!Message.SUCCESS.equals(message)){
                System.out.println(message);
                return;
            }
            System.out.println("批量新增用户成功>>>>>>"+params);
        }
    }

    public static void main(String[] args) {
        TestServiceImpl service = new TestServiceImpl();
        //测试1
        service.addUser("oujie","male","aaa");

        //测试2
        UserVO userVO = new UserVO();
        userVO.userName = "oujie";
        userVO.gender = "22";
        userVO.jobNumber = "11111";
        service.addUser(userVO);

        //测试3
        List<UserVO> userVOs = Lists.newArrayList();
        UserVO userVO1 = new UserVO();
        userVO1.userName = "oujie1";
        userVO1.jobNumber = "11111";
        userVOs.add(userVO1);
        UserVO userVO2 = new UserVO();
        userVO2.userName = "oujie2";
        userVO2.jobNumber = "222222";
        userVOs.add(userVO2);
        service.addUsers(userVOs);
    }

}
