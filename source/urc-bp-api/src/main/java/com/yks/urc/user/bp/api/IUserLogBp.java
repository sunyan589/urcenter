/**
 * 〈一句话功能简述〉<br>
 * 〈日志记录〉
 *
 * @author lwx
 * @create 2018/11/26
 * @since 1.0.0
 */
package com.yks.urc.user.bp.api;

import com.yks.urc.entity.UserLoginLogDO;

public interface IUserLogBp {

    /**
     *  日志记录
     * @param
     * @return
     * @Author lwx
     * @Date 2018/11/26 14:36
     */
    void insertLog(UserLoginLogDO logDO);
}
