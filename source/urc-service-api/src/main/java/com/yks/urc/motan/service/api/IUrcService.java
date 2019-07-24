package com.yks.urc.motan.service.api;

import java.util.List;
import java.util.Map;

import com.yks.urc.vo.*;

public interface IUrcService {
    /**
     * 同步数据
     *
     * @param json
     * @return
     */
    ResultVO syncUserInfo(String json);

    /**
     * 新增客服分组
     *
     * @param json
     * @return
     */
    ResultVO addCsUserGroup(String json);

    /**
     * 编辑客服分组名称
     *
     * @param json
     * @return
     */
    ResultVO editCsUserGroup(String json);


    /**
     * 删除客服分组
     *
     * @param json
     * @return
     */
    ResultVO delCsUserGroup(String json);

    /**
     * 登陆校验：用户名密码检验
     *
     * @param map
     * @return
     * @author panyun@youkeshu.com
     * @date 2018年6月6日 下午12:22:35
     */
    ResultVO<LoginRespVO> login(Map<String, String> map);

    ResultVO logout(String jsonStr);

    /**
     * 校验ticket及功能权限版本
     *
     * @param map
     * @return
     * @author panyun@youkeshu.com
     * @date 2018年6月14日 下午1:11:56
     */
    ResultVO funcPermitValidate(Map<String, String> map);

    /**
     * 手动触发“同步钉钉部门及人员”信息
     *
     * @param str
     * @return
     */
    ResultVO syncDingOrgAndUser();


    /**
     * 用户管理搜索用户
     *
     * @return
     */
    ResultVO<PageResultVO> getUsersByUserInfo(String params);


    /**
     * 根据DingOrgId获取人员信息含有分页
     *
     * @param str
     * @return
     */
    ResultVO getUserByDingOrgId(String params);


    /**
     * 组织架构搜索用户
     *
     * @param params
     * @return
     */
    ResultVO getUserByUserInfo(String params);

    /**
     * 组织结构树
     *
     * @param params
     * @return
     */
    ResultVO getAllOrgTree();

    /**
     * 获取全部用户及组织结构
     *
     * @param
     * @return
     */
    ResultVO getAllOrgTreeAndUser();

    /**
     * 快速分配数据权限模板给用户
     *
     * @param jsonStr
     * @return
     */
    ResultVO assignDataRuleTempl2User(String jsonStr);

    /**
     * 根据templId获取数据权限模板
     *
     * @param jsonStr
     * @return
     */
    ResultVO<DataRuleTemplVO> getDataRuleTemplByTemplId(String jsonStr);

    /**
     * 获取数据权限模板
     *
     * @param jsonStr
     * @return
     */
    ResultVO<PageResultVO> getDataRuleTempl(String jsonStr);


    /**
     * 获取所有平台
     *
     * @param jsonStr
     * @return
     */
    ResultVO<List<OmsPlatformVO>> getPlatformList(String jsonStr);

    /**
     * 获取指定平台下的店铺名称和站点
     *
     * @param jsonStr
     * @return
     */
    ResultVO<List<OmsShopVO>> getShopList(String jsonStr);

    /**
     * 角色名判重
     *
     * @param operator
     * @param newRoleName
     * @param roleId      可为空，若为修改情况下，会排除自身进行校重
     * @return 返回值为0--表示不重复, 1--表示重复
     * @author oujie@youkeshu.com
     */
    ResultVO<Integer> checkDuplicateRoleName(String jsonStr);

    /**
     * 复制角色
     * 包含复制角色对应权限
     *
     * @param jsonStr
     * @return
     */
    ResultVO copyRole(String jsonStr);

    /**
     * 获取应用系统及其授权方式
     *
     * @param jsonStr
     * @return ResultVO<List<SysAuthWayVO>>
     * @Author linwanxian@youkeshu.com
     * @Date 2018/6/14 14:31
     */
    ResultVO<List<SysAuthWayVO>> getMyAuthWay(String jsonStr);


    /**
     * 获取功能权限及版本号
     *
     * @param jsonStr
     * @return
     * @author panyun@youkeshu.com
     * @date 2018年6月14日 下午12:45:36
     */
    ResultVO<GetAllFuncPermitRespVO> getAllFuncPermit(String jsonStr);


    /**
     * 获取角色关联的用户
     *
     * @param jsonStr
     * @return
     */
    ResultVO getUserByRoleId(String jsonStr);

