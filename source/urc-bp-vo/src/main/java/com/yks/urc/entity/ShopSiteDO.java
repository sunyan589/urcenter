/**
 * 〈一句话功能简述〉<br>
 * 〈账号站点表〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/7/6
 * @since 1.0.0
 */
package com.yks.urc.entity;

import java.util.Date;

public class ShopSiteDO {

    private int id;
    /**
     * 平台Id
     */
    private String platformId;
    /**
     * 账号
     */
    private String shopSystem;
    /**
     * 账号名称
     */
    private String shop;

    /**
     * 账号ID
     */
    private String sellerId;
    /**
     * 站点名称
     */
    private String siteName;
    /**
     * 站点ID
     */
    private String siteId;

    private Date createTime;

    private String createBy;

    private Date modifiedTime;

    private String modifiedBy;

    private int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShopSystem() {
        return shopSystem;
    }

    public void setShopSystem(String shopSystem) {
        this.shopSystem = shopSystem;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
