/*
 * 文件名：AbstractURCException.java
 * 版权：Copyright by www.youkeshu.com
 * 描述：
 * 创建人：OuJie
 * 创建时间：2018年06月15日
 * 修改理由：
 * 修改内容：
 */
package com.yks.urc.exception;

import com.yks.urc.vo.ResultVO;
import com.yks.urc.vo.helper.VoHelper;

/**
 * URC 异常抽象类
 * @author OuJie
 * @version 1.2
 * @date 2018年06月15日
 * @see AbstractURCException
 * @since JDK1.8
 */
public abstract class AbstractURCException extends RuntimeException{

    private static final long serialVersionUID = 783930107232188014L;

    private String state;
    private String msg;

    private void init(String state, String msg){
        this.state = state;
        this.msg = msg;
    }
    private void init(ErrorCode errorCode){
        init(errorCode.getState(), errorCode.getMsg());
    }

    public AbstractURCException() {
    }
    public AbstractURCException(ErrorCode errorCode){
        init(errorCode);
    }
    public AbstractURCException(ErrorCode errorCode, Throwable cause){
        super(cause);
        init(errorCode);
    }

    public AbstractURCException(String message,ErrorCode errorCode){
        super(message);
        init(errorCode);
    }

    public AbstractURCException(String state, String message) {
        super(message);
        init(state, message);
    }

    public AbstractURCException(String state, String message, Throwable cause) {
        super(message, cause);
        init(state, message);
    }

    public String getState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }
}
