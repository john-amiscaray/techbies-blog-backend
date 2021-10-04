package me.john.amiscaray.blogapi.service

import me.john.amiscaray.blogapi.domain.AuthRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

interface JWTAuthService {

    fun getJWT(authRequest: AuthRequest): String

    fun verifyJWT(token: String): UsernamePasswordAuthenticationToken

}