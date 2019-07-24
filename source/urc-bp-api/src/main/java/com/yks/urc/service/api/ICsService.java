package com.yks.urc.service.api;

import com.yks.urc.vo.*;

import java.util.List;
import java.util.Map;

public interface ICsService {


	/**
	 * 新增客服分组
	 *
	 * @param json
	 * @return
	 */
	ResultVO addCsUserGroup(String json);

	/**
	 * 编辑客服分组名称
	 *
	 * @param json
	 * @return
	 */
	ResultVO editCsUserGroup(String json);


	/**
	 * 删除客服分组
	 *
	 * @param json
	 * @return
	 */
	ResultVO delCsUserGroup(String json);

}
