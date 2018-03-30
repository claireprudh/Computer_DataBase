package com.excilys.formation.cdb.servlet;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.excilys.formation.cdb.spring.AppConfig;

@Configuration
public class ApplicationAnnotationConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {AppConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {	
        return new Class<?>[] {WebMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}