package com.yks.urc.userValidate.bp.api;

/**
 * 刷新数据库中ticket过期时间
 *
 * @return
 * @Author panyun@youkeshu.com
 * @Date 2019/1/9 10:00
 */
public interface ITicketUpdateBp {
    void refreshExpiredTime(String userName, String ticket);

    /**
     * 定期更新数据库ticket过期时间
     *
     * @return
     * @Author panyun@youkeshu.com
     * @Date 2019/1/9 10:25
     */
    void dump2Db();
}
