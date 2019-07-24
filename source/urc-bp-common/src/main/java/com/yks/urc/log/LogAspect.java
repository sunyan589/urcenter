/*
 * 文件名：LogAspect.java
 * 版权：Copyright by www.youkeshu.com
 * 描述：
 * 创建人：OuJie
 * 创建时间：2018年06月14日
 * 修改理由：
 * 修改内容：
 */
package com.yks.urc.log;

import com.yks.urc.exception.AbstractURCException;
import com.yks.urc.exception.ErrorCode;
import com.yks.urc.exception.URCServiceException;
import com.yks.urc.fw.StringUtility;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * 通用日志注解拦截器
 * 实现异步打印日志功能
 * TODO dao层使用注解无效
 * @author OuJie
 * @version 1.2
 * @date 2018年06月14日
 * @see LogAspect
 * @since JDK1.8
 */
public class LogAspect implements InitializingBean, DisposableBean {
    private final static Logger log = LoggerFactory.getLogger(LogAspect.class);
    /**
     * 默认日志异步线程数量
     */
    private int logThreads = 3;
    /**
     * 保存logId属性，同一请求操作共享logId
     */
    private ThreadLocal<String> logIdLocal = new ThreadLocal<>();

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public void setLogThreads(int logThreads) {
        this.logThreads = logThreads;
    }

    private ExecutorService executorService;

    public Object around(ProceedingJoinPoint jp, Log log){
        Date startDate = new Date(),
                endDate = null;
        Object result = null;
        try{
            result = jp.proceed();
            endDate = new Date();
            return result;
        }catch (AbstractURCException e){
            throw e;
        }catch (Throwable e){
            throw new URCServiceException(ErrorCode.E_000007, e);
        }finally {
            executorService.submit(new LogTask(getLogId(),jp,result,startDate,endDate, log));
        }
    }
    @Deprecated
    public void after(JoinPoint jp,Log log, Object result) {
        executorService.submit(new LogTask(getLogId(), jp, result,null,null, log));
    }

    @Override
    public void afterPropertiesSet(){
        log.info("日志拦截器初始化...");

        executorService = new ThreadPoolExecutor(logThreads, logThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    @Override
    public void destroy() {
        //销毁内部日志线程池
        executorService.shutdown();
    }

    private String getLogId(){
        if (null == logIdLocal.get()){
            Lock lock = this.lock.writeLock();
            lock.lock();
            try{
                if (null == logIdLocal.get()){
                    logIdLocal.set(buildLogId());
                }
            }finally {
                lock.unlock();
            }
        }
        return logIdLocal.get();
    }

    private String buildLogId() {
        return StringUtility.getUUID();
    }
}
