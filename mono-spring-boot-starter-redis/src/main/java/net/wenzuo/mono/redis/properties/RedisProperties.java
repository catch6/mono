package net.wenzuo.mono.redis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Catch
 * @since 2023-06-06
 */
@Data
@ConfigurationProperties(prefix = "mono.redis")
public class RedisProperties {

    /**
     * 是否启用 redis 模块
     */
    private Boolean enabled = true;

    /**
     * 是否启用 redisTemplate
     */
    private Boolean redisTemplate = true;

    /**
     * 是否启用 cacheManager
     */
    private Boolean cacheManager = true;

    /**
     * 是否启用 RedisService
     */
    private Boolean redisService = true;

}
