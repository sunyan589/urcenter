package com.yks.urc.entity;

import java.util.Date;

 /**
  * 〈一句话功能简述〉 
  * 〈功能详细描述〉 
  * @author lvcr
  * @version 1.0 
  * @see DataRuleColDO 
  * @since JDK1.8
  * @date 2018/6/14 14:57
  */ 
public class DataRuleColDO {
    private Long id;

    private Long dataRuleSysId;

    private String entityCode;

    private Date createTime;

    private String createBy;

    private Date modifiedTime;

    private String modifiedBy;

    private String colJson;

    private String entityName;

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

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode == null ? null : entityCode.trim();
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

    public String getColJson() {
        return colJson;
    }

    public void setColJson(String colJson) {
        this.colJson = colJson == null ? null : colJson.trim();
    }

     public String getEntityName() {
         return entityName;
     }

     public void setEntityName(String entityName) {
         this.entityName = entityName;
     }
 }