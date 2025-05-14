package com.mpc.wallet.app.api.common.filter.xss;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;


public class XssRequestWrapper extends HttpServletRequestWrapper {

    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return StringUtils.isBlank(value) ? null : HtmlUtils.htmlEscape(value);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return StringUtils.isBlank(value) ? null : HtmlUtils.htmlEscape(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escValues = new String[length];
            for (int i = 0; i < length; i++) {
                escValues[i] = values[i] == null ? null : HtmlUtils.htmlEscape(values[i]);
            }
            return escValues;
        }
        return super.getParameterValues(name);
    }
}

