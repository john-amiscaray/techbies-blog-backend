package me.john.amiscaray.blogapi.config

import me.john.amiscaray.blogapi.filter.JWTFilter
import me.john.amiscaray.blogapi.services.AuthService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebSecurity
class SecurityConfig(private val userDetailsService: UserDetailsService,
                     private val authManager: AuthenticationManager,
                     private val authService: AuthService,
                     private val passwordEncoder: PasswordEncoder): WebSecurityConfigurerAdapter() {


    private val logger: Logger = LoggerFactory.getLogger(SecurityConfig::class.java)

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .addFilter(JWTFilter(authManager, authService))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        logger.info("Configured the filters")
    }

    override fun configure(web: WebSecurity) {

        logger.info("Configured paths to ignore")

        web.ignoring()
            .antMatchers("/auth/**")
            .antMatchers("/swagger-ui.html")
            .antMatchers("/v2/**")
            .antMatchers("/webjars/**")
            .antMatchers("/swagger-resources/**")
            .antMatchers("/views/**")
    }

    @Throws(java.lang.Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
    }

}