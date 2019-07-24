package com.yks.urc.ldap.bp.impl;

import com.alibaba.fastjson.JSONObject;
import com.yks.urc.entity.UserLoginLogDO;
import com.yks.urc.fw.HttpUtility2;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.user.bp.api.IUserLogBp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Component
public class LDAPUtil2 {
    @Value("${ldap.uri}")
	private String ldapURL;
	/**
	 * token 请求地址
	 */
	@Value("${userInfo.token}")
	private String getTokenUrl;

	@Autowired
	private IUserLogBp userLogBp;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public boolean connect(String username, String password) {

		DirContext ctx = null;
		UserLoginLogDO loginLog = new UserLoginLogDO();
		try {
			Hashtable<String, Object> env = new Hashtable<String, Object>();
			//一种模式，不用管，就这么写就可以了
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, username);
			env.put(Context.SECURITY_CREDENTIALS, password);
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, ldapURL);
			ctx = new InitialDirContext(env);
			//*---------记录到日志------*/
			loginLog.loginSuccess = 1;
			loginLog.userName = username;
			loginLog.remark = "ldap登录成功";
			loginLog.createTime =new Date();
			loginLog.modifiedTime =new Date();
			userLogBp.insertLog(loginLog);
			//*----------------------------*/
			return true;
		} catch (Exception e) {
			logger.error(String.format("connect:%s %s", username, password), e);
//			String exceptionString = e.getMessage();
			//出现data 532是账号有效、密码有效但是密码过期了
			if(true){
				//改为登录失败后调账号管理系统的接口 判断账号密码是否有误 正确则登录成功 错误则登录失败（更新日志）
				Map<String, String> map = new HashMap<>(15);
				map.put("username",username);
				map.put("password",password);
				String response;
				try {
					response = HttpUtility2.postForm(getTokenUrl, map, null);
				}catch (Exception e2){
					logger.error(String.format("Call the userInfo management system to get the token interface to keep the error.userName:%s,passWord:%s",username,password),e2);
					return false;
				}
				if(response != null){
					JSONObject tokenObject = StringUtility.parseString(response);
					String token = tokenObject.getString("token");
					if(token != null && !"".equals(token)){
						//*---------记录到日志------*/
						loginLog.loginSuccess = 1;
						loginLog.userName = username;
						loginLog.remark = "账号密码正确，但是ldap方式登录失败";
						loginLog.createTime =new Date();
						loginLog.modifiedTime =new Date();
						userLogBp.insertLog(loginLog);
						//*----------------------------*/
						return true;
					}
				}
			}
		}
		finally {
			try {
				if(ctx!=null) {
					ctx.close();
				}
			} catch (Exception e) {
				logger.error("LdapContext close ERROR",e);
			}
		}
		return false;
	}
}