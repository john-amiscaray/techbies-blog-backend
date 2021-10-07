package me.john.amiscaray.blogapi.service

import me.john.amiscaray.blogapi.data.UserRepository
import me.john.amiscaray.blogapi.exceptions.TechbiesAuthException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationManagerImpl(private val passwordEncoder: PasswordEncoder,
                                private val userRepo: UserRepository) : AuthenticationManager {

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {

/*
   Assume UsernamePasswordAuthenticationToken, Principle is the username, credentials are the password.
   Return a UsernamePasswordAuthenticationToken.
 */
        val user = userRepo.findUserByUsername(authentication.principal as String) ?: throw TechbiesAuthException("User not found")

        if(authentication.principal !is String || authentication.credentials !is String){

            throw TechbiesAuthException("Invalid credential types given. Provide a username and password.")

        }else{

            if(passwordEncoder.matches(user.password, authentication.credentials as String)){

                authentication.isAuthenticated = true
                return authentication

            }

            throw TechbiesAuthException("Unable to verify password.")

        }

    }

}