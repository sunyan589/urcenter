/*
 * 文件名：URCBizException.java
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
 * @see URCBizException
 * @since JDK1.8
 */
public class URCBizException extends AbstractURCException{

    public URCBizException(ErrorCode errorCode) {
        super(errorCode);
    }

    public URCBizException(String message,ErrorCode errorCode) {
        super(message,errorCode);
    }

    public URCBizException(String state, String message) {
        super(state, message);
    }
}
