package com.yks.urc.fw;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

import com.alibaba.fastjson.serializer.JSONSerializer;

/**
 * 日期序列化成字符串
 * 
 * @author Administrator
 * 
 */
public class MyDateSerializer implements com.alibaba.fastjson.serializer.ObjectSerializer {

	@Override
	public void write(JSONSerializer arg0, Object value, Object arg2, Type arg3, int arg4) throws IOException {
		if (value instanceof Boolean) {
			arg0.getWriter().write(String.format("\"%s\"", (Boolean) value ? 1 : 0));
		} else {
			arg0.getWriter().write(String.format("\"%s\"", StringUtility.getDateTime_yyyyMMddHHmmssSSS((Date) value)));
		}
	}
}