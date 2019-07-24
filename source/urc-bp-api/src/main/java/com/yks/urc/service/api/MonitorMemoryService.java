/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/7/2
 * @since 1.0.0
 */
package com.yks.urc.service.api;

import com.yks.urc.vo.ResultVO;

public interface MonitorMemoryService {

    ResultVO startMonitor();

    ResultVO endMonitor();
}
