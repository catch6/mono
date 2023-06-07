package net.wenzuo.mono.web.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.wenzuo.mono.core.utils.NanoIdUtils;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Catch
 * @since 2023-06-06
 */
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mono.web.logging", matchIfMissing = true)
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final ThreadLocal<Long> TIMER = new ThreadLocal<>();
    private static final String REQ_ID = "Req-Id";

    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return false;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return isAsyncDispatch(request);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        TIMER.set(System.currentTimeMillis());

        String reqId = NanoIdUtils.nanoId();
        response.setHeader(REQ_ID, reqId);
        MDC.put(REQ_ID, reqId);

        CachedRequestWrapper requestToUse;
        if (request instanceof CachedRequestWrapper) {
            requestToUse = (CachedRequestWrapper) request;
        } else {
            requestToUse = new CachedRequestWrapper(request);
        }

        CachedResponseWrapper responseToUse;
        if (response instanceof CachedResponseWrapper) {
            responseToUse = (CachedResponseWrapper) response;
        } else {
            responseToUse = new CachedResponseWrapper(response);
        }

        loggingRequest(requestToUse);

        try {
            filterChain.doFilter(requestToUse, responseToUse);
        } finally {
            if (!isAsyncStarted(requestToUse)) {
                loggingResponse(responseToUse);
                TIMER.remove();
                MDC.remove(REQ_ID);
            }
        }

    }

    private void loggingRequest(CachedRequestWrapper wrapper) throws IOException {
        StringBuilder msg = new StringBuilder();
        msg.append("REQUEST: ")
           .append(wrapper.getMethod()).append(' ')
           .append(wrapper.getRequestURI());

        String queryString = wrapper.getQueryString();
        if (queryString != null) {
            msg.append('?').append(URLDecoder.decode(queryString, StandardCharsets.UTF_8));
        }

        // String client = request.getRemoteAddr();
        // if (StringUtils.hasLength(client)) {
        //     msg.append(", client=").append(client);
        // }
        // HttpSession session = request.getSession(false);
        // if (session != null) {
        //     msg.append(", session=").append(session.getId());
        // }
        // String user = request.getRemoteUser();
        // if (user != null) {
        //     msg.append(", user=").append(user);
        // }

        // HttpHeaders headers = new ServletServerHttpRequest(request).getHeaders();
        // msg.append(", headers=").append(headers);

        if (isReadable(wrapper)) {
            ServletInputStream is = wrapper.getInputStream();
            String payload = StreamUtils.copyToString(is, StandardCharsets.UTF_8);

            if (payload.length() == 0) {
                Map<String, String[]> form = wrapper.getParameterMap();
                StringBuilder param = new StringBuilder();
                for (Iterator<String> nameIterator = form.keySet().iterator(); nameIterator.hasNext(); ) {
                    String name = nameIterator.next();
                    List<String> values = Arrays.asList(form.get(name));
                    for (Iterator<String> valueIterator = values.iterator(); valueIterator.hasNext(); ) {
                        String value = valueIterator.next();
                        param.append(name);
                        if (value != null) {
                            param.append('=')
                                 .append(value);
                            if (valueIterator.hasNext()) {
                                param.append('&');
                            }
                        }
                    }
                    if (nameIterator.hasNext()) {
                        param.append('&');
                    }
                }
                payload = param.toString();
            }
            if (payload.length() > 0) {
                msg.append(" ").append(payload);
            }
        }

        log.info(msg.toString());
    }

    private void loggingResponse(CachedResponseWrapper wrapper) throws IOException {
        StringBuilder msg = new StringBuilder();
        long time = System.currentTimeMillis() - TIMER.get();
        msg.append("RESPONSE: ").append(time).append("ms");
        msg.append(" ").append(wrapper.getStatus());

        if (isReadable(wrapper)) {
            InputStream is = wrapper.getContentInputStream();
            String payload = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
            wrapper.copyBodyToResponse();
            if (payload.length() > 0) {
                msg.append(" ").append(payload);
            }
        }

        log.info(msg.toString());
    }

    private boolean isReadable(CachedRequestWrapper wrapper) {
        String contentType = wrapper.getContentType();
        if (contentType == null) {
            return false;
        }
        return !contentType.contains(MediaType.MULTIPART_FORM_DATA_VALUE);
    }

    private boolean isReadable(CachedResponseWrapper wrapper) {
        String contentType = wrapper.getContentType();
        if (contentType == null) {
            return false;
        }
        return contentType.contains(MediaType.APPLICATION_JSON_VALUE) ||
            contentType.contains(MediaType.APPLICATION_XML_VALUE) ||
            contentType.contains(MediaType.TEXT_PLAIN_VALUE);
    }

}
