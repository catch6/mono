package net.wenzuo.mono.feign.config;

import feign.FeignException;
import feign.Request;

/**
 * @author Catch
 * @since 2021-12-02
 */
public class ThirdException extends FeignException {

    public ThirdException(int status, String message, Request request) {
        super(status, message, request);
    }

}
