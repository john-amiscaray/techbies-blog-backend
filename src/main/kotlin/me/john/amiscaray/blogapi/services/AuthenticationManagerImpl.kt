package me.john.amiscaray.blogapi.services

import me.john.amiscaray.blogapi.data.UserRepository
import me.john.amiscaray.blogapi.exceptions.TechbiesAuthException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
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


        val user = userRepo.findUserByEmail(authentication.principal as String) ?: throw TechbiesAuthException("User not found")

        if(!user.accountActivated){

            throw TechbiesAuthException("Account not activated")

        }

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