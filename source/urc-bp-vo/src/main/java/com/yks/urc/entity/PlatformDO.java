/**
 * 〈一句话功能简述〉<br>
 * 〈平台表〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/7/6
 * @since 1.0.0
 */
package com.yks.urc.entity;

import java.util.Date;

public class PlatformDO {

    private int  id;
    /**
     * 平台名称
     */
    private String platformName;
    /**
     * 平台ID
     */
    private String platformId;

    private Date createTime;

    private String createBy;

    private Date modifiedTime;

    private String modifiedBy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
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
