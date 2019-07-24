package com.yks.urc.mapper;

import com.yks.urc.entity.DataRuleSysDO;
import com.yks.urc.entity.UserDO;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface IDataRuleSysMapper {

    /**
     * Description: 新增
     *
     * @param : record
     * @return:
     * @auther: lvcr
     * @date: 2018/6/14 15:50
     * @see
     */
    int insert(DataRuleSysDO record);

    /**
     * Description: 批量新增
     *
     * @param :
     * @return:
     * @auther: lvcr
     * @date: 2018/6/15 17:41
     * @see
     */
    int insertBatch(List<DataRuleSysDO> dataRuleSysDOS);


    /**
     * Description: 根据dataRuleIds 获取数据权限Sys  包括对应的行、列权限
     *
     * @param :
     * @return:
     * @auther: lvcr
     * @date: 2018/6/15 17:43
     * @see
     */
    List<DataRuleSysDO> getDataRuleSysDatas(@Param("dataRuleId") Long dataRuleId);

    /**
     * 根据用户名查询sys-id
     *
     * @param userDO
     * @return
     */
    List<DataRuleSysDO> getDataRuleSysByUserName(@Param("userDO") UserDO userDO,@Param("list") List sysKeys );


    /**
     * Description: 根据dataRuleId删除 权限数据Sys、行权限、列权限
     *
     * @param : dataRuleId
     * @return: Integer
     * @auther: lvcr
     * @date: 2018/6/14 17:26
     * @see
     */
    Integer deldataRuleSysDatasById(@Param("dataRuleId") Long dataRuleId);

    /**
     * Description: 根据dataRuleID列表删除 权限数据Sys、行权限、列权限
     *
     * @param :
     * @return:
     * @auther: lvcr
     * @date: 2018/6/29 14:56
     * @see
     */
    Integer delRuleSysDatasByIdsAndCreatBy(@Param("dataRuleIds") List<Long> dataRuleIds, @Param("createBy") String createBy);

    /**
     * Description: 根据dataRuleID以及sysKeys列表删除 权限数据Sys、行权限、列权限
     * @param dataRuleId
     * @param sysKeys
     */
    Integer delRuleSysDatasByIdsAndSyskey( @Param("sysKeys") List<String> sysKeys,@Param("dataRuleId") Long dataRuleId);

    /**
     * Description: 根据dataRuleId 获取数据权限Sys 包含行权限、列权限
     *
     * @param :
     * @return:
     * @auther: lvcr
     * @date: 2018/6/29 18:38
     * @see
     */
    List<DataRuleSysDO> getDataRuleSyAndOpersById(@Param("dataRuleId") Long dataRuleId);
    /**
     * Description: 获取dataRuleId所拥有的系统
     * @param :
     * @return: 
     * @auther: lvcr
     * @date: 2018/7/4 12:45
     * @see
     */
    List<String> getTemplOwnSysByDataRuleId(@Param("dataRuleId") Long dataRuleId);
    /**
     * Description: 获取userName所拥有的系统
     * @param :
     * @return:
     * @auther: lvcr
     * @date: 2018/7/4 12:45
     * @see
     */
    List<String> getUserOwnSysByUserName(@Param("userName") String userName);


    List<DataRuleSysDO> getDataRuleSysGtDt(@Param("sysKey") String sysKey ,@Param("createTime") Date date,@Param("pageSize") Integer pageSize );

    List<DataRuleSysDO> getDataRuleSysEqDt(@Param("sysKey") String sysKey,@Param("createTime") Date createTime);
}