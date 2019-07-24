/**
 * 〈一句话功能简述〉<br>
 * 〈行列权限〉
 *
 * @author 31076
 * @create 2018/6/5
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;
import java.util.List;

public class UrcSqlVO implements Serializable {
    private static final long serialVersionUID = -451388606819738586L;
    /**
     *
     */
    public String sqlId;
    /**
     *
     */
    public String entity_code;
    /**
     * 列权限,不显示的fieldCode
     */
    public List<String> hiddenFileds;
    /**
     * 行权限
     */
    public ExpressionVO whereClause;

}
