package cn.vorbote.vocabbuddy.config;

import cn.vorbote.vocabbuddy.constant.Region;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 可用地区配置
 * <p>
 * Created at 00:16, 23 Feb 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@Configuration
public class RegionConfig {

    /**
     * 设置该系统可用地区
     * <p>
     * 2023-05-15：可用地区为<b>中国大陆</b>、<b>中国香港</b>、<b>中国澳门</b>和<b>中国台湾</b>
     *
     * @return 可用地区列表
     */
    @Bean
    public List<Region> supportedRegions() {
        return List.of(
                Region.CHINA_MAINLAND,
                Region.HONG_KONG,
                Region.MACAU,
                Region.TAIWAN
        );
    }

}
