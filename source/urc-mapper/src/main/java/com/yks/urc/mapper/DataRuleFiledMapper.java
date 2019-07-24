package com.yks.urc.mapper;

import com.yks.urc.entity.DataRuleFiled;

public interface DataRuleFiledMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DataRuleFiled record);

    int insertSelective(DataRuleFiled record);

    DataRuleFiled selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DataRuleFiled record);

    int updateByPrimaryKey(DataRuleFiled record);
}