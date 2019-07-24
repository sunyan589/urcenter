/*
 * 文件名：ProviderExceptionFilter.java
 * 版权：Copyright by www.youkeshu.com
 * 描述：
 * 创建人：OuJie
 * 创建时间：2018年06月15日
 * 修改理由：
 * 修改内容：
 */
package com.yks.urc.motan;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.core.extension.Activation;
import com.weibo.api.motan.core.extension.SpiMeta;
import com.weibo.api.motan.exception.MotanAbstractException;
import com.weibo.api.motan.exception.MotanBizException;
import com.weibo.api.motan.filter.Filter;
import com.weibo.api.motan.rpc.Caller;
import com.weibo.api.motan.rpc.DefaultResponse;
import com.weibo.api.motan.rpc.Request;
import com.weibo.api.motan.rpc.Response;
import com.yks.urc.exception.AbstractURCException;
import com.yks.urc.exception.ErrorCode;
import com.yks.urc.exception.URCServiceException;
import com.yks.urc.vo.ResultVO;
import com.yks.urc.vo.helper.VoHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author OuJie
 * @version 1.2
 * @date 2018年06月15日
 * @see ProviderExceptionFilter
 * @since JDK1.8
 */
@SpiMeta(name = "yks-exception-filter")
@Activation(sequence = 99, key = {MotanConstants.NODE_TYPE_SERVICE})
public class ProviderExceptionFilter implements Filter {
    private final static Logger log = LoggerFactory.getLogger(ProviderExceptionFilter.class);
    @Override
    public Response filter(Caller<?> caller, Request request) {
        Response response;
        try {
            response = caller.call(request);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return buildExceptionResponse(e);
        }
        if (response.getException() != null){
            return buildExceptionResponse(response);
        }
        return response;
    }

    private Response buildExceptionResponse(Exception e){
        DefaultResponse response = new DefaultResponse();
        AbstractURCException ex;
        //URC自定义异常
        if (e instanceof MotanAbstractException){
            ex = getURCException((MotanAbstractException) e);
        }else{//系统未知异常
            ex = new URCServiceException(ErrorCode.E_000007, e);
            log.error("未知异常",ex);
        }
        response.setValue(formatError(ex));
        return response;
    }
    private AbstractURCException getURCException(MotanAbstractException e){
        Throwable cause = e.getCause();
        if (cause instanceof AbstractURCException){
            return (AbstractURCException) cause;
        }else{
            log.error("未知异常",e);
            return new URCServiceException(ErrorCode.E_000007, e);
        }
    }

    private ResultVO formatError(AbstractURCException e){
        return VoHelper.getErrorResult(e.getState(), e.getMsg());
    }

    private Response buildExceptionResponse(Response response){
        return buildExceptionResponse(response.getException());
    }
}
