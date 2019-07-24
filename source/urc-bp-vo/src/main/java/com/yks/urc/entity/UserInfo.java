/**
 * 〈一句话功能简述〉<br>
 * 〈员工钉钉id、是否禁用信息〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/6/7
 * @since 1.0.0
 */
package com.yks.urc.entity;

import java.io.Serializable;
import java.util.List;

public class UserInfo implements Serializable {
    private static final long serialVersionUID = 8572322957045776482L;
    /**
     * 钉钉用户名
     */
    public Integer id;
    /**
     * 钉钉用户名
     */
    public String username;
    /**
     * 钉钉用户id
     */
    public String ding_userid;
    /**
     * 表示是否启用， 66050表示禁用，其他的表示启用。
     */
    public String ad_control_number;
    /**
     *  启用时间
     */
    public String date_joined;
    /**
     *  中文名
     */
    public String chinese_name;
    /**
     *  部门
     */
    public List<Integer> department;
    /**
     *  职位
     */
    public String position;
    /**
     * 手机号
     */
    public String mobile;
    /**
     * 公司
     */
    public String company;
}
