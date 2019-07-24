package com.yks.urc.mapper;

import com.yks.urc.entity.SystemParameter;

public interface SystemParameterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemParameter record);

    int insertSelective(SystemParameter record);

    SystemParameter selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SystemParameter record);

    int updateByPrimaryKey(SystemParameter record);

	SystemParameter querySystemValuebyParameterName(String parameterName);
}