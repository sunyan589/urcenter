package com.yks.urc.mapper;

import com.yks.urc.entity.DataRuleColDO;
import com.yks.urc.entity.ExpressionDO;
import com.yks.urc.entity.UrcSqlDO;
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
 * @see ExPressionMapperTest
 * @since JDK1.8
 */
public class DataRuleColMapperTest extends BaseMapperTest {

    @Autowired
    private IDataRuleColMapper dataRuleColMapper;

    @Test
    public void insert() {
        DataRuleColDO dataRuleColDO = new DataRuleColDO();
        dataRuleColDO.setDataRuleSysId(1L);
        dataRuleColDO.setColJson("asas");
        dataRuleColDO.setEntityCode("aaa");
        dataRuleColDO.setCreateBy("admin");
        dataRuleColDO.setCreateTime(new Date());
        int rtn = dataRuleColMapper.insert(dataRuleColDO);
        Assert.assertEquals(1,rtn);
    }

    @Test
    public void insertBatch() {
        List<DataRuleColDO> dataRuleColDOS = new ArrayList<>();
        /*DataRuleColDO dataRuleColDO = new DataRuleColDO();
        dataRuleColDO.setDataRuleSysId(1L);
        dataRuleColDO.setColJson("asas");
        dataRuleColDO.setEntityCode("aaa");
        dataRuleColDO.setCreateBy("admin");
        dataRuleColDO.setCreateTime(new Date());
        dataRuleColDOS.add(dataRuleColDO);

        DataRuleColDO dataRuleColDO1 = new DataRuleColDO();
        dataRuleColDO1.setDataRuleSysId(2L);
        dataRuleColDO1.setColJson("asas");
        dataRuleColDO1.setEntityCode("aaa");
        dataRuleColDO1.setCreateBy("admin");
        dataRuleColDO1.setCreateTime(new Date());
        dataRuleColDOS.add(dataRuleColDO1);

        DataRuleColDO dataRuleColDO3 = new DataRuleColDO();
        dataRuleColDO3.setDataRuleSysId(3L);
        dataRuleColDO3.setColJson("asas");
        dataRuleColDO3.setEntityCode("aaa");
        dataRuleColDO3.setCreateBy("admin");
        dataRuleColDO3.setCreateTime(new Date());
        dataRuleColDOS.add(dataRuleColDO3);*/
        int rtn = dataRuleColMapper.insertBatch(dataRuleColDOS);
        Assert.assertEquals(3,rtn);
    }



}
