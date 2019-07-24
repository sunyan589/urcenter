/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/6/14
 * @since 1.0.0
 */
package com.yks.urc.mapper;

import com.yks.urc.entity.AuthWay;
import com.yks.urc.vo.AuthWayVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AuthwayMapperTest extends BaseMapperTest {

    @Autowired
    private AuthWayMapper authWayMapper;

    @Test
    public void testGetentity(){

        List<AuthWayVO> authWayVOS =authWayMapper.getAuthWayVoBySysKey("001");
        System.out.println(authWayVOS.get(0).entityName);
    }
}
