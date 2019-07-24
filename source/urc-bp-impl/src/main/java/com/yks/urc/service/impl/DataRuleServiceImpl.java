package com.yks.urc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yks.common.enums.CommonMessageCodeEnum;
import com.yks.common.util.DateUtil;
import com.yks.common.util.StringUtil;
import com.yks.oms.order.manage.motan.service.api.IOrderManageService;
import com.yks.urc.cache.bp.api.ICacheBp;
import com.yks.urc.entity.*;
import com.yks.urc.exception.ErrorCode;
import com.yks.urc.exception.URCBizException;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.mapper.*;
import com.yks.urc.mq.bp.api.IMqBp;
import com.yks.urc.seq.bp.api.ISeqBp;
import com.yks.urc.service.api.IDataRuleService;
import com.yks.urc.service.api.IUserService;
import com.yks.urc.vo.*;
import com.yks.urc.vo.helper.Query;
import com.yks.urc.vo.helper.VoHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉
 * 功能权限模板操作service
 *
 * @author lvcr
 * @version 1.0
 * @date 2018/6/12 9:04
 * @see DataRuleServiceImpl
 * @since JDK1.8
 */
@Service
public class DataRuleServiceImpl implements IDataRuleService {
    private static final Logger logger = Logger.getLogger(DataRuleServiceImpl.class);

    @Autowired
    private IDataRuleTemplMapper dataRuleTemplMapper;

    @Autowired
    private CsPlatformMapper csPlatformMapper;

    @Autowired
    private IDataRuleMapper dataRuleMapper;

    @Autowired
    private IDataRuleSysMapper dataRuleSysMapper;

    @Autowired
    private IUserRoleMapper userRoleMapper;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private IRoleMapper roleMapper;

    @Autowired
    private IExpressionMapper expressionMapper;

    @Autowired
    private IDataRuleColMapper dataRuleColMapper;

    @Autowired
    private FieldMapper fieldMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private IMqBp mqBp;

    @Autowired
    private ISeqBp seqBp;


    @Autowired
    private CsPlatformGroupMapper csPlatformGroupMapper;

    @Autowired
    private IUserService userService;


    @Autowired
    private IDataRuleService dataRuleService;

    @Autowired
    private ShopSiteMapper shopSiteMapper;

    @Autowired
    private PlatformMapper platformMapper;

    @Autowired
    private ICacheBp cacheBp;

    @Autowired(required = false)
    private IOrderManageService orderManageService;
    /**
     * ebay 缓存
     */
    private String Key_platform_shop ="ebay_platform_shop";
    /**
     * 客服缓存
     */
    private String Key_all_platform_shop ="all_platform_shop";
    /**
     * Description: 根据模板Id获取数据权限模板
     *
     * @param :
     * @return:
     * @auther: lvcr
     * @date: 2018/6/12 9:04
     * @see
     */
    @Override
    public ResultVO<DataRuleTemplVO> getDataRuleTemplByTemplId(String jsonStr) {
        JSONObject jsonObject = StringUtility.parseString(jsonStr);
        /**
         * 1、获取参数并做基本校验
         */
        String operator = jsonObject.getString("operator");
        if (StringUtil.isEmpty(operator)) {
            // logger.error("当前用户不能为空");
            throw new URCBizException("parameter operator is null", ErrorCode.E_000002);
        }
        String templIdStr = jsonObject.getString("templId");
        if (StringUtil.isEmpty(templIdStr)) {
            throw new URCBizException("parameter templId is null", ErrorCode.E_000002);
        }
        Long templId = Long.valueOf(templIdStr);
        /**
         * 2、获取权限模板信息
         */
        Boolean isSuperAdmin = roleMapper.isSuperAdminAccount(operator);
        /*非超级管理员用户需要校验当前用户是否与方案模板对应拥有系统匹配*/
        String valFlag = jsonObject.getString("valFlag");
        if (!isSuperAdmin && "1".equals(valFlag)) {
            Boolean isBizAdmin = roleMapper.isAdminAccount(operator);
            if (isBizAdmin) {
                checkSysPermission(operator, templId);
            } else {
                throw new URCBizException(ErrorCode.E_000003.getState(), String.format("用户[%s]非管理员，不能选择该方案", operator));
            }
        }
        DataRuleTemplVO dataRuleTemplVO = new DataRuleTemplVO();

        DataRuleTemplDO dataRuleTemplDO = null;
        if (isSuperAdmin) {
            dataRuleTemplDO = dataRuleTemplMapper.selectByTemplId(templId, null);
        } else {
            dataRuleTemplDO = dataRuleTemplMapper.selectByTemplId(templId, operator);
        }
        if (dataRuleTemplDO == null) {
            return VoHelper.getSuccessResult();
            //throw new URCBizException(String.format("urc_data_rule_templ is null where templId is: %s and operator is: %s",templId,operator),ErrorCode.E_000002);
        }
        BeanUtils.copyProperties(dataRuleTemplDO, dataRuleTemplVO);
        dataRuleTemplVO.setTemplId(String.valueOf(dataRuleTemplDO.getTemplId()));

        /**
         *3、获取数据权限Sys  行权限数据 列权限数据 实体定义名称 字段定义名称
         */
        //List<DataRuleSysDO> dataRuleSysDOS = dataRuleSysMapper.getDataRuleSysDatas(templId);
//        List<DataRuleSysDO> dataRuleSysDOS = new ArrayList<>();
        /*获取系统对应的名称*/
        Map<String, PermissionDO> sysNameMap = permissionMapper.perMissionMap();
        /*获取数据权限Sys  行权限数据 列权限数据*/
        List<DataRuleSysDO> dataRuleSyAndOpers = dataRuleSysMapper.getDataRuleSyAndOpersById(templId);
        setName(dataRuleSyAndOpers, sysNameMap);
        /*获取实体定义*/
        Map<String, Entity> entityMap = entityMapper.getEntityMap();
        /*获取字段定义*/
        Map<String, Field> fieldMap = fieldMapper.getFieldMap();
        /*设置实体名称名称和字段定义名称*/
        setEntityNameAndFieldName(dataRuleSyAndOpers, entityMap, fieldMap);

        /**
         * 4、重新组装行权限数据
         */
        for (DataRuleSysDO dataRuleSysDO : dataRuleSyAndOpers) {
            List<ExpressionDO> expressionDOS = dataRuleSysDO.getExpressionDOS();
            /*获取父级行权限*/
            ExpressionDO parentExpressionDO = null;
            for (ExpressionDO expressionDO : expressionDOS) {
                if (expressionDO.getParentExpressionId() == null) {
                    parentExpressionDO = expressionDO;
                    break;
                }
            }
            /*组装父级行权限*/
            List<ExpressionDO> subExpressionDOList = new ArrayList<>();
            for (ExpressionDO expressionDO : expressionDOS) {
                if (expressionDO.getParentExpressionId() != null && parentExpressionDO.getExpressionId().equals(expressionDO.getParentExpressionId())) {
                    subExpressionDOList.add(expressionDO);
                }
                parentExpressionDO.setExpressionDOList(subExpressionDOList);
            }
            dataRuleSysDO.setParentExpressionDO(parentExpressionDO);
        }
        /**
         * 5、DO 转 VO
         */
        List<DataRuleSysVO> dataRuleSysVOS = new ArrayList<>();
        convertRuleSysDo2Vo(dataRuleSyAndOpers, dataRuleSysVOS);
        /*设置权限系统数据  一个系统一个dataRukeSysVo*/
        dataRuleTemplVO.setLstDataRuleSys(dataRuleSysVOS);

        return VoHelper.getSuccessResult(dataRuleTemplVO);
    }

    /**
     * Description: 判断当前用户对应权限的系统是否包含 方案对应的系统
     *
     * @param :
     * @return:
     * @auther: lvcr
     * @date: 2018/7/4 17:38
     * @see
     */
    private void checkSysPermission(String operator, Long templId) {
        /*获取方案对应的数据权限系统*/
        List<String> templOwnSyss = new ArrayList<>();
        templOwnSyss = dataRuleSysMapper.getTemplOwnSysByDataRuleId(templId);
        /*获取用户对应的数据权限系统*/
        List<String> operatorOwnSyss = new ArrayList<>();
        operatorOwnSyss = userRoleMapper.getUserOwnSysByUserName(operator);
        Map<String, PermissionDO> permissionDOMap = permissionMapper.perMissionMap();
        if (!operatorOwnSyss.containsAll(templOwnSyss)) {
            StringBuilder diffSyss = new StringBuilder();
            for (String templOwnSys : templOwnSyss) {
                if (!operatorOwnSyss.contains(templOwnSys)) {
                    if (permissionDOMap.get(templOwnSys) != null) {
                        diffSyss.append(permissionDOMap.get(templOwnSys).getSysName()).append(",");
                    }
                }
            }
            throw new URCBizException(ErrorCode.E_000003.getState(), String.format("用户[%s]没有该方案对应系统权限，例如[%s]", operator, diffSyss.toString().substring(0, diffSyss.toString().length() - 1)));
        } else {
            for (String templOwnSys : templOwnSyss) {
                Boolean isSysAdmin = roleMapper.isSysAdminAccount(operator, templOwnSys);
                if (!isSysAdmin) {
                    throw new URCBizException(ErrorCode.E_000003.getState(), String.format("用户[%s]不是系统[%s]的业务管理员，不能操作该方案", operator, permissionDOMap.get(templOwnSys).getSysName()));
                }
            }


        }
    }

