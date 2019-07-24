package com.yks.urc.mapper;

import java.util.List;

import com.yks.urc.entity.PersonOrg;

public interface PersonOrgMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PersonOrg record);

    int insertSelective(PersonOrg record);

    PersonOrg selectByPrimaryKey(Long id);

    List<String> selectDingUserIdByDingOrgId(Long dingUserId);

    int updateByPrimaryKeySelective(PersonOrg record);

    int updateByPrimaryKey(PersonOrg record);

    /**
     * 批量插入
     * @param personOrgList
     * @return
     */
	int insertBatchPersonOrg(List<PersonOrg> personOrgList);

	/**
	 * 清空所有部门人员表
	 */
	void deleteAllPersonOrg();
}