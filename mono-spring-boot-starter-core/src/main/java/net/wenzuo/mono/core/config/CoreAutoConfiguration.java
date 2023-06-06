package net.wenzuo.mono.core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Catch
 * @since 2023-06-05
 */
@RequiredArgsConstructor
@ComponentScan("net.wenzuo.mono.core")
@ConfigurationPropertiesScan("net.wenzuo.mono.core.properties")
@PropertySource("classpath:application-core.properties")
@ConditionalOnProperty(value = "mono.core.enabled", matchIfMissing = true)
public class CoreAutoConfiguration {

}