    private void setEntityNameAndFieldName(List<DataRuleSysDO> dataRuleSyAndOpers, Map<String, Entity> entityMap, Map<String, Field> fieldMap) {
        for (DataRuleSysDO dataRuleSysDO : dataRuleSyAndOpers) {
            List<DataRuleColDO> dataRuleColDOS = dataRuleSysDO.getDataRuleColDOS();
            for (DataRuleColDO dataRuleColDO : dataRuleColDOS) {
                dataRuleColDO.setEntityName(entityMap.get(dataRuleColDO.getEntityCode()) == null ? null : entityMap.get(dataRuleColDO.getEntityCode()).getEntityName());
            }

            List<ExpressionDO> expressionDOS = dataRuleSysDO.getExpressionDOS();
            for (ExpressionDO expressionDO : expressionDOS) {
                expressionDO.setEntityName((entityMap.get(expressionDO.getEntityCode())) == null ? null : entityMap.get(expressionDO.getEntityCode()).getEntityName());
                expressionDO.setFieldName((fieldMap.get(expressionDO.getFieldCode())) == null ? null : fieldMap.get(expressionDO.getFieldCode()).getFieldName());
            }
        }
    }

    private void setName(List<DataRuleSysDO> dataRuleSyAndOpers, Map<String, PermissionDO> sysNameMap) {
        for (DataRuleSysDO dataRuleSysDO : dataRuleSyAndOpers) {
            dataRuleSysDO.setSysName(sysNameMap.get(dataRuleSysDO.getSysKey()).getSysName());
        }
    }


    /**
     * Description: 根据方案名称、创建人等条件获取数据授权方案（分页）
     *
     * @param : jsonStr
     * @return: ResultVO<PageResultVO>
     * @auther: lvcr
     * @date: 2018/6/12 20:54
     * @see
     */
    @Override
    public ResultVO<PageResultVO> getDataRuleTempl(String jsonStr) {
        /*1、json字符串转json对象*/
        JSONObject jsonObject = StringUtility.parseString(jsonStr);
        /*2、请求参数的基本校验并转换为内部使用的Map*/
        Map<String, Object> queryMap = new HashMap<>();
        checkAndConvertParam(queryMap, jsonObject);
        /*3、查询数据权限模板列表信息*/
        List<DataRuleTemplDO> dataRuleTemplDOS = dataRuleTemplMapper.listDataRuleTemplDOsByPage(queryMap);
        /*4、List<DO> 转 List<VO>*/
        List<DataRuleTemplVO> dataRuleTemplVOS = convertDoToVO(dataRuleTemplDOS);
        /*5、获取总条数*/
        Long total = dataRuleTemplMapper.getCounts(queryMap);
        PageResultVO pageResultVO = new PageResultVO(dataRuleTemplVOS, total, queryMap.get("pageSize").toString());
        return VoHelper.getSuccessResult(pageResultVO);
    }

    /**
     * Description: 检查输入参数并转换
     *
     * @param :
     * @return:
     * @auther: lvcr
     * @date: 2018/6/13 11:54
     * @see
     */
    private void checkAndConvertParam(Map<String, Object> queryMap, JSONObject jsonObject) {
         /*获取当前用户*/
        String operator = jsonObject.getString("operator");
        if (StringUtility.isNullOrEmpty(operator)) {
            throw new URCBizException("parameter operator is null", ErrorCode.E_000002);
        }
        queryMap.put("createBy", operator);
        String pageNumber = jsonObject.getString("pageNumber");
        String pageData = jsonObject.getString("pageData");
        if (!StringUtil.isNum(pageNumber) || !StringUtil.isNum(pageData)) {
            throw new URCBizException(String.format("parameter pageNumber or pageData is not num pageNumber:%s , pageData:%s", pageNumber, pageData), ErrorCode.E_000002);
        }
        int currPage = Integer.valueOf(pageNumber);
        int pageSize = Integer.valueOf(pageData);
        queryMap.put("currIndex", (currPage - 1) * pageSize);
        queryMap.put("pageSize", pageSize);

        /*获取当前用户的角色*/
        Boolean isAdmin = roleMapper.isSuperAdminAccount(operator);
        if (isAdmin) {
            //如果是管理员,createBy不作为查询条件
            queryMap.put("createBy", null);
        }
        /*获取复数模板名称*/
        String templStr = jsonObject.getString("templ");
        if (StringUtil.isNotEmpty(templStr)) {
            DataRuleTemplVO dataRuleTemplVO = StringUtility.parseObject(templStr, DataRuleTemplVO.class);
            String[] templNames = dataRuleTemplVO.templName.split(System.getProperty("line.separator"));
            queryMap.put("templNames", templNames);
        }
    }


    /**
     * Description: 数据授权方案 快速分配数据权限模板给用户
     * 1-快速分配
     * 2-发送到MQ
     *
     * @param :
     * @return:
     * @auther: lvcr
     * @date: 2018/6/12 21:03
     * @see
     */
    @Transactional
    @Override
    public ResultVO assignDataRuleTempl2User(String jsonStr) {
        /*1、将json字符串转为Json对象*/
        JSONObject jsonObject = StringUtility.parseString(jsonStr);
        /*2、获取参数并校验*/
        String operator = jsonObject.getString("operator");
        if (StringUtil.isEmpty(operator)) {
            throw new URCBizException("parameter operator is null", ErrorCode.E_000002);
        }
        String templIdStr = jsonObject.getString("templId");
        if (StringUtil.isEmpty(templIdStr)) {
            throw new URCBizException("parameter templId is null", ErrorCode.E_000002);
        }
        Long templId = Long.valueOf(templIdStr);
        String lstUserNameStr = jsonObject.getString("lstUserName");
        if (StringUtil.isEmpty(lstUserNameStr)) {
            throw new URCBizException("parameter lstUserName is null", ErrorCode.E_000002);
        }
        List<String> lstUserName = StringUtility.jsonToList(lstUserNameStr, String.class);
        if (lstUserName == null || lstUserName.isEmpty()) {
            throw new URCBizException("parameter lstUserName is null", ErrorCode.E_000002);
        }
        Boolean isSuperAdmin = roleMapper.isSuperAdminAccount(operator);
        /*非超级管理员用户需要校验当前用户是否与方案模板对应拥有系统匹配*/
        if (!isSuperAdmin) {
            Boolean isBizAdmin = roleMapper.isAdminAccount(operator);
            if (isBizAdmin) {
                checkSysPermission(operator, templId);
            } else {
                throw new URCBizException(ErrorCode.E_000003.getState(), String.format("用户[%s]非管理员，不能操作该方案", operator));
            }
        }
        /*3、获取该模板对应的数据权限对应系统数据*/
//        List<DataRuleSysDO> dataRuleSysDOS = dataRuleSysMapper.getDataRuleSysDatas(templId);
          /*获取系统对应的名称*/
        Map<String, PermissionDO> sysNameMap = permissionMapper.perMissionMap();
        /*获取数据权限Sys  行权限数据 列权限数据*/
        List<DataRuleSysDO> dataRuleSyAndOpers = dataRuleSysMapper.getDataRuleSyAndOpersById(templId);
        setName(dataRuleSyAndOpers, sysNameMap);
        /*获取实体定义*/
        Map<String, Entity> entityMap = entityMapper.getEntityMap();
        /*获取字段定义*/
        Map<String, Field> fieldMap = fieldMapper.getFieldMap();
        /*设置实体名称名称和字段定义名称*/
        setEntityNameAndFieldName(dataRuleSyAndOpers, entityMap, fieldMap);
        if (dataRuleSyAndOpers == null || dataRuleSyAndOpers.isEmpty()) {
            throw new URCBizException("get urc_data_rule_sys is null where templId is:" + templId, ErrorCode.E_000003);
        }
        /*数据权限对应系统缓存列表 */
        List<DataRuleSysDO> dataRuleSysDOSCache = new ArrayList<>();
        /*用户-数据权限关系缓存列表*/
        List<DataRuleDO> dataRuleDOSCache = new ArrayList<>();
         /*列权限数据缓存列表*/
        List<DataRuleColDO> dataRuleColDOSCache = new ArrayList<>();
         /*行权限数据缓存列表*/
        List<ExpressionDO> expressionDOSCache = new ArrayList<>();
       /*4、组装数据  并放入待入库列表*/
        assembleDatasToAdd(dataRuleSysDOSCache, dataRuleDOSCache, dataRuleColDOSCache, expressionDOSCache, lstUserName, operator, dataRuleSyAndOpers);
        /*5.1、删除用户原有的数据权限关系数据 包括行权限 列权限*/
        List<Long> dataRuleIds = dataRuleMapper.getDataRuleIdsByUserName(lstUserName);
        dataRuleMapper.delBatchByUserNames(lstUserName);
        /*5.2、删除用户列表对应的 数据权限Sys   行权限  列权限*/
        if (dataRuleIds != null && !dataRuleIds.isEmpty()) {
            dataRuleSysMapper.delRuleSysDatasByIdsAndCreatBy(dataRuleIds, null);
        }
        /*6 批量添加用户数据权限关系数据*/
        if (dataRuleDOSCache != null && !dataRuleDOSCache.isEmpty()) {
            dataRuleMapper.insertBatch(dataRuleDOSCache);
        }
        /*7 批量添加数据权限系统数据*/
        if (dataRuleSysDOSCache != null && !dataRuleSysDOSCache.isEmpty()) {
            dataRuleSysMapper.insertBatch(dataRuleSysDOSCache);
        }
        /*批量添加对应的行权限数据*/
        if (dataRuleColDOSCache != null && !dataRuleColDOSCache.isEmpty()) {
            dataRuleColMapper.insertBatch(dataRuleColDOSCache);
        }
        /*批量添加对应的列权限数据*/
        if (expressionDOSCache != null && !expressionDOSCache.isEmpty()) {
            expressionMapper.insertBatch(expressionDOSCache);
        }
        /*8、发送消息到kafka*/
        /**
         * 重新组装行权限数据
         */
        for (DataRuleSysDO dataRuleSysDO : dataRuleSyAndOpers) {
            List<ExpressionDO> expressionDOS = dataRuleSysDO.getExpressionDOS();
            /*获取父级行权限*/
            ExpressionDO parentExpressionDO = null;
            for (ExpressionDO expressionDO : expressionDOS) {
                if (expressionDO.getParentExpressionId() == null) {
                    parentExpressionDO = expressionDO;
                    break;
                }
            }
            /*组装父级行权限*/
            List<ExpressionDO> subExpressionDOList = new ArrayList<>();
            for (ExpressionDO expressionDO : expressionDOS) {
                if (expressionDO.getParentExpressionId() != null && parentExpressionDO.getExpressionId().equals(expressionDO.getParentExpressionId())) {
                    subExpressionDOList.add(expressionDO);
                }
                parentExpressionDO.setExpressionDOList(subExpressionDOList);
            }
            dataRuleSysDO.setParentExpressionDO(parentExpressionDO);
        }
        List<DataRuleSysVO> dataRuleSysVOS = new ArrayList<>();
        /**/
        convertRuleSysDo2VoNoName(dataRuleSyAndOpers, dataRuleSysVOS);
        List<DataRuleVO> dataRuleVOS = new ArrayList<>();
        for (String userName : lstUserName) {
            DataRuleVO dataRuleVO = new DataRuleVO();
            dataRuleVO.userName = userName;
            List<DataRuleSysVO> dataRuleSysVOS1 = new ArrayList();
            dataRuleSysVOS1.addAll(StringUtility.jsonToList(StringUtility.toJSONString_NoException(dataRuleSysVOS), DataRuleSysVO.class));
            for (DataRuleSysVO mem : dataRuleSysVOS1) {
                mem.t = String.valueOf(new Date().getTime());
                mem.setUserName(userName);
            }
            dataRuleVO.lstDataRuleSys = dataRuleSysVOS1;
            dataRuleVOS.add(dataRuleVO);
        }
        sendToMq(dataRuleVOS);
        return VoHelper.getSuccessResult();
    }

