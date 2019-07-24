package com.yks.urc.service;

import com.yks.urc.dingding.client.DingApiProxy;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.service.api.IOrganizationService;
import com.yks.urc.service.api.IPersonService;
import com.yks.urc.vo.PersonVO;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/*@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceProviderApplication.class)*/
public class TestOrgService extends BaseServiceTest {
	
	private static Logger LOG = LoggerFactory.getLogger(TestOrgService.class);
	
	@Autowired
	private IOrganizationService orgService;
	
	@Autowired
	private IPersonService personService;
	
	
	@Autowired
	private DingApiProxy dingApiProxy;

    @Test
    public void getAlldeptJson() throws Exception{
/*    	PersonVO personVO=new PersonVO();
    	personVO.setPersonName("aaa");
    	System.out.println(personService.getUserByUserInfo(personVO));*/
    	PersonVO person=new PersonVO();
//    	person.setOrgId("11111");
    	//person.setPhoneNum("17771054080");
    	personService.getUserByDingOrgId("11111", "0", "10");
    }
    
}
