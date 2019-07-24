package com.yks.urc.mapper;

import com.yks.urc.entity.UserPermitStatDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IUserPermitStatMapper {


    List<UserPermitStatDO> listUserPermitStatsByPage(Map<String, Object> queryMap);

    Long getCounts(@Param("userName") String userName);


}