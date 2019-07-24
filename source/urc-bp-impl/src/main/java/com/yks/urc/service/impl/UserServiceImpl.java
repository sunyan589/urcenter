package com.yks.urc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yks.common.enums.CommonMessageCodeEnum;
import com.yks.urc.authway.bp.api.AuthWayBp;
import com.yks.urc.dataauthorization.bp.api.DataAuthorization;
import com.yks.urc.entity.*;
import com.yks.urc.exception.URCServiceException;
import com.yks.urc.fw.HttpUtility2;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.fw.constant.StringConstant;
import com.yks.urc.log.Log;
import com.yks.urc.mapper.IRoleMapper;
import com.yks.urc.mapper.IUserMapper;
import com.yks.urc.mapper.PlatformMapper;
import com.yks.urc.mapper.ShopSiteMapper;
import com.yks.urc.service.api.IUserService;
import com.yks.urc.session.bp.api.ISessionBp;
import com.yks.urc.user.bp.api.IUserBp;
import com.yks.urc.userValidate.bp.api.IUserValidateBp;
import com.yks.urc.vo.*;
import com.yks.urc.vo.helper.Query;
import com.yks.urc.vo.helper.VoHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserServiceImpl implements IUserService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IUserBp userBp;
    @Autowired
    DataAuthorization dataAuthorization;
    @Autowired
    private AuthWayBp authWayBp;
    @Autowired
    private IUserMapper userMapper;
    @Autowired
    private IUserValidateBp userValidateBp;
    @Autowired
    private IRoleMapper roleMapper;
    @Autowired
    private ISessionBp sessionBp;
    @Autowired
    private IUserMapper iUserMapper;

    @Value("${userInfo.resetPwdGetVerificationCode}")
    private String resetPwdGetVerificationCode;
    @Value("${sku.castInfo}")
    private String castInfo;
    @Value("${warehouse.warehouseInfo}")
    private String warehouseInfo;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO syncUserInfo(String operator) {
        ResultVO resultVO = new ResultVO();
        try {
            resultVO = userBp.SynUserFromUserInfo(operator);
        } catch (Exception e) {
            logger.error("同步任务异常" + e.getMessage());
            return VoHelper.getErrorResult();
        } finally {
            return resultVO;
        }
    }

    @Override
    public ResultVO login(Map<String, String> map) {
        return userBp.login(map);
    }

    @Override
    public ResultVO logout(String jsonStr) {
        return userBp.logout(jsonStr);
    }

	/*
     * public ResultVO queryUserDataByRuleId(DataRuleDO ruleDO) { List<UserDO>
	 * userList = userMapper.queryUserDataByRuleId(ruleDO); if (userList != null &&
	 * userList.size() > 0) { return VoHelper.getSuccessResult(userList); } return
	 * VoHelper.getErrorResult(); }
	 *
	 * public ResultVO queryUserNoDataByRuleId(DataRuleDO ruleDO) { List<UserDO>
	 * userList = userMapper.queryUserNoDataByRuleId(ruleDO); if (userList != null
	 * && userList.size() > 0) { return VoHelper.getSuccessResult(userList); }
	 * return VoHelper.getErrorResult(); }
	 */


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<PageResultVO> getUsersByUserInfo(String operator, UserVO userVO, String pageNumber, String pageData) {
        //首先要判断该用户是否是超级管理员或业务管理员
        if (!roleMapper.isAdminOrSuperAdmin(operator)) {
            return VoHelper.getResultVO(CommonMessageCodeEnum.FAIL.getCode(), "非管理员无法查看此数据");
        }
        return userBp.getUsersByUserInfo(operator, userVO, pageNumber, pageData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<List<OmsPlatformVO>> getPlatformList(String operator) {
        ResultVO<List<OmsPlatformVO>> rslt = new ResultVO();
        try {
            if (StringUtility.isNullOrEmpty(operator)) {
                rslt.msg = "操作人员为空 " + operator;
                rslt.state = CommonMessageCodeEnum.FAIL.getCode();
                return rslt;
            }
            rslt.data = dataAuthorization.getPlatformList(operator);
            if (rslt.data == null) {
                rslt.msg = "Error,获取的平台为空 " + operator;
                rslt.state = CommonMessageCodeEnum.FAIL.getCode();
                return rslt;
            }
            rslt.msg = "Success " + operator;
            rslt.state = CommonMessageCodeEnum.SUCCESS.getCode();
        } catch (Exception e) {
            logger.error("未知异常", e);
            throw new URCServiceException(CommonMessageCodeEnum.UNKOWN_ERROR.getCode(), "出现未知异常", e);
        } finally {
            return rslt;
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<List<OmsShopVO>> getShopList(String operator, String platform) {
        ResultVO<List<OmsShopVO>> rslt = new ResultVO();
        try {
            if (StringUtility.isNullOrEmpty(operator) || StringUtility.isNullOrEmpty(platform)) {
                return rslt;
            }
            rslt.data = dataAuthorization.getShopList(operator, platform);
            rslt.msg = "Success";
            rslt.state = CommonMessageCodeEnum.SUCCESS.getCode();
            if (rslt.data == null) {
                rslt.msg = "Error 无法找到此平台的店铺信息,或者无此平台," + operator;
                rslt.state = CommonMessageCodeEnum.FAIL.getCode();
                return rslt;
            }
        } catch (Exception e) {
            logger.error("未知异常", e);
            throw new URCServiceException(CommonMessageCodeEnum.UNKOWN_ERROR.getCode(), "出现未知异常", e);
        } finally {
            return rslt;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<List<SysAuthWayVO>> getMyAuthWay(String operator) {
        ResultVO<List<SysAuthWayVO>> rslt = new ResultVO();
        try {
            if (StringUtility.isNullOrEmpty(operator)) {
                return rslt;
            }
            rslt.data = authWayBp.getMyAuthWay(operator);
            if (rslt.data == null) {
                rslt.msg = "Failed ," + operator + "您不是管理员,没有授权权限";
                rslt.state = CommonMessageCodeEnum.FAIL.getCode();
                return rslt;
            }
            rslt.msg = "成功, " + operator;
            rslt.state = CommonMessageCodeEnum.SUCCESS.getCode();
        } catch (Exception e) {
            logger.error("未知异常", e);
            throw new URCServiceException(CommonMessageCodeEnum.UNKOWN_ERROR.getCode(), "出现未知异常", e);
        } finally {
            return rslt;
        }
    }

    @Log("getAllFuncPermit")
    @Override
    public ResultVO<GetAllFuncPermitRespVO> getAllFuncPermit(String jsonStr) {
        try {
            JSONObject jsonObject = StringUtility.parseString(jsonStr);
            String operator = jsonObject.getString(StringConstant.operator);
            return userBp.getAllFuncPermit(operator);
        } catch (Exception ex) {
            logger.error(String.format("getAllFuncPermit:%s", jsonStr), ex);
            return VoHelper.getErrorResult();
        }
    }

    @Log("funcPermitValidate")
    @Override
    public ResultVO funcPermitValidate(Map<String, String> map) {
        return userValidateBp.funcPermitValidate(map);
    }


    @Override
    public ResultVO fuzzySearchUsersByUserName(String pageNumber, String pageData, String userName, String operator) {
        UserVO userVO = new UserVO();
        userVO.userName = userName;
        if (!roleMapper.isSuperAdminAccount(operator)) {
            userVO.createBy = operator;
        }
        Query query = new Query(userVO, pageNumber, pageData);
        List<UserVO> userList = userMapper.fuzzySearchUsersByUserName(query);
        long userCount = userMapper.fuzzySearchUsersByUserNameCount(query);
        PageResultVO pageResultVO = new PageResultVO(userList, userCount, pageData);
        return VoHelper.getSuccessResult(pageResultVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<List<UserVO>> getUserByUserName(String operator, UserVO userVO) {
        List<UserVO> userVOS = new ArrayList<>();
        UserVO userVO1 = new UserVO();
        UserDO userDO = userMapper.getUserByUserName(userVO);
        if (userDO == null) {
            return VoHelper.getResultVO(CommonMessageCodeEnum.FAIL.getCode(), "用户不存在");
        }
        userVO1.userName = userDO.getUserName();
        userVOS.add(userVO1);
        return VoHelper.getSuccessResult(userVOS);
    }

    @Autowired
    private PlatformMapper platformMapper;
    @Autowired
    private ShopSiteMapper shopSiteMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<List<OmsPlatformVO>> getPlatformShopSite(String operator) {
        try {
            List<PlatformDO> platformDOS = platformMapper.selectAll();
            //装载平台volist
            List<OmsPlatformVO> omsPlatformVOS = new ArrayList<>();
            for (PlatformDO platformDO : platformDOS) {
                OmsPlatformVO omsPlatformVO = new OmsPlatformVO();
                omsPlatformVO.platformId = platformDO.getPlatformId();
                omsPlatformVO.platformName = platformDO.getPlatformName();

                List<ShopSiteDO> shopSiteDOS = shopSiteMapper.selectShopSiteByPlatformId(platformDO.getPlatformId());
                if (shopSiteDOS == null || shopSiteDOS.size() == 0) {
                    continue;
                } else {
                    //集合都必须先初识化
                    omsPlatformVO.lstShop = new ArrayList<>(shopSiteDOS.size());
                    for (ShopSiteDO shopSiteDO : shopSiteDOS) {
                        OmsShopVO omsShopVO = new OmsShopVO();
                        //针对速卖通的
                        omsShopVO.shopId = shopSiteDO.getSellerId();
                        omsShopVO.shopName = shopSiteDO.getShop();
                        //如果站点id为空,则list为空
                        if ("".equals(shopSiteDO.getSiteId())) {
                            omsShopVO.lstSite = null;
                        } else {
                            OmsSiteVO omsSiteVO = new OmsSiteVO();
                            omsSiteVO.siteId = shopSiteDO.getSiteId();
                            //如果站点名称为空,则吧站点id赋值给站点名称
                            if ("".equals(shopSiteDO.getSiteName())) {
                                omsSiteVO.siteName = omsSiteVO.siteId;
                            } else {
                                omsSiteVO.siteName = shopSiteDO.getSiteName();
                                omsShopVO.lstSite.add(omsSiteVO);
                            }
                        }
                        omsPlatformVO.lstShop.add(omsShopVO);
                    }
                    omsPlatformVOS.add(omsPlatformVO);
                }
            }
            return VoHelper.getSuccessResult(omsPlatformVOS);
        } catch (Exception e) {
            logger.error("获取数据异常:", e);
            return VoHelper.getErrorResult(CommonMessageCodeEnum.FAIL.getCode(), "未知异常");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO syncPlatform(String operator) {
        try {
            dataAuthorization.syncPlatform(operator);
            return VoHelper.getSuccessResult();
        } catch (Exception e) {
            logger.error("同步平台数据出错", e);
            return VoHelper.getErrorResult();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO syncShopSite(String operator) {
        try {
            dataAuthorization.syncShopSite(operator);
            return VoHelper.getSuccessResult();
        } catch (Exception e) {
            logger.error("同步账号站点数据出错", e);
            return VoHelper.getErrorResult();
        }
    }

    @Override
    public ResultVO resetPwdGetVerificationCode(String userName, String mobile) {
        if (StringUtility.isNullOrEmpty(userName)) {
            return VoHelper.getErrorResult(CommonMessageCodeEnum.PARAM_NULL.getCode(), "用户名不能为空");
        }
        if (StringUtility.isNullOrEmpty(mobile)) {
            return VoHelper.getErrorResult(CommonMessageCodeEnum.PARAM_NULL.getCode(), "手机号不能为空");
        }
        Map map = new HashMap(10);
        map.put("username", userName);
        map.put("mobile", mobile);
        map.put("get_code", "true");
        String response;
        try {
            response = HttpUtility2.postForm(resetPwdGetVerificationCode, map, null);
        } catch (Exception e) {
            return VoHelper.getErrorResult(CommonMessageCodeEnum.FAIL.getCode(), "获取验证码失败");
        }
        JSONObject jsonObject = JSONObject.parseObject(response);
        String message = jsonObject.getString("message");
        String error = jsonObject.getString("error");
        if (!StringUtility.isNullOrEmpty(error)) {
            return VoHelper.getErrorResult(CommonMessageCodeEnum.FAIL.getCode(), error);
        }
        return VoHelper.getSuccessResult(message);
    }

    @Override
    public ResultVO resetPwdSubmit(String mobile, String new_password, String username, String code) {
        ResultVO rslt = new ResultVO();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", mobile);
        jsonObject.put("code", code);
        jsonObject.put("username", username);
        jsonObject.put("new_password", new_password);
        jsonObject.put("get_code", false);
        String requestBody = jsonObject.toString();
        Map<String, String> requestHeader = new HashMap();
        requestHeader.put("Content-Type", "application/json");
        String response;
        try {
            response = HttpUtility2.postString(resetPwdGetVerificationCode, requestBody, requestHeader);
        } catch (Exception e) {
            return VoHelper.getErrorResult(CommonMessageCodeEnum.FAIL.getCode(), "重置密码失败。");
        }
        JSONObject jsonObjectResponse = JSONObject.parseObject(response);
        String message = jsonObjectResponse.getString("message");
        String error = jsonObjectResponse.getString("error");
        if (!StringUtility.isNullOrEmpty(error)) {
            return VoHelper.getResultVO(CommonMessageCodeEnum.FAIL.getCode(), error);
        }
        rslt.msg = message;
        rslt.state = CommonMessageCodeEnum.SUCCESS.getCode();
        return rslt;
    }

    @Override
    public ResultVO getBasicDataList(String jsonStr) {
        ResultVO resultVO = new ResultVO();
        String response;
        try {
            JSONObject jsonObject = StringUtility.parseString(jsonStr);
            String operator = jsonObject.getString("operator");
            if (operator == null) {
                logger.error("操作人员不能为空");
                return VoHelper.getResultVO(CommonMessageCodeEnum.PARAM_NULL.getCode(), "操作人员不能为空");
            }
            response = HttpUtility2.postForm(castInfo, null, null);
            JSONArray jsonArray = JSONArray.parseArray(response);
            int size1 = jsonArray.size();
            SkuCategoryVO skuCategoryVO = new SkuCategoryVO();
            //  List<CategoryVO> categoryVOList = new ArrayList<>();
            List<CategoryResponseVO> categoryResponseVOListFirst = new ArrayList<>();
            String cateId;
            String cateNameCn;
            for (int i = 0; i < size1; i++) {
                CategoryResponseVO categoryResponseVO = new CategoryResponseVO();
                List<CategoryResponseVO> categoryResponseVOListSecond = new ArrayList<>();
                //第一级分类
                JSONObject jsonObjectFirst = StringUtility.parseString(StringUtility.toJSONString(jsonArray.get(i)));
                cateId = jsonObjectFirst.getString("cateId");
                cateNameCn = jsonObjectFirst.getString("cateNameCn");
                categoryResponseVO.setCateId(cateId);
                categoryResponseVO.setCateNameCn(cateNameCn);
                JSONArray jsonArraySceond = jsonObjectFirst.getJSONArray("subCategorys");
                int size2 = jsonArraySceond.size();
                for (int j = 0; j < size2; j++) {
                    CategoryResponseVO categoryResponseVOSecond = new CategoryResponseVO();
                    List<CategoryResponseVO> categoryResponseVOListThird = new ArrayList<>();
                    JSONObject jsonObjectSecond = StringUtility.parseString(StringUtility.toJSONString(jsonArraySceond.get(j)));
                    cateId = jsonObjectSecond.getString("cateId");
                    cateNameCn = jsonObjectSecond.getString("cateNameCn");
                    categoryResponseVOSecond.setCateId(cateId);
                    categoryResponseVOSecond.setCateNameCn(cateNameCn);
                    JSONArray jsonArrayThird = jsonObjectSecond.getJSONArray("subCategorys");
                    int size3 = jsonArrayThird.size();

                    for (int k = 0; k < size3; k++) {
                        CategoryResponseVO categoryResponseVOThird = new CategoryResponseVO();
                        JSONObject jsonObjectThird = StringUtility.parseString(StringUtility.toJSONString(jsonArrayThird.get(k)));
                        cateId = jsonObjectThird.getString("cateId");
                        cateNameCn = jsonObjectThird.getString("cateNameCn");
                        categoryResponseVOThird.setCateId(cateId);
                        categoryResponseVOThird.setCateNameCn(cateNameCn);
                        categoryResponseVOListThird.add(categoryResponseVOThird);
                    }
                    categoryResponseVOSecond.setChildren(categoryResponseVOListThird);
                    categoryResponseVOListSecond.add(categoryResponseVOSecond);
                }
                categoryResponseVO.setChildren(categoryResponseVOListSecond);
                categoryResponseVOListFirst.add(categoryResponseVO);
            }
            //组装返回数据basicDataVO
            BasicDataVO basicDataVO = getBasicDataVO(skuCategoryVO, categoryResponseVOListFirst);
            resultVO.msg = "操作成功";
            resultVO.data = basicDataVO;
            resultVO.state = CommonMessageCodeEnum.SUCCESS.getCode();
        } catch (Exception e) {
            logger.error("获取sku分类,库存等数据权限失败", e);
            return VoHelper.getErrorResult();
        }
        return resultVO;
    }


    private BasicDataVO getBasicDataVO(SkuCategoryVO skuCategoryVO, List<CategoryResponseVO> categoryResponseVOList) {
        skuCategoryVO.setFirstCategory(categoryResponseVOList);
        List<DataColumnVO> dataColumnVOList = new ArrayList<>();
        DataColumnVO chineseNameDataColumnVO = new DataColumnVO();
        chineseNameDataColumnVO.setKey("chineseName");
        chineseNameDataColumnVO.setName("中文名称");
        DataColumnVO costPriceDataColumnVO = new DataColumnVO();
        costPriceDataColumnVO.setKey("costPrice");
        costPriceDataColumnVO.setName("成本价");
        DataColumnVO availableStockDataColumnVO = new DataColumnVO();
        availableStockDataColumnVO.setKey("availableStock");
        availableStockDataColumnVO.setName("可用库存");
        DataColumnVO logisticsPropertiesDataColumnVO = new DataColumnVO();
        logisticsPropertiesDataColumnVO.setKey("logisticsProperties");
        logisticsPropertiesDataColumnVO.setName("物流属性");
        dataColumnVOList.add(chineseNameDataColumnVO);
        dataColumnVOList.add(costPriceDataColumnVO);
        dataColumnVOList.add(availableStockDataColumnVO);
        dataColumnVOList.add(logisticsPropertiesDataColumnVO);
        skuCategoryVO.setDataColumn(dataColumnVOList);
        BasicDataVO basicDataVO = new BasicDataVO();
        basicDataVO.setBasicData(skuCategoryVO);
        return basicDataVO;
    }

    @Override
    public ResultVO getWarehouse(String jsonStr) {
        ResultVO resultVO = new ResultVO();
        JSONObject jsonObject = StringUtility.parseString(jsonStr);
        String operator = jsonObject.getString("operator");
        if (operator == null) {
            logger.error("操作人员不能为空");
            return VoHelper.getErrorResult(CommonMessageCodeEnum.PARAM_NULL.getCode(), "操作人员不能为空");
        }
        List<Object> list = new ArrayList();
        try {
            String response = HttpUtility2.postForm(warehouseInfo, null, null);
            JSONObject jsonObjectResponse = StringUtility.parseString(response);
            JSONArray jsonArray = jsonObjectResponse.getJSONArray("data");
            int size = jsonArray.size();
            for (int i = 0; i < size; i++) {
                WarehourseResponseVO warehourseResponseVO = new WarehourseResponseVO();
                JSONObject jsonObjectToWeb = StringUtility.parseString(StringUtility.toJSONString(jsonArray.get(i)));
                if (!StringUtility.isNullOrEmpty(jsonObjectToWeb.getString("code"))) {
                    warehourseResponseVO.setLabel(jsonObjectToWeb.getString("name"));
                    warehourseResponseVO.setValue(jsonObjectToWeb.getString("code"));
                    list.add(warehourseResponseVO);
                }
            }
        } catch (Exception e) {
            logger.error(String.format("Failed to get warehousing data authorization:%s", jsonStr), e);
            return VoHelper.getResultVO(CommonMessageCodeEnum.FAIL.getCode(), "获取仓储数据授权失败！");
        }
        resultVO.state = CommonMessageCodeEnum.SUCCESS.getCode();
        resultVO.data = list;
        resultVO.msg = "获取成功";
        return resultVO;
    }

    @Override
    public ResultVO searchUserPerson(String jsonStr) {
        ResultVO resultVO = new ResultVO();
        List<UserAndPersonDO> userAndPersonDOS = new ArrayList<>();
        try {
            JSONObject jsonObject = StringUtility.parseString(jsonStr).getJSONObject("data");
            String searchContext = jsonObject.getString("searchContext");
            Integer pageData = jsonObject.getInteger("pageData");
            if (pageData == null) {
                return VoHelper.getErrorResult(CommonMessageCodeEnum.PARAM_NULL.getCode(), "每页的条数不能为空");
            }
            Integer pageNumber = jsonObject.getInteger("pageNumber");
            if (pageNumber == null) {
                return VoHelper.getErrorResult(CommonMessageCodeEnum.PARAM_NULL.getCode(), "页码不能为空");
            }
            if (pageData <= 0 || pageData == null) {
                pageData = 10;
            }
            if (pageNumber <= 0 || pageNumber == null) {
                pageNumber = 1;
            }
            int currIndex = (pageNumber - 1) * pageData;
            int currSize = pageData;
            UserPersonParamDO userPersonParamDO = new UserPersonParamDO();
            userPersonParamDO.currIndex = currIndex;
            userPersonParamDO.currSize = currSize;
            userPersonParamDO.searchContext = searchContext;
            userAndPersonDOS = iUserMapper.selectUserNameAndPeronNameByUserName(userPersonParamDO);
        } catch (Exception e) {
            logger.error(String.format("Search user online account and user name failed:%s", jsonStr), e);
            return VoHelper.getErrorResult(CommonMessageCodeEnum.FAIL.getCode(), "搜索用户上网账号和用户名失败");
        }
        resultVO.data = userAndPersonDOS;
        resultVO.msg = "搜索用户上网账号和用户名成功";
        resultVO.state = CommonMessageCodeEnum.SUCCESS.getCode();
        return resultVO;
    }
}


