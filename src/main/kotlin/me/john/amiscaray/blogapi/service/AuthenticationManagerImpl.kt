package me.john.amiscaray.blogapi.service

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Service

@Service
class AuthenticationManagerImpl : AuthenticationManager {

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {

        TODO("Not Implemented yet. Assume UsernamePasswordAuthenticationToken, Principle is the username, " +
                "Credentials are the password. Return back a UsernamePasswordAuthenticationToken.")

    }

}