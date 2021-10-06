package me.john.amiscaray.blogapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@EnableSwagger2
@Configuration
class SwaggerConfig {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("me.john.amiscaray.blogapi"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(metaData())
            .pathMapping("/")
    }

    private fun metaData(): ApiInfo {

        return ApiInfoBuilder()
            .title("Techbies API")
            .description("API for Techbies project from the unclebigbay and friends bootcamp")
            .version("1.0")
            .build()

    }
}