package com.yks.urc.mapper;

import com.yks.urc.entity.UrcSqlDO;

import java.util.List;

public interface IUrcSqlMapper {

    List<UrcSqlDO> listUrcSqlDOs(Long[] dataRuleSysId);

    int deleteByPrimaryKey(Long id);

    int insert(UrcSqlDO record);

}