/**
 * 〈一句话功能简述〉<br>
 * 〈平台基础信息〉
 *
 * @author 31076
 * @create 2018/6/5
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;
import java.util.List;

public class OmsPlatformVO implements Serializable {
    private static final long serialVersionUID = 7915177115475857675L;
    /**
     * 平台名称
     */
    public String platformName;
    /**
     * 平台ID
     */
    public String platformId;
    /**
     * 账号
     */
    public List<OmsShopVO> lstShop;
}
