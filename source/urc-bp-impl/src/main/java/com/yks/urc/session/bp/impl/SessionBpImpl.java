package com.yks.urc.session.bp.impl;

import com.alibaba.fastjson.JSONObject;
import com.yks.urc.motan.MotanSession;
import com.yks.urc.session.bp.api.ISessionBp;
import org.springframework.stereotype.Component;

@Component
public class SessionBpImpl implements ISessionBp {
    @Override
    public String getOperator() {
        return MotanSession.getRequest().getOperator();
    }

    @Override
    public String getStringValue(String strKey) {
        return MotanSession.getRequest().getStringValue(strKey);
    }

    @Override
    public Long getLong(String strKey) {
        return MotanSession.getRequest().getLongValue(strKey);
    }
}
