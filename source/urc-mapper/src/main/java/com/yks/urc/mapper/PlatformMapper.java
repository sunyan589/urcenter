package com.yks.urc.mapper;

import com.yks.urc.entity.PlatformDO;
import com.yks.urc.vo.OmsPlatShopSiteVO;

import java.util.List;

public interface PlatformMapper {

    int insertPlatform(PlatformDO record);

    int deletePlatform();

    /**
     *  获取所有的平台
     * @param
     * @return
     * @Author lwx
     * @Date 2018/9/5 11:52
     */
    List<PlatformDO> selectAll();

    List<OmsPlatShopSiteVO> getAllPlatformShopSite();

    /**
     *  查询对应的平台
     * @param
     * @return
     * @Author lwx
     * @Date 2018/9/5 11:52
     */
    List<PlatformDO> selectPlatforms(List<String> platforms);
}