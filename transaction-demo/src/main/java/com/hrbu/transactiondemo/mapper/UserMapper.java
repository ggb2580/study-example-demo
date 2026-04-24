package com.hrbu.transactiondemo.mapper;

import com.hrbu.transactiondemo.entirty.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    int insert(@Param("sql") String sql);
}