    private void convertRuleSysDo2Vo(List<DataRuleSysDO> dataRuleSyAndOpers, List<DataRuleSysVO> dataRuleSysVOS) {
        for (DataRuleSysDO dataRuleSysDO : dataRuleSyAndOpers) {
            DataRuleSysVO dataRuleSysVO = new DataRuleSysVO();
            BeanUtils.copyProperties(dataRuleSysDO, dataRuleSysVO);
            /*列权限DO 转 VO*/
            List<DataRuleColVO> dataRuleColVOS = new ArrayList<>();
            List<DataRuleColDO> dataRuleColDOS = dataRuleSysDO.getDataRuleColDOS();
            for (DataRuleColDO dataRuleColDO : dataRuleColDOS) {
                DataRuleColVO dataRuleColVO = new DataRuleColVO();
                BeanUtils.copyProperties(dataRuleColDO, dataRuleColVO);
                dataRuleColVOS.add(dataRuleColVO);

                dataRuleSysVO.setCol(dataRuleColVOS);
            }
            /*行权限 DO 转 VO*/
            ExpressionDO parentExpressionDO = dataRuleSysDO.getParentExpressionDO();
            ExpressionVO parentExpressionVO = new ExpressionVO();
            BeanUtils.copyProperties(parentExpressionDO, parentExpressionVO);
            parentExpressionVO.setIsAnd(parentExpressionDO.getAnd() ? 1 : 0);
            List<ExpressionDO> subExpressionDOS = parentExpressionDO.getExpressionDOList();
            List<ExpressionVO> subExpressionVOS = new ArrayList<>();
            for (ExpressionDO subExpressionDO : subExpressionDOS) {
                ExpressionVO subExpressionVO = new ExpressionVO();
                BeanUtils.copyProperties(subExpressionDO, subExpressionVO);
                subExpressionVO.setOperValuesArr(JSONArray.parseArray(subExpressionDO.getOperValues(), String.class));
                subExpressionVOS.add(subExpressionVO);
            }
            parentExpressionVO.setSubWhereClause(subExpressionVOS);
            dataRuleSysVO.setRow(parentExpressionVO);

            dataRuleSysVOS.add(dataRuleSysVO);
        }
    }


    private void convertRuleSysDo2VoNoName(List<DataRuleSysDO> dataRuleSyAndOpers, List<DataRuleSysVO> dataRuleSysVOS) {
        for (DataRuleSysDO dataRuleSysDO : dataRuleSyAndOpers) {
            DataRuleSysVO dataRuleSysVO = new DataRuleSysVO();
            BeanUtils.copyProperties(dataRuleSysDO, dataRuleSysVO);
            dataRuleSysVO.setSysName(null);
            /*列权限DO 转 VO*/
            List<DataRuleColVO> dataRuleColVOS = new ArrayList<>();
            List<DataRuleColDO> dataRuleColDOS = dataRuleSysDO.getDataRuleColDOS();
            for (DataRuleColDO dataRuleColDO : dataRuleColDOS) {
                DataRuleColVO dataRuleColVO = new DataRuleColVO();
                BeanUtils.copyProperties(dataRuleColDO, dataRuleColVO);
                dataRuleColVO.setEntityName(null);
                dataRuleColVO.setDataRuleSysId(null);
                dataRuleColVO.setId(null);
                dataRuleColVOS.add(dataRuleColVO);
                dataRuleSysVO.setCol(dataRuleColVOS);
            }
            /*行权限 DO 转 VO*/
            ExpressionDO parentExpressionDO = dataRuleSysDO.getParentExpressionDO();
            ExpressionVO parentExpressionVO = new ExpressionVO();
            BeanUtils.copyProperties(parentExpressionDO, parentExpressionVO);
            parentExpressionVO.setIsAnd(parentExpressionDO.getAnd() ? 1 : 0);
            parentExpressionVO.setFieldName(null);
            parentExpressionVO.setExpressionId(null);
            List<ExpressionDO> subExpressionDOS = parentExpressionDO.getExpressionDOList();
            List<ExpressionVO> subExpressionVOS = new ArrayList<>();
            for (ExpressionDO subExpressionDO : subExpressionDOS) {
                ExpressionVO subExpressionVO = new ExpressionVO();
                BeanUtils.copyProperties(subExpressionDO, subExpressionVO);
                subExpressionVO.setOperValuesArr(JSONArray.parseArray(subExpressionDO.getOperValues(), String.class));
                subExpressionVO.setOperValues(null);
                subExpressionVO.setEntityName(null);
                subExpressionVO.setFieldName(null);
                subExpressionVO.setExpressionId(null);
                subExpressionVO.setParentExpressionId(null);
                subExpressionVOS.add(subExpressionVO);
            }
            parentExpressionVO.setSubWhereClause(subExpressionVOS);
            dataRuleSysVO.setRow(parentExpressionVO);

            dataRuleSysVOS.add(dataRuleSysVO);
        }
    }

    /**
     * Description: 组装数据  并放入待入库列表
     *
     * @param :
     * @param dataRuleColDOSCache
     * @param expressionDOSCache  @return:
     * @auther: lvcr
     * @date: 2018/6/15 14:29
     * @see
     */
    private void assembleDatasToAdd(List<DataRuleSysDO> dataRuleSysDOSCache,
                                    List<DataRuleDO> dataRuleDOSCache,
                                    List<DataRuleColDO> dataRuleColDOSCache,
                                    List<ExpressionDO> expressionDOSCache,
                                    List<String> lstUserName,
                                    String createBy,
                                    List<DataRuleSysDO> dataRuleSysDOS) {
        for (String userName : lstUserName) {
            /*4、组装用户-数据权限关系数据  并放入待入库列表*/
            DataRuleDO dataRuleDO = new DataRuleDO();
            dataRuleDO.setCreateBy(createBy);
            dataRuleDO.setCreateTime(new Date());
            dataRuleDO.setUserName(userName);
            Long dataRuleId = seqBp.getNextDataRuleId();
            dataRuleDO.setDataRuleId(dataRuleId);
            dataRuleDOSCache.add(dataRuleDO);
            for (DataRuleSysDO dataRuleSysDO : dataRuleSysDOS) {
                DataRuleSysDO targetDataRuleSysDO = new DataRuleSysDO();
                BeanUtils.copyProperties(dataRuleSysDO, targetDataRuleSysDO);
                /*5、组装数据权限对应系统数据  并放入待入库缓存列表*/
                targetDataRuleSysDO.setDataRuleId(dataRuleId);
                Long dataRuleSysId = seqBp.getNextDataRuleSysId();
                targetDataRuleSysDO.setDataRuleSysId(dataRuleSysId);
                targetDataRuleSysDO.setCreateTime(new Date());
                targetDataRuleSysDO.setCreateBy(createBy);
                dataRuleSysDOSCache.add(targetDataRuleSysDO);
                /*6、组装列权限数据  并放入待入库缓存列表*/
                List<DataRuleColDO> dataRuleColDOS = dataRuleSysDO.getDataRuleColDOS();
                if (dataRuleColDOS != null && !dataRuleColDOS.isEmpty()) {
                    for (DataRuleColDO dataRuleColDO : dataRuleColDOS) {
                        DataRuleColDO targetDataRuleColDO = new DataRuleColDO();
                        BeanUtils.copyProperties(dataRuleColDO, targetDataRuleColDO);
                        targetDataRuleColDO.setDataRuleSysId(dataRuleSysId);
                        targetDataRuleColDO.setCreateBy(createBy);
                        targetDataRuleColDO.setCreateTime(new Date());
                        dataRuleColDOSCache.add(targetDataRuleColDO);
                    }
                }
                /*7、组装行权限数据  并放入待入库缓存列表*/
                List<ExpressionDO> expressionDOS = dataRuleSysDO.getExpressionDOS();
                Long parentExpressionId = null;
                /*父级行权限数据*/
                for (ExpressionDO expressionDO : expressionDOS) {
                    if (expressionDO.getParentExpressionId() == null) {
                        ExpressionDO targetExpressionDO = new ExpressionDO();
                        BeanUtils.copyProperties(expressionDO, targetExpressionDO);
                        targetExpressionDO.setDataRuleSysId(dataRuleSysId);
                        parentExpressionId = seqBp.getExpressionId();
                        targetExpressionDO.setExpressionId(parentExpressionId);
                        targetExpressionDO.setCreateBy(createBy);
                        targetExpressionDO.setCreateTime(new Date());
                        expressionDOSCache.add(targetExpressionDO);
                    }
                }
                /*子集行权限数据*/
                for (ExpressionDO expressionDO : expressionDOS) {
                    if (expressionDO.getParentExpressionId() != null) {
                        ExpressionDO targetExpressionDO = new ExpressionDO();
                        BeanUtils.copyProperties(expressionDO, targetExpressionDO);
                        targetExpressionDO.setDataRuleSysId(dataRuleSysId);
                        Long expressionId = seqBp.getExpressionId();
                        targetExpressionDO.setExpressionId(expressionId);
                        targetExpressionDO.setCreateBy(createBy);
                        targetExpressionDO.setCreateTime(new Date());
                        targetExpressionDO.setParentExpressionId(parentExpressionId);
                        expressionDOSCache.add(targetExpressionDO);
                    }
                }
            }
        }
    }

