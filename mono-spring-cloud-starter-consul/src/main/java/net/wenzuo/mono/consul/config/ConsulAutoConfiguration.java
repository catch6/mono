package net.wenzuo.mono.consul.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import lombok.RequiredArgsConstructor;

/**
 * @author Catch
 * @since 2023-06-06
 */
@RequiredArgsConstructor
@ComponentScan("net.wenzuo.mono.consul")
@ConfigurationPropertiesScan("net.wenzuo.mono.consul.properties")
@PropertySource("classpath:application-consul.properties")
@ConditionalOnProperty(value = "mono.consul.enabled", matchIfMissing = true)
public class ConsulAutoConfiguration {

}
