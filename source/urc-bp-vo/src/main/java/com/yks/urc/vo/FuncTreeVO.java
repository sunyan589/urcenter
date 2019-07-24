/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author lwx
 * @create 2018/11/2
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class FuncTreeVO implements Serializable{

    private static final long serialVersionUID = 5180617940244370510L;

    public Set<String> delKeys;

    public String sysKey;

    public List<NodeVO> updateNode;

}
