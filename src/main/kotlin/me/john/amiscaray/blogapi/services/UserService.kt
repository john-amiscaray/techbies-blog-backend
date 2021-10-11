package me.john.amiscaray.blogapi.services

import me.john.amiscaray.blogapi.domain.AuthRequest
import me.john.amiscaray.blogapi.entities.User
import org.springframework.security.core.userdetails.UserDetails

interface UserService {

    fun findUserById(id: Long) : User

    fun signUpUser(authRequest: AuthRequest)

    fun loginUser(authRequest: AuthRequest): String

    fun findUserByUsername(username: String): User

    fun getCurrentlySignedInUser(): User

    fun activateAccount(id: Long)

    fun activateAccount(email: String)

}