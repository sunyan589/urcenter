package com.yks.urc.dingding.client;

import com.alibaba.fastjson.JSONArray;
import com.yks.urc.dingding.client.vo.DingDeptVO;
import com.yks.urc.dingding.client.vo.DingUserVO;

import java.util.List;


public interface DingApiProxy {

    /**
     * 获取钉钉accessToken
     * @return
     */
    String getDingAccessToken() throws Exception ;


    /**
     * 获取钉钉所有部门
     * @return
     */
    List<DingDeptVO> getDingAllDept() throws Exception ;


    /**
     * 根据部门id获取直接子部门
     * @param departmentId 部门id
     * @return
     */
    List<DingDeptVO> getDingSubDept(String departmentId) throws Exception ;


    /**
     *根据部门id获取所有的部门
     * @param departmentId 部门id
     * @return
     */
    List<DingDeptVO> getDingAllSubDept(String departmentId) throws Exception ;


    /**
     * 根据部门id得到成员信息
     * @param departmentId
     * @return
     */
    List<DingUserVO> getDingMemberByDepId(String departmentId) throws Exception;

    /**
     * 根据部门id得到父部门id路径
     * @param departmentId
     * @return
     */
    JSONArray getDingParentDepts(String departmentId) throws Exception;

}
