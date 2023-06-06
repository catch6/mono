package net.wenzuo.mono.jwt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Catch
 * @since 2023-06-06
 */
@Data
@ConfigurationProperties(prefix = "mono.jwt")
public class JwtProperties {

    /**
     * jwt secret
     */
    private String secret;

}
