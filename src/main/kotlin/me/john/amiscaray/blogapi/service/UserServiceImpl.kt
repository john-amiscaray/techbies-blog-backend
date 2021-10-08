package me.john.amiscaray.blogapi.service

import me.john.amiscaray.blogapi.data.UserRepository
import me.john.amiscaray.blogapi.domain.AuthRequest
import me.john.amiscaray.blogapi.entities.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepo: UserRepository, private val jwtAuthService: JWTAuthService,
                      private val passwordEncoder: PasswordEncoder): UserService {

    @Throws(NoSuchElementException::class)
    override fun findUserById(id: Long): User {
        return userRepo.findById(id).orElseThrow()
    }

    override fun signUpUser(authRequest: AuthRequest) {
        val newUser = User(-1, authRequest.email, passwordEncoder.encode(authRequest.password))
        userRepo.save(newUser)
    }

    override fun loginUser(authRequest: AuthRequest): String {
        return jwtAuthService.getJWT(authRequest)
    }

    override fun findUserByUsername(username: String): User {
        TODO("Not yet implemented")
    }

    override fun getCurrentlySignedInUser(): UserDetails {
        TODO("Not yet implemented")
    }
}