/**
 * 〈一句话功能简述〉<br>
 * 〈数据权限-授权方式〉
 *
 * @author 31076
 * @create 2018/6/5
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;
import java.util.List;

public class AuthWayVO implements Serializable {
    private static final long serialVersionUID = 8673521455672654547L;
    /**
     *
     */
    public String authWayId;
    /**
     *
     */
    public String sysKey;
    public String sysName;
    public String sortIdx;
    public String entityCode;
    public String entityName;
    /**
     *
     */
    public List<DataRuleObjVO> lstObj;
}
