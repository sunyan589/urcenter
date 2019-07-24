package com.yks.urc.task;

import com.yks.urc.userValidate.bp.api.ITicketUpdateBp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 更新 urc_user_ticket 的过期时间
 *
 * @return
 * @Author panyun@youkeshu.com
 * @Date 2019/1/9 9:43
 */
@Component
public class TicketDelayTask {
    private static Logger logger = LoggerFactory.getLogger(TicketDelayTask.class);

    @Autowired
    private ITicketUpdateBp ticketUpdateBp;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void doTicketDelay() {
        logger.info("START doTicketDelay");
        ticketUpdateBp.dump2Db();
        logger.info("END doTicketDelay");
    }
}
