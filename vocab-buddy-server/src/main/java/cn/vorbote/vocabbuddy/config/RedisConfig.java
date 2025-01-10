package cn.vorbote.vocabbuddy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 配置
 * <p>
 * Created at 18:04, 05 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@Configuration
public class RedisConfig {

    /**
     * 创建 {@code RedisTemplate} 实例
     *
     * @param redisConnectionFactory Redis 连接池工厂
     * @return RedisTemplate 实例
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 创建 RedisTemplate
        final var redisTemplate = new RedisTemplate<String, Object>();

        // 创建 Redis 序列化工具
        final var stringRedisSerializer = new StringRedisSerializer();
        final var jacksonRedisSerializer = new GenericJackson2JsonRedisSerializer();

        // 设置 Redis 连接池
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 设置序列化工具
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jacksonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jacksonRedisSerializer);

        // 完成设置
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
