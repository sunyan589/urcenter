/**
 * 〈一句话功能简述〉<br>
 * 〈监控内存〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/7/2
 * @since 1.0.0
 */
package com.yks.urc.service.impl;

import com.yks.urc.monitormemory.bp.api.MonitorMemoryBp;
import com.yks.urc.service.api.MonitorMemoryService;
import com.yks.urc.vo.ResultVO;
import com.yks.urc.vo.helper.VoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MonitorMemoryServiceImpl implements MonitorMemoryService {
    @Autowired
    private MonitorMemoryBp memoryBp;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO startMonitor() {
        try {
            memoryBp.startMonitor();
            return VoHelper.getSuccessResult();
        } catch (Exception e) {
            return VoHelper.getErrorResult();
        }
    }

    @Override
    public ResultVO endMonitor() {
        try {
            memoryBp.endMonitor();
            return VoHelper.getSuccessResult();
        } catch (Exception e) {
            return VoHelper.getErrorResult();
        }
    }
}
