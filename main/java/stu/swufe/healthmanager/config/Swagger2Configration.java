package stu.swufe.healthmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Swagger2Configration {
    public static final String VERSION = "1.0.0";


    /**
     * 用户中心
     * @return
     */
    @Bean
    public Docket usersApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(usersApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("stu.swufe.healthmanager.controller.user"))
                .paths(PathSelectors.any())
                .build()
                .groupName("用户页面");
    }

    private ApiInfo usersApiInfo(){
        return new ApiInfoBuilder()
                .title("健康管理用户页面接口文档")
                .description("用户页面接口文档")
                .version(VERSION)
                .build();
    }
}
