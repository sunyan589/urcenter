package com.yks.urc.mapper;


import com.yks.urc.entity.ShopSiteDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ShopSiteMapper {

    int insertBatchShopSite(@Param("shopSiteDOList") List<ShopSiteDO> shopSiteDOList);

    int deleteShopSite();
    /**
     * 查询卖家id不为空的账号站点
     * @param:
     * @return
     * @Author lwx
     * @Date 2018/7/24 16:24
     */
    List<ShopSiteDO> selectShopSiteByPlatformId(@Param("platformId") String platformId);

    /**
     * 查询账号站点
     * @param:
     * @return
     * @Author lwx
     * @Date 2018/7/24 16:27
     */
    List<ShopSiteDO> selectShopSite(@Param("platformId") String platformId);

    /**
     * 分页查询账号
     * @param:
     * @return
     * @Author lwx
     * @Date 2018/7/21 10:33
     */
    List<String> listShopByPage(Map<String, Object> data);

    long listShopByPageCount(Map<String,Object> data);

    /**
     *   查询所有数据 入缓存
     * @param
     * @return
     * @Author lwx
     * @Date 2018/12/17 16:19
     */
    List<ShopSiteDO> selectAll();


}