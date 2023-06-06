package net.wenzuo.mono.core.config;

import net.wenzuo.mono.core.utils.JsonUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Catch
 * @since 2023-06-06
 */
@Configuration
@ConditionalOnProperty(value = "mono.core.json", matchIfMissing = true)
public class CoreJsonConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return JsonUtils.customize();
    }

}
