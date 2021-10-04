package me.john.amiscaray.blogapi.service

import me.john.amiscaray.blogapi.domain.AuthRequest
import me.john.amiscaray.blogapi.entities.User
import org.springframework.stereotype.Service

@Service
class UserServiceImp: UserService {
    override fun findUserById(id: Long): User {
        TODO("Not yet implemented")
    }

    override fun signUpUser(authRequest: AuthRequest) {
        TODO("Not yet implemented")
    }

    override fun loginUser(authRequest: AuthRequest): String {
        TODO("Not yet implemented")
    }

    override fun findUserByUsername(username: String): User {
        TODO("Not yet implemented")
    }
}