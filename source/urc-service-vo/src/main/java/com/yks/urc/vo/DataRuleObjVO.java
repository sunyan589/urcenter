/**
 * 〈一句话功能简述〉<br>
 * 〈数据权限-可授权的对象〉
 *
 * @author 31076
 * @create 2018/6/5
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;
import java.util.List;

public class DataRuleObjVO implements Serializable{
    private static final long serialVersionUID = 820704124887192813L;
    /**
     *
     */
    public String dataRuleObjId;
    /**
     *
     */
    public String entityCode;
    /**
     *
     */
    public String objName;
    /**
     *
     */
    public int sortIdx;
    /**
     * 列权限fieldCode
     */
    public List<FieldVO> lstSelectField;
    /**
     * 行权限fieldCode
     */
    public List<FieldVO> lstWhereField;
}
