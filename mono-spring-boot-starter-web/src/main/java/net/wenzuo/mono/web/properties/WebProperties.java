package net.wenzuo.mono.web.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author Catch
 * @since 2023-06-06
 */
@Data
@ConfigurationProperties(prefix = "mono.web")
public class WebProperties {

	/**
	 * 是否启用 web 模块
	 */
	private Boolean enabled = true;

	/**
	 * 是否启用全局异常处理
	 */
	private Boolean exceptionHandler = true;

}
