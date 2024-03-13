package org.uinme.tools.azdatachecker.mapper_sqlite;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DDLMapper {
    public void createTable();
}
