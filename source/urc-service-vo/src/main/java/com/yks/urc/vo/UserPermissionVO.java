/**
 * 〈一句话功能简述〉<br>
 * 〈用户权限〉
 *
 * @author 31076
 * @create 2018/6/5
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;
import java.util.List;

public class UserPermissionVO implements Serializable {
    private static final long serialVersionUID = 8238720451031313737L;
    /**
     * 用户名
     */
    public String userName;
    /**
     *
     */
    public String sysKey;
    /**
     *  平台订单/速卖通订单/速卖通订单详情
     */
    public String pageFullPathName;
    /**
     *
     */
    public String pageFullPathKey;
    /**
     *
     */
    public List<String> function;
}
