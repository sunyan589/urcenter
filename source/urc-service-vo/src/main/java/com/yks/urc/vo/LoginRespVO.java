/**
 * 〈一句话功能简述〉<br>
 * 〈数据权限-授权方式〉
 *
 * @author 31076
 * @create 2018/6/5
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;
import java.util.List;

public class LoginRespVO implements Serializable {
	/**
	 * 
	 * @author panyun@youkeshu.com
	 * @date 2018年6月15日 下午4:44:22
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *
	 */
	public List<String> sysKey;
	/**
	 *
	 */
	public String ticket;
	/**
	 *
	 */
	public String userName;
	
	public String ip;
	public String personName;
}
