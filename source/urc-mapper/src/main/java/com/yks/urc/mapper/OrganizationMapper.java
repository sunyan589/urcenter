package com.yks.urc.mapper;

import java.util.List;

import com.yks.urc.entity.Organization;


public interface OrganizationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Organization record);

    int insertSelective(Organization record);

    Organization selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Organization record);

    int updateByPrimaryKey(Organization record);
    
    
    /**
     * 得到所有的部门
     * @return
     */
    List<Organization> queryAllDept();

    /**
     * 批量插入部门
     * @param orgList
     * @return
     */
    int insertBatchOrg(List<Organization> orgList);
    
    /**
     * 清空部门表
     */
	void deleteAllOrg();
}