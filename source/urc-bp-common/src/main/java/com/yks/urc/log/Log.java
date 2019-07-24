/*
 * 文件名：Log.java
 * 版权：Copyright by www.youkeshu.com
 * 描述：
 * 创建人：OuJie
 * 创建时间：2018年06月14日
 * 修改理由：
 * 修改内容：
 */
package com.yks.urc.log;

import java.lang.annotation.*;

/**
 * @author OuJie
 * @version 1.2
 * @date 2018年06月14日
 * @see Log
 * @since JDK1.8
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Log {
    LogLevel level() default LogLevel.INFO;
    String value() default "日志描述信息...";
}
