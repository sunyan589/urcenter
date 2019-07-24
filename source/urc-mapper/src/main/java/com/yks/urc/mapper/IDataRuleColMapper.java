package com.yks.urc.mapper;

import com.yks.urc.entity.DataRuleColDO;
import com.yks.urc.entity.UserDO;

import java.util.List;

import org.apache.ibatis.annotations.Param;


public interface IDataRuleColMapper {
    int insert(DataRuleColDO record);

    int insertBatch(List<DataRuleColDO> dataRuleDOList);

    int deleteByPrimaryKey(Long id);

    int insertSelective(DataRuleColDO record);

    DataRuleColDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DataRuleColDO record);

    int updateByPrimaryKeyWithBLOBs(DataRuleColDO record);

    int updateByPrimaryKey(DataRuleColDO record);
    
    
	List<DataRuleColDO> listRuleColBySysKey(@Param("dataRuleSysId") long dataRuleSysId );
}