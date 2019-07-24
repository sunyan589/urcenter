package com.yks.urc.session.bp.api;

import com.alibaba.fastjson.JSONObject;

public interface ISessionBp {
    String getOperator();
    String getStringValue(String strKey);
    Long getLong(String strKey);
}
