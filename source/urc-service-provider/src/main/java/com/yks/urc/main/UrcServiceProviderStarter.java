package com.yks.urc.main;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;
import com.yks.urc.constant.Constant;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

 /**
  * 〈一句话功能简述〉 
  *  启动类
  * @author lvcr
  * @version 1.0 
  * @see UrcServiceProviderStarter 
  * @since JDK1.8
  * @date 2018/6/11 9:05
  */ 
public class UrcServiceProviderStarter {

    private static final Logger LOGGER = Logger.getLogger(UrcServiceProviderStarter.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                new String[]{Constant.SERVICE_STARTER_CONFIG});

        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);

        LOGGER.info("Urc Service Provider start...");
    }
}
