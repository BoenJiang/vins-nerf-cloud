package com.vins_nerf.core.http;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@Order(999) // 序号越低，优先级越高
@Component
@WebFilter(urlPatterns = "/*", filterName = "restFilter")
public class RestFilter implements Filter {
    private static class HttpWrapper extends HttpServletRequestWrapper {
        /**
         * To fix the Network Security Leak from FastJson
         *
         * @param rawBody
         * @return
         */
        private static String convertBody(String rawBody) {
            if (!JSON.isValid(rawBody)) return rawBody;

            JSONObject bodyJSON = JSON.parseObject(rawBody);
            return bodyJSON.containsKey("data") ?
                    JSON.toJSONString(bodyJSON.getJSONObject("data")) : rawBody;
        }

        private final String body;

        /**
         * Constructs a request object wrapping the given request.
         *
         * @param request The request to wrap
         * @throws IllegalArgumentException if the request is null
         */
        public HttpWrapper(HttpServletRequest request) throws IOException {
            super(request);

            // 将requestBody写入request的Attribute
            StringBuilder sb = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
                char[] charBuffer = new char[1024];
                int readCount;
                while ((readCount = in.read(charBuffer)) != -1) {
                    sb.append(charBuffer, 0, readCount);
                }
            } catch (IOException e) {
                log.error("[RestFilter]", e);
                throw e;
            }
            String rawBody = sb.toString();
            request.setAttribute(RestConstants.REQUEST_BODY, rawBody);
            this.body = convertBody(rawBody);
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            final ByteArrayInputStream byteArrayIns = new ByteArrayInputStream(body.getBytes());
            ServletInputStream servletIns = new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    return byteArrayIns.read();
                }
            };
            return servletIns;
        }

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new HttpWrapper((HttpServletRequest) request), response);
    }
}
