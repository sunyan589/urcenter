package com.yks.urc.mapper;

import com.yks.urc.entity.Entity;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EntityMapper {

    int insert(Entity record);

    /**
     * 通过enctity_code　查询实体 和授权方式一对一
     *
     * @param
     * @return
     * @Author linwanxian@youkeshu.com
     * @Date 2018/6/15 9:39
     */
    Entity selectEntityByCode(@Param("entityCode") String entityCode);

    /**
     * Description: 获取entityMap集合
     *
     * @param :
     * @return:
     * @auther: lvcr
     * @date: 2018/7/2 9:25
     * @see
     */
    @MapKey("entityCode")
    Map<String, Entity> getEntityMap();
}