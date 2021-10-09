package me.john.amiscaray.blogapi.services

import me.john.amiscaray.blogapi.data.UserRepository
import me.john.amiscaray.blogapi.domain.AuthRequest
import me.john.amiscaray.blogapi.entities.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepo: UserRepository, private val authService: AuthService,
                      private val passwordEncoder: PasswordEncoder, private val mailService: MailService): UserService {

    @Throws(NoSuchElementException::class)
    override fun findUserById(id: Long): User {
        return userRepo.findById(id).orElseThrow()
    }

    override fun signUpUser(authRequest: AuthRequest) {
        val newUser = User(-1, authRequest.email, passwordEncoder.encode(authRequest.password))
        userRepo.save(newUser)
        val templateVars: MutableMap<String, Any> = mutableMapOf()
        templateVars["user"] = authRequest.email.split("@")[0]
        mailService.sendTemplatedEmail(mailService.processTemplate("welcome-mail.html", templateVars),
            authRequest.email, "Welcome to Techbie's Blog")
    }

    override fun loginUser(authRequest: AuthRequest): String {
        return authService.getToken(authRequest)
    }

    override fun findUserByUsername(username: String): User {
        TODO("Not yet implemented")
    }

    override fun getCurrentlySignedInUser(): UserDetails {
        TODO("Not yet implemented")
    }

    override fun activateAccount(id: Long) {
        val user = userRepo.findById(id).orElseThrow()
        user.accountActivated = true
    }
}