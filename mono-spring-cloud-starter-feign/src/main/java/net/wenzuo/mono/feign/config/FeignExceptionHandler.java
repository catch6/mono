package net.wenzuo.mono.feign.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Catch
 * @since 2021-06-29
 */
@Slf4j
@RestControllerAdvice
@ConditionalOnProperty(value = "mono.feign.exception-handler", matchIfMissing = true)
public class FeignExceptionHandler {

	/**
	 * openfeign 异常处理,
	 *
	 * @param e 异常对象
	 * @return Result
	 */
	@ExceptionHandler(ThirdException.class)
	public ResponseEntity<String> handler(ThirdException e) {
		if (e.status() < 500) {
			log.warn(e.getMessage(), e);
		}
		else {
			log.error(e.getMessage(), e);
		}
		return ResponseEntity.status(e.status()).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
	}

}
