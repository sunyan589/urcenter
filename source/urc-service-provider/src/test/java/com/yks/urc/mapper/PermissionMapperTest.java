/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/6/14
 * @since 1.0.0
 */
package com.yks.urc.mapper;

import com.yks.urc.entity.PermissionDO;
import org.apache.ibatis.annotations.MapKey;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class PermissionMapperTest extends BaseMapperTest {

    @Autowired
    PermissionMapper permissionMapper;


    @Test
    public void getPermissionDoByRoleId(){
        Map<String,PermissionDO> map= permissionMapper.getSysContextByRoleId(1529743116993000004L);
        System.out.println(map);
    }

    @Test
    public void  getAllKey(){
        permissionMapper.getAllSysKey();
    }

    @Test
    public void perMissionMap(){
        Map<String,PermissionDO> permissionDOMap= permissionMapper.perMissionMap();
      for (String key : permissionDOMap.keySet()){
          PermissionDO permissionDO =permissionDOMap.get(key);
          System.out.println(permissionDO.getSysName());
      }
    }

    @Test
    public void test_getSysNameBykey(){
       String sysName= permissionMapper.getSysNameByKey("005");
        System.out.println(sysName);
    }

}
