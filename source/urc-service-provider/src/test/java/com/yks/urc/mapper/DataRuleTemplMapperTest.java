package com.yks.urc.mapper;

import com.yks.urc.entity.DataRuleTemplDO;
import com.yks.urc.entity.RoleDO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 〈一句话功能简述〉
 * 功能权限模板Mapper操作类
 *
 * @author lvcr
 * @version 1.0
 * @date 2018/6/12 8:50
 * @see DataRuleTemplMapperTest
 * @since JDK1.8
 */
public class DataRuleTemplMapperTest extends BaseMapperTest {

    @Autowired
    private IDataRuleTemplMapper dataRuleTemplMapper;

    @Test
    public void selectByTemplId() {
        DataRuleTemplDO dataRuleTemplDO = dataRuleTemplMapper.selectByTemplId(1L,"admin");
        Assert.assertNotNull(dataRuleTemplDO);
    }

    @Test
    public void getCounts() {
        Map<String, Object> map = new HashMap<>();
        String[] templNames = {"数据权限模板1", "数据权限模板2", "数据权限模板8", "数据权限模板9", "数据权限模板5", "数据权限模板6", "数据权限模板7"};
        map.put("createBy", "edison");
        map.put("templNames", templNames);
        Long counts = dataRuleTemplMapper.getCounts(map);
        System.out.println(counts);
    }

    @Test
    public void listDataRuleTemplDOsByPage() {
        //通过接收currPage参数表示显示第几页的数据，pageSize表示每页显示的数据条数。
        int currPage = 1;
        int pageSize = 3;

        Map<String, Object> map = new HashMap<>();
        String[] templNames = {"数据权限模板1", "数据权限模板2", "数据权限模板8", "数据权限模板9", "数据权限模板5", "数据权限模板6", "数据权限模板7"};
        map.put("createBy", "edison");
        map.put("templNames", templNames);
        map.put("currIndex", (currPage - 1) * pageSize);
        map.put("pageSize", pageSize);

        List<DataRuleTemplDO> dataRuleTemplDOS = dataRuleTemplMapper.listDataRuleTemplDOsByPage(map);
        System.out.println(dataRuleTemplDOS);
    }

    @Test
    public void insert(){
        DataRuleTemplDO dataRuleTemplDO = new DataRuleTemplDO();
        dataRuleTemplDO.setTemplName("自己创建的模板");
        dataRuleTemplDO.setUserName("admin");
        dataRuleTemplDO.setCreateTime(new Date());
        dataRuleTemplDO.setCreateBy("admin");
        dataRuleTemplDO.setRemark("模板备注");
        dataRuleTemplDO.setTemplId(1L);
        int rtn = dataRuleTemplMapper.insert(dataRuleTemplDO);
        Assert.assertEquals(1,rtn);
    }


    @Test
    public void deleteByTemplId(){
        int rtn = dataRuleTemplMapper.deleteByTemplId(1L);
        System.out.println(rtn);

    }

    @Test
    public void updateDataRuleTemplById(){
        DataRuleTemplDO dataRuleTemplDO = new DataRuleTemplDO();
        dataRuleTemplDO.setTemplId(3L);
        dataRuleTemplDO.setTemplName("数据权限模3");
        dataRuleTemplDO.setRemark("备注信息");
        dataRuleTemplDO.setModifiedBy("admin12");
        dataRuleTemplDO.setModifiedTime(new Date());
        dataRuleTemplMapper.updateDataRuleTemplById(dataRuleTemplDO);
    }

    @Test
    public void delTemplByIdsAndCreatBy(){
        List<Long> lstTemplId = new ArrayList<>();
        lstTemplId.add(5L);
        String operator="lvchangrong";
        dataRuleTemplMapper.delTemplByIdsAndCreatBy(lstTemplId,operator);
    }


}
