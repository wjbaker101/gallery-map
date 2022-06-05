package com.wjbaker.gallery_map.configuration;

import com.wjbaker.gallery_map.api.auth.RequiresAdminHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final RequiresAdminHandlerInterceptor requiresAdminHandlerInterceptor;

    public WebMvcConfiguration(@Autowired final RequiresAdminHandlerInterceptor requiresAdminHandlerInterceptor) {
        this.requiresAdminHandlerInterceptor = requiresAdminHandlerInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(this.requiresAdminHandlerInterceptor);
    }
}