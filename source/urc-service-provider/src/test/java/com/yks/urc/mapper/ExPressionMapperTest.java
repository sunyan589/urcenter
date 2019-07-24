package com.yks.urc.mapper;

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
public class ExPressionMapperTest extends BaseMapperTest {

    @Autowired
    private IExpressionMapper expressionMapper;

    @Test
    public void listExpressionDOs() {
        Long[] ids = {1L, 2L, 3L};
        List<ExpressionDO> expressionDOS = expressionMapper.listExpressionDOs(ids);
        Assert.assertNotNull(expressionDOS);
    }

    @Test
    public void insert() {
        ExpressionDO expressionDO = new ExpressionDO();
        expressionDO.setExpressionId(1L);
        expressionDO.setAnd(Boolean.TRUE);
        expressionDO.setCreateBy("admin");
        expressionDO.setCreateTime(new Date());
        expressionDO.setOper("in");
        expressionDO.setDataRuleSysId(1L);
        expressionDO.setFieldCode("fieldParm");
        int rtn = expressionMapper.insert(expressionDO);
        Assert.assertEquals(1, rtn);
    }

    @Test
    public void insertBatch() {
        List<ExpressionDO> expressionDOS = new ArrayList<>();
        ExpressionDO expressionDO = new ExpressionDO();
        expressionDO.setExpressionId(2L);
        expressionDO.setAnd(Boolean.TRUE);
        expressionDO.setCreateBy("admin");
        expressionDO.setCreateTime(new Date());
        expressionDO.setOper("in");
        expressionDO.setDataRuleSysId(2L);
        expressionDO.setFieldCode("fieldParm");
        expressionDOS.add(expressionDO);

        ExpressionDO expressionDO2 = new ExpressionDO();
        expressionDO2.setExpressionId(3L);
        expressionDO2.setAnd(Boolean.TRUE);
        expressionDO2.setCreateBy("admin");
        expressionDO2.setCreateTime(new Date());
        expressionDO2.setOper("in");
        expressionDO2.setDataRuleSysId(2L);
        expressionDO2.setFieldCode("fieldParm");
        expressionDO2.setParentExpressionId(1L);
        expressionDOS.add(expressionDO2);

        ExpressionDO expressionDO3 = new ExpressionDO();
        expressionDO3.setExpressionId(4L);
        expressionDO3.setAnd(Boolean.TRUE);
        expressionDO3.setCreateBy("admin");
        expressionDO3.setCreateTime(new Date());
        expressionDO3.setOper("in");
        expressionDO3.setDataRuleSysId(2L);
        expressionDO3.setFieldCode("fieldParm");
        expressionDO2.setParentExpressionId(2L);
        expressionDOS.add(expressionDO3);
        int rtn = expressionMapper.insertBatch(expressionDOS);
        Assert.assertEquals(3, 3);
    }


}
