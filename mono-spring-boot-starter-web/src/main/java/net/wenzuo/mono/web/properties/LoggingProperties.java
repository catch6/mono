package net.wenzuo.mono.web.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Catch
 * @since 2021-06-26
 */
@Data
@ConfigurationProperties(prefix = "mono.web.logging")
public class LoggingProperties {

    /**
     * 是否启用记录 request response 请求响应参数及耗时
     */
    private Boolean enabled = true;

}
