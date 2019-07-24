/**
 * 〈一句话功能简述〉<br>
 * 〈获取平台,店铺,站点信息〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/6/12
 * @since 1.0.0
 */
package com.yks.urc.dataauthorization.bp.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yks.common.enums.CommonMessageCodeEnum;
import com.yks.distributed.lock.core.DistributedReentrantLock;
import com.yks.urc.cache.bp.api.ICacheBp;
import com.yks.urc.dataauthorization.bp.api.DataAuthorization;
import com.yks.urc.entity.PlatformDO;
import com.yks.urc.entity.ShopSiteDO;
import com.yks.urc.exception.ErrorCode;
import com.yks.urc.exception.URCBizException;
import com.yks.urc.exception.URCServiceException;
import com.yks.urc.fw.HttpUtility;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.mapper.PlatformMapper;
import com.yks.urc.mapper.ShopSiteMapper;
import com.yks.urc.operation.bp.api.IOperationBp;
import com.yks.urc.vo.*;
import com.yks.urc.vo.helper.VoHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class DataAuthorizationImpl implements DataAuthorization {
    private static Logger logger = LoggerFactory.getLogger(DataAuthorizationImpl.class);
    /**
     * 请求平台url
     */
    @Value("${dataAuthorization.getPlatformList}")
    private String GET_PLATFORM;
    /**
     * 请求店铺账号和站点信息
     */
    @Value("${dataAuthorization.getShopList}")
    private String GET_SHOP_AND_SITE;

    @Autowired
    private PlatformMapper platformMapper;
    @Autowired
    private ShopSiteMapper shopSiteMapper;
    @Autowired
    private IOperationBp operationBp;
    @Autowired
    private ICacheBp  cacheBp;
    /**
     *  ebay entity
     */
    private String E_PlsShopAccount ="E_PlsShopAccount";
    /**
     *  客服 entity
     */
    private String E_CustomerService ="E_CustomerService";

    DistributedReentrantLock platformLock = new DistributedReentrantLock("syncPlatform");

    /**
     * 同步平台信息
     *
     * @param operator
     * @return
     * @Author linwanxian@youkeshu.com
     * @Date 2018/6/12 20:52
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO syncPlatform(String operator) {
        if (platformLock.tryLock()) {
            try {
                String getPlatformResult = HttpUtility.httpGet(GET_PLATFORM);
                //将拿到的结果转为json ,获取平台信息
                JSONObject platformObject = StringUtility.parseString(getPlatformResult);
                if (platformObject.getInteger("state") != 200) {
                    throw new URCBizException("请求平台信息异常", ErrorCode.E_000000);
                } else {
                    JSONArray dataArray = platformObject.getJSONArray("data");
                    List<PlatformResp> platformResps = StringUtility.jsonToList(dataArray.toString(), PlatformResp.class);
                    platformResps =platformResps.stream().filter(distinctByKey(platformResp -> platformResp.code)).collect(Collectors.toList());
                    if (platformResps != null && platformResps.size() > 0) {
                        platformMapper.deletePlatform();
                        logger.info("清理平台表完成");
                        for (PlatformResp platformResp : platformResps) {
                            PlatformDO platformDO = new PlatformDO();
                            platformDO.setPlatformId(StringUtility.trimPattern_Private(platformResp.code, "\\s"));
                            platformDO.setPlatformName(StringUtility.trimPattern_Private(platformResp.name,"\\s"));
                            platformDO.setCreateBy(operator);
                            platformDO.setModifiedBy(operator);
                            platformDO.setCreateTime(StringUtility.getDateTimeNow());
                            platformDO.setModifiedTime(StringUtility.getDateTimeNow());
                            platformMapper.insertPlatform(platformDO);
                        }
                        logger.info("装载平台表完成");
                    }
                    operationBp.addLog(this.getClass().getName(), "同步平台数据成功..", null);
                    return VoHelper.getResultVO(CommonMessageCodeEnum.SUCCESS.getCode(), "同步平台数据成功..");
                }
            } catch (Exception e) {
                throw new URCServiceException(CommonMessageCodeEnum.FAIL.getCode(), "同步平台数据出错..", e);
            } finally {
                platformLock.unlock();
            }
        } else {

            logger.info("同步userInfo数据正在执行...,");
            if (!"system".equals(operator)) {
                // 手动触发正在执行..记录日志
                operationBp.addLog(this.getClass().getName(), "手动触发正在执行..", null);
                return VoHelper.getResultVO(CommonMessageCodeEnum.SUCCESS.getCode(), "手动触发正在执行..");
            } else {
                // 定时任务触发正在执行..记录日志
                operationBp.addLog(this.getClass().getName(), "定时任务正在执行..", null);
                return VoHelper.getResultVO(CommonMessageCodeEnum.SUCCESS.getCode(), "定时任务正在执行..");
            }
        }
    }

    DistributedReentrantLock shopSiteLock = new DistributedReentrantLock("syncShopSite");
    /**
     * 同步站点和店铺信息
     *
     * @param operator
     * @param operator
     * @return
     * @Author linwanxian@youkeshu.com
     * @Date 2018/6/12 21:14
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO syncShopSite(String operator) {
        if (shopSiteLock.tryLock()) {
            try {
                List<PlatformDO> platformDOS = platformMapper.selectAll();
                if (platformDOS == null && platformDOS.size() ==0){
                    throw new URCBizException(CommonMessageCodeEnum.HANDLE_DATA_EXCEPTION.getCode(), "数据库查找的平台信息为空");
                }
                shopSiteMapper.deleteShopSite();
                logger.info("清理账号站点表完成");
                for (PlatformDO platformDO : platformDOS) {
                    // 将获取的平台进行转码
                    String platforms = URLEncoder.encode(platformDO.getPlatformId(),"utf-8");
                    StringBuffer url =new StringBuffer();
                    url.append(GET_SHOP_AND_SITE).append("&platform=").append(platforms);
                    logger.info(String.format("请求的地址为:[%s ]",url));
                    String getShopAndSiteResult = HttpUtility.httpGet(String.valueOf(url));
                    logger.info(String.format("获取的结果为:[%s ]",getShopAndSiteResult));
                    if (StringUtility.isNullOrEmpty(getShopAndSiteResult)) {
                        throw new URCBizException(CommonMessageCodeEnum.FAIL.getCode(), "获取账号站点信息为空");
                    }
                    JSONObject shopObject = StringUtility.parseString(getShopAndSiteResult);
                    if (shopObject.getInteger("state") != 200) {
                        throw new URCBizException("请求平台信息异常", ErrorCode.E_000000);
                    } else {
                        JSONArray dataArray = shopObject.getJSONArray("data");
                        List<ShopAndSiteResp> shopAndSiteResps = StringUtility.jsonToList(dataArray.toString(), ShopAndSiteResp.class);
                        List<ShopSiteDO> shopSiteDOS = new ArrayList<>();

                        for (ShopAndSiteResp shopAndSiteResp : shopAndSiteResps) {
                            // 组装数据入库
                            ShopSiteDO shopSiteDO = assembleShopSiteDO(operator, shopSiteDOS, shopAndSiteResp);
                            if (shopSiteDOS.size() >= 1000) {
                                //去重
                                shopSiteDOS =shopSiteDOS.stream().filter(distinctByKey(ShopSiteDO::getShopSystem)).collect(Collectors.toList());
                                shopSiteMapper.insertBatchShopSite(shopSiteDOS);
                                shopSiteDOS.clear();
                            }
                        }
                        if (shopSiteDOS.size() != 0) {
                            //去重
                            shopSiteDOS =shopSiteDOS.stream().filter(distinctByKey(ShopSiteDO::getShopSystem)).collect(Collectors.toList());
                            shopSiteMapper.insertBatchShopSite(shopSiteDOS);
                        }
                    }
                }
                // 入缓存
                assembleCache();
                operationBp.addLog(this.getClass().getName(), "同步账号站点数据成功..", null);
                return VoHelper.getResultVO(CommonMessageCodeEnum.SUCCESS.getCode(), "同步账号站点数据成功..");
            } catch (Exception e) {
                throw new URCServiceException(CommonMessageCodeEnum.FAIL.getCode(), "同步账号站点数据出错..", e);
            } finally {
                shopSiteLock.unlock();
            }
        } else {
            logger.info("同步userInfo数据正在执行...,");
            if (!"system".equals(operator)) {
                // 手动触发正在执行..记录日志
                operationBp.addLog(this.getClass().getName(), "手动触发正在执行..", null);
                return VoHelper.getResultVO(CommonMessageCodeEnum.SUCCESS.getCode(), "手动触发正在执行..");
            } else {
                // 定时任务触发正在执行..记录日志
                operationBp.addLog(this.getClass().getName(), "定时任务正在执行..", null);
                return VoHelper.getResultVO(CommonMessageCodeEnum.SUCCESS.getCode(), "定时任务正在执行..");
            }
        }
    }

    /**
     *  入缓存
     * @param
     * @return
     * @Author lwx
     * @Date 2018/12/17 16:29
     */
    public void assembleCache() {
        // 入缓存
        List<PlatformDO> platformDOS = platformMapper.selectAll();
        if (CollectionUtils.isEmpty(platformDOS)) {
            return;
        }
        List<OmsPlatformVO> customerCache = new ArrayList<>();
        List<OmsPlatformVO> ebayCache = new ArrayList<>();
        // 入缓存
        platformDOS.forEach(platformDO -> {
            OmsPlatformVO platformVO = new OmsPlatformVO();
            //ebay只需要 shopee ebay 亚马逊 lazada 速卖通
            if (platformDO == null) {
                return;
            }
            platformVO.platformId = platformDO.getPlatformId();
            platformVO.platformName = platformDO.getPlatformName();
            platformVO.lstShop = new ArrayList<>();
            List<ShopSiteDO> shopSiteDOS = shopSiteMapper.selectShopSite(platformDO.getPlatformId());
            shopSiteDOS.forEach(shopSiteDO -> {
                OmsShopVO shopVO = new OmsShopVO();
                shopVO.shopId = shopSiteDO.getShopSystem();
                shopVO.shopName = shopSiteDO.getShop();
                platformVO.lstShop.add(shopVO);
            });
            customerCache.add(platformVO);
            if ("shopee".equalsIgnoreCase(platformDO.getPlatformId()) || "ebay".equalsIgnoreCase(platformDO.getPlatformId()) || "亚马逊".equalsIgnoreCase(platformDO.getPlatformId()) || "lazada".equalsIgnoreCase(platformDO.getPlatformId()) || "速卖通".equalsIgnoreCase(platformDO.getPlatformId())) {
                ebayCache.add(platformVO);
            }
        });
        // 入 ebay缓存
        cacheBp.setAllPlatformShop(StringUtility.toJSONString_NoException(ebayCache),E_PlsShopAccount);
        // 入客服缓存
        cacheBp.setAllPlatformShop(StringUtility.toJSONString_NoException(customerCache),E_CustomerService);
    }

    /**
     *   数据组装
     * @param
     * @return
     * @Author lwx
     * @Date 2018/12/17 16:07
     */
    public ShopSiteDO assembleShopSiteDO(String operator, List<ShopSiteDO> shopSiteDOS, ShopAndSiteResp shopAndSiteResp) {
        ShopSiteDO shopSiteDO = new ShopSiteDO();
        shopSiteDO.setPlatformId(StringUtility.trimPattern_Private(shopAndSiteResp.platform_code,"\\s"));
        shopSiteDO.setSellerId(StringUtility.trimPattern_Private(shopAndSiteResp.sellerid,"\\s"));
        shopSiteDO.setShopSystem(StringUtility.trimPattern_Private(shopAndSiteResp.shop_system,"\\s"));
        shopSiteDO.setShop(StringUtility.trimPattern_Private(shopAndSiteResp.shop,"\\s"));
        shopSiteDO.setSiteId(StringUtility.trimPattern_Private(shopAndSiteResp.site_code,"\\s"));
        shopSiteDO.setSiteName(StringUtility.trimPattern_Private(shopAndSiteResp.site_name,"\\s"));
        shopSiteDO.setCreateTime(StringUtility.getDateTimeNow());
        shopSiteDO.setCreateBy(operator);
        shopSiteDO.setModifiedTime(StringUtility.getDateTimeNow());
        shopSiteDO.setModifiedBy(operator);
        shopSiteDOS.add(shopSiteDO);
        return shopSiteDO;
    }

    @Override
    public List<OmsPlatformVO> getPlatformList(String operator) {
        List<OmsPlatformVO> omsPlatformVoList = new ArrayList<>();
       // String getPlatformResult = HttpUtility.httpGet(GET_PLATFORM);
        //将拿到的结果转为json ,获取平台信息
       // JSONObject platformObject = StringUtility.parseString(getPlatformResult);
        List<PlatformDO> platformDOS =platformMapper.selectAll();
        if (platformDOS != null && platformDOS.size() >0){
            platformDOS.forEach(platformDO -> {
                OmsPlatformVO omsPlatformVO = new OmsPlatformVO();
                omsPlatformVO.platformId =platformDO.getPlatformId();
                //如果没有name , 将id作为name
                if (StringUtility.isNullOrEmpty(platformDO.getPlatformName())){
                    omsPlatformVO.platformName =omsPlatformVO.platformId;
                }else {
                    omsPlatformVO.platformName=platformDO.getPlatformName();
                }
                omsPlatformVoList.add(omsPlatformVO);
            });
        }
        /*if (platformObject.getInteger("state") == 200) {
            JSONArray dataArray = platformObject.getJSONArray("data");
            List<PlatformResp> platformResps = StringUtility.jsonToList(dataArray.toString(), PlatformResp.class);
            for (PlatformResp platformResp : platformResps) {
                OmsPlatformVO omsPlatformVO = new OmsPlatformVO();
                omsPlatformVO.platformId = platformResp.code;
                // 如果id 为空,则不装载数据
                if (StringUtility.isNullOrEmpty(omsPlatformVO.platformId)) {
                    continue;
                }
                omsPlatformVO.platformName = platformResp.name;
                omsPlatformVoList.add(omsPlatformVO);
            }
        }*/
        return omsPlatformVoList;
    }

    /**
     * 获取平台下的站点和店铺信息
     *
     * @param operator
     * @param platform
     * @return List<DataAuthorizationVo>
     * @Author linwanxian@youkeshu.com
     * @Date 2018/6/12 21:14
     */
    @Override
    public List<OmsShopVO> getShopList(String operator, String platform) {
        List<OmsShopVO> omsShopVoList = new ArrayList<>();
        if (StringUtility.isNullOrEmpty(platform)){
            return null;
        }
       List<ShopSiteDO> shopSiteDOS= shopSiteMapper.selectShopSite(platform);
        if (shopSiteDOS != null && shopSiteDOS.size() >0){
            shopSiteDOS.forEach(shopSiteDO -> {
                OmsShopVO omsShopVO = new OmsShopVO();
                OmsSiteVO omsSiteVO =new OmsSiteVO();
                omsShopVO.shopId =shopSiteDO.getShopSystem();
                if (StringUtility.isNullOrEmpty(omsShopVO.shopId)) {
                    return;
                }
                if (StringUtility.isNullOrEmpty(shopSiteDO.getShop())){
                    omsShopVO.shopName=omsShopVO.shopId;
                }else {
                    omsShopVO.shopName = shopSiteDO.getShop();
                }
                omsShopVO.lstSite =new ArrayList<>();
                omsSiteVO.siteId =shopSiteDO.getSiteId();
                if (StringUtility.isNullOrEmpty(omsSiteVO.siteId)){
                    return;
                }
                if (StringUtility.isNullOrEmpty(shopSiteDO.getSiteName())){
                   omsSiteVO.siteName =omsSiteVO.siteId;
                }else {
                    omsSiteVO.siteName =shopSiteDO.getSiteName();
                }
                omsShopVO.lstSite.add(omsSiteVO);
                omsShopVoList.add(omsShopVO);
            });
        }
    /*    *//*String url = GET_SHOP_AND_SITE + "&platform=" + platform;
        String getShopAndSiteResult = HttpUtility.httpGet(url);
        if (StringUtility.isNullOrEmpty(getShopAndSiteResult)) {
            throw new URCBizException(CommonMessageCodeEnum.FAIL.getCode(),"获取站点信息为空");
        }
        JSONObject shopObject = StringUtility.parseString(getShopAndSiteResult);*//*
        if (shopObject.getInteger("state") == 200) {
            JSONArray dataArray = shopObject.getJSONArray("data");
            List<ShopAndSiteResp> shopAndSiteResps = StringUtility.jsonToList(dataArray.toString(), ShopAndSiteResp.class);
            for (ShopAndSiteResp shopAndSiteResp : shopAndSiteResps) {
                OmsShopVO omsShopVO = new OmsShopVO();
                omsShopVO.shopId = shopAndSiteResp.sellerid;
                // 如果id 为空,则不装载数据
                if (StringUtility.isNullOrEmpty(omsShopVO.shopId)) {
                    continue;
                }
                omsShopVO.shopName = shopAndSiteResp.shop_system;
                // name没有, 将id赋值给name
                if (StringUtility.isNullOrEmpty(omsShopVO.shopName)) {
                    omsShopVO.shopName = omsShopVO.shopId;
                }
                // 如果获取的site_code 为空,则不装载数
                if (StringUtility.isNullOrEmpty(shopAndSiteResp.site_code)) {
                    omsShopVO.lstSite = null;
                } else {
                    omsShopVO.lstSite = new ArrayList<>();
                    OmsSiteVO omsSiteVO = new OmsSiteVO();
                    omsSiteVO.siteId = shopAndSiteResp.site_code;
                    omsSiteVO.siteName = shopAndSiteResp.site_name;
                    // name 没有 将id  赋值给name
                    if (StringUtility.isNullOrEmpty(omsSiteVO.siteName)) {
                        omsSiteVO.siteName = omsSiteVO.siteId;
                    }
                    omsShopVO.lstSite.add(omsSiteVO);
                }
                omsShopVoList.add(omsShopVO);
            }
        }*/
        return omsShopVoList;
    }

    public static void main(String[] args) {
        DataAuthorizationImpl dataAuthorizationImpl = new DataAuthorizationImpl();
        List<OmsPlatformVO> omsPlatformVOS = dataAuthorizationImpl.getPlatformList("test3");
        for (OmsPlatformVO omsPlatformVO : omsPlatformVOS) {
            System.out.println(omsPlatformVO.platformName);
        }
        System.out.println("============================");
        String platform = "eBay";
        List<OmsShopVO> omsShopVoList = dataAuthorizationImpl.getShopList("test3", platform);
        for (OmsShopVO omsShopVO : omsShopVoList) {
            System.out.println(omsShopVO.shopName);
        }
    }
    /**
     * 对象去重构造器
     * @param
     * @return
     * @Author lwx
     * @Date 2018/10/17 15:48
     */
    public static <T>Predicate<T> distinctByKey(Function<? super T,Object> keyExtractor){
        Map<Object,Boolean> seen =new ConcurrentHashMap<>();
        return object ->seen.putIfAbsent(keyExtractor.apply(object),Boolean.TRUE) ==null;
    }

}
