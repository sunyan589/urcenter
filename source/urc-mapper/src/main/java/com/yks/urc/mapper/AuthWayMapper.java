/**
 * 〈一句话功能简述〉<br>
 * 〈授权方式〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/6/14
 * @since 1.0.0
 */
package com.yks.urc.mapper;

import com.yks.urc.entity.AuthWay;
import com.yks.urc.vo.AuthWayVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthWayMapper {
    /**
     * 通过 sysKey 获取实体对象
     * @param
     * @return
     * @Author linwanxian@youkeshu.com
     * @Date 2018/6/14 16:40
     */
    List<AuthWayVO> getAuthWayVoBySysKey(@Param("sysKey") String sysKey);

    /**
     *  通过sysKey 拿到entitiyCode
     * @param sysKey
     * @return
     */
    AuthWay getEntityBySyskey(@Param("sysKey") String sysKey);

}
