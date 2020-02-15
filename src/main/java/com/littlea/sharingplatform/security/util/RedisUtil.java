package com.littlea.sharingplatform.security.util;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author chenqiting
 */
@Component
public class RedisUtil {

    @Value("${security.jwtDefaultExp}")
    Integer expTime;

    private StringRedisTemplate redisTemplate;

    public RedisUtil(StringRedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public String get(String key){
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    public boolean set(String key, String value){
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)){
            return false;
        }else {
            //放进redis数据库并设置好时间
            redisTemplate.opsForValue().set(key, value, expTime, TimeUnit.SECONDS);
            return true;
        }
    }

    public boolean delete(String key){
        return redisTemplate.delete(key);
    }
}
