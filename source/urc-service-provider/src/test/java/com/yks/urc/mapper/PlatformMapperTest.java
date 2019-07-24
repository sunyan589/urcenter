/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author linwanxian@youkeshu.com
 * @create 2018/7/7
 * @since 1.0.0
 */
package com.yks.urc.mapper;

import com.yks.urc.vo.OmsPlatShopSiteVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PlatformMapperTest extends BaseMapperTest {
    @Autowired
    private PlatformMapper platformMapper;

    @Test
    public void getAll(){
        platformMapper.selectAll();
    }
    @Test
    public void getPlatformShopSite(){
      List<OmsPlatShopSiteVO> omsPlatShopSiteVOS =platformMapper.getAllPlatformShopSite();
        for (OmsPlatShopSiteVO omsPlatShopSiteVO :omsPlatShopSiteVOS){
            System.out.println("===========================");
            System.out.println(omsPlatShopSiteVO.platformId);
        }
    }
}
