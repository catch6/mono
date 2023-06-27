package net.wenzuo.mono.web.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author Catch
 * @since 2023-06-06
 */
@Data
@ConfigurationProperties(prefix = "mono.web.cors")
public class CorsProperties {

	/**
	 * 是否启用 CORS
	 */
	private Boolean enabled = true;

	private List<Config> configs = Collections.singletonList(new Config());

	@Data
	public static class Config {

		/**
		 * 是否配置全局跨域支持
		 */
		private String pattern = "/**";

		/**
		 * 是否允许 Credentials , 默认 false
		 */
		private Boolean allowCredentials = false;

		/**
		 * 允许的 Origins, 默认 *, 生产环境建议设置为具体值，可以为多个
		 */
		private List<String> allowedOrigins = Collections.singletonList("*");

		/**
		 * 允许的 Origins Pattern, 默认 [], 生产环境建议设置为具体值，可以为多个
		 */
		private List<String> allowedOriginPatterns = new ArrayList<>();

		/**
		 * 允许的 Headers, 默认 *, 生产环境建议设置为具体值，可以为多个
		 */
		private List<String> allowedHeaders = Collections.singletonList("*");

		/**
		 * 允许的 Methods, 默认 ["*"], 生产环境建议设置为具体值，可以为多个
		 */
		private List<String> allowedMethods = Collections.singletonList("*");

		/**
		 * 允许客户端读取的 Headers, 默认 *
		 */
		private List<String> exposedHeaders = Collections.singletonList("*");

	}

}
