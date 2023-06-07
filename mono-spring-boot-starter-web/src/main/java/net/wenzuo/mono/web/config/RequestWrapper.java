package net.wenzuo.mono.web.config;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author Catch
 * @since 2021-08-25
 */
@Deprecated
public class RequestWrapper extends HttpServletRequestWrapper {

    private final ByteArrayOutputStream cachedContent;

    private byte[] cachedBody = {};

    public RequestWrapper(HttpServletRequest request) {
        super(request);
        int contentLength = request.getContentLength();
        this.cachedContent = new ByteArrayOutputStream(contentLength >= 0 ? contentLength : 1024);
    }

    public String getContentAsString() throws IOException {
        writeRequestParametersToCachedContent();
        String params = this.cachedContent.toString(StandardCharsets.UTF_8);
        return URLDecoder.decode(params, StandardCharsets.UTF_8);
    }

    public String getBodyAsString() throws IOException {
        ServletInputStream is = getInputStream();
        String body = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
        String contentType = getContentType();
        if (contentType != null && contentType.contains(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
            body = URLDecoder.decode(body, StandardCharsets.UTF_8);
            body = body.replace("&", " ");
        }
        return body;
    }

    @Override
    public String getCharacterEncoding() {
        String enc = super.getCharacterEncoding();
        return (enc != null ? enc : StandardCharsets.UTF_8.name());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (this.cachedBody.length == 0) {
            this.cachedBody = StreamUtils.copyToByteArray(getRequest().getInputStream());
        }
        return new CachedBodyServletInputStream(this.cachedBody);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (this.cachedBody.length == 0) {
            this.cachedBody = StreamUtils.copyToByteArray(getInputStream());
        }
        ByteArrayInputStream is = new ByteArrayInputStream(this.cachedBody);
        return new BufferedReader(new InputStreamReader(is, getCharacterEncoding()));
    }

    @Override
    public String getParameter(String name) {
        writeRequestParametersToCachedContent();
        return super.getParameter(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        writeRequestParametersToCachedContent();
        return super.getParameterMap();
    }

    @Override
    public Enumeration<String> getParameterNames() {
        writeRequestParametersToCachedContent();
        return super.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String name) {
        writeRequestParametersToCachedContent();
        return super.getParameterValues(name);
    }

    private void writeRequestParametersToCachedContent() {
        if (this.cachedContent.size() > 0) {
            return;
        }
        try {
            String requestEncoding = getCharacterEncoding();
            Map<String, String[]> form = super.getParameterMap();
            for (Iterator<Map.Entry<String, String[]>> iterator = form.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry<String, String[]> entry = iterator.next();
                String name = entry.getKey();
                List<String> values = Arrays.asList(entry.getValue());
                for (Iterator<String> valueIterator = values.iterator(); valueIterator.hasNext(); ) {
                    String value = valueIterator.next();
                    this.cachedContent.write(URLEncoder.encode(name, requestEncoding)
                                                       .getBytes());
                    if (value != null) {
                        this.cachedContent.write('=');
                        this.cachedContent.write(URLEncoder.encode(value, requestEncoding)
                                                           .getBytes());
                        if (valueIterator.hasNext()) {
                            this.cachedContent.write('&');
                        }
                    }
                }
                if (iterator.hasNext()) {
                    this.cachedContent.write(' ');
                }
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to write request parameters to cached content", ex);
        }
    }

    private class CachedBodyServletInputStream extends ServletInputStream {

        private final ByteArrayInputStream is;

        public CachedBodyServletInputStream(byte[] cachedBody) {
            this.is = new ByteArrayInputStream(cachedBody);
        }

        @Override
        public int read() {
            int ch = this.is.read();
            if (ch != -1) {
                cachedContent.write(ch);
            }
            return ch;
        }

        @Override
        public int read(@NonNull byte[] b) throws IOException {
            int count = this.is.read(b);
            writeToCache(b, 0, count);
            return count;
        }

        @Override
        public int read(@NonNull final byte[] b, final int off, final int len) {
            int count = this.is.read(b, off, len);
            writeToCache(b, off, count);
            return count;
        }

        @Override
        public int readLine(final byte[] b, final int off, final int len) {
            int count = this.doReadLine(b, off, len);
            writeToCache(b, off, count);
            return count;
        }

        private int doReadLine(byte[] b, int off, int len) {
            if (len <= 0) {
                return 0;
            } else {
                int count = 0;
                int c;
                while ((c = this.is.read()) != -1) {
                    b[off++] = (byte) c;
                    ++count;
                    if (c == 10 || count == len) {
                        break;
                    }
                }
                return count > 0 ? count : -1;
            }
        }

        private void writeToCache(final byte[] b, final int off, int count) {
            if (count > 0) {
                cachedContent.write(b, off, count);
            }
        }

        @SneakyThrows
        @Override
        public boolean isFinished() {
            return this.is.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException();
        }

    }

}