    /**
     * Description: 发送消息到MQ
     *
     * @param :
     * @return:
     * @auther: lvcr
     * @date: 2018/6/15 14:26
     * @see
     */
    private void sendToMq(List<DataRuleVO> dataRuleVOS) {
/*        List<DataRuleSysVO> dataRuleSysVOS = new ArrayList<>();
        for (DataRuleSysDO dataRuleSysDO : dataRuleSysDOS) {
            DataRuleSysVO dataRuleSysVO = new DataRuleSysVO();
            BeanUtils.copyProperties(dataRuleSysDO, dataRuleSysVO);
            dataRuleSysVOS.add(dataRuleSysVO);
        }
        List<DataRuleVO> dataRuleVOS = new ArrayList<>();
        for (String userName : lstUserName) {
            DataRuleVO dataRuleVO = new DataRuleVO();
            dataRuleVO.userName = userName;
            dataRuleVO.lstDataRuleSys = dataRuleSysVOS;
            dataRuleVOS.add(dataRuleVO);
        }*/
        mqBp.send2Mq(dataRuleVOS);
    }

    /**
     * Description:新增或编辑一个方案
     *
     * @param :jsonStr
     * @return: ResultVO<DataRuleTemplVO>
     * @auther: lvcr
     * @date: 2018/6/13 15:06
     * @see
     */
    @Transactional
    @Override
    public ResultVO<DataRuleTemplVO> addOrUpdateDataRuleTempl(String jsonStr) {
        /*1、将json字符串转为Json对象*/
        JSONObject jsonObject = StringUtility.parseString(jsonStr);
        /*2、获取参数*/
        String operator = jsonObject.getString("operator");
        if (StringUtil.isEmpty(operator)) {
            throw new URCBizException("parameter operator is null", ErrorCode.E_000002);
        }
        DataRuleTemplVO templVO = jsonObject.getObject("templ", DataRuleTemplVO.class);
        /*校验基本参数*/
        checkParam(templVO);
        /** 3、判断该方案是否属于当前用户（非管理员角色）
         *  1)、当temp方案存在 2)、当前用户非管理员  3)、temp方案不属于当前用户
         */
        String tempIdStr = templVO.getTemplId();
        DataRuleTemplDO dataRuleTemplDO = null;
        if (!StringUtil.isEmpty(tempIdStr)) {
            Long currentTemplId = Long.valueOf(tempIdStr);
            /*判断是否为超级管理员*/
            Boolean isAdmin = roleMapper.isSuperAdminAccount(operator);
            dataRuleTemplDO = dataRuleTemplMapper.selectByTemplId(currentTemplId, null);
            if (!isAdmin) {
                if (dataRuleTemplDO == null || !operator.equals(dataRuleTemplDO.getCreateBy())) {
                    throw new URCBizException(String.format("该方案不属于该用户，不能操作 where templId is: %s and operator is: %s", currentTemplId, operator), ErrorCode.E_000003);
                }
            }
             /*4、删除该方案对应的数据(包括对应的数据权限Sys、行权限、列权限)*/
            // dataRuleTemplMapper.delTemplDatasById(currentTemplId);
            dataRuleSysMapper.deldataRuleSysDatasById(currentTemplId);
        }
        /*5、新增该方案对应的数据（包括对应的数据权限Sys、行权限、列权限）*/
        insertOrUpdateTemlDatas(templVO, operator, dataRuleTemplDO);
        return VoHelper.getSuccessResult(templVO);
    }

    private void checkParam(DataRuleTemplVO templVO) {
        if (templVO == null) {
            throw new URCBizException("parameter templ is null", ErrorCode.E_000002);
        }
        if (StringUtil.isEmpty(templVO.getTemplName())) {
            throw new URCBizException("parameter templName is null", ErrorCode.E_000002);
        }
    }

    /**
     * Description:
     * 1、组装数据权限sys数据，并添加到缓存List dataRuleSysCache里，供批量添加
     * 2、组装行权限数据，并添加到缓存List expressionCache里，供批量添加
     * 3、组装列权限数据，并添加到缓存List dataRuleColCache里，供批量添加
     *
     * @param :dataRuleSysCache
     * @param :dataRuleColCache
     * @param :expressionCache
     * @param :dataRuleSysVOS
     * @param :templId          模板Id
     * @param :operator         当前用户
     * @auther: lvcr
     * @date: 2018/6/14 20:00
     * @see
     */
    private void assembleDataRuleSysDatas(List<DataRuleSysDO> dataRuleSysCache, List<DataRuleColDO> dataRuleColCache, List<ExpressionDO> expressionCache, List<DataRuleSysVO> dataRuleSysVOS, Long templId, String operator) {
        for (DataRuleSysVO dataRuleSysVO : dataRuleSysVOS) {
            /*1、添加到数据权限Sys列表*/
            DataRuleSysDO dataRuleSysDO = new DataRuleSysDO();
            dataRuleSysVO.setSysName(null);
            //增加创建时间
            dataRuleSysVO.t = String.valueOf(new Date().getTime());
            BeanUtils.copyProperties(dataRuleSysVO, dataRuleSysDO);
            Long dataRuleSysId = seqBp.getNextDataRuleSysId();
            dataRuleSysDO.setDataRuleSysId(dataRuleSysId);
            dataRuleSysDO.setDataRuleId(templId);
            dataRuleSysDO.setCreateBy(operator);
            dataRuleSysDO.setCreateTime(new Date());
            dataRuleSysCache.add(dataRuleSysDO);
            /*2、添加到行权限数据列表*/
            /*获取行权限数据*/
            ExpressionVO parentExpressionVO = dataRuleSysVO.getRow();
            /*组装父级行权限*/
            ExpressionDO parentExpressionDO = new ExpressionDO();
            BeanUtils.copyProperties(parentExpressionVO, parentExpressionDO);
            parentExpressionDO.setDataRuleSysId(dataRuleSysId);
            Long parentExpressionId = seqBp.getExpressionId();
            parentExpressionDO.setExpressionId(parentExpressionId);
            parentExpressionDO.setParentExpressionId(null);
            parentExpressionDO.setAnd(parentExpressionVO.getIsAnd() == 1 ? true : false);
            parentExpressionDO.setFieldCode(null);
            parentExpressionDO.setCreateBy(operator);
            parentExpressionDO.setCreateTime(new Date());
            expressionCache.add(parentExpressionDO);
            /*组装子级行权限数据*/
            List<ExpressionVO> subExpressions = parentExpressionVO.getSubWhereClause();
            if (subExpressions != null && !subExpressions.isEmpty()) {
                for (ExpressionVO subExpressionVO : subExpressions) {
                    ExpressionDO subExpressionDO = new ExpressionDO();
                    BeanUtils.copyProperties(subExpressionVO, subExpressionDO);
                    List<String> operValuesArrList = subExpressionVO.getOperValuesArr();
                    if (operValuesArrList != null && !operValuesArrList.isEmpty()) {
                        subExpressionDO.setOperValues(StringUtility.toJSONString(subExpressionVO.getOperValuesArr()));
                    }
                    subExpressionDO.setDataRuleSysId(dataRuleSysId);
                    subExpressionDO.setParentExpressionId(parentExpressionId);
                    Long expressionId = seqBp.getExpressionId();
                    subExpressionDO.setExpressionId(expressionId);
                    subExpressionDO.setCreateTime(new Date());
                    subExpressionDO.setCreateBy(operator);
                    expressionCache.add(subExpressionDO);
                }
            }
            /*3、添加到列权限数据列表*/
            /*获取列权限数据*/
            List<DataRuleColVO> dataRuleColVOS = dataRuleSysVO.getCol();
            if (dataRuleColVOS != null && !dataRuleColVOS.isEmpty()) {
                for (DataRuleColVO dataRuleColVO : dataRuleColVOS) {
                    DataRuleColDO dataRuleColDO = new DataRuleColDO();
                    BeanUtils.copyProperties(dataRuleColVO, dataRuleColDO);
                    dataRuleColDO.setDataRuleSysId(dataRuleSysId);
                    dataRuleColDO.setCreateTime(new Date());
                    dataRuleColDO.setCreateBy(operator);
                    dataRuleColCache.add(dataRuleColDO);
                }
            }
        }
    }

