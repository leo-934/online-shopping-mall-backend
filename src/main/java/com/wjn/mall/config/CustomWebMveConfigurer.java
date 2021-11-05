package com.wjn.mall.config;

import com.wjn.mall.common.Constants;
import com.wjn.mall.config.handler.TokenUserAnnotationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
public class CustomWebMveConfigurer implements WebMvcConfigurer {

    @Autowired
    TokenUserAnnotationHandler tokenUserAnnotationHandler;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(tokenUserAnnotationHandler);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + Constants.imageDirectory);
        registry.addResourceHandler("/goods-img/**").addResourceLocations("file:" + Constants.imageDirectory);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods(new String[]{"GET", "HEAD", "POST", "PUT", "DELETE","OPTIONS"})
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}
