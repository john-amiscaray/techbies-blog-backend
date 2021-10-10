package me.john.amiscaray.blogapi.filter

import me.john.amiscaray.blogapi.services.AuthService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.stereotype.Service
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class JWTFilter(authManager: AuthenticationManager, private val authService: AuthService)
    : BasicAuthenticationFilter(authManager) {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        filterChain: FilterChain
    ) {

        // Get Authorization header
        val authorizationHeader = httpServletRequest.getHeader("Authorization")
        // Remove the "Bearer" prefix
        val token = authorizationHeader.substring(7)
        // Verify token
        val auth: UsernamePasswordAuthenticationToken = authService.verifyToken(token)
        SecurityContextHolder.getContext().authentication = auth
        // send request through next filter
        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }

}