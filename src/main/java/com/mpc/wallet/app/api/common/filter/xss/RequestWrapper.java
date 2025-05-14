package com.mpc.wallet.app.api.common.filter.xss;


import org.apache.commons.lang3.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class RequestWrapper extends HttpServletRequestWrapper {

    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return StringUtils.isBlank(value) ? null : value;
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return StringUtils.isBlank(value) ? null : value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escValues = new String[length];
            for (int i = 0; i < length; i++) {
                escValues[i] = values[i] == null ? null : values[i];
            }
            return escValues;
        }
        return super.getParameterValues(name);
    }
}

