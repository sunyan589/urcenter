package com.yks.demo;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import com.weibo.api.motan.util.MotanSwitcherUtil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ImportResource(locations={"classpath*:spring/motan_client_local_test.xml"})
public class DemoClientApplication {    
	public static void main(String[] args) {
		SpringApplication.run(DemoClientApplication.class, args);
		MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
		
	}
}
