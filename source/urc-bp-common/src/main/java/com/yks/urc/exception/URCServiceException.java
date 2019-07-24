/*
 * 文件名：URCServiceException.java
 * 版权：Copyright by www.youkeshu.com
 * 描述：
 * 创建人：OuJie
 * 创建时间：2018年06月15日
 * 修改理由：
 * 修改内容：
 */
package com.yks.urc.exception;

/**
 * @author OuJie
 * @version 1.2
 * @date 2018年06月15日
 * @see URCServiceException
 * @since JDK1.8
 */
public class URCServiceException extends AbstractURCException{
    private static final long serialVersionUID = -5418084777975228851L;

    public URCServiceException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public URCServiceException(String state, String message, Throwable cause) {
        super(state, message, cause);
    }
}
