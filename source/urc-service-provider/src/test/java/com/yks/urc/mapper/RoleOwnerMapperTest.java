/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author lwx
 * @create 2018/7/20
 * @since 1.0.0
 */
package com.yks.urc.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class RoleOwnerMapperTest extends BaseMapperTest {
    @Autowired
    private RoleOwnerMapper ownerMapper;

    @Test
    public void test_delete(){
        List<Long> roleIds =new ArrayList<>();
        roleIds.add(Long.valueOf("1529635932385000002"));
        roleIds.stream().forEach(s->ownerMapper.deleteOwnerByRoleId(s));

    }
}
