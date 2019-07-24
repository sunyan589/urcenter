package com.yks.urc.entity;

import java.util.Date;
import java.util.List;

public class DataRuleSysDO {
    private Long id;

    private Long dataRuleSysId;

    private Long dataRuleId;

    private String sysKey;

    private String sysName;

    private String userName;

    private Date createTime;

    private String createBy;

    private Date modifiedTime;

    private String modifiedBy;

    private List<DataRuleColDO> dataRuleColDOS;

    private List<ExpressionDO> expressionDOS;

    private ExpressionDO parentExpressionDO;

    public ExpressionDO getParentExpressionDO() {
        return parentExpressionDO;
    }

    public void setParentExpressionDO(ExpressionDO parentExpressionDO) {
        this.parentExpressionDO = parentExpressionDO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDataRuleSysId() {
        return dataRuleSysId;
    }

    public void setDataRuleSysId(Long dataRuleSysId) {
        this.dataRuleSysId = dataRuleSysId;
    }

    public Long getDataRuleId() {
        return dataRuleId;
    }

    public void setDataRuleId(Long dataRuleId) {
        this.dataRuleId = dataRuleId;
    }

    public String getSysKey() {
        return sysKey;
    }

    public void setSysKey(String sysKey) {
        this.sysKey = sysKey == null ? null : sysKey.trim();
    }
    public String getSysName() {
        return sysName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
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
        this.createBy = createBy == null ? null : createBy.trim();
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
        this.modifiedBy = modifiedBy == null ? null : modifiedBy.trim();
    }

    public List<DataRuleColDO> getDataRuleColDOS() {
        return dataRuleColDOS;
    }

    public void setDataRuleColDOS(List<DataRuleColDO> dataRuleColDOS) {
        this.dataRuleColDOS = dataRuleColDOS;
    }

    public List<ExpressionDO> getExpressionDOS() {
        return expressionDOS;
    }

    public void setExpressionDOS(List<ExpressionDO> expressionDOS) {
        this.expressionDOS = expressionDOS;
    }
}