    /**
     * Description: 新增该方案对应的数据（包括对应的数据权限Sys、行权限、列权限）
     *
     * @param :
     * @return:
     * @auther: lvcr
     * @date: 2018/6/15 16:43
     * @see
     */
    private void insertOrUpdateTemlDatas(DataRuleTemplVO templVO, String operator, DataRuleTemplDO orgDataRuleTemplDO) {
        /*1、新增数据权限模板 urc_data_rule_templ*/
        Long templId = null;
        if (orgDataRuleTemplDO == null) {
            DataRuleTemplDO dataRuleTemplDO = new DataRuleTemplDO();
            BeanUtils.copyProperties(templVO, dataRuleTemplDO);
            templId = seqBp.getNextDataRuleTemplId();
            templVO.setTemplId(templId.toString());
            dataRuleTemplDO.setTemplId(templId);
            dataRuleTemplDO.setCreateBy(operator);
            dataRuleTemplDO.setCreateTime(new Date());
            dataRuleTemplDO.setModifiedTime(new Date());
            dataRuleTemplDO.setModifiedBy(operator);
            dataRuleTemplMapper.insert(dataRuleTemplDO);
        } else {
            DataRuleTemplDO dataRuleTemplDO = dataRuleTemplMapper.selectByTemplId(Long.parseLong(templVO.getTemplId()),null);
            if(dataRuleTemplDO!=null){
                if(!dataRuleTemplDO.getTemplName().equals(templVO.getTemplName()))
                {
                    if(dataRuleTemplMapper.checkDuplicateTemplName(templVO.getTemplName(),null))
                    {
                        throw new URCBizException(ErrorCode.E_000003.getState(),"方案名称重复");
                    }
                }
            }
            templId = orgDataRuleTemplDO.getTemplId();
            BeanUtils.copyProperties(templVO, orgDataRuleTemplDO);
            orgDataRuleTemplDO.setModifiedTime(new Date());
            orgDataRuleTemplDO.setModifiedBy(operator);
            dataRuleTemplMapper.updateDataRuleTemplById(orgDataRuleTemplDO);

        }

        /*2、新增数据权限sys urc_data_rule_sys */
        /*数据权限Sys列表*/
        List<DataRuleSysDO> dataRuleSysCache = new ArrayList<>();
        /*列权限数据列表*/
        List<DataRuleColDO> dataRuleColCache = new ArrayList<>();
        /*行权限数据列表*/
        List<ExpressionDO> expressionCache = new ArrayList<>();

        List<DataRuleSysVO> dataRuleSysVOS = templVO.getLstDataRuleSys();
        if (dataRuleSysVOS != null) {
            assembleDataRuleSysDatas(dataRuleSysCache, dataRuleColCache, expressionCache, dataRuleSysVOS, templId, operator);
            /*批量新增数据权限Sys*/
            if (dataRuleSysCache != null && !dataRuleSysCache.isEmpty()) {
                dataRuleSysMapper.insertBatch(dataRuleSysCache);
            }
             /*批量新增行权限数据*/
            if (expressionCache != null && !expressionCache.isEmpty()) {
                expressionMapper.insertBatch(expressionCache);
            }
            /*批量新增列权限数据*/
            if (dataRuleColCache != null && !dataRuleColCache.isEmpty()) {
                dataRuleColMapper.insertBatch(dataRuleColCache);
            }
        }


    }


    /**
     * Description: List<DO> 转List<VO>
     *
     * @param : dataRuleTemplDOS
     * @return: List<DataRuleTemplVO>
     * @auther: lvcr
     * @date: 2018/6/13 13:00
     * @see
     */
    private List<DataRuleTemplVO> convertDoToVO(List<DataRuleTemplDO> dataRuleTemplDOS) {
        List<DataRuleTemplVO> dataRuleTemplVOS = new ArrayList<>();
        for (DataRuleTemplDO dataRuleTemplDO : dataRuleTemplDOS) {
            DataRuleTemplVO dataRuleTemplVO = new DataRuleTemplVO();
            BeanUtils.copyProperties(dataRuleTemplDO, dataRuleTemplVO);
            dataRuleTemplVO.setTemplId(String.valueOf(dataRuleTemplDO.getTemplId()));
            dataRuleTemplVO.setCreateTimeStr(dataRuleTemplDO.getCreateTime() != null ? DateUtil.formatDate(dataRuleTemplDO.getCreateTime(), "yyyy-MM-dd HH:mm:ss") : null);
            dataRuleTemplVO.setModifiedTimeStr(dataRuleTemplDO.getModifiedTime() != null ? DateUtil.formatDate(dataRuleTemplDO.getModifiedTime(), "yyyy-MM-dd HH:mm:ss") : null);
            dataRuleTemplVOS.add(dataRuleTemplVO);
        }
        return dataRuleTemplVOS;
    }

    @Override
    public ResultVO getMyDataRuleTempl(String pageNumber, String pageData, String operator) {
        DataRuleTemplDO templDO = new DataRuleTemplDO();
        if (!roleMapper.isSuperAdminAccount(operator)) {
            templDO.setCreateBy(operator);
        }

        Query query = new Query(templDO, pageNumber, pageData);
        List<DataRuleTemplDO> dataRuleTempList = dataRuleTemplMapper.getMyDataRuleTempl(query);
        List<DataRuleTemplVO> dataRuleTempListVO = convertDoToVO(dataRuleTempList);
        int dataRuleTempCount = dataRuleTemplMapper.getMyDataRuleTemplCount(query);

        PageResultVO pageResultVO = new PageResultVO(dataRuleTempListVO, dataRuleTempCount, pageData);
        return VoHelper.getSuccessResult(pageResultVO);
    }

    /**
     * Description: 删除一个或多个方案 包括对应的数据权限Sys  行权限  列权限
     *
     * @param :
     * @return:
     * @auther: lvcr
     * @date: 2018/6/15 16:53
     * @see
     */
    @Override
    public ResultVO deleteDataRuleTempl(String jsonStr) {
         /*1、将json字符串转为Json对象*/
        JSONObject jsonObject = StringUtility.parseString(jsonStr);
        /*2、获取参数*/
        String operator = jsonObject.getString("operator");
        if (StringUtil.isEmpty(operator)) {
            throw new URCBizException("parameter operator is null", ErrorCode.E_000002);
        }
        String lstTemplIdStr = jsonObject.getString("lstTemplId");
        if (StringUtil.isEmpty(lstTemplIdStr)) {
            throw new URCBizException("parameter lstTemplIdStr is null", ErrorCode.E_000002);
        }
        List<Long> lstTemplId = StringUtility.jsonToList(lstTemplIdStr, Long.class);

        /*3、判断当前用户是否为管理员，普通用户只能删除自己管理的方案*/
        Boolean isAdmin = roleMapper.isSuperAdminAccount(operator);
        if (isAdmin) {
             /*4.1、根据templId列表批量删除数据权限方案模板*/
            //dataRuleTemplMapper.delTemplDatasByIds(lstTemplId);
            dataRuleTemplMapper.delTemplByIdsAndCreatBy(lstTemplId, null);
            dataRuleSysMapper.delRuleSysDatasByIdsAndCreatBy(lstTemplId, null);
        } else {
             /*4.2、根据templId列表和创建人批量删除数据权限方案模板*/
            //dataRuleTemplMapper.delTemplDatasByIdsAndCreatBy(lstTemplId, operator);
            dataRuleTemplMapper.delTemplByIdsAndCreatBy(lstTemplId, operator);
            dataRuleSysMapper.delRuleSysDatasByIdsAndCreatBy(lstTemplId, operator);
        }
        return VoHelper.getSuccessResult();
    }

