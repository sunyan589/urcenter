/**
 * 〈一句话功能简述〉<br>
 * 〈数据权限-可授权的字段〉
 *
 * @author 31076
 * @create 2018/6/5
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;

public class FieldVO implements Serializable {
    private static final long serialVersionUID = -4499054469616439688L;
    /**
     *
     */
    public String fieldId;
    /**
     *
     */
    public String fieldCode;
    /**
     *
     */
    public String fieldName;
    /**
     *
     */
    public String entityCode;
    /**
     *
     */
    public String dataType;
}
