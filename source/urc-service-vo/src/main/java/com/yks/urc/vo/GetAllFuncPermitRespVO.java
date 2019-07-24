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

/**
 * IUrcService.getAllFuncPermit返回VO
 * @author panyun@youkeshu.com
 * @date 2018年6月21日 下午3:20:50
 * 
 */
public class GetAllFuncPermitRespVO implements Serializable {
    /**
	 * 
	 * @author panyun@youkeshu.com
	 * @date 2018年6月21日 下午3:21:09
	 */
	private static final long serialVersionUID = -3844838872017780007L;
	
	public String funcVersion;
	/**
	 * 返回给前端使用
	 * @author panyun@youkeshu.com
	 * @date 2018年6月21日 下午3:45:41
	 */
	public List<String> lstSysRoot;
	/**
	 * 插入缓存时使用
	 * @author panyun@youkeshu.com
	 * @date 2018年6月21日 下午3:45:47
	 */
	public List<UserSysVO> lstUserSysVO;
}
