package cn.vorbote.vocabbuddy.config;

import cn.vorbote.vocabbuddy.entrypoint.AdminEntrypoint;
import cn.vorbote.vocabbuddy.entrypoint.AuthenticatedEntrypoint;
import cn.vorbote.vocabbuddy.entrypoint.PublicEntrypoint;
import cn.vorbote.vocabbuddy.entrypoint.UserEntrypoint;
import cn.vorbote.vocabbuddy.filter.AdminInterceptor;
import cn.vorbote.vocabbuddy.filter.CommonInterceptor;
import cn.vorbote.vocabbuddy.filter.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 拦截器配置
 * <p>
 * Created at 02:31, 23 Feb 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final CommonInterceptor commonInterceptor;

    private final UserInterceptor userInterceptor;

    private final AdminInterceptor adminInterceptor;

    public InterceptorConfig(CommonInterceptor commonInterceptor,
                             UserInterceptor userInterceptor,
                             AdminInterceptor adminInterceptor) {
        this.commonInterceptor = commonInterceptor;
        this.userInterceptor = userInterceptor;
        this.adminInterceptor = adminInterceptor;
    }

    /**
     * 向拦截器链中添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加通用拦截器
        registry.addInterceptor(commonInterceptor)
                .addPathPatterns("/**");

        // 添加用户拦截器
        registry.addInterceptor(userInterceptor)
                .excludePathPatterns(
                        PublicEntrypoint.ADMIN_LOGIN,
                        PublicEntrypoint.USER_LOGIN,
                        PublicEntrypoint.USER_REGISTER,
                        PublicEntrypoint.SUPPORTED_REGIONS,
                        PublicEntrypoint.COMMON_ENTRYPOINT_PATTERN
                )
                .addPathPatterns(
                        AuthenticatedEntrypoint.LIST_WORD_BY_TAG,
                        AuthenticatedEntrypoint.LIST_ALL_TAGS,

                        UserEntrypoint.COMMON_ENTRYPOINT_PATTERN,
                        UserEntrypoint.GENERATE_DICTATION,
                        UserEntrypoint.RECORD_DICTATION_DETAIL,
                        UserEntrypoint.STOP_DICTATION,
                        UserEntrypoint.RETRIEVE_HISTORICAL_DICTATIONS,
                        UserEntrypoint.RETRIEVE_DICTATION
                );

        // 添加管理员拦截器
        registry.addInterceptor(adminInterceptor)
                .excludePathPatterns(
                        PublicEntrypoint.ADMIN_LOGIN,
                        PublicEntrypoint.USER_LOGIN,
                        PublicEntrypoint.USER_REGISTER,
                        PublicEntrypoint.SUPPORTED_REGIONS,
                        PublicEntrypoint.COMMON_ENTRYPOINT_PATTERN
                )
                .addPathPatterns(
                        AuthenticatedEntrypoint.LIST_WORD_BY_TAG,
                        AuthenticatedEntrypoint.LIST_ALL_TAGS,

                        AdminEntrypoint.COMMON_ENTRYPOINT_PATTERN
                );
    }
}
