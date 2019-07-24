/**
 * 〈一句话功能简述〉<br>
 * 〈授权方式〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/6/14
 * @since 1.0.0
 */
package com.yks.urc.authway.bp.api;

import com.yks.urc.vo.ResultVO;
import com.yks.urc.vo.SysAuthWayVO;

import java.util.List;

public interface AuthWayBp {
    /**
     * 获取应用系统及其授权方式
     * @param operator
     * @return
     * @Author linwanxian@youkeshu.com
     * @Date 2018/6/14 14:57
     */
   List<SysAuthWayVO> getMyAuthWay(String operator);
}
