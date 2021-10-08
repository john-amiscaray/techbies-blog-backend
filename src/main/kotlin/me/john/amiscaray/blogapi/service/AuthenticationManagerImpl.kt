package me.john.amiscaray.blogapi.service

import me.john.amiscaray.blogapi.data.UserRepository
import me.john.amiscaray.blogapi.exceptions.TechbiesAuthException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationManagerImpl(private val passwordEncoder: PasswordEncoder,
                                private val userRepo: UserRepository) : AuthenticationManager {

    private val logger: Logger = LoggerFactory.getLogger(AuthenticationManager::class.java)

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

            if(passwordEncoder.matches(authentication.credentials as String, user.password)){

                return authentication

            }
            logger.info("passwords do not match for ${user.password} and ${authentication.credentials}")

            throw TechbiesAuthException("Unable to verify password.")

        }

    }

}