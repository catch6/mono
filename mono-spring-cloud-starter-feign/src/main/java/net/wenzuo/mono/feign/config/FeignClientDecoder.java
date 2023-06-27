package net.wenzuo.mono.feign.config;

import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.stereotype.Component;

import feign.FeignException;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import net.wenzuo.mono.core.utils.JsonUtils;

/**
 * @author Catch
 * @since 2021-06-29
 */
@Slf4j
@Component
@ConditionalOnProperty(value = "mono.feign.codec", matchIfMissing = true)
public class FeignClientDecoder extends SpringDecoder {

	public FeignClientDecoder(ObjectFactory<HttpMessageConverters> messageConverters, ObjectProvider<HttpMessageConverterCustomizer> customizers) {
		super(messageConverters, customizers);
	}

	@Override
	public Object decode(Response response, Type type) throws IOException, FeignException {
		long time = System.currentTimeMillis() - FeignClientEncoder.TIMER.get();
		FeignClientEncoder.TIMER.remove();
		int status = response.status();
		Object result = super.decode(response, type);
		String data = JsonUtils.toJson(result);
		log.info("THIRD-RESPONSE: {}ms {} {}", time, status, data);
		if (status == 200) {
			return result;
		}
		throw new ThirdException(status, data, response.request());
	}

}
