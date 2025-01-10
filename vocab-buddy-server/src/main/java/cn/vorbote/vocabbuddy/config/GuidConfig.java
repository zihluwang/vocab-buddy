package cn.vorbote.vocabbuddy.config;

import cn.vorbote.core.utils.SnowFlake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 全局唯一 ID 生成器配置
 * <br>
 * Created at 19/12/2022 19:06
 *
 * @author theod
 */
@Configuration
public class GuidConfig {

    /**
     * 管理员全局唯一ID生成器
     */
    @Bean
    public SnowFlake adminGuidUtil() {
        return new SnowFlake(0x0, 0x0);
    }

    /**
     * 用户全局唯一 ID 生成器
     */
    @Bean
    public SnowFlake userGuidUtil() {
        return new SnowFlake(0x0, 0x1);
    }

    /**
     * 管理员日志全局唯一 ID 生成器
     */
    @Bean
    public SnowFlake adminLogGuidUtil() {
        return new SnowFlake(0x1, 0x0);
    }

    /**
     * 客户日志全局唯一 ID 生成器
     */
    @Bean
    public SnowFlake userLogGuidUtil() {
        return new SnowFlake(0x1, 0x1);
    }

    /**
     * 听写测试全局唯一 ID 生成器
     */
    @Bean
    public SnowFlake dictationGuidUtil() {
        return new SnowFlake(0x2, 0x0);
    }

    /**
     * 听写测试详情全局唯一 ID 生成器
     */
    @Bean
    public SnowFlake dictationDetailGuidUtil() {
        return new SnowFlake(0x2, 0x1);
    }

    /**
     * 标签全局唯一 ID 生成器
     */
    @Bean
    public SnowFlake tagGuidUtil() {
        return new SnowFlake(0x3, 0x0);
    }

    /**
     * 单词全局唯一 ID 生成器
     */
    @Bean
    public SnowFlake wordGuidUtil() {
        return new SnowFlake(0x4, 0x0);
    }

    /**
     * 单词释义日志全局唯一 ID 生成器
     */
    @Bean
    public SnowFlake wordDescriptionsGuidUtil() {
        return new SnowFlake(0x4, 0x1);
    }

    /**
     * 单词标签全局唯一 ID 生成器
     */
    @Bean
    public SnowFlake wordTagsGuidUtil() {
        return new SnowFlake(0x4, 0x2);
    }

}
