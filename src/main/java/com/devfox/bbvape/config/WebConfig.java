package com.devfox.bbvape.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/thumbnail/**")
                .addResourceLocations("file:/var/boot/bbvape/upload/thumbnail/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:/var/boot/bbvape/upload/img/");
        registry.addResourceHandler("/setting/**")
                .addResourceLocations("file:/var/boot/bbvape/upload/setting/");
    }
}
