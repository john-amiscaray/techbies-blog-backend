package me.john.amiscaray.blogapi.config

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.util.logging.Logger

@Configuration
class SecurityBeans {

    private val logger = LoggerFactory.getLogger(SecurityBeans::class.java)

    @Value("\${cors.allowed.origins}")
    private lateinit var allowedOrigins: String

    @Bean
    fun getPasswordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder(10)
    }

    @Bean
    fun cors(): WebMvcConfigurer {

        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                logger.info("Allowed CORS for origins: $allowedOrigins")
                registry.addMapping("/**")
                    .allowedOrigins(allowedOrigins)
                    .allowedMethods("*")

            }
        }

    }

}