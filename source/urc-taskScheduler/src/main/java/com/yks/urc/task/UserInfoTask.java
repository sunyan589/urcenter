/**
 * 〈一句话功能简述〉<br>
 * 〈请求UserInfo定时任务〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/6/9
 * @since 1.0.0
 */
package com.yks.urc.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yks.urc.user.bp.impl.UserBpImpl;

@Component
public class UserInfoTask {
    private static Logger logger = LoggerFactory.getLogger(UserInfoTask.class);

    @Autowired
    private UserBpImpl userBp;

    public static final String SYSTEM = "system";
	@Scheduled(cron = "0 0 2 * * ?")
//    @Scheduled(cron = "0 0 */12 * * ?")
    public void executeGetUserInfo() {
        logger.info("开始同步用户数据");
        try {
            userBp.SynUserFromUserInfo(SYSTEM);
            logger.info("用户数据同步完成");
        } catch (Exception e) {
            logger.error("同步用户数据出错:",e);
        }
    }
}
