package net.wenzuo.mono.redis.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Catch
 * @since 2023-06-14
 */
@RequiredArgsConstructor
@ComponentScan("net.wenzuo.mono.redis")
@ConfigurationPropertiesScan("net.wenzuo.mono.redis.properties")
@PropertySource("classpath:application-redis.properties")
@ConditionalOnProperty(value = "mono.redis.enabled", matchIfMissing = true)
public class RedisAutoConfiguration {

}
