package me.john.amiscaray.blogapi.config

import me.john.amiscaray.blogapi.filter.JWTFilter
import me.john.amiscaray.blogapi.service.JWTAuthService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig(private val userDetailsService: UserDetailsService): WebSecurityConfigurerAdapter() {

    private val logger: Logger = LoggerFactory.getLogger(SecurityConfig::class.java)

    @Autowired
    private lateinit var jwtAuthService: JWTAuthService

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .addFilter(JWTFilter(authenticationManagerBean(), jwtAuthService))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        logger.info("Configured the filters")
    }

    override fun configure(web: WebSecurity) {

        logger.info("Configured paths to ignore")

        web.ignoring()
            .antMatchers("/auth/signup")
            .antMatchers("/auth/login")
            .antMatchers("/swagger-ui.html")
            .antMatchers("/v2/**")
            .antMatchers("/webjars/**")
            .antMatchers("/swagger-resources/**")
    }

    @Throws(java.lang.Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder())
    }

    @Bean
    fun getPasswordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder(10)
    }

}