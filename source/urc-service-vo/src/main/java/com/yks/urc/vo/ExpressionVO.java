/**
 * 〈一句话功能简述〉<br>
 * 〈行权限〉
 *
 * @author 31076
 * @create 2018/6/5
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;
import java.util.List;

public class ExpressionVO implements Serializable {
    private static final long serialVersionUID = 7054457903132702754L;
    /**
     *
     */
    private Long expressionId;
    /**
     * 子级where条件
     */
    private List<ExpressionVO> subWhereClause;
    /**
     * 子级条件的关系符  0-or,1-and
     */
    private Integer isAnd;
    /**
     *
     */
    private String fieldCode;
    /**
     *
     */
    private String entityCode;
    /**
     * in/eq/gt/lt
     */
    private String oper;

    /**
     * 操作值 [“v1”,”v2”]
     */
    private String operValues;
    
    /**
     * 由operValues转换而来
     */
    private List<String> operValuesArr;
    
    /**
     * 父级ID
     */
    private Long parentExpressionId;

    private String entityName;
    private String fieldName;


    public List<String> getOperValuesArr() {
		return operValuesArr;
	}

	public void setOperValuesArr(List<String> operValuesArr) {
		this.operValuesArr = operValuesArr;
	}

	public List<ExpressionVO> getSubWhereClause() {
        return subWhereClause;
    }

    public void setSubWhereClause(List<ExpressionVO> subWhereClause) {
        this.subWhereClause = subWhereClause;
    }

    public Integer getIsAnd() {
        return isAnd;
    }

    public void setIsAnd(Integer isAnd) {
        this.isAnd = isAnd;
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

    public String getOperValues() {
        return operValues;
    }

    public void setOperValues(String operValues) {
        this.operValues = operValues;
    }

    public Long getExpressionId() {
        return expressionId;
    }

    public void setExpressionId(Long expressionId) {
        this.expressionId = expressionId;
    }

    public Long getParentExpressionId() {
        return parentExpressionId;
    }

    public void setParentExpressionId(Long parentExpressionId) {
        this.parentExpressionId = parentExpressionId;
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