    /**
     * Description: 创建或更新多个用户的数据权限
     *
     * @param jsonStr@return:
     * @auther: lvcr
     * @date: 2018/6/20 16:10
     * @see
     */
    @Override
    @Transactional
    public ResultVO addOrUpdateDataRule(String jsonStr) {
         /*1、将json字符串转为Json对象*/
        JSONObject jsonObject = StringUtility.parseString(jsonStr);
        /*2、获取参数*/
        String operator = jsonObject.getString("operator");
        if (StringUtil.isEmpty(operator)) {
            throw new URCBizException("parameter operator is null", ErrorCode.E_000002);
        }
        JSONObject dataJson = jsonObject.getJSONObject("data");
        List<String> lstUserName =StringUtility.parseObject(dataJson.getString("lstUserName").toString(),List.class);
        if (CollectionUtils.isEmpty(lstUserName)){
            throw new URCBizException("parameter lstUserName is null", ErrorCode.E_000002);
        }
        //DataRuleSysVO

        List<DataRuleSysVO> dataRuleSys =StringUtility.jsonToList(dataJson.getString("lstDataRuleSys"),DataRuleSysVO.class);
        logger.info(String.format("dataRuleSys =%s",StringUtility.toJSONString(dataRuleSys)));
        if (CollectionUtils.isEmpty(dataRuleSys)) {
            throw new URCBizException("parameter lstDataRule is null", ErrorCode.E_000002);
        }

        //分批量操作
        List<DataRuleDO> dataBatchRuleIds = new ArrayList<DataRuleDO>();
        if (lstUserName != null && lstUserName.size() > 1) {
            for (String  userName : lstUserName) {
                DataRuleDO dataRuleDO = new DataRuleDO();
                dataRuleDO.setUserName(userName);
                DataRuleDO dataRule = dataRuleMapper.getDataRule(dataRuleDO);
                //记下dataRuleId
                dataBatchRuleIds.add(dataRule);
                if (dataRule != null && dataRule.getDataRuleId() != null) {
                    if (!CollectionUtils.isEmpty(dataRuleSys)) {
                        List<String> sysKeys = new ArrayList<>();
                        for (DataRuleSysVO dataRuleSysVo : dataRuleSys) {
                            sysKeys.add(dataRuleSysVo.getSysKey());
                            //增加创建时间
                            dataRuleSysVo.t = String.valueOf(System.currentTimeMillis());
                        }
                        dataRuleSysMapper.delRuleSysDatasByIdsAndSyskey(sysKeys, dataRule.getDataRuleId());
                    }
                }

            }
        } else {
        /*1、删除用户列表对应的数据权限 */
            List<Long> dataRuleIds = dataRuleMapper.getDataRuleIdsByUserName(lstUserName);
            dataRuleMapper.delBatchByUserNames(lstUserName);
                    /*2、删除用户列表对应的 数据权限Sys   行权限  列权限*/
            if (dataRuleIds != null && !dataRuleIds.isEmpty()) {
                dataRuleSysMapper.delRuleSysDatasByIdsAndCreatBy(dataRuleIds, null);
            }
        }

        List<DataRuleDO> dataRuleDOSCache = new ArrayList<>();
         /*数据权限Sys列表*/
        List<DataRuleSysDO> dataRuleSysCache = new ArrayList<>();
        /*列权限数据列表*/
        List<DataRuleColDO> dataRuleColCache = new ArrayList<>();
        /*行权限数据列表*/
        List<ExpressionDO> expressionCache = new ArrayList<>();
        //组装下发数据
        List<DataRuleVO> dataRuleVOS =new ArrayList<>();
        /*2、新增用户-操作权限关系数据 dataRule*/
        for (int i = 0; i < lstUserName.size(); i++) {
            // 组装 dataRuleVO
            DataRuleVO dataRuleVO = new DataRuleVO();
            dataRuleVO.t = String.valueOf(System.currentTimeMillis());
            dataRuleVO.lstDataRuleSys =dataRuleSys;
            dataRuleVO.userName =lstUserName.get(i);

            // 组装入库数据
            DataRuleDO dataRuleDO = new DataRuleDO();
            dataRuleDO.setCreateBy(operator);
            dataRuleDO.setCreateTime(new Date());
            Long dataRuleId = null;
            dataRuleDO.setUserName(lstUserName.get(i));
            if (dataBatchRuleIds.isEmpty() || dataBatchRuleIds.get(i) == null) {
                dataRuleId = seqBp.getNextDataRuleId();
                dataRuleDO.setDataRuleId(dataRuleId);
                dataRuleDOSCache.add(dataRuleDO);
            } else {
                dataRuleId = dataBatchRuleIds.get(i).getDataRuleId();
            }

            /*新增urc_data_rule_sys*/
         if (!CollectionUtils.isEmpty(dataRuleSys)) {
                assembleDataRuleSysDatas(dataRuleSysCache, dataRuleColCache, expressionCache, dataRuleSys, dataRuleId, operator);
            }
            // 组装下发消息
            dataRuleVOS.add(dataRuleVO);
        }
        /*批量新增用户-数据权限关系*/
        if (dataRuleDOSCache != null && !dataRuleDOSCache.isEmpty()) {
            dataRuleMapper.insertBatch(dataRuleDOSCache);
        }
         /*批量新增数据权限Sys*/
        if (dataRuleSysCache != null && !dataRuleSysCache.isEmpty()) {
            dataRuleSysMapper.insertBatch(dataRuleSysCache);
        }
             /*批量新增行权限数据*/
        if (expressionCache != null && !expressionCache.isEmpty()) {
            expressionMapper.insertBatch(expressionCache);
        }
            /*批量新增列权限数据*/
        if (dataRuleColCache != null && !dataRuleColCache.isEmpty()) {
            dataRuleColMapper.insertBatch(dataRuleColCache);
        }
        /*发送MQ*/
        sendToMq(dataRuleVOS);
        return VoHelper.getSuccessResult();
    }

    @Override
    public ResultVO getDataRuleByUser(List<String> lstUserName, String operator,String sysKey) {
        if (!roleMapper.isAdminOrSuperAdmin(operator)) {
            throw new URCBizException("既不是超级管理员也不是业务管理员", ErrorCode.E_100003);
        }

/*        if(StringUtility.isNullOrEmpty(sysKey)){
            return VoHelper.getSuccessResult((Object) "sysKey为空");
        }*/

        List<DataRuleVO> dataRuel = new ArrayList<DataRuleVO>();
        if (lstUserName != null && lstUserName.size() > 0) {
            for (int i = 0; i < lstUserName.size(); i++) {
                UserDO userDO = new UserDO();
                userDO.setUserName(lstUserName.get(i));
                List<String> sysKeys = new ArrayList<>();
               if (roleMapper.isAdminAccount(operator)) {
                    sysKeys = userRoleMapper.getSysKeyByUser(operator);
                }

                //sysKeys.add(sysKey);

                //通过用户名得到DataRuleSysId\SysKey\SysName
                List<DataRuleSysDO> syskeyList = dataRuleSysMapper.getDataRuleSysByUserName(userDO, sysKeys);
                List<DataRuleSysVO> lstDataRuleSys = new ArrayList<DataRuleSysVO>();
                if (syskeyList != null && syskeyList.size() > 0) {
                    for (int j = 0; j < syskeyList.size(); j++) {
                        //通过sysKey得到 行权限
                        List<ExpressionDO> expressionList = expressionMapper.listExpressionDOsBySysKey(syskeyList.get(j).getDataRuleSysId());
                        //通过sysKey得到列权限
                        List<DataRuleColDO> dataRuleColList = dataRuleColMapper.listRuleColBySysKey(syskeyList.get(j).getDataRuleSysId());
                        List<DataRuleColVO> dataRuleColVOList = new ArrayList<DataRuleColVO>();
                        for (DataRuleColDO colDO : dataRuleColList) {
                            DataRuleColVO dataRuleColVO = new DataRuleColVO();
                            if (!StringUtility.isNullOrEmpty(colDO.getEntityCode())) {
                                Entity entity = entityMapper.selectEntityByCode(colDO.getEntityCode());
                                if (entity != null) {
                                    dataRuleColVO.setEntityName(entity.getEntityName());
                                }
                            }

                            BeanUtils.copyProperties(colDO, dataRuleColVO);
                            dataRuleColVOList.add(dataRuleColVO);
                        }

                        List<ExpressionVO> expressionVOList = new ArrayList<ExpressionVO>();
                        ExpressionVO expressionVO = new ExpressionVO();
                        for (ExpressionDO expressionDO : expressionList) {
                            if (expressionDO.getParentExpressionId() == null) {
                                expressionVO.setIsAnd(1);
                                continue;
                            }
                            ExpressionVO expression = new ExpressionVO();
                            if (!StringUtility.isNullOrEmpty(expressionDO.getOperValues())) {
                                String operValues = expressionDO.getOperValues();
                                List<String> operValuesArr = StringUtility.jsonToList(operValues, String.class);
                                expression.setOperValuesArr(operValuesArr);
                            }
                            if (!StringUtility.isNullOrEmpty(expressionDO.getEntityCode())) {
                                Entity entity = entityMapper.selectEntityByCode(expressionDO.getEntityCode());
                                if (entity != null) {
                                    expressionDO.setEntityName(entity.getEntityName());
                                }
                            }

                            BeanUtils.copyProperties(expressionDO, expression);
                            expressionVOList.add(expression);
                        }

                        DataRuleSysVO dataRuleSysVO = new DataRuleSysVO();

                        expressionVO.setSubWhereClause(expressionVOList);
                        dataRuleSysVO.sysKey = syskeyList.get(j).getSysKey();
                        dataRuleSysVO.sysName = syskeyList.get(j).getSysName();
                        dataRuleSysVO.col = dataRuleColVOList;
                        dataRuleSysVO.row = expressionVO;
                        lstDataRuleSys.add(dataRuleSysVO);
                    }
                    DataRuleVO dataRuleVO = new DataRuleVO();
                    dataRuleVO.userName = lstUserName.get(i);
                    dataRuleVO.lstDataRuleSys = lstDataRuleSys;
                    dataRuel.add(dataRuleVO);
                }
            }
        }
        return VoHelper.getSuccessResult(dataRuel);
    }

    @Override
    public ResultVO<Integer> checkDuplicateTemplName(String operator, String newTemplName, String templId) {
        return VoHelper.getSuccessResult(dataRuleTemplMapper.checkDuplicateTemplName(newTemplName, templId) ? 1 : 0);

    }

