/**
 * 〈一句话功能简述〉<br>
 * 〈授权方式Vo〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/6/14
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;
import java.util.List;

public class SysAuthWayVO implements Serializable {
    private static final long serialVersionUID = -1403316581523660098L;
    /**
     * 业务系统key
     */
    public String sysKey;
    /**
     * 业务系统名称
     */
    public String sysName;
    /**
     *
     */
    public String sortIdx;
    /**
     * 实体vo
     */
    public List<AuthWayVO> lstEntity;


}
