package com.crowdfunding.farming.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author: kevin
 * @create: 11/12/19 09:46
 * @description:
 **/
@EnableCaching
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {
  /**
   * 配置自定义 redisTemplate
   *
   * @param connectionFactory
   * @return
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
    final RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);
    template.setValueSerializer(genericJackson2JsonRedisSerializer());
    //使用StringRedisSerializer来序列化和反序列化redis的key值
    template.setKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setHashValueSerializer(genericJackson2JsonRedisSerializer());
    template.afterPropertiesSet();
    return template;
  }

  /**
   * json序列化
   *
   * @return
   */
  @Bean
  public RedisSerializer genericJackson2JsonRedisSerializer(){
    ObjectMapper om = new ObjectMapper();
    om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    om.registerModule(new JavaTimeModule());
    om.registerModule((new SimpleModule()));
    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
    GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(om);
    return serializer;
  }

  /**
   * 配置缓存管理器
   *
   * @param redisConnectionFactory
   * @return
   */
  @Bean
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
    RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
    //设置缓存的默认过期时间，也是使用Duration设置
    config.entryTtl(Duration.ofDays(1))
        //设置 key为string序列化
        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
        //设置value为json序列化
        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
        //不缓存空值
        .disableCachingNullValues();
    //使用自定义的缓存配置初始化一个cacheManager
    RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
        //一个要先调用该方法设置初始化的缓存名，在初始化相关的配置
        .build();
    return cacheManager;
  }
}
