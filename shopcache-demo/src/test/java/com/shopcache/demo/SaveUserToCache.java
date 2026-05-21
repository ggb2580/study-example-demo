package com.shopcache.demo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.json.JSONUtil;
import com.shopcache.demo.entity.User;
import com.shopcache.demo.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.convert.RedisData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class SaveUserToCache {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserServiceImpl userService;

    @Test
    public void test(){
        List<User> list = userService.list();
        for (User user : list){
            Map<String, Object> map = BeanUtil.beanToMap(user, new HashMap<>(),
                    CopyOptions.create()
                            .setIgnoreNullValue(true)
                            .setFieldValueEditor((filed,filedValue)->
                                    filedValue.toString()));
            redisTemplate.opsForHash().putAll("user:"+user.getId(),map);
            redisTemplate.expire("user:"+user.getId(),30, TimeUnit.MINUTES);
        }

    }
    @Test
    public void test02(){
        Map<Object, Object> map = redisTemplate.opsForHash().entries("user:1");
        User user = BeanUtil.fillBeanWithMap(map, new User(), false);
        System.out.println(user);
    }
}