    /**
     * 获取多个角色已有的用户
     *
     * @param jsonStr
     * @return
     */
    ResultVO getRoleUser(String jsonStr);

    /**
     * 获取用户可选择的所有数据授权方案
     *
     * @param jsonStr
     * @return
     */
    ResultVO getMyDataRuleTempl(String jsonStr);


    /**
     * 获取多个用户的所有数据权限
     *
     * @param jsonStr
     * @return
     */
    ResultVO getDataRuleByUser(String jsonStr);

    /**
     * 导入sys功能权限定义
     *
     * @param jsonStr
     * @return
     * @author panyun@youkeshu.com
     * @date 2018年6月14日 下午7:17:14
     */
    ResultVO importSysPermit(String jsonStr);

    /**
     * 获取指定用户可授权给其它角色的功能权限
     *
     * @param jsonStr
     * @return
     */
    ResultVO getUserAuthorizablePermission(String jsonStr);

    /**
     * 获取多个角色已有的功能权限
     *
     * @param jsonStr
     * @return
     */
    ResultVO getRolePermission(String jsonStr);

    /**
     * 精确搜索用户
     *
     * @param jsonStr
     * @return
     */
    ResultVO getUserByUserName(String jsonStr);

    /**
     * 模糊搜索用户域账号
     *
     * @param jsonStr
     * @return
     */
    ResultVO fuzzySearchUsersByUserName(String jsonStr);

    /**
     * 更新多个角色的用户
     *
     * @param jsonStr
     * @return
     */
    ResultVO updateUsersOfRole(String jsonStr);

    ResultVO updateRolePermission(String jsonStr);


    ResultVO getMavenPackageTime();

    /**
     * Description: 新增或编辑一个方案
     *
     * @param :
     * @return:
     * @auther: lvcr
     * @date: 2018/6/20 15:31
     * @see
     */
    ResultVO addOrUpdateDataRuleTempl(String jsonStr);

    /**
     * Description: 删除一个或多个方案
     *
     * @param :
     * @return:
     * @auther: lvcr
     * @date: 2018/6/20 15:34
     * @see
     */
    ResultVO deleteDataRuleTempl(String jsonStr);

    /**
     * Description: 方案名判重
     */
    ResultVO<Integer> checkDuplicateTemplName(String jsonStr);

    /**
     * Description: 查看用户的功能权限列表
     *
     * @param :
     * @return:
     * @auther: lvcr
     * @date: 2018/6/20 15:35
     * @see
     */
    ResultVO getUserPermissionList(String jsonStr);

    /**
     * Description: 创建或更新多个用户的数据权限
     *
     * @param :
     * @return:
     * @auther: lvcr
     * @date: 2018/6/20 15:39
     * @see
     */
    ResultVO addOrUpdateDataRule(String jsonStr);

    /**
     * Description: 多条件搜索角色
     *
     * @param :
     * @return:
     * @auther: lvcr
     * @date: 2018/6/20 15:41
     * @see
     */
    ResultVO getRolesByInfo(String jsonStr);

    /**
     * Description: 新增或更新角色、功能权限、用户
     *
     * @param :
     * @return:
     * @auther: lvcr
     * @date: 2018/6/20 15:42
     * @see
     */
    ResultVO addOrUpdateRoleInfo(String jsonStr);

    /**
     * Description: 根据角色id获取角色信息
     *
     * @param :
     * @return:
     * @auther: lvcr
     * @date: 2018/6/20 15:44
     * @see
     */
    ResultVO getRoleByRoleId(String jsonStr);

    /**
     * Description: 删除多个角色
     *
     * @param :
     * @return:
     * @auther: lvcr
     * @date: 2018/6/20 15:46
     * @see
     */
    ResultVO deleteRoles(String jsonStr);

    /**
     * 将所有系统的所有功能权限授予指定角色
     *
     * @param jsonStr
     * @return
     */
    ResultVO assignAllPermit2Role(String jsonStr);

    /**
     * 开启监控内存
     *
     * @param jsonStr
     * @return
     * @Author linwanxian@youkeshu.com
     * @Date 2018/7/2 17:05
     */
    ResultVO startMonitorMemory(String jsonStr);

    /**
     * 开启监控内存
     *
     * @param jsonStr
     * @return
     * @Author linwanxian@youkeshu.com
     * @Date 2018/7/2 17:05
     */
    ResultVO endMonitorMemory(String jsonStr);

