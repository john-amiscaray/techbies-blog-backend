package me.john.amiscaray.blogapi.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import me.john.amiscaray.blogapi.data.UserRepository
import me.john.amiscaray.blogapi.domain.AuthRequest
import me.john.amiscaray.blogapi.domain.UserDetailsImpl
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException

@Service
class JWTAuthService(private val userDetailsService: UserDetailsService,
                     private val authManager: AuthenticationManager, private val userRepo: UserRepository): AuthService {

    @Value("\${app.secret}")
    private lateinit var secret: String

    private val tenHours = 36000000L

    @Throws(AuthenticationException::class)
    override fun getToken(authRequest: AuthRequest): String {

        val user = userDetailsService.loadUserByUsername(authRequest.email) as UserDetailsImpl
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authRequest.email,
                authRequest.password,
                user.authorities
            )
        )

        return JWT.create()
            .withSubject(user.username)
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

    override fun getSignupToken(authRequest: AuthRequest): String {

        return JWT.create()
            .withSubject(authRequest.email)
            .withExpiresAt(Date(System.currentTimeMillis() + tenHours))
            .sign(Algorithm.HMAC512(secret.toByteArray()))

    }

    override fun verifySignupToken(token: String): String {
        val username = JWT.require(Algorithm.HMAC512(secret.toByteArray()))
            .build()
            .verify(token)
            .subject

        return userRepo.findUserByEmail(username)?.email ?: throw NoSuchElementException("User not found")

    }
}