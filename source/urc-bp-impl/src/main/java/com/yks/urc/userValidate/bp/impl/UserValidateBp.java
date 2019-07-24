package com.yks.urc.userValidate.bp.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yks.common.enums.CommonMessageCodeEnum;
import com.yks.common.util.StringUtil;
import com.yks.urc.cache.bp.api.ICacheBp;
import com.yks.urc.entity.IUrcWhiteApiVO;
import com.yks.urc.entity.PermissionDO;
import com.yks.urc.entity.UserLoginLogDO;
import com.yks.urc.entity.UserPermitStatDO;
import com.yks.urc.entity.UserTicketDO;
import com.yks.urc.exception.ErrorCode;
import com.yks.urc.exception.URCBizException;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.fw.constant.StringConstant;
import com.yks.urc.mapper.IRoleMapper;
import com.yks.urc.mapper.IUrcWhiteApiUrlMapper;
import com.yks.urc.mapper.PermissionMapper;
import com.yks.urc.mapper.UserTicketMapper;
import com.yks.urc.operation.bp.api.IOperationBp;
import com.yks.urc.permitStat.bp.api.IPermitStatBp;
import com.yks.urc.session.bp.api.ISessionBp;
import com.yks.urc.user.bp.api.IUserLogBp;
import com.yks.urc.userValidate.bp.api.ITicketUpdateBp;
import com.yks.urc.userValidate.bp.api.IUserValidateBp;
import com.yks.urc.vo.FunctionVO;
import com.yks.urc.vo.GetAllFuncPermitRespVO;
import com.yks.urc.vo.MenuVO;
import com.yks.urc.vo.ModuleVO;
import com.yks.urc.vo.ResultVO;
import com.yks.urc.vo.SystemRootVO;
import com.yks.urc.vo.UserVO;
import com.yks.urc.vo.helper.VoHelper;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserValidateBp implements IUserValidateBp {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IRoleMapper roleMapper;
    @Autowired
    ICacheBp cacheBp;
    @Autowired
    private IUrcWhiteApiUrlMapper urcWhiteApiUrlMapper;

    @Autowired
    IOperationBp operationBp;
    @Autowired
    private ISessionBp sessionBp;

    @Override
    public List<String> getFuncJsonLstByUserAndSysKey(String userName, String sysKey) {
        return roleMapper.getFuncJsonByUserAndSysKey(userName, sysKey);
    }

    /**
     * 计算funcVersion
     *
     * @param strFuncJson
     * @return
     * @author panyun@youkeshu.com
     * @date 2018年6月12日 下午3:56:19
     */
    @Override
    public String calcFuncVersion(String strFuncJson) {
        return StringUtility.md5_NoException(strFuncJson);
    }

    /**
     * 打平所有module
     *
     * @param sys1
     * @param userName
     * @author panyun@youkeshu.com
     * @date 2018年6月12日 下午7:22:38
     */
    @Override
    public List<UserPermitStatDO> plainSys(SystemRootVO sys1, String userName) {
        if (sys1 == null) return Collections.emptyList();
        List<MenuVO> lstMenu = sys1.menu;

        List<ModuleVO> lstModuleRslt = new ArrayList<>();
        for (MenuVO menu : lstMenu) {
            List<ModuleVO> lstModule = menu.module;
            if (lstModule == null)
                continue;
            for (ModuleVO m : lstModule) {
                m.pageFullPathName.append(m.name);
                m.lstChildFunc = getChildrenFuncDesc(m);
                m.sysKey = sys1.system.key;
                lstModuleRslt.add(m);
                plainModule(m, lstModuleRslt);
            }
        }

        List<UserPermitStatDO> lstRslt = new ArrayList<>();
        for (ModuleVO m : lstModuleRslt) {
            UserPermitStatDO statDo = new UserPermitStatDO();
            statDo.setUserName(userName);
            statDo.setSysKey(sys1.system.key);
            statDo.setModuleName(m.pageFullPathName.toString());
            statDo.setFuncJson(StringUtility.toJSONString_NoException(m.lstChildFunc));
            // System.out.println(m.sysKey + " " + m.pageFullPathName + " " +
            // StringUtility.toJSONString_NoException(m.lstChildFunc));
            lstRslt.add(statDo);
        }

        return lstRslt;
    }

    /**
     * 按module节点打平
     *
     * @param m
     * @param lstModuleRslt
     * @author panyun@youkeshu.com
     * @date 2018年6月12日 下午7:21:54
     */
    private void plainModule(ModuleVO m, List<ModuleVO> lstModuleRslt) {
        if (m.module != null) {
            for (ModuleVO mem : m.module) {
                // if (mem.pageFullPathName == null)
                // mem.pageFullPathName = new StringBuilder();
                mem.pageFullPathName.append(m.pageFullPathName);
                mem.pageFullPathName.append("/");
                mem.pageFullPathName.append(mem.name);
                mem.lstChildFunc = getChildrenFuncDesc(mem);
                mem.sysKey = m.sysKey;
                lstModuleRslt.add(mem);
                plainModule(mem, lstModuleRslt);
            }
        }
    }

    /**
     * 获取module所有子级function name
     *
     * @param mem
     * @return
     * @author panyun@youkeshu.com
     * @date 2018年6月12日 下午7:09:28
     */
    private List<String> getChildrenFuncDesc(ModuleVO mem) {
        if (mem == null || mem.function == null || mem.function.size() == 0)
            return null;
        List<String> sbFunc = new ArrayList();
        for (FunctionVO f : mem.function) {
            calcFuncDesc(f, sbFunc);
        }
        return sbFunc;
    }

    /**
     * 递归查询所有function name
     *
     * @param f
     * @param sbRslt
     * @author panyun@youkeshu.com
     * @date 2018年6月12日 下午7:09:00
     */
    private void calcFuncDesc(FunctionVO f, List<String> sbRslt) {
        sbRslt.add(f.name);
        if (f.function != null) {
            for (FunctionVO fMem : f.function) {
                calcFuncDesc(fMem, sbRslt);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        // 读取func1.json文件
        String strJson1 = StringUtility.inputStream2String(ClassLoader.getSystemResourceAsStream("func1.json"));
        String strJson2 = StringUtility.inputStream2String(ClassLoader.getSystemResourceAsStream("func2.json"));
        System.out.println(new UserValidateBp().cleanDeletedNode(strJson1, strJson2));

        // SystemRootVO sys1 = StringUtility.parseObject(strJson1, SystemRootVO.class);
        // System.out.println(StringUtility.toJSONString_NoException(sys1));
        // new UserValidateBp().plainSys(sys1, "panyun");

        // 读取func2.json文件
        // String strJson2 =
        // StringUtility.inputStream2String(ClassLoader.getSystemResourceAsStream("func2.json"));
        // SystemRootVO sys2 = StringUtility.parseObject(strJson2, SystemRootVO.class);
        //
        // System.out.println("合并前的sys1:" +
        // StringUtility.toJSONString_NoException(sys1));
        // // sys2中的功能权限合并到sys1中
        // distinctSystemRootVO(sys1, sys2);
        // System.out.println("合并后的sys1:" +
        // StringUtility.toJSONString_NoException(sys1));
    }

    /**
     * 功能权限json清理：将old与newest对比，删除old中存在但newest不存在的node删除
     *
     * @param strFuncJsonOld
     * @param strFuncJsonNewest
     * @return
     * @author panyun@youkeshu.com
     * @date 2018年6月15日 上午8:26:43
     */
    @Override
    public String cleanDeletedNode(String strFuncJsonOld, String strFuncJsonNewest) {
        SystemRootVO sysOld = StringUtility.parseObject(strFuncJsonOld, SystemRootVO.class);
        SystemRootVO sysNewest = StringUtility.parseObject(strFuncJsonNewest, SystemRootVO.class);

        // 将newest中的所有key存入List
        List<String> lstNewestKey = new ArrayList<>();
        if (sysNewest.menu != null) {
            for (MenuVO m : sysNewest.menu) {
                lstNewestKey.add(m.key);
                searchModuleKey(m.module, lstNewestKey);
            }
        }

        // 若old中的key在List中不存在，则删除
        // 判断 menu是否存在,不存在则删除
        if (sysOld.menu != null) {
            for (int i = 0; i < sysOld.menu.size(); i++) {
                if (lstNewestKey.contains(sysOld.menu.get(i).key))
                    continue;
                // 删除menu
                sysOld.menu.remove(i);
                i--;
            }
        }

        for (MenuVO m : sysOld.menu) {
            cleanDeletedModule(m.module, lstNewestKey);
        }

        return StringUtility.toJSONString_NoException(sysOld);
    }

    /**
     * 递归删除module下不存在的子node
     *
     * @param lstModule
     * @param lstNewestKey
     * @author panyun@youkeshu.com
     * @date 2018年6月15日 上午8:53:44
     */
    private void cleanDeletedModule(List<ModuleVO> lstModule, List<String> lstNewestKey) {
        if (lstModule == null || lstNewestKey == null)
            return;
        for (int i = 0; i < lstModule.size(); i++) {
            if (lstNewestKey.contains(lstModule.get(i).key)) {
                // 递归删除module下不存在的子node
                cleanDeletedModule(lstModule.get(i), lstNewestKey);
                continue;
            }
            // 删除module
            lstModule.remove(i);
            i--;
        }
    }

    /**
     * 递归删除module下不存在的子node
     *
     * @param moduleVO
     * @param lstNewestKey
     * @author panyun@youkeshu.com
     * @date 2018年6月15日 上午8:46:27
     */
    private void cleanDeletedModule(ModuleVO moduleVO, List<String> lstNewestKey) {
        if (moduleVO == null || lstNewestKey == null)
            return;

        cleanDeletedModule(moduleVO.module, lstNewestKey);
        cleanDeletedFunction(moduleVO.function, lstNewestKey);
    }

    /**
     * 递归删除function下不存在的子node
     *
     * @param lstFunc
     * @param lstNewestKey
     * @author panyun@youkeshu.com
     * @date 2018年6月15日 上午8:53:52
     */
    private void cleanDeletedFunction(List<FunctionVO> lstFunc, List<String> lstNewestKey) {
        if (lstFunc == null || lstNewestKey == null)
            return;
        for (int i = 0; i < lstFunc.size(); i++) {
            if (lstNewestKey.contains(lstFunc.get(i).key)) {
                cleanDeletedFunction(lstFunc.get(i).function, lstNewestKey);
                continue;
            }
            lstFunc.remove(i);
            i--;
        }
    }

    /**
     * 递归查找module的key
     *
     * @param lstModule
     * @param lstNewestKey
     * @author panyun@youkeshu.com
     * @date 2018年6月15日 上午8:37:41
     */
    private void searchModuleKey(List<ModuleVO> lstModule, List<String> lstNewestKey) {
        if (lstModule != null) {
            for (ModuleVO module : lstModule) {
                lstNewestKey.add(module.key);
                searchModuleKey(module.module, lstNewestKey);
                searchFunctionKey(module.function, lstNewestKey);
            }
        }
    }

    /**
     * 递归查找function的key
     *
     * @param lstFunc
     * @param lstNewestKey
     * @author panyun@youkeshu.com
     * @date 2018年6月15日 上午8:37:28
     */
    private void searchFunctionKey(List<FunctionVO> lstFunc, List<String> lstNewestKey) {
        if (lstFunc == null)
            return;
        for (FunctionVO f : lstFunc) {
            lstNewestKey.add(f.key);
            if (f.function != null) {
                searchFunctionKey(f.function, lstNewestKey);
            }
        }
    }

    /**
     * sys2中的功能权限合并到sys1中
     *
     * @param sys1
     * @param sys2
     * @author panyun@youkeshu.com
     * @date 2018年5月26日 上午11:51:59
     */
    private static void distinctSystemRootVO(SystemRootVO sys1, SystemRootVO sys2) {
        if (sys1.menu == null)
            sys1.menu = new ArrayList<>();
        if (sys2.menu == null)
            sys2.menu = new ArrayList<>();
        List<MenuVO> menu1 = sys1.menu;
        List<MenuVO> menu2 = sys2.menu;

        for (int j = 0; j < menu2.size(); j++) {
            boolean hasSameMenu = false;
            for (int i = 0; i < menu1.size(); i++) {
                if (StringUtility.stringEqualsIgnoreCase(menu1.get(i).key, menu2.get(j).key)) {
                    // 合并相同menu
                    distinctMenu(menu1.get(i), menu2.get(j));
                    hasSameMenu = true;
                    break;
                }
            }

            if (hasSameMenu) {
                // 有相同menu,前面已经做了menu合并,menu2删除一个元素
                menu2.remove(j);
                j--;
            } else {
                // 没有相同menu,menu2中的添加到menu1中，并删除menu2中当前元素
                menu1.add(menu2.get(j));
                menu2.remove(j);
                j--;
            }
        }
    }

    private static void distinctMenu(MenuVO menu1, MenuVO menu2) {
        // 合并menu下的page
        if (menu1.module == null)
            menu1.module = new ArrayList<>();
        if (menu2.module == null)
            menu2.module = new ArrayList<>();

        List<ModuleVO> page1 = menu1.module;
        List<ModuleVO> page2 = menu2.module;
        distinctPages(page1, page2);
    }

    private static void distinctPages(List<ModuleVO> page1, List<ModuleVO> page2) {
        if (page1 == null)
            page1 = new ArrayList<>();
        if (page2 == null)
            page2 = new ArrayList<>();
        for (int j = 0; j < page2.size(); j++) {
            boolean hasSameMenu = false;
            for (int i = 0; i < page1.size(); i++) {
                if (StringUtility.stringEqualsIgnoreCase(page1.get(i).key, page2.get(j).key)) {
                    // 合并相同page
                    distinctPage(page1.get(i), page2.get(j));
                    hasSameMenu = true;
                    break;
                }
            }

            if (hasSameMenu) {
                // 有相同page,前面已经做了page合并,page2删除一个元素
                page2.remove(j);
                j--;
            } else {
                // 没有相同page,page2中的添加到page1中，并删除page2中当前元素
                page1.add(page2.get(j));
                page2.remove(j);
                j--;
            }
        }
    }

    private static void distinctPage(ModuleVO page1, ModuleVO page2) {
        // 合并page下的pages
        distinctPages(page1.module, page2.module);
        // 合并page下的functions
        distinctFunctions(page1.function, page2.function);
    }

    private static void distinctFunctions(List<FunctionVO> functions1, List<FunctionVO> functions2) {
        if (functions1 == null)
            functions1 = new ArrayList<>();
        if (functions2 == null)
            functions2 = new ArrayList<>();
        for (int j = 0; j < functions2.size(); j++) {
            boolean hasSameMenu = false;
            for (int i = 0; i < functions1.size(); i++) {
                if (StringUtility.stringEqualsIgnoreCase(functions1.get(i).key, functions2.get(j).key)) {
                    // 合并相同functions
                    distinctFunction(functions1.get(i), functions2.get(j));
                    hasSameMenu = true;
                    break;
                }
            }

            if (hasSameMenu) {
                // 有相同page,前面已经做了page合并,page2删除一个元素
                functions2.remove(j);
                j--;
            } else {
                // 没有相同page,page2中的添加到page1中，并删除page2中当前元素
                functions1.add(functions2.get(j));
                functions2.remove(j);
                j--;
            }
        }
    }

    private static void distinctFunction(FunctionVO function1, FunctionVO function2) {
        // 合并function下的functions
        distinctFunctions(function1.function, function2.function);
    }

    @Override
    public String createTicket(String strUserName) {
        return StringUtility.md5_NoException(String.format("%s%s", strUserName, StringUtility.getUUIDLowercase_Dt()));

    }

    @Override
    public String getFuncJsonByUserAndSysKey(String userName, String sysKey) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String mergeFuncJson(List<String> lstJson) {
        return StringUtility.toJSONString_NoException(mergeFuncJson2Obj(lstJson));
    }

    @Override
    public SystemRootVO mergeFuncJson2Obj(List<String> lstJson) {
        if (lstJson == null || lstJson.size() == 0)
            return null;
        List<SystemRootVO> lstSysRootVO = new ArrayList<>();
        for (String mem : lstJson) {
            SystemRootVO sys1 = StringUtility.parseObject(mem, SystemRootVO.class);
            if (sys1 != null) lstSysRootVO.add(sys1);
        }
        if (lstSysRootVO.size() == 0) return null;
        SystemRootVO sys1 = lstSysRootVO.get(0);
        for (int i = 1; i < lstSysRootVO.size(); i++) {
            // sys2中的功能权限合并到sys1中
            SystemRootVO sys2 = lstSysRootVO.get(i);
            distinctSystemRootVO(sys1, sys2);
        }
        return sys1;
    }

   /* private List<String> lstWhiteApiUrl = Arrays.asList("/urc/motan/service/api/IUrcService/getAllFuncPermit",
            "/urc/motan/service/api/IUrcService/logout");*/

    private List<String> lstWhiteApiUrl() {
        List<String> whiteApi = new ArrayList<>();
        String whiteApiCash = cacheBp.getWhiteApi("api");
        if (whiteApiCash == null) {
            whiteApi = urcWhiteApiUrlMapper.selectWhiteApi();
            if (whiteApi == null) {
                whiteApi = new ArrayList<>();
            }
        } else {
            whiteApi = StringUtility.jsonToList(whiteApiCash, String.class);
        }

        return whiteApi;

    }


    @Autowired
    private IUserLogBp userLogBp;
    @Autowired
    private UserTicketMapper userTicketMapper;

    @Autowired
    private ITicketUpdateBp ticketUpdateBp;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

    @Override
    public ResultVO funcPermitValidate(Map<String, String> map) {
        logger.info(String.format("funcPermitValidate:%s", StringUtility.toJSONString_NoException(map)));
        try {
            String apiUrl = map.get("apiUrl");
            String moduleUrl = map.get("moduleUrl");
            String operator = map.get(StringConstant.operator);
            if (StringUtility.isNullOrEmpty(operator)) {
                return VoHelper.getResultVO("100002", "登录超时:operator参数为空");
            }
            String ticket = map.get(StringConstant.ticket);
            String urcVersion = map.get(StringConstant.funcVersion);
            UserVO u = cacheBp.getUser(operator);
            // 校验ticket
            UserLoginLogDO loginLogDO = new UserLoginLogDO();
            loginLogDO.userName = operator;
            loginLogDO.createTime = new Date();
            loginLogDO.modifiedTime = new Date();
            String loginTimeString = "";
            UserTicketDO userTicketDO;
            if (u == null) {
                //如果缓存的用户信息为null 从数据库里获取用户ticket信息
                userTicketDO = userTicketMapper.selectUserTicketByUserName(operator);
                if (StringUtil.isEmpty(userTicketDO)) {
                    //数据库也没有此用户的ticket信息
                    loginLogDO.remark = String.format("funcPermitValidate,request:[%s],此次的ticket:[%s];(数据库没有数据)", StringUtility.toJSONString(map), ticket);
                    userLogBp.insertLog(loginLogDO);
                    logger.error(String.format("funcPermitValidate login timeout request = %s", StringUtility.toJSONString(map)));
                    return VoHelper.getResultVO("100002", "登录超时:用户信息为空");
                }
                //校验设备
                ResultVO deviceChange = checkDeviceFromDB(userTicketDO, loginLogDO, loginTimeString, ticket);
                if (deviceChange != null) {
                    return deviceChange;
                }
                Date now = new Date();
                //如果ticket没过期且缓存为null 则更新缓存
                if (now.before(userTicketDO.getExpiredTime())) {
                    UserVO userVO = new UserVO();
                    userVO.ticket = userTicketDO.getTicket();
                    userVO.userName = userTicketDO.getUserName();
                    userVO.loginTime = userTicketDO.getModifiedTime().getTime();
                    userVO.ip = map.get(StringConstant.ip);
                    userVO.deviceName = map.get(StringConstant.deviceName);
                    cacheBp.insertUser(userVO);
                }
                //根据过期时间ExpiredTime来判断ticket是否过期
                if (now.after(userTicketDO.getExpiredTime())) {
                    // 100002
                    loginLogDO.remark = String.format("funcPermitValidate ,request:[%s],此次的ticket:[%s];(从数据库中获取的ticket信息:[%s])", StringUtility.toJSONString(map), ticket, StringUtility.toJSONString(userTicketDO.getTicket()));
                    userLogBp.insertLog(loginLogDO);
                    logger.error(String.format("funcPermitValidate login timeout request = %s ,ticket =%s;(从数据库中获取的ticket信息:[%s])", StringUtility.toJSONString(map), ticket, StringUtility.toJSONString(userTicketDO.getTicket())));
                    return VoHelper.getResultVO("100002", "登录超时:ticket已过期");
                }

            } else {
                //校验设备
                ResultVO deviceChange = checkDeviceFromCache(u, loginLogDO, loginTimeString, ticket);
                if (deviceChange != null) {
                    return deviceChange;
                }
                //校验ticket
//				if(!StringUtility.stringEqualsIgnoreCase(u.ticket, ticket)) {
//					// 缓存不为null
//					loginLogDO.remark=String.format("funcPermitValidate ,request:[%s],此次的ticket:[%s]};从redis中获取的ticket信息:[%s]", StringUtility.toJSONString(map), ticket, StringUtility.toJSONString(u.ticket));
//					userLogBp.insertLog(loginLogDO);
//					logger.error(String.format("funcPermitValidate login timeout request = %s ,ticket =%s;从redis中获取的ticket信息:[%s]", StringUtility.toJSONString(map), ticket, StringUtility.toJSONString(u.ticket)));
//					return VoHelper.getResultVO("100002", "登录超时:ticket已过期");
//				}

            }
            // 刷新数据库ticket过期时间
            ticketUpdateBp.refreshExpiredTime(operator, ticket);

         /*   if (lstWhiteApiUrl.contains(apiUrl)) {
                return VoHelper.getResultVO(StringConstant.STATE_100006, "用户功能权限版本正确");
            }*/
            if (this.lstWhiteApiUrl().contains(apiUrl)) {
                return VoHelper.getResultVO(StringConstant.STATE_100006, "用户功能权限版本正确(请求api为白名单)");
            }

            // 校验功能权限版本
            String newFuncVersion = getFuncVersionFromDbOrCache(operator);
            if (!StringUtility.stringEqualsIgnoreCase(urcVersion, newFuncVersion)) {
                Map<String, String> dataMap = new HashMap<>();
                dataMap.put("newFuncVersion", newFuncVersion);
                logger.error(String.format("funcPermitValidate func error  request =%s ,newFuncVersion =%s", StringUtility.toJSONString(map), newFuncVersion));
                return VoHelper.getResultVO("100007", "功能权限版本错误", dataMap);
            }

            // 校验是否有权限
            String sysKey = getSysKeyByApiUrl(apiUrl);
            if (StringUtility.isNullOrEmpty(sysKey)) {
                return VoHelper.getResultVO(ErrorCode.E_100007, String.format("%s don't belong to any system", apiUrl));
            }
            if (!hasApiFunc(moduleUrl, apiUrl, operator, sysKey)) {
                return VoHelper.getResultVO(ErrorCode.E_100003, "没有权限");
            }
            return VoHelper.getResultVO(StringConstant.STATE_100006, "用户功能权限版本正确");
        } catch (Exception e) {
            logger.error("权限校验异常,原因为", e);
            throw new URCBizException(ErrorCode.E_000008.getState(), "权限校验异常");
        }
    }

    @Nullable
    private ResultVO checkDeviceFromCache(UserVO u, UserLoginLogDO loginLogDO, String loginTimeString, String ticket) {
        if (u.loginTime != null) {
            loginTimeString = simpleDateFormat.format(u.loginTime);
        }
        if (!StringUtility.stringEqualsIgnoreCase(u.ticket, ticket)) {
            loginLogDO.remark = String.format("您的账号于%s 在另一设备（%s;IP:%s）登录成功，请重新登录并检查您的账号密码是否泄漏，并及时修改密码", loginTimeString, u.deviceName, u.ip);
            userLogBp.insertLog(loginLogDO);
            logger.info(String.format("Your account has been successfully logged in to another device (%s;IP:%s) at :%s. Please log in again and check whether your account password has been leaked, and modify the password in time。", u.deviceName, u.ip, loginTimeString));
            return VoHelper.getResultVO("101003", String.format("您的账号于%s 在另一设备（%s;IP:%s）登录成功，请重新登录并检查您的账号密码是否泄漏，并及时", loginTimeString, u.deviceName, u.ip));
        }
        return null;
    }

    @Nullable
    private ResultVO checkDeviceFromDB(UserTicketDO u, UserLoginLogDO loginLogDO, String loginTimeString, String ticket) {
        if (u.getLoginTime() != null) {
            loginTimeString = simpleDateFormat.format(u.getLoginTime());
        }
        if (!StringUtility.stringEqualsIgnoreCase(u.getTicket(), ticket)) {
            loginLogDO.remark = String.format("您的账号于%s 在另一设备（%s;IP:%s）登录成功，请重新登录并检查您的账号密码是否泄漏，并及时修改密码", loginTimeString, u.getDeviceName(), u.getLoginIp());
            userLogBp.insertLog(loginLogDO);
            logger.info(String.format("Your account has been successfully logged in to another device (%s;IP:%s) at :%s. Please log in again and check whether your account password has been leaked, and modify the password in time。", u.getDeviceName(), u.getLoginIp(), loginTimeString));
            return VoHelper.getResultVO("101003", String.format("您的账号于%s 在另一设备（%s;IP:%s）登录成功，请重新登录并检查您的账号密码是否泄漏，并及时", loginTimeString, u.getDeviceName(), u.getLoginIp()));
        }
        return null;
    }

    /**
     * 获取apiUrl是哪个sysKey
     *
     * @param strApiUrl
     * @return
     * @author panyun@youkeshu.com
     * @date 2018年6月25日 下午2:30:19
     */
    private String getSysKeyByApiUrl(String strApiUrl) {
        if (StringUtility.isNullOrEmpty(strApiUrl))
            return StringUtility.Empty;
        List<PermissionDO> lstApiPrefix = cacheBp.getSysApiUrlPrefix();
        if (lstApiPrefix == null) {
            lstApiPrefix = permissionMapper.getSysApiUrlPrefix();
            cacheBp.setSysApiUrlPrefix(lstApiPrefix);
        }
        if (lstApiPrefix == null || lstApiPrefix.size() == 0)
            return StringUtility.Empty;

        for (PermissionDO p : lstApiPrefix) {
            String[] arrPrefix = StringUtility.parseObject(p.getApiUrlPrefixJson(), arrEmpty.getClass());
            if (arrPrefix == null || arrPrefix.length == 0)
                continue;
            for (String prefix : arrPrefix) {
                if (strApiUrl.startsWith(prefix))
                    return p.getSysKey();
            }
        }
        return StringUtility.Empty;
    }

    private String[] arrEmpty = new String[0];

    @Autowired
    private IPermitStatBp permitStatBp;

    /**
     * 从db或cache获取funcVersion
     *
     * @param userName
     * @param
     * @return
     * @author panyun@youkeshu.com
     * @date 2018年6月14日 下午4:43:27
     */
    public String getFuncVersionFromDbOrCache(String userName) {
        String funcVersion = cacheBp.getFuncVersion(userName);
        if (funcVersion == null) {
            GetAllFuncPermitRespVO ca = permitStatBp.updateUserPermitCache(userName);
            if (ca != null)
                return ca.funcVersion;
        }
        return funcVersion;
    }

    /**
     * 判断是否有当前api权限
     *
     * @param moduleUrl
     * @param apiUrl
     * @param operator
     * @param sysKey
     * @return
     * @author panyun@youkeshu.com
     * @date 2018年6月14日 下午3:14:43
     */
    private boolean hasApiFunc(String moduleUrl, String apiUrl, String operator, String sysKey) {
        // 获取业务系统的功能权限定义 jsonA
        String strSysFuncJson = cacheBp.getSysContext(sysKey);
        if (strSysFuncJson == null) {
            strSysFuncJson = this.getSysContextFromDb(sysKey);
            cacheBp.insertSysContext(sysKey, strSysFuncJson);
            if (StringUtility.isNullOrEmpty(strSysFuncJson))
                return true;
        }

        if (StringUtility.Empty.equals(strSysFuncJson))
            return true;

        // apiUrl在A不存在,返回true
        if (!strSysFuncJson.contains(String.format("%s%s%s", "\"", apiUrl, "\"")))
            return true;

        // 获取用户当前业务系统的funcJson, 若本身没有权限,返回NA
        String strCurUserFuncJson = cacheBp.getFuncJson(operator, sysKey);
        if (strCurUserFuncJson == null)
            return false;

        // apiUrl在SubA中存在，返回true
        if (strCurUserFuncJson.contains(String.format("%s%s%s", "\"", apiUrl, "\"")))
            return true;
        return false;
    }

    @Autowired
    PermissionMapper permissionMapper;

    private String getSysContextFromDb(String sysKey) {
        PermissionDO per = permissionMapper.getPermissionBySysKey(sysKey);
        if (per == null || per.getSysContext() == null)
            return StringUtility.Empty;
        return per.getSysContext();
    }

    @Override
    public ResultVO addUrcWhiteApi(String json) {
        JSONObject jsonObject = StringUtility.parseString(json).getJSONObject("data");
        String whiteApiUrl = jsonObject.getString("whiteApiUrl");
        IUrcWhiteApiVO iUrcWhiteApiVO = new IUrcWhiteApiVO();
        List<String> apiList = JSONArray.parseArray(whiteApiUrl, String.class);
        if (apiList.size() == 0 || apiList == null) {
            return VoHelper.getErrorResult(CommonMessageCodeEnum.PARAM_NULL.getCode(), "api不能为空");
        }

        for (String whiteApi : apiList) {
//如果新增的api已经在数据库中存在，那么不会新增
            Integer count = urcWhiteApiUrlMapper.selectWhiteApiByWiteApi(whiteApi);
            if (count != 0) {
                continue;
            }
            iUrcWhiteApiVO.setWhiteApiUrl(whiteApi);
            iUrcWhiteApiVO.setCreateBy(sessionBp.getOperator());
            iUrcWhiteApiVO.setCreateTime(new Date());
            iUrcWhiteApiVO.setModifiedBy(sessionBp.getOperator());
            iUrcWhiteApiVO.setModifiedTime(new Date());
            urcWhiteApiUrlMapper.insert(iUrcWhiteApiVO);
        }

        List<String> ListApi = urcWhiteApiUrlMapper.selectWhiteApi();
        String apiStr = StringUtility.toJSONString(ListApi);
//缓存api
        cacheBp.insertWhiteApi(apiStr);
        return VoHelper.getSuccessResult();

    }

    @Override
    public ResultVO deleteWhiteApi(String json) {
        JSONObject jsonObject = StringUtility.parseString(json).getJSONObject("data");
        String whiteApi = jsonObject.getString("whiteApiUrl");
        if (StringUtility.isNullOrEmpty(whiteApi)) {
            return VoHelper.getErrorResult(CommonMessageCodeEnum.PARAM_NULL.getCode(), "请求api不能为空");
        }
        Integer count = urcWhiteApiUrlMapper.selectWhiteApiByWiteApi(whiteApi);
        if (count == 0) {
            return VoHelper.getErrorResult(CommonMessageCodeEnum.FAIL.getCode(), "需要删除的api在数据库中不存在");
        }
        urcWhiteApiUrlMapper.deleteApi(whiteApi);
        List<String> ListApi = urcWhiteApiUrlMapper.selectWhiteApi();
        String apiStr = StringUtility.toJSONString(ListApi);
        //将所有的白名单放入缓存
        cacheBp.insertWhiteApi(apiStr);
        return VoHelper.getSuccessResult();
    }
}
