package com.yks.urc.seq.bp.api;

/**
 * 序列生成
 * 
 * @author panyun@youkeshu.com
 * @date 2018年6月13日 上午8:37:47
 * 
 */
public interface ISeqBp {
	long getNextSeq(String strSeqName);

	/**
	 * urc_role.role_id
	 * 
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月13日 上午8:39:11
	 */
	long getNextRoleId();

	/**
	 * urc_data_rule.data_rule_id
	 * 
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月13日 上午8:42:06
	 */
	long getNextDataRuleId();

	/**
	 * urc_data_rule_templ.templ_id
	 * 
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月13日 上午8:42:28
	 */
	long getNextDataRuleTemplId();

	/**
	 * urc_data_rule_sys.data_rule_sys_id
	 * 
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月13日 上午8:42:45
	 */
	long getNextDataRuleSysId();

	/**
	 * urc_sql.sql_id
	 * 
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月13日 上午8:43:03
	 */
	long getExpressionId();

	/**
	 * urc_auth_way.auth_way_id
	 * 
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月13日 上午8:43:21
	 */
	long getNextAuthWayId();

	/**
	 * urc_data_rule_obj.data_rule_obj_id
	 * 
	 * @return
	 * @author panyun@youkeshu.com
	 * @date 2018年6月13日 上午8:43:45
	 */
	long getNextDataRuleObjId();
}
