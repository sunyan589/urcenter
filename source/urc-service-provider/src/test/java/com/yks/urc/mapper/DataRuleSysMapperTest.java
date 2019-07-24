package com.yks.urc.mapper;

import com.yks.urc.entity.DataRuleSysDO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 数据权限sys Mapper操作类
 *
 * @author lvcr
 * @version 1.0
 * @date 2018/6/12 8:50
 * @see DataRuleSysMapperTest
 * @since JDK1.8
 */
public class DataRuleSysMapperTest extends BaseMapperTest {

    @Autowired
    private IDataRuleSysMapper dataRuleSysMapper;




    @Test
    public void insert() {
        DataRuleSysDO dataRuleSysDO = new DataRuleSysDO();
        dataRuleSysDO.setDataRuleSysId(1L);
        dataRuleSysDO.setDataRuleId(1L);
        dataRuleSysDO.setSysKey("001");
        dataRuleSysDO.setCreateTime(new Date());
        dataRuleSysDO.setCreateBy("admin");
        int rtn = dataRuleSysMapper.insert(dataRuleSysDO);
        Assert.assertEquals(1, rtn);
    }


    @Test
    public void insertBatch() {
        List<DataRuleSysDO> dataRuleSysDOS = new ArrayList<>();
        DataRuleSysDO dataRuleSysDO = new DataRuleSysDO();
        dataRuleSysDO.setDataRuleSysId(2L);
        dataRuleSysDO.setDataRuleId(2L);
        dataRuleSysDO.setSysKey("001");
        dataRuleSysDO.setCreateTime(new Date());
        dataRuleSysDO.setCreateBy("admin");
        dataRuleSysDOS.add(dataRuleSysDO);

        DataRuleSysDO dataRuleSysDO1 = new DataRuleSysDO();
        dataRuleSysDO1.setDataRuleSysId(3L);
        dataRuleSysDO1.setDataRuleId(3L);
        dataRuleSysDO1.setSysKey("001");
        dataRuleSysDO1.setCreateTime(new Date());
        dataRuleSysDO1.setCreateBy("admin");
        dataRuleSysDOS.add(dataRuleSysDO1);

        DataRuleSysDO dataRuleSysDO2 = new DataRuleSysDO();
        dataRuleSysDO2.setDataRuleSysId(4L);
        dataRuleSysDO2.setDataRuleId(4L);
        dataRuleSysDO2.setSysKey("001");
        dataRuleSysDO2.setCreateTime(new Date());
        dataRuleSysDO2.setCreateBy("admin");
        dataRuleSysDOS.add(dataRuleSysDO2);
        int rtn = dataRuleSysMapper.insertBatch(dataRuleSysDOS);
        Assert.assertEquals(3, rtn);
    }



    @Test
    public void delRuleSysDatasByIdsAndCreatBy(){
        List<Long> lstIds = new ArrayList<>();
        lstIds.add(1529748564925000043L);
        String operator = "lvchangrong";
        dataRuleSysMapper.delRuleSysDatasByIdsAndCreatBy(lstIds,operator);
    }



    @Test
    public void getDataRuleSyAndOpers(){
        List<DataRuleSysDO> dataRuleSysDOS = dataRuleSysMapper.getDataRuleSyAndOpersById(1530265147529000007L);
        System.out.println(dataRuleSysDOS);
    }
}
