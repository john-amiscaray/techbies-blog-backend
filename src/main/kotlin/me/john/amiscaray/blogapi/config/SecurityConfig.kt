package me.john.amiscaray.blogapi.config

import me.john.amiscaray.blogapi.filter.JWTFilter
import me.john.amiscaray.blogapi.service.JWTAuthService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
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
class SecurityConfig(private val jwtAuthService: JWTAuthService,
                     private val userDetailsService: UserDetailsService): WebSecurityConfigurerAdapter() {


    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .addFilter(JWTFilter(authenticationManager(), jwtAuthService))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Throws(java.lang.Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder())
    }

    @Bean
    fun getPasswordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder(10)
    }

    @Bean(name = [BeanIds.AUTHENTICATION_MANAGER])
    @Throws(java.lang.Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

    @Throws(java.lang.Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring()
            .antMatchers("/api/auth/signup")
            .antMatchers("/api/auth/login")
    }


}