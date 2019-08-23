package com.mrwan.pigcount;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

/**
 * @Author: litong
 * @Description:
 * @Date: Created in 10:59 2018/1/12
 * @Modified By:
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {


    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        RedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jdkSerializationRedisSerializer);
        template.setValueSerializer(jdkSerializationRedisSerializer);
        template.setConnectionFactory(factory);
        template.afterPropertiesSet();
        return template;
    }

}