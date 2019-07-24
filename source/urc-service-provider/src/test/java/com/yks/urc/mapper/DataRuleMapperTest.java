package com.yks.urc.mapper;

import com.yks.urc.entity.DataRuleDO;
import com.yks.urc.entity.DataRuleSysDO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉
 *
 * @author lvcr
 * @version 1.0
 * @date 2018/6/12 8:50
 * @see DataRuleMapperTest
 * @since JDK1.8
 */
public class DataRuleMapperTest extends BaseMapperTest {

    @Autowired
    private IDataRuleMapper dataRuleMapper;


    @Test
    public void delBatchByUserNames() {
        List<String> userNames = new ArrayList<>();
        userNames.add("admin1");
        userNames.add("edison");
        int rtn = dataRuleMapper.delBatchByUserNames(userNames);
        System.out.println(rtn);
    }

    @Test
    public void insertBatch(){
        List<DataRuleDO> dataRuleDOS = new ArrayList<>();
        DataRuleDO dataRuleDO = new DataRuleDO();
        dataRuleDO.setUserName("admin1");
        dataRuleDO.setCreateTime(new Date());
        dataRuleDO.setCreateBy("admin");
        dataRuleDOS.add(dataRuleDO);


        DataRuleDO dataRuleDO2 = new DataRuleDO();
        dataRuleDO2.setUserName("admin2");
        dataRuleDO2.setCreateTime(new Date());
        dataRuleDO2.setCreateBy("admin");
        dataRuleDOS.add(dataRuleDO2);

        int rtn = dataRuleMapper.insertBatch(dataRuleDOS);
        System.out.println(rtn);
    }

   @Test
    public void getDataRuleIdsByUserName(){
        List<String> userNames = new ArrayList<>();
        userNames.add("admin22");
        userNames.add("admin");
        List<Long> ids = dataRuleMapper.getDataRuleIdsByUserName(userNames);
       System.out.println(ids);
   }


}
