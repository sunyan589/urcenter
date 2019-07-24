package com.yks.urc.vo.helper;

import com.yks.urc.exception.ErrorCode;
import com.yks.urc.vo.ResultVO;

public class PlsVoHelper {
    public static ResultVO getErrorResult(String strMsg) {
        return VoHelper.getResultVO(ErrorCode.E_000000,strMsg);
    }
    public static <T> ResultVO getErrorResult(String strMsg, T data) {
        return VoHelper.getResultVO(ErrorCode.E_000000, strMsg, data);
    }
}
