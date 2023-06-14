package net.wenzuo.mono.feign.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Catch
 * @since 2021-06-29
 */
@Data
@ConfigurationProperties(prefix = "mono.feign")
public class FeignProperties {

    /**
     * 是否启用 Mono Feign 模块功能
     */
    private Boolean enabled = true;
    /**
     * 是否启用 FeignClientEncoder 和 FeignClientDecoder
     */
    private Boolean codec = true;
    /**
     * 是否启用 FeignExceptionHandler
     */
    private Boolean exceptionHandler = true;

}
