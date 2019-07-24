package com.yks.urc.motan;

import com.alibaba.fastjson.JSONObject;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.fw.constant.StringConstant;

import java.util.Map;

public class MotanRequest {
    public static MotanRequest Empty = new MotanRequest();
    JSONObject _jsonArg;

    public JSONObject getJSONObjectArg() {
        return _jsonArg;
    }

    public void setJSONObjectArg(JSONObject jsonArg) {
        _jsonArg = jsonArg;
    }

    private Map<String, String> _mapArg;

    public Map<String, String> getMapArg() {
        return _mapArg;
    }

    public void setMapArg(Map<String, String> mapArg) {
        this._mapArg = mapArg;
    }

    public String getOperator() {
        if (_mapArg != null) return _mapArg.get(StringConstant.operator);
        if (_jsonArg != null) return _jsonArg.getString(StringConstant.operator);
        return StringUtility.Empty;
    }

    public String getStringValue(String strKey) {
        if (_jsonArg != null) return _jsonArg.getString(strKey);
        if (_mapArg != null) return _mapArg.get(strKey);
        return StringUtility.Empty;
    }

    public Long getLongValue(String strKey) {
        if (_jsonArg != null) return _jsonArg.getLong(strKey);
        if (_mapArg != null) return StringUtility.convertToLong(_mapArg.get(strKey), null);
        return null;
    }
}
