package com.yks.urc.mapper;

import com.yks.urc.entity.Entity;
import com.yks.urc.entity.Field;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class EntityMapperTest extends BaseMapperTest{
    @Autowired
    private EntityMapper entityMapper;


    @Autowired
    private FieldMapper fieldMapper;

    @Test
    public void getEntityMap(){
       Map<String,Entity> entityMap = entityMapper.getEntityMap();
        System.out.println(entityMap);
    }

    @Test
    public void getFieldMap(){
        Map<String,Field> fieldMap = fieldMapper.getFieldMap();
        System.out.println(fieldMap);
    }
}
