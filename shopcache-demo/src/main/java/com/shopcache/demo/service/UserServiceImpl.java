package com.shopcache.demo.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopcache.demo.entity.User;
import com.shopcache.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Say my name
 */
/*
* 代码存在问题：递归重试不合理，容易造成栈内存溢出
* 没有释放锁，可能造成死锁（已解决√ 加入了try-catch-finally）确保最终释放资源
* 缓存空值没有生效，在查询缓存时应该进行判断是否为空值，既然存在这个key,那么就说明这个值是没有问题的，可以传递给前端，
* 增加双重检查，避免锁被获取后重复查询数据库。
* */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> {
@Autowired
private StringRedisTemplate redisTemplate;


    public User queryById(Integer id) throws InterruptedException {
        //缓存穿透 ： 布隆过滤器 缓存空值
        if (id == null){
            return null;
        }
        return queryWithThrough(id);
    }

    /*
    * 缓存穿透
    * */
    private User queryWithThrough(Integer id)  {
        //先查询缓存
        Map<Object, Object> map = redisTemplate.opsForHash().entries("user:" + id);
        /*
        * 缓存为空
        * */
        if (!map.isEmpty()){
            User user = BeanUtil.fillBeanWithMap(map, new User(), false);
            return user;
        }
        if (map == null){
            return null;
        }
        /*
        * 获取互斥锁
        * */

        try {
            boolean b = tryLock("lockKey:" + id);
            if (!b){
                /*
                *没有获取到锁休眠等待300毫秒
                * */
                Thread.sleep(300);
                /*
                * 重试
                * */
                return queryWithThrough(id);
            }

            /*
            * 双重检查：可能我们获取了锁，但是其他线程已经对缓存进行了重建
            * */
            Map<Object, Object> maps = redisTemplate.opsForHash().entries("user:" + id);
            /*
             * 缓存为空
             * */
            if (!maps.isEmpty()){
                User user = BeanUtil.fillBeanWithMap(maps, new User(), false);
                return user;
            }



            /*
            * 缓存重建
            * */
            User id1 = query().eq("id", id).one();
            if (id1 == null){
                redisTemplate.opsForHash().putAll("user:"+id,new HashMap<>());
                return null;
            }else{
                Map<String, Object> map1 = BeanUtil.beanToMap(id1, new HashMap<>(),
                        CopyOptions.create()
                                .setIgnoreNullValue(true)
                                .setFieldValueEditor((filed,filedValue)->
                                        filedValue.toString()));
                redisTemplate.opsForHash().putAll("user:"+id1.getId(),map1);
                redisTemplate.expire("user:"+id1.getId(),30, TimeUnit.MINUTES);
            }
            return id1;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            unLock("lockKey:" + id);
        }
    }

    /*
    * 获取锁
    * */
    private boolean tryLock(String lockKey) {
        Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent(lockKey, "1", 3, TimeUnit.SECONDS);
        boolean aTrue = BooleanUtil.isTrue(ifAbsent);
        return aTrue;

    }
    /*
    * 释放锁
    * */
    private void unLock(String lockKey){
        redisTemplate.delete(lockKey);
    }
}
