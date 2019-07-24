/**
 * 〈一句话功能简述〉<br>
 * 〈平台账号站点数据〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/7/7
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;

public class OmsPlatShopSiteVO implements Serializable {
    private static final long serialVersionUID = 4624085804592679017L;
    /**
     *  平台id
     */
    public String platformId;
    /**
     * 平台名称
     */
    public String platformName;
    /**
     * 账号id
     */
    public String shopId;
    /**
     * 账号名称
     */
    public String shopName;
    /**
     * 站点id
     */
    public String siteId;
    /**
     * 站点名称
     */
    public String siteName;
}
