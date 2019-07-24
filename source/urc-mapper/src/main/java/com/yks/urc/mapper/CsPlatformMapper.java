package com.yks.urc.mapper;

import com.yks.urc.entity.CsPlatform;
import com.yks.urc.entity.CsPlatformGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CsPlatformMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CsPlatform record);

    int insertSelective(CsPlatform record);

    CsPlatform selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CsPlatform record);

    int updateByPrimaryKey(CsPlatform record);

    List<CsPlatform> selectAllInfo();

    CsPlatform selectByPlatformId(@Param(value = "platformId") String centerPlatformId);
}