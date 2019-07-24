/**
 * 〈一句话功能简述〉<br>
 * 〈店铺站点信息返回实体〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/6/21
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;

public class ShopAndSiteResp implements Serializable {
    private static final long serialVersionUID = -1694925342415533827L;
    public String shop_system;
    public String shop;
    public String platform_code;
    public String platform_name;
    public String sellerid;
    public String site_code;
    public String site_name;
}
