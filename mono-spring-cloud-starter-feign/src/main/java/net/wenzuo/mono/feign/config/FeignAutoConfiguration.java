package net.wenzuo.mono.feign.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Catch
 * @since 2021-06-29
 */
@RequiredArgsConstructor
@ComponentScan("net.wenzuo.mono.feign")
@ConfigurationPropertiesScan("net.wenzuo.mono.feign.properties")
@PropertySource("classpath:application-feign.properties")
@ConditionalOnProperty(value = "mono.feign.enabled", matchIfMissing = true)
public class FeignAutoConfiguration {

}
