/**
 * Swagger2 配置类, 指定哪些目录下的controller需要调试, 以及描述信息配置
 */
package com.waysli.tools.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置类, swagger是一个Rest API的可视化调试工具
 * @author waysli
 * @date 16/10/9 下午5:06
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig
{
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.waysli.tools.security.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Restful API",
                "Rest API的可视化调试入口",
                "API debug",
                "Terms of service",
                "58322379@qq.com",
                "Licence Type: 内部开源",
                "License URL: none");
        return apiInfo;
    }
}
