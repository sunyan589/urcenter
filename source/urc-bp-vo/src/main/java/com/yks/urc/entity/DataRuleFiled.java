package com.yks.urc.entity;

import java.util.Date;

public class DataRuleFiled {
    private Long id;

    private Long dataRuleObjId;

    private String fieldCode;

    private String entityCode;

    private Byte clauseType;

    private Date createTime;

    private String createBy;

    private Date modifiedTime;

    private String modifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDataRuleObjId() {
        return dataRuleObjId;
    }

    public void setDataRuleObjId(Long dataRuleObjId) {
        this.dataRuleObjId = dataRuleObjId;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode == null ? null : fieldCode.trim();
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode == null ? null : entityCode.trim();
    }

    public Byte getClauseType() {
        return clauseType;
    }

    public void setClauseType(Byte clauseType) {
        this.clauseType = clauseType;
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