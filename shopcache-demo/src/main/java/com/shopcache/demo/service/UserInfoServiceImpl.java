package com.shopcache.demo.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopcache.demo.entity.User;
import com.shopcache.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Say my name
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserMapper,User> {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public User queryById(Integer id) throws InterruptedException {
        if (id == null){
            return null;
        }
        //查缓存
       return queryWithThrough(id);
    }

    private User queryWithThrough(Integer id) throws InterruptedException {
        Map<Object, Object> map = redisTemplate.opsForHash().entries("user:" + id);
        if (!map.isEmpty()){
            return BeanUtil.toBean(map, User.class);
        }


        //缓存重建
        boolean b = tryLocks(id);
        if (!b){
            Thread.sleep(300);
            return queryWithThrough(id);
        }

        //双检
        try {
            Map<Object, Object> map1 = redisTemplate.opsForHash().entries("user:" + id);
            if (!map1.isEmpty()){
                return BeanUtil.toBean(map1, User.class);
            }

            User id1 = query().eq("id", id).one();
            if (id1 == null){
                redisTemplate.opsForHash().putAll("user:"+id,new HashMap<>());
                redisTemplate.expire("user:"+id,5,TimeUnit.MINUTES);
            }

            Map<String, Object> map2 = BeanUtil.beanToMap(id1, new HashMap<>(),
                    CopyOptions.create()
                            .setIgnoreNullValue(true)
                            .setFieldValueEditor((filed,filedValue)->
                                    filedValue.toString()));
            redisTemplate.opsForHash().putAll("user:"+id1.getId(),map2);
            redisTemplate.expire("user:"+id1.getId(),30, TimeUnit.MINUTES);

            return id1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            unLock(id);
        }


    }
    /*
    * 获取互斥锁
    * */
    public boolean tryLocks(Integer id){
        Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent("lockKey:"+id, "1", 5, TimeUnit.SECONDS);
        boolean aTrue = BooleanUtil.isTrue(ifAbsent);
        return aTrue;
    }

    /*
    * 释放锁
    * */
    public void unLock(Integer id){
        redisTemplate.delete("lockKey:"+id);
    }

}
