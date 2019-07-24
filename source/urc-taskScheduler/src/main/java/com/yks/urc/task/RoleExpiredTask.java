package com.yks.urc.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yks.urc.dingding.client.DingApiProxy;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.service.api.IPersonService;
import com.yks.urc.service.api.IRoleService;
import com.yks.urc.service.impl.PersonServiceImpl;

/**
 * 角色过期处理
 * 
 * @author panyun@youkeshu.com
 * @date 2018年6月15日 上午10:55:04
 * 
 */
@Component
public class RoleExpiredTask {
	private static final Logger logger = LoggerFactory.getLogger(RoleExpiredTask.class);

	@Autowired
	private IRoleService roleSevice;
	
//	@Scheduled(cron = "0/10 * * * * ?")
	// 每天凌晨3点
	@Scheduled(cron = "0 0 4 * * ?")
	public void executeRoleExpiredTask() {
		logger.info("角色过期检查START");
		roleSevice.handleExpiredRole();
		logger.info("角色过期检查END");
//		System.out.println(StringUtility.getDateTime_yyyyMMddHHmmssSSS(new Date()) + " 角色过期检查");
	}
	

	@Scheduled(cron = "0/59 * * * * ?")
	public void testTask() {
		logger.info("testTask");
	}
}
