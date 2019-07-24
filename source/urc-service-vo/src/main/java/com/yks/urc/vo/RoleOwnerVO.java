/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author lwx
 * @create 2018/7/19
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;
import java.util.Date;

public class RoleOwnerVO implements Serializable{
    private static final long serialVersionUID = 1566894091146283698L;
    /**
     *
     */
    public Integer id;
    /**
     * roleId
     */
    public String roleId;
    /**
     * 角色拥有者
     */
    public String owner;
    /**
     * 创建时间
     */
    public Date createTime;

    /**
     * 创建人
     */
    public String createBy;
    /**
     * 更新时间
     */
    public Date modifiedTime;
    /**
     * 更新人
     */
    public String modifiedBy;

    public String createdTimeStr;

    public String updatedTimeStr;

}
