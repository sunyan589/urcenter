/**
 * 〈一句话功能简述〉<br>
 * 〈用户业务系统VO〉
 *
 * @author 31076
 * @create 2018/6/5
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;

public class UserSysVO implements Serializable {
    private static final long serialVersionUID = -3878825413931253041L;
    /**
     *
     */
    public String userName;
    /**
     *
     */
    public String sysKey;
    /**
     *  功能版本
     */
    public String funcVersion;
    /**
     * 功能权限json
     */
    public String context;
}
