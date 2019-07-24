/**
 * 〈一句话功能简述〉<br>
 * 〈分页请求〉
 *
 * @author 31076
 * @create 2018/6/5
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;

public class PageParamVO implements Serializable {
    private static final long serialVersionUID = -5067413972363301549L;
    /**
     *
     */
    public int  pageSize;
    /**
     *
     */
    public int  pageIdx;
}
