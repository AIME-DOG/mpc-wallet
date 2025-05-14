package com.mpc.wallet.app.api.common.filter;


import com.mpc.wallet.app.api.common.enums.AppCode;
import com.mpc.wallet.app.api.common.exception.AppServerException;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Slf4j
public class InRequestWrapper extends HttpServletRequestWrapper {

    private static final String UPLOAD_URI = "/file/upload";

    private byte[] byteArr;
    private JSONObject json = null;
    private String method;
    private String uri;

    public InRequestWrapper(HttpServletRequest request) throws Exception {
        super(request);
        method = request.getMethod();
        uri = request.getRequestURI();
        if (Objects.equals("POST" , method) && !uri.endsWith(UPLOAD_URI)) {
            String decrypt = StreamUtils.copyToString(request.getInputStream() , StandardCharsets.UTF_8);
            if (StringUtils.isBlank(decrypt)) {
                throw new AppServerException(AppCode.SERVER_ERROR);
            }
            json = JSONObject.parseObject(decrypt);
            byteArr = decrypt.getBytes();
        }
    }


    @Override
    public ServletInputStream getInputStream( ) throws IOException {
        if (Objects.equals("POST" , method) && !uri.endsWith(UPLOAD_URI)) {
            return new DelegatingServletInputStream(new ByteArrayInputStream(byteArr));
        }
        return super.getRequest().getInputStream();
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(name);
        if (parameterValues == null && json != null && json.getString(name) != null) {
            parameterValues = new String[]{json.getString(name)};
        }
        return parameterValues;
    }

    @Override
    public Enumeration<String> getParameterNames( ) {
        Enumeration<String> parameterNames = super.getParameterNames();
        Set<String> set = new HashSet<>();
        while (parameterNames.hasMoreElements()) {
            set.add(parameterNames.nextElement());
        }
        if (json != null) {
            set.addAll(json.keySet());
        }
        Iterator<String> iterator = set.iterator();

        return new Enumeration<String>() {
            @Override
            public boolean hasMoreElements( ) {
                return iterator.hasNext();
            }

            @Override
            public String nextElement( ) {
                return iterator.next();
            }
        };
    }


}

