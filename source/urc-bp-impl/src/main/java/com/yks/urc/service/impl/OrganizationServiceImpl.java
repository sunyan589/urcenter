package com.yks.urc.service.impl;

import java.sql.RowIdLifetime;
import java.util.ArrayList;
import java.util.List;

import com.yks.urc.entity.PersonOrg;
import com.yks.urc.entity.UserAndPersonDO;
import com.yks.urc.entity.UserDO;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.mapper.IRoleMapper;
import com.yks.urc.mapper.IUserMapper;
import com.yks.urc.mapper.PersonOrgMapper;
import com.yks.urc.vo.OrgTreeAndUserVO;
import com.yks.urc.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yks.urc.dingding.client.DingApiProxy;
import com.yks.urc.entity.Organization;
import com.yks.urc.mapper.OrganizationMapper;
import com.yks.urc.service.api.IOrganizationService;
import com.yks.urc.vo.OrgVO;
import com.yks.urc.vo.ResultVO;
import com.yks.urc.vo.helper.VoHelper;
import org.springframework.util.CollectionUtils;

@Service
public class OrganizationServiceImpl implements IOrganizationService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DingApiProxy dingApiProxy;

    @Autowired
    private IRoleMapper roleMapper;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private IUserMapper userMapper;
    @Autowired
    private PersonOrgMapper personOrgMapper;
    @Autowired
    private IUserMapper iUserMapper;

    @Override
    public ResultVO getAllOrgTree() {
        JSONArray deptJosn = null;
        try {
            List<Organization> orgList = organizationMapper.queryAllDept();
            List<OrgVO> orgListVO = new ArrayList<OrgVO>();
            for (Organization org : orgList) {
                OrgVO orgVO = new OrgVO();
                orgVO.dingOrgId = org.getDingOrgId();
                orgVO.orgName = org.getOrgName();
                orgVO.parentDingOrgId = org.getParentDingOrgId();
                orgListVO.add(orgVO);
            }
            deptJosn = treeDingDeptList(orgListVO, 0);
        } catch (Exception e) {
            VoHelper.getErrorResult();
        }

        return VoHelper.getSuccessResult(deptJosn);
    }

    @Override
    public ResultVO getAllOrgTreeAndUser() {
        List<OrgTreeAndUserVO> deptJosn = null;
        try {
            List<Organization> orgList = organizationMapper.queryAllDept();
            List<OrgTreeAndUserVO> orgTreeAndUserVOList = new ArrayList<>();
            for (Organization org : orgList) {
                OrgTreeAndUserVO orgTreeAndUserVO = new OrgTreeAndUserVO();
                orgTreeAndUserVO.key = org.getDingOrgId();
                orgTreeAndUserVO.title = org.getOrgName();
                //设立初始值，0代表组织结构
                orgTreeAndUserVO.isUser = 0;
                orgTreeAndUserVO.parentDingOrgId = org.getParentDingOrgId();
                orgTreeAndUserVOList.add(orgTreeAndUserVO);
            }
            deptJosn = treeDingDeptAndUserList2(orgTreeAndUserVOList, 0);
            recursionSetUser(deptJosn);
        } catch (Exception e) {
            logger.error("getAllOrgTreeAndUser ERROR:", e);
            VoHelper.getErrorResult();
        }
        return VoHelper.getSuccessResult(deptJosn);
    }

    @Override
    public ResultVO getUserByUserName(String operator, UserVO userVo) {
        try {
            //只需要查找用户的域账号
            if (!roleMapper.isAdminAccount(operator)) {
                userVo.createBy = operator;
            }
            UserDO userDO = userMapper.getUserByUserName(userVo);
            userVo.userName = userDO.getUserName();
            return VoHelper.getSuccessResult(userVo);
        } catch (Exception e) {
            return VoHelper.getErrorResult();
        }
    }

    //菜单树形结构
    private JSONArray treeDingDeptList(List<OrgVO> deptList, long parentId) {
        JSONArray childMenu = new JSONArray();
        for (OrgVO dept : deptList) {
            JSONObject jsonMenu = JSONObject.parseObject(JSON.toJSONString(dept));
            long id = jsonMenu.getLong("dingOrgId");
            long pid = jsonMenu.getLong("parentDingOrgId");
            if (parentId == pid) {
                JSONArray c_node = treeDingDeptList(deptList, id);
                jsonMenu.put("subOrg", c_node);
                childMenu.add(jsonMenu);
            }
        }
        return childMenu;
    }

    private void recursionSetUser(List<OrgTreeAndUserVO> deptJosn) {
        if (CollectionUtils.isEmpty(deptJosn)) return;
        for (OrgTreeAndUserVO mem : deptJosn) {
            if (mem == null) continue;
            if (mem.children == null) mem.children = new ArrayList<>();
            recursionSetUser(mem.children);

            //去urc_person_org去查找ding_user_id
            List<String> dingUserIds = personOrgMapper.selectDingUserIdByDingOrgId(StringUtility.convertToLong(mem.key));
            //去urc_user去查找user_name
            if (!CollectionUtils.isEmpty(dingUserIds)) {
                List<UserAndPersonDO> userAndPersonDOS = iUserMapper.selectUserNameAndPeronNameByDingUserId(dingUserIds);
                List<OrgTreeAndUserVO> orgTreeAndUsers = new ArrayList<>();
                for (UserAndPersonDO userAndPersonDO : userAndPersonDOS) {
                    OrgTreeAndUserVO orgTreeAndUserVO = new OrgTreeAndUserVO();
                    orgTreeAndUserVO.isUser = 1;
                    // StringBuilder stringBuilder=new StringBuilder();
                    //orgTreeAndUserVO.key = stringBuilder.append(userAndPersonDO.userName).append("__").append(mem.key).toString();
                    orgTreeAndUserVO.key = userAndPersonDO.userName;
                    orgTreeAndUserVO.title = userAndPersonDO.personName;
                    orgTreeAndUsers.add(orgTreeAndUserVO);
                }
                mem.children.addAll(orgTreeAndUsers);
            }
        }
    }

    private List<OrgTreeAndUserVO> treeDingDeptAndUserList2(List<OrgTreeAndUserVO> orgTreeAndUserVOList, long parentId) {
        List<OrgTreeAndUserVO> childMenu = new ArrayList<>();
        for (OrgTreeAndUserVO dept1 : orgTreeAndUserVOList) {
            OrgTreeAndUserVO dept = StringUtility.parseObject(StringUtility.toJSONString_NoException(dept1), OrgTreeAndUserVO.class);
            long id = StringUtility.convertToLong(dept.key);
            long pid = StringUtility.convertToLong(dept.parentDingOrgId);
            if (parentId == pid) {
                List<OrgTreeAndUserVO> c_node = treeDingDeptAndUserList2(orgTreeAndUserVOList, id);
                //判断当最底层的children的size()为0时，则应该组装这个组织的人员结构，即组装成员
                if (!CollectionUtils.isEmpty(c_node))
                    dept.children = c_node;
                childMenu.add(dept);
            }
        }
        return childMenu;
    }

    //迭代组装菜单树
    private JSONArray treeDingDeptAndUserList(List<OrgTreeAndUserVO> orgTreeAndUserVOList, long parentId) {
        JSONArray childMenu = new JSONArray();
        for (OrgTreeAndUserVO dept : orgTreeAndUserVOList) {
            JSONObject jsonMenu = JSONObject.parseObject(JSON.toJSONString(dept));
            long id = jsonMenu.getLong("key");
            long pid = jsonMenu.getLong("parentDingOrgId");
            if (parentId == pid) {
                JSONArray c_node = treeDingDeptAndUserList(orgTreeAndUserVOList, id);
                //判断当最底层的children的size()为0时，则应该组装这个组织的人员结构，即组装成员
                if (c_node.size() == 0) {
                    //去urc_person_org去查找ding_user_id
                    List<String> dingUserIds = personOrgMapper.selectDingUserIdByDingOrgId(id);
                    //去urc_user去查找user_name
                    if (!CollectionUtils.isEmpty(dingUserIds)) {
                        List<UserAndPersonDO> userAndPersonDOS = iUserMapper.selectUserNameAndPeronNameByDingUserId(dingUserIds);
                        List<OrgTreeAndUserVO> orgTreeAndUsers = new ArrayList<>();
                        for (UserAndPersonDO userAndPersonDO : userAndPersonDOS) {
                            OrgTreeAndUserVO orgTreeAndUserVO = new OrgTreeAndUserVO();
                            orgTreeAndUserVO.isUser = 1;
                            orgTreeAndUserVO.key = userAndPersonDO.userName;
                            orgTreeAndUserVO.title = userAndPersonDO.personName;
                            orgTreeAndUsers.add(orgTreeAndUserVO);
                        }
                        jsonMenu.put("children", orgTreeAndUsers);
                    }
                }
                //否则就组装菜单树
                else {
                    jsonMenu.put("children", c_node);
                }
                childMenu.add(jsonMenu);
            }
        }
        return childMenu;
    }

}
