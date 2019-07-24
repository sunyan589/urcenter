package com.yks.urc.dingding;

import java.util.List;

import org.junit.Test;

import com.yks.urc.dingding.client.DingApiProxy;
import com.yks.urc.dingding.client.vo.DingDeptVO;
import com.yks.urc.dingding.client.vo.DingUserVO;
import com.yks.urc.dingding.proxy.ApiProxy;
/**
 * @Author: wujianghui@youkeshu.com
 * @Date: 2018/6/6 8:59
 */
public class TestGetAccessToken {
    ApiProxy proxy=new ApiProxy();



	@Test
	public void  testGetAccessToken(){
		try {
            DingApiProxy p = proxy.getDingProxy();
			String sss=p.getDingAccessToken();
			System.out.println(sss);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Test
	public void  getDingAllDept(){
		try {
            DingApiProxy p = proxy.getDingProxy();
			List<DingDeptVO> dd = p.getDingAllSubDept("64195047");
			System.out.println(dd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Test
	public void  getDingMemberByDepId(){
		try {
            DingApiProxy p = proxy.getDingProxy();
			List<DingUserVO> dd =p.getDingMemberByDepId("64195047");
			System.out.println(dd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
