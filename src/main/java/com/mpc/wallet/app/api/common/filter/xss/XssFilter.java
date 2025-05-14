package com.mpc.wallet.app.api.common.filter.xss;


import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class XssFilter implements Filter {

    private static final String UPLOAD_URI = "/file/upload";

    private final List<String> excludeUriXssList = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("XssFilter beginning");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (request.getRequestURI().endsWith(UPLOAD_URI)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (!this.getExcludeUri(excludeUriXssList, request.getRequestURI())) {
            XssRequestWrapper xssRequest = new XssRequestWrapper((HttpServletRequest) servletRequest);
            filterChain.doFilter(xssRequest, servletResponse);
            return;
        }

        RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest);
        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public void destroy() {
        log.debug("XssFilter destroy");
    }

    private boolean getExcludeUri(List<String> uriList, String uri) {
        for (String e : uriList) {
            if (uri.endsWith(e)) {
                return true;
            }
        }
        return false;
    }
}

