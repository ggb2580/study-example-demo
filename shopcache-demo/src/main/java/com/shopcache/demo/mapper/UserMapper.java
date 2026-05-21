package com.shopcache.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shopcache.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Say my name
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
