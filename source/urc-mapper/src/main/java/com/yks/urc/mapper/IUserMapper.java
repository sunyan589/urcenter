package com.yks.urc.mapper;

import com.yks.urc.entity.*;
import com.yks.urc.vo.NameVO;
import com.yks.urc.vo.UserInfoVO;
import com.yks.urc.vo.UserVO;
import com.yks.urc.vo.helper.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    UserDO selectByPrimaryKey(Long id);

    List<UserAndPersonDO>  selectUserNameAndPeronNameByDingUserId(@Param("personOrgs") List<String> personOrgs);

    List<UserAndPersonDO>  selectUserNameAndPeronNameByUserName(UserPersonParamDO userPersonParamDO);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);

    List<UserDO> listUsersByRoleId(String roleId);


    /**
     * 搜索用户 分页 ,多个用户搜索
     *
     * @param query
     * @param userName
     * @return List<UserDO>
     * @Author linwanxian@youkeshu.com
     * @Date 2018/6/11 10:34
     */
    List<UserVO> getUsersByUserInfo(@Param("query") Query query, @Param("list") List userName);

    /**
     * 获取搜索用户分页的总数
     *
     * @param query
     * @param userName
     * @return int
     * @Author linwanxian@youkeshu.com
     * @Date 2018/6/11 17:05
     */
    int getUsersByUserInfoCount(@Param("query") Query query, @Param("list") List userName);

    /**
     * 批量同步到数据库
     *
     * @param userDoList
     * @return
     */
    int insertBatchUser(@Param("userDoList") List<UserDO> userDoList);

    /**
     * @return
     * @Author linwanxian@youkeshu.com
     * @Date 2018/6/11 10:43
     */
    int deleteUrcUser();

    /**
     * 通过钉钉userId获取用户
     *
     * @param userId
     * @return
     */
    UserDO getUserInfoByDingUserId(String userId);

/*	*//**
     * 根据ruleId查看哪些用户已经拥有数据权限
     * @param ruleDO
     * @return
     *//*
    List<UserDO> queryUserDataByRuleId(DataRuleDO ruleDO);
	
	*//**
     * 根据ruleId查看哪些用户没有数据权限
     * @param ruleDO
     * @return
     *//*
	List<UserDO> queryUserNoDataByRuleId(DataRuleDO ruleDO);*/

    /**
     * 获取所有的用户
     *
     * @return
     */
    List<String> listAllUsersUserName();


    /**
     * 根据roleId查看所有用户名称
     *
     * @param roleId
     * @return
     */
    List<String> listUsersUserNameByRoleId(Long roleId);


    /**
     * 根据roleId得到User
     *
     * @param roleId
     * @return
     */
    List<UserDO> getUserByRoleId(UserRoleDO userRole);

    UserDO test(@Param("userName") String userName);

    /**
     * 精确搜索用户
     *
     * @param userVO
     * @return
     */
    UserDO getUserByUserName(@Param("userVO") UserVO userVO);

    UserDO getUserByName(@Param("userName") String userName);


    /**
     * 模糊搜索用户域账号
     *
     * @param query
     * @return
     */
    List<UserVO> fuzzySearchUsersByUserName(Query query);

    /**
     * 模糊搜索用户域账号总数
     *
     * @param query
     * @return
     */
    int fuzzySearchUsersByUserNameCount(Query query);

    String getPersonNameByUserName(@Param("userName") String userName);

    /**
     * 模糊搜索 用户 给第三方
     *
     * @param
     * @return
     * @Author linwanxian@youkeshu.com
     * @Date 2018/7/9 20:52
     */
    List<UserInfoVO> fuzzSearchUserByName(@Param("name") String name);
    /**
     *   通过userName 拿personName
     * @param
     * @return
     * @Author lwx
     * @Date 2019/1/10 14:17
     */
    List<NameVO> getUserPersonByUserNames(@Param("lstUserName") List<String> lstUserName);
}