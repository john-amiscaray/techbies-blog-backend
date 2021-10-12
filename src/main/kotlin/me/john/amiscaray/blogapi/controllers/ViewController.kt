package me.john.amiscaray.blogapi.controllers

import com.auth0.jwt.exceptions.JWTVerificationException
import me.john.amiscaray.blogapi.services.AuthService
import me.john.amiscaray.blogapi.services.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class ViewController(private val userService: UserService, private val authService: AuthService) {

    @GetMapping("/views/activate/account")
    fun activateAccountAndShowView(@RequestParam("email") email: String, @RequestParam("token") token: String): String{

        return try{
            val username = authService.verifySignupToken(token)
            userService.activateAccount(username)
            "activated-account"
        }catch(err: JWTVerificationException){
            if(err.message?.contains("expired") == true){
                userService.deleteUnactivatedUser(email)
            }
            "activation-token-error"
        }catch(err: NoSuchElementException){
            "bad-auth-request"
        }

    }

}