/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/7/7
 * @since 1.0.0
 */
package com.yks.urc.mapper;

import com.yks.urc.entity.ShopSiteDO;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.vo.OmsShopVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopSiteMapperTest extends BaseMapperTest {
    @Autowired
    private ShopSiteMapper shopSiteMapper;

    @Test
    public void testInset(){
        ShopSiteDO shopSiteDO =new ShopSiteDO();
        shopSiteDO.setPlatformId("1");
        shopSiteDO.setSellerId("111");
        shopSiteDO.setShop("111");
        shopSiteDO.setShopSystem("111");
        shopSiteDO.setSiteId("11111");
        shopSiteDO.setSiteName("11111");
        shopSiteDO.setCreateTime(StringUtility.getDateTimeNow());
        shopSiteDO.setModifiedTime(StringUtility.getDateTimeNow());
        shopSiteDO.setCreateBy("lin");
        shopSiteDO.setModifiedBy("lin");

        ShopSiteDO shopSiteDO1 =new ShopSiteDO();
        shopSiteDO1.setPlatformId("2");
        shopSiteDO1.setSellerId("222");
        shopSiteDO1.setShop("222");
        shopSiteDO1.setShopSystem("222");
        shopSiteDO1.setSiteId("2222");
        shopSiteDO1.setSiteName("2222");
        shopSiteDO1.setCreateTime(StringUtility.getDateTimeNow());
        shopSiteDO1.setModifiedTime(StringUtility.getDateTimeNow());
        shopSiteDO1.setCreateBy("lin");
        shopSiteDO1.setModifiedBy("lin");

        List<ShopSiteDO> list = new ArrayList<>();
        list.add(shopSiteDO);
        list.add(shopSiteDO1);
        for (ShopSiteDO shopSiteDO2 : list) {
            System.out.println(shopSiteDO2.getPlatformId());
        }

        if (list != null && list.size() != 0) {
            shopSiteMapper.insertBatchShopSite(list);
        }
    }
    @Test
    public void selectShopSiteByPlatformId(){
        String platform ="ebay";
        List<ShopSiteDO>  shopSiteDOS =shopSiteMapper.selectShopSiteByPlatformId(platform);
        for (ShopSiteDO shopSiteDO:shopSiteDOS){
            System.out.println(shopSiteDO.getPlatformId());
        }
    }
    @Test
    public void test_listShopByPage(){
        int pageNumber =1;
        int pageData =30;
        Map<String,Object> queryMap =new HashMap<>();
        queryMap.put("platformId","亚马逊");
        int currPage = Integer.valueOf(pageNumber);
        int pageSize = Integer.valueOf(pageData);
        queryMap.put("currIndex", (currPage - 1) * pageSize);
        queryMap.put("pageSize", pageSize);
       List<String> shops=  shopSiteMapper.listShopByPage(queryMap);
       shops.stream().forEach(shop -> System.out.println(shop));
       long count =shopSiteMapper.listShopByPageCount(queryMap);
        System.out.println(count);
    }
}
