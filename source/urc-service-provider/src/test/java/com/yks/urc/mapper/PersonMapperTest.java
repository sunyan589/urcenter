/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/7/9
 * @since 1.0.0
 */
package com.yks.urc.mapper;

import com.yks.urc.vo.UserInfoVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PersonMapperTest extends BaseMapperTest {

    @Autowired
    private PersonMapper personMapper;

    @Test
    public void fuzzUser(){
       /* String name ="潘";
        List<UserInfoVO> userInfoVOList =personMapper.getUserByDingOrgId(name);
        for (UserInfoVO userInfoVO :userInfoVOList){
            System.out.println(userInfoVO.personName);
        }*/
    }
}
