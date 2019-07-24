package com.yks.urc.vo;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉
 * 列权限
 *
 * @author lvcr
 * @version 1.0
 * @date 2018/6/14 14:24
 * @see DataRuleColVO
 * @since JDK1.8
 */
public class DataRuleColVO implements Serializable {
    private static final long serialVersionUID = 5518670534908998280L;

    private Long id;

    private Long dataRuleSysId;

    private String colJson;

    private String entityCode;
    
    private String entityName;
    
    

    public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
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

    public String getColJson() {
        return colJson;
    }

    public void setColJson(String colJson) {
        this.colJson = colJson;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }
}
