/**
 * 〈一句话功能简述〉<br>
 * 〈组织信息〉
 *
 * @author 31076
 * @create 2018/6/5
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;

public class OrgVO implements Serializable {
    private static final long serialVersionUID = -6286394698947023509L;
    /**
     *
     */
    public String dingOrgId;
    /**
     * 组织名称
     */
    public String orgName;
    
    
    public String parentDingOrgId;
}
