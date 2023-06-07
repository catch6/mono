package net.wenzuo.mono.web.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.wenzuo.mono.core.utils.NanoIdUtils;
import net.wenzuo.mono.web.properties.LoggingProperties;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Catch
 * @since 2021-03-31
 */
@Deprecated
@Slf4j
// @ConditionalOnProperty(value = "mono.web.logging.enabled", matchIfMissing = true)
// @Component
public class OldLoggingFilter extends OncePerRequestFilter {

    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();
    private static final ThreadLocal<Long> START = new ThreadLocal<>();
    private static final String REQ_ID = "Req-Id";
    private static final String WILDCARD = "*";
    private static final String DOT = ".";

    private static final String PREFIX_ACTUATOR = "/actuator";
    private static final String PREFIX_API_DOCS = "/v3/api-docs";
    private static final String PREFIX_SWAGGER_UI = "/swagger-ui";

    // 用来截取 serverServletPath+springServletPath
    private final int beginIndex;
    // 精确的路径使用 hashset 做到 O(1)  算法复杂度
    private final Set<String> fixedPaths = new HashSet<>();
    // 通配符路径使用 PathMatcher 进行匹配
    private final List<String> wildcardPaths = new ArrayList<>();

    private final boolean apiDocsEnabled;
    private final boolean swaggerUiEnabled;

    public OldLoggingFilter(LoggingProperties loggingProperties,
                            @Value("${server.servlet.context-path:}") String serverServletPath,
                            @Value("${spring.mvc.servlet.path:}") String springServletPath,
                            @Value("${springdoc.api-docs.enabled:true}") boolean apiDocsEnabled,
                            @Value("${springdoc.swagger-ui.enabled:true}") boolean swaggerUiEnabled) {
        beginIndex = serverServletPath.length() + springServletPath.length();
        List<String> paths = loggingProperties.getExcludePaths();
        for (String path : paths) {
            if (path.contains(WILDCARD)) {
                wildcardPaths.add(path);
            } else {
                fixedPaths.add(path);
            }
        }
        this.apiDocsEnabled = apiDocsEnabled;
        this.swaggerUiEnabled = swaggerUiEnabled;
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        if (beginIndex > 0) {
            requestURI = requestURI.substring(beginIndex);
        }
        if (fixedPaths.contains(requestURI)) {
            return true;
        }
        for (String path : wildcardPaths) {
            if (PATH_MATCHER.match(path, requestURI)) {
                return true;
            }
        }
        if (requestURI.startsWith(PREFIX_ACTUATOR)) {
            return true;
        }
        if (apiDocsEnabled && requestURI.startsWith(PREFIX_API_DOCS)) {
            return true;
        }
        if (swaggerUiEnabled && requestURI.startsWith(PREFIX_SWAGGER_UI)) {
            return true;
        }
        if (requestURI.contains(DOT)) {
            return true;
        }
        String method = request.getMethod();
        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(method)) {
            return true;
        }
        String accept = request.getHeader("Accept");
        if (MediaType.TEXT_EVENT_STREAM_VALUE.equals(accept)) {
            return true;
        }
        return false;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws ServletException, IOException {
        // 记录开始时间
        START.set(System.currentTimeMillis());

        // log 及 response header 中加入 Req-Id, 方便追踪单次请求
        String traceId = NanoIdUtils.nanoId();
        response.setHeader(REQ_ID, traceId);
        MDC.put(REQ_ID, traceId);

        RequestWrapper requestWrapper = new RequestWrapper(request);
        ResponseWrapper responseWrapper = new ResponseWrapper(response);

        // 记录 request
        loggingRequest(requestWrapper);
        try {
            chain.doFilter(requestWrapper, responseWrapper);
        } finally {
            // 记录 response
            loggingResponse(responseWrapper);
        }
    }

    private void loggingRequest(RequestWrapper requestWrapper) throws IOException {
        StringBuilder data = new StringBuilder();

        String params = requestWrapper.getContentAsString();
        data.append(params);

        String contentType = requestWrapper.getContentType();
        if (contentType == null || !contentType.contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            String body = requestWrapper.getBodyAsString();
            data.append(body);
        }

        log.info("REQUEST: {} DATA: {}", requestWrapper.getRequestURI(), data);
    }

    private void loggingResponse(ResponseWrapper responseWrapper) throws IOException {
        StringBuilder data = new StringBuilder();
        String contentType = responseWrapper.getContentType();
        if (contentType != null && contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
            byte[] byteArray = responseWrapper.getContentAsByteArray();
            String body = new String(byteArray, StandardCharsets.UTF_8);
            data.append(body);
        }
        responseWrapper.copyBodyToResponse();
        long took = System.currentTimeMillis() - START.get();
        log.info("RESPONSE: {}ms DATA: {}", took, data);
        START.remove();
        MDC.remove(REQ_ID);
    }

}