    private List<DataRuleSysVO> getDataRuleVOByDataRuleSys(List<DataRuleSysDO> lstDrSysGt) {
        List<DataRuleSysVO> lstDataRuleSys = new ArrayList<DataRuleSysVO>();
        if (lstDrSysGt != null && lstDrSysGt.size() > 0) {
            for (int j = 0; j < lstDrSysGt.size(); j++) {
                //通过sysKey得到 行权限
                List<ExpressionDO> expressionList = expressionMapper.listExpressionDOsBySysKey(lstDrSysGt.get(j).getDataRuleSysId());
                //通过sysKey得到列权限
                List<DataRuleColDO> dataRuleColList = dataRuleColMapper.listRuleColBySysKey(lstDrSysGt.get(j).getDataRuleSysId());
                List<DataRuleColVO> dataRuleColVOList = new ArrayList<DataRuleColVO>();
                for (DataRuleColDO colDO : dataRuleColList) {
                    DataRuleColVO dataRuleColVO = new DataRuleColVO();
                    if (!StringUtility.isNullOrEmpty(colDO.getEntityCode())) {
                        Entity entity = entityMapper.selectEntityByCode(colDO.getEntityCode());
                        if (entity != null) {
                            dataRuleColVO.setEntityName(entity.getEntityName());
                        }
                    }
                    BeanUtils.copyProperties(colDO, dataRuleColVO);
                    dataRuleColVOList.add(dataRuleColVO);
                }
                List<ExpressionVO> expressionVOList = new ArrayList<ExpressionVO>();
                ExpressionVO expressionVO = new ExpressionVO();
                for (ExpressionDO expressionDO : expressionList) {
                    if (expressionDO.getParentExpressionId() == null) {
                        expressionVO.setIsAnd(1);
                        continue;
                    }
                    ExpressionVO expression = new ExpressionVO();
                    if (!StringUtility.isNullOrEmpty(expressionDO.getOperValues())) {
                        String operValues = expressionDO.getOperValues();
                        List<String> operValuesArr = StringUtility.jsonToList(operValues, String.class);
                        expression.setOperValuesArr(operValuesArr);
                    }
                    if (!StringUtility.isNullOrEmpty(expressionDO.getEntityCode())) {
                        Entity entity = entityMapper.selectEntityByCode(expressionDO.getEntityCode());
                        if (entity != null) {
                            expressionDO.setEntityName(entity.getEntityName());
                        }
                    }
                    BeanUtils.copyProperties(expressionDO, expression);
                    expressionVOList.add(expression);
                }
                DataRuleSysVO dataRuleSysVO = new DataRuleSysVO();
                expressionVO.setSubWhereClause(expressionVOList);
                dataRuleSysVO.createTime = lstDrSysGt.get(j).getCreateTime();
                dataRuleSysVO.userName = lstDrSysGt.get(j).getUserName();
                dataRuleSysVO.sysKey = lstDrSysGt.get(j).getSysKey();
                dataRuleSysVO.col = dataRuleColVOList;
                dataRuleSysVO.row = expressionVO;
                lstDataRuleSys.add(dataRuleSysVO);
            }
        }
        return lstDataRuleSys;
    }

    @Override
    public ResultVO<List<DataRuleSysVO>> getDataRuleGtDt(String sysKey, Date dt, Integer pageSize) {
        List<DataRuleSysDO> lstDrSysGt = dataRuleSysMapper.getDataRuleSysGtDt(sysKey, dt, pageSize == null ? 200 : pageSize);
        if (lstDrSysGt != null && lstDrSysGt.size() > 0) {
            // 查询等于最大时间的记录
            List<DataRuleSysDO> lstDrSysEq = dataRuleSysMapper.getDataRuleSysEqDt(sysKey, lstDrSysGt.get(lstDrSysGt.size() - 1).getCreateTime());
            lstDrSysEq.remove(0);
            lstDrSysGt.addAll(lstDrSysEq);
        }
        List<DataRuleSysVO> lstDr = getDataRuleVOByDataRuleSys(lstDrSysGt);
        return VoHelper.getSuccessResult(lstDr);
    }


    @Override
    @Transactional
    public ResultVO<List<OmsPlatformVO>> getPlatformShop(String operator, List<String> platformIds,String entityCode) {
        try {
            String redisResult =null;
            List<OmsPlatformVO> omsPlatformVOS = new ArrayList<>();
            //ebay 缓存
            if (StringUtility.stringEqualsIgnoreCase("E_PlsShopAccount",entityCode) ){
                 redisResult = cacheBp.getAllPlatformShop(Key_platform_shop,entityCode);
                //客服缓存
            }else if( StringUtility.stringEqualsIgnoreCase("E_CustomerService",entityCode)){
                //先从缓存取, 没有在从数据库读
                redisResult = cacheBp.getAllPlatformShop(Key_all_platform_shop,entityCode);
            }
            if (!StringUtility.isNullOrEmpty(redisResult)) {
                //转成vo  在转成json
                omsPlatformVOS = StringUtility.jsonToList(redisResult, OmsPlatformVO.class);
                return VoHelper.getResultVO(CommonMessageCodeEnum.SUCCESS.getCode(), CommonMessageCodeEnum.SUCCESS.getName(), omsPlatformVOS);
            }
            //缓存没有 从DB取
            return this.getAllPlatformShopFromDB(omsPlatformVOS,platformIds,entityCode);
        } catch (Exception e) {
            logger.error("获取平台账号站点失败", e);
            return VoHelper.getErrorResult();
        }
    }





    /**
     *  从DB 获取平台账号站点
     * @param
     * @return
     * @Author lwx
     * @Date 2018/9/4 17:05
     */
    public ResultVO getAllPlatformShopFromDB( List<OmsPlatformVO> omsPlatformVOS,List<String> platformIds,String entityCode){
        //返回所有平台和账号
        //获取所有平台
       // List<PlatformDO> platformDOS = platformMapper.selectAll();
        //获取一部分平台的数据
        List<PlatformDO> platformDOS =new ArrayList<>();
        platformDOS = platformMapper.selectPlatforms(platformIds);
        if (platformDOS != null && platformDOS.size() > 0) {
            for (PlatformDO platformDO : platformDOS) {
                if (StringUtility.isNullOrEmpty(platformDO.getPlatformId())) {
                    continue;
                }
                //装载平台
                OmsPlatformVO omsPlatformVO = new OmsPlatformVO();
                omsPlatformVO.platformId = platformDO.getPlatformId();
                omsPlatformVO.platformName = platformDO.getPlatformName();

                List<ShopSiteDO> shopSiteDOS  = shopSiteMapper.selectShopSite(platformDO.getPlatformId());
                if (shopSiteDOS == null || shopSiteDOS.size() == 0) {
                    continue;
                }
                omsPlatformVO.lstShop = new ArrayList<>();
                for (ShopSiteDO shopSiteDO : shopSiteDOS) {
                    if (StringUtility.isNullOrEmpty(shopSiteDO.getShopSystem())) {
                        continue;
                    }
                    //组装账号
                    OmsShopVO omsShopVO = new OmsShopVO();
                    omsShopVO.shopId = shopSiteDO.getShopSystem();
                    omsShopVO.shopName = shopSiteDO.getShop();
                    omsPlatformVO.lstShop.add(omsShopVO);
                    //客服系统, 需要站点
                    /*if (StringUtility.stringEqualsIgnoreCase(entityCode, "E_CustomerService")) {
                        if (StringUtility.isNullOrEmpty(shopSiteDO.getSiteId())) {
                            continue;
                        }
                        OmsSiteVO siteVO = new OmsSiteVO();
                        omsShopVO.lstSite =new ArrayList<>();
                        siteVO.siteId = shopSiteDO.getSiteId();
                        if (StringUtility.isNullOrEmpty(shopSiteDO.getSiteName())) {
                            siteVO.siteName = siteVO.siteId;
                        } else {
                            siteVO.siteName = shopSiteDO.getSiteName();
                        }
                        omsShopVO.lstSite.add(siteVO);
                    }*/
                }
                //组装平台
                omsPlatformVOS.add(omsPlatformVO);
            }
        }
        //放入缓存 , 只有刊登, 客服系统 放入缓存
        if (StringUtility.stringEqualsIgnoreCase("E_PlsShopAccount",entityCode) || StringUtility.stringEqualsIgnoreCase("E_CustomerService",entityCode)) {
            cacheBp.setAllPlatformShop(StringUtility.toJSONString(omsPlatformVOS),entityCode);
        }
        return VoHelper.getSuccessResult(omsPlatformVOS);
    }

    @Override
    public ResultVO<List<OmsPlatformVO>> appointPlatformShopSite(String operator, String platformId) {
        try {
            List<OmsPlatformVO> omsPlatformVOS = new ArrayList<>();
            OmsPlatformVO omsPlatformVO = new OmsPlatformVO();
            omsPlatformVO.platformId = platformId;
            omsPlatformVO.platformName = platformId;
            omsPlatformVO.lstShop = new ArrayList<>();
            List<ShopSiteDO> shopSiteDOS = shopSiteMapper.selectShopSiteByPlatformId(platformId);

            //组装账号
            for (ShopSiteDO shopSiteDO : shopSiteDOS) {
                if (StringUtility.isNullOrEmpty(shopSiteDO.getShopSystem())) {
                    continue;
                }
                OmsShopVO omsShopVO = new OmsShopVO();
                omsShopVO.shopId = shopSiteDO.getShopSystem();
                omsShopVO.shopName = shopSiteDO.getShop();
                omsPlatformVO.lstShop.add(omsShopVO);

                if (StringUtility.isNullOrEmpty(shopSiteDO.getSiteId() )) {

                    omsShopVO.lstSite = null;
                } else {
                    omsShopVO.lstSite = new ArrayList<>();
                    OmsSiteVO siteVO = new OmsSiteVO();
                    siteVO.siteId = shopSiteDO.getSiteId();

                    if (StringUtility.isNullOrEmpty(shopSiteDO.getSiteName())) {

                        siteVO.siteName = siteVO.siteId;
                    } else {
                        siteVO.siteName = shopSiteDO.getSiteName();
                    }
                    omsShopVO.lstSite.add(siteVO);
                }
            }
            //组装平台
            omsPlatformVOS.add(omsPlatformVO);
            return VoHelper.getSuccessResult(omsPlatformVOS);
        } catch (Exception e) {
            logger.error("未知异常", e);
            return VoHelper.getErrorResult();
        }
    }


