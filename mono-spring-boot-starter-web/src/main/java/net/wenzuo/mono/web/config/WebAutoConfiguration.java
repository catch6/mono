package net.wenzuo.mono.web.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Catch
 * @since 2023-06-06
 */
@RequiredArgsConstructor
@ComponentScan("net.wenzuo.mono.web")
@ConfigurationPropertiesScan("net.wenzuo.mono.web.properties")
@PropertySource("classpath:application-web.properties")
@ConditionalOnProperty(value = "mono.web.enabled", matchIfMissing = true)
public class WebAutoConfiguration {

}
