package net.wenzuo.mono.feign.config;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * @author Catch
 * @since 2021-06-29
 */
@Slf4j
@Component
@ConditionalOnProperty(value = "mono.feign.codec", matchIfMissing = true)
public class FeignClientEncoder extends SpringEncoder {

    static final ThreadLocal<Long> TIMER = new ThreadLocal<>();

    public FeignClientEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        super(messageConverters);
    }

    @Override
    public void encode(Object requestBody, Type bodyType, RequestTemplate request) throws EncodeException {
        super.encode(requestBody, bodyType, request);
        TIMER.set(System.currentTimeMillis());
        String bodyStr = new String(request.body(), request.requestCharset());
        log.info("THIRD-REQUEST: {} {}{} {}", request.method(), request.feignTarget().url(), request.path(), bodyStr);
    }

}
