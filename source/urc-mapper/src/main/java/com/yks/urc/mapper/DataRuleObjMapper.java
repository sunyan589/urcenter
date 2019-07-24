package com.yks.urc.mapper;

import com.yks.urc.entity.DataRuleObj;

public interface DataRuleObjMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DataRuleObj record);

    int insertSelective(DataRuleObj record);

    DataRuleObj selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DataRuleObj record);

    int updateByPrimaryKey(DataRuleObj record);
}