package org.uinme.tools.azdatachecker.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestMapper {
    @Select("select * from test")
    Map<String, String> selectAll();
}
