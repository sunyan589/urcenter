package com.yks.urc.mq.bp.api;

import com.yks.urc.vo.DataRuleVO;

import java.util.List;

public interface IMqBp {
    /**
     * 发一个用户的数据权限到mq
     *
     * @param dr
     * @author panyun@youkeshu.com
     * @date 2018年6月12日 下午2:00:58
     */
    void send2Mq(DataRuleVO dr);

    /**
     * Description:
     *
     * @param :
     * @return:
     * @auther: lvcr
     * @date: 2018/6/13 12:28
     * @see
     */
    void send2Mq(List<DataRuleVO> dataRuleVOList);
}
