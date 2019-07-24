package com.yks.urc.motan;

import com.alibaba.fastjson.JSONObject;
import com.yks.urc.fw.StringUtility;

import java.util.Map;

public class MotanSession {
    private static final ThreadLocal<MotanRequest> curOperator = new ThreadLocal<MotanRequest>();

    public static void setRequest(MotanRequest operator) {
        curOperator.set(operator);
    }

    public static MotanRequest getRequest() {
        MotanRequest req = curOperator.get();
        if (req == null) return MotanRequest.Empty;
        return req;
    }

    public static void initialSession(Object arg) {
        if (arg == null) return;
        if (arg instanceof Map) {
            Map<String, String> mapArg = (Map<String, String>) arg;
            MotanRequest req = new MotanRequest();
            req.setMapArg(mapArg);
            MotanSession.setRequest(req);
        } else if (arg instanceof String) {
            JSONObject jo = StringUtility.parseString((String) arg);
            MotanRequest req = new MotanRequest();
            req.setJSONObjectArg(jo);
            MotanSession.setRequest(req);
        }
    }
}
