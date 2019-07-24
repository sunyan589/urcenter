/**
 * 〈一句话功能简述〉<br>
 * 〈用户信息〉
 *
 * @author 31076
 * @create 2018/6/5
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserVO implements Serializable {
    private static final long serialVersionUID = -7653119695391553598L;
    /**
     * 用户Id
     */
    public String userId;
    /**
     * 域账号
     */
    public String userName;
    /**
     * 姓名
     */
    public String personName;
    /**
     * 是否启用
     */
    public boolean  isActive;
    /**
     *  启用时间
     */
    public Date activeTime;
    /**
     *  启用时间 (前端)
     */
    public String activeTimeStr;
    /**
     * 性别
     */
    public String gender;
    /**
     *   电话号码
     */
    public String phoneNum;
    
    /**
     *  创建人
     */
    public String createBy;
    
    /**
     * 钉钉号在当前企业中的userId
     */
    public String dingUserId;
    /**
     * 钉钉号Id
     */
    public String dingId;
    /**
     *  生日
     */
    public Date birthday;
    /**
     *  加入集团时间
     */
    public Date joinDate;
    /**
     * 离职日期
     */
    public Date leaveDate;
    /**
     *  加入集团时间 (前端)
     */
    public String joinDateStr;
    /**
     * 离职日期 (前端)
     */
    public String leaveDateStr;
    /**
     * 工号
     */
    public String jobNumber;
    /**
     *  邮箱
     */
    public String email;
    /**
     *  职位
     */
    public String position;
    /**
     * 密码
     */
    public String pwd;
    /**
     *
     */
    public String ip;
    /**
     * 拥有哪些系统功能权限
     */
    public String sysKey;
    /**
     *  票据
     */
    public String ticket;
    /**
     *  角色
     */
    public List<RoleVO> roles;
    /**
     *  设备名称
     */
    public  String 	deviceName;
    /**
     *  登录时间
     */
    public  Long loginTime;

}
