package net.wenzuo.mono.web.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 记录最大长度
     */
    private Integer maxPayloadLength = 2048;

    /**
     * 不记录日志的接口
     */
    @Deprecated
    private List<String> excludePaths = new ArrayList<>();

}
