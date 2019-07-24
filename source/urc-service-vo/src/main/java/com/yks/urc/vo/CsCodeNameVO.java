/**
 * 〈一句话功能简述〉<br>
 * 〈平台基础信息〉
 *
 * @author 31076
 * @create 2018/6/5
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;
import java.util.List;

public class CsCodeNameVO implements Serializable {
    private static final long serialVersionUID = 7915177115475857675L;

    public String code;

    public String name;


    public String type;

    public List<CsCodeNameVO> children;
}
