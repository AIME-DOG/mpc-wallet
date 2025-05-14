package com.mpc.wallet.app.api.common.filter;


import com.mpc.wallet.app.api.common.enums.AppCode;
import com.mpc.wallet.app.api.common.response.Result;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
@Component
@RefreshScope
public class InFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest , ServletResponse servletResponse , FilterChain filterChain) throws IOException, ServletException {
        InRequestWrapper inRequestWrapper;
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            inRequestWrapper = new InRequestWrapper(request);
        } catch (Exception e) {
            servletResponse.setContentType("application/json;charset=UTF-8");
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type" , "application/json;charset=UTF-8");

            //组装返回参数
            Result<Void> result = Result.fail(AppCode.SERVER_ERROR.getCode() , AppCode.SERVER_ERROR.getMsg() , null);
            servletResponse.getWriter().print(JSONObject.toJSONString(result));
            //推送错误消息
            servletResponse.getWriter().flush();
            servletResponse.getWriter().close();
            return;
        }
        filterChain.doFilter(inRequestWrapper , servletResponse);
    }

    @Override
    public void destroy( ) {

    }


}
