package com.mpc.wallet.app.api.common.config;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${spring.profiles.active:dev}")
    private String PROFILES_ACTIVE;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
                    @Override
                    public boolean preHandle(HttpServletRequest request , HttpServletResponse response , Object handler) throws Exception {
                        return HandlerInterceptor.super.preHandle(request , response , handler);
                    }
                })
                .addPathPatterns("/**")
                .excludePathPatterns(
                        List.of("/swagger-ui/**" ,
                                "/doc.html/**" ,
                                "/swagger-resources/**" ,
                                "/webjars/**" ,
                                "/error" ,
                                "/v3/api-docs/**")
                );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 解决静态资源无法访问
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

        if (!PROFILES_ACTIVE.equalsIgnoreCase("prod")) {
            // 解决swagger无法访问
            registry.addResourceHandler("doc.html")
                    .addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/");
            // 解决swagger的js文件无法访问
            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        // 配置其他序列化处理器
        FastJsonConfig config = converter.getFastJsonConfig();
        config.setWriterFeatures(
                JSONWriter.Feature.WriteLongAsString ,
                JSONWriter.Feature.WriteNullStringAsEmpty ,
                JSONWriter.Feature.WriteNullListAsEmpty ,
                JSONWriter.Feature.WriteMapNullValue
        );
        converter.setFastJsonConfig(config);
        converters.add(0 , converter);
    }


}