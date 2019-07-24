package com.yks.urc.mapper;

import com.yks.urc.entity.UserTicketDO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.Map;

/**
 * 版权：Copyright by www.youkeshu.com
 * 描述：代码注释以及格式化示例
 * 创建人：@author Songguanye
 * 创建时间：2019/1/8 16:13
 * 修改理由：
 * 修改内容：
 */
public interface UserTicketMapper {
    /**
     * 根据用户名查询用户的ticket信息
     * @param userName
     * @return
     */
    UserTicketDO selectUserTicketByUserName(String userName);
    /**
     * 用户ticket信息
     * @param map
     */
    void insertUserTicket(Map map);

    /**
     * 更新用户ticket表
     * @param map
     */
    void updateUserTicket(Map map);

    /**
     * 更新最后过期时间为2小时
     * @return
     * @Author panyun@youkeshu.com
     * @Date 2019/1/9 10:40
     */
    void updateExpiredTime(@Param("userName") String userName, @Param("expiredTime") Date expiredTime);

    /**
     * 根据用户名删除用户ticket信息
     * @param userName 用户名
     */
    void deleteUserTicketByUserName(String userName);
}
