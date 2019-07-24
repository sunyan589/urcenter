/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author lwx
 * @create 2018/11/26
 * @since 1.0.0
 */
package com.yks.urc.user.bp.impl;

import com.yks.urc.entity.UserLoginLogDO;
import com.yks.urc.mapper.IUserLoginLogMapper;
import com.yks.urc.user.bp.api.IUserLogBp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class UserLogBpImpl implements IUserLogBp {

    private static Logger logger = LoggerFactory.getLogger(UserLogBpImpl.class);

    ExecutorService service = Executors.newFixedThreadPool(3);

    @Autowired
    private IUserLoginLogMapper logMapper;


    @Override
    public void insertLog(UserLoginLogDO logDO) {
        // 异步入库
        try {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        logMapper.insertLogs(logDO);
                    } catch (Exception e) {
                        logger.error("日志任务执行异常", e);
                    }
                }
            });

            // service.awaitTermination(5,TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("线程池关闭出错,出错原因", e);
        }
    }
}
