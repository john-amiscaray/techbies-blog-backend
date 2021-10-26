package me.john.amiscaray.blogapi.services

import me.john.amiscaray.blogapi.data.UserRepository
import me.john.amiscaray.blogapi.domain.AuthRequest
import me.john.amiscaray.blogapi.entities.User
import me.john.amiscaray.blogapi.exceptions.TechbiesUserNotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserServiceImpl(private val userRepo: UserRepository,
                      private val authService: AuthService,
                      private val passwordEncoder: PasswordEncoder,
                      private val mailService: MailService): UserService {

    @Value("\${app.origin}")
    private lateinit var origin: String

    @Throws(NoSuchElementException::class)
    override fun findUserById(id: Long): User {
        return userRepo.findById(id).orElseThrow()
    }

    override fun signUpUser(authRequest: AuthRequest) {
        userRepo.save(User(authRequest.email, passwordEncoder.encode(authRequest.password)))
        val templateVars: MutableMap<String, Any> = mutableMapOf()
        templateVars["user"] = authRequest.email.split("@")[0]
        templateVars["token"] = authService.getSignupToken(authRequest)
        templateVars["origin"] = origin
        templateVars["email"] = authRequest.email
        mailService.sendTemplatedEmail(mailService.processTemplate("welcome-mail.html", templateVars),
            authRequest.email, "Welcome to Techbie's Blog")
    }

    override fun loginUser(authRequest: AuthRequest): String {
        return authService.getToken(authRequest)
    }

    override fun findUserByUsername(username: String): User {
        TODO("Not yet implemented")
    }

    override fun getCurrentlySignedInUser(): User {
        val auth = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken
        return userRepo.findUserByEmail(auth.principal as String) ?: throw TechbiesUserNotFoundException()
    }

    override fun activateAccount(id: Long) {
        val user = userRepo.findById(id).orElseThrow{ TechbiesUserNotFoundException() }
        user.accountActivated = true
    }

    override fun activateAccount(email: String) {
        val user = userRepo.findUserByEmail(email) ?: throw TechbiesUserNotFoundException()
        user.accountActivated = true
        userRepo.save(user)
    }

    override fun deleteUnactivatedUser(email: String) {
        val user = userRepo.findUserByEmail(email) ?: throw TechbiesUserNotFoundException()
        if(!user.accountActivated){
            userRepo.delete(user)
        }
    }

}