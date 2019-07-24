package com.yks.urc.mapper;

import com.yks.urc.entity.ExpressionDO;
import com.yks.urc.entity.UserDO;

import java.util.List;

import org.apache.ibatis.annotations.Param;


public interface IExpressionMapper {

    List<ExpressionDO> listExpressionDOs(Long[] sqlIds);

    int deleteByPrimaryKey(Long id);

    int insert(ExpressionDO record);

    int insertBatch(List<ExpressionDO> expressionDOS);

    List<ExpressionDO> listExpressionDOsBySysKey(@Param("dataRuleSysId") long dataRuleSysId );


}