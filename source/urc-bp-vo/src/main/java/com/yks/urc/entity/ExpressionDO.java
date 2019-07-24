package com.yks.urc.entity;

import java.util.Date;
import java.util.List;

public class ExpressionDO {
    private Long id;

    private Long expressionId;

    private Long dataRuleSysId;

    private String fieldCode;

    private String entityCode;

    private String oper;

    private Long parentExpressionId;

    private Boolean isAnd;

    private Date createTime;

    private String createBy;

    private Date modifiedTime;

    private String modifiedBy;

    private String operValues;

    private List<ExpressionDO> expressionDOList;

    private String entityName;
    private String fieldName;

    public List<ExpressionDO> getExpressionDOList() {
        return expressionDOList;
    }

    public void setExpressionDOList(List<ExpressionDO> expressionDOList) {
        this.expressionDOList = expressionDOList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExpressionId() {
        return expressionId;
    }

    public void setExpressionId(Long expressionId) {
        this.expressionId = expressionId;
    }

    public Long getDataRuleSysId() {
        return dataRuleSysId;
    }

    public void setDataRuleSysId(Long dataRuleSysId) {
        this.dataRuleSysId = dataRuleSysId;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    public Long getParentExpressionId() {
        return parentExpressionId;
    }

    public void setParentExpressionId(Long parentExpressionId) {
        this.parentExpressionId = parentExpressionId;
    }

    public Boolean getAnd() {
        return isAnd;
    }

    public void setAnd(Boolean and) {
        isAnd = and;
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

    public String getOperValues() {
        return operValues;
    }

    public void setOperValues(String operValues) {
        this.operValues = operValues;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}