/**
 * 〈一句话功能简述〉<br>
 * 〈功能权限树相关〉
 *
 * @author lwx
 * @create 2018/11/5
 * @since 1.0.0
 */
package com.yks.urc.funcjsontree.bp.impl;

import com.yks.common.enums.CommonMessageCodeEnum;
import com.yks.urc.entity.PermissionDO;
import com.yks.urc.entity.RolePermissionDO;
import com.yks.urc.exception.URCBizException;
import com.yks.urc.funcjsontree.bp.api.IFuncJsonTreeBp;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.mapper.IRolePermissionMapper;
import com.yks.urc.mapper.IUserRoleMapper;
import com.yks.urc.mapper.PermissionMapper;
import com.yks.urc.permitStat.bp.api.IPermitStatBp;
import com.yks.urc.session.bp.api.ISessionBp;
import com.yks.urc.vo.*;
import com.yks.urc.vo.helper.VoHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class IFuncJsonTreeBpImpl implements IFuncJsonTreeBp {
    private static Logger logger = LoggerFactory.getLogger(IFuncJsonTreeBpImpl.class);

    @Autowired
    private IRolePermissionMapper rolePermissionMapper;
    @Autowired
    private IUserRoleMapper userRoleMapper;
    @Autowired
    private IPermitStatBp permitStatBp;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private ISessionBp sessionBp;


    /**
     * 删除功能权限树节点
     *
     * @param
     * @return
     * @Author lwx
     * @Date 2018/11/2 10:33
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO deleteSysPermitNode(FuncTreeVO funcTreeVO) {
        //获取所有和sysKey 有关的角色和权限
        try {
            //递归返回结果
            //受影响的角色id 有
            Boolean result;
            // sysKey, 角色
            List<RolePermissionDO> updatePermissionMap = new ArrayList<>();
            // 递归删除角色下的功能权限树, 组装需要更新的数据
            ResultVO x = deleteUrcPermission(funcTreeVO, updatePermissionMap);
            if (x != null) return x;
        } catch (Exception e) {
            logger.error("删除节点失败,更新权限出错", e);
        }
        return VoHelper.getErrorResult();
    }

    /**
     * 修改功能权限节点树
     *
     * @param
     * @return
     * @Author lwx
     * @Date 2018/11/2 15:46
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO updateSysPermitNode(FuncTreeVO funcTreeVO) {
        // 找到和sysKey 相关的 权限
        try {
            List<RolePermissionDO> updateRolePermissions = new ArrayList<>();
            if (StringUtils.isEmpty(funcTreeVO.sysKey)) {
                return VoHelper.getSuccessResult();
            }
            //更新系统定义树
           Boolean result = udateSysRootVo(funcTreeVO);
            if (result == false){
                return VoHelper.getErrorResult(CommonMessageCodeEnum.FAIL.getCode(),"找不到需要更新的对应系统");
            }
            // 更新角色的功能权限,组装需要更新的数据
            if (updateUrcRoleSysContext(funcTreeVO, updateRolePermissions)) {
                return VoHelper.getSuccessResult("无关联角色需要处理");
            }
            updateRolePermissionAndRoleUser(updateRolePermissions);
            return VoHelper.getSuccessResult();
        } catch (Exception e) {
            logger.error("删除节点失败,更新权限出错", e.getMessage());
            return VoHelper.getErrorResult();
        }
    }

    /**
     * 递归json 树 删除角色下的功能权限,组装需要处理的数据
     *
     * @param
     * @return
     * @Author lwx
     * @Date 2018/11/13 14:45
     */

    private ResultVO deleteUrcPermission(FuncTreeVO funcTreeVO, List<RolePermissionDO> updatePermissionMap) {
        Boolean result;
        if (StringUtils.isEmpty(funcTreeVO.sysKey)) {
            return VoHelper.getErrorResult(CommonMessageCodeEnum.PARAM_NULL.getCode(), CommonMessageCodeEnum.PARAM_NULL.getDesc());
        }
        //拿到所有和当前系统有关的角色和权限
        List<RolePermissionDO> rolePermissionDOS = rolePermissionMapper.getROlePermissionBySysKey(funcTreeVO.sysKey);
        if (CollectionUtils.isEmpty(rolePermissionDOS)) {
            return VoHelper.getSuccessResult("无关联角色需要处理");
        }
        //sysKey不为空且delKeys为空时，删除sysKey系统的所有功能权限
        if (CollectionUtils.isEmpty(funcTreeVO.delKeys)) {
            //删除对应的系统
            if (deleteSystemFuncBykey(funcTreeVO, rolePermissionDOS)) {
                return VoHelper.getSuccessResult();
            }
        } else {
            //删除节点 并更新系统菜单树 ,复制源数据
            foreachDeleteUpateSysRootVo(funcTreeVO);

            for (RolePermissionDO rolePermissionDO : rolePermissionDOS) {
                RolePermissionDO updateRolePermission = new RolePermissionDO();
                if (StringUtils.isEmpty(rolePermissionDO.getSelectedContext())) {
                    continue;
                }
                //遍历 json 树,删除节点
                SystemRootVO systemRootVO = StringUtility.parseObject(rolePermissionDO.getSelectedContext(), SystemRootVO.class);
                if (systemRootVO == null) {
                    logger.error("json 树转换失败", rolePermissionDO.getSelectedContext());
                    throw new URCBizException("数据转换异常,异常的角色为", String.valueOf(rolePermissionDO.getRoleId()));
                }
                //遍历删除menu
                result = this.foreachMenuTree(systemRootVO, funcTreeVO.delKeys);
                //节点删除后,在转化为 json 权限树.入库
                String selectedContext = StringUtility.toJSONString(systemRootVO);
                if (StringUtils.isEmpty(selectedContext)) {
                    logger.error("systemRootVO　转 json 失败,失败的roleId为[%s]", rolePermissionDO.getRoleId());
                    continue;
                }
                if (result == false) {
                    continue;
                }
                if (rolePermissionDO.getRoleId() != null) {
                    // 已删除完成, 收集受影响的角色id , 组装角色权限
                    updateRolePermission.setRoleId(rolePermissionDO.getRoleId());
                    updateRolePermission.setSelectedContext(selectedContext);
                    updateRolePermission.setSysKey(rolePermissionDO.getSysKey());
                    updateRolePermission.setModifiedTime(StringUtility.getDateTimeNow());
                    updateRolePermission.setModifiedBy(sessionBp.getOperator());
                    if (updateRolePermission != null) {
                        updatePermissionMap.add(updateRolePermission);
                    }
                }
            }
            //更新角色下的用户
            updateRolePermissionAndRoleUser(updatePermissionMap);
            return VoHelper.getSuccessResult();
        }
        return null;
    }

    /**
     * 删除对应系统的数据
     *
     * @param
     * @return
     * @Author lwx
     * @Date 2018/11/13 9:34
     */
    private boolean deleteSystemFuncBykey(FuncTreeVO funcTreeVO, List<RolePermissionDO> rolePermissionDOS) {
        // 删除对应的系统定义
        permissionMapper.deleteSysPermissionBySysKey(funcTreeVO.sysKey);
        //1、删除sysKey对应的 角色-功能权限数据
        rolePermissionMapper.deleteBySysKey(funcTreeVO.sysKey);
        //获取sysKey相关连的roleId
        List<Long> roleIds = rolePermissionDOS.stream().map(RolePermissionDO::getRoleId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(roleIds)) {
            logger.info("roleID 的集合为空");
            return true;
        }
                /*2、获取roleIds角色对应的用户名*/
        Map dataMap = new HashMap();
        dataMap.put("roleIds", roleIds);
        List<String> userNames = userRoleMapper.listUserNamesByRoleIds(dataMap);
                /*3、更新用户操作权限冗余表和缓存*/
        if (!CollectionUtils.isEmpty(userNames)) {
            permitStatBp.updateUserPermitCache(userNames);
        }
        return true;
    }

    /**
     * 更新系统菜单树
     *
     * @param
     * @return
     * @Author lwx
     * @Date 2018/11/13 9:27
     */
    private void foreachDeleteUpateSysRootVo(FuncTreeVO funcTreeVO) {
        //拿到所有的系统定义树
        PermissionDO permissionDO = permissionMapper.getPermissionBySysKey(funcTreeVO.sysKey);
        //组装系统定义树
        if (permissionDO != null && StringUtils.isNotEmpty(permissionDO.getSysContext())) {
            //遍历 json 树,删除节点
            SystemRootVO systemRootVO = StringUtility.parseObject(permissionDO.getSysContext(), SystemRootVO.class);
            if (systemRootVO == null) {
                logger.error("json树转换systemRootVO转失败", permissionDO.getSysContext());
                throw new URCBizException(CommonMessageCodeEnum.HANDLE_DATA_EXCEPTION.getCode(), String.format("json树转换systemRootVO转失败:失败的json[%s]", permissionDO.getSysContext()));
            }
            this.foreachMenuTree(systemRootVO, funcTreeVO.delKeys);
            //节点删除后,在转化为 json 权限树.入库
            String sysContext = StringUtility.toJSONString(systemRootVO);
            if (StringUtils.isEmpty(sysContext)) {
                logger.error("systemRootVO转 json 失败,失败的systemRootVO为[%s]", StringUtility.toJSONString(systemRootVO));
                throw new URCBizException(CommonMessageCodeEnum.HANDLE_DATA_EXCEPTION.getCode(), String.format("json树转换systemRootVO转失败:失败的json[%s]", StringUtility.toJSONString(systemRootVO)));
            }
            PermissionDO updatePermission = new PermissionDO();
            updatePermission.setSysKey(permissionDO.getSysKey());
            String systemName = permissionMapper.getSysNameByKey(permissionDO.getSysKey());
            if (StringUtils.isNotEmpty(systemName)) {
                updatePermission.setSysName(systemName);
            }
            updatePermission.setSysContext(sysContext);
            updatePermission.setModifiedBy(sessionBp.getOperator());
            updatePermission.setModifiedTime(StringUtility.getDateTimeNow());
            permissionMapper.updateSysContextBySysKeyCondition(updatePermission);
        }
    }

    /**
     * 更新系统菜单树
     *
     * @param
     * @return
     * @Author lwx
     * @Date 2018/11/13 9:27
     */
    private Boolean udateSysRootVo(FuncTreeVO funcTreeVO) {
        //拿到所有的系统定义树
        PermissionDO permissionDO = permissionMapper.getPermissionBySysKey(funcTreeVO.sysKey);
        //组装系统定义树
        if (permissionDO == null || StringUtils.isEmpty(permissionDO.getSysContext())){
            return false;
        }
            //遍历 json 树,修改节点
            SystemRootVO systemRootVO = StringUtility.parseObject(permissionDO.getSysContext(), SystemRootVO.class);
            if (systemRootVO == null) {
                logger.error("json树转换systemRootVO转失败", permissionDO.getSysContext());
                throw new URCBizException(CommonMessageCodeEnum.HANDLE_DATA_EXCEPTION.getCode(), String.format("json树转换systemRootVO转失败:失败的json[%s]", permissionDO.getSysContext()));
            }
            this.updateMenuValue(systemRootVO, funcTreeVO.updateNode);
            //节点修改后,在转化为 json 权限树.入库
            String sysContext = StringUtility.toJSONString(systemRootVO);
            if (StringUtils.isEmpty(sysContext)) {
                logger.error("systemRootVO转 json 失败,失败的systemRootVO为[%s]", StringUtility.toJSONString(systemRootVO));
                throw new URCBizException(CommonMessageCodeEnum.HANDLE_DATA_EXCEPTION.getCode(), String.format("json树转换systemRootVO转失败:失败的json[%s]", StringUtility.toJSONString(systemRootVO)));
            }
            PermissionDO updatePermission = new PermissionDO();
            updatePermission.setSysKey(permissionDO.getSysKey());
            String systemName = permissionMapper.getSysNameByKey(permissionDO.getSysKey());
            if (StringUtils.isNotEmpty(systemName)) {
                updatePermission.setSysName(systemName);
            }
            updatePermission.setSysContext(sysContext);
            updatePermission.setModifiedBy(sessionBp.getOperator());
            updatePermission.setModifiedTime(StringUtility.getDateTimeNow());
            permissionMapper.updateSysContextBySysKeyCondition(updatePermission);
            return  true;
    }

    /**
     * 更新角色的权限,角色下的用户及缓存
     *
     * @param
     * @return
     * @Author lwx
     * @Date 2018/11/2 17:22
     */
    private void updateRolePermissionAndRoleUser(List<RolePermissionDO> updatePermissions) {
        //更新权限树
        if (CollectionUtils.isEmpty(updatePermissions)) {
            return;
        }
        Map dataMap = new HashMap();
        List<String> roleIds = new ArrayList<>();
        for (RolePermissionDO permissionDO : updatePermissions) {
            //更新权限树
            rolePermissionMapper.updateUserRoleByRoleId(permissionDO);
            if (permissionDO.getRoleId() != null) {
                roleIds.add(String.valueOf(permissionDO.getRoleId()));
            }
        }
        //去重
        roleIds = roleIds.stream().distinct().collect(Collectors.toList());
        dataMap.put("roleIds", roleIds);
        /*3、获取roleIds角色对应的用户名*/
        logger.info(String.format("获取的角色id为%s", roleIds));
        if (CollectionUtils.isEmpty(roleIds)) {
            logger.info("roleID 的集合为空");
        }
        List<String> userNames = userRoleMapper.listUserNamesByRoleIds(dataMap);
        logger.info(String.format("获取的用户名为%s", userNames));
        userNames = userNames.stream().distinct().collect(Collectors.toList());
    /*4、更新用户操作权限冗余表和缓存*/
        permitStatBp.updateUserPermitCache(userNames);
    }

    /**
     * 遍历 清理module
     *
     * @param
     * @return
     * @Author lwx
     * @Date 2018/11/2 14:38
     */
    private void foreachModuleTree(List<ModuleVO> lstModule, Set<String> lstDeleteKey) {
        if (CollectionUtils.isEmpty(lstModule) || CollectionUtils.isEmpty(lstDeleteKey)) {
            return;
        }
     /*   for (int i = 0; i < lstModule.size(); i++) {
            if (lstDeleteKey.contains(lstModule.get(i).key)) {
                lstModule.remove(i);
                //key 已经删除成功
                lstDeleteKey.remove(lstModule.get(i).key);
                i--;
            }
            foreachModuleTree(lstModule.get(i).module, lstDeleteKey);
            foreachFuncTree(lstModule.get(i).function, lstDeleteKey);
        }*/
        Iterator moduleTor = lstModule.iterator();
        while (moduleTor.hasNext()) {
            ModuleVO moduleVO = (ModuleVO) moduleTor.next();
            if (lstDeleteKey.contains(moduleVO.key)) {
                moduleTor.remove();
                //lstDeleteKey.remove(moduleVO.key);
            }
            foreachModuleTree(moduleVO.module, lstDeleteKey);
            foreachFuncTree(moduleVO.function, lstDeleteKey);
        }
    }

    /**
     * 遍历 清楚func 节点
     *
     * @param
     * @return
     * @Author lwx
     * @Date 2018/11/2 14:57
     */
    private void foreachFuncTree(List<FunctionVO> lstFunction, Set<String> lstDeleteKey) {
        if (CollectionUtils.isEmpty(lstFunction) || CollectionUtils.isEmpty(lstDeleteKey)) {
            return;
        }
    /*    for (int i = 0; i < lstFunction.size(); i++) {
            if (lstDeleteKey.contains(lstFunction.get(i).key)) {
                lstFunction.remove(i);
                //移除
                lstDeleteKey.remove(lstFunction.get(i).key);
                i--;
            }
            foreachFuncTree(lstFunction.get(i).function, lstDeleteKey);
        }*/
        Iterator funcTor = lstFunction.iterator();
        while (funcTor.hasNext()) {
            FunctionVO functionVO = (FunctionVO) funcTor.next();
            if (lstDeleteKey.contains(functionVO.key)) {
                funcTor.remove();
                //lstDeleteKey.remove(functionVO.key);
            }
            foreachFuncTree(functionVO.function, lstDeleteKey);
        }
       /* lstFunction.forEach(functionVO -> {
            if (lstDeleteKey.contains(functionVO.key)){
                lstFunction.remove(functionVO.key);
                lstDeleteKey.remove(functionVO.key);
            }
            foreachFuncTree(functionVO.function, lstDeleteKey);
        });*/
    }

    /**
     * 遍历所有的menu
     *
     * @param
     * @return
     * @Author lwx
     * @Date 2018/11/2 11:19
     */
    private Boolean foreachMenuTree(SystemRootVO systemRootVO, Set<String> lstDeleteKey) {
        if (CollectionUtils.isEmpty(systemRootVO.menu) || CollectionUtils.isEmpty(lstDeleteKey)) {
            return false;
        }
 /*     systemRootVO.menu.forEach(menuVO -> {
          if (lstDeleteKey.contains(menuVO.key)) {
              systemRootVO.menu.remove(menuVO.key);
              //key 也移除掉
              lstDeleteKey.remove(menuVO.key);
          } else {
              foreachModuleTree(menuVO.module, lstDeleteKey);
          }
      });*/
        Iterator menuTor = systemRootVO.menu.iterator();
        while (menuTor.hasNext()) {
            MenuVO menuVO = (MenuVO) menuTor.next();
            if (lstDeleteKey.contains(menuVO.key)) {
                menuTor.remove();
                //lstDeleteKey.remove(menuVO.key);
            } else {
                foreachModuleTree(menuVO.module, lstDeleteKey);
            }

        }
        return true;
    }


    /**
     * 递归json 树, 更新角色下的功能权限,组装需要更新的数据
     *
     * @param
     * @return
     * @Author lwx
     * @Date 2018/11/13 14:41
     */
    private boolean updateUrcRoleSysContext(FuncTreeVO funcTreeVO, List<RolePermissionDO> updateRolePermissions) {
        //拿到所有和当前系统有关的角色和权限
        List<RolePermissionDO> rolePermissionDOS = rolePermissionMapper.getROlePermissionBySysKey(funcTreeVO.sysKey);
        if (CollectionUtils.isEmpty(rolePermissionDOS)) {
            return true;
        }
        rolePermissionDOS.forEach(rolePermissionDO -> {
            Boolean result;
            RolePermissionDO updatePermission = new RolePermissionDO();
            if (StringUtils.isEmpty(rolePermissionDO.getSelectedContext())) {
                return;
            }
            //遍历 json 树, 修改节点
            SystemRootVO systemRootVO = StringUtility.parseObject(rolePermissionDO.getSelectedContext(), SystemRootVO.class);
            if (systemRootVO == null) {
                logger.error("json 树转换失败", rolePermissionDO.getSelectedContext());
                return;
            }
            //修改节点
            result = updateMenuValue(systemRootVO, funcTreeVO.updateNode);
            //将得到的结果再转为json
            String selectedContext = StringUtility.toJSONString(systemRootVO);
            if (StringUtils.isEmpty(selectedContext)) {
                logger.error("systemRootVO　转 json 失败,失败的roleId为[%s]", rolePermissionDO.getRoleId());
                return;
            }
            if (result == false) {
                return;
            }
            if (rolePermissionDO.getRoleId() != null) {
                //更新角色 权限
                updatePermission.setSelectedContext(selectedContext);
                updatePermission.setRoleId(rolePermissionDO.getRoleId());
                updatePermission.setSysKey(rolePermissionDO.getSysKey());
                updatePermission.setModifiedBy(sessionBp.getOperator());
                updatePermission.setModifiedTime(StringUtility.getDateTimeNow());
                if (updatePermission != null) {
                    updateRolePermissions.add(updatePermission);
                }
            }
        });
        return false;
    }

    /**
     * 更新菜单下的节点
     *
     * @param
     * @return
     * @Author lwx
     * @Date 2018/11/2 17:11
     */
    private Boolean updateMenuValue(SystemRootVO systemRootVO, List<NodeVO> lstNode) {
        if (systemRootVO == null || CollectionUtils.isEmpty(lstNode) || CollectionUtils.isEmpty(systemRootVO.menu)) {
            return false;
        }
        systemRootVO.menu.forEach(menuVO -> {
            lstNode.forEach(nodeVO -> {
                if (StringUtils.isEmpty(nodeVO.key)) {
                    return;
                }
                //当key相等时,修改数据
                if (nodeVO.key.equalsIgnoreCase(menuVO.key)) {
                    if (StringUtils.isNotEmpty(nodeVO.name)) {
                        menuVO.name = nodeVO.name;
                    }
                    if (StringUtils.isNotEmpty(nodeVO.url)) {
                        menuVO.url = nodeVO.url;
                    }
                }
                updateModuleValue(menuVO.module, lstNode);
            });
        });
        return true;
    }

    /**
     * 更新模块下的节点
     *
     * @param
     * @return
     * @Author lwx
     * @Date 2018/11/2 17:12
     */
    private void updateModuleValue(List<ModuleVO> lstModule, List<NodeVO> lstNode) {
        if (CollectionUtils.isEmpty(lstModule) || CollectionUtils.isEmpty(lstNode)) {
            return;
        }
        lstModule.forEach(moduleVO -> {
            lstNode.forEach(nodeVO -> {
                if (StringUtils.isEmpty(nodeVO.key)) {
                    return;
                }
                //当key相等时,修改数据
                if (nodeVO.key.equalsIgnoreCase(moduleVO.key)) {
                    if (StringUtils.isNotEmpty(nodeVO.name)) {
                        moduleVO.name = nodeVO.name;
                    }
                    if (StringUtils.isNotEmpty(nodeVO.url)) {
                        moduleVO.url = nodeVO.url;
                    }
                }
                updateModuleValue(moduleVO.module, lstNode);
                updateFuncValue(moduleVO.function, lstNode);
            });
        });
    }

    /**
     * 更新功能下的节点
     *
     * @param
     * @return
     * @Author lwx
     * @Date 2018/11/2 17:19
     */
    private void updateFuncValue(List<FunctionVO> lstFunction, List<NodeVO> lstNode) {
        if (CollectionUtils.isEmpty(lstFunction) || CollectionUtils.isEmpty(lstNode)) {
            return;
        }
        lstFunction.forEach(functionVO -> {
            lstNode.forEach(nodeVO -> {
                if (StringUtils.isEmpty(nodeVO.key)) {
                    return;
                }
                //当key相等时,修改数据
                if (nodeVO.key.equalsIgnoreCase(functionVO.key)) {
                    if (StringUtils.isNotEmpty(nodeVO.name)) {
                        functionVO.name = nodeVO.name;
                    }
                    updateFuncValue(functionVO.function, lstNode);
                }
            });
        });
    }
}
