package com.yks.urc.ldap.bp.impl;

import com.alibaba.fastjson.JSONObject;
import com.yks.urc.entity.UserLoginLogDO;
import com.yks.urc.fw.HttpUtility2;
import com.yks.urc.user.bp.api.IUserLogBp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.yks.urc.fw.StringUtility;
import com.yks.urc.ldap.bp.api.ILdapBp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class LdapBpImpl implements ILdapBp {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	LDAPUtil2 ldapUtil;

	@Override
	public boolean validateUser(String userName, String pwd) {
		if (StringUtility.isNullOrEmpty(userName) || StringUtility.isNullOrEmpty(pwd))
		{
			return false;
		}
		if(ldapUtil.connect(userName, pwd)){
			return true;
		}
		//取消登录失败后重试
// 		int i=0;
//		while (i < 3) {
//			if (ldapUtil.connect(userName, pwd)) {
//				return true;
//			}
//			try {
//				Thread.sleep(1000);
//			}catch(Exception ex){
//				logger.error(String.format("loginFailure for sleep:%s|%s", userName, pwd), ex);
//			}
//			i++;
//		}
		logger.error(String.format("loginFailure:%s|%s", userName, pwd));
		return false;
	}
}