    /**
     * 手动触发角色过期方法
     *
     * @param jsonStr
     * @return
     * @Author linwanxian@youkeshu.com
     * @Date 2018/7/4 9:22
     */
    ResultVO handleExpiredRole(String jsonStr);

    /**
     * 更新用户缓存
     *
     * @param jsonStr
     * @return
     * @Author linwanxian@youkeshu.com
     * @Date 2018/7/4 16:06
     */
    ResultVO updateUserPermitCache(String jsonStr);

    /**
     * 查看用户是否是超级管理员
     *
     * @param jsonStr
     * @return
     * @Author linwanxian@youkeshu.com
     * @Date 2018/7/4 16:06
     */
    ResultVO operIsSuperAdmin(String jsonStr);

    /**
     * 查找所有的平台账号和站点
     *
     * @param
     * @return
     * @Author linwanxian@youkeshu.com
     * @Date 2018/7/7 14:42
     */
    ResultVO getPlatformShopSite(String jsonStr);

    ResultVO syncPlatform(String jsonStr);

    ResultVO syncShopSite(String jsonStr);

    /**
     * 通过
     *
     * @param
     * @return
     * @Author linwanxian@youkeshu.com
     * @Date 2018/7/9 10:45
     */
    ResultVO fuzzSearchPersonByName(String json);

    /**
     * 获取指定系统大于某个时间之后有更新的数据权限
     *
     * @param json
     * @return
     */
    ResultVO<List<DataRuleSysVO>> getDataRuleGtDt(String json);

    /**
     * 更新缓存ａｐｉ前缀
     *
     * @return
     * @param:
     * @Author lwx
     * @Date 2018/7/17 15:34
     */
    ResultVO updateApiPrefixCache(String json);

    /**
     * 获取亚马逊的账号,数据授权
     *
     * @return
     * @param:
     * @Author lwx
     * @Date 2018/7/21 10:26
     */
    ResultVO<List<OmsPlatformVO>> getPlatformShop(String json);

    /**
     * 获取指定平台下的账号站点 数据权限
     *
     * @param
     * @return
     * @Author lwx
     * @Date 2018/8/2 14:45
     */
    ResultVO<List<OmsPlatformVO>> appointPlatformShopSite(String json);


    /**
     * @Description : 根据entityCode
     * @Author: wujianghui@youkeshu.com
     * @Date: 2018/8/13 14:23
     * @Param: [jsonStr]
     * @return: com.yks.urc.vo.ResultVO
     **/
    ResultVO getPlatformShopByEntityCode(String jsonStr);

    /**
     * 获取验证码
     *
     * @param jsonStr 前端传入的json字符串
     * @return
     */
    ResultVO resetPwdGetVerificationCode(String jsonStr);

    /**
     * 重置密码-提交重置请求
     *
     * @param jsonStr
     * @return
     */
    ResultVO resetPwdSubmit(String jsonStr);

    /**
     * 删除权限树节点
     *
     * @param
     * @return
     * @Author lwx
     * @Date 2018/11/2 15:38
     */
    ResultVO deleteSysPermitNode(String jsonStr);

    /**
     * 修改权限树节点
     *
     * @param
     * @return
     * @Author lwx
     * @Date 2018/11/2 15:38
     */
    ResultVO updateSysPermitNode(String jsonStr);

    /**
     * @Description :获取sku分类,库存等数据权限
     * @Author: tangjianbo@youkeshu.com
     * @Date: 2018/11/23 17:24
     * @Param:
     * @return:
     **/
    ResultVO getBasicDataList(String jsonStr);

    /**
     * @Description :仓储的数据授权
     * @Author: tangjianbo@youkeshu.com
     * @Date: 2018/12/20 16:10
     * @Param:
     * @return:
     **/
    ResultVO getWarehouse(String jsonStr);

    /**
     * @Description :搜索用户上网账号和用户名
     * @Author: tangjianbo@youkeshu.com
     * @Date: 2018/12/22 9:27
     * @Param:
     * @return:
     **/
    ResultVO searchUserPerson(String jsonStr);

    /**
     * @Description :新增用户中心白名单
     * @Author: tangjianbo@youkeshu.com
     * @Date: 2019/01/19 9:27
     * @Param:
     * @return:
     **/
    ResultVO addUrcWhiteApi(String json);

    /**
     * @Description :删除用户中心白名单
     * @Author: tangjianbo@youkeshu.com
     * @Date: 2019/01/19 9:27
     * @Param:
     * @return:
     **/
    ResultVO deleteWhiteApi(String json);
}
