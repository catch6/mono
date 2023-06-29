package net.wenzuo.mono.consul.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author Catch
 * @since 2021-06-29
 */
@Data
@ConfigurationProperties(prefix = "mono.consul")
public class ConsulProperties {

	/**
	 * 是否启用 Mono Consul 模块功能
	 */
	private Boolean enabled = true;

}
