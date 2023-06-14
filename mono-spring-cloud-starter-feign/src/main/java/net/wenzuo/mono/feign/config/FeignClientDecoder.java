package net.wenzuo.mono.feign.config;

import feign.FeignException;
import feign.Response;
import feign.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;

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
        Response.Body body = response.body();
        String bodyStr = Util.toString(body.asReader(response.charset()));
        log.info("THIRD-RESPONSE: {}ms {} {}", time, status, bodyStr);
        if (status == 200) {
            return super.decode(response, type);
        }
        throw new ThirdException(status, bodyStr, response.request());
    }

}
