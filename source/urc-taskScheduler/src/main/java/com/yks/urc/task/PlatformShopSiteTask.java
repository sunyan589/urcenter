/**
 * 〈一句话功能简述〉<br>
 * 〈同步平台账号站点数据〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/7/7
 * @since 1.0.0
 */
package com.yks.urc.task;

import com.yks.urc.dataauthorization.bp.api.DataAuthorization;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PlatformShopSiteTask {
    private static Logger logger = Logger.getLogger(PlatformShopSiteTask.class);

    @Autowired
    private DataAuthorization dataAuthorization;

    public static final String SYSTEM = "system";
    @Scheduled(cron = "0 0 3 * * ?")
    public void executeGetPlatformShopSite(){
        try {
            logger.info("开始同步平台数据");
            dataAuthorization.syncPlatform(SYSTEM);
            logger.info("平台数据同步完成");
            logger.info("开始同步账号站点数据");
            dataAuthorization.syncShopSite(SYSTEM);
            logger.info("账号站点数据同步完成");
        } catch (Exception e) {
            logger.error("同步平台账号站点出错:",e);
        }
    }
}
