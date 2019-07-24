/**
 * 〈一句话功能简述〉<br>
 * 〈数据授权平台店铺站点Vo〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/6/12
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;

public class DataAuthorizationVo implements Serializable {
    private static final long serialVersionUID = 2104167697048097420L;
    /**
     *  请求店铺信息的 参数 平台
     */
    public String platform;
    /**
     *  请求店铺信息的 参数  站点
     */
    public String site;
    /**
     *  请求店铺信息的 参数 店铺名称
     */
    public String shop_system;
    /**
     * 卖家id
     */
    public String sellerid;
    /**
     * 返回状态码
     */
    public int state;
    /**
     * 平台编码
     */
    public String code;
    /**
     * 平台名称
     */
    public String name;
    /**
     *  站点编码
     */
    public String site_code;
    /**
     * 站点名称
     */
    public String site_name;
}
