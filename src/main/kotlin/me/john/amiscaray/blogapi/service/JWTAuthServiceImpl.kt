package me.john.amiscaray.blogapi.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import me.john.amiscaray.blogapi.domain.AuthRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class JWTAuthServiceImpl(private val userDetailsService: UserDetailsService,
                         private val authManager: AuthenticationManager): JWTAuthService {

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    override fun getJWT(authRequest: AuthRequest): String {
        try {
            val user = userDetailsService.loadUserByUsername(authRequest.username)
            val auth: Authentication = authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    authRequest.username,
                    authRequest.password,
                    user.authorities
                )
            )
        } catch (ex: AuthenticationException) {
            throw IllegalArgumentException("User not found")
        }

        val tenHours = 36000000L
        return JWT.create()
            .withSubject(authRequest.username)
            .withExpiresAt(Date(System.currentTimeMillis() + tenHours))
            .sign(Algorithm.HMAC512(secret.toByteArray()))
    }

    override fun verifyJWT(token: String): UsernamePasswordAuthenticationToken {

        // Decode the token, verify it and get the subject
        val username = JWT.require(Algorithm.HMAC512(secret.toByteArray()))
            .build()
            .verify(token)
            .subject
        // If username is not null, get the UserDetails and return a new UsernamePasswordAuthenticationToken
        if (username != null) {
            val userDetails = userDetailsService.loadUserByUsername(username)
            return UsernamePasswordAuthenticationToken(
                userDetails,
                null, userDetails.authorities
            )
        }
        throw BadCredentialsException("Failed to verify the JWT token")

    }
}