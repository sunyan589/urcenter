/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 31076
 * @create 2018/6/5
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;
import java.util.List;

public class FunctionVO implements Serializable{
    private static final long serialVersionUID = -7928134759539106358L;
    /**
     *
     */
    public String name;
    /**
     *
     */
    public String key;
    /**
     *
     */
    public List<FunctionVO> function;
}
