package net.wenzuo.mono.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Catch
 * @since 2023-06-06
 */
@Data
@ConfigurationProperties(prefix = "mono.core")
public class CoreProperties {

    /**
     * 是否启用 core 模块
     */
    private boolean enabled = true;

    /**
     * 是否启用异步
     */
    private boolean async = true;

    /**
     * 是否启用json
     */
    private boolean json = true;

}
