package com.nko.esfilesearch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ProjectName: esFileSearch
 * @Package: com.nko.esfilesearch.core
 * @ClassName: SwaggerConfig
 * @Author: sun71
 * @Description:
 * @Date: 2019/10/21 15:21
 * @Version: 1.0
 */
@Configuration  //声明这是一个注解类
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket customDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.nko.esfilesearch.controller"))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo(){
        Contact contact = new Contact("zjk",
                "https://baidu.com/",
                "1066406756@qq.com");
        return new ApiInfoBuilder()
                .title("项目API接口")
                .description("接口描述")
                .contact(contact)
                .version("1.1.0")
                .build();
    }
}
