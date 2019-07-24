/**
 * 〈一句话功能简述〉<br>
 * 〈账号基础信息〉
 *
 * @author 31076
 * @create 2018/6/5
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;
import java.util.List;

public class OmsShopVO implements Serializable {
    private static final long serialVersionUID = 6296977801408635617L;
    /**
     * 账号名称
     */
    public String shopName;
    /**
     * 账号ID
     */
    public String shopId;
    /**
     * 站点
     */
    public List<OmsSiteVO> lstSite;

}
