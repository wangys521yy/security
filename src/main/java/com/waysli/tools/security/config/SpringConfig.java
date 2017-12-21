package com.waysli.tools.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waysli.tools.security.demo.converter.IdConverter;
import com.waysli.tools.security.demo.converter.IdMixAnnotationFormatterFactory;
import com.waysli.tools.security.demo.converter.IdModule;
import com.waysli.tools.security.demo.converter.Java8Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.TimeZone;

import static com.waysli.tools.security.demo.converter.PetstoreConstants.ENUM_PROP_NAME;

@Configuration
public class SpringConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new IdConverter());
//        registry.addFormatterForFieldAnnotation(new IdMixAnnotationFormatterFactory());
        super.addFormatters(registry);
    }

    @Bean
    public ObjectMapper objectMapper() {
        Java8Mapper java8Mapper = new Java8Mapper();
        // 确保反序列化时自动加上TimeZone信息
        java8Mapper.setTimeZone(TimeZone.getDefault());
        java8Mapper.registerModule(new IdModule(ENUM_PROP_NAME));

        return java8Mapper;
    }
}
