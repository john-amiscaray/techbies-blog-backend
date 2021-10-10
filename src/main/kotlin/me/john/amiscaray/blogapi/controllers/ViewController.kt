package me.john.amiscaray.blogapi.controllers

import me.john.amiscaray.blogapi.services.AuthService
import me.john.amiscaray.blogapi.services.UserService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class ViewController(private val userService: UserService, private val authService: AuthService) {

    @GetMapping("/views/activate/account")
    fun activateAccountAndShowView(@RequestParam("token") token: String): String{

        val username = authService.verifySignupToken(token)
        userService.activateAccount(username)
        return "activated-account"

    }

}