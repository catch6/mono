package net.wenzuo.mono.web.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author Catch
 * @since 2023-06-28
 */
@Data
@ConfigurationProperties(prefix = "mono.web.logging")
public class LoggingProperties {

	/**
	 * 是否启用请求响应日志记录
	 */
	private Boolean enabled = true;

	/**
	 * 日志过滤包含的路径
	 */
	private String[] includePath = {"/**"};

	/**
	 * 日志过滤排除的路径
	 */
	private String[] excludePath = {};

	/**
	 * 日志过滤内部排除的路径, 优先级高于 excludePath, 一般不建议修改
	 */
	private String[] internalExcludePath = {"/actuator/**", "/error", "/v3/api-docs/**", "/swagger-ui*/**", "/webjars/**"};

}
