package me.john.amiscaray.blogapi.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import me.john.amiscaray.blogapi.domain.AuthRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class JWTAuthService(private val userDetailsService: UserDetailsService,
                     val authManager: AuthenticationManager): AuthService {

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    @Throws(AuthenticationException::class)
    override fun getToken(authRequest: AuthRequest): String {

        val user = userDetailsService.loadUserByUsername(authRequest.email)
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authRequest.email,
                authRequest.password,
                user.authorities
            )
        )


        val tenHours = 36000000L
        return JWT.create()
            .withSubject(authRequest.email)
            .withExpiresAt(Date(System.currentTimeMillis() + tenHours))
            .sign(Algorithm.HMAC512(secret.toByteArray()))
    }

    override fun verifyToken(token: String): UsernamePasswordAuthenticationToken {

        // Decode the token, verify it and get the subject
        val username = JWT.require(Algorithm.HMAC512(secret.toByteArray()))
            .build()
            .verify(token)
            .subject
        // If username is not null, get the UserDetails and return a new UsernamePasswordAuthenticationToken
        if (username != null) {
            val userDetails = userDetailsService.loadUserByUsername(username)
            return UsernamePasswordAuthenticationToken(
                userDetails.username,
                userDetails.password, userDetails.authorities
            )
        }
        throw BadCredentialsException("Failed to verify the JWT token")

    }
}