package com.yks.urc.entity;

import java.util.Date;

/**
 * TODO:
 *
 * @author tangjianbo
 * @date 2019/1/19 10:31
 */
public class IUrcWhiteApiVO {

    private String whiteApiUrl;
    private String createBy;
    private Date createTime;
    private Date modifiedTime;
    private String modifiedBy;

    public String getWhiteApiUrl() {
        return whiteApiUrl;
    }

    public void setWhiteApiUrl(String whiteApiUrl) {
        this.whiteApiUrl = whiteApiUrl;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
