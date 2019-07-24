/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/6/14
 * @since 1.0.0
 */
package com.yks.urc.authway.bp.impl;

import com.yks.urc.authway.bp.api.AuthWayBp;
import com.yks.urc.entity.PermissionDO;
import com.yks.urc.exception.ErrorCode;
import com.yks.urc.exception.URCBizException;
import com.yks.urc.mapper.*;
import com.yks.urc.vo.AuthWayVO;
import com.yks.urc.vo.SysAuthWayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthWayBpImpl implements AuthWayBp {

    @Autowired
    private IRoleMapper roleMapper;
    @Autowired
    private IRolePermissionMapper rolePermissionMapper;
    @Autowired
    private AuthWayMapper authWayMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 1. 首先通过用户判断是否是管理员 2. 通过管理员拿到对应的system_key,若是超管, 获取所有的sys_key , 3. sys_key 拿到对应的业务系统实体
     *
     * @param operator
     * @return
     */
    @Override
    public List<SysAuthWayVO> getMyAuthWay(String operator) {
        boolean isSuperAdmin = roleMapper.isSuperAdminAccount(operator);
        List<SysAuthWayVO> lstAuthWayVOS = new ArrayList<>();
        //1.首先通过用户判断是否是超级管理员 还是业务管理员,超级管理员拿到所有的业务系统key, 只需要拿定义表的数据即可
        if (isSuperAdmin == true) {
            List<PermissionDO> permissionDOS = permissionMapper.getAllSysKey();
            List<String> getSysKey = new ArrayList<>();
            //组装所有的key
            for (PermissionDO permissionDO : permissionDOS) {
                getSysKey.add(permissionDO.getSysKey());
            }
            //组装sysAuthWayVO
            lstAuthWayVOS = this.AssembleSysAuthWay(getSysKey);
        } else {
            boolean isAdmin = roleMapper.isAdminAccount(operator);
            if (isAdmin == true) {
                //2. 通过管理员拿到sys_key , 过滤掉禁用, 过期的角色
                List<String> getSysKey = rolePermissionMapper.getSysKetByRoleAndUserName(operator);
                //组装sysAuthWayVO
                lstAuthWayVOS = this.AssembleSysAuthWay(getSysKey);
            } else {
                lstAuthWayVOS = null;
            }
        }
        return lstAuthWayVOS;
    }

    /**
     * 组装 sysAuthWayVO
     *
     * @param
     * @return
     * @Author linwanxian@youkeshu.com
     * @Date 2018/6/14 17:14
     */
    public List<SysAuthWayVO> AssembleSysAuthWay(List<String> getSysKey) {
        List<SysAuthWayVO> sysAuthWayVOList = new ArrayList<>();
        //3. sys_key 拿到对应的业务系统实体
        for (String sysKey : getSysKey) {
            SysAuthWayVO sysAuthWayVO = new SysAuthWayVO();
            //获取最终结果 , 一个系统对应的多个授权方式
            List<AuthWayVO> authWayVOS = authWayMapper.getAuthWayVoBySysKey(sysKey);
            if (authWayVOS.size() == 0) {
                continue;
            }
            for (AuthWayVO authWayVO : authWayVOS) {
                if (authWayVO == null) {
                    //有的系统授权方式没有
                    continue;
                }
                //组装sysKey
                sysAuthWayVO.sysKey = authWayVO.sysKey;
                //然后将authWayVO的sysKey置为空
                authWayVO.sysKey = null;
                //组装sortIdx
                sysAuthWayVO.sortIdx = authWayVO.sortIdx;
                authWayVO.sortIdx = null;
                //组装sysName
                sysAuthWayVO.sysName = authWayVO.sysName;
                authWayVO.sysName = null;
            }
            //组装 lstEntity  authWayVO 和 entity 是一对一的关系 entity和sysAuthWayVO 是一对多的关系
            sysAuthWayVO.lstEntity = authWayVOS;
            sysAuthWayVOList.add(sysAuthWayVO);
        }
        return sysAuthWayVOList;
    }

    public static void main(String[] args) {
        AuthWayBp authWayBp = new AuthWayBpImpl();
        authWayBp.getMyAuthWay("panyun");
    }
}
