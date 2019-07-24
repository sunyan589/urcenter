/*
 * 文件名：LogTask.java
 * 版权：Copyright by www.youkeshu.com
 * 描述：
 * 创建人：OuJie
 * 创建时间：2018年06月19日
 * 修改理由：
 * 修改内容：
 */
package com.yks.urc.log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yks.common.util.DateUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static com.yks.urc.log.LogLevel.*;

/**
 * @author OuJie
 * @version 1.2
 * @date 2018年06月19日
 * @see LogTask
 * @since JDK1.8
 */
public class LogTask implements Runnable{

    private final static String YYYY_MM_DD_HH_MM_SS_MS = "yyyy-MM-dd HH:mm:ss.SSS";
    private String logId;
    private JoinPoint jp;
    private Object result;
    private Date startDate;
    private Date endDate;
    private Log log;

    public LogTask(String logId, JoinPoint jp, Object result, Date startDate, Date endDate, Log log) {
        this.logId = logId;
        this.jp = jp;
        this.result = result;
        this.startDate = startDate;
        this.endDate = endDate;
        this.log = log;
    }

    private final static String getClassName(JoinPoint jp){
        return jp.getSignature().getDeclaringTypeName();
    }


    private final static String getMethodFullName(JoinPoint jp){
        Signature signature = jp.getSignature();
        String name = new StringBuilder(signature.getDeclaringTypeName())
                .append('.').append(signature.getName())
                .toString();
        return name;
    }

    private final static String getArgsJson(JoinPoint jp){
        Object argsJson = JSONArray.toJSON(jp.getArgs());
        return argsJson.toString();
    }

    private final static String formatResult(Object result){
        return JSONObject.toJSONString(result);
    }

    private final static String format(Date date){
        return DateUtil.formatDate(date, YYYY_MM_DD_HH_MM_SS_MS);
    }
    private Long getConsume(){
        if (null == startDate || null == endDate){
            return null;
        }
        return endDate.getTime()-startDate.getTime();
    }
    @Override
    public void run() {
        logPrint();
    }

    private String buildLogInf(){
        String logInfo = new StringBuilder(getMethodFullName(jp))
                .append('|').append(logId)
                .append('|').append(format(startDate))
                .append('|').append(format(endDate))
                .append('|').append(getConsume()).append("ms")
                .append('|').append(getArgsJson(jp))
                .append('|').append(formatResult(result))
                .append('|').append(log.value())
                .append('|').toString();
        return logInfo;
    }
    private void logPrint(){
        String logInfo = buildLogInf();
        Logger logger = LoggerFactory.getLogger(getClassName(jp));
        if (ERROR == log.level()){
            logger.error(logInfo);
        }else if (INFO == log.level()){
            logger.info(logInfo);
        }else if (WARN == log.level()){
            logger.warn(logInfo);
        }else if (DEBUG == log.level()){
            logger.debug(logInfo);
        }
    }

}
