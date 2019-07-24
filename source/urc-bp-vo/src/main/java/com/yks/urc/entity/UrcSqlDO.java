package com.yks.urc.entity;

import java.util.Date;
import java.util.List;

public class UrcSqlDO {
    private Long id;

    private Long sqlId;

    private Long dataRuleSysId;

    private String entityCode;

    private String hiddenFields;

    private Date createTime;

    private String createBy;

    private Date modifiedTime;

    private String modifiedBy;

    private List<ExpressionDO> expressionDOS;

    public List<ExpressionDO> getExpressionDOS() {
        return expressionDOS;
    }

    public void setExpressionDOS(List<ExpressionDO> expressionDOS) {
        this.expressionDOS = expressionDOS;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSqlId() {
        return sqlId;
    }

    public void setSqlId(Long sqlId) {
        this.sqlId = sqlId;
    }

    public Long getDataRuleSysId() {
        return dataRuleSysId;
    }

    public void setDataRuleSysId(Long dataRuleSysId) {
        this.dataRuleSysId = dataRuleSysId;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode == null ? null : entityCode.trim();
    }

    public String getHiddenFields() {
        return hiddenFields;
    }

    public void setHiddenFields(String hiddenFields) {
        this.hiddenFields = hiddenFields == null ? null : hiddenFields.trim();
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
}