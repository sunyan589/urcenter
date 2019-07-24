package com.yks.urc.userValidate.bp.impl;

import com.yks.urc.cache.bp.api.ICacheBp;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.mapper.UserTicketMapper;
import com.yks.urc.userValidate.bp.api.ITicketUpdateBp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TicketUpdateBpImpl implements ITicketUpdateBp {
    private static Logger logger = LoggerFactory.getLogger(TicketUpdateBpImpl.class);

    Map<String, String> mapUser = new ConcurrentHashMap<>();

    @Autowired
    ICacheBp cacheBp;

    @Override
    public void refreshExpiredTime(String userName, String ticket) {
        if (StringUtility.isNullOrEmpty(userName)) return;

        try {
            cacheBp.refreshUserExpiredTime(userName);
        }
        catch(Exception ex){
            logger.error(String.format("refresh redis ERROR:%s %s", userName, ticket), ex);
        }

        // 如果map中有，则表示还没入库，不需要处理
        if (mapUser.containsKey(userName)) return;
        mapUser.put(userName, ticket);
    }

    @Autowired
    private UserTicketMapper userTicketMapper;

    Long EXPIRE_TIME = 2 * 1 * 60 * 60 * 1000L;

    @Override
    public void dump2Db() {
        try {
            // 将map copy一份，再删除旧map元素，再写库
            if (mapUser.size() == 0) return;

            Map<String, String> mapCp = StringUtility.parseObject(StringUtility.toJSONString_NoException(mapUser), mapUser.getClass());
            if (mapCp == null || mapCp.size() == 0) return;
            for (Map.Entry<String, String> en : mapCp.entrySet()) {
                try {
                    userTicketMapper.updateExpiredTime(en.getKey(), new Date(new Date().getTime() + EXPIRE_TIME));
                    mapUser.remove(en.getKey());
                } catch (Exception ex) {
                    logger.error(String.format("%s %s", en.getKey(), en.getValue()), ex);
                }
            }
        } catch (Exception ex) {
            logger.error(StringUtility.toJSONString_NoException(mapUser), ex);
        }
    }
}
