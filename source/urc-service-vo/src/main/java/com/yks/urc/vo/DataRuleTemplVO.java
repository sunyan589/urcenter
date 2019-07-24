/**
 * 〈一句话功能简述〉<br>
 * 〈数据权限模板〉
 *
 * @author 31076
 * @create 2018/6/5
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DataRuleTemplVO implements Serializable {



    private static final long serialVersionUID = 824547161363763919L;
    /**
     * 模板ID
     */
    private String templId;
    /**
     *  创建人
     */
    public String userName;
    /**
     * 模板名称
     */
    public String templName;
    /**
     * 备注
     */
    public String remark;
    /**
     * 一个系统一个VO
     */
    public List<DataRuleSysVO> lstDataRuleSys;

    /**
     * 创建时间
     */
    private Date createTime;
    private String createTimeStr;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 更新时间
     */
    private Date modifiedTime;
    private String modifiedTimeStr;
    /**
     * 更新人
     */
    private String modifiedBy;

    public String getTemplId() {
        return templId;
    }

    public void setTemplId(String templId) {
        this.templId = templId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTemplName() {
        return templName;
    }

    public void setTemplName(String templName) {
        this.templName = templName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<DataRuleSysVO> getLstDataRuleSys() {
        return lstDataRuleSys;
    }

    public void setLstDataRuleSys(List<DataRuleSysVO> lstDataRuleSys) {
        this.lstDataRuleSys = lstDataRuleSys;
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

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getModifiedTimeStr() {
        return modifiedTimeStr;
    }

    public void setModifiedTimeStr(String modifiedTimeStr) {
        this.modifiedTimeStr = modifiedTimeStr;
    }
}
