package me.john.amiscaray.blogapi.services

import me.john.amiscaray.blogapi.domain.AuthRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

interface AuthService {

    fun getToken(authRequest: AuthRequest): String

    fun verifyToken(token: String): UsernamePasswordAuthenticationToken

}