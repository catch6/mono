package net.wenzuo.mono.mybatisplus.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Catch
 * @since 2023-06-06
 */
@RequiredArgsConstructor
@ComponentScan("net.wenzuo.mono.mybatisplus")
@ConfigurationPropertiesScan("net.wenzuo.mono.mybatisplus.properties")
@PropertySource("classpath:application-mybatis-plus.properties")
@ConditionalOnProperty(value = "mono.mybatis-plus.enabled", matchIfMissing = true)
public class MybatisPlusAutoConfiguration {

    @ConditionalOnProperty(value = "mono.mybatis-plus.interceptor", matchIfMissing = true)
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }

}
