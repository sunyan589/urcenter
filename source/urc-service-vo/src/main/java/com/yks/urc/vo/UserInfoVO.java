/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/7/9
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;

public class UserInfoVO implements Serializable {
    private static final long serialVersionUID = -3396291419184536199L;

    public String userName;
    public String personName;
    public String dingUserId;
    public String orgName;
    public String parentOrgName;
    //增加两个字段返回
    /**
     * 手机号
     */
    public String phoneNum;
    /**
     * 邮箱
     */
    public String email;
    /**
     *  工号
     */
    public String jobNumber;
    /**
     *  职位
     */
    public String position;
    /**
     *  男 女
     */
    public String gender;

}
