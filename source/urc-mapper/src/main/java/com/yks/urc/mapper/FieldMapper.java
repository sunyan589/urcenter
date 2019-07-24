package com.yks.urc.mapper;

import com.yks.urc.entity.Field;
import org.apache.ibatis.annotations.MapKey;

import java.util.Map;

public interface FieldMapper {

    int insert(Field record);

    @MapKey("fieldCode")
    Map<String,Field> getFieldMap();

}