package com.yks.urc.operation.bp.api;

import com.yks.urc.vo.ResultVO;

/**
 * 写操作日志
 * 
 * @author panyun@youkeshu.com
 * @date 2018年6月13日 上午9:16:12
 * 
 */
public interface IOperationBp {
	void addLog(String logger, String msg, Exception ex);
	
	/**
	 * maven最新一次打包日期
	 * @return
	 */
	ResultVO  getMavenPackageTime();
}
