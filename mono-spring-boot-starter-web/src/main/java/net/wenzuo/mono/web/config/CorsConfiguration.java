package net.wenzuo.mono.web.config;

import lombok.RequiredArgsConstructor;
import net.wenzuo.mono.web.properties.CorsProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author Catch
 * @since 2023-06-06
 */
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mono.web.cors.enabled", matchIfMissing = true)
@Configuration
public class CorsConfiguration {

    private final CorsProperties corsProperties;

    @Bean
    @ConditionalOnMissingBean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        for (CorsProperties.Config config : corsProperties.getConfigs()) {
            org.springframework.web.cors.CorsConfiguration corsConfig = new org.springframework.web.cors.CorsConfiguration();
            corsConfig.setAllowCredentials(config.getAllowCredentials());
            corsConfig.setAllowedOrigins(config.getAllowedOrigins());
            corsConfig.setAllowedOriginPatterns(config.getAllowedOriginPatterns());
            corsConfig.setAllowedHeaders(config.getAllowedHeaders());
            corsConfig.setAllowedMethods(config.getAllowedMethods());
            corsConfig.setExposedHeaders(config.getExposedHeaders());
            source.registerCorsConfiguration(config.getPattern(), corsConfig);
        }
        return new CorsFilter(source);
    }

}
