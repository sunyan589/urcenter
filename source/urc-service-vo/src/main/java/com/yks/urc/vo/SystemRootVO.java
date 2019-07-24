/**
 * 〈一句话功能简述〉<br>
 * 〈功能权限定义相关VO〉
 *
 * @author 31076
 * @create 2018/6/5
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;
import java.util.List;

public class SystemRootVO implements Serializable {
    private static final long serialVersionUID = 280918960727443765L;
    /**
     *
     */
    public SystemVO system;

	public List<MenuVO> menu;

	public List<String> apiUrlPrefix;

}
