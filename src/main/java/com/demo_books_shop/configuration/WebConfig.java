package com.demo_books_shop.configuration;


import com.demo_books_shop.utils.FileUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String pathToImages = FileUtils.getImageFolder();
        registry.addResourceHandler("/images/**").addResourceLocations("file:///"+pathToImages);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/welcomePage").setViewName("welcomePage");
        registry.addViewController("/registration").setViewName("registration");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/creatingPage").setViewName("creatingPage");
        registry.addViewController("/addBooks").setViewName("addBooks");
    }
}
