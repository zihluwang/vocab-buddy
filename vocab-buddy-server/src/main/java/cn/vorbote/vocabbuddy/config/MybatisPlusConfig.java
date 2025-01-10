package cn.vorbote.vocabbuddy.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis Plus 配置
 * <br>
 * Created at 19/12/2022 19:04
 *
 * @author theod
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * MyBatis Plus 分页配置
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        var interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
