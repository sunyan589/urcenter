package com.yks.urc.mapper;

import com.yks.urc.entity.Person;
import com.yks.urc.vo.PersonVO;
import com.yks.urc.vo.UserInfoVO;
import com.yks.urc.vo.helper.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PersonMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Person record);

    int insertSelective(Person record);

    Person selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Person record);

    int updateByPrimaryKey(Person record);
    



	/**
	 * 搜索人员信息全局
	 * @param query
	 * @return
	 */
	List<PersonVO> list(Query query);
	
	/**
	 * 人员搜索数量
	 * @param query
	 * @return
	 */
	long count(Query query);

	/**
	 * 批量插入人员
	 * @param personList
	 */
	int insertBatchPerson(List<Person> personList);

	/**
	 * 清空所有的人员表
	 */
	void deleteAllPerson();

	/**
	 * 根据部门id查询数据
	 * @param query
	 * @return
	 */
	List<PersonVO> getUserByDingOrgId(Query query);

	/**
	 * 根据部门id查询数据总数
	 * @param query
	 * @return
	 */
	long getUserByDingOrgIdCount(Query query);

	UserInfoVO getPersonOrgById(@Param("dingUserId") String dingUserId);
	
}