    @Override
    public ResultVO<List<OmsPlatformVO>> appointPlatformShopSiteOms(String operator, String platformId) {
        try {
            List<OmsPlatformVO> omsPlatformVOS = new ArrayList<>();
            OmsPlatformVO omsPlatformVO = new OmsPlatformVO();
            omsPlatformVO.platformId = platformId;
            omsPlatformVO.platformName = platformId;
            omsPlatformVO.lstShop = new ArrayList<>();
            List<ShopSiteDO> shopSiteDOS = shopSiteMapper.selectShopSiteByPlatformId(platformId);

            //组装账号
            for (ShopSiteDO shopSiteDO : shopSiteDOS) {
                if (StringUtility.isNullOrEmpty(shopSiteDO.getShopSystem())) {
                    continue;
                }
                OmsShopVO omsShopVO = new OmsShopVO();
                omsShopVO.shopId = shopSiteDO.getShopSystem();
                omsShopVO.shopName = shopSiteDO.getShop();
                omsPlatformVO.lstShop.add(omsShopVO);

                if (StringUtility.isNullOrEmpty(shopSiteDO.getSiteId() )) {

                    omsShopVO.lstSite = null;
                } else {
                    omsShopVO.lstSite = new ArrayList<>();
                    OmsSiteVO siteVO = new OmsSiteVO();
                    siteVO.siteId = shopSiteDO.getSiteId();

                    if (StringUtility.isNullOrEmpty(shopSiteDO.getSiteName())) {

                        siteVO.siteName = siteVO.siteId;
                    } else {
                        siteVO.siteName = shopSiteDO.getSiteName();
                    }
                    omsShopVO.lstSite.add(siteVO);
                }
            }
            //组装平台
            omsPlatformVOS.add(omsPlatformVO);
            return VoHelper.getSuccessResult(omsPlatformVOS);
        } catch (Exception e) {
            logger.error("未知异常", e);
            return VoHelper.getErrorResult();
        }
    }


    private List<CsCodeNameVO> getCsChildren(){
        List<CsCodeNameVO> csCodeNameVOList=new ArrayList<>();
        CsCodeNameVO csCodeNameVO1=new CsCodeNameVO();
        csCodeNameVO1.code="ZZ";
        csCodeNameVO1.name="组长";
        csCodeNameVO1.type="0";
        CsCodeNameVO csCodeNameVO2=new CsCodeNameVO();
        csCodeNameVO2.code="ZY";
        csCodeNameVO2.name="组员";
        csCodeNameVO2.type="0";
        csCodeNameVOList.add(csCodeNameVO1);
        csCodeNameVOList.add(csCodeNameVO2);
        return  csCodeNameVOList;
    }

    private CsCodeNameVO getCsManager(){
        CsCodeNameVO csCodeNameVO1=new CsCodeNameVO();
        csCodeNameVO1.code="PM";
        csCodeNameVO1.name="经理";
        csCodeNameVO1.type="0";
        return  csCodeNameVO1;
    }


    @Override
    public ResultVO getCsPlatformCodeName(String operator) {
        List<CsPlatform> csPlatformGroupList=  csPlatformMapper.selectAllInfo();
        if(CollectionUtils.isEmpty(csPlatformGroupList)) {
            return VoHelper.getSuccessResult();
        }
        List<CsCodeNameVO> csCodeNameVOList=new ArrayList<>();
        for (CsPlatform csPlatform:csPlatformGroupList){
            CsCodeNameVO csCodeNameVO=new CsCodeNameVO();
            csCodeNameVO.code=csPlatform.getPlatformId();
            csCodeNameVO.name=csPlatform.getPlatformName();
            csCodeNameVO.type="1";
            List<CsCodeNameVO> csCodeNameVOSChildren=new ArrayList<>();
            csCodeNameVOSChildren.add(getCsManager());
            List<CsPlatformGroup> csPlatformGroupDataList= csPlatformGroupMapper.selectByPlantformId(csPlatform.getPlatformId());
            if(!CollectionUtils.isEmpty(csPlatformGroupDataList)){
                for (CsPlatformGroup csPlatformGroupData:csPlatformGroupDataList){
                    CsCodeNameVO csCodeNameVOData =csCodeNameVOData=new CsCodeNameVO();
                    csCodeNameVOData.code=String.valueOf(csPlatformGroupData.getGroupId());
                    csCodeNameVOData.name=csPlatformGroupData.getGroupName();
                    csCodeNameVOData.type="1";
                    csCodeNameVOData.children=getCsChildren();
                    csCodeNameVOSChildren.add(csCodeNameVOData);
                }
            }
            csCodeNameVO.children=csCodeNameVOSChildren;
            csCodeNameVOList.add(csCodeNameVO);
        }
        return VoHelper.getSuccessResult(csCodeNameVOList);
    }

    @Override
    public ResultVO getPlatFormForLogistics(List<PlatformDO> platformDOS) {
            List<OmsPlatformVO> omsPlatformVOList = new ArrayList<>();
        try {
            if (platformDOS != null && platformDOS.size() > 0) {
                platformDOS.forEach(platformDO -> {
                    OmsPlatformVO omsPlatformVO=new OmsPlatformVO();
                    omsPlatformVO.platformId=platformDO.getPlatformId();
                    omsPlatformVO.platformName=platformDO.getPlatformName();
                    omsPlatformVOList.add(omsPlatformVO);
                });
            }
        }catch (Exception e){
            logger.error("Handle data exception",e);
            return VoHelper.getResultVO(CommonMessageCodeEnum.HANDLE_DATA_EXCEPTION.getCode(),"处理数据异常",new ArrayList<>());
        }
        return VoHelper.getSuccessResult(omsPlatformVOList);
    }

    @Override
    public ResultVO getPlatformShopByEntityCode(String operator, String entityCode) {
        List<String> platformIds = new ArrayList<>();
        if (StringUtility.isNullOrEmpty(entityCode)) {
            return VoHelper.getErrorResult(CommonMessageCodeEnum.FAIL.getCode(), "entityCode为空");
        }
        //根据entityCode找到对应得platforid
        if (entityCode.equalsIgnoreCase("E_PlatformShopSite")) {
            //通过调oms的接口获取销售账号
            return getSellerIdByOmsInterface();
//            return dataRuleService.appointPlatformShopSiteOms(operator, "速卖通");
        } else if (entityCode.equalsIgnoreCase("E_ArmShopAccount")) {
            //索赔-->亚马逊 只需要账号
            platformIds.add("亚马逊");
            return dataRuleService.getPlatformShop(operator, platformIds,entityCode);
        } else if (entityCode.equalsIgnoreCase("E_PlsShopAccount")) {
            // 刊登--->ebyay 只需要账号, 目前返回这4个平台
            platformIds.add("shopee");
            platformIds.add("ebay");
            platformIds.add("亚马逊");
            platformIds.add("lazada");
            platformIds.add("速卖通");
            return dataRuleService.getPlatformShop(operator, platformIds,entityCode);
        } else if (entityCode.equalsIgnoreCase("E_CustomerService")) {
            //客服系统 --> 平台账号  站点
            List<PlatformDO> platformDOS = platformMapper.selectAll();
            if (platformDOS != null && platformDOS.size() > 0) {
                platformIds = platformDOS.stream().map(platformDO -> platformDO.getPlatformId()).collect(Collectors.toList());
            }
            return dataRuleService.getPlatformShop(operator, platformIds,entityCode);
        } else  if(entityCode.equalsIgnoreCase("E_CsOrg")){
            //客服--【职级】---范围
            return  dataRuleService.getCsPlatformCodeName(operator);
        } else  if(entityCode.equalsIgnoreCase("E_Logistics")){
            //物流--【平台】
            List<PlatformDO> platformDOS = platformMapper.selectAll();
            return  dataRuleService.getPlatFormForLogistics(platformDOS);
        }else {
            //待定后续....
            return VoHelper.getSuccessResult((Object) "待配置!......");
        }

    }

    private ResultVO getSellerIdByOmsInterface() {
        ResultVO resultVO = null;
        try{
            //oms
           resultVO = orderManageService.getAllSellerId();
        }catch (Exception e){
            logger.error("throw Exception when retrieve oms interface ",e);
            return VoHelper.getResultVO(CommonMessageCodeEnum.FAIL.getCode(),"调取oms接口获取销售账号出错",new ArrayList<>());
        }
        List<OmsPlatformVO> omsPlatformVOList = new ArrayList<>();
        try{
            //组装数据
            JSONArray data = (JSONArray) resultVO.data;
            if(data != null && data.size() > 0){
                for (int i=0; i < data.size(); i++) {
                    OmsPlatformVO omsPlatformVO = new OmsPlatformVO();
                    List<OmsShopVO> omsShopVOList = new ArrayList<>();
                    JSONObject jsonObject = (JSONObject) data.get(i);
                    omsPlatformVO.platformName = jsonObject.getString("platformName");
                    omsPlatformVO.platformId = jsonObject.getString("platformCode");
                    List lstSellerId = StringUtility.jsonToList(jsonObject.getString("lstSellerId"),String.class) ;
                    if(lstSellerId != null && lstSellerId.size() > 0){
                        for (int i1=0; i1 < lstSellerId.size(); i1++) {
                            OmsShopVO omsShopVO = new OmsShopVO();
                            omsShopVO.shopName = (String) lstSellerId.get(i1);
                            omsShopVO.shopId = omsShopVO.shopName;
                            omsShopVOList.add(omsShopVO);
                        }
                    }
                    omsPlatformVO.lstShop = omsShopVOList;
                    omsPlatformVOList.add(omsPlatformVO);
                }
            }

        }catch (Exception e){
            logger.error("Handle data Exception",e);
            return VoHelper.getResultVO(CommonMessageCodeEnum.HANDLE_DATA_EXCEPTION.getCode(),"处理数据异常",new ArrayList<>());
        }
        return VoHelper.getSuccessResult(omsPlatformVOList);
    }
}
