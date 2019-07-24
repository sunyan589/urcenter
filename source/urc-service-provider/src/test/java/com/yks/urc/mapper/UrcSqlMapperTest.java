package com.yks.urc.mapper;

import com.yks.urc.entity.DataRuleSysDO;
import com.yks.urc.entity.UrcSqlDO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 〈一句话功能简述〉
 * @author lvcr
 * @version 1.0
 * @see UrcSqlMapperTest
 * @since JDK1.8
 * @date 2018/6/12 8:50
 */
public class UrcSqlMapperTest extends BaseMapperTest {

   @Autowired
   private IUrcSqlMapper urcSqlMapper;

   @Test
   public void selectByTemplId() {
       Long[] ids = {1L,2L,3L};
       List<UrcSqlDO> urcSqlDOS = urcSqlMapper.listUrcSqlDOs(ids);
       Assert.assertNotNull(urcSqlDOS);
   }